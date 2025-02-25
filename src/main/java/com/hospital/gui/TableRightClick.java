package com.hospital.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import com.hospital.dao.BaseDAO;
import com.hospital.dao.DAOFactory;

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
        // Add your add logic here
        System.out.println("Add clicked!");
    }
    
    private void handleEdit() {
        // Add your edit logic here
        System.out.println("Edit clicked!");
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