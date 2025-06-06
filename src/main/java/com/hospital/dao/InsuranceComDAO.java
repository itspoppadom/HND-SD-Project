package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.exceptions.DatabaseException;
import com.hospital.models.InsuranceCom;

public class InsuranceComDAO implements BaseDAO<InsuranceCom> {

    // Functions for searching the database by different parameters of the table
    // All functions use the LIKE operator to search for partial matches

    public List<InsuranceCom> findByCompany(String company) {
        return executeSearch("SELECT * FROM insurance WHERE company LIKE ?", company);
    }
    public List<InsuranceCom> findByAddress(String address) {
        return executeSearch("SELECT * FROM insurance WHERE address LIKE ?", address);
    }
    public List<InsuranceCom> findByPhone(String phone) {
        return executeSearch("SELECT * FROM insurance WHERE phone LIKE ?", phone);
    }

    // Function to execute the search query
    private List<InsuranceCom> executeSearch(String query, String value) {
        List<InsuranceCom> insuranceComs = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + value + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                InsuranceCom insuranceCom = new InsuranceCom();
                insuranceCom.setInsuranceID(resultSet.getString("insuranceID"));
                insuranceCom.setCompanyName(resultSet.getString("company"));
                insuranceCom.setAddress(resultSet.getString("address"));
                insuranceCom.setPhone(resultSet.getString("phone"));
                insuranceComs.add(insuranceCom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insuranceComs;
    }

    // Function to get all the records from the table
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

    // Function to get a single record from the table by the Primary Key (ID)
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


    // Add new Insurance company to the database
    @Override
    public void save(InsuranceCom insuranceCom) throws DatabaseException {
        String query = "INSERT INTO insurance (insuranceID, company, address, phone) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, insuranceCom.getInsuranceID());
            stmt.setString(2, insuranceCom.getCompanyName());
            stmt.setString(3, insuranceCom.getAddress());
            stmt.setString(4, insuranceCom.getPhone());

            stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                throw new DatabaseException("An Insurance Company with ID " + insuranceCom.getInsuranceID() + " already exists");
            } else {
                throw new DatabaseException("Error saving Insurance Company: " + e.getMessage(), e);
            }
        }
    }

    // Function to delete an Insurance Company from the database
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

    // Function to update an Insurance Company in the database
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