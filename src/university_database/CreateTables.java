package university_database;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;

import java.sql.Statement;

public class CreateTables {

	public void createStudentTable(Statement stmt) throws SQLException {
		String dropSql = "DROP TABLE IF EXISTS students";
		stmt.execute(dropSql);

		String sql = "CREATE TABLE students (" + "sid INTEGER UNIQUE NOT NULL," + "ssn INTEGER PRIMARY KEY,"
				+ "name VARCHAR(20) NOT NULL," + "gender VARCHAR(1) NOT NULL," + "dob VARCHAR(10) NOT NULL,"
				+ "p_addr VARCHAR(20) NOT NULL," + "p_phone VARCHAR(20) NOT NULL," + "c_addr VARCHAR(20) NOT NULL,"
				+ "c_phone VARCHAR(20) NOT NULL" + ")";

		stmt.execute(sql);
	}

	public void createDepartmentTable(Statement stmt) throws SQLException {
		// Drop the table if it exists
		String dropSql = "DROP TABLE IF EXISTS departments";
		stmt.execute(dropSql);

		String sql = "CREATE TABLE departments (" + "dcode INTEGER PRIMARY KEY," + "dname VARCHAR(50) UNIQUE NOT NULL,"
				+ "phone VARCHAR(10) NOT NULL," + "college VARCHAR(20) NOT NULL)";

		stmt.execute(sql);

	}

	public void createDegreesTable(Statement stmt) throws SQLException {
		// Drop the table if it exists
		String dropSql = "DROP TABLE IF EXISTS degrees";
		stmt.execute(dropSql);

		String sql = "CREATE TABLE degrees (" + "dgname VARCHAR(50) NOT NULL," + "level VARCHAR(5) NOT NULL,"
				+ "department_code INTEGER NOT NULL," + "PRIMARY KEY (dgname, level),"
				+ "FOREIGN KEY (department_code) REFERENCES departments(dcode) ON DELETE CASCADE)";

		stmt.execute(sql);

	}

	public void createCoursesTable(Statement stmt) throws SQLException {
		// Drop the table if it exists
		String dropSql = "DROP TABLE IF EXISTS courses";
		stmt.execute(dropSql);

		String sql = "CREATE TABLE courses (" + "cnumber INTEGER PRIMARY KEY," + "cname VARCHAR(50) NOT NULL,"
				+ "description VARCHAR(50)," + "credithours INTEGER NOT NULL," + "level VARCHAR(20) NOT NULL,"
				+ "department_code INTEGER NOT NULL,"
				+ "FOREIGN KEY (department_code) REFERENCES departments(dcode) ON DELETE CASCADE)";

		stmt.execute(sql);
	}

	public void createRegisterTable(Statement stmt) throws SQLException {
		// Drop the table if it exists
		String dropSql = "DROP TABLE IF EXISTS register";
		stmt.execute(dropSql);

		String sql = "CREATE TABLE register (" + "sid INTEGER NOT NULL," + "course_number INTEGER NOT NULL,"
				+ "regtime VARCHAR(20) NOT NULL," + "grade INTEGER," + "PRIMARY KEY (sid, course_number),"
				+ "FOREIGN KEY (sid) REFERENCES students(sid) ON DELETE CASCADE,"
				+ "FOREIGN KEY (course_number) REFERENCES courses(cnumber) ON DELETE CASCADE" + ")";

		stmt.execute(sql);
	}

	public void createMajorTable(Statement stmt) throws SQLException {
		// Drop the table if it exists
		String dropSql = "DROP TABLE IF EXISTS major";
		stmt.execute(dropSql);

		String sql = "CREATE TABLE major (" + "sid INTEGER NOT NULL," + "name VARCHAR(50) NOT NULL,"
				+ "level VARCHAR(5) NOT NULL," + "PRIMARY KEY (sid, name, level),"
				+ "FOREIGN KEY (sid) REFERENCES students(sid) ON DELETE CASCADE,"
				+ "FOREIGN KEY (name, level) REFERENCES degrees(dgname, level) ON DELETE CASCADE" + ")";

		stmt.execute(sql);
	}

	public void createMinorTable(Statement stmt) throws SQLException {
		// Drop the table if it exists
		String dropSql = "DROP TABLE IF EXISTS minor";
		stmt.execute(dropSql);

		String sql = "CREATE TABLE minor (" + "sid INTEGER NOT NULL," + "name VARCHAR(50) NOT NULL,"
				+ "level VARCHAR(5) NOT NULL," + "PRIMARY KEY (sid, name, level),"
				+ "FOREIGN KEY (sid) REFERENCES students(sid) ON DELETE CASCADE,"
				+ "FOREIGN KEY (name, level) REFERENCES degrees(dgname, level) ON DELETE CASCADE" + ")";

		stmt.execute(sql);
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

			// Disable foreign key checks before dropping tables
			System.out.println("Disabling foreign key checks...");
			stmt.execute("SET FOREIGN_KEY_CHECKS=0;");

			// Create tables in the correct order:
			// 1) students
			// 2) departments
			// 3) degrees
			// 4) courses
			// 5) major
			// 6) minor
			// 7) register

			CreateTables tableCreator = new CreateTables();

			System.out.println("Creating student table...");
			tableCreator.createStudentTable(stmt);
			System.out.println("Student table created successfully.");

			System.out.println("Creating department table...");
			tableCreator.createDepartmentTable(stmt);
			System.out.println("Department table created successfully.");

			System.out.println("Creating degrees table...");
			tableCreator.createDegreesTable(stmt);
			System.out.println("Degrees table created successfully.");

			System.out.println("Creating courses table...");
			tableCreator.createCoursesTable(stmt);
			System.out.println("Courses table created successfully.");

			System.out.println("Creating major table...");
			tableCreator.createMajorTable(stmt);
			System.out.println("Major table created successfully.");

			System.out.println("Creating minor table...");
			tableCreator.createMinorTable(stmt);
			System.out.println("Minor table created successfully.");

			System.out.println("Creating register table...");
			tableCreator.createRegisterTable(stmt);
			System.out.println("Register table created successfully.");

			// Enable foreign key checks after creating tables
			System.out.println("Enabling foreign key checks...");
			stmt.execute("SET FOREIGN_KEY_CHECKS=1;");

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