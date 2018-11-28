/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Clases.Diagrama;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Cristobal Andres
 */
public class ModificarDiagramaController implements Initializable {
    FXMLDocumentController controlador1;
    Diagrama diagrama;
    @FXML private AnchorPane root;
    @FXML private Button cerrarVentana;
    @FXML private ComboBox comboBoxEntidad;
    @FXML private TextField modificarNombreEntidad;
    @FXML private TextField modificarNombreRelacion;
    @FXML private TextField modificarNombreAtributo;
    @FXML private ComboBox  comboBoxRelacion;
    @FXML private ComboBox comboBoxHerencia;

    @FXML private ComboBox tiposAtributos; //Se utiliza para modificar el tipo del atributo seleccionado
    @FXML private ComboBox tipoOrigenAtributo;//Contiene el tipo de objeto del origen del atributo(Entidad, relación o atributo).
    @FXML private ComboBox origenAtributo; //Contiene los elementos del tipo seleccionedo
    @FXML private ComboBox comboBoxAtributo; //Atributos contenidos en el elemento seleccionado;    
    @FXML private ListView<CheckBox> listaAtributosEntidad;
    @FXML private ListView<CheckBox> listaAtributosRelacion;
    @FXML private ListView<CheckBox> listaEntidadesRelacion;  
    @FXML private ListView<CheckBox> listaEntidadesHijasHerencia; 
    private int entidadSeleccionada;
    
    public void recibirParametros(FXMLDocumentController controlador,Diagrama diagrama){
        this.diagrama=diagrama;
        this.controlador1=controlador;
        for(int i = 0; i<diagrama.getEntidades().size();i++){
            comboBoxEntidad.getItems().add(diagrama.getEntidades().get(i).getNombre());
        }
        for(int i = 0; i<diagrama.getRelaciones().size();i++){
            comboBoxRelacion.getItems().add(diagrama.getRelaciones().get(i).getNombre());
        }        
        for(int i = 0; i<diagrama.getHerencias().size();i++){
            comboBoxHerencia.getItems().add(diagrama.getHerencias().get(i).getEntidadPadre().getNombre());
        }    
        tipoOrigenAtributo.getItems().addAll("Entidad","Relación");
        
    }  
    
    @FXML
    private void origenAtributo(){
        tiposAtributos.getItems().addAll("Generico","Clave","Clave Parcial","Multivaluado","Derivado","Compuesto");
        if(tipoOrigenAtributo.getValue().equals("Entidad")){
            for(int i = 0; i<diagrama.getEntidades().size();i++){
                origenAtributo.getItems().add(diagrama.getEntidades().get(i).getNombre());
            }            
            
        }
        if(tipoOrigenAtributo.getValue().equals("Relación")){
            for(int i = 0; i<diagrama.getRelaciones().size();i++){
                origenAtributo.getItems().add(diagrama.getRelaciones().get(i).getNombre());
            }             
        }
        if(tipoOrigenAtributo.getValue().equals("Atributo")){
            tiposAtributos.getItems().clear();
            tiposAtributos.getItems().addAll("Generico");
            for(int i = 0; i<diagrama.getRelaciones().size();i++){
                origenAtributo.getItems().add(diagrama.getRelaciones().get(i).getNombre());
            }              
            
            
        }
    }
    @FXML 
    private void atributosEncontrados(){
        comboBoxAtributo.getItems().clear();
        if(tipoOrigenAtributo.getValue().equals("Entidad")){
            for(int i = 0; i<diagrama.getEntidades().size();i++){
                if(origenAtributo.getValue().equals(diagrama.getEntidades().get(i).getNombre())){
                    for(int j = 0; j<diagrama.getEntidades().get(i).getAtributos().size();j++){
                        comboBoxAtributo.getItems().add(diagrama.getEntidades().get(i).getAtributos().get(j).getNombre());
                    }
                }
                
            }
        }
        if(tipoOrigenAtributo.getValue().equals("Relación")){
            for(int i = 0; i<diagrama.getRelaciones().size();i++){
                if(origenAtributo.getValue().equals(diagrama.getRelaciones().get(i).getNombre())){
                    for(int j = 0; j<diagrama.getRelaciones().get(i).getAtributos().size();j++){
                        comboBoxAtributo.getItems().add(diagrama.getRelaciones().get(i).getAtributos().get(j).getNombre());
                    }
                }
                
            }            
        }/*
        if(tipoOrigenAtributo.getValue().equals("Atributo")){
            for(int i = 0; i<diagrama.getRelaciones().size();i++){
                if(origenAtributo.equals(diagrama.getRelaciones().get(i))){
                    for(int j = 0; j<diagrama.getRelaciones().get(i).getAtributos().size();i++){
                        comboBoxAtributo.getItems().add(diagrama.getRelaciones().get(i).getAtributos().get(j).getNombre());
                    }
                }
                
            }            
        } */       
    }
    
    @FXML private void seccionRelacion(){
        listaAtributosRelacion.getItems().clear();
        for(int i = 0; i<diagrama.getRelaciones().size();i++){
            if(comboBoxRelacion.getValue().equals(diagrama.getRelaciones().get(i).getNombre())){
                for(int j = 0; j<diagrama.getRelaciones().get(i).getAtributos().size();j++){
                    CheckBox nuevo = new CheckBox(diagrama.getRelaciones().get(i).getAtributos().get(j).getNombre());
                    nuevo.setSelected(true);
                    listaAtributosRelacion.getItems().add(nuevo);
                }
            }
        }  
        listaEntidadesRelacion.getItems().clear();
        for(int i = 0; i<diagrama.getRelaciones().size();i++){
            if(comboBoxRelacion.getValue().equals(diagrama.getRelaciones().get(i).getNombre())){
                for(int j = 0; j<diagrama.getRelaciones().get(i).getEntidad().size();j++){
                    CheckBox nuevo = new CheckBox(diagrama.getRelaciones().get(i).getEntidad().get(j).getNombre());
                    nuevo.setSelected(true);
                    listaEntidadesRelacion.getItems().add(nuevo);
                }
            }
        }         
    }
    @FXML private void seccionHerencia(){
        listaEntidadesHijasHerencia.getItems().clear();
        for(int i = 0; i<diagrama.getHerencias().size();i++){
            if(comboBoxHerencia.getValue().equals(diagrama.getHerencias().get(i).getEntidadPadre().getNombre())){
                for(int j = 0; j<diagrama.getHerencias().get(i).getEntidadesHijas().size();j++){
                    CheckBox nuevo = new CheckBox(diagrama.getHerencias().get(i).getEntidadesHijas().get(j).getNombre());
                    nuevo.setSelected(true);
                    listaEntidadesHijasHerencia.getItems().add(nuevo);
                }
            }
        }          
        
        
    }
    
    
    @FXML private void seccionEntidad(){
        listaAtributosEntidad.getItems().clear();
        for(int i = 0; i<diagrama.getEntidades().size();i++){
            if(comboBoxEntidad.getValue().equals(diagrama.getEntidades().get(i).getNombre())){
                entidadSeleccionada = i;
                for(int j = 0; j<diagrama.getEntidades().get(i).getAtributos().size();j++){
                    CheckBox nuevo = new CheckBox(diagrama.getEntidades().get(i).getAtributos().get(j).getNombre());
                    nuevo.setSelected(true);
                    listaAtributosEntidad.getItems().add(nuevo);
                }
            }
        }
    }    
    
    
    
    
    
    
    /*
    @FXML
    public void guardarCambios(){
        controlador1.modificarDiagrama(diagrama);
        Stage stage = (Stage)cerrarVentana.getScene().getWindow();
        stage.close();
        

    }
    */
    @FXML
    public void Cancelar(){
        Stage stage = (Stage)cerrarVentana.getScene().getWindow();
        stage.close();        
    }  
    
    
    
    @FXML private void eliminarEntidad(){
        for(int i =0; i<diagrama.getEntidades().size();i++){
            if(comboBoxEntidad.getValue().equals(diagrama.getEntidades().get(i).getNombre())){
                diagrama.eliminarEntidad(diagrama.getEntidades().get(i));
            }
        }
    }
    @FXML private void eliminarRelacion(){
        for(int i =0; i<diagrama.getRelaciones().size();i++){
            if(comboBoxRelacion.getValue().equals(diagrama.getRelaciones().get(i).getNombre())){
                diagrama.eliminarRelacion(diagrama.getRelaciones().get(i));
            }
        }
    }    
    @FXML private void eliminarHerencia(){
        for(int i =0; i<diagrama.getHerencias().size();i++){
            if(comboBoxHerencia.getValue().equals(diagrama.getHerencias().get(i).getEntidadPadre().getNombre())){
                diagrama.eliminarHerencia(diagrama.getHerencias().get(i));
            }
        }
    }    
    
    
    
    
    @FXML private void modificarEntidad(){
        for(int i = 0; i<diagrama.getEntidades().get(entidadSeleccionada).getAtributos().size();i++){
            if(listaAtributosEntidad.getItems().get(i).isSelected()==false){
                diagrama.getEntidades().get(entidadSeleccionada).getAtributos().remove(i);
                
            }
            
        }
        diagrama.getEntidades().get(entidadSeleccionada).crearLineasunionAtributos();
        String nombre = (String)modificarNombreEntidad.getText();
        if(!"".equals(nombre)){
            for(int i = 0; i<diagrama.getRelaciones().size();i++){
                for(int j = 0; j<diagrama.getRelaciones().get(i).getEntidad().size();j++){
                    if(diagrama.getEntidades().get(entidadSeleccionada).getNombre().equals(diagrama.getRelaciones().get(i).getEntidad().get(j).getNombre())){
                        diagrama.getRelaciones().get(i).getEntidad().get(j).setNombre(nombre);
                    }
                }  
                
            }
            for(int i = 0; i<diagrama.getHerencias().size();i++){
                if(diagrama.getEntidades().get(entidadSeleccionada).getNombre().equals(diagrama.getHerencias().get(i).getEntidadPadre().getNombre())){
                    diagrama.getHerencias().get(i).getEntidadPadre().setNombre(nombre);
                }
                
                for(int j = 0; j<diagrama.getHerencias().get(i).getEntidadesHijas().size();j++){
                    if(diagrama.getEntidades().get(entidadSeleccionada).getNombre().equals(diagrama.getHerencias().get(i).getEntidadesHijas().get(j).getNombre())){
                        
                        diagrama.getHerencias().get(i).getEntidadesHijas().get(j).setNombre(nombre);
                        
                    }
                }  
                
            }            
            diagrama.getEntidades().get(entidadSeleccionada).setNombre(nombre);   
        }

        
        
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
