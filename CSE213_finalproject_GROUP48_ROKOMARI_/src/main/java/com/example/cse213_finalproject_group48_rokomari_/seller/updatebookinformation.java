
package com.example.cse213_finalproject_group48_rokomari_.seller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
        import javafx.event.ActionEvent;


public class updatebookinformation {

    @FXML private Label headerLabel;
    @FXML private Label statusLabel;

    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField priceField;
    @FXML private TextField stockField;

    @FXML private Button updateButton;
    @FXML private Button cancelButton;


    @FXML
    public void initialize() {
        // Initialize UI state
        clearStatus();
        // Optionally, you could load existing book data here if an ID is passed in the scene
    }


    @FXML
    private void handleUpdate(ActionEvent event) {
        clearStatus();

        String title = safeTrim(titleField.getText());
        String author = safeTrim(authorField.getText());
        String priceText = safeTrim(priceField.getText());
        String stockText = safeTrim(stockField.getText());

        // Basic validation
        if (title.isEmpty()) {
            showError("Title cannot be empty.");
            return;
        }
        if (author.isEmpty()) {
            showError("Author cannot be empty.");
            return;
        }
        double price;
        try {
            price = Double.parseDouble(priceText);
            if (price < 0) {
                showError("Price must be zero or positive.");
                return;
            }
        } catch (NumberFormatException e) {
            showError("Invalid price. Use a numeric value (e.g. 199.99).");
            return;
        }

        int stock;
        try {
            stock = Integer.parseInt(stockText);
            if (stock < 0) {
                showError("Stock must be zero or positive.");
                return;
            }
        } catch (NumberFormatException e) {
            showError("Invalid stock. Use an integer value (e.g. 10).");
            return;
        }

        // Disable update button to prevent duplicate clicks (UI responsiveness)
        updateButton.setDisable(true);

        // Perform update (replace performUpdate with a real DB/API call)
        boolean ok = performUpdate(title, author, price, stock);

        // Re-enable button
        updateButton.setDisable(false);

        if (ok) {
            showSuccess("Book information updated successfully.");
            // Optionally clear or reset fields:
            // clearForm();
        } else {
            showError("Failed to update book information. Try again later.");
        }
    }


    @FXML
    private void handleCancel(ActionEvent event) {
        clearForm();
        clearStatus();
        // If this view is a dialog, you may want to close the window instead.
        // Example (if you have the Stage): ((Stage)cancelButton.getScene().getWindow()).close();
    }


    private boolean performUpdate(String title, String author, double price, int stock) {
        try {
            // TODO: call your service / API / DAO here to update the book record.
            // Example pseudo-code:
            // Book book = new Book(title, author, price, stock);
            // bookDao.update(book);
            //
            // For skeleton/demo purposes we simulate success:
            Thread.sleep(120); // simulate small delay (remove in real implementation)
            return true;
        } catch (Exception ex) {
            // Log exception in real app
            return false;
        }
    }

    /* -------------------- Helper methods -------------------- */

    private void showError(String msg) {
        if (statusLabel != null) {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText(msg);
        } else {
            // fallback: show an alert
            showAlert(Alert.AlertType.ERROR, "Validation error", msg);
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
        if (titleField != null) titleField.clear();
        if (authorField != null) authorField.clear();
        if (priceField != null) priceField.clear();
        if (stockField != null) stockField.clear();
    }

    private String safeTrim(String s) {
        return s == null ? "" : s.trim();
    }

    private void showAlert(Alert.AlertType type, String title, String body) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(body);
        a.showAndWait();
    }
}
