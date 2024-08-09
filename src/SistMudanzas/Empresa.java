/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SistMudanzas;

import estructuras.Diccionario;
import estructuras.GrafoEtiquetado;
import estructuras.MapeoAMuchos;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author giuli
 */
public class Empresa {

    //ESTRUCTURAS
    private static final Diccionario ciudades = new Diccionario();
    private static final MapeoAMuchos solicitudes = new MapeoAMuchos();
    private static final GrafoEtiquetado mapaRutas = new GrafoEtiquetado();
    private static final HashMap<String, Cliente> clientes = new HashMap<>();
    private static final Scanner sc = new Scanner(System.in);
    private static FileWriter logWriter;

    public static void main(String[] args) {
        System.out.println("QUE PASAAA");
        boolean exito = cargarDatos();

        if (exito) {
            System.out.println("Bienvenido");
            System.out.println("Se cargaron de manera exitosa los datos de 30 ciudades,20 clientes, 40 rutas entre esas ciudades, y 20 pedidos");
            menu();
        }
    }

    public static boolean cargarDatos() {
        String archivo = "C:\\Users\\giuli\\Desktop\\DatosCarga.txt";
        String[] datos;
        boolean exito = true;
        System.out.println("CARGA");
        inicializarLog();
        System.out.println("chau log");
        try {
            FileReader lector = new FileReader(archivo);
            try (BufferedReader buffer = new BufferedReader(lector)) {
                String linea;
                while ((linea = buffer.readLine()) != null) {
                    datos = linea.split(";");
                    switch (datos[0]) {
                        case "C":
                            cargarCiudad(Integer.parseInt(datos[1]), datos[2], datos[3]);
                            ;
                            break;
                        case "P":
                            cargarCliente(datos[1], Integer.parseInt(datos[2]), datos[3], datos[4], datos[5], datos[6]);
                            ;
                            break;
                        case "R":
                            cargarMapa(Integer.parseInt(datos[1]), Integer.parseInt(datos[2]), Double.parseDouble(datos[3]));
                            ;
                            break;
                        case "S":
                            cargarSolicitud(Integer.parseInt(datos[1]), Integer.parseInt(datos[2]), datos[3], datos[4], Integer.parseInt(datos[5]), Integer.parseInt(datos[6]), Integer.parseInt(datos[7]), datos[8], datos[9], datos[10]);

                    }

                }
                // EN CASO DE QUE OCURRA UN ERROR MOSTRAMOS LA EXCEPCION Y NO SE EJECUTA EL MENU PRINCIPAL
            }
        } catch (FileNotFoundException ex) {
            exito = false;
            System.err.println(ex.getMessage() + "El archivo al que intenta acceder no existe o la ruta es incorrecta");
        } catch (IOException ex) {
            exito = false;
            System.err.println(ex.getMessage() + "Error leyendo el archivo.");
        }
        return exito;
    }

    public static void cargarCiudad(int codigo, String nombre, String provincia) {
        // METODO QUE CARGA UNA CIUDAD AL AVL DICCIONARIO, Y AL GRAFO PARA EL MAPA DE RUTAS
        Ciudad ciudad = new Ciudad(codigo, nombre, provincia);
        boolean exito = ciudades.insertar(codigo, ciudad) && mapaRutas.insertarVertice(codigo);
        if (exito) {
            escribirEnLog("Se cargo la " + ciudad.toString());
        } else {
            escribirEnLog("No se pudo cargar la " + ciudad.toString());
        }
    }

    public static void cargarCliente(String tipoDoc, int numDoc, String apellido, String nombre, String telefono, String email) {
        // METODO QUE CARGA UN CLIENTE AL HASH MAP
        Cliente cliente = new Cliente(tipoDoc, numDoc, apellido, nombre, telefono, email);
        String clave = tipoDoc + numDoc;
        if (clientes.containsKey(clave)) {
            escribirEnLog("No se pudo cargar el " + cliente.toString());
        } else {
            clientes.put(clave, cliente);
            escribirEnLog("Se cargo el " + cliente.toString());
        }
    }

    public static void cargarMapa(int codigoOrigen, int codigoDestino, double etiqueta) {
        // METODO QUE CARGA UNA RUTA AL GRAFO ENTRE 2 CIUDADES EXISTENTES
        boolean exito = mapaRutas.existeVertice(codigoOrigen) && mapaRutas.existeVertice(codigoDestino);
        if (exito) {
            boolean yaExiste = mapaRutas.existeArco(codigoOrigen, codigoDestino);
            if (yaExiste) {
                escribirEnLog("No se pudo cargar la Ruta entre " + codigoOrigen + " y " + codigoDestino + ", ya que ya existe una ruta entre ambas");
            } else {
                mapaRutas.insertarArco(codigoOrigen, codigoDestino, etiqueta);
                escribirEnLog("Se cargo la Ruta de " + codigoOrigen + " a " + codigoDestino + " con una distancia de " + etiqueta + " kilometros");
            }
        } else {
            escribirEnLog("No se pudo cargar la Ruta entre " + codigoOrigen + " y " + codigoDestino + ", ya que una de las ciudades no esta en el sistema");
        }
    }

    public static void cargarSolicitud(int origen, int destino, String fechaSolicitud, String tipoDocumento, int numeroDocumento, int cantMetrosCubicos,
            int cantBultos, String domicilioRetiro, String domicilioEntrega, String estaPago) {
        // METODO QUE CARGA UN PEDIDO AL AVL MAPEO A MUCHOS ENTRE 2 CIUDADES EXISTENTES
        Solicitud solicitud = new Solicitud(origen, destino, fechaSolicitud, tipoDocumento,
                numeroDocumento, cantMetrosCubicos, cantBultos, domicilioRetiro, domicilioEntrega, estaPago);
        boolean exito = mapaRutas.existeVertice(origen) && mapaRutas.existeVertice(destino);
        if (exito) {
            solicitudes.asociar(origen + "" + destino, solicitud);
            escribirEnLog("Se cargo el " + solicitud.toString());
        } else {
            escribirEnLog("Una de las ciudades no se encuentra en el sistema. Error al solicitar un pedido");
        }
    }

    public static void menu() {
        // MENU PRINCIPAL
        int opcion = 0;
        while (opcion != 10) {
            System.out.println("...................................................... ");
            System.out.println("               MENU - MUDANZAS COMPARTIDAS");
            System.out.println("......................................................");
            System.out.println("Ingrese una opcion");
            System.out.println("1- Realizar ABM de Ciudades");
            System.out.println("2- Realizar ABM de la Red de Rutas");
            System.out.println("3- Realizar ABM de Clientes");
            System.out.println("4- Realizar ABM de Pedidos");
            System.out.println("5- Realizar consultas sobre Clientes");
            System.out.println("6- Realizar consultas sobre Ciudades");
            System.out.println("7- Realizar consultas sobre Viajes (Dada una Ciudad A y una Ciudad B)");
            System.out.println("8- Verificar Viajes");
            System.out.println("9- Mostrar el sistema");
            System.out.println("10- Finalizar el programa");
            System.out.println("---------------------------------------------------------------------");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    ABMciudades();
                    break;
                case 2:
                    //ABMrutas();
                    break;
                case 3:
                    //ABMclientes();
                    break;
                case 4:
                    //ABMpedidos();
                    break;
                case 5:
                    //consultasClientes();
                    break;
                case 6:
                    //consultasCiudades();
                    break;
                case 7:
                    //consultasViajes();
                    break;
                case 8:
                    //verificarViajes();
                    break;
                case 9:
                    //mostrarSistema();
                    break;
                case 10:
                    finalizarLog();
                    System.out.println("Programa finalizado");
                    break;
                default:
                    System.out.println("Opcion invalida. Por favor ingrese una opcion valida");
                    break;
            }
        }
    }

    public static void inicializarLog() {
        String rutaLog = "C:\\Users\\giuli\\Desktop\\log.txt";
        try {
            logWriter = new FileWriter(rutaLog, true); // true para permitir agregar registros al archivo existente
            logWriter.write("Inicio del registro: \n");
            logWriter.flush();
        } catch (IOException ex) {
            System.err.println(ex.getMessage() + "Error al inicializar el archivo log.");
        }
    }

    public static void escribirEnLog(String mensaje) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String fechaHora = formatoFecha.format(new Date());
            logWriter.write(fechaHora + " - " + mensaje + "\n");
            logWriter.flush();
        } catch (IOException ex) {
            System.err.println(ex.getMessage() + "Error al escribir en el archivo log.");
        }
    }

    public static void finalizarLog() {
        try {
            logWriter.write("Fin del registro");
            logWriter.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage() + "Error al finalizar el archivo log.");
        }
    }

    public static void ABMciudades() {
        int opcion = 0;
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Se selecciono la opcion 1. Realizar ABM de Ciudades");
        while (opcion != 4) {
            System.out.println("---------------------------------------------------------------------");
            System.out.println("Ingrese una opcion");
            System.out.println("1. Insertar una nueva Ciudad");
            System.out.println("2. Eliminar una Ciudad existente");
            System.out.println("3. Modificar una Ciudad existente");
            System.out.println("4. Volver al Menu Principal");
            System.out.println("---------------------------------------------------------------------");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    agregarCiudad();
                    break;
                case 2:
                    eliminarCiudad();
                    break;
                case 3:
                    modificarCiudad();
                    break;
                case 4:
                    break; // SE CORTA EL BUCLE    
                default:
                    System.out.println("Opcion invalida. Por favor ingrese una opcion valida");
            }
        }
    }

    private static void agregarCiudad() {
        String txt = "";
        int cp;
        String nombre, provincia;

        System.out.println("Ingrese el numero postal.");
        cp = sc.nextInt();
        System.out.println("Ingrese el nombre de la ciudad.");
        nombre = sc.next();
        System.out.println("Ingrese la provincia.");
        provincia = sc.next();

        Ciudad unaCiudad = new Ciudad(cp, nombre, provincia);

        boolean exito = ciudades.insertar(cp, unaCiudad);

        if (exito) {
            txt = ("Se agrego la ciudad: " + unaCiudad.toString() + " correctamente.");
            System.out.println(txt);
            escribirEnLog(txt);
            //actualiza el sistema, agrega la ciudad al grafo
            mapaRutas.insertarVertice(cp);
        } else {
            System.out.println("ERROR. No ha sido posible agregar la ciudad.");
        }
    }

    //BAJAS
    private static void eliminarCiudad() {
        Scanner sc = new Scanner(System.in);
        String txt = "";
        int cp, eleccion;
        System.out.println("Ingrese el numero postal de la ciudad que desea dar de BAJA.");
        cp = sc.nextInt();

        boolean exito = ciudades.eliminar(cp);

        if (exito) {
            txt = "Ciudad con C.P: " + cp + ", eliminada correctamente.";
            System.out.println(txt);
            escribirEnLog(txt);
            //actualizo el sistema, se elimina tambien el vertice del grafo y sus rutas,
            //como tambien las solicitudes que involucren la ciudad
            mapaRutas.eliminarVertice(cp);
        } else {
            System.out.println("ERROR. No ha sido posible eliminar la ciudad.");
        }
    }

    //MODIFICACIONES
    private static void modificarCiudad() {
        Scanner sc = new Scanner(System.in);
        String txt = "";
        int cp, eleccion;
        String cadena;
        System.out.println("Ingrese el numero postal de la ciudad que desea modificar");
        cp = sc.nextInt();
        Ciudad unaCiudad = (Ciudad) ciudades.obtenerDato(cp); //lo casteo xq el metodo devuelve tipo object
        if (unaCiudad != null) {
            System.out.println("La ciudad que va a modificar es: \n" + unaCiudad.toString());
            System.out.println("Ingrese una opcion");
            System.out.println("1-Modificar nombre de la ciudad");
            System.out.println("2-Modificar provincia de la ciudad");
            System.out.println("3-Volver al menu de Ciudades");
            eleccion = sc.nextInt();
            switch (eleccion) {
                case 1:
                    System.out.println("Ingrese el nuevo nombre de la Ciudad con codigo postal " + cp);
                    cadena = sc.nextLine();
                    unaCiudad.setNombre(cadena);
                    escribirEnLog("La Ciudad con codigo postal " + cp + " ,procede a llamarse: " + cadena);
                    break;
                case 2:
                    System.out.println("Ingrese la nueva provincia de la Ciudad con codigo postal " + cp);
                    cadena = sc.nextLine();
                    unaCiudad.setProvincia(cadena);
                    escribirEnLog("La Ciudad con codigo postal " + cp + " ahora pertenece a la provincia de " + cadena);
                    break;
                case 3:
                    break; // SE CORTA EL BUCLE
                default:
                    System.out.println("Opcion invalida. Por favor ingrese una opcion valida");
            }
        } else {
            System.out.println("No existe ninguna ciudad con el codigo postal ingresado, intente nuevamente");
        }

    }

}
