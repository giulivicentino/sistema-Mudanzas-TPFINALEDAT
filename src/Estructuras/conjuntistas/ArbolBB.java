package Estructuras.conjuntistas;

import java.util.HashSet;
import java.util.Set;
import Estructuras.lineales.dinamicas.Lista;

public class ArbolBB {

    private NodoABB raiz;

    public ArbolBB() {
        this.raiz = null;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public boolean insertar(Comparable elem) {
        boolean exito = true;
        if (esVacio()) {//CASO 1 cuando el arbol este vacio
            this.raiz = new NodoABB(elem, null, null);
        } else {
            exito = insertarAux(raiz, elem);
        }
        return exito;
    }

    private boolean insertarAux(NodoABB nodo, Comparable elem) {
        boolean exito = true;

        if (nodo.getElem().compareTo(elem) == 0) { //para que no busque mas nada donde insertarlo si es que ya esta en el arbol el elemento
            exito = false;
        } else {
            if (nodo.getElem().compareTo(elem) > 0) { //si el elemento es MENOR a la raiz, recorre subarbol izq 
                if (nodo.getIzquierdo() != null) { // si tiene hijo izquierdo 
                    exito = insertarAux(nodo.getIzquierdo(), elem);
                } else { // si no tiene hijo izq ya lo inserta ahi
                    nodo.setIzquierdo(new NodoABB(elem, null, null));
                }
            } else { //si el elemento es MAYOR que la raiz, recorre subarbol derecho
                if (nodo.getDerecho() != null) {
                    exito = insertarAux(nodo.getDerecho(), elem);
                } else { // si no tiene hijo derecho, lo inserta
                    nodo.setDerecho(new NodoABB(elem, null, null));
                }

            }
        }
        return exito;
    }

    public boolean pertenece(Comparable elem) {
        boolean exito;
        if (raiz.getElem().compareTo(elem) == 0) {
            exito = false;
        } else {
            exito = perteneceAux(raiz, elem);
        }
        return exito;
    }

    private boolean perteneceAux(NodoABB nodo, Comparable elem) {
        boolean exito = false;
        int comparado = nodo.getElem().compareTo(elem);
        if (comparado == 0) {
            exito = true;
        } else {
            if (comparado < 0) {
                if (nodo.getIzquierdo() != null) {
                    exito = perteneceAux(nodo.getIzquierdo(), elem);
                }
            } else {
                if (nodo.getDerecho() != null) {
                    exito = perteneceAux(nodo.getDerecho(), elem);
                }
            }
        }
        return exito;
    }

    public Lista listar() {
        Lista lissta = new Lista();
        listarAux(raiz, lissta);
        return lissta;
    }

    private void listarAux(NodoABB nodo, Lista lista) {
        if (nodo != null) {
            listarAux(nodo.getIzquierdo(), lista);
            lista.insertar(nodo.getElem(), lista.longitud() + 1);
            listarAux(nodo.getDerecho(), lista);
        }

    }

    public Comparable minimoElem() {
        Comparable elem = null;
        if (raiz != null && raiz.getIzquierdo() == null) {
            elem = raiz.getElem();
        } else {
            elem = minimoAux(raiz);
        }
        return elem;
    }

    private Comparable minimoAux(NodoABB nodo) {
        Comparable elem = null;
        if (nodo != null) {
            if (nodo.getIzquierdo() == null) {
                elem = nodo.getElem();
            } else {
                elem = minimoAux(nodo.getIzquierdo());
            }
        }
        return elem;
    }

    public Comparable maximoElem() {
        Comparable elem = null;
        if (raiz != null && raiz.getDerecho() == null) {
            elem = raiz.getElem();
        } else {
            elem = maximoAux(raiz);
        }
        return elem;
    }

    private Comparable maximoAux(NodoABB nodo) {
        Comparable elem = null;
        if (nodo != null) {
            if (nodo.getDerecho() == null) {
                elem = nodo.getElem();
            } else {
                elem = maximoAux(nodo.getDerecho());
            }
        }
        return elem;
    }

    /*
    public String toString() {
        String cad = " ";
        if (this.raiz == null) {
            cad = "Arbol vacio";
        } else {
            cad = toStringAux(this.raiz);
        }
        return cad;
    }

    private String toStringAux(NodoABB nodo) {
        String cad = "";
        NodoABB izq = nodo.getIzquierdo();
        NodoABB der = nodo.getDerecho();
        if (nodo != null) {
            cad = "nodo: " + nodo.getElem().toString();
            if (izq != null) {
                cad = cad + " HI: " + izq.getElem().toString();
            } else {
                cad = cad + " HI: --- ";
            }
            if (der != null) {
                cad = cad + " HD: " + der.getElem().toString() + "\n";
            } else {
                cad = cad + " HD: --- " + "\n";
            }
        }
        if (izq != null) {
            cad = cad + toStringAux(izq);
        }
        if (der != null) {
            cad = cad + toStringAux(der);
        }

        return cad;
    }
     */
    public String toString() {
        String toString = toStringPR(this.raiz);
        return toString;
    }

    private String toStringPR(NodoABB nodo) {
        String toString = "Arbol vacio";
        if (nodo != null) {
            toString = nodo.getElem().toString();
            NodoABB hijoIzq = nodo.getIzquierdo();
            NodoABB hijoDer = nodo.getDerecho();
            if (hijoIzq != null) {
                toString = toString + ", H.I: " + hijoIzq.getElem().toString();

            } else {
                toString = toString + ", H.I: -";
            }
            if (hijoDer != null) {
                toString = toString + ", H.D: " + hijoDer.getElem().toString() + "\n";
            } else {
                toString = toString + ", H.D: -\n";
            }

            if (hijoIzq != null) {
                toString = toString + toStringPR(hijoIzq);
            }

            if (hijoDer != null) {
                toString = toString + toStringPR(hijoDer);
            }
        }
        return toString;
    }

    public boolean eliminar(Comparable elem) {
        boolean exito = true;
        if (this.raiz != null) {
            exito = eliminarAux(raiz, null, elem);
        }
        return exito;
    }

    private boolean eliminarAux(NodoABB nodo, NodoABB padre, Comparable elem) {
        boolean exito = false;

        if (elem.compareTo(nodo.getElem()) == 0) { // SI ENCONTRO EL ELEMENTO EN NODO
            //CASO 1: ES HOJA
            System.out.println("ENCONTRO EL NODO CON EL ELEM");
            if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                exito = caso1(nodo, padre, elem);
            } else {
                if ((nodo.getIzquierdo() != null && nodo.getDerecho() == null) || (nodo.getIzquierdo() == null && nodo.getDerecho() != null)) { // si tiene un hijo izq o un hijo der
                    exito = caso2(nodo, padre, elem);
                }

            }

            //SI NO ESTA PARADO EN EL ELEMENTO
        } else if (elem.compareTo(nodo.getElem()) < 0) {  // si el elemento en menor va al subIzq
            if (nodo.getIzquierdo() != null) {
                System.out.println("busca por izq");
                exito = eliminarAux(nodo.getIzquierdo(), nodo, elem);
            }
        } else { // si el elemento es mayor, va a subDer
            if (nodo.getDerecho() != null) {
                System.out.println("busca por derecho");
                exito = eliminarAux(nodo.getDerecho(), nodo, elem);
            }
        }
        return exito;
    }

