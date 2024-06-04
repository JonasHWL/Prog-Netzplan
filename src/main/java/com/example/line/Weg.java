package com.example.line;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public abstract class Weg extends Line {
    boolean horizontal = false;

    Weg(Punkt start, Zwischenpunkt ende, Color farbe){
        super(start.getxPos(), start.getyPos(), start.getxPos(), ende.getyPos());
        super.setStroke(farbe);
        super.setStrokeWidth(2);
    }

    Weg(Punkt start, Zwischenpunkt ende, Color farbe, boolean horizontal){
        super(start.getxPos(), ende.getyPos(), ende.getxPos(), ende.getyPos());
        super.setStroke(farbe);
        super.setStrokeWidth(2);
        this.horizontal = horizontal;
    }

    Weg(Zwischenpunkt start, Punkt ende, Color farbe){
        super(start.getxPos(), start.getyPos(), start.getxPos(), ende.getyPos());
        super.setStroke(farbe);
        super.setStrokeWidth(2);
    }

    Weg(Zwischenpunkt start, Punkt ende, Color farbe, boolean horizontal){
        super(start.getxPos(), ende.getyPos(), ende.getxPos(), ende.getyPos());
        super.setStroke(farbe);
        super.setStrokeWidth(2);
        this.horizontal = horizontal;
    }

    Weg(Bahnhof start, Bahnhof ende, Color farbe){
        super(start.getxPos(), start.getyPos(), start.getxPos(), ende.getyPos());
    }
}
