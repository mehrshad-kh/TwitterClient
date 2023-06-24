package com.mkh.twitter.client.controllers;

import com.mkh.TwitterClient;
import com.mkh.twitter.Country;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class SignUpController extends AbstractController {
    @FXML
    private ComboBox<String> countriesComboBox;

    public void initialize() {
        System.out.println("What the fuck, man?");
    }

    public void populateCountriesComboBox() {
        System.out.println("What the fuck?");
        Iterator<Country> countries = getClient().retrieveCountries();
        while (countries.hasNext()) {
            Country country = countries.next();
            System.out.println(country.getNiceName());
            countriesComboBox.getItems().add(country.getNiceName());
        }
    }
}
