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
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;


public class FensterController implements Initializable {




    @FXML
    Stage test1;
    @FXML
    Parent root1;

    @FXML
    static String E;
    @FXML
    static String artE;
    @FXML
    static int posXE;
    @FXML
    static int posYE;

    int speicherD;


    @FXML
    public ArrayList<String> nameE;

    @FXML
    public ArrayList<String> speicher;

    @FXML
    public void einlesen(String Name, String Art, int PosX, int PosY,ArrayList<String>speicherUE, int speicherDUE){
        speicherD = speicherDUE;
        artE = Art;
        posXE = PosX;
        posYE = PosY;
        E = Name;
        loadSpeicher();
        speicher.addAll(speicherUE);

    };

    @FXML
    private TableColumn<Tabelle, String > name;
    @FXML
    private TableColumn<Tabelle, String> art;
    @FXML
    private TableColumn<Tabelle, Integer> posX;
    @FXML
    private TableColumn<Tabelle, Integer> posY;
    @FXML
    private TableView<Tabelle> tabelleCustom;

    @FXML
    public void loadData() {
        nameE = new ArrayList<>();
        if(speicher == null){
            loadSpeicher();
        }
        for(int i = 0; i <= 5;i++){
            nameE.addAll(speicher);
            //System.out.println(nameE.get(i));
        }

    }

    @FXML
    public void loadSpeicher() {
        speicher = new ArrayList<>();
        for(int i = 0; i <= 5;i++){
            speicher.add("L");
            speicherD = speicherD + 1;
            //System.out.println(speicher.get(i));
        }

    }

    ObservableList<Tabelle> list = FXCollections.observableArrayList(
            //TODO eine variable anzahl an tabellen felder und nicht immer das erste ersetzen.
            //new Tabelle(artE,nameE,posXE, posYE)
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        int zahl = 0;
        String leer = "test";
            //System.out.println("vor if "+ nameE.get(0));
            if(nameE.get(6) == leer){
                zahl = zahl + 7;
                System.out.println(zahl);
            }
            System.out.println(nameE);
            nameE.set(zahl , E);

            System.out.println(speicher);

            //System.out.println("nach if "+ nameE.get(1));
            speicher.addAll(nameE);
            System.out.println(speicher);
            System.out.println(speicher.get(6));
            list.addAll(new Tabelle(artE, nameE.get(0),posXE,posYE));
            list.addAll(new Tabelle(artE, nameE.get(1),posXE,posYE));
            list.addAll(new Tabelle(artE, nameE.get(2),posXE,posYE));
            name.setCellValueFactory(new PropertyValueFactory<Tabelle, String>("name"));
            art.setCellValueFactory(new PropertyValueFactory<Tabelle, String>("art"));
            posX.setCellValueFactory(new PropertyValueFactory<Tabelle, Integer>("posX"));
            posY.setCellValueFactory(new PropertyValueFactory<Tabelle, Integer>("posY"));

            tabelleCustom.setItems(list);
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
                controllerCustomPunkte.uebergabe(test1, root1, root2, kopfzeile, speicher, speicherD);
                test1.setScene(new Scene(root2));
            }catch (IOException e){

                System.out.println("Scene konnte nicht gewechselt werden.");

            }
    }
}