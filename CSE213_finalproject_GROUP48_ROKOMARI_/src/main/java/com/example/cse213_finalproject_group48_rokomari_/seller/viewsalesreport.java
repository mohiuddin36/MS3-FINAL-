
package com.example.cse213_finalproject_group48_rokomari_.seller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;


public class viewsalesreport {

    @FXML private Button generateReportButton;
    @FXML private Label reportLabel;

    @FXML private TableView<SalesRecord> reportTable;
    @FXML private TableColumn<SalesRecord, String> monthColumn;
    @FXML private TableColumn<SalesRecord, Double> totalSalesColumn;
    @FXML private TableColumn<SalesRecord, Integer> numberOfOrdersColumn;

    @FXML private TextArea workflowTextArea;

    private final ObservableList<SalesRecord> salesData = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        // Bind columns to SalesRecord properties
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        totalSalesColumn.setCellValueFactory(new PropertyValueFactory<>("totalSales"));
        numberOfOrdersColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfOrders"));

        reportTable.setItems(salesData);

        // Display initial workflow in TextArea
        if (workflowTextArea != null) {
            workflowTextArea.setText("Sales Report Workflow:\n" +
                    "1. Seller clicks 'Sales Report'\n" +
                    "2. System retrieves sales data\n" +
                    "3. Display sales report in table");
        }
    }


    @FXML
    private void handleGenerateReport(ActionEvent event) {
        salesData.clear();

        // Sample data - replace with backend API or database query
        salesData.addAll(
                new SalesRecord("January 2025", 1250.50, 45),
                new SalesRecord("February 2025", 980.00, 30),
                new SalesRecord("March 2025", 1430.75, 52)
        );

        if (reportLabel != null) {
            reportLabel.setText("Sales report generated successfully.");
        }
    }


    public static class SalesRecord {
        private final String month;
        private final double totalSales;
        private final int numberOfOrders;

        public SalesRecord(String month, double totalSales, int numberOfOrders) {
            this.month = month;
            this.totalSales = totalSales;
            this.numberOfOrders = numberOfOrders;
        }

        public String getMonth() { return month; }
        public double getTotalSales() { return totalSales; }
        public int getNumberOfOrders() { return numberOfOrders; }
    }
}
