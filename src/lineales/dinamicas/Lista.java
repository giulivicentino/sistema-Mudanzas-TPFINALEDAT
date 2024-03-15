package lineales.dinamicas;

import java.util.Set;

public class Lista {

    private Nodo cabecera;
      private int longitud;

    public Lista() {
        cabecera = null;
        //   longitud = 0;
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

    /*public Object recuperar(int pos) {
        Object elem;
       
        if (pos >= 1 || pos <= longitud()) { //posicion valida
            Nodo aux = this.cabecera;
            int i = 1;
            while (i < pos) { 
                
              // que itere hasta que encuentre la posicion
                    aux = aux.getEnlace();
                    i++;
                
                //CON EL NODO QUE QUIERO
                
            }elem = aux.getElem();
        } else {
            elem = null;
        }
        return elem;
    }*/
    public Object recuperar(int pos) {
        // Devuelve el elemento en la posicion requerida por el usuario
        // Caso invalido retorna null
        Object elem;
        if (pos < 1 || pos > longitud()) {
            elem = null;
        } else { // Caso valido retorna elemento
            Nodo aux = this.cabecera;
            int i = 1;
            while (i < pos) { //recorre hasta que llega a la posicion deseada
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
     Lista clon=new Lista();
     int i, largo=this.longitud();
     
     if (this.cabecera !=null) {
         Nodo aux=this.cabecera.getEnlace();
         clon.cabecera=new Nodo(this.cabecera.getElem(), null);
         Nodo aux2=clon.cabecera;
        for (i=1; i<largo; i++) {
            aux2.setEnlace(new Nodo(aux.getElem(), null));
            aux=aux.getEnlace();
            aux2=aux2.getEnlace();
        }         
     }
     return clon;
 }

    @Override
    public String toString() {
        String resultado = "";
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
        }
        return resultado;
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------
  public Lista obtenerMultiplos(int num) {
        Lista nuevaLista = new Lista();
        Nodo nodo1 = this.cabecera;
        Nodo nodo2 = null;
        int pos1 = 1;
        if (this.cabecera != null) {
            while (pos1 < this.longitud() - 1) { //para recorrer toda la lista
                if (pos1 % num == 0) { //si la posicion es multiplo del numero
                    if (nuevaLista.cabecera == null) { //si la lista neuva esta vacia
                        nuevaLista.cabecera = new Nodo(nodo1.getElem(), null);
                        nodo2 = nuevaLista.cabecera;
                      //  nodo1 = nodo1.getEnlace(); //ITERO EN LISTA 1
                    } else {
                        nodo2.setEnlace(new Nodo(nodo1.getElem(), null));
                        nodo2 = nodo2.getEnlace(); //ITERO EN LISTA 2
                    }
                }
                nodo1 = nodo1.getEnlace();
                pos1++;
            }
        }
        return nuevaLista;
    }
  public void eliminarApariciones(Object x) {
        int i=1;
        Nodo aux = this.cabecera;
        while(i<this.longitud){
            System.out.println(i);
            if (i == 1 && (aux.getElem().equals(x))) {
                aux = cabecera.getEnlace();
                this.cabecera = cabecera.getEnlace();
                this.longitud--;
            } else {
                if(aux.getEnlace()!=null){
                    if((aux.getEnlace().getElem().equals(x))){
                        aux.setEnlace(aux.getEnlace().getEnlace());
                        aux=aux.getEnlace();
                        this.longitud--;
                    } else {
                        aux = aux.getEnlace();
                        i++;
                    }
                }
            }
        }


    }
  //---------------------------------------------------------------RECU---------------------------------------------------------------------------------------
  
  
}
