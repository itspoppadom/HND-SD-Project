package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.models.Patient;


public class PatientDAO {
    public static void addPatient(Patient patient){
        String query = "INSERT INTO patient (patientID, firstname, surname,postcode ,address, phone, email ,insuranceID) VALUES (?, ?, ?, ?, ? , ? , ? , ? )";

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
        String query = "UPDATE patient SET firstname = ?, surname = ?, postcode = ?, address = ?, phone = ?, email = ?, insuranceID = ? WHERE patientID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, patient.getFirstName());
            stmt.setString(2, patient.getLastName());
            stmt.setString(3, patient.getPostcode());
            stmt.setString(4, patient.getAddress());
            stmt.setString(5, patient.getPhoneNumber());
            stmt.setString(6, patient.getEmail());
            stmt.setString(7, patient.getInsuranceID());
            stmt.setString(8, patient.getPatientID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deletePatient(int patientID){
        String query = "DELETE FROM patient WHERE patientID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, patientID);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        


    }
    public static Patient getPatient(int patientID){
        String query = "SELECT * FROM patient WHERE patientID = ?";
        Patient patient = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, patientID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                patient = new Patient(
                        rs.getString("patientID"),
                        rs.getString("firstname"),
                        rs.getString("surname"),
                        rs.getString("postcode"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("insuranceID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }
    public static List<Patient> getAllPatients() {

        String query = "SELECT * FROM patient";
        List<Patient> patients = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                patients.add(new Patient(
                        rs.getString("patientID"),
                        rs.getString("firstname"),
                        rs.getString("surname"),
                        rs.getString("postcode"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("insuranceID")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patients;
    }
}

