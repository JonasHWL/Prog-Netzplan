package com.example.line;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;

public class Model {
    private final long seed;
    private final Random rand;
    private final ArrayList<Punkt> punkte = new ArrayList<>();
    //private ArrayList<Fahrzeug> fahrzeuge = new ArrayList<Fahrzeug>();
    private final ArrayList<ArrayList<Weg>> wege= new ArrayList<>();

    /**
     * Konstruktor für das Model.
     * @param root Das Fenster Objekt
     * @param seed Seed für die Zufallsgeneration der Punkte
     * @param anzahlParkhaus Anzahl der zu erstellenden Punkte
     * @param anzahlBushaltestellen Anzahl der zu erstellenden Punkte
     * @param anzahlBahnhof Anzahl der zu erstellenden Punkte
     */
    Model(Pane root, long seed, int anzahlParkhaus, int anzahlBushaltestellen, int anzahlBahnhof){
        this.seed = seed;
        this.rand = new Random(seed);

        generierePunkte(anzahlParkhaus, anzahlBushaltestellen, anzahlBahnhof);
        erstelleStraszen(punkte);
        zeichne(root);
    }

    /**
     * Generiert zufällig punkte anhand des Seeds.
     *
     * @param anzahlParkhaus Anzahl der zu erstellenden Punkte
     * @param anzahlBushaltestellen Anzahl der zu erstellenden Punkte
     * @param anzahlBahnhof Anzahl der zu erstellenden Punkte
     */
    private void generierePunkte(int anzahlParkhaus, int anzahlBushaltestellen, int anzahlBahnhof){
        for (int i = 0; i < anzahlParkhaus; i++){
            int xPos = rand.nextInt(1, 15);
            int yPos = rand.nextInt(1,15);
            punkte.add(new Parkhaus(xPos*40, yPos*40, "Parkhaus"));
        }

        for (int i = 0; i < anzahlBushaltestellen; i++){
            int xPos = rand.nextInt(1, 20);
            int yPos = rand.nextInt(1,20);
            punkte.add(new Bushaltestelle(xPos*20, yPos*20, "Bus"));
        }

        for (int i = 0; i < anzahlBahnhof; i++){
            int xPos = rand.nextInt(1, 20);
            int yPos = rand.nextInt(1,20);
            punkte.add(new Bahnhof(xPos*20, yPos*20, "Parkhaus"));
        }
    }

    /**
     * Erstellt Wege passend zu allen Punkten die erstellt wurden.
     * Nach Aufruf dieser Methode können keine weiteren Punkte erstellt werden!
     *
     * @param punkte Arraylist mit Punkte objekte zwischen dem die Linien generiert werden.
     */
    private void erstelleStraszen(ArrayList<Punkt> punkte){
        for (int i = 0; i < punkte.size(); i++) {
            // Current point A
            Parkhaus aktuell = (Parkhaus) punkte.get(i);
            // Next point B, check if the aktuell element is the last to loop back to the first
            Parkhaus nächste;
            if (i + 1 < punkte.size()) {
                nächste = (Parkhaus) punkte.get(i + 1);
            } else {
                nächste = (Parkhaus) punkte.getFirst();
            }

            Zwischenpunkt mittel = new Zwischenpunkt((aktuell.getxPos() + nächste.getxPos()) / 2, (aktuell.getyPos() + nächste.getyPos()) / 2, "Hallo");

            ArrayList<Weg> wege = new ArrayList<>();

            wege.add(new Strasze(aktuell,mittel));
            wege.add(new Strasze(aktuell, mittel, true));
            wege.add(new Strasze(mittel, nächste));
            wege.add(new Strasze(mittel, nächste, true));

            this.wege.add(wege);
        }
    }

    /**
     * Geht durch die Arraylist durch, welche alle erstellten Objekte speichert.
     *
     * @param root Das Fenster Objekt
     */
    private void zeichne(Pane root){
        for (ArrayList<Weg> wegeArr : wege) {
            for (Weg weg : wegeArr) {
                root.getChildren().addAll(weg);
            }
        }

        for (Punkt punkt : punkte) {
            root.getChildren().addAll(punkt);
        }
    }
}
