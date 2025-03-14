package com.hospital.gui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.hospital.models.Drug;

public class DrugForm extends BaseForm<Drug> { 

    // Attributes
    private final Drug drug;
    private boolean submitted = false;

    // Form fields
    private JTextField drugIDField;
    private JTextField drugNameField;
    private JTextField sideEffectsField;
    private JTextField benefitsField;


    // Constructor
    public DrugForm(JFrame parent, Drug drug){
        super(parent, drug, "Drug Form");
        this.drug = drug;
    
    }

    // Method to create form fields
    @Override
    protected void createFormFields() {
        // Create form fields
        drugIDField = new JTextField(20);
        drugNameField = new JTextField(20);
        sideEffectsField = new JTextField(20);
        benefitsField = new JTextField(20);

        // Add form fields to form
        addFormField("Drug ID", drugIDField);
        addFormField("Drug Name", drugNameField);
        addFormField("Side Effects", sideEffectsField);
        addFormField("Benefits", benefitsField);

    }

    // Method to populate form fields for editing
    @Override 
    protected void populateFormFields() {
        if (entity != null) { 
            drugIDField.setText(entity.getDrugID());
            drugNameField.setText(entity.getDrugName());
            sideEffectsField.setText(entity.getSideEffects());
            benefitsField.setText(entity.getBenefits());
        }
    }

    // Method to get the entity type
    @Override
    protected String getEntityType() {
        return "drug";
    }

    // Method to get the field by name
    @Override
    protected JTextField getFieldByName(String fieldName) {
        return switch (fieldName) {
            case "drugID" -> drugIDField;
            case "drugName" -> drugNameField;
            case "sideEffects" -> sideEffectsField;
            case "benefits" -> benefitsField;
            default -> throw new IllegalArgumentException("Invalid field name: " + fieldName);
        };
    }

    // Method to save the entity
    @Override
    protected void saveEntity() {
        if (!validateFields()) {
            return;
        } 
        drug.setDrugID(drugIDField.getText());
        drug.setDrugName(drugNameField.getText());
        drug.setSideEffects(sideEffectsField.getText());
        drug.setBenefits(benefitsField.getText());

        submitted = true;
        dispose();
    }

    // Method to check if the form has been submitted
    @Override
    public boolean isSubmitted() {
        return submitted;
    }
    }
