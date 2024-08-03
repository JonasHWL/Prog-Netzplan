
package com.example.line;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * Punkte die auf der Benutzeroberfläche erscheinen.
 *
 * @author Amel Aho
 * @version 20.06.2024
 */
public abstract class Punkt extends Circle {
    private final String name;
    private final double xPos;
    private final double yPos;
    private boolean custom;
    private final ArrayList<Punkt> benachbartePunkte;
    private ArrayList<Model.Node> breitensucheErgebnis;

    /**
     * Konstruktor für die Punkte.
     *
     * @param xPos  X-Position des Punkts
     * @param yPos  Y-Position des Punkts
     * @param farbe Farbe des Punkts
     */
    Punkt(double xPos, double yPos, Color farbe, String name) {
        super(xPos, yPos, 5, farbe);
        this.xPos = xPos;
        this.yPos = yPos;
        this.name = name;
        custom = false;
        benachbartePunkte = new ArrayList<>();
        breitensucheErgebnis = new ArrayList<>();
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
        return datentyp + "[" + "Name=" + name + ", xPos=" + xPos +
                ", yPos=" + yPos + "]";
    }

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

    public String getName() {
        return name;
    }

    public void addBenachbartePunkte(Punkt punkt) {
        if(!benachbartePunkte.contains(punkt)){
            this.benachbartePunkte.add(punkt);
        }
    }

    public ArrayList<Punkt> getBenachbartePunkte() {
        return benachbartePunkte;
    }

    public ArrayList<Model.Node> getBreitensucheErgebnis() {
        return breitensucheErgebnis;
    }

    public void setBreitensucheErgebnis(ArrayList<Model.Node> breitensucheErgebnis) {
        this.breitensucheErgebnis = breitensucheErgebnis;
    }
}
