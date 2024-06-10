package com.example.line;

import javafx.scene.paint.Color;

public class Bahnhof extends Punkt{
    Bahnhof(double xPos, double yPos, String name) {
        super(xPos, yPos, name, Color.GREEN);
    }

    @Override
    public String toString(){
        return super.toString("Bahnhof");
    }
}
