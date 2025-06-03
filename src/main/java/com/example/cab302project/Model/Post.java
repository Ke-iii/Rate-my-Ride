package com.example.cab302project.Model;

/**
 * The Post class represents a post entity with attributes such as title, description, author, model, make, location,
 * post image, rating, number of comments, and number of shares. This class provides getter and setter methods for all fields
 * and allows for the creation of a Post object with specific attributes.
 */
public class Post {
    // All post attributes
    private int id;
    private String title;
    private String description;
    private String author;
    private String model;
    private String make;
    private String location;
    private byte[] postImage;
    private Integer rating;
    private Integer numComments;
    private Integer numshares;

    /**
     * Constructor to initialize a Post object with the provided parameters.
     * It sets the initial values for rating, number of comments, and number of shares to zero.
     *
     * @param title       The title of the post.
     * @param description A brief description of the post.
     * @param author      The author who created the post.
     * @param model       The model related to the post.
     * @param make        The make of the item related to the post.
     * @param location    The location associated with the post.
     * @param image       A byte array representing the image of the post.
     * @param rating      The initial rating of the post (set to 0).
     * @param numComments The number of comments on the post (set to 0).
     * @param numshares   The number of shares of the post (set to 0).
     */
    public Post(String title, String description, String author, String model, String make, String location, byte[] image, Integer rating, Integer numComments, Integer numshares) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.model = model;
        this.make = make;
        this.location = location;
        this.postImage = image;
        this.rating = 0;
        this.numComments = 0;
        this.numshares = 0;
    }

    /**
     * Gets the unique ID of the post.
     *
     * @return The post's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique ID of the post.
     *
     * @param id The ID to be set for the post.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the title of the post.
     *
     * @return The post's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the post.
     *
     * @param title The title to be set for the post.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the post.
     *
     * @return The post's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the post.
     *
     * @param description The description to be set for the post.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the author of the post.
     *
     * @return The post's author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the post.
     *
     * @param author The author to be set for the post.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the model related to the post.
     *
     * @return The model related to the post.
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model related to the post.
     *
     * @param model The model to be set for the post.
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets the make related to the post.
     *
     * @return The make related to the post.
     */
    public String getMake() {
        return make;
    }

    /**
     * Sets the make related to the post.
     *
     * @param make The make to be set for the post.
     */
    public void setMake(String make) {
        this.make = make;
    }

    /**
     * Gets the location associated with the post.
     *
     * @return The post's location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location associated with the post.
     *
     * @param location The location to be set for the post.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the byte array representing the image of the post.
     *
     * @return The post's image in byte array format.
     */
    public byte[] getPostImage() {
        return postImage;
    }

    /**
     * Sets the byte array representing the image of the post.
     *
     * @param postImage The image in byte array format to be set for the post.
     */
    public void setPostImage(byte[] postImage) {
        this.postImage = postImage;
    }

    /**
     * Gets the rating of the post.
     *
     * @return The post's rating.
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * Sets the rating of the post.
     *
     * @param rating The rating to be set for the post.
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * Gets the number of comments on the post.
     *
     * @return The number of comments on the post.
     */
    public Integer getNumComments() {
        return numComments;
    }

    /**
     * Sets the number of comments on the post.
     *
     * @param numComments The number of comments to be set for the post.
     */
    public void setNumComments(Integer numComments) {
        this.numComments = numComments;
    }

    /**
     * Gets the number of shares of the post.
     *
     * @return The number of shares of the post.
     */
    public Integer getNumshares() {
        return numshares;
    }

    /**
     * Sets the number of shares of the post.
     *
     * @param numshares The number of shares to be set for the post.
     */
    public void setNumshares(Integer numshares) {
        this.numshares = numshares;
    }
}
