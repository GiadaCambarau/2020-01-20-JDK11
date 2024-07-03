package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Arco;
import it.polito.tdp.artsmia.model.Artists;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola artisti connessi \n");
    	List<Arco> archi =model.getConnessi();
    	for (Arco a : archi) {
    		txtResult.appendText(a +"\n");
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola percorso \n");
    	if (txtArtista.getText().compareTo("")!=0) {
    		try {
    			int id = Integer.parseInt(txtArtista.getText());
    			List<Artists> lista = model.trovaPercorso(id);
    			if (lista.size()!= 0) {
    				for (Artists l: lista) {
    					txtResult.appendText(l+"\n");
    				}
    				int peso = model.getPeso();
    				txtResult.appendText("Il peso migliore è: "+ peso);
    			}
    		}catch(NumberFormatException e ) {
    			return;
    		}
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Crea grafo \n");
    	if (boxRuolo.getValue() != null) {
    		String ruolo =boxRuolo.getValue();
    		model.creaGrafo(ruolo);
    		txtResult.appendText("Vertici: "+ model.getV()+ "\n");
    		txtResult.appendText("Archi: "+ model.getA()+"\n");
    		}
    }

    public void setModel(Model model) {
    	this.model = model;
    	boxRuolo.getItems().addAll(model.getRuoli());
    }

    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }
}
