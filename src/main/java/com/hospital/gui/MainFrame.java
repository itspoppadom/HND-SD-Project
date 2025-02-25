package com.hospital.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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

    public void createTable(Object[][] data, String[] columnNames, String tableType) {
    JTable table = new JTable(data, columnNames);
    
    // Add table formatting
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    table.getTableHeader().setReorderingAllowed(false);
    table.setRowHeight(25);
    
    // Add a sorter
    table.setAutoCreateRowSorter(true);
    
    JScrollPane scrollPane = new JScrollPane(table);
    mainPanel.removeAll();
    mainPanel.add(scrollPane, BorderLayout.CENTER);
    mainPanel.revalidate();
    mainPanel.repaint();
    new TableRightClick(table, tableType);
}



    private void viewDoctorTable() {
        // Get the DAO from the factory
        BaseDAO<Doctor> doctorDAO = DAOFactory.getDAO("doctor");
        
        // Retrieve the list of doctors from the database
        List<Doctor> doctors = doctorDAO.getAll();
        
        String tableType = "doctor";
        String[] columnNames = {"Doctor ID", "First Name", "Last Name", "Address", "Email", "Specialization", "Hospital"};

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

        createTable(data, columnNames, tableType);
    }

    private void viewPatientTable() {
    BaseDAO<Patient> patientDAO = DAOFactory.getDAO("patient");
    List<Patient> patients = patientDAO.getAll();
    
    String tableType = "patient";
    // Update column order to match display requirements
    String[] columnNames = {
        "Patient ID", 
        "First Name", 
        "Last Name", 
        "Address",
        "Postcode", 
        "Phone", 
        "Email",
        "Insurance ID"
    };

    Object[][] data = new Object[patients.size()][8];
    for (int i = 0; i < patients.size(); i++) {
        Patient patient = patients.get(i);
        // Update data order to match column headers
        data[i][0] = patient.getPatientID();
        data[i][1] = patient.getFirstName();
        data[i][2] = patient.getLastName();
        data[i][3] = patient.getAddress();
        data[i][4] = patient.getPostcode();
        data[i][5] = patient.getPhoneNumber();
        data[i][6] = patient.getEmail();
        data[i][7] = patient.getInsuranceID();
    }

    createTable(data, columnNames, tableType);
}

    private void viewDrugTable() {
        BaseDAO<Drug> drugDAO = DAOFactory.getDAO("drug");
        List<Drug> drugs = drugDAO.getAll();
        // Table type
        String tableType = "drug";
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
        createTable(data, columnNames, tableType);
        
    }
    public void viewInsuranceComTable(){
         BaseDAO<InsuranceCom> insuranceDAO = DAOFactory.getDAO("insurance");
        List<InsuranceCom> insuranceCompanies = insuranceDAO.getAll();
        // Table type
        String tableType = "insurance";


        // Create column names
        String[] columnNames = {"Insurance ID", "Name", "Address", "Phone"};
        Object[][] data = new Object[insuranceCompanies.size()][4];
        for (int i = 0; i < insuranceCompanies.size(); i++) {
            InsuranceCom insuranceCompany = insuranceCompanies.get(i);
            data[i][0] = insuranceCompany.getInsuranceID();
            data[i][1] = insuranceCompany.getCompanyName();
            data[i][2] = insuranceCompany.getAddress();
            data[i][3] = insuranceCompany.getPhone();
        }
        createTable(data, columnNames, tableType);

    }

    public void viewPrescriptionTable(){
        BaseDAO<Prescription> prescriptionDAO = DAOFactory.getDAO("prescription");
        List<Prescription> prescriptions = prescriptionDAO.getAll();
        // Table type
        String tableType = "prescription";
        // Create column names
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
        createTable(data, columnNames, tableType);
    }
    public void viewVisitTable(){
        BaseDAO<Visit> visitDAO = DAOFactory.getDAO("visit");
        List<Visit> visits = visitDAO.getAll();
        // Table type
        String tableType = "visit";
        // Create column names
        String[] columnNames = {"PatientID", "DoctorID", "DateOfVisit", "Symptoms", "DiagnosisID"};
        // Create data array
        Object[][] data = new Object[visits.size()][5];
        for (int i = 0; i < visits.size(); i++) {
            Visit visit = visits.get(i);
            data[i][0] = visit.getPatientID();
            data[i][1] = visit.getDoctorID();
            data[i][2] = visit.getDateOfVisit();
            data[i][3] = visit.getSymptoms();
            data[i][4] = visit.getDiagnosisID();
        }
        createTable(data, columnNames, tableType);
    }
    private void showInsuranceComForm() {
    InsuranceCom insuranceCom = new InsuranceCom();
    BaseForm<InsuranceCom> form = FormFactory.getForm("insurance", this, insuranceCom);
    form.setVisible(true);
    if (form.isSubmitted()) {
        BaseDAO<InsuranceCom> insuranceDAO = DAOFactory.getDAO("insurance");
        insuranceDAO.save(insuranceCom);
        viewInsuranceComTable();
    }
}

private void showDrugForm() {
    Drug drug = new Drug();
    BaseForm<Drug> form = FormFactory.getForm("drug", this, drug);
    form.setVisible(true);
    if (form.isSubmitted()) {
        BaseDAO<Drug> drugDAO = DAOFactory.getDAO("drug");
        drugDAO.save(drug);
        viewDrugTable();
    }
}

private void showDoctorForm() {
    Doctor doctor = new Doctor();
    BaseForm<Doctor> form = FormFactory.getForm("doctor", this, doctor);
    form.setVisible(true);
    if (form.isSubmitted()) {
        BaseDAO<Doctor> doctorDAO = DAOFactory.getDAO("doctor");
        doctorDAO.save(doctor);
        viewDoctorTable();
    }
}

private void showPatientForm() {
    Patient patient = new Patient();
    BaseForm<Patient> form = FormFactory.getForm("patient", this, patient);
    form.setVisible(true);
    if (form.isSubmitted()) {
        BaseDAO<Patient> patientDAO = DAOFactory.getDAO("patient");
        patientDAO.save(patient);
        viewPatientTable();
    }
}

// Add missing forms
private void showPrescriptionForm() {
    Prescription prescription = new Prescription();
    BaseForm<Prescription> form = FormFactory.getForm("prescription", this, prescription);
    form.setVisible(true);
    if (form.isSubmitted()) {
        BaseDAO<Prescription> prescriptionDAO = DAOFactory.getDAO("prescription");
        prescriptionDAO.save(prescription);
        viewPrescriptionTable();
    }
}

private void showVisitForm() {
    Visit visit = new Visit();
    BaseForm<Visit> form = FormFactory.getForm("visit", this, visit);
    form.setVisible(true);
    if (form.isSubmitted()) {
        BaseDAO<Visit> visitDAO = DAOFactory.getDAO("visit");
        visitDAO.save(visit);
        viewVisitTable();
    }
}

    public static void main(String[] args) {
        // Test the database connection
        DatabaseConnection.testConnection();

        SwingUtilities.invokeLater(MainFrame::new);
    }
}