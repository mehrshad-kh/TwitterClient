module com.mkh.twitterclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.mkh.twitterclient to javafx.fxml;
    exports com.mkh.twitterclient;
}