package com.hospital.gui;


import javax.swing.JFrame;
import javax.swing.JTextField;

import com.hospital.models.Prescription;

public class PrescriptionForm extends BaseForm<Prescription> {
    private final Prescription prescription;
    private boolean submitted = false;

    private JTextField prescriptionIDField;
    private JTextField dateOfPrescriptionField;
    private JTextField dosageField;
    private JTextField durationField;
    private JTextField commentField;
    private JTextField drugIDField;
    private JTextField doctorIDField;
    private JTextField patientIDField;
public PrescriptionForm(JFrame parent, Prescription prescription){
    super(parent, prescription, "Add Prescription");
    this.prescription = prescription;
}
@Override
protected void createFormFields(){
    prescriptionIDField = new JTextField(20);
    dateOfPrescriptionField = new JTextField(20);
    dosageField = new JTextField(20);
    durationField = new JTextField(20);
    commentField = new JTextField(80);
    drugIDField = new JTextField(20);
    doctorIDField = new JTextField(20);
    patientIDField = new JTextField(20);

    addFormField("Prescription ID", prescriptionIDField);
    addFormField("Date of Prescription", dateOfPrescriptionField);
    addFormField("Dosage", dosageField);
    addFormField("Duration", durationField);
    addFormField("Comment", commentField);
    addFormField("Drug ID", drugIDField);
    addFormField("Doctor ID", doctorIDField);
    addFormField("Patient ID", patientIDField);
}
@Override
protected void saveEntity(){
    prescription.setPrescriptionID(prescriptionIDField.getText());
    prescription.setDatePrescribed(dateOfPrescriptionField.getText());
    prescription.setDosage(Integer.parseInt(dosageField.getText()));
    prescription.setDuration(Integer.parseInt(durationField.getText()));
    prescription.setComment(commentField.getText());
    prescription.setDrugID(drugIDField.getText());
    prescription.setDoctorID(doctorIDField.getText());
    prescription.setPatientID(patientIDField.getText());

    submitted = true;
    dispose();

}
public boolean isSubmitted(){
    return submitted;

}
}