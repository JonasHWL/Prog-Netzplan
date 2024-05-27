package com.example.line;

import javafx.scene.layout.Pane;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.IOException;

public class Model {
    private final long seed;
    private final Random rand;
    private final int anzahlParkhaus;
    private final int anzahlBushaltestellen;
    private final int anzahlBahnhof;
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
        this.anzahlParkhaus = anzahlParkhaus;
        this.anzahlBushaltestellen = anzahlBushaltestellen;
        this.anzahlBahnhof = anzahlBahnhof;

        //Methode soll vom Controller aufgerufen werden!
        benutzerDefinierterPunkt(4, 4, "Custom", 'p');
        benutzerDefinierterPunkt(14,14,"Custom2", 'p');
        benutzerDefinierterPunkt(6,10,"Custom3", 'p');

        generierePunkte(anzahlParkhaus, anzahlBushaltestellen, anzahlBahnhof);
        erstelleStraszen(punkte);
        zeichne(root);

        export();
    }

    /**
     * Generiert zufällig punkte anhand des Seeds.
     *
     * @param anzahlParkhaus Anzahl der zu erstellenden Punkte
     * @param anzahlBushaltestellen Anzahl der zu erstellenden Punkte
     * @param anzahlBahnhof Anzahl der zu erstellenden Punkte
     */
    private void generierePunkte(int anzahlParkhaus, int anzahlBushaltestellen, int anzahlBahnhof){
        while ( punkte.size() < anzahlParkhaus){
            int xPos = rand.nextInt(1, 15);
            int yPos = rand.nextInt(1,15);
            punkte.add(new Parkhaus(xPos*40, yPos*40, "Parkhaus"));
        }

        for (int i = 0; i < anzahlBushaltestellen; i++){
            int randPostion = rand.nextInt(0,  punkte.size());
            punkte.set(randPostion, new Bushaltestelle(punkte.get(randPostion).getXPos(), punkte.get(randPostion).getYPos(), "Bus"));
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
            if(punkte.get(i) instanceof Parkhaus aktuell){
                // Next point B, check if the aktuell element is the last to loop back to the first
                Parkhaus naechste;
                if (i + 1 < punkte.size()) {
                    naechste = (Parkhaus) punkte.get(i + 1);
                } else {
                    naechste = (Parkhaus) punkte.getFirst();
                }

                Zwischenpunkt mittel = new Zwischenpunkt((aktuell.getXPos() + naechste.getXPos()) / 2, (aktuell.getYPos() + naechste.getYPos()) / 2, "Hallo");

                ArrayList<Weg> wege = new ArrayList<>();

                for (int j = 0; j < 4; j++) {
                    switch (j) {
                        case 0:
                            wege.add(new Strasze(aktuell,mittel, j));
                            break;
                        case 1:
                            wege.add(new Strasze(aktuell, mittel, true, j));
                            break;
                        case 2:
                            wege.add(new Strasze(mittel, naechste, j));
                            break;
                        case 3:
                            wege.add(new Strasze(mittel, naechste, true, j));
                            break;
                    }
                }
                this.wege.add(wege);
            }
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
                System.out.println(weg.toString());
                root.getChildren().addAll(weg);
            }
        }

        for (Punkt punkt : punkte) {
            System.out.println(punkt.toString());
            root.getChildren().addAll(punkt);
        }
    }

    /**
     * Methode die über dem Controller aufgerufen wird um benutzerdefinierte Punkte zu Erstellen
     * @param xPos X-Position vom Punkt 1-15
     * @param yPos Y-Position vom Punkt 1-15
     * @param name Name vom Punkt
     * @param datentyp Ob es Parkhaus 'p', Bushaltestelle 'b' oder Bahnhof 'z' ist
     */
    public void benutzerDefinierterPunkt(int xPos, int yPos, String name, char datentyp){
        switch (datentyp) {
            case 'p' -> punkte.add(new Parkhaus(xPos*40, yPos*40, name));
            case 'b' -> punkte.add(new Bushaltestelle(xPos*40, yPos*40, name));
            case 'z' -> punkte.add(new Bahnhof(xPos*40, yPos*40, name));
        }
    }

    private void export(){
        try{
            File export = new File("karte.txt");
            if(export.createNewFile()){
                System.out.println("Datei erstellt! " + export.getName());
            } else{
                System.out.println("Datei existiert bereits!");
            }
        } catch (IOException e){
            System.out.println("An error occurred.");
            throw new RuntimeException(e);
        }

        try {
            FileWriter schreiber = new FileWriter("karte.txt");
            schreiber.write("Seed=" + seed +"\n");
            schreiber.write("Parkhaus=" + anzahlParkhaus +"\n");
            schreiber.write("Bushaltestellen=" + anzahlBushaltestellen +"\n");
            schreiber.write("Bahnhof=" + anzahlBahnhof +"\n");
            for (Punkt punkt : punkte) {
                schreiber.write(punkt.toString() +"\n");
            }
            for (ArrayList<Weg> wegeArr : wege) {
                for (Weg weg : wegeArr) {
                    schreiber.write(weg.toString() +"\n");
                }
            }
            schreiber.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
