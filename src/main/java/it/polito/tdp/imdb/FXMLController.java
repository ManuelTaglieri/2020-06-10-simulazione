/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Model;
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

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimili"
    private Button btnSimili; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimulazione"
    private Button btnSimulazione; // Value injected by FXMLLoader

    @FXML // fx:id="boxGenere"
    private ComboBox<String> boxGenere; // Value injected by FXMLLoader

    @FXML // fx:id="boxAttore"
    private ComboBox<Actor> boxAttore; // Value injected by FXMLLoader

    @FXML // fx:id="txtGiorni"
    private TextField txtGiorni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doAttoriSimili(ActionEvent event) {
    	
    	txtResult.clear();
    	if (this.boxAttore.getValue()==null) {
    		txtResult.setText("Selezionare un attore");
    		return;
    	}
    	List<Actor> risultato = this.model.getConnessi(this.boxAttore.getValue());
    	txtResult.appendText("Attori simili a: "+this.boxAttore.getValue()+"\n");
    	for (Actor a : risultato) {
    		txtResult.appendText(a.toString()+"\n");
    	}

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	if (this.boxGenere.getValue()==null) {
    		txtResult.setText("Selezionare un genere");
    		return;
    	}
    	this.model.creaGrafo(this.boxGenere.getValue());
    	txtResult.appendText("Grafo creato!\n");
    	txtResult.appendText("# Vertici: "+this.model.getNumVertici()+"\n");
    	txtResult.appendText("# Archi: "+this.model.getNumArchi()+"\n");
    	btnSimili.setDisable(false);
    	btnSimulazione.setDisable(false);
    	this.boxAttore.getItems().clear();
    	this.boxAttore.getItems().addAll(this.model.getVertici());
    }

    @FXML
    void doSimulazione(ActionEvent event) {
    	
    	txtResult.clear();
    	try {
    		
    		int giorni = Integer.parseInt(txtGiorni.getText());
    		
    		if (giorni<=0) {
    			txtResult.setText("Inserire un numero naturale per i giorni");
    			return;
    		}
    		
    		this.model.simula(giorni);
    		this.txtResult.appendText("Simulazione completata!\n");
    		this.txtResult.appendText("Lista degli intervistati:\n");
    		for (Actor a : this.model.getIntervistati()) {
    			this.txtResult.appendText(a.toString()+"\n");
    		}
    		this.txtResult.appendText("Giorni totali di pausa: "+this.model.getGiorniPausa()+"\n");
    		
    	} catch(NumberFormatException e) {
    		txtResult.setText("Errore: inserire un valore numerico intero per i giorni.");
    		return;
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimili != null : "fx:id=\"btnSimili\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimulazione != null : "fx:id=\"btnSimulazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGenere != null : "fx:id=\"boxGenere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAttore != null : "fx:id=\"boxAttore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGiorni != null : "fx:id=\"txtGiorni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxGenere.getItems().addAll(this.model.getGeneri());
    }
}
