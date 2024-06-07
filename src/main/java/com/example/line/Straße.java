package com.example.line;

import javafx.scene.paint.Color;

/**
 * Straßen verbindungen zwischen Parkhäuser.
 *
 * @author Amel Aho
 * @version 07.06.2024
 */
public class Straße extends Weg {

    /**
     * Konstruktor für die erste Vertikale Straße.
     *
     * @param start Startpunkt
     * @param ende End-zwischenpunkt
     */
    Straße(Punkt start, Zwischenpunkt ende) {
        super(start, ende, Color.BLACK);
    }

    /**
     * Konstruktor für die erste horizontale Straße.
     *
     * @param start      Startpunkt
     * @param ende       End-zwischenpunkt
     * @param horizontal richtung vom Weg
     */
    Straße(Punkt start, Zwischenpunkt ende, boolean horizontal) {
        super(start, ende, Color.BLACK, horizontal);
    }

    /**
     * Konstruktor für die zweite Vertikale Straße.
     *
     * @param start Start-zwischenpunkt
     * @param ende  Endpunkt
     */
    Straße(Zwischenpunkt start, Punkt ende) {
        super(start, ende, Color.BLACK);
    }

    /**
     * Konstruktor für die zweite horizontale Straße.
     *
     * @param start      Start-zwischenpunkt
     * @param ende       Endpunkt
     * @param horizontal richtung vom Weg
     */
    Straße(Zwischenpunkt start, Punkt ende, boolean horizontal) {
        super(start, ende, Color.BLACK, horizontal);
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