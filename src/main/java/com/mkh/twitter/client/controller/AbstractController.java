package com.mkh.twitter.client.controller;

import com.mkh.twitter.client.TwitterClient;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public abstract class AbstractController {
    private TwitterClient client;

    protected TwitterClient getClient() {
        return client;
    }

    public void setClient(TwitterClient client) {
        this.client = client;
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
