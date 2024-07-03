package it.polito.tdp.gosales;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.gosales.model.Methods;
import it.polito.tdp.gosales.model.Model;
import it.polito.tdp.gosales.model.PrdottiUscenti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnProdotti;

    @FXML
    private Button btnRicorsione;

    @FXML
    private ComboBox<Integer> cmbAnno;

    @FXML
    private ComboBox<Methods> cmbMetodo;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField txtS;

    @FXML
    void doCalcolaProdotti(ActionEvent event) {
    	List<PrdottiUscenti> result = model.getTop();
    	txtResult.appendText("\n\nProdotti redditizzi: \n");
    	for (int i=0; i<result.size(); i++) {
    		if (i<5) {
    			txtResult.appendText(result.get(i)+"\n");
    		}
    	}
    }
    

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	if (cmbAnno.getValue() != null && cmbMetodo.getValue()!= null && txtS.getText().compareTo("")!= 0 ) {
    		int anno = cmbAnno.getValue();
    		Methods m = cmbMetodo.getValue();
    		String input = txtS.getText();
    		try {
    			double s = Double.parseDouble(input);
    			model.creaGrafo(anno, m, s);
    			txtResult.appendText("Vertici: "+model.getV()+ "\nArchi: "+model.getA());
    		}catch (NumberFormatException e) {
				// TODO: handle exception
			}
    	}
    }
    

    @FXML
    void doRicorsione(ActionEvent event) {

    }

    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnProdotti != null : "fx:id=\"btnProdotti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRicorsione != null : "fx:id=\"btnRicorsione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMetodo != null : "fx:id=\"cmbMetodo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtS != null : "fx:id=\"txtS\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    
    public void setModel(Model model) {
    	this.model = model;  
    	cmbAnno.getItems().add(2015);
    	cmbAnno.getItems().add(2016);
    	cmbAnno.getItems().add(2017);
    	cmbAnno.getItems().add(2018);
    	cmbMetodo.getItems().addAll(model.getMetodi());
    }
    

}
