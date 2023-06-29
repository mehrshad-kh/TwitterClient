package com.mkh.twitter.client.controller;

import com.mkh.twitter.User;
import com.mkh.twitter.client.TwitterApplication;
import com.mkh.twitter.client.TwitterClient;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SignInController extends AbstractController {
    @FXML private Button signInButton;
    @FXML private Label createAccountLabel;
    @FXML private Label usernamePasswordErrorLabel;
    @FXML private PasswordField passwordField;
    @FXML private TextField usernameTextField;

    public void initialize() {
        initializeUsernamePasswordCheck();
    }

    private void initializeUsernamePasswordCheck() {
        passwordField.focusedProperty().addListener(new UsernamePasswordChangeListener());
        usernameTextField.focusedProperty().addListener(new UsernamePasswordChangeListener());
    }

    private class UsernamePasswordChangeListener implements ChangeListener<Boolean> {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (oldValue && !newValue) {
                if (usernamePasswordErrorLabel.isVisible()) {
                    usernamePasswordErrorLabel.setVisible(false);
                }
            }
        }
    }

    @FXML
    private void signInButtonActioned(ActionEvent event) {
        if (usernameTextField.getText().isBlank() || passwordField.getText().isBlank()) {
            usernamePasswordErrorLabel.setText("Please enter your username and password.");
            usernamePasswordErrorLabel.setVisible(true);
        } else {
            User signedInUser;
            try {
                signedInUser = getClient().performSignIn(usernameTextField.getText(), passwordField.getText());
            } catch (StatusRuntimeException e) {
                Status status = Status.fromThrowable(e);
                if (status.getCode() == Status.UNAUTHENTICATED.getCode()) {
                    usernamePasswordErrorLabel.setText("Your username or password is incorrect. Please try again.");
                    usernamePasswordErrorLabel.setVisible(true);
                } else {
                    AbstractController.displayStatusAlert(status);
                }
                return;
            }

            Parent root;
            FXMLLoader loader
                    = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/mkh/twitter/client/main-view.fxml")));
            try {
                root = loader.load();
            } catch (IOException e) {
                displayAlert(e);
                return;
            }

            MainController controller = loader.getController();
            controller.setClient(getClient());
            Scene scene = new Scene(root);
            Stage currentStage = findStage(createAccountLabel);
            currentStage.setScene(scene);
            currentStage.centerOnScreen();
        }
    }

    @FXML
    private void createAccountLabelClicked(MouseEvent event) {
        Parent root;
        FXMLLoader loader
                = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/mkh/twitter/client/sign-up-view.fxml")));
        try {
            root = loader.load();
        } catch (IOException e) {
            displayAlert(e);
            return;
        }

        SignUpController controller = loader.getController();
        controller.setClient(getClient());
        controller.setUp();
        Scene scene = new Scene(root);
        Stage currentStage = (Stage) createAccountLabel.getScene().getWindow();
        currentStage.setScene(scene);
    }

    public static void displaySignInView(Stage stage, TwitterClient client) {
        Parent root;
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(SignInController.class.getResource("/com/mkh/twitter/client/sign-in-view.fxml")));
        try {
            root = loader.load();
        } catch (IOException e) {
            AbstractController.displayAlert(e);
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
        stage.setTitle("Twitter");
        stage.setScene(scene);
        stage.show();

    }
}