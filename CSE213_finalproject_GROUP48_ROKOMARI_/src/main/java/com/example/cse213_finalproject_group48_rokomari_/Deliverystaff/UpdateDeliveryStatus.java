
package com.example.cse213_finalproject_group48_rokomari_.Deliverystaff;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
        import javafx.event.ActionEvent;



public class UpdateDeliveryStatus {

    @FXML private TableView<DeliveryRecord> deliveryTable;
    @FXML private TableColumn<DeliveryRecord, String> customerColumn;
    @FXML private TableColumn<DeliveryRecord, String> orderIdColumn;
    @FXML private TableColumn<DeliveryRecord, String> statusColumn;

    @FXML private ComboBox<String> newStatusComboBox;
    @FXML private Button updateStatusButton;
    @FXML private Label statusLabel;

    private final ObservableList<DeliveryRecord> deliveryData = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        // Bind columns to DeliveryRecord properties
        customerColumn.setCellValueFactory(cellData -> cellData.getValue().customerProperty());
        orderIdColumn.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        deliveryTable.setItems(deliveryData);

        // Add some sample status options to ComboBox
        newStatusComboBox.setItems(FXCollections.observableArrayList("Pending", "Dispatched", "Delivered", "Cancelled"));

        // Load sample delivery data (replace with backend call)
        loadSampleData();

        // Clear status label initially
        clearStatus();
    }


    @FXML
    private void handleUpdateStatus(ActionEvent event) {
        clearStatus();

        DeliveryRecord selectedRecord = deliveryTable.getSelectionModel().getSelectedItem();
        String newStatus = newStatusComboBox.getSelectionModel().getSelectedItem();

        if (selectedRecord == null) {
            showError("Please select a delivery record to update.");
            return;
        }
        if (newStatus == null || newStatus.isEmpty()) {
            showError("Please select a new status.");
            return;
        }

        // Simulate status update (replace with backend/database call)
        selectedRecord.setStatus(newStatus);
        deliveryTable.refresh(); // refresh table to show updated status

        showSuccess("Status updated successfully.");
    }

    /* -------------------- Helper methods -------------------- */

    private void loadSampleData() {
        deliveryData.addAll(
                new DeliveryRecord("Alice", "ORD001", "Pending"),
                new DeliveryRecord("Bob", "ORD002", "Dispatched"),
                new DeliveryRecord("Charlie", "ORD003", "Pending")
        );
    }

    private void showError(String msg) {
        if (statusLabel !=

