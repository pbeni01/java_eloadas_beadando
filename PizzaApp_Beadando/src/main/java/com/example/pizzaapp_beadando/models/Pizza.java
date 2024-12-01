package com.example.pizzaapp_beadando.models;


public class Pizza {
    private String nev;
    private String kategorianev;
    private boolean vegetarianus;

    public Pizza(String nev, String kategorianev, boolean vegetarianus) {
        this.nev = nev;
        this.kategorianev = kategorianev;
        this.vegetarianus = vegetarianus;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getKategorianev() {
        return kategorianev;
    }

    public void setKategorianev(String kategorianev) {
        this.kategorianev = kategorianev;
    }

    public boolean isVegetarianus() {
        return vegetarianus;
    }

    public void setVegetarianus(boolean vegetarianus) {
        this.vegetarianus = vegetarianus;
    }
}




