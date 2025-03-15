package com.hospital.models;

import java.util.Scanner;

import com.hospital.utils.DateValidator;

public class Prescription {

    //Attributes for Prescription Class
    private String prescriptionID;
    private String datePrescribed;
    private int dosage;
    private int duration;
    private String comment;
    private String drugID;  // Association by ID
    private String doctorID;  // Association by ID
    private String patientID;  // Association by ID

    // Singleton Scanner    
    Scanner scanner = new Scanner(System.in);

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

    // Constructor for Prescription Class
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
        this.datePrescribed = DateValidator.isValidDate(datePrescribed) ? datePrescribed : DateValidator.getCurrentDate();
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

    // Function to display Prescription Information
    public void displayInfo() {
        System.out.println("Prescription ID: " + prescriptionID);
        System.out.println("Date Prescribed: " + datePrescribed);
        System.out.println("Dosage: " + dosage);
        System.out.println("Duration: " + duration);
        System.out.println("Comment: " + comment);
        System.out.println("Drug ID: " + drugID);
        System.out.println("Doctor ID: " + doctorID);
        System.out.println("Patient ID: " + patientID);
    }

    // Function to add a new Prescription
    public void addNewPrescription (){
        System.out.println("Enter Prescription ID: ");
        prescriptionID = scanner.nextLine();
        System.out.println("Enter Date (YYYY-MM-DD): ");
        datePrescribed = scanner.nextLine();
        datePrescribed = DateValidator.isValidDate(datePrescribed) ? datePrescribed : DateValidator.getCurrentDate();
        System.out.println("Enter Dosage: ");
        dosage = scanner.nextInt();
        System.out.println("Enter Duration: ");
        duration = scanner.nextInt();
        System.out.println("Enter Comment: ");
        comment = scanner.nextLine();
        System.out.println("Enter Drug ID: ");
        drugID = scanner.nextLine();
        System.out.println("Enter Doctor ID: ");
        doctorID = scanner.nextLine();
        System.out.println("Enter Patient ID: ");
        patientID = scanner.nextLine();
    }
}
