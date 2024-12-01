package com.example.pizzaapp_beadando.views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class WriteView {

    private TextField nameField;
    private ComboBox<String> categoryComboBox;
    private CheckBox vegetarianCheckBox;

    private final Stage primaryStage;
    private final Scene mainMenuScene;

    public WriteView(Stage primaryStage, Scene mainMenuScene) {
        this.primaryStage = primaryStage;
        this.mainMenuScene = mainMenuScene;
    }

    public VBox getView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // ** Form elemek létrehozása **
        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setHgap(10);
        form.setVgap(10);

        // Pizza neve mező
        Label nameLabel = new Label("Pizza neve:");
        nameField = new TextField();
        nameField.setPromptText("Írja be a pizza nevét");

        // Kategória lenyíló lista
        Label categoryLabel = new Label("Kategória:");
        categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Király", "Lovag", "Apród", "Főnemes"); // Kategóriák az adatbázis szerint
        categoryComboBox.setValue("Király");

        // Vegetáriánus jelölőnégyzet
        Label vegetarianLabel = new Label("Vegetáriánus:");
        vegetarianCheckBox = new CheckBox("Igen");

        // Mentés gomb
        Button saveButton = new Button("Mentés");
        saveButton.setOnAction(e -> savePizza());

        // Vissza gomb
        Button backButton = new Button("Vissza");
        backButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));

        // Form elemek elrendezése
        form.add(nameLabel, 0, 0);
        form.add(nameField, 1, 0);
        form.add(categoryLabel, 0, 1);
        form.add(categoryComboBox, 1, 1);
        form.add(vegetarianLabel, 0, 2);
        form.add(vegetarianCheckBox, 1, 2);
        form.add(saveButton, 1, 3);

        root.getChildren().addAll(new Label("Új pizza hozzáadása az adatbázishoz"), form, backButton);

        return root;
    }

    private void savePizza() {
        String url = "jdbc:sqlite:C:/adatok/adatok.db";

        String sql = "INSERT INTO pizza (nev, kategorianev, vegetarianus) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nameField.getText());
            pstmt.setString(2, categoryComboBox.getValue());
            pstmt.setInt(3, vegetarianCheckBox.isSelected() ? 1 : 0);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Siker");
                alert.setHeaderText(null);
                alert.setContentText("Az új pizza sikeresen hozzáadva az adatbázishoz!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba");
            alert.setHeaderText(null);
            alert.setContentText("Hiba történt az adatbázisba való mentés során!");
            alert.showAndWait();
        }
    }
}
