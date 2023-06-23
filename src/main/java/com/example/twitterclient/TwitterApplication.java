package com.example.twitterclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TwitterApplication extends Application {
    @Override
    public void start(Stage stage) {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("sign-in-view.fxml")));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            System.err.println("ERROR: An internal error occurred.");
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(root);
        stage.setTitle("Twitter");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}