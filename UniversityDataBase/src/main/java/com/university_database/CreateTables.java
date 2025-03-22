package com.university_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTables {

    public void createStudentTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE students("
                + "sid INTEGER UNIQUE NOT NULL,"
                + "ssn INTEGER PRIMARY KEY,"
                + "name VARCHAR(20) NOT NULL,"
                + "gender VARCHAR(1) NOT NULL,"
                + "dob VARCHAR(10) NOT NULL,"
                + "p_addr VARCHAR(20) NOT NULL,"
                + "p_phone VARCHAR(20) NOT NULL,"
                + "c_addr VARCHAR(20) NOT NULL,"
                + "c_phone VARCHAR(20) NOT NULL)";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createDepartmentTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE departments ("
                + "dcode INTEGER PRIMARY KEY,"
                + "dname VARCHAR(50) UNIQUE NOT NULL,"
                + "phone VARCHAR(10) NOT NULL,"
                + "college VARCHAR(20) NOT NULL)";
        try {
            
        } catch (SQLException e) {
            
        }
    }

    public static void main(String[] args) {
        // Open a connection
        CreateTables tables = new CreateTables();
        try (
                Connection conn = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS); Statement stmt = conn.createStatement();) {
            tables.createStudentTable(stmt);
            System.out.println("Created student table in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
