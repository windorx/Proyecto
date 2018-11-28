/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareaalgoritmos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author 19998211
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File file = new File("datos.txt");

        FileReader fileR = null;
        BufferedReader file2 = null;
        ArrayList<Integer> datos = new ArrayList<>();
        try {
            fileR = new FileReader(file);
            file2 = new BufferedReader(fileR);


        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el archivo "+file.getName());
        }
        int i=0;
        try {
            while(file2.readLine() != null)
                {
                    
                    datos.add(Integer.parseInt(file2.readLine()));
                    
                    i++;
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        for(i=0; i<datos.size();i++){
            System.out.println(datos.get(i));
        }*/
        ArrayList<Integer> numeros = new ArrayList<>();
        for(i=0;i<500;i++){
            numeros.add(i);
        }
        encontrarValor(numeros,30);
        
    }
    public static void encontrarValor(ArrayList<Integer> arreglo, int numero){
          int medio = arreglo.size()/2;
          System.out.println(medio);
          boolean encontrado = false;
          while (encontrado ==false){
              System.out.println(medio);
              if(arreglo.get(medio)>= numero){
                  
                  if(arreglo.get(medio)==numero){
                      System.out.println("encontrado");
                      encontrado = true;
                  }
                  else{
                      medio = medio/2;
                  }
              }
              else{
                  medio = (medio + medio/2);
              }
          }

          
    }
}
