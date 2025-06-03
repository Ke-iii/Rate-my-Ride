package com.example.cab302project.Controller;

import com.example.cab302project.HelloApplication;
import com.example.cab302project.Model.RegistrationSystem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class RegisterController {
    public TextField descriptionTextField;
    private String filename = null;
    private int s = 0;
    private byte[] profile_image = null;
    @FXML
    private ImageView logoImageView;

    @FXML
    private TextField firstnameTextField;

    @FXML
    private TextField lastnameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField setpasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label confirmPasswordLabel;

    @FXML
    private Label registrationMessageLabel;

    @FXML
    private Button closeButton;

    @FXML
    private Label statusLabel;

    @FXML

    private RegistrationSystem registrationSystem = new RegistrationSystem();
// checks if fileds entered are valid, if they are create new user and redirect to login page, otherwise display error message
    public void registerButtonOnAction(ActionEvent event) throws IOException {
        if (validateInputs() && !registrationSystem.duplicateUser(emailTextField.getText())) {
            if (setpasswordField.getText().equals(confirmPasswordField.getText())) {
                registrationMessageLabel.setText("Registration successful!");
                confirmPasswordLabel.setText("Passwords match.");
                registerUser();
                Stage window = (Stage) closeButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login_UI.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
                window.setScene(scene);
            } else {
                confirmPasswordLabel.setText("Passwords does not match.");
            }
        }

    }
//redirects the user to the login page
    public void closeButtonOnAction(ActionEvent event) throws IOException {
        Stage window = (Stage) closeButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }
// ensures that user inputs are not null
    private boolean validateInputs() {
        if (firstnameTextField.getText().isEmpty() || lastnameTextField.getText().isEmpty() ||
                emailTextField.getText().isEmpty() || usernameTextField.getText().isEmpty() ||
                setpasswordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty()) {
            registrationMessageLabel.setText("Please fill in all fields.");
            return false;
        }
        return true;
    }
// gets currently entered data from text fields and calls the addUser method
    private void registerUser() {
        String firstName = firstnameTextField.getText();
        String lastName = lastnameTextField.getText();
        String email = emailTextField.getText();
        String username = usernameTextField.getText();
        String password = setpasswordField.getText();
        byte[] profilePicture = profile_image;
        String description = descriptionTextField.getText();
        registrationSystem.addUser(firstName, lastName, email, username, password, profilePicture, description);
    }
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
                profile_image = bos.toByteArray();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
}
