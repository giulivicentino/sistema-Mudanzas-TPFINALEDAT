package conjuntistas;

public class NodoABB {
    private Comparable elem;
    private NodoABB izquierdo;
    private NodoABB derecho;
    
    public NodoABB(Comparable elem ,NodoABB izquierdo,NodoABB derecho){
        this.elem = elem;
        this.izquierdo= izquierdo;
        this.derecho= derecho;
    }
    
    public Comparable getElem(){
        return elem;
    }
    
    public NodoABB getIzquierdo(){
        return izquierdo;
    }
    
    public NodoABB getDerecho(){
        return derecho;
    }
    
    public void setElem(Comparable elem){
        this.elem= elem;
    }
    
    public void setIzquierdo(NodoABB izquierdo){
        this.izquierdo= izquierdo;
    }
    
    public void setDerecho (NodoABB derecho){
        this.derecho= derecho;
    }
    
}
