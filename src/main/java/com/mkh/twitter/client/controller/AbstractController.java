package com.mkh.twitter.client.controller;

import com.mkh.twitter.client.TwitterClient;

public abstract class AbstractController {
    private TwitterClient client;

    protected TwitterClient getClient() {
        return client;
    }

    public void setClient(TwitterClient client) {
        this.client = client;
    }
}
