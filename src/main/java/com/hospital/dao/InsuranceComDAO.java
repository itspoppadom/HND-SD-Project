package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.models.InsuranceCom;

public class InsuranceComDAO {
    
    public static List<InsuranceCom> getAllInsuranceCom() {
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

    public InsuranceCom getInsuranceCom(String insuranceID) {
        String query = "SELECT * FROM insurancecom WHERE insuranceID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, insuranceID);

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
}