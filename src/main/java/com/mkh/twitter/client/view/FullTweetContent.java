package com.mkh.twitter.client.view;

import com.mkh.twitter.Tweet;
import com.mkh.twitter.TweetPhoto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.shape.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.ByteArrayInputStream;

public class FullTweetContent extends StackPane {
    private static final String FONT_FAMILY = "Arial";
    private static final int FONT_SIZE = 14;
    private static final int PADDING = 10;
    private static final String BG_COLOR = "#FFFFFF";
    private static final String TEXT_COLOR = "#000000";

    TextFlow textFlow; // changed from TextArea to TextFlow
    ImageView imageView;

    public FullTweetContent(Tweet tweet, TweetPhoto tweetPhoto) {
        this.setPadding(new Insets(PADDING));
        this.setBackground(new Background(new BackgroundFill(Color.web(BG_COLOR), new CornerRadii(10), Insets.EMPTY)));
        this.setBorder(new Border(new BorderStroke(Color.web("#E1E8ED"), BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));

        textFlow = new TextFlow(); // created a TextFlow
        textFlow.setMaxWidth(400);
        textFlow.setLineSpacing(5);
        textFlow.setStyle("-fx-font-family: " + FONT_FAMILY + "; -fx-font-size: " + FONT_SIZE + "px; -fx-text-fill: " + TEXT_COLOR + ";");
        textFlow.setPadding(new Insets(0, PADDING, 0, PADDING));

        // split the tweet text by spaces
        String[] words = tweet.getText().split(" ");

        // loop through the words and create Text nodes with different styles
        for (String word : words) {
            Text text = new Text(word + " ");
            if (word.startsWith("#")) { // if the word is a hashtag, make it blue and bold
                text.setFill(Color.BLUE);
                text.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, FontPosture.REGULAR, FONT_SIZE));
            } else if (word.startsWith("@")) { // if the word is a mention, make it gray and italic
                text.setFill(Color.GRAY);
                text.setFont(Font.font(FONT_FAMILY, FontWeight.NORMAL, FontPosture.ITALIC, FONT_SIZE));
            } else if (word.startsWith("http")) { // if the word is a link, make it green and underlined
                text.setFill(Color.GREEN);
                text.setUnderline(true);
            }
            textFlow.getChildren().add(text); // add the text to the text flow
        }

        this.getChildren().add(textFlow); // add the text flow to the stack pane

        if (tweetPhoto != null ) {
            Image img = new Image(new ByteArrayInputStream(tweetPhoto.getPhoto().getBytes().toByteArray()));
            imageView = new ImageView(img);
            imageView.setSmooth(true);
            imageView.setFitHeight(200);
            imageView.setFitWidth(175);
            imageView.setPreserveRatio(true);

            Rectangle clip = new Rectangle(175.0, 200.0);
            clip.setArcWidth(10);
            clip.setArcHeight(10);
            imageView.setClip(clip);
            // added some CSS styles to the image view
            imageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.73), 10, 0, 0, 0);");
            this.getChildren().add(imageView); // add the image view to the stack pane
        }

        StackPane.setAlignment(textFlow, Pos.BOTTOM_LEFT);// align the text flow to the top left of the stack pane
        // StackPane.setAlignment(imageView, Pos.CENTER); // align the image view to the bottom right of the stack pane
    }
}