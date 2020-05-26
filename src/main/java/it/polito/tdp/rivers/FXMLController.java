/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.rivers;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.DatiFiume;
import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import it.polito.tdp.rivers.model.Simulator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxRiver"
    private ComboBox<River> boxRiver; // Value injected by FXMLLoader

    @FXML // fx:id="txtStartDate"
    private TextField txtStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtEndDate"
    private TextField txtEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtNumMeasurements"
    private TextField txtNumMeasurements; // Value injected by FXMLLoader

    @FXML // fx:id="txtFMed"
    private TextField txtFMed; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void aggiornaInterfaccia(ActionEvent event) {
    	aggiornaInterfaccia(boxRiver.getValue());
    }
    
    @FXML
    void avviaSimulazioni(ActionEvent event) {
    	if(boxRiver.getValue() == null) {
    		txtResult.setText("Scegli un fiume!");
    		return;
    	}
    	if(txtK.getText().equals("")) {
    		txtResult.setText("Scegli un 'k'!");
    		return;
    	}
    	int k;
    	try {
    		String kraw = txtK.getText();
    		k = Integer.parseInt(kraw.trim());
    	} catch (Exception e) {
			txtResult.setText("'k' inserito non valido!");
			return;
		}
    	txtResult.setText("ok "+k);
    	Object[] result  = model.simulazione(boxRiver.getValue(), k);
    	txtResult.setText("Giorni senza irrigazione minima: "+result[0]);
    	txtResult.appendText(String.format("\nOccupazione Media: %.2f%%", result[1]));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxRiver.getItems().addAll(model.getRivers());
    	/*
    	try {
    		boxRiver.setValue(boxRiver.getItems().get(0));
    		aggiornaInterfaccia(boxRiver.getItems().get(0));
    	}
    	catch (Exception e) {
			txtResult.setText("Nessun fiume trovato");
		}
		*/
    }
    
    public void aggiornaInterfaccia (River river) {
    	DatiFiume dati = model.getDatiFiume(river);
		txtStartDate.setText(dati.getUltimaMisurazione()+"");
		txtEndDate.setText(dati.getUltimaMisurazione()+"");
		txtFMed.setText(String.format("%.2f", dati.getFlussoMedio()));
		txtNumMeasurements.setText(dati.getTotmisurazioni()+"");
    }
}
