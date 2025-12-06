package com.example.cse213_finalproject_group48_rokomari_.seller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
        import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;

public class addnewbook {

    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField priceField;
    @FXML private TextField stockField;
    @FXML private TextArea descriptionArea;
    @FXML private ImageView coverImageView;
    @FXML private Label messageLabel;

    private File coverFile; // selected image file

    @FXML
    public void initialize() {
        // Ensure price accepts numeric-like input: allow digits and dot
        priceField.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*(\\.\\d{0,2})?")) {
                priceField.setText(oldText);
            }
        });

        // Ensure stock accepts integers only
        stockField.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*")) {
                stockField.setText(oldText);
            }
        });
    }

    @FXML
    private void handleUploadCover(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Cover Image");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        Window window = coverImageView.getScene().getWindow();
        File selected = chooser.showOpenDialog(window);
        if (selected != null) {
            try {
                Image img = new Image(new FileInputStream(selected));
                coverImageView.setImage(img);
                coverFile = selected;
                messageLabel.setText("Cover selected: " + selected.getName());
            } catch (FileNotFoundException e) {
                messageLabel.setText("Selected file not found.");
            }
        }
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        // Basic validation
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String priceText = priceField.getText().trim();
        String stockText = stockField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (title.isEmpty() || author.isEmpty() || priceText.isEmpty() || stockText.isEmpty()) {
            messageLabel.setText("Please fill Title, Author, Price and Stock.");
            return;
        }

        BigDecimal price;
        int stock;
        try {
            price = new BigDecimal(priceText);
            if (price.compareTo(BigDecimal.ZERO) < 0) throw new NumberFormatException();
        } catch (Exception e) {
            messageLabel.setText("Enter valid price (e.g. 12.99).");
            return;
        }

        try {
            stock = Integer.parseInt(stockText);
            if (stock < 0) throw new NumberFormatException();
        } catch (Exception e) {
            messageLabel.setText("Stock must be a non-negative integer.");
            return;
        }

        // TODO: package data into a DTO or call your service to save to DB and upload coverFile
        // Example placeholders:
        // Book book = new Book(title, author, price, stock, description);
        // bookService.submitForApproval(book, coverFile);

        // Show confirmation (example uses label; you can use Alert)
        messageLabel.setText("Book submitted for approval.");
        clearForm();
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        clearForm();
        messageLabel.setText("Cancelled.");
    }

    private void clearForm() {
        titleField.clear();
        authorField.clear();
        priceField.clear();
        stockField.clear();
        descriptionArea.clear();
        coverImageView.setImage(null);
        coverFile = null;
    }
}
