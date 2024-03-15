package Estructuras.testt;

import Estructuras.lineales.dinamicas.Cola;
import Estructuras.lineales.dinamicas.Cola;

public class TestColaD {

    public static void main(String[] args) {
        Cola colaa = new Cola();
        System.out.println("Esta vacia??  " + colaa.esVacia());
        System.out.println("Frente: " + colaa.obtenerFrente());
        for (int i = 1; i <= 10; i++) {
            colaa.poner(i);
        }
        
        System.out.println("Cola Original:  " + colaa.toString());
        System.out.println("que saco??");
        colaa.sacar();
        System.out.println(colaa.toString());
        System.out.println("Frente: " + colaa.obtenerFrente());
        System.out.println("Esta vacia??  " + colaa.esVacia());
        System.out.println("Cola Clonada???? : " + (colaa.clone()).toString());
        colaa.clone().sacar();
        System.out.println("Sacar del clone:  " + colaa.toString());
        
          System.out.println("Cola Clonada???? : " + (colaa.clone()).toString());
          System.out.println("Cola Original:  " + colaa.toString());
        System.out.println("Cola Actual:  " + colaa.toString()); 
        colaa.vaciar();
        System.out.println("Cola vaciada paaa: "+colaa.toString());
        
        
    }
}

    
