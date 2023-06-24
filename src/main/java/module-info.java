module com.mkh.twitterclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires protobuf.java;
    requires grpc.api;
    requires java.annotation;
    requires grpc.stub;
    requires grpc.protobuf;
    requires com.google.common;
    requires java.logging;

    opens com.mkh.twitterclient to javafx.fxml;
    exports com.mkh.twitterclient;
}