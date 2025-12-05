module com.example.cse213_finalproject_group48_rokomari_ {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cse213_finalproject_group48_rokomari_ to javafx.fxml;
    exports com.example.cse213_finalproject_group48_rokomari_;
    exports com.example.cse213_finalproject_group48_rokomari_.Seller;
    opens com.example.cse213_finalproject_group48_rokomari_.Seller to javafx.fxml;
    exports com.example.cse213_finalproject_group48_rokomari_.seller;
    opens com.example.cse213_finalproject_group48_rokomari_.seller to javafx.fxml;
}