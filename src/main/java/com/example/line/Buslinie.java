package com.example.line;

public class Buslinie extends Straße {

    Buslinie(Punkt start, Punkt ende, Zwischenpunkt mitte, boolean horizontal, boolean zuMitte, int schritt) {
        super(start, ende, mitte, horizontal, zuMitte, schritt);
    }

    @Override
    public String toString(){
        return super.toString("Buslinie");
    }
}
