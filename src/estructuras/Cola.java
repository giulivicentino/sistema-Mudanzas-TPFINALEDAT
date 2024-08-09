package estructuras;

public class Cola {

    private Nodo frente;
    private Nodo fin;

    public Cola() {
        frente = null;
        fin = null;
    }

    public boolean poner(Object elem) {
        boolean exito = true;
        Nodo nuevo = new Nodo(elem, null);
        if (this.frente == null) {  //si la cola esta vacia tanto el frente como el fin se refieren al nodo recien creado
            this.frente = nuevo;
            this.fin = nuevo;
        } else {// para agregar nodo a una cola comun
            this.fin.setEnlace(nuevo);
            this.fin = nuevo; //enlace del fin al nodo nuevo
        }
        return exito;
    }

    public boolean sacar() {
        boolean exito = true;
        if (this.frente == null) {
            exito = false;
        } else {  //SI HAY UN ELEMENTO COMO MINIMO
            this.frente = this.frente.getEnlace();  //ACTUALIZA EL FRENTE
            if (this.frente == null) { //si al actualizar el frente ya sacaste el ultimo cosa
                this.fin = null;
            }
        }
        return exito;
    }

    public Object obtenerFrente() {
        Object elemento;
        if (this.frente == null) { // por que si la cola esta vacia el enlace de frente va a ser nulo y tira error
            elemento = null;
        } else {
            elemento = this.frente.getElem(); //a partir del nodo enlazado con el frente obtenes el elemento del nodo
        }
        return elemento;
    }

    public boolean esVacia() {
        return this.frente == null;
    }

    public void vaciar() {
        this.frente = null;
        //this.frente.setEnlace(null);
        //this.fin.setEnlace(null);
    }

    public Cola clone() {
        Cola colaClon = new Cola();
        if (this.frente != null) {
            Nodo aux1 = this.frente;
            Nodo aux2 = new Nodo(aux1.getElem(), null);// el primer elemento del clon lo creo con el getElem del frente original
            colaClon.frente = aux2;
            while (aux1.getEnlace() != null) {  // se saca el elemento del frente y se asocia frente al siguiente, y el frente original es el nuevo nodo de la cola clonada
                aux1 = aux1.getEnlace(); // itero el aux1 
                Nodo nuevo = new Nodo(aux1.getElem(), null); // creo el nodo con el elemento correspondiente de la original
                aux2.setEnlace(nuevo);  //engancho el aux con el nodo nuevo 
                aux2 = aux2.getEnlace(); // itero el aux2
            }
            colaClon.fin = aux2;
        }
        return colaClon;
    }

    @Override
    public String toString() {
        String resultado = "";
        Nodo aux = this.frente;
        if (esVacia()) {
            resultado = "La cola esta vacia";
        } else {
            while (aux != null) {
                resultado = resultado + aux.getElem().toString();
                aux = aux.getEnlace();
                if (aux != null) {
                    resultado = resultado + ",";
                }
            }
            resultado = "[" + resultado + "]";
        }
        return resultado;
    }
    
    public boolean equals(Cola c) {
        boolean esIgual = true;
        Nodo aux1 = this.frente;
        Nodo aux2 = c.frente;
        if((aux1 == null && aux2 != null) || (aux1 != null && aux2 == null)) {
            esIgual = false;
        }
        while (aux1 != null && esIgual) {
            if(aux1.getElem().equals(aux2.getElem())) {
                aux1 = aux1.getEnlace();
                aux2 = aux2.getEnlace();
            } else {
                esIgual = false;
            }
        }
        return esIgual;
    }
}
