package com.hospital.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.exceptions.DatabaseException;
import com.hospital.models.Visit;


public class VisitDAO implements BaseDAO<Visit> {

     //Search for Visit by Patient, Doctor, Date, Diagnosis, or Symptoms
        // Return a list of visits that match the search parameter
        // The Search uses a global prefix to allow for partial matches
    public List<Visit> findByPatient(String patientID) throws DatabaseException {
        String query = "SELECT * FROM visit WHERE patientID LIKE ?";
        return executeSearch(query, patientID);
    }

    public List<Visit> findByDoctor(String doctorID) throws DatabaseException {
        String query = "SELECT * FROM visit WHERE doctorID LIKE ?";
        return executeSearch(query, doctorID);
    }

    public List<Visit> findByDate(String date) throws DatabaseException {
        String query = "SELECT * FROM visit WHERE dateOfVisit LIKE ?";
        return executeSearch(query, date);
    }
    public List<Visit> findByDiagnosis(String diagnosisID) throws DatabaseException {
        String query = "SELECT * FROM visit WHERE diagnosisID LIKE ?";
        return executeSearch(query, diagnosisID);
    }
    public List<Visit> findBySymptoms(String symptoms) throws DatabaseException {
        String query = "SELECT * FROM visit WHERE symptoms LIKE ?";
        return executeSearch(query, symptoms);
    }
    
    // Function to execute the search query
    private List<Visit> executeSearch(String query, String param) throws DatabaseException {
        List<Visit> visits = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, "%" +param + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                visits.add(new Visit(
                    rs.getString("patientID"),
                    rs.getString("doctorID"),
                    rs.getString("dateOfVisit"),
                    rs.getString("symptoms"),
                    rs.getString("diagnosisID")
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error searching visits: " + e.getMessage());
        }
        return visits;
    }

    // Abstract function getAll() to get all visits from the database
    // Return a list of all visits
    @Override
    public List<Visit> getAll() {
        List<Visit> visits = new ArrayList<>();
        String query = "SELECT * FROM visit";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Visit visit = new Visit();
                visit.setPatientID(rs.getString("patientID"));
                visit.setDoctorID(rs.getString("doctorID"));
                visit.setDateOfVisit(rs.getString("dateOfVisit"));
                visit.setSymptoms(rs.getString("symptoms"));
                visit.setDiagnosisID(rs.getString("diagnosisID"));

                visits.add(visit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visits;
    }
    // Function to get a visit by the combination of primary keys
    // patientID, doctorID, and dateOfVisit
    // Return the visit object
    @Override
    public Visit get(String... ids) throws DatabaseException {
        if (ids.length < 3) {
            throw new DatabaseException("Visit requires patientID, doctorID, and dateOfVisit");
        }

        String query = "SELECT * FROM visit WHERE patientID = ? AND doctorID = ? AND dateOfVisit = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, ids[0]); // patientID
            stmt.setString(2, ids[1]); // doctorID
            stmt.setString(3, ids[2]); // dateOfVisit
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Visit(
                    rs.getString("patientID"),
                    rs.getString("doctorID"),
                    rs.getString("dateOfVisit"),
                    rs.getString("symptoms"),
                    rs.getString("diagnosisID")
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving visit: " + e.getMessage());
        }
        return null;
    }

    // Function to save a visit to the database
    // Return nothing
    @Override   
    public void save(Visit visit) {
        String query = "INSERT INTO visit (patientID, doctorID, dateOfVisit, symptoms, diagnosisID) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, visit.getPatientID());
            stmt.setString(2, visit.getDoctorID());
            stmt.setString(3, visit.getDateOfVisit());
            stmt.setString(4, visit.getSymptoms());
            stmt.setString(5, visit.getDiagnosisID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Function to delete a visit from the database
    // Return nothing
    @Override  
    public void delete(String... ids) {
        if (ids.length != 3) {
            throw new IllegalArgumentException("Visit deletion requires patientID, doctorID, and dateOfVisit");
        }
        
        String patientID = ids[0];
        String doctorID = ids[1];
        String dateOfVisit = ids[2];
        
        String query = "DELETE FROM visit WHERE patientID = ? AND doctorID = ? AND dateOfVisit = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, patientID);
            stmt.setString(2, doctorID);
            stmt.setString(3, dateOfVisit);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function to update a visit in the database
    // Return nothing
    @Override
    public void update(Visit visit) {
        String query = "UPDATE visit SET symptoms = ?, diagnosisID = ? WHERE patientID = ? AND doctorID = ? AND dateOfVisit = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, visit.getSymptoms());
            stmt.setString(2, visit.getDiagnosisID());
            stmt.setString(3, visit.getPatientID());
            stmt.setString(4, visit.getDoctorID());
            stmt.setString(5, visit.getDateOfVisit());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
       
}


