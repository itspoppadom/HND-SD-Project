package com.hospital.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.models.Doctor;

public class DoctorDAO implements BaseDAO<Doctor> {
    @Override
    public void save(Doctor doctor) {
        String query = "INSERT INTO doctor (doctorID, firstname, surname, address, email, specialization, hospital) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, doctor.getDoctorID());
            stmt.setString(2, doctor.getFirstName());
            stmt.setString(3, doctor.getLastName());
            stmt.setString(4, doctor.getAddress());
            stmt.setString(5, doctor.getEmail());
            stmt.setString(6, doctor.getSpecialization());
            stmt.setString(7, doctor.getHospital());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update(Doctor doctor) {
        String query = "UPDATE doctor SET firstname = ?, surname = ?, address = ?, email = ?, specialization = ?, hospital = ? WHERE doctorID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, doctor.getFirstName());
            stmt.setString(2, doctor.getLastName());
            stmt.setString(3, doctor.getAddress());
            stmt.setString(4, doctor.getEmail());
            stmt.setString(5, doctor.getSpecialization());
            stmt.setString(6, doctor.getHospital());
            stmt.setString(7, doctor.getDoctorID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void delete(String... ids) {
        String query = "DELETE FROM doctor WHERE doctorID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ids[0]);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Doctor get(String... ids) {
        String query = "SELECT * FROM doctor WHERE doctorID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ids[0]);

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Doctor> getAll() {
        String query = "SELECT * FROM doctor";
        List<Doctor> doctors = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                doctors.add(new Doctor(
                        rs.getString("firstname"),
                        rs.getString("surname"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("doctorID"),
                        rs.getString("specialization"),
                        rs.getString("hospital")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctors;
    }
}