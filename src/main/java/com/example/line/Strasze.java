package com.example.line;

import javafx.scene.paint.Color;

public class Strasze extends Weg{
    Strasze(Punkt start, Zwischenpunkt ende) {
        super(start, ende, Color.BLACK);
    }

    Strasze(Punkt start, Zwischenpunkt ende, boolean horizontal) {
        super(start, ende, Color.BLACK, horizontal);
    }

    Strasze(Zwischenpunkt start, Punkt ende) {
        super(start, ende, Color.BLACK);
    }

    Strasze(Zwischenpunkt start, Punkt ende, boolean horizontal) {
        super(start, ende, Color.BLACK, horizontal);
    }
}
