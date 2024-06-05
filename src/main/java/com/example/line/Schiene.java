package com.example.line;

import javafx.scene.paint.Color;

public class Schiene extends Weg{
    Schiene(Bahnhof start, Bahnhof ende, int schritt) {
        super(start, ende, Color.LIGHTGREEN, schritt);
    }
}
