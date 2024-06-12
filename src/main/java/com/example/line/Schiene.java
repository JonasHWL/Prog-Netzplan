package com.example.line;

import javafx.scene.paint.Color;

public class Schiene extends Weg{
    Schiene(Bahnhof start, Bahnhof ende) {
        super(start, ende, Color.LIGHTGREEN);
    }

    @Override
    public String toString(){
        return super.toString("Schiene");
    }
}
