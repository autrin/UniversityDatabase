package university_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ModifyRecords {

	public void modifyRecord1(Statement stmt) throws SQLException {
		/*
		 * Change the name of the student with ssn = 144673371 to Scott and print out
		 * the new table where the modification is conducted.
		 */
		String sql = "UPDATE students SET name = 'Scott' WHERE ssn = 144673371";
		try {
			int update = stmt.executeUpdate(sql);
			System.out.println("Updated " + update + " record(s).");

			// Query the updated record from students table
			String selectSql = "SELECT * FROM students WHERE ssn = 144673371";
			ResultSet rs = stmt.executeQuery(selectSql);
			System.out.println("Current record:");
			while (rs.next()) {
				int sid = rs.getInt("sid");
				int ssn = rs.getInt("ssn");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				String dob = rs.getString("dob");
				String p_addr = rs.getString("p_addr");
				String p_phone = rs.getString("p_phone");
				String c_addr = rs.getString("c_addr");
				String c_phone = rs.getString("c_phone");
				System.out.println("sid: " + sid + ", ssn: " + ssn + ", name: " + name + ", gender: " + gender
						+ ", dob: " + dob + ", p_addr: " + p_addr + ", p_phone: " + p_phone + ", c_addr: " + c_addr
						+ ", c_phone: " + c_phone);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error in modifyRecord1(): " + e.getMessage());
		}
	}

	public void modifyRecords2(Statement stmt) throws SQLException {
		/*
		 * Change the major of the student with ssn = 144673371 to Computer Science,
		 * Master. and print out the new table (major) where the modification is
		 * conducted.
		 */
		String updateSql = "UPDATE major SET name = 'Computer Science', level = 'MS' "
				+ "WHERE sid = (SELECT sid FROM students WHERE ssn = 144673371)";
		try {
			int update = stmt.executeUpdate(updateSql);
			System.out.println("2nd record modification done successfully. Updated " + update + " record(s).");

			// Now query the major table for the updated record
			String selectSql = "SELECT * FROM major WHERE sid = (SELECT sid FROM students WHERE ssn = 144673371)";
			ResultSet rs = stmt.executeQuery(selectSql);
			System.out.println("Updated major record:");
			while (rs.next()) {
				int sid = rs.getInt("sid");
				String majorName = rs.getString("name");
				String level = rs.getString("level");
				System.out.println("sid: " + sid + ", Major: " + majorName + ", Level: " + level);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error in modifyRecord2(): " + e.getMessage());
		}
	}

	public void modifyRecords3(Statement stmt) throws SQLException {
		// Delete registration records for Summer2024
		String deleteSql = "DELETE FROM register WHERE regtime = 'Summer2024'";
		try {
			int rowsDeleted = stmt.executeUpdate(deleteSql);
			System.out.println("Deleted " + rowsDeleted + " registration records for Summer2024.");

			// Now query the register table to print its current contents
			String selectSql = "SELECT * FROM register";
			ResultSet rs = stmt.executeQuery(selectSql);
			System.out.println("Current register records:");
			while (rs.next()) {
				int sid = rs.getInt("sid");
				int courseNumber = rs.getInt("course_number");
				String regtime = rs.getString("regtime");
				int grade = rs.getInt("grade");
				System.out.println("sid: " + sid + ", course_number: " + courseNumber + ", regtime: " + regtime
						+ ", grade: " + grade);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error in modifyRecord3(): " + e.getMessage());
		}
	}

	public void modifyRecords4(Statement stmt) throws SQLException {
		/*
		 * If a group of courses have the same level and department_code, only keep
		 * the one with the smallest course number and delete the rest. If a course is
		 * deleted, the corresponding record in register relation should also be
		 * deleted.
		 */
        String deleteCoursesSql = 
                "DELETE c1 FROM courses c1 " +
                "INNER JOIN courses c2 ON c1.department_code = c2.department_code " +
                "AND c1.level = c2.level " +
                "AND c1.cnumber > c2.cnumber";
		try {
			int rowsDeleted = stmt.executeUpdate(deleteCoursesSql);
			System.out.println("Deleted " + rowsDeleted + " courses.");
		
			System.out.println("Current courses records:");
			ResultSet rs = stmt.executeQuery("SELECT * FROM courses");
			while(rs.next()) {
				// another approach to print out the table
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1) System.out.print(", ");
					String columnValue = rs.getString(i);
					System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
				}
				System.out.println("");
			}
		} catch (SQLException e) {
			System.out.println("Error in modifyRecord4(): " + e.getMessage());
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

			System.out.println("Modifying record #3 ...");
			modifyRecords.modifyRecords3(stmt);

			System.out.println("Modifying record #4 ...");
			modifyRecords.modifyRecords4(stmt);
			
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
