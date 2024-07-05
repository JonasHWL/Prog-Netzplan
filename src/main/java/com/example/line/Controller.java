package com.example.line;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Verwalten der Main-view.
 * In dieser Class wird auf alles geguckt was die Buttons machen und verwaltet.
 *
 * @author Jonas Hanewinkel
 * @version 03.07.2024
 */
public class Controller {


    @FXML
    AnchorPane anchorPane;
    Model model;

    @FXML
    void initialize() {
        model = Model.getInstanz();
    }

    @FXML
    public Button customButton;
    @FXML
    public Button generieren;
    @FXML
    public MenuItem ex;
    @FXML
    public MenuItem in;


    @FXML
    CheckBox checkBoxP;
    @FXML
    CheckBox checkBoxBa;
    @FXML
    CheckBox checkBoxBu;

    @FXML
    private Parent root;

    /**
     * In dieser Methode wird initialisiert das in dem anchorPane in der mitte des fensters der Netzplan erstellt wird.
     *
     * @param event Button click
     */
    @FXML
    void generieren(ActionEvent event) {
        //hier wird geguckt ob mindestens eine der Checkboxen am linken rand angehackt werden muss da sonst kein netzplan
        //erstellt werden kann.
        //wenn in
        if(!checkBoxP.isSelected() && !checkBoxBa.isSelected() && !checkBoxBu.isSelected()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Mindestens in einer der CheckBoxen muss ein Haken sein!!!");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        //deaktiviert denn custom und den generieren Button da diese nicht mehr gebraucht werden wenn der Netzplan erstellt wurde
        customButton.setDisable(true);
        generieren.setDisable(true);
        in.setDisable(true);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("anzahl-view.fxml"));
            root = loader.load();
            ControllerMenge controllerMenge = loader.getController();
            controllerMenge.Uebernahme(anchorPane, checkBoxP.isSelected(), checkBoxBa.isSelected(), checkBoxBu.isSelected(),generieren, customButton, ex, in);
            Stage fenster1 = new Stage();
            fenster1.setTitle("Anzahl");
            fenster1.setScene(new Scene(root));
            fenster1.show();
        }
        catch (Exception e) {
            //ausgabe in der Console wen das fenster nicht geladen werden konnte
            System.out.println("Fenster konnte nicht geladen werden");
        }
    }

    /*
            ---------- Ab hier ist für die erstellung der scene zu ständig wo man die custom punkte einlesen kann. -----------

            Datum: 10.06.24
            Custom ist das event damit der Button Custom ein Neues Fenster erstellt
            über die methode Eingabe wird der Eingabe Button erstellt und die Funktion Uebergabe aufgerufen.
     */

    /**
     * Hier wird mit einem Button click das Custom fenster geöffnet.
     *
     * @param event Button click
     */
    @FXML
    void Custom(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("custom-main-view.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage fenster = new Stage();
            FensterController fensterController = fxmlLoader.getController();
            //übergabe der Stage und des Parents an den fensterController
            fensterController.uebergabe(fenster,root1);
            fenster.setTitle("Eingabe");
            fenster.setScene(new Scene(root1));
            fenster.show();
        }
        catch (Exception e) {
            System.out.println("Fenster konnte nicht geladen werden");
        }
    }

    /**
     * Diese Methode ist dafür da eine Stage zu beenden.
     * Die Methode existiert damit sich code dopplungen vermieden werden kann.
     *
     * @param stage mit stage wird die Stage übergeben von der die Methode aufgerufen wurde
     */
    @FXML
    public static void beenden(Stage stage){
        if(stage != null)
            stage.close();
    }


    /*
    ---------   Menubar     --------
    */

    // Erstellung einer Hilfe alert box, um den grundaufbau zu erklären es
    //TODO fehlen noch weitere Informationen!

    @FXML
    public void help(ActionEvent event) {
        Alert help = new Alert(Alert.AlertType.INFORMATION);
        help.setTitle("Hilfe");
        help.setHeaderText(null);
        help.setContentText("Mit dem Button Generieren kann man einen Netzplan/Karte erstellen" +"\n"+
                "Mit dem Button Custom kann man Eigene Punkte der Karte festlegen");
        help.showAndWait();
    }

    @FXML
    public void ex(ActionEvent event) {
        model.export();
    }

    @FXML
    public void in(ActionEvent event) {
        model.importKarte(anchorPane);
    }
}