package com.example.line;

import javafx.scene.paint.Color;

public class Zwischenpunkt extends Punkt{
    Zwischenpunkt(double xPos, double yPos) {
        super(xPos, yPos, Color.BLACK, "Zwischenpunkt");
    }

    /**
     * Methode um die Daten vom Zwischenpunkt als String auszugeben.
     * @return String mit Daten vom Zwischenpunkt
     */
    @Override
    public String toString(){
        return super.toString("Zwischenpunkt");
    }
}