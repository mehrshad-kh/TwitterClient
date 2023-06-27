package com.mkh.twitter.client.controllers;

import com.mkh.Utility;
import com.mkh.twitter.client.TwitterClient;
import com.mkh.twitter.Country;
import com.mkh.twitter.client.CountryRetrievalTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class SignUpController extends AbstractController {
    @FXML
    private ComboBox<Country> countriesComboBox;

    public void initialize() {
    }

    public void populateCountriesComboBox(TwitterClient client) {
        CountryRetrievalTask task = new CountryRetrievalTask(client);
        task.valueProperty().addListener(new ChangeListener<Iterator<Country>>() {
            @Override
            public void changed(ObservableValue<? extends Iterator<Country>> observableValue,
                                Iterator<Country> oldValue,
                                Iterator<Country> newValue) {
                countriesComboBox.setItems(FXCollections.observableArrayList(Utility.Collections.convert(newValue)));
            }
        });

        Thread daemonThread = new Thread(task);
        daemonThread.setDaemon(true);
        daemonThread.start();

//        Callback<ListView<Country>, ListCell<Country>> callback = new Callback<ListView<Country>, ListCell<Country>>() {
//            @Override
//            public ListCell<Country> call(ListView<Country> countryListView) {
//                return new ListCell<>() {
//                    @Override
//                    protected void updateItem(Country country, boolean empty) {
//                        if (country == null || empty) {
//                            setText(null);
//                        } else {
//                            setText(country.getNiceName());
//                        }
//                    }
//                };
//            }
//        };
//        countriesComboBox.setButtonCell(callback.call(null));
//        countriesComboBox.setCellFactory(callback);

        countriesComboBox.setConverter(new StringConverter<Country>() {
            @Override
            public String toString(Country country) {
                if (country == null) {
                    return null;
                }

                return country.getNiceName();
            }

            @Override
            public Country fromString(String s) {
                return null;
            }
        });
    }
}
