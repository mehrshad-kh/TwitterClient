package com.mkh.twitter.client.controllers;

import com.mkh.twitter.Country;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class SignUpController extends AbstractController implements Initializable {
    @FXML
    private ComboBox<String> countriesComboBox;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Retrieve countries from the server.
        // Populate the combo box.
        Iterator<Country> countries = getClient().retrieveCountries();
        while (countries.hasNext()) {
            countriesComboBox.getItems().add(countries.next().getNiceName());
        }
    }
}
