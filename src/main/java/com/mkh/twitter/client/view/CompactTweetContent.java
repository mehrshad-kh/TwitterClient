package com.mkh.twitter.client.view;

import com.mkh.twitter.Tweet;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class CompactTweetContent extends HBox
        implements  ITweetContent{
    // A label to display the tweet text
    private final Label tweetLabel;

    // A constructor that takes a tweet object and creates a label for it
    public CompactTweetContent(Tweet tweet) {
        tweetLabel = new Label(tweet.getText());
        // Add some padding and alignment to the label
        tweetLabel.setPadding(new Insets(5, 5, 5, 5));
        tweetLabel.setAlignment(Pos.CENTER);
        // Add the label to the HBox
        this.getChildren().add(tweetLabel);
    }


}
