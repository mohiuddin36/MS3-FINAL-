
package com.example.cse213_finalproject_group48_rokomari_.seller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.application.Platform;


public class managepaymentrasactions {

    @FXML private Label headerLabel;                // layoutX="17" layoutY="5"
    @FXML private Label paymentRecordsLabel;        // layoutX="18" layoutY="133"
    @FXML private Button paymentButton;             // layoutX="25" layoutY="67"
    @FXML private TableView<PaymentRecord> paymentsTable; // layoutX="14" layoutY="176"
    @FXML private TableColumn<PaymentRecord, String> monthColumn;
    @FXML private TableColumn<PaymentRecord, String> totalSalesColumn;
    @FXML private TableColumn<PaymentRecord, String> ordersColumn;
    @FXML private Button requestPayoutButton;       // layoutX="279" layoutY="276"
    @FXML private Label payoutConfirmationLabel;    // layoutX="281" layoutY="337"

    private final ObservableList<PaymentRecord> paymentData = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        // Bind columns to PaymentRecord properties
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        totalSalesColumn.setCellValueFactory(new PropertyValueFactory<>("totalSales"));
        ordersColumn.setCellValueFactory(new PropertyValueFactory<>("orders"));

        paymentsTable.setItems(paymentData);

        // simulate event-1: seller opens Payments view -> retrieve records
        fetchPaymentRecords();
    }


    private void fetchPaymentRecords() {
        paymentData.clear();

        // Sample/demo data — replace with backend response
        paymentData.addAll(
                new PaymentRecord("January 2025", "1250.00", "45"),
                new PaymentRecord("February 2025", "980.50", "30"),
                new PaymentRecord("March 2025", "1430.75", "52")
        );

        // Clear previous confirmation text
        payoutConfirmationLabel.setText("");
    }

    private void handlePaymentButton(ActionEvent event) {
        // Example behaviour: refresh table
        fetchPaymentRecords();
    }

    @FXML
    private void handleRequestPayout(ActionEvent event) {
        PaymentRecord selected = paymentsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showValidationError("Please select a month / transaction to request payout.");
            return;
        }

        // event-4: validate account info
        boolean accountOk = validateAccountInfo();
        if (!accountOk) {
            showValidationError("Payout account not set or invalid. Please configure payout account.");
            return;
        }

        // Confirm and perform payout — in a real app you'd show a confirmation dialog
        String confirmationText = String.format("Requesting payout of %s for %s", selected.getTotalSales(), selected.getMonth());
        showPayoutConfirmation("Confirm: " + confirmationText);

        // Simulate performing payout (call backend)
        boolean success = performPayout(selected);
        if (success) {
            // event-5: display payout confirmation (OP)
            payoutConfirmationLabel.setText("Payout requested successfully for " + selected.getMonth());
            // Optionally refresh table to reflect new state
            Platform.runLater(this::fetchPaymentRecords);
        } else {
            showValidationError("Failed to request payout. Please try again later.");
        }
    }


    private boolean validateAccountInfo() {
        // TODO: call backend to check account info. For skeleton, assume true.
        return true;
    }


    private boolean performPayout(PaymentRecord selected) {
        // TODO: call your payout API here and return true/false depending on response.
        // For skeleton, return true to simulate success.
        return true;
    }


    private void showPayoutConfirmation(String text) {
        payoutConfirmationLabel.setText(text);
    }


    private void showValidationError(String text) {
        payoutConfirmationLabel.setText(text);
    }


    public static class PaymentRecord {
        private final String month;
        private final String totalSales;
        private final String orders;

        public PaymentRecord(String month, String totalSales, String orders) {
            this.month = month;
            this.totalSales = totalSales;
            this.orders = orders;
        }

        public String getMonth() {
            return month;
        }

        public String getTotalSales() {
            return totalSales;
        }

        public String getOrders() {
            return orders;
        }
    }
}
