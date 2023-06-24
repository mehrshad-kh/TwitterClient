package com.mkh.twitter.client;

import com.mkh.TwitterClient;
import com.mkh.twitter.Country;
import com.mkh.twitter.client.controllers.AbstractController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

public class TwitterApplication extends Application {
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
        TwitterClient client = new TwitterClient("localhost", 8080);
        AbstractController controller = loader.getController();
        controller.setClient(client);

        Iterator<Country> countries = client.retrieveCountries();
        while (countries.hasNext()) {
            Country country = countries.next();
            System.out.println(country.getNiceName());
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