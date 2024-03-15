package testt;

import lineales.estaticas.Pila;
import lineales.estaticas.Pila;
import java.util.Scanner;
import lineales.estaticas.Pila;

public class testPila {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numero;
        Pila pilita = new Pila();
        //pila del 1 al 10 automatica
       for (int i = 1; i <= 10; i++) {
            pilita.apilar(i);
        }

        //PARA CARGAR EL ARREGLO MANUALMENTE
/*
        do {
             System.out.println("Por favor ingrese un número");
             numero = sc.nextInt();
         } while (pilita.apilar(numero));
        */
           System.out.println("pila original: " + pilita.toString());
        //desapila
        //       pilita.desapilar();
//        System.out.println("ultimo ufuera xd " + pilita.toString());
        //obtener tope
//        System.out.println("El elemento tope es: " + pilita.obtenerTope());
//        System.out.println("tope " + pilita.obtenerTope());
        //       pilita.vaciar();
        //       System.out.println("pila vacia? " + pilita.toString());
        //       System.out.println("la pila esta vacia? " + pilita.esVacio());
        //    System.out.println("CLON" + pilita.clone());
        //      System.out.println("original" + pilita.toString());

        /*creas pila2 y es clonado(se clona por que no hay acceso al arreglo), 
        se crea pila3 y ahi se desa´ila al revez la pila clonada(o la pila 1 tmb tendria q funcionar),
        despues con un mientras hay que igualar las pilas para ver si son iguales, 
        la condicion va a ser que el numero sea igual, si no se cumple ya no es capicua*/
        
        System.out.println("pila original: " + pilita.toString());
         System.out.println("pila invertida?"+invertirPila(pilita));
         if(esCapicua(pilita)){
             System.out.println("Si es capicua");
         }else{
             System.out.println("No es capicua");
         }
        
    }
    public static Pila invertirPila(Pila pilita) {
        Pila pilaInvertida=new Pila();
        Pila pilaClon=pilita.clone();

        do {
        pilaInvertida.apilar(pilaClon.obtenerTope());
        }
        while (pilaClon.desapilar());

        return pilaInvertida;
    }
    public static boolean esCapicua(Pila pilita) {
        Pila pilaInversa=invertirPila(pilita.clone());
        Pila pilaAux=pilita.clone();
        boolean resultado;

        do {
        resultado=pilaInversa.obtenerTope()==pilaAux.obtenerTope();
        }
        while (resultado && (pilaAux.desapilar() && pilaInversa.desapilar()));
        return resultado;
    }
}

