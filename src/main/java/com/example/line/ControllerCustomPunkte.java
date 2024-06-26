package com.example.line;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerCustomPunkte {

    @FXML
    void initialize() {
        model = Model.getInstance();
    }

    @FXML
    Stage test1;
    @FXML
    Parent root1;
    @FXML
    Parent root2;

    @FXML
    Model model;

    @FXML
    private Label kopf;
    @FXML
    private Label Error;

    @FXML
    private TextField textField1;
    @FXML
    private TextField textField2;
    @FXML
    private TextField textField3;
    @FXML
    String art;

    /**
     * uebergabe ist eine Methode die communication zwischen 2 Controllern möglich macht und sachen zwischen diesen austauscht.
     *
     * @param stage übergibt die Stage, von der die Methode aufgerufen wurde.
     * @param rootUE übergibt die scene von der die Methode aufgerufen wurde.
     * @param rootUE1 übergibt die scene die aufgerufen würde.
     * @param kopfzeile übergibt einen string der in der überschrift steht.
     */
    @FXML
    public void uebergabe(Stage stage, Parent rootUE, Parent rootUE1, String kopfzeile){
        test1 = stage;
        root1 = rootUE;
        root2 = rootUE1;
        art = kopfzeile;
        kopf.setText("Eingabe Custom: " + kopfzeile);
    }

    Integer p1;
    Integer p2;
    String n;

    @FXML
    void eingabe(ActionEvent event) {
        if(textField1.getText().isEmpty() || textField2.getText().isEmpty() || textField3.getText().isEmpty()){
            Error.setText("Alle Felder müssen ausgefüllt sein.");
            return;
        }
        try{
            //Auslesen der Text felder wo man pos1, pos2 und den namen eingeben kann
            p1 = Integer.parseInt(textField1.getText());
            p2 = Integer.parseInt(textField2.getText());
            n = textField3.getText();

            //hier werden die Pos1, Pos2 und der name in die ausgabe methode geschriebben.
            Uebergabe ue = new Uebergabe(p1, p2, n);
            try {
                FensterController.einlesen(n,art,p1,p2);
                model.benutzerDefinierterPunkt(p1, p2, 'p');
            } catch (PunktExistiertBereitsException e) {
                Error.setText(e.getMessage());
                throw new RuntimeException(e);
            }
            wechsel();
        }
        catch (NumberFormatException e) {
            Error.setText("Ungültige Eingabe für Zahlen.");
        }
    }

    /**
     * Die Methode ruft die wechsel methode auf. über einen Button click
     *
     */
    @FXML
    public void zurueck(ActionEvent event) {
        wechsel();
    }

    /**
     * Die Methode wechsel ist dafür da das man zwischen 2 scenen wechseln kann.
     */
    @FXML
    public void wechsel(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("custom-main-view.fxml"));
            Parent root1 = fxmlLoader.load();
            FensterController fensterController = fxmlLoader.getController();
            fensterController.uebergabe(test1, root1);
            test1.setScene(new Scene(root1));
        } catch (Exception e) {
            System.err.println("Scene konnte nicht gewechselt werden.");
        }
    }
}
