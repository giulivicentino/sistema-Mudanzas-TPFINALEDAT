/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SistMudanzas;

import estructuras.Diccionario;
import estructuras.GrafoEtiquetado;
import estructuras.Lista;
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

    // ESTRUCTURAS
    private static final Diccionario ciudades = new Diccionario();
    private static final MapeoAMuchos solicitudes = new MapeoAMuchos();
    private static final GrafoEtiquetado mapaRutas = new GrafoEtiquetado();
    private static final HashMap<String, Cliente> clientes = new HashMap<>();
    private static final Scanner sc = new Scanner(System.in);
    private static FileWriter logWriter;

    public static void main(String[] args) {
        boolean exito = cargarDatos();

        if (exito) {
            System.out.println("Bienvenido");
            System.out.println(
                    "Se cargaron de manera exitosa los datos de 30 ciudades,20 clientes, 40 rutas entre esas ciudades, y 20 pedidos");
            menu();
        }
    }

    // EJERCICIO
    // 1-----------------------------------------------------------------------------------------------------------------------------------------------
    public static boolean cargarDatos() {
        String archivo = "C:\\Users\\giuli\\Desktop\\DatosCarga.txt";
        String[] datos;
        boolean exito = true;
        inicializarLog();
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
                            cargarMapa(Integer.parseInt(datos[1]), Integer.parseInt(datos[2]),
                                    Double.parseDouble(datos[3]));
                            ;
                            break;
                        case "S":
                            cargarSolicitud(Integer.parseInt(datos[1]), Integer.parseInt(datos[2]), datos[3], datos[4],
                                    Integer.parseInt(datos[5]), Integer.parseInt(datos[6]), Integer.parseInt(datos[7]),
                                    datos[8], datos[9], datos[10]);

                    }

                }
                // EN CASO DE QUE OCURRA UN ERROR MOSTRAMOS LA EXCEPCION Y NO SE EJECUTA EL MENU
                // PRINCIPAL
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

    public static void cargarCiudad(int codigo, String nombre, String provincia) { //carga ciudad al AVL diccionario y rutas a un GRAFO
        Ciudad ciudad = new Ciudad(codigo, nombre, provincia);
        boolean exito = ciudades.insertar(codigo, ciudad) && mapaRutas.insertarVertice(codigo);
        if (exito) {
            escribirEnLog("Se cargo la " + ciudad.toString());
        } else {
            escribirEnLog("No se pudo cargar la " + ciudad.toString());
        }
    }

    public static void cargarCliente(String tipoDoc, int numDoc, String apellido, String nombre, String telefono,
            String email) {
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
                escribirEnLog("No se pudo cargar la Ruta entre " + codigoOrigen + " y " + codigoDestino
                        + ", ya que ya existe una ruta entre ambas");
            } else {
                mapaRutas.insertarArco(codigoOrigen, codigoDestino, etiqueta);
                escribirEnLog("Se cargo la Ruta de " + codigoOrigen + " a " + codigoDestino + " con una distancia de "
                        + etiqueta + " kilometros");
            }
        } else {
            escribirEnLog("No se pudo cargar la Ruta entre " + codigoOrigen + " y " + codigoDestino
                    + ", ya que una de las ciudades no esta en el sistema");
        }
    }

    public static void cargarSolicitud(int origen, int destino, String fechaSolicitud, String tipoDocumento,
            int numeroDocumento, int cantMetrosCubicos,
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
            System.out.println("»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»««««««««««««««««««««««««««««««««««««");
            System.out.println("                    MENU - MUDANZAS COMPARTIDAS");
            
            System.out.println("Ingrese una opcion");
            System.out.println(" (1)  Realizar ABM de Ciudades");
            System.out.println(" (2)  Realizar ABM de la Red de Rutas");
            System.out.println(" (3)  Realizar ABM de Clientes");
            System.out.println(" (4)  Realizar ABM de Pedidos");
            System.out.println(" (5)  Realizar consultas sobre Clientes");
            System.out.println(" (6)  Realizar consultas sobre Ciudades");
            System.out.println(" (7)  Realizar consultas sobre Viajes (Dada una Ciudad A y una Ciudad B)");
            System.out.println(" (8)  Verificar Viajes");
            System.out.println(" (9)  Mostrar el sistema");
            System.out.println(" (10) Finalizar el programa");
            System.out.println("»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»««««««««««««««««««««««««««««««««««««");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    ABMciudades();
                    break;
                case 2:
                    ABMrutas();
                    break;
                case 3:
                    ABMclientes();
                    break;
                case 4:
                    ABMpedidos();
                    break;
                case 5:
                    consultasClientes();
                    break;
                case 6:
                    consultasCiudades();
                    break;
                case 7:
                    consultasViajes();
                    break;
                case 8:
                    verificarViaje();
                    break;
                case 9:
                    mostrarSistema();
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

    // EJERCICIO
    // 2-----------------------------------------------------------------------------------------------------------------------------------------------
    public static void ABMciudades() {
        int opcion = 0;
        System.out.println("--- ABM CIUDADES ---");
        while (opcion != 4) {
            System.out.println("---------------------------------------------------------------------");
            System.out.println("Ingrese una opcion");
            System.out.println("(1) Insertar una nueva Ciudad");
            System.out.println("(2) Eliminar una Ciudad existente");
            System.out.println("(3) Modificar una Ciudad existente");
            System.out.println("(4) Volver al Menu Principal");
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
            // actualiza el sistema, agrega la ciudad al grafo
            mapaRutas.insertarVertice(cp);
        } else {
            System.out.println("ERROR. No ha sido posible agregar la ciudad.");
        }
    }

    // BAJAS
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
            // actualizo el sistema, se elimina tambien el vertice del grafo y sus rutas,
            // como tambien las solicitudes que involucren la ciudad
            mapaRutas.eliminarVertice(cp);
        } else {
            System.out.println("ERROR. No ha sido posible eliminar la ciudad.");
        }
    }

    // MODIFICACIONES
    private static void modificarCiudad() {
        Scanner sc = new Scanner(System.in);
        String txt = "";
        int cp, eleccion;
        String cadena;
        System.out.println("Ingrese el numero postal de la ciudad que desea modificar");
        cp = sc.nextInt();
        Ciudad unaCiudad = (Ciudad) ciudades.obtenerDato(cp); // lo casteo xq el metodo devuelve tipo object
        if (unaCiudad != null) {
            System.out.println("La ciudad que va a modificar es: \n" + unaCiudad.toString());
            System.out.println("Ingrese una opcion");
            System.out.println("1) Modificar nombre de la ciudad");
            System.out.println("2) Modificar provincia de la ciudad");
            System.out.println("3)  Volver al menu de Ciudades");
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
    // EJERCICIO
    // 3-----------------------------------------------------------------------------------------------------------------------------------------------

    public static void ABMrutas() {
        int opcion = 0;
        System.out.println("--- ABM RUTAS ---");

        while (opcion != 4) {
            System.out.println("---------------------------------------------------------------------");
            System.out.println("Ingrese una opcion");
            System.out.println("(1) Insertar una nueva Ruta");
            System.out.println("(2) Eliminar una Ruta existente");
            System.out.println("(3) Modificar una Ruta existente");
            System.out.println("(4) Volver al Menu Principal");
            System.out.println("---------------------------------------------------------------------");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    insertarRuta();
                    break;
                case 2:
                    // eliminarRuta();
                    break;
                case 3:
                    System.out.println("No es posible modificar la distancia de una Ciudad a otra");
                    System.out.println("Elimine una Ruta existente o Inserte una nueva Ruta");
                    ;
                    break;
                case 4:
                    break; // SE CORTA EL BUCLE
                default:
                    System.out.println("Opcion invalida. Por favor ingrese una opcion valida");
            }
        }
    }

    public static void insertarRuta() {
        int origen, destino;
        double km;
        System.out.println("Ingrese el codigo postal de la ciudad de origen");
        origen = sc.nextInt();
        System.out.println("Ingrese el codigo postal de la ciudad de destino");
        destino = sc.nextInt();
        System.out.println("Ingrese la distancia en kilometros entre las dos ciudad");
        km = sc.nextDouble();

        // LAS DOS CIUDADES DEBEN EXISTIR PARA CREAR UNA RUTA ENTRE ELLAS
        if (mapaRutas.existeVertice(origen) && mapaRutas.existeVertice(destino)) {
            // ADEMAS, NO DEBE EXISTIR UNA RUTA ENTRE ELLAS
            if (mapaRutas.existeArco(origen, destino)) {
                System.out.println("Ya existe una Ruta que conecta las dos Ciudades ingresadas. ERROR");
            } else {
                System.out.println("Ingrese la distancia entre las dos Ciudades");
                km = sc.nextDouble();
                if (km <= 0) {
                    System.out.println("Ingrese una distancia valida");
                } else {
                    cargarMapa(origen, destino, km);
                }
            }
        } else {
            System.out.println("Una de las Ciudades ingresadas no existe en el sistema. ERROR");
        }

    }

    public static void eliminarRuta() {
        // METODO QUE ELIMINA UNA RUTA DEL SISTEMA
        int origen, destino;
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Eliminar una Ruta existente entre dos Ciudades");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Ingrese el codigo postal de la primera Ciudad");
        origen = sc.nextInt();
        System.out.println("Ingrese el codigo postal de la segunda Ciudad");
        destino = sc.nextInt();
        // LAS DOS CIUDADES DEBEN EXISTIR PARA BORRAR LA RUTA ENTRE ELLAS
        if (mapaRutas.existeVertice(origen) && mapaRutas.existeVertice(destino)) {
            // ADEMAS, DEBE EXISTIR UNA RUTA ENTRE ELLAS
            if (mapaRutas.existeArco(origen, destino)) {
                if (mapaRutas.eliminarArco(origen, destino)) {
                    escribirEnLog("La Ruta que conecta a " + origen + " y " + destino + " fue eliminada con exito");
                } else {
                    escribirEnLog("La Ruta que conecta a " + origen + " y " + destino + " no se pudo eliminar");
                }
            } else {
                System.out.println("No existe ninguna Ruta que conecta estas dos Ciudades. ERROR");
            }
        } else {
            System.out.println("Una de las Ciudades ingresadas no existe en el sistema. ERROR");
        }
    }

    // EJERCICIO
    // 4-----------------------------------------------------------------------------------------------------------------------------------------------
    public static void ABMclientes() {
        int opcion = 0;
        System.out.println("--- ABM CLIENTES ---");
        while (opcion != 4) {
            System.out.println("---------------------------------------------------------------------");
            System.out.println("Ingrese una opcion");
            System.out.println("(1) Insertar un nuevo Cliente");
            System.out.println("(2) Eliminar un Cliente existente");
            System.out.println("(3) Modificar un Cliente existente");
            System.out.println("(4) Volver al Menu Principal");
            System.out.println("---------------------------------------------------------------------");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    insertarCliente();
                    break;
                case 2:
                    eliminarCliente();
                    break;
                case 3:
                    modificarCliente();
                    break;
                case 4:
                    break; // SE CORTA EL BUCLE
                default:
                    System.out.println("Opcion invalida. Por favor ingrese una opcion valida");
            }
        }
    }

    public static void insertarCliente() {
        // METODO QUE INSERTA UN CLIENTE AL SISTEMA
        int numDoc;
        String tipoDoc;
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Dar de alta un nuevo Cliente al Sistema");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Ingrese el tipo de documento del nuevo Cliente");
        tipoDoc = sc.next();
        System.out.println("Ingrese el numero de documento del nuevo Cliente");
        numDoc = sc.nextInt();
        // PARA INSERTARLO, NO DEBE EXISTIR UN CLIENTE CON LA MISMA CLAVE
        String clave = tipoDoc + numDoc;
        if (clientes.containsKey(clave)) {
            System.out.println("El Cliente con clave " + clave + " ya existe. ERROR");
        } else {
            sc.nextLine();
            String apellido, nombre, telefono, email;
            System.out.println("Ingrese el apellido del nuevo Cliente");
            apellido = sc.nextLine();
            System.out.println("Ingrese el nombre del nuevo Cliente");
            nombre = sc.nextLine();
            System.out.println("Ingrese el numero de telefono del nuevo Cliente");
            telefono = sc.nextLine();
            System.out.println("Ingrese la direccion de email del nuevo Cliente");
            email = sc.nextLine();
            cargarCliente(tipoDoc, numDoc, apellido, nombre, telefono, email);
        }
    }

    public static void eliminarCliente() {
        // METODO QUE ELIMINA UN CLIENTE DEL SISTEMA
        int numDoc;
        String tipoDoc;
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Dar de baja un Cliente del sistema");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Ingrese el tipo de documento del Cliente a eliminar");
        tipoDoc = sc.next();
        System.out.println("Ingrese el numero de documento del Cliente a eliminar");
        numDoc = sc.nextInt();
        // PARA ELIMINARLO, EL CLIENTE DEBE EXISTIR
        String clave = tipoDoc + numDoc;
        if (clientes.containsKey(clave)) {
            boolean seElimino = clientes.remove(clave) != null;
            if (seElimino) {
                escribirEnLog("El Cliente con clave " + clave + " se elimino con exito del sistema");
            } else {
                escribirEnLog("NO se pudo eliminar al Cliente con clave " + clave);
            }
        } else {
            System.out.println("El Cliente con clave " + clave + " no existe. ERROR");
        }
    }

    public static void modificarCliente() {
        // METODO QUE MODIFICA ATRIBUTOS DE UN CLIENTE DEL SISTEMA
        // NO SE PUEDEN MODIFICAR LAS CLAVES
        int numDoc;
        String tipoDoc;
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Modificar un Cliente del sistema");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Ingrese el tipo de documento del Cliente a modificar");
        tipoDoc = sc.next();
        System.out.println("Ingrese un numero de documento del Cliente a modificar");
        numDoc = sc.nextInt();
        // SI EL CLIENTE EXISTE, MODIFICAMOS EL ATRIBUTO QUE EL USUARIO ELIJA
        String clave = tipoDoc + numDoc;
        if (clientes.containsKey(clave)) {
            Cliente cliente = (Cliente) clientes.get(clave);
            int opcion = 0;
            String cadena;
            while (opcion != 5) {
                System.out.println("---------------------------------------------------------------------");
                System.out.println("Ingrese una opcion");
                System.out.println("1) Modificar el apellido del Cliente con numero de documento " + numDoc);
                System.out.println("2) Modificar el nombre del Cliente con numero de documento " + numDoc);
                System.out.println("3) Modificar el numero de telefono del Cliente con numero de documento " + numDoc);
                System.out.println("4) Modificar la direccion de email del Cliente con numero de documento " + numDoc);
                System.out.println("5) Volver al menu de ABMclientes");
                System.out.println("---------------------------------------------------------------------");
                opcion = sc.nextInt();
                sc.nextLine();
                switch (opcion) {
                    case 1:
                        System.out.println("Ingrese el nuevo apellido del Cliente con clave " + clave);
                        cadena = sc.nextLine();
                        cliente.setApellido(cadena);
                        escribirEnLog("El Cliente con clave " + clave + " ahora se apellida " + cadena);
                        break;
                    case 2:
                        System.out.println("Ingrese el nuevo nombre del Cliente con clave " + clave);
                        cadena = sc.nextLine();
                        cliente.setNombre(cadena);
                        escribirEnLog("El Cliente con clave " + clave + " ahora se llama " + cadena);
                        break;
                    case 3:
                        System.out.println("Ingrese el nuevo numero de telefono del Cliente con clave " + clave);
                        cadena = sc.nextLine();
                        cliente.setTelefono(cadena);
                        escribirEnLog("El Cliente con clave " + clave + " cambio de numero de telefono a " + cadena);
                        break;
                    case 4:
                        System.out.println("Ingrese la nueva direccion de email del Cliente con clave " + clave);
                        cadena = sc.nextLine();
                        cliente.setEmail(cadena);
                        escribirEnLog("El Cliente con clave " + clave + " cambio su direccion de email a " + cadena);
                        break;
                    case 5:
                        break; // SE CORTA EL BUCLE
                    default:
                        System.out.println("Opcion invalida. Por favor ingrese una opcion valida");
                }
            }
        } else {
            System.out.println("El Cliente con clave " + clave + " no existe. ERROR");
        }
    }

    // EJERCICIO
    // 5-----------------------------------------------------------------------------------------------------------------------------------------------
    public static void ABMpedidos() {
        int opcion = 0;
        System.out.println("--- ABM PEDIDOS ---");
        while (opcion != 4) {
            System.out.println("---------------------------------------------------------------------");
            System.out.println("Ingrese una opcion");
            System.out.println("(1) Agregar un nuevo Pedido");
            System.out.println("(2) Eliminar un Pedido existente");
            System.out.println("(3) Modificar un Pedido existente");
            System.out.println("(4) Volver al Menu Principal");
            System.out.println("---------------------------------------------------------------------");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    insertarPedido();
                    break;
                case 2:
                    eliminarPedido();
                    break;
                case 3:
                    modificarPedido();
                    break;
                case 4:
                    break; // SE CORTA EL BUCLE
                default:
                    System.out.println("Opcion invalida. Por favor ingrese una opcion valida");
            }
        }
    }

    public static void insertarPedido() {
        // METODO QUE INSERTA UN PEDIDO AL SISTEMA
        int origen, destino;
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Agregar un nuevo Pedido entre Ciudades");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Ingrese el codigo postal de la Ciudad origen");
        origen = sc.nextInt();
        System.out.println("Ingrese el codigo postal de la Ciudad destino");
        destino = sc.nextInt();
        // PARA INSERTARLO, LAS CIUDADES DEBEN EXISTIR
        if (mapaRutas.existeVertice(origen) && mapaRutas.existeVertice(destino)) {
            System.out.println("Ingrese el tipo de documento del Cliente");
            String tipoDoc = sc.next();
            System.out.println("Ingrese el numero de documento del Cliente");
            int numDoc = sc.nextInt();
            // ADEMAS, EL CLIENTE DEBE EXISTIR Y NO DEBE REALIZAR UN PEDIDO IGUAL
            String clave = tipoDoc + numDoc;
            if (clientes.containsKey(clave)) {
                System.out.println("Ingrese la fecha que solicito el Pedido");
                String fechaSolicitud = sc.next();
                if (solicitudes.obtenerPedido(origen + "" + destino, new Solicitud(origen, destino,
                        fechaSolicitud, tipoDoc, numDoc, 0, 0,
                        null, null, null)) != null) {
                    System.out.println("Ya existe un pedido en camino entre las Ciudades ingresadas, realizado por "
                            + clave + " en esa fecha. ERROR");
                } else {
                    System.out.println("Ingrese la cantidad de metros cubicos que ocupa el Pedido");
                    int cantMetrosCubicos = sc.nextInt();
                    System.out.println("Ingrese la cantidad de bultos que ocupa el Pedido");
                    int cantBultos = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Ingrese el domicilio de retiro");
                    String domicilioRetiro = sc.nextLine();
                    System.out.println("Ingrese el domicilio de entrega");
                    String domicilioEntrega = sc.nextLine();
                    System.out.println("El envio esta pago? (T/F)");
                    String estaPago = sc.nextLine();
                    cargarSolicitud(origen, destino, fechaSolicitud, tipoDoc, numDoc, cantMetrosCubicos, cantBultos,
                            domicilioRetiro, domicilioEntrega, estaPago);
                }
            } else {
                System.out.println("El Cliente con clave " + clave + " no existe. ERROR");
            }
        } else {
            System.out.println("Una de las Ciudades ingresadas no existe en el sistema. ERROR");
        }
    }

    public static void eliminarPedido() {
        // METODO QUE ELIMINA UN PEDIDO DEL SISTEMA
        int origen, destino, numDoc;
        String tipoDoc;
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Eliminar un Pedido existente entre Ciudades");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Ingrese el codigo postal de la Ciudad origen");
        origen = sc.nextInt();
        System.out.println("Ingrese el codigo postal de la Ciudad destino");
        destino = sc.nextInt();
        // PARA ELIMINARLO LAS CIUDADES DEBEN EXISTIR
        if (mapaRutas.existeVertice(origen) && mapaRutas.existeVertice(destino)) {
            System.out.println("Ingrese el tipo de documento del Cliente que quiere cancelar su Pedido");
            tipoDoc = sc.next();
            System.out.println("Ingrese el numero de documento del Cliente que quiere cancelar el Pedido");
            numDoc = sc.nextInt();
            // ADEMAS, EL CLIENTE Y EL PEDIDO DEBEN EXISTIR
            String clave = tipoDoc + numDoc;
            if (clientes.containsKey(clave)) {
                // ADEMAS, EL PEDIDO DEBE EXISTIR
                System.out.println("Ingrese la fecha que solicito el Pedido");
                String fechaSolicitud = sc.next();
                if (solicitudes.desasociar(origen + "" + destino, new Solicitud(origen, destino,
                        fechaSolicitud, tipoDoc, numDoc, 0, 0,
                        null, null, null))) {
                    escribirEnLog("Se cancelo el Pedido de " + clave + " entre " + origen + " y " + destino
                            + " realizado el " + fechaSolicitud);
                } else {
                    System.out.println("El Pedido que quiere eliminar no existe");
                }
            } else {
                System.out.println("El Cliente con clave " + clave + " no existe. ERROR");
            }
        } else {
            System.out.println("Una de las Ciudades ingresadas no existe en el sistema. ERROR");
        }
    }

    public static void modificarPedido() {
        // METODO QUE MODIFICA ATRIBUTOS DE UN PEDIDO DEL SISTEMA
        // NO SE PUEDEN MODIFICAR LAS CLAVES
        int origen, destino, numDoc;
        String tipoDoc;
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Modificar un Pedido existente entre Ciudades");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Ingrese el codigo postal de la Ciudad origen");
        origen = sc.nextInt();
        System.out.println("Ingrese el codigo postal de la Ciudad destino");
        destino = sc.nextInt();
        // SI EL PEDIDO EXISTE, MODIFICAMOS EL ATRIBUTO QUE EL USUARIO ELIJA
        if (mapaRutas.existeVertice(origen) && mapaRutas.existeVertice(destino)) {
            System.out.println("Ingrese el tipo de documento del Cliente que quiere modificar su Pedido");
            tipoDoc = sc.next();
            System.out.println("Ingrese el numero de documento del Cliente que quiere modificar su Pedido");
            numDoc = sc.nextInt();
            String clave = tipoDoc + numDoc;
            if (clientes.containsKey(clave)) {
                System.out.println("Ingrese la fecha que solicito el Pedido");
                String fechaSolicitud = sc.next();
                Solicitud solicitud = (Solicitud) solicitudes.obtenerPedido(origen + "" + destino,
                        new Solicitud(origen, destino,
                                fechaSolicitud, tipoDoc, numDoc, 0, 0,
                                null, null, null));
                if (solicitud != null) {
                    int opcion = 0;
                    String cadena;
                    int cantidad;
                    while (opcion != 6) {
                        System.out.println("---------------------------------------------------------------------");
                        System.out.println("Ingrese una opcion");
                        System.out.println("1) Modificar la cantidad de metros cubicos que ocupa el Pedido");
                        System.out.println("2) Modificar la cantidad de bultos que compone el Pedido");
                        System.out.println("3) Cambiar la direccion de retiro del Pedido");
                        System.out.println("4) Cambiar la direccion de entrega del Pedido");
                        System.out.println("5) Cambiar la condicion de pago en la que se encuentra el Pedido");
                        System.out.println("6) Volver al menu de ABMpedidos");
                        System.out.println("---------------------------------------------------------------------");
                        opcion = sc.nextInt();
                        sc.nextLine();
                        switch (opcion) {
                            case 1:
                                System.out.println("Ingrese la cantidad de metros cubicos que ahora ocupa el Pedido");
                                cantidad = sc.nextInt();
                                escribirEnLog("El Pedido " + solicitud.toString() + " ahora ocupa " + cantidad
                                        + " metros cubicos");
                                solicitud.setCantMetrosCubicos(cantidad);
                                break;
                            case 2:
                                System.out.println("Ingrese la cantidad de bultos que ahora compone el Pedido");
                                cantidad = sc.nextInt();
                                escribirEnLog("El Pedido " + solicitud.toString() + " ahora consta de " + cantidad
                                        + " bultos");
                                solicitud.setCantBultos(cantidad);
                                break;
                            case 3:
                                System.out.println("Ingrese la nueva direccion de retiro");
                                cadena = sc.nextLine();
                                escribirEnLog("El Pedido " + solicitud.toString() + " ahora se retirara en " + cadena);
                                solicitud.setDomicilioRetiro(cadena);
                                break;
                            case 4:
                                System.out.println("Ingrese la nueva direccion de entrega");
                                cadena = sc.nextLine();
                                escribirEnLog("El Pedido " + solicitud.toString() + " ahora se entregara en " + cadena);
                                solicitud.setDomicilioEntrega(cadena);
                                break;
                            case 5:
                                System.out.println("Ingrese la nueva condicion en la que se encuentra el Pedido (T/F)");
                                cadena = sc.nextLine();
                                escribirEnLog(
                                        "El Pedido " + solicitud.toString() + " ahora se encuentra pagado?: " + cadena);
                                solicitud.setEstaPago(cadena);
                                break;
                            case 6:
                                break; // SE CORTA EL BUCLE
                            default:
                                System.out.println("Opcion invalida. Por favor ingrese una opcion valida");
                        }
                    }
                } else {
                    System.out.println("El Pedido que quiere modificar no existe");
                }
            } else {
                System.out.println("El Cliente con clave " + clave + " no existe. ERROR");
            }
        } else {
            System.out.println("Una de las ciudades ingresadas no existe en el sistema. ERROR");
        }
    }

    // EJERCICIO
    // 6-----------------------------------------------------------------------------------------------------------------------------------------------
    public static void consultasClientes() {
        // METODO QUE REALIZA CONSULTAS SOBRE LOS CLIENTES
        // SOLO HAY UNA OPCION, PARA REALIZARLA, EL CLIENTE DEBE EXISTIR
        int opcion = 0;
        System.out.println("--- CONSULTAS CLIENTES ---");
        while (opcion != 2) {
            System.out.println("---------------------------------------------------------------------");
            System.out.println("Ingrese una opcion");
            System.out
                    .println("(1) Dada una clave de un Cliente (tipoDoc+numDoc), mostrar toda la información del mismo");
            System.out.println("(2) Volver al menu principal");
            System.out.println("---------------------------------------------------------------------");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    int numDoc;
                    String tipoDoc;
                    System.out.println("Ingrese un tipo de documento");
                    tipoDoc = sc.next();
                    System.out.println("Ingrese un numero de documento");
                    numDoc = sc.nextInt();
                    String clave = tipoDoc + numDoc;
                    if (clientes.containsKey(clave)) {
                        System.out.println("Informacion del Cliente solicitado: " + clientes.get(clave).toString());
                    } else {
                        System.out.println("No existe ningun Cliente con la clave " + clave);
                    }
                    break;
                case 2:
                    break; // SE CORTA EL BUCLE
                default:
                    System.out.println("Opcion invalida. Por favor ingrese una opcion valida");
            }
        }
    }

    // EJERCICIO
    // 7-----------------------------------------------------------------------------------------------------------------------------------------------
    public static void consultasCiudades() {
        // METODO QUE REALIZA CONSULTAS SOBRE LAS CIUDADES
        int opcion = 0;
        System.out.println("--- CONSULTAS CIUDADES ---");
        while (opcion != 3) {
            System.out.println("---------------------------------------------------------------------");
            System.out.println("Ingrese una opcion");
            System.out.println("(1) Dado un codigo postal de una Ciudad, mostrar toda su informacion");
            System.out.println("(2) Dado un prefijo, devolver todas las Ciudades cuyo código postal comienza con dicho prefijo");
            System.out.println("(3) Volver al menu principal");
            System.out.println("---------------------------------------------------------------------");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    mostrarInfoCiudad();
                    break;
                case 2:
                    prefijoCiudad();
                    break;
                case 3:
                    break; // SE CORTA EL BUCLE
                default:
                    System.out.println("Opcion invalida. Por favor ingrese una opcion valida");
            }
        }
    }

    public static void prefijoCiudad() {
        /*
         * SI EL PREFIJO ES 83 DEBERIA CONSIDERAR LISTAR TODAS LAS CIUDADES
         * CUYO CODIGO POSTAL ESTE EN EL RANGO 8300 HASTA 8399
         */
        // SI NO EXISTEN CIUDADES CON ESE PREFIJO, DEVUELVE LA LISTA VACIA Y ENVIAMOS UN
        // MENSAJE POR PANTALLA
        int prefijo;
        Lista listaCiudades;
        System.out.println("---------------------------------------------------------------------");
        System.out
                .println("Dado un prefijo, devolver todas las Ciudades cuyo codigo postal comienza con dicho prefijo");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Ingrese un prefijo");
        prefijo = sc.nextInt();
        listaCiudades = ciudades.listarRango(prefijo * 100, (prefijo * 100) + 99);
        System.out.println("---------------------------------------------------------------------");
        if (listaCiudades.esVacia()) {
            System.out.println("No existen Ciudades que el prefijo de su codigo postal sea " + prefijo);
        } else {
            System.out.println("Ciudades con prefijo " + prefijo + " en su codigo postal: " + listaCiudades.toString());
        }
    }

    public static void mostrarInfoCiudad() {
        int codigo;
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Dado un codigo postal de una Ciudad, mostrar toda su informacion");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Ingrese un codigo postal");
        codigo = sc.nextInt();
        System.out.println("---------------------------------------------------------------------");
        // PARA REALIZAR LA CONSULTA, LA CIUDAD DEBE EXISTIR
        if (ciudades.existeClave(codigo)) {
            System.out.println("Informacion de la Ciudad solicitada: " + ciudades.obtenerDato(codigo).toString());
        } else {
            System.out.println("No existe ninguna Ciudad con el codigo postal " + codigo);
        }
    }

    // EJERCICIO
    // 8-----------------------------------------------------------------------------------------------------------------------------------------------
    public static void consultasViajes() {
        int respuesta;
        do {
            System.out.println("--- CONSULTA DE VIAJES ---");
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" (1) Obtener el camino que llegue de A a B que pase por menos ciudades");
            System.out.println(" (2) Obtener el camino que llegue de A a B de menor distancia en kilómetros");
            System.out.println(" (3) Obtener todos los caminos posibles de A a B que pasen por una ciudad C sin pasar dos veces por la misma ciudad.");
            System.out.println(" (4) Verificar si es posible llegar de A a B recorriendo como máximo una cantidad X de kilómetros");       
            System.out.println(" (5) Volver al Menu");                
            System.out.println("---------------------------------------------------------------------");
            respuesta = sc.nextInt();
            switch (respuesta) {
                case 1:
                    caminoMenosCiudades();
                    break;
                case 2:
                    caminoMenosKilometros();
                    break;
                case 3:
                    caminoPorCiudadIntermedia();
                    break;
                case 4:
                    caminoXkilometrosMax();
                    break;
                case 5:// VUELVE AL MENU
                    break;
                default:
                    System.out.println("RESPUESTA INVALIDA.");
                    break;
            }
        } while (respuesta != 5);
    }

    public static void caminoMenosCiudades() {
        System.out.println("Ingrese el codigo de la ciudad A inicial");
        int codigoA = sc.nextInt();
        System.out.println("Ingrese el codigo de la ciudad B final");
        int codigoB = sc.nextInt();

        Lista camino = mapaRutas.caminoMasCorto(codigoA, codigoB);
        if (camino != null) {
            System.out.println("EL CAMINO QUE PASA POR MENOS CIUDADES ES: \n" + camino.toString());
        } else {
            System.out.println("NO EXISTE UN CAMINO ENTRE AMBAS CIUDADES");
        }
    }

    public static void caminoMenosKilometros() {
        System.out.println("Ingrese el codigo de la ciudad A inicial");
        int codigoA = sc.nextInt();
        System.out.println("Ingrese el codigo de la ciudad B final");
        int codigoB = sc.nextInt();

        Lista camino = mapaRutas.caminoMasRapido(codigoA, codigoB);
        if (camino != null) {
            System.out.println("EL CAMINO QUE TIENE MENOS KM ES: \n" + camino.toString());
        } else {
            System.out.println("NO EXISTE UN CAMINO ENTRE AMBAS CIUDADES");
        }
    }

    public static void caminoPorCiudadIntermedia() {
        System.out.println("Ingrese el codigo de la ciudad A inicial");
        int codigoA = sc.nextInt();
        System.out.println("Ingrese el codigo de la ciudad C intermedia");
        int codigoC = sc.nextInt();

        System.out.println("Ingrese el codigo de la ciudad B final");
        int codigoB = sc.nextInt();

        Lista camino = mapaRutas.listarCaminosConCiudad(codigoA, codigoC, codigoB);
        if (!camino.esVacia()) {
            for (int i = 1; i <= camino.longitud(); i++) { // para imprimir cada camino posible
                Object aux = camino.recuperar(i);
                System.out.println("POSIBLE CAMINO N°: "+i+"  " + aux.toString());
            }
        } else {
            System.out.println("NO EXISTEN CAMINOS");
        }
    }

    public static void caminoXkilometrosMax() {
        System.out.println("Ingrese el codigo de la ciudad A inicial");
        int codigoA = sc.nextInt();
        System.out.println("Ingrese el codigo de la ciudad B final");
        int codigoB = sc.nextInt();
        System.out.println("Ingrese la cantidad maxima de kilometros que desea recorrer:");
        int cantKm = sc.nextInt();

        double kmCamino = mapaRutas.caminoMasRapidoenKM(codigoA, codigoB, cantKm);
        if (kmCamino < cantKm) {
            System.out.println("ES POSIBLE IR DE " + codigoA + " A " + codigoB + " RECORRIENDO: " + kmCamino + " KM");
        } else {
            System.out.println("NO EXISTE UN CAMINO CON MENOS KM QUE EL INGRESADO ");
        }
    }

    public static void verificarViaje() {
        int respuesta;
        do {
            System.out.println("--- VERIFICAR VIAJES ---");
            System.out.println("---------------------------------------------------------------------");
            System.out.println(" (1) Dada una ciudad A y una ciudad B mostrar todos los pedidos y calcular cuanto espacio total hace falta en el camion");
            System.out.println(" (2) Verificar si sobra espacio para solicitudes intermedias.");
            System.out.println(" (3) Verificar un camino perfecto usando una lista.");
            System.out.println(" (4) Volver al Menu");
            System.out.println("---------------------------------------------------------------------");
            respuesta = sc.nextInt();
            switch (respuesta) {
                case 1:
                    pedidosYCalcularEspacio();
                    break;
                case 2:
                    espacioSobrante();
                    break;
                case 3:
                    caminoPerfecto();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("RESPUESTA INVALIDA.");
                    break;
            }
        } while (respuesta != 4);
    }
    
    public static void pedidosYCalcularEspacio() {
        System.out.println("Ingrese el codigo de la ciudad A inicial");
        int codigoA = sc.nextInt();
        System.out.println("Ingrese el codigo de la ciudad B final");
        int codigoB = sc.nextInt();
        Lista listaSolicitudes = solicitudes.obtenerValores(codigoA + "" + codigoB);
        if (mapaRutas.existeVertice(codigoA) && mapaRutas.existeVertice(codigoB)) {
            if (!listaSolicitudes.esVacia()) {
                int espacio = 0;
                System.out.println("Las solicitudes son:\n " + listaSolicitudes.toString());
                for (int i = 1; i <= listaSolicitudes.longitud(); i++) {
                    Solicitud solicitud = (Solicitud) listaSolicitudes.recuperar(i);
                    espacio = espacio + solicitud.getCantMetrosCubicos();
                }
                System.out.println("El espacio necesario de: " + codigoA + " a " + codigoB +
                        " en el camion es de: "
                        + espacio + " m3");
            } else {
                System.out.println("NO HAY PEDIDOS ENTRE LAS CIUDADES");
            }
        } else {
            System.out.println("LOS CODIGOS DE CIUDADES SON INCORRECTOS");
        }
    }

    public static void espacioSobrante() {
        System.out.println("Ingrese el codigo de la ciudad A inicial");
        int codigoA = sc.nextInt();
        System.out.println("Ingrese el codigo de la ciudad B final");
        int codigoB = sc.nextInt();
        System.out.println("Ingrese los metros cubicos del camion ");
        int mtsNecesarios = sc.nextInt();

        int espacio = obtenerEspacio(codigoA, codigoB); //obtengo el espacio que ocupan los pedidos entre esas dos ciudades
        if (espacio < mtsNecesarios) {
            System.out.println("Hay suficiente espacio para otras solicitudes");
            Lista camino = mapaRutas.caminoMasCorto(codigoA, codigoB);
            for (int i = 1; i < camino.longitud() - 1; i++) { // para todas las ciudades del camino mas corto entre A y B
                Lista listaSolicitudes = solicitudes.obtenerValores(codigoA + "" + camino.recuperar(i)); //crea una lista entre solicitudes de A y la siguiente
                if (!listaSolicitudes.esVacia()) {
                    System.out.println("Posibles pedidos a despachar son:");
                    for (int j = 1; j <= listaSolicitudes.longitud(); j++) { //para esa lista
                        Solicitud solicitud = (Solicitud) listaSolicitudes.recuperar(j);
                        System.out.println(solicitud.toString());
                    }
                }

            }
        } else { //si no hay espacio
            System.out.println("NO HAY SUFICIENTE ESPACIO PARA MAS PEDIDOS");
        }
    }

    private static int obtenerEspacio(int codigoA, int codigoB) {
        Lista listaSolicitudes = solicitudes.obtenerValores(codigoA + "" + codigoB);
        int espacio = 0;
        if (mapaRutas.existeVertice(codigoA) && mapaRutas.existeVertice(codigoB)) {
            if (!listaSolicitudes.esVacia()) {
                for (int i = 1; i <= listaSolicitudes.longitud(); i++) { //acumula el espacio de cada solicitud de una lista de solicitudes entre A y B
                    Solicitud solicitud = (Solicitud) listaSolicitudes.recuperar(i);
                    espacio = espacio + solicitud.getCantMetrosCubicos();
                }
            }
        }
        return espacio;
    }

    public static void caminoPerfecto() {
        int capacidad, codigo, longitud, i = 1;
        boolean exito = true;
        Lista listaCodigo = new Lista();
        System.out.println(
                "Dada una lista de Ciudades y una cantidad de metros cubicos que corresponden a capacidad del camion, verificar si es un “camino perfecto”");
        System.out.println(
                "---------------------------------------------------------------------");
        System.out.println("Ingrese la cantidad de metros cubicos de capacidad del camion");
        capacidad = sc.nextInt();
        System.out.println("Ingrese una cantidad de Ciudades");
        longitud = sc.nextInt();
        if (longitud > 1) {
            while (i <= longitud && exito) {
                System.out.println("Ingrese el codigo postal de la Ciudad a insertar en la Lista");
                codigo = sc.nextInt();
                if (mapaRutas.existeVertice(codigo)) {
                    listaCodigo.insertar(codigo, i);
                } else {
                    exito = false;
                }
                i++;
            }
            if (exito) {
                verificarCaminoPerfecto(listaCodigo, capacidad);
            } else {
                System.out.println("Una de las Ciudades ingresadas no existe en el sistema. ERROR");
            }
        } else {
            System.out.println("Ingrese un numero valido de Ciudades");
        }
    }

    public static void verificarCaminoPerfecto(Lista lista, int capacidad) {
        int i = 1, ocupacion = 0;
        boolean exito = true;
        while (i < lista.longitud() && exito) {
            int origen = (int) lista.recuperar(i), destino = (int) lista.recuperar(i +
                    1);
            if (mapaRutas.existeArco(origen, destino)) {
                Lista listaPedidos = solicitudes.obtenerValores(origen + "" + destino);
                if (listaPedidos.esVacia()) {
                    System.out.println(
                            "No es camino perfecto ya que no existen Pedidos entre " + origen + " y " +
                                    destino);
                    exito = false;
                } else {
                    Solicitud solicitud = (Solicitud) listaPedidos.recuperar(1);
                    ocupacion = ocupacion + solicitud.getCantMetrosCubicos();
                }
            } else {
                exito = false;
                System.out.println(
                        "No es camino perfecto ya que no existe una ruta entre la Ciudad " + origen +
                                " y " + destino);
            }
            i++;
        }
        if (exito) {
            if (capacidad >= ocupacion) {
                System.out.println("Es camino perfecto");
            } else {
                System.out.println("No es camino perfecto ya que la capacidad del camion no soporta todos los pedidos");
            }
        }
    }
     

    public static void mostrarSistema() {
        int respuesta;
        
        do {
            System.out.println("--- MOSTRAR SISTEMA ---");
            System.out.println("(1) Mostrar las ciudades.\n(2) Mostrar las rutas.\n(3) Mostrar los pedidos" +
                    "\n(4) Mostrar los clientes.\n(5) Volver al menu.");
            respuesta = sc.nextInt();
            switch (respuesta) {
                case 1:
                    System.out.println("------------SISTEMA DE CIUDADES------------");
                    System.out.println(ciudades.toString());
                    break;
                case 2:
                    System.out.println("------------SISTEMA DE RUTAS------------");
                    System.out.println(mapaRutas.toString());
                    break;
                case 3:
                    System.out.println("------------SISTEMA DE PEDIDOS------------");
                    System.out.println(solicitudes.toString());
                    break;
                case 4:
                    System.out.println("------------SISTEMA DE CLIENTES------------");
                    System.out.println(clientes.toString());
                    break;
                case 5:
                    break;
                default:
                    System.out.println("RESPUESTA INVALIDA.");
                    break;
            }
        } while (respuesta != 5);

    }

}
