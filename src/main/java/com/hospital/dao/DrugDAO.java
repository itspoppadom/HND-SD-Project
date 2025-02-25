package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;  
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.models.Drug;

public class DrugDAO {
    public static List<Drug> getAllDrugs() {
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
    public Drug getDrug(String drugID) {
        String query = "SELECT * FROM drug WHERE drugID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, drugID);

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

}