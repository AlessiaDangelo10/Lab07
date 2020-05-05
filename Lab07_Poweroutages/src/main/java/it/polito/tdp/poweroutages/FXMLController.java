package it.polito.tdp.poweroutages;

import java.net.URL;


import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutages;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class FXMLController {

private Model model;
    @FXML
    private ResourceBundle resources;

    @FXML
    private ImageView txtImage;
    
    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Nerc> txtChoice;

    @FXML
    private TextField txtInsertYears;

    @FXML
    private TextField txtInsertHours;

   

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcola(ActionEvent event) {
    	txtResult.clear();
    	try {
			Nerc selectedNerc = txtChoice.getSelectionModel().getSelectedItem ();
			if (selectedNerc ==  null ) {
				txtResult . setText ( " Seleziona un NERC (identificatore area) " );
			return ;
			}

			int maxY =Integer.parseInt (txtInsertYears. getText ());
			int yearListSize =model.listaAnni().size();
			if (maxY <=  0  || maxY > yearListSize) {
				txtResult . setText ( " Seleziona un numero di anni nell'intervallo [1, "  + yearListSize +  " ] " );
				return ;
			}

			int maxH = Integer.parseInt (txtInsertHours . getText ());
			if (maxH <=  0 ) {
				txtResult . setText ( " Seleziona un numero di ore maggiore di 0 " );
				return ;
			}

			txtResult.setText (String.format ( " Calcolo dell'analisi del caso peggiore ... per% d ore e% d anni " , maxH, maxY));
			List<PowerOutages > worstCase = model.getWorstCase (maxY, maxH, selectedNerc);

			txtResult . clear();
			txtResult . appendText ( " Tot persone interessate: "  + model.sommaPersoneCoinvolte(worstCase)+  "\n " );
			txtResult . appendText ( " Tot ore di interruzione: "  + model.sommaOreDisservizio(worstCase)+  "\n " );

			for( PowerOutages ee : worstCase) {
				txtResult . appendText ( String . format ( " %d %s %s %d %d  " , ee.getAnno(),ee.getAvvio_interruzione(),
						ee.getFine_interruzione(), ee.getDurata_interruzione(), ee.getInteressato()));
				txtResult . appendText ( " \n " );
			}

		} catch ( NumberFormatException e) {
			txtResult . setText ( " Inserisci un numero valido di anni e di ore " );
		}

    	
    }
    @FXML
    void initialize() {
    	assert txtImage != null : "fx:id=\"txtImage\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtChoice != null : "fx:id=\"txtChoice\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtInsertYears != null : "fx:id=\"txtInsertYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtInsertHours != null : "fx:id=\"txtInsertHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
   txtResult . setStyle ( " -fx-font-family: monospace " );
       
    }
    public void setModel(Model model) {
		this.model=model;
		
			List<Nerc> nercList=model.getNercList();
			txtChoice.getItems().addAll(nercList);
		
	}
}
