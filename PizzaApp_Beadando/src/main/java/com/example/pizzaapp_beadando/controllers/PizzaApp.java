package com.example.pizzaapp_beadando.controllers;

import com.example.pizzaapp_beadando.views.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PizzaApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Menü létrehozása
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menü");
        menuBar.getMenus().add(menu);

        // ** Alapértelmezett jelenet létrehozása **
        root.setTop(menuBar);
        Scene mainMenuScene = new Scene(root, 800, 600);

        // ** Olvas1 menüelem (Mindhárom tábla megjelenítése) **
        MenuItem olvas1MenuItem = new MenuItem("Olvas1");
        olvas1MenuItem.setOnAction(e -> {
            Scene scene = new Scene(new Olvas1View(primaryStage, mainMenuScene).getView(), 800, 600); // Az Olvas1 nézet hivatkozása
            primaryStage.setScene(scene);
        });
        menu.getItems().add(olvas1MenuItem);

        // ** Olvas2 menüelem (Szűrős nézet) **
        MenuItem olvas2MenuItem = new MenuItem("Olvas2");
        olvas2MenuItem.setOnAction(e -> {
            Scene scene = new Scene(new Olvas2View(primaryStage, mainMenuScene).getView(), 800, 600); // Az Olvas2 nézet hivatkozása
            primaryStage.setScene(scene);
        });
        menu.getItems().add(olvas2MenuItem);

        // ** Ír menüelem (Új rekord hozzáadása nézet) **
        MenuItem irMenuItem = new MenuItem("Ír");
        irMenuItem.setOnAction(e -> {
            Scene scene = new Scene(new WriteView(primaryStage, mainMenuScene).getView(), 800, 600);
            primaryStage.setScene(scene);
        });
        menu.getItems().add(irMenuItem);

        // ** Módosít menüelem (Rekord módosítása nézet) **
        MenuItem modositMenuItem = new MenuItem("Módosít");
        modositMenuItem.setOnAction(e -> {
            Scene scene = new Scene(new ModifyView(primaryStage, mainMenuScene).getView(), 800, 600);
            primaryStage.setScene(scene);
        });
        menu.getItems().add(modositMenuItem);

// ** Töröl menüelem (Rekord törlése nézet) **
        MenuItem torolMenuItem = new MenuItem("Töröl");
        torolMenuItem.setOnAction(e -> {
            Scene scene = new Scene(new TorolView(primaryStage, primaryStage.getScene()).getView(), 800, 600);
            primaryStage.setScene(scene);
        });
        menu.getItems().add(torolMenuItem);

        // ** Párhuzamos menüelem (Párhuzamos végrehajtás nézet) **
        MenuItem parhuzamosMenuItem = new MenuItem("Párhuzamos");
        parhuzamosMenuItem.setOnAction(e -> {
            Scene scene = new Scene(new ParhuzamosView(primaryStage, primaryStage.getScene()).getView(), 800, 600);
            primaryStage.setScene(scene);
        });
        menu.getItems().add(parhuzamosMenuItem);




        // Menü hozzáadása a gyökérelemhez
        primaryStage.setScene(mainMenuScene);
        primaryStage.setTitle("PizzaApp");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
