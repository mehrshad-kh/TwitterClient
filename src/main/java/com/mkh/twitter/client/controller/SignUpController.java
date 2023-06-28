package com.mkh.twitter.client.controller;

import com.mkh.Utility;
import com.mkh.twitter.client.TwitterApplication;
import com.mkh.twitter.client.TwitterClient;
import com.mkh.twitter.Country;
import com.mkh.twitter.client.CountryRetrievalTask;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
    private final SimpleBooleanProperty usernameIsUnique = new SimpleBooleanProperty(true);

    private final static Image exclamationmarkCirclFillImage
            = new Image(String.valueOf(TwitterApplication.class.getResource("/images/exclamationmark.circle.fill.png")));

    @FXML private Button signUpButton;
    @FXML private ComboBox<Country> countriesComboBox;
    @FXML private ImageView emailErrorImageView;
    @FXML private ImageView passwordConfirmatioErrorImageView;
    @FXML private ImageView phoneNumberErrorImageView;
    @FXML private ImageView usernameErrorImageView;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField emailTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private TextField usernameTextField;

    public SignUpController() {
         super();
    }

    public SignUpController(TwitterClient client) {
         super(client);
    }

    public void initialize() {
        initializePasswordConfirmationCheck();
        initializeEmailCheck();
        initializePhoneNumberCheck();
    }

    private void initializePasswordConfirmationCheck() {
        passwordConfirmatioErrorImageView.setImage(exclamationmarkCirclFillImage);
        confirmPasswordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue && !newValue) {
                passwordIsConfirmed.set(passwordField.getText().equals(confirmPasswordField.getText()));
            }
        });
        passwordConfirmatioErrorImageView.visibleProperty().bind(passwordIsConfirmed.not());
    }

    private void initializeEmailCheck() {
        emailErrorImageView.setImage(exclamationmarkCirclFillImage);
        emailTextField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (oldValue && !newValue) {
                emailIsCorrect.set(Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}", emailTextField.getText()));
            }
        }));
        emailErrorImageView.visibleProperty().bind(emailIsCorrect.not());
    }

    private void initializePhoneNumberCheck() {
        phoneNumberErrorImageView.setImage(exclamationmarkCirclFillImage);
        phoneNumberTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue && !newValue) {
                phoneNumberIsCorrect.set(Pattern.matches("(|\\+[\\d]{1,3}|0)[\\d]{10}", phoneNumberTextField.getText()));
            }
        });
        phoneNumberErrorImageView.visibleProperty().bind(phoneNumberIsCorrect.not());
    }

    public void setUp() {
        setUpUsernameCheck();
        setUpCountriesComboBox();
    }

    private void setUpUsernameCheck() {
        usernameErrorImageView.setImage(exclamationmarkCirclFillImage);
        usernameTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (oldValue && !newValue && !usernameTextField.getText().isBlank()) {
                    usernameIsUnique.set(getClient().isTakenUsername(usernameTextField.getText()));
                }
            }
        });
        usernameErrorImageView.visibleProperty().bind(usernameIsUnique.not());
    }

    private void setUpCountriesComboBox() {
        CountryRetrievalTask task = new CountryRetrievalTask(getClient());
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

    @FXML
    public void signUpButtonActioned(ActionEvent event) {

    }
}
