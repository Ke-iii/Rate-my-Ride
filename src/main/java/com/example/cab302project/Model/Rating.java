package com.example.cab302project.Model;

public class Rating {
    int id;
    String username;
    float rating;
    int post_id;
    public Rating( String username, float rating, int post_id) {

        this.username = username;
        this.rating = rating;
        this.post_id = post_id;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public float getRating() {
        return rating;
    }
    public void setRating(float rating) {
        this.rating = rating;
    }
    public int getPost_id() {
        return post_id;
    }
    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

}
