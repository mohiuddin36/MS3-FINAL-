package com.example.cse213_finalproject_group48_rokomari_.seller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.chart.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class viewsalesreport
{

    @FXML private DatePicker fromDate;
    @FXML private DatePicker toDate;
    @FXML private ChoiceBox<String> groupByBox;
    @FXML private Button fetchButton;
    @FXML private Button exportButton;

    @FXML private ChoiceBox<String> categoryBox;
    @FXML private TextField searchField;
    @FXML private CheckBox includeRefundsCheck;

    @FXML private Label totalSalesLabel;
    @FXML private Label totalOrdersLabel;
    @FXML private Label avgOrderValueLabel;

    @FXML private BarChart<String, Number> salesChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;

    @FXML private TableView<SalesRow> salesTable;
    @FXML private TableColumn<SalesRow, String> colDate;
    @FXML private TableColumn<SalesRow, String> colOrder;
    @FXML private TableColumn<SalesRow, String> colProduct;
    @FXML private TableColumn<SalesRow, Integer> colQty;
    @FXML private TableColumn<SalesRow, String> colPrice;
    @FXML private TableColumn<SalesRow, String> colTotal;
    @FXML private TableColumn<SalesRow, String> colStatus;

    @FXML private Label messageLabel;
    @FXML private Pagination pagination;

    private ObservableList<SalesRow> salesData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set defaults
        toDate.setValue(LocalDate.now());
        fromDate.setValue(LocalDate.now().minusDays(30));
        groupByBox.setItems(FXCollections.observableArrayList("Day","Week","Month"));
        groupByBox.getSelectionModel().select("Day");

        // Populate category choices (replace with backend values)
        categoryBox.setItems(FXCollections.observableArrayList("All","Fiction","Non-fiction","Education"));
        categoryBox.getSelectionModel().select(0);

        // Table column cell factories (simple)
        colDate.setCellValueFactory(c -> c.getValue().dateProperty());
        colOrder.setCellValueFactory(c -> c.getValue().orderIdProperty());
        colProduct.setCellValueFactory(c -> c.getValue().productProperty());
        colQty.setCellValueFactory(c -> c.getValue().qtyProperty().asObject());
        colPrice.setCellValueFactory(c -> c.getValue().priceProperty());
        colTotal.setCellValueFactory(c -> c.getValue().totalProperty());
        colStatus.setCellValueFactory(c -> c.getValue().statusProperty());

        salesTable.setItems(salesData);

        // Setup pagination (simple page size)
        pagination.setPageFactory(this::createPage);

        // Disable export until data loads
        exportButton.setDisable(true);
    }

    // Called when user clicks Fetch
    @FXML
    private void handleFetch(ActionEvent event) {
        messageLabel.setText("");
        // 1. access check
        if (!isUserAuthorized()) {
            messageLabel.setText("Access denied: you do not have permission to view sales reports.");
            clearDisplay();
            return;
        }

        // 2. gather filter values
        LocalDate from = fromDate.getValue();
        LocalDate to = toDate.getValue();
        String groupBy = groupByBox.getSelectionModel().getSelectedItem();
        String category = categoryBox.getSelectionModel().getSelectedItem();
        String search = searchField.getText().trim();
        boolean includeRefunds = includeRefundsCheck.isSelected();

        // 3. fetch data (replace with real service call)
        List<SalesRow> rows = fetchSalesDataMock(from, to, category, search, includeRefunds);



