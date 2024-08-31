/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras.nuevas;

/**
 *
 * @author giuli
 */
public class NodoAVL {
    //ATRIBUTOS
    private Comparable elem;
    private NodoAVL izq;
    private NodoAVL der;
    private int altura;
    
    //CONSTRUCTOR
    public NodoAVL(Comparable elemento,NodoAVL izquierdo, NodoAVL derecho){
        elem= elemento;
        izq= izquierdo;
        der= derecho;
        altura= 0;
    }
      public void setElem(Comparable elem) {
        this.elem = elem;
    }
      
      public Comparable getElem(){
          return elem;
      }
    
    public int getAltura() {
        return altura;
    }
    
    public void recalcularAltura() {
        this.altura = alturaAux(this); 
    }
    
    private int alturaAux(NodoAVL nodo) {
        int alt = 0, altIzq = 0, altDer = 0;
        if (nodo != null) {
            if (nodo.getIzquierdo() != null) {
                altIzq = altIzq + alturaAux(nodo.getIzquierdo()) + 1;
            }
            if (nodo.getDerecho() != null) {
                altDer = altDer + alturaAux(nodo.getDerecho()) + 1;
            }
            if (altIzq > altDer) {
                alt = altIzq;
            } else {
                alt = altDer;
            }
        }
        return alt;
    }
    
    public NodoAVL getIzquierdo() {
        return izq;
    }
    
    public void setIzquierdo(NodoAVL izquierdo) {
        this.izq = izquierdo;
    }
    
    public NodoAVL getDerecho() {
        return der;
    }
    
    public void setDerecho(NodoAVL derecho) {
        this.der = derecho;
    }
}
    
    
    
    
    

