package Clases;

import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.shape.Line;

/**
 *
 * @author Los olvidados.
 */
public class Relacion {
    public int posX;
    public int posY;
    public String nombre;
    public boolean encontrarEntidadDebil = false;
    
    public ArrayList<Entidad> entidad;
    public Figura figura;
    private ArrayList<Line> lineasRelacion = new ArrayList<>();
    
    private ArrayList<Atributo> atributos = new ArrayList<>();
    private ArrayList<Line> lineasUnionAtributos = new ArrayList<>();
    private int zoom;
    public Relacion(int posX, int posY,String nombre,int zoom) {
        this.posX = posX;
        this.posY = posY;
        this.nombre=nombre;
        this.zoom=zoom;
        this.entidad = new ArrayList<>();
        this.figura = new Figura();
    }
    public void AnadirEntidad(Entidad nueva){
        this.entidad.add(nueva);

    }
    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Entidad> getEntidad() {
        return entidad;
    }

    public void setEntidad(ArrayList<Entidad> entidad) {
        this.entidad = entidad;
    }

    public Figura getFigura() {
        return figura;
    }

    public void setFigura(Figura figura) {
        this.figura = figura;
    }
    /**
     * Según la cantidad de entidades agregadas a la relacion
     * se creara la figura junto con las lineas de union de la figura con las entidades.
     * @param zoom
     */
    public void crearRelacion(){
       
        for(int i = 0; i<entidad.size();i++){
            if(entidad.get(i).isDebil()){
                encontrarEntidadDebil=true;
            }
        }
        if(encontrarEntidadDebil==true){
            figura.crearFigura(true,entidad.size(), this.nombre, this.getPosX(), this.getPosY(),zoom);

        }
        else{
            figura.crearFigura(false,entidad.size(), this.nombre, this.getPosX(), this.getPosY(),zoom);

        }

        PuntoCercano pc = new PuntoCercano(this.figura,this.entidad);  
        
        int noLineaDoble =entidad.size();
        for(int j = 0; j<entidad.size();j++){
            if(entidad.get(j).isDebil() && noLineaDoble==1){

                    Line linea = new Line(entidad.get(j).getFigura().getPosicionesX().get(pc.pcEntidad(j)),entidad.get(j).getFigura().getPosicionesY().get(pc.pcEntidad(j)),
                    figura.getPosicionesX().get(pc.pcFigura(j)),figura.getPosicionesY().get(pc.pcFigura(j)));

                    Line linea2 = new Line(entidad.get(j).getFigura().getPosicionesX().get(pc.pcEntidad(j))+3,entidad.get(j).getFigura().getPosicionesY().get(pc.pcEntidad(j)),
                    figura.getPosicionesX().get(pc.pcFigura(j))+3,figura.getPosicionesY().get(pc.pcFigura(j)));
                    lineasRelacion.add(linea2);
                    lineasRelacion.add(linea);                  
            }
            else if(entidad.get(j).isDebil() && noLineaDoble==2){

                    Line linea = new Line(entidad.get(j).getFigura().getPosicionesX().get(pc.pcEntidad(j)),entidad.get(j).getFigura().getPosicionesY().get(pc.pcEntidad(j)),
                    figura.getPosicionesX().get(pc.pcFigura(j)),figura.getPosicionesY().get(pc.pcFigura(j)));

                    Line linea2 = new Line(entidad.get(j).getFigura().getPosicionesX().get(pc.pcEntidad(j))+3,entidad.get(j).getFigura().getPosicionesY().get(pc.pcEntidad(j)),
                    figura.getPosicionesX().get(pc.pcFigura(j))+3,figura.getPosicionesY().get(pc.pcFigura(j)));
                    lineasRelacion.add(linea2);
                    lineasRelacion.add(linea);                  
            }
            else if(entidad.get(j).isDebil() && noLineaDoble==4){

                    Line linea = new Line(entidad.get(j).getFigura().getPosicionesX().get(pc.pcEntidad(j)),entidad.get(j).getFigura().getPosicionesY().get(pc.pcEntidad(j)),
                    figura.getPosicionesX().get(pc.pcFigura(j)),figura.getPosicionesY().get(pc.pcFigura(j)));

                    Line linea2 = new Line(entidad.get(j).getFigura().getPosicionesX().get(pc.pcEntidad(j))+3,entidad.get(j).getFigura().getPosicionesY().get(pc.pcEntidad(j)),
                    figura.getPosicionesX().get(pc.pcFigura(j))+3,figura.getPosicionesY().get(pc.pcFigura(j)));
                    lineasRelacion.add(linea2);
                    lineasRelacion.add(linea);                  
            }
            else{
                Line linea = new Line(entidad.get(j).getFigura().getPosicionesX().get(pc.pcEntidad(j)),entidad.get(j).getFigura().getPosicionesY().get(pc.pcEntidad(j)),
                figura.getPosicionesX().get(pc.pcFigura(j)),figura.getPosicionesY().get(pc.pcFigura(j)));
                lineasRelacion.add(linea);  
            }
            
        }
    }
    public void actualizarLineasRelacion(){
        this.lineasRelacion.clear();
        this.figura=new Figura();
        crearRelacion();
        
    }
    public ArrayList<Line> getLineasRelacion() {
        return lineasRelacion;
    }

