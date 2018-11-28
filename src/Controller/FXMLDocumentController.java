package Controller;
import Clases.Atributo;
import Clases.Diagrama;
import Clases.Entidad;
import Clases.Herencia;
import Clases.Relacion;
import Clases.RespaldoDiagrama;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


/**
 * Clase donde se realizan la programacion.
 * @author Los olvidados.
 */
public class FXMLDocumentController implements Initializable {
      
    @FXML private Label label;
    @FXML private AnchorPane root;
    @FXML public Pane panelDibujo; // panel donde se dibujan las entidades y relaciones.
    
    @FXML private Pane panelOpciones; // se utiliza practicamente para deshabilitar las opciones al momento de dibujar
                                      // entidades o relaciones.    
    @FXML private String textoEntidad; //guarda el nombre de la entidad ingresada por el usuario.    
    @FXML private String textoRelacion;  //guarda el nombre de la relacion ingresada por el usuario.   
    @FXML private String textoAtributo;
                                                                       //seleccionó para crear una relación.    
        

    @FXML private ListView<CheckBox> entidadesDisponibles=new ListView<>();
    private String tipoAtributo;
    @FXML private CheckBox puntosDeControl;
    private String comboBoxEntidadesRelaciones;
    private String destinoAtributo;
    private Diagrama diagrama = new Diagrama(); //se crea un diagrama donde se guardan las entidades y relaciones.
    private int entidadMover = -1; //guarda el indice en que se encontraba la entidad en el arreglo del diagrama antes de ser movido.

    private String nombreEntidadMover=""; //Guarda el nombre de la entidad seleccionada para mover.
    private boolean crearEntidad = false;//Permite saber si el usuario seleccionó el boton para crear una entidad
    private boolean moverFiguras = true;//Se utiliza para activar o desactivar el movimiento de las figuras(entidades y relaciones).
    private boolean crearRelacion = false;//Permite saber si el usuario seleccionó el boton para crear una relacion
    private boolean crearAtributo = false;
    private boolean crearHerencia = false;
    private boolean atributoEncontradoMover = false;
    private boolean entidadEncontradaMover=false;//Permite saber si el usuario seleccionó una entidad, permitiendo ser movida.
    private boolean relacionEncontradaMover = false;//Permite saber si el usuario seleccionó una relación, permitiendo ser movida.        
    private boolean herenciaEncontradaMover = false;
    
    private String tipoHerenciaMover="";
    private String nombreAtributoMover = "";
    private Relacion relacionAuxiliar;//Guarda la relacion seleccionada para mover.
    private Herencia herenciaAuxiliar;
    private String nombreRelacionMover="";//Guarda el nombre de la relación seleccionada para mover.
    private String tipoAtributoMover="";
    private int indiceEntidadAtributo;
    private int indiceRelacionAtributo;
    private int indiceRelacionAtributoAtributo;
    private int indiceEntidadAtributoAtributo;
    private Entidad entidadAuxiliar;
    private Atributo atributoAuxiliar;
    private boolean atributoEnEntidadMover=false; //Permite saber si el atributo se encuentra en una entidad.
    private boolean atributoEnRelacionMover=false; //Permite saber si un atributo se encuentra en una relación.
    private boolean atributoEnAtributoMover=false;
    private int zoom;
    private boolean entidadDebil = false;
    FXMLDocumentController controlador;
    RespaldoDiagrama listaDiagramas = new RespaldoDiagrama();
    int rehacer = 0;
    
    
//--------------------------------------------------------------------------------------------------  
    
    
    
