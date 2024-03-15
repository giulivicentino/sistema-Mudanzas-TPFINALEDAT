package conjuntistas;

/**
 *
 * @author Giuli Vicentino
 */
public class HeapMin {

    private Comparable[] heap;
    private int ultimo;
    private int TAMANIO = 20;

    public HeapMin() {
        this.heap = new Comparable[TAMANIO];
        this.ultimo = 0; //la posicion 0 nunca se usa

    }

    public boolean eliminarCima() {
        boolean exito;
        if (this.ultimo == 0) {
            exito = false;
        } else {
            this.heap[1] = this.heap[ultimo]; //pasa la hoja mas derecha a la raiz
            this.heap[ultimo] = null; //deja en nulo la hoja q no usa mas
            this.ultimo--; //resta el puntero ulitmo

            hacerBajar(1); // para que baje desde la raiz (posicion 1)
            exito = true;

        }
        return exito;
    }

    private void hacerBajar(int posPadre) {
        int posH;
        Comparable aux = this.heap[posPadre];
        boolean salir = false;
        while (!salir) { // se multiplica para avanzar en el arreglo y asi ubicar a los hijos
            posH = posPadre * 2; //primer hijo
            if (posH <= this.ultimo) { //hasta que existan posiciones validas en el arreglo

                if (posH < this.ultimo) { //si tiene hermanos
                    if (this.heap[posH + 1].compareTo(this.heap[posH]) < 0) { //compara con el hermano e itera hijo
                        posH++;
                    }
                }
                // posH pasa a ser el hijo mas chico
                if (this.heap[posH].compareTo(aux) < 0) { //compara el hijo chico con el padre
                    this.heap[posPadre] = this.heap[posH]; // intercambia padre e hijo
                    this.heap[posH] = aux; //actualiza el padre
                    posPadre = posH; //actualiza posicion del padre
                } else {
                    //cuando ya esta ubicado, el hijo es menor que su padre
                    salir = true;
                }
            } else {
                //cuando ya no tiene mas hijos el nodo
                salir = true;
            }
        }
    }

    
  
     

    public boolean insertar(Comparable elem) {
        boolean exito = false;
        if (ultimo < TAMANIO) { //para que no este lleno
            if (ultimo == 0) { //para cuando es el primer elemento
                heap[1] = elem;
                ultimo = 1; //actualiza posUltimo
            } else {
                heap[ultimo + 1] = elem; // que lo inserte en la posicion mas afuera 
                ultimo = ultimo + 1; //itera ultimo
                // ordena el arreglo si es nesesario (si el nuevo elemento es mas chico que su padre)
                if (this.heap[ultimo].compareTo(this.heap[ultimo / 2]) < 0) { //si el HIJO es mas grande que el PADRE
                    hacerSubir(ultimo);
                }
            }

        } else {
            exito = false;
        }

        return exito;
    }

    // hay que ir intercambiando el nodo con el padre sucesivamente hasta que el padre sea menor que el nodo
    private void hacerSubir(int posUltimo) {
        boolean salir = false;
        int posPadre = posUltimo / 2; // para poder saber el padre de cualq nodo
        while (!salir) {
            Comparable aux = this.heap[posPadre]; //guardo a padre para no perderlo
            //intercambia ya el del padre con el del hijo
            this.heap[posPadre] = this.heap[posUltimo];
            this.heap[posUltimo] = aux;
            if (posPadre / 2 > 0) {//condicion de corte y que no intercambie si llego a la raiz
                // si el padre es MENOR que su abuelo  
                if ((this.heap[posPadre].compareTo(this.heap[posPadre / 2])) >= 0) {
                    salir = true; //ya estaria ordenado
                }else{
                    posUltimo=posPadre; //actualizo la posicion a su padre
                    posPadre= posPadre/2; //hago que padre ahora sea el abuelo
                }
            }else{//si es la raiz ya
                salir=true;
            }
        }
    }
    
    public String toString(){
        String resultado="";
        int i=1;
        
        while(i*2 <= ultimo){ //para que recorra solo el arreglo y no se exceda
        
               resultado= resultado + "PADRE: "+this.heap[i]+" HI: "+this.heap[i*2]+" HD: "+this.heap[(i*2)+1]+"\n";
        
           
            i++;
        }
        return resultado;
    }
    
      public void probar(){
        for(int i=0; i<= ultimo ;i++){
            System.out.print(this.heap[i]+",");
        }
    }
}