    public ArrayList<Atributo> getAtributos() {
        return atributos;
    }
    
    public void agregarAtributo(Atributo nuevo){
        atributos.add(nuevo);
        
    }
    public ArrayList<Line> getLineasUnionAtributos() {
        return lineasUnionAtributos;
    }
    public void crearLineasunionAtributos(){
        this.lineasUnionAtributos.clear();
        for(int i =0; i<atributos.size();i++){
            int indRelacion = puntoCercanoAtributo(i);
            Line linea = new Line(this.figura.getPosicionesX().get(indRelacion),this.figura.getPosicionesY().get(indRelacion),atributos.get(i).puntoMedioXFigura(),atributos.get(i).getPosY());
            this.lineasUnionAtributos.add(linea);
        }     
    }    
    private int puntoCercanoAtributo(int indiceAtributo){
        int x1=atributos.get(indiceAtributo).puntoMedioXFigura();
        int y1 = atributos.get(indiceAtributo).getPosY();
        ArrayList<Integer> distancias = new ArrayList<>();
        ArrayList<Integer> orden = new ArrayList<>();
        int x2,y2;
        for(int i=0; i< this.figura.getPosicionesX().size();i++){
            orden.add(i);
            x2 = this.getFigura().getPosicionesX().get(i);
            y2 = this.getFigura().getPosicionesY().get(i);
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
    public void actualizarTamano(int zoom){
        this.zoom=zoom;
        for(int i = 0; i<this.getEntidad().size();i++){
            this.getEntidad().get(i).actualizarTamano(zoom);
        }
        this.actualizarLineasRelacion();
        
        for(int i =0; i<this.atributos.size();i++){
            this.getAtributos().get(i).actualizarTamano(zoom);
        }
        this.crearLineasunionAtributos();

        
        
        
    }
    public ArrayList<Node> dibujoRelacion(){
        ArrayList<Node> retornar = new ArrayList<>();
        retornar.addAll(this.lineasUnionAtributos);
        for(int i = 0 ; i<this.atributos.size();i++){
            retornar.addAll(this.atributos.get(i).dibujoAtributo());
        }

        retornar.addAll(this.figura.getLineas());
        retornar.addAll(this.lineasRelacion);
        retornar.add(this.figura.getTextoFigura());
        return retornar;
    }    
    public ArrayList<Atributo> retornarAtributosDeAtributos(int i){
        return this.atributos.get(i).getAtributos();
    }
    
    public void eliminarEntidad(int i){
        this.entidad.remove(i);
        this.actualizarLineasRelacion();
        
    }
}
