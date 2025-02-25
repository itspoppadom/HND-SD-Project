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
    protected void saveEntity() {
        entity.setDoctorID(doctorIDField.getText());
        entity.setFirstName(firstNameField.getText());
        entity.setLastName(lastNameField.getText());
        entity.setAddress(addressField.getText());
        entity.setEmail(emailField.getText());
        entity.setSpecialization(specializationField.getText());
        entity.setHospital(hospitalField.getText());
    }
}