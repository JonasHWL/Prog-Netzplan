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

/**
 * In FensterController wird die Custom das Main Custom fenster geöffnet und verwaltet die Buttons Labels und Textfelder
 *
 *
 * @author Jonas Hanewinkel
 * @version 03.07.2024
 */
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
    public ArrayList<String> nameSpeicher;
    @FXML
    public ArrayList<String> typSpeicher;
    @FXML
    public ArrayList<String> posXSpeicher;
    @FXML
    public ArrayList<String> posYSpeicher;

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

    //erstellt eine ObservableList für die Tabelle
    ObservableList<Tabelle> list = FXCollections.observableArrayList(
    );

    /**
     * In dieser Methode wird die Tabelle initialisiert
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //loadDate() Läd bei dem aufrufen der Methode initialize alle Arraylisten damit man keine NullPointerException bekommt
        loadData();
        //hier werden die CustomPunkte aufgerufen
        Model m;
        m = Model.getInstanz();
        ArrayList<Punkt> benutzerPunkte;
        benutzerPunkte = m.getPunkte();

        //Mit dieser Abfrage wird geguckt ob die customPunkte einen inhalt haben
        if(benutzerPunkte.size()>0){
            //Mit dieser schleife werden die inhalte die eingegeben werden an ArrayListen übergeben.
            for (int i = 0, j = 0; i < benutzerPunkte.size(); i++, j++){

                posXSpeicher.set(j, String.valueOf(benutzerPunkte.get(j).getXPos()));
                posYSpeicher.set(j, String.valueOf(benutzerPunkte.get(j).getYPos()));
                nameSpeicher.set(j, benutzerPunkte.get(j).getName());
                //mit dieser abfrage wird geguckt ob der inhalt der Arraylist customPunkte inhalt der Bahnhof, Bushaltestelle oder Parkhaus
                //Class ist und für jeden der richtige Typ festgelegt
                if( benutzerPunkte.get(j) instanceof Bahnhof){
                    typSpeicher.set(j,"Bahnhof");
                }
                else if(benutzerPunkte.get(j) instanceof Bushaltestelle){
                    typSpeicher.set(j, "Bushaltestelle");
                }
                else if(benutzerPunkte.get(j) instanceof Parkhaus){
                    typSpeicher.set(j, "Parkhaus");
                }
            }
        }
            //TODO hier muss ich noch eine bessere möglichkeit finden eine Custom anzahl an zeile zu erstellen
            list.addAll(new Tabelle(typSpeicher.get(0), nameSpeicher.get(0) ,posXSpeicher.get(0),posYSpeicher.get(0)));
            list.addAll(new Tabelle(typSpeicher.get(1), nameSpeicher.get(1) ,posXSpeicher.get(1),posYSpeicher.get(1)));
            list.addAll(new Tabelle(typSpeicher.get(2), nameSpeicher.get(2) ,posXSpeicher.get(2),posYSpeicher.get(2)));
            list.addAll(new Tabelle(typSpeicher.get(3), nameSpeicher.get(3) ,posXSpeicher.get(3),posYSpeicher.get(3)));
            list.addAll(new Tabelle(typSpeicher.get(4), nameSpeicher.get(4) ,posXSpeicher.get(4),posYSpeicher.get(4)));
            list.addAll(new Tabelle(typSpeicher.get(5), nameSpeicher.get(5) ,posXSpeicher.get(5),posYSpeicher.get(5)));
            list.addAll(new Tabelle(typSpeicher.get(6), nameSpeicher.get(6) ,posXSpeicher.get(6),posYSpeicher.get(6)));
            list.addAll(new Tabelle(typSpeicher.get(7), nameSpeicher.get(7) ,posXSpeicher.get(7),posYSpeicher.get(7)));

            //setzen der spalten
            name.setCellValueFactory(new PropertyValueFactory<Tabelle, String>("name"));
            art.setCellValueFactory(new PropertyValueFactory<Tabelle, String>("art"));
            posX.setCellValueFactory(new PropertyValueFactory<Tabelle, String>("posX"));
            posY.setCellValueFactory(new PropertyValueFactory<Tabelle, String>("posY"));

            //erstellen der Tabelle
            tabelleCustom.setItems(list);
    }


    /**
     * Die loadData Methode ist dafür da vor der initialize Methode einmal alle Arraylisten zu beschreiben das kein nullpointer kommt
     */
    @FXML
    public void loadData() {
        // erstellen einer neuen ArrayList
        nameSpeicher = new ArrayList<>();
        //beschreiben der arraylist mit einem lehren String
        for(int i = 0; i <= 8;i++){
            nameSpeicher.add(" ");
        }
        // erstellen einer neuen ArrayList
        posXSpeicher = new ArrayList<>();
        //beschreiben der arraylist mit einem lehren String
        for (int i = 0; i <= 8;i++){
            posXSpeicher.add(" ");
        }
        // erstellen einer neuen ArrayList
        posYSpeicher = new ArrayList<>();
        //beschreiben der arraylist mit einem lehren String
        for (int i = 0; i <= 8;i++){
            posYSpeicher.add(" ");
        }
        // erstellen einer neuen ArrayList
        typSpeicher = new ArrayList<>();
        //beschreiben der arraylist mit einem lehren String
        for (int i = 0; i <= 8;i++){
            typSpeicher.add(" ");
        }
    }

    /**
     * Mit dieser Methode wird die stage aus einer anderen class geholt
     *
     * @param testS Stage von dem aufruf ort der Methode
     * @param root gibt die Parent wieder von der Methode auf der die Methode aufgerufen wird
      */
    @FXML
    public void uebergabe(Stage testS, Parent root){
        test1 = testS;
        root1 = root;
    }

    /**
     * Die abbrechen Methode ruft die beenden Methode im Controller auf und übergibt die stage
     * auf der sich der abbrechen button befindet
     *
     */
    @FXML
    public void abbrechen(ActionEvent event) throws IOException {
        //Hier wird festgestellt in welcher Stage man sich grade befindet dafür guckt man wo das ActionEvent ausgeführt wurde
        //und dann wird diese Stage in die Methode beenden() übergeben
        Controller.beenden((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    /**
     * öffnet die scene mit der überschrift Parkhaus um ein Custom Parkhaus zu erstellen
     *
     * @param event button click
     */
    @FXML
    public void openPark(ActionEvent event) {
        String kopfzeile = "Parkhaus";
        eingabe(kopfzeile);
    }

    /**
     * öffnet die scene mit der überschrift Bushaltestelle um eine Custom Bushaltestelle zu erstellen
     *
     * @param event button click
     */
    @FXML
    public void openBus(ActionEvent event) {
        String kopfzeile = "Bushaltestelle";
        eingabe(kopfzeile);
    }

    /**
     * öffnet die scene mit der überschrift Bahnhof um einen Custom Bahnhof zu erstellen
     *
     * @param event button click
     */
    @FXML
    public void openBahn(ActionEvent event) {
        String kopfzeile = "Bahnhof";
        eingabe(kopfzeile);
    }

    /**
     * Die eingabe Methode öffnet die Scene zu eingabe der Custom punkte
     *
     * @param kopfzeile überschrift/ Typ der eingabe Parkhaus, Bushaltestelle oder Bahnhof
     */
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

    /**
     * Beendet das fenster
     *
     */
    @FXML
    public void apply(ActionEvent event) throws IOException {
        //Hier wird festgestellt in welcher Stage man sich grade befindet dafür guckt man wo das ActionEvent ausgeführt wurde
        //und dann wird diese Stage in die Methode beenden() übergeben
        Controller.beenden((Stage) ((Node) event.getSource()).getScene().getWindow());
    }
}