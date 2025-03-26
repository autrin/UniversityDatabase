package university_database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Index {

	public void indexedQuery3(Statement stmt) throws SQLException {
		/*
		 * Create an index on gender in query 3:
		 * The names and levels of degrees that have more male students than female
		 * students (major or minor)
		 */
		String sql = "CREATE INDEX idx_gender "
				+ "ON students(gender) "
				+ "WITH all_degrees AS (" + "SELECT sid, name, level FROM major " + "UNION ALL "
				+ "SELECT sid, name, level FROM minor " + ") " + "SELECT ad.name, ad.level, "
				+ "SUM(CASE WHEN s.gender = 'M' THEN 1 ELSE 0 END) AS male_count, "
				+ "SUM(CASE WHEN s.gender = 'F' THEN 1 ELSE 0 END) AS female_count " + "FROM all_degrees ad "
				+ "JOIN students s ON ad.sid = s.sid " + "GROUP BY ad.name, ad.level "
				+ "HAVING SUM(CASE WHEN s.gender = 'M' THEN 1 ELSE 0 END) > SUM(CASE WHEN s.gender = 'F' THEN 1 ELSE 0 END);";

		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			String name = rs.getString("name");
			String level = rs.getString("level");
			System.out.println("Name: " + name + ", Level: " + level);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Starting database setup process...");
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);
			stmt = conn.createStatement();
			System.out.println("Connection established.");

			/*
			 * Execute the query 3) (names and levels of degrees that have more male than
			 * female students) and take a screenshot of the query execution time
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
		} finally {
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
