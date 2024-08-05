package com.example.line;

import javafx.scene.layout.Pane;

import java.awt.*;
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
    private final ArrayList<Zwischenpunkt> zwischenpunkte = new ArrayList<>();
    private final HashSet<Koordinaten> koordinaten= new HashSet<>();
    private final ArrayList<Weg> wege= new ArrayList<>();
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
        for (Punkt punkt : punkte) {
            if (!(punkt instanceof Zwischenpunkt)) {
                punkt.setBreitensucheErgebnis(breitensuche(punkt));
            }
        }
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
        int anzahl = 0;
        int zielPunkte = anzahlParkhaus + anzahlBushaltestellen + anzahlBahnhöfe;

        //Kontrolle ob, gewünschte Punkte nicht die maximale Anzahl an Punkten überschreitet
        if (zielPunkte > MAXIMUM_POSITION_X * MAXIMUM_POSITION_Y){
            zielPunkte = MAXIMUM_POSITION_X* MAXIMUM_POSITION_Y;
        }

        while (anzahl < anzahlParkhaus && anzahl < zielPunkte) {
            int xPos = rand.nextInt(MINIMUM_POSITION_X, MAXIMUM_POSITION_X) * POSITION_MULTIPLIKATOR_X;
            int yPos = rand.nextInt(MINIMUM_POSITION_Y, MAXIMUM_POSITION_Y) * POSITION_MULTIPLIKATOR_Y;

            Koordinaten neueKoordinaten = new Koordinaten(xPos, yPos);

            if(!koordinaten.contains(neueKoordinaten)){
                koordinaten.add(neueKoordinaten);
                punkte.add(new Parkhaus(xPos, yPos, "Parkhaus " + 1 + anzahl++));
            }
        }
        //Erstellung der Bushaltestellen
        while (anzahl < anzahlParkhaus+anzahlBushaltestellen && anzahl < zielPunkte){
            int xPos = rand.nextInt(MINIMUM_POSITION_X, MAXIMUM_POSITION_X) * POSITION_MULTIPLIKATOR_X;
            int yPos = rand.nextInt(MINIMUM_POSITION_Y, MAXIMUM_POSITION_Y) * POSITION_MULTIPLIKATOR_Y;

            Koordinaten neueKoordinaten = new Koordinaten(xPos, yPos);

            if(!koordinaten.contains(neueKoordinaten)){
                koordinaten.add(neueKoordinaten);
                if (!punkte.isEmpty()) {
                    punkte.add(rand.nextInt(ANFANG_VON_PUNKTE_ARRAYLIST, punkte.size()), new Bushaltestelle(xPos, yPos, "Bushaltestelle " + anzahl++));
                } else {
                    punkte.add(new Bushaltestelle(xPos, yPos, "Bushaltestelle " + 1 + anzahl++));
                }
            }
        }

        //Erstellung der Bahnhöfe
        while (anzahl < anzahlParkhaus+anzahlBushaltestellen+anzahlBahnhöfe && anzahl < zielPunkte){
            int xPos = rand.nextInt(MINIMUM_POSITION_X, MAXIMUM_POSITION_X) * POSITION_MULTIPLIKATOR_X;
            int yPos = rand.nextInt(MINIMUM_POSITION_Y, MAXIMUM_POSITION_Y) * POSITION_MULTIPLIKATOR_Y;

            Koordinaten neueKoordinaten = new Koordinaten(xPos, yPos);

            if(!koordinaten.contains(neueKoordinaten)){
                koordinaten.add(neueKoordinaten);
                if (!punkte.isEmpty()) {
                    punkte.add(rand.nextInt(ANFANG_VON_PUNKTE_ARRAYLIST, punkte.size()), new Bahnhof(xPos, yPos, "Bahnhof " + anzahl++));
                } else {
                    punkte.add(new Bahnhof(xPos, yPos, "Bahnhof " + anzahl++));
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
            Punkt naechste;
            if (i + 1 < punkte.size() ) {
                naechste = punkte.get(i + 1);
            } else {
                naechste = punkte.getFirst();
            }

            Zwischenpunkt mitte = new Zwischenpunkt((aktuell.getXPos() + naechste.getXPos())/2, (aktuell.getYPos() + naechste.getYPos())/2);
            Zwischenpunkt startZuMitte = new Zwischenpunkt(aktuell.getXPos(), mitte.getYPos());
            Zwischenpunkt mitteZuEnde = new Zwischenpunkt(mitte.getXPos(), naechste.getYPos());

            aktuell.addBenachbartePunkte(startZuMitte);
            startZuMitte.addBenachbartePunkte(aktuell);
            startZuMitte.addBenachbartePunkte(mitte);
            mitte.addBenachbartePunkte(startZuMitte);
            mitte.addBenachbartePunkte(mitteZuEnde);
            mitteZuEnde.addBenachbartePunkte(mitte);
            mitteZuEnde.addBenachbartePunkte(naechste);
            naechste.addBenachbartePunkte(mitteZuEnde);

            this.zwischenpunkte.add(startZuMitte);
            this.zwischenpunkte.add(mitteZuEnde);
            this.zwischenpunkte.add(mitte);
            this.wege.add(new Straße(aktuell, startZuMitte));
            this.wege.add(new Straße(startZuMitte, mitte));
            this.wege.add(new Straße(mitte, mitteZuEnde));
            this.wege.add(new Straße(mitteZuEnde, naechste));
        }

        //Erstellung vom Netz für die Bahnhöfe
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

            this.wege.add(new Schiene(aktuell, naechste));
        }
        //Zeichnen der Wege und Punkte
        root.getChildren().addAll(wege);
        root.getChildren().addAll(punkte);

        punkte.addAll(zwischenpunkte);
    }

    /**
     * Methode die über dem Controller aufgerufen wird um benutzerdefinierte Punkte zu Erstellen
     * @param xPos X-Position vom Punkt 1-15
     * @param yPos Y-Position vom Punkt 1-15
     * @param datentyp Ob es Parkhaus 'p', Bushaltestelle 'b' oder Bahnhof 'z' ist
     * @throws PunktExistiertBereitsException Falls die Koordinaten schon belegt sind.
     */
    public void benutzerDefinierterPunkt(int xPos, int yPos, String name ,char datentyp) throws PunktExistiertBereitsException {
        xPos *= POSITION_MULTIPLIKATOR_X;
        yPos *= POSITION_MULTIPLIKATOR_Y;
        Koordinaten neueKoordinaten = new Koordinaten(xPos, yPos);
        switch (datentyp) {
            case 'p' :
                if(!koordinaten.contains(neueKoordinaten)){
                    koordinaten.add(neueKoordinaten);
                    Parkhaus parkhaus = new Parkhaus(xPos, yPos, name);
                    punkte.add(parkhaus);
                    parkhaus.setCustom(true);
                } else {
                    throw new PunktExistiertBereitsException("Diese Koordinaten sind belegt!");
                }
                break;
            case 'b' :
                if(!koordinaten.contains(neueKoordinaten)){
                    koordinaten.add(neueKoordinaten);
                    Bushaltestelle bushaltestelle = new Bushaltestelle(xPos, yPos, name);
                    punkte.add(bushaltestelle);
                    bushaltestelle.setCustom(true);
                } else {
                    throw new PunktExistiertBereitsException("Diese Koordinaten sind belegt!");
                }
                break;
            case 'z' :
                if(!koordinaten.contains(neueKoordinaten)){
                    koordinaten.add(neueKoordinaten);
                    Bahnhof bahnhof = new Bahnhof(xPos, yPos, name);
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
     * Getter für alle Punkte.
     *
     * @return Arraylist mit allen aktuellen Punkten
     */
    public ArrayList<Punkt> getPunkte() {
        return punkte;
    }

    /**
     * Gibt ein Arraylist mit Punkt Objekten, welche nicht Zwischenpunkte sind als Datentyp aus.
     *
     * @return Arraylist mit Punkt Objekten
     */
    public ArrayList<Punkt> getNichtZwischenpunktPunkte(){
        ArrayList<Punkt> nichtZwischenpunktPunkte = new ArrayList<>();
        for (Punkt punkt : punkte) {
            if (!(punkt instanceof Zwischenpunkt)) {
                nichtZwischenpunktPunkte.add(punkt);
            }
        }
        return nichtZwischenpunktPunkte;
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
            String name = zeile.split("Name=")[1].split(",")[0];
            double xPos = Double.parseDouble(zeile.split("xPos=")[1].split(",")[0]);
            double yPos = Double.parseDouble(zeile.split("yPos=")[1].split("]")[0]);
            if (Objects.equals(datentyp, "Parkhaus")) {
                punkte.add(new Parkhaus(xPos, yPos, name));
            } else if (Objects.equals(datentyp, "Bushaltestelle")) {
                punkte.add(new Bushaltestelle(xPos, yPos, name));
            } else if (Objects.equals(datentyp, "Bahnhof")) {
                punkte.add(new Bahnhof(xPos, yPos, name));
            }
        }
    }

    /**
     * Die Klasse Node dient dazu, um die Punkte klasse so zu übersetzen, dass es möglich ist die Breitensuche oder Dijkstra-Algorithmus anzuwenden.
     */
    static class Node {
        // vorgaenger = pi
        Node vorgaenger;
        static int entfernung;
        // startpunktEntfernung = d
        int startpunktEntfernung;
        final Punkt punkt;
        static int idZaeler;
        final int id;
        Color farbe;

        // node = u
        Node(Punkt punkt){
            this.punkt = punkt;
            this.id = idZaeler++;
            this.farbe = Color.WHITE;
            this.startpunktEntfernung = 0;
            this.vorgaenger = this;
        }
    }

    /**
     * Breitensuche für den jeweiligen punkt.
     * Gibt ein Arraylist aus mit Nodes welche als vorgaenger ein Node haben welche den schnellsten weg zum Start Node hätten.
     *
     * @param start Startpunkt
     * @return Wege die gefahren werden müssen
     */
    private ArrayList<Node> breitensuche(Punkt start){
        //nodes = G.V
        ArrayList<Node> nodes = new ArrayList<>();
        for(Punkt punkt : punkte){
            nodes.add(new Node(punkt));
        }

        Node startNode = null;
        for(Node node : nodes){
            if(node.punkt == start){
                startNode = node;
                break;
            }
        }

        if (startNode == null){
            throw new IllegalStateException("Fehlender Punkt start Parameter");
        }

        startNode.farbe = Color.gray;
        startNode.startpunktEntfernung = 0;
        startNode.vorgaenger = startNode;

        LinkedList<Node> queue = new LinkedList<>();
        queue.add(startNode);

        while(!queue.isEmpty()){
            Node u = queue.pop();
            for(int i = 0; i < u.punkt.getBenachbartePunkte().size(); i++){
                for( Node node : nodes){
                    if(u.punkt.getBenachbartePunkte().contains(node.punkt)){
                        if(node.farbe == Color.WHITE){
                            node.farbe = Color.GRAY;
                            node.startpunktEntfernung = u.startpunktEntfernung + 1;
                            node.vorgaenger = u;
                            queue.add(node);
                        }
                    }
                }
            }
            u.farbe = Color.BLACK;
        }

        return nodes;
    }

    /**
     * Gibt ein Arraylist von Punkten aus, zu dem sich ein Fahrzeug hinbewegen muss um von einem Punkt zu dem anderen zu kommen.
     * Von jedem Punkt müssen X und Y Koordinaten abgelesen werden.
     *
     * @param start Start
     * @param ende Ziel
     * @return Arraylist mit Punkten welche in der reihenfolge besucht werden müssen
     */
    public ArrayList<Punkt> schnellsterWeg(Punkt start, Punkt ende){
        Node zielNode = null;
        for (Node node : start.getBreitensucheErgebnis()) {
            if (node.punkt == ende) {
                zielNode = node;
                break;
            }
        }

        ArrayList<Punkt> wegZwischenZiele = new ArrayList<>();
        Node aktuellerNode = zielNode;
        assert aktuellerNode != null;
        while(aktuellerNode.startpunktEntfernung != 0){
            wegZwischenZiele.add(aktuellerNode.punkt);
            aktuellerNode = aktuellerNode.vorgaenger;
        }
        wegZwischenZiele.add(aktuellerNode.punkt);
        return wegZwischenZiele;
    }
}