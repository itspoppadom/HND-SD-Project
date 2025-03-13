package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;  
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.models.Drug;

public class DrugDAO implements BaseDAO<Drug> {

    public List<Drug> findByDrugName(String name) {
        return executeSearch("SELECT * FROM drug WHERE drugname LIKE ?", name);
    }
    public List<Drug> findBySideEffects(String sideEffects) {
        return executeSearch("SELECT * FROM drug WHERE sideEffects LIKE ?", sideEffects);
    }
    public List<Drug> findByBenefits(String benefits) {
        return executeSearch("SELECT * FROM drug WHERE benefits LIKE ?", benefits);
    }
    private List<Drug> executeSearch(String query, String value) {
        List<Drug> drugs = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + value + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Drug drug = new Drug();
                drug.setDrugID(resultSet.getString("drugID"));
                drug.setDrugName(resultSet.getString("drugname"));
                drug.setSideEffects(resultSet.getString("sideEffects"));
                drug.setBenefits(resultSet.getString("benefits"));
                drugs.add(drug);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drugs;
        }
    


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