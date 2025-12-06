package com.example.cse213_finalproject_group48_rokomari_.seller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;


public class contactcoustomersupport {


@FXML private TextField subjectField;
@FXML private TextArea messageArea;
@FXML private Label validationLabel;
@FXML private Button submitButton;
@FXML private StackPane confirmationPane;
@FXML private Label confirmationLabel;


// event-1: Seller clicks “Help/Support" -> user opens this view (handled by navigation)


@FXML
private void handleSubmit() {
    String message = messageArea.getText();
    if (!validateMessage(message)) return;


    submitButton.setDisable(true);
// event-4: create support ticket (DP)
    boolean created = createSupportTicket(subjectField.getText(), message);
    submitButton.setDisable(false);


    if (created) showConfirmation("Your ticket was created. Ticket ID: #12345");
    else showValidationError("Unable to create ticket. Please try again.");
}


private boolean validateMessage(String message) {
// event-3: system validates message (VL)
    if (message == null || message.trim().isEmpty()) {
        showValidationError("Message cannot be empty.");
        return false;
    }
    if (message.length() < 10) {
        showValidationError("Message is too short. Please provide more detail.");
        return false;
    }
    validationLabel.setVisible(false);
    return true;
}


private void showValidationError(String text) {
    validationLabel.setText(text);
    validationLabel.setVisible(true);
}


private boolean createSupportTicket(String subject, String message) {
// Simulate DP: call backend service / REST API to create ticket and return success
// Replace with real HTTP client code or service injection
    return true;
}


private void showConfirmation(String text) {
    confirmationLabel.setText(text);
    confirmationPane.setVisible(true);
    confirmationPane.setMouseTransparent(false);
}


@FXML
private void handleConfirmationOk() {
    confirmationPane.setVisible(false);
    confirmationPane.setMouseTransparent(true);
// event-5: display confirmation (OP) already done; optionally navigate back
}


@FXML
private void handleCancel() {
// clear fields or close view
}
