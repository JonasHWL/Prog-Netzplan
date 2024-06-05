package com.example.line;

import javafx.scene.layout.Pane;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Model {
    private final long seed;
    private final Random rand;
    private final ArrayList<Punkt> punkte = new ArrayList<>();
    //private ArrayList<Fahrzeug> fahrzeuge = new ArrayList<Fahrzeug>();
    private final ArrayList<ArrayList<Weg>> wege= new ArrayList<>();
    private final ArrayList<Punkt> benutzerPunkte = new ArrayList<>();

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
        zeichnePunkte(root);

        //Benutzer Punkte Test
        benutzerPunkte.add(erstelleBenutzerdefinierterPunktObjekt(4,4,"Custom", 'p'));
        benutzerPunkte.add(erstelleBenutzerdefinierterPunktObjekt(14,14,"Custom2", 'p'));
        benutzerPunkte.add(erstelleBenutzerdefinierterPunktObjekt(6,10,"Custom3", 'p'));
        zeichneBenutzerPunkte(root);
        punkte.addAll(benutzerPunkte);


        erstelleStraszen(punkte);
        zeichneWege(root);
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
            int randPostion = rand.nextInt(0,  punkte.size());
            punkte.set(randPostion, new Bushaltestelle(punkte.get(randPostion).getxPos(), punkte.get(randPostion).getyPos(), "Bus"));
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

            for (int j = 0; j < 4; j++) {
                switch (j) {
                    case 0:
                        wege.add(new Strasze(aktuell,mittel));
                        break;
                    case 1:
                        wege.add(new Strasze(aktuell, mittel, true));
                        break;
                    case 2:
                        wege.add(new Strasze(mittel, nächste));
                        break;
                    case 3:
                        wege.add(new Strasze(mittel, nächste, true));
                        break;
                }
            }

            this.wege.add(wege);
        }
    }

    /**
     * Geht durch die Arraylist durch, welche alle erstellten Objekte speichert.
     *
     * @param root Das Fenster Objekt
     */
    private void zeichneWege(Pane root){
        for (ArrayList<Weg> wegeArr : wege) {
            for (Weg weg : wegeArr) {
                System.out.println(weg.toString());
                root.getChildren().addAll(weg);
            }
        }
    }

    /**
     * Geht durch punkte durch, um alle Punkte darzustellen.
     *
     * @param root Das Fenster Objekt
     */
    private void zeichnePunkte(Pane root){
        for (Punkt punkt : punkte) {
            System.out.println(punkt.toString());
            root.getChildren().addAll(punkt);
        }
    }

    /**
     * Erstelle ein benutzerdefinierter Punkt.
     *
     * @param xPos X-Achsen Position des Punkts 1-15.
     * @param yPos Y-Achsen Position des Punkts 1-15.
     * @param name Name zur identifikation des Punkts.
     * @param datentyp Welcher Datentyp zurückgegeben werden soll. 'p' Für Parkhaus, 'b' Für Bushaltestelle und 'z' Für Bahnhof.
     * @return Parkhaus, Bushaltestelle oder Bahnhof
     */
    private Punkt erstelleBenutzerdefinierterPunktObjekt(int xPos, int yPos, String name, char datentyp){
        return switch (datentyp) {
            case 'p' -> new Parkhaus(xPos*40, yPos*40, name);
            case 'b' -> new Bushaltestelle(xPos*40, yPos*40, name);
            case 'z' -> new Bahnhof(xPos*40, yPos*40, name);
            default -> null; //Weil sonst IntelliJ meckert
        };
    }

    private void zeichneBenutzerPunkte(Pane root){
        for (Punkt punkt : benutzerPunkte) {
            System.out.println(punkt.toString());
            root.getChildren().addAll(punkt);
        }
    }
}
