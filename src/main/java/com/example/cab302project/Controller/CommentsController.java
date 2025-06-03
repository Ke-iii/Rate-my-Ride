package com.example.cab302project.Controller;

import com.example.cab302project.HelloApplication;
import com.example.cab302project.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CommentsController {

    public MenuItem logout;
    public ImageView profileImageView2;
    public Text descriptionText;
    public Label Comments;
    public VBox commentsContainer;
    @FXML
    private VBox postsContainer;


    @FXML
    private VBox leftSection;  // The section that takes 25%

    @FXML
    private VBox rightSection; // The section that takes 75%

    SQLitePostDOA sqLitePostDOA = new SQLitePostDOA();
    SQLiteUserDOA sqLiteUserDOA = new SQLiteUserDOA();
    SQLiteCommentDAO sqLiteCommentDAO = new SQLiteCommentDAO();
    SQLiteRatingDOA sqLiteRatingDOA = new SQLiteRatingDOA();
    Post post = sqLitePostDOA.getPost(ProfileController.getId());


    // gets the current user who is logged in, and initialises list of posts
    @FXML
    public void initialize() throws SQLException {
        leftSection.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                // Bind the width properties to maintain 25%-75% ratio
                leftSection.prefWidthProperty().bind(newScene.widthProperty().multiply(0.40));
                rightSection.prefWidthProperty().bind(newScene.widthProperty().multiply(0.60));
            }
        });
        LoginController.Session session = new LoginController.Session();
        Post post = SQLitePostDOA.getPost(ProfileController.getId());
        List<Comment> commentsList = sqLiteCommentDAO.getCommentsByPostId(post.getId());
        VBox postBox = createPostBox(post);
        postsContainer.getChildren().add(postBox);
        for (Comment comment : commentsList) {
            VBox commentBox = createCommentBox(comment);
            commentsContainer.getChildren().add(commentBox);
        }



    }

    @FXML
    public void logOut() throws IOException {
        LoginController.Session.clearSession();
        Stage window = (Stage) Comments.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }

    // redirects user to the post creation page
    @FXML
    protected void onNextButtonClick() throws IOException {
        Stage window = (Stage) Comments.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Create_Post.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }

    // sets the username label to current user and number of posts label to the total posts the user has made

    // Dynamically adds all of the details for a post based on the list of Posts created by the user
    private VBox createCommentBox(Comment comment) {
        VBox commentBox = new VBox();
        commentBox.setAlignment(Pos.CENTER_LEFT);
        commentBox.setSpacing(10);
        commentBox.setPadding(new Insets(10));
        commentBox.getStyleClass().add("form-box"); // Add CSS class for styling

        // Create author label
        Label authorLabel = new Label("Author: " + comment.getAuthor());
        authorLabel.setStyle("-fx-font-weight: bold;");

        // Create timestamp label
        Label timestampLabel = new Label("Posted on: " + comment.getTimestamp());
        timestampLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: grey;");

        // Create comment text label
        Label commentText = new Label(comment.getText());
        commentText.setWrapText(true); // Enable text wrapping for long comments
        commentText.setStyle("-fx-font-size: 14px;");

        // Add all components to the commentBox
        commentBox.getChildren().addAll(authorLabel, timestampLabel, commentText);

        return commentBox;
    }

    private VBox createPostBox(Post post) {
        VBox postBox = new VBox();
        postBox.setAlignment(Pos.CENTER);
        postBox.setSpacing(10);
        postBox.setPadding(new Insets(10));
        postBox.getStyleClass().add("post-box"); // Add CSS class
        Label postTitle = new Label(post.getTitle());
        postTitle.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");
        ImageView postImageView = new ImageView();
        postImageView.setFitWidth(600);
        postImageView.setFitHeight(400);
        if (post.getPostImage() != null) {
            byte[] imageBytes = post.getPostImage();
            postImageView.setImage(new Image(new ByteArrayInputStream(imageBytes)));
        }
        Label postDescription = new Label(post.getDescription());
        postDescription.setWrapText(true);
        HBox detailsBox = new HBox();
        detailsBox.setAlignment(Pos.CENTER);
        detailsBox.setSpacing(10);
        Label postModel = new Label(post.getModel());
        Label postMake = new Label(post.getMake());
        Label postLocation = new Label(post.getLocation());

        detailsBox.getChildren().addAll(postModel, postMake, postLocation);

        HBox controlBox = new HBox();
        controlBox.setAlignment(Pos.CENTER);
        controlBox.setSpacing(10);



        // Add comment button and commentsBox
        Button addCommentButton = new Button("Add Comment");
        VBox commentsBox = new VBox();
        commentsBox.setSpacing(10);
        commentsBox.setPadding(new Insets(5, 0, 0, 0)); // Padding for comments
        Button ratingButton = new Button("Rating: " + sqLiteRatingDOA.getPostRating(post.getId()));
        ratingButton.setOnAction(e -> {
            // Prompt the user for a comment
            TextInputDialog ratingDialog = new TextInputDialog();
            ratingDialog.setTitle("Add Rating");
            ratingDialog.setHeaderText(null);
            ratingDialog.setContentText("Enter your rating:");

            // Show the dialog and capture the user's input
            Optional<String> ratingResult = ratingDialog.showAndWait();
            ratingResult.ifPresent(rating -> {
                if (rating.trim().isEmpty()) {
                    // Show a warning if the comment is empty
                    Alert emptyAlert = new Alert(Alert.AlertType.WARNING);
                    emptyAlert.setTitle("Warning");
                    emptyAlert.setHeaderText(null);
                    emptyAlert.setContentText("Comment cannot be empty!");
                    emptyAlert.showAndWait();
                } else {
                    Rating newRating = new Rating( LoginController.Session.getLoggedInUser(), Float.parseFloat(rating), post.getId() );                    try {
                        sqLiteRatingDOA.addRating(newRating);
                        sqLitePostDOA.incrementFollowers(post.getId());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    Stage window = (Stage) Comments.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Comment_Section_UI.fxml"));
                    Parent root = null;
                    try {
                        root = fxmlLoader.load();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
                    window.setScene(scene);

//                    // Add the comment to the commentsBox
//                    Label commentLabel = new Label(comment);
//                    commentLabel.setWrapText(true); // Wrap text for long comments
//                    commentsBox.getChildren().add(commentLabel);
                }
            });
        });
        addCommentButton.setOnAction(e -> {
            // Prompt the user for a comment
            TextInputDialog commentDialog = new TextInputDialog();
            commentDialog.setTitle("Add Comment");
            commentDialog.setHeaderText(null);
            commentDialog.setContentText("Enter your comment:");

            // Show the dialog and capture the user's input
            Optional<String> commentResult = commentDialog.showAndWait();
            commentResult.ifPresent(comment -> {
                if (comment.trim().isEmpty()) {
                    // Show a warning if the comment is empty
                    Alert emptyAlert = new Alert(Alert.AlertType.WARNING);
                    emptyAlert.setTitle("Warning");
                    emptyAlert.setHeaderText(null);
                    emptyAlert.setContentText("Comment cannot be empty!");
                    emptyAlert.showAndWait();
                } else {
                    Comment newComment = new Comment( post.getId(), comment,null, LoginController.Session.getLoggedInUser());                    try {
                        sqLiteCommentDAO.addComment(newComment);
                        sqLitePostDOA.incrementFollowers(post.getId());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    Stage window = (Stage) Comments.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Comment_Section_UI.fxml"));
                    Parent root = null;
                    try {
                        root = fxmlLoader.load();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
                    window.setScene(scene);

//                    // Add the comment to the commentsBox
//                    Label commentLabel = new Label(comment);
//                    commentLabel.setWrapText(true); // Wrap text for long comments
//                    commentsBox.getChildren().add(commentLabel);
                }
            });
        });

        controlBox.getChildren().addAll(addCommentButton, ratingButton);

        // Add all components to postBox
        postBox.getChildren().addAll(postImageView, postTitle, postDescription, detailsBox, controlBox, commentsBox);
        return postBox;
    }

    public void HomeButton(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) Comments.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Home_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }

    public void pageRedirect(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) Comments.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Profile_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }

    public void pageRedirectSettings(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) Comments.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("EditProfile.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }
}