    private void guardarDiagrama(){
        
        if(listaDiagramas.tamano()-1!=rehacer){
            listaDiagramas.eliminar(rehacer+1);
        }
        listaDiagramas.agregarDiagrama(diagrama);
        rehacer = listaDiagramas.tamano()-1;
    }
    @FXML
    private void deshacer(){
        rehacer++;
        if(rehacer>=listaDiagramas.tamano()){
            rehacer = listaDiagramas.tamano()-1;
        }        
        diagrama =listaDiagramas.retornarDiagrama(rehacer);
        actualizarPanel();        
    }
    @FXML
    private void rehacer(){
        rehacer--;
        if(rehacer<0){
            rehacer=0;
        }

        diagrama =listaDiagramas.retornarDiagrama(rehacer);
        actualizarPanel();
        
    }
    @FXML
    private void crear(MouseEvent event){
//creamos entidad---------------------------------------------------------------------------------------------
        if(crearEntidad){
            
            Entidad nueva = new Entidad(false,(int)event.getX(),(int)event.getY(),textoEntidad,zoom);
            if(entidadDebil){
                nueva = new Entidad(true,(int)event.getX(),(int)event.getY(),textoEntidad,zoom); 
                entidadDebil=false;
            }

            if(puntosDeControl.isSelected()){
                panelDibujo.getChildren().addAll(nueva.getFigura().getPuntosControl());
            }
            entidadesDisponibles.getItems().add(new CheckBox(textoEntidad));
            diagrama.getEntidades().add(nueva);  
            guardarDiagrama();
            
        }
//creamos relacion-------------------------------------------------------------------------------------------------        
        if(crearRelacion){
            Relacion nueva = new Relacion((int)event.getX(),(int)event.getY(),textoRelacion,zoom);
            for(int i = 0; i<entidadesDisponibles.getItems().size();i++){
                if(entidadesDisponibles.getItems().get(i).isSelected()){
                    nueva.getEntidad().add(diagrama.getEntidades().get(i));
                }
            }
            nueva.crearRelacion();
            diagrama.getRelaciones().add(nueva);
        }
//creamos herencia-----------------------------------------------------------------------------------------------------
        if(crearHerencia){
            Herencia nueva = new Herencia(herenciaAuxiliar.getEntidadPadre(),herenciaAuxiliar.getEntidadesHijas(),
                    herenciaAuxiliar.getTipoHerencia(),(int)event.getX(),(int)event.getY());
            nueva.crearHerencia();
            diagrama.getHerencias().add(nueva);

             if(puntosDeControl.isSelected()){
                panelDibujo.getChildren().addAll(nueva.getPuntosControl());
            }
                        
        }
//creamos atributo------------------------------------------------------------------------------------------------------------
        if(crearAtributo){
            
                if(destinoAtributo.equals("Entidad")){
                    
                    Atributo nueva = new Atributo(tipoAtributo,(int)event.getX(),(int)event.getY(),textoAtributo,zoom);
                    for(int i=0;i<diagrama.getEntidades().size();i++){
                        if(comboBoxEntidadesRelaciones.equals(diagrama.getEntidades().get(i).getNombre())){
                            diagrama.getEntidades().get(i).agregarAtributo(nueva);
                            diagrama.getEntidades().get(i).crearLineasunionAtributos();  
                            diagrama.getAtributos().add(nueva);
                        }


                    }
                    
                }
                if(destinoAtributo.equals("Relación")){

                    Atributo nueva = new Atributo(tipoAtributo,(int)event.getX(),(int)event.getY(),textoAtributo,zoom);
                    for(int i=0;i<diagrama.getRelaciones().size();i++){
                        if(comboBoxEntidadesRelaciones.equals(diagrama.getRelaciones().get(i).getNombre())){
                            diagrama.getRelaciones().get(i).agregarAtributo(nueva);
                            diagrama.getRelaciones().get(i).crearLineasunionAtributos();  
                            diagrama.getAtributos().add(nueva);
                        }
                        

                    }

                }
                if(destinoAtributo.equals("Atributo")){
                    
                    Atributo nueva = new Atributo((String)tipoAtributo,(int)event.getX(),(int)event.getY(),textoAtributo,zoom);

                    for(int i=0; i< diagrama.getEntidades().size();i++){
                        for(int j = 0; j < diagrama.getEntidades().get(i).getAtributos().size();j++){
                            if(diagrama.getEntidades().get(i).getAtributos().get(j).getNombre().equals(comboBoxEntidadesRelaciones)){
                                diagrama.getEntidades().get(i).getAtributos().get(j).getAtributos().add(nueva);
                                diagrama.getEntidades().get(i).getAtributos().get(j).crearLineasunionAtributos();

                            }
                        }
                    }
                    for(int i=0; i< diagrama.getRelaciones().size();i++){
                        for(int j = 0; j < diagrama.getRelaciones().get(i).getAtributos().size();j++){
                            if(diagrama.getRelaciones().get(i).getAtributos().get(j).getNombre().equals(comboBoxEntidadesRelaciones)){
                                diagrama.getRelaciones().get(i).getAtributos().get(j).getAtributos().add(nueva);
                                diagrama.getRelaciones().get(i).getAtributos().get(j).crearLineasunionAtributos();
                            }
                        }
                    }
                    diagrama.getAtributos().add(nueva);
                }
        }
        actualizarPanel();
                                
        crearEntidad=false;
        moverFiguras = true;
        crearRelacion = false;
        crearAtributo = false;
        crearHerencia = false;

    }


