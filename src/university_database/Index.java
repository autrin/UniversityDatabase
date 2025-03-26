package university_database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Index {

	public static void main(String[] args) {
		System.out.println("Starting database setup process...");
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);
			stmt = conn.createStatement();
			System.out.println("Connection established.");
			
			/*
			 * Execute the query 3) (names and levels of degrees that have more male than female students) and take a screenshot of the query execution time 
			 */
			long startTime = System.nanoTime();
			Query q = new Query();
			q.query3(stmt);
			long endTime = System.nanoTime();
			long duration = (endTime - startTime);
			System.out.println("Query 3 execution time: " + duration + " nanoseconds");
		} catch (SQLException e) {
			System.out.println("Database operation failed:");
			System.out.println("Error: " + e.getMessage());
			System.out.println("SQL State: " + e.getSQLState());
			System.out.println("Error Code: " + e.getErrorCode());
			e.printStackTrace();
		}
		finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error closing resources.");
			}
		}
	}

}
