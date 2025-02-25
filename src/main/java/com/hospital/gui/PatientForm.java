package com.hospital.gui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.hospital.models.Patient;

public class PatientForm extends BaseForm<Patient> {
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

    public PatientForm(JFrame parent, Patient patient){
        super(parent, patient,"Patient Form");
        this.patient = patient;
    }

    @Override
    protected void createFormFields() {
        patientIDField = new JTextField(20);
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        addressField = new JTextField(20);
        emailField = new JTextField(20);
        postcodeField = new JTextField(20);
        phoneNumberField = new JTextField(20);
        insuranceIDField = new JTextField(20);

        addFormField("Patient ID", patientIDField);
        addFormField("First Name", firstNameField);
        addFormField("Last Name", lastNameField);
        addFormField("Address", addressField);
        addFormField("Email", emailField);
        addFormField("Postcode", postcodeField);
        addFormField("Phone Number", phoneNumberField);
        addFormField("Insurance ID", insuranceIDField);
    }
     
    
    @Override   
    protected void saveEntity() {
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
    @Override
    public boolean isSubmitted() {
        return submitted;
    }   
}