
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTables {

    static final String DB_URL = "jdbc:mysql://localhost/";
    static final String USER = "";
    static final String PASS = "";

    public static void main(String[] args) {
        // Open a connection
        try (
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement();) {
            String sql = "CREATE TABLE students("
                    + "sid INTEGER UNIQUE NOT NULL,"
                    + "ssn INTEGER PRIMARY KEY,"
                    + "name VARCHAR(20) NOT NULL,"
                    + "gender VARCHAR(1) NOT NULL,"
                    + "dob VARCHAR(10) NOT NULL,"
                    + "p_addr VARCHAR(20) NOT NULL,"
                    + "p_phone VARCHAR(20) NOT NULL,"
                    + "c_addr VARCHAR(20) NOT NULL,"
                    + "c_phone VARCHAR(20) NOT NULL)";

            stmt.executeUpdate(sql);
            System.out.println("Created student table in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
