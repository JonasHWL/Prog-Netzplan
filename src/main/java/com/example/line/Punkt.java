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

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }
}
