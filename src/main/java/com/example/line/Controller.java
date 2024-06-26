package com.example.line;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;


public class Controller {


    @FXML
    AnchorPane anchorPane;
    Model model;

    @FXML
    void initialize() {
        model = Model.getInstance();
    }

    @FXML
    public Button customButton;
    @FXML
    public Button generieren;

    @FXML
    CheckBox checkBoxP;
    @FXML
    CheckBox checkBoxBa;
    @FXML
    CheckBox checkBoxBu;

    @FXML
    private Parent root;

    @FXML
    void generieren(ActionEvent event) {
        if(!checkBoxP.isSelected() && !checkBoxBa.isSelected() && !checkBoxBu.isSelected()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Mindestens in einer der CheckBoxen muss ein Haken sein!!!");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        customButton.setDisable(true);
        generieren.setDisable(true);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("anzahl-view.fxml"));
            root = loader.load();
            ControllerMenge controllerMenge = loader.getController();
            controllerMenge.Uebernahme(anchorPane, checkBoxP.isSelected(), checkBoxBa.isSelected(), checkBoxBu.isSelected(),generieren, customButton);
            Stage fenster1 = new Stage();
            fenster1.setTitle("Anzahl");
            fenster1.setScene(new Scene(root));
            fenster1.show();
        }
        catch (Exception e) {
            System.out.println("Fenster konnte nicht geladen werden");
        }
    }

    /*
            ---------- Ab hier ist für die erstellung der scene zu ständig wo man die custom punkte einlesen kann. -----------

            Datum: 10.06.24
            Custom ist das event damit der Button Custom ein Neues Fenster erstellt
            über die methode Eingabe wird der Eingabe Button erstellt und die Funktion Uebergabe aufgerufen.
     */

    @FXML
    void Custom(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("custom-main-view.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage fenster = new Stage();
            FensterController fensterController = fxmlLoader.getController();
            //TODO fenster verbessern für eingabe von Custom bahnhof und bus halte
            fensterController.uebergabe(fenster,root1);
            fenster.setTitle("Eingabe");
            fenster.setScene(new Scene(root1));
            fenster.show();
        }
        catch (Exception e) {
            System.out.println("Fenster konnte nicht geladen werden");
            e.printStackTrace();

        }
    }

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


}