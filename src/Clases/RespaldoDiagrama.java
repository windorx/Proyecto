/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;

/**
 *
 * @author Cristobal Andres
 */
public class RespaldoDiagrama {
    ArrayList<Diagrama> diagramas = new ArrayList<>();

    
    public void agregarDiagrama(Diagrama diagrama){
        Diagrama nuevo = new Diagrama();
        for(int i = 0; i<diagrama.getEntidades().size();i++){
            Entidad a =diagrama.getEntidades().get(i);
            Entidad nueva = new Entidad(a.isDebil(),a.getPosX(),a.getPosY(),a.getNombre(),a.getZoom());
            
            nuevo.getEntidades().add(nueva);
        }
        diagramas.add(nuevo);
        
        
    }
    
    public Diagrama retornarDiagrama(int i){
        return diagramas.get(i);
        
        

    }
    public void eliminar(int indice){
        while(indice<diagramas.size()){
            diagramas.remove(indice);
        }
        
        
    }

    
    public int tamano(){
        return diagramas.size();
    }
    
}
