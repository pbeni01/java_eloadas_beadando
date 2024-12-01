package com.example.pizzaapp_beadando.views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TorolView {

    private ComboBox<String> pizzaNameComboBox;
    private final Stage primaryStage;
    private final Scene mainMenuScene;

    public TorolView(Stage primaryStage, Scene mainMenuScene) {
        this.primaryStage = primaryStage;
        this.mainMenuScene = mainMenuScene;
    }

    public VBox getView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // Rekord neve kiválasztása
        Label selectLabel = new Label("Válassz pizzát név alapján:");
        pizzaNameComboBox = new ComboBox<>();
        loadPizzaNames();

        // Törlés gomb
        Button deleteButton = new Button("Törlés");
        deleteButton.setOnAction(e -> deleteRecord());

        // Vissza gomb
        Button visszaButton = new Button("Vissza");
        visszaButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));

        root.getChildren().addAll(selectLabel, pizzaNameComboBox, deleteButton, visszaButton);

        return root;
    }

    private void loadPizzaNames() {
        String url = "jdbc:sqlite:C:/adatok/adatok.db";
        pizzaNameComboBox.getItems().clear();

        String sql = "SELECT nev FROM pizza";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                pizzaNameComboBox.getItems().add(rs.getString("nev"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteRecord() {
        if (pizzaNameComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Figyelmeztetés");
            alert.setHeaderText(null);
            alert.setContentText("Válassz ki egy pizzát a törléshez!");
            alert.showAndWait();
            return;
        }

        String url = "jdbc:sqlite:C:/adatok/adatok.db";
        String sql = "DELETE FROM pizza WHERE nev = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pizzaNameComboBox.getValue());
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Siker");
                alert.setHeaderText(null);
                alert.setContentText("A pizza sikeresen törölve!");
                alert.showAndWait();

                // Frissítsük a lenyíló listát
                loadPizzaNames();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hiba");
                alert.setHeaderText(null);
                alert.setContentText("Nem sikerült a rekord törlése.");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba");
            alert.setHeaderText(null);
            alert.setContentText("Nem sikerült a törlés végrehajtása.");
            alert.showAndWait();
        }
    }
}
