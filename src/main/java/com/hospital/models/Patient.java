package com.hospital.models;

public class Patient extends Person {
   private String patientID;
   private String postcode;
   private String phoneNumber;
   private String insuranceID;

    public Patient(String firstName, String lastName, String address, String email, String patientID, String postcode, String phoneNumber, String insuranceID) {
        super(firstName, lastName, address, email);
        this.patientID = patientID;
        this.postcode = postcode;
        this.phoneNumber = phoneNumber;
        this.insuranceID = insuranceID;
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
    @Override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Postcode: " + postcode);
        System.out.println("Patient ID: " + patientID);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Insurance ID: " + insuranceID);
    }

}
