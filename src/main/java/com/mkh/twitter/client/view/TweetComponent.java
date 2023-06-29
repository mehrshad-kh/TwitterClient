package com.mkh.twitter.client.view;
import com.mkh.twitter.client.TwitterApplication;
import com.mkh.twitter.ProfilePhoto;
import com.mkh.twitter.Tweet;
import com.mkh.twitter.TweetPhoto;
import com.mkh.twitter.User;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class TweetComponent extends AnchorPane {
    private static final Image redHeartImage = new Image(String.valueOf(TwitterApplication.class.getResource("/images/heart_red.png")));
    private static final Image grayHeartImage = new Image(String.valueOf(TwitterApplication.class.getResource("/images/heart_gray.png")));
    private static final Image quoteImage = new Image(String.valueOf(TwitterApplication.class.getResource("/images/quote.png")));
    private static final Image retImage = new Image(String.valueOf(TwitterApplication.class.getResource("/images/retweet.png")));
    private static final Image dotImage = new Image(String.valueOf(TwitterApplication.class.getResource("/images/three_dot.png")));
    private final InnerTweetComponent innerTweetComponent;


    private Label countOfRet;
    private Label countOfView;
    private Label countOfLikes;
    private ImageView retweetLabel;
    private ImageView quoteLabel;
    private ImageView likeLabel;
    private Button dotButton; // added a button for the three dot icon
    private HBox iconBox; // added a HBox to layout the icons and labels horizontally

    // added some properties to store the retweet count, like count and view count
    private IntegerProperty retCountProperty;
    private IntegerProperty likeCountProperty;
    private IntegerProperty viewCountProperty;


    public TweetComponent(User tweetSender, ProfilePhoto profilePhoto, Tweet tweet, TweetPhoto tweetPhoto, int retCount, int likeCount, int viewCount) {
        this.innerTweetComponent = new InnerTweetComponent(tweetSender, profilePhoto, tweet, tweetPhoto); // changed to use the updated InnerTweetComponent class
        this.countOfRet = new Label();
        this.countOfView = new Label();
        this.countOfLikes = new Label();
        this.retweetLabel = new ImageView(retImage);
        this.quoteLabel = new ImageView(quoteImage);
        this.likeLabel = new ImageView(grayHeartImage);
        this.dotButton = new Button("", new ImageView(dotImage)); // created a button with the three dot icon as graphic
        this.dotButton.setStyle("-fx-background-color: transparent;"); // removed the default button background
        this.iconBox = new HBox(10); // created a HBox with 10 pixels of spacing
        this.iconBox.getChildren().addAll(retweetLabel, countOfRet, likeLabel, countOfLikes, quoteLabel, countOfView); // added the icons and labels to the HBox
        this.getChildren().addAll(innerTweetComponent, iconBox, dotButton); // added the HBox and the button to the children

        // initialized the properties with the initial values
        this.retCountProperty = new SimpleIntegerProperty(retCount);
        this.likeCountProperty = new SimpleIntegerProperty(likeCount);
        this.viewCountProperty = new SimpleIntegerProperty(viewCount);

        // bound the labels to the properties using string converters
        this.countOfRet.textProperty().bind(Bindings.convert(retCountProperty));
        this.countOfLikes.textProperty().bind(Bindings.convert(likeCountProperty));
        this.countOfView.textProperty().bind(Bindings.convert(viewCountProperty));

        // added some CSS styles to the components
        this.setStyle("-fx-font-family: Arial; -fx-font-size: 14px;");
        this.iconBox.setStyle("-fx-alignment: center-left;");
        this.countOfRet.setStyle("-fx-text-fill: #657786;");
        this.countOfLikes.setStyle("-fx-text-fill: #657786;");
        this.countOfView.setStyle("-fx-text-fill: #657786;");
        this.retweetLabel.setStyle("-fx-cursor: hand;");
        this.likeLabel.setStyle("-fx-cursor: hand;");
        this.quoteLabel.setStyle("-fx-cursor: hand;");
        this.dotButton.setStyle("-fx-cursor: hand;");


        this.setLocation();
        this.addTooltip(); // added tooltips to the icons and the button
        this.addEventHandler(); // added event handlers to the icons and the button

    }

    public void setLocation() {
        // set the location of the components using AnchorPane constraints
        AnchorPane.setTopAnchor(innerTweetComponent, 10.0);
        AnchorPane.setLeftAnchor(innerTweetComponent, 10.0);
        AnchorPane.setRightAnchor(innerTweetComponent, 10.0);

        AnchorPane.setBottomAnchor(iconBox, 10.0); // set the bottom and left anchor for the HBox
        AnchorPane.setRightAnchor(iconBox, 10.0);

        AnchorPane.setTopAnchor(dotButton, 10.0); // set the top and right anchor for the button
        AnchorPane.setRightAnchor(dotButton, 10.0);

    }

    public void addTooltip() {
        // create tooltips for the icons and the button
        Tooltip retweetTooltip = new Tooltip("Retweet");
        Tooltip likeTooltip = new Tooltip("Like");
        Tooltip quoteTooltip = new Tooltip("Quote");
        Tooltip dotTooltip = new Tooltip("More options");

        // install tooltips to the icons and the button
        Tooltip.install(retweetLabel, retweetTooltip);
        Tooltip.install(likeLabel, likeTooltip);
        Tooltip.install(quoteLabel, quoteTooltip);
        Tooltip.install(dotButton, dotTooltip);
    }

    public void addEventHandler() {
        // create a context menu for the dot button with some options
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Delete");
        MenuItem editItem = new MenuItem("Edit");
        MenuItem reportItem = new MenuItem("Report");
        contextMenu.getItems().addAll(deleteItem, editItem, reportItem);

        // set the action for the dot button to show the context menu
        dotButton.setOnMouseClicked(e -> {
            contextMenu.show(dotButton, e.getScreenX(), e.getScreenY()); // use the getScreenX() and getScreenY() methods
            e.consume();
        });
        ;

        // set the action for the like icon to toggle between gray and red
        likeLabel.setOnMouseClicked(e -> {
            if (likeLabel.getImage() == grayHeartImage) {
                likeLabel.setImage(redHeartImage);
                likeCountProperty.set(likeCountProperty.get() + 1); // increment the like count property
            } else {
                likeLabel.setImage(grayHeartImage);
                likeCountProperty.set(likeCountProperty.get() - 1); // decrement the like count property
            }
            e.consume();
        });

        // set the action for the retweet icon to increment the retweet count
        retweetLabel.setOnMouseClicked(e -> {
            retCountProperty.set(retCountProperty.get() + 1); // increment the retweet count property
            e.consume();
        });

        // set the action for the quote icon to open a new window for quoting the tweet
        quoteLabel.setOnMouseClicked(e -> {
            // TODO: implement the logic for quoting the tweet
        });

        // added some animations and transitions to the icons and the button
        // create a scale transition for the icons when hovered
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200));
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);


        // create a rotate transition for the dot button when clicked
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(200));
        rotateTransition.setByAngle(90);

        // set the scale transition to play when the icons are entered or exited by mouse
        retweetLabel.setOnMouseEntered(e -> {
            scaleTransition.setNode(retweetLabel);
            scaleTransition.playFromStart();
            e.consume();
        });

        retweetLabel.setOnMouseExited(e -> {
            scaleTransition.stop();
            retweetLabel.setScaleX(1.0);
            retweetLabel.setScaleY(1.0);
            e.consume();
        });

        likeLabel.setOnMouseEntered(e -> {
            scaleTransition.setNode(likeLabel);
            scaleTransition.playFromStart();
            e.consume();
        });

        likeLabel.setOnMouseExited(e -> {
            scaleTransition.stop();
            likeLabel.setScaleX(1.0);
            likeLabel.setScaleY(1.0);
            e.consume();
        });

        quoteLabel.setOnMouseEntered(e -> {
            scaleTransition.setNode(quoteLabel);
            scaleTransition.playFromStart();
            e.consume();
        });

        quoteLabel.setOnMouseExited(e -> {
            scaleTransition.stop();
            quoteLabel.setScaleX(1.0);
            quoteLabel.setScaleY(1.0);
            e.consume();
        });

        // set the rotate transition to play when the dot button is pressed or released by mouse
        dotButton.setOnMousePressed(e -> {
            rotateTransition.setNode(dotButton);
            rotateTransition.playFromStart();
            e.consume();
        });

        dotButton.setOnMouseReleased(e -> {
            rotateTransition.stop();
            dotButton.setRotate(0);
            e.consume();
        });
    }
}
