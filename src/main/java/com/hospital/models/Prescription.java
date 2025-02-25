package com.hospital.models;

import com.hospital.utils.DateValidator;

public class Prescription {

    private String prescriptionID;
    private String datePrescribed;
    private int dosage;
    private int duration;
    private String comment;
    private String drugID;  // Association by ID
    private String doctorID;  // Association by ID
    private String patientID;  // Association by ID

    // Default Constructor for Prescription Class
    public Prescription() {
        this.prescriptionID = "";
        this.datePrescribed = DateValidator.getCurrentDate();
        this.dosage = 0;
        this.duration = 0;
        this.comment = "";
        this.drugID = "";
        this.doctorID = "";
        this.patientID = "";
    }

    public Prescription(String prescriptionID, String datePrescribed, int dosage, int duration, String comment, String drugID, String doctorID, String patientID) {
        this.prescriptionID = prescriptionID;
        this.datePrescribed = datePrescribed;
        this.dosage = dosage;
        this.duration = duration;
        this.comment = comment;
        this.drugID = drugID;
        this.doctorID = doctorID;
        this.patientID = patientID;
    }

    // Setters and Getters for Prescription Class
    public String getPrescriptionID() {
        return prescriptionID;
    }

    public void setPrescriptionID(String prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public String getDatePrescribed() {
        return datePrescribed;
    }

    public void setDatePrescribed(String datePrescribed) {
        this.datePrescribed = datePrescribed;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDrugID() {
        return drugID;
    }

    public void setDrugID(String drugID) {
        this.drugID = drugID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }
}
