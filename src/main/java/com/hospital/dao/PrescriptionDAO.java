package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.exceptions.DatabaseException;
import com.hospital.models.Prescription;

public class PrescriptionDAO implements BaseDAO<Prescription> {

    public List<Prescription> findByPatient(String patientID) throws DatabaseException {
        return executeSearch("SELECT * FROM prescription WHERE patientID = ?", patientID);
    }

    public List<Prescription> findByDoctor(String doctorID) throws DatabaseException {
        return executeSearch("SELECT * FROM prescription WHERE doctorID = ?", doctorID);
    }
    public List<Prescription> findByDrug(String drugID) throws DatabaseException {
        return executeSearch("SELECT * FROM prescription WHERE drugID = ?", drugID);
    }
    public List<Prescription> findByDate(String date) throws DatabaseException {
        return executeSearch("SELECT * FROM prescription WHERE datePrescribed = ?", date);
    }
    public List<Prescription> findByDuration(int duration) throws DatabaseException {
        return executeSearch("SELECT * FROM prescription WHERE duration = ?", String.valueOf(duration));
    }
    public List<Prescription> findByDosage(int dosage) throws DatabaseException {
        return executeSearch("SELECT * FROM prescription WHERE dosage = ?", String.valueOf(dosage));
    }
    private List<Prescription> executeSearch(String query, String value) throws DatabaseException {
        List<Prescription> prescriptions = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Prescription prescription = new Prescription();
                prescription.setPrescriptionID(resultSet.getString("prescriptionID"));
                prescription.setDatePrescribed(resultSet.getString("datePrescribed"));
                prescription.setDosage(resultSet.getInt("dosage"));
                prescription.setDuration(resultSet.getInt("duration"));
                prescription.setComment(resultSet.getString("comment"));
                prescription.setDrugID(resultSet.getString("drugID"));
                prescription.setDoctorID(resultSet.getString("doctorID"));
                prescription.setPatientID(resultSet.getString("patientID"));
                prescriptions.add(prescription);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error searching for prescriptions: " + e.getMessage(), e);
        }
        return prescriptions;
    }
    
    @Override
    public void save( Prescription prescription) throws DatabaseException {
    String query = "INSERT INTO prescription (prescriptionID, datePrescribed, dosage, duration, comment, drugID, doctorID, patientID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    try(Connection connection = DatabaseConnection.getConnection()) {
            
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, prescription.getPrescriptionID());
            preparedStatement.setString(2, prescription.getDatePrescribed());
            preparedStatement.setInt(3, prescription.getDosage());
            preparedStatement.setInt(4, prescription.getDuration());
            preparedStatement.setString(5, prescription.getComment());
            preparedStatement.setString(6, prescription.getDrugID());
            preparedStatement.setString(7, prescription.getDoctorID());
            preparedStatement.setString(8, prescription.getPatientID());
            preparedStatement.executeUpdate();
            System.out.println("Prescription added successfully");
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                throw new DatabaseException("A prescription with ID " + prescription.getPrescriptionID() + " already exists");
            } else {
                throw new DatabaseException("Error saving prescription: " + e.getMessage(), e);
            }
        }
    }
    @Override
    public void update( Prescription prescription) {
        String query = "UPDATE prescription SET datePrescribed = ?, dosage = ?, duration = ?, comment = ?, drugID = ?, doctorID = ?, patientID = ? WHERE prescriptionID = ?";
            
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, prescription.getDatePrescribed());
            preparedStatement.setInt(2, prescription.getDosage());
            preparedStatement.setInt(3, prescription.getDuration());
            preparedStatement.setString(4, prescription.getComment());
            preparedStatement.setString(5, prescription.getDrugID());
            preparedStatement.setString(6, prescription.getDoctorID());
            preparedStatement.setString(7, prescription.getPatientID());
            preparedStatement.setString(8, prescription.getPrescriptionID());
            preparedStatement.executeUpdate();
            System.out.println("Prescription updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void delete(String... ids) {
        String query = "DELETE FROM prescription WHERE prescriptionID = ?";
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, ids[0]);
            preparedStatement.executeUpdate();
            System.out.println("Prescription deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Prescription get(String... ids) {
        String query = "SELECT * FROM prescription WHERE prescriptionID = ?";
        Prescription prescription = new Prescription();
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, ids[0]);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                prescription.setPrescriptionID(resultSet.getString("prescriptionID"));
                prescription.setDatePrescribed(resultSet.getString("datePrescribed"));
                prescription.setDosage(resultSet.getInt("dosage"));
                prescription.setDuration(resultSet.getInt("duration"));
                prescription.setComment(resultSet.getString("comment"));
                prescription.setDrugID(resultSet.getString("drugID"));
                prescription.setDoctorID(resultSet.getString("doctorID"));
                prescription.setPatientID(resultSet.getString("patientID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prescription;
    }
    @Override
    public List<Prescription> getAll() {
        List<Prescription> prescriptions = new ArrayList<>();
        String query = "SELECT * FROM prescription";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Prescription prescription = new Prescription();
                prescription.setPrescriptionID(resultSet.getString("prescriptionID"));
                prescription.setDatePrescribed(resultSet.getString("datePrescribed"));
                prescription.setDosage(resultSet.getInt("dosage"));
                prescription.setDuration(resultSet.getInt("duration"));
                prescription.setComment(resultSet.getString("comment"));
                prescription.setDrugID(resultSet.getString("drugID"));
                prescription.setDoctorID(resultSet.getString("doctorID"));
                prescription.setPatientID(resultSet.getString("patientID"));
                prescriptions.add(prescription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return prescriptions;
        }
    
}
