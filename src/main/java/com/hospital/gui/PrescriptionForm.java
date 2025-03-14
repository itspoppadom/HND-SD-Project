package com.hospital.gui;


import javax.swing.JFrame;
import javax.swing.JTextField;

import com.hospital.models.Prescription;

public class PrescriptionForm extends BaseForm<Prescription> {
    // Attributes
    private final Prescription prescription;
    private boolean submitted = false;

    // Form fields
    private JTextField prescriptionIDField;
    private JTextField dateOfPrescriptionField;
    private JTextField dosageField;
    private JTextField durationField;
    private JTextField commentField;
    private JTextField drugIDField;
    private JTextField doctorIDField;
    private JTextField patientIDField;

    // Constructor
public PrescriptionForm(JFrame parent, Prescription prescription){
    super(parent, prescription, "Add Prescription");
    this.prescription = prescription;
}

// Method to create form fields
@Override
protected void createFormFields(){

    // Create form fields
    prescriptionIDField = new JTextField(20);
    dateOfPrescriptionField = new JTextField(20);
    dosageField = new JTextField(20);
    durationField = new JTextField(20);
    commentField = new JTextField(80);
    drugIDField = new JTextField(20);
    doctorIDField = new JTextField(20);
    patientIDField = new JTextField(20);

    // Add form fields
    addFormField("Prescription ID", prescriptionIDField);
    addFormField("Date of Prescription", dateOfPrescriptionField);
    addFormField("Dosage", dosageField);
    addFormField("Duration", durationField);
    addFormField("Comment", commentField);
    addFormField("Drug ID", drugIDField);
    addFormField("Doctor ID", doctorIDField);
    addFormField("Patient ID", patientIDField);
}

// Method to populate form fields for editing
@Override
protected void populateFormFields(){
    if (entity != null) {
        prescriptionIDField.setText(entity.getPrescriptionID());
        dateOfPrescriptionField.setText(entity.getDatePrescribed());
        dosageField.setText(String.valueOf(entity.getDosage()));
        durationField.setText(String.valueOf(entity.getDuration()));
        commentField.setText(entity.getComment());
        drugIDField.setText(entity.getDrugID());
        doctorIDField.setText(entity.getDoctorID());
        patientIDField.setText(entity.getPatientID());
    }
}

// Method to get the entity type
@Override
protected String getEntityType(){
    return "prescription";
}
// Method to get the field/collumn by name
@Override
protected JTextField getFieldByName(String fieldName){
    return switch (fieldName) {
        case "prescriptionID" -> prescriptionIDField;
        case "dateOfPrescription" -> dateOfPrescriptionField;
        case "dosage" -> dosageField;
        case "duration" -> durationField;
        case "comment" -> commentField;
        case "drugID" -> drugIDField;
        case "doctorID" -> doctorIDField;
        case "patientID" -> patientIDField;
        default -> throw new IllegalArgumentException("Invalid field name: " + fieldName);
    };
}

// Method to save the details input by the user in the form
@Override
protected void saveEntity(){

    if (entity == null) {
        entity = new Prescription();
    }
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
// Method to check if the form is submitted
public boolean isSubmitted(){
    return submitted;

}
}