package com.example.line;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public abstract class Punkt extends Circle {
    private final double xPos;
    private final double yPos;
    private final String name;
    private final Color farbeColor;

    Punkt(double xPos, double yPos, String name, Color farbe) {
        super(xPos, yPos, 5, farbe);
        this.xPos = xPos;
        this.yPos = yPos;
        this.name = name;
        this.farbeColor = farbe;
    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }

    @Override
    public String toString() {
        return "Punkt[" + "xPos=" + xPos +
                ", yPos=" + yPos +
                ", name=" + name +
                ", farbeColor=" + farbeColor +
                "]";
    }

    protected String toString(String datentyp) {
        return datentyp + "[" + "xPos=" + xPos +
                ", yPos=" + yPos +
                ", name=" + name +
                ", farbeColor=" + farbeColor +
                "]";
    }
}
