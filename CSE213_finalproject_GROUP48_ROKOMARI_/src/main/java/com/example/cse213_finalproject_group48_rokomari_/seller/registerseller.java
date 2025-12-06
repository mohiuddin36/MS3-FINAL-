
package com.example.cse213_finalproject_group48_rokomari_.seller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;


public class registerseller {

    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField passwordField;

    @FXML private Button submitButton;

    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        clearStatus();
    }


    @FXML
    private void handleSubmit(ActionEvent event) {
        clearStatus();

        String email = safeTrim(emailField.getText());
        String phone = safeTrim(phoneField.getText());
        String password = safeTrim(passwordField.getText());

        // Basic validation
        if (email.isEmpty()) {
            showError("Email cannot be empty.");
            return;
        }
        if (!email.contains("@")) {
            showError("Invalid email format.");
            return;
        }
        if (phone.isEmpty()) {
            showError("Phone number cannot be empty.");
            return;
        }
        if (password.isEmpty()) {
            showError("Password cannot be empty.");
            return;
        }

        // Simulate registration (replace with real backend/API call)
        boolean success = performRegistration(email, phone, password);

        if (success) {
            showSuccess("Seller registered successfully.");
            clearForm();
        } else {
            showError("Failed to register seller. Try again later.");
        }
    }

    /**
     * Simulate registration backend call.
     * Replace with real service / database call.
     */
    private boolean performRegistration(String email, String phone, String password) {
        try {
            // TODO: replace with actual registration logic
            Thread.sleep(100); // simulate delay
            return true;       // simulate success
        } catch (Exception e) {
            return false;
        }
    }

    /* -------------------- Helper methods -------------------- */
    private void showError(String msg) {
        if (statusLabel != null) {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText(msg);
        }
    }

    private void showSuccess(String msg) {
        if (statusLabel != null) {
            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText(msg);
        }
    }

    private void clearStatus() {
        if (statusLabel != null) {
            statusLabel.setText("");
            statusLabel.setStyle("");
        }
    }

    private void clearForm() {
        if (emailField != null) emailField.clear();
        if (phoneField != null) phoneField.clear();
        if (passwordField != null) passwordField.clear();
    }

    private String safeTrim(String s) {
        return s == null ? "" : s.trim();
    }
}
