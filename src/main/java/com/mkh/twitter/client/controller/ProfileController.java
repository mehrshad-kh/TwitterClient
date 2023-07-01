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

    @FXML private Button reloadButton;
    @FXML private ImageView headerImageView;
    @FXML private ImageView reloadImageView;
    @FXML private Label headerPhotoUploadResultLabel;
    @FXML private TextField firstNameTextField;

    public void initialize() {
        initializeHeaderPhotoUploadResultLabel();
        initializeReloadImageView();
        initializeReloadButton();
    }

    private void initializeHeaderPhotoUploadResultLabel() {
        headerPhotoUploadResultLabel.setVisible(false);
    }

    private void initializeReloadImageView() {
        reloadImageView = new ImageView(reloadImage);
        reloadImageView.setFitHeight(20);
        reloadImageView.setFitWidth(20);
    }

    private void initializeReloadButton() {
        reloadButton.setText("");
        reloadButton.setGraphic(reloadImageView);
    }

    public void setUp() {
        reloadButton.fire();
    }

    @FXML
    private void reloadButtonActioned(ActionEvent event) {
        headerImageView.setImage((getClient().performRetrieveHeaderPhoto(getUser())));
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

        headerPhotoUploadResultLabel.setText("Uploaded successfully.");
        headerPhotoUploadResultLabel.setTextFill(Color.GREEN);
        headerPhotoUploadResultLabel.setVisible(true);
    }
}
