package university_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DropTables {
	// delete all tables

	public void dropStudentTable(Statement stmt) throws SQLException {
		String sqlDrop = "DROP TABLE IF EXISTS register, minor, major, courses, degrees, departments, students; ";
		try {
			stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
			stmt.execute(sqlDrop);
			System.out.println("Tables dropped successfully.");
			stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
		} catch (SQLException e) {
			System.out.println("Error in dropStudentTable(): " + e.getMessage());
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

			System.out.println("Dropping tables...");
			DropTables dropTables = new DropTables();
			dropTables.dropStudentTable(stmt);

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
