package com.example.line;

import javafx.scene.layout.Pane;

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
    private static Model instanz;
    private long seed;
    private Random rand;
    private final ArrayList<Punkt> punkte = new ArrayList<>();
    private final HashSet<Koordinaten> koordinaten= new HashSet<>();
    private final ArrayList<ArrayList<Weg>> wege= new ArrayList<>();

    /**
     * Konstruktor für das Model.
     */
    private Model() {
        //Default Seed 9223372036854775807L
        this.seed = 55555;
        this.rand = new Random(seed);
    }

    /**
     * Gibt die aktuelle Instanz vom Model Objekt
     * @return Das model Objekt
     */
    public static Model getInstanz(){
        if (instanz == null){
            instanz = new Model();
        }
        return instanz;
    }

    /**
     * Koordinaten welche in einem HashSet gespeichert werden, um doppelte Koordinaten zu verhindern.
     *
     * @param xPos X-Position des Punkts
     * @param yPos Y-Position des Punkts
     */
    record Koordinaten(int xPos, int yPos){}

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
            int xPos = rand.nextInt(1, 16) * 40;
            int yPos = rand.nextInt(1, 16) * 40;

            Koordinaten neueKoordinaten = new Koordinaten(xPos, yPos);

            if(!koordinaten.contains(neueKoordinaten)){
                koordinaten.add(neueKoordinaten);
                punkte.add(new Parkhaus(xPos, yPos));
            }
        }

        //Generation für Bushaltestellen
        while (koordinaten.size() < anzahlParkhaus+anzahlBushaltestellen){
            int xPos = rand.nextInt(1, 16) * 40;
            int yPos = rand.nextInt(1, 16) * 40;

            Koordinaten neueKoordinaten = new Koordinaten(xPos, yPos);

            if(!koordinaten.contains(neueKoordinaten)){
                koordinaten.add(neueKoordinaten);
                if (!punkte.isEmpty()) {
                    punkte.add(rand.nextInt(0, punkte.size()), new Bushaltestelle(xPos, yPos));
                } else {
                    punkte.add(new Bushaltestelle(xPos, yPos));
                }
            }
        }

        //Generation für Bahnhof
        while (koordinaten.size() < anzahlParkhaus+anzahlBushaltestellen+anzahlBahnhöfe){
            int xPos = rand.nextInt(1, 16) * 40;
            int yPos = rand.nextInt(1, 16) * 40;

            Koordinaten neueKoordinaten = new Koordinaten(xPos, yPos);

            if(!koordinaten.contains(neueKoordinaten)){
                koordinaten.add(neueKoordinaten);
                if (!punkte.isEmpty()) {
                    punkte.add(rand.nextInt(0, punkte.size()), new Bahnhof(xPos, yPos));
                } else {
                    punkte.add(new Bahnhof(xPos, yPos));
                }
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

        //Durchgehen vom Bahnhöfe
        int gefundenBahnhoefe = 0;
        for (Punkt punkt : punkte) {
            if (punkt instanceof Bahnhof) {
                gefundenBahnhoefe++;
            }
        }

        while (gefundenBahnhoefe > 0) {
            for (int i = 0; i < punkte.size(); i++) {
                for (int j = i; j < punkte.size() - 1; j++) {
                    if (punkte.get(i) instanceof Bahnhof aktuell) {
                        if (punkte.get(j) instanceof Bahnhof naechste) {
                            ArrayList<Weg> wege = new ArrayList<>();
                            wege.add(new Schiene(aktuell, naechste));
                            this.wege.add(wege);
                            gefundenBahnhoefe--;
                        }
                    }
                }
            }
        }

        //Zeichnen der Wege und Punkte
        for (ArrayList<Weg> wegeArr : wege) {
            for (Weg weg : wegeArr) {
                //System.out.println(weg.toString());
                root.getChildren().addAll(weg);
            }
        }

        root.getChildren().addAll(punkte);
    }

    /**
     * Methode die über dem Controller aufgerufen wird um benutzerdefinierte Punkte zu Erstellen
     * @param xPos X-Position vom Punkt 1-15
     * @param yPos Y-Position vom Punkt 1-15
     * @param datentyp Ob es Parkhaus 'p', Bushaltestelle 'b' oder Bahnhof 'z' ist
     * @throws PunktExistiertBereitsException Falls die Koordinaten schon belegt sind.
     */
    public void benutzerDefinierterPunkt(int xPos, int yPos, char datentyp) throws PunktExistiertBereitsException {
        xPos *= 40;
        yPos *= 40;
        Koordinaten neueKoordinaten = new Koordinaten(xPos, yPos);
        switch (datentyp) {
            case 'p' :
                if(!koordinaten.contains(neueKoordinaten)){
                    koordinaten.add(neueKoordinaten);
                    Parkhaus parkhaus = new Parkhaus(xPos, yPos);
                    punkte.add(parkhaus);
                    parkhaus.setCustom(true);
                } else {
                    throw new PunktExistiertBereitsException("Diese Koordinaten sind belegt!");
                }
                break;
            case 'b' :
                if(!koordinaten.contains(neueKoordinaten)){
                    koordinaten.add(neueKoordinaten);
                    Bushaltestelle bushaltestelle = new Bushaltestelle(xPos, yPos);
                    punkte.add(bushaltestelle);
                    bushaltestelle.setCustom(true);
                } else {
                    throw new PunktExistiertBereitsException("Diese Koordinaten sind belegt!");
                }
                break;
            case 'z' :
                if(!koordinaten.contains(neueKoordinaten)){
                    koordinaten.add(neueKoordinaten);
                    Bahnhof bahnhof = new Bahnhof(xPos, yPos);
                    punkte.add(bahnhof);
                    bahnhof.setCustom(true);
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
                punkte.add(new Bahnhof(xPos, yPos));
            }
        }
    }
}