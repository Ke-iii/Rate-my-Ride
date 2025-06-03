package com.example.cab302project.Model;

/**
 * Represents a comment made on a post in the system.
 */
public class Comment {
    private int commentId;
    private int postId;
    private String text;
    private String timestamp;
    private String author;

    /**
     * Constructs a new Comment object with the specified details.
     *
     * @param postId    the ID of the post this comment is associated with
     * @param text      the content of the comment
     * @param timestamp the time the comment was created
     * @param author    the author of the comment
     */
    public Comment(int postId, String text, String timestamp, String author) {
        this.postId = postId;
        this.text = text;
        this.timestamp = timestamp;
        this.author = author;
    }

    /**
     * Gets the unique ID of the comment.
     *
     * @return the comment ID
     */
    public int getCommentId() {
        return commentId;
    }

    /**
     * Sets the unique ID of the comment.
     *
     * @param commentId the new comment ID
     */
    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    /**
     * Gets the ID of the post this comment is associated with.
     *
     * @return the post ID
     */
    public int getPostId() {
        return postId;
    }

    /**
     * Sets the ID of the post this comment is associated with.
     *
     * @param postId the new post ID
     */
    public void setPostId(int postId) {
        this.postId = postId;
    }

    /**
     * Gets the content of the comment.
     *
     * @return the comment text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the content of the comment.
     *
     * @param text the new comment text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the timestamp when the comment was created.
     *
     * @return the timestamp of the comment
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp when the comment was created.
     *
     * @param timestamp the new timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Gets the author of the comment.
     *
     * @return the author of the comment
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the comment.
     *
     * @param author the new author
     */
    public void setAuthor(String author) {
        this.author = author;
    }
}
