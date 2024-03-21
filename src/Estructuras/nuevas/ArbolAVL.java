/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras.nuevas;

import Estructuras.lineales.dinamicas.Lista;

/**
 *
 * @author giuli
 */
public class ArbolAVL {
    

    private NodoAVL raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    public boolean insertar(Comparable elemento) {
        /* Recibe un elemento y lo agrega en el arbol de manera ordenada. Si el 
        elemento ya se encuentra en el árbol no se realiza la inserción. Devuelve 
        verdadero si el elemento se agrega a la estructura y falso en caso contrario */
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoAVL(elemento, null, null);
        } else {
            exito = insertarAux(this.raiz, null, elemento);
        }
        return exito;
    }

    private boolean insertarAux(NodoAVL nodo, NodoAVL padre, Comparable elemento) {
        // Precondicion: nodo no es nulo
        boolean exito = true;
        if (elemento.compareTo(nodo.getElem()) == 0) {
            // Elemento repetido
            exito = false;
        } else if (elemento.compareTo(nodo.getElem()) < 0) {
            // elemento es menor a nodo.getElem()
            // Si tiene HI baja a la izquierda, sino lo setea
            if (nodo.getIzquierdo() != null) {
                exito = insertarAux(nodo.getIzquierdo(), nodo, elemento);
            } else {
                nodo.setIzquierdo(new NodoAVL(elemento, null, null));
            }
        } else if (elemento.compareTo(nodo.getElem()) > 0) {
            // elemento es mayor a nodo.getElem()
            // Si tiene HD baja a la derecha, sino lo setea
            if (nodo.getDerecho() != null) {
                exito = insertarAux(nodo.getDerecho(), nodo, elemento);
            } else {
                nodo.setDerecho(new NodoAVL(elemento, null, null));
            }
        }
        if (exito) {
            nodo.recalcularAltura();
            balancear(nodo, padre);
            nodo.recalcularAltura();
        }
        return exito;
    }

    public boolean eliminar(Comparable elemento) {
        /* Recibe el elemento que se desea eliminar y se procede a removerlo del
        arbol. Si no se encuentra, no se puede realizar la eliminación. Devuelve
        verdadero si el elemento se elimina de la estructura y falso en caso contrario */
        boolean resultado = false;
        if (this.raiz != null) {
            resultado = eliminarAux(this.raiz, null, elemento);
        }
        return resultado;
    }

    private boolean eliminarAux(NodoAVL nodo, NodoAVL padre, Comparable elemento) {
        boolean exito = false;
        if (nodo != null) {
            if (elemento.compareTo(nodo.getElem()) == 0) {
                /*  Si es hoja: eliminar segun el caso 1
                    Si tiene un hijo: eliminar segun el caso 2
                    Si tiene ambos hijos: eliminar segun el caso 3
                 */
                if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                    caso1(nodo, padre);
                } else if ((nodo.getIzquierdo() != null && nodo.getDerecho() == null) || (nodo.getIzquierdo() == null && nodo.getDerecho() != null)) {
                    caso2(nodo, padre);
                } else if (nodo.getIzquierdo() != null && nodo.getDerecho() != null) {
                    caso3(nodo, padre);
                }
                exito = true;
            } else if (elemento.compareTo(nodo.getElem()) < 0) {
                exito = eliminarAux(nodo.getIzquierdo(), nodo, elemento);
            } else if (elemento.compareTo(nodo.getElem()) > 0) {
                exito = eliminarAux(nodo.getDerecho(), nodo, elemento);
            }
        }
        if (exito && nodo != null) {
            nodo.recalcularAltura();
            balancear(nodo, padre);
            nodo.recalcularAltura();
        }
        return exito;
    }

    private void caso1(NodoAVL nodo, NodoAVL padre) {
        //  El nodo a eliminar es una hoja
        if (padre != null) {
            if (nodo.getElem().compareTo(padre.getElem()) < 0) {
                // Caso en el que nodo es HI de su padre
                padre.setIzquierdo(null);
            } else if (nodo.getElem().compareTo(padre.getElem()) > 0) {
                // Caso en el que nodo es HD de su padre
                padre.setDerecho(null);
            }
        } else {
            // Caso raiz
            this.raiz = null;
        }
    }

    private void caso2(NodoAVL nodo, NodoAVL padre) {
        // El nodo a eliminar tiene un solo hijo
        if (padre != null) {
            if (nodo.getElem().compareTo(padre.getElem()) < 0) {
                // Caso en el que nodo es HI de su padre
                if (nodo.getIzquierdo() != null) {
                    // Caso en el que el unico hijo de nodo es HI
                    padre.setIzquierdo(nodo.getIzquierdo());
                } else {
                    // Caso en el que el unico hijo de nodo es HD
                    padre.setIzquierdo(nodo.getDerecho());
                }
            } else if (nodo.getElem().compareTo(padre.getElem()) > 0) {
                // Caso en el que nodo es HD de su padre
                if (nodo.getIzquierdo() != null) {
                    // Caso en el que el unico hijo de nodo es HI
                    padre.setDerecho(nodo.getIzquierdo());
                } else {
                    // Caso en el que el unico hijo de nodo es HD
                    padre.setDerecho(nodo.getDerecho());
                }
            }
        } else {
            // Caso raiz
            if (nodo.getIzquierdo() != null) {
                // Caso en el que el unico hijo de la raiz es HI
                this.raiz = this.raiz.getIzquierdo();
            } else {
                // Caso en el que el unico hijo de nodo es HD
                this.raiz = this.raiz.getDerecho();
            }
        }
    }

    private void caso3(NodoAVL nodo, NodoAVL padre) {
        // El nodo a eliminar tiene ambos hijos
        if (padre != null) {
            // Caso NO raiz
            if (nodo.getIzquierdo().getDerecho() != null) {
                // Caso en el que el candidato es el nieto/bisnieto
                NodoAVL nodoAux = nodo.getIzquierdo();
                NodoAVL padreAux = null;
                while (nodoAux.getDerecho() != null) {
                    padreAux = nodoAux;
                    nodoAux = nodoAux.getDerecho();
                }
                nodo.setElem(nodoAux.getElem());
                if (nodoAux.getIzquierdo() != null) {
                    padreAux.setDerecho(nodoAux.getIzquierdo());
                } else {
                    padreAux.setDerecho(null);
                }
            } else {
                // Caso en el que el candidato es el hijo
                nodo.setElem(nodo.getIzquierdo().getElem());
                if (nodo.getIzquierdo().getIzquierdo() != null) {
                    nodo.setIzquierdo(nodo.getIzquierdo().getIzquierdo());
                } else {
                    nodo.setIzquierdo(null);
                }
            }
        } else {
            // Caso raiz
            if (nodo.getIzquierdo().getDerecho() != null) {
                // Caso en el que el candidato es el nieto/bisnieto
                NodoAVL nodoAux = nodo.getIzquierdo();
                NodoAVL padreAux = null;
                while (nodoAux.getDerecho() != null) {
                    padreAux = nodoAux;
                    nodoAux = nodoAux.getDerecho();
                }
                this.raiz.setElem(nodoAux.getElem());
                if (nodoAux.getIzquierdo() != null) {
                    padreAux.setDerecho(nodoAux.getIzquierdo());
                } else {
                    padreAux.setDerecho(null);
                }
            } else {
                // Caso en el que el candidato es el hijo
                this.raiz.setElem(nodo.getIzquierdo().getElem());
                if (nodo.getIzquierdo().getIzquierdo() != null) {
                    this.raiz.setIzquierdo(nodo.getIzquierdo().getIzquierdo());
                } else {
                    this.raiz.setIzquierdo(null);
                }
            }
        }
    }

    private void balancear(NodoAVL nodo, NodoAVL padre) {
        int balanceNodo;
        int balanceHijo;
        balanceNodo = balance(nodo);
        if (balanceNodo == 2) {
            // Torcido hacia la izquierda
            balanceHijo = balance(nodo.getIzquierdo());
            if (balanceHijo == 1 || balanceHijo == 0) {
                // Rotacion simple derecha
                if (padre == null) { // El nodo a balancear es la raiz
                    this.raiz = rotarDerecha(nodo);
                } else {
                    padre.setIzquierdo(rotarDerecha(nodo));
                }
            } else if (balanceHijo == -1) {
                // Rotacion doble izquierda-derecha
                nodo.setIzquierdo(rotarIzquierda(nodo.getIzquierdo()));
                if(padre == null) {
                    this.raiz = rotarDerecha(nodo);
                } else {
                    if (padre.getIzquierdo().getElem().equals(nodo.getElem())) {
                        padre.setIzquierdo(rotarDerecha(nodo));
                    } else {
                        padre.setDerecho(rotarDerecha(nodo));
                    }
                }
            }
        } else if (balanceNodo == -2) {
            // Torcido hacia la derecha
            balanceHijo = balance(nodo.getDerecho());
            if (balanceHijo == -1 || balanceHijo == 0) {
                // Rotacion simple izquierda
                if (padre == null) { // El nodo a balancear es la raiz
                    this.raiz = rotarIzquierda(nodo);
                } else {
                    padre.setDerecho(rotarIzquierda(nodo));
                }
            } else if (balanceHijo == 1) {
                // Rotacion doble derecha-izquierda
                nodo.setDerecho(rotarDerecha(nodo.getDerecho()));
                if (padre == null) {
                    this.raiz = rotarIzquierda(nodo);
                } else {
                    if (padre.getIzquierdo().getElem().equals(nodo.getElem())) {
                        padre.setIzquierdo(rotarIzquierda(nodo));
                    } else {
                        padre.setDerecho(rotarIzquierda(nodo));
                    }
                }
            }
        }
        nodo.recalcularAltura();
    }

    private NodoAVL rotarIzquierda(NodoAVL r) {
        //h es el hijo que va a pasar a ser la nueva raiz
        NodoAVL h = r.getDerecho();
        //solamente tiene hijo izq (por eso el desbalance), se lo guarda para setearselo a la antigua raiz
        NodoAVL temp = h.getIzquierdo();
        h.setIzquierdo(r);
        r.setDerecho(temp);
        h.recalcularAltura();
        r.recalcularAltura();
        return h;
    }

    private NodoAVL rotarDerecha(NodoAVL r) {
        NodoAVL h = r.getIzquierdo();
        NodoAVL temp = h.getDerecho();
        h.setDerecho(r);
        r.setIzquierdo(temp);
        h.recalcularAltura();
        r.recalcularAltura();
        return h;
    }

    private int balance(NodoAVL nodo) {
        int balanceNodo;
        int alturaHijoIzquierdo = -1;
        int alturaHijoDerecho = -1;
        if (nodo.getIzquierdo() != null) {
            alturaHijoIzquierdo = nodo.getIzquierdo().getAltura();
        }
        if (nodo.getDerecho() != null) {
            alturaHijoDerecho = nodo.getDerecho().getAltura();
        }
        balanceNodo = alturaHijoIzquierdo - alturaHijoDerecho;
        return balanceNodo;
    }

    public void vaciar() {
        this.raiz = null;
    }
    
    public boolean pertenece(Comparable elemento) {
        // Devuelve verdadero si el elemento recibido está en el arbol y falso en caso contrario
        boolean resultado = false;
        if (this.raiz != null) {
            resultado = perteneceAux(this.raiz, elemento);
        }
        return resultado;
    }

    private boolean perteneceAux(NodoAVL nodo, Comparable buscado) {
        boolean exito = false;
        if (nodo != null) {
            if (buscado.compareTo(nodo.getElem()) == 0) {
                exito = true;
            } else if (buscado.compareTo(nodo.getElem()) < 0) {
                exito = perteneceAux(nodo.getIzquierdo(), buscado);
            } else if (buscado.compareTo(nodo.getElem()) > 0) {
                exito = perteneceAux(nodo.getDerecho(), buscado);
            }
        }
        return exito;
    }

    public boolean esVacio() {
        // Devuelve falso si hay al menos un elemento en el arbol y verdadero en caso contrario
        return this.raiz == null;
    }
