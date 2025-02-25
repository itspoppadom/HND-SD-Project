package com.hospital.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.models.Visit;


public class VisitDAO {
    
    public static List<Visit> getAllVisits() {
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
}


