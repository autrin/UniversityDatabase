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
        stmt.executeUpdate(sql);
    }

    public void createDepartmentTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE departments ("
                + "dcode INTEGER PRIMARY KEY,"
                + "dname VARCHAR(50) UNIQUE NOT NULL,"
                + "phone VARCHAR(10) NOT NULL,"
                + "college VARCHAR(20) NOT NULL)";
        stmt.executeUpdate(sql);
    }

    public static void main(String[] args) {
        // Open a connection
        System.out.println("Starting database setup process...");
        Connection conn = null;
        Statement stmt = null;

        try {
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);
            System.out.println("Connection established successfully.");

            // Create tables
            System.out.println("Creating student table...");
            stmt = conn.createStatement();
            CreateTables tableCreator = new CreateTables();
            tableCreator.createStudentTable(stmt);

            System.out.println("Creating department table...");
            tableCreator.createDepartmentTable(stmt);

            System.out.println("Department table created successfully.");

            // Display success message
            System.out.println("Database setup completed successfully.");
        } catch (SQLException e) {
            System.out.println("Database operation failed:");
            System.out.println("Error: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        } finally {
            // Close resources
            System.out.println("Closing database resources...");
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
                System.out.println("Resources closed successfully.");
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
