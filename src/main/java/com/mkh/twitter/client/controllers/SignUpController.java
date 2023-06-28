package com.mkh.twitter.client.controllers;

import com.mkh.Utility;
import com.mkh.twitter.client.TwitterApplication;
import com.mkh.twitter.client.TwitterClient;
import com.mkh.twitter.Country;
import com.mkh.twitter.client.CountryRetrievalTask;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;

import java.util.Iterator;
import java.util.regex.Pattern;

public class SignUpController extends AbstractController {
    private final SimpleBooleanProperty emailIsCorrect = new SimpleBooleanProperty(true);
    private final SimpleBooleanProperty phoneNumberIsCorrect = new SimpleBooleanProperty(true);
    private final SimpleBooleanProperty passwordIsConfirmed = new SimpleBooleanProperty(true);

    private final static Image exclamationmarkCirclFillImage
            = new Image(String.valueOf(TwitterApplication.class.getResource("/images/exclamationmark.circle.fill.png")));

    @FXML private ComboBox<Country> countriesComboBox;
    @FXML private ImageView passwordConfirmatioErrorImageView;
    @FXML private ImageView emailErrorImageView;
    @FXML private ImageView phoneNumberErrorImageView;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField emailTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private TextField usernameTextField;

    public void initialize() {
        emailErrorImageView.setImage(exclamationmarkCirclFillImage);
        emailTextField.focusedProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (oldValue && !newValue) {
                emailIsCorrect.set(Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}", emailTextField.getText()));
            }
        }));
        emailErrorImageView.visibleProperty().bind(emailIsCorrect.not());

        phoneNumberErrorImageView.setImage(exclamationmarkCirclFillImage);
        phoneNumberTextField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (oldValue && !newValue) {
                phoneNumberIsCorrect.set(Pattern.matches("(|\\+[\\d]{1,3}|0)[\\d]{10}", phoneNumberTextField.getText()));
            }
        });
        phoneNumberErrorImageView.visibleProperty().bind(phoneNumberIsCorrect.not());

        passwordConfirmatioErrorImageView.setImage(exclamationmarkCirclFillImage);
        confirmPasswordField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (oldValue && !newValue) {
                passwordIsConfirmed.set(passwordField.getText().equals(confirmPasswordField.getText()));
            }
        });
        passwordConfirmatioErrorImageView.visibleProperty().bind(passwordIsConfirmed.not());
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

    public void doSomething() {
        usernameTextField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (oldValue && !newValue) {
                if (usernameTextField.getText().isBlank()) {
                    // usernameIsBlank = true;

                }
            }
        });
    }
}
