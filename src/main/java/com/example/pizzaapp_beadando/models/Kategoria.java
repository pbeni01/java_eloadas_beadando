package com.example.pizzaapp_beadando.models;


public class Kategoria {

    private String nev;
    private int ar;

    public Kategoria(String nev, int ar) {
        this.nev = nev;
        this.ar = ar;
    }


    public int getAr() {
        return ar;
    }

    public void setAr(int ar) {
        this.ar = ar;
    }


    // Constructor
    public Kategoria(String nev) {
        this.nev = nev;
    }

    // Getters and Setters
    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }
}


