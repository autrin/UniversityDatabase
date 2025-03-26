package university_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Index {

	public void indexedQuery3(Statement stmt) throws SQLException {
		// Run the query: the names and levels of degrees that have more male
		// students than female students
		// (major or minor)
		String querySql = "WITH all_degrees AS ( " + "SELECT sid, name, level FROM major " + "UNION ALL "
				+ "SELECT sid, name, level FROM minor " + ") " + "SELECT ad.name, ad.level, "
				+ "SUM(CASE WHEN s.gender = 'M' THEN 1 ELSE 0 END) AS male_count, "
				+ "SUM(CASE WHEN s.gender = 'F' THEN 1 ELSE 0 END) AS female_count " + "FROM all_degrees ad "
				+ "JOIN students s ON ad.sid = s.sid " + "GROUP BY ad.name, ad.level "
				+ "HAVING SUM(CASE WHEN s.gender = 'M' THEN 1 ELSE 0 END) > "
				+ "SUM(CASE WHEN s.gender = 'F' THEN 1 ELSE 0 END)";

		ResultSet rsQuery = stmt.executeQuery(querySql);
		while (rsQuery.next()) {
			String name = rsQuery.getString("name");
			String level = rsQuery.getString("level");
			int maleCount = rsQuery.getInt("male_count");
			int femaleCount = rsQuery.getInt("female_count");
			System.out.println("Degree: " + name + ", Level: " + level + ", Male Count: " + maleCount
					+ ", Female Count: " + femaleCount);
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

			// Time the execution of the non-indexed query (from Query.java)
			long startTime = System.nanoTime();
			Query q = new Query();
			q.query3(stmt);
			long endTime = System.nanoTime();
			long duration = (endTime - startTime);
			System.out.println("Query 3 execution time: " + duration + " nanoseconds");

			// Now run the indexed query
			System.out.println("Running indexed query 3...");
			Index index = new Index();
			// Check if index exists and drop if needed
			String checkIndex = "SELECT COUNT(*) FROM information_schema.statistics "
			        + "WHERE table_schema = 'university1' AND table_name = 'students' AND index_name = 'idx_gender'";
			ResultSet rs = stmt.executeQuery(checkIndex);
			boolean indexExists = false;
			if (rs.next()) {
			    indexExists = rs.getInt(1) > 0;
			}
			rs.close();
			if (!indexExists) {
			    stmt.executeUpdate("CREATE INDEX idx_gender ON students(gender)");
			    System.out.println("Index idx_gender created.");
			} else {
			    System.out.println("Index idx_gender already exists.");
			}

			// Now run the indexed query several times to measure its performance
			long indexedStartTime = System.nanoTime();
			index.indexedQuery3(stmt); // Query uses the already-created index
			long indexedEndTime = System.nanoTime();
			long indexedDuration = (indexedEndTime - indexedStartTime);
			System.out.println("Indexed query 3 execution time: " + indexedDuration + " nanoseconds");

		} catch (SQLException e) {
			System.out.println("Database operation failed:");
			System.out.println("Error: " + e.getMessage());
			System.out.println("SQL State: " + e.getSQLState());
			System.out.println("Error Code: " + e.getErrorCode());
			e.printStackTrace();
		} finally {
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
