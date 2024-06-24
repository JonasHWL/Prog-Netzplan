package com.example.line;
import java.util.ArrayList;

public class WegGruppe {
    private final ArrayList<Weg> wege;
    private final Punkt startPunkt;
    private final Punkt endPunkt;

    public WegGruppe(Punkt startPunkt,Punkt endPunkt) {
        wege = new ArrayList<>();
        this.startPunkt = startPunkt;
        this.endPunkt = endPunkt;
        Zwischenpunkt zwischenpunkt = new Zwischenpunkt((startPunkt.getXPos() + endPunkt.getXPos()) / 2, (startPunkt.getYPos() + endPunkt.getYPos()) / 2);

        for (int j = 0; j < 4; j++) {
            wege.add(new Straße(startPunkt, endPunkt, zwischenpunkt, j % 2 != 0, j / 2 == 0, j));
        }
    }

    public WegGruppe(Bahnhof startPunkt, Bahnhof endPunkt) {
        wege = new ArrayList<>();
        this.startPunkt = startPunkt;
        this.endPunkt = endPunkt;

        wege.add(new Schiene(startPunkt, endPunkt));
    }

    public ArrayList<Weg> getWege() {
        return wege;
    }

    public Punkt getStartPunkt() {
        return startPunkt;
    }

    public Punkt getEndPunkt() {
        return endPunkt;
    }
}
