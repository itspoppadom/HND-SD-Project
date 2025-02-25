package com.hospital.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

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

import com.hospital.dao.DatabaseConnection;
import com.hospital.dao.DoctorDAO;
import com.hospital.dao.DrugDAO;
import com.hospital.dao.InsuranceComDAO;
import com.hospital.dao.PatientDAO;
import com.hospital.dao.PrescriptionDAO;
import com.hospital.dao.VisitDAO;
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
        JMenu viewTableMenu = new JMenu("View Table");
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
        addDoctorFormMenuItem.addActionListener(e -> showDoctorForm());
        JMenuItem addPatientFormMenuItem = new JMenuItem("Patient Form");
        addPatientFormMenuItem.addActionListener(e -> showPatientForm());
        JMenuItem addDrugFormMenuItem = new JMenuItem("Drug Form");
        addDrugFormMenuItem.addActionListener(e -> showDrugForm());
        JMenuItem addInsuranceComFormMenuItem = new JMenuItem("Insurance Company Form");
        addInsuranceComFormMenuItem.addActionListener(e -> showInsuranceComForm());
        addRecordsMenu.add(addDrugFormMenuItem);
        addRecordsMenu.add(addInsuranceComFormMenuItem);
        addRecordsMenu.add(addDoctorFormMenuItem);
        addRecordsMenu.add(addPatientFormMenuItem);
        menuBar.add(addRecordsMenu);

        setJMenuBar(menuBar);

        setVisible(true);
    }

    public void createTable(Object[][] data, String[] columnNames) {
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.removeAll();
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();

        
    }

    private void viewDoctorTable() {
        // Retrieve the list of doctors from the database
        List<Doctor> doctors = DoctorDAO.getAllDoctors();

        // Create column names
        String[] columnNames = {"Doctor ID", "First Name", "Last Name", "Address", "Email", "Specialization", "Hospital"};

        // Create data array
        Object[][] data = new Object[doctors.size()][7];
        for (int i = 0; i < doctors.size(); i++) {
            Doctor doctor = doctors.get(i);
            data[i][0] = doctor.getDoctorID();
            data[i][1] = doctor.getFirstName();
            data[i][2] = doctor.getLastName();
            data[i][3] = doctor.getAddress();
            data[i][4] = doctor.getEmail();
            data[i][5] = doctor.getSpecialization();
            data[i][6] = doctor.getHospital();
        }

        // Create JTable with the data
        createTable(data, columnNames);
    }

    private void viewPatientTable() {
        // Retrieve the list of patients from the database
        List<Patient> patients = PatientDAO.getAllPatients();

        // Create column names
        String[] columnNames = {"Patient ID", "First Name", "Last Name", "Postcode", "Address", "Phone", "Email", "Insurance ID"};

        // Create data array
        Object[][] data = new Object[patients.size()][8];
        for (int i = 0; i < patients.size(); i++) {
            Patient patient = patients.get(i);
            data[i][0] = patient.getPatientID();
            data[i][1] = patient.getFirstName();
            data[i][2] = patient.getLastName();
            data[i][3] = patient.getPostcode();
            data[i][4] = patient.getAddress();
            data[i][5] = patient.getPhoneNumber();
            data[i][6] = patient.getEmail();
            data[i][7] = patient.getInsuranceID();
        }

        // Create JTable with the data
        createTable(data, columnNames);
    }

    private void viewDrugTable() {
        // Retrieve the list of drugs from the database
        List<Drug> drugs = DrugDAO.getAllDrugs();
        // Create column names
        String[] columnNames = {"Drug ID", "Name", "SideEffects", "Benefits"};
        // Create data array
        Object[][] data = new Object[drugs.size()][4];
        for (int i = 0; i < drugs.size(); i++) {
            Drug drug = drugs.get(i);
            data[i][0] = drug.getDrugID();
            data[i][1] = drug.getDrugName();
            data[i][2] = drug.getSideEffects();
            data[i][3] = drug.getBenefits();
        }

        // Create JTable with the data
        createTable(data, columnNames);
    }
    public void viewInsuranceComTable(){
        List<InsuranceCom> insuranceCompanies = InsuranceComDAO.getAllInsuranceCom(); 
        String[] columnNames = {"Insurance ID", "Name", "Address", "Phone"};
        Object[][] data = new Object[insuranceCompanies.size()][4];
        for (int i = 0; i < insuranceCompanies.size(); i++) {
            InsuranceCom insuranceCompany = insuranceCompanies.get(i);
            data[i][0] = insuranceCompany.getInsuranceID();
            data[i][1] = insuranceCompany.getCompanyName();
            data[i][2] = insuranceCompany.getAddress();
            data[i][3] = insuranceCompany.getPhone();
        }
        createTable(data, columnNames);

    }

    public void viewPrescriptionTable(){
        List<Prescription> prescriptions = PrescriptionDAO.getAllPrescriptions();
        String[] columnNames = {"Prescription ID", "Date Prescribed", "Dosage", "Duration", "Comment", "Drug ID", "Doctor ID", "Patient ID"};   
        Object[][] data = new Object[prescriptions.size()][8];
        for (int i = 0; i < prescriptions.size(); i++) {
            Prescription prescription = prescriptions.get(i);
            data[i][0] = prescription.getPrescriptionID();
            data[i][1] = prescription.getDatePrescribed();
            data[i][2] = prescription.getDosage();
            data[i][3] = prescription.getDuration();
            data[i][4] = prescription.getComment();
            data[i][5] = prescription.getDrugID();
            data[i][6] = prescription.getDoctorID();
            data[i][7] = prescription.getPatientID();
        }
        createTable(data, columnNames);
    }
    public void viewVisitTable(){
        List<Visit> visits = VisitDAO.getAllVisits();
        String[] columnNames = {"PatientID", "DoctorID", "DateOfVisit", "Symptoms", "DiagnosisID"};
        Object[][] data = new Object[visits.size()][5];
        for (int i = 0; i < visits.size(); i++) {
            Visit visit = visits.get(i);
            data[i][0] = visit.getPatientID();
            data[i][1] = visit.getDoctorID();
            data[i][2] = visit.getDateOfVisit();
            data[i][3] = visit.getSymptoms();
            data[i][4] = visit.getDiagnosisID();
        }
        createTable(data, columnNames);
    }
    private void showInsuranceComForm(){
        // Implement the logic to display the drug form
        JOptionPane.showMessageDialog(this, "Displaying Drug Form");
    
    }


    private void showDrugForm() {
        // Implement the logic to display the drug form
        JOptionPane.showMessageDialog(this, "Displaying Drug Form");
    }

    private void showDoctorForm() {
        // Implement the logic to display the doctor form
        JOptionPane.showMessageDialog(this, "Displaying Doctor Form");
    }

    private void showPatientForm() {
        // Implement the logic to display the patient form
        JOptionPane.showMessageDialog(this, "Displaying Patient Form");
    }

    public static void main(String[] args) {
        // Test the database connection
        DatabaseConnection.testConnection();

        SwingUtilities.invokeLater(MainFrame::new);
    }
}