package com.example.line;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControllerMenge {

    public Button bestaetigen1;

    @FXML
    void initialize() {
        model = Model.getInstance();
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
    Integer anzahlFeld1;
    @FXML
    Integer anzahlFeld2;
    @FXML
    Integer anzahlFeld3;

    @FXML
    void bestaetigen1(ActionEvent event) {
        try{
            anzahlFeld1 = Integer.parseInt(eingabeZahlParkhaus.getText());
            anzahlFeld2 = Integer.parseInt(eingabeZahlBus.getText());
            anzahlFeld3 = Integer.parseInt(eingabeZahlBahnhof.getText());
            try{
                Controller.beenden((Stage) ((Node) event.getSource()).getScene().getWindow());
                erstellen(anzahlFeld1,anzahlFeld2,anzahlFeld3);
            }
            catch (NullPointerException e) {
                System.out.println("ERROR!");
            }
        }
        catch(NumberFormatException e){
            System.out.println("Bitte eine zahl eingeben");
        }
    }

    @FXML
    void erstellen(int anzahlParkplatz, int anzahlBus, int anzahlBahnhof){;
        model.generiere(anchorPane, anzahlParkplatz, anzahlBus, anzahlBahnhof);
        System.out.println("Generieren Knopf gedrückt und ausgeführt");
    }

    AnchorPane anchorPane;
    Button erstellen;
    Button customB;

    public void Uebernahme(AnchorPane pane, boolean P, boolean Ba, boolean Bu, Button eline, Button custom ){
        anchorPane = pane;
        erstellen = eline;
        customB = custom;
        if(!P){
            //TODO muss noch auf 0 gesetzt werden
            eingabeZahlParkhaus.setText("2");
            anzahlParkplatz.setDisable(true);
            eingabeZahlParkhaus.setDisable(true);
            anzahlParkplatz.setVisible(false);
            eingabeZahlParkhaus.setVisible(false);
        }
        if(!Ba){
            //TODO muss noch auf 0 gesetzt werden
            eingabeZahlBus.setText("2");
            anzahlBus.setDisable(true);
            eingabeZahlBus.setDisable(true);
            anzahlBus.setVisible(false);
            eingabeZahlBus.setVisible(false);
        }
        if(!Bu){
            //TODO muss noch auf 0 gesetzt werden
            eingabeZahlBahnhof.setText("2");
            anzahlBahnhof.setDisable(true);
            eingabeZahlBahnhof.setDisable(true);
            anzahlBahnhof.setVisible(false);
            eingabeZahlBahnhof.setVisible(false);
        }
    }

    @FXML
    public void cancel(ActionEvent event) {
        erstellen.setDisable(false);
        customB.setDisable(false);
        Controller.beenden((Stage) ((Node) event.getSource()).getScene().getWindow());
    }
}
