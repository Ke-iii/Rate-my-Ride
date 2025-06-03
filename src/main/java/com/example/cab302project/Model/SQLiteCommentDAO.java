package com.example.cab302project.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;  // Import LocalDateTime
import java.time.format.DateTimeFormatter; // Import DateTimeFormatter

/**
 * The SQLiteCommentDAO class provides methods to manage comments in the database,
 * including creating the comments table, adding comments, retrieving comments by post ID,
 * deleting comments, and updating comment authors.
 */
public class SQLiteCommentDAO {
    private Connection connection;

    /**
     * Constructor for SQLiteCommentDAO. Initializes the database connection
     * and creates the comments table if it doesn't already exist.
     */
    public SQLiteCommentDAO() {
        connection = SqliteConnection.connect(); // Assuming you have a method to establish a connection
        createCommentTable(); // Create comments table if it doesn't exist
    }

    /**
     * Creates the 'comments' table in the database if it does not already exist.
     */
    public void createCommentTable() {
        String sql = "CREATE TABLE IF NOT EXISTS comments (" +
                "commentId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "postId INTEGER NOT NULL," +
                "commentText TEXT NOT NULL," +
                "timestamp TEXT NOT NULL," +
                "author TEXT NOT NULL," +
                "FOREIGN KEY(postId) REFERENCES posts(id)," +
                "FOREIGN KEY(author) REFERENCES users(username)" +
                ");";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating comments table: " + e.getMessage());
        }
    }

    /**
     * Adds a new comment to a post.
     *
     * @param comment The Comment object containing the details of the comment to be added.
     * @throws SQLException If there is an error executing the insert operation.
     */
    public void addComment(Comment comment) throws SQLException {
        String sql = "INSERT INTO comments (postId, commentText, timestamp, author) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, comment.getPostId());
            statement.setString(2, comment.getText());
            statement.setString(3, LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            statement.setString(4, comment.getAuthor());
            statement.executeUpdate();
        }
    }

    /**
     * Retrieves comments for a specific post by its ID.
     *
     * @param postId The ID of the post for which comments are to be retrieved.
     * @return A list of Comment objects associated with the specified post ID.
     * @throws SQLException If there is an error executing the query.
     */
    public List<Comment> getCommentsByPostId(int postId) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE postId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, postId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int commentId = resultSet.getInt("commentId");
                    String commentText = resultSet.getString("commentText");
                    String timestamp = resultSet.getString("timestamp");
                    String author = resultSet.getString("author");
                    comments.add(new Comment(postId, commentText, timestamp, author)); // Adjust constructor as needed
                }
            }
        }
        return comments;
    }

    /**
     * Deletes a comment from the database by its ID.
     *
     * @param commentId The ID of the comment to be deleted.
     * @throws SQLException If there is an error executing the delete operation.
     */
    public void deleteComment(int commentId) throws SQLException {
        String sql = "DELETE FROM comments WHERE commentId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, commentId);
            statement.executeUpdate();
        }
    }

    /**
     * Updates the author of a comment by the current author's username.
     *
     * @param CurrentUser The current username of the author whose comments are to be updated.
     * @param newUserName The new username to replace the current author.
     * @throws SQLException If there is an error executing the update operation.
     */
    public void updateCommentUser(String CurrentUser, String newUserName) throws SQLException {
        String sql = "UPDATE comments SET author = ? WHERE author = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newUserName);
            statement.setString(2, CurrentUser);
            statement.executeUpdate();
        }
    }
    public Integer getNumComments(int postID) {
        String sql = "SELECT COUNT(*) FROM comments WHERE postId = ?";
        Integer numComments = null;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, postID);

            // Directly assign the ResultSet to `rs`
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    numComments = rs.getInt(1); // Get the first column (AVG result)
                    return numComments;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving post rating", e);
        }

        return 0;  // Return 0 if no rating found
    }
}
