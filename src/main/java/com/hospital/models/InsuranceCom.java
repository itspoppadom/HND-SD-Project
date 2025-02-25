package com.hospital.models;

import java.util.Scanner;

public class InsuranceCom {

    private String insuranceID;
    private String companyName;
    private String address;
    private String phone;

    //Constructor for Insurance Company Tables

    public InsuranceCom (String insuranceID, String companyName, String address, String phone){
        this.insuranceID = insuranceID;
        this.companyName = companyName;
        this.address = address;
        this.phone = phone;
    }
    //Default Constructor
    public InsuranceCom (){
        this.insuranceID = "";
        this.companyName = "";
        this.address = "";
        this.phone = "";
    }


    //Setters and getters for the Insurance company Tables. 
    public String getInsuranceID() {
        return insuranceID;
    }
    public void setInsuranceID(String insuranceID) {
        this.insuranceID = insuranceID;
    }

    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
        
    public void addNewInsuranceCom(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Insurance ID: ");
        this.insuranceID = scanner.nextLine();
        System.out.println("Enter Company Name: ");
        this.companyName = scanner.nextLine();
        System.out.println("Enter Address: ");
        this.address = scanner.nextLine();
        System.out.println("Enter Phone: ");
        this.phone = scanner.nextLine();
    }
}