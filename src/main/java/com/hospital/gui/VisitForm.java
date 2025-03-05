package com.hospital.gui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.hospital.models.Visit;

public class VisitForm extends BaseForm<Visit> {
    private final Visit visit;
    private boolean submitted = false;

    private JTextField patiendID;
    private JTextField doctorID;
    private JTextField dateOfVisit;
    private JTextField symptoms;
    private JTextField diagnosisID;


public VisitForm(JFrame parent, Visit visit){
    super(parent, visit, "Visit Form");
    this.visit = visit;
}
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
@Override
public boolean isSubmitted(){
    return submitted;
}


}