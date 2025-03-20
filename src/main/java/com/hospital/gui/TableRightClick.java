package com.hospital.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import com.hospital.dao.BaseDAO;
import com.hospital.dao.DAOFactory;
import com.hospital.dao.PatientDAO;
import com.hospital.exceptions.DatabaseException;
import com.hospital.models.Doctor;
import com.hospital.models.Drug;
import com.hospital.models.InsuranceCom;
import com.hospital.models.Patient;
import com.hospital.models.Prescription;
import com.hospital.models.Visit;



public class TableRightClick extends MouseAdapter {

    // Attributes
    private final JPopupMenu popupMenu;
    private final JTable table;
    private final String tableType;
    private final BaseDAO<?> dao;
    

    // Constructor
    public TableRightClick(JTable table, String tableType) {
        
        // Initialize attributes
        this.table = table;
        this.tableType = tableType;
        this.dao = DAOFactory.getDAO(tableType);
        this.popupMenu = new JPopupMenu();
        
        // Create menu items
        JMenuItem addItem = new JMenuItem("Add");
        JMenuItem editItem = new JMenuItem("Edit");
        JMenuItem deleteItem = new JMenuItem("Delete");
        JMenuItem reloadTable = new JMenuItem("Refresh Table");
        
        // Add action listeners
        addItem.addActionListener(e -> handleAdd());
        editItem.addActionListener(e -> handleEdit());
        deleteItem.addActionListener(e -> handleDelete());
        reloadTable.addActionListener(e -> handleReload());

        // Add menu items to popup menu
        popupMenu.add(addItem);
        popupMenu.add(editItem);
        popupMenu.add(deleteItem);
        popupMenu.add(reloadTable);

        // Add patient-specific menu items
        if (tableType.equalsIgnoreCase("patient")) {
            popupMenu.addSeparator();
            JMenuItem viewDoctorItem = new JMenuItem("View Primary Doctor");
            // Optional: set an icon for the menu item
            //viewDoctorItem.setIcon(new ImageIcon(getClass().getResource("icons/doctor.png"))); // Optional
            viewDoctorItem.addActionListener(e -> showPrimaryDoctor());
            popupMenu.add(viewDoctorItem);
        }
        
        // Add mouse listener to table
        table.addMouseListener(this);
    }
    

