package com.example.cab302project.Controller;

import com.example.cab302project.HelloApplication;
import com.example.cab302project.Model.Post;
import com.example.cab302project.Model.SQLitePostDOA;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static com.example.cab302project.Controller.ProfileController.id;

public class EditPostController {

    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField PostTitleField;
    @FXML
    private TextField PostDescriptionField;
    @FXML
    private TextField CarModelField;
    @FXML
    private TextField CarMakeField;
    @FXML
    private TextField LocationField;


    private SQLitePostDOA sqLitePostDOA = new SQLitePostDOA();
    Post post = sqLitePostDOA.getPost(ProfileController.getId());


    // Sets the post ID and fetch the post details

    public void initialize() {
        if (post != null) {
            PostTitleField.setText(post.getTitle());
            PostDescriptionField.setText(post.getDescription());
            CarModelField.setText(post.getModel());
            CarMakeField.setText(post.getMake());
            LocationField.setText(post.getLocation());
        } else {
            System.out.println(id + "Post is null, cannot initialize fields.");
        }
    }
//redirects the user to the profile page
    public void onCancelButtonClick(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) cancelButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Profile_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }
//sets all post attributes to values entered by the user and calls post update method and redirects user to the profile page
    public void onSaveButtonClick(ActionEvent actionEvent) throws SQLException, IOException {
        post.setTitle(PostTitleField.getText());
        post.setDescription(PostDescriptionField.getText());
        post.setModel(CarModelField.getText());
        post.setMake(CarMakeField.getText());
        post.setLocation(LocationField.getText());
        System.out.println(post.getTitle() + Integer.toString(post.getId()) + " Post saved successfully");
        SQLitePostDOA postDAO = new SQLitePostDOA();
        postDAO.updatePost(post, id);
        Stage window = (Stage) saveButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Profile_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }
}