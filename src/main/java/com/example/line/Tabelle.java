package com.example.line;

public class Tabelle {


    String name, art;
    int posX, posY;


    public String getName() {
        return name;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public String getArt() {
        return art;
    }

    public Tabelle(String art, String name, int posx, int posy) {
        this.art = art;
        this.name = name;
        this.posX = posx;
        this.posY = posy;
    }





}
