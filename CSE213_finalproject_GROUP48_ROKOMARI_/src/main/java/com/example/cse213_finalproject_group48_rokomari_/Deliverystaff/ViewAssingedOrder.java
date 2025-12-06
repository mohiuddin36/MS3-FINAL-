
package com.example.cse213_finalproject_group48_rokomari_.Deliverystaff;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;


public class ViewAssingedOrder {

    // Injected root controls (optional — add fx:id in FXML if you want them injected)
    @FXML private TableView<AssignedOrder> ordersTable;
    @FXML private Button myOrderButton;
    @FXML private Label statusLabel;

    // backing list (replace with real data source / DB call)
    private final ObservableList<AssignedOrder> orders = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // If the TableView was not given an fx:id in the FXML and therefore wasn't injected,
        // we can't proceed. Check and bail with a console message (safe no-op).
        if (ordersTable == null) {
            System.out.println("Warning: ordersTable was not injected. Add fx:id=\"ordersTable\" to your TableView in FXML to enable controller wiring.");
            return;
        }

        // Configure columns by index (matches the 4 columns in your FXML)
        // Column 0 -> Order Id
        TableColumn<AssignedOrder, String> orderIdCol = (TableColumn<AssignedOrder, String>) ordersTable.getColumns().get(0);
        orderIdCol.setCellValueFactory(cell -> cell.getValue().orderIdProperty());

        // Column 1 -> Customer Name
        TableColumn<AssignedOrder, String> customerCol = (TableColumn<AssignedOrder, String>) ordersTable.getColumns().get(1);
        customerCol.setCellValueFactory(cell -> cell.getValue().customerNameProperty());

        // Column 2 -> Address
        TableColumn<AssignedOrder, String> addressCol = (TableColumn<AssignedOrder, String>) ordersTable.getColumns().get(2);
        addressCol.setCellValueFactory(cell -> cell.getValue().addressProperty());

        // Column 3 -> Status
        TableColumn<AssignedOrder, String> statusCol = (TableColumn<AssignedOrder, String>) ordersTable.getColumns().get(3);
        statusCol.setCellValueFactory(cell -> cell.getValue().statusProperty());

        // Sample data — replace with your DB/service call
        orders.addAll(
                new AssignedOrder("ORD-101", "Md. Rahim", "Gopalganj", "Pending"),
                new AssignedOrder("ORD-102", "S. Akter", "Natore", "Out for delivery"),
                new AssignedOrder("ORD-103", "Rana Ahmed", "Dhaka", "Delivered")
        );

        ordersTable.setItems(orders);

        // Safe wiring of button if it was injected
        if (myOrderButton != null) {
            myOrderButton.setOnAction(this::onMyOrderClicked);
        }

        // If statusLabel is present in FXML with fx:id you can show messages there
        if (statusLabel != null) {
            statusLabel.setText(""); // clear any placeholder text
        }
    }


    @FXML
    private void onMyOrderClicked(ActionEvent event) {
        // Example behavior: show only the orders assigned to the current delivery staff.
        // For the demo we simply filter by a fixed example value. Replace with real filtering logic.
        ObservableList<AssignedOrder> filtered = FXCollections.observableArrayList();
        String currentDeliveryStaffIdentifier = "Md. Rahim"; // replace with actual logged-in user id/name

        for (AssignedOrder o : orders) {
            if (o.getCustomerName().equals(currentDeliveryStaffIdentifier)) {
                filtered.add(o);
            }
        }

        if (filtered.isEmpty()) {
            // Nothing found -- fallback to showing all or show message
            ordersTable.setItems(orders); // revert to full list
            if (statusLabel != null) statusLabel.setText("No assigned orders found for you.");
        } else {
            ordersTable.setItems(filtered);
            if (statusLabel != null) statusLabel.setText("Showing your assigned orders.");
        }
    }


    public static class AssignedOrder {
        private final SimpleStringProperty orderId;
        private final SimpleStringProperty customerName;
        private final SimpleStringProperty address;
        private final SimpleStringProperty status;

        public AssignedOrder(String orderId, String customerName, String address, String status) {
            this.orderId = new SimpleStringProperty(orderId);
            this.customerName = new SimpleStringProperty(customerName);
            this.address = new SimpleStringProperty(address);
            this.status = new SimpleStringProperty(status);
        }

        public String getOrderId() { return orderId.get(); }
        public void setOrderId(String value) { orderId.set(value); }
        public SimpleStringProperty orderIdProperty() { return orderId; }

        public String getCustomerName() { return customerName.get(); }
        public void setCustomerName(String value) { customerName.set(value); }
        public SimpleStringProperty customerNameProperty() { return customerName; }

        public String getAddress() { return address.get(); }
        public void setAddress(String value) { address.set(value); }
        public SimpleStringProperty addressProperty() { return address; }

        public String getStatus() { return status.get(); }
        public void setStatus(String value) { status.set(value); }
        public SimpleStringProperty statusProperty() { return status; }
    }
}
