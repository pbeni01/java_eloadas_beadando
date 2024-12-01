package com.example.pizzaapp_beadando.views;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.pizzaapp_beadando.models.Pizza;

public class Olvas2View {

    private TableView<Pizza> tableView;
    private TextField nameField;
    private ComboBox<String> categoryComboBox;
    private RadioButton vegetarianRadio;
    private RadioButton nonVegetarianRadio;
    private CheckBox longNameCheckBox;

    private final Stage primaryStage;
    private final Scene mainMenuScene;

    public Olvas2View(Stage primaryStage, Scene mainMenuScene) {
        this.primaryStage = primaryStage;
        this.mainMenuScene = mainMenuScene;
    }

    public VBox getView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // Szűrő űrlap elemek
        nameField = new TextField();
        nameField.setPromptText("Pizza neve");

        categoryComboBox = new ComboBox<>();
        loadCategories();
        categoryComboBox.setValue("Összes");

        ToggleGroup vegetarianGroup = new ToggleGroup();
        vegetarianRadio = new RadioButton("Vegetáriánus");
        nonVegetarianRadio = new RadioButton("Nem vegetáriánus");
        vegetarianRadio.setToggleGroup(vegetarianGroup);
        nonVegetarianRadio.setToggleGroup(vegetarianGroup);

        longNameCheckBox = new CheckBox("Csak 5 karakternél hosszabb pizzák");

        Button searchButton = new Button("Keresés");
        searchButton.setOnAction(e -> filterData());

        // Táblázat
        tableView = new TableView<>();
        TableColumn<Pizza, String> nameColumn = new TableColumn<>("Név");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nev"));
        TableColumn<Pizza, String> categoryColumn = new TableColumn<>("Kategória");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("kategorianev"));
        TableColumn<Pizza, String> vegetarianColumn = new TableColumn<>("Vegetáriánus");
        vegetarianColumn.setCellValueFactory(pizza -> {
            boolean isVegetarian = pizza.getValue().isVegetarianus();
            return new SimpleStringProperty(isVegetarian ? "Igen" : "Nem");
        });
        tableView.getColumns().addAll(nameColumn, categoryColumn, vegetarianColumn);

        Button visszaButton = new Button("Vissza");
        visszaButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));

        root.getChildren().addAll(nameField, categoryComboBox, vegetarianRadio, nonVegetarianRadio, longNameCheckBox, searchButton, tableView, visszaButton);

        return root;
    }

    private void loadCategories() {
        String url = "jdbc:sqlite:C:/adatok/adatok.db";
        ObservableList<String> categories = FXCollections.observableArrayList("Összes");

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement("SELECT DISTINCT nev FROM kategoria")) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                categories.add(rs.getString("nev"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        categoryComboBox.setItems(categories);
    }

    private void filterData() {
        String url = "jdbc:sqlite:C:/adatok/adatok.db";
        ObservableList<Pizza> data = FXCollections.observableArrayList();

        String sql = "SELECT * FROM pizza WHERE 1=1";
        if (!nameField.getText().isEmpty()) sql += " AND nev LIKE ?";
        if (!categoryComboBox.getValue().equals("Összes")) sql += " AND kategorianev = ?";
        if (vegetarianRadio.isSelected()) sql += " AND vegetarianus = 1";
        if (nonVegetarianRadio.isSelected()) sql += " AND vegetarianus = 0";
        if (longNameCheckBox.isSelected()) sql += " AND LENGTH(nev) > 5";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int index = 1;
            if (!nameField.getText().isEmpty()) pstmt.setString(index++, "%" + nameField.getText() + "%");
            if (!categoryComboBox.getValue().equals("Összes")) pstmt.setString(index++, categoryComboBox.getValue());

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                data.add(new Pizza(rs.getString("nev"), rs.getString("kategorianev"), rs.getBoolean("vegetarianus")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        tableView.setItems(data);
    }
}
