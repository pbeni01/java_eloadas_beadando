module com.example.pizzaapp_beadando {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.pizzaapp_beadando to javafx.fxml;
    exports com.example.pizzaapp_beadando.models;
    opens com.example.pizzaapp_beadando.models to javafx.fxml;
    exports com.example.pizzaapp_beadando.views;
    opens com.example.pizzaapp_beadando.views to javafx.fxml;
    exports com.example.pizzaapp_beadando.controllers;
    opens com.example.pizzaapp_beadando.controllers to javafx.fxml;
}