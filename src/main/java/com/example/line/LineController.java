package com.example.line;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LineController {

    List<LineE> lines = new ArrayList<>();

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
}