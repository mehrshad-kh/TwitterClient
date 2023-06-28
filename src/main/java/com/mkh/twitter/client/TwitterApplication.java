package com.mkh.twitter.client;

import com.mkh.twitter.Country;
import com.mkh.twitter.client.controllers.AbstractController;
import com.mkh.twitter.client.controllers.SignUpController;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Iterator;
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
            System.err.println("ERROR: An internal error occurred.");
            e.printStackTrace();
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

//        loader.setControllerFactory(new Callback<Class<?>, Object>() {
//            @Override
//            public Object call(Class<?> controllerClass) {
//                return new SignInController(getClient());
//            }
//        });

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
}