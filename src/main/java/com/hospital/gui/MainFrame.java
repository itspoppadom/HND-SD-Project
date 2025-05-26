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

    //Constants


    // Constants for Column names
    private static final String[] DOCTOR_COLUMNS = {"Doctor ID", "First Name", "Last Name", "Address", "Email", "Specialization", "Hospital"};
    private static final String[] PATIENT_COLUMNS = {"Patient ID", "First Name", "Last Name", "Address", "Postcode", "Phone Number", "Email", "Insurance ID"};
    private static final String[] DRUG_COLUMNS = {"Drug ID", "Drug Name", "Side Effects", "Benefits"};
    private static final String[] INSURANCE_COLUMNS = {"Insurance ID", "Company Name", "Address", "Phone Number"};
    private static final String[] PRESCRIPTION_COLUMNS = {"Prescription ID", "Patient ID", "Doctor ID", "Drug ID", "Date", "Dosage", "Duration"};
    private static final String[] VISIT_COLUMNS = {"Patient ID", "Doctor ID", "Date", "Diagnosis ID", "Symptoms"};

    // Constants for Search Parameters
    private static final String[] DOCTOR_SEARCH_PARAMS = {"keys", "firstName", "lastName", "address", "email", "specialization", "hospital"};
    private static final String[] PATIENT_SEARCH_PARAMS = {"keys", "firstName", "lastName", "address", "postcode", "phone", "email", "insurance"};
    private static final String[] DRUG_SEARCH_PARAMS = {"keys", "drugName", "sideEffects", "benefits"};
    private static final String[] INSURANCE_SEARCH_PARAMS = {"keys", "company", "address", "phone"};
    private static final String[] PRESCRIPTION_SEARCH_PARAMS = {"prescription", "patient", "doctor", "drug", "date", "dosage", "duration"};
    private static final String[] VISIT_SEARCH_PARAMS = {"patient", "doctor", "date", "diagnosis", "symptoms"};


    // Constants for Entity Types
    private static final String DOCTOR = "doctor";
    private static final String PATIENT = "patient";
    private static final String DRUG = "drug";
    private static final String INSURANCE = "insurance";
    private static final String PRESCRIPTION = "prescription";
    private static final String VISIT = "visit";

    // Utils message
    private static final String SEARCH_BY = "Search by : ";

    public MainFrame() {
        // Set window properties
        setTitle("Hospital Database System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add welcome message
        JLabel welcomeLabel = new JLabel("Welcome to the Hospital Database System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Add welcome message to main panel
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
        JMenuItem viewDoctorTableMenuItem = new JMenuItem(DOCTOR);
        viewDoctorTableMenuItem.addActionListener(e -> viewDoctorTable());
        JMenuItem viewPatientTableMenuItem = new JMenuItem(PATIENT);
        viewPatientTableMenuItem.addActionListener(e -> viewPatientTable());
        JMenuItem viewDrugTableMenuItem = new JMenuItem(DRUG);
        viewDrugTableMenuItem.addActionListener(e -> viewDrugTable());
        JMenuItem viewICTableMenuItem = new JMenuItem(INSURANCE);
        viewICTableMenuItem.addActionListener(e -> viewInsuranceComTable());
        JMenuItem viewPrescriptioMenuItem = new JMenuItem(PRESCRIPTION);
        viewPrescriptioMenuItem.addActionListener(e -> viewPrescriptionTable());
        JMenuItem viewVisitMenuItem = new JMenuItem(VISIT);
        
        // Add view table menu items
        viewVisitMenuItem.addActionListener(e -> viewVisitTable());
        viewTableMenu.add(viewDoctorTableMenuItem);
        viewTableMenu.add(viewDrugTableMenuItem);
        viewTableMenu.add(viewICTableMenuItem);
        viewTableMenu.add(viewPatientTableMenuItem);
        viewTableMenu.add(viewPrescriptioMenuItem);
        viewTableMenu.add(viewVisitMenuItem);
        menuBar.add(viewTableMenu);

        // Search menu
        JMenu searchMenu = new JMenu("Search");
        JMenuItem searchDoctorMenuItem = new JMenuItem(DOCTOR);
        JMenuItem searchPatientMenuItem = new JMenuItem(PATIENT);
        JMenuItem searchDrugMenuItem = new JMenuItem(DRUG);
        JMenuItem searchInsuranceComMenuItem = new JMenuItem(INSURANCE);
        JMenuItem searchPrescriptionMenuItem = new JMenuItem(PRESCRIPTION);
        JMenuItem searchVisitMenuItem = new JMenuItem(VISIT);

        // Add search menu items

        //Update the Doctor search menu item action listener
        searchDoctorMenuItem.addActionListener(e -> {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout(5, 5));

            // Create radio buttons for search type 
            JRadioButton searchByKeys = new JRadioButton(SEARCH_BY + DOCTOR_COLUMNS[0]);
            JRadioButton searchByFirstName = new JRadioButton(SEARCH_BY + DOCTOR_COLUMNS[1]);
            JRadioButton searchByLastName = new JRadioButton(SEARCH_BY + DOCTOR_COLUMNS[2]);
            JRadioButton searchByAddress = new JRadioButton(SEARCH_BY + DOCTOR_COLUMNS[3]);
            JRadioButton searchByEmail = new JRadioButton(SEARCH_BY + DOCTOR_COLUMNS[4]);
            JRadioButton searchBySpecialization = new JRadioButton(SEARCH_BY + DOCTOR_COLUMNS[5]);
            JRadioButton searchByHospital = new JRadioButton(SEARCH_BY + DOCTOR_COLUMNS[6]);

            // Add radio buttons to button group
            ButtonGroup bg = new ButtonGroup();
            bg.add(searchByKeys);
            bg.add(searchByFirstName);
            bg.add(searchByLastName);
            bg.add(searchByAddress);
            bg.add(searchByEmail);
            bg.add(searchBySpecialization);
            bg.add(searchByHospital);
            searchByKeys.setSelected(true);

            // Create fields panel
            JPanel fieldsPanel = new JPanel();
            fieldsPanel.setLayout(new GridLayout(7, 1, 5, 5));
            JTextField doctorIDField = new JTextField(10);
            JTextField firstNameField = new JTextField(10);
            JTextField lastNameField = new JTextField(10);
            JTextField addressField = new JTextField(10);
            JTextField emailField = new JTextField(10);
            JTextField specializationField = new JTextField(10);
            JTextField hospitalField = new JTextField(10);

            // Add fields to panel
            fieldsPanel.add(new JLabel(DOCTOR_COLUMNS[0]+" :"));
            fieldsPanel.add(doctorIDField);
            fieldsPanel.add(new JLabel(DOCTOR_COLUMNS[1] + " :"));
            fieldsPanel.add(firstNameField);
            fieldsPanel.add(new JLabel(DOCTOR_COLUMNS[2] + " :"));
            fieldsPanel.add(lastNameField);
            fieldsPanel.add(new JLabel(DOCTOR_COLUMNS[3] + " :"));
            fieldsPanel.add(addressField);
            fieldsPanel.add(new JLabel(DOCTOR_COLUMNS[4]+" :"));
            fieldsPanel.add(emailField);
            fieldsPanel.add(new JLabel(DOCTOR_COLUMNS[5]+ " :"));
            fieldsPanel.add(specializationField);
            fieldsPanel.add(new JLabel(DOCTOR_COLUMNS[6]+" :"));
            fieldsPanel.add(hospitalField);

            // Add components to panel
            JPanel radioPanel = new JPanel(new GridLayout(7, 1));
            radioPanel.add(searchByKeys);
            radioPanel.add(searchByFirstName);
            radioPanel.add(searchByLastName);
            radioPanel.add(searchByAddress);
            radioPanel.add(searchByEmail);
            radioPanel.add(searchBySpecialization);
            radioPanel.add(searchByHospital);

            // Add panels to main panel
            panel.add(radioPanel, BorderLayout.NORTH);
            panel.add(fieldsPanel, BorderLayout.CENTER);

            // Enable/disable fields based on radio selection
            searchByKeys.addActionListener(event -> {
                doctorIDField.setEnabled(true);
                firstNameField.setEnabled(false);
                lastNameField.setEnabled(false);
                addressField.setEnabled(false);
                emailField.setEnabled(false);
                specializationField.setEnabled(false);
                hospitalField.setEnabled(false);
            });
            searchByFirstName.addActionListener(event -> {
                doctorIDField.setEnabled(false);
                firstNameField.setEnabled(true);
                lastNameField.setEnabled(false);
                addressField.setEnabled(false);
                emailField.setEnabled(false);
                specializationField.setEnabled(false);
                hospitalField.setEnabled(false);
            });
            searchByLastName.addActionListener(event -> {
                doctorIDField.setEnabled(false);
                firstNameField.setEnabled(false);
                lastNameField.setEnabled(true);
                addressField.setEnabled(false);
                emailField.setEnabled(false);
                specializationField.setEnabled(false);
                hospitalField.setEnabled(false);
            });
            searchByAddress.addActionListener(event -> {
                doctorIDField.setEnabled(false);
                firstNameField.setEnabled(false);
                lastNameField.setEnabled(false);
                addressField.setEnabled(true);
                emailField.setEnabled(false);
                specializationField.setEnabled(false);
                hospitalField.setEnabled(false);
            });
            searchByEmail.addActionListener(event -> {
                doctorIDField.setEnabled(false);
                firstNameField.setEnabled(false);
                lastNameField.setEnabled(false);
                addressField.setEnabled(false);
                emailField.setEnabled(true);
                specializationField.setEnabled(false);
                hospitalField.setEnabled(false);
            });
            searchBySpecialization.addActionListener(event -> {
                doctorIDField.setEnabled(false);
                firstNameField.setEnabled(false);
                lastNameField.setEnabled(false);
                addressField.setEnabled(false);
                emailField.setEnabled(false);
                specializationField.setEnabled(true);
                hospitalField.setEnabled(false);
            });
            searchByHospital.addActionListener(event -> {
                doctorIDField.setEnabled(false);
                firstNameField.setEnabled(false);
                lastNameField.setEnabled(false);
                addressField.setEnabled(false);
                emailField.setEnabled(false);
                specializationField.setEnabled(false);
                hospitalField.setEnabled(true);
            });
            // Display search dialog
            int result = JOptionPane.showConfirmDialog(
                this, panel, "Search Doctor",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            //Handle search
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String searchType = "";
                    String[] searchParams = null;

                    if (searchByKeys.isSelected()) {
                        if (doctorIDField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Doctor ID required");
                        }
                        searchType = DOCTOR_SEARCH_PARAMS[0];
                        searchParams = new String[]{doctorIDField.getText()};
                    } else if (searchByFirstName.isSelected()) {
                        if (firstNameField.getText().isEmpty()) {
                            throw new IllegalArgumentException("First name required");
                        }
                        searchType = DOCTOR_SEARCH_PARAMS[1];
                        searchParams = new String[]{firstNameField.getText()};
                    } else if (searchByLastName.isSelected()) {
                        if (lastNameField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Last name required");
                        }
                        searchType = DOCTOR_SEARCH_PARAMS[2];
                        searchParams = new String[]{lastNameField.getText()};
                    } else if (searchByAddress.isSelected()) {
                        if (addressField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Address required");
                        }
                        searchType = DOCTOR_SEARCH_PARAMS[3];
                        searchParams = new String[]{addressField.getText()};
                    } else if (searchByEmail.isSelected()) {
                        if (emailField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Email required");
                        }
                        searchType = DOCTOR_SEARCH_PARAMS[4];
                        searchParams = new String[]{emailField.getText()};
                    } else if (searchBySpecialization.isSelected()) {
                        if (specializationField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Specialization required");
                        }
                        searchType = DOCTOR_SEARCH_PARAMS[5];
                        searchParams = new String[]{specializationField.getText()};
                    } else if (searchByHospital.isSelected()) {
                        if (hospitalField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Hospital required");
                        }
                        searchType = DOCTOR_SEARCH_PARAMS[6];
                        searchParams = new String[]{hospitalField.getText()};
                    }

                    ResultSet(DOCTOR, searchType, searchParams);

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Update the patient search menu item action listener
        searchPatientMenuItem.addActionListener(e -> {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout(5, 5));

            // Create radio buttons for search type
            JRadioButton searchByKeys = new JRadioButton(SEARCH_BY + PATIENT_COLUMNS[0]);
            JRadioButton searchByFirstName = new JRadioButton(SEARCH_BY + PATIENT_COLUMNS[1]);
            JRadioButton searchByLastName = new JRadioButton(SEARCH_BY + PATIENT_COLUMNS[2]);
            JRadioButton searchByAddress = new JRadioButton(SEARCH_BY + PATIENT_COLUMNS[3]);
            JRadioButton searchByPostcode = new JRadioButton(SEARCH_BY + PATIENT_COLUMNS[4]);
            JRadioButton searchByPhone = new JRadioButton(SEARCH_BY + PATIENT_COLUMNS[5]);
            JRadioButton searchByEmail = new JRadioButton(SEARCH_BY + PATIENT_COLUMNS[6]);
            JRadioButton searchByInsurance = new JRadioButton(SEARCH_BY + PATIENT_COLUMNS[7]);

            // Add radio buttons to button group
            ButtonGroup bg = new ButtonGroup();
            bg.add(searchByKeys);
            bg.add(searchByFirstName);
            bg.add(searchByLastName);
            bg.add(searchByAddress);
            bg.add(searchByPostcode);
            bg.add(searchByPhone);
            bg.add(searchByEmail);
            bg.add(searchByInsurance);
            searchByKeys.setSelected(true);

            // Create fields panel
            JPanel fieldsPanel = new JPanel();
            fieldsPanel.setLayout(new GridLayout(8, 1, 5, 5));
            JTextField patientIDField = new JTextField(10);
            JTextField firstNameField = new JTextField(10);
            JTextField lastNameField = new JTextField(10);
            JTextField addressField = new JTextField(10);
            JTextField postcodeField = new JTextField(10);
            JTextField phoneField = new JTextField(10);
            JTextField emailField = new JTextField(10);
            JTextField insuranceField = new JTextField(10);

            // Add fields to panel
            fieldsPanel.add(new JLabel(PATIENT_COLUMNS[0] +" :"));
            fieldsPanel.add(patientIDField);
            fieldsPanel.add(new JLabel(PATIENT_COLUMNS[1]+ " :"));
            fieldsPanel.add(firstNameField);
            fieldsPanel.add(new JLabel(PATIENT_COLUMNS[2]+" :"));
            fieldsPanel.add(lastNameField);
            fieldsPanel.add(new JLabel(PATIENT_COLUMNS[3] + " :"));
            fieldsPanel.add(addressField);
            fieldsPanel.add(new JLabel(PATIENT_COLUMNS[4] + " :"));
            fieldsPanel.add(postcodeField);
            fieldsPanel.add(new JLabel(PATIENT_COLUMNS[5] + " :"));
            fieldsPanel.add(phoneField);
            fieldsPanel.add(new JLabel(PATIENT_COLUMNS[6]+" :"));
            fieldsPanel.add(emailField);
            fieldsPanel.add(new JLabel(PATIENT_COLUMNS[7] + " :"));
            fieldsPanel.add(insuranceField);

            // Add components to panel
            JPanel radioPanel = new JPanel(new GridLayout(8, 1));
            radioPanel.add(searchByKeys);
            radioPanel.add(searchByFirstName);
            radioPanel.add(searchByLastName);
            radioPanel.add(searchByAddress);
            radioPanel.add(searchByPostcode);
            radioPanel.add(searchByPhone);
            radioPanel.add(searchByEmail);
            radioPanel.add(searchByInsurance);


            // Add panels to main panel
            panel.add(radioPanel, BorderLayout.NORTH);
            panel.add(fieldsPanel, BorderLayout.CENTER);

            // Enable/disable fields based on radio selection
            searchByKeys.addActionListener(event -> {
                patientIDField.setEnabled(true);
                firstNameField.setEnabled(false);
                lastNameField.setEnabled(false);
                addressField.setEnabled(false);
                postcodeField.setEnabled(false);
                phoneField.setEnabled(false);
                emailField.setEnabled(false);
                insuranceField.setEnabled(false);
            });
            searchByFirstName.addActionListener(event -> {
                patientIDField.setEnabled(false);
                firstNameField.setEnabled(true);
                lastNameField.setEnabled(false);
                addressField.setEnabled(false);
                postcodeField.setEnabled(false);
                phoneField.setEnabled(false);
                emailField.setEnabled(false);
                insuranceField.setEnabled(false);
            });
            searchByLastName.addActionListener(event -> {
                patientIDField.setEnabled(false);
                firstNameField.setEnabled(false);
                lastNameField.setEnabled(true);
                addressField.setEnabled(false);
                postcodeField.setEnabled(false);
                phoneField.setEnabled(false);
                emailField.setEnabled(false);
                insuranceField.setEnabled(false);
            });
            searchByAddress.addActionListener(event -> {
                patientIDField.setEnabled(false);
                firstNameField.setEnabled(false);
                lastNameField.setEnabled(false);
                addressField.setEnabled(true);
                postcodeField.setEnabled(false);
                phoneField.setEnabled(false);
                emailField.setEnabled(false);
                insuranceField.setEnabled(false);
            });
            searchByPostcode.addActionListener(event -> {
                patientIDField.setEnabled(false);
                firstNameField.setEnabled(false);
                lastNameField.setEnabled(false);
                addressField.setEnabled(false);
                postcodeField.setEnabled(true);
                phoneField.setEnabled(false);
                emailField.setEnabled(false);
                insuranceField.setEnabled(false);
            });
            searchByPhone.addActionListener(event -> {
                patientIDField.setEnabled(false);
                firstNameField.setEnabled(false);
                lastNameField.setEnabled(false);
                addressField.setEnabled(false);
                postcodeField.setEnabled(false);
                phoneField.setEnabled(true);
                emailField.setEnabled(false);
                insuranceField.setEnabled(false);
            });
            searchByEmail.addActionListener(event -> {
                patientIDField.setEnabled(false);
                firstNameField.setEnabled(false);
                lastNameField.setEnabled(false);
                addressField.setEnabled(false);
                postcodeField.setEnabled(false);
                phoneField.setEnabled(false);
                emailField.setEnabled(true);
                insuranceField.setEnabled(false);
            });
            searchByInsurance.addActionListener(event -> {
                patientIDField.setEnabled(false);
                firstNameField.setEnabled(false);
                lastNameField.setEnabled(false);
                addressField.setEnabled(false);
                postcodeField.setEnabled(false);
                phoneField.setEnabled(false);
                emailField.setEnabled(false);
                insuranceField.setEnabled(true);
            });
            // Display search dialog
            int result = JOptionPane.showConfirmDialog(
                this, panel, "Search Patient",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );
            //Handle search

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String searchType = "";
                    String[] searchParams = null;

                    if (searchByKeys.isSelected()) {
                        if (patientIDField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Patient ID required");
                        }
                        searchType = PATIENT_SEARCH_PARAMS[0];
                        searchParams = new String[]{patientIDField.getText()};
                    } else if (searchByFirstName.isSelected()) {
                        if (firstNameField.getText().isEmpty()) {
                            throw new IllegalArgumentException("First name required");
                        }
                        searchType = PATIENT_SEARCH_PARAMS[1];
                        searchParams = new String[]{firstNameField.getText()};
                    } else if (searchByLastName.isSelected()) {
                        if (lastNameField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Last name required");
                        }
                        searchType = PATIENT_SEARCH_PARAMS[2];
                        searchParams = new String[]{lastNameField.getText()};
                    } else if (searchByAddress.isSelected()) {
                        if (addressField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Address required");
                        }
                        searchType = PATIENT_SEARCH_PARAMS[3];
                        searchParams = new String[]{addressField.getText()};
                    } else if (searchByPostcode.isSelected()) {
                        if (postcodeField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Postcode required");
                        }
                        searchType = PATIENT_SEARCH_PARAMS[4];
                        searchParams = new String[]{postcodeField.getText()};
                    } else if (searchByPhone.isSelected()) {
                        if (phoneField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Phone number required");
                        }
                        searchType = PATIENT_SEARCH_PARAMS[5];
                        searchParams = new String[]{phoneField.getText()};
                    } else if (searchByEmail.isSelected()) {
                        if (emailField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Email required");
                        }
                        searchType = PATIENT_SEARCH_PARAMS[6];
                        searchParams = new String[]{emailField.getText()};
                    } else if (searchByInsurance.isSelected()) {
                        if (insuranceField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Insurance ID required");
                        }
                        searchType = PATIENT_SEARCH_PARAMS[7];
                        searchParams = new String[]{insuranceField.getText()};
                    }

                    ResultSet(PATIENT, searchType, searchParams);

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }

        });


        // Update the drug search menu item action listener
        searchDrugMenuItem.addActionListener(e -> {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout(5, 5));

            // Create radio buttons for search type
            JRadioButton searchByKeys = new JRadioButton(SEARCH_BY + DRUG_COLUMNS[0]);
            JRadioButton searchByDrugName = new JRadioButton(SEARCH_BY + DRUG_COLUMNS[1]);
            JRadioButton searchBySideEffects = new JRadioButton(SEARCH_BY + DRUG_COLUMNS[2]);
            JRadioButton searchByBenefits = new JRadioButton(SEARCH_BY + DRUG_COLUMNS[3]);

            // Add radio buttons to button group
            ButtonGroup bg = new ButtonGroup();
            bg.add(searchByKeys);
            bg.add(searchByDrugName);
            bg.add(searchBySideEffects);
            bg.add(searchByBenefits);
            searchByKeys.setSelected(true);


            // Create fields panel
            JPanel fieldsPanel = new JPanel();
            fieldsPanel.setLayout(new GridLayout(4, 1, 5, 5));
            JTextField drugIDField = new JTextField(10);
            JTextField drugNameField = new JTextField(10);
            JTextField sideEffectsField = new JTextField(10);
            JTextField benefitsField = new JTextField(10);

            // Add fields to panel
            fieldsPanel.add(new JLabel(DRUG_COLUMNS[0] + " :"));
            fieldsPanel.add(drugIDField);
            fieldsPanel.add(new JLabel(DRUG_COLUMNS[1] + " :"));
            fieldsPanel.add(drugNameField);
            fieldsPanel.add(new JLabel(DRUG_COLUMNS[2] + " :"));
            fieldsPanel.add(sideEffectsField);
            fieldsPanel.add(new JLabel(DRUG_COLUMNS[3] + " :"));
            fieldsPanel.add(benefitsField);

            // Add components to panel
            JPanel radioPanel = new JPanel(new GridLayout(4, 1));
            radioPanel.add(searchByKeys);
            radioPanel.add(searchByDrugName);
            radioPanel.add(searchBySideEffects);
            radioPanel.add(searchByBenefits);
            
            // Add panels to main panel
            panel.add(radioPanel, BorderLayout.NORTH);
            panel.add(fieldsPanel, BorderLayout.CENTER);

            // Enable/disable fields based on radio selection
            searchByKeys.addActionListener(event -> {
                drugIDField.setEnabled(true);
                drugNameField.setEnabled(false);
                sideEffectsField.setEnabled(false);
                benefitsField.setEnabled(false);
            });
            searchByDrugName.addActionListener(event -> {
                drugIDField.setEnabled(false);
                drugNameField.setEnabled(true);
                sideEffectsField.setEnabled(false);
                benefitsField.setEnabled(false);
            });
            searchBySideEffects.addActionListener(event -> {
                drugIDField.setEnabled(false);
                drugNameField.setEnabled(false);
                sideEffectsField.setEnabled(true);
                benefitsField.setEnabled(false);
            });
            searchByBenefits.addActionListener(event -> {
                drugIDField.setEnabled(false);
                drugNameField.setEnabled(false);
                sideEffectsField.setEnabled(false);
                benefitsField.setEnabled(true);
            });

            // Display search dialog
            int result = JOptionPane.showConfirmDialog(
                this, panel, "Search Drug",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );
            //Handle search

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String searchType = "";
                    String[] searchParams = null;

                    if (searchByKeys.isSelected()) {
                        if (drugIDField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Drug ID required");
                        }
                        searchType = DRUG_SEARCH_PARAMS[0];
                        searchParams = new String[]{drugIDField.getText()};
                    } else if (searchByDrugName.isSelected()) {
                        if (drugNameField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Drug name required");
                        }
                        searchType = DRUG_SEARCH_PARAMS[1];
                        searchParams = new String[]{drugNameField.getText()};
                    } else if (searchBySideEffects.isSelected()) {
                        if (sideEffectsField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Side effects required");
                        }
                        searchType = DRUG_SEARCH_PARAMS[2];
                        searchParams = new String[]{sideEffectsField.getText()};
                    } else if (searchByBenefits.isSelected()) {
                        if (benefitsField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Benefits required");
                        }
                        searchType = DRUG_SEARCH_PARAMS[3];
                        searchParams = new String[]{benefitsField.getText()};
                    }

                    ResultSet(DRUG, searchType, searchParams);

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
            
        });

        // Update the insurance company search menu item action listener
        searchInsuranceComMenuItem.addActionListener(e -> {
           JPanel panel = new JPanel();
           panel.setLayout(new BorderLayout(5,5));

              // Create radio buttons for search type
            JRadioButton searchByKeys = new JRadioButton(SEARCH_BY + INSURANCE_COLUMNS[0]);
            JRadioButton searchByCompany = new JRadioButton(SEARCH_BY + INSURANCE_COLUMNS[1]);
            JRadioButton searchByAddress = new JRadioButton(SEARCH_BY + INSURANCE_COLUMNS[2]);
            JRadioButton searchByPhone = new JRadioButton(SEARCH_BY + INSURANCE_COLUMNS[3]);

            // Add radio buttons to button group
            ButtonGroup bg = new ButtonGroup();
            bg.add(searchByKeys);
            bg.add(searchByCompany);
            bg.add(searchByAddress);
            bg.add(searchByPhone);
            searchByKeys.setSelected(true);

            // Create fields panel
            JPanel fieldsPanel = new JPanel();
            fieldsPanel.setLayout(new GridLayout(4, 1, 5, 5));
            JTextField insuranceIDField = new JTextField(10);
            JTextField companyField = new JTextField(10);
            JTextField addressField = new JTextField(10);
            JTextField phoneField = new JTextField(10);
            
            // Add fields to panel
            fieldsPanel.add(new JLabel(INSURANCE_COLUMNS[0] + " :"));
            fieldsPanel.add(insuranceIDField);
            fieldsPanel.add(new JLabel(INSURANCE_COLUMNS[1] + " :"));
            fieldsPanel.add(companyField);
            fieldsPanel.add(new JLabel(INSURANCE_COLUMNS[2] + " :"));
            fieldsPanel.add(addressField);
            fieldsPanel.add(new JLabel(INSURANCE_COLUMNS[3] + " :"));
            fieldsPanel.add(phoneField);

            // Add components to panel
            JPanel radioPanel = new JPanel(new GridLayout(4, 1));
            radioPanel.add(searchByKeys);
            radioPanel.add(searchByCompany);
            radioPanel.add(searchByAddress);
            radioPanel.add(searchByPhone);

            panel.add(radioPanel, BorderLayout.NORTH);
            panel.add(fieldsPanel, BorderLayout.CENTER);

            // Enable/disable fields based on radio selection
            searchByKeys.addActionListener(event -> {
                insuranceIDField.setEnabled(true);
                companyField.setEnabled(false);
                addressField.setEnabled(false);
                phoneField.setEnabled(false);
            });
            searchByCompany.addActionListener(event -> {
                insuranceIDField.setEnabled(false);
                companyField.setEnabled(true);
                addressField.setEnabled(false);
                phoneField.setEnabled(false);
            });
            searchByAddress.addActionListener(event -> {
                insuranceIDField.setEnabled(false);
                companyField.setEnabled(false);
                addressField.setEnabled(true);
                phoneField.setEnabled(false);
            });
            searchByPhone.addActionListener(event -> {
                insuranceIDField.setEnabled(false);
                companyField.setEnabled(false);
                addressField.setEnabled(false);
                phoneField.setEnabled(true);
            });

            // Display search dialog
            int result = JOptionPane.showConfirmDialog(
                this, panel, "Search Insurance Company",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );
            
            //Handle search
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String searchType = "";
                    String[] searchParams = null;
                    
                    if (searchByKeys.isSelected()) {
                        if (insuranceIDField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Insurance ID required");
                        }
                        searchType = INSURANCE_SEARCH_PARAMS[0];
                        searchParams = new String[]{insuranceIDField.getText()};
                    } else if (searchByCompany.isSelected()) {
                        if (companyField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Company name required");
                        }
                        searchType = INSURANCE_SEARCH_PARAMS[1];
                        searchParams = new String[]{companyField.getText()};
                    } else if (searchByAddress.isSelected()) {
                        if (addressField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Address required");
                        }
                        searchType = INSURANCE_SEARCH_PARAMS[2];
                        searchParams = new String[]{addressField.getText()};
                    } else if (searchByPhone.isSelected()) {
                        if (phoneField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Phone number required");
                        }
                        searchType = INSURANCE_SEARCH_PARAMS[3];
                        searchParams = new String[]{phoneField.getText()};
                    }
                    
                    ResultSet(INSURANCE, searchType, searchParams);
                    
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }

        });



        // Update the prescription search menu item action listener
        searchPrescriptionMenuItem.addActionListener(e -> {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout(5, 5));

        // Create radio buttons for search type

            JRadioButton searchByKeys = new JRadioButton(SEARCH_BY + PRESCRIPTION_COLUMNS[0]);
            JRadioButton searchByPatient = new JRadioButton(SEARCH_BY + PRESCRIPTION_COLUMNS[1]);
            JRadioButton searchByDoctor = new JRadioButton(SEARCH_BY + PRESCRIPTION_COLUMNS[2]);
            JRadioButton searchByDrug = new JRadioButton(SEARCH_BY + PRESCRIPTION_COLUMNS[3]);
            JRadioButton searchByDate = new JRadioButton(SEARCH_BY + PRESCRIPTION_COLUMNS[4]);
            JRadioButton searchByDosage = new JRadioButton(SEARCH_BY + PRESCRIPTION_COLUMNS[5]);
            JRadioButton searchByDuration = new JRadioButton(SEARCH_BY + PRESCRIPTION_COLUMNS[6]);
            
            // Add radio buttons to button group
            ButtonGroup bg = new ButtonGroup();
            bg.add(searchByKeys);
            bg.add(searchByPatient);
            bg.add(searchByDoctor); 
            bg.add(searchByDrug);
            bg.add(searchByDate);
            bg.add(searchByDosage);
            bg.add(searchByDuration);
            searchByKeys.setSelected(true);
            
            // Create fields panel
            JPanel fieldsPanel = new JPanel();
            fieldsPanel.setLayout(new GridLayout(4, 1, 5, 5)); 

            // Create text fields for search parameters
            JTextField prescriptionIDField = new JTextField(10);
            JTextField patientIDField = new JTextField(10);
            JTextField doctorIDField = new JTextField(10);
            JTextField drugIDField = new JTextField(10);
            JTextField dateField = new JTextField(10);
            JTextField dosageField = new JTextField(10);
            JTextField durationField = new JTextField(10);

            // Add fields to panel
            fieldsPanel.add(new JLabel(PRESCRIPTION_COLUMNS[0] + " :"));
            fieldsPanel.add(prescriptionIDField);
            fieldsPanel.add(new JLabel(PRESCRIPTION_COLUMNS[1] + " :"));
            fieldsPanel.add(patientIDField);
            fieldsPanel.add(new JLabel(PRESCRIPTION_COLUMNS[2] + " :"));
            fieldsPanel.add(doctorIDField);
            fieldsPanel.add(new JLabel(PRESCRIPTION_COLUMNS[3] + " :"));
            fieldsPanel.add(drugIDField);
            fieldsPanel.add(new JLabel(PRESCRIPTION_COLUMNS[4] + " (YYYY-MM-DD) :"));
            fieldsPanel.add(dateField);
            fieldsPanel.add(new JLabel(PRESCRIPTION_COLUMNS[5] + " :"));
            fieldsPanel.add(dosageField);
            fieldsPanel.add(new JLabel(PRESCRIPTION_COLUMNS[6] + " :"));
            fieldsPanel.add(durationField);

            // Add components to panel  
            JPanel radioPanel = new JPanel(new GridLayout(5, 5));
            radioPanel.add(searchByKeys);
            radioPanel.add(searchByPatient);
            radioPanel.add(searchByDoctor);
            radioPanel.add(searchByDrug);
            radioPanel.add(searchByDate);
            radioPanel.add(searchByDosage);
            radioPanel.add(searchByDuration);

            // Add panels to main panel
            panel.add(radioPanel, BorderLayout.NORTH);
            panel.add(fieldsPanel, BorderLayout.CENTER);


            // Enable/disable fields based on radio selection
            searchByKeys.addActionListener(event -> {
                prescriptionIDField.setEnabled(true);
                patientIDField.setEnabled(false);
                doctorIDField.setEnabled(false);
                drugIDField.setEnabled(false);
                dateField.setEnabled(false);
                dosageField.setEnabled(false);
                durationField.setEnabled(false);
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

            // Show dialog
            int result = JOptionPane.showConfirmDialog(
                this, panel, "Search Prescription",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            // Handle search depending on selected parameters 
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String searchType = "";
                    String[] searchParams = null;

                    if (searchByKeys.isSelected()) {
                        if (prescriptionIDField.getText().isEmpty())  {
                            throw new IllegalArgumentException("All prescriptionID is required for primary key search");
                        }
                        searchType = PRESCRIPTION_SEARCH_PARAMS[0];
                        searchParams = new String[]{
                            prescriptionIDField.getText(),
                        };
                    } else if (searchByPatient.isSelected()) {
                        if (patientIDField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Patient ID required");
                        }
                        searchType = PRESCRIPTION_SEARCH_PARAMS[1];
                        searchParams = new String[]{patientIDField.getText()};
                    } else if (searchByDoctor.isSelected()) {
                        if (doctorIDField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Doctor ID required");
                        }
                        searchType = PRESCRIPTION_SEARCH_PARAMS[2];
                        searchParams = new String[]{doctorIDField.getText()};
                    } else if (searchByDrug.isSelected()) {
                        if (drugIDField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Drug ID required");
                        }
                        searchType = PRESCRIPTION_SEARCH_PARAMS[3];
                        searchParams = new String[]{drugIDField.getText()};
                    } else if (searchByDate.isSelected()) {
                        if (dateField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Date required");
                        }
                        searchType = PRESCRIPTION_SEARCH_PARAMS[4];
                        searchParams = new String[]{dateField.getText()};
                    } else if (searchByDosage.isSelected()) {
                        if (dosageField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Dosage required");
                        }
                        searchType = PRESCRIPTION_SEARCH_PARAMS[5];
                        searchParams = new String[]{dosageField.getText()};
                    } else if (searchByDuration.isSelected()) {
                        if (durationField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Duration required");
                        }
                        searchType = PRESCRIPTION_SEARCH_PARAMS[6];
                        searchParams = new String[]{durationField.getText()};
                    }

                    ResultSet(PRESCRIPTION, searchType, searchParams);
            
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
            JRadioButton searchByKeys = new JRadioButton(SEARCH_BY + " Primary Keys");
            JRadioButton searchByPatient = new JRadioButton(SEARCH_BY + VISIT_COLUMNS[0]);
            JRadioButton searchByDoctor = new JRadioButton(SEARCH_BY + VISIT_COLUMNS[1]);
            JRadioButton searchByDate = new JRadioButton(SEARCH_BY + VISIT_COLUMNS[2]);
            JRadioButton searchByDiagnosis = new JRadioButton(SEARCH_BY + VISIT_COLUMNS[3]);
            JRadioButton searchBySymptoms = new JRadioButton(SEARCH_BY + VISIT_COLUMNS[4]);
            
            // Add radio buttons to button group
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
            
            // Add fields to panel
            fieldsPanel.add(new JLabel(VISIT_COLUMNS[0] + " :"));
            fieldsPanel.add(patientIDField);
            fieldsPanel.add(new JLabel(VISIT_COLUMNS[1] + " :"));
            fieldsPanel.add(doctorIDField);
            fieldsPanel.add(new JLabel(VISIT_COLUMNS[2] + " (YYYY-MM-DD):"));
            fieldsPanel.add(dateField);
            fieldsPanel.add(new JLabel(VISIT_COLUMNS[3] + " :"));
            fieldsPanel.add(diagnosisField);
            fieldsPanel.add(new JLabel(VISIT_COLUMNS[4] + " :"));
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
            // Handle search
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
                        searchType = VISIT_SEARCH_PARAMS[0];
                        searchParams = new String[]{patientIDField.getText()};
                    } else if (searchByDoctor.isSelected()) {
                        if (doctorIDField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Doctor ID required");
                        }
                        searchType = VISIT_SEARCH_PARAMS[1];
                        searchParams = new String[]{doctorIDField.getText()};
                    } else if (searchByDate.isSelected()) {
                        if (dateField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Date required");
                        }
                        searchType = VISIT_SEARCH_PARAMS[2];
                        searchParams = new String[]{dateField.getText()};
                    } else if (searchByDiagnosis.isSelected()) {
                        if (diagnosisField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Diagnosis ID required");
                        }
                        searchType = VISIT_SEARCH_PARAMS[3];
                        searchParams = new String[]{diagnosisField.getText()};
                    } else if (searchBySymptoms.isSelected()) {
                        if (symptomsField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Symptoms required");
                        }
                        searchType = VISIT_SEARCH_PARAMS[4];
                        searchParams = new String[]{symptomsField.getText()};
                    }
                    
                    ResultSet(VISIT, searchType, searchParams);
             
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        // Add search menu
        searchMenu.add(searchDoctorMenuItem);
        searchMenu.add(searchDrugMenuItem);
        searchMenu.add(searchInsuranceComMenuItem);
        searchMenu.add(searchPatientMenuItem);
        searchMenu.add(searchPrescriptionMenuItem);
        searchMenu.add(searchVisitMenuItem);
        menuBar.add(searchMenu);

        // Add Records menu
        JMenu addRecordsMenu = new JMenu("Add Records");

        // Add menu items to add records menu
        JMenuItem addDoctorFormMenuItem = new JMenuItem(DOCTOR);
        JMenuItem addPatientFormMenuItem = new JMenuItem(PATIENT);
        JMenuItem addDrugFormMenuItem = new JMenuItem(DRUG);
        JMenuItem addInsuranceComFormMenuItem = new JMenuItem(INSURANCE);
        JMenuItem addPrescriptionFormMenuItem = new JMenuItem(PRESCRIPTION);
        JMenuItem addVisitFormMenuItem = new JMenuItem(VISIT);


        // Add action listeners to menu items
        addDoctorFormMenuItem.addActionListener(e -> showDoctorForm());
        addPatientFormMenuItem.addActionListener(e -> showPatientForm());
        addDrugFormMenuItem.addActionListener(e -> showDrugForm());
        addInsuranceComFormMenuItem.addActionListener(e -> showInsuranceComForm());
        addPrescriptionFormMenuItem.addActionListener(e -> showPrescriptionForm());
        addVisitFormMenuItem.addActionListener(e -> showVisitForm());

        // Add menu items to menu
        addRecordsMenu.add(addDoctorFormMenuItem);
        addRecordsMenu.add(addDrugFormMenuItem);
        addRecordsMenu.add(addInsuranceComFormMenuItem);
        addRecordsMenu.add(addPatientFormMenuItem);
        addRecordsMenu.add(addPrescriptionFormMenuItem);
        addRecordsMenu.add(addVisitFormMenuItem);
        menuBar.add(addRecordsMenu);

        setJMenuBar(menuBar);

        setVisible(true);
    }
    // A function designed to create a table based on the selected table type
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


    // Method to display search results depending on the different keys, search types and search parameters in different tables
    public void ResultSet(String tableType, String searchType, String... searchParams) {
        try {
            BaseDAO<?> dao = DAOFactory.getDAO(tableType);
            List<?> results;
            
            switch (tableType) {
                case VISIT -> {
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
                case PRESCRIPTION -> {
                    PrescriptionDAO prescriptionDao = (PrescriptionDAO) dao;
                    results = switch (searchType) {
                        case "prescription" -> Collections.singletonList(prescriptionDao.get(searchParams));
                        case "patient" -> prescriptionDao.findByPatient(searchParams[0]);
                        case "doctor" -> prescriptionDao.findByDoctor(searchParams[0]);
                        case "drug" -> prescriptionDao.findByDrug(searchParams[0]);
                        case "date" -> prescriptionDao.findByDate(searchParams[0]);
                        case "dosage" -> prescriptionDao.findByDosage(Integer.parseInt(searchParams[0]));
                        case "duration" -> prescriptionDao.findByDuration(Integer.parseInt(searchParams[0]));
                        default -> throw new IllegalArgumentException("Invalid search type for Prescription");
                    };
                } 
                case INSURANCE -> {
                    InsuranceComDAO insuranceDao = (InsuranceComDAO) dao;
                    results = switch (searchType) {
                        case "keys" -> Collections.singletonList(insuranceDao.get(searchParams));
                        case "company" -> insuranceDao.findByCompany(searchParams[0]);
                        case "address" -> insuranceDao.findByAddress(searchParams[0]);
                        case "phone" -> insuranceDao.findByPhone(searchParams[0]);
                        default -> throw new IllegalArgumentException("Invalid search type for Insurance Company");};
                } case DRUG -> {
                    DrugDAO drugDao = (DrugDAO) dao;
                    results = switch (searchType) {
                        case "keys" -> Collections.singletonList(drugDao.get(searchParams));
                        case "drugName" -> drugDao.findByDrugName(searchParams[0]);
                        case "sideEffects" -> drugDao.findBySideEffects(searchParams[0]);
                        case "benefits" -> drugDao.findByBenefits(searchParams[0]);
                        default -> throw new IllegalArgumentException("Invalid search type for Drug");
                    };
                } case PATIENT -> {
                    PatientDAO patientDao = (PatientDAO) dao;
                    results = switch (searchType) {
                        case "keys" -> Collections.singletonList(patientDao.get(searchParams));
                        case "firstName" -> patientDao.findByFirstName(searchParams[0]);
                        case "lastName" -> patientDao.findByLastName(searchParams[0]);
                        case "address" -> patientDao.findByAddress(searchParams[0]);
                        case "postcode" -> patientDao.findByPostcode(searchParams[0]);
                        case "phone" -> patientDao.findByPhoneNumber(searchParams[0]);
                        case "email" -> patientDao.findByEmail(searchParams[0]);
                        case "insurance" -> patientDao.findByInsuranceID(searchParams[0]);
                        default -> throw new IllegalArgumentException("Invalid search type for Patient");
                    };
                } case DOCTOR -> {
                    DoctorDAO doctorDAO = (DoctorDAO) dao;
                    results = switch (searchType) {
                        case "keys" -> Collections.singletonList(doctorDAO.get(searchParams));
                        case "firstName" -> doctorDAO.findByFirstName(searchParams[0]);
                        case "lastName" -> doctorDAO.findByLastName(searchParams[0]);
                        case "address" -> doctorDAO.findByAddress(searchParams[0]);
                        case "email" -> doctorDAO.findByEmail(searchParams[0]);
                        case "specialization" -> doctorDAO.findBySpecialization(searchParams[0]);
                        case "hospital" -> doctorDAO.findByHospital(searchParams[0]);
                        default -> throw new IllegalArgumentException("Invalid search type for Doctor");
                    };
                } default -> {
                    // Simple single parameter search for other entities
                    results = Collections.singletonList(dao.get(searchParams));
                }
            }
            
            if (results != null && !results.isEmpty() && results.get(0) != null) {
                displayResults(results, tableType);
                
            } else {
                // Display message if no results found
                JOptionPane.showMessageDialog(
                    this,
                    "No results found",
                    "Not Found",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
            
            //Error handling for search invalid search type
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

        //Add right click functionality
        new TableRightClick(table, tableType);
    }
    // Method to view the doctor table
    private void viewDoctorTable() {
        createTable(DOCTOR);
    }

    private void viewPatientTable() {
        createTable(PATIENT);
    }

    private void viewDrugTable() {
        createTable(DRUG);
    }

    public void viewInsuranceComTable() {
        createTable(INSURANCE);
    }

    public void viewPrescriptionTable() {
        createTable(PRESCRIPTION);
    }

    public void viewVisitTable() {
        createTable(VISIT);
    }
    
    // showForm method to display the form for adding a new record
    // The type parameter is used to determine the type of form to display
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
    // Different Methods used to call and display different types of forms. 
    private void showInsuranceComForm() {
        showForm(INSURANCE, new InsuranceCom());
    }

    private void showDrugForm() {
        showForm(DRUG, new Drug());
    }

    private void showDoctorForm() {
        showForm(DOCTOR, new Doctor());
    }

    private void showPatientForm() {
        showForm(PATIENT, new Patient());
    }

    private void showPrescriptionForm() {
        showForm(PRESCRIPTION, new Prescription());
    }

    private void showVisitForm() {
        showForm(VISIT, new Visit());
    }

    public static void main(String[] args) {
        // Test the database connection
        DatabaseConnection.testConnection();

        SwingUtilities.invokeLater(MainFrame::new);
    }
}