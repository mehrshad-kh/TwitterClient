package com.mkh.twitter.client;

import com.mkh.TwitterClient;
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
        Iterator<Country> countries = client.retrieveCountries();
        while (countries.hasNext()) {
            Country country = countries.next();
            System.out.println(country.getNiceName());
        }

        return countries;
    }
}
