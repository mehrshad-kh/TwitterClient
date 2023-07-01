package com.mkh.twitter.client.controller;

import com.mkh.twitter.client.TwitterApplication;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.IOException;
import com.mkh.twitter.client.Model;

public class ProfileController extends AbstractController {
    private final static Image reloadImage
            = new Image(String.valueOf(TwitterApplication.class.getResource("/images/reload.png")));

    @FXML private Button headerPhotoButton;
    @FXML private Button profilePhotoButton;
    @FXML private Button headerPhotoReloadButton;
    @FXML private Button profilePhotoReloadButton;
    @FXML private ImageView headerImageView;
    @FXML private ImageView profileImageView;
    @FXML private ImageView headerReloadImageView;
    @FXML private ImageView profileReloadImageView;
    @FXML private Label headerPhotoResultLabel;
    @FXML private Label profilePhotoResultLabel;
    @FXML private TextField firstNameTextField;

    public void initialize() {
        initializeHeaderPhotoResultLabel();
        initializeProfilePhotoResultLabel();
        initializeHeaderReloadImageView();
        initializeProfileReloadImageView();
        initializeHeaderPhotoReloadButton();
        initializeProfilePhotoReloadButton();
    }

    private void initializeHeaderPhotoResultLabel() {
        headerPhotoResultLabel.setVisible(false);
    }

    private void initializeProfilePhotoResultLabel() {
        profilePhotoResultLabel.setVisible(false);
    }

    private void initializeHeaderReloadImageView() {
        headerReloadImageView = new ImageView(reloadImage);
        headerReloadImageView.setFitHeight(20);
        headerReloadImageView.setFitWidth(20);
    }

    private void initializeProfileReloadImageView() {
        profileReloadImageView = new ImageView(reloadImage);
        profileReloadImageView.setFitHeight(20);
        profileReloadImageView.setFitWidth(20);
    }

    private void initializeHeaderPhotoReloadButton() {
        headerPhotoReloadButton.setText("");
        headerPhotoReloadButton.setGraphic(headerReloadImageView);
    }

    private void initializeProfilePhotoReloadButton() {
        profilePhotoReloadButton.setText("");
        profilePhotoReloadButton.setGraphic(profileReloadImageView);
    }

    public void setUp() {
        setUpUserInfo();
        setUpHeaderPhoto();
        setUpProfilePhoto();
    }

    public void setUpUserInfo() {
    }

    private void setUpHeaderPhoto() {
        headerPhotoReloadButton.fire();
    }

    private void setUpProfilePhoto() {
        profilePhotoReloadButton.fire();
    }

    @FXML
    private void headerPhotoReloadButtonActioned(ActionEvent event) {
        headerImageView.setImage((getClient().performRetrieveHeaderPhoto(getUser())));
    }

    @FXML
    private void profilePhotoReloadButtonActioned(ActionEvent event) {
        profileImageView.setImage(getClient().performRetrieveProfilePhoto(getUser()));
    }

    @FXML
    private void uploadHeaderPhotoButtonActioned(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Header Photo");
        fileChooser.getExtensionFilters()
                .add(new ExtensionFilter("Image Files", "*.png", "*.jpeg", "*.gif"));
        File file = fileChooser.showOpenDialog(findStage(firstNameTextField));
        if (file == null) {
            return;
        }

        Image image = new Image(file.toURI().toString());
        if (image.getHeight() > Model.maxHeightForHeaderPhotos || image.getWidth() > Model.maxWidthForHeaderPhotos) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Header photo must have a maximum size of "
                    + Model.maxHeightForHeaderPhotos + " by " + Model.maxWidthForHeaderPhotos + ".");
            alert.showAndWait();
            return;
        } else if (file.length() > Model.maxSizeForHeaderPhotos) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Size of header photo must be less than "
                    + Model.maxSizeForHeaderPhotos / Model.oneMegaByte
                    + " MB.");
            alert.showAndWait();
            return;
        }

        try {
            getClient().performSubmitHeaderPhoto(file, getUser());
        } catch (IndexOutOfBoundsException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Internal Error");
            alert.setContentText("Is file path of the correct format?\n" +
                    "Does it include file extension?");
            alert.showAndWait();
            return;
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Internal Error");
            alert.setContentText("Could not retrieve file path.");
            alert.showAndWait();
            return;
        } catch (StatusRuntimeException e) {
            displayStatusAlert(Status.fromThrowable(e));
            return;
        }

        headerPhotoResultLabel.setText("Uploaded successfully.");
        headerPhotoResultLabel.setTextFill(Color.GREEN);
        headerPhotoResultLabel.setVisible(true);
    }

    public void uploadProfilePhotoButtonActioned(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Profile Photo");
        fileChooser.getExtensionFilters()
                .add(new ExtensionFilter("Image Files", "*.png", "*.jpeg", "*.gif"));
        File file = fileChooser.showOpenDialog(findStage(firstNameTextField));
        if (file == null) {
            return;
        }

        Image image = new Image(file.toURI().toString());
        if (image.getHeight() > Model.maxHeightForProfilePhotos || image.getWidth() > Model.maxWidthForProfilePhotos) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Profile photo must have a maximum size of "
                    + Model.maxHeightForProfilePhotos + " by " + Model.maxWidthForProfilePhotos + ".");
            alert.showAndWait();
            return;
        } else if (file.length() > Model.maxSizeForProfilePhotos) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Size of profile photo must be less than "
                    + Model.maxSizeForProfilePhotos / Model.oneMegaByte
                    + " MB.");
            alert.showAndWait();
            return;
        }

        try {
            getClient().performSubmitProfilePhoto(file, getUser());
        } catch (IndexOutOfBoundsException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Internal Error");
            alert.setContentText("Is file path of the correct format?\n" +
                    "Does it include file extension?");
            alert.showAndWait();
            return;
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Internal Error");
            alert.setContentText("Could not retrieve file path.");
            alert.showAndWait();
            return;
        } catch (StatusRuntimeException e) {
            displayStatusAlert(Status.fromThrowable(e));
            return;
        }

        profilePhotoResultLabel.setText("Uploaded successfully.");
        profilePhotoResultLabel.setTextFill(Color.GREEN);
        profilePhotoResultLabel.setVisible(true);
    }
}
