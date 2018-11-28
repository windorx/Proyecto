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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Cristobal Andres
 */
public class EntidadController implements Initializable {
    
    Diagrama diagrama;
    FXMLDocumentController controlador1;
    @FXML private AnchorPane root;
    @FXML private TextField textoEntidad;
    @FXML private CheckBox entidadDebil;
    @FXML private Button cerrarVentana;
    
    public void recibirParametros(FXMLDocumentController stage, Diagrama diagrama){
        this.diagrama=diagrama;
        controlador1=stage;    
    }

//valida nombre de la entidad cuando no se le pone un nombre por defecto
    private boolean validarNombreParaEntidad(){
        
        for(int i=0;i<diagrama.getEntidades().size();i++){
            if(diagrama.getEntidades().get(i).getNombre().equals(textoEntidad.getText())){
                return false;
            }
        }
        return true;
    }   
    
//valida los nombres por defecto para las entidades
    private String nombreEntidadVacia(){
        boolean disponible=true;
        String nombre = "Entidad 0";
        for(int i = 0; i<diagrama.getEntidades().size();i++){
            nombre = "Entidad "+(i+1);
            for(int j=0;j<diagrama.getEntidades().size();j++){
                if(diagrama.getEntidades().get(j).getNombre().equals(nombre)){
                    disponible=false;
                    break;
                }
            }
            if(disponible){
                return nombre;
            }
        }
        return nombre;
    }

    
    public void Aceptar(){
        if(validarNombreParaEntidad()){
            if(textoEntidad.getText().isEmpty()){
                controlador1.obtenerEntidad(nombreEntidadVacia(), entidadDebil.isSelected(),this.diagrama);
            } 
            else{
                controlador1.obtenerEntidad(textoEntidad.getText(), entidadDebil.isSelected(),this.diagrama);
            }
            Stage stage = (Stage)cerrarVentana.getScene().getWindow();
            stage.close();
        }
        else{
            mensaje("Nombre ingresado anteriormente. Por favor,\n"
                    + "     ingrese un nuevo nombre.");
            textoEntidad.clear();
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
        // TODO
    }    
    
    
}
