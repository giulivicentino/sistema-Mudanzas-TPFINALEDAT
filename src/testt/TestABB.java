package testt;

import conjuntistas.ArbolBB;

public class TestABB {

    public static void main(String[] args) {
        ArbolBB arbol = new ArbolBB();
        System.out.println("Esta vacio? " + arbol.esVacio());
      /*   arbol.insertar(32);
        arbol.insertar(9);
        arbol.insertar(56);
        arbol.insertar(19);
        arbol.insertar(6);
        arbol.insertar(72);
        arbol.insertar(64);
        arbol.insertar(84);
        arbol.insertar(20);
       
            arbol.insertar(48);
            arbol.insertar(43);
            arbol.insertar(64);
            arbol.insertar(57);
            
            arbol.insertar(47);
          
            arbol.insertar(78);
         */
        arbol.insertar(56);
       arbol.insertar(13);
        arbol.insertar(78);
        arbol.insertar(7);
        arbol.insertar(24);
        arbol.insertar(100);
        arbol.insertar(15);
  
        System.out.println(arbol.toString());
        System.out.println(arbol.listar().toString());
        System.out.println("MINIMO? " + arbol.minimoElem());
        System.out.println("MAXIMO? " + arbol.maximoElem());
        System.out.println("ARBOL: ");
        System.out.println(arbol.toString());
      //  System.out.println(arbol.eliminar(19));
       // System.out.println("ARBOL con eliminarr : ");
      //  System.out.println(arbol.toString());
      //  System.out.println(arbol.listar().toString());
     //   arbol.eliminarMinimo();
     //    System.out.println("ARBOL con eliminarrMINIMO : ");
      //  System.out.println(arbol.toString());
        
        
    }
}
