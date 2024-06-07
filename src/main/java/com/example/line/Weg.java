package com.example.line;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Klasse für die Wege welche auf der Benutzeroberfläche angezeigt werden.
 *
 * @author Amel Aho
 * @version 07.06.2024
 */
public abstract class Weg extends Line {
    private boolean horizontal = false;
    private final double startX;
    private final double startY;
    private final double endeX;
    private final double endeY;
    private final Color farbe;
    private final int schritt;

    /**
     * Konstruktor für den ersten Vertikalen weg.
     *
     * @param start Startpunkt
     * @param ende  End-zwischenpunkt
     * @param farbe Farbe vom Weg
     */
    Weg(Punkt start, Zwischenpunkt ende, Color farbe) {
        super(start.getXPos(), start.getYPos(), start.getXPos(), ende.getYPos());
        super.setStroke(farbe);
        super.setStrokeWidth(2);
        this.startX = start.getXPos();
        this.startY = start.getYPos();
        this.endeX = ende.getXPos();
        this.endeY = ende.getXPos();
        this.farbe = farbe;
        this.schritt = 0;
    }

    /**
     * Konstruktor für den ersten horizontalen weg.
     *
     * @param start      Startpunkt
     * @param ende       End-zwischenpunkt
     * @param farbe      Farbe vom Weg
     * @param horizontal richtung vom Weg
     */
    Weg(Punkt start, Zwischenpunkt ende, Color farbe, boolean horizontal){
        super(start.getXPos(), ende.getYPos(), ende.getXPos(), ende.getYPos());
        super.setStroke(farbe);
        super.setStrokeWidth(2);
        this.horizontal = horizontal;
        this.startX = start.getXPos();
        this.startY = start.getYPos();
        this.endeX = ende.getXPos();
        this.endeY = ende.getXPos();
        this.farbe = farbe;
        this.schritt = 1;
    }

    /**
     * Konstruktor für den zweiten Vertikalen weg.
     *
     * @param start Start-zwischenpunkt
     * @param ende  Endpunkt
     * @param farbe Farbe vom Weg
     */
    Weg(Zwischenpunkt start, Punkt ende, Color farbe){
        super(start.getXPos(), start.getYPos(), start.getXPos(), ende.getYPos());
        super.setStroke(farbe);
        super.setStrokeWidth(2);
        this.startX = start.getXPos();
        this.startY = start.getYPos();
        this.endeX = ende.getXPos();
        this.endeY = ende.getXPos();
        this.farbe = farbe;
        this.schritt = 2;
    }

    /**
     * Konstruktor für den zweiten horizontalen weg.
     *
     * @param start      Start-zwischenpunkt
     * @param ende       Endpunkt
     * @param farbe      Farbe vom Weg
     * @param horizontal richtung vom Weg
     */
    Weg(Zwischenpunkt start, Punkt ende, Color farbe, boolean horizontal){
        super(start.getXPos(), ende.getYPos(), ende.getXPos(), ende.getYPos());
        super.setStroke(farbe);
        super.setStrokeWidth(2);
        this.horizontal = horizontal;
        this.startX = start.getXPos();
        this.startY = start.getYPos();
        this.endeX = ende.getXPos();
        this.endeY = ende.getXPos();
        this.farbe = farbe;
        this.schritt = 3;
    }

    Weg(Bahnhof start, Bahnhof ende, Color farbe, int schritt){
        super(start.getXPos(), start.getYPos(), start.getXPos(), ende.getYPos());
        this.startX = start.getXPos();
        this.startY = start.getYPos();
        this.endeX = ende.getXPos();
        this.endeY = ende.getXPos();
        this.farbe = farbe;
        this.schritt = schritt;
    }

    /**
     * Gibt alle Instanzvariablen als String aus
     *
     * @return String mit Daten vom Parkhaus
     */
    @Override
    public String toString(){
        return "Weg[" + "StartX=" + startX +
                ", StartY=" + startY +
                ", EndeX=" + endeX +
                ", EndeY=" + endeY +
                ", StrokeColor=" + farbe +
                ", Horizontal=" + horizontal +
                ", Schritt=" + schritt +
                "]";
    }

    /**
     * Gibt alle Instanzvariablen als String aus
     *
     * @param datentyp Ob es eine Straße, Buslinie oder Schiene sind.
     * @return Daten vom Objekt als String.
     */
    public String toString(String datentyp){
        return datentyp + "[" + "StartX=" + startX +
                ", StartY=" + startY +
                ", EndeX=" + endeX +
                ", EndeY=" + endeY +
                ", StrokeColor=" + farbe +
                ", Horizontal=" + horizontal +
                ", Schritt=" + schritt +
                "]";
    }
}
