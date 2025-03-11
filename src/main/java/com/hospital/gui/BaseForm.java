package com.hospital.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hospital.validation.FieldValidation;
import com.hospital.validation.ValidationConfig;

public abstract class BaseForm<T> extends JDialog {
    protected T entity;
    protected boolean submitted = false;
    protected JPanel mainPanel;
    protected GridBagConstraints gbc;

    public BaseForm(JFrame parent, T entity, String title) {
        super(parent, title, true);
        this.entity = entity;
        initializeForm();
    }

    protected void initializeForm() {
        mainPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        setContentPane(mainPanel);
        createFormFields();      // First create the fields
        populateFormFields();    // Then populate them with entity data
        createButtons();         // Finally add the buttons
        
        pack();
        setLocationRelativeTo(getOwner());
    }

    // Abstract methods that must be implemented by concrete forms
    protected abstract void createFormFields();
    protected abstract void populateFormFields();
    protected abstract void saveEntity();

    protected void createButtons() {
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            if (validateFields()) {
                saveEntity();
                submitted = true;
                dispose();
            }
        });

        cancelButton.addActionListener(e -> dispose());

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, gbc);
    }

    protected void addFormField(String label, javax.swing.JComponent component) {
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel(label + ":"), gbc);
        
        gbc.gridx = 1;
        mainPanel.add(component, gbc);
    }

    public boolean isSubmitted() {
        return submitted;
    }

    protected boolean validateFields() {
        FieldValidation[] rules = ValidationConfig.getValidationRules(getEntityType());
        List<String> errors = new ArrayList<>();

        for (FieldValidation rule : rules) {
            JTextField field = getFieldByName(rule.getFieldName());
            String value = field.getText().trim();

            // Check required fields
            if (rule.isRequired() && value.isEmpty()) {
                errors.add(rule.getFieldName() + " is required");
                continue;
            }

            // Check length
            if (value.length() > rule.getMaxLength()) {
                errors.add(rule.getFieldName() + " must be " + rule.getMaxLength() + " characters or less");
                continue;
            }

            // Check pattern if specified
            if (rule.getPattern() != null && !value.isEmpty()) {
                if (!Pattern.matches(rule.getPattern(), value)) {
                    errors.add(rule.getErrorMessage());
                }
            }
        }

        if (!errors.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                String.join("\n", errors),
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    protected abstract String getEntityType();
    protected abstract JTextField getFieldByName(String fieldName);
}