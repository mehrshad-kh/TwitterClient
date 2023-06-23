module com.example.twitterclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.twitterclient to javafx.fxml;
    exports com.example.twitterclient;
}