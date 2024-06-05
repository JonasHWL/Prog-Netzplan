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
        super(start.getxPos(), start.getyPos(), start.getxPos(), ende.getyPos());
        super.setStroke(farbe);
        super.setStrokeWidth(2);
        this.startX = start.getxPos();
        this.startY = start.getyPos();
        this.endeX = ende.getxPos();
        this.endeY = ende.getxPos();
        this.farbe = farbe;
        this.schritt = schritt;
    }

    Weg(Punkt start, Zwischenpunkt ende, Color farbe, boolean horizontal, int schritt){
        super(start.getxPos(), ende.getyPos(), ende.getxPos(), ende.getyPos());
        super.setStroke(farbe);
        super.setStrokeWidth(2);
        this.horizontal = horizontal;
        this.startX = start.getxPos();
        this.startY = start.getyPos();
        this.endeX = ende.getxPos();
        this.endeY = ende.getxPos();
        this.farbe = farbe;
        this.schritt = schritt;
    }

    Weg(Zwischenpunkt start, Punkt ende, Color farbe, int schritt){
        super(start.getxPos(), start.getyPos(), start.getxPos(), ende.getyPos());
        super.setStroke(farbe);
        super.setStrokeWidth(2);
        this.startX = start.getxPos();
        this.startY = start.getyPos();
        this.endeX = ende.getxPos();
        this.endeY = ende.getxPos();
        this.farbe = farbe;
        this.schritt = schritt;
    }

    Weg(Zwischenpunkt start, Punkt ende, Color farbe, boolean horizontal, int schritt){
        super(start.getxPos(), ende.getyPos(), ende.getxPos(), ende.getyPos());
        super.setStroke(farbe);
        super.setStrokeWidth(2);
        this.horizontal = horizontal;
        this.startX = start.getxPos();
        this.startY = start.getyPos();
        this.endeX = ende.getxPos();
        this.endeY = ende.getxPos();
        this.farbe = farbe;
        this.schritt = schritt;
    }

    Weg(Bahnhof start, Bahnhof ende, Color farbe, int schritt){
        super(start.getxPos(), start.getyPos(), start.getxPos(), ende.getyPos());
        this.startX = start.getxPos();
        this.startY = start.getyPos();
        this.endeX = ende.getxPos();
        this.endeY = ende.getxPos();
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
}
