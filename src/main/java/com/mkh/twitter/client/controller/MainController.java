package com.mkh.twitter.client.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.VBox;

public class MainController extends AbstractController {
    private static final double dividerPosition = 0.1;
    @FXML
    private Button homeButton;

    @FXML
    private VBox leftVbox;

    @FXML
    private Button newButton;

    @FXML
    private Button profileButton;

    @FXML
    private ScrollPane scorllPane;

    @FXML
    private SplitPane splitPane;
    public void initialize() {
        initializeSplitPane();
        initializeButtons();
    }


    private void initializeSplitPane() {
        SplitPane.Divider divider = splitPane.getDividers().get(0);
        divider.positionProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                divider.setPosition(dividerPosition);
            }
        });
    }

    private void initializeButtons() {
        initializeHomeButton();
        initializeNewButton();
        initializeProfileButton();
    }

    private void initializeHomeButton() {
        homeButton.prefWidthProperty().bind(leftVbox.widthProperty());
        homeButton.prefHeightProperty().bind(leftVbox.widthProperty());
    }

    private void initializeNewButton() {
        newButton.prefWidthProperty().bind(leftVbox.widthProperty());
        newButton.prefHeightProperty().bind(leftVbox.widthProperty());
    }

    private void initializeProfileButton() {
        profileButton.prefWidthProperty().bind(leftVbox.widthProperty());
        profileButton.prefHeightProperty().bind(leftVbox.widthProperty());
    }
    public void setUp() {
        this.setUpForYou();
    }
    public void setUpForYou(){


    }

    @FXML
    private void homeButtonActioned(ActionEvent event) {
        setAndResetEffectsForButtons((Button) event.getSource());
    }

    @FXML
    private void newButtonActioned(ActionEvent event) {
        setAndResetEffectsForButtons((Button) event.getSource());
    }

    @FXML
    private void profileButtonActioned(ActionEvent event) {
        setAndResetEffectsForButtons((Button) event.getSource());
    }

    private void setAndResetEffectsForButtons(Button button) {
        resetEffectsForAllButtons();
        setEffect(button);
    }

    private void setEffect(Button button) {
        button.setEffect(new Lighting());
    }

    private void resetEffectsForAllButtons() {
        for (Node node: leftVbox.getChildren()) {
            if (node instanceof Button button) {
                button.setEffect(null);
            }
        }
    }
}
