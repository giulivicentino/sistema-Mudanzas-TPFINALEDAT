package lineales.estaticas;
public class Cola {
    
    private Object[] arr;
    private int frente;
    private int fin;
    private static final int TAMANIO = 10;
    
    public Cola(){
        this.arr = new Object[this.TAMANIO];
        this.frente = 0;
        this.fin = 0;
    }

    public boolean poner(Object elem){
        boolean exito;
        if(this.frente == (this.fin + 1) % TAMANIO){ // SI ESTA LLENA
            exito = false;
        } else{
            this.arr[this.fin] = elem;
            this.fin = (this.fin + 1) % TAMANIO;
            exito = true;
        }
        return exito;
    }
    
    public boolean sacar(){
        boolean exito = true;
        if (this.esVacia()){
            exito = false;
        } else {
            this.arr[this.frente] = null;
            this.frente = (this.frente + 1) % TAMANIO;
        }
        return exito;
    }
    
    public Object obtenerFrente(){
        Object objeto;
        if(frente==fin){
            objeto=null;
        }else{
             objeto=arr[frente];
        }
        return objeto;
    }
    
    public boolean esVacia(){
        return this.frente == this.fin;
    }
    
    public void vaciar(){
        while(this.fin != this.frente){
            this.arr[this.frente] = null;
            this.frente = (this.frente + 1) % TAMANIO;
        }
    }
    
    //imprimir solo los elementos no nulos de una cola
  /*  public String toString(){
        String cadena= "";
        while(frente!=fin){
            cadena= cadena + arr[frente].toString();
            this.frente = (this.frente + 1) % TAMANIO;
        }
        return cadena;        
    } */
    //imprimir toda la cola
     public String toString() {
        int i;
        String cadena = "Cola: ";
        for ( i = 0; i < TAMANIO; i++) {
            cadena = cadena + arr[i]+" ";
        }
        return cadena;
    }
    public Cola clone() {
         Cola clon = new Cola();
         if (this.frente!=this.fin) {
             clon.frente=this.frente;
             clon.fin=this.fin;
             int i = frente;
             while (i != fin && i < TAMANIO) {
                clon.arr[i] = this.arr[i];
                i = (i + 1) % TAMANIO;
            }
         }
       return clon;
     }
}
    //Esta Llena cuando el fin esta una posicion atras del inicio

/*
    public String toString(){
        String cadena= " ";
        if(frente!=fin)
        for(int i =  frente; i != fin ; i=(this.frente + 1) % TAMANIO){
            cadena= cadena + arr[i].toString();
        }
        return cadena;
    }*/