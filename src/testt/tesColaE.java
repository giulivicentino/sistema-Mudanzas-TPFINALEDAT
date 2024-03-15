/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testt;

import lineales.estaticas.Cola;
import lineales.estaticas.Cola;

/**
 *
 * @author Giuli Vicentino
 */
public class tesColaE {

    public static void main(String[] args) {

        Cola unaCola = new Cola();
        for (int i = 1; i < 8; i++) {
            unaCola.poner(i);
        }

        System.out.println("cola original: " + unaCola.toString());
        System.out.println("frnte: " + unaCola.obtenerFrente());
        System.out.println("pudo sacar? " + unaCola.sacar());
        System.out.println("nuevo frente: " + unaCola.obtenerFrente());
        System.out.println("cadena sin el frente: " + unaCola.toString());
        System.out.println("esta vacia?: " + unaCola.esVacia());
        System.out.println("Cola original:" + unaCola.toString());
        System.out.println("Cola clonada: " + (unaCola.clone()).toString());
        System.out.println("MAY");
        //    System.out.println("VACIAAADO");
        //    unaCola.vaciar();
        //     System.out.println("cola vaciada: "+unaCola.toString());
        //     System.out.println("VERDADERAMENTE esta vacia?: "+unaCola.esVacia());
    }
}
