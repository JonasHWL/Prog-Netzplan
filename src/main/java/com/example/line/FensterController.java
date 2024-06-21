package com.example.line;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class FensterController {



    @FXML
    void initialize() {
        model = Model.getInstance();


    }

    @FXML
    Model model;

    @FXML
    private Label Error;

    @FXML
    private TextField textField1;
    @FXML
    private TextField textField2;
    @FXML
    private TextField textField3;

    Integer p1;
    Integer p2;
    String n;

    @FXML
    Stage test1;
    @FXML
    Parent root1;

    @FXML
    public void uebergabe(Stage testS, Parent root){
        test1 = testS;
        root1 = root;
    }

    @FXML
    void Eingabe(ActionEvent event) {
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
                model.benutzerDefinierterPunkt(p1, p2, 'p');
            } catch (PunktExistiertBereitsException e) {
                Error.setText(e.getMessage());
                throw new RuntimeException(e);
            }
            //Hier wird festgestellt in welcher Stage man sich grade befindet dafür guckt man wo das ActionEvent ausgeführt wurde
            //und dann wird diese Stage in die Methode beenden() übergeben
            Controller.beenden((Stage) ((Node) event.getSource()).getScene().getWindow());
        }
        catch (NumberFormatException e) {
            Error.setText("Ungültige Eingabe für Zahlen.");
        }
    }

    @FXML
    public void Abbrechen(ActionEvent event) {
        //Hier wird festgestellt in welcher Stage man sich grade befindet dafür guckt man wo das ActionEvent ausgeführt wurde
        //und dann wird diese Stage in die Methode beenden() übergeben
        Controller.beenden((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    @FXML
    public void openPark(ActionEvent event) throws IOException {
        String kopfzeile = "Parkhaus";
        eingabe(kopfzeile);
    }

    @FXML
    public void openBus(ActionEvent event) throws IOException {
        String kopfzeile = "Bushaltestelle";
        eingabe(kopfzeile);
    }

    @FXML
    public void openBahn(ActionEvent event) throws IOException {
        String kopfzeile = "Bahnhof";
        eingabe(kopfzeile);
    }


    @FXML
    public void eingabe(String kopfzeile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("eingabe-view.fxml"));
        Parent root2 = fxmlLoader.load();
        test1.setScene(new Scene(root2));
        //ControllerCustomPunkte.test();
    }
}