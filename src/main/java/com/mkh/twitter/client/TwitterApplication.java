package com.mkh.twitter.client;

import com.mkh.TwitterClient;
import com.mkh.twitter.Country;
import com.mkh.twitter.client.controllers.AbstractController;
import io.grpc.ManagedChannel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

public class TwitterApplication extends Application {
    private final TwitterClient client;

    public TwitterApplication() {
        client = new TwitterClient("localhost", 8080);
    }

//    @Override
//    public void init() {
//        client = new TwitterClient("localhost", 8080);
//    }

    @Override
    public void start(Stage stage) {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/mkh/twitter/client/sign-in-view.fxml")));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            System.err.println("ERROR: An internal error occurred.");
            e.printStackTrace();
            return;
        }
        System.out.println(((ManagedChannel) client.getChannel()).getState(false));

        AbstractController controller = loader.getController();
        controller.setClient(client);
        Scene scene = new Scene(root);
        stage.setTitle("Twitter");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}