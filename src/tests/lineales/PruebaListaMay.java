package tests.lineales;

import lineales.dinamicas.Lista;
import lineales.dinamicas.Pila;
import lineales.dinamicas.Cola;
import java.util.Scanner;
public class PruebaListaMay {
    public static void main(String[] args) {
        Lista listaOriginal = new Lista();
        listaOriginal.insertar(9,1);
        listaOriginal.insertar(6,2);
        listaOriginal.insertar(5,3);
        listaOriginal.insertar(8,4);
        listaOriginal.insertar(0,5);
        listaOriginal.insertar(9,6);
        listaOriginal.insertar(6,7);
        listaOriginal.insertar(5,8);
        listaOriginal.insertar(8,9);
        listaOriginal.insertar(0,10);
        listaOriginal.insertar(8,11);
        listaOriginal.insertar(5,12);
        listaOriginal.insertar(6,13);
        //listaOriginal.insertar(9,14);
        System.out.println(listaOriginal.toString());
        System.out.println(comprobar(listaOriginal));
        //System.out.println(sacarLongitud(listaOriginal));
        //System.out.println("Lista original: "+listaOriginal.toString());
        //System.out.println("Lista invertida: "+invertirLista(listaOriginal).toString());
    }
    /*
    comprobar : recibe una lista L1 cargada con dígitos (números enteros de 0 a 9) y verica si los
    elementos que contiene tienen la forma cadena0cadena0cadena*
      (donde cadena*
    es cadena invertida).
       Ej: si L1=[9,6,5,0,9,6,5,0,5,6,9], cadena=965, luego cadena*=569, entonces la lista L1 cumple con la
    condición deseada.
    Atención: la longitud de cada cadena no se conoce de antemano, hay que identicarla por la primera
    posición de 0 en la lista.
    Nota: Utilizar una Pila y una Cola como estructuras auxiliares
    */
    
    public static int sacarLongitud(Lista listaOriginal){
        int contador=0;
        Object aux=0;
        while(listaOriginal.recuperar(contador)!=aux){
            contador++;
        }
        return contador-1;
    }
    
    public static boolean comprobarLongs(Lista listaOriginal){
        int contador=0,i=1,longCadena=sacarLongitud(listaOriginal);
        boolean resultado = true;
        Object aux=0;
        while(i<=listaOriginal.longitud() && resultado){
            contador++;
            if(i==listaOriginal.longitud()){
                if(contador!=longCadena){
                    resultado = false;
                }
            }
            if(listaOriginal.recuperar(i)==aux) {
                if(contador-1!=longCadena){
                    resultado = false;
                } else {
                    contador = 0;
                }
            }
            
            i++;
        }
        return resultado;
    }
    // Ej: si L1=[9,6,5,0,9,6,5,0,5,6,9]
    public static boolean compararCadenas(Pila cadenaInvertida,Cola cadenaOriginal){
        boolean resultado = true;
        while(resultado && !cadenaInvertida.esVacia()){
            if(cadenaInvertida.obtenerTope()!=cadenaOriginal.obtenerFrente()){
                resultado = false;
            } else {
                cadenaInvertida.desapilar();
                cadenaOriginal.sacar();
            }
        }
        return resultado;
    }
    // Ej: si L1=[9,6,5,0,9,6,5,0,5,6,9]
    public static boolean comprobar(Lista listaOriginal){
        boolean resultado=true;
        Pila tercerCadena = new Pila();
        Cola segundaCadena = new Cola();
        Cola primeraCadena = new Cola();
        int i=1,cantCeros=0;
        Object aux=0;
        if(comprobarLongs(listaOriginal)){
            while(i<=listaOriginal.longitud() && resultado){
                if(listaOriginal.recuperar(i)==aux){
                    cantCeros++;
                }
                if(listaOriginal.recuperar(i)!=aux){
                    //deberia ser un switch
                    if(cantCeros==0){
                        primeraCadena.poner(listaOriginal.recuperar(i));
                    } else if(cantCeros==1){
                        segundaCadena.poner(listaOriginal.recuperar(i));
                    } else if(cantCeros==2){
                        tercerCadena.apilar(listaOriginal.recuperar(i));
                    }
                }
                i++;
            }
            resultado = primeraCadena.equals(segundaCadena);
            if(resultado){
                resultado = compararCadenas(tercerCadena, primeraCadena);
            }
        } else {
            resultado = false;
        }
        
        return resultado;
    }
   
    public static Lista invertirLista (Lista listaOriginal){
        Lista listaInvertida = new Lista();
        int i,largo = listaOriginal.longitud(),j=1;
        Object elemento;
        for (i=largo; i>=1; i--) {
             elemento = listaOriginal.recuperar(i);
             listaInvertida.insertar(elemento,j);
             j++;
         }
        return listaInvertida;
    }
    
}
