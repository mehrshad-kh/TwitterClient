package com.mkh.twitter.client;

import com.mkh.twitter.*;
import com.mkh.twitter.TwitterGrpc.TwitterBlockingStub;
import com.mkh.twitter.TwitterGrpc.TwitterStub;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.Iterator;
import java.util.logging.Level;
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

        boolean result = false;
        result = blockingStub.isTakenUsername(MKString.newBuilder().setValue(username).build()).getValue();

        return result;
    }

    /**
     * @return null if StatusRuntimeException occurs.
     */
    public Iterator<Country> retrieveCountries() throws StatusRuntimeException {
        logger.info("retrieveCountries() was called by the client.");

        Iterator<Country> countryIterator = null;
        countryIterator = blockingStub.retrieveCountries(MKEmpty.newBuilder().build());
//            StreamObserver<Country> responseObserver = new StreamObserver<>() {
//                @Override
//                public void onNext(Country country) {
//                    System.out.println(country.getNiceName());
//                    countries.add(country);
//                }
//
//                @Override
//                public void onError(Throwable throwable) {
//
//                }
//
//                @Override
//                public void onCompleted() {
//
//                }
//            };
//            asyncStub.retrieveCountries(MKEmpty.newBuilder().build(), responseObserver);

        return countryIterator;
    }
    public Iterator<Tweet> retrieveTweets(User user) throws StatusRuntimeException {
        logger.info("retrieveTweets() was called by the client.");

        Iterator<Tweet> tweetIterator = null;
        tweetIterator = blockingStub.retrieveTweets(user);
        return tweetIterator;
    }
    public Iterator<Tweet>  getDailyBriefing(User user) throws StatusRuntimeException {
        logger.info("getDailyBriefing() was called by the client.");

        Iterator<Tweet> tweetIterator = null;
        tweetIterator = blockingStub.getDailyBriefing(user);
        return tweetIterator;
    }
    public Iterator<TweetPhoto> retrieveTweetPhoto(Tweet tweet) throws StatusRuntimeException {
        logger.info("retrieveTweetPhoto() was called by the client.");
        final  Iterator<MKFile>  fileIterator ;
        Iterator<TweetPhoto> tweetPhoto = null;
        fileIterator = blockingStub.retrieveTweetPhotos(tweet);
        tweetPhoto = new Iterator<TweetPhoto>() {
            @Override
            public boolean hasNext() {
                return fileIterator.hasNext();
            }

            @Override
            public TweetPhoto next() {
                MKFile file = fileIterator.next();
                TweetPhoto tweetPhoto = TweetPhoto.newBuilder().setPhoto(file).setTweetId(tweet.getId()).build();
                return tweetPhoto;
            }
        };
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
}
