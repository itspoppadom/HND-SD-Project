package com.hospital.gui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.hospital.models.InsuranceCom;


public class InsuranceComForm extends BaseForm<InsuranceCom> {

    // Attributes
    private final InsuranceCom insurance;
    private boolean submitted = false;

    // Form fields
    private JTextField insuranceIDField;
    private JTextField companyNameField;
    private JTextField addressField;
    private JTextField phoneField;

    // Constructor
    public InsuranceComForm(JFrame parent, InsuranceCom insurance){
        super(parent, insurance,"Add Insurance Company");
        this.insurance = insurance; 
    }

    // Method to create form fields
    @Override
    protected void createFormFields(){
        insuranceIDField = new JTextField(20);
        companyNameField = new JTextField(20);
        addressField = new JTextField(20);
        phoneField = new JTextField(20);

        addFormField("Insurance ID", insuranceIDField);
        addFormField("Company Name", companyNameField);
        addFormField("Address", addressField);
        addFormField("Phone", phoneField);
    }
    // Method to populate form fields for editing
    @Override
    protected void populateFormFields(){
        if (entity != null) {
            insuranceIDField.setText(entity.getInsuranceID());
            companyNameField.setText(entity.getCompanyName());
            addressField.setText(entity.getAddress());
            phoneField.setText(entity.getPhone());
        }
    }
    // Method to get the entity type
    @Override
    protected String getEntityType(){
        return "insurance";
    }

    // Method to get the field by name
    @Override
    protected JTextField getFieldByName(String fieldName){
        return switch (fieldName) {
            case "insuranceID" -> insuranceIDField;
            case "companyName" -> companyNameField;
            case "address" -> addressField;
            case "phone" -> phoneField;
            default -> throw new IllegalArgumentException("Invalid field name: " + fieldName);
        };
    }


    // Method to save the entity
    @Override
    protected void saveEntity(){
        insurance.setInsuranceID(insuranceIDField.getText());
        insurance.setCompanyName(companyNameField.getText());
        insurance.setAddress(addressField.getText());
        insurance.setPhone(phoneField.getText());

        submitted = true;
        dispose();
    }

    // Method to check if the form is submitted
    public boolean isSubmitted(){
        return submitted;
    }
}