package jerarquicas;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;

/**
 *
 * @author Giuli Vicentino
 */
public class ArbolGen {

    private NodoGen raiz;

    public ArbolGen() {
        this.raiz = null;
    }

    /*
    insertar(elemNuevo, elemPadre): boolean
// Dado un elemento elemNuevo y un elemento elemPadre, agrega elemNuevo como hijo de la primer
aparición de elemPadre. Para que la operación termine con éxito debe existir un nodo en el árbol con
elemento = elemPadre. No se establece ninguna preferencia respecto a la posición del hijo respecto a sus
posibles hermanos. Esta operación devuelve verdadero cuando se pudo agregar elemNuevo a la estructura
y falso en caso contrario
    1- si esta vacio lo haces raiz
    2- si es el primer hijo que tiene
    3- si ya tiene mas hijos
     */
    public boolean insertar(Object elemNuevo, Object elemPadre) {
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoGen(elemNuevo, null, null);
        } else {
            NodoGen nPadre = obtenerNodo(this.raiz, elemPadre);
            if (nPadre != null) {

                if (nPadre.getHijoIzquierdo() == null) {
                    nPadre.setHijoIzquierdo(new NodoGen(elemNuevo, null, null));
                } else {
                    NodoGen aux = nPadre.getHijoIzquierdo();

                    while (aux.getHermanoDerecho() != null) {
                        aux = aux.getHermanoDerecho();
                    }
                    aux.setHermanoDerecho(new NodoGen(elemNuevo, null, null));
                }
            } else {
                exito = false;
            }
        }
        return exito;
    }

    private NodoGen obtenerNodo(NodoGen n, Object elem) {
        NodoGen resultado = null;
        if (n != null) {
            if (n.getElem().equals(elem)) {
                resultado = n;
            } else {
                NodoGen hijo = n.getHijoIzquierdo();    //EL PRIMER LLAMADO LO HACE CON EL HIJO IZQ
                while (hijo != null && resultado == null) {
                    if (hijo.getElem().equals(elem)) {
                        resultado = hijo;
                    } else {
                        if (hijo.getHijoIzquierdo() != null) {
                            resultado = obtenerNodo(hijo, elem);
                        }
                    }
                    hijo = hijo.getHermanoDerecho(); //CAMBIA A QUE EL HIJO SEA SUS HERMANOS
                }
            }
        }
        return resultado;
    }

    public boolean pertenece(Object elem) {
        boolean exito = false;

        if (this.raiz != null) {

            exito = perteneceAux(this.raiz, elem);
        }
        return exito;
    }

    private boolean perteneceAux(NodoGen n, Object elem) {
        boolean resultado = false;
        if (n != null) {
            if (n.getElem().equals(elem)) {
                resultado = true;
            } else {
                NodoGen hijo = n.getHijoIzquierdo();    //EL PRIMER LLAMADO LO HACE CON EL HIJO IZQ
                while (hijo != null && !resultado) {
                    if (hijo.getElem().equals(elem)) {
                        resultado = true;
                    } else {
                        if (hijo.getHijoIzquierdo() != null) {
                            resultado = perteneceAux(hijo, elem);
                        }
                    }

                    hijo = hijo.getHermanoDerecho(); //CAMBIA A QUE EL HIJO SEA SUS HERMANOS
                }

            }
        }
        return resultado;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public void vaciar() {
        this.raiz = null;
    }

    public int altura() {
        int resultado = -1;
        if (this.raiz != null) {
            resultado = alturaAux(this.raiz);
        }
        return resultado;
    }

    private int alturaAux(NodoGen nAux) {
        int contador = 0, masGrande = 0;

        if (nAux.getHijoIzquierdo() != null) {
            NodoGen aux = nAux.getHijoIzquierdo();
            while (aux != null) {
                contador = alturaAux(aux);
                if (contador > masGrande) {
                    masGrande = contador;
                }
                aux = aux.getHermanoDerecho();
            }
            contador = masGrande + 1;
        }
        return contador;
    }

    public boolean equals(ArbolGen otroArbol) {
        boolean resultado = false;
        if (this.raiz != null) {
            resultado = equalsAux(this.raiz, otroArbol.raiz);
        }
        return resultado;
    }

    private boolean equalsAux(NodoGen nOriginal, NodoGen nOtro) {
        boolean resultado = true;
        if (nOriginal != null) {
            if (nOtro != null) {
                if (!nOriginal.getElem().equals(nOtro.getElem())) {
                    resultado = false;
                } else {
                    NodoGen aux1 = nOriginal.getHijoIzquierdo();
                    NodoGen aux2 = nOtro.getHijoIzquierdo();
                    if ((aux1 != null && aux2 == null) || (aux1 == null && aux2 != null)) {
                        resultado = false;
                    }
                    while (resultado && aux1 != null && aux2 != null) {
                        if (nOriginal.getElem().equals(nOtro.getElem())) {
                            resultado = equalsAux(aux1, aux2);
                        } else {
                            resultado = false;
                        }
                        aux1 = aux1.getHermanoDerecho();
                        aux2 = aux2.getHermanoDerecho();
                    }
                    if ((aux1 != null && aux2 == null) || (aux1 == null && aux2 != null)) {
                        resultado = false;
                    }
                }
            } else {
                resultado = false;
            }
        } else {
            resultado = nOtro == null;
        }
        return resultado;
    }

    public Lista listarInorden() {
        Lista salida = new Lista();
        listarInordenAux(this.raiz, salida);
        return salida;
    }

    private void listarInordenAux(NodoGen nodo, Lista lista) {
        if (nodo != null) {
            if (nodo.getHijoIzquierdo() != null) {  //llamado recursivo con primer hijo de nodo
                listarInordenAux(nodo.getHijoIzquierdo(), lista);
            }
// visita del nodo 
            lista.insertar(nodo.getElem(), lista.longitud() + 1);
//lamado recursivo con los otros hijos
            if (nodo.getHijoIzquierdo() != null) {
                NodoGen hijo = nodo.getHijoIzquierdo().getHermanoDerecho();
                while (hijo != null) {
                    listarInordenAux(hijo, lista);
                    hijo = hijo.getHermanoDerecho();
                }

            }
        }
    }

    public Lista listarPreorden() {
        Lista lista = new Lista();
        listarPreordenAux(this.raiz, lista);
        return lista;
    }

    private void listarPreordenAux(NodoGen nodo, Lista lista) {
        if (nodo != null) {
            //visita la RAIZ
            lista.insertar(nodo.getElem(), lista.longitud() + 1);
            NodoGen hijo = nodo.getHijoIzquierdo();
            while (hijo != null) { //mientras que tenga hermanos, haga mas llamados recursivos
                listarPreordenAux(hijo, lista);  //con el llamado va a insertar el hijo hermano en la lista
                hijo = hijo.getHermanoDerecho(); //sigue actualizando al hermano de al lado
            }

        }
    }

    public Lista listarPosorden() {
        Lista lista = new Lista();
        if (this.raiz != null) {
            listarPosordenAux(this.raiz, lista);
        }
        return lista;
    }

    private void listarPosordenAux(NodoGen nodo, Lista lista) {
        if (nodo != null) {
            //inserta los maximo izq
            NodoGen hijo = nodo.getHijoIzquierdo();
            while (hijo != null) {
                listarPosordenAux(hijo, lista);
                hijo = hijo.getHermanoDerecho();
            }
            //inserta la RAIZ
            lista.insertar(nodo.getElem(), lista.longitud() + 1);

        }
    }

    public Object padre(Object elem) {
        Object elemPadre = null;
        if (!this.raiz.equals(elem)) {
            elemPadre = padreAux(this.raiz, elem);
        }
        return elemPadre;
    }

    private Object padreAux(NodoGen nodo, Object elem) {
        Object resultado = null;
        if (nodo != null) {
            // visita de n
            NodoGen hijo = nodo.getHijoIzquierdo();
            while (hijo != null && resultado == null) { //visita de los hijos de n
                if (hijo.getElem().equals(elem)) {
                    resultado = nodo.getElem();
                } else {
                    hijo = hijo.getHermanoDerecho();
                }
            }
            if (resultado == null) { //si no era hijo de n, busco en los hijos de los hijos de n
                hijo = nodo.getHijoIzquierdo();
                while (hijo != null && resultado == null) {
                    resultado = padreAux(hijo, elem);
                    hijo = hijo.getHermanoDerecho();
                }

            }
        }
        return resultado;
    }

    public int nivel(Object elem) {
        int prof = 0;
        if (!this.raiz.getElem().equals(elem) && this.raiz != null) {
            prof = nivelAux(this.raiz, elem, prof);
        }
        return prof;
    }

    public int nivelAux(NodoGen nodo, Object elem, int prof) {
        int nivel = -1;
        boolean encontrado;
        if (nodo != null) {
            if (!nodo.getElem().equals(elem)) { //mientras que no lo encuentre que itere nivel
                NodoGen hijo = nodo.getHijoIzquierdo();
                encontrado = false;
                while (hijo != null && !encontrado) { //recorre los hermanos nesesarios hasta q encuentre o busque en sus hijos
                    nivel = nivelAux(hijo, elem, prof + 1);
                    encontrado = nivel != -1;
                    hijo = hijo.getHermanoDerecho(); //cambia de hermano
                }
            } else {
                nivel = prof; //cuando encuentre el elemento, devuelve el nivel alcanzado hasta el momento    

            }
        }
        return nivel;
    }

    public String toString() {
        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoGen nodo) {
        String cadena = "";

        if (nodo != null) {
            cadena = cadena + nodo.getElem().toString() + " -> ";
            NodoGen hijo = nodo.getHijoIzquierdo();
            while (hijo != null) {
                cadena = cadena + hijo.getElem().toString() + ", ";
                hijo = hijo.getHermanoDerecho();
            }
            hijo = nodo.getHijoIzquierdo();
            while (hijo != null) {
                cadena = cadena + "\n" + toStringAux(hijo);
                hijo = hijo.getHermanoDerecho();
            }
        }

        return cadena;
    }

    public Lista ancestros(Object elem) {
        Lista l1 = new Lista();
        ancestrosAux(this.raiz, elem, l1);
        return l1;
    }

    private boolean ancestrosAux(NodoGen nodo, Object elem, Lista lista) {
        boolean encontrado = false;
        if (nodo != null && !encontrado) {
            if (nodo.getElem().equals(elem)) {
                encontrado = true;
            } else {
                lista.insertar(nodo.getElem(), lista.longitud() + 1);
                NodoGen hijo = nodo.getHijoIzquierdo();
                while (hijo != null && !encontrado) { //que recorra los hermanos que hagan falta 
                    encontrado = ancestrosAux(hijo, elem, lista); //ANALIZA HIJO IZQUIERDO
                    hijo = hijo.getHermanoDerecho(); //itera el hijo  //ACTUALIZA A SUS HERMANOS
                }
            }
        }
        if (!encontrado) {
            lista.eliminar(lista.longitud());
        }
        return encontrado;
    }

    public ArbolGen clone() {
        ArbolGen clon = new ArbolGen();

        if (this.raiz != null) {
            clon.raiz = new NodoGen(null, null, null);
            cloneAux(this.raiz, clon.raiz);
        }

        return clon;
    }

    private void cloneAux(NodoGen nAux, NodoGen nClon) {
        while (nAux != null) {
            nClon.setElem(nAux.getElem()); // carga el elemento en el nodo nuevo del clon
            if (nAux.getHijoIzquierdo() != null) {     //si el nodo original tiene hijos, le crea un hijo al clon y lo setea
                nClon.setHijoIzquierdo(new NodoGen(nAux.getHijoIzquierdo(), null, null));
                cloneAux(nAux.getHijoIzquierdo(), nClon.getHijoIzquierdo()); //llamado recursivo para cada vez q el nodo original tenga hijos
            }
            if (nAux.getHermanoDerecho() != null) {
                nClon.setHermanoDerecho(new NodoGen(nAux.getElem(), null, null));
            }
            nAux = nAux.getHermanoDerecho();
            nClon = nClon.getHermanoDerecho();
        }
    }

    public Lista listarPorNiveles() {
        Lista ls = new Lista();
        listarNivelAux(raiz, ls);
        return ls;
    }

    private void listarNivelAux(NodoGen n, Lista ls) {
        Cola q = new Cola();
        q.poner(n);
        if (raiz != null) {
            while (!q.esVacia()) {
                NodoGen nodo = (NodoGen) q.obtenerFrente();
                q.sacar();
                ls.insertar(nodo.getElem(), ls.longitud() + 1);
                if (nodo.getHijoIzquierdo() != null) {
                    NodoGen hijo = nodo.getHijoIzquierdo();
                    while (hijo != null) {
                        q.poner(hijo);
                        hijo = hijo.getHermanoDerecho();
                    }
                }
            }
        }
    }
    //------------------SIMULACRO----------------------------------------------------------------------------------------------------------------------------

    public boolean verificarCamino(Lista camino) {
        boolean exito = false;
        if (this.raiz != null) {
            exito = verificarCaminoAux(raiz, camino, 1);
        }
        return exito;
    }

    private boolean verificarCaminoAux(NodoGen nodo, Lista camino, int i) {
        boolean exito = false;
        int longitudd = camino.longitud();
        Object elemLista = camino.recuperar(i);
        if (nodo != null && i <= longitudd && !exito) {
            if (nodo.getElem().equals(elemLista)) { //analiza siempre que el nodo coincide con el elem de la lista
                if (i == longitudd) { //si ya dio valido toda la lista
                    exito = true;
                }
                i++;
                NodoGen hijo = nodo.getHijoIzquierdo();
                while (hijo != null && !exito) { //recorra todos los hermanos hasta q encuentra el elemento de la lista
                    exito = verificarCaminoAux(hijo, camino, i);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return exito;
    }

    public Lista listarEntreNiveles(int niv1, int niv2) {
        Lista lista1 = new Lista();
        if (this.raiz != null) {
            listarEntreNivelesAux(niv1, niv2, 0, raiz, lista1);
        }
        return lista1;
    }

    private void listarEntreNivelesAux(int niv1, int niv2, int i, NodoGen nodo, Lista lista1) {
        int longitudd = lista1.longitud();
        if (nodo != null) {
            if (nodo.getHijoIzquierdo() != null) {
                listarEntreNivelesAux(niv1, niv2, i++, nodo.getHijoIzquierdo(), lista1);
            }
            if (i >= niv1 && i <= niv2) {
                //lamado hijo izq
                lista1.insertar(nodo.getElem(), longitudd + 1);
                //inserta nodo actual 
            }
            if (nodo.getHijoIzquierdo() != null) {
                NodoGen hijo = nodo.getHijoIzquierdo();
                while (hijo != null) {
                    listarEntreNivelesAux(niv1, niv2, i, nodo.getHermanoDerecho(), lista1);
                }
            }
        } else {
            i++;
        }
    }

    public Lista listarHastaNivel(int n2) {
        Lista listado = new Lista();
        listarHastaNivelAux(this.raiz, n2, 0, listado);
        return listado;
    }

    public void listarHastaNivelAux(NodoGen nodo, int n2, int nivelActual, Lista listado) {
        if (nodo != null) {
            if (nivelActual <= n2) {
                listado.insertar(nodo.getElem(), listado.longitud() + 1);
            }
            NodoGen hijo = nodo.getHijoIzquierdo();
            while (hijo != null) {
                listarHastaNivelAux(hijo, n2, nivelActual + 1, listado);
                hijo = hijo.getHermanoDerecho();
            }

        }
    }

    //listar niveles pares
    public Lista listarNivelesPares() {
        Lista listado = new Lista();
        if (this.raiz != null) {
            listarPares(this.raiz, 0, listado);
        }
        return listado;
    }

    private void listarPares(NodoGen nodo, int nivelActual, Lista listado) {

        if (nodo != null) {

            if (nivelActual % 2 == 0) {
                listado.insertar(nodo.getElem(), listado.longitud() + 1);
            }
            NodoGen hijo = nodo.getHijoIzquierdo();
            while (hijo != null) {
                listarPares(hijo, nivelActual + 1, listado);
                hijo = hijo.getHermanoDerecho();
            }
        }
    }

    public void insertarEnPosicion(Object elem, Object elemPadre, int pos) {
        if (this.raiz != null) {
            NodoGen padre = encontrarPadre(raiz, elemPadre);
            System.out.println("PADRE? " + padre.getElem().toString());
            if (padre != null) { //siempre que exista el padre en el arbol, llama al modulo
                insertarAux(padre, elem, pos);
            }
        }
    }

    private NodoGen encontrarPadre(NodoGen nodo, Object elemPadre) {
        NodoGen padre = null;
        if (nodo != null) {
            if (nodo.getElem().equals(elemPadre)) {
                padre = nodo;
            } else {
                NodoGen hijo = nodo.getHijoIzquierdo();
                while (hijo != null && padre == null) {
                    if (hijo.getElem().equals(elemPadre)) {
                        padre = hijo;
                    } else {
                        padre = encontrarPadre(hijo, elemPadre);
                    }
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return padre;
    }

    private void insertarAux(NodoGen padre, Object elem, int pos) {
        int posActual = 1;
        NodoGen nodoNuevo = new NodoGen(elem, null, null);
        if (padre.getHijoIzquierdo() != null) { //si tiene hijos
            NodoGen hijo = padre.getHijoIzquierdo();
            if (pos == 1) { //si quiere insertar como el primer hijo
                 if (hijo.getHermanoDerecho() != null) {// si el hijo si TIENE HERMANO
                    nodoNuevo.setHermanoDerecho(hijo.getHermanoDerecho());
                    padre.setHijoIzquierdo(nodoNuevo);
                } else {
                    nodoNuevo.setHermanoDerecho(hijo);
                    padre.setHijoIzquierdo(nodoNuevo);
                }
            } else {
                //NodoGen hijo2 = hijo;
                while (posActual < pos - 1 && hijo != null) {
                    hijo = hijo.getHermanoDerecho();
                    posActual++;
                }
                if (hijo.getHermanoDerecho() != null) {
                    System.out.println("que paaasaa");
                    nodoNuevo.setHermanoDerecho(hijo.getHermanoDerecho());
                    hijo.setHermanoDerecho(nodoNuevo);
                } else { // si el while se corto por qu eno tiene mas hermanos
                    hijo.setHermanoDerecho(nodoNuevo);
                }
            }
        } else {//si no tiene hijos
            padre.setHijoIzquierdo(new NodoGen(elem, null, null));
        }

    }

    public boolean esSobrino(Object elem, Object tio) {
        boolean sobrino = false;
        NodoGen nodoTio = obtenerNodo(tio, this.raiz);
        System.out.println("nodoTio: "+nodoTio.getElem());
        if (nodoTio != null) {
            sobrino = verificaSobrino(nodoTio, elem);
        }
        return sobrino;
    }

    private NodoGen obtenerNodo(Object tio, NodoGen nodo) {
        NodoGen nodoTio = null;
        if (nodo != null) {
            if (nodo.getElem().equals(tio)) {
                nodoTio = nodo;
            } else {
                NodoGen hijo = nodo.getHijoIzquierdo();
                while (hijo != null && nodoTio == null) {
                    if (hijo.getElem().equals(tio)) {
                        nodoTio = hijo;
                    } else {
                        nodoTio = obtenerNodo(tio, hijo);
                    }
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return nodoTio;
    }

    private boolean verificaSobrino(NodoGen nodo, Object elem) {
        boolean esSobrino = false;
        if (nodo != null && !esSobrino) {
            NodoGen nodoHermano = nodo.getHermanoDerecho();
            while (!esSobrino && nodoHermano != null) {
                System.out.println("esta en el hermano: "+nodoHermano.getElem());
                NodoGen nodoSobrino = nodoHermano.getHijoIzquierdo();
                while (!esSobrino && nodoSobrino != null) {
                    if (nodoSobrino.getElem().equals(elem)) {
                        esSobrino = true;
                        System.out.println("encontro el sobrino"); 
                    } else { //para cambiar de sobrino
                        System.out.println("No encontro el sobrino, esta en: "+nodoSobrino.getElem());
                        nodoSobrino = nodoSobrino.getHermanoDerecho();
                        
                    }
                } //para cambiar de hermano
                if(!esSobrino){
                nodoHermano = nodoHermano.getHermanoDerecho();
                esSobrino = verificaSobrino(nodoHermano, elem);  
                }
            }                
        }
        return esSobrino;
    }
    public boolean eliminar(Object elem){
        return eliminarxdxd(elem,this.raiz);
    }
    
    private boolean eliminarxdxd(Object elem, NodoGen nAux) {
        boolean resultado = false;
        if (nAux != null) {
            if (nAux.getHijoIzquierdo() != null) {
                if (nAux.getHijoIzquierdo().getElem().equals(elem)) {
                    if (nAux.getHijoIzquierdo().getHermanoDerecho() != null) {
                        NodoGen temp = nAux.getHijoIzquierdo().getHermanoDerecho();
                        nAux.setHijoIzquierdo(temp);
                        resultado = true;
                    } else {
                        nAux.setHijoIzquierdo(null);
                        resultado = true;
                    }
                } else if (nAux.getHermanoDerecho() != null) {
                    if (nAux.getHermanoDerecho().getElem().equals(elem)) {
                        if (nAux.getHermanoDerecho().getHermanoDerecho() != null) {
                            NodoGen temp = nAux.getHermanoDerecho().getHermanoDerecho();
                            nAux.setHermanoDerecho(temp);
                            resultado = true;
                        } else {
                            nAux.setHermanoDerecho(null);
                            resultado = true;
                        }
                    }
                }
            }
            NodoGen aux = nAux.getHijoIzquierdo();
            while (aux != null && !resultado) {
                resultado = eliminarxdxd(elem, aux);
                aux = aux.getHermanoDerecho();
            }
        }
        return resultado;
    }
    
    
    
    
/*
    public int orden(){
        int numOrden =0;
        if(this.raiz!=null){
            numOrden = ordenAux(this.raiz,0);
        }            
     return numOrden;
    }
    
    private int ordenAux(NodoGen nodo, int ordenMax){
        int ordenFinal;
        if(nodo!=null){
            
            if(nodo.getHijoIzquierdo()!=null){
                ordenActual = 1;
                NodoGen hijo = nodo.getHijoIzquierdo();
                while(hijo!=null){
                    ordenActual++;
                    hijo = hijo.getHermanoDerecho();
                }
                
             
            
            
        }
        
        return ordenFinal;
    }
    
}
    private int calcularOrden(NodoGen nodo){
        int numOrden = 0;
        NodoGen hijo = nodo.getHijoIzquierdo();
        while(hijo!=null){
            numOrden = numOrden +1;
            hijo= hijo.getHermanoDerecho();
            numOrden = numOrden + calcularOrden(hijo);
        }
    }
*/
}