    private boolean caso1(NodoABB nodo, NodoABB padre, Comparable elem) {
        boolean exito = true;
        if (padre == null) { //si se quiere eliminar el  UNICO elemento del arbol
            this.raiz = null;
        } else {
            System.out.println("ARRANCO A ELIMINARLO");
            if (nodo.getElem().compareTo(padre.getElem()) < 0) {//significa q n es hijo izq
                padre.setIzquierdo(null);
            } else {
                padre.setDerecho(null);
            }
            if (nodo.getElem().compareTo(padre.getElem()) == 0) {
                exito = false;
            }
        }
        return exito;
    }

    private boolean caso2(NodoABB nodo, NodoABB padre, Comparable elem) {
        boolean exito = true;
        System.out.println("entra en caso2");
        if (padre == null) {
            if (nodo.getIzquierdo() != null && nodo.getDerecho() == null) {
                this.raiz = nodo.getIzquierdo();
            } else if (nodo.getIzquierdo() == null && nodo.getDerecho() != null) {
                this.raiz = nodo.getDerecho();
            }
        } else {
            // si nodo es HI de padre
            if (nodo.getElem().compareTo(padre.getElem()) < 0) {
                System.out.println("si nodo es HI de padre");
                if (nodo.getIzquierdo() != null) { // si el nodo tiene HI
                    padre.setIzquierdo(nodo.getIzquierdo());
                } else if (nodo.getDerecho() != null) {// si el nodo tiene HD
                    padre.setIzquierdo(nodo.getDerecho());
                }
                //si el nodo es HD de padre
            } else if ((nodo.getElem().compareTo(padre.getElem()) > 0)) {
                if (nodo.getIzquierdo() != null) { // si el nodo tiene HI
                    System.out.println("el nodo tien HI");
                    padre.setDerecho(nodo.getIzquierdo());
                } else if (nodo.getDerecho() != null) {// si el nodo tiene HD
                    System.out.println(" el nodo tiene HD");
                    padre.setDerecho(nodo.getDerecho());
                }
            }
        }
        return exito;
    }

//------------------SIMULACRO----------------------------------------------------------------------------------------------------------------------------
    public void eliminarMinimo() {
        eliminarMinAux(raiz);
    }

    private void eliminarMinAux(NodoABB nodo) {
        NodoABB padre = null;
        if (nodo != null) {

            //ENCUENTRO EL NODO MENOR Y SU PADRE
            while (nodo.getIzquierdo() != null) { //recorre solo subIZQ y consigue el minimo y su padre
                padre = nodo;
                nodo = nodo.getIzquierdo();
            }
            //si significa que la raiz no tiene hijo izq
            if (padre == null) {
                if (raiz.getDerecho() != null) { //si la raiz tiene HD
                    raiz = raiz.getDerecho();
                } else { //si la raiz NO tiene HD
                    this.raiz = null;
                }
            } else { // si si recorrio el arbol izq
                if (nodo.getDerecho() != null) {//si es hoja y tiene HD
                    padre.setIzquierdo(nodo.getDerecho());
                } else { //si es hoja y NO tiene HD
                    padre.setIzquierdo(null);
                }
            }
        }
    }
    
    public eliminarElemAnterior(Object elem){
        
    }
    
    
    
    
    
}
