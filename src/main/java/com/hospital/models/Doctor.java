package com.hospital.models;

public class Doctor extends Person {

    private String doctorID;
    private String specialization;
    private String hospital;

    public Doctor(String firstName, String lastName, String address,String email, String doctorID,String specialization,String hospital ){
        super(firstName,lastName,address,email);
        this.doctorID = doctorID;
        this.specialization = specialization;
        this.hospital = hospital;
    }



    //Setters & Getters for the Doctor class
    public String getDoctorID() {
        return doctorID;
    }
    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }
    public String getSpecialization() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    public String getHospital() {
        return hospital;
    }
    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    //Functions for the Doctor class
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Doctor ID: "+doctorID);
        System.out.println("Specialization: "+specialization);
        System.out.println("Hospital: "+hospital);

    }


}
