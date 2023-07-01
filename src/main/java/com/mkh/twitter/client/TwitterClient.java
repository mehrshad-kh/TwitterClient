package com.mkh.twitter.client;

import com.google.protobuf.ByteString;
import com.mkh.twitter.*;
import com.mkh.twitter.TwitterGrpc.TwitterBlockingStub;
import com.mkh.twitter.TwitterGrpc.TwitterStub;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.logging.Logger;

public class TwitterClient {
    private static final Logger logger = Logger.getLogger(TwitterClient.class.getName());

    private final TwitterBlockingStub blockingStub;
    private final TwitterStub asyncStub;

    public TwitterClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
    }

    public TwitterClient(Channel channel) {
        blockingStub = TwitterGrpc.newBlockingStub(channel);
        asyncStub = TwitterGrpc.newStub(channel);
    }

    public TwitterClient(ManagedChannelBuilder<?> channelBuilder) {
        this(channelBuilder.build());
    }

    public boolean isTakenUsername(String username) throws StatusRuntimeException {
        logger.info("isTakenUsername() was called by the client.");

        return blockingStub.isTakenUsername(MKString.newBuilder().setValue(username).build()).getValue();
    }

    /**
     * @return null if StatusRuntimeException occurs.
     */
    public Iterator<Country> retrieveCountries() throws StatusRuntimeException {
        logger.info("retrieveCountries() was called by the client.");

        Iterator<Country> countryIterator = null;
        countryIterator = blockingStub.retrieveCountries(MKEmpty.newBuilder().build());
        return countryIterator;
    }
    
    public User performSignIn(String username, String password) throws StatusRuntimeException {
        User user = User.newBuilder().setUsername(username).setPassword(password).build();
        return blockingStub.signIn(user);
    }

    public void performSignUp(User user) throws StatusRuntimeException {
        blockingStub.signUp(user);
    }

    public Image performRetrieveHeaderPhoto(User user) throws StatusRuntimeException {
        logger.info("performRetrieveHeaderPhoto() was called by the client.");

        HeaderPhoto headerPhoto = blockingStub.retrieveHeaderPhoto(user);
        return new Image(new ByteArrayInputStream(headerPhoto.getPhoto().getBytes().toByteArray()));
    }

    public Iterator<Tweet> retrieveTweets(User user) throws StatusRuntimeException {
        logger.info("retrieveTweets() was called by the client.");

        return blockingStub.retrieveTweets(user);
    }

    public void performSubmitHeaderPhoto(File file, User user) throws IOException, StatusRuntimeException {
        logger.info("performSubmitHeaderPhoto() was called by the client.");

        byte[] bytes = Files.readAllBytes(file.toPath());
        String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        MKFile mkfile = MKFile.newBuilder()
                .setBytes(ByteString.copyFrom(bytes))
                .setExtension(extension)
                .build();
        HeaderPhoto headerPhoto = HeaderPhoto.newBuilder()
                .setPhoto(mkfile)
                .setUserId(user.getId())
                .build();
         blockingStub.submitHeaderPhoto(headerPhoto);
    }

    public Iterator<Tweet>  getDailyBriefing(User user) throws StatusRuntimeException {
        logger.info("getDailyBriefing() was called by the client.");

        Iterator<Tweet> tweetIterator = null;
        tweetIterator = blockingStub.getDailyBriefing(user);
        return tweetIterator;
    }

    public TweetPhoto retrieveTweetPhoto(Tweet tweet) throws StatusRuntimeException {
        logger.info("retrieveTweetPhoto() was called by the client.");
        final  Iterator<MKFile>  fileIterator ;
        TweetPhoto tweetPhoto = null;
        fileIterator = blockingStub.retrieveTweetPhotos(tweet);
        //just returning the first photo
        if(fileIterator.hasNext()){
            tweetPhoto = TweetPhoto.newBuilder().setPhoto(fileIterator.next()).build();
        }
        return tweetPhoto;
    }

    public User searchUser(String username) throws StatusRuntimeException {
        logger.info("searchUser() was called by the client.");

        Iterator<User> userIterator = null;
        userIterator = blockingStub.searchUsers(MKString.newBuilder().setValue(username).build());
        userIterator.hasNext();
        return userIterator.next();
    }

    public ProfilePhoto retrieveProfilePhoto(User user) throws StatusRuntimeException {
        logger.info("retrieveProfilePhoto() was called by the client.");

        ProfilePhoto profilePhoto = null;
        profilePhoto = blockingStub.retrieveProfilePhoto(user);
        return profilePhoto;
    }
    public int retrieveLikeCount(Tweet tweet) throws StatusRuntimeException {
        logger.info("retrieveNumberOfLike() was called by the client.");

        int numberOfLike = 0;
        numberOfLike = blockingStub.retrieveLikeCount(tweet).getValue();
        return numberOfLike;
    }
    public int retrieveRetweetCount(Tweet tweet) throws StatusRuntimeException {
        logger.info("retrieveNumberOfRetweet() was called by the client.");

        int numberOfRetweet = 0;
        numberOfRetweet = blockingStub.retrieveRetweetCount(tweet).getValue();
        return numberOfRetweet;
    }
    public int retrieveReplyCount(Tweet tweet) throws StatusRuntimeException {
        logger.info("retrieveNumberOfReply() was called by the client.");

        int numberOfReply = 0;
        numberOfReply = blockingStub.retrieveReplyCount(tweet).getValue();
        return numberOfReply;
    }
    public int  sendTweet(String text, User user ) throws StatusRuntimeException {
        logger.info("sendTweet() was called by the client.");
        Tweet newTweet = Tweet.newBuilder()
                .setText(text)
                .setSenderId(user.getId())
                .build();

       Tweet sentTweet =  blockingStub.sendTweet(newTweet);
       return sentTweet.getId();
    }
    public void uploadTweetPhoto(String path, int id) throws StatusRuntimeException {
        logger.info("sendTweetPhoto() was called by the client.");
        TweetPhoto tweetPhoto;
        Path sourcePath = Paths.get(path);
        byte[] bytes;

        try {
            bytes = Files.readAllBytes(sourcePath);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        MKFile file = MKFile.newBuilder()
                .setBytes(ByteString.copyFrom(bytes)).setExtension("jpeg").build();

        tweetPhoto = TweetPhoto.newBuilder()
                .setPhoto(file).setTweetId(id).build();

        blockingStub.uploadTweetPhoto(tweetPhoto);
    }









}
