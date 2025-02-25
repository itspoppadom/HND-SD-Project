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
import com.hospital.models.Doctor;
import com.hospital.models.Drug;
import com.hospital.models.InsuranceCom;
import com.hospital.models.Patient;
import com.hospital.models.Prescription;
import com.hospital.models.Visit;


public class TableRightClick extends MouseAdapter {
    private final JPopupMenu popupMenu;
    private final JTable table;
    private final String tableType;
    private final BaseDAO<?> dao;

    public TableRightClick(JTable table, String tableType) {
        this.table = table;
        this.tableType = tableType;
        this.dao = DAOFactory.getDAO(tableType);
        this.popupMenu = new JPopupMenu();
        
        JMenuItem addItem = new JMenuItem("Add");
        JMenuItem editItem = new JMenuItem("Edit");
        JMenuItem deleteItem = new JMenuItem("Delete");
        
        addItem.addActionListener(e -> handleAdd());
        editItem.addActionListener(e -> handleEdit());
        deleteItem.addActionListener(e -> handleDelete());
        
        popupMenu.add(addItem);
        popupMenu.add(editItem);
        popupMenu.add(deleteItem);
        
        table.addMouseListener(this);
    }
    
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
    
    private void handleAdd() {
        
    }
    
    private void handleEdit() {
        int row = table.getSelectedRow();
        if (row != -1) {
            try {
                // Get the ID(s) of the selected record
                String[] ids;
                if (tableType.equalsIgnoreCase("visit")) {
                    ids = new String[]{
                        table.getValueAt(row, 0).toString(), // patientID
                        table.getValueAt(row, 1).toString(), // doctorID
                        table.getValueAt(row, 2).toString()  // dateOfVisit
                    };
                } else {
                    ids = new String[]{table.getValueAt(row, 0).toString()};
                }

                // Get the record from the database with proper type casting
                switch (tableType.toLowerCase()) {
                    case "patient" -> {
                        Patient patient = (Patient) dao.get(ids);
                        handleFormSubmission(patient);
                    }
                    case "doctor" -> {
                        Doctor doctor = (Doctor) dao.get(ids);
                        handleFormSubmission(doctor);
                    }
                    case "drug" -> {
                        Drug drug = (Drug) dao.get(ids);
                        handleFormSubmission(drug);
                    }
                    case "prescription" -> {
                        Prescription prescription = (Prescription) dao.get(ids);
                        handleFormSubmission(prescription);
                    }
                    case "insurance" -> {
                        InsuranceCom insurance = (InsuranceCom) dao.get(ids);
                        handleFormSubmission(insurance);
                    }
                    case "visit" -> {
                        Visit visit = (Visit) dao.get(ids);
                        handleFormSubmission(visit);
                    }
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

    private <T> void handleFormSubmission(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Record not found");
        }

        try {
            // Create and show the form with proper typing
            BaseForm<T> form = (BaseForm<T>) FormFactory.getForm(
                tableType,
                (JFrame) SwingUtilities.getWindowAncestor(table),
                entity
            );
            form.setVisible(true);

            // Handle form submission
            if (form.isSubmitted()) {
                // Update the record using the DAO with proper type casting
                switch (tableType.toLowerCase()) {
                    case "patient" -> {
                        BaseDAO<Patient> patientDao = DAOFactory.getDAO("patient");
                        patientDao.update((Patient) entity);
                    }
                    case "doctor" -> {
                        BaseDAO<Doctor> doctorDao = DAOFactory.getDAO("doctor");
                        doctorDao.update((Doctor) entity);
                    }
                    case "drug" -> {
                        BaseDAO<Drug> drugDao = DAOFactory.getDAO("drug");
                        drugDao.update((Drug) entity);
                    }
                    case "prescription" -> {
                        BaseDAO<Prescription> prescriptionDao = DAOFactory.getDAO("prescription");
                        prescriptionDao.update((Prescription) entity);
                    }
                    case "insurance" -> {
                        BaseDAO<InsuranceCom> insuranceDao = DAOFactory.getDAO("insurance");
                        insuranceDao.update((InsuranceCom) entity);
                    }
                    case "visit" -> {
                        BaseDAO<Visit> visitDao = DAOFactory.getDAO("visit");
                        visitDao.update((Visit) entity);
                    }
                    default -> throw new IllegalArgumentException("Unknown table type: " + tableType);
                }
                
                // Refresh the table
                table.setModel(new CustomTableModel(dao.getAll(), tableType));
                JOptionPane.showMessageDialog(table, "Record updated successfully");
            }
        } catch (ClassCastException ex) {
            JOptionPane.showMessageDialog(
                table,
                "Error: Invalid form type for " + tableType,
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                table,
                "Error updating record: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
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
}

