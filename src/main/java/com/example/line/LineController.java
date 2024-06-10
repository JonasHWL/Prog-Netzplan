package com.example.line;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;

public class LineController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    void Eline(ActionEvent event) {

        Model model = new Model(anchorPane, 9223372036854775807L, 5, 0, 0);
    }

    @FXML
    public Button button;

    @FXML
    void Test(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("eingabe-view.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Eingabe");
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch (Exception e) {
            System.out.println("Fenster konnte nicht geladen werden");
        }
    }

    @FXML
    private TextField textField1;
    @FXML
    private TextField textField2;
    @FXML
    private TextField textField3;

    int p1;
    int p2;
    String n;

    @FXML
    void Eingabe(ActionEvent event) {

        p1 = Integer.parseInt(textField1.getText());
        p2 = Integer.parseInt(textField2.getText());
        n = textField3.getText();

        Uebergabe ue = new Uebergabe(p1,p2,n);
    }


}