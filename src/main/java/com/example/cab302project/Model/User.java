package com.example.cab302project.Model;

/**
 * The User class represents a user entity with attributes such as first name, last name, email, username,
 * number of followers, number of posts, profile picture, and description. This class provides getter and setter
 * methods for each field and allows for the creation of a User object with specific attributes.
 */
public class User {
    // User attributes
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private Integer followers;
    private Integer numberOfPosts;
    private byte[] profilePicture;
    private String description;

    /**
     * Constructor to initialize a User object with the provided parameters.
     *
     * @param id           The unique ID of the user.
     * @param firstName    The first name of the user.
     * @param lastName     The last name of the user.
     * @param email        The email address of the user.
     * @param userName     The username of the user.
     * @param followers    The number of followers the user has.
     * @param numberOfPosts The number of posts the user has made.
     * @param description  A brief description or bio of the user.
     */
    public User(int id, String firstName, String lastName, String email, String userName, Integer followers, Integer numberOfPosts, String description) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.followers = followers;
        this.numberOfPosts = numberOfPosts;
        this.description = description;
    }

    /**
     * Gets the unique ID of the user.
     *
     * @return The user's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique ID of the user.
     *
     * @param id The ID to be set for the user.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the first name of the user.
     *
     * @return The user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName The first name to be set for the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return The user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName The last name to be set for the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email address of the user.
     *
     * @return The user's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email The email address to be set for the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the username of the user.
     *
     * @return The user's username.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username of the user.
     *
     * @param userName The username to be set for the user.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the number of followers the user has.
     *
     * @return The number of followers.
     */
    public Integer getFollowers() {
        return followers;
    }

    /**
     * Sets the number of followers the user has.
     *
     * @param followers The number of followers to be set for the user.
     */
    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    /**
     * Gets the number of posts the user has made.
     *
     * @return The number of posts the user has made.
     */
    public Integer getNumberOfPosts() {
        return numberOfPosts;
    }

    /**
     * Sets the number of posts the user has made.
     *
     * @param numberOfPosts The number of posts to be set for the user.
     */
    public void setNumberOfPosts(Integer numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }

    /**
     * Gets the description or bio of the user.
     *
     * @return The user's description or bio.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description or bio of the user.
     *
     * @param description The description to be set for the user.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
