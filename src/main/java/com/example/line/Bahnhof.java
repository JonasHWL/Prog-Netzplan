package com.example.line;

import javafx.scene.paint.Color;

public class Bahnhof extends Punkt{
    Bahnhof(double xPos, double yPos) {
        super(xPos, yPos, Color.GREEN);
    }

    @Override
    public String toString(){
        return super.toString("Bahnhof");
    }
}
