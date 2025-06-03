package com.example.cab302project.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The LoginSystem class provides functionality to check a user's login credentials.
 * It interacts with the database to verify if the provided username and password exist.
 */
public class LoginSystem {

    /**
     * Checks if the provided username and password match a record in the database.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return true if the credentials match a user in the database, false otherwise.
     */
    public boolean checkLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = SqliteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the parameters for the prepared statement
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            // Execute the query and check if a matching user is found
            ResultSet rs = pstmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            // Log the SQL exception message
            System.out.println(e.getMessage());
        }

        // Return false if there is an exception or no match
        return false;
    }
}
