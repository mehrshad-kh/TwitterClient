package com.mkh.twitter.client.view;


import com.mkh.twitter.ProfilePhoto;
import com.mkh.twitter.User;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.*;

import java.io.ByteArrayInputStream;

import com.mkh.twitter.client.controller.AbstractController;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

public abstract class AbstractPersonComponent  extends HBox {
    private static final String FONT_FAMILY = "Arial";
    private static final int FONT_SIZE = 14;
    private static final int PADDING = 10;
    private static final String BG_COLOR = "#FFFFFF";
    private static final String TEXT_COLOR = "#000000";
    private User user;
    private ProfilePhoto profilePhoto;
    private boolean isFollowing;

    Circle profilePicture;
    VBox nameAndUsername;
    Label name;
    Label username;
    Button followButton;
    Button unfollowButton;

    public AbstractPersonComponent(User user, ProfilePhoto profilePhoto){
        this.user = user;
        this.profilePhoto = profilePhoto;
        this.isFollowing = isFollowing;
        // create a circle to display the profile picture
        profilePicture = new Circle();
        byte[] bytes = profilePhoto.getPhoto().getBytes().toByteArray();
        Image image = new Image(new ByteArrayInputStream(bytes));
        profilePicture.setRadius(25);
        profilePicture.setFill(new javafx.scene.paint.ImagePattern(image));

        // create a label to display the name
        name = new Label(user.getFirstName()+" "+user.getLastName());
        name.setFont(javafx.scene.text.Font.font(FONT_FAMILY, javafx.scene.text.FontWeight.BOLD, FONT_SIZE));
        name.setTextFill(javafx.scene.paint.Color.web(TEXT_COLOR));

        // create a label to display the username
        username = new Label("@" + user.getUsername());
        username.setFont(javafx.scene.text.Font.font(FONT_FAMILY, javafx.scene.text.FontWeight.NORMAL, FONT_SIZE));
        username.setTextFill(javafx.scene.paint.Color.web(TEXT_COLOR));

        // create a vertical box to hold the name and username labels
        nameAndUsername = new VBox(name, username);
        nameAndUsername.setSpacing(5);

        // create a button to follow the user
        followButton = new Button("Follow");
        followButton.setStyle("-fx-background-color: #1DA1F2; -fx-text-fill: white;");

        // create a button to unfollow the user
        unfollowButton = new Button("Unfollow");
        unfollowButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #1DA1F2; -fx-border-color: #1DA1F2;");

        // add a listener to the follow button to change its visibility and text
        followButton.setOnAction(e -> {
            followButton.setVisible(false);
            unfollowButton.setVisible(true);
            followButton.setText("Following");
            // TODO: add logic to follow the user on the server side
        });

        // add a listener to the unfollow button to change its visibility and text
        unfollowButton.setOnAction(e -> {
            unfollowButton.setVisible(false);
            followButton.setVisible(true);
            followButton.setText("Follow");
            // TODO: add logic to unfollow the user on the server side
        });

        // set the padding and background color of the HBox
        this.setPadding(new javafx.geometry.Insets(PADDING));
        this.setStyle("-fx-background-color: " + BG_COLOR + ";");

        // add the components to the HBox with some spacing
        this.getChildren().addAll(profilePicture, nameAndUsername, followButton, unfollowButton);
        this.setSpacing(10);

    }
}
