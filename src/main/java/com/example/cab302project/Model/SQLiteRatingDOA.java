package com.example.cab302project.Model;

import java.sql.*;

public class SQLiteRatingDOA {
    private Connection connection;

    /**
     * Constructor for SQLiteCommentDAO. Initializes the database connection
     * and creates the comments table if it doesn't already exist.
     */
    public SQLiteRatingDOA() {
        connection = SqliteConnection.connect(); // Assuming you have a method to establish a connection
        createCommentTable(); // Create comments table if it doesn't exist
    }

    /**
     * Creates the 'comments' table in the database if it does not already exist.
     */
    public void createCommentTable() {
        String sql = "CREATE TABLE IF NOT EXISTS ratings (" +
                "ratingId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "postId INTEGER NOT NULL," +
                "rating FlOAT NOT NULL," +
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
    public void addRating(Rating rating) throws SQLException {
        String sql = "INSERT INTO ratings(postId, rating, author) VALUES (?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, rating.getPost_id());
            pstmt.setFloat(2, rating.getRating());
            pstmt.setString(3, rating.getUsername());
            pstmt.executeUpdate();
        }
    }
    public Float getPostRating(int postID) {
        String sql = "SELECT AVG(rating) FROM ratings WHERE postId = ?";
        Float rating = null;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, postID);

            // Directly assign the ResultSet to `rs`
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    rating = rs.getFloat(1);  // Get the first column (AVG result)
                    return rating;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving post rating", e);
        }

        return 0.0f;  // Return 0 if no rating found
    }

}
