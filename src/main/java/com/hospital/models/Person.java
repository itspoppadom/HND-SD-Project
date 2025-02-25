package com.hospital.models;
import java.util.Scanner;

public class Person {
    //Variables for Person class    
    private String firstName; 
    private String lastName; 
    private String address; 
    private String email;


    //Singleton scanner
    Scanner scanner = new Scanner(System.in);

    //Constructor for Person class
    public Person(String firstName, String lastName, String address, String email) {
        this.firstName = firstName; this.lastName = lastName; this.address = address;
        this.email = email;
    }
    //Default Constructor
    public Person() {
        this.firstName = ""; this.lastName = ""; this.address = ""; this.email = "";
    }
    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }

    //Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setAddress(String address) { this.address = address; }
    public void setEmail(String email) { this.email = email; }

    //Functions for Person class.
    public void displayInfo(){
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Address: " + address);

    }
    public void addNewPerson(){
        System.out.println("Enter First Name: ");
        setFirstName(scanner.nextLine());
        System.out.println("Enter Last Name: ");
        setLastName(scanner.nextLine());
        System.out.println("Enter Email: ");
        setEmail(scanner.nextLine());
        System.out.println("Enter Address: ");
        setAddress(scanner.nextLine());
    }
    
}
