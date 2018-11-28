package Clases;

import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.shape.Line;

/**
 *
 * @author Los olvidados.
 */
public class Entidad {
    private String nombre;
    private int posX;
    private int posY;
    private ArrayList<Atributo> atributos = new ArrayList<>();
    private Figura figura;
    private ArrayList<Line> lineasUnionAtributos = new ArrayList<>();
    private int zoom;
    private boolean debil;
    public Entidad(boolean debil,int posX,int posY, String nombre,int zoom) {
        this.nombre=nombre;
        this.posX=posX;
        this.posY=posY;
        figura = new Figura();
        this.debil=debil;
        this.zoom=zoom;
        figura.crearFigura(this.debil,-1, this.nombre,this.posX,this.posY,this.zoom);//se crea inmediatamente la figura
                                                                //ya que es especifica para las entidades.
                                                                
                                                                
                                                                
    }

    public int getZoom() {
        return zoom;
    }

    public void setDebil(boolean debil) {
        this.debil = debil;
    }

    public boolean isDebil() {
        return debil;
    }
    
    public Figura getFigura() {
        return figura;
    }

    public void setFigura(Figura figura) {
        this.figura = figura;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.figura=new Figura();
        this.figura.crearFigura(this.debil,-1, this.nombre,this.posX,this.posY,this.zoom);
        this.crearLineasunionAtributos();
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
    public ArrayList<Integer> posicionesX(){
        return figura.getPosicionesX();
    }
    public ArrayList<Integer> posicionesY(){
        return figura.getPosicionesY();
    }    
    
    public void crearLineasunionAtributos(){
        this.lineasUnionAtributos.clear();
        for(int i =0; i<atributos.size();i++){
            int indEntidad = puntoCercano(i);
            Line linea = new Line(this.figura.getPosicionesX().get(indEntidad),this.figura.getPosicionesY().get(indEntidad),atributos.get(i).puntoMedioXFigura(),atributos.get(i).getPosY());
            this.lineasUnionAtributos.add(linea);
        }
    }

    public ArrayList<Atributo> getAtributos() {
        return atributos;
    }

    public ArrayList<Line> getLineasUnionAtributos() {
        return lineasUnionAtributos;
    }
    
    public void agregarAtributo(Atributo nuevo){
        atributos.add(nuevo);
        
    }
    private int puntoCercano(int indiceAtributo){
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
        figura = new Figura();
        figura.crearFigura(this.debil,-1, this.nombre, this.posX, this.posY, zoom);
        
        for(int i = 0; i<this.getAtributos().size(); i++){
            this.getAtributos().get(i).actualizarTamano(zoom);
        }
        
        
        
        
        crearLineasunionAtributos();
    }
    public ArrayList<Node> dibujoEntidad(){
        ArrayList<Node> retornar = new ArrayList<>();
        retornar.addAll(this.lineasUnionAtributos);
        for(int i = 0 ; i<this.atributos.size();i++){
            retornar.addAll(this.atributos.get(i).dibujoAtributo());
        }

        retornar.addAll(this.figura.getLineas());
        retornar.add(this.figura.getTextoFigura());
        return retornar;
    }
    
    public ArrayList<Atributo> retornarAtributosDeAtributos(int i){
        return this.atributos.get(i).getAtributos();
    }
    
    
    /*public void modificarNombreAtributo(Atributo atributo,String nuevoNombre){
        for(int i = 0; i<this.getAtributos().size()){
            if(atributo.getNombre().equals(this.getAtributos().get(i).getNombre())){
                atributo.getAtributos().get(i).s
            }
        }
        
        
        
    }*/

    Object get(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    
}
