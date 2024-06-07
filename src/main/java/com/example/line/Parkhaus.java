package com.example.line;

import javafx.scene.paint.Color;

/**
 * Parkhaus Punkte, indem Autos parken und rausfahren.
 *
 * @author Amel Aho
 * @version 07.06.2024
 */
public class Parkhaus extends Punkt{

    /**
     * Erstellt ein Parkhaus punkt.
     *
     * @param xPos X-Position vom Parkhaus
     * @param yPos Y-Position vom Parkhaus
     * @param name Name vom Parkhaus
     */
    Parkhaus(double xPos, double yPos, String name) {
        super(xPos, yPos, name, Color.RED);
    }

    /**
     * Methode um die Daten vom Parkhaus als String auszugeben.
     * @return String mit Daten vom Parkhaus
     */
    @Override
    public String toString(){
        return super.toString("Parkhaus");
    }
}
