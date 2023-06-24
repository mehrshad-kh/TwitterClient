package com.mkh.twitter.client.controllers;

import com.mkh.TwitterClient;

public abstract class AbstractController {
    private TwitterClient client;

    protected TwitterClient getClient() {
        return client;
    }

    public void setClient(TwitterClient client) {
        this.client = client;
    }
}
