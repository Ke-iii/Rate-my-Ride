package com.example.cab302project.Controller;

import com.example.cab302project.HelloApplication;
import com.example.cab302project.Model.LoginSystem;
import com.example.cab302project.Model.SQLitePostDOA;
import com.example.cab302project.Model.SqliteConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField UsernameField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Label LoginStatus;

    private LoginSystem loginSystem = new LoginSystem();

    @FXML
    public void HandleLoginAction() {
        String username = UsernameField.getText();
        String password = PasswordField.getText();
        if (loginSystem.checkLogin(username, password)) {
            LoginStatus.setText("Login successful!");
            Session.setLoggedInUser(username);
            try {
                Stage window = (Stage) UsernameField.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Profile_UI.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
                window.setScene(scene);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            LoginStatus.setText("Login failed. Invalid username or password.");
        }
    }

    public LoginController() {
    }

    public void HandleSignUp(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) UsernameField.getScene().getWindow();


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registration.fxml"));
        Parent root = fxmlLoader.load();


        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);


        window.setScene(scene);
    }

    public static class Session {
        private static String loggedInUser;

        public static void setLoggedInUser(String username) {
            loggedInUser = username;
        }

        public static String getLoggedInUser() {
            return loggedInUser;
        }

        public static void clearSession() {
            loggedInUser = null;
        }
    }

}
