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
    

    // Constants

    // Constants for table types
    private static final String PATIENT = "patient";
    private static final String DOCTOR = "doctor";
    private static final String DRUG = "drug";
    private static final String PRESCRIPTION = "prescription";
    private static final String INSURANCE = "insurance";
    private static final String VISIT = "visit";

    // Constants for menu items
    private static final String ADD = "Add";
    private static final String EDIT = "Edit";
    private static final String DELETE = "Delete";
    private static final String REFRESH = "Refresh Table";
    private static final String VIEW_DOCTOR = "View Primary Doctor";
    private static final String VIEW_INSURANCE = "View Insurance Company";

    // Constants for messages
    private static final String RECORD_ADDED = "Record added successfully";
    private static final String RECORD_UPDATED = "Record updated successfully";
    private static final String RECORD_DELETED = "Record deleted successfully";
    private static final String NO_DOCTOR = "No primary doctor found for this patient";
    private static final String NO_INSURANCE = "No insurance company found for this patient";
    private static final String ERROR_ADDING = "Error adding record: ";
    private static final String ERROR_EDITING = "Error updating record: ";
    private static final String ERROR_DELETING = "Error deleting record: ";
    private static final String ERROR_REFRESHING = "Error refreshing table: ";
    private static final String ERROR_DOCTOR = "Error getting primary doctor information: ";
    private static final String ERROR_INSURANCE = "Error getting insurance company information: ";
    private static final String UNEXPECTED_ERROR = "Unexpected error: ";
    private static final String ERROR = "Error";
    private static final String WARNING = "Warning";
    private static final String PRIMARY_DOCTOR_INFO = "Primary Doctor Information";
    private static final String UNKNOWN_TABLE_TYPE = "Unknown table type: ";
    private static final String INSURANCE_COMPANY_INFO = "Insurance Company Information";


    // Constructor
    public TableRightClick(JTable table, String tableType) {
        
        // Initialize attributes
        this.table = table;
        this.tableType = tableType;
        this.dao = DAOFactory.getDAO(tableType);
        this.popupMenu = new JPopupMenu();
        
        // Create menu items
        JMenuItem addItem = new JMenuItem(ADD);
        JMenuItem editItem = new JMenuItem(EDIT);
        JMenuItem deleteItem = new JMenuItem(DELETE);
        JMenuItem reloadTable = new JMenuItem(REFRESH);
        
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
        if (tableType.equalsIgnoreCase(PATIENT)) {
            popupMenu.addSeparator();
            JMenuItem viewDoctorItem = new JMenuItem(VIEW_DOCTOR);
            JMenuItem viewInsuranceItem = new JMenuItem(VIEW_INSURANCE);
            // Optional: set an icon for the menu item
            //viewDoctorItem.setIcon(new ImageIcon(getClass().getResource("icons/doctor.png"))); // Optional
            viewDoctorItem.addActionListener(e -> showPrimaryDoctor());
            viewInsuranceItem.addActionListener(e -> showInsuranceCompany());
            
            popupMenu.add(viewDoctorItem);
            popupMenu.add(viewInsuranceItem);
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
                case PATIENT -> new Patient();
                case DOCTOR -> new Doctor();
                case DRUG -> new Drug();
                case PRESCRIPTION -> new Prescription();
                case INSURANCE -> new InsuranceCom();
                case VISIT -> new Visit();
                default -> throw new IllegalArgumentException(UNKNOWN_TABLE_TYPE + tableType);
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
                        case PATIENT -> ((BaseDAO<Patient>) typedDao).save((Patient) entity);
                        case DOCTOR -> ((BaseDAO<Doctor>) typedDao).save((Doctor) entity);
                        case DRUG -> ((BaseDAO<Drug>) typedDao).save((Drug) entity);
                        case PRESCRIPTION -> ((BaseDAO<Prescription>) typedDao).save((Prescription) entity);
                        case INSURANCE -> ((BaseDAO<InsuranceCom>) typedDao).save((InsuranceCom) entity);
                        case VISIT -> ((BaseDAO<Visit>) typedDao).save((Visit) entity);
                        default -> throw new IllegalArgumentException(UNKNOWN_TABLE_TYPE + tableType);
                        }
                
                        // Refresh table
                        table.setModel(new CustomTableModel(typedDao.getAll(), tableType));
                        JOptionPane.showMessageDialog(table, RECORD_ADDED, "Success" ,
                        JOptionPane.INFORMATION_MESSAGE);
                    } catch (DatabaseException ex) {
                    JOptionPane.showMessageDialog(
                        table,
                        ERROR_ADDING + ex.getMessage(),
                        ERROR,
                        JOptionPane.ERROR_MESSAGE
                    );}
                    }
            } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                table,
                ERROR_ADDING + ex.getMessage(),
                ERROR,
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
                if (tableType.equalsIgnoreCase(VISIT)) {
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
                    case PATIENT -> handleFormSubmission((Patient) dao.get(ids));
                    case DOCTOR -> handleFormSubmission((Doctor) dao.get(ids));
                    case DRUG -> handleFormSubmission((Drug) dao.get(ids));
                    case PRESCRIPTION -> handleFormSubmission((Prescription) dao.get(ids));
                    case INSURANCE -> handleFormSubmission((InsuranceCom) dao.get(ids));
                    case VISIT -> handleFormSubmission((Visit) dao.get(ids));
                    default -> throw new IllegalArgumentException(UNKNOWN_TABLE_TYPE + tableType);
                }
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    table,
                    ERROR_EDITING + ex.getMessage(),
                    ERROR,
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
                JOptionPane.showMessageDialog(table, RECORD_UPDATED);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                table,
                ERROR_EDITING + ex.getMessage(),
                ERROR,
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
                if (tableType.equalsIgnoreCase(VISIT)) {
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
                    JOptionPane.showMessageDialog(table, RECORD_DELETED);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    table,
                    ERROR_DELETING + ex.getMessage(),
                    ERROR,
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
                ERROR_REFRESHING + ex.getMessage(),
                ERROR,
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
                    PRIMARY_DOCTOR_INFO,
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                showWarning(NO_DOCTOR);
            }
        } catch (DatabaseException ex) {
            showError("Database error: " + ex.getMessage() + ERROR_DOCTOR);
        } catch (Exception ex) {
            showError(UNEXPECTED_ERROR + ex.getMessage() + ERROR_DOCTOR);
        }
    }
    // Show insurance company information using regular factory method
    private void showInsuranceCompany() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        try {
            String insuranceID = table.getValueAt(row, 7).toString();

            //Use the specific DAO getter
            BaseDAO<InsuranceCom> indao = DAOFactory.getDAO(INSURANCE);
            InsuranceCom insuranceCom = indao.get(insuranceID);

            if (insuranceCom != null) {
                String message = String.format("""
                    Insurance Company Information:
                    Company Name: %s
                    Address: %s
                    Phone: %s
                    """,
                    insuranceCom.getCompanyName(),
                    insuranceCom.getAddress(),
                    insuranceCom.getPhone()
                );
                JOptionPane.showMessageDialog(
                    table,
                    message,
                    INSURANCE_COMPANY_INFO,
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                showWarning(NO_INSURANCE);
            }
        } catch (Exception e) {
            showError(ERROR_INSURANCE + e.getMessage());
        }
    }



    // Helper methods to show messages
    private void showError(String message) {
        JOptionPane.showMessageDialog(
            table,
            message,
            ERROR,
            JOptionPane.ERROR_MESSAGE
        );
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(
            table,
            message,
            WARNING,
            JOptionPane.WARNING_MESSAGE
        );
    }
}

