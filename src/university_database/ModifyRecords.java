package university_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ModifyRecords {
	
	public void modifyRecord1(Statement stmt) throws SQLException {
		/*
		 * Change the name of the student with ssn = 144673371 to Scott
		 */
		String sql = "UPDATE students SET name = 'Scott' WHERE ssn = 144673371";
		try {
			stmt.execute(sql);
			System.out.println("1st record modification done successfully.");
		} catch (SQLException e) {
			System.out.println("Error in modifyRecord1(): " + e.getMessage());
		}
	}
	
	public void modifyRecords2(Statement stmt) throws SQLException {
		/*
		 * Change the major of the student with ssn = 144673371 to Computer Science, Master. 
		 */
		String sql = "UPDATE major SET name = 'Computer Science', level = 'MS' "
				+ "WHERE (SELECT sid FROM students WHERE ssn = 144673371)";
		try {
			stmt.execute(sql);
			System.out.println("2nd record modification done successfully.");
		} catch (SQLException e) {
			System.out.println("Error in modifyRecord2(): " + e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Starting database setup process...");

		Connection conn = null;
		Statement stmt = null;

		try {
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);
			System.out.println("Connection established successfully.");

			stmt = conn.createStatement();
			System.out.println("Statement created successfully.");
			
			System.out.println("Modifying records...");
			ModifyRecords modifyRecords = new ModifyRecords();
			System.out.println("Modifying record #1 ...");
			modifyRecords.modifyRecord1(stmt);
			
			System.out.println("Modifying record #2 ...");
			modifyRecords.modifyRecords2(stmt);
			

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
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
				System.out.println("Resources closed successfully.");
			} catch (SQLException e) {
				System.out.println("Error closing resources: " + e.getMessage());
			}
		}

	}

}
