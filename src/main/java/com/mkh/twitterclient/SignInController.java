package com.mkh.twitterclient;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SignInController {
    @FXML
    private Label createAccountLabel;

    @FXML
    private void createAccountLabelClicked(MouseEvent event) {
        Parent root;
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("sign-up-view.fxml")));
        try {
            root = loader.load();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.toString());
            alert.showAndWait();
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(root);
        Stage currentStage = (Stage) createAccountLabel.getScene().getWindow();
        currentStage.setScene(scene);
    }
}