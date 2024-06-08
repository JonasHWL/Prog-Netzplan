package com.example.line;

import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Erstellt die verschiedene Punkte und dazugehörigen Wege die dargestellt werden sollen.
 *
 * @author Amel Aho
 * @version 07.06.2024
 */
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
     * @param seed Seed für die Zufallsgeneration der Punkte.
     * @param anzahlParkhaus Anzahl der zu erstellenden Punkte
     * @param anzahlBushaltestellen Anzahl der zu erstellenden Punkte
     * @param anzahlBahnhof Anzahl der zu erstellenden Punkte
     */
    Model(Pane root, long seed, int anzahlParkhaus, int anzahlBushaltestellen, int anzahlBahnhof){
        this.seed = seed;
        this.rand = new Random(seed);
        //TODO Die anzahl Parameter vom Konstruktor zu einer Methode verschieben.
        this.anzahlParkhaus = anzahlParkhaus;
        this.anzahlBushaltestellen = anzahlBushaltestellen;
        this.anzahlBahnhof = anzahlBahnhof;

        //Methode soll vom Controller aufgerufen werden!
        benutzerDefinierterPunkt(4, 4, "Custom", 'p');
        benutzerDefinierterPunkt(14,14,"Custom2", 'p');
        benutzerDefinierterPunkt(6,10,"Custom3", 'p');

        //TODO folgende 3 Methoden in einer Methode einbauen die von Controller aufgerufen wird, für die ganze generation.
        generierePunkte(anzahlParkhaus, anzahlBushaltestellen, anzahlBahnhof);
        erstelleStraßen();
        zeichne(root);

        //Methode soll vom Controller aufgerufen werden!
        export();
        importkarte();
    }

    /**
     * Generiert zufällig punkte anhand des Seeds.
     * Es werden zuerst die Benutzer definierten Punkte gezählt und dann die restlichen nachgefüllt.
     * Bahnhof und Bushaltestellen sind, extra aufgaben.
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

        //Generation für Bushaltestellen muss angepasst werden (Extra aufgabe)
        for (int i = 0; i < anzahlBushaltestellen; i++){
            int randPostion = rand.nextInt(0,  punkte.size());
            punkte.set(randPostion, new Bushaltestelle(punkte.get(randPostion).getXPos(), punkte.get(randPostion).getYPos(), "Bus"));
        }

        //Generation für Bahnhof muss angepasst werden (Extra aufgabe)
        for (int i = 0; i < anzahlBahnhof; i++){
            int xPos = rand.nextInt(1, 20);
            int yPos = rand.nextInt(1,20);
            punkte.add(new Bahnhof(xPos * 20, yPos * 20, "Bahnhof"));
        }
    }

    /**
     * Erstellt Wege passend zu allen Punkten die erstellt wurden.
     * Nach Aufruf dieser Methode sollten keine weiteren Punkte erstellt werden!
     */
    private void erstelleStraßen() {
        //Durchgehen des punkte Array.
        for (int i = 0; i < punkte.size(); i++) {
            //Kontrolliert den Datentyp von punkt i, ob es ein Parkhaus ist und speichert es ab in der Variable aktuell.
            if(punkte.get(i) instanceof Parkhaus aktuell){
                //Der nächste Punkt wird genommen und in einer Variable gespeichert.
                //Wenn Punkt aktuell der letzte un der liste ist und es kein i+1 gibt, dann wird das erste element ausgewählt.
                Parkhaus nächste;
                if (i + 1 < punkte.size()) {
                    nächste = (Parkhaus) punkte.get(i + 1);
                } else {
                    nächste = (Parkhaus) punkte.getFirst();
                }

                //Zwischenpunkt als hilfe für die liniengeneration. Wird nicht abgespeichert.
                Zwischenpunkt mittel = new Zwischenpunkt((aktuell.getXPos() + nächste.getXPos()) / 2, (aktuell.getYPos() + nächste.getYPos()) / 2, "Hallo");

                //Initialisierung eines Weg Arraylist welches die 4 Wege speichert.
                ArrayList<Weg> wege = new ArrayList<>();

                //Erstellung der 4 Wege von punkt aktuell zu Punkt nächste, mithilfe überladene Konstruktoren.
                for (int j = 0; j < 4; j++) {
                    wege.add(new Straße(aktuell, nächste, mittel, j % 2 != 0, j / 2 == 0, j));
                }
                //Hinzufügen der 4 Wege in einem Array zur generellen Arraylist.
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
                //System.out.println(weg.toString());
                root.getChildren().addAll(weg);
            }
        }

        for (Punkt punkt : punkte) {
            //System.out.println(punkt.toString());
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

    /**
     * Erstellt eine .txt Datei welche Informationen zur Generation der Karte mitgibt.
     * Der Seed.
     * Die Anzahl von Punkten.
     * Es wird von jedem Punkt und Weg die toString methode aufgerufen, für die Instanzvariablen.
     *
     * @see Punkt
     * @see Weg
     */
    private void export(){
        try{
            File export = new File("karte.txt");
            if(export.createNewFile()){
                System.out.println("Datei erstellt! " + export.getName());
            } else{
                System.out.println("Datei existiert bereits!");
            }
        } catch (IOException e){
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
            System.out.println("Erfolgreich die Daten exportiert");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * sucht nach einer karte.txt datei und liest sie ab.
     * Bisher werden die einzelnen Werte nur in der Konsole ausgegeben.
     */
    private void importkarte() {
        try {
            File karte = new File("karte.txt");
            Scanner leser = new Scanner(karte);
            while (leser.hasNextLine()) {
                String zeile = leser.nextLine();
                String search = "Seed=";
                if (zeile.contains(search)) {
                    zeile = zeile.substring(search.length());
                    System.out.println("Importierter Seed=" + zeile);
                }
                search = "Parkhaus=";
                if (zeile.contains(search)) {
                    zeile = zeile.substring(search.length());
                    System.out.println("Importierter Parkhaus=" + zeile);
                }
                search = "Bushaltestellen=";
                if (zeile.contains(search)) {
                    zeile = zeile.substring(search.length());
                    System.out.println("Importierter Bushaltestellen=" + zeile);
                }
                search = "Bahnhof=";
                if (zeile.contains(search)) {
                    zeile = zeile.substring(search.length());
                    System.out.println("Importierter Bahnhof=" + zeile);
                }
                search = "Parkhaus[";
                if (zeile.contains(search)) {
                    double xPos = Double.parseDouble(zeile.split("xPos=")[1].split(",")[0]);
                    double yPos = Double.parseDouble(zeile.split("yPos=")[1].split(",")[0]);
                    String name = zeile.split("farbeColor=")[1].split(",")[0];
                    System.out.println(xPos + " " + yPos + " " + name);
                }
            }
            leser.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
