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
        Iterator<Country> countryIterator = client.getMyCountries();

        System.out.println(countryIterator.next().getNiceName());

//        while (countryIterator.hasNext()) {
//            Country country = countryIterator.next();
//            System.out.println(country.getNiceName());
//        }

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
