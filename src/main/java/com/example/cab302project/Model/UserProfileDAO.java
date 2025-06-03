package com.example.cab302project.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The UserProfileDAO class provides methods to manage user profiles in the database.
 * This includes updating user information and fetching user profiles.
 */
public class UserProfileDAO {
    private static Connection connection;

    /**
     * Constructor for UserProfileDAO. It initializes the database connection.
     */
    public UserProfileDAO() {
        connection = SqliteConnection.connect();
    }

    /**
     * Updates the user's profile information in the 'users' table.
     *
     * @param currentUserName The current username of the user.
     * @param firstName       The new first name of the user.
     * @param lastName        The new last name of the user.
     * @param username        The new username of the user.
     * @param email           The new email address of the user.
     * @param description     The new description or bio of the user.
     */
    public void updateUserProfile(String currentUserName, String firstName, String lastName, String username, String email, String description) {
        String updateQuery = "UPDATE users SET firstName = ?, lastName = ?, email = ?, username = ?, description = ? WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, username);
            pstmt.setString(5, description);
            pstmt.setString(6, currentUserName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches the user profile from the 'users' table using the provided username.
     *
     * @param username The username of the user whose profile is to be retrieved.
     * @return A User object containing the user's profile information, or null if the user is not found.
     */
    public User getUserProfile(String username) {
        User user = null;
        String selectQuery = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectQuery)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                int followers = rs.getInt("followers");
                int numberOfPosts = rs.getInt("numberOfPosts");
                String description = rs.getString("description");

                // Constructing the User object with retrieved data
                user = new User(id, firstName, lastName, email, username, followers, numberOfPosts, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
