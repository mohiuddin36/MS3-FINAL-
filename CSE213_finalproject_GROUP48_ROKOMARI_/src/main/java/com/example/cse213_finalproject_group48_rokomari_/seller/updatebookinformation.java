package com.example.cse213_finalproject_group48_rokomari_.seller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.event.ActionEvent;

import java.awt.print.Book;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class updatebookinformation
{

    @FXML private TableView<Book> bookTable;
    @FXML private TableColumn<Book,String> colTitle;
    @FXML private TableColumn<Book,String> colAuthor;
    @FXML private TableColumn<Book,BigDecimal> colPrice;

    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField priceField;
    @FXML private TextField stockField;
    @FXML private TextArea descriptionArea;
    @FXML private ImageView coverImageView;
    @FXML private ComboBox<String> statusBox;
    @FXML private Label messageLabel;
    @FXML private Button saveButton;

    private File coverFile;
    private Book selectedBook; // domain object

    // example in-memory list (replace with your service)
    private ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // prepare table columns (assumes Book has getters)
        colTitle.setCellValueFactory(cell -> cell.getValue().titleProperty());
        colAuthor.setCellValueFactory(cell -> cell.getValue().authorProperty());
        colPrice.setCellValueFactory(cell -> cell.getValue().priceProperty().asObject());

        bookTable.setItems(books);

        // populate status choices
        statusBox.setItems(FXCollections.observableArrayList("AVAILABLE","OUT_OF_STOCK","PENDING_APPROVAL"));

        // numeric constraints
        priceField.textProperty().addListener((obs,oldVal,newVal) -> {
            if (!newVal.matches("\\d*(\\.\\d{0,2})?")) priceField.setText(oldVal);
        });
        stockField.textProperty().addListener((obs,oldVal,newVal) -> {
            if (!newVal.matches("\\d*")) stockField.setText(oldVal);
        });

        // initially disable save until a book is selected
        saveButton.setDisable(true);

        // Example: load sample data (replace with your backend call)
        loadSampleData();
    }

    private void loadSampleData() {
        books.addAll(
                new Book(1, "1984", "George Orwell", new BigDecimal("9.99"), 12, "Dystopia", "AVAILABLE", null),
                new Book(2, "Clean Code", "Robert C. Martin", new BigDecimal("29.99"), 5, "Software craft", "AVAILABLE", null)
        );
    }

    @FXML
    private void handleSelectBook() {
        selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            populateForm(selectedBook);
            saveButton.setDisable(false);
            messageLabel.setText("");
        }
    }

    private void populateForm(Book book) {
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        priceField.setText(book.getPrice().toPlainString());
        stockField.setText(String.valueOf(book.getStock()));
        descriptionArea.setText(book.getDescription());
        statusBox.getSelectionModel().select(book.getStatus());
        if (book.getCoverPath() != null) {
            try {
                Image img = new Image(new FileInputStream(new File(book.getCoverPath())));
                coverImageView.setImage(img);
            } catch (FileNotFoundException e) {
                coverImageView.setImage(null);
            }
        } else {
            coverImageView.setImage(null);
        }
    }


    @FXML
    private void handleSave(ActionEvent event) {
        if (selectedBook == null) {
            messageLabel.setText("No book selected.");
            return;
        }

        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String priceText = priceField.getText().trim();
        String stockText = stockField.getText().trim();
        String description = descriptionArea.getText().trim();
        String status = statusBox.getSelectionModel().getSelectedItem();

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
            messageLabel.setText("Enter valid price.");
            return;
        }

        try {
            stock = Integer.parseInt(stockText);
            if (stock < 0) throw new NumberFormatException();
        } catch (Exception e) {
            messageLabel.setText("Enter valid stock.");
            return;
        }

        // Update the selectedBook object
        selectedBook.setTitle(title);
        selectedBook.setAuthor(author);
        selectedBook.setPrice(price);
        selectedBook.setStock(stock);
        selectedBook.setDescription(description);
        selectedBook.setStatus(status);

        // If coverFile != null, upload it to server or set path
        if (coverFile != null) {
            // TODO: upload to server or copy locally, set selectedBook.setCoverPath(...)
            selectedBook.setCoverPath(coverFile.getAbsolutePath());
        }

        // TODO: call service to persist changes (DB or REST)
        // bookService.updateBook(selectedBook);

        // refresh table view
        bookTable.refresh();

        messageLabel.setText("Book updated successfully.");
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        clearForm();
        messageLabel.setText("Edit cancelled.");
        saveButton.setDisable(true);
        bookTable.getSelectionModel().clearSelection();
        selectedBook = null;
    }

    private void clearForm() {
        titleField.clear();
        authorField.clear();
        priceField.clear();
        stockField.clear();
        descriptionArea.clear();
        coverImageView.setImage(null);
        statusBox.getSelectionModel().clearSelection();
        coverFile = null;
    }
}
