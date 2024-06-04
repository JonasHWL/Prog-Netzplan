package com.example.line;

public class Buslinie extends Strasze{
    Buslinie(Punkt start, Zwischenpunkt ende) {
        super(start, ende);
    }

    Buslinie(Punkt start, Zwischenpunkt ende, boolean horizontal) {
        super(start, ende, horizontal);
    }

    Buslinie(Zwischenpunkt start, Punkt ende) {
        super(start, ende);
    }

    Buslinie(Zwischenpunkt start, Punkt ende, boolean horizontal) {
        super(start, ende, horizontal);
    }
}
