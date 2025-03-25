package university_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Query {

	public void query1(Statement stmt) throws SQLException {
		/*
		 * The numbers and names of courses and their corresponding average grades from
		 * students registered in the past semesters
		 */
		String sql = "SELECT c.cnumber, c.cname, AVG(r.grade)" + "FROM courses c"
				+ "Join register r on c.cnumber = r.cnumber" + "GROUP BY c.cnumber, c.cname";
		stmt.execute(sql);
	}

	public static void main(String[] args) {
		System.out.println("Starting Query process...");
		Connection conn = null;
		Statement stmt = null;
		Query query = new Query();

		try {
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);
			stmt = conn.createStatement();
			System.out.println("Connection established.");

			System.out.println("Query 1:");
			query.query1(stmt);
			System.out.println("Query 1 completed.");

		} catch (SQLException e) {
			System.out.println("Database operation failed:");
			System.out.println("Error: " + e.getMessage());
			System.out.println("SQL State: " + e.getSQLState());
			System.out.println("Error Code: " + e.getErrorCode());
			e.printStackTrace();
		} finally {
			// Close recourses
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
