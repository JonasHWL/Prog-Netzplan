package com.example.line;

public class PunktExistiertBereitsException extends Exception{
    PunktExistiertBereitsException(String fehler){
        super(fehler);
    }
}
