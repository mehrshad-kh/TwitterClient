package com.mkh.twitter.client;

import com.mkh.twitter.client.controller.SignInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TwitterApplication extends Application {
    private final TwitterClient client;

    public TwitterApplication() {
        client = new TwitterClient("localhost", 8080);
    }

    @Override
    public void start(Stage primaryStage) {
        SignInController.displaySignInView(primaryStage, client);
    }

    public static void main(String[] args) {
        launch(args);
    }

}