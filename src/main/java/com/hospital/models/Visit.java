package com.hospital.models;

import java.util.Scanner;

public class Visit{

    //Attributes for the Visit class
    private String patientID;
    private String doctorID;
    private String dateOfVisit;
    private String symptoms;
    private String diagnosisID;

    //Singleton scanner
    Scanner scanner = new Scanner(System.in);

    //Constructor for the Visit class
    public Visit(String patientID, String doctorID, String dateOfVisit, String symptoms, String diagnosisID){
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.dateOfVisit = dateOfVisit;
        this.symptoms = symptoms;
        this.diagnosisID = diagnosisID;
    }

    //Default Constructor for Visit class
    public Visit(){
        this.patientID = "";
        this.doctorID = "";
        this.dateOfVisit = "";
        this.symptoms = "";
        this.diagnosisID = "";
    }

    //Setters and Getters for the Visit class
    public String getPatientID() {
        return patientID;
    }
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }
    public String getDoctorID() {
        return doctorID;
    }
    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }
    public String getDateOfVisit() {
        return dateOfVisit;
    }
    public void setDateOfVisit(String dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }
    public String getSymptoms() {
        return symptoms;
    }
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
    public String getDiagnosisID() {
        return diagnosisID;
    }
    public void setDiagnosisID(String diagnosisID) {
        this.diagnosisID = diagnosisID;
    }

    //Function to display the Visit Information
    public void displayVisit(){
        System.out.println("Patient ID: " + patientID);
        System.out.println("Doctor ID: " + doctorID);
        System.out.println("Date of Visit: " + dateOfVisit);
        System.out.println("Symptoms: " + symptoms);
        System.out.println("Diagnosis ID: " + diagnosisID);
    }
    //Function to add a new Visit
    public void addNewVisit(){
        System.out.println("Enter Patient ID: ");
        patientID = scanner.nextLine();
        System.out.println("Enter Doctor ID: ");
        doctorID = scanner.nextLine();
        System.out.println("Enter Date of Visit: ");
        dateOfVisit = scanner.nextLine();
        System.out.println("Enter Symptoms: ");
        symptoms = scanner.nextLine();
        System.out.println("Enter Diagnosis ID: ");
        diagnosisID = scanner.nextLine();
    }
}