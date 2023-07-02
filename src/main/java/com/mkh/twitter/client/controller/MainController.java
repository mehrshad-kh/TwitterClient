package com.mkh.twitter.client.controller;

import com.google.protobuf.ByteString;
import com.mkh.twitter.MKFile;
import com.mkh.twitter.ProfilePhoto;
import com.mkh.twitter.Tweet;
import com.mkh.twitter.User;
import com.mkh.twitter.client.view.TweetComponent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class MainController extends AbstractController {
    private static final double dividerPosition = 0.1;
    // a constant for the maximum tweet length
    public static final int MAX_TWEET_LENGTH = 280;

    // a constant for the image view size
    public static final int IMAGE_SIZE = 100;


    private VBox tweetsVbox;
    private AnchorPane dailyBriefingAnchorPane;


    private Iterator<Tweet> tweets;
    @FXML
    private ToggleButton homeToggleButton;

    @FXML
    private VBox leftVbox;

    @FXML
    private Button newButton;

    @FXML
    private ToggleButton peopleToggleButton;
    @FXML
    private ToggleButton profileToggleButton;

    @FXML private AnchorPane anchorPane;

    @FXML
    private SplitPane splitPane;

    public ToggleButton getHomeToggleButton() {
        return homeToggleButton;
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
        initializeHomeToggleButton();
        initializeNewButton();
        initializePeopleToggleButton();
        initializeProfileToggleButton();
    }

    private void initializeHomeToggleButton() {
        homeToggleButton.prefWidthProperty().bind(leftVbox.widthProperty());
        homeToggleButton.prefHeightProperty().bind(leftVbox.widthProperty());
    }

    private void initializeNewButton() {
        newButton.prefWidthProperty().bind(leftVbox.widthProperty());
        newButton.prefHeightProperty().bind(leftVbox.widthProperty());
    }

    private void initializePeopleToggleButton() {
        peopleToggleButton.prefWidthProperty().bind(leftVbox.widthProperty());
        peopleToggleButton.prefHeightProperty().bind(leftVbox.widthProperty());
    }

    private void initializeProfileToggleButton() {
        profileToggleButton.prefWidthProperty().bind(leftVbox.widthProperty());
        profileToggleButton.prefHeightProperty().bind(leftVbox.widthProperty());
    }

    private void displayDailyBriefing() {

        File fi = new File("C:\\Users\\amirsalar.abedini\\Desktop\\client\\TwitterClient\\src\\main\\resources\\images\\account.png");
        byte[] fileContent = null  ;
        try {
            fileContent = Files.readAllBytes(fi.toPath());
        } catch (Exception e ){
            e.printStackTrace();
        }
        setUpVboxToShowDailyBrief();
        tweets  = getClient().retrieveTweets(getUser());
        while (tweets.hasNext()) {
            Tweet tweet = tweets.next();
            TweetComponent tweetComponent = new TweetComponent(getClient().searchUser(tweet.getSenderId()),
                    getClient().hasProfilePhoto(User.newBuilder().setId(tweet.getSenderId()).build()) ?
                            getClient().retrieveProfilePhoto(User.newBuilder().setId(tweet.getSenderId()).build()) :
                            ProfilePhoto.newBuilder().setPhoto(MKFile.newBuilder().setBytes(ByteString.copyFrom(fileContent))).build(),
                    tweet,
                    getClient().performHasTweetPhoto(tweet) ?
                            getClient().retrieveTweetPhoto(tweet) :
                            null,
                    getClient().retrieveRetweetCount(tweet),
                    getClient().retrieveLikeCount(tweet),
                    getClient().retrieveReplyCount(tweet));

            tweetComponent.setMinWidth(Region.USE_COMPUTED_SIZE);
            tweetComponent.setPrefWidth(Region.USE_COMPUTED_SIZE);
            tweetComponent.setMaxWidth(Region.USE_COMPUTED_SIZE);
            tweetComponent.setMinHeight(Region.USE_COMPUTED_SIZE);
            tweetComponent.setPrefHeight(Region.USE_COMPUTED_SIZE);
            tweetComponent.setMaxHeight(Region.USE_COMPUTED_SIZE);

            VBox.setVgrow(tweetComponent, Priority.ALWAYS);
            tweetsVbox.getChildren().add(tweetComponent);

        }
    }

    @FXML
    private void homeButtonActioned(ActionEvent event) {
        anchorPane.getChildren().clear();
        displayDailyBriefing();
    }

    @FXML
    private void newButtonActioned(ActionEvent event) {
        setUpSendTweet();
    }

    @FXML
    private void peopleButtonActioned(ActionEvent event) {
        AnchorPane peopleAnchorPane;
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(ProfileController.class.getResource("/com/mkh/twitter/client/people-view.fxml")));
        try {
            peopleAnchorPane = loader.load();
        } catch (IOException e) {
            AbstractController.displayAlert(e);
            return;
        }
        PeopleController controller = loader.getController();
        controller.setClient(getClient());
        controller.setUser(getUser());
        controller.setUp();
        anchorPane.getChildren().clear();
        anchorPane.getChildren().setAll(peopleAnchorPane.getChildren());
    }

    @FXML
    private void profileButtonActioned(ActionEvent event) {
        AnchorPane profileAnchorPane;
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(SignInController.class.getResource("/com/mkh/twitter/client/profile-view.fxml")));
        try {
            profileAnchorPane = loader.load();
        } catch (IOException e) {
            AbstractController.displayAlert(e);
            return;
        }
        ProfileController controller = loader.getController();
        controller.setClient(getClient());
        controller.setUser(getUser());
        controller.setUp();
        anchorPane.getChildren().clear();
        anchorPane.getChildren().setAll(profileAnchorPane.getChildren());
    }

    public void setUpSendTweet(){
        // create a new stage
        Stage newStage = new Stage();

        // create a text field to let the user write their tweet
        TextField textField = new TextField();

        // create a label to show the character count
        Label label = new Label("0/" + MAX_TWEET_LENGTH);
        //create an atomic reference to hold the path of the photo
        AtomicReference<String> tweetPhotoPath = new AtomicReference<>();


        // create an image view to show the photo preview
        ImageView imageView = new ImageView();
        imageView.setFitWidth(IMAGE_SIZE);
        imageView.setFitHeight(IMAGE_SIZE);
        // create a button to let the user upload a photo
        Button uploadButton = new Button("Upload photo");
        uploadButton.setOnAction(e -> {
            // create a file chooser to let the user select an image file
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select an image file");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
            );

            // get the selected file from the file chooser
            File selectedFile = fileChooser.showOpenDialog(newStage);
            tweetPhotoPath.set(selectedFile.getAbsolutePath());
            // if there is a selected file, load it as an image and set it to the image view
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
                // get the name of the selected file
            }
        });

        // create another button to let the user close the window and add the tweet and photo to the text area
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            // get the text from the text field
            String tweet = textField.getText();
            int id = getClient().sendTweet(tweet,getUser());
            getClient().uploadTweetPhoto(String.valueOf(tweetPhotoPath), id) ;
            // close the new stage
            newStage.close();
        });

        // add a listener to the text field to update the label
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            // get the length of the new text
            int length = newValue.length();

            // if the length is greater than the maximum tweet length, truncate it
            if (length > MAX_TWEET_LENGTH) {
                textField.setText(oldValue);
                return;
            }

            // update the label with the new length
            label.setText(length + "/" + MAX_TWEET_LENGTH);
        });

        // create a vertical box to hold the text field, the label, the image view and the buttons
        VBox root = new VBox(textField, label, imageView, uploadButton, saveButton);

        // create the new scene and set its title
        Scene newScene = new Scene(root, 300, 200);
        newStage.setTitle("Add tweet");

        // set the new scene and show the new stage
        newStage.setScene(newScene);
        newStage.show();
    }
    private void setUpVboxToShowDailyBrief(){
        tweetsVbox = new VBox();
        // tweetsVbox.setSpacing(10);
        // tweetsVbox.setPadding(new Insets(10, 10, 10, 10));
        // tweetsVbox.setAlignment(Pos.CENTER);
        // tweetsVbox.setStyle("-fx-background-color: #1DA1F2");
        tweetsVbox.setMinWidth(600);
        tweetsVbox.setMaxWidth(600);
        tweetsVbox.setPrefWidth(600);
        tweetsVbox.setPrefHeight(Region.USE_COMPUTED_SIZE);
        tweetsVbox.setMaxHeight(Region.USE_COMPUTED_SIZE);
        setUpAnchorPaneToShowDailyBrief();
        // add the vertical box to the anchor pane
        // Possible problems.
        dailyBriefingAnchorPane.getChildren().add(tweetsVbox);
    }
    private void setUpAnchorPaneToShowDailyBrief(){
        dailyBriefingAnchorPane = new AnchorPane();
        // anchorPaneToShowDailyBrief.setPrefSize(800, 600);
        // anchorPaneToShowDailyBrief.setPadding(new Insets(10, 10, 10, 10));
        dailyBriefingAnchorPane.setStyle("-fx-background-color: #1DA1F2");
        AnchorPane.setTopAnchor(tweetsVbox, 100.0);
        AnchorPane.setBottomAnchor(tweetsVbox, 100.0);
        AnchorPane.setLeftAnchor(tweetsVbox, 100.0);
        AnchorPane.setRightAnchor(tweetsVbox, 100.0);
        dailyBriefingAnchorPane.setMinWidth(Region.USE_COMPUTED_SIZE);
        dailyBriefingAnchorPane.setMaxWidth(Region.USE_COMPUTED_SIZE);
        dailyBriefingAnchorPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
        dailyBriefingAnchorPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
        dailyBriefingAnchorPane.setMaxWidth(Region.USE_COMPUTED_SIZE);
        dailyBriefingAnchorPane.setMaxHeight(Region.USE_COMPUTED_SIZE);
        anchorPane.getChildren().clear();

        //  add the anchor pane to the main anchor pane
        anchorPane.getChildren().add(dailyBriefingAnchorPane);
        // anchorPane.getChildren().setAll(anchorPaneToShowDailyBrief.getChildren());
    }
}
