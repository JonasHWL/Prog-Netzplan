package com.example.line;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class LineController {

    List<LineE> lines = new ArrayList<>();

    @FXML
    private AnchorPane anchorPane;

    @FXML
    void Eline(ActionEvent event) {
        LineE newLine = new LineE(anchorPane);
        lines.add(newLine);
    }
}