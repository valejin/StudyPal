package com.example.studypal;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

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
    private ListView<String> multiSelectListView;

    // Metodo per ottenere le selezioni dell'utente
    public void handleSelection() {
        ObservableList<String> selectedItems = multiSelectListView.getSelectionModel().getSelectedItems();
        // Fai qualcosa con le opzioni selezionate
        for (String item : selectedItems) {
            System.out.println("Opzione selezionata: " + item);
        }
    }









    @FXML
    protected void onCiaoButtonClick() { testoBenvenuto.setText("Ciao da Elisa e Valentina :)"); }
}

