package com.example.line;

import javafx.scene.paint.Color;

public class Parkhaus extends Punkt{

    Parkhaus(double xPos, double yPos, String name) {
        super(xPos, yPos, name, Color.RED);
    }

    @Override
    public String toString(){
        return super.toString("Parkhaus");
    }
}
