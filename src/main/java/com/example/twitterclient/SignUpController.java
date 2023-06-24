package com.example.twitterclient;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private ComboBox<String> countriesComboBox;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Retrieve countries from the server.
        // Populate the combo box.
    }
}
