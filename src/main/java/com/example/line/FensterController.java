package com.example.line;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;


/*notizen Platz:
* modality
*               eingabe-view.fxml
* */
public class FensterController {


    public FensterController() throws IOException {
    }



    private void testtt(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LineApplication.class.getResource("eingabe-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

    }

}
