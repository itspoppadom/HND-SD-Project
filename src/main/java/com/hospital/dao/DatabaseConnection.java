package com.hospital.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
private static final String URL = "jdbc:mysql://localhost:3306/assesment_hospital";
private static final String USER = "root";
private static final String PASSWORD = "root";

public static Connection getConnection() {
    Connection con = null;
    try {
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
}
}
