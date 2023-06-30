package com.mkh.twitter.client.controller;

import com.mkh.Utility;
import com.mkh.twitter.Tweet;
import com.mkh.twitter.User;
import com.mkh.twitter.client.TweetRetrievalTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

public class MainController extends AbstractController {
    private static final double dividerPosition = 0.1;
    private ObservableList<Tweet> tweets;
    @FXML
    private Button homeButton;

    @FXML
    private VBox leftVbox;

    @FXML
    private Button newButton;

    @FXML
    private Button profileButton;

//    @FXML
//    private VBox rightVbox;
    @FXML private AnchorPane anchorPane;

    @FXML
    private SplitPane splitPane;

    public Button getHomeButton() {
        return homeButton;
    }

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

    private void displayDailyBriefing() {
        TweetRetrievalTask task = new TweetRetrievalTask(getClient(), getUser());
        task.valueProperty().addListener(new ChangeListener<Iterator<Tweet>>() {
            @Override
            public void changed(ObservableValue<? extends Iterator<Tweet>> observableValue,
                                Iterator<Tweet> oldValue,
                                Iterator<Tweet> newValue) {
                // System.out.println("changed() was called.");
                // System.out.println("newValue.hasNext(): " + newValue.hasNext());
                tweets = FXCollections.observableArrayList(Utility.Collections.convert(newValue));
                // System.out.println("tweetIterator.hasNext(): " + tweets.hasNext());
            }
        });
        Thread daemonThread = new Thread(task);
        daemonThread.setDaemon(true);
        daemonThread.start();

        System.out.println("setUpDailyBriefing(), tweets.size(): " + tweets.size());
//
//        while (tweets.hasNext()) {
//            Tweet tweet = tweets.next();
//            TweetComponent tweetComponent = new TweetComponent(getClient().searchUser(Integer.toString(tweet.getSenderId())),
//                    getClient().retrieveProfilePhoto(User.newBuilder().setId(tweet.getSenderId()).build()),
//                    tweet,
//                    getClient().retrieveTweetPhoto(tweet),
//                    getClient().retrieveRetweetCount(tweet),
//                    getClient().retrieveLikeCount(tweet),
//                    getClient().retrieveReplyCount(tweet));
//            System.out.println("salam");
//            rightVbox.getChildren().add(tweetComponent);
//
//        }

    }

    @FXML
    private void homeButtonActioned(ActionEvent event) {
        setAndResetEffectsForButtons((Button) event.getSource());
        // displayDailyBriefing();
    }

    @FXML
    private void newButtonActioned(ActionEvent event) {
        setAndResetEffectsForButtons((Button) event.getSource());
    }

    @FXML
    private void profileButtonActioned(ActionEvent event) {
        setAndResetEffectsForButtons((Button) event.getSource());

        AnchorPane profileAnchorPane;
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(SignInController.class.getResource("/com/mkh/twitter/client/profile-view.fxml")));
        try {
            profileAnchorPane = (AnchorPane) loader.load();
        } catch (IOException e) {
            AbstractController.displayAlert(e);
            return;
        }
        AbstractController controller = loader.getController();
        controller.setClient(getClient());
        controller.setUser(getUser());
        anchorPane.getChildren().clear();
        anchorPane.getChildren().setAll(profileAnchorPane.getChildren());
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
