package com.hospital.models;

import java.util.Scanner;


public class Drug {

    //Attributes for the Drug Class
    private String drugID;
    private String drugName;
    private String sideEffects;
    private String benefits;

    //Singleton Scanner
    Scanner scanner = new Scanner(System.in);

    //Constructor for the Drug Class
    public Drug(String drugID, String drugName, String sideEffects, String benefits) {
        this.drugID = drugID;
        this.drugName = drugName;
        this.sideEffects = sideEffects;
        this.benefits = benefits;
    }

    //Default Constructor for Drug Class
    public Drug(){
        this.drugID = "";
        this.drugName = "";
        this.sideEffects = "";
        this.benefits = "";
    }
    
    //Setters and Getters for Drug Class
    public String getDrugID() {
        return drugID;
    }
    public void setDrugID(String drugID) {
        this.drugID = drugID;
    }
    public String getDrugName() {
        return drugName;
    }
    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }
    public String getSideEffects() {
        return sideEffects;
    }
    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }
    public String getBenefits() {
        return benefits;
    }
    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    //Function to display the Drug Information
    public void displayInfo(){
        System.out.println("Drug ID: " + drugID);
        System.out.println("Drug Name: " + drugName);
        System.out.println("Side Effects: " + sideEffects);
        System.out.println("Benefits: " + benefits);
    }

    //Function to add a new Drug
    public void addNewDrug(){
        System.out.println("Enter Drug ID: ");
        drugID = scanner.nextLine();
        System.out.println("Enter Drug Name: ");
        drugName = scanner.nextLine();
        System.out.println("Enter Side Effects: ");
        sideEffects = scanner.nextLine();
        System.out.println("Enter Benefits: ");
        benefits = scanner.nextLine();
    }

}