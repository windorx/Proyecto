/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Los olvidados
 */
public class Herencia {
    private Entidad entidadPadre;
    private ArrayList<Entidad> EntidadesHijas = new ArrayList<>();
    private ArrayList<Line> unionesEntidades = new ArrayList<>();
    private ArrayList<CubicCurve> figuraHerencia = new ArrayList<>();
    private String tipoHerencia;
    private Text textTipoHerencia;
    private int posX;
    private int posY;
    private ArrayList<Line> puntosControl = new ArrayList<>(); //puntos de control de la figura.
    private ArrayList<Integer> posicionesX = new ArrayList<>(); //posiciones x de la figura.
    private ArrayList<Integer> posicionesY = new ArrayList<>(); //posiciones y de la figura.
    
    
    public Herencia(Entidad entidadPadre, ArrayList<Entidad> hijas,String tipoHerencia,int posX,int posY) {
        this.posX=posX;
        this.posY=posY;
        this.entidadPadre=entidadPadre;
        this.EntidadesHijas=hijas;
        this.tipoHerencia=tipoHerencia;
        this.puntosControl = new ArrayList<>();
    }

    public ArrayList<Line> getPuntosControl() {
        return puntosControl;
    }

    public void setPuntosControl(ArrayList<Line> puntosControl) {
        this.puntosControl = puntosControl;
    }
    
    public void crearPuntosDeControl(){
        for(int i=0; i<posicionesX.size();i++){
            Line linea = new Line(posicionesX.get(i), posicionesY.get(i), posicionesX.get(i), posicionesY.get(i));
            linea.setStrokeWidth(7);
            puntosControl.add(linea);
        }
        
    }  
    private void crearFigura(){
        posicionesX.add(posX);
        posicionesY.add(posY);
                
        posicionesY.add(posY+20);

        posicionesX.add(posX+30);
        posicionesY.add(posY+20);
        
        posicionesX.add(posX+30);
        
        
        posicionesY.add(posY-20);
        
        figuraHerencia.clear();
        CubicCurve relleno1 = new CubicCurve(posX, posY, posX,posY+20,posX+30,posY+20,posX+30,posY);
        relleno1.setFill(Color.WHITE);

        CubicCurve relleno2 = new CubicCurve(posX, posY, posX,posY-20,posX+30,posY-20,posX+30,posY);
        relleno2.setFill(Color.WHITE);

        CubicCurve curva = new CubicCurve(posX, posY, posX,posY+20,posX+30,posY+20,posX+30,posY);
        curva.setStroke(Color.BLACK);
        curva.setStrokeWidth(2);
        curva.setFill(null);   
        CubicCurve curva2 = new CubicCurve(posX, posY, posX,posY-20,posX+30,posY-20,posX+30,posY);
        curva2.setStroke(Color.BLACK);
        curva2.setStrokeWidth(2);
        curva2.setFill(null);
        figuraHerencia.add(relleno1);
        figuraHerencia.add(relleno2);
        figuraHerencia.add(curva);
        figuraHerencia.add(curva2);
        
         
        textTipoHerencia=new Text(posX,posY+5,tipoHerencia);
        textTipoHerencia.setWrappingWidth(30);
        textTipoHerencia.setStrokeWidth(2);
       textTipoHerencia.setTextAlignment(TextAlignment.CENTER);          
            crearPuntosDeControl();

        
    }
    public ArrayList<Node> dibujoHerencia(){
        
        
        
        ArrayList<Node> retornar = new ArrayList<>();

        retornar.addAll(this.unionesEntidades);
        retornar.addAll(this.figuraHerencia);
        retornar.add(textTipoHerencia);
        
        return retornar;

        
    }
    public ArrayList<Integer> posicionesXFigura(){
        ArrayList<Integer> x = new ArrayList<>();
        x.add(posX);
        x.add(posX+30);
        x.add(posX+15);
        x.add(posX+15);
        return x;
    }
    public ArrayList<Integer> posicionesYFigura(){
        ArrayList<Integer> y = new ArrayList<>();
        y.add(posY);
        y.add(posY);
        y.add(posY-20);
        y.add(posY+20);
        return y;
    }
    public Entidad getEntidadPadre() {
        return entidadPadre;
    }

