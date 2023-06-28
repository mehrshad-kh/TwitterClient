package com.mkh.twitter.client.controller;

import com.mkh.twitter.client.TwitterClient;

public abstract class AbstractController {
    private TwitterClient client;

    public AbstractController() {

    }

    public AbstractController(TwitterClient client) {
        setClient(client);
    }

    protected TwitterClient getClient() {
        return client;
    }

    // Change to protected if possible.
    public void setClient(TwitterClient client) {
        this.client = client;
    }
}