    @FXML
    private void moverEnPanel(MouseEvent event){
        if(moverFiguras){
            int posX = (int)event.getX();
            int posY = (int)event.getY();
            if (0<=posX && posX<=1050 && 0<=posY && posY<= 687){
                if(relacionEncontradaMover == entidadEncontradaMover && herenciaEncontradaMover == atributoEncontradoMover){
                    for (int i=0; i<diagrama.getEntidades().size();i++){
                        ArrayList<Integer> x=diagrama.getEntidades().get(i).posicionesX();
                        ArrayList<Integer> y=diagrama.getEntidades().get(i).posicionesY();
                        if (x.get(0) < posX && x.get(1) > posX && y.get(0) < posY && y.get(x.size()-1) > posY) {
                            entidadDebil = diagrama.getEntidades().get(i).isDebil();
                            nombreEntidadMover=diagrama.getEntidades().get(i).getNombre();
                            entidadMover =i;
                            entidadAuxiliar=diagrama.getEntidades().get(i);
                            diagrama.getEntidades().remove(i);
                            entidadEncontradaMover = true;
                            
                            


                        } 
                    }
                    for(int i=0; i<diagrama.getRelaciones().size();i++){
                        if(areaFiguras((int)event.getX(), (int)event.getY(), i)){
                            nombreRelacionMover = diagrama.getRelaciones().get(i).getNombre();
                            relacionAuxiliar = diagrama.getRelaciones().get(i);
                            diagrama.getRelaciones().remove(i);
                            relacionEncontradaMover = true;
                        }
                    }
                    
                    for(int i = 0; i<diagrama.getEntidades().size(); i++){
                        for(int j =0;j<diagrama.getEntidades().get(i).getAtributos().size();j++){
                            ArrayList<Integer> x=diagrama.getEntidades().get(i).getAtributos().get(j).posicionesX();
                            ArrayList<Integer> y=diagrama.getEntidades().get(i).getAtributos().get(j).posicionesY();
                            if (x.get(0) <= posX && x.get(1) >= posX && y.get(2) <= posY && y.get(3) >= posY) {
                               
                                tipoAtributoMover = diagrama.getEntidades().get(i).getAtributos().get(j).getTipoAtributo();
                                nombreAtributoMover=diagrama.getEntidades().get(i).getAtributos().get(j).getNombre();
                                atributoAuxiliar = diagrama.getEntidades().get(i).getAtributos().get(j);
                                 removerAtributoEnDiagrama(atributoAuxiliar);
                                diagrama.getEntidades().get(i).getAtributos().remove(j);
                                atributoEncontradoMover = true;
                                indiceEntidadAtributo=i;
                                atributoEnEntidadMover = true;
                                


                            }                            
                        }
                         
                    }
                    for(int i = 0; i<diagrama.getRelaciones().size(); i++){
                        for(int j =0;j<diagrama.getRelaciones().get(i).getAtributos().size();j++){
                            ArrayList<Integer> x=diagrama.getRelaciones().get(i).getAtributos().get(j).posicionesX();
                            ArrayList<Integer> y=diagrama.getRelaciones().get(i).getAtributos().get(j).posicionesY();
                            if (x.get(0) <= posX && x.get(1) >= posX && y.get(2) <= posY && y.get(3) >= posY) {
                                
                                tipoAtributoMover = diagrama.getRelaciones().get(i).getAtributos().get(j).getTipoAtributo();
                                nombreAtributoMover=diagrama.getRelaciones().get(i).getAtributos().get(j).getNombre();
                                atributoAuxiliar = diagrama.getRelaciones().get(i).getAtributos().get(j);
                                removerAtributoEnDiagrama(atributoAuxiliar);
                                diagrama.getRelaciones().get(i).getAtributos().remove(j);
                                atributoEncontradoMover = true;
                                indiceRelacionAtributo=i;
                                atributoEnRelacionMover=true;
                                


                            }                            
                        }   
                    }
                    for(int i=0; i< diagrama.getRelaciones().size();i++){
                        for(int j = 0; j < diagrama.getRelaciones().get(i).getAtributos().size();j++){
                            if(diagrama.getRelaciones().get(i).getAtributos().get(j).getTipoAtributo().equals("Compuesto")){
                                for(int k = 0; k<diagrama.getRelaciones().get(i).getAtributos().get(j).getAtributos().size();k++){
                                ArrayList<Integer> x=diagrama.getRelaciones().get(i).getAtributos().get(j).getAtributos().get(k).getFigura().getPosicionesX();
                                ArrayList<Integer> y=diagrama.getRelaciones().get(i).getAtributos().get(j).getAtributos().get(k).getFigura().getPosicionesY();
                                if (x.get(0) <= posX && x.get(1) >= posX && y.get(2) <= posY && y.get(3) >= posY) {

                                    tipoAtributoMover = diagrama.getRelaciones().get(i).getAtributos().get(j).getAtributos().get(k).getTipoAtributo();
                                    nombreAtributoMover=diagrama.getRelaciones().get(i).getAtributos().get(j).getAtributos().get(k).getNombre();
                                    atributoAuxiliar = diagrama.getRelaciones().get(i).getAtributos().get(j).getAtributos().get(k);
                                    removerAtributoEnDiagrama(atributoAuxiliar);
                                    diagrama.getRelaciones().get(i).getAtributos().get(j).getAtributos().remove(k);
                                    atributoEncontradoMover = true;
                                    indiceRelacionAtributo=i;
                                    indiceRelacionAtributoAtributo=j;
                                    atributoEnAtributoMover=true;
                                    atributoEnRelacionMover=true;


                                }                             
                                }
                            }
                        }
                    }
                    for(int i = 0; i< diagrama.getHerencias().size();i++){
                        ArrayList<Integer> x = diagrama.getHerencias().get(i).posicionesXFigura();
                        ArrayList<Integer> y = diagrama.getHerencias().get(i).posicionesYFigura();
                        if(x.get(0)<=posX && posX<=x.get(1) && y.get(2)<=posY && posY<=y.get(3)){
                            herenciaEncontradaMover = true;
                            herenciaAuxiliar = diagrama.getHerencias().get(i);
                            tipoHerenciaMover = diagrama.getHerencias().get(i).getTipoHerencia();
                            diagrama.getHerencias().remove(i);
                            
                        }
                    }
                    
                    
                    for(int i=0; i< diagrama.getEntidades().size();i++){
                        for(int j = 0; j < diagrama.getEntidades().get(i).getAtributos().size();j++){
                            if(diagrama.getEntidades().get(i).getAtributos().get(j).getTipoAtributo().equals("Compuesto")){
                                for(int k = 0; k<diagrama.getEntidades().get(i).getAtributos().get(j).getAtributos().size();k++){
                                ArrayList<Integer> x=diagrama.getEntidades().get(i).getAtributos().get(j).getAtributos().get(k).getFigura().getPosicionesX();
                                ArrayList<Integer> y=diagrama.getEntidades().get(i).getAtributos().get(j).getAtributos().get(k).getFigura().getPosicionesY();
                                if (x.get(0) <= posX && x.get(1) >= posX && y.get(2) <= posY && y.get(3) >= posY) {

                                    tipoAtributoMover = diagrama.getEntidades().get(i).getAtributos().get(j).getAtributos().get(k).getTipoAtributo();
                                    nombreAtributoMover=diagrama.getEntidades().get(i).getAtributos().get(j).getAtributos().get(k).getNombre();
                                    atributoAuxiliar = diagrama.getEntidades().get(i).getAtributos().get(j).getAtributos().get(k);
                                    diagrama.getEntidades().get(i).getAtributos().get(j).getAtributos().remove(k);
                                    atributoEncontradoMover = true;
                                    indiceEntidadAtributo=i;
                                    indiceEntidadAtributoAtributo=j;
                                    atributoEnAtributoMover=true;
                                    atributoEnEntidadMover=true;



                                }                             
                                }
                            }
                        }
                    }
                }
                else{
                    if(entidadEncontradaMover){
                                                
                        Entidad nueva = new Entidad(entidadDebil,(int)event.getX(),(int)event.getY(),nombreEntidadMover,zoom);

                        
                        
                        for(int i = 0; i<entidadAuxiliar.getAtributos().size();i++){
                            nueva.getAtributos().add(entidadAuxiliar.getAtributos().get(i));
                        }
                        nueva.crearLineasunionAtributos();
                        if(sobreposicionEntidad(nueva)){

                            moverEntidadEnElementos(nueva);
                            diagrama.getEntidades().add(nueva);

                            actualizarPanel();
                            entidadAuxiliar=nueva;
                            diagrama.getEntidades().remove(diagrama.getEntidades().size()-1);
                            
                            }
                        }                         
                    }
                    if(relacionEncontradaMover){
                        
                        Relacion nueva = new Relacion((int)event.getX(),(int)event.getY(),nombreRelacionMover,zoom); 
                        for(int i = 0; i<relacionAuxiliar.getEntidad().size();i++){
                            nueva.getEntidad().add(relacionAuxiliar.getEntidad().get(i));
                        }
                        nueva.crearRelacion();
                         
                        for(int i = 0; i<relacionAuxiliar.getAtributos().size();i++){
                            nueva.getAtributos().add(relacionAuxiliar.getAtributos().get(i));
                        }

                        nueva.crearLineasunionAtributos(); 
                        if(sobreposicionRelacion(nueva)){    
                            diagrama.getRelaciones().add(nueva);

                            actualizarPanel();

                            relacionAuxiliar=nueva;
                            diagrama.getRelaciones().remove(diagrama.getRelaciones().size()-1);
                        }
                        
                    }
                    if(herenciaEncontradaMover){
                        ArrayList<Entidad> hijas = new ArrayList<>();                        
                        for(int i = 0; i < herenciaAuxiliar.getEntidadesHijas().size();i++){
                            hijas.add(herenciaAuxiliar.getEntidadesHijas().get(i));
                        }
                        
                        
                        Herencia nueva = new Herencia(herenciaAuxiliar.getEntidadPadre(),hijas,tipoHerenciaMover,(int)event.getX(),(int)event.getY());
                        nueva.crearHerencia();

                        if(sobreposicionHerencia(nueva)){

                            diagrama.getHerencias().add(nueva);

                            actualizarPanel();
                            herenciaAuxiliar = nueva;
                            diagrama.getHerencias().remove(diagrama.getHerencias().size()-1);
                            
                            }                        
                    }
                    if(atributoEncontradoMover){
                        if(atributoEnEntidadMover){
                            if(atributoEnAtributoMover){
                                Atributo nueva = new Atributo(tipoAtributoMover,(int)event.getX(),(int)event.getY(),nombreAtributoMover,zoom);
                                    
                                if(sobreposicionAtributo(nueva)){
                                    diagrama.getEntidades().get(indiceEntidadAtributo).getAtributos().get(indiceEntidadAtributoAtributo).agregarAtributo(nueva);
                                     diagrama.getEntidades().get(indiceEntidadAtributo).getAtributos().get(indiceEntidadAtributoAtributo).crearLineasunionAtributos();

                                     actualizarPanel();      
                                     atributoAuxiliar = nueva;
                                     removerAtributoEnDiagrama(atributoAuxiliar);
                                     int tamano = diagrama.getEntidades().get(indiceEntidadAtributo).getAtributos().get(indiceEntidadAtributoAtributo).getAtributos().size()-1;
                                     diagrama.getEntidades().get(indiceEntidadAtributo).getAtributos().get(indiceEntidadAtributoAtributo).getAtributos().remove(tamano);                                 

                                }
 
                            }else{
                                Atributo nueva = new Atributo(tipoAtributoMover,(int)event.getX(),(int)event.getY(),nombreAtributoMover,zoom);
                                for(int i = 0;i<atributoAuxiliar.getAtributos().size();i++){
                                    nueva.getAtributos().add(atributoAuxiliar.getAtributos().get(i));
                                }
                                nueva.crearLineasunionAtributos();
                                if(sobreposicionAtributo(nueva)){
                                    diagrama.getEntidades().get(indiceEntidadAtributo).agregarAtributo(nueva);
                                    diagrama.getEntidades().get(indiceEntidadAtributo).crearLineasunionAtributos();
                                    panelDibujo.getChildren().clear();
                                    actualizarPanel();      
                                    atributoAuxiliar = nueva;
                                    removerAtributoEnDiagrama(atributoAuxiliar);
                                    diagrama.getEntidades().get(indiceEntidadAtributo).getAtributos().remove(diagrama.getEntidades().get(indiceEntidadAtributo).getAtributos().size()-1);                            

                                }

                            }

                        }
                        if(atributoEnRelacionMover){
                            if(atributoEnAtributoMover){
                                Atributo nueva = new Atributo(tipoAtributoMover,(int)event.getX(),(int)event.getY(),nombreAtributoMover,zoom);

                                if(sobreposicionAtributo(nueva)){

                                    diagrama.getRelaciones().get(indiceRelacionAtributo).getAtributos().get(indiceRelacionAtributoAtributo).agregarAtributo(nueva);
                                    diagrama.getRelaciones().get(indiceRelacionAtributo).getAtributos().get(indiceRelacionAtributoAtributo).crearLineasunionAtributos();

                                    actualizarPanel(); 
                                    atributoAuxiliar = nueva;
                                    removerAtributoEnDiagrama(atributoAuxiliar);
                                    int tamano = diagrama.getRelaciones().get(indiceRelacionAtributo).getAtributos().get(indiceRelacionAtributoAtributo).getAtributos().size()-1;
                                    diagrama.getRelaciones().get(indiceRelacionAtributo).getAtributos().get(indiceRelacionAtributoAtributo).getAtributos().remove(tamano);                                 

                                                                    

                                }

                            }
                            else{
                                Atributo nueva = new Atributo(tipoAtributoMover,(int)event.getX(),(int)event.getY(),nombreAtributoMover,zoom);

                                for(int i = 0;i<atributoAuxiliar.getAtributos().size();i++){
                                    nueva.getAtributos().add(atributoAuxiliar.getAtributos().get(i));
                                }
                                nueva.crearLineasunionAtributos();
                                if(sobreposicionAtributo(nueva)){
                                    diagrama.getRelaciones().get(indiceRelacionAtributo).agregarAtributo(nueva);
                                    diagrama.getRelaciones().get(indiceRelacionAtributo).crearLineasunionAtributos();
                                    panelDibujo.getChildren().clear();
                                    actualizarPanel();      
                                    atributoAuxiliar = nueva;
                                    removerAtributoEnDiagrama(atributoAuxiliar);
                                    diagrama.getRelaciones().get(indiceRelacionAtributo).getAtributos().remove(diagrama.getRelaciones().get(indiceRelacionAtributo).getAtributos().size()-1);                                 
                                }    
                                
   
                            }
                            
                        }
                    

                    }
                           
            }
        }
        
    }
    
    
    public void actualizarPanel(){
        panelDibujo.getChildren().clear();
        for(int i = 0; i<diagrama.getEntidades().size();i++){
            panelDibujo.getChildren().addAll(diagrama.getEntidades().get(i).dibujoEntidad());
            if(puntosDeControl.isSelected()){
                panelDibujo.getChildren().addAll(diagrama.getEntidades().get(i).getFigura().getPuntosControl());
            }
        }
        
        for(int i = 0; i < diagrama.getRelaciones().size();i++){
            panelDibujo.getChildren().addAll(diagrama.getRelaciones().get(i).dibujoRelacion());
            if(puntosDeControl.isSelected()){
                panelDibujo.getChildren().addAll(diagrama.getRelaciones().get(i).getFigura().getPuntosControl());
            } 
        }
        for(int i = 0; i<diagrama.getHerencias().size();i++){
            panelDibujo.getChildren().addAll(diagrama.getHerencias().get(i).dibujoHerencia());
        }

    }   
    @FXML
    public void mouseNoPresionado(MouseEvent event){
        if(moverFiguras){
            if(entidadEncontradaMover){

                diagrama.getEntidades().add(entidadAuxiliar);
                actualizarPanel();                                             
                entidadesDisponibles.getItems().remove(entidadMover);
                entidadesDisponibles.getItems().add(new CheckBox(nombreEntidadMover));
                nombreEntidadMover = "";   
                entidadDebil=false;
                guardarDiagrama();
            }
            if(relacionEncontradaMover){
                        diagrama.getRelaciones().add(relacionAuxiliar);
                        actualizarPanel(); 
                        nombreRelacionMover = ""; 

            }
            if(herenciaEncontradaMover){
                diagrama.getHerencias().add(herenciaAuxiliar);
                
                actualizarPanel();
                tipoHerenciaMover = "";
                
            }
            if(atributoEncontradoMover){

                        if(atributoEnEntidadMover){   
                            if(atributoEnAtributoMover){
                                diagrama.getEntidades().get(indiceEntidadAtributo).getAtributos().get(indiceEntidadAtributoAtributo).getAtributos().add(atributoAuxiliar);
                                diagrama.getEntidades().get(indiceEntidadAtributo).getAtributos().get(indiceEntidadAtributoAtributo).crearLineasunionAtributos();
                                
                            }
                            else{
                                diagrama.getEntidades().get(indiceEntidadAtributo).getAtributos().add(atributoAuxiliar);
                                diagrama.getEntidades().get(indiceEntidadAtributo).crearLineasunionAtributos();                                
                            }
                        }
                        if(atributoEnRelacionMover){
                            if(atributoEnAtributoMover){
                                diagrama.getRelaciones().get(indiceRelacionAtributo).getAtributos().get(indiceRelacionAtributoAtributo).getAtributos().add(atributoAuxiliar);
                                diagrama.getRelaciones().get(indiceRelacionAtributo).getAtributos().get(indiceRelacionAtributoAtributo).crearLineasunionAtributos();                                    
                            }
                            else{
                                diagrama.getRelaciones().get(indiceRelacionAtributo).getAtributos().add(atributoAuxiliar);
                                diagrama.getRelaciones().get(indiceEntidadAtributo).crearLineasunionAtributos(); 
                            }
                        }
                        diagrama.getAtributos().add(atributoAuxiliar);
                        atributoEnAtributoMover=false;
                        atributoEnRelacionMover=false;
                        atributoEnEntidadMover=false;
                            nombreAtributoMover = ""; 
                            
            }

            actualizarPanel();
            relacionEncontradaMover = false;
            entidadEncontradaMover= false;
            atributoEncontradoMover = false;
            herenciaEncontradaMover = false;
            
            
        }
    }
     
    private boolean areaFiguras(int x, int y, int indice){
        int tamano = diagrama.getRelaciones().get(indice).getEntidad().size();
        ArrayList<Integer> posX=diagrama.getRelaciones().get(indice).getFigura().getPosicionesX();
        ArrayList<Integer> posY=diagrama.getRelaciones().get(indice).getFigura().getPosicionesY();

        if(tamano==3){
            if(posX.get(0)<=x && x<=posX.get(1) && posY.get(2)<=y && y<=posY.get(0)){
                return true;
            }             
        }
        else{
            if(posX.get(0)<=x && x<=posX.get(1) && posY.get(2)<=y && y<=posY.get(3)){
                return true;
            }  
        }
        return false;
    }    
    private void moverEntidadEnElementos(Entidad entidad){
        for(int i =0; i<diagrama.getRelaciones().size();i++){
            for(int j = 0; j<diagrama.getRelaciones().get(i).getEntidad().size(); j++){
                if(diagrama.getRelaciones().get(i).getEntidad().get(j).getNombre().equals(entidad.getNombre())){
                    
                    diagrama.getRelaciones().get(i).getEntidad().remove(j);
                    diagrama.getRelaciones().get(i).getEntidad().add(entidad);
                    diagrama.getRelaciones().get(i).actualizarLineasRelacion();
                }
            }
        }
        for(int i =0; i<diagrama.getHerencias().size();i++){
            if(entidad.getNombre().equals(diagrama.getHerencias().get(i).getEntidadPadre().getNombre())){
                diagrama.getHerencias().get(i).setEntidadPadre(entidad);
                diagrama.getHerencias().get(i).crearHerencia();
            }
            
            for(int j = 0; j<diagrama.getHerencias().get(i).getEntidadesHijas().size(); j++){
                if(diagrama.getHerencias().get(i).getEntidadesHijas().get(j).getNombre().equals(entidad.getNombre())){
                    
                    diagrama.getHerencias().get(i).getEntidadesHijas().remove(j);
                    diagrama.getHerencias().get(i).getEntidadesHijas().add(entidad);
                    diagrama.getHerencias().get(i).crearHerencia();
                }
            }
        }
        
    }
        
    @FXML
    private void limpiarPagina(){
        panelDibujo.getChildren().clear();
        if(diagrama.cantidadEntidad()!=0 || !diagrama.getAtributos().isEmpty()){
            
            diagrama.getEntidades().clear();
            diagrama.getRelaciones().clear();
            diagrama.getAtributos().clear();  
            diagrama.getHerencias().clear();
            entidadesDisponibles.getItems().clear();

            
            
        }
        else{
            mensaje("Ya esta todo limpio...");
        }

    }     
    
    private void removerAtributoEnDiagrama(Atributo atributo){
        for(int i  = 0; i<diagrama.getAtributos().size(); i++){
            if(atributo.getNombre().equals(diagrama.getAtributos().get(i).getNombre())){
                diagrama.getAtributos().remove(i);
            }
        }
    }
    /**
     * Funcion que exporta el panel de dibujo a png
     */
    @FXML
    private void ExportarAPNG() throws IOException{
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("PNG", "*.png")     
        );
        
        File file = fileChooser.showSaveDialog(null);
            WritableImage wim = new WritableImage(1274,683);
            panelDibujo.snapshot(null, wim);
            try{
                ImageIO.write(SwingFXUtils.fromFXImage(wim, null),"png",file);

            }catch (IOException s){
                mensaje("Error: "+s);
            }  
        


    }     
    /**
     * Exporta el panel en formato PDF
     */
    @FXML
    private void exportarPdf(){
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
            File file = new File("CanvasImage.png");
             WritableImage wim = new WritableImage(626,681);
             panelDibujo.snapshot(null, wim);
             try{
                 ImageIO.write(SwingFXUtils.fromFXImage(wim, null),"png",file);

             }catch (IOException s){
                 mensaje("Error: "+s);
             }
             File enviar = fileChooser.showSaveDialog(null);
             PDDocument doc    = new PDDocument();
             PDPage page = new PDPage();
             PDImageXObject pdimage;
             PDPageContentStream content;
             try {
                 pdimage = PDImageXObject.createFromFile("CanvasImage.png",doc);
                 content = new PDPageContentStream(doc, page);
                 content.drawImage(pdimage, -15, 100);
                 content.close();
                 doc.addPage(page);
                 doc.save(enviar.getPath());
                 doc.close();
                 file.delete();
             } catch (IOException ex) {
                 Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
             }
    }        
    private boolean sobreposicionAtributo(Atributo atributo){
            for(int i=0;i<diagrama.getEntidades().size();i++){
                ArrayList<Integer> x=diagrama.getEntidades().get(i).getFigura().getPosicionesX();
                ArrayList<Integer> y=diagrama.getEntidades().get(i).getFigura().getPosicionesY();
                for(int j=0;j<atributo.getFigura().getPosicionesX().size();j++){
                    int posX = atributo.getFigura().getPosicionesX().get(j);
                    int posY = atributo.getFigura().getPosicionesY().get(j);
                    if (x.get(0)-5 <= posX && x.get(1)+5 >= posX && y.get(0)-5 <= posY && y.get(3)+5 >= posY) {
                            return false;
                        }
                }    
            } 
            for(int j=0;j<atributo.getFigura().getPosicionesX().size();j++){
                int posX=atributo.getFigura().getPosicionesX().get(j);
                int posY = atributo.getFigura().getPosicionesY().get(j);
                for (int i=0; i<diagrama.getRelaciones().size();i++){
                    ArrayList<Integer> x=diagrama.getRelaciones().get(i).getFigura().getPosicionesX();
                    ArrayList<Integer> y=diagrama.getRelaciones().get(i).getFigura().getPosicionesY();
                    if (diagrama.getRelaciones().get(i).getEntidad().size()==3){
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(0) > posY) {
                            return false;
                        }                         
                    }
                    else{
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(3) > posY) {
                            return false;
                        }                         
                    }

                } 
            }  

            ArrayList<Integer> x=atributo.getFigura().getPosicionesX();
            ArrayList<Integer> y=atributo.getFigura().getPosicionesY();
            for(int i=0;i<diagrama.getEntidades().size();i++){
                ArrayList<Integer> Ex=diagrama.getEntidades().get(i).getFigura().getPosicionesX();
                ArrayList<Integer> Ey=diagrama.getEntidades().get(i).getFigura().getPosicionesY();
                for(int j=0;j<Ex.size();j++){
                    int posX = Ex.get(j);
                    int posY = Ey.get(j);
                    if (x.get(0)-5 <= posX && x.get(1)+5 > posX && y.get(0)-5 < posY && y.get(3)+5 > posY) {
                            return false;
                        }
                }    
            } 

            for(int i=0;i<diagrama.getAtributos().size();i++){
                ArrayList<Integer> Ex=diagrama.getAtributos().get(i).getFigura().getPosicionesX();
                ArrayList<Integer> Ey=diagrama.getAtributos().get(i).getFigura().getPosicionesY();
                for(int j=0;j<Ex.size();j++){
                    int posX = Ex.get(j);
                    int posY = Ey.get(j);
                    if (x.get(0)-5 <= posX && x.get(1)+5 > posX && y.get(0)-5 < posY && y.get(3)+5 > posY) {
                            return false;
                        }
                }    
            }             


        return true;
    }    
    /**
     * Limpia el programa considerando el panel de dibujo, listView y el diagrama
     * con las entidades y relaciones que fueron creadas.
     */
    private boolean sobreposicionRelacion(Relacion relacion){
            for(int i=0;i<diagrama.getEntidades().size();i++){
                ArrayList<Integer> x=diagrama.getEntidades().get(i).getFigura().getPosicionesX();
                ArrayList<Integer> y=diagrama.getEntidades().get(i).getFigura().getPosicionesY();
                for(int j=0;j<relacion.getFigura().getPosicionesX().size();j++){
                    int posX = relacion.getFigura().getPosicionesX().get(j);
                    int posY = relacion.getFigura().getPosicionesY().get(j);
                    if (x.get(0)-5 <= posX && x.get(1)+5 >= posX && y.get(0)-5 <= posY && y.get(3)+5 >= posY) {
                            return false;
                        }
                }    
            }
            for(int j=0;j<relacion.getFigura().getPosicionesX().size();j++){
                int posX=relacion.getFigura().getPosicionesX().get(j);
                int posY = relacion.getFigura().getPosicionesY().get(j);
                for (int i=0; i<diagrama.getRelaciones().size();i++){
                    ArrayList<Integer> x=diagrama.getRelaciones().get(i).getFigura().getPosicionesX();
                    ArrayList<Integer> y=diagrama.getRelaciones().get(i).getFigura().getPosicionesY();
                    if (diagrama.getRelaciones().get(i).getEntidad().size()==3){
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(0) > posY) {
                            return false;
                        }                         
                    }
                    else{
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(3) > posY) {
                            return false;
                        }                         
                    }

                } 
            }
            ArrayList<Integer> x=relacion.getFigura().getPosicionesX();
            ArrayList<Integer> y=relacion.getFigura().getPosicionesY();
            for(int j=0;j<diagrama.getRelaciones().size();j++){
                ArrayList<Integer> rX=diagrama.getRelaciones().get(j).getFigura().getPosicionesX();
                ArrayList<Integer> rY=diagrama.getRelaciones().get(j).getFigura().getPosicionesY();
                for (int i=0; i<rX.size();i++){
                    int posX = rX.get(i);
                    int posY = rY.get(i);
                    if (relacion.getEntidad().size()==3){
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(0) > posY) {
                            return false;
                        }                         
                    }
                    else{
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(3) > posY) {
                            return false;
                        }                         
                    }

                } 
            }
            for(int j=0;j<diagrama.getEntidades().size();j++){
                ArrayList<Integer> rX=diagrama.getEntidades().get(j).getFigura().getPosicionesX();
                ArrayList<Integer> rY=diagrama.getEntidades().get(j).getFigura().getPosicionesY();
                for (int i=0; i<rX.size();i++){
                    int posX = rX.get(i);
                    int posY = rY.get(i);
                    if (relacion.getEntidad().size()==3){
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(0) > posY) {
                            return false;
                        }                         
                    }
                    else{
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(3) > posY) {
                            return false;
                        }                         
                    }

                } 
            }
            for(int j=0;j<diagrama.getAtributos().size();j++){
                ArrayList<Integer> rX=diagrama.getAtributos().get(j).getFigura().getPosicionesX();
                ArrayList<Integer> rY=diagrama.getAtributos().get(j).getFigura().getPosicionesY();
                for (int i=0; i<rX.size();i++){
                    int posX = rX.get(i);
                    int posY = rY.get(i);
                    if (relacion.getEntidad().size()==3){
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(0) > posY) {
                            return false;
                        }                         
                    }
                    else{
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(3) > posY) {
                            return false;
                        }                         
                    }

                } 
               
            }             
            
            return true;   
            }
      
    private boolean sobreposicionEntidad(Entidad entidad){
            for(int i=0;i<diagrama.getEntidades().size();i++){
                ArrayList<Integer> x=diagrama.getEntidades().get(i).getFigura().getPosicionesX();
                ArrayList<Integer> y=diagrama.getEntidades().get(i).getFigura().getPosicionesY();
                for(int j=0;j<entidad.getFigura().getPosicionesX().size();j++){
                    int posX = entidad.getFigura().getPosicionesX().get(j);
                    int posY = entidad.getFigura().getPosicionesY().get(j);
                    if (x.get(0)-5 <= posX && x.get(1)+5 >= posX && y.get(0)-5 <= posY && y.get(3)+5 >= posY) {
                            return false;
                        }
                }    
            } 
            for(int j=0;j<entidad.getFigura().getPosicionesX().size();j++){
                int posX=entidad.getFigura().getPosicionesX().get(j);
                int posY = entidad.getFigura().getPosicionesY().get(j);
                for (int i=0; i<diagrama.getRelaciones().size();i++){
                    ArrayList<Integer> x=diagrama.getRelaciones().get(i).getFigura().getPosicionesX();
                    ArrayList<Integer> y=diagrama.getRelaciones().get(i).getFigura().getPosicionesY();
                    if (diagrama.getRelaciones().get(i).getEntidad().size()==3){
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(0) > posY) {
                            return false;
                        }                         
                    }
                    else{
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(3) > posY) {
                            return false;
                        }                         
                    }

                } 
            }  
            ArrayList<Integer> x=entidad.getFigura().getPosicionesX();
            ArrayList<Integer> y=entidad.getFigura().getPosicionesY();
            for(int i=0;i<diagrama.getEntidades().size();i++){
                ArrayList<Integer> Ex=diagrama.getEntidades().get(i).getFigura().getPosicionesX();
                ArrayList<Integer> Ey=diagrama.getEntidades().get(i).getFigura().getPosicionesY();
                for(int j=0;j<entidad.getFigura().getPosicionesX().size();j++){
                    int posX = Ex.get(j);
                    int posY = Ey.get(j);
                    if (x.get(0)-5 <= posX && x.get(1)+5 >= posX && y.get(0)-5 <= posY && y.get(3)+5 >= posY) {
                            return false;
                        }
                }    
            }
            for(int i=0;i<diagrama.getRelaciones().size();i++){
                ArrayList<Integer> Ex=diagrama.getRelaciones().get(i).getFigura().getPosicionesX();
                ArrayList<Integer> Ey=diagrama.getRelaciones().get(i).getFigura().getPosicionesY();
                for(int j=0;j<Ex.size();j++){
                    int posX = Ex.get(j);
                    int posY = Ey.get(j);
                    if (x.get(0)-5 <= posX && x.get(1)+5 >= posX && y.get(0)-5 <= posY && y.get(3)+5 >= posY) {
                            return false;
                        }
                }    
            } 
            for(int i=0;i<diagrama.getAtributos().size();i++){
                ArrayList<Integer> Ex=diagrama.getAtributos().get(i).getFigura().getPosicionesX();
                ArrayList<Integer> Ey=diagrama.getAtributos().get(i).getFigura().getPosicionesY();
                for(int j=0;j<Ex.size();j++){
                    int posX = Ex.get(j);
                    int posY = Ey.get(j);
                    if (x.get(0)-5 <= posX && x.get(1)+5 >= posX && y.get(0)-5 <= posY && y.get(3)+5 >= posY) {
                            return false;
                        }
                }    
            }             
            
        return true;    
            
    }

    private boolean sobreposicionHerencia(Herencia herencia){
        return true;
    }
 
 
    
    /**
     * Activa los puntos de control de las entidades y relaciones.
     */
    @FXML
    private void puntosDeControlActivar(){

        actualizarPanel();
    }
    
    
    /**
     * Funcion que permite crear una ventana de alerta
     * @param texto: mensaje que se quiere escribir en la ventana. 
     */
    public void mensaje(String texto){
        JOptionPane.showMessageDialog(null, texto);
    }

    
    /**
     * Funcion que valida que el nombre que ingresó el usuario no este
     * repetido en una entidad ya creada.
     * @return 
     * @return: true si es no esta repetido y false si es que esta repetido. 
     */
    
    @FXML
    private void zoomMas(){
        int x1;
        int y1;
        
        if(diagrama.cantidadEntidad()!=0){

                zoom=zoom+50; 
                for(int i = 0; i<diagrama.getEntidades().size();i++){
                    x1 = diagrama.getEntidades().get(i).getPosY();
                    y1= diagrama.getEntidades().get(i).getPosY();
                    diagrama.getEntidades().get(i).actualizarTamano(zoom);

                    if(x1<320){
                        diagrama.getEntidades().get(i).setPosX(x1-10);                        
                    }
                    else if(x1>320){
                        diagrama.getEntidades().get(i).setPosX(x1+10);
                    }                   
                    
                }
                for(int i = 0; i<diagrama.getRelaciones().size();i++){
                    diagrama.getRelaciones().get(i).actualizarTamano(zoom);
                }
                actualizarPanel();
            
            
        }
        else{
            mensaje("Para poder usar el zoom por lo menos debe agregar una entidad");
        }
    }
    
    @FXML
    private void zoomMenos(){
        if(diagrama.cantidadEntidad()!=0){
            if(zoom>-50){
                zoom=zoom-50;
                for(int i = 0; i<diagrama.getEntidades().size();i++){
                    diagrama.getEntidades().get(i).actualizarTamano(zoom);
                }
                for(int i = 0; i<diagrama.getRelaciones().size();i++){
                    diagrama.getRelaciones().get(i).actualizarTamano(zoom);
                }
                actualizarPanel();
            }
            else{
                mensaje("El zoom no puedes ser menor...");
            }
        }
        else{
            mensaje("Para poder usar el zoom por lo menos debe agregar una entidad");
        }
    }
    public void obtenerEntidad(String nombreEntidad,boolean debil,Diagrama diagrama){
        this.diagrama=diagrama;
        textoEntidad=nombreEntidad;
        entidadDebil = debil;
        crearEntidad=true;
        moverFiguras = false; 
        crearRelacion=false; 
        crearAtributo = false;
        
        
    }



    @FXML
    private void ventanaEntidad() throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        AnchorPane root1 = (AnchorPane)loader.load(getClass().getResource("Entidad.fxml").openStream());
        EntidadController instanciaControlador = (EntidadController)loader.getController();
        instanciaControlador.recibirParametros(controlador,diagrama);
        Scene scene = new Scene(root1);
        stage.setScene(scene);
        stage.alwaysOnTopProperty();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        
    }

    
    public void creacionRelaciones(String nombreRelacion,Diagrama diagrama,ListView<CheckBox> entidadesDisponibles){
        this.entidadesDisponibles=entidadesDisponibles;
        this.textoRelacion=nombreRelacion;
        this.diagrama=diagrama;
            crearEntidad=false;
            moverFiguras = false; 
            crearRelacion=true;    
            crearAtributo = false;
            crearHerencia = false;
        
    }  
    @FXML
    private void ventanaRelacion() throws IOException{
        
        if(!diagrama.getEntidades().isEmpty()){
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root1 = (AnchorPane)loader.load(getClass().getResource("Relaciones.fxml").openStream());
            RelacionesController instanciaControlador = (RelacionesController)loader.getController();
            instanciaControlador.recibirParametros(controlador,diagrama,entidadesDisponibles);
            Scene scene = new Scene(root1);
            stage.setScene(scene);
            stage.alwaysOnTopProperty();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();            
        }
        else{
            mensaje("Debe crear una entidad como mínimo para poder\n"
                    + "             crear una relación");
        }

        
    }
    @FXML
    private void ventanaAtributo() throws IOException{
        
        if(!diagrama.getEntidades().isEmpty()){
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root1 = (AnchorPane)loader.load(getClass().getResource("Atributos.fxml").openStream());
            AtributosController instanciaControlador = (AtributosController)loader.getController();
            instanciaControlador.recibirParametros(controlador,diagrama);
            Scene scene = new Scene(root1);
            stage.setScene(scene);
            stage.alwaysOnTopProperty();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();            
        }
        else{
            mensaje("Debe crear una entidad como mínimo para poder\n"
                    + "crear una relación");
        }

        
    }    
    @FXML
    private void ventanaHerencia() throws IOException{
        
        if(!diagrama.getEntidades().isEmpty()){
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root1 = (AnchorPane)loader.load(getClass().getResource("Herencia.fxml").openStream());
            HerenciaController instanciaControlador = (HerenciaController)loader.getController();
            instanciaControlador.recibirParametros(controlador,diagrama);
            Scene scene = new Scene(root1);
            stage.setScene(scene);
            stage.alwaysOnTopProperty();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();            
        }
        else{
            mensaje("Debe crear una entidad como mínimo para poder\n"
                    + "             crear una relación");
        }

        
    }     
    
    
    @FXML
    public void creacionAtributo(String tipo, String destino, String comboBox,Diagrama diagrama,String textoAtributo){
        this.tipoAtributo=tipo;
        this.comboBoxEntidadesRelaciones=comboBox;
        this.diagrama=diagrama;
        this.textoAtributo=textoAtributo;
        this.destinoAtributo=destino;
            crearEntidad=false;
            moverFiguras = false; 
            crearRelacion=false;
            crearAtributo = true;
            crearHerencia = false;

    }    

    @FXML
    private void ventanaModificarDiagrama() throws IOException{
        if(!diagrama.getEntidades().isEmpty()){
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root1 = (AnchorPane)loader.load(getClass().getResource("ModificarDiagrama.fxml").openStream());
            ModificarDiagramaController instanciaControlador = (ModificarDiagramaController)loader.getController();
            instanciaControlador.recibirParametros(controlador,diagrama);
            Scene scene = new Scene(root1);
            stage.setScene(scene);
            stage.alwaysOnTopProperty();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();              
        }
        else{
            mensaje("Debe crear una entidad como mínimo para poder\n"
                    + "             Modificar o eliminar");
        }        
    }
    public void creacionHerencia(Entidad padre,ArrayList<Entidad> hijas,String tipo){
        herenciaAuxiliar = new Herencia(padre,hijas,tipo,0,0);
        crearEntidad=false;
        moverFiguras = false; 
        crearRelacion=false;
        crearAtributo = false;
        crearHerencia = true;
    }

    public void modificarDiagrama(Diagrama diagrama){
        this.diagrama=diagrama;
        this.actualizarPanel();
    }



    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controlador = this;

        
        
        
        
        

    }  
    
    
}