// LISTADOS-------------------------------------------------------------------------------------------
    public Lista listar() {
        /* Recorre el arbol completo y devuelve una lista ordenada con los
        elementos que se encuentran almacenados en el */
        Lista lista = new Lista();
        listarAux(this.raiz, lista);
        return lista;
    }

    private void listarAux(NodoAVL nodo, Lista lista) {
        // Recorrido inorden
        if (nodo != null) {
            listarAux(nodo.getIzquierdo(), lista);
            lista.insertar(nodo.getElem(), lista.longitud() + 1);
            listarAux(nodo.getDerecho(), lista);
        }
    }

    public Lista listarRango(Comparable elemMinimo, Comparable elemMaximo) {
        /* Recorre parte del arbol (sólo lo necesario) y devuelve una lista ordenada
        con los elementos que se encuentran en el intervalo [elemMinimo, elemMaximo] */
        Lista lista = new Lista();
        listarRangoAux(this.raiz, lista, elemMinimo, elemMaximo);
        return lista;
    }

    private void listarRangoAux(NodoAVL nodo, Lista lista, Comparable minimo, Comparable maximo) {
        if (nodo != null) {
            if (minimo.compareTo(nodo.getElem()) <= 0) {
                listarRangoAux(nodo.getIzquierdo(), lista, minimo, maximo);
            }
            if (minimo.compareTo(nodo.getElem()) <= 0 && maximo.compareTo(nodo.getElem()) >= 0) {
                lista.insertar(nodo.getElem(), lista.longitud() + 1);
            }
            if (maximo.compareTo(nodo.getElem()) >= 0) {
                listarRangoAux(nodo.getDerecho(), lista, minimo, maximo);
            }
        }
    }

    public Comparable minimoElem() {
        // Recorre la rama correspondiente y devuelve el elemento mas pequeño almacenado en el arbol
        Comparable elemMinimo = null;
        if (this.raiz != null) {
            elemMinimo = minimoElemAux(this.raiz);
        }
        return elemMinimo;
    }

    private Comparable minimoElemAux(NodoAVL nodo) {
        Comparable elemento = null;
        if (nodo != null) {
            if (nodo.getIzquierdo() == null) {
                elemento = nodo.getElem();
            } else {
                elemento = minimoElemAux(nodo.getIzquierdo());
            }
        }
        return elemento;
    }

    public Comparable maximoElem() {
        // Recorre la rama correspondiente y devuelve el elemento mas grande almacenado en el arbol
        Comparable elemMaximo = null;
        if (this.raiz != null) {
            elemMaximo = maximoElemAux(this.raiz);
        }
        return elemMaximo;
    }

    private Comparable maximoElemAux(NodoAVL nodo) {
        Comparable elemento = null;
        if (nodo != null) {
            if (nodo.getDerecho() == null) {
                elemento = nodo.getElem();
            } else {
                elemento = maximoElemAux(nodo.getDerecho());
            }
        }
        return elemento;
    }

    @Override
    public String toString() {
        if (this.raiz == null) {
            return "El arbol es vacio";
        } else {
            return stringAux(this.raiz);
        }
    }

    private String stringAux(NodoAVL nodo) {
        String cadena = "";
        if (nodo != null) {
            cadena = "\n" + nodo.getElem() + " |" + cadena;
            if (nodo.getIzquierdo() != null) {
                cadena = cadena + " HI: (menor): " + nodo.getIzquierdo().getElem() + " |";
            } else {
                cadena = cadena + " HI: (menor): - |";
            }
            if (nodo.getDerecho() != null) {
                cadena = cadena + " HD: (mayor): " + nodo.getDerecho().getElem() + " |";
            } else {
                cadena = cadena + " HD: (mayor): - |";
            }
            cadena = cadena + " Altura: " + nodo.getAltura();
            if (nodo.getIzquierdo() != null) {
                cadena = cadena + stringAux(nodo.getIzquierdo());
            }
            if (nodo.getDerecho() != null) {
                cadena = cadena + stringAux(nodo.getDerecho());
            }
        }
        return cadena;
    }

}
