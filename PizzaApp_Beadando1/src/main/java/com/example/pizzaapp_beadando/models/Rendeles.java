package com.example.pizzaapp_beadando.models;


public class Rendeles {
    private String pizzanev;
    private int darab;
    private String felvetel; // String format for datetime
    private String kiszallitas; // String format for datetime

    // Constructor
    public Rendeles(String pizzanev, int darab, String felvetel, String kiszallitas) {
        this.pizzanev = pizzanev;
        this.darab = darab;
        this.felvetel = felvetel;
        this.kiszallitas = kiszallitas;
    }

    // Getters and Setters
    public String getPizzanev() {
        return pizzanev;
    }

    public void setPizzanev(String pizzanev) {
        this.pizzanev = pizzanev;
    }

    public int getDarab() {
        return darab;
    }

    public void setDarab(int darab) {
        this.darab = darab;
    }

    public String getFelvetel() {
        return felvetel;
    }

    public void setFelvetel(String felvetel) {
        this.felvetel = felvetel;
    }

    public String getKiszallitas() {
        return kiszallitas;
    }

    public void setKiszallitas(String kiszallitas) {
        this.kiszallitas = kiszallitas;
    }
}



