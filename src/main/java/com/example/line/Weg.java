package com.example.line;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public abstract class Weg extends Line {
    private boolean horizontal = false;
    private final double startX;
    private final double startY;
    private final double endeX;
    private final double endeY;
    private final Color farbe;
    private final int schritt;

    Weg(Punkt start, Zwischenpunkt ende, Color farbe, int schritt){
        super(start.getXPos(), start.getYPos(), start.getXPos(), ende.getYPos());
        super.setStroke(farbe);
        super.setStrokeWidth(2);
        this.startX = start.getXPos();
        this.startY = start.getYPos();
        this.endeX = ende.getXPos();
        this.endeY = ende.getXPos();
        this.farbe = farbe;
        this.schritt = schritt;
    }

    Weg(Punkt start, Zwischenpunkt ende, Color farbe, boolean horizontal, int schritt){
        super(start.getXPos(), ende.getYPos(), ende.getXPos(), ende.getYPos());
        super.setStroke(farbe);
        super.setStrokeWidth(2);
        this.horizontal = horizontal;
        this.startX = start.getXPos();
        this.startY = start.getYPos();
        this.endeX = ende.getXPos();
        this.endeY = ende.getXPos();
        this.farbe = farbe;
        this.schritt = schritt;
    }

    Weg(Zwischenpunkt start, Punkt ende, Color farbe, int schritt){
        super(start.getXPos(), start.getYPos(), start.getXPos(), ende.getYPos());
        super.setStroke(farbe);
        super.setStrokeWidth(2);
        this.startX = start.getXPos();
        this.startY = start.getYPos();
        this.endeX = ende.getXPos();
        this.endeY = ende.getXPos();
        this.farbe = farbe;
        this.schritt = schritt;
    }

    Weg(Zwischenpunkt start, Punkt ende, Color farbe, boolean horizontal, int schritt){
        super(start.getXPos(), ende.getYPos(), ende.getXPos(), ende.getYPos());
        super.setStroke(farbe);
        super.setStrokeWidth(2);
        this.horizontal = horizontal;
        this.startX = start.getXPos();
        this.startY = start.getYPos();
        this.endeX = ende.getXPos();
        this.endeY = ende.getXPos();
        this.farbe = farbe;
        this.schritt = schritt;
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
     *
     * @param datentyp Ob es eine Stasze, Buslinie oder Schiene ist.
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
