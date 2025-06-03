package com.example.cab302project.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The RegistrationSystem class provides methods to manage user registration,
 * including adding new users and checking for duplicate users.
 */
public class RegistrationSystem {

    /**
     * Adds a new user to the database.
     *
     * @param firstName      The first name of the user.
     * @param lastName       The last name of the user.
     * @param email          The email address of the user.
     * @param userName       The username of the user.
     * @param password       The password for the user's account.
     * @param profilePicture  The profile picture of the user as a byte array.
     * @param description    A brief description of the user.
     */
    public void addUser(String firstName, String lastName, String email, String userName, String password, byte[] profilePicture, String description) {
        String sql = "INSERT INTO users (firstName, lastName, email, userName, password, profilePicture, followers, numberOfPosts, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Check for empty fields and duplicate email
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || userName.isEmpty() || password.isEmpty() || duplicateUser(email)) {
            return;
        } else {
            try (Connection conn = SqliteConnection.connect();
                 PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, email);
                statement.setString(4, userName);
                statement.setString(5, password);
                statement.setBytes(6, profilePicture);
                statement.setInt(7, 0); // Initial followers count
                statement.setInt(8, 0); // Initial number of posts
                statement.setString(9, description);
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Checks if a user with the specified email already exists in the database.
     *
     * @param email The email address to check for duplication.
     * @return true if a user with the given email already exists; false otherwise.
     */
    public boolean duplicateUser(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = SqliteConnection.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Returns true if a record is found
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
