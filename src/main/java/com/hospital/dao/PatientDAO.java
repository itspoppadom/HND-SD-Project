package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.models.Patient;


public class PatientDAO implements BaseDAO<Patient> {
    
    @Override
    public void save(Patient patient){
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
    @Override
    public void update(Patient patient){
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
    @Override
    public void delete(String... ids){
        String query = "DELETE FROM patient WHERE patientID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ids[0]);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        


    }
    @Override
    public Patient get(String... ids) {
        String query = "SELECT * FROM patient WHERE patientID = ?";
        Patient patient = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ids[0]);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                patient = new Patient(
                    rs.getString("firstname"),    // Match getAll() order
                    rs.getString("surname"),
                    rs.getString("address"),
                    rs.getString("email"),
                    rs.getString("patientID"),
                    rs.getString("postcode"),
                    rs.getString("phone"),
                    rs.getString("insuranceID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }
    @Override
    public List<Patient> getAll() {
        String query = "SELECT * FROM patient ORDER BY patientID";  // Added ORDER BY for consistent display
        List<Patient> patients = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Patient patient = new Patient(
                    rs.getString("firstname"),    // First name
                    rs.getString("surname"),      // Last name
                    rs.getString("address"),      // Address
                    rs.getString("email"),        // Email
                    rs.getString("patientID"),    // Patient ID
                    rs.getString("postcode"),     // Postcode
                    rs.getString("phone"),        // Phone
                    rs.getString("insuranceID")   // Insurance ID
                );
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patients;
    }
}

