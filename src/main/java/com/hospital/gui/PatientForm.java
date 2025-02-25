package com.hospital.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hospital.models.Patient;

public class PatientForm extends JDialog {
    private final Patient patient;
    private boolean submitted = false;

    private JTextField patientIDField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField addressField;
    private JTextField emailField;
    private JTextField postcodeField;
    private JTextField phoneNumberField;
    private JTextField insuranceIDField;

    public PatientForm(JFrame parent, Patient patient, boolean modal){
        super(parent, "Add Patient", modal);
        this.patient = patient;
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add form fields
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Patient ID:"), gbc);
        gbc.gridx = 1;
        patientIDField = new JTextField(20);
        add(patientIDField, gbc);

        // Add other fields similarly...

        // Add buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> save());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        pack();
        setLocationRelativeTo(parent);
    }

    private void save() {
        patient.setPatientID(patientIDField.getText());
        patient.setFirstName(firstNameField.getText());
        patient.setLastName(lastNameField.getText());
        patient.setPostcode(postcodeField.getText());
        patient.setAddress(addressField.getText());
        patient.setPhoneNumber(phoneNumberField.getText());
        patient.setEmail(emailField.getText());
        patient.setInsuranceID(insuranceIDField.getText());

        submitted = true;
        dispose();
    }

    public boolean isSubmitted() {
        return submitted;
    }   
}