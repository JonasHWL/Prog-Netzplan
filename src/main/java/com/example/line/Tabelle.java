package com.example.line;

import java.util.ArrayList;

public class Tabelle {


    String name, art;
    String posX, posY;


    public String getName() {
        return name;
    }

    public String getPosX() {
        return posX;
    }

    public String getPosY() {
        return posY;
    }

    public String getArt() {
        return art;
    }

    public Tabelle(String art, String name, String posx, String posy) {
        this.art = art;
        this.name = name;
        this.posX = posx;
        this.posY = posy;
    }





}
