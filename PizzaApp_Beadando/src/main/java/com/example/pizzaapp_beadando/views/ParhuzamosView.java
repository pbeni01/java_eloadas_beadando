package com.example.pizzaapp_beadando.views;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParhuzamosView {

    private final Stage primaryStage;
    private final Scene mainMenuScene;

    public ParhuzamosView(Stage primaryStage, Scene mainMenuScene) {
        this.primaryStage = primaryStage;
        this.mainMenuScene = mainMenuScene;
    }

    public VBox getView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label label1 = new Label("Label 1: ");
        Label label2 = new Label("Label 2: ");

        Button startButton = new Button("Indítás");
        Button backButton = new Button("Vissza");

        // Párhuzamos szálak futtatása
        startButton.setOnAction(e -> startParallelExecution(label1, label2));

        // Vissza gomb az alapmenübe
        backButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));

        root.getChildren().addAll(label1, label2, startButton, backButton);

        return root;
    }

    private void startParallelExecution(Label label1, Label label2) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            int count = 0;
            while (count < 10) {
                count++;
                final String text = "Label 1: " + count;
                Platform.runLater(() -> label1.setText(text));
                try {
                    Thread.sleep(1000); // 1 másodpercenként frissít
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.submit(() -> {
            int count = 0;
            while (count < 5) {
                count++;
                final String text = "Label 2: " + count;
                Platform.runLater(() -> label2.setText(text));
                try {
                    Thread.sleep(2000); // 2 másodpercenként frissít
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.shutdown();
    }
}
