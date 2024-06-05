package com.example.line;

public class Buslinie extends Strasze{
    Buslinie(Punkt start, Zwischenpunkt ende, int schritt) {
        super(start, ende, schritt);
    }

    Buslinie(Punkt start, Zwischenpunkt ende, boolean horizontal, int schritt) {
        super(start, ende, horizontal, schritt);
    }

    Buslinie(Zwischenpunkt start, Punkt ende, int schritt) {
        super(start, ende, schritt);
    }

    Buslinie(Zwischenpunkt start, Punkt ende, boolean horizontal, int schritt) {
        super(start, ende, horizontal, schritt);
    }
}
