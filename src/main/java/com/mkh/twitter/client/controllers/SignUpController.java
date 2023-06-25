package com.mkh.twitter.client.controllers;

import com.mkh.TwitterClient;
import com.mkh.twitter.Country;
import com.mkh.twitter.client.CountryRetrievalTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class SignUpController extends AbstractController {
    @FXML
    private ComboBox<Country> countriesComboBox;

    public void initialize() {
    }

    public void populateCountriesComboBox(TwitterClient client) {
        CountryRetrievalTask task = new CountryRetrievalTask(client);
        // task.valueProperty().addListener(((observableValue, countryIterator, t1) -> countriesComboBox.get));
        Thread daemonThread = new Thread(task);
        daemonThread.setDaemon(true);
        daemonThread.start();

        ArrayList<Country> countries = new ArrayList<>();
        countries.add(Country.newBuilder().setId(1).setNiceName("Afghanistan").build());
        countries.add(Country.newBuilder().setId(2).setNiceName("Albania").build());
        ObservableList<Country> observableCountries = FXCollections.observableArrayList(countries);
        countriesComboBox.setItems(observableCountries);
        countriesComboBox.setCellFactory(listView -> new CountryListCell());
        countriesComboBox.setButtonCell(new CountryListCell());
    }


    private static class CountryListCell extends ListCell<Country> {
        @Override
        public void updateItem(Country country, boolean empty) {
            super.updateItem(country, empty);

            if (country == null || empty) {
                setText(null);
            } else {
                setText(country.getNiceName());
            }
        }
    }
}
