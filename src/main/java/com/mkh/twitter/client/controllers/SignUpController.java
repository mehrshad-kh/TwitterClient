package com.mkh.twitter.client.controllers;

import com.mkh.TwitterClient;
import com.mkh.twitter.Country;
import com.mkh.twitter.client.CountryRetrievalTask;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class SignUpController extends AbstractController {
    @FXML
    private ComboBox<String> countriesComboBox;

    public void initialize() {
    }

    public void populateCountriesComboBox(TwitterClient client) {
        CountryRetrievalTask task = new CountryRetrievalTask(client);
    }
}
