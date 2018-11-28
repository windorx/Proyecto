/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5;

/**
 *
 * @author Henry
 */
public abstract class Legislador extends Persona{
    //atributos
	public String provinciaQueRepresenta; 
	
	public Legislador(String provinciaQueRepresenta){
		this.provinciaQueRepresenta  = provinciaQueRepresenta; 
		
	}
	
	public abstract String getCamaraEnQueTrabaja();
    
}
