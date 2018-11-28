/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Clases.Diagrama;
import Clases.Entidad;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Cristobal Andres
 */
public class HerenciaController implements Initializable {
    FXMLDocumentController controlador1;
    Diagrama diagrama;
    @FXML private AnchorPane root;
    @FXML private Button cerrarVentana;
    @FXML private RadioButton disyuncion;
    @FXML private RadioButton solapamiento;
    @FXML private ComboBox entidadPadre;
    @FXML ListView<CheckBox> entidadesHijas = new ListView<>();
    private String tipoHerencia;
    private int indicePadre;

    
    
    
    public void recibirParametros(FXMLDocumentController controlador,Diagrama diagrama){
        this.diagrama=diagrama;
        this.controlador1=controlador;
        
        
        for(int i = 0; i<diagrama.getEntidades().size();i++){
            entidadPadre.getItems().add(diagrama.getEntidades().get(i).getNombre());         
        }       
    }    
    
    
    @FXML private void eliminarPadreDeHijas(){
        entidadesHijas.getItems().clear();
        for(int i = 0; i<diagrama.getEntidades().size();i++){
            if(!diagrama.getEntidades().get(i).getNombre().equals((String)entidadPadre.getValue())){
                entidadesHijas.getItems().add(new CheckBox(diagrama.getEntidades().get(i).getNombre()));                 
            }
            else{
                indicePadre = i;
            }
           
        }
    }
    
    
    
    @FXML
    private void solapamiento(){
        disyuncion.setSelected(false);
        solapamiento.setSelected(true);
        tipoHerencia = "S";
    }
    @FXML
    private void disyuncion(){
        solapamiento.setSelected(false);
        disyuncion.setSelected(true);
        tipoHerencia = "D";
    }
    
    private ArrayList<Entidad> entidadesHijas(){

        ArrayList<Entidad> entidades = new ArrayList<>();
        for(int i = 0; i<entidadesHijas.getItems().size();i++){
            if(entidadesHijas.getItems().get(i).isSelected() ){
                for(int j=0; j<diagrama.getEntidades().size();j++){
                    if(entidadesHijas.getItems().get(i).getText().equals(diagrama.getEntidades().get(j).getNombre())){
                        entidades.add(diagrama.getEntidades().get(j));
                    }
                }
            }    
        }
        
        return entidades;
    }
    
    
    public void Aceptar(){
        controlador1.creacionHerencia(diagrama.getEntidades().get(indicePadre), entidadesHijas(), tipoHerencia);
        Stage stage = (Stage)cerrarVentana.getScene().getWindow();
        stage.close();
        

    }
    @FXML
    public void Cancelar(){
        Stage stage = (Stage)cerrarVentana.getScene().getWindow();
        stage.close();        
    }    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
}
