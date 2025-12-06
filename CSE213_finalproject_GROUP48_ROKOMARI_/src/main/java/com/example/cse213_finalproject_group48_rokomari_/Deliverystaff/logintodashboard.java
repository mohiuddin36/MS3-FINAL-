


package com.example.cse213_finalproject_group48_rokomari_.Deliverystaff;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller class matching the fx:controller value in your FXML.
 * This class only provides @FXML fields and a simple initialize() hook.
 * No behavior was forced onto your FXML (no onAction added).
 */
public class logintodashboard {

    @FXML private Label titleLabel;
    @FXML private TextField userIdField;
    @FXML private TextField passwordField; // kept as TextField to match your original FXML
    @FXML private Label passwordLabel;
    @FXML private Button loginButton;
    @FXML private Label userLabel;
    @FXML private Label statusLabel;
    @FXML private Label dashboardLabel;

    /**
     * Called after FXML fields are injected.
     * Keep initialization minimal since you requested no changes.
     */
    @FXML
    private void initialize() {
        // preserve existing text/status — do not overwrite.
        // Example safe read-only checks (no UI mutation):
        if (titleLabel != null) {
            // nothing changed — just ensuring injection worked
        }
    }

    /**
     * Optional: helper you can wire later in FXML if you want.
     * Left here commented so there is no change to your FXML file.
     */
    // @FXML
    // private void onLoginButtonClicked() {
    //     // implement authentication / navigation here if/when you want to wire it
    // }

    // Add any getters/setters or helper methods you want — leaving behavior up to you.
}
