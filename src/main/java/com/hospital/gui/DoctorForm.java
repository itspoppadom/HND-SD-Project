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

import com.hospital.models.Doctor;

public class DoctorForm extends JDialog {
    private final Doctor doctor;
    private boolean submitted = false;
    
    private JTextField doctorIDField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField addressField;
    private JTextField emailField;
    private JTextField specializationField;
    private JTextField hospitalField;

    public DoctorForm(JFrame parent, Doctor doctor, boolean modal) {
        super(parent, "Add Doctor", modal);
        this.doctor = doctor;
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Add form fields
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Doctor ID:"), gbc);
        gbc.gridx = 1;
        doctorIDField = new JTextField(20);
        add(doctorIDField, gbc);
        
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
        doctor.setDoctorID(doctorIDField.getText());
        doctor.setFirstName(firstNameField.getText());
        doctor.setLastName(lastNameField.getText());
        doctor.setAddress(addressField.getText());
        doctor.setEmail(emailField.getText());
        doctor.setSpecialization(specializationField.getText());
        doctor.setHospital(hospitalField.getText());
        
        submitted = true;
        dispose();
    }
    
    public boolean isSubmitted() {
        return submitted;
    }
}