package com.mkh.twitter.client.controller;

import com.mkh.twitter.client.TwitterApplication;
import com.mkh.twitter.client.TwitterClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Objects;


public class SignInController extends AbstractController {
    @FXML private Button signInButton;
    @FXML private Label createAccountLabel;

    @FXML
    private void signInButtonActioned(ActionEvent event) {
        Parent root;
        FXMLLoader loader
                = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/mkh/twitter/client/main-view.fxml")));
        try {
            root = loader.load();
        } catch (IOException e) {
            TwitterApplication.displayAlert(e);
            return;
        }

        MainController controller = loader.getController();
        controller.setClient(getClient());
        Scene scene = new Scene(root);
        Stage currentStage = (Stage) createAccountLabel.getScene().getWindow();
        currentStage.setScene(scene);
        currentStage.centerOnScreen();
    }

    @FXML
    private void createAccountLabelClicked(MouseEvent event) {
        Parent root;
        FXMLLoader loader
                = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/mkh/twitter/client/sign-up-view.fxml")));
        try {
            root = loader.load();
        } catch (IOException e) {
            TwitterApplication.displayAlert(e);
            return;
        }

        SignUpController controller = loader.getController();
        controller.setClient(getClient());
        controller.setUp();
        Scene scene = new Scene(root);
        Stage currentStage = (Stage) createAccountLabel.getScene().getWindow();
        currentStage.setScene(scene);
    }
}