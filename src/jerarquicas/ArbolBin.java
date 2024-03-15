package jerarquicas;

import lineales.dinamicas.Lista;
import jerarquicas.NodoArbol;

public class ArbolBin {

    private NodoArbol raiz;

    public ArbolBin() {
        this.raiz = null;
    }

    public boolean insertar(Object elemNuevo, Object elemPadre, char lugar) {
        boolean exito = true;
        if (this.raiz == null) { //para cuando esta vacio asi hace que eso sea la raiz
            this.raiz = new NodoArbol(elemNuevo, null, null);
        } else {
            NodoArbol nPadre = obtenerNodo(this.raiz, elemPadre); //si el arbol no esta vacio(raiz no nula), busca el nodo padre
            if (nPadre != null) {  //si el nodo padre existe y el lugar no esta ocupado lo pone, si no, da error
                if ((lugar == 'I' || lugar == 'i') && nPadre.getIzquierdo() == null) {
                    nPadre.setIzquierdo(new NodoArbol(elemNuevo, null, null));
                } else if ((lugar == 'D' || lugar == 'd') && nPadre.getDerecho() == null) {
                    nPadre.setDerecho(new NodoArbol(elemNuevo, null, null));
                } else {
                    exito = false;
                }
            } else {
                exito = false;
            }
        }
        return exito;
    }

    public boolean esVacio() {
        return this.raiz != null;
    }

    public int altura() {
        int alturita = -1;
        if (this.raiz != null) {
            alturita = alturaAux(this.raiz);
        }
        return alturita;
    }

