package com.mkh.twitter.client.controller;

import com.mkh.twitter.User;
import com.mkh.twitter.client.TwitterClient;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public abstract class AbstractController {
    private TwitterClient client = null;
    private User user = null;

    protected TwitterClient getClient() {
        return client;
    }

    public void setClient(TwitterClient client) {
        this.client = client;
    }

    protected User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static void displayAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(e.toString());
        alert.showAndWait();
        e.printStackTrace();
    }

    public static void displayStatusAlert(Status status) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(status.getCode().toString());
        alert.setContentText(status.getDescription());
    }

    public static Stage findStage(Node node) {
        return (Stage) node.getScene().getWindow();
    }
}
