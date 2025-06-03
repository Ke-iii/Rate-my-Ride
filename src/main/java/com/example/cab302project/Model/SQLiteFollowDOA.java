package com.example.cab302project.Model;

import java.sql.*;

/**
 * The SQLiteFollowDOA class provides methods to manage follower relationships
 * in the database, including creating tables, inserting followers, updating
 * follower information, checking follower status, and unfollowing users.
 */
public class SQLiteFollowDOA {
    private static Connection connection;

    /**
     * Constructor for SQLiteFollowDOA. It initializes the database connection
     * and creates the followers table if it doesn't already exist.
     */
    public SQLiteFollowDOA() {
        connection = SqliteConnection.connect();
        createTable();
    }

    /**
     * Enables foreign key support in the SQLite database.
     */
    private void enableForeignKeys() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON;");
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the 'followers' table in the database if it does not already exist.
     */
    public void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS followers ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "account STRING NOT NULL,"
                    + "follower STRING NOT NULL,"
                    + "FOREIGN KEY(account) REFERENCES users(username),"
                    + "FOREIGN KEY(follower) REFERENCES users(username)"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a new follower relationship into the database.
     *
     * @param account  The username of the account being followed.
     * @param follower The username of the user who is following the account.
     */
    public void insertFollower(String account, String follower) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO followers (account, follower) VALUES (?, ?)"
            );
            statement.setString(1, account);
            statement.setString(2, follower);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the account name for a user in the followers table.
     *
     * @param currentUserName The current username of the account to update.
     * @param newUserName     The new username to replace the current one.
     */
    public void updateAccount(String currentUserName, String newUserName) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE followers SET account = ? WHERE account = ?"
            );
            statement.setString(1, newUserName);
            statement.setString(2, currentUserName);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the follower name for a user in the followers table.
     *
     * @param currentUserName The current username of the follower to update.
     * @param newUserName     The new username to replace the current one.
     */
    public void updateFollower(String currentUserName, String newUserName) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE followers SET follower = ? WHERE follower = ?"
            );
            statement.setString(1, newUserName);
            statement.setString(2, currentUserName);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if a user is following another user.
     *
     * @param account  The username of the account being checked.
     * @param follower The username of the potential follower.
     * @return True if the follower relationship exists, false otherwise.
     * @throws SQLException If there is an error executing the query.
     */
    public Boolean isFollower(String account, String follower) throws SQLException {
        String query = "SELECT follower FROM followers WHERE account = ? AND follower = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, account);
            statement.setString(2, follower);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();  // Return true if a record is found, meaning they are a follower
            }
        }
    }

    /**
     * Removes a follower relationship from the database.
     *
     * @param account  The username of the account being unfollowed.
     * @param follower The username of the user who is unfollowing the account.
     * @throws SQLException If there is an error executing the delete operation.
     */
    public void unfollow(String account, String follower) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM followers WHERE account = ? AND follower = ?"
        );
        statement.setString(1, account);
        statement.setString(2, follower);
        statement.executeUpdate();
    }
}
