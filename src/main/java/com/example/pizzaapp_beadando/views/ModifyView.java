package com.example.pizzaapp_beadando.views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ModifyView {

    private ComboBox<String> recordIdComboBox;
    private TextField nameField;
    private TextField categoryField;
    private CheckBox vegetarianField;

    private final Stage primaryStage;
    private final Scene mainMenuScene;

    public ModifyView(Stage primaryStage, Scene mainMenuScene) {
        this.primaryStage = primaryStage;
        this.mainMenuScene = mainMenuScene;
    }

    public VBox getView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // Rekord ID kiválasztása
        HBox idSelectionBox = new HBox(10);
        idSelectionBox.setPadding(new Insets(10));

        recordIdComboBox = new ComboBox<>();
        loadRecordIds();

        idSelectionBox.getChildren().addAll(
                new Label("Válassz rekordot ID alapján:"),
                recordIdComboBox
        );

        // Rekord módosításához szükséges mezők
        HBox form = new HBox(10);
        form.setPadding(new Insets(10));

        nameField = new TextField();
        nameField.setPromptText("Pizza neve");

        categoryField = new TextField();
        categoryField.setPromptText("Kategória neve");

        vegetarianField = new CheckBox("Vegetáriánus");

        form.getChildren().addAll(
                new Label("Pizza név:"), nameField,
                new Label("Kategória:"), categoryField,
                vegetarianField
        );

        // Módosítás gomb
        Button updateButton = new Button("Módosítás");
        updateButton.setOnAction(e -> updateRecord());

        // Vissza gomb
        Button visszaButton = new Button("Vissza");
        visszaButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));

        root.getChildren().addAll(idSelectionBox, form, updateButton, visszaButton);

        return root;
    }

    private void loadRecordIds() {
        String url = "jdbc:sqlite:C:/adatok/adatok.db";
        recordIdComboBox.getItems().clear();

        String sql = "SELECT rowid FROM pizza";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                recordIdComboBox.getItems().add(rs.getString("rowid"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateRecord() {
        String url = "jdbc:sqlite:C:/adatok/adatok.db";

        String sql = "UPDATE pizza SET nev = ?, kategorianev = ?, vegetarianus = ? WHERE rowid = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nameField.getText());
            pstmt.setString(2, categoryField.getText());
            pstmt.setInt(3, vegetarianField.isSelected() ? 1 : 0);
            pstmt.setString(4, recordIdComboBox.getValue());

            pstmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sikeres módosítás");
            alert.setHeaderText(null);
            alert.setContentText("A rekord sikeresen módosítva!");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba");
            alert.setHeaderText(null);
            alert.setContentText("Nem sikerült a rekord módosítása.");
            alert.showAndWait();
        }
    }
}
