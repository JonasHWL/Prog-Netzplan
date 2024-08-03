package com.example.line;

import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.lang.reflect.Array;
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
        /*
        System.out.println(punkte.getFirst());
        System.out.println(punkte.get(8));
        berechneWeg2(punkte.get(1), punkte.get(8), true);
        System.out.println("Weg2");
        ArrayList<Weg> wegAusgabe = berechneWeg(punkte.get(1), punkte.get(8));
        for(Weg weg : wegAusgabe){
            System.out.println(weg.toString());
        }*/
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
            //TODO Name berücksichtigen.

            if (Objects.equals(datentyp, "Parkhaus")) {
                punkte.add(new Parkhaus(xPos, yPos, name));
            } else if (Objects.equals(datentyp, "Bushaltestelle")) {
                punkte.add(new Bushaltestelle(xPos, yPos, name));
            } else if (Objects.equals(datentyp, "Bahnhof")) {
                punkte.add(new Bahnhof(xPos, yPos, name));
            }
        }
    }

    //TODO Wegberechnung verbessern
    /**
     * Experimental! Fehlerhaft! Nicht Verwenden außer für Testzwecke
     * bestimmt alle Weg Objekte welche benötigt werden, um die Koordinaten für die Fahrzeuge zu bestimmen.
     *
     * @param start Startpunkt
     * @param ziel  Endpunkt
     * @return Arraylist mit Weg Objekte von denen weg.get(i).getStartX aufgerufen werden kann,
     */
    private ArrayList<Weg> berechneWeg2(Punkt start, Punkt ziel, boolean normalfolge) {
        ArrayList<Weg> wegAusgabe = new ArrayList<>();
        ArrayList<Punkt> wegPunkte = new ArrayList<>();
        if(normalfolge){
            int i = punkte.lastIndexOf(start);
            int j = punkte.lastIndexOf(ziel);
            wegPunkte.add(start);
            if (i != -1 && j != -1) {
                while (i < j) {
                    wegPunkte.add(punkte.get(i++));
                }
            }
        }
        return wegAusgabe;
    }


    //TODO Breitensuche verbessern

    /**
     * Experimental! Fehlerhaft! Nicht Verwenden außer für Testzwecke
     *
     * @param start Startpunkt
     * @param ende Endpunk
     * @return Wege die gefahren werden müssen
     */
    private ArrayList<Weg> berechneWeg(Punkt start, Punkt ende){
        ArrayList<Weg> wegAusgabe = new ArrayList<>();

        class Node {
            // vargaenger = pi
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


        /*
        while(!queue.isEmpty()){
            Node u = queue.pop();
            for(int i = 0; i < u.punkt.getWegGruppen().size(); i++){
                if(u.punkt.getWegGruppen().get(i).getStartPunkt() == u.punkt){
                    Punkt tempPunkt = u.punkt.getWegGruppen().get(i).getEndPunkt();
                    for( Node node : nodes){
                        if(node.punkt == tempPunkt){
                            if(node.farbe == Color.WHITE){
                                node.farbe = Color.GRAY;
                                node.startpunktEntfernung = u.startpunktEntfernung + 1;
                                node.vorgaenger = u;
                                queue.add(node);
                            }
                        }
                    }
                }
            }
            u.farbe = Color.BLACK;
        }

        System.out.println("Loool");

        Node endeNode;
        endeNode = null;
        for(Node node : nodes){
            if(node.punkt == ende){
                endeNode = node;
                break;
            }
        }

        if (endeNode == null){
            throw new IllegalStateException("Fehlender Punkt start Parameter");
        }

        int counter = 0;
        Node aktuellerNode = endeNode;
        Node letzterNode = null;
        while(aktuellerNode.startpunktEntfernung != 0){
            for(WegGruppe wegGruppe : aktuellerNode.punkt.getWegGruppen()){
                if(aktuellerNode.vorgaenger.punkt == wegGruppe.getStartPunkt() && aktuellerNode.punkt == wegGruppe.getEndPunkt()){
                    wegAusgabe.addAll(wegGruppe.getWege());
                    counter++;
                    break;
                }
            }
            letzterNode = aktuellerNode;
            aktuellerNode = aktuellerNode.vorgaenger;
        }
        /*
        for(WegGruppe wegGruppe : aktuellerNode.punkt.getWegGruppen()){
            assert letzterNode != null;
            if(letzterNode.punkt == wegGruppe.getStartPunkt()){
                wegAusgabe.addAll(wegGruppe.getWege());
                counter++;
                break;
            }
        }*/


        System.out.println("Kek");
        //System.out.println(counter);

        return wegAusgabe;
    }
}