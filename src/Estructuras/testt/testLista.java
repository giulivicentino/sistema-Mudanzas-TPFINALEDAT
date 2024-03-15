package Estructuras.testt;

import Estructuras.lineales.dinamicas.Lista;

public class testLista {

    public static void main(String[] args) {
        Lista listita = new Lista();
        System.out.println("Esta vacia??  " + listita.esVacia());
       
       /* for (int i = 1; i <= 10; i++) {
            listita.insertar(i, i);
        }*/
         listita.insertar(1, 1);
          listita.insertar(1, 2);
           listita.insertar(2, 3);
            listita.insertar(3, 4);
             listita.insertar(1, 5);
           
         System.out.println("LISTA: "+listita.toString()); 
         
        // listita.eliminar(1);
       //  System.out.println(listita.toString());
         listita.eliminarApariciones(1);
         System.out.println("LISTA SIN 1: "+listita.toString()); 
         
        System.out.println("Posicioniones multiplo de 3: "+listita.obtenerMultiplos(3).toString());
        System.out.println("CLONADA? "+listita.clone().toString());
        System.out.println("que longitttuud ttiene?: (tiene q decir 10) " + listita.longitud());
        System.out.println(listita.toString());
        listita.eliminar(10);
        System.out.println("se sale la 10ma??? " + listita.toString());
        System.out.println("que longitttuud ttiene?: " + listita.longitud());
        System.out.println("Recupera el elemento 2?:" + listita.recuperar(2));
        System.out.println("Donde esta el elemento 500?: "+listita.localizar(500));
        
        
        
        listita.vaciar();
        System.out.println("Esta vacia che???" +listita.toString());
    }
}
