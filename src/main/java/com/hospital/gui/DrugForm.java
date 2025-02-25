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

import com.hospital.models.Drug;

public class DrugForm extends JDialog { 
    private final Drug drug;
    private boolean submitted = false;

    private JTextField drugIDField;
    private JTextField drugNameField;
    private JTextField sideEffectsField;
    private JTextField benefitsField;

    public DrugForm(JFrame parent, Drug drug, boolean modal){
        super(parent, "Add Drug", modal);
        this.drug = drug;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        // Add form fields
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Drug ID:"), gbc);
        gbc.gridx = 1;
        drugIDField = new JTextField(20);
        add(drugIDField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Drug Name:"), gbc);
        gbc.gridx = 1;
        drugNameField = new JTextField(20);
        add(drugNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Side Effects:"), gbc);
        gbc.gridx = 1;
        sideEffectsField = new JTextField(20);
        add(sideEffectsField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Benefits:"), gbc);
        gbc.gridx = 1;
        benefitsField = new JTextField(20);
        add(benefitsField, gbc);

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
            drug.setDrugID(drugIDField.getText());
            drug.setDrugName(drugNameField.getText());
            drug.setSideEffects(sideEffectsField.getText());
            drug.setBenefits(benefitsField.getText());

            submitted = true;
            dispose();
        }

        public boolean isSubmitted() {
            return submitted;
        }
    }
