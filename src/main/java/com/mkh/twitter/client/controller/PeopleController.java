package com.mkh.twitter.client.controller;

import com.mkh.twitter.User;
import com.mkh.twitter.client.view.FolloweeComponent;
import com.mkh.twitter.client.view.FollowerComponent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.VBox;

import java.util.Iterator;

public class PeopleController extends AbstractController {



    @FXML
    private VBox followersVbox;

    @FXML
    private VBox followingsVbox;

    Iterator<User> userIteratorForFollowers;
    Iterator<User> userIteratorForFollowing;
    public void initialize() {
    }
    public void setUp(){
        userIteratorForFollowers = getClient().performRetrieveFollowers(getUser());
        userIteratorForFollowing = getClient().performRetrieveFollowees(getUser());
        displayFollowers();
        displayFollowees();
    }
    public void displayFollowers(){
        if(userIteratorForFollowers.hasNext()){
            User user = userIteratorForFollowers.next();
            displayFollower(user);
        }
    }
    public void displayFollowees(){
        if(userIteratorForFollowing.hasNext()){
            User user = userIteratorForFollowing.next();
            displayFollowee(user);
        }
    }
    public void displayFollower(User user){
        FollowerComponent followerComponent = new FollowerComponent(user,getClient().retrieveProfilePhoto(user));
        System.out.println(user.getUsername());
        followersVbox.getChildren().add(followerComponent);

    }
    public void displayFollowee(User user){
        FolloweeComponent followeeComponent = new FolloweeComponent(user,getClient().retrieveProfilePhoto(user));
        followingsVbox.getChildren().add(followeeComponent);
    }

}
