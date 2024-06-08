package com.example.line;

import javafx.scene.paint.Color;

/**
 * Straßen verbindungen zwischen Parkhäuser.
 *
 * @author Amel Aho
 * @version 07.06.2024
 */
public class Straße extends Weg {

    Straße(Punkt start, Punkt ende, Zwischenpunkt mitte, boolean horizontal, boolean zuMitte, int schritt) {
        super(start, ende, mitte, Color.BLACK, horizontal, zuMitte, schritt);
    }

    /**
     * Gibt alle Instanzvariablen als String aus
     * @return String mit Daten vom Parkhaus
     */
    @Override
    public String toString() {
        return super.toString("Straße");
    }
}