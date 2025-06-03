package com.example.cab302project.Controller;

import com.example.cab302project.HelloApplication;
import com.example.cab302project.Model.SQLiteUserDOA;
import com.example.cab302project.Model.ViewingUser;
import com.example.cab302project.Observer;
import com.example.cab302project.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeController implements Subject {
    public Button search;
    public ImageView profileImageView;
    public Label FindLabel;
    @FXML
    private TextField accountSearchField;
    @FXML
    private ListView<String> accountListView;

    private SQLiteUserDOA sqLiteUserDOA = new SQLiteUserDOA();
    private ObservableList<String> accounts = sqLiteUserDOA.getAllUsers(LoginController.Session.getLoggedInUser());
    private ObservableList<String> noSearch = FXCollections.observableArrayList();


    private List<Observer> observers = new ArrayList<>();

    public void initialize() throws SQLException {
        accountListView.setItems(noSearch);


        addObserver(new AccountListViewObserver(accountListView, accounts));


        accountSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            notifyObservers();
        });


        accountListView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                String selectedUser = accountListView.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    try {

                        handleRowClick();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });


        LoginController.Session session = new LoginController.Session();
        Image image = sqLiteUserDOA.getProfilePicture(session.getLoggedInUser());
        if (image != null) {
            profileImageView.setImage(image);
        }
    }


    private void handleRowClick() throws IOException {
        ViewingUser.setSelectedUser(accountListView.getSelectionModel().getSelectedItem());
        Stage window = (Stage) FindLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Other_Profile_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }


    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void addObserver(java.util.Observer observer) {

    }

    @Override
    public void removeObserver(java.util.Observer observer) {

    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(accountSearchField.getText()); // Pass the search term to the observers
        }
    }


    public void HomeButton(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) FindLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Home_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }

    @FXML
    public void logOut() throws IOException {
        LoginController.Session.clearSession();
        Stage window = (Stage) FindLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }


    @FXML
    protected void onNextButtonClick() throws IOException {
        Stage window = (Stage) FindLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Create_Post.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }

    public void pageRedirect(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) FindLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Profile_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }
}
