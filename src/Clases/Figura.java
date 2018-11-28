package Clases;

import java.util.ArrayList;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Los olvidados.
 */
public class Figura {

    private ArrayList<Line> lineas = new ArrayList<>(); // guarda las lineas creadas.
    
    private ArrayList<Integer> posicionesX = new ArrayList<>(); //posiciones x de la figura.
    private ArrayList<Integer> posicionesY = new ArrayList<>(); //posiciones y de la figura.
    private ArrayList<Line> puntosControl = new ArrayList<>(); //puntos de control de la figura.

    private int puntoMedioX,puntoMedioY;
    private Text textoFigura;
    
    private ArrayList<Integer> puntosOcupados= new ArrayList<>();
    public void crearFigura(boolean debil,int tipo,String nombre, int posX,int posY,int zoom){

        if (tipo == -1){//Rectangulo
            int agrandar=nombre.length()*5;
            posicionesX.add(posX);
            posicionesY.add(posY);
            posicionesX.add(posX+80+agrandar+zoom);      
            posicionesY.add(posY);
            posicionesX.add(posX);
            posicionesY.add(posY+40+(zoom/5));
            posicionesX.add(posX+80+agrandar+zoom);
            posicionesY.add(posY+40+(zoom/5));
            
            puntoMedioX = posX+(80+agrandar)/2;
            puntoMedioY = posY+20;

            

           
            lineas.add(new Line(posX, posY, posX+80+agrandar+zoom, posY));
            lineas.add(new Line(posX+80+agrandar+zoom, posY, posX+80+agrandar+zoom, posY+40+(zoom/5)));
            lineas.add(new Line(posX+80+agrandar+zoom, posY+40+(zoom/5), posX, posY+40+(zoom/5)));
            lineas.add(new Line(posX, posY+40+(zoom/5), posX, posY)); 
            
            if(debil){
                lineas.add(new Line(posX+3, posY+3, posX+80+agrandar+zoom-3, posY+3));
                lineas.add(new Line(posX+80+agrandar+zoom-3, posY+3, posX+80+agrandar+zoom-3, posY+40+(zoom/5)-3));
                lineas.add(new Line(posX+80+agrandar+zoom-3, posY+40+(zoom/5)-3, posX+3, posY+40+(zoom/5)-3));
                lineas.add(new Line(posX+3, posY+40+(zoom/5)-3, posX+3, posY+3)); 
                            
            }

            crearPuntosDeControl();
            
            textoFigura = new Text(posX,puntoMedioY+(zoom/10),nombre);
            textoFigura.setWrappingWidth(80+agrandar+zoom);
            textoFigura.setTextAlignment(TextAlignment.CENTER);            
            textoFigura.setFont(new Font(14+(zoom/10)));
            
            
            
            
            

        }
        if(tipo == 2 || tipo == 1 || tipo==4){//Rombo
            
            int agrandar=nombre.length()*4;
            puntoMedioX = posX+(50+agrandar+zoom)/2;
            puntoMedioY = posY;             
            int sumarY = (50+agrandar+zoom)/2;
           
            posicionesX.add(posX);
            posicionesY.add(posY);
            posicionesX.add(posX+50+agrandar+zoom);      
            posicionesY.add(posY);
            posicionesX.add(puntoMedioX);
            posicionesY.add(posY-sumarY);
            posicionesX.add(puntoMedioX);
            posicionesY.add(posY+sumarY);


            lineas.add(new Line(posX, posY, puntoMedioX, posY-sumarY));
            lineas.add(new Line(puntoMedioX, posY-sumarY, posX+50+agrandar+zoom, posY));
            lineas.add(new Line(posX+50+agrandar+zoom, posY, puntoMedioX, posY+sumarY));
            lineas.add(new Line(puntoMedioX, posY+sumarY, posX, posY));    
            
            
            if(debil==true){
                lineas.add(new Line(posX+3, posY, puntoMedioX, posY-sumarY+3));
                lineas.add(new Line(puntoMedioX, posY-sumarY+3, posX+50+agrandar+zoom-3, posY));
                lineas.add(new Line(posX+50+agrandar+zoom-3, posY, puntoMedioX, posY+sumarY-3));
                lineas.add(new Line(puntoMedioX, posY+sumarY-3, posX+3, posY));  
            }
            
            crearPuntosDeControl();
            textoFigura = new Text(posX,puntoMedioY,nombre);
            textoFigura.setWrappingWidth(50+agrandar+zoom);
            textoFigura.setTextAlignment(TextAlignment.CENTER);  
            textoFigura.setFont(new Font(14+(zoom/10))); 
           
        }
        if (tipo == 3){ //Triangulo
            
            int agrandar=nombre.length()*8;
            int sumarXY = (50+agrandar+zoom);
            
            puntoMedioX= posX + (50+agrandar+zoom)/2;
            puntoMedioY = posY-(sumarXY/3);
            
            posicionesX.add(posX);
            posicionesY.add(posY);
            posicionesX.add(posX+sumarXY);      
            posicionesY.add(posY);
            posicionesX.add(puntoMedioX);
            posicionesY.add(posY-sumarXY);
            lineas.add(new Line(posX, posY, posX+sumarXY, posY));
            lineas.add(new Line(posX+sumarXY, posY, puntoMedioX, posY-sumarXY));
            lineas.add(new Line(puntoMedioX, posY-sumarXY, posX, posY));
            
            
            crearPuntosDeControl();
            
            textoFigura = new Text(posX,puntoMedioY,nombre);
            textoFigura.setWrappingWidth(sumarXY);
            textoFigura.setTextAlignment(TextAlignment.CENTER);  
            textoFigura.setFont(new Font(14+(zoom/10)));             
            
        }
 
        if ( tipo == 5){//pentagono
            int agrandar=nombre.length()*4;
            
            puntoMedioX=posX+(100+agrandar+zoom)/2;
            
            posicionesX.add(posX);
            posicionesY.add(posY);
            posicionesX.add(posX+100+agrandar+zoom);      
            posicionesY.add(posY);
            posicionesX.add(puntoMedioX);
            posicionesY.add(posY-(100+agrandar+zoom)/4);
            posicionesX.add(puntoMedioX-(100+agrandar+zoom)/4);
            posicionesY.add(posY+(100+agrandar+zoom)/2);
            posicionesX.add(puntoMedioX+(100+agrandar+zoom)/4);
            posicionesY.add(posY+(100+agrandar+zoom)/2);    
            
            lineas.add(new Line(posX, posY, puntoMedioX, posY-(100+agrandar+zoom)/4));
            lineas.add(new Line(puntoMedioX, posY-(100+agrandar+zoom)/4, posX+100+agrandar, posY));
            lineas.add(new Line(posX+100+agrandar+zoom, posY, puntoMedioX+(100+agrandar+zoom)/4, posY+(100+agrandar+zoom)/2));
            lineas.add(new Line(puntoMedioX+(100+agrandar+zoom)/4, posY+(100+agrandar+zoom)/2, puntoMedioX-(100+agrandar+zoom)/4, posY+(100+agrandar+zoom)/2));
            lineas.add(new Line(puntoMedioX-(100+agrandar+zoom)/4, posY+(100+agrandar+zoom)/2, posX, posY)); 
            
            
            crearPuntosDeControl();
            
            puntoMedioY = posY+(50+agrandar+zoom)/2;
            textoFigura = new Text(posX+(100+agrandar+zoom)/9,posY+(100+agrandar+zoom)/8,nombre);
            textoFigura.setWrappingWidth(100+agrandar-((100+agrandar+zoom)/5));
            textoFigura.setTextAlignment(TextAlignment.CENTER); 
            textoFigura.setFont(new Font(14+(zoom/10)));             
        }
        if ( tipo == 6){ // Hexagono
            int agrandar=nombre.length()*4;            
            puntoMedioX=posX+(100+agrandar+zoom)/2;
            
            posicionesX.add(posX);//p1
            posicionesY.add(posY);
            posicionesX.add(posX+100+agrandar+zoom);//p2
            posicionesY.add(posY);            
            posicionesX.add(puntoMedioX-(100+agrandar+zoom)/4); //p3     
            posicionesY.add(posY-(100+agrandar+zoom)/4);
            posicionesX.add(puntoMedioX+(100+agrandar+zoom)/4);//p4
            posicionesY.add(posY+(100+agrandar+zoom)/4);   
            posicionesX.add(puntoMedioX+(100+agrandar+zoom)/4);
            posicionesY.add(posY-(100+agrandar+zoom)/4);

 
            posicionesX.add(puntoMedioX-(100+agrandar+zoom)/4);
            posicionesY.add(posY+(100+agrandar+zoom)/4);
            
            lineas.add(new Line(posX, posY, puntoMedioX-(100+agrandar+zoom)/4, posY-(100+agrandar+zoom)/4));
            lineas.add(new Line(puntoMedioX-(100+agrandar+zoom)/4, posY-(100+agrandar+zoom)/4, puntoMedioX+(100+agrandar+zoom)/4, posY-(100+agrandar+zoom)/4));
            lineas.add(new Line(puntoMedioX+(100+agrandar+zoom)/4, posY-(100+agrandar+zoom)/4, posX+100+agrandar+zoom, posY));
            lineas.add(new Line(posX+100+agrandar+zoom, posY, puntoMedioX+(100+agrandar+zoom)/4, posY+(100+agrandar+zoom)/4));
            lineas.add(new Line(puntoMedioX+(100+agrandar+zoom)/4, posY+(100+agrandar+zoom)/4, puntoMedioX-(100+agrandar+zoom)/4, posY+(100+agrandar+zoom)/4)); 
            lineas.add(new Line(puntoMedioX-(100+agrandar+zoom)/4, posY+(100+agrandar+zoom)/4, posX, posY)); 

            crearPuntosDeControl();
            puntoMedioY = posY;
            textoFigura = new Text(posX,posY,nombre);
            textoFigura.setWrappingWidth(100+agrandar+zoom);
            textoFigura.setTextAlignment(TextAlignment.CENTER); 
            textoFigura.setFont(new Font(14+(zoom/10)));             
        }

        
        
        
    }
    private void crearPuntosDeControl(){
        for(int i=0; i<posicionesX.size();i++){
            Line linea = new Line(posicionesX.get(i), posicionesY.get(i), posicionesX.get(i), posicionesY.get(i));
            linea.setStrokeWidth(7);
            puntosControl.add(linea);
        }
        
    }
    public ArrayList<Line> getLineas() {
        return lineas;
    }

