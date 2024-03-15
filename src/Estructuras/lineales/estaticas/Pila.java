package Estructuras.lineales.estaticas;

public class Pila {

    private final Object[] arreglo;
    private int tope;
    private int tamanio = 10;

    public Pila() {
        this.arreglo = new Object[tamanio];
        this.tope = -1;
    }

    public boolean apilar(Object nuevoElemento) {
        boolean exito;

        if (this.tope + 1 >= this.tamanio) {
            exito = false;
        } else {
            this.tope++;
            this.arreglo[tope] = nuevoElemento;
            exito = true;
        }
        return exito;
    }

    public boolean desapilar() {
        boolean exito;
        if (this.tope >= 0) {
            this.arreglo[tope] = null;
            this.tope--;
            exito = true;
        } else {
            exito = false;
        }
        return exito;
    }

    public Object obtenerTope() {
        Object elemento;
        if (tope >= 0) {
            elemento = this.arreglo[tope];
        } else {
            elemento = null;
        }
        return elemento;
    }

    public boolean esVacia() {
        return this.tope == -1;
    }

    public void vaciar() {
        while (this.tope >= 0) {
            this.arreglo[tope] = null;
            tope--;
        }
    }

    public Pila clone() {
        Pila pila1 = new Pila();

        for (int i = 0; i <= tope; i++) {
            pila1.arreglo[i] = this.arreglo[i];
        }
        pila1.tope = this.tope;
        return pila1;
    }

    public String toString() {
        String cadena = "";

        for (int i = 0; i <= tope; i++) {
            cadena = cadena + " " + arreglo[i];
        }
        return cadena;
    }
}
