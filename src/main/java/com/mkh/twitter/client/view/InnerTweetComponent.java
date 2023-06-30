package com.mkh.twitter.client.view;

import com.mkh.twitter.ProfilePhoto;
import com.mkh.twitter.Tweet;
import com.mkh.twitter.TweetPhoto;
import com.mkh.twitter.User;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.geometry.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.ByteArrayInputStream;
import java.time.*;
import java.time.format.*;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;


public class InnerTweetComponent extends AnchorPane {
    private static final int PADDING = 10;
    private static final String BG_COLOR = "#FFFFFF";
    private final FullTweetContent fullTweetContent;
    private final Label name;
    private final Label username ;
    private final Label date;
    private final Circle profilePicture;

    public  InnerTweetComponent(User tweetSender, ProfilePhoto profilePhoto,  Tweet tweet, TweetPhoto tweetPhoto){
        fullTweetContent = new FullTweetContent(tweet, tweetPhoto);
        name = new Label();
        date = new Label(timeDifference(tweet));
        username  = new Label();
        profilePicture = new Circle(24.0);
        profilePicture.setFill(Color.web("#000000"));
        profilePicture.setStroke(Color.web("#000000"));
        profilePicture.setFill(new ImagePattern(new Image(new ByteArrayInputStream(profilePhoto.getPhoto().getBytes().toByteArray()))));
        profilePicture.setStrokeWidth(1);
        this.getChildren().addAll(name, username , date, profilePicture, fullTweetContent);
        this.setLocation();

        // bound the labels to the properties of the user object using string converters
        name.textProperty().bind(Bindings.concat(tweetSender.getFirstName(), " ", tweetSender.getLastName()));
        username.textProperty().bind(Bindings.concat("@", tweetSender.getUsername()));

        // added some CSS styles to the components
        this.setStyle("-fx-font-family: Arial; -fx-font-size: 14px;");
        this.setPadding(new Insets(PADDING)); // set the padding around the grid pane
        this.setBackground(new Background(new BackgroundFill(Color.web(BG_COLOR), new CornerRadii(10), Insets.EMPTY)));
        this.setBorder(new Border(new BorderStroke(Color.web("#E1E8ED"), BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));
        name.setStyle("-fx-font-weight: bold;");
        username.setStyle("-fx-text-fill: #657786;");
        date.setStyle("-fx-text-fill: #657786;");
    }
    public void setLocation() {
        this.setPadding(new Insets(PADDING));
        this.setBackground(new Background(new BackgroundFill(Color.web(BG_COLOR), new CornerRadii(10), Insets.EMPTY)));
        this.setBorder(new Border(new BorderStroke(Color.web("#E1E8ED"), BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));

        AnchorPane.setTopAnchor(this, 10.0);
        AnchorPane.setLeftAnchor(this, 10.0);
        AnchorPane.setRightAnchor(this, 10.0);

        AnchorPane.setTopAnchor(name, 10.0);
        AnchorPane.setLeftAnchor(name, 60.0);

        AnchorPane.setTopAnchor(username , 10.0);
        AnchorPane.setLeftAnchor(username , 200.0);

        AnchorPane.setTopAnchor(date, 10.0);
        AnchorPane.setLeftAnchor(date, 350.0);

        AnchorPane.setTopAnchor(profilePicture, 10.0);
        AnchorPane.setLeftAnchor(profilePicture, 10.0);

        AnchorPane.setTopAnchor(fullTweetContent, 40.0);
        AnchorPane.setLeftAnchor(fullTweetContent, 60.0);
        AnchorPane.setRightAnchor(fullTweetContent, 10.0);
    }
    public String timeDifference(Tweet tweet){

        // Get the timestamp string
        String timestampString;
        timestampString = tweet.getDateCreated();

        // Convert the timestamp string to a LocalDateTime object
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime timestamp = LocalDateTime.parse(timestampString, formatter);

        // Convert the LocalDateTime to a ZonedDateTime using the system default time zone
        ZonedDateTime zonedTimestamp = ZonedDateTime.of(timestamp, ZoneId.systemDefault());

        // Convert the ZonedDateTime to an Instant
        Instant instantTimestamp = zonedTimestamp.toInstant();

        // Get the current time in UTC as an Instant
        Instant now = Instant.now();

        // Calculate the duration between the two Instants
        Duration duration = Duration.between(instantTimestamp, now);

        // If the duration is less than an hour, print the duration in minutes
        if (duration.toMinutes() < 60) {
            return  duration.toMinutes() + " m ago" ;
        }
        // If the duration is less than a day, print the duration in hours
        else if (duration.toHours() < 24) {
            return duration.toHours() + " h ago";
        }
        // Otherwise, print the duration in days
        else {
            return duration.toDays() + " d ago";
        }
    }
}
