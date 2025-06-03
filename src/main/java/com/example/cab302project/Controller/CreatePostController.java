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
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.example.cab302project.Controller.LoginController.Session.getLoggedInUser;

public class CreatePostController {

    private String filename = null;
    private int s = 0;
    private byte[] post_image = null;

    @FXML
    private Button cancelButton;
    @FXML
    private TextField PostTitleField;

    @FXML
    private TextField PostDescriptionField;

    @FXML
    private TextField CarMakeField;

    @FXML
    private TextField CarModelField;

    @FXML
    private TextField LocationField;

    @FXML
    private Button postButton;

    @FXML
    private Button uploadButton;

    private Image selectedImage;

    @FXML
    public void initialize() {
        uploadButton.setOnAction(this::handleUploadFile);
    }
    //Method to allow user to uplad image file. This method also converts the file to bytes so that it can be stored as a BLOB in the database

    @FXML
    public void handleUploadFile(ActionEvent actionEvent) {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        if (file != null) {
            filename = file.getAbsolutePath();
            try {
                FileInputStream fis = new FileInputStream(file);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int readNum;
                while ((readNum = fis.read(buf)) != -1) {
                    bos.write(buf, 0, readNum);
                }
                post_image = bos.toByteArray();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    //resets all post attributes and redirects the user to the profiles page

    public void onCancelButtonClick(ActionEvent actionEvent) throws IOException {
        PostTitleField.clear();
        PostDescriptionField.clear();
        CarMakeField.clear();
        CarModelField.clear();
        LocationField.clear();
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Profile_UI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }
// Sets post attributes to values entered by the user in the text fields and redirects user to the profile page
    public void onPostButtonClick(ActionEvent actionEvent) throws IOException {
        String postTitle = PostTitleField.getText();
        String postDescription = PostDescriptionField.getText();
        String carMake = CarMakeField.getText();
        String carModel = CarModelField.getText();
        String location = LocationField.getText();
        byte[] image = post_image;
        String userName = getLoggedInUser();
        if ( postTitle == "" || postDescription == "" || carModel == "" || location == "" || carMake == "") {
            return;
        }
        else {
        Post newPost = new Post(postTitle, postDescription, "author", carModel, carMake, location, image, 0, 0, 0);
        SQLitePostDOA postDAO = new SQLitePostDOA();
        postDAO.addPost(newPost, userName);
        Stage window = (Stage) postButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Profile_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
        }
    }

    private void onAdd() {
        final String DEFAULT_TITLE = "";
        final String DEFAULT_DESCRIPTION = "";
        final String DEFAULT_MAKE = "";
        final String DEFAULT_MODEL = "";
        final String DEFAULT_LOCATION = "";
        final byte[] DEFAULT_IMAGE = null;
        final Integer DEFAULT_RATING = 0;
        final Integer DEFAULT_NUMBERCOMMENTS = 0;
        final Integer DEFAULT_NUMBERSHARES = 0;

        Post newPost = new Post(DEFAULT_TITLE, DEFAULT_DESCRIPTION, DEFAULT_MAKE, DEFAULT_MODEL, DEFAULT_LOCATION, DEFAULT_TITLE, DEFAULT_IMAGE, DEFAULT_RATING, DEFAULT_NUMBERCOMMENTS, DEFAULT_NUMBERSHARES);
        PostManager.addPost(newPost);
    }
}