    public ArrayList<Entidad> getEntidadesHijas() {
        return EntidadesHijas;
    }

    public ArrayList<CubicCurve> getFiguraHerencia() {
        return figuraHerencia;
    }


    

    public Text getTextTipoHerencia() {
        return textTipoHerencia;
    }

    public ArrayList<Line> getUnionesEntidades() {
        return unionesEntidades;
    }

    public String getTipoHerencia() {
        return tipoHerencia;
    }

    
    private void semicircunferencia(int finalX,int finalY,int i){
        
        int pX=posX+15;
        int pmediox=pX+(-pX+finalX)/2-15;
        int pmedioy =posY+(-posY+finalY)/2;      
        
        CubicCurve curva = new CubicCurve(pmediox,pmedioy+20,pmediox,pmedioy+20,pmediox+30,pmedioy+20,pmediox+30,pmedioy-10);
        curva.setStroke(Color.BLACK);
        curva.setStrokeWidth(2);
        curva.setFill(null);
        curva.setRotate(50);

        figuraHerencia.add(curva);

                
    }
    public ArrayList<Line> crearHerencia(){
        unionesEntidades.clear();

        /*int p = puntoCercano(posX,posY,entidadPadre);
        Line linea = new Line(posX+15,posY,entidadPadre.getFigura().getPosicionesX().get(p),
                                  entidadPadre.getFigura().getPosicionesY().get(p));
        unionesEntidades.add(linea);
        */
        crearFigura();
        EntidadesHijas.add(entidadPadre);
        
        
        
        for(int i=0;i<EntidadesHijas.size();i++){
                int indice= puntoCercano(posY,posX,EntidadesHijas.get(i));
                Line nueva = new Line(posX+15,posY,EntidadesHijas.get(i).getFigura().getPosicionesX().get(indice),
                                      EntidadesHijas.get(i).getFigura().getPosicionesY().get(indice));

                if(i<EntidadesHijas.size()-1){
                    semicircunferencia(EntidadesHijas.get(i).getFigura().getPosicionesX().get(indice),EntidadesHijas.get(i).getFigura().getPosicionesY().get(indice),i);            
                    
                }
                unionesEntidades.add(nueva);

        }

        EntidadesHijas.remove(EntidadesHijas.size()-1);
        return unionesEntidades;
    } 

    public void setEntidadPadre(Entidad entidadPadre) {
        this.entidadPadre = entidadPadre;
    }
    
    private int puntoCercano(int posY,int posX,Entidad entidad){
        int x1=posX+15;
        int y1 = posY;
        ArrayList<Integer> distancias = new ArrayList<>();
        ArrayList<Integer> orden = new ArrayList<>();
        int x2,y2;
        for(int i=0; i< entidad.getFigura().getPosicionesX().size();i++){
            orden.add(i);
            x2 = entidad.getFigura().getPosicionesX().get(i);
            y2 = entidad.getFigura().getPosicionesY().get(i);
            distancias.add((int)Math.sqrt(((Math.pow(x1-x2, 2)+Math.pow(y2-y1, 2)))));
        }
                
        for(int x = 0; x<distancias.size(); x++){
            for(int i = 0; i<distancias.size()-1-x;i++){
                if (distancias.get(i+1) < distancias.get(i)){
                    int auxIndice = orden.get(i);
                    orden.set(i, orden.get(i+1));
                    orden.set(i+1, auxIndice);
                    
                    int aux= distancias.get(i);
                    distancias.set(i, distancias.get(i+1));
                    distancias.set(i+1, aux);
                    
                }


            }
        }
        return orden.get(0);
    }  
    
    
    public void eliminarEntidadHija(int i){
        this.EntidadesHijas.remove(i);
        this.crearHerencia();
        
    }
    
    
    
    
    
}
