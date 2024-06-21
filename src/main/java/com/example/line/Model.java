package com.example.line;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;
import java.io.File;
import java.io.IOException;

/**
 * Erstellt die verschiedene Punkte und dazugehörigen Wege die dargestellt werden sollen.
 *
 * @author Amel Aho
 * @version 11.06.2024
 */
public class Model {
    private static Model instance;
    private long seed;
    private Random rand;
    private final ArrayList<Punkt> punkte = new ArrayList<>();
    private final ArrayList<Bahnhof> bahnhöfe = new ArrayList<>();
    private final HashSet<String> koordinaten= new HashSet<>();
    //private ArrayList<Fahrzeug> fahrzeuge = new ArrayList<Fahrzeug>();
    private final ArrayList<ArrayList<Weg>> wege= new ArrayList<>();

    /**
     * Konstruktor für das Model.
     */
    private Model() {
        //Default Seed
        this.seed = 9223372036854775807L;
        this.rand = new Random(seed);

        /*
        4 4 cus1
        14 14 cus2
        6 10 cus3
        */
    }

    /**
     * Gibt die aktuelle Instanz vom Model Objekt
     * @return Das model Objekt
     */
    public static Model getInstance(){
        if (instance == null){
            instance = new Model();
        }
        return instance;
    }

    /**
     * Erstellt die fehlenden Punkte und generiert die Wege.
     * Zeichnet die Wege und Punkte.
     *
     * @param root Das Fenster selbst anchorPane
     * @param anzahlParkhaus Anzahl der Parkhäuser
     * @param anzahlBushaltestellen Anzahl der Bushaltestellen
     * @param anzahlBahnhof Anzahl der Bahnhöfe
     */
    public void generiere(Pane root ,int anzahlParkhaus, int anzahlBushaltestellen, int anzahlBahnhof) {
        generierePunkte(anzahlParkhaus, anzahlBushaltestellen, anzahlBahnhof);
        erstelleStraßen(root);
    }
    /**
     * Setter für seed
     *
     * @param seed Neuer Seed.
     */
    public void setSeed(long seed) {
        this.seed = seed;
        this.rand = new Random(seed);
    }

    /**
     * Generiert zufällig punkte anhand des Seeds.
     * Es werden zuerst die Benutzer definierten Punkte gezählt und dann die restlichen nachgefüllt.
     * Bahnhof und Bushaltestellen sind, extra aufgaben.
     *
     * @param anzahlParkhaus Anzahl der zu erstellenden Punkte
     * @param anzahlBushaltestellen Anzahl der zu erstellenden Punkte
     * @param anzahlBahnhöfe Anzahl der zu erstellenden Punkte
     */
    private void generierePunkte(int anzahlParkhaus, int anzahlBushaltestellen, int anzahlBahnhöfe) {

        while (koordinaten.size() < anzahlParkhaus) {
            int xPos = rand.nextInt(1, 15);
            int yPos = rand.nextInt(1,15);
            if(!koordinaten.contains(String.valueOf(xPos + yPos))){
                koordinaten.add(String.valueOf(xPos + yPos));
                punkte.add(new Parkhaus(xPos*40, yPos*40));
            }
        }

        //Generation für Bushaltestellen
        while (koordinaten.size() < anzahlParkhaus+anzahlBushaltestellen){
            int xPos = rand.nextInt(1, 15);
            int yPos = rand.nextInt(1,15);
            if(!koordinaten.contains(String.valueOf(yPos + xPos))){
                koordinaten.add(String.valueOf(yPos + xPos));
                punkte.add(rand.nextInt(0, punkte.size()), new Bushaltestelle(xPos * 40, yPos * 40));
            }
        }

        //Generation für Bahnhof
        while (koordinaten.size() < anzahlParkhaus+anzahlBushaltestellen+anzahlBahnhöfe){
            int xPos = rand.nextInt(1, 15);
            int yPos = rand.nextInt(1,15);
            if(!koordinaten.contains(String.valueOf(yPos + xPos))){
                koordinaten.add(String.valueOf(yPos + xPos));
                bahnhöfe.add(new Bahnhof(xPos*40, yPos*40));
            }
        }
    }

