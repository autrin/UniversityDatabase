package university_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DropTables {

	public void dropAllTables(Statement stmt) throws SQLException {
		String sqlDrop = "DROP TABLE IF EXISTS register, minor, major, courses, degrees, departments, students";
		try {
			stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
			stmt.execute(sqlDrop);
			System.out.println("All tables dropped successfully.");
			stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
		} catch (SQLException e) {
			System.out.println("Error dropping tables: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		System.out.println("Dropping tables...");

		try (Connection conn = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);
				Statement stmt = conn.createStatement();) {
			DropTables dropTables = new DropTables();
			dropTables.dropAllTables(stmt);
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}