package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.exceptions.DatabaseException;
import com.hospital.models.Doctor;
import com.hospital.models.InsuranceCom;
import com.hospital.models.Patient;


public class PatientDAO implements BaseDAO<Patient> {


    // Functions for searching the database by different parameters of the table
    // All functions use the LIKE operator to search for partial matches
    public List<Patient> findByFirstName(String firstName) {
        return executeSearch("SELECT * FROM patient WHERE firstname LIKE ?", firstName);
    }
    public List<Patient> findByLastName(String lastName) {
        return executeSearch("SELECT * FROM patient WHERE surname LIKE ?", lastName);
    }
    public List<Patient> findByPostcode(String postcode) {
        return executeSearch("SELECT * FROM patient WHERE postcode LIKE ?", postcode);
    }
    public List<Patient> findByAddress(String address) {
        return executeSearch("SELECT * FROM patient WHERE address LIKE ?", address);
    }
    public List<Patient> findByPhoneNumber(String phoneNumber) {
        return executeSearch("SELECT * FROM patient WHERE phone LIKE ?", phoneNumber);
    }
    public List<Patient> findByEmail(String email) {
        return executeSearch("SELECT * FROM patient WHERE email LIKE ?", email);
    }
    public List<Patient> findByInsuranceID(String insuranceID) {
        return executeSearch("SELECT * FROM patient WHERE insuranceID LIKE ?", insuranceID);
    }

    // Function to execute the search query
    private List<Patient> executeSearch(String query, String value) {
        List<Patient> patients = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + value + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setPatientID(resultSet.getString("patientID"));
                patient.setFirstName(resultSet.getString("firstname"));
                patient.setLastName(resultSet.getString("surname"));
                patient.setPostcode(resultSet.getString("postcode"));
                patient.setAddress(resultSet.getString("address"));
                patient.setPhoneNumber(resultSet.getString("phone"));
                patient.setEmail(resultSet.getString("email"));
                patient.setInsuranceID(resultSet.getString("insuranceID"));
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }
    // 
    
    // Function to save a new record in the table
    // Throws an exception if a record with the same ID already exists
    @Override
    public void save(Patient patient) throws DatabaseException {
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
            if (e.getMessage().contains("Duplicate entry")) {
                throw new DatabaseException("A Patient with ID " + patient.getPatientID() + " already exists");
            } else {
                throw new DatabaseException("Error saving Patient: " + e.getMessage(), e);
            }
            
        }
    }

    // Function to update a record in the table
    @Override
    public void update(Patient patient){
        String query = "UPDATE patient SET firstname = ?, surname = ?, postcode = ?, address = ?, phone = ?, email = ?, insuranceID = ?, patientID = ? WHERE patientID = ?";

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
            stmt.setString(9, patient.getPatientID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Function to delete a record from the table
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

    // Function to get a record from the table
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

    // Function to get all records from the table
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

    public Doctor getPrimaryDoctor(String patientID) throws DatabaseException {
        String query = """
            SELECT d.*, v.dateOfVisit 
            FROM doctor d
            INNER JOIN visit v ON d.doctorID = v.doctorID
            WHERE v.patientID = ?
            ORDER BY v.dateOfVisit DESC
            LIMIT 1
            """;
            
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, patientID);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Doctor(
                    rs.getString("firstname"),
                        rs.getString("surname"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("doctorID"),
                        rs.getString("specialization"),
                        rs.getString("hospital")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Error getting primary doctor: " + e.getMessage());
        }
    }

    // Function to get the company information of the patient
    public  InsuranceCom getInsuranceComInfo(String insuranceID) throws DatabaseException {
        String query = "SELECT * FROM insurance WHERE insuranceID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, insuranceID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new InsuranceCom(
                    rs.getString("insuranceID"),
                    rs.getString("company"),
                    rs.getString("address"),
                    rs.getString("phone")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Error getting insurance company information: " + e.getMessage());
        }
        
    }
}

