package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.models.Prescription;

public class PrescriptionDAO {

    public static void addNewPrescription( Prescription prescription) {
    String query = "INSERT INTO Prescription (prescriptionID, datePrescribed, dosage, duration, comment, drugID, doctorID, patientID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
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
            e.printStackTrace();
        }
    }

    public static void updatePrescription( Prescription prescription) {
        String query = "UPDATE Prescription SET datePrescribed = ?, dosage = ?, duration = ?, comment = ?, drugID = ?, doctorID = ?, patientID = ? WHERE prescriptionID = ?";
            
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
    public static Prescription getPrescription(String prescriptionID) {
        String query = "SELECT * FROM Prescription WHERE prescriptionID = ?";
        Prescription prescription = new Prescription();
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, prescriptionID);
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
    public static List<Prescription> getAllPrescriptions() {
        List<Prescription> prescriptions = new ArrayList<>();
        String query = "SELECT * FROM Prescription";
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
    public static void deletePrescription(String prescriptionID) {
        String query = "DELETE FROM Prescription WHERE prescriptionID = ?";
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, prescriptionID);
            preparedStatement.executeUpdate();
            System.out.println("Prescription deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
