package com.example.line;

import javafx.scene.paint.Color;

public class Bushaltestelle extends Punkt{
    Bushaltestelle(double xPos, double yPos) {
        super(xPos, yPos, Color.BLUE);
    }

    @Override
    public String toString(){
        return super.toString("Bushaltestelle");
    }
}
