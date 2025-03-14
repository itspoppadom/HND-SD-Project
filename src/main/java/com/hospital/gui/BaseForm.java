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

    // Attributes
    protected T entity;
    protected boolean submitted = false;
    protected JPanel mainPanel;
    protected GridBagConstraints gbc;

    // Constructor
    public BaseForm(JFrame parent, T entity, String title) {
        super(parent, title, true);
        this.entity = entity;
        initializeForm();
    }

    // Method to initialize the form
    protected void initializeForm() {

        // Create the main panel
        mainPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        // Set the grid bag constraints
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Set the main panel as the content pane
        setContentPane(mainPanel);
        createFormFields();      // First create the fields
        populateFormFields();    // Then populate them with entity data
        createButtons();         // Finally add the buttons
        
        // Set the size of the form
        pack();
        setLocationRelativeTo(getOwner());
    }

    // Abstract methods that must be implemented by concrete forms
    protected abstract void createFormFields();
    protected abstract void populateFormFields();
    protected abstract void saveEntity();

    // Method to create buttons to save and cancel
    protected void createButtons() {
        
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        // Save button action listener
        saveButton.addActionListener(e -> {
            if (validateFields()) {
                saveEntity();
                submitted = true;
                dispose();
            }
        });

        // Cancel button action listener
        cancelButton.addActionListener(e -> dispose());

        //Add buttons to the form on a predefined grid
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, gbc);
    }


    // Method to add a form field to the form
    protected void addFormField(String label, javax.swing.JComponent component) {
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel(label + ":"), gbc);
        
        gbc.gridx = 1;
        mainPanel.add(component, gbc);
    }


    // Method to check if the form has been submitted
    public boolean isSubmitted() {
        return submitted;
    }


    // Method to validate the form fields
    protected boolean validateFields() {

        // Get the validation rules for the entity type
        FieldValidation[] rules = ValidationConfig.getValidationRules(getEntityType());
        //List to store errors
        List<String> errors = new ArrayList<>();

        // Loop through the rules and validate each field
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
        //Error message if there are issues with validation
        if (!errors.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                String.join("\n", errors),
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }


    // Abstract methods to be implemented by concrete forms
    protected abstract String getEntityType();
    protected abstract JTextField getFieldByName(String fieldName);
}