    /**
     * Erstellt Wege passend zu allen Punkten die erstellt wurden.
     * Nach Aufruf dieser Methode sollten keine weiteren Punkte erstellt werden!
     *
     * @param root Das Fenster selbst anchorPane
     */
    private void erstelleStraßen(Pane root) {
        //Durchgehen des punkte Array.
        for (int i = 0; i < punkte.size(); i++) {
            //Nimmt das aktuelle Element element
            Punkt aktuell = punkte.get(i);
            //Nimmt das nächste Element, falls es schon das letzte element ist nimmt es das erste Element als Ziel.
            Punkt nächste;
            if (i + 1 < punkte.size() ) {
                nächste = punkte.get(i + 1);
            } else {
                nächste = punkte.getFirst();
            }

            //Zwischenpunkt als hilfe für die liniengeneration. Wird nicht abgespeichert.
            Zwischenpunkt mittel = new Zwischenpunkt((aktuell.getXPos() + nächste.getXPos()) / 2, (aktuell.getYPos() + nächste.getYPos()) / 2);

            //Initialisierung eines Weg Arraylist welches die 4 Wege speichert.
            ArrayList<Weg> wege = new ArrayList<>();

            //Erstellung der 4 Wege von punkt aktuell zu Punkt nächste, mithilfe überladene Konstruktoren.
            for (int j = 0; j < 4; j++) {
                wege.add(new Straße(aktuell, nächste, mittel, j % 2 != 0, j / 2 == 0, j));
            }
            //Hinzufügen der 4 Wege in einem Array zur generellen Arraylist.
            this.wege.add(wege);
        }

        //Durchgehen vom Bahnhöfe array
        for (int i = 0; i < bahnhöfe.size(); i++) {
            Bahnhof aktuell = bahnhöfe.get(i);
            Bahnhof nächste;
            if (i + 1 < bahnhöfe.size() ) {
                nächste = bahnhöfe.get(i + 1);
            } else {
                nächste = bahnhöfe.getFirst();
            }
            ArrayList<Weg> wege = new ArrayList<>();
            wege.add(new Schiene(aktuell, nächste));
            this.wege.add(wege);
        }

        //Zeichnen der Wege und Punkte
        for (ArrayList<Weg> wegeArr : wege) {
            for (Weg weg : wegeArr) {
                //System.out.println(weg.toString());
                root.getChildren().addAll(weg);
            }
        }

        root.getChildren().addAll(punkte);
        root.getChildren().addAll(bahnhöfe);
    }

    /**
     * Methode die über dem Controller aufgerufen wird um benutzerdefinierte Punkte zu Erstellen
     * @param xPos X-Position vom Punkt 1-15
     * @param yPos Y-Position vom Punkt 1-15
     * @param datentyp Ob es Parkhaus 'p', Bushaltestelle 'b' oder Bahnhof 'z' ist
     * @throws PunktExistiertBereitsException Falls die Koordinaten schon belegt sind.
     */
    public void benutzerDefinierterPunkt(int xPos, int yPos, char datentyp) throws PunktExistiertBereitsException {
        switch (datentyp) {
            case 'p' :
                if(!koordinaten.contains(String.valueOf(xPos + yPos))){
                    koordinaten.add(String.valueOf(xPos + yPos));
                    punkte.add(new Parkhaus(xPos*40, yPos*40));
                } else {
                    throw new PunktExistiertBereitsException("Diese Koordinaten sind belegt!");
                }
                break;
            case 'b' :
                if(!koordinaten.contains(String.valueOf(xPos + yPos))){
                    koordinaten.add(String.valueOf(xPos + yPos));
                    punkte.add(new Bushaltestelle(xPos*40, yPos*40));
                } else {
                    throw new PunktExistiertBereitsException("Diese Koordinaten sind belegt!");
                }
                break;
            case 'z' :
                if(!koordinaten.contains(String.valueOf(xPos + yPos))){
                    koordinaten.add(String.valueOf(xPos + yPos));
                    bahnhöfe.add(new Bahnhof(xPos*40, yPos*40));
                } else {
                    throw new PunktExistiertBereitsException("Diese Koordinaten sind belegt!");
                }
                break;
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
    public void export() {
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
            for (Punkt punkt : punkte) {
                schreiber.write(punkt.toString() +"\n");
            }
            for (Bahnhof bahnhof : bahnhöfe){
                schreiber.write(bahnhof.toString() +"\n");
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
     * Muss so behandelt werden wie der Generieren-Knopf.
     *
     * @param root Das Fenster selbst anchorPane
     */
    public void importKarte(Pane root) {
        try {
            File karte = new File("karte.txt");
            Scanner leser = new Scanner(karte);
            while (leser.hasNextLine()) {
                String zeile = leser.nextLine();
                String search = "Seed=";
                if (zeile.contains(search)) {
                    zeile = zeile.substring(search.length());
                    seed = Long.parseLong(zeile);
                }
                searchImport(zeile, "Parkhaus");
                searchImport(zeile, "Bushaltestelle");
                searchImport(zeile, "Bahnhof");
            }
            leser.close();
            erstelleStraßen(root);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Wiederholender Codeblock in eine Methode zusammengefasst.
     * @param zeile Die zeile wo der Leser ist.
     * @param datentyp Nach welchen Datentyp gesucht wird.
     */
    private void searchImport(String zeile, String datentyp) {
        if (zeile.contains(datentyp + "[")) {
            double xPos = Double.parseDouble(zeile.split("xPos=")[1].split(",")[0]);
            double yPos = Double.parseDouble(zeile.split("yPos=")[1].split("]")[0]);
            if (Objects.equals(datentyp, "Parkhaus")) {
                punkte.add(new Parkhaus(xPos, yPos));
            } else if (Objects.equals(datentyp, "Bushaltestelle")) {
                punkte.add(new Bushaltestelle(xPos, yPos));
            } else if (Objects.equals(datentyp, "Bahnhof")) {
                bahnhöfe.add(new Bahnhof(xPos, yPos));
            }
        }
    }
}