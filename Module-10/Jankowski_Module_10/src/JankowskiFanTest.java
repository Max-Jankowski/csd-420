// Max Jankowski
// Bellevue University
// Module 10 test code

import java.sql.*; //not using any of the UI for the test so only importing what I need for DB connection

//testing the fan view class methods, doesn't use JAVA fx, rather is performed as a pass fail result in terminal
public class JankowskiFanTest {
    // database connection
    static final String URL  = "jdbc:mysql://localhost:3306/databasedb";
    static final String USER = "student1";
    static final String PASS = "pass";

    public static void main(String[] args) throws Exception {

        Connection conn = DriverManager.getConnection(URL, USER, PASS);
        System.out.println("Connected to database.\n");

        int passed = 0;
        int failed = 0;

        // first test checks that when checking key id 1 there is an existing record
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT id, firstname, lastname, favoriteteam FROM fans WHERE id = ?")) {
            ps.setInt(1, 1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("PASS - displayRecord: found existing ID 1 ("
                        + rs.getString("firstname") + " " + rs.getString("lastname") + ")");
                passed++;
            } else {
                System.out.println("FAIL - displayRecord: no row returned for ID 1");
                failed++;
            }
        }

        // checking that there are no erroneous non-existent records and the class can recognize no record present.
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT id FROM fans WHERE id = ?")) {
            ps.setInt(1, 99999);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                System.out.println("PASS - displayRecord: correctly returned no row for ID 99999");
                passed++;
            } else {
                System.out.println("FAIL - displayRecord: unexpectedly found a row for ID 99999");
                failed++;
            }
        }

        // third tests makes sure that records can be changed, original values saved and restored after test
        String origFirst = "", origLast = "", origTeam = "";
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT firstname, lastname, favoriteteam FROM fans WHERE id = ?")) {
            ps.setInt(1, 1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                origFirst = rs.getString("firstname");
                origLast  = rs.getString("lastname");
                origTeam  = rs.getString("favoriteteam");
            }
        }

        // Running the update of record test
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE fans SET firstname=?, lastname=?, favoriteteam=? WHERE id=?")) {
            ps.setString(1, "TestFirst");
            ps.setString(2, "TestLast");
            ps.setString(3, "TestTeam");
            ps.setInt(4, 1);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("PASS - updateRecord: row updated successfully for ID 1");
                passed++;
            } else {
                System.out.println("FAIL - updateRecord: no rows updated for ID 1");
                failed++;
            }
        }

        // making sure that the update method actually saved the changes
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT firstname FROM fans WHERE id = ?")) {
            ps.setInt(1, 1);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && "TestFirst".equals(rs.getString("firstname"))) {
                System.out.println("PASS - updateRecord: updated value confirmed in database");
                passed++;
            } else {
                System.out.println("FAIL - updateRecord: database did not reflect the update");
                failed++;
            }
        }

        // Restore original data so the table is unchanged after testing
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE fans SET firstname=?, lastname=?, favoriteteam=? WHERE id=?")) {
            ps.setString(1, origFirst);
            ps.setString(2, origLast);
            ps.setString(3, origTeam);
            ps.setInt(4, 1);
            ps.executeUpdate();
            System.out.println("      (original data restored for ID 1)");
        }

        // attempting to update a record that is not present to make sure that programs handleUpdate method is operating properly
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE fans SET firstname=?, lastname=?, favoriteteam=? WHERE id=?")) {
            ps.setString(1, "X");
            ps.setString(2, "Y");
            ps.setString(3, "Z");
            ps.setInt(4, 99999);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("PASS - updateRecord: correctly returned no rows for ID 99999");
                passed++;
            } else {
                System.out.println("FAIL - updateRecord: unexpectedly updated a row for ID 99999");
                failed++;
            }
        }

        //printing out the summery in terminal for the tests
        conn.close();
        System.out.println("\n--- Results: " + passed + " passed, " + failed + " failed ---");
    }
}
 