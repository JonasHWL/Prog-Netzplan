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
 * @version 20.06.2024
 */
public class Model {
    private static Model instanz;
    private long seed;
    private Random rand;
    private final ArrayList<Punkt> punkte = new ArrayList<>();
    private final HashSet<Koordinaten> koordinaten= new HashSet<>();
    private final ArrayList<ArrayList<Weg>> wege= new ArrayList<>();
    private final int MINIMUM_POSITION_X;
    private final int MAXIMUM_POSITION_X;
    private final int MINIMUM_POSITION_Y;
    private final int MAXIMUM_POSITION_Y;
    private final int POSITION_MULTIPLIKATOR_X;
    private final int POSITION_MULTIPLIKATOR_Y;

    /**
     * Konstruktor für das Model.
     */
    private Model() {
        //Default Seed 9223372036854775807L
        this.seed = 55555;
        this.rand = new Random(seed);

        this.MINIMUM_POSITION_X = 1;
        this.MAXIMUM_POSITION_X = 16;
        this.MINIMUM_POSITION_Y = 1;
        this.MAXIMUM_POSITION_Y = 16;
        this.POSITION_MULTIPLIKATOR_X = 40;
        this.POSITION_MULTIPLIKATOR_Y = 40;

        //ACHTUNG Limit der Punkten ist MAXIMUM_POSITION_X * MAXIMUM_POSITION_Y
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
        final int ANFANG_VON_PUNKTE_ARRAYLIST = 0;

        while (koordinaten.size() < anzahlParkhaus) {
            int xPos = rand.nextInt(MINIMUM_POSITION_X, MAXIMUM_POSITION_X) * POSITION_MULTIPLIKATOR_X;
            int yPos = rand.nextInt(MINIMUM_POSITION_Y, MAXIMUM_POSITION_Y) * POSITION_MULTIPLIKATOR_Y;

            Koordinaten neueKoordinaten = new Koordinaten(xPos, yPos);

            if(!koordinaten.contains(neueKoordinaten)){
                koordinaten.add(neueKoordinaten);
                punkte.add(new Parkhaus(xPos, yPos));
            }
        }

        //Generation für Bushaltestellen
        while (koordinaten.size() < anzahlParkhaus+anzahlBushaltestellen){
            int xPos = rand.nextInt(MINIMUM_POSITION_X, MAXIMUM_POSITION_X) * POSITION_MULTIPLIKATOR_X;
            int yPos = rand.nextInt(MINIMUM_POSITION_Y, MAXIMUM_POSITION_Y) * POSITION_MULTIPLIKATOR_Y;

            Koordinaten neueKoordinaten = new Koordinaten(xPos, yPos);

            if(!koordinaten.contains(neueKoordinaten)){
                koordinaten.add(neueKoordinaten);
                if (!punkte.isEmpty()) {
                    punkte.add(rand.nextInt(ANFANG_VON_PUNKTE_ARRAYLIST, punkte.size()), new Bushaltestelle(xPos, yPos));
                } else {
                    punkte.add(new Bushaltestelle(xPos, yPos));
                }
            }
        }

        //Generation für Bahnhof
        while (koordinaten.size() < anzahlParkhaus+anzahlBushaltestellen+anzahlBahnhöfe){
            int xPos = rand.nextInt(MINIMUM_POSITION_X, MAXIMUM_POSITION_X) * POSITION_MULTIPLIKATOR_X;
            int yPos = rand.nextInt(MINIMUM_POSITION_Y, MAXIMUM_POSITION_Y) * POSITION_MULTIPLIKATOR_Y;

            Koordinaten neueKoordinaten = new Koordinaten(xPos, yPos);

            if(!koordinaten.contains(neueKoordinaten)){
                koordinaten.add(neueKoordinaten);
                if (!punkte.isEmpty()) {
                    punkte.add(rand.nextInt(ANFANG_VON_PUNKTE_ARRAYLIST, punkte.size()), new Bahnhof(xPos, yPos));
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

        /*
        Erstellung vom Netz für die Bahnhöfe
        Laufzeit O(n)
         */
        ArrayList<Bahnhof> bahnhoefe = new ArrayList<>();
        for (Punkt punkt : punkte) {
            if (punkt instanceof Bahnhof) {
                bahnhoefe.add((Bahnhof) punkt);
            }
        }

        for (int i = 0; i < bahnhoefe.size(); i++) {
            Bahnhof aktuell = bahnhoefe.get(i);
            //Nimmt das nächste Element, falls es schon das letzte element ist nimmt es das erste Element als Ziel.
            Bahnhof naechste;
            if (i + 1 < bahnhoefe.size()) {
                naechste = bahnhoefe.get(i + 1);
            } else {
                naechste = bahnhoefe.getFirst();
            }

            ArrayList<Weg> wege = new ArrayList<>();
            wege.add(new Schiene(aktuell, naechste));
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
    }

    /**
     * Methode die über dem Controller aufgerufen wird um benutzerdefinierte Punkte zu Erstellen
     * @param xPos X-Position vom Punkt 1-15
     * @param yPos Y-Position vom Punkt 1-15
     * @param datentyp Ob es Parkhaus 'p', Bushaltestelle 'b' oder Bahnhof 'z' ist
     * @throws PunktExistiertBereitsException Falls die Koordinaten schon belegt sind.
     */
    public void benutzerDefinierterPunkt(int xPos, int yPos, char datentyp) throws PunktExistiertBereitsException {
        xPos *= POSITION_MULTIPLIKATOR_X;
        yPos *= POSITION_MULTIPLIKATOR_Y;
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
     * Gibt ein ArrayList mit allen Custom generierten Punkten zurück.
     *
     * @return ArrayList mit Custom generierten Punkten.
     */
    public ArrayList<Punkt> getCustomPunkte() {
        ArrayList<Punkt> customPunkte = new ArrayList<>();
        for (Punkt punkt : punkte) {
            if (punkt.isCustom()) {
                customPunkte.add(punkt);
            }
        }
        return customPunkte;
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