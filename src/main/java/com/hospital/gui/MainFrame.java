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

        // Add search menu items

        //Update the Doctor search menu item action listener
        searchDoctorMenuItem.addActionListener(e -> {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout(5, 5));

            // Create radio buttons for search type 
            JRadioButton searchByKeys = new JRadioButton("Search by Doctor ID");
            JRadioButton searchByFirstName = new JRadioButton("Search by First Name");
            JRadioButton searchByLastName = new JRadioButton("Search by Last Name");
            JRadioButton searchByAddress = new JRadioButton("Search by Address");
            JRadioButton searchByEmail = new JRadioButton("Search by Email");
            JRadioButton searchBySpecialization = new JRadioButton("Search by Specialization");
            JRadioButton searchByHospital = new JRadioButton("Search by Hospital");

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

            fieldsPanel.add(new JLabel("Doctor ID:"));
            fieldsPanel.add(doctorIDField);
            fieldsPanel.add(new JLabel("First Name:"));
            fieldsPanel.add(firstNameField);
            fieldsPanel.add(new JLabel("Last Name:"));
            fieldsPanel.add(lastNameField);
            fieldsPanel.add(new JLabel("Address:"));
            fieldsPanel.add(addressField);
            fieldsPanel.add(new JLabel("Email:"));
            fieldsPanel.add(emailField);
            fieldsPanel.add(new JLabel("Specialization:"));
            fieldsPanel.add(specializationField);
            fieldsPanel.add(new JLabel("Hospital:"));
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

            int result = JOptionPane.showConfirmDialog(
                this, panel, "Search Doctor",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String searchType = "";
                    String[] searchParams = null;

                    if (searchByKeys.isSelected()) {
                        if (doctorIDField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Doctor ID required");
                        }
                        searchType = "keys";
                        searchParams = new String[]{doctorIDField.getText()};
                    } else if (searchByFirstName.isSelected()) {
                        if (firstNameField.getText().isEmpty()) {
                            throw new IllegalArgumentException("First name required");
                        }
                        searchType = "firstName";
                        searchParams = new String[]{firstNameField.getText()};
                    } else if (searchByLastName.isSelected()) {
                        if (lastNameField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Last name required");
                        }
                        searchType = "lastName";
                        searchParams = new String[]{lastNameField.getText()};
                    } else if (searchByAddress.isSelected()) {
                        if (addressField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Address required");
                        }
                        searchType = "address";
                        searchParams = new String[]{addressField.getText()};
                    } else if (searchByEmail.isSelected()) {
                        if (emailField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Email required");
                        }
                        searchType = "email";
                        searchParams = new String[]{emailField.getText()};
                    } else if (searchBySpecialization.isSelected()) {
                        if (specializationField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Specialization required");
                        }
                        searchType = "specialization";
                        searchParams = new String[]{specializationField.getText()};
                    } else if (searchByHospital.isSelected()) {
                        if (hospitalField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Hospital required");
                        }
                        searchType = "hospital";
                        searchParams = new String[]{hospitalField.getText()};
                    }

                    ResultSet("doctor", searchType, searchParams);

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
            JRadioButton searchByKeys = new JRadioButton("Search by Patient ID");
            JRadioButton searchByFirstName = new JRadioButton("Search by First Name");
            JRadioButton searchByLastName = new JRadioButton("Search by Last Name");
            JRadioButton searchByAddress = new JRadioButton("Search by Address");
            JRadioButton searchByPostcode = new JRadioButton("Search by Postcode");
            JRadioButton searchByPhone = new JRadioButton("Search by Phone Number");
            JRadioButton searchByEmail = new JRadioButton("Search by Email");
            JRadioButton searchByInsurance = new JRadioButton("Search by Insurance ID");

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

            fieldsPanel.add(new JLabel("Patient ID:"));
            fieldsPanel.add(patientIDField);
            fieldsPanel.add(new JLabel("First Name:"));
            fieldsPanel.add(firstNameField);
            fieldsPanel.add(new JLabel("Last Name:"));
            fieldsPanel.add(lastNameField);
            fieldsPanel.add(new JLabel("Address:"));
            fieldsPanel.add(addressField);
            fieldsPanel.add(new JLabel("Postcode:"));
            fieldsPanel.add(postcodeField);
            fieldsPanel.add(new JLabel("Phone Number:"));
            fieldsPanel.add(phoneField);
            fieldsPanel.add(new JLabel("Email:"));
            fieldsPanel.add(emailField);
            fieldsPanel.add(new JLabel("Insurance ID:"));
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

            int result = JOptionPane.showConfirmDialog(
                this, panel, "Search Patient",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String searchType = "";
                    String[] searchParams = null;

                    if (searchByKeys.isSelected()) {
                        if (patientIDField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Patient ID required");
                        }
                        searchType = "keys";
                        searchParams = new String[]{patientIDField.getText()};
                    } else if (searchByFirstName.isSelected()) {
                        if (firstNameField.getText().isEmpty()) {
                            throw new IllegalArgumentException("First name required");
                        }
                        searchType = "firstName";
                        searchParams = new String[]{firstNameField.getText()};
                    } else if (searchByLastName.isSelected()) {
                        if (lastNameField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Last name required");
                        }
                        searchType = "lastName";
                        searchParams = new String[]{lastNameField.getText()};
                    } else if (searchByAddress.isSelected()) {
                        if (addressField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Address required");
                        }
                        searchType = "address";
                        searchParams = new String[]{addressField.getText()};
                    } else if (searchByPostcode.isSelected()) {
                        if (postcodeField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Postcode required");
                        }
                        searchType = "postcode";
                        searchParams = new String[]{postcodeField.getText()};
                    } else if (searchByPhone.isSelected()) {
                        if (phoneField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Phone number required");
                        }
                        searchType = "phone";
                        searchParams = new String[]{phoneField.getText()};
                    } else if (searchByEmail.isSelected()) {
                        if (emailField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Email required");
                        }
                        searchType = "email";
                        searchParams = new String[]{emailField.getText()};
                    } else if (searchByInsurance.isSelected()) {
                        if (insuranceField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Insurance ID required");
                        }
                        searchType = "insurance";
                        searchParams = new String[]{insuranceField.getText()};
                    }

                    ResultSet("patient", searchType, searchParams);

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
            JRadioButton searchByKeys = new JRadioButton("Search by Drug ID");
            JRadioButton searchByDrugName = new JRadioButton("Search by Drug Name");
            JRadioButton searchBySideEffects = new JRadioButton("Search by Side Effects");
            JRadioButton searchByBenefits = new JRadioButton("Search by Benefits");

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

            fieldsPanel.add(new JLabel("Drug ID:"));
            fieldsPanel.add(drugIDField);
            fieldsPanel.add(new JLabel("Drug Name:"));
            fieldsPanel.add(drugNameField);
            fieldsPanel.add(new JLabel("Side Effects:"));
            fieldsPanel.add(sideEffectsField);
            fieldsPanel.add(new JLabel("Benefits:"));
            fieldsPanel.add(benefitsField);

            // Add components to panel
            JPanel radioPanel = new JPanel(new GridLayout(4, 1));
            radioPanel.add(searchByKeys);
            radioPanel.add(searchByDrugName);
            radioPanel.add(searchBySideEffects);
            radioPanel.add(searchByBenefits);
            
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

            int result = JOptionPane.showConfirmDialog(
                this, panel, "Search Drug",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String searchType = "";
                    String[] searchParams = null;

                    if (searchByKeys.isSelected()) {
                        if (drugIDField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Drug ID required");
                        }
                        searchType = "keys";
                        searchParams = new String[]{drugIDField.getText()};
                    } else if (searchByDrugName.isSelected()) {
                        if (drugNameField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Drug name required");
                        }
                        searchType = "drugName";
                        searchParams = new String[]{drugNameField.getText()};
                    } else if (searchBySideEffects.isSelected()) {
                        if (sideEffectsField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Side effects required");
                        }
                        searchType = "sideEffects";
                        searchParams = new String[]{sideEffectsField.getText()};
                    } else if (searchByBenefits.isSelected()) {
                        if (benefitsField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Benefits required");
                        }
                        searchType = "benefits";
                        searchParams = new String[]{benefitsField.getText()};
                    }

                    ResultSet("drug", searchType, searchParams);

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
            JRadioButton searchByKeys = new JRadioButton("Search by Insurance ID");
            JRadioButton searchByCompany = new JRadioButton("Search by Company Name");
            JRadioButton searchByAddress = new JRadioButton("Search by Address");
            JRadioButton searchByPhone = new JRadioButton("Search by Phone Number");

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
            
            fieldsPanel.add(new JLabel("Insurance ID:"));
            fieldsPanel.add(insuranceIDField);
            fieldsPanel.add(new JLabel("Company Name:"));
            fieldsPanel.add(companyField);
            fieldsPanel.add(new JLabel("Address:"));
            fieldsPanel.add(addressField);
            fieldsPanel.add(new JLabel("Phone Number:"));
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
                        searchType = "keys";
                        searchParams = new String[]{insuranceIDField.getText()};
                    } else if (searchByCompany.isSelected()) {
                        if (companyField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Company name required");
                        }
                        searchType = "company";
                        searchParams = new String[]{companyField.getText()};
                    } else if (searchByAddress.isSelected()) {
                        if (addressField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Address required");
                        }
                        searchType = "address";
                        searchParams = new String[]{addressField.getText()};
                    } else if (searchByPhone.isSelected()) {
                        if (phoneField.getText().isEmpty()) {
                            throw new IllegalArgumentException("Phone number required");
                        }
                        searchType = "phone";
                        searchParams = new String[]{phoneField.getText()};
                    }
                    
                    ResultSet("insurance", searchType, searchParams);
                    
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

            JRadioButton searchByKeys = new JRadioButton("Search by Prescription ID");
            JRadioButton searchByPatient = new JRadioButton("Search by Patient ID");
            JRadioButton searchByDoctor = new JRadioButton("Search by Doctor ID");
            JRadioButton searchByDrug = new JRadioButton("Search by Drug ID");
            JRadioButton searchByDate = new JRadioButton("Search by Prescription Date");
            JRadioButton searchByDosage = new JRadioButton("Search by Dosage");
            JRadioButton searchByDuration = new JRadioButton("Search by Duration");
            
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
            // Show dialog
            int result = JOptionPane.showConfirmDialog(
                this, panel, "Search Prescription",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            // Handle search
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
                case "insurance" -> {
                    InsuranceComDAO insuranceDao = (InsuranceComDAO) dao;
                    results = switch (searchType) {
                        case "keys" -> Collections.singletonList(insuranceDao.get(searchParams));
                        case "company" -> insuranceDao.findByCompany(searchParams[0]);
                        case "address" -> insuranceDao.findByAddress(searchParams[0]);
                        case "phone" -> insuranceDao.findByPhone(searchParams[0]);
                        default -> throw new IllegalArgumentException("Invalid search type for Insurance Company");};
                } case "drug" -> {
                    DrugDAO drugDao = (DrugDAO) dao;
                    results = switch (searchType) {
                        case "keys" -> Collections.singletonList(drugDao.get(searchParams));
                        case "drugName" -> drugDao.findByDrugName(searchParams[0]);
                        case "sideEffects" -> drugDao.findBySideEffects(searchParams[0]);
                        case "benefits" -> drugDao.findByBenefits(searchParams[0]);
                        default -> throw new IllegalArgumentException("Invalid search type for Drug");
                    };
                } case "patient" -> {
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
                } case "doctor" -> {
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