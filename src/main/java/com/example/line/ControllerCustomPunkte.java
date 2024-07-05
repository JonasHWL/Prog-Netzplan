package com.example.line;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Eingabe der Custom Punkte
 *
 * @author Jonas Hanewinkel
 * @version 03.07.2024
 */
public class ControllerCustomPunkte {

    @FXML
    void initialize() {
        model = Model.getInstanz();
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
    private char art;
    @FXML
    private String kopfZ;
    /**
     * uebergabe ist eine Methode die communication zwischen 2 Controllern möglich macht und sachen zwischen diesen austauscht.
     *
     * @param stage übergibt die Stage, von der die Methode aufgerufen wurde.
     * @param rootUE übergibt die scene von der die Methode aufgerufen wurde.
     * @param rootUE1 übergibt die scene die aufgerufen würde.
     * @param kopfzeile übergibt einen string der in der überschrift steht | und was alls titel verwendet werden soll.
     */
    @FXML
    public void uebergabe(Stage stage, Parent rootUE, Parent rootUE1, String kopfzeile){
        //Beschreibung der Variablen mit den werten.
        test1 = stage;
        root1 = rootUE;
        root2 = rootUE1;
        kopfZ = kopfzeile;
        //mit dieser Abfrage wird geguckt welcher Typ der Custom punkt ist.
        if(kopfzeile == "Parkhaus"){
            art = 'p';
        }
        else if(kopfzeile == "Bushaltestelle"){
            art = 'b';
        }
        else{
            art = 'z';
        }
        //setzen der Überschrift auf der neuen Scene
        kopf.setText("Eingabe Custom: " + kopfzeile);
    }

    Integer p1;
    Integer p2;
    String n;

    /**
     * Hier wird nach dem der Button eingabe gedrückt wird geguckt ob alle felder ausgefüllt sind
     * und dann an denn FensterController weiter gegeben der sie in die Tabelle einfügt
     *
     * @param event Der Button click
     */
    @FXML
    void eingabe(ActionEvent event) {
        //Es wird hier geguckt ob in allen fenstern eine eingabe getätigt wurde. Wenn nicht alle fenster ausgefüllt sind
        //wird in einem Label eine fehler meldung ausgegeben und wieder auf die Scene zurück gesetzt.
        if(textField1.getText().isEmpty() || textField2.getText().isEmpty() || textField3.getText().isEmpty()){
            //ausgabe das es einen Fehler wen nicht alle felder ausgefüllt sind
            Error.setText("Alle Felder müssen ausgefüllt sein.");
            //hier wird die eingabe abgebrochen um fehler zu vermeiden.
            return;
        }
        try{
            //Auslesen der Text felder wo man pos1, pos2 und den namen eingeben kann
            p1 = Integer.parseInt(textField1.getText());
            p2 = Integer.parseInt(textField2.getText());
            n = textField3.getText();
            if (p1 < 0 ||p2 < 0){
                Error.setText("Bitte nur positive Zahlen eingeben!");
                return;
            }
            //hier werden die Pos1, Pos2 und der name in die ausgabe methode geschriebben.
            Uebergabe ue = new Uebergabe(p1, p2, n);
            try {
                FensterController fenster = new FensterController();
                fenster.einlesen(n,kopfZ);
                model.benutzerDefinierterPunkt(p1, p2,n ,art);
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
     * Die Methode ruft die wechsel methode auf. über einen Button click.
     *
     */
    @FXML
    public void zurueck(ActionEvent event) {
        wechsel();
    }

    /**
     * Die Methode wechsel ist dafür da das man zwischen 2 scenen wechseln kann.
     * Die wechsel methode existiert da man öfter zwischen 2 Scenen wechseln muss und ich code
     * Dopplungen vermeiden will.
     */
    @FXML
    public void wechsel(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("custom-main-view.fxml"));
            Parent root1 = fxmlLoader.load();
            FensterController fensterController = fxmlLoader.getController();
            //übergabe um die Scene von der man kommt an die neue Methode weiter zuleiten.
            fensterController.uebergabe(test1, root1);
            test1.setScene(new Scene(root1));
        } catch (Exception e) {
            //ausgabe eines Fehlers wenn die Scene nicht gewechselt werden konnte
            //Das (wechsel) zeigt dabei wo der fehler ist da dieser Fehler code öfter vorkommt
            System.err.println("Scene konnte nicht gewechselt werden. (wechsel)");
        }
    }
}