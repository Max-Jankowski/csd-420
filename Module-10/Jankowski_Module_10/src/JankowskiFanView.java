// Max Jankowski
// Bellevue University
// Module 10 FanView class

import javafx.application.Application; // gives me the launch and start methods for FX
import javafx.geometry.Insets; // provides margins and padding
import javafx.geometry.Pos; //FX tooling
import javafx.scene.Scene; // Java FX tool import like all but one below
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*; // used to make a connection to the database, contains th DriverManager


// Public class that views a table bassed on ID input, allows for modification of other fields
public class JankowskiFanView extends Application {

    // the constants for the database connection
    static final String DB_URL  = "jdbc:mysql://localhost:3306/databasedb";
    static final String DB_USER = "student1";
    static final String DB_PASS = "pass";

    //the fields for the UI
    TextField tfId;
    TextField tfFirstName;
    TextField tfLastName;
    TextField tfFavoriteTeam;
    Label     lblStatus;
    //connection is shared and opened once and closed on exit
    Connection connection;


    // the entry of JavaFX
    @Override
    public void start(Stage primaryStage) {
        // Open one shared connection for the run period of program
        try {
            connection = openConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            showAlert("Connection Error", "Could not connect to database:\n" + e.getMessage());
        }

        primaryStage.setTitle("Fan Viewer");
        primaryStage.setScene(buildScene());
        primaryStage.setOnCloseRequest(e -> closeConnection(connection));
        primaryStage.show();
    }


    // Building the UI elements
    Scene buildScene() {

        // forming of the labels and text fields
        Label lblId          = new Label("Fan ID:");
        Label lblFirst       = new Label("First Name:");
        Label lblLast        = new Label("Last Name:");
        Label lblTeam        = new Label("Favorite Team:");

        tfId           = new TextField();
        tfFirstName    = new TextField();
        tfLastName     = new TextField();
        tfFavoriteTeam = new TextField();

        tfId.setPromptText("Enter ID to look up");

        // A bar to show the status below he buttons
        lblStatus = new Label("Enter an ID and click Display.");
        lblStatus.setStyle("-fx-text-fill: #555;");

        // Making the buttons for user interaction
        Button btnDisplay = new Button("Display");
        Button btnUpdate  = new Button("Update");

        btnDisplay.setMinWidth(90);
        btnUpdate.setMinWidth(90);

        // Display button that fetchs records when id entered
        btnDisplay.setOnAction(e -> handleDisplay());

        // The Update button to change records of given element on the table
        btnUpdate.setOnAction(e -> handleUpdate());

        // built grid layout with labels and all fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(lblId,           0, 0); grid.add(tfId,           1, 0);
        grid.add(lblFirst,        0, 1); grid.add(tfFirstName,    1, 1);
        grid.add(lblLast,         0, 2); grid.add(tfLastName,     1, 2);
        grid.add(lblTeam,         0, 3); grid.add(tfFavoriteTeam, 1, 3);

        // the row of buttons for display and update
        HBox buttonRow = new HBox(15, btnDisplay, btnUpdate);
        buttonRow.setAlignment(Pos.CENTER_RIGHT);
        buttonRow.setPadding(new Insets(0, 20, 10, 20));

        // the lower root layout and where the error messages will be displayed
        VBox root = new VBox(10, grid, buttonRow, lblStatus);
        root.setPadding(new Insets(10));
        VBox.setMargin(lblStatus, new Insets(0, 20, 10, 20));

        return new Scene(root, 380, 320); //needed to tinker with size a bit to properly display window
    }                                            // too small and I was unable to see the error messages


    // handling the display button
    void handleDisplay() {
        System.out.println("Display clicked, lblStatus is: " + lblStatus); // temporary debug line remove before submitting once the lack of error message is determined
        String idText = tfId.getText().trim();

        // Validates ID, field must not be empty
        if (idText.isEmpty()) {
            setStatus("Please enter a Fan ID.", true);
            clearFields(false);
            return;
        }

        //validating that id is a number
        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            setStatus("ID key must be a whole number.", true);
            clearFields(false);
            return;
        }

        // Fetch from the database  and populate the fields, report error status bar
        try {
            boolean found = displayRecord(connection, id);
            if (!found) { //clear the name fields and present error
                clearFields(false); // clear the fields but keep ID
                setStatus("No fan found with the ID " + id + ".", true);

            }
        } catch (SQLException ex) {
            setStatus("Database error: " + ex.getMessage(), true);
        }
    }
    // handling method for the 'update' button
    void handleUpdate() {
        String idText = tfId.getText().trim();

        if (idText.isEmpty()) { //user hasnt loaded in a record if this field is empty
            setStatus("Load a record first (use Display).", true);
            return;
        }
        //ID must be a whole number to be a ble to match ID key
        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            setStatus("ID must be a whole number.", true);
            return;
        }

        try { // passing the id and all editable fields to the database update method and trim strips white spaces
            boolean updated = updateRecord(
                    connection, id,
                    tfFirstName.getText().trim(),
                    tfLastName.getText().trim(),
                    tfFavoriteTeam.getText().trim()
            );
            if (updated) { //executes an update if one field was changed
                setStatus("Record updated successfully.", false);
            } else {
                setStatus("No record found with ID " + id + " to update.", true);
            }
        } catch (SQLException ex) {
            setStatus("Database error: " + ex.getMessage(), true);
        }
    }

    // database layer to separate the test portion of the assignment from the JavaFX UI.
    static Connection openConnection(String url, String user, String pass)
            throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    // gets the fan record for the id entered. populates the text fields returns true if records found
    boolean displayRecord(Connection conn, int id) throws SQLException {
        String sql = "SELECT id, firstname, lastname, favoriteteam FROM fans WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Populate the UI fields with the retrieved data
                    tfId.setText(String.valueOf(rs.getInt("id")));
                    tfFirstName.setText(rs.getString("firstname"));
                    tfLastName.setText(rs.getString("lastname"));
                    tfFavoriteTeam.setText(rs.getString("favoriteteam"));
                    setStatus("Record loaded for ID " + id + ".", false);
                    return true;
                }
                return false;
            }
        }
    }

    // method to update records in the database
    boolean updateRecord(Connection conn, int id,
                         String firstName, String lastName, String favoriteTeam)
            throws SQLException {

        String sql = "UPDATE fans SET firstname=?, lastname=?, favoriteteam=? WHERE id=?";
        // try block to see if change is made
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, favoriteTeam);
            pstmt.setInt(4, id);

            return pstmt.executeUpdate() > 0; // true means at least one row changed
        }
    }
    // the next section are some extra features that I added later that air the user in navigation and using of the app

    // sets label text for status to red in case of error and green for completed
    void setStatus(String message, boolean isError) {
        lblStatus.setText(message);
        lblStatus.setStyle(isError
                ? "-fx-text-fill: #c0392b;"   // red bad
                : "-fx-text-fill: #27ae60;"); // green good
    }

    // used to clear the fields that user can edit,also allowed after tests to clear the ID field as well
    void clearFields(boolean includeId) {
        if (includeId) tfId.setText("");
        tfFirstName.setText("");
        tfLastName.setText("");
        tfFavoriteTeam.setText("");
    }

    // closes the database connecion,swallows and close exceptions
    static void closeConnection(Connection conn) {
        if (conn != null) {
            try { conn.close(); } catch (SQLException ignored) {}
        }
    }

   // used to show a very simple error dialog
    static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


    // main function
    public static void main(String[] args) {
        launch(args);
    }
}

//Additional resources that I used to supplement the provided text:
// https://medium.com/@hewage.d.sampath/jdbc-crud-operations-inserting-updating-and-deleting-data-3790844a23d4
// not used to code but found useful: https://www.cs.auckland.ac.nz/references/java/java1.5/tutorial/jdbc/basics/updating.html
// https://docs.oracle.com/javase/tutorial/jdbc/basics/retrieving.html
