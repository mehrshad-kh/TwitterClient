package com.mkh.twitter.client;

import com.mkh.twitter.client.controller.AbstractController;
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
        Parent root;
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/mkh/twitter/client/sign-in-view.fxml")));
        try {
            root = loader.load();
        } catch (IOException e) {
            displayAlert(e);
            return;
        }

//        Task<Iterator<Country>> task = new Task<>() {
//            @Override
//            public Iterator<Country> call() {
//                Iterator<Country> countryIterator = client.getMyCountries();
//                return countryIterator;
//            }
//        };
//
//        Thread daemonThread = new Thread(task);
//        daemonThread.setDaemon(true);
//        daemonThread.start();

        AbstractController controller = loader.getController();
        controller.setClient(client);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Twitter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void displayAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(e.toString());
        alert.showAndWait();
        e.printStackTrace();
    }
}