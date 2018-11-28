/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Cristobal Andres
 */
public class FiguraAtributos {
    private ArrayList<CubicCurve> lineas = new ArrayList<>(); // guarda las lineas creadas.
    
    private ArrayList<Integer> posicionesX = new ArrayList<>(); //posiciones x de la figura.
    private ArrayList<Integer> posicionesY = new ArrayList<>(); //posiciones y de la figura.
    private ArrayList<Line> puntosControl = new ArrayList<>(); //puntos de control de la figura.
    private int puntoMedioX;

    private Text textoFigura;  
    private int posX;
    private int posY;
    private String tipoAtributo;
    private int zoom;
    public FiguraAtributos(String tipoAtributo,String nombre, int posX, int posY,int zoom) {

        this.posX = posX;
        this.posY = posY;
        this.tipoAtributo=tipoAtributo;
        this.zoom=zoom;
        crearAtributo(nombre);
    }



    
    private void crearAtributo(String nombre){
        int agrandar = nombre.length()*5;
            
        int sumaH=(100+agrandar+zoom)/20;
        int sumaV = (100+agrandar/3+zoom)/4;
            
        posicionesX.add(posX);
        posicionesY.add(posY);
                
        posicionesX.add(posX+100+agrandar+zoom);
        posicionesY.add(posY);

        posicionesX.add(posX+(100+agrandar+zoom)/2);
        posicionesY.add(posY-sumaV);
        
        posicionesX.add(posX+(100+agrandar+zoom)/2);
        posicionesY.add(posY+sumaV);
        
        posicionesX.add(posX+sumaH);
        posicionesY.add(posY-sumaV);
        posicionesX.add(posX+100+agrandar+zoom-sumaH);
        posicionesY.add(posY-sumaV);
        posicionesX.add(posX+sumaH);
        posicionesY.add(posY+sumaV);
        posicionesX.add(posX+100+agrandar+zoom-sumaH);
        posicionesY.add(posY+sumaV);        
        

            

                        
            CubicCurve relleno1 = new CubicCurve(posX, posY, posX+sumaH,posY-sumaV,posX+100+agrandar+zoom-sumaH,posY-sumaV,posX+agrandar+100+zoom,posY);
            relleno1.setFill(Color.WHITE);

            CubicCurve relleno2 = new CubicCurve(posX, posY, posX+sumaH,posY+sumaV,posX+agrandar+100+zoom-sumaH,posY+sumaV,posX+agrandar+100+zoom,posY);
            relleno2.setFill(Color.WHITE);


            CubicCurve curva = new CubicCurve(posX, posY, posX+sumaH,posY-sumaV,posX+agrandar+100+zoom-sumaH,posY-sumaV,posX+agrandar+100+zoom,posY);
            curva.setStroke(Color.BLACK);
            curva.setStrokeWidth(2);
            curva.setFill(null);


            CubicCurve curva2 = new CubicCurve(posX, posY, posX+sumaH,posY+sumaV,posX+agrandar+100+zoom-sumaH,posY+sumaV,posX+agrandar+100+zoom,posY);
            curva2.setStroke(Color.BLACK);
            curva2.setStrokeWidth(2);
            curva2.setFill(null);



            textoFigura = new Text(posX,posY,nombre);
            textoFigura.setWrappingWidth(100+agrandar+zoom);
            textoFigura.setTextAlignment(TextAlignment.CENTER);        
            textoFigura.setFont(new Font(14+(zoom/10)));        
            puntoMedioX=posX+(100+agrandar+zoom)/2;
            crearPuntosDeControl();

        if(tipoAtributo.equals("Generico")||tipoAtributo.equals("Compuesto")){ //Atributo generico
            lineas.add(relleno1);            
            lineas.add(relleno2);
            lineas.add(curva); 
            lineas.add(curva2);                        
        }


        if(tipoAtributo.equals("Clave")){//calve
            lineas.add(relleno1);
            lineas.add(relleno2);
            lineas.add(curva);      
            lineas.add(curva2);
            textoFigura.underlineProperty().set(true);
            
        }
        if(tipoAtributo.equals("Clave Parcial")){//calve parcial: subrayado punteado
            lineas.add(relleno1);
            lineas.add(relleno2);
            lineas.add(curva);      
            lineas.add(curva2);
            
            
            CubicCurve liena1 = new CubicCurve(posX, posY, posX+sumaH,posY,posX+agrandar+100+zoom-sumaH,posY,posX+agrandar+100+zoom,posY);
            liena1.setStroke(Color.BLACK);
            liena1.setStrokeWidth(1);
            liena1.setFill(null);
            liena1.getStrokeDashArray().addAll(6d);

            lineas.add(liena1);                    

            
        }
        if(tipoAtributo.equals("Multivaluado")){//Multivaluado


            lineas.add(relleno1);

            lineas.add(relleno2);            
            lineas.add(curva);
            lineas.add(curva2);        

            CubicCurve doble1 = new CubicCurve(posX-sumaH, posY, posX,posY-(sumaV+sumaV/5),posX+agrandar+100+zoom,posY-(sumaV+sumaV/5),posX+agrandar+100+zoom+sumaH,posY);
            doble1.setStroke(Color.BLACK);
            doble1.setStrokeWidth(2);
            doble1.setFill(null);
            lineas.add(doble1);
            CubicCurve doble2 = new CubicCurve(posX-sumaH, posY, posX,posY+(sumaV+sumaV/5),posX+agrandar+100+zoom,posY+(sumaV+sumaV/5),posX+agrandar+100+zoom+sumaH,posY);
            doble2.setStroke(Color.BLACK);
            doble2.setStrokeWidth(2);
            doble2.setFill(null);
            lineas.add(doble2);            
           
                        
        }

        if(tipoAtributo.equals("Derivado")){//derivado
       
            lineas.add(relleno1);
            lineas.add(relleno2);
            

          
            curva.getStrokeDashArray().addAll(6d);
            lineas.add(curva);
            curva2.getStrokeDashArray().addAll(6d);
            lineas.add(curva2);
        }        

                
    }
    private void crearPuntosDeControl(){
        for(int i=0; i<posicionesX.size();i++){
            Line linea = new Line(posicionesX.get(i), posicionesY.get(i), posicionesX.get(i), posicionesY.get(i));
            linea.setStrokeWidth(7);
            puntosControl.add(linea);
        }
        
    }   

    public ArrayList<CubicCurve> getLineas() {
        return lineas;
    }

    public ArrayList<Integer> getPosicionesX() {
        return posicionesX;
    }

    public ArrayList<Integer> getPosicionesY() {
        return posicionesY;
    }

    public ArrayList<Line> getPuntosControl() {
        return puntosControl;
    }

    public Text getTextoFigura() {
        return textoFigura;
    }
    public int puntoMedioXfigura(){
        
        return puntoMedioX;
    }
    
    
}
