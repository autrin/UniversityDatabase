package university_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Index {

    public static void main(String[] args) {
        System.out.println("Starting Index process...");
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);
            stmt = conn.createStatement();

            // Drop index if exists to ensure initial run is without it
            try {
                stmt.executeUpdate("DROP INDEX idx_gender ON students");
                System.out.println("Dropped existing idx_gender index.");
            } catch (SQLException e) {
                System.out.println("No existing idx_gender index to drop.");
            }

            // Execute query without index
            System.out.println("Executing query without index...");
            Query query = new Query();
            long startTime = System.nanoTime();
            query.query3(stmt);
            long endTime = System.nanoTime();
            long durationWithoutIndex = endTime - startTime;
            System.out.println("Execution time without index: " + durationWithoutIndex + " ns");

            // Create index
            System.out.println("Creating index...");
            stmt.executeUpdate("CREATE INDEX idx_gender ON students(gender)");
            System.out.println("Index idx_gender created.");

            // Execute query with index
            System.out.println("Executing query with index...");
            long indexedStartTime = System.nanoTime();
            query.query3(stmt);
            long indexedEndTime = System.nanoTime();
            long durationWithIndex = indexedEndTime - indexedStartTime;
            System.out.println("Execution time with index: " + durationWithIndex + " ns");

        } catch (SQLException e) {
            System.out.println("Database operation failed:");
            e.printStackTrace();
        } finally {
            System.out.println("Closing resources...");
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}