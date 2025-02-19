package com.hospital.dao;

import com.hospital.models.Patient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PatientDAO {
    public static void addPatient(Patient patient){
        String query = "INSERT INTO patients (patientID, firstname, surname,postcode ,address, phone, email ,insurance_id) VALUES (?, ?, ?, ?, ? , ? , ? , ? )";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, patient.getPatientID());
            stmt.setString(2, patient.getFirstName());
            stmt.setString(3, patient.getLastName());
            stmt.setString(4, patient.getPostcode());
            stmt.setString(5, patient.getAddress());
            stmt.setString(6, patient.getPhoneNumber());
            stmt.setString(7, patient.getEmail());
            stmt.setString(8, patient.getInsuranceID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updatePatient(Patient patient){

    }
    public static void deletePatient(int patientID){
        String query = "DELETE FROM patients WHERE patientID = ?";


    }
    public static Patient getPatient(int patientID){
        String query = "SELECT * FROM patients WHERE patientID = ?";
        Patient patient = null;

    }
    public static List<Patient> getAllPatients(){
        String query = "SELECT * FROM patients";
        List<Patient> patients = new ArrayList<>();

    }
    
    }

