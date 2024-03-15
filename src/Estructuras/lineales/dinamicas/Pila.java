package Estructuras.lineales.dinamicas;

import Estructuras.lineales.dinamicas.Nodo;

public class Pila {

    private Nodo tope;

    public Pila() {
        this.tope = null;
    }

    public boolean apilar(Object elem) {
        Nodo nuevo = new Nodo(elem, this.tope);
        this.tope = nuevo;
        return true;
    }

    public boolean desapilar() {
        boolean exito = false;
        if (this.tope != null) {

            this.tope = this.tope.getEnlace();
            exito = true;
        }
        return exito;
    }

    public Object obtenerTope() {
        Object elemento = null;
        if (this.tope != null) {

            elemento = this.tope.getElem();
        }
        return elemento;
    }

    public boolean esVacia() {
        boolean estaVacia = false;
        if (this.tope == null) {
            estaVacia = true;
        }
        return estaVacia;
    }

    public void vaciar() {
        this.tope = null;
    }

    public Pila clone() {
        Pila clon = new Pila();
        clonarxd(clon, this.tope);
        return clon;
    }

    private void clonarxd(Pila clon, Nodo otroEnlace) {
        if (otroEnlace != null) {
            clonarxd(clon, otroEnlace.getEnlace());
            clon.tope = new Nodo(otroEnlace.getElem(), clon.tope);
        }
    }

    @Override
    public String toString() {
        String cadena = "";
        Nodo aux = this.tope;
        if (this.tope == null) {
            cadena = "La pila está vacía";
        } else {
            while (aux != null) {
                cadena = aux.getElem().toString() + "," + cadena;
                aux = aux.getEnlace();
            }
        }
        return "[" + cadena + "]";
    }

    public boolean equals(Pila pilaP) {
        boolean resultado = false;
        if (this.tope != null) {
            resultado = equalsAux(this.tope, pilaP.tope);
        }
        return resultado;
    }

        private boolean equalsAux(Nodo nOriginal, Nodo nOtro){
        boolean resultado=false;
        if(nOriginal!=null){
            if(nOtro!=null){
                if(nOriginal.getElem().equals(nOtro.getElem())){
                resultado = equalsAux(nOriginal.getEnlace(),nOtro.getEnlace());
               
            } else {
                resultado = false;
            }
        } else {
            resultado = nOtro == null;
        }
        return resultado;
    }

//  if(this.tope != null){
    //      nuevaPila.setElem(this.tope.getElem());

