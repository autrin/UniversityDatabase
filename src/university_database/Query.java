package university_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Query {

	public void query1(Statement stmt) throws SQLException {
		/*
		 * The numbers and names of courses and their corresponding average grades from
		 * students registered in the past semesters
		 */
		String sql = "SELECT c.cnumber, c.cname, AVG(r.grade) AS avg_grade " + "FROM courses c "
				+ "JOIN register r ON c.cnumber = r.course_number " + "GROUP BY c.cnumber, c.cname";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			int cnumber = rs.getInt("cnumber");
			String cname = rs.getString("cname");
			double avgGrade = rs.getDouble("avg_grade");

			System.out.println("Course #: " + cnumber + ", Name: " + cname + ", Avg Grade: " + avgGrade);
		}
	}

	public void query2(Statement stmt) throws SQLException {
		/*
		 * The count of female students who major or minor in a degree managed by LAS
		 * departments
		 * 
		 */
		String sql = "WITH f_students AS (" +
					"SELECT DISTINCT s.sid " +
					"FROM students s " +
					"WHERE s.gender = 'F')," +
					"all_degrees AS (" +
					"SELECT sid, name, level " +
					"FROM major " +
					"UNION " +
					"SELECT sid, name, level " +
					"FROM minor) " +
					"SELECT COUNT(DISTINCT f.sid) AS f_count " +
					"FROM f_students f " +
					"JOIN all_degrees ad on f.sid = ad.sid " +
					"JOIN degrees dg on (ad.name = dg.dgname AND ad.level = dg.level) " +
					"JOIN departments dp on dg.department_code = dp.dcode " +
					"WHERE dp.college = 'LAS'";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			int f_count = rs.getInt("f_count");
			System.out.println(f_count);
		}
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

			System.out.println("Query 2:");
			query.query2(stmt);
			System.out.println("Query 2 completed.");
			
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
