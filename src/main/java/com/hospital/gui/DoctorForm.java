package com.hospital.gui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.hospital.models.Doctor;

public class DoctorForm extends BaseForm<Doctor> {
    private JTextField doctorIDField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField addressField;
    private JTextField emailField;
    private JTextField specializationField;
    private JTextField hospitalField;

    public DoctorForm(JFrame parent, Doctor doctor) {
        super(parent, doctor, "Doctor Form");
    }

    @Override
    protected void createFormFields() {
        doctorIDField = new JTextField(20);
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        addressField = new JTextField(20);
        emailField = new JTextField(20);
        specializationField = new JTextField(20);
        hospitalField = new JTextField(20);

        addFormField("Doctor ID", doctorIDField);
        addFormField("First Name", firstNameField);
        addFormField("Last Name", lastNameField);
        addFormField("Address", addressField);
        addFormField("Email", emailField);
        addFormField("Specialization", specializationField);
        addFormField("Hospital", hospitalField);
    }
    @Override
    protected String getEntityType() {
        return "doctor";
    }
    @Override
    protected JTextField getFieldByName(String fieldName) {
        return switch (fieldName) {
            case "doctorID" -> doctorIDField;
            case "firstName" -> firstNameField;
            case "lastName" -> lastNameField;
            case "address" -> addressField;
            case "email" -> emailField;
            case "specialization" -> specializationField;
            case "hospital" -> hospitalField;
            default -> throw new IllegalArgumentException("Invalid field name: " + fieldName);
        };
    }

    @Override
    protected void populateFormFields() {
        if (entity != null) {
            doctorIDField.setText(entity.getDoctorID());
            firstNameField.setText(entity.getFirstName());
            lastNameField.setText(entity.getLastName());
            addressField.setText(entity.getAddress());
            emailField.setText(entity.getEmail());
            specializationField.setText(entity.getSpecialization());
            hospitalField.setText(entity.getHospital());
        }
    }

    @Override
    protected void saveEntity() {
        if (!validateFields()) {
            return;
        }
        entity.setDoctorID(doctorIDField.getText());
        entity.setFirstName(firstNameField.getText());
        entity.setLastName(lastNameField.getText());
        entity.setAddress(addressField.getText());
        entity.setEmail(emailField.getText());
        entity.setSpecialization(specializationField.getText());
        entity.setHospital(hospitalField.getText());

        submitted = true;
        dispose();
    }

    @Override
    public boolean isSubmitted() {
        return submitted;
    }
}