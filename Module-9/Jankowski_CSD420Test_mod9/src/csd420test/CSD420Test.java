//Max Jankowski
//Bellevue University 
//CSD420 Module 9


package csd420test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CSD420Test {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/databasedb";
        String user = "student1";
        String password = "pass";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection successful!");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM address33");

            while (rs.next()) {
                System.out.println(rs.getInt("ID") + " " +
                        rs.getString("LASTNAME") + " " +
                        rs.getString("FIRSTNAME") + " " +
                        rs.getString("STREET") + " " +
                        rs.getString("CITY") + " " +
                        rs.getString("STATE") + " " +
                        rs.getString("ZIP"));
            }

            conn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


// Source: https://medium.com/@christopher.suffi/establishing-jdbc-connection-in-java-d712f4b86ad5
