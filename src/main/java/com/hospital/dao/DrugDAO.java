package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;  
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.models.Drug;

public class DrugDAO implements BaseDAO<Drug> {
    @Override
    public List<Drug> getAll() {
        List<Drug> drugs = new ArrayList<>();
        String query = "SELECT * FROM drug";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Drug drug = new Drug();
                drug.setDrugID(rs.getString("drugID"));
                drug.setDrugName(rs.getString("drugname"));
                drug.setSideEffects(rs.getString("sideEffects"));
                drug.setBenefits(rs.getString("benefits"));

                drugs.add(drug);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drugs;
    }
    @Override
    public Drug get(String... ids) {
        String query = "SELECT * FROM drug WHERE drugID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ids[0]);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Drug drug = new Drug();
                drug.setDrugID(rs.getString("drugID"));
                drug.setDrugName(rs.getString("drugname"));
                drug.setSideEffects(rs.getString("sideEffects"));
                drug.setBenefits(rs.getString("benefits"));

                return drug;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
    @Override
    // Delete Drug from Database
    public void delete(String... ids) {
        String query = "DELETE FROM drug WHERE drugID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ids[0]);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void save(Drug drug) {
        String query = "INSERT INTO drug (drugID, drugname, sideEffects, benefits) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, drug.getDrugID());
            stmt.setString(2, drug.getDrugName());
            stmt.setString(3, drug.getSideEffects());
            stmt.setString(4, drug.getBenefits());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update(Drug drug) {
        String query = "UPDATE drug SET drugname = ?, sideEffects = ?, benefits = ? WHERE drugID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, drug.getDrugName());
            stmt.setString(2, drug.getSideEffects());
            stmt.setString(3, drug.getBenefits());
            stmt.setString(4, drug.getDrugID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}