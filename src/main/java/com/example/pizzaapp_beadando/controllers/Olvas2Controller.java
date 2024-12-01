package com.example.pizzaapp_beadando.controllers;

import com.example.pizzaapp_beadando.models.FilterCriteria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class Olvas2Controller {
    private final TableView<Object> tableView = new TableView<>();

    public Olvas2Controller() {
        setupTableView();
    }

    public TableView<Object> getTableView() {
        return tableView;
    }

    public void filterData(String text, String comboValue, Object radioValue, boolean isChecked) {
        // Alkalmazzuk a szűrési logikát
        ObservableList<Object> filteredData = FXCollections.observableArrayList();
        // Szűrés logikája itt
        // Példa adatok hozzáadása:
        filteredData.add(new Object()); // Itt a megfelelő modellt kell használnod
        tableView.setItems(filteredData);
    }

    private void setupTableView() {
        TableColumn<Object, String> column1 = new TableColumn<>("Oszlop 1");
        column1.setCellValueFactory(new PropertyValueFactory<>("property1"));

        TableColumn<Object, String> column2 = new TableColumn<>("Oszlop 2");
        column2.setCellValueFactory(new PropertyValueFactory<>("property2"));

        tableView.getColumns().addAll(column1, column2);
    }
}
