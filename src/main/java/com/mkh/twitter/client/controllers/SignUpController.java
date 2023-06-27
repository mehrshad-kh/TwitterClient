package com.mkh.twitter.client.controllers;

import com.mkh.Utility;
import com.mkh.twitter.client.TwitterApplication;
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SignUpController extends AbstractController {
    @FXML
    private Button emailErrorButton;
    @FXML
    private ComboBox<Country> countriesComboBox;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField emailTextField;

    private static Image exclamationmarkCirclFillRedImage = new Image(String.valueOf(TwitterApplication.class.getResource("/images/exclamationmark.circle.fill.red.png")));

    public void initialize() {
        ImageView exclamationmarkCirclFillRedImageView = new ImageView(exclamationmarkCirclFillRedImage);
        exclamationmarkCirclFillRedImageView.setFitWidth(20.0);
        exclamationmarkCirclFillRedImageView.setFitHeight(20.0);
        imageView.setImage(exclamationmarkCirclFillRedImage);

        emailTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue,
                                Boolean oldValue,
                                Boolean newValue) {
                if (oldValue && !newValue) {
                    if (!Pattern.matches("^(.+)@(.+)$",
                            emailTextField.getText())) {
                        imageView.setVisible(true);
                    } else {
                        imageView.setVisible(false);
                    }
                }
            }
        });
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
