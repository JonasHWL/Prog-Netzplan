package com.example.line;

import javafx.scene.paint.Color;

public class Strasze extends Weg{
    Strasze(Punkt start, Zwischenpunkt ende, int schritt) {
        super(start, ende, Color.BLACK, schritt);
    }

    Strasze(Punkt start, Zwischenpunkt ende, boolean horizontal, int schritt) {
        super(start, ende, Color.BLACK, horizontal, schritt);
    }

    Strasze(Zwischenpunkt start, Punkt ende, int schritt) {
        super(start, ende, Color.BLACK, schritt);
    }

    Strasze(Zwischenpunkt start, Punkt ende, boolean horizontal, int schritt) {
        super(start, ende, Color.BLACK, horizontal, schritt);
    }
}
