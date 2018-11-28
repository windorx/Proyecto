/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;

/**
 *
 * @author Los olvidados.
 */
public class Diagrama {
    private ArrayList<Entidad> entidades;
    private ArrayList<Relacion> relaciones;
    private ArrayList<Atributo> atributos;
    private ArrayList<Herencia> herencias;
    private int numero=0;
  
    public Diagrama() {
        
        entidades = new ArrayList<>();
        relaciones = new ArrayList<>();
        atributos = new ArrayList<>();
        herencias = new ArrayList<>();
        
    }   
    public void agregarEntidades(ArrayList<Entidad> a){
        entidades =a;
    }
    public void agregarHerencias(ArrayList<Herencia> a){
        herencias =a;
    }   
    public void agregarRelaciones(ArrayList<Relacion> a){
        relaciones =a;
    }  
    public void agregarAtributos(ArrayList<Atributo> a){
        atributos =a;
    }          
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public ArrayList<Herencia> getHerencias() {
        return herencias;
    }
    
    public  ArrayList<Entidad> getEntidades() {
        return entidades;
    }

    public  ArrayList<Relacion> getRelaciones() {
        return relaciones;
    }
    public void agregarEntidad(Entidad nuevo){
        this.getEntidades().add(nuevo);
    }
    
    public Entidad obtenerEntidad(int i){
        return (Entidad)entidades.get(i);
    }
    public Relacion obtenerRelacion(int i){
        return relaciones.get(i);
    }
    public int cantidadEntidad(){
        return this.entidades.size();
    }
    public int cantidadRelacion(){
        return this.getRelaciones().size();
    }
    public void agregarRelacion(Relacion nuevo){
        this.getRelaciones().add(nuevo);
    }
    
    
    public void eliminarRelacion(int i){
        this.relaciones.remove(i);
    }

    public ArrayList<Atributo> getAtributos() {
        return atributos;
    }
    public void eliminarAtributo(String origen,Atributo atributo){   
    }
    
    
    public void eliminarEntidad(Entidad entidad){
        for(int i = 0; i<this.getEntidades().size();i++){
            if(entidad.equals(this.entidades.get(i))){
                this.entidades.remove(i);
            }            
        }
        for(int i = 0; i<this.relaciones.size();i++){
            if(this.relaciones.get(i).getEntidad().size()==1 && this.relaciones.get(i).getEntidad().get(0).equals(entidad)){
                this.relaciones.remove(i);
            }
            else{
                for(int j = 0; j<this.getRelaciones().get(i).getEntidad().size();j++){
                    if(this.relaciones.get(i).getEntidad().get(j).equals(entidad)){
                        this.relaciones.get(i).eliminarEntidad(j);
                    }
                }
            }
        }
        
        for(int i = 0; i<this.herencias.size();i++){
            if(this.herencias.get(i).getEntidadPadre().equals(entidad)){
                this.herencias.remove(i);
                i=0;
            }
            if(this.herencias.get(i).getEntidadesHijas().size()==1 &this.herencias.get(i).getEntidadesHijas().get(0).equals(entidad)){
                this.herencias.remove(i);
                i=0;
            }            
        }        
        for(int i = 0; i<this.herencias.size();i++){
            for(int j =0; j<this.herencias.get(i).getEntidadesHijas().size();j++){
                if(this.herencias.get(i).getEntidadesHijas().get(j).equals(entidad)){
                    this.herencias.get(i).eliminarEntidadHija(i);
                }
            }
        }
        
        
        
    }
    public void eliminarRelacion(Relacion relacion){
        for(int i = 0; i< this.relaciones.size();i++){
            if(this.relaciones.get(i).equals(relacion)){
                this.relaciones.remove(i);
            }
        } 
    }
    public void eliminarHerencia(Herencia herencia){
        for(int i = 0; i< this.herencias.size();i++){
            if(this.herencias.get(i).equals(herencia)){
                this.herencias.remove(i);
            }
        }         
    }
    
    
    
    
    
}
