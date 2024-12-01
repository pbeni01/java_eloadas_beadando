package com.example.pizzaapp_beadando.views;

import com.example.pizzaapp_beadando.models.Kategoria;
import com.example.pizzaapp_beadando.models.Pizza;
import com.example.pizzaapp_beadando.models.Rendeles;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Olvas1View {

    private final Stage primaryStage;
    private final Scene mainMenuScene;

    public Olvas1View(Stage primaryStage, Scene mainMenuScene) {
        this.primaryStage = primaryStage;
        this.mainMenuScene = mainMenuScene;
    }

    public VBox getView() {
        VBox vbox = new VBox(10);

        // Pizza táblázat
        TableView<Pizza> pizzaTable = new TableView<>();
        TableColumn<Pizza, String> nevCol = new TableColumn<>("Pizza Név");
        nevCol.setCellValueFactory(new PropertyValueFactory<>("nev"));
        TableColumn<Pizza, String> kategoriakNevCol = new TableColumn<>("Kategória Név");
        kategoriakNevCol.setCellValueFactory(new PropertyValueFactory<>("kategorianev"));
        TableColumn<Pizza, String> vegetarianusCol = new TableColumn<>("Vegetáriánus");
        vegetarianusCol.setCellValueFactory(pizza -> {
            boolean isVegetarian = pizza.getValue().isVegetarianus();
            return new SimpleStringProperty(isVegetarian ? "Igen" : "Nem");
        });
        pizzaTable.getColumns().addAll(nevCol, kategoriakNevCol, vegetarianusCol);
        pizzaTable.setItems(fetchPizzaData());

        // Kategoria táblázat
        TableView<Kategoria> kategoriaTable = new TableView<>();
        TableColumn<Kategoria, String> katNevCol = new TableColumn<>("Kategória Név");
        katNevCol.setCellValueFactory(new PropertyValueFactory<>("nev"));
        TableColumn<Kategoria, Integer> arCol = new TableColumn<>("Ár");
        arCol.setCellValueFactory(new PropertyValueFactory<>("ar"));
        kategoriaTable.getColumns().addAll(katNevCol, arCol);
        kategoriaTable.setItems(fetchKategoriaData());

        // Rendeles táblázat
        TableView<Rendeles> rendelesTable = new TableView<>();
        TableColumn<Rendeles, String> rendeltPizzaCol = new TableColumn<>("Pizza Név");
        rendeltPizzaCol.setCellValueFactory(new PropertyValueFactory<>("pizzanev"));
        TableColumn<Rendeles, Integer> darabCol = new TableColumn<>("Darab");
        darabCol.setCellValueFactory(new PropertyValueFactory<>("darab"));
        TableColumn<Rendeles, String> felvetelCol = new TableColumn<>("Felvétel");
        felvetelCol.setCellValueFactory(new PropertyValueFactory<>("felvetel"));
        TableColumn<Rendeles, String> kiszallitasCol = new TableColumn<>("Kiszállítás");
        kiszallitasCol.setCellValueFactory(new PropertyValueFactory<>("kiszallitas"));
        rendelesTable.getColumns().addAll(rendeltPizzaCol, darabCol, felvetelCol, kiszallitasCol);
        rendelesTable.setItems(fetchRendelesData());

        // Vissza gomb
        Button visszaButton = new Button("Vissza");
        visszaButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));

        vbox.getChildren().addAll(
                new Label("Pizza Táblázat"), pizzaTable,
                new Label("Kategória Táblázat"), kategoriaTable,
                new Label("Rendelés Táblázat"), rendelesTable,
                visszaButton
        );

        return vbox;
    }

    private ObservableList<Pizza> fetchPizzaData() {
        ObservableList<Pizza> data = FXCollections.observableArrayList();
        String url = "jdbc:sqlite:C:/adatok/adatok.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pizza");

            while (rs.next()) {
                data.add(new Pizza(
                        rs.getString("nev"),
                        rs.getString("kategorianev"),
                        rs.getBoolean("vegetarianus")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private ObservableList<Kategoria> fetchKategoriaData() {
        ObservableList<Kategoria> data = FXCollections.observableArrayList();
        String url = "jdbc:sqlite:C:/adatok/adatok.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM kategoria");

            while (rs.next()) {
                data.add(new Kategoria(
                        rs.getString("nev"),
                        rs.getInt("ar")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private ObservableList<Rendeles> fetchRendelesData() {
        ObservableList<Rendeles> data = FXCollections.observableArrayList();
        String url = "jdbc:sqlite:C:/adatok/adatok.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM rendeles");

            while (rs.next()) {
                data.add(new Rendeles(
                        rs.getString("pizzanev"),
                        rs.getInt("darab"),
                        rs.getString("felvetel"),
                        rs.getString("kiszallitas")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
