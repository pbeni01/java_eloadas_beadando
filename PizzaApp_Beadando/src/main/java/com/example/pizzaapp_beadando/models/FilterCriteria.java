package com.example.pizzaapp_beadando.models;

public class FilterCriteria {
    private String textFilter;
    private String comboSelection;
    private String radioSelection;
    private boolean isChecked;

    // Constructor
    public FilterCriteria(String textFilter, String comboSelection, String radioSelection, boolean isChecked) {
        this.textFilter = textFilter;
        this.comboSelection = comboSelection;
        this.radioSelection = radioSelection;
        this.isChecked = isChecked;
    }

    // Getters and Setters
    public String getTextFilter() {
        return textFilter;
    }

    public void setTextFilter(String textFilter) {
        this.textFilter = textFilter;
    }

    public String getComboSelection() {
        return comboSelection;
    }

    public void setComboSelection(String comboSelection) {
        this.comboSelection = comboSelection;
    }

    public String getRadioSelection() {
        return radioSelection;
    }

    public void setRadioSelection(String radioSelection) {
        this.radioSelection = radioSelection;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
