package com.example.studypal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

//import java.text.BreakIterator;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label testoBenvenuto;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Hello from Elisa and Valentina :)");
    }



    @FXML
    protected void onCiaoButtonClick() { testoBenvenuto.setText("Ciao da Elisa e Valentina :)"); }
}