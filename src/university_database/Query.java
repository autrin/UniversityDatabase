package university_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Query {

	public void query1(Statement stmt) {
		/*
		 * The numbers and names of courses and their corresponding average grades from
		 * students registered in the past semesters
		 */
		
	}

	public static void main(String[] args) {
		System.out.println("Starting Query process...");
		Connection conn = null;
		Statement stmt = null;
		Query query = new Query();
		
		try {
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);
			stmt  = conn.createStatement();
			System.out.println("Connection established.");
			
			
		} catch (SQLException e) {
			
		}
	}

}
