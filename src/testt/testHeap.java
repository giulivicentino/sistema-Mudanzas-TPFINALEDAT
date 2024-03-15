package testt;
import conjuntistas.HeapMin;
public class testHeap {
     public static void main(String[] args) {
         HeapMin arbol = new HeapMin();
         arbol.insertar(10);
         arbol.insertar(12);
         arbol.insertar(6);
         arbol.insertar(2);
         arbol.insertar(5);
         arbol.insertar(19);
         arbol.insertar(20);
        // arbol.insertar(90);
         
            /*arbol.insertar(10);
         arbol.insertar(12);
         arbol.insertar(15);
         arbol.insertar(21);
         arbol.insertar(45);
         arbol.insertar(19);
        */
         arbol.probar();
         System.out.println("");
         System.out.println(arbol.toString());
          arbol.insertar(90);
           arbol.probar();
          System.out.println("inserto 90 ");
          System.out.println(arbol.toString());
     }
}
