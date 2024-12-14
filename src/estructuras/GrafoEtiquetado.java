package estructuras;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class GrafoEtiquetado {

    private NodoVert inicio;

    public GrafoEtiquetado() {
        this.inicio = null;
    }

    public boolean insertarVertice(Object elem) {
        /*
         * Dado un elemento de TipoVertice se lo agrega a la estructura controlando
         * que no se inserten vertices repetidos. Si puede realizar la insercion
         * devuelve verdadero, en caso contrario devuelve falso
         */
        boolean exito = false;
        NodoVert aux = ubicarVertice(elem);
        if (aux == null) {
            this.inicio = new NodoVert(elem, this.inicio);
            exito = true;
        }
        return exito;
    }

    private NodoVert ubicarVertice(Object buscado) {
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean eliminarVertice(Object elem) {
        /*
         * Dado un elemento de TipoVertice se lo quita de la estructura. Si se
         * encuentra el vertice, tambien deben eliminarse todos los arcos que lo
         * tengan como origen o destino. Si se puede realizar la eliminacion con
         * exito devuelve verdadero, en caso contrario devuelve falso
         */
        boolean exito = false;
        if (this.inicio != null) {
            if (this.inicio.getElem().equals(elem)) {
                eliminarVerticeAux(this.inicio.getPrimerAdy(), elem);
                this.inicio = this.inicio.getSigVertice();
                exito = true;
            } else {
                NodoVert aux = this.inicio;
                while (aux != null && !exito) {
                    if (aux.getSigVertice().getElem().equals(elem)) {
                        eliminarVerticeAux(aux.getSigVertice().getPrimerAdy(), elem);
                        aux.setSigVertice(aux.getSigVertice().getSigVertice());
                        exito = true;
                    } else {
                        aux = aux.getSigVertice();
                    }
                }
            }
        }
        return exito;
    }

    private void eliminarVerticeAux(NodoAdy nodo, Object elem) {
        // Modulo que elimina los arcos que tengan como destino a elem
        while (nodo != null) {
            NodoAdy aux = nodo.getVertice().getPrimerAdy();
            if (aux.getVertice().getElem().equals(elem)) {
                nodo.getVertice().setPrimerAdy(aux.getSigAdyacente());
            } else {
                boolean corte = false;
                while (aux != null && !corte) {
                    if (aux.getSigAdyacente().getVertice().getElem().equals(elem)) {
                        aux.setSigAdyacente(aux.getSigAdyacente().getSigAdyacente());
                        corte = true;
                    } else {
                        aux = aux.getSigAdyacente();
                    }
                }
            }
            nodo = nodo.getSigAdyacente();
        }
    }

    public boolean existeVertice(Object buscado) {
        // Dado un elemento, devuelve verdadero si esta en la estructura y falso en caso
        // contrario
        boolean resultado = false;
        if (this.inicio != null) {
            NodoVert aux = this.inicio;
            while (aux != null && !resultado) {
                if (aux.getElem().equals(buscado)) {
                    resultado = true;
                } else {
                    aux = aux.getSigVertice();
                }
            }
        }
        return resultado;
    }

    public boolean insertarArco(Object origen, Object destino, double etiqueta) {
        /*
         * Dados dos elementos de TipoVertice (origen y destino) agrega el arco
         * en la estructura, solo si ambos vertices ya existen en el grafo. Si puede
         * realizar la insercion devuelve verdadero, en caso contrario devuelve falso
         */
        boolean exito = false;
        if (this.inicio != null) {
            NodoVert origenAux = ubicarVertice(origen);
            NodoVert destinoAux = ubicarVertice(destino);
            if (origenAux != null && destinoAux != null && (!origen.equals(destino))) {
                origenAux.setPrimerAdy(new NodoAdy(destinoAux, origenAux.getPrimerAdy(), etiqueta));
                destinoAux.setPrimerAdy(new NodoAdy(origenAux, destinoAux.getPrimerAdy(), etiqueta));
                exito = true;
            }
        }
        return exito;
    }

    public boolean eliminarArco(Object origen, Object destino) {
        /*
         * Dados dos elementos de TipoVertice (origen y destino) se quita de la
         * estructura el arco que une ambos vertices. Si el arco existe y se puede
         * realizar la eliminacion con éxito devuelve verdadero, en caso contrario
         * devuelve falso
         */
        boolean exito = false;
        if (this.inicio != null) {
            NodoVert origenAux = ubicarVertice(origen);
            NodoVert destinoAux = ubicarVertice(destino);
            if (origenAux != null && destinoAux != null && (!origen.equals(destino))) {
                // Si los vertices existen, verifico si tienen un arco y lo elimino
                boolean exitoOrigen = eliminarArcoAux(origenAux, destino);
                boolean exitoDestino = eliminarArcoAux(destinoAux, origen);
                exito = exitoOrigen && exitoDestino;
            }
        }
        return exito;
    }

    private boolean eliminarArcoAux(NodoVert origen, Object destino) {
        boolean resultado = false;
        if (origen.getPrimerAdy().getVertice().getElem().equals(destino)) {
            origen.setPrimerAdy(origen.getPrimerAdy().getSigAdyacente());
            resultado = true;
        } else {
            NodoAdy aux = origen.getPrimerAdy();
            while (aux.getSigAdyacente() != null && !resultado) {
                if (aux.getSigAdyacente().getVertice().getElem().equals(destino)) {
                    aux.setSigAdyacente(aux.getSigAdyacente().getSigAdyacente());
                    resultado = true;
                } else {
                    aux = aux.getSigAdyacente();
                }
            }
        }
        return resultado;
    }

    public boolean existeArco(Object origen, Object destino) {
        /*
         * Dados dos elementos de TipoVertice (origen y destino), devuelve verdadero
         * si existe un arco en la estructura que los une y falso en caso contrario
         */
        boolean resultado = false;
        if (this.inicio != null) {
            NodoVert origenAux = ubicarVertice(origen);
            if (origenAux != null) {
                NodoAdy aux = origenAux.getPrimerAdy();
                while (aux != null && !resultado) {
                    if (aux.getVertice().getElem().equals(destino)) {
                        resultado = true;
                    } else {
                        aux = aux.getSigAdyacente();
                    }
                }
            }
        }
        return resultado;
    }

    public boolean existeCamino(Object origen, Object destino) {
        /*
         * Dados dos elementos de TipoVertice (origen y destino), devuelve verdadero
         * si existe al menos un camino que permite llegar del vertice origen al
         * vertice destino y falso en caso contrario
         */
        boolean exito = false;
        if (this.inicio != null) {
            NodoVert origenAux = ubicarVertice(origen);
            NodoVert destinoAux = ubicarVertice(destino);
            if (origenAux != null && destinoAux != null) {
                // Si ambos vertices existen, verificamos si existe camino entre ellos
                Lista visitados = new Lista();
                exito = existeCaminoAux(origenAux, destino, visitados);
            }
        }
        return exito;
    }

    private boolean existeCaminoAux(NodoVert nodo, Object destino, Lista visitados) {
        boolean exito = false;
        if (nodo != null) {
            if (nodo.getElem().equals(destino)) {
                // Si vertice nodo es el destino, hay camino
                exito = true;
            } else {
                // Si no es el destino, verifica si hay camino entre nodo y destino
                visitados.insertar(nodo.getElem(), visitados.longitud() + 1);
                NodoAdy ady = nodo.getPrimerAdy();
                while (!exito && ady != null) {
                    if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                        exito = existeCaminoAux(ady.getVertice(), destino, visitados);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    public Lista caminoMasCorto(Object origen, Object destino) {
        Lista visitados = new Lista();
        Lista res = new Lista();
        if (this.inicio != null) {
            NodoVert origenAux = ubicarVertice(origen);
            NodoVert destinoAux = ubicarVertice(destino);
            if (origenAux != null && destinoAux != null) {
                res = caminoMasCortoAux(origenAux, destino, visitados, res);
            }
        }
        return res;
    }

    private Lista caminoMasCortoAux(NodoVert vert, Object destino, Lista visitados, Lista res) {
        if (vert != null) {
            visitados.insertar(vert.getElem(), visitados.longitud() + 1);
            System.out.println("SOY: " + vert.getElem() + "   visitados: " + visitados.toString());
            if (vert.getElem().equals(destino)) { // si vert es el destino, encontró un camino
                res = visitados.clone();
                System.out.println("--------------------------------------------------------------ENCONTRE CAMINO: " + res.toString()+ "----------------------------------------------------------------------------------------------------");
            } else {
                NodoAdy ady = vert.getPrimerAdy();
                while (ady != null) {
                    if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                       // System.out.println(" LONGITUD VISITADOS: "+visitados.longitud()+"  LONGITUD MAS CORTO: "+res.longitud());   
                        if (res.esVacia() || res.longitud() > visitados.longitud()) { // que para seguir buscando un camino, no supere la longitud del anterior
                            
                            res = caminoMasCortoAux(ady.getVertice(), destino, visitados, res); // llamado recursivo con  el vecino
                            
                        } /*else{
                        System.out.println("ESTABA ENCONTRANDO UNO MAS GRANDE LONGITUD VISITADOS: "+visitados.longitud()+"  LONGITUD MAS CORTO: "+res.longitud());   
                        } */
                    }
                    ady = ady.getSigAdyacente();
                }
            }
            visitados.eliminar(visitados.longitud());//a la vuelta lo elimina
        }
        return res;
    }
     // este metodo no era requerido pero devuelve todos los caminos entre dos ciudades
    public Lista listarTodosLosCaminos(Object origen, Object destino) {
        Lista visitados = new Lista();
        Lista res = new Lista();
        Lista total = new Lista();
        if (this.inicio != null) {
            NodoVert origenAux = ubicarVertice(origen);
            NodoVert destinoAux = ubicarVertice(destino);
            if (origenAux != null && destinoAux != null) {
                res = caminosTodosAux(origenAux, destino, visitados, res);
            }
        }
        System.out.println("QUE DEVUELVO ARRIBAAAA: "+res.toString());
        return res;
    }

    private Lista caminosTodosAux(NodoVert vert, Object destino, Lista visitados, Lista caminos) {
        if (vert != null) {
            visitados.insertar(vert.getElem(), visitados.longitud() + 1);
            System.out.println("SOY: " + vert.getElem() + "   visitados: " + visitados.toString());
    
            // Si el nodo actual es el destino, se ha encontrado un camino
            if (vert.getElem().equals(destino)) {
                Lista caminoEncontrado = visitados.clone();
                caminos.insertar(caminoEncontrado, caminos.longitud() + 1); // Añadir el camino encontrado a la lista de caminos
                System.out.println("--------------------------------------------------------------ENCONTRE CAMINO: " + caminoEncontrado.toString() + "----------------------------------------------------------------------------------------------------");
            } else {
                NodoAdy ady = vert.getPrimerAdy();
                while (ady != null) {
                    // Solo continuar si el nodo adyacente no ha sido visitado
                    if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                        caminos = caminosTodosAux(ady.getVertice(), destino, visitados, caminos); // Llamada recursiva con el vecino
                    }
                    ady = ady.getSigAdyacente();
                }
            }
            // Eliminar el nodo actual de la lista de visitados antes de regresar
            visitados.eliminar(visitados.longitud());
        }
        return caminos;
    }

    

    public Lista caminoMasRapido(Object origen, Object destino) {
        Lista visitados = new Lista();
        Lista res = new Lista();
        double[] menosKM = new double[1];
        menosKM[0] = 0;
        if (this.inicio != null) {
            NodoVert origenAux = ubicarVertice(origen);
            NodoVert destinoAux = ubicarVertice(destino);
            if (origenAux != null && destinoAux != null) {
                res = caminoMasRapidoAux(origenAux, destino, 0, menosKM, visitados, res);
            }
        }
        return res;
    }

    private Lista caminoMasRapidoAux(NodoVert vert, Object destino, double kmAux, double[] menosKM, Lista visitados,Lista res) {
        if (vert != null) {
            double km = menosKM[0];
            //System.out.println("SOY: " + vert.getElem() + "con: "+ kmAux   +"km hechos y visitados: " + visitados.toString() + " menorKmPrevio: "+ menosKM[0]);
            if (km == 0 || km > kmAux) {
                visitados.insertar(vert.getElem(), visitados.longitud() + 1);
                if (vert.getElem().equals(destino)) {
                    menosKM[0] = kmAux;
                    res = visitados.clone();
                    //System.out.println("ENCONTRE UN CAMINO: " + res.toString() + " CON KM: " + menosKM[0]);
                } else {
                    NodoAdy ady = vert.getPrimerAdy();
                    while (ady != null) {
                        if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                            double aux = kmAux + ady.getEtiqueta(); //sumo los kilometros del ady
                            res = caminoMasRapidoAux(ady.getVertice(), destino, aux, menosKM, visitados, res);
                        }
                        ady = ady.getSigAdyacente();
                    }
                }
                visitados.eliminar(visitados.longitud());
            }
        }
        return res;
    }

    public Lista caminoMasLargo(Object origen, Object destino) {
        Lista visitados = new Lista();
        Lista actual = new Lista();
        Lista res = new Lista();
        if (this.inicio != null) {
            NodoVert origenAux = ubicarVertice(origen);
            NodoVert destinoAux = ubicarVertice(destino);
            if (origenAux != null && destinoAux != null) {
                res = caminoMasLargoAux(origenAux, destino, visitados, actual, res);
            }
        }
        return res;
    }

    private Lista caminoMasLargoAux(NodoVert vert, Object destino, Lista visitados, Lista actual, Lista res) {
        if (vert != null) {
            visitados.insertar(vert.getElem(), visitados.longitud() + 1);
            actual.insertar(vert.getElem(), actual.longitud() + 1);
            if (vert.getElem().equals(destino)) {
                if ((actual.longitud() > res.longitud()) || res.esVacia()) {
                    res = actual.clone();
                }
            } else {
                NodoAdy ady = vert.getPrimerAdy();
                while (ady != null) {
                    if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                        res = caminoMasLargoAux(ady.getVertice(), destino, visitados, actual, res);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
            actual.eliminar(actual.longitud()); // ya lo visite, lo elimino del camino
            visitados.eliminar(visitados.longitud());
        }
        return res;
    }

    public Lista listarEnProfundidad() {
        // Devuelve una lista con los vertices del grafo visitados segun el recorrido en
        // profundidad
        Lista visitados = new Lista();
        // Define un vertice donde empezar a recorrer
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                // Si el vertice todavia no se visito, avanza en profundidad
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnProfundidadAux(NodoVert nodo, Lista visitados) {
        if (nodo != null) {
            // Marca al vertice como visitado
            visitados.insertar(nodo.getElem(), visitados.longitud() + 1);
            NodoAdy ady = nodo.getPrimerAdy();
            while (ady != null) {
                // Visita en profundidad los adyacentes de nodo aun no visitados
                if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                    listarEnProfundidadAux(ady.getVertice(), visitados);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    public Lista listarEnAnchura() {
        // Devuelve una lista con los vertices del grafo visitados según el recorrido en
        // anchura
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                listarEnAnchuraAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnAnchuraAux(NodoVert verticeInicial, Lista visitados) {
        Cola cola = new Cola();
        visitados.insertar(verticeInicial.getElem(), visitados.longitud() + 1);
        cola.poner(verticeInicial);
        while (!cola.esVacia()) {
            NodoVert aux = (NodoVert) cola.obtenerFrente();
            cola.sacar();
            NodoAdy ady = aux.getPrimerAdy();
            while (ady != null) {
                if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                    visitados.insertar(ady.getVertice().getElem(), visitados.longitud() + 1);
                    cola.poner(ady.getVertice());
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    public int cantAdy(NodoVert origen){ //devuelve la cantidad total de adyacentes de un nodoVert
        NodoAdy ady =origen.getPrimerAdy();
        int cant =0;
        while(ady!=null){
            cant++;
            ady= ady.getSigAdyacente();
        }
        return cant;
    }




    public Lista caminoMaxKm(Object origen,Object destino,int cantKm) {
        Lista visitados = new Lista();
        Lista res = new Lista();
        if (this.inicio != null) {
            NodoVert origenAux = ubicarVertice(origen);
            NodoVert destinoAux = ubicarVertice(destino);
            
            if (origenAux != null && destinoAux != null) {
                res = caminoMaxKmAux(origenAux, destino, cantKm,0,visitados, res);
            }
        }
        return res;
    }

    private Lista caminoMaxKmAux(NodoVert vert, Object destino, int cantKm, int acumuladorKm, Lista visitados,Lista res) {
        if (vert != null) {
            //System.out.println("SOY: " + vert.getElem() + "   visitados: " + visitados.toString() + " km recorridos: "+ acumuladorKm);
            if (acumuladorKm < cantKm) {
                if (vert.getElem().equals(destino)) { // si vert es el destino, encontró un camino
                    visitados.insertar(vert.getElem(), visitados.longitud() + 1);
                    res = visitados.clone();
                    //System.out.println("ENCONTRE UN CAMINO: " + res.toString() + " CON KM: " + acumuladorKm);
                } else {
                    NodoAdy ady = vert.getPrimerAdy();
                    while (ady != null) {
                        if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                            //System.out.println("(1)km antes de sumar distancia hasta << "+ ady.getVertice().getElem().toString() + " >> son :" + acumuladorKm);
                            // SUMO LOS KM
                            acumuladorKm = (int) (acumuladorKm + ady.getEtiqueta());                                                                                        
                            //System.out.println("(2)desp km que sumo: " + ady.getEtiqueta() + ", resultado acum: " + acumuladorKm);
                            if (acumuladorKm <= cantKm) { // si no es mayor no hace la recursion
                                visitados.insertar(vert.getElem(), visitados.longitud() + 1);
                                res = caminoMaxKmAux(ady.getVertice(), destino, cantKm, acumuladorKm, visitados, res); // llamado recursivo con el vecino
                            } 
                             //si con ese ady me pasaba, no hago otro llamado con esa ciudad
                            //System.out.println("ME PASABA DE KILOMETROS kmActual: " + acumuladorKm + "  kmMaximo: " + cantKm);
                            
                            //RESTO LOS KM
                            acumuladorKm = (int) (acumuladorKm - ady.getEtiqueta());
                            //System.out.println("los descuento para intentar por otro camino acum: " + acumuladorKm);
                            
                        }
                        ady = ady.getSigAdyacente();
                    }
                }
                visitados.eliminar(visitados.longitud());// a la vuelta lo elimina
                //System.out.println("como no era por ese camino, termina esta linea de recursion");
            }
        }
        return res;
    }
    
      // este metodo no era requerido pero devuelve todos los caminos entre dos ciudades
    public Lista listarCaminosConCiudad(Object origen,Object intermedio, Object destino) {
        Lista visitados = new Lista();
        Lista res = new Lista();
        boolean pasoInter=false;
        if (this.inicio != null) {
            NodoVert origenAux = ubicarVertice(origen);
            NodoVert destinoAux = ubicarVertice(destino);
            if (origenAux != null && destinoAux != null) {
                res = caminosConIntermedioAux(origenAux, intermedio, destino, visitados,pasoInter, res);
            }
        }
        
        return res;
    }

    private Lista caminosConIntermedioAux(NodoVert vert, Object intermedio, Object destino, Lista visitados,boolean pasoInter, Lista caminos) {
        if (vert != null) {
            visitados.insertar(vert.getElem(), visitados.longitud() + 1);
            System.out.println("SOY: " + vert.getElem() + "   visitados: " + visitados.toString());
    
            if (vert.getElem().equals(intermedio)) {
            pasoInter=true;
            }
            // Si el nodo actual es el destino, se ha encontrado un camino
            if (vert.getElem().equals(destino)) {
                if(pasoInter){
                    Lista caminoEncontrado = visitados.clone();
                    caminos.insertar(caminoEncontrado, caminos.longitud() + 1); // Añadir el camino encontrado a la lista de caminos
                    System.out.println("--------------------------------------------------------------ENCONTRE CAMINO: " + caminoEncontrado.toString() + "----------------------------------------------------------------------------------------------------");
                }
                
            } else {
                NodoAdy ady = vert.getPrimerAdy();
                while (ady != null) {
                    // Solo continuar si el nodo adyacente no ha sido visitado
                    if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                        caminos = caminosTodosAux(ady.getVertice(), destino, visitados, caminos); // Llamada recursiva con el vecino
                    }
                    ady = ady.getSigAdyacente();
                }
            }
            // Eliminar el nodo actual de la lista de visitados antes de regresar
            visitados.eliminar(visitados.longitud());
        }
        return caminos;
    }


        public static boolean buscarCaminoEnLista(Lista listaTotal, Lista listaBuscada) {
            boolean encontrada = false;
            int i = 0;
            if (!listaTotal.esVacia()) {
                while (i < listaTotal.longitud() || !encontrada) {
                    Lista lista1 = (Lista) listaTotal.recuperar(i);
                    System.out.println("que estoy comparando de total: " + lista1 + "   y lo que busco es: " + listaBuscada.toString());
                    
                    encontrada = lista1.equals(listaBuscada); //aca puede dar TRUE si la encontro
                    System.out.println(" eran iguales? "+encontrada);
                    i++;
                }
            }

            return encontrada;
        }


    

/* QUE NO VISITE NINGUNO PREVIO TAMPOCO
 * public Lista listarCaminosConCiudad(Object origen, Object intermedio, Object destino) {
        Lista visitadosTotal = new Lista();
        Lista visitados = new Lista();
        Lista listaCaminosFinal = new Lista();
        
        NodoVert origenAux = ubicarVertice(origen);
        NodoVert destinoAux = ubicarVertice(destino);
        NodoVert intermedioAux = ubicarVertice(intermedio);
        int i=0,cantPosibles=0;

        cantPosibles = cantAdy(origenAux); //como maximo van a haber tantos caminos como asdyacentes tenga el nodo inicial

        if (origenAux != null && destinoAux != null && intermedioAux != null) { // si ingresaron parametros validos
            
            while (i<cantPosibles){ //sigue construyendo la lista como cant max de ady q tenga el origen
                Lista unCamino = new Lista(); // desde origen hasta intermedio
                Lista resp = new Lista();
                Lista resp2 = new Lista();
                boolean pasoInter =false;
                //para sumarlo al acumulador de visitados, le saco el origen, destino y el intermedio asi no interfiere para buscar despues
                
                resp = caminoSinRepetir(origenAux,intermedio, destino, visitados, visitadosTotal, unCamino,pasoInter, resp);
                
                if(!resp.esVacia()){
                resp2= resp.clone();
                
                listaCaminosFinal.insertar(resp2, listaCaminosFinal.longitud()+1);
                
                resp.eliminar(resp.localizar(origen));
                resp.eliminar(resp.localizar(intermedio));
                resp.eliminar(resp.localizar(destino));

                visitadosTotal = concatenar(visitadosTotal,resp); //le agrego los nuevos asi no los recorre 
                
                }
                i++;
            }
            
        }
        return listaCaminosFinal;
    }

    private Lista caminoSinRepetir(NodoVert vert,Object intermedio ,Object destino, Lista visitados,Lista visitadosTotal, Lista unCamino,boolean pasoInter,Lista res) {
        if (vert != null) {
           
            visitados.insertar(vert.getElem(), visitados.longitud() + 1); //lo tacho para no volver a visitarlo
            unCamino.insertar(vert.getElem(), unCamino.longitud() + 1);
            if (vert.getElem().equals(intermedio)) {
            pasoInter=true;
            }
            if (vert.getElem().equals(destino) && pasoInter) {
                if ((unCamino.longitud() < res.longitud()) || res.esVacia()) { //para hacer que tambien sea el mas corto pero esto es opcional
                res = unCamino.clone();
                
                }
            } else { //busca un vecino no visitado y hace el llamado recursivo con eso, si estan todos ya termina
                NodoAdy ady = vert.getPrimerAdy();
                while (ady != null) {
                    if (visitadosTotal.localizar(ady.getVertice().getElem()) < 0 && visitados.localizar(ady.getVertice().getElem()) < 0) {//  busca que no ente en cualquier otro camino anterior
                        res = caminoSinRepetir(ady.getVertice(),intermedio, destino, visitados, visitadosTotal, unCamino,pasoInter, res); // llamado recursivo con el vecino
                    }
                ady = ady.getSigAdyacente(); //cambia de ady
                }    
            }
            
            //si no llego a destino 
            
            visitados.eliminar(visitados.longitud()); // en caso de que termina la ejecucion y no encontro camino, entonces "libera" los nosods q visito
            unCamino.eliminar(unCamino.longitud()); // ya encontre un camino, vacio la lista
        }
            return res;
        }
        
    

    public static Lista concatenar(Lista lista1, Lista lista2) {
        Lista conca = new Lista();
        int i = 1, j = 1, longi = lista1.longitud() + lista2.longitud();

        while (i <= lista1.longitud() || j <= lista2.longitud()) {
            Object aux1, aux2;
            aux1 = lista1.recuperar(i);
            aux2 = lista2.recuperar(j);
            if (i <= longi - lista2.longitud()) { //longitud de la primera lista
                conca.insertar(aux1, i);
                i++;
            } else { //para la longitud de lista2
                conca.insertar(aux2, i);
                i++;
                j++;
            }
        }

        return conca;
    }
 */
    
    public boolean esVacio() {
        return this.inicio == null;
    }

    @Override
    public GrafoEtiquetado clone() {
        /*
         * Genera y devuelve un grafo que es equivalente (igual estructura y
         * contenido de los nodos) al original
         */
        GrafoEtiquetado grafoClon = new GrafoEtiquetado();
        if (this.inicio != null) {
            grafoClon.inicio = new NodoVert(this.inicio.getElem(), null);
            clonarVertices(this.inicio, grafoClon.inicio);
            clonarAdyacentes(this.inicio, grafoClon.inicio);
        }
        return grafoClon;
    }

    private void clonarAdyacentes(NodoVert vertOriginal, NodoVert vertClon) {
        if (vertOriginal != null) {
            if (vertOriginal.getPrimerAdy() != null) {
                vertClon.setPrimerAdy(new NodoAdy(vertOriginal.getPrimerAdy().getVertice(), null,
                        vertOriginal.getPrimerAdy().getEtiqueta()));
                NodoAdy ady1 = vertOriginal.getPrimerAdy();
                NodoAdy ady2 = vertClon.getPrimerAdy();
                while (ady1.getSigAdyacente() != null) {
                    ady2.setSigAdyacente(new NodoAdy(ady1.getSigAdyacente().getVertice(), null,
                            ady1.getSigAdyacente().getEtiqueta()));
                    ady1 = ady1.getSigAdyacente();
                    ady2 = ady2.getSigAdyacente();
                }
            }
            clonarAdyacentes(vertOriginal.getSigVertice(), vertClon.getSigVertice());
        }
    }

    private void clonarVertices(NodoVert vertOriginal, NodoVert vertClon) {
        if (vertOriginal != null) {
            if (vertOriginal.getSigVertice() != null) {
                vertClon.setSigVertice(new NodoVert(vertOriginal.getSigVertice().getElem(), null));
            }
            clonarVertices(vertOriginal.getSigVertice(), vertClon.getSigVertice());
        }
    }

    @Override
    public String toString() {
        /*
         * Con fines de debugging, este metodo genera y devuelve una cadena String
         * que muestra los vertices almacenados en el grafo y que adyacentes tiene cada
         * uno de ellos
         */
        String resultado;
        if (this.inicio != null) {
            resultado = toStringAux(this.inicio);
        } else {
            resultado = "El grafo esta vacio ¿?";
        }
        return resultado;
    }

    private String toStringAux(NodoVert vertice) {
        String cadena = "";
        if (vertice != null) {
            cadena = cadena + vertice.getElem().toString() + " | Adyacentes: ";
            NodoAdy ady = vertice.getPrimerAdy();
            while (ady != null) {
                if (ady.getSigAdyacente() != null) {
                    cadena = cadena + ady.getVertice().getElem().toString() + "(" + ady.getEtiqueta() + ")" + ", ";
                } else {
                    cadena = cadena + ady.getVertice().getElem().toString() + "(" + ady.getEtiqueta() + ")";
                }
                ady = ady.getSigAdyacente();
            }
            cadena = cadena + "\n" + toStringAux(vertice.getSigVertice());
        }
        return cadena;
    }
}