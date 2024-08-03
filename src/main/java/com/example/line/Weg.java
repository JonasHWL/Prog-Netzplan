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

    Weg(Punkt start, Punkt ende, Zwischenpunkt mitte, Color farbe, boolean horizontal, boolean zuMitte, int schritt) {
        super(
            zuMitte ? //Für Line StartX
                start.getXPos() : //zuMitte true
                mitte.getXPos(), //zuMitte false

            horizontal ? //Fur Line StartY
                zuMitte ? //Horizontal true
                    mitte.getYPos() : //zuMitte true
                    ende.getYPos() //zzMitte false
                : zuMitte ? //Horizontal false
                    start.getYPos() : //zuMitte true
                    mitte.getYPos(), //zuMitte false

            horizontal ? //Fur Line EndX
                zuMitte ? //Horizontal true
                    mitte.getXPos() : //zuMitte true
                    ende.getXPos() //zuMitte false
                : zuMitte ? //Horizontal false
                    start.getXPos() : //zuMitte true
                    mitte.getXPos(), //zuMitte false

            zuMitte ? //Für Line EndY
                mitte.getYPos() : //zuMitte true
                ende.getYPos()//zuMitte false
        );

        this.start = start;
        this.startX = super.getStartX();
        this.startY = super.getStartY();
        this.endeX = super.getEndX();
        this.endeY = super.getEndY();
        this.farbe = farbe;
        this.horizontal = horizontal;
        this.zuMitte = zuMitte;
        this.schritt = schritt;
        super.setStroke(farbe);
        super.setStrokeWidth(2);
    }

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
