package estructuras;

import SistMudanzas.Solicitud;

public class Lista {

    private Nodo cabecera;
    // private int longitud;

    public Lista() {
        this.cabecera = null;
        // longitud = 0;
    }
    //longitud puede estar como metodo o como atributo

    public boolean insertar(Object nuevoElemento, int pos) {
        boolean exito = true;
        if (pos < 1 || pos > this.longitud() + 1) { //para que sea una posicion valida
            exito = false;
        } else {
            if (pos == 1) { // si la lista esta vacia, crea el primer nodo con el valor ingresado
                this.cabecera = new Nodo(nuevoElemento, this.cabecera);
            } else {                                        // para agregarlo a una lista cualq
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {                   //cuenta la longitud para que no se pase y lo inserte en el lugar antes de la posicion deseada
                    aux = aux.getEnlace();         // si lo quiere en la pos 5, itera hasta 4 y lo inserta en ese lugar
                    i++;
                }                                               //crea el nodo y lo enlaza con el siguiente, y despues hace el enganche con el anterior
                Nodo nuevo = new Nodo(nuevoElemento, aux.getEnlace());          //( q le pase el getEnlace de aux q era el anterior)
                aux.setEnlace(nuevo);
            }
        }
        return exito;
    }

    public boolean eliminar(int pos) {
        boolean exito = true;
        if (pos < 1 || pos > longitud()) {
            exito = false;
        } else {
            if (pos == 1) {
                this.cabecera = this.cabecera.getEnlace();
            } else {
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace());
            }
        }
        return exito;
    }

    public Object recuperar(int pos) {
        // Devuelve el elemento en la posicion requerida por el usuario
        // Caso invalido retorna null
        Object elem;
        if (pos < 1 || pos > longitud()) {
            elem = null;
        } else { // Caso valido retorna elemento
            Nodo aux = this.cabecera;
            int i = 1;
            while (i < pos) {
                aux = aux.getEnlace();
                i++;
            }
            elem = aux.getElem();
        }
        return elem;
    }

    public int localizar(Object elem) {
        int pos = -1, i = 1;
        boolean exito = false;

        Nodo aux = this.cabecera;
        while (i <= longitud() && aux != null && exito == false) {
            if (aux.getElem().equals(elem)) {
                exito = true;
                pos = i;
            } else {
                aux = aux.getEnlace();
                i++;
            }
        }

        return pos;
    }

    public int longitud() {
        int i = 0;
        if (cabecera != null) {
            i = 1;
            Nodo enlace = this.cabecera.getEnlace();  // si el enlace no es nulo, que lo cuente 
            while (enlace != null) {
                enlace = enlace.getEnlace();
                i++;
            }
        }
        return i;
    }

    public boolean esVacia() {
        return this.cabecera == null;
    }

    public void vaciar() {
        this.cabecera = null;
    }

    public Lista clone() {
        Lista listaClon = new Lista();
        if (!esVacia()) {
            Nodo aux1 = this.cabecera;
            Nodo aux2 = new Nodo(aux1.getElem(), null);
            listaClon.cabecera = aux2;
            while (aux1.getEnlace() != null) {
                aux1 = aux1.getEnlace(); // itero el aux1 
                Nodo nuevo = new Nodo(aux1.getElem(), null); // creo el nodo con el elemento correspondiente de la original
                aux2.setEnlace(nuevo);  //engancho el aux con el nodo nuevo 
                aux2 = aux2.getEnlace(); // itero el aux2 
            }
        }
        return listaClon;
    }

    @Override
    public String toString() {
        String resultado = "[";
        Nodo aux = this.cabecera;
        if (esVacia()) {
            resultado = "La lista esta vacia";
        } else {
            while (aux != null) {
                resultado = resultado + aux.getElem().toString();
                aux = aux.getEnlace();
                if (aux != null) {
                    resultado = resultado + ", ";
                }
            }
            resultado = resultado +"]";
        }
        return resultado;
    }




    
    public boolean equals(Lista l) {
        boolean esIgual = true;
        Nodo aux1 = this.cabecera;
        Nodo aux2 = l.cabecera;
        if ((aux1 != null && aux2 == null) || (aux1 == null && aux2 != null)) {
            esIgual = false;
        }
        while (aux1 != null && esIgual && aux2 != null) {
            if (aux1.getElem().equals(aux2.getElem())) {
                aux1 = aux1.getEnlace();
                aux2 = aux2.getEnlace();
            } else {
                esIgual = false;
            }
        }
        return esIgual;
    }

    public boolean eliminarSolicitud(Object elem) {
        boolean exito = false;
        Solicitud elemAux = (Solicitud) elem;
        if(this.cabecera != null) {
            Solicitud solicitud = (Solicitud) this.cabecera.getElem();
            if(solicitud.equals(elemAux)) {
                this.cabecera = this.cabecera.getEnlace();
                exito = true;
            } else {
                Nodo aux = this.cabecera;
                while (aux.getEnlace() != null && !exito) {
                    solicitud = (Solicitud) aux.getEnlace().getElem();
                    if(solicitud.equals(elemAux)) {
                        aux.setEnlace(aux.getEnlace().getEnlace());
                        exito = true;
                    } else {
                        aux = aux.getEnlace();
                    }
                }
            }
        }
        return exito;
    }

    public Object buscarSolicitud(Object solicitud) {
        Object resultado = null;
        Solicitud nodo;
        Nodo aux = this.cabecera;
        while(aux != null && resultado == null) {
            nodo = (Solicitud) aux.getElem();
            if(nodo.equals((Solicitud)solicitud)) {
                resultado = aux.getElem();
            } else {
                aux = aux.getEnlace();
            }
        }
        return resultado;
    }

    
}