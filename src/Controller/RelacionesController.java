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
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Cristobal Andres
 */
public class RelacionesController implements Initializable {
    Diagrama diagrama;
    FXMLDocumentController controlador1;
    @FXML private TextField textoRelacion;
   
    @FXML private Button cerrarVentana;
    @FXML ListView<CheckBox> entidadesDisponibles = new ListView<>();
    @FXML ComboBox TipoEntidades;
    
    
    public void recibirParametros(FXMLDocumentController controlador,Diagrama diagrama,ListView<CheckBox> entidadesDisponibles){
        this.diagrama=diagrama;
        this.controlador1=controlador;
        this.entidadesDisponibles.getItems().addAll(entidadesDisponibles.getItems());
        
        
        
        
    }
    
    
    
    
    private boolean validarNombreParaRelacion(){
        
        for(int i=0;i<diagrama.getRelaciones().size();i++){
            if(diagrama.getRelaciones().get(i).getNombre().equals(textoRelacion.getText())){
                return false;
            }
        }
        return true;
    }     
    private String nombreRelacionVacia(){
        boolean disponible=true;
        String nombre = "Relación 0";
        for(int i = 0; i<diagrama.getRelaciones().size();i++){
            nombre = "Relación "+(i+1);
            for(int j=0;j<diagrama.getRelaciones().size();j++){
                if(diagrama.getRelaciones().get(j).getNombre().equals(nombre)){
                    disponible=false;
                    break;
                }
            }
            if(disponible){
                return nombre;
            }
            disponible = true;
        }
        return nombre;
    }

    

    
    
    
    public void Aceptar(){
        if(validarNombreParaRelacion()){
            if(textoRelacion.getText().isEmpty()){
                controlador1.creacionRelaciones(nombreRelacionVacia(),this.diagrama,this.entidadesDisponibles);
            } 
            else{
                controlador1.creacionRelaciones(textoRelacion.getText(),this.diagrama,this.entidadesDisponibles);
            }
            Stage stage = (Stage)cerrarVentana.getScene().getWindow();
            stage.close();
        }
        else{
            mensaje("Nombre ingresado anteriormente. Por favor,\n"
                    + "     ingrese un nuevo nombre.");
            textoRelacion.clear();
        }
    }
    @FXML
    public void Cancelar(){
        Stage stage = (Stage)cerrarVentana.getScene().getWindow();
        stage.close();        
    }
    
    /**
     * Funcion que permite crear una ventana de alerta
     * @param texto: mensaje que se quiere escribir en la ventana. 
     */
    public void mensaje(String texto){
        JOptionPane.showMessageDialog(null, texto);
    }     
    
    
    
    
    
    
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    
    
}
