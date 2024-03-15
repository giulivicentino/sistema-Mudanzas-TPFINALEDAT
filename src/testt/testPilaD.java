package testt;

import java.util.Scanner;
import lineales.dinamicas.Pila;

public class testPilaD {
       public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numero;
        Pila pilita = new Pila();
        Pila pila2= new Pila();
        //pila del 1 al 10 automatica
       for (int i = 1; i <= 10; i++) {
            pilita.apilar(i);
        }
         for (int i = 1; i <= 9; i++) {
            pila2.apilar(i);
        }
           System.out.println("pila original:  "+pilita.toString());
        System.out.println("pila 2:  "+pila2.toString());
           System.out.println("Son iguales??? "+ pilita.equals(pila2));
       }
}
