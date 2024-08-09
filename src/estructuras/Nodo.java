package estructuras;

public class Nodo {
    
    private Object elemento;
    private Nodo enlace;

    public Nodo (Object elemento, Nodo enlace) {
        this.elemento = elemento;
        this.enlace = enlace;
    }

    public Object getElem() {
        return elemento;
    }

    public void setElem(Object elemento) {
        this.elemento = elemento;
    }

    public Nodo getEnlace() {
        return enlace;
    }

    public void setEnlace(Nodo enlace) {
        this.enlace = enlace;
    }
}
