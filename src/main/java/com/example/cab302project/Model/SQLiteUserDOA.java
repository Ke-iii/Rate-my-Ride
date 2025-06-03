package com.example.cab302project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The SQLiteUserDOA class provides methods to interact with the SQLite database for managing user information.
 * This includes retrieving user details, profile pictures, all users, and updating follower counts.
 */
public class SQLiteUserDOA {
    private static Connection connection;

    /**
     * Constructor for SQLiteUserDOA. It initializes the database connection.
     */
    public SQLiteUserDOA() {
        connection = SqliteConnection.connect();
    }

    /**
     * Retrieves the profile picture of a user by their username.
     *
     * @param username The username of the user.
     * @return The profile picture of the user as an Image object, or null if not found.
     */
    public Image getProfilePicture(String username) {
        String sql = "SELECT profilePicture FROM users WHERE username = ?";
        Image image = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    InputStream inputStream = resultSet.getBinaryStream("profilePicture");
                    if (inputStream != null) {
                        image = new Image(inputStream);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return image;
    }

    /**
     * Retrieves user details from the database by their username.
     *
     * @param username The username of the user.
     * @return A User object containing the user's details, or null if the user is not found.
     * @throws SQLException If a database access error occurs.
     */
    public User getUser(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Integer userID = resultSet.getInt("id");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    String email = resultSet.getString("email");
                    String userName = resultSet.getString("userName");
                    Integer followers = resultSet.getInt("followers");
                    Integer numberOfPosts = resultSet.getInt("numberOfPosts");
                    String description = resultSet.getString("description");

                    return new User(userID, firstName, lastName, email, userName, followers, numberOfPosts, description);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }

    /**
     * Retrieves a list of all users from the database, excluding the given username.
     *
     * @param username The username to be excluded from the list.
     * @return An ObservableList of usernames.
     */
    public ObservableList<String> getAllUsers(String username) {
        String sql = "SELECT username FROM users WHERE username != ?";
        ObservableList<String> userList = FXCollections.observableArrayList();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    userList.add(resultSet.getString("username"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    /**
     * Increments or decrements the number of followers of a user.
     *
     * @param username The username of the user whose followers count is to be updated.
     * @param follower The username of the follower (not used directly in the method).
     * @param amount   The amount by which to increment or decrement the follower count (positive or negative value).
     */
    public void incrementFollowers(String username, String follower, Integer amount) {
        String selectQuery = "SELECT followers FROM users WHERE username = ?";
        String updateQuery = "UPDATE users SET followers = ? WHERE username = ?";

        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setString(1, username);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    int followers = resultSet.getInt("followers");
                    followers += amount;

                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setInt(1, followers);
                        updateStatement.setString(2, username);
                        updateStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error incrementing followers: " + e.getMessage(), e);
        }
    }
}
