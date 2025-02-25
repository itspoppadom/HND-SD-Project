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

import com.hospital.models.InsuranceCom;


public class InsuranceForm extends JDialog {
    private final InsuranceCom insurance;
    private boolean submitted = false;


    private JTextField insuranceIDField;
    private JTextField companyNameField;
    private JTextField addressField;
    private JTextField phoneField;

    public InsuranceForm(JFrame parent, InsuranceCom insurance, boolean modal){
        super(parent, "Add Insurance Company", modal);
        this.insurance = insurance; 

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add form fields
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Insurance ID:"), gbc);
        gbc.gridx = 1;
        insuranceIDField = new JTextField(20);
        add(insuranceIDField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Company Name:"), gbc);  
        gbc.gridx = 1;
        companyNameField = new JTextField(20);
        add(companyNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        addressField = new JTextField(20);
        add(addressField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(20);
        add(phoneField, gbc);

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
    private void save(){
        insurance.setInsuranceID(insuranceIDField.getText());
        insurance.setCompanyName(companyNameField.getText());
        insurance.setAddress(addressField.getText());
        insurance.setPhone(phoneField.getText());

        submitted = true;
        dispose();
    }
    public boolean isSubmitted(){
        return submitted;
    }
}