package tests.lineales;

import lineales.dinamicas.Lista;
import lineales.dinamicas.Pila;
import lineales.dinamicas.Cola;
import java.util.Scanner;

public class recu1ro {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Cola c1 = new Cola();
        System.out.println("5mod4:"+ 5%4);
        /*//EJERCICIO INVERTIR CON VOCALES
        c1.poner('G');
        c1.poner('I');
        c1.poner('U');
        c1.poner('#');
        c1.poner('g');
        c1.poner('g');
        c1.poner('g');
        c1.poner('#');
        c1.poner('C');
        c1.poner('A');
        c1.poner('S');
        c1.poner('A');
        System.out.println("Cola envidada: " + c1.toString());
        System.out.println("lista generada: ");
        System.out.println(invertirConVocales(c1).toString());
         */
//EJERCICIO generarOtraLista
        Lista lis = new Lista();
        lis.insertar('A', 1);
        lis.insertar('B', 2);
        lis.insertar('#', 3);
        lis.insertar('C', 4);
        lis.insertar('#', 5);
        lis.insertar('D', 6);
        lis.insertar('E', 7);
        lis.insertar('F', 8);
        System.out.println("Lista envidada: " + lis.toString());
        System.out.println("lista generada: ");
        System.out.println(generarOtraLista(lis).toString());
    }

    public static Lista invertirConVocales(Cola c1) {
        Lista resultado = new Lista();
        // resultado.insertar("giuli", 1);
        Pila pilaVocal = new Pila();
        Cola colaNormal = new Cola();
        Lista listaNormal = new Lista();
        Cola q = c1.clone();
        boolean tieneVocal = false;
        q.poner('#');
        int k = 1;

        while (!q.esVacia()) {
            Object elem = q.obtenerFrente();
            if (!elem.equals('#')) { //recorre toda la cola y va llenando la pila y lista
                pilaVocal.apilar(elem);
                colaNormal.poner(elem);
                if (esVocal(elem)) {
                    tieneVocal = true;
                }
            } else { //comienza a que tiene q agregar al resultado
                if (tieneVocal) {
                    while (!pilaVocal.esVacia()) {
                        resultado.insertar(pilaVocal.obtenerTope(), k);
                        pilaVocal.desapilar();
                        k++;
                    }
                    resultado.insertar('#', k);
                    k++;
                } else {
                    pilaVocal.vaciar();
                    System.out.println("cola " + colaNormal.toString());
                    System.out.println(colaNormal.esVacia());
                    while (!colaNormal.esVacia()) {
                        resultado.insertar(colaNormal.obtenerFrente(), k);
                        colaNormal.sacar();
                        k++;
                    }
                    resultado.insertar('#', k);
                    k++;
                }
                //PARA CAMBIAR POR PALABRA
                tieneVocal = false;
                colaNormal.vaciar();
                pilaVocal.vaciar();
            }
            q.sacar();
        }

        return resultado;
    }

    public static boolean esVocal(Object elem) {
        boolean vocal = false;
        if (elem.equals('a') || elem.equals('e') || elem.equals('i') || elem.equals('o') || elem.equals('u') || elem.equals('A') || elem.equals('E') || elem.equals('I') || elem.equals('O') || elem.equals('U')) {
            vocal = true;
        }
        return vocal;
    }

    /*
    generarOtraListta(Lista l)
    AB#C#DEF -> BAAB#CC#FEDDEF
    pilaInv+colaNormal
     */
    public static Lista generarOtraLista(Lista lis) {
        Lista resultado = new Lista();
        Cola cola1 = new Cola();
        Pila pila1 = new Pila();
        lis.insertar('#', lis.longitud() + 1);
        System.out.println("Lista? " + lis.toString());
        System.out.println("Lista resultado? " + resultado.toString());
        int i = 1, k = 1, longitud = lis.longitud();
        System.out.println("esta vacia???" + lis.esVacia());
       

        while (i <= longitud) {
          Object elem = lis.recuperar(i);
            if ((char) elem != '#') { //llena cola y pila con la palabra
                cola1.poner(elem);
                pila1.apilar(elem);
                System.out.println("cola: "+cola1.toString());
                System.out.println("pila: "+pila1.toString());
            } else {
                while (!pila1.esVacia()) {
                    resultado.insertar(pila1.obtenerTope(), k);
                    pila1.desapilar();
                    k++;
                }
                while (!cola1.esVacia()) {
                    resultado.insertar(cola1.obtenerFrente(), k);
                    cola1.sacar();
                    k++;
                }
                resultado.insertar('#', k);
                k++;
             cola1.vaciar();
            pila1.vaciar();
            }
           
            i++; //itera en la lista original
        }

        return resultado;
    }

}
