package com.example.line;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Klasse für die Wege welche auf der Benutzeroberfläche angezeigt werden.
 *
 * @author Amel Aho
 * @version 20.06.2024
 */
public abstract class Weg extends Line {
    private boolean horizontal = false;
    private boolean zuMitte = false;
    private final double startX;
    private final double startY;
    private final double endeX;
    private final double endeY;
    private final Color farbe;
    private final int schritt;
    private final Punkt start;

    Weg(Punkt start, Punkt ende, Color farbe){
        super(start.getXPos(), start.getYPos(), ende.getXPos(), ende.getYPos());
        startX = start.getXPos();
        startY = start.getYPos();
        endeX = ende.getXPos();
        endeY = ende.getYPos();
        this.farbe = farbe;
        this.horizontal = false;
        this.zuMitte = false;
        this.schritt = 0;
        this.start = start;
        super.setStroke(farbe);
        super.setStrokeWidth(2);
    }

    Weg(Bahnhof start, Bahnhof ende, Color farbe){
        super(start.getXPos(), start.getYPos(), ende.getXPos(), ende.getYPos());
        this.startX = start.getXPos();
        this.startY = start.getYPos();
        this.endeX = ende.getXPos();
        this.endeY = ende.getXPos();
        this.farbe = farbe;
        this.schritt = 0;
        super.setStroke(farbe);
        super.setStrokeWidth(2);

        this.start = start;
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
                ", zuMitte=" + zuMitte +
                ", Schritt=" + schritt +
                "]";
    }

    /**
     * Gibt alle Instanzvariablen als String aus
     *
     * @param datentyp Ob es eine Straße oder Schiene ist.
     * @return Daten vom Objekt als String.
     */
    public String toString(String datentyp) {
        return datentyp + "[" + "StartX=" + startX +
                ", StartY=" + startY +
                ", EndeX=" + endeX +
                ", EndeY=" + endeY +
                ", StrokeColor=" + farbe +
                ", Horizontal=" + horizontal +
                ", zuMitte=" + zuMitte +
                ", Schritt=" + schritt +
                "]";
    }

    public Punkt getStart() {
        return this.start;
    }
}
