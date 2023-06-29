package com.mkh.twitter.client;

import com.mkh.twitter.Tweet;
import com.mkh.twitter.User;
import javafx.concurrent.Task;
import java.util.Iterator;
public class TweetRetrievalTask extends Task<Iterator<Tweet>> {
    private final TwitterClient client;
    private final User user;
    public TweetRetrievalTask(TwitterClient client, User user) {
        this.client = client;
        this.user = user;
    }
    @Override
    public Iterator<Tweet> call() throws Exception {
        System.out.println("call was called.");
        Iterator<Tweet> tweetIterator = client.getDailyBriefing(user);
        updateValue(tweetIterator);
        System.out.println(tweetIterator.hasNext());
        return tweetIterator;
    }

    @Override
    public void failed() {
        System.out.println("Task failed.");
    }
    @Override
    public void succeeded() {
        System.out.println("Task succeeded.");
    }

}