    public int alturaAux(NodoArbol nodo) {
        int alt = 0, alt2 = 0, altIzq = 0, altDer = 0;
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

    public int nivel(Object elem) {
        int niv = -1;
        if (this.raiz != null) {
            niv = nivelAux(this.raiz, elem, 0);
        }
        return niv;
    }

    private int nivelAux(NodoArbol nodo, Object buscado, int profundidad) {
        int nivel = -1;
        if (nodo != null) {
            if (nodo.getElem() != buscado) {
                nivel = nivelAux(nodo.getIzquierdo(), buscado, profundidad + 1);
                if (nivel == -1) { //si siguio sin encontrarlo
                    nivel = nivelAux(nodo.getDerecho(), buscado, profundidad + 1);
                }
            } else {
                nivel = profundidad;
            }
        }
        return nivel;
    }

    public void vaciar() {
        this.raiz = null;
    }
   /*
    @Override
    public ArbolBin clone() {
        // Genera y devuelve un árbol binario que es equivalente (igual 
        // estructura y contenido de los nodos) que el árbol original.
        ArbolBin arbolClon = new ArbolBin();
        if (this.raiz != null) {
            arbolClon.raiz = new NodoArbol(this.raiz.getElem(), null, null);
            cloneAux(arbolClon.raiz, this.raiz);
        }
        return arbolClon;
    }

    private void cloneAux(NodoArbol nodoClon, NodoArbol nodoOriginal) {
        if (nodoOriginal != null) {
            if (nodoOriginal.getIzquierdo() != null) {
                nodoClon.setIzquierdo(new NodoArbol(nodoOriginal.getIzquierdo().getElem(), null, null));
            }
            if (nodoOriginal.getDerecho() != null) {
                nodoClon.setDerecho(new NodoArbol(nodoOriginal.getDerecho().getElem(), null, null));
            }
            cloneAux(nodoClon.getIzquierdo(), nodoOriginal.getIzquierdo());
            cloneAux(nodoClon.getDerecho(), nodoOriginal.getDerecho());
        }
    }  OTRA MANERA DE HACER EL CLONE
*/
  
    
    
    

  
public ArbolBin clone(){
        ArbolBin arbolClon = new ArbolBin();
        if (this.raiz != null) {
            arbolClon.raiz = cloneAux(this.raiz);
        }
        return arbolClon;
    
    }
     private NodoArbol cloneAux(NodoArbol nodoOriginal) {
         NodoArbol nuevo = null;
         if(nodoOriginal!=null){   
             nuevo= new NodoArbol(nodoOriginal.getElem(),  cloneAux(nodoOriginal.getIzquierdo()) ,   cloneAux(nodoOriginal.getDerecho()));
         }
         return nuevo;
     }
    public Object padre(Object buscado) {
        Object elemento = null;
        if (this.raiz != null) {
            if (this.raiz.getElem() != buscado) {
                elemento = obtenerPadre(this.raiz, buscado);
            }
        }
        return elemento;
    }
    private Object obtenerPadre(NodoArbol n, Object buscado) {
        Object resultado = null;
        if (n != null) {
            if (n.getIzquierdo() != null) {
                if (n.getIzquierdo().getElem().equals(buscado)) {
                    resultado = n.getElem();
                }
            }
            if (n.getDerecho() != null && resultado == null) {
                if (n.getDerecho().getElem().equals(buscado)) {
                    resultado = n.getElem();
                }

            }
            if (resultado == null) {
                resultado = obtenerPadre(n.getDerecho(), buscado);
                if (resultado == null) {
                    resultado = obtenerPadre(n.getIzquierdo(), buscado);
                }
            }
        }
        return resultado;
    }

    public Lista listarPreOrden() {
        Lista listaPre = new Lista();
        listarPreOrdenAux(this.raiz, listaPre);
        return listaPre;
    }

    private void listarPreOrdenAux(NodoArbol nodo, Lista lis) {
        if (nodo != null) {
            lis.insertar(nodo.getElem(), lis.longitud() + 1);
            listarPreOrdenAux(nodo.getIzquierdo(), lis);
            listarPreOrdenAux(nodo.getDerecho(), lis);
        }
    }

    public Lista listarInorden() {
        // Retorna una lista con los elementos del árbol en INORDEN 
        Lista lis = new Lista();
        listarInordenAux(this.raiz, lis);
        return lis;
    }

    private void listarInordenAux(NodoArbol n, Lista lis) {
        // método recursivo PRIVADO ya que su parámetro es de tipo NodoArbol
        if (n != null) {
            listarInordenAux(n.getIzquierdo(), lis);
            lis.insertar(n.getElem(), lis.longitud() + 1);
            listarInordenAux(n.getDerecho(), lis);
        }
    }

    public Lista listarPosorden() {
        // Retorna una lista con los elementos del árbol en POSORDEN 
        Lista lis = new Lista();
        listarPosordenAux(this.raiz, lis);
        return lis;
    }

    private void listarPosordenAux(NodoArbol n, Lista lis) {
        // Método recursivo PRIVADO ya que si parámetro es de tipo NodoArbol
        if (n != null) {
            // Primero recorre el hijo izquierdo hasta el nodo hoja, luego visita si tiene el hijo derecho
            listarPosordenAux(n.getIzquierdo(), lis);
            listarPosordenAux(n.getDerecho(), lis);
            lis.insertar(n.getElem(), lis.longitud() + 1);
        }
    }

    // public void
    @Override
    public String toString() {
        String cad = " ";
        if (this.raiz == null) {
            cad = "el arbol esta vacio";
        } else {
            cad = toStringAux(this.raiz);
        }
        return cad;
    }

    private String toStringAux(NodoArbol nodo) {
        String mensaje = "";
        if (nodo != null) {
            mensaje = "\n NODO: " + nodo.getElem() + mensaje;
            if (nodo.getIzquierdo() != null) {
                mensaje = mensaje + " HI:" + (nodo.getIzquierdo().getElem());
            } else {
                mensaje = mensaje + " HI: - ";
            }
            if (nodo.getDerecho() != null) {
                mensaje = mensaje + (" HD:" + nodo.getDerecho().getElem());
            } else {
                mensaje = mensaje + " HD: - ";
            }
        }

        if (nodo.getIzquierdo() != null) {
            mensaje = mensaje + toStringAux(nodo.getIzquierdo());
        }

        if (nodo.getDerecho() != null) {
            mensaje = mensaje + toStringAux(nodo.getDerecho());
        }

        return mensaje;
    }

    //para obtener el nodo segun un elemento q busques
    private NodoArbol obtenerNodo(NodoArbol n, Object buscado) {
        // METODO PRIVADO que busca un elemento y devuelve el nodo que lo contiene. Si no se encuentra el buscado devueve null
        NodoArbol resultado = null;
        if (n != null) {
            if (n.getElem().equals(buscado)) { //si el elemento buscado es n, lo devuelve
                resultado = n;
            } else { //si no era, busca primero por el izquierdo
                resultado = obtenerNodo(n.getIzquierdo(), buscado);
                if (resultado == null) { //si tampoco era, finalmente busca por el derecho
                    resultado = obtenerNodo(n.getDerecho(), buscado);
                }
            }
        }
        return resultado;

    }
    //-------------------------------------------------------------------------------------------------------------------------------

    public Lista frontera() {
        Lista listaFrontera = new Lista();
        fronteraAux(this.raiz, listaFrontera);
        return listaFrontera;
    }

    private void fronteraAux(NodoArbol n, Lista listita) {
        if (n != null) {
            if (n.getIzquierdo() == null && n.getDerecho() == null) {
                listita.insertar(n.getElem(), listita.longitud() + 1);
            }
            if (n.getIzquierdo() != null) {
                fronteraAux(n.getIzquierdo(), listita);
            }
            if (n.getDerecho() != null) {
                fronteraAux(n.getDerecho(), listita);
            }
        }
    }

    
    public Lista obtenerAncestros(Object elem){
        Lista ancestros = new Lista();
        if(this.raiz!=null){
            ancestrosAux(this.raiz,elem,ancestros);
        }
        return ancestros;
    }

    private boolean ancestrosAux(NodoArbol nAux, Object buscado, Lista unaLista){
        boolean encontrado = false;
        if(nAux!=null){
            Object elemento = nAux.getElem();
  
            if(elemento.equals(buscado)){
                encontrado = true;
            } else {
                encontrado = ancestrosAux(nAux.getIzquierdo(),buscado,unaLista);
            }
            if(!encontrado){
                encontrado = ancestrosAux(nAux.getDerecho(),buscado,unaLista);
            }
            if(encontrado && (!elemento.equals(buscado)||elemento.equals(this.raiz.getElem().toString()))){
                unaLista.insertar(nAux.getElem(), unaLista.longitud()+1);
            }
        }
        return encontrado;
    }
    public Lista obtenerDescendientes(Object elemento) {
        Lista listaDescendientes = new Lista();
        if (this.raiz != null) {
            obtenerDescendientesAux(this.raiz, listaDescendientes, elemento, false);
        }
        return listaDescendientes;
    }

    private void obtenerDescendientesAux(NodoArbol nodo, Lista unaLista, Object buscado, boolean encontrado) {
        if(nodo != null){
            if(nodo.getElem().equals(buscado)){
                    encontrado = true;
                }
            if (encontrado) {
                if (nodo.getIzquierdo() != null) {
                    unaLista.insertar(nodo.getIzquierdo().getElem(), unaLista.longitud() + 1);
                    obtenerDescendientesAux(nodo.getIzquierdo(), unaLista, buscado, encontrado);

                }
                if(nodo.getDerecho() != null) {
                    unaLista.insertar(nodo.getDerecho().getElem(), unaLista.longitud() + 1);
                    obtenerDescendientesAux(nodo.getDerecho(), unaLista, buscado, encontrado);
                }
            } else {
                obtenerDescendientesAux(nodo.getIzquierdo(), unaLista, buscado, encontrado);
                obtenerDescendientesAux(nodo.getDerecho(), unaLista, buscado, encontrado);
            }
        }
    }
    
    private boolean verificarPatronAux(Lista patron, NodoArbol nAux, int i) {
        boolean resultado = true;
        if (nAux.getElem().equals(patron.recuperar(i))) {
            if (i == patron.longitud() && (nAux.getIzquierdo() != null || nAux.getDerecho() != null)) {
                resultado = false;
            } else {
                if (nAux.getIzquierdo() != null) {
                    resultado = verificarPatronAux(patron, nAux.getIzquierdo(), i + 1);
                }
                if (resultado == false && nAux.getDerecho() != null) {
                    resultado = verificarPatronAux(patron, nAux.getDerecho(), i + 1);
                }
            }
        } else {
            resultado = false;
        }

        return resultado;
    }
    
    
}
