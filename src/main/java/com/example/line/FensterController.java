package com.example.line;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class FensterController {

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
    public void abbrechen(ActionEvent event) throws IOException {
        //Hier wird festgestellt in welcher Stage man sich grade befindet dafür guckt man wo das ActionEvent ausgeführt wurde
        //und dann wird diese Stage in die Methode beenden() übergeben
        Controller.beenden((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    @FXML
    public void openPark(ActionEvent event) {
        String kopfzeile = "Parkhaus";
        eingabe(kopfzeile);
    }

    @FXML
    public void openBus(ActionEvent event) {
        String kopfzeile = "Bushaltestelle";
        eingabe(kopfzeile);
    }

    @FXML
    public void openBahn(ActionEvent event) {
        String kopfzeile = "Bahnhof";
        eingabe(kopfzeile);
    }

    @FXML
    public void eingabe(String kopfzeile) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("eingabe-view.fxml"));
                Parent root2 = fxmlLoader.load();
                ControllerCustomPunkte controllerCustomPunkte = fxmlLoader.getController();
                controllerCustomPunkte.uebergabe(test1, root1, root2, kopfzeile);
                test1.setScene(new Scene(root2));
            }catch (IOException e){
                System.out.println("Scene konnte nicht gewechselt werden.");
            }
    }
}