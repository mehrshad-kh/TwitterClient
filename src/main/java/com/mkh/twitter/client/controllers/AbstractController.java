package com.mkh.twitter.client.controllers;

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

    public void setClient(TwitterClient client) {
        this.client = client;
    }
}
