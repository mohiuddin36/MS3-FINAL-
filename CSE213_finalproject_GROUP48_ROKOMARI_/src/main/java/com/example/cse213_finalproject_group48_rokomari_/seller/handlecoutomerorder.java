
package com.example.cse213_finalproject_group48_rokomari_.seller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.application.Platform;


public class handlecoutomerorder {

    @FXML private Label headerLabel;
    @FXML private Label newOrderLabel;

    @FXML private TableView<OrderItem> orderTable;
    @FXML private TableColumn<OrderItem, String> colProduct;
    @FXML private TableColumn<OrderItem, Integer> colQuantity;
    @FXML private TableColumn<OrderItem, Integer> colStock;
    @FXML private TableColumn<OrderItem, Double> colPrice;

    @FXML private Label checkingStockLabel;
    @FXML private Button acceptButton;
    @FXML private Button rejectButton;
    @FXML private Label statusUpdatedLabel;

    private final ObservableList<OrderItem> orderItems = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        // wire table columns to properties (use property names of OrderItem getters)
        colProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        orderTable.setItems(orderItems);

        // sample/demo data - replace with real order loading code
        loadDemoOrder();

        // clear status text
        checkingStockLabel.setText("");
        statusUpdatedLabel.setText("");

        // selection listener to show stock checking status
        orderTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                checkingStockLabel.setText("Checking stock for: " + newSel.getProduct() + " ...");
                // simulate check (in real app, call backend)
                Platform.runLater(() -> checkingStockLabel.setText("Stock: " + newSel.getStock()));
            } else {
                checkingStockLabel.setText("");
            }
        });
    }

    private void loadDemoOrder() {
        orderItems.clear();
        orderItems.addAll(
                new OrderItem("The Alchemist", 2, 5, 299.00),
                new OrderItem("Java Programming", 1, 0, 850.50),
                new OrderItem("Data Structures", 3, 10, 420.75)
        );
    }


    @FXML
    private void handleAccept(ActionEvent event) {
        OrderItem selected = orderTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No selection", "Please select an order item to accept.");
            return;
        }

        // validate stock
        if (selected.getStock() < selected.getQuantity()) {
            String msg = String.format("Insufficient stock for '%s'. Requested: %d, Available: %d",
                    selected.getProduct(), selected.getQuantity(), selected.getStock());
            showAlert(Alert.AlertType.ERROR, "Stock check failed", msg);
            statusUpdatedLabel.setText("Order cannot be accepted: insufficient stock.");
            return;
        }

        // perform accept actions: decrease stock, update order status, notify backend (replace with REST call)
        boolean success = performAccept(selected);
        if (success) {
            statusUpdatedLabel.setText("Order accepted and stock updated.");
            showAlert(Alert.AlertType.INFORMATION, "Accepted", "Order accepted successfully.");
            // refresh table to show updated stock
            orderTable.refresh();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to accept order. Try again later.");
            statusUpdatedLabel.setText("Failed to update order status.");
        }
    }


    @FXML
    private void handleReject(ActionEvent event) {
        OrderItem selected = orderTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No selection", "Please select an order item to reject.");
            return;
        }

        // perform reject actions: mark order rejected, notify backend (replace with REST call)
        boolean success = performReject(selected);
        if (success) {
            statusUpdatedLabel.setText("Order rejected.");
            showAlert(Alert.AlertType.INFORMATION, "Rejected", "Order rejected successfully.");
            // optionally remove from list or change UI to indicate rejection
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to reject order. Try again later.");
            statusUpdatedLabel.setText("Failed to update order status.");
        }
    }


    private boolean performAccept(OrderItem item) {
        try {
            // update local stock for demo
            int newStock = item.getStock() - item.getQuantity();
            item.setStock(newStock);
            // TODO: call backend API to persist acceptance & stock change
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean performReject(OrderItem item) {
        try {
            // TODO: call backend API to mark order rejected
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }


    public static class OrderItem {
        private String product;
        private int quantity;
        private int stock;
        private double price;

        public OrderItem(String product, int quantity, int stock, double price) {
            this.product = product;
            this.quantity = quantity;
            this.stock = stock;
            this.price = price;
        }

        // getters & setters used by PropertyValueFactory
        public String getProduct() { return product; }
        public void setProduct(String product) { this.product = product; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public int getStock() { return stock; }
        public void setStock(int stock) { this.stock = stock; }

        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
    }
}