    public void setLineas(ArrayList<Line> lineas) {
        this.lineas = lineas;
    }

    public ArrayList<Integer> getPosicionesX() {
        return posicionesX;
    }

    public void setPosicionesX(ArrayList<Integer> posicionesX) {
        this.posicionesX = posicionesX;
    }

    public ArrayList<Integer> getPosicionesY() {
        return posicionesY;
    }

    public void setPosicionesY(ArrayList<Integer> posicionesY) {
        this.posicionesY = posicionesY;
    }

    public int getPuntoMedioX() {
        return puntoMedioX;
    }

    public void setPuntoMedioX(int puntoMedioX) {
        this.puntoMedioX = puntoMedioX;
    }

    public int getPuntoMedioY() {
        return puntoMedioY;
    }

    public void setPuntoMedioY(int puntoMedioY) {
        this.puntoMedioY = puntoMedioY;
    }

    public ArrayList<Line> getPuntosControl() {
        return puntosControl;
    }

    public void setPuntosControl(ArrayList<Line> puntosControl) {
        this.puntosControl = puntosControl;
    }

    public Text getTextoFigura() {
        return textoFigura;
    }

    public void setTextoFigura(Text textoFigura) {
        this.textoFigura = textoFigura;
    }
    public void anadirPuntosOcupados(int i){
        this.puntosOcupados.add(i);
    }

    public ArrayList<Integer> getPuntosOcupados() {
        return puntosOcupados;
    }
    
}
