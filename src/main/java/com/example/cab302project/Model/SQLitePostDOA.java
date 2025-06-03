package com.example.cab302project.Model;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The SQLitePostDAO class provides methods to interact with the SQLite database for managing posts.
 * This includes creating the posts table, adding, updating, deleting posts, and retrieving posts by ID or author.
 */
public class SQLitePostDOA {
    private static Connection connection;

    /**
     * Constructor for SQLitePostDAO. It initializes the database connection and creates the posts table if it doesn't exist.
     */
    public SQLitePostDOA() {
        connection = SqliteConnection.connect();
        createTable();
    }

    /**
     * Enables foreign key support in SQLite.
     */
    private void enableForeignKeys() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON;");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the posts table in the SQLite database with relevant fields and data types.
     */
    public void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS posts ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "userId INTEGER NOT NULL,"
                    + "postTitle VARCHAR NOT NULL,"
                    + "postDescription VARCHAR NOT NULL,"
                    + "carMake VARCHAR NOT NULL,"
                    + "carModel VARCHAR NOT NULL,"
                    + "postLocation VARCHAR NULL,"
                    + "postImage BLOB NULL,"
                    + "rating INTEGER NULL,"
                    + "numberOfComments INTEGER NULL,"
                    + "numberOfShares INTEGER NULL,"
                    + "FOREIGN KEY(userId) REFERENCES users(id)"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new post to the database. The post details and author username are inserted into the posts table.
     *
     * @param post     The Post object containing post details.
     * @param userName The username of the post author.
     */
    public void addPost(Post post, String userName) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO posts (userId, postTitle, postDescription, carMake, carModel, postLocation, postImage, rating, numberOfComments, numberOfShares) VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            if (post.getTitle().isEmpty() || post.getDescription().isEmpty() || post.getMake().isEmpty() || post.getModel().isEmpty() || post.getLocation().isEmpty()) {
                return;
            } else {
                statement.setString(1, userName);
                statement.setString(2, post.getTitle());
                statement.setString(3, post.getDescription());
                statement.setString(4, post.getMake());
                statement.setString(5, post.getModel());
                statement.setString(6, post.getLocation());
                statement.setBytes(7, post.getPostImage());
                statement.setInt(8, 0);
                statement.setInt(9, 0);
                statement.setInt(10, 0);
                statement.executeUpdate();
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the details of an existing post in the database, identified by post ID.
     *
     * @param post   The Post object with updated information.
     * @param postId The ID of the post to be updated.
     * @throws SQLException If there is a database access error or other errors.
     */
    public void updatePost(Post post, int postId) throws SQLException {
        String sql = "UPDATE posts SET postTitle = ?, postDescription = ?, carMake = ?, carModel = ?, postLocation = ? WHERE id = ?";
        if (post.getTitle().isEmpty() || post.getDescription().isEmpty() || post.getMake().isEmpty() || post.getModel().isEmpty() || post.getLocation().isEmpty()) {
            return;
        } else {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, post.getTitle());
                statement.setString(2, post.getDescription());
                statement.setString(3, post.getMake());
                statement.setString(4, post.getModel());
                statement.setString(5, post.getLocation());
                statement.setInt(6, postId);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    /**
     * Updates the userID of all posts authored by a given user.
     *
     * @param CurrentUserName The current username of the user.
     * @param NewUserName     The new username of the user.
     * @throws SQLException If there is a database access error or other errors.
     */
    public void updateUserPost(String CurrentUserName, String NewUserName) throws SQLException {
        String sql = "UPDATE posts SET userId = ? WHERE userId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, NewUserName);
            statement.setString(2, CurrentUserName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Deletes a post from the database, identified by post ID.
     *
     * @param postId The ID of the post to be deleted.
     */
    public void deletePost(Integer postId) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM posts WHERE id = ?");
            statement.setInt(1, postId);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the number of posts authored by a given user.
     *
     * @param userName The username of the user.
     * @return The number of posts authored by the user.
     * @throws SQLException If there is a database access error or other errors.
     */
    public int getNumPosts(String userName) throws SQLException {
        int numPosts = 0;
        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM posts WHERE userID = ?");
        statement.setString(1, userName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            numPosts = resultSet.getInt(1);
        }
        resultSet.close();
        statement.close();
        return numPosts;
    }

    /**
     * Increments the number of comments for a post.
     *
     * @param postId The ID of the post.
     */
    public void incrementFollowers(Integer postId) {
        String selectQuery = "SELECT numberOfComments FROM posts WHERE id = ?";
        String updateQuery = "UPDATE posts SET numberOfComments = ? WHERE id = ?";

        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setInt(1, postId);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    int comments = resultSet.getInt("numberOfComments");
                    comments += 1;

                    // Update the followers count
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setInt(1, comments);
                        updateStatement.setInt(2, postId);
                        updateStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error incrementing followers: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves the number of comments for a post.
     *
     * @param postId The ID of the post.
     * @return The number of comments on the post.
     */
    public Integer getFollowers(Integer postId) {
        String selectQuery = "SELECT numberOfComments FROM posts WHERE id = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setInt(1, postId);
            try (ResultSet rs = selectStatement.executeQuery()) {
                int comments = 0;
                if (rs.next()) {
                    comments = rs.getInt("numberOfComments");
                }
                return comments;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving number of comments: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves a post by its ID from the database.
     *
     * @param id The ID of the post.
     * @return The Post object if found, otherwise null.
     */
    public static Post getPost(int id) {
        String sql = "SELECT * FROM posts WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Integer postId = resultSet.getInt("id");
                    String title = resultSet.getString("postTitle");
                    String description = resultSet.getString("postDescription");
                    String userID = resultSet.getString("userID");
                    String make = resultSet.getString("carMake");
                    String model = resultSet.getString("carModel");
                    String location = resultSet.getString("postLocation");
                    byte[] imageBytes = resultSet.getBytes("postImage");
                    Integer rating = resultSet.getInt("rating");
                    Integer numberComments = resultSet.getInt("numberOfComments");
                    Integer numberShares = resultSet.getInt("numberOfShares");

                    Post post = new Post(title, description, userID, make, model, location, imageBytes, rating, numberComments, numberShares);
                    post.setId(postId);
                    return post;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all posts authored by a given user.
     *
     * @param author The username of the author.
     * @return A list of posts authored by the user.
     */
    public static List<Post> getPostsByAuthor(String author) {
        List<Post> posts = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM posts WHERE userId = ?");
            statement.setString(1, author);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer postId = resultSet.getInt("id");
                String title = resultSet.getString("postTitle");
                String description = resultSet.getString("postDescription");
                String make = resultSet.getString("carMake");
                String model = resultSet.getString("carModel");
                String location = resultSet.getString("postLocation");
                byte[] imageBytes = resultSet.getBytes("postImage");
                Integer rating = resultSet.getInt("rating");
                Integer numberComments = resultSet.getInt("numberOfComments");
                Integer numberShares = resultSet.getInt("numberOfShares");

                Post post = new Post(title, description, author, make, model, location, imageBytes, rating, numberComments, numberShares);
                post.setId(postId);
                posts.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    /**
     * Increments the followers count for a user.
     *
     * @param username The username of the user.
     */
    public void incrementFollowers(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT followers FROM user WHERE userID = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int followers = resultSet.getInt("followers");
                followers += 1;
                PreparedStatement statement2 = connection.prepareStatement("UPDATE followers SET followers = ? WHERE userID = ?");
                statement2.setInt(1, followers);
                statement2.setString(2, username);
                statement2.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates the comments table in the SQLite database.
     */
    public void commentTable() {
        String sql = "CREATE TABLE IF NOT EXISTS comments (" +
                "commentId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "postId INTEGER NOT NULL," +
                "commentText TEXT NOT NULL," +
                "timestamp TEXT NOT NULL," +
                "FOREIGN KEY(id) REFERENCES posts(id)" +
                ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating comments table: " + e.getMessage());
        }
    }
}
