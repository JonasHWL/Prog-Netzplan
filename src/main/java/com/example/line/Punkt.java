package com.example.line;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Punkte die auf der Benutzeroberfläche erscheinen.
 *
 * @author Amel Aho
 * @version 07.06.2024
 */
public abstract class Punkt extends Circle {
    private final double xPos;
    private final double yPos;
    private boolean custom;

    /**
     * Konstruktor für die Punkte.
     *
     * @param xPos  X-Position des Punkts
     * @param yPos  Y-Position des Punkts
     * @param farbe Farbe des Punkts
     */
    Punkt(double xPos, double yPos, Color farbe) {
        super(xPos, yPos, 5, farbe);
        this.xPos = xPos;
        this.yPos = yPos;
        custom = false;
    }

    /**
     * Getter für die X-Position
     * @return X-Position vom Punkt
     */
    public double getXPos() {
        return xPos;
    }

    /**
     * Getter für die Y-Position
     * @return Y-Position vom Punkt
     */
    public double getYPos() {
        return yPos;
    }

    /**
     * Gibt alle Instanzvariablen als String aus
     * @param datentyp Datentyp, des Objects
     * @return Instanzvariablen als String
     */
    protected String toString(String datentyp) {
        return datentyp + "[" + "xPos=" + xPos +
                ", yPos=" + yPos + "]";
    }

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }
}