    // Methods to handle mouse events
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            showMenu(e);
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            showMenu(e);
        }
    }
    
    private void showMenu(MouseEvent e) {
        int row = table.rowAtPoint(e.getPoint());
        if (row >= 0 && row < table.getRowCount()) {
            table.setRowSelectionInterval(row, row);
        }
        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }
    
    // Methods to handle menu item actions
    private void handleAdd() {
        try {
            // Create a new empty entity based on table type with proper typing
            Object entity = switch (tableType.toLowerCase()) {
                case "patient" -> new Patient();
                case "doctor" -> new Doctor();
                case "drug" -> new Drug();
                case "prescription" -> new Prescription();
                case "insurance" -> new InsuranceCom();
                case "visit" -> new Visit();
                default -> throw new IllegalArgumentException("Unknown table type: " + tableType);
            };

            // Use FormFactory consistently with proper typing
            BaseForm<?> form = FormFactory.getForm(
                tableType,
                (JFrame) SwingUtilities.getWindowAncestor(table),
                entity
            );
            form.setVisible(true);

            if (form.isSubmitted()) {
                // Get properly typed DAO for the entity
                try {
                    BaseDAO<?> typedDao = DAOFactory.getDAO(tableType);
                    switch (tableType.toLowerCase()) {
                        case "patient" -> ((BaseDAO<Patient>) typedDao).save((Patient) entity);
                        case "doctor" -> ((BaseDAO<Doctor>) typedDao).save((Doctor) entity);
                        case "drug" -> ((BaseDAO<Drug>) typedDao).save((Drug) entity);
                        case "prescription" -> ((BaseDAO<Prescription>) typedDao).save((Prescription) entity);
                        case "insurance" -> ((BaseDAO<InsuranceCom>) typedDao).save((InsuranceCom) entity);
                        case "visit" -> ((BaseDAO<Visit>) typedDao).save((Visit) entity);
                        default -> throw new IllegalArgumentException("Unknown table type: " + tableType);
                        }
                
                        // Refresh table
                        table.setModel(new CustomTableModel(typedDao.getAll(), tableType));
                        JOptionPane.showMessageDialog(table, "Record added successfully", "Success" ,
                        JOptionPane.INFORMATION_MESSAGE);
                    } catch (DatabaseException ex) {
                    JOptionPane.showMessageDialog(
                        table,
                        "Error adding record: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );}
                    }
            } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                table,
                "Error adding record: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    

    // Method to handle editing a record
    private void handleEdit() {
        int row = table.getSelectedRow();
        if (row != -1) {
            try {
                int modelRow = table.convertRowIndexToModel(row);
                
                // Get the ID(s) of the selected record
                String[] ids;
                if (tableType.equalsIgnoreCase("visit")) {
                    ids = new String[]{
                        table.getModel().getValueAt(modelRow, 0).toString(),
                        table.getModel().getValueAt(modelRow, 1).toString(),
                        table.getModel().getValueAt(modelRow, 2).toString()
                    };
                } else {
                    ids = new String[]{table.getModel().getValueAt(modelRow, 0).toString()};
                }

                // Get record with proper typing
                switch (tableType.toLowerCase()) {
                    case "patient" -> handleFormSubmission((Patient) dao.get(ids));
                    case "doctor" -> handleFormSubmission((Doctor) dao.get(ids));
                    case "drug" -> handleFormSubmission((Drug) dao.get(ids));
                    case "prescription" -> handleFormSubmission((Prescription) dao.get(ids));
                    case "insurance" -> handleFormSubmission((InsuranceCom) dao.get(ids));
                    case "visit" -> handleFormSubmission((Visit) dao.get(ids));
                    default -> throw new IllegalArgumentException("Unknown table type: " + tableType);
                }
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    table,
                    "Error editing record: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // Method to handle form submission
    private <T> void handleFormSubmission(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Record not found");
        }

        try {
            // Get properly typed DAO for updates
            BaseDAO<T> typedDao = (BaseDAO<T>) DAOFactory.getDAO(tableType);
            
            // Create and show form with the existing entity data
            BaseForm<T> form = (BaseForm<T>) FormFactory.getForm(
                tableType,
                (JFrame) SwingUtilities.getWindowAncestor(table),
                entity  // This should contain the data from dao.get(ids)
            );

            // Initialize form and make visible
            form.initializeForm();  // Add this line to ensure form fields are populated
            form.setVisible(true);

            if (form.isSubmitted()) {
                // Update entity in database
                typedDao.update(entity);
                
                // Refresh table
                table.setModel(new CustomTableModel(typedDao.getAll(), tableType));
                JOptionPane.showMessageDialog(table, "Record updated successfully");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                table,
                "Error updating record: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    // Method to handle deleting a record
    private void handleDelete() {
        int row = table.getSelectedRow();
        if (row != -1) {
            try {
                String[] ids;
                if (tableType.equalsIgnoreCase("visit")) {
                // For Visit table, we need three columns
                ids = new String[]{
                    table.getValueAt(row, 0).toString(), // patientID
                    table.getValueAt(row, 1).toString(), // doctorID
                    table.getValueAt(row, 2).toString()  // dateOfVisit
                    };
                } else {
                    // For other tables that use a single ID
                    ids = new String[]{table.getValueAt(row, 0).toString()};
                }

                int confirm = JOptionPane.showConfirmDialog(
                    table,
                    "Are you sure you want to delete this record?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirm == JOptionPane.YES_OPTION) {
                    dao.delete(ids);
                    JOptionPane.showMessageDialog(table, "Record deleted successfully");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    table,
                    "Error deleting record: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // Method to handle reloading the table
    private void handleReload() {
        try {
            table.setModel(new CustomTableModel(dao.getAll(), tableType));
        } catch (DatabaseException ex) {
            JOptionPane.showMessageDialog(
                table,
                "Error refreshing table: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Method to show primary doctor information
    private void showPrimaryDoctor() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        try {
            String patientId = table.getValueAt(row, 0).toString();
            
            // Use the specific DAO getter
            PatientDAO patientDao = DAOFactory.getPatientDAO();
            Doctor doctor = patientDao.getPrimaryDoctor(patientId);

            if (doctor != null) {
                String message = String.format("""
                    Primary Doctor Information:
                    Dr. %s %s
                    Specialization: %s
                    Hospital: %s
                    Contact: %s
                    """,
                    doctor.getFirstName(),
                    doctor.getLastName(),
                    doctor.getSpecialization(),
                    doctor.getHospital(),
                    doctor.getEmail()
                );
                JOptionPane.showMessageDialog(
                    table,
                    message,
                    "Primary Doctor Information",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                showWarning("No primary doctor found for this patient");
            }
        } catch (DatabaseException ex) {
            showError("Database error: " + ex.getMessage());
        } catch (Exception ex) {
            showError("Unexpected error: " + ex.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
            table,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(
            table,
            message,
            "Warning",
            JOptionPane.WARNING_MESSAGE
        );
    }
}

