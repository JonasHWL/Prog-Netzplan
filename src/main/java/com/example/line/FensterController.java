package com.example.line;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class FensterController implements Initializable {


    @FXML
    Stage test1;
    @FXML
    Parent root1;

    @FXML
    String nameeeE;
    @FXML
    String artE;

    @FXML
    public ArrayList<String> nameE;
    @FXML
    public ArrayList<String> artEE;
    @FXML
    public ArrayList<String> posXX;
    @FXML
    public ArrayList<String> posYY;

    @FXML
    public void einlesen(String Name, String Art){
        nameeeE = Name;
        artE = Art;
    };

    @FXML
    private TableColumn<Tabelle, String > name;
    @FXML
    private TableColumn<Tabelle, String> art;
    @FXML
    private TableColumn<Tabelle, String> posX;
    @FXML
    private TableColumn<Tabelle, String> posY;
    @FXML
    private TableView<Tabelle> tabelleCustom;

    ObservableList<Tabelle> list = FXCollections.observableArrayList(
            //TODO eine variable anzahl an tabellen felder und nicht immer das erste ersetzen.
            //new Tabelle(artE,nameE,posXE, posYE)
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        Model m;
        m = Model.getInstanz();
        ArrayList<Punkt> customPunkte;
        customPunkte = m.getPunkte();

        if(customPunkte.size()>0){
            for (int i = 0, j = 0; i < customPunkte.size(); i++, j++){

                posXX.set(j, String.valueOf(customPunkte.get(j).getXPos()));
                posYY.set(j, String.valueOf(customPunkte.get(j).getYPos()));
                nameE.set(j, customPunkte.get(j).getName());
                if( customPunkte.get(j) instanceof Bahnhof){
                    artEE.set(j,"Bahnhof");
                }
                else if(customPunkte.get(j) instanceof Bushaltestelle){
                    artEE.set(j, "Bushaltestelle");
                }
                else if(customPunkte.get(j) instanceof Parkhaus){
                    artEE.set(j, "Parkhaus");
                }
            }
        }
            list.addAll(new Tabelle(artEE.get(0), nameE.get(0) ,posXX.get(0),posYY.get(0)));
            list.addAll(new Tabelle(artEE.get(1), nameE.get(1) ,posXX.get(1),posYY.get(1)));
            list.addAll(new Tabelle(artEE.get(2), nameE.get(2) ,posXX.get(2),posYY.get(2)));
            list.addAll(new Tabelle(artEE.get(3), nameE.get(3) ,posXX.get(3),posYY.get(3)));
            list.addAll(new Tabelle(artEE.get(4), nameE.get(4) ,posXX.get(4),posYY.get(4)));
            list.addAll(new Tabelle(artEE.get(5), nameE.get(5) ,posXX.get(5),posYY.get(5)));
            list.addAll(new Tabelle(artEE.get(6), nameE.get(6) ,posXX.get(6),posYY.get(6)));
            list.addAll(new Tabelle(artEE.get(7), nameE.get(7) ,posXX.get(7),posYY.get(7)));

            name.setCellValueFactory(new PropertyValueFactory<Tabelle, String>("name"));
            art.setCellValueFactory(new PropertyValueFactory<Tabelle, String>("art"));
            posX.setCellValueFactory(new PropertyValueFactory<Tabelle, String>("posX"));
            posY.setCellValueFactory(new PropertyValueFactory<Tabelle, String>("posY"));

            tabelleCustom.setItems(list);
    }


    @FXML
    public void loadData() {
        nameE = new ArrayList<>();
        for(int i = 0; i <= 8;i++){
            nameE.add(" ");
        }
        posXX = new ArrayList<>();
        for (int i = 0; i <= 8;i++){
            posXX.add(" ");
        }
        posYY = new ArrayList<>();
        for (int i = 0; i <= 8;i++){
            posYY.add(" ");
        }
        artEE = new ArrayList<>();
        for (int i = 0; i <= 8;i++){
            artEE.add(" ");
        }
    }

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

    @FXML
    public void apply(ActionEvent event) throws IOException {
        //Hier wird festgestellt in welcher Stage man sich grade befindet dafür guckt man wo das ActionEvent ausgeführt wurde
        //und dann wird diese Stage in die Methode beenden() übergeben
        Controller.beenden((Stage) ((Node) event.getSource()).getScene().getWindow());
    }
}