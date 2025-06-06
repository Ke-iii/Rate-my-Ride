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

public class ViewingProfileController {
    public MenuItem logout;
    public ImageView profileImageView2;
    public Text descriptionText;
    public Label followerCount;
    @FXML
    private VBox postsContainer;

    @FXML
    private Label welcomeText1;

    @FXML
    private Label welcomeText2;

    @FXML
    private Label welcomeText3;

    @FXML
    private Label welcomeText4;

    @FXML
    private Label welcomeText5;

    @FXML
    private Region spacer;

    @FXML
    private Region spacer2;

    @FXML
    private Button followButton;
    @FXML
    private ImageView profileImageView;
    @FXML
    private VBox leftSection;  // The section that takes 25%

    @FXML
    private VBox rightSection; // The section that takes 75%

    SQLitePostDOA sqLitePostDOA = new SQLitePostDOA();
    SQLiteUserDOA sqLiteUserDOA = new SQLiteUserDOA();
    SQLiteCommentDAO sqLiteCommentDAO = new SQLiteCommentDAO();
    SQLiteRatingDOA sqLiteRatingDOA = new SQLiteRatingDOA();

    private LoginController.Session session;
    public static int id;

    public void setId(int newId) {
        id = newId;
    }

    public static int getId() {
        return id;
    }
    // gets the current user who is logged in, and initialises list of posts
    @FXML
    public void initialize() throws SQLException {
        SQLiteFollowDOA sqLiteFollowDOA = new SQLiteFollowDOA();
        try {
            if ((sqLiteFollowDOA.isFollower(ViewingUser.getSelectedUser(),LoginController.Session.getLoggedInUser()))){
//                followButton.setDisable(true);
                followButton.setText("UnFollow");
                followButton.setStyle("-fx-text-fill: red;");
                    followButton.setOnAction((ActionEvent event) -> {
                        try {
                            sqLiteFollowDOA.unfollow(ViewingUser.getSelectedUser(), LoginController.Session.getLoggedInUser());
                            sqLiteUserDOA.incrementFollowers(ViewingUser.getSelectedUser(), LoginController.Session.getLoggedInUser(), -1);
                            Stage window = (Stage) followButton.getScene().getWindow();
                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Other_Profile_UI.fxml"));
                            Parent root = fxmlLoader.load();
                            Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
                            window.setScene(scene);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        leftSection.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                // Bind the width properties to maintain 25%-75% ratio
                leftSection.prefWidthProperty().bind(newScene.widthProperty().multiply(0.25));
                rightSection.prefWidthProperty().bind(newScene.widthProperty().multiply(0.75));
            }
        });
        session = new LoginController.Session();
        changeLabelText();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        Image image = sqLiteUserDOA.getProfilePicture(session.getLoggedInUser());
        Image profileImage = sqLiteUserDOA.getProfilePicture(ViewingUser.getSelectedUser());
        if (image != null) {
            profileImageView.setImage(image);
            profileImageView2.setImage(profileImage);
        }
//        String currentUser = session.getLoggedInUser();
        List<Post> posts = SQLitePostDOA.getPostsByAuthor(ViewingUser.getSelectedUser());
        for (Post post : posts) {
            System.out.println("Fetched Post ID: " + post.getId());
            VBox postBox = createPostBox(post);
            postsContainer.getChildren().add(postBox);
        }

    }
    @FXML
    public void logOut() throws IOException {
        LoginController.Session.clearSession();
        Stage window = (Stage) followButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);

    }
    //redirects user to the post creation page
    @FXML
    protected void onNextButtonClick() throws IOException {
        Stage window = (Stage) followButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Create_Post.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }
    // sets the username label to current user and number of posts label to the total posts the user has made
    private void changeLabelText() throws SQLException {
        User user = sqLiteUserDOA.getUser(ViewingUser.getSelectedUser());

        welcomeText1.setText(ViewingUser.getSelectedUser());
        followerCount.setText(String.valueOf(user.getFollowers()));
        welcomeText5.setText(String.valueOf(sqLitePostDOA.getNumPosts(ViewingUser.getSelectedUser())));
        descriptionText.setText(user.getDescription());
    }
    // Dynamically adds all of the details for a post based on the list of Posts created by the user
    private VBox createPostBox(Post post) {
        VBox postBox = new VBox();
        postBox.setAlignment(Pos.CENTER_LEFT);
        postBox.setSpacing(10);
        postBox.setPadding(new Insets(10));
        postBox.getStyleClass().add("post-box"); // Add CSS class

        ImageView postImageView = new ImageView();
        postImageView.setFitWidth(600);
        postImageView.setFitHeight(400);
        if (post.getPostImage() != null) {
            byte[] imageBytes = post.getPostImage();
            postImageView.setImage(new Image(new ByteArrayInputStream(imageBytes)));
        }

        Label postTitle = new Label(post.getTitle());
        postTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label postDescription = new Label(post.getDescription());
        postDescription.setWrapText(true);

        HBox detailsBox = new HBox();
        detailsBox.setAlignment(Pos.CENTER_LEFT);
        detailsBox.setSpacing(10);
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
                    Stage window = (Stage) welcomeText1.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ProfileController.fxml"));
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
        Button commentButton = new Button("Comments: " + sqLiteCommentDAO.getNumComments(post.getId()));
        commentButton.setOnAction(e -> {
            int id = post.getId();
            setId(id);
            Stage window = (Stage) welcomeText1.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Comment_Section_UI.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
            window.setScene(scene);
        });


        detailsBox.getChildren().addAll(ratingButton, commentButton);

        HBox controlBox = new HBox();
        controlBox.setAlignment(Pos.CENTER_LEFT);
        controlBox.setSpacing(10);



        // Add comment button and commentsBox
        Button addCommentButton = new Button("Add Comment");
        VBox commentsBox = new VBox();
        commentsBox.setSpacing(10);
        commentsBox.setPadding(new Insets(5, 0, 0, 0)); // Padding for comments

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
                    Stage window = (Stage) welcomeText1.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ProfileController.fxml"));
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

        controlBox.getChildren().addAll(addCommentButton);

        // Add all components to postBox
        postBox.getChildren().addAll(postImageView, postTitle, postDescription, detailsBox, controlBox, commentsBox);
        return postBox;
    }


    public void HomeButton(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) followButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Home_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }
    public void pageRedirect(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) followButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Profile_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }

    public void followOnAction(ActionEvent actionEvent) throws IOException {
        SQLiteFollowDOA sqliteFollowDOA = new SQLiteFollowDOA();
        sqliteFollowDOA.insertFollower(ViewingUser.getSelectedUser(), LoginController.Session.getLoggedInUser());
        sqLiteUserDOA.incrementFollowers(ViewingUser.getSelectedUser(), LoginController.Session.getLoggedInUser(), 1);
        Stage window = (Stage) followButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Other_Profile_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }
    private void displayComments(Post post) throws SQLException {
        Alert commentsAlert = new Alert(Alert.AlertType.INFORMATION);
        commentsAlert.setTitle("Comments for: " + post.getTitle());
        commentsAlert.setHeaderText(null);

        // Fetch comments for the given post
        List<Comment> commentsList = sqLiteCommentDAO.getCommentsByPostId(post.getId());

        // StringBuilder to accumulate all comments
        StringBuilder comments = new StringBuilder();

        // Loop through the list of comments and append them
        for (Comment comment : commentsList) {
            comments.append("Author: ").append(comment.getAuthor())
                    .append("\n")
                    .append("Comment: ").append(comment.getText())
                    .append("\n")
                    .append("Posted at: ").append(comment.getTimestamp())
                    .append("\n\n");  // Add extra newline for better readability
        }

        // If there are no comments, display a placeholder message
        if (commentsList.isEmpty()) {
            comments.append("No comments yet for this post.");
        }

        // Set the accumulated comments as the content of the alert
        commentsAlert.setContentText(comments.toString());

        commentsAlert.showAndWait();
    }
}
