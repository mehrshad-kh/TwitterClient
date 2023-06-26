package com.mkh.twitter.client;

import com.mkh.twitter.client.TwitterClient;
import com.mkh.twitter.Country;
import javafx.concurrent.Task;

import java.util.Iterator;

public class CountryRetrievalTask extends Task<Iterator<Country>> {
    private final TwitterClient client;

    public CountryRetrievalTask(TwitterClient client) {
        this.client = client;
    }

    @Override
    public Iterator<Country> call() throws Exception {
        System.out.println("Before call in CountryRetrievalTask.call()");
        Iterator<Country> countryIterator = client.getMyCountries();
        System.out.println("After call in CountryRetrievalTask.call()");

        while (countryIterator.hasNext()) {
            Country country = countryIterator.next();
            System.out.println(country.getName());
        }

        return countryIterator;
    }

    @Override
    public void failed() {
        System.out.println("Task failed.");
    }

    @Override
    public void succeeded() {
        System.out.println("Task succeeded");
    }
}
