//Dominic Cameron - HND Software Development
//Hospital Database System
package com.hospital.gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.hospital.dao.BaseDAO;
import com.hospital.dao.DAOFactory;
import com.hospital.dao.DatabaseConnection;
import com.hospital.models.Doctor;
import com.hospital.models.Drug;
import com.hospital.models.InsuranceCom;
import com.hospital.models.Patient;
import com.hospital.models.Prescription;
import com.hospital.models.Visit;


public class MainFrame extends JFrame {
    private JPanel mainPanel;

    public MainFrame() {
        setTitle("Hospital Database System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add welcome message
        JLabel welcomeLabel = new JLabel("Welcome to the Hospital Database System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(welcomeLabel, BorderLayout.CENTER);
        add(mainPanel);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        // View Table menu
        JMenu viewTableMenu = new JMenu("View / Edit");
        JMenuItem viewDoctorTableMenuItem = new JMenuItem("Doctor Table");
        viewDoctorTableMenuItem.addActionListener(e -> viewDoctorTable());
        JMenuItem viewPatientTableMenuItem = new JMenuItem("Patient Table");
        viewPatientTableMenuItem.addActionListener(e -> viewPatientTable());
        JMenuItem viewDrugTableMenuItem = new JMenuItem("Drug Table");
        viewDrugTableMenuItem.addActionListener(e -> viewDrugTable());
        JMenuItem viewICTableMenuItem = new JMenuItem("Insurance Company Table");
        viewICTableMenuItem.addActionListener(e -> viewInsuranceComTable());
        JMenuItem viewPrescriptioMenuItem = new JMenuItem("Prescription Table");
        viewPrescriptioMenuItem.addActionListener(e -> viewPrescriptionTable());
        JMenuItem viewVisitMenuItem = new JMenuItem("Visit Table");
        viewVisitMenuItem.addActionListener(e -> viewVisitTable());
        viewTableMenu.add(viewVisitMenuItem);
        viewTableMenu.add(viewPrescriptioMenuItem);
        viewTableMenu.add(viewDrugTableMenuItem);
        viewTableMenu.add(viewDoctorTableMenuItem);
        viewTableMenu.add(viewPatientTableMenuItem);
        viewTableMenu.add(viewICTableMenuItem);
        menuBar.add(viewTableMenu);

        // Add Records menu
        JMenu addRecordsMenu = new JMenu("Add Records");

        JMenuItem addDoctorFormMenuItem = new JMenuItem("Doctor Form");
        JMenuItem addPatientFormMenuItem = new JMenuItem("Patient Form");
        JMenuItem addDrugFormMenuItem = new JMenuItem("Drug Form");
        JMenuItem addInsuranceComFormMenuItem = new JMenuItem("Insurance Company Form");
        JMenuItem addPrescriptionFormMenuItem = new JMenuItem("Prescription Form");
        JMenuItem addVisitFormMenuItem = new JMenuItem("Visit Form");

        addDoctorFormMenuItem.addActionListener(e -> showDoctorForm());
        addPatientFormMenuItem.addActionListener(e -> showPatientForm());
        addDrugFormMenuItem.addActionListener(e -> showDrugForm());
        addInsuranceComFormMenuItem.addActionListener(e -> showInsuranceComForm());
        addPrescriptionFormMenuItem.addActionListener(e -> showPrescriptionForm());
        addVisitFormMenuItem.addActionListener(e -> showVisitForm());

        addRecordsMenu.add(addDoctorFormMenuItem);
        addRecordsMenu.add(addPatientFormMenuItem);
        addRecordsMenu.add(addDrugFormMenuItem);
        addRecordsMenu.add(addInsuranceComFormMenuItem);
        addRecordsMenu.add(addPrescriptionFormMenuItem);
        addRecordsMenu.add(addVisitFormMenuItem);
        menuBar.add(addRecordsMenu);

        setJMenuBar(menuBar);

        setVisible(true);
    }

    public void createTable(String tableType) {
        try {
            // Get the DAO from the factory
            BaseDAO<?> dao = DAOFactory.getDAO(tableType);

            // Create table with CustomTableModel
            JTable table = new JTable(new CustomTableModel(dao.getAll(), tableType));

            // Add table formatting
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            table.getTableHeader().setReorderingAllowed(false);
            table.setRowHeight(25);
            table.setAutoCreateRowSorter(true);

            // Update panel
            JScrollPane scrollPane = new JScrollPane(table);
            mainPanel.removeAll();
            mainPanel.add(scrollPane, BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();

            // Add right-click functionality
            new TableRightClick(table, tableType);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Error creating table: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
        }

    private void viewDoctorTable() {
        createTable("doctor");
    }

    private void viewPatientTable() {
        createTable("patient");
    }

    private void viewDrugTable() {
        createTable("drug");
    }

    public void viewInsuranceComTable() {
        createTable("insurance");
    }

    public void viewPrescriptionTable() {
        createTable("prescription");
    }

    public void viewVisitTable() {
        createTable("visit");
    }

    private <T> void showForm(String type, T entity) {
        try {
            BaseForm<T> form = FormFactory.getForm(type, this, entity);
            form.setVisible(true);
            if (form.isSubmitted()) {
                BaseDAO<T> dao = DAOFactory.getDAO(type);
                dao.save(entity);
                createTable(type);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Error showing form: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void showInsuranceComForm() {
        showForm("insurance", new InsuranceCom());
    }

    private void showDrugForm() {
        showForm("drug", new Drug());
    }

    private void showDoctorForm() {
        showForm("doctor", new Doctor());
    }

    private void showPatientForm() {
        showForm("patient", new Patient());
    }

    private void showPrescriptionForm() {
        showForm("prescription", new Prescription());
    }

    private void showVisitForm() {
        showForm("visit", new Visit());
    }

    public static void main(String[] args) {
        // Test the database connection
        DatabaseConnection.testConnection();

        SwingUtilities.invokeLater(MainFrame::new);
    }
}