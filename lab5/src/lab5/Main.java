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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Legislador dip = new Diputado("Camara 1");
        Legislador sen = new Senador("Camara 2");

        System.out.println(dip.getCamaraEnQueTrabaja());
        System.out.println(sen.getCamaraEnQueTrabaja());
        // TODO code application logic here
    }
    
}
