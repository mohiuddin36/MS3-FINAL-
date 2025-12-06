
package com.example.cse213_finalproject_group48_rokomari_.Deliverystaff;

import javafx.fxml.FXML;
import javafx.scene.control.*;
        import javafx.event.ActionEvent;


public class ConfirmDelivery {

    @FXML private TextField recipientNameField;
    @FXML private TextField orderIdField;
    @FXML private TextField digitalSignatureField;

    @FXML private Button confirmDeliveryButton;

    @FXML private Label statusLabel; // optional, to display confirmation messages


    @FXML
    public void initialize() {
        clearStatus();
    }


    @FXML
    private void handleConfirmDelivery(ActionEvent event) {
        clearStatus();

        String recipientName = safeTrim(recipientNameField.getText());
        String orderId = safeTrim(orderIdField.getText());
        String digitalSignature = safeTrim(digitalSignatureField.getText());

        // Basic validation
        if (recipientName.isEmpty()) {
            showError("Recipient Name cannot be empty.");
            return;
        }
        if (orderId.isEmpty()) {
            showError("Order Id cannot be empty.");
            return;
        }
        if (digitalSignature.isEmpty()) {
            showError("Digital Signature cannot be empty.");
            return;
        }

        // Simulate confirming delivery (replace with real backend/API call)
        boolean success = performConfirmDelivery(recipientName, orderId, digitalSignature);

        if (success) {
            showSuccess("Delivery confirmed successfully.");
            clearForm();
        } else {
            showError("Failed to confirm delivery. Try again later.");
        }
    }

    private boolean performConfirmDelivery(String recipientName, String orderId, String digitalSignature) {
        try {
            // TODO: replace with real backend logic
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
        } else {
            // fallback alert
            showAlert(Alert.AlertType.ERROR, "Error", msg);
        }
    }

    private void showSuccess(String msg) {
        if (statusLabel != null) {
            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText(msg);
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Success", msg);
        }
    }

    private void clearStatus() {
        if (statusLabel != null) {
            statusLabel.setText("");
            statusLabel.setStyle("");
        }
    }

    private void clearForm() {
        if (recipientNameField != null) recipientNameField.clear();
        if (orderIdField != null) orderIdField.clear();
        if (digitalSignatureField != null) digitalSignatureField.clear();
    }

    private String safeTrim(String s) {
        return s == null ? "" : s.trim();
    }

    private void showAlert(Alert.AlertType type, String title, String body) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(body);
        alert.showAndWait();
    }
}
