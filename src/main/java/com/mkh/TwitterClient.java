package com.mkh;

import com.mkh.twitter.Country;
import com.mkh.twitter.MKEmpty;
import com.mkh.twitter.TwitterGrpc;
import com.mkh.twitter.TwitterGrpc.TwitterBlockingStub;
import com.mkh.twitter.TwitterGrpc.TwitterStub;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

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

    /**
     * @return null if StatusRuntimeException occurs.
     */
    public Iterator<Country> getMyCountries() {
        logger.info("retrieveCountries was called by the client.");

        Iterator<Country> countries = null;
        try {
            // countries = blockingStub.retrieveCountries(MKEmpty.newBuilder().build());
            StreamObserver<Country> responseObserver = new StreamObserver<Country>() {
                @Override
                public void onNext(Country country) {

                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onCompleted() {

                }
            };
            asyncStub.retrieveCountries(MKEmpty.newBuilder().build(), responseObserver);
            System.out.println("After async call.");
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
        }

        return countries;
    }
}
