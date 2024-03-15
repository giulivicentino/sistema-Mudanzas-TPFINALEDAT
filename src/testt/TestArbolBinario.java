package testt;

import jerarquicas.ArbolBin;
import jerarquicas.ArbolBin;

/**
 *
 * @author Fran
 */
public class TestArbolBinario {

    public static void main(String[] args) {
        ArbolBin arbol = new ArbolBin();
        System.out.println("vacio??? " + arbol.toString());
        //que el nodo padre este creado antes de hacer los hijos!!!!
        //arbol numeros
       /* arbol.insertar(1, null, 'i');
        arbol.insertar(2, 1, 'i');
        arbol.insertar(4, 2, 'i');
        arbol.insertar(5, 2, 'd');
        arbol.insertar(6, 4, 'd');
        arbol.insertar(3, 1, 'd');
        arbol.insertar(7, 3, 'i');
        arbol.insertar(8, 3, 'd');
        arbol.insertar(9, 8, 'i');
*/
        arbol.insertar(14, null, 'i');
        arbol.insertar(4, 14, 'i');
        arbol.insertar(7, 4, 'i');
        arbol.insertar(9, 4, 'd');
        arbol.insertar(7, 9, 'i');
        arbol.insertar(5, 7, 'i');
        arbol.insertar(4, 5, 'i');
        arbol.insertar(5, 5, 'd');
        arbol.insertar(15, 14, 'd');
        arbol.insertar(5, 15, 'i');
        arbol.insertar(18, 15, 'd');
        arbol.insertar(16, 18, 'i');
        arbol.insertar(7, 16, 'd');
        arbol.insertar(15, 18, 'd');
        //arbol.insertar(10, 6, 'i');
        /*
        //arbol del apunte
        arbol.insertar('A', null, 'I');
        arbol.insertar('B', 'A', 'I');
        arbol.insertar('C', 'A', 'D');
        arbol.insertar('D', 'B', 'I');
        arbol.insertar('E', 'C', 'I');
        arbol.insertar('F', 'C', 'D');
        arbol.insertar('G', 'E', 'I');
        arbol.insertar('H', 'E', 'D');
         */
        System.out.println("ARBOL ORIGINAL" + arbol.toString());
        System.out.println("ANCESTROS DE 7: " + arbol.obtenerAncestros(7));
        System.out.println("ANCESTROS DE 9: " + arbol.obtenerAncestros(9));
        System.out.println("DESCENDIENTES DE 3???" + arbol.obtenerDescendientes(3));
        System.out.println("PREORDEN:    " + arbol.listarPreOrden());
        System.out.println("POSTORDEN:   " + arbol.listarPosorden());
        System.out.println("Altura del arbol:" + arbol.altura());
        System.out.println("Nivel de 2: " + arbol.nivel(2));
        System.out.println("Nivel de 4: " + arbol.nivel(4));
        System.out.println("Nivel de 6: " + arbol.nivel(6));
        System.out.println("ARBOL ORIGINAL" + arbol.toString());

        ArbolBin arbolClon = new ArbolBin();
        arbolClon = arbol.clone();
        arbolClon.insertar(10, 6, 'i');
        System.out.println("ARBOL CLONADO + NODO 10: " + arbolClon);
        System.out.println("ARBOL ORIGINAL" + arbol.toString());
        System.out.println("FRONTER????: " + arbol.frontera().toString());
        for (int i = 1; i < 10; i++) {
            System.out.println("Nodo: " + i + " y su padre es: " + arbol.padre(i));

        }

    }

}
