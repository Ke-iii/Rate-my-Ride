package com.example.cab302project;

import com.example.cab302project.Model.SqliteConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;

import java.io.IOException;

public class HelloApplication extends Application {

    public static final int WIDTH = 640;
    public static final int HEIGHT = 700;


    @Override
    public void start(Stage stage) throws IOException {

        SqliteConnection.setupDatabase();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login_UI.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("RateMyRide!");
        stage.setScene(scene);

        // Get the screen size
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Set stage width and height to match the screen size
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());

        // Optionally, center the window on the screen
        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
