//Dominic Cameron - HND Software Development
//Hospital Database System
package com.hospital.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Collections;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.hospital.dao.BaseDAO;
import com.hospital.dao.DAOFactory;
import com.hospital.dao.DatabaseConnection;
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

        // Search menu
        JMenu searchMenu = new JMenu("Search");
        JMenuItem searchDoctorMenuItem = new JMenuItem("Doctor");
        JMenuItem searchPatientMenuItem = new JMenuItem("Patient");
        JMenuItem searchDrugMenuItem = new JMenuItem("Drug");
        JMenuItem searchInsuranceComMenuItem = new JMenuItem("Insurance Company");
        JMenuItem searchPrescriptionMenuItem = new JMenuItem("Prescription");
        JMenuItem searchVisitMenuItem = new JMenuItem("Visit");

        searchDoctorMenuItem.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "Enter Doctor ID:");
            ResultSet("doctor", id);
        });
        searchPatientMenuItem.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "Enter Patient ID:");
            ResultSet("patient", id);
        });
        searchDrugMenuItem.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "Enter Drug ID:");
            ResultSet("drug", id);
        });
        searchInsuranceComMenuItem.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "Enter Insurance Company ID:");
            ResultSet("insurance", id);
        });
        
        
        searchPrescriptionMenuItem.addActionListener(e -> {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout(5, 5));


            JRadioButton searchByKeys = new JRadioButton("Search by Prescription ID");
            JRadioButton searchByPatient = new JRadioButton("Search by Patient ID");
            JRadioButton searchByDoctor = new JRadioButton("Search by Doctor ID");
            JRadioButton searchByDrug = new JRadioButton("Search by Drug ID");
            JRadioButton searchByDate = new JRadioButton("Search by Prescription Date");
            JRadioButton searchByDosage = new JRadioButton("Search by Dosage");
            JRadioButton searchByDuration = new JRadioButton("Search by Duration");
            
            
            ButtonGroup bg = new ButtonGroup();
            bg.add(searchByKeys);
            bg.add(searchByPatient);
            bg.add(searchByDoctor); 
            bg.add(searchByDrug);
            bg.add(searchByDate);
            bg.add(searchByDosage);
            bg.add(searchByDuration);
            searchByKeys.setSelected(true);

            JPanel fieldsPanel = new JPanel();
            fieldsPanel.setLayout(new GridLayout(4, 1, 5, 5));  
            JTextField prescriptionIDField = new JTextField(10);
            JTextField patientIDField = new JTextField(10);
            JTextField doctorIDField = new JTextField(10);
            JTextField drugIDField = new JTextField(10);
            JTextField dateField = new JTextField(10);
            JTextField dosageField = new JTextField(10);
            JTextField durationField = new JTextField(10);

            fieldsPanel.add(new JLabel("Prescription ID:"));
            fieldsPanel.add(prescriptionIDField);
            fieldsPanel.add(new JLabel("Patient ID:"));
            fieldsPanel.add(patientIDField);
            fieldsPanel.add(new JLabel("Doctor ID:"));
            fieldsPanel.add(doctorIDField);
            fieldsPanel.add(new JLabel("Drug ID:"));
            fieldsPanel.add(drugIDField);
            fieldsPanel.add(new JLabel("Date (YYYY-MM-DD):"));
            fieldsPanel.add(dateField);
            fieldsPanel.add(new JLabel("Dosage:"));
            fieldsPanel.add(dosageField);
            fieldsPanel.add(new JLabel("Duration:"));
            fieldsPanel.add(durationField);

            JPanel radioPanel = new JPanel(new GridLayout(5, 5));
            radioPanel.add(searchByKeys);
            radioPanel.add(searchByPatient);
            radioPanel.add(searchByDoctor);
            radioPanel.add(searchByDrug);
            radioPanel.add(searchByDate);
            radioPanel.add(searchByDosage);
            radioPanel.add(searchByDuration);

            panel.add(radioPanel, BorderLayout.NORTH);
            panel.add(fieldsPanel, BorderLayout.CENTER);

            searchByKeys.addActionListener(event -> {
                prescriptionIDField.setEnabled(true);
                patientIDField.setEnabled(true);
                doctorIDField.setEnabled(true);
                drugIDField.setEnabled(true);
                dateField.setEnabled(true);
                dosageField.setEnabled(true);
                durationField.setEnabled(true);
            });
            searchByPatient.addActionListener(event -> {
                prescriptionIDField.setEnabled(false);
                patientIDField.setEnabled(true);
                doctorIDField.setEnabled(false);
                drugIDField.setEnabled(false);
                dateField.setEnabled(false);
                dosageField.setEnabled(false);
                durationField.setEnabled(false);
            });
            searchByDoctor.addActionListener(event -> {
                prescriptionIDField.setEnabled(false);
                patientIDField.setEnabled(false);
                doctorIDField.setEnabled(true);
                drugIDField.setEnabled(false);
                dateField.setEnabled(false);
                dosageField.setEnabled(false);
                durationField.setEnabled(false);
            });
            searchByDrug.addActionListener(event -> {
                prescriptionIDField.setEnabled(false);
                patientIDField.setEnabled(false);
                doctorIDField.setEnabled(false);
                drugIDField.setEnabled(true);
                dateField.setEnabled(false);
                dosageField.setEnabled(false);
                durationField.setEnabled(false);
            });
            searchByDate.addActionListener(event -> {
                prescriptionIDField.setEnabled(false);
                patientIDField.setEnabled(false);
                doctorIDField.setEnabled(false);
                drugIDField.setEnabled(false);
                dateField.setEnabled(true);
                dosageField.setEnabled(false);
                durationField.setEnabled(false);
            });
            searchByDosage.addActionListener(event -> {
                prescriptionIDField.setEnabled(false);
                patientIDField.setEnabled(false);
                doctorIDField.setEnabled(false);
                drugIDField.setEnabled(false);
                dateField.setEnabled(false);
                dosageField.setEnabled(true);
                durationField.setEnabled(false);
            });
            searchByDuration.addActionListener(event -> {
                prescriptionIDField.setEnabled(false);
                patientIDField.setEnabled(false);
                doctorIDField.setEnabled(false);
                drugIDField.setEnabled(false);
                dateField.setEnabled(false);
                dosageField.setEnabled(false);
                durationField.setEnabled(true);
            });

            int result = JOptionPane.showConfirmDialog(
                this, panel, "Search Prescription",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String searchType = "";
                    String[] searchParams = null;

                    if (searchByKeys.isSelected()) {
                        if (prescriptionIDField.getText().isEmpty() || 
                            patientIDField.getText().isEmpty() || 
                            doctorIDField.getText().isEmpty() || 
                            drugIDField.getText().isEmpty() || 
                            dateField.getText().isEmpty() || 
                            dosageField.getText().isEmpty() || 
                            durationField.getText().isEmpty()) {
                            throw new IllegalArgumentException("All fields required for primary key search");
                        }
                        searchType = "keys";
                        searchParams = new String[]{
                            prescriptionIDField.getText(),
                            patientIDField.getText(),
                            doctorIDField.getText(),
                            drugIDField.getText(),
                            dateField.getText(),
                            dosageField.getText(),
                            durationField.getText()
                        };
                    } else if (searchByPatient.isSelected()) {
                        if (patientIDField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Patient ID required");
                        }
                        searchType = "patient";
                        searchParams = new String[]{patientIDField.getText()};
                    } else if (searchByDoctor.isSelected()) {
                        if (doctorIDField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Doctor ID required");
                        }
                        searchType = "doctor";
                        searchParams = new String[]{doctorIDField.getText()};
                    } else if (searchByDrug.isSelected()) {
                        if (drugIDField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Drug ID required");
                        }
                        searchType = "drug";
                        searchParams = new String[]{drugIDField.getText()};
                    } else if (searchByDate.isSelected()) {
                        if (dateField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Date required");
                        }
                        searchType = "date";
                        searchParams = new String[]{dateField.getText()};
                    } else if (searchByDosage.isSelected()) {
                        if (dosageField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Dosage required");
                        }
                        searchType = "dosage";
                        searchParams = new String[]{dosageField.getText()};
                    } else if (searchByDuration.isSelected()) {
                        if (durationField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Duration required");
                        }
                        searchType = "duration";
                        searchParams = new String[]{durationField.getText()};
                    }

                    ResultSet("prescription", searchType, searchParams);

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Update the visit search menu item action listener
        searchVisitMenuItem.addActionListener(e -> {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout(5, 5));
            
            // Create radio buttons for search type
            JRadioButton searchByKeys = new JRadioButton("Search by Primary Keys");
            JRadioButton searchByPatient = new JRadioButton("Search by Patient ID");
            JRadioButton searchByDoctor = new JRadioButton("Search by Doctor ID");
            JRadioButton searchByDate = new JRadioButton("Search by Date");
            JRadioButton searchByDiagnosis = new JRadioButton("Search by Diagnosis ID");
            JRadioButton searchBySymptoms = new JRadioButton("Search by Symptoms");
            
            ButtonGroup bg = new ButtonGroup();
            bg.add(searchByKeys);
            bg.add(searchByPatient);
            bg.add(searchByDoctor);
            bg.add(searchByDate);
            bg.add(searchByDiagnosis);
            bg.add(searchBySymptoms);
            searchByKeys.setSelected(true);
            
            // Create fields panel
            JPanel fieldsPanel = new JPanel();
            fieldsPanel.setLayout(new GridLayout(5, 1, 5, 5));
            JTextField patientIDField = new JTextField(10);
            JTextField doctorIDField = new JTextField(10);
            JTextField dateField = new JTextField(10);
            JTextField diagnosisField = new JTextField(10);
            JTextField symptomsField = new JTextField(40);
            
            fieldsPanel.add(new JLabel("Patient ID:"));
            fieldsPanel.add(patientIDField);
            fieldsPanel.add(new JLabel("Doctor ID:"));
            fieldsPanel.add(doctorIDField);
            fieldsPanel.add(new JLabel("Date (YYYY-MM-DD):"));
            fieldsPanel.add(dateField);
            fieldsPanel.add(new JLabel("Diagnosis ID:"));
            fieldsPanel.add(diagnosisField);
            fieldsPanel.add(new JLabel("Symptoms:"));
            fieldsPanel.add(symptomsField);
            
            // Add components to panel
            JPanel radioPanel = new JPanel(new GridLayout(4, 1));
            radioPanel.add(searchByKeys);
            radioPanel.add(searchByPatient);
            radioPanel.add(searchByDoctor);
            radioPanel.add(searchByDate);
            radioPanel.add(searchByDiagnosis);
            radioPanel.add(searchBySymptoms);
            
            panel.add(radioPanel, BorderLayout.NORTH);
            panel.add(fieldsPanel, BorderLayout.CENTER);
            
            // Enable/disable fields based on radio selection
            searchByKeys.addActionListener(event -> {
                patientIDField.setEnabled(true);
                doctorIDField.setEnabled(true);
                dateField.setEnabled(true);
                diagnosisField.setEnabled(false);
                symptomsField.setEnabled(false);
            });
            searchByPatient.addActionListener(event -> {
                patientIDField.setEnabled(true);
                doctorIDField.setEnabled(false);
                dateField.setEnabled(false);
            });
            searchByDoctor.addActionListener(event -> {
                patientIDField.setEnabled(false);
                doctorIDField.setEnabled(true);
                dateField.setEnabled(false);
            });
            searchByDate.addActionListener(event -> {
                patientIDField.setEnabled(false);
                doctorIDField.setEnabled(false);
                dateField.setEnabled(true);
            });
            searchByDiagnosis.addActionListener(event -> {
                patientIDField.setEnabled(false);
                doctorIDField.setEnabled(false);
                dateField.setEnabled(false);
                diagnosisField.setEnabled(true);
            });
            searchBySymptoms.addActionListener(event -> {
                patientIDField.setEnabled(false);
                doctorIDField.setEnabled(false);
                dateField.setEnabled(false);
                symptomsField.setEnabled(true);
            });
            
            int result = JOptionPane.showConfirmDialog(
                this, panel, "Search Visit",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );
            
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String searchType = "";
                    String[] searchParams = null;
                    
                    if (searchByKeys.isSelected()) {
                        if (patientIDField.getText().isEmpty() || 
                            doctorIDField.getText().isEmpty() || 
                            dateField.getText().isEmpty()) {
                            throw new IllegalArgumentException("All fields required for primary key search");
                        }
                        searchType = "keys";
                        searchParams = new String[]{
                            patientIDField.getText(),
                            doctorIDField.getText(),
                            dateField.getText()
                        };
                    } else if (searchByPatient.isSelected()) {
                        if (patientIDField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Patient ID required");
                        }
                        searchType = "patient";
                        searchParams = new String[]{patientIDField.getText()};
                    } else if (searchByDoctor.isSelected()) {
                        if (doctorIDField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Doctor ID required");
                        }
                        searchType = "doctor";
                        searchParams = new String[]{doctorIDField.getText()};
                    } else if (searchByDate.isSelected()) {
                        if (dateField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Date required");
                        }
                        searchType = "date";
                        searchParams = new String[]{dateField.getText()};
                    } else if (searchByDiagnosis.isSelected()) {
                        if (diagnosisField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Diagnosis ID required");
                        }
                        searchType = "diagnosis";
                        searchParams = new String[]{diagnosisField.getText()};
                    } else if (searchBySymptoms.isSelected()) {
                        if (symptomsField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Symptoms required");
                        }
                        searchType = "symptoms";
                        searchParams = new String[]{symptomsField.getText()};
                    }
                    
                    ResultSet("visit", searchType, searchParams);
                    
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        searchMenu.add(searchDoctorMenuItem);
        searchMenu.add(searchPatientMenuItem);
        searchMenu.add(searchDrugMenuItem);
        searchMenu.add(searchInsuranceComMenuItem);
        searchMenu.add(searchPrescriptionMenuItem);
        searchMenu.add(searchVisitMenuItem);
        menuBar.add(searchMenu);

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

    public void ResultSet(String tableType, String searchType, String... searchParams) {
        try {
            BaseDAO<?> dao = DAOFactory.getDAO(tableType);
            List<?> results;
            
            switch (tableType) {
                case "visit" -> {
                    VisitDAO visitDao = (VisitDAO) dao;
                    results = switch (searchType) {
                        case "keys" -> Collections.singletonList(visitDao.get(searchParams));
                        case "patient" -> visitDao.findByPatient(searchParams[0]);
                        case "doctor" -> visitDao.findByDoctor(searchParams[0]);
                        case "date" -> visitDao.findByDate(searchParams[0]);
                        case "diagnosis" -> visitDao.findByDiagnosis(searchParams[0]);
                        case "symptoms" -> visitDao.findBySymptoms(searchParams[0]);
                        default -> throw new IllegalArgumentException("Invalid search type for Visit");
                    };
                }
                case "prescription" -> {
                    PrescriptionDAO prescriptionDao = (PrescriptionDAO) dao;
                    results = switch (searchType) {
                        case "keys" -> Collections.singletonList(prescriptionDao.get(searchParams));
                        case "patient" -> prescriptionDao.findByPatient(searchParams[0]);
                        case "doctor" -> prescriptionDao.findByDoctor(searchParams[0]);
                        case "drug" -> prescriptionDao.findByDrug(searchParams[0]);
                        case "date" -> prescriptionDao.findByDate(searchParams[0]);
                        case "dosage" -> prescriptionDao.findByDosage(Integer.parseInt(searchParams[0]));
                        case "duration" -> prescriptionDao.findByDuration(Integer.parseInt(searchParams[0]));
                        default -> throw new IllegalArgumentException("Invalid search type for Prescription");
                    };
                }
                default -> {
                    // Simple single parameter search for other entities
                    results = Collections.singletonList(dao.get(searchParams));
                }
            }
            
            if (results != null && !results.isEmpty() && results.get(0) != null) {
                displayResults(results, tableType);
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "No results found",
                    "Not Found",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Error searching: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Helper method to display results
    private void displayResults(List<?> results, String tableType) {
        JTable table = new JTable(new CustomTableModel(results, tableType));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(25);
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.removeAll();
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
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