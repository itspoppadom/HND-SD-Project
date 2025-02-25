package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.models.InsuranceCom;

public class InsuranceComDAO implements BaseDAO<InsuranceCom> {
    @Override
    public List<InsuranceCom> getAll() {
        List<InsuranceCom> insuranceComs = new ArrayList<>();
        String query = "SELECT * FROM insurance";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                InsuranceCom insuranceCom = new InsuranceCom();
                insuranceCom.setInsuranceID(rs.getString("insuranceID"));
                insuranceCom.setCompanyName(rs.getString("company"));
                insuranceCom.setAddress(rs.getString("address"));
                insuranceCom.setPhone(rs.getString("phone"));

                insuranceComs.add(insuranceCom);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return insuranceComs;
    
}
    @Override
    public InsuranceCom get(String... ids) {
        String query = "SELECT * FROM insurance WHERE insuranceID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ids[0]);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                InsuranceCom insuranceCom = new InsuranceCom();
                insuranceCom.setInsuranceID(rs.getString("insuranceID"));
                insuranceCom.setCompanyName(rs.getString("company"));
                insuranceCom.setAddress(rs.getString("address"));
                insuranceCom.setPhone(rs.getString("phone"));

                return insuranceCom;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
    // Add new Insurance com
    @Override
    public void save(InsuranceCom insuranceCom) {
        String query = "INSERT INTO insurance (insuranceID, company, address, phone) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, insuranceCom.getInsuranceID());
            stmt.setString(2, insuranceCom.getCompanyName());
            stmt.setString(3, insuranceCom.getAddress());
            stmt.setString(4, insuranceCom.getPhone());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void delete(String... ids) {
        String query = "DELETE FROM insurance WHERE insuranceID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ids[0]);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update(InsuranceCom insuranceCom){
        String query = "UPDATE insurance SET company = ?, address = ?, phone = ? WHERE insuranceID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, insuranceCom.getCompanyName());
            stmt.setString(2, insuranceCom.getAddress());
            stmt.setString(3, insuranceCom.getPhone());
            stmt.setString(4, insuranceCom.getInsuranceID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}