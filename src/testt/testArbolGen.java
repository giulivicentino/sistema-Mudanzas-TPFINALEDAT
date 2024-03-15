package testt;

import jerarquicas.ArbolGen;
import lineales.dinamicas.Lista;

/**
 *
 * @author Giuli Vicentino
 */
public class testArbolGen {

    public static void main(String[] args) {
        ArbolGen arbol = new ArbolGen();
        System.out.println("vacio??? " + arbol.toString());
        /*     arbol.insertar('a',null);
        arbol.insertar('b', 'a');
        arbol.insertar('c','a');
        arbol.insertar('d','a');
        arbol.insertar('e','b');
        arbol.insertar('f','b');
        arbol.insertar('j','f');
        arbol.insertar('k','f');
        arbol.insertar('l','f');
        arbol.insertar('g','d');
        arbol.insertar('h','d');
        arbol.insertar('i','d');
        arbol.insertar('m','g');
        arbol.insertar('p','m');
        arbol.insertar('q','m');
        arbol.insertar('n','i');
        arbol.insertar('o','i');
         */
 /*
        arbol.insertar(1, null);
        arbol.insertar(2, 1);
        arbol.insertar(3, 1);
        arbol.insertar(4, 2);
        arbol.insertar(5, 3);
        arbol.insertar(6, 3);

        System.out.println("Arbol ORIGINAL: ");
        System.out.println(arbol.toString());
        System.out.println("RECORRIDOS");
        System.out.println("Recorrido inorden: " + arbol.listarInorden().toString());
        System.out.println("Recorrido preorden: " + arbol.listarPreorden().toString());
        System.out.println("Recorrido posorden: " + arbol.listarPosorden().toString());
        if (arbol.pertenece('z')) {
            System.out.println("SI pertenece");
        } else {
            System.out.println("NO pertenece");
        }
        System.out.println("Padre de g? " + arbol.padre('g'));
        System.out.println("Padre de 3? " + arbol.padre(3));
        System.out.println("Nivel de 5? " + arbol.nivel(5));
        System.out.println("Ancestros de 3: " + arbol.ancestros(3));
        System.out.println("Ancestros de 6: " + arbol.ancestros(6));
        System.out.println("Ancestro de q? " + arbol.ancestros('q'));
        System.out.println("Altura del arbol???  " + arbol.altura());
        ArbolGen arbolClon = arbol.clone();
        
        System.out.println("Arbol ORIGINAL: ");
        System.out.println(arbol.toString());
        System.out.println("Arbol clonado?? ");
        System.out.println(arbolClon.toString());
         

     
        System.out.println("Arbol: " + arbol.toString());
        //arbol.insertarEnPosicion(10, 54, 2);
        //System.out.println("Arbol con el nuevooOo: " + arbol.toString());
System.out.println("Arbol con listado niveles pares: "+arbol.listarNivelesPares().toString());
ArbolGen pino = new ArbolGen();
        pino.insertar(1,1);
        pino.insertar(2,1);
        pino.insertar(3,1);
        pino.insertar(4,1);
        pino.insertar(5,2);
        pino.insertar(6, 2);
        pino.insertar(7, 2);
        pino.insertar(8, 3);
        pino.insertar(9, 8);
        pino.insertar(10, 6);
        pino.insertar(11, 5);
        //pino.insertar(12, 10);
        System.out.println("Nodo mas profundo: "+pino.obtenerNodoMasProfundo().toString());
        /* Lista lista = new Lista();
        lista.insertar(20, 1);
        lista.insertar(13, 2);
        lista.insertar(12, 3);
       lista.insertar(45, 4);
        System.out.println("Se verifica?: " + arbol.verificarCamino(lista));
        System.out.println(arbol.listarEntreNiveles(1, 2).toString());
        System.out.println("LISTAR HASTA NIVEL 2"+arbol.listarHastaNivel(2).toString());
         
 
 ArbolGen arbol2 = new ArbolGen();
 arbol2.insertar('A',null);
 arbol2.insertar('H','A');
  arbol2.insertar('B','A');
   arbol2.insertar('Z','A');
    arbol2.insertar('D','H');
     arbol2.insertar('P','D');
      arbol2.insertar('G','D');
       arbol2.insertar('Q','H');
        arbol2.insertar('F','Z');
         arbol2.insertar('W','F');
          arbol2.insertar('N','F');
           arbol2.insertar('C','Z');
            arbol2.insertar('J','Z');
             arbol2.insertar('V','J');
              arbol2.insertar('M','J');
              System.out.println("Arbol: "+arbol2.toString());
              System.out.println("es J, sobrino de H?  "+arbol2.esSobrino('J','H'));
              */
              
                 arbol.insertar(20, null);
        arbol.insertar(13, 20);
        arbol.insertar(54, 20);
        arbol.insertar(15, 13);
        arbol.insertar(12, 13);
        arbol.insertar(11, 54);
        arbol.insertar(27, 54);
        arbol.insertar(4, 54);
        arbol.insertar(17, 27);
        arbol.insertar(5, 54);
         arbol.insertar(6, 54);
          arbol.insertar(7, 54);
              arbol.insertar(77, 17);
        System.out.println("ARBOL:  "+arbol.toString());
        System.out.println("eliminar 4: "+arbol.eliminar(6));
         System.out.println("ARBOL sin 4?:  "+arbol.toString());
         System.out.println("LISTAR HASTA NIVEL 1: "+arbol.listarHastaNivel(3).toString());
    }
}
