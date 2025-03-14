package com.hospital.gui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.hospital.models.Visit;

public class VisitForm extends BaseForm<Visit> {

    // Attributes
    private final Visit visit;
    private boolean submitted = false;

    // Form fields
    private JTextField patiendID;
    private JTextField doctorID;
    private JTextField dateOfVisit;
    private JTextField symptoms;
    private JTextField diagnosisID;


// Constructor
public VisitForm(JFrame parent, Visit visit){
    super(parent, visit, "Visit Form");
    this.visit = visit;
}

// Method to create form fields
@Override
protected void createFormFields(){
    patiendID = new JTextField(20);
    doctorID = new JTextField(20);
    dateOfVisit = new JTextField(20);
    symptoms = new JTextField(20);
    diagnosisID = new JTextField(20);

    addFormField("Patient ID", patiendID);
    addFormField("Doctor ID", doctorID);
    addFormField("Date of Visit", dateOfVisit);
    addFormField("Symptoms", symptoms);
    addFormField("Diagnosis ID", diagnosisID);
}

// Method to populate form fields for editing
@Override
protected void populateFormFields(){
    if (entity != null) {
        patiendID.setText(entity.getPatientID());
        doctorID.setText(entity.getDoctorID());
        dateOfVisit.setText(entity.getDateOfVisit());
        symptoms.setText(entity.getSymptoms());
        diagnosisID.setText(entity.getDiagnosisID());
    }
}

// Method to get the entity type
@Override
protected String getEntityType(){
    return "visit";
}


// Method to get the field by name
@Override
protected JTextField getFieldByName(String fieldName){
    return switch (fieldName) {
        case "patientID" -> patiendID;
        case "doctorID" -> doctorID;
        case "dateOfVisit" -> dateOfVisit;
        case "symptoms" -> symptoms;
        case "diagnosisID" -> diagnosisID;
        default -> throw new IllegalArgumentException("Invalid field name: " + fieldName);
    };
}


// Method to save the entity
@Override
protected void saveEntity(){
    visit.setPatientID(patiendID.getText());
    visit.setDoctorID(doctorID.getText());
    visit.setDateOfVisit(dateOfVisit.getText());
    visit.setSymptoms(symptoms.getText());
    visit.setDiagnosisID(diagnosisID.getText());

    submitted = true;
    dispose();
}

// Method to check if the form is submitted
@Override
public boolean isSubmitted(){
    return submitted;
}


}