package com.example.line;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;


public class Controller {




    @FXML
    public Button customButton;

    @FXML
    private AnchorPane anchorPane;
    //TODO instanzvariable Besser speichern.
    Model model; //= new Model(anchorPane);

    @FXML
    void Eline(ActionEvent event) {
        customButton.setDisable(true);
        model = new Model(anchorPane);
        //TODO mit anchorPane/root besser umgehen.
        //model.setRoot(anchorPane);
        //model.generiere(5, 0, 0);
        System.out.println("Generieren Knopf gedrückt und ausgeführt");
    }



    /*
            ---------- Ab hier ist für die erstellung der scene zu ständig wo man die custom punkte einlesen kann. -----------

            Datum: 10.06.24
            Custom ist das event damit der Button Custom ein Neues Fenster erstellt
            über die methode Eingabe wird der Eingabe Button erstellt und die Funktion Uebergabe aufgerufen.
     */

    @FXML
    void Custom(ActionEvent event) throws IOException {
        int i = 0;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("eingabe-view.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage fenster = new Stage();
            fenster.setTitle("Eingabe");
            fenster.setScene(new Scene(root1));
            fenster.show();
        }
        catch (Exception e) {
            System.out.println("Fenster konnte nicht geladen werden");
        }
    }

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
    void Eingabe(ActionEvent event) {
        if(textField1.getText().isEmpty() || textField2.getText().isEmpty() || textField3.getText().isEmpty()){
            Error.setText("Alle Felder müssen ausgefüllt sein.");
            return;
        }
        try{
            //Auslesen der Text felder wo man pos1, pos2 und denn namen eingeben kann
            p1 = Integer.parseInt(textField1.getText());
            p2 = Integer.parseInt(textField2.getText());
            n = textField3.getText();

            //hier werden die Pos1, Pos2 und der name in die ausgabe methode geschriebben.
            Uebergabe ue = new Uebergabe(p1, p2, n);
            model.benutzerDefinierterPunkt(p1, p2, n, 'p');
            //Hier wird festgestellt in welcher Stage man sich grade befindet dafür guckt man wo das ActionEvent
            //ausgeführt wurde deswegen kann man das nicht in einer anderen Methode machen
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            //aufruf der beenden Methode die die stage auf der man sich befindet beendet
            beenden(stage);
        }
        catch (NumberFormatException e) {
            Error.setText("Ungültige Eingabe für Zahlen.");
        }
    }

    @FXML
    public void Abbrechen(ActionEvent event) {
        //Hier wird festgestellt in welcher Stage man sich grade befindet dafür guckt man wo das ActionEvent
        //ausgeführt wurde deswegen kann man das nicht in einer anderen Methode machen
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //aufruf der beenden Methode die die stage auf der man sich befindet beendet
        beenden(stage);
        System.out.println("Test");
    }

    @FXML
    public void beenden(Stage stage){
        if(stage != null)
            stage.close();
    }
}