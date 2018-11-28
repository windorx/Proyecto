/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.net.URL;

/**
 *
 * @author rjere
 */
public class SceneHandler {
    static public void CargarVista(Pane viewRoot, URL resource){
        Stage stage = (Stage) viewRoot.getScene().getWindow();
        Parent root=null;        
        try {
            root = FXMLLoader.load(resource);
        } catch (IOException ex) {
            System.out.println("No se puede cargar la vista "+resource.toString());
            Logger.getLogger(SceneHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);        
        stage.setScene(scene);//
    }
}
