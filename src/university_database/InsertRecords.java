package university_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertRecords {

    // Method to load students records
    public void loadStudentRecords(Statement stmt) throws SQLException {
        String sql = "LOAD DATA LOCAL INFILE \"C:\\\\Users\\\\autri\\\\OneDrive - Iowa State University\\\\Spring2025\\\\CS363\\\\project1.2\\\\project1_data\\\\students.csv\" "
                   + "INTO TABLE students "
                   + "FIELDS TERMINATED BY ',' "
                   + "ENCLOSED BY '\"' "
                   + "LINES TERMINATED BY '\\r\\n' "
                   + "IGNORE 1 ROWS "
                   + "(sid, ssn, name, gender, dob, p_addr, p_phone, c_addr, c_phone)";
        stmt.execute(sql);
    }

    // Method to load departments records
    public void loadDepartmentRecords(Statement stmt) throws SQLException {
        String sql = "LOAD DATA LOCAL INFILE \"C:\\\\Users\\\\autri\\\\OneDrive - Iowa State University\\\\Spring2025\\\\CS363\\\\project1.2\\\\project1_data\\\\departments.csv\" "
                   + "INTO TABLE departments "
                   + "FIELDS TERMINATED BY ',' "
                   + "ENCLOSED BY '\"' "
                   + "LINES TERMINATED BY '\\r\\n' "
                   + "IGNORE 1 ROWS "
                   + "(dcode, dname, phone, college)";
        stmt.execute(sql);
    }

    // Method to load degrees records
    public void loadDegreesRecords(Statement stmt) throws SQLException {
        String sql = "LOAD DATA LOCAL INFILE \"C:\\\\Users\\\\autri\\\\OneDrive - Iowa State University\\\\Spring2025\\\\CS363\\\\project1.2\\\\project1_data\\\\degrees.csv\" "
                   + "INTO TABLE degrees "
                   + "FIELDS TERMINATED BY ',' "
                   + "ENCLOSED BY '\"' "
                   + "LINES TERMINATED BY '\\r\\n' "
                   + "IGNORE 1 ROWS "
                   + "(dgname, level, department_code)";
        stmt.execute(sql);
    }

    // Method to load major records
    public void loadMajorRecords(Statement stmt) throws SQLException {
        String sql = "LOAD DATA LOCAL INFILE \"C:\\\\Users\\\\autri\\\\OneDrive - Iowa State University\\\\Spring2025\\\\CS363\\\\project1.2\\\\project1_data\\\\major.csv\" "
                   + "INTO TABLE major "
                   + "FIELDS TERMINATED BY ',' "
                   + "ENCLOSED BY '\"' "
                   + "LINES TERMINATED BY '\\r\\n' "
                   + "IGNORE 1 ROWS "
                   + "(sid, name, level)";
        stmt.execute(sql);
    }

    // Method to load minor records
    public void loadMinorRecords(Statement stmt) throws SQLException {
        String sql = "LOAD DATA LOCAL INFILE \"C:\\\\Users\\\\autri\\\\OneDrive - Iowa State University\\\\Spring2025\\\\CS363\\\\project1.2\\\\project1_data\\\\minor.csv\" "
                   + "INTO TABLE minor "
                   + "FIELDS TERMINATED BY ',' "
                   + "ENCLOSED BY '\"' "
                   + "LINES TERMINATED BY '\\r\\n' "
                   + "IGNORE 1 ROWS "
                   + "(sid, name, level)";
        stmt.execute(sql);
    }

    // Method to load courses records
    public void loadCoursesRecords(Statement stmt) throws SQLException {
        String sql = "LOAD DATA LOCAL INFILE \"C:\\\\Users\\\\autri\\\\OneDrive - Iowa State University\\\\Spring2025\\\\CS363\\\\project1.2\\\\project1_data\\\\courses.csv\" "
                   + "INTO TABLE courses "
                   + "FIELDS TERMINATED BY ',' "
                   + "ENCLOSED BY '\"' "
                   + "LINES TERMINATED BY '\\r\\n' "
                   + "IGNORE 1 ROWS "
                   + "(cnumber, cname, description, credithours, level, department_code)";
        stmt.execute(sql);
    }

    // Method to load register records
    public void loadRegisterRecords(Statement stmt) throws SQLException {
        String sql = "LOAD DATA LOCAL INFILE \"C:\\\\Users\\\\autri\\\\OneDrive - Iowa State University\\\\Spring2025\\\\CS363\\\\project1.2\\\\project1_data\\\\register.csv\" "
                   + "INTO TABLE register "
                   + "FIELDS TERMINATED BY ',' "
                   + "ENCLOSED BY '\"' "
                   + "LINES TERMINATED BY '\\r\\n' "
                   + "IGNORE 1 ROWS "
                   + "(sid, course_number, regtime, grade)";
        stmt.execute(sql);
    }

    public static void main(String[] args) {
        System.out.println("Starting InsertRecords process...");

        Connection conn = null;
        Statement stmt = null;
        InsertRecords inserter = new InsertRecords();

        try {
            conn = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);
            stmt = conn.createStatement();
            System.out.println("Connection established.");

            stmt.execute("SET GLOBAL local_infile = 1");
            
            System.out.println("Loading student records...");
            inserter.loadStudentRecords(stmt);
            System.out.println("Student records loaded.");

            System.out.println("Loading department records...");
            inserter.loadDepartmentRecords(stmt);
            System.out.println("Department records loaded.");

            System.out.println("Loading degrees records...");
            inserter.loadDegreesRecords(stmt);
            System.out.println("Degrees records loaded.");

            System.out.println("Loading major records...");
            inserter.loadMajorRecords(stmt);
            System.out.println("Major records loaded.");

            System.out.println("Loading minor records...");
            inserter.loadMinorRecords(stmt);
            System.out.println("Minor records loaded.");

            System.out.println("Loading courses records...");
            inserter.loadCoursesRecords(stmt);
            System.out.println("Courses records loaded.");

            System.out.println("Loading register records...");
            inserter.loadRegisterRecords(stmt);
            System.out.println("Register records loaded.");

            System.out.println("InsertRecords process completed successfully.");
        } catch (SQLException e) {
            System.out.println("InsertRecords operation failed:");
            e.printStackTrace();
        } finally {
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
