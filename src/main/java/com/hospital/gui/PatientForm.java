package com.hospital.gui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.hospital.models.Patient;

public class PatientForm extends BaseForm<Patient> {

    // Attributes
    private final Patient patient;
    private boolean submitted = false;

    // Form fields
    private JTextField patientIDField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField addressField;
    private JTextField emailField;
    private JTextField postcodeField;
    private JTextField phoneNumberField;
    private JTextField insuranceIDField;

    // Constructor
    public PatientForm(JFrame parent, Patient patient){
        super(parent, patient,"Patient Form");
        this.patient = patient;
    }

    // Method to create form fields
    @Override
    protected void createFormFields() {

        // Create form fields
        patientIDField = new JTextField(20);
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        addressField = new JTextField(20);
        postcodeField = new JTextField(20);
        phoneNumberField = new JTextField(20);
        emailField = new JTextField(20);
        insuranceIDField = new JTextField(20);

        // Add form fields
        addFormField("Patient ID", patientIDField);
        addFormField("First Name", firstNameField);
        addFormField("Last Name", lastNameField);
        addFormField("Address", addressField);
        addFormField("Postcode", postcodeField);
        addFormField("Phone Number", phoneNumberField);
        addFormField("Email", emailField);
        addFormField("Insurance ID", insuranceIDField);
    }

    // Method to populate form fields for editing
    @Override
    protected void populateFormFields() {
        if (entity != null) {  // Use entity from BaseForm instead of local patient field
            patientIDField.setText(entity.getPatientID());
            firstNameField.setText(entity.getFirstName());
            lastNameField.setText(entity.getLastName());
            addressField.setText(entity.getAddress());
            postcodeField.setText(entity.getPostcode());
            phoneNumberField.setText(entity.getPhoneNumber());
            emailField.setText(entity.getEmail());
            insuranceIDField.setText(entity.getInsuranceID());
        }
    }

    // Method to get the entity type
    @Override
    protected String getEntityType() {
        return "patient";
    }


    // Method to get the field by name
    @Override
    protected JTextField getFieldByName(String fieldName) {
        return switch (fieldName) {
            case "patientID" -> patientIDField;
            case "firstName" -> firstNameField;
            case "lastName" -> lastNameField;
            case "address" -> addressField;
            case "postcode" -> postcodeField;
            case "phone" -> phoneNumberField;
            case "email" -> emailField;
            case "insuranceID" -> insuranceIDField;
            default -> throw new IllegalArgumentException("Unknown field: " + fieldName);
        };
    }

    // Method to save the entity
    @Override   
    protected void saveEntity() {
        if (!validateFields()) {
            return;
        }
        entity.setPatientID(patientIDField.getText());  // Use entity from BaseForm
        entity.setFirstName(firstNameField.getText());
        entity.setLastName(lastNameField.getText());
        entity.setPostcode(postcodeField.getText());
        entity.setAddress(addressField.getText());
        entity.setPhoneNumber(phoneNumberField.getText());
        entity.setEmail(emailField.getText());
        entity.setInsuranceID(insuranceIDField.getText());

        submitted = true;
        dispose();
    }

    // Method to check if the form has been submitted
    @Override
    public boolean isSubmitted() {
        return submitted;
    }   
}