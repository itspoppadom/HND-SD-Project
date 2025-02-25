package com.hospital.models;

import java.util.Scanner;;

public class Patient extends Person {
   
   //Variables for the Patient class
   private String patientID;
   private String postcode;
   private String phoneNumber;
   private String insuranceID;

    //Constructor for the Patient class
    public Patient(String firstName, String lastName, String address, String email, String patientID, String postcode, String phoneNumber, String insuranceID) {
        super(firstName, lastName, address, email);
        this.patientID = patientID;
        this.postcode = postcode;
        this.phoneNumber = phoneNumber;
        this.insuranceID = insuranceID;
    }

    //Default Constructor for Patient class
    public Patient() {
        super();
        this.patientID = "";
        this.postcode = "";
        this.phoneNumber = "";
        this.insuranceID = "";
    }
    //Setters and getters for the variables of a patient
    public String getPatientID() {
        return patientID;
    }
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getInsuranceID() {
        return insuranceID;

    }
    public void setInsuranceID(String insuranceID) {
        this.insuranceID = insuranceID;
    }

    //Display Info operation for the Patient class
    @Override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Postcode: " + postcode);
        System.out.println("Patient ID: " + patientID);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Insurance ID: " + insuranceID);
    }

    //Add new person operation
    public void addNewPatient(){
        Scanner scanner = new Scanner(System.in);
        super.addNewPerson();
        System.out.println("Enter Patient ID: ");
        patientID = scanner.nextLine();
        System.out.println("Enter Postcode: ");
        postcode = scanner.nextLine();
        System.out.println("Enter Phone Number: ");
        phoneNumber = scanner.nextLine();
        System.out.println("Enter Insurance ID: ");
        insuranceID = scanner.nextLine();
    }


}
