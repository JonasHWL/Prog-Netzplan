package com.example.line;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControllerMenge {

    public Button bestaetigen1;

    @FXML
    void initialize() {
        model = Model.getInstanz();
    }

    @FXML
    Model model;


    @FXML
    TextField eingabeZahlParkhaus;
    @FXML
    TextField eingabeZahlBus;
    @FXML
    TextField eingabeZahlBahnhof;

    @FXML
    Label anzahlParkplatz;
    @FXML
    Label anzahlBus;
    @FXML
    Label anzahlBahnhof;
    @FXML
    Label errorFeld;

    @FXML
    Integer anzahlFeld1;
    @FXML
    Integer anzahlFeld2;
    @FXML
    Integer anzahlFeld3;

    /**
     * hier werden die eingabe felder geprüft und in variablen geschrieben.
     *
     * @param event Button click
     */
    @FXML
    void bestaetigen1(ActionEvent event) {
        ex.setDisable(false);
        try{
            anzahlFeld1 = Integer.parseInt(eingabeZahlParkhaus.getText());
            anzahlFeld2 = Integer.parseInt(eingabeZahlBus.getText());
            anzahlFeld3 = Integer.parseInt(eingabeZahlBahnhof.getText());
            try{
                //hier wird die beenden Methode aufgerufen mit der Stage auf der sich der bestaetigen1 befindet.
                Controller.beenden((Stage) ((Node) event.getSource()).getScene().getWindow());
                //weiterleitung an die erstellen Methode damit man sich code dopplungen vermeidet
                erstellen(anzahlFeld1,anzahlFeld2,anzahlFeld3);
            }
            catch (NullPointerException e) {
                System.out.println("ERROR!");
            }
        }
        catch(NumberFormatException e){
            errorFeld.setText("Bitte eine zahl eingeben");
        }
    }

    /**
     * Die erstellen Methode ruft die generieren Methode in model auf um die Anzahl der Punkte festzulegen
     *
     * @param anzahlParkplatz Int werde wie viele Parkplätze
     * @param anzahlBus Int werde wie viele bushaltestellen
     * @param anzahlBahnhof Int werde wie viele bahnhöfe
     */
    @FXML
    void erstellen(int anzahlParkplatz, int anzahlBus, int anzahlBahnhof){;
        model.generiere(anchorPane, anzahlParkplatz, anzahlBus, anzahlBahnhof);
        System.out.println("Generieren Knopf gedrückt und ausgeführt");
    }

    AnchorPane anchorPane;
    Button erstellen;
    Button customB;
    MenuItem ex;
    MenuItem in;
    /**
     * Uebergabe ist eine Methode die communication zwischen 2 Controllern möglich macht und sachen zwischen diesen austauscht.
     *
     * @param pane übergabe des AnchorPanes von der die Methode aufgerufen wurde.
     * @param P gibt an ob das Parkhaus als checkBox aktiviert ist
     * @param Ba gibt an ob der Bahnhof als checkBox aktiviert ist
     * @param Bu gibt an ob die Bushaltestellen als checkBox aktiviert ist
     * @param eline gibt denn Eline Button an die neue Scene weiter
     * @param custom gibt denn custom Button an die neue Scene weiter
     */
    public void Uebernahme(AnchorPane pane, boolean P, boolean Ba, boolean Bu, Button eline, Button custom, MenuItem exU, MenuItem inU){
        anchorPane = pane;
        erstellen = eline;
        customB = custom;
        ex = exU;
        in = inU;
        //Hier wird geguckt welche CheckBox auf der Main view ausgewählt ist
        //Guckt welche nicht gecheckt sind und setzt diese dann auf 0
        if(!P){

            eingabeZahlParkhaus.setText("0");
            anzahlParkplatz.setDisable(true);
            eingabeZahlParkhaus.setDisable(true);
            anzahlParkplatz.setVisible(false);
            eingabeZahlParkhaus.setVisible(false);
        }
        if(!Ba){
            eingabeZahlBus.setText("0");
            anzahlBus.setDisable(true);
            eingabeZahlBus.setDisable(true);
            anzahlBus.setVisible(false);
            eingabeZahlBus.setVisible(false);
        }
        if(!Bu){
            eingabeZahlBahnhof.setText("0");
            anzahlBahnhof.setDisable(true);
            eingabeZahlBahnhof.setDisable(true);
            anzahlBahnhof.setVisible(false);
            eingabeZahlBahnhof.setVisible(false);
        }
    }

    /**
     * hier werden die beiden Buttons die beim erstellen deaktiviert werden wieder aktiviert
     *
     */
    @FXML
    public void cancel(ActionEvent event) {
        erstellen.setDisable(false);
        customB.setDisable(false);
        in.setDisable(false);
        Controller.beenden((Stage) ((Node) event.getSource()).getScene().getWindow());
    }
}
