module com.mkh.twitter.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires protobuf.java;
    requires grpc.api;
    requires java.annotation;
    requires grpc.stub;
    requires grpc.protobuf;
    requires com.google.common;
    requires java.logging;
    // requires com.google.protobuf;

    opens com.mkh.twitter.client to javafx.fxml;
    opens com.mkh.twitter.client.controllers to javafx.fxml;
    // exports com.mkh;
    exports com.mkh.twitter.client;
    exports com.mkh.twitter.client.controllers;
    exports com.mkh.twitter to protobuf.java;
    exports com.mkh;
    opens com.mkh to javafx.fxml;
    // exports com.mkh.twitter;
}