package com.example.cab302project.Controller;

import com.example.cab302project.HelloApplication;
import com.example.cab302project.Model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EditProfileController {

    public TextField descriptionField;
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    private UserProfileDAO userProfileDAO = new UserProfileDAO();

    @FXML
    public void initialize() {
        String currentUsername = LoginController.Session.getLoggedInUser();
        loadUserProfile(currentUsername);
    }

    private void loadUserProfile(String username) {
        User userProfile = userProfileDAO.getUserProfile(username);
        if (userProfile != null) {
            firstNameField.setText(userProfile.getFirstName());
            lastNameField.setText(userProfile.getLastName());
            usernameField.setText(userProfile.getUserName());
            emailField.setText(userProfile.getEmail());
            descriptionField.setText(userProfile.getDescription());
        }
    }

    @FXML
    public void saveProfile() throws SQLException, IOException {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();
        String description = descriptionField.getText();
        SQLitePostDOA sqLitePostDOA  = new SQLitePostDOA();
        SQLiteCommentDAO sqLiteCommentDAO = new SQLiteCommentDAO();
        SQLiteFollowDOA sqLiteFollowDOA = new SQLiteFollowDOA();
        sqLitePostDOA.updateUserPost(LoginController.Session.getLoggedInUser(), username);
        sqLiteCommentDAO.updateCommentUser(LoginController.Session.getLoggedInUser(), username);
        sqLiteFollowDOA.updateFollower(LoginController.Session.getLoggedInUser(), username);
        sqLiteFollowDOA.updateAccount(LoginController.Session.getLoggedInUser(), username);
        userProfileDAO.updateUserProfile(LoginController.Session.getLoggedInUser(), firstName, lastName, username, email, description);
        LoginController.Session.setLoggedInUser(username);
        // Add success message or alert to inform user the profile is updated
        Stage window = (Stage) usernameField.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Profile_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }

    @FXML
    private void handleCancel() {
        // Add logic to handle cancel action
        System.out.println("Changes canceled.");
    }
}