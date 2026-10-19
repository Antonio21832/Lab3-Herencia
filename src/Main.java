
//Estuardo Antonio Molina 26878
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
//Clase principal que permite utilizar el sistema TecniHogar
public class Main {
    //Scanner que permite leer los datos ingresados por el usuario
    private static Scanner teclado = new Scanner(System.in);
    //Objeto que administra las ordenes, cuadrillas y jornadas
    private static TecniHogar empresa = new TecniHogar();
    //Metodo principal donde comienza la ejecucion
    public static void main(String[] args) {
        //Carga los datos iniciales del sistema
        cargarDatosIniciales();
        //Variable que guarda la opcion seleccionada
        int opcion = 0;
        //Mantiene el menu activo hasta seleccionar salir
        while (opcion != 12) {
            //Muestra las opciones disponibles
            mostrarMenu();
            try {
                System.out.print("Seleccione una opcion: ");
                opcion = teclado.nextInt();
                teclado.nextLine();
                if (opcion == 1) {
                    cotizarOrden();
                }
                else if (opcion == 2) {
                    registrarOrden();
                }
                else if (opcion == 3) {
                    registrarCuadrilla();
                }
                else if (opcion == 4) {
                    crearJornada();
                }
                else if (opcion == 5) {
                    asignarOrden();
                }
                else if (opcion == 6) {
                    retirarOrden();
                }
                else if (opcion == 7) {
                    iniciarJornada();
                }
                else if (opcion == 8) {
                    registrarResultado();
                }
                else if (opcion == 9) {
                    consultarOrdenes();
                }
                else if (opcion == 10) {
                    consultarCuadrillas();
                }
                else if (opcion == 11) {
                    mostrarReporte();
                }
                else if (opcion == 12) {
                    System.out.println("Programa finalizado.");
                }
                else {
                    System.out.println("Opcion no valida.");
                }
            }
            //Controla entradas numericas incorrectas
            catch (InputMismatchException e) {
                System.out.println("Debe ingresar un valor numerico correcto.");
                teclado.nextLine();
            }
            //Controla los datos que no cumplen las validaciones
            catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    //Metodo que muestra el menu principal
    public static void mostrarMenu() {
        System.out.println("\nTECNIHOGAR");
        System.out.println("1. Cotizar orden");
        System.out.println("2. Registrar orden");
        System.out.println("3. Registrar cuadrilla");
        System.out.println("4. Crear jornada");
        System.out.println("5. Asignar orden");
        System.out.println("6. Retirar orden");
        System.out.println("7. Iniciar jornada");
        System.out.println("8. Registrar resultado de visita");
        System.out.println("9. Consultar ordenes");
        System.out.println("10. Consultar cuadrillas");
        System.out.println("11. Mostrar reporte");
        System.out.println("12. Salir");
    }
    //Metodo que crea las ordenes y cuadrillas iniciales
    public static void cargarDatosIniciales() {
        //Crea dos diagnosticos
        empresa.agregarOrdenInicial(new Diagnostico("D001", "Carlos Perez", "Zona 1", 1, "Metropolitana"));
        empresa.agregarOrdenInicial(new Diagnostico("D002", "Maria Lopez", "Mixco", 1.5, "Periferica"));
        //Crea dos instalaciones normales
        empresa.agregarOrdenInicial(new Instalacion("I001", "Pedro Garcia", "Zona 10", 2.5, "Metropolitana", 5));
        empresa.agregarOrdenInicial(new Instalacion("I002", "Ana Morales", "Villa Nueva", 3, "Periferica", 3));
        //Crea dos instalaciones electricas certificadas
        empresa.agregarOrdenInicial(new InstalacionElectrica("E001", "Luis Ramirez", "Zona 12", 2.5, "Metropolitana", 5, 2000));
        empresa.agregarOrdenInicial(new InstalacionElectrica("E002", "Sofia Hernandez", "Amatitlan", 4, "Periferica", 4, 1500));
        //Crea dos mantenimientos de aire acondicionado
        empresa.agregarOrdenInicial(new MantenimientoAire("A001", "Jorge Castillo", "Zona 14", 2, "Metropolitana", 12000));
        empresa.agregarOrdenInicial(new MantenimientoAire("A002", "Elena Torres", "Zona 15", 3, "Metropolitana", 24000));
        //Crea tres cuadrillas con diferentes capacidades
        empresa.registrarCuadrilla(new Cuadrilla("C001", 8, true));
        empresa.registrarCuadrilla(new Cuadrilla("C002", 6, false));
        empresa.registrarCuadrilla(new Cuadrilla("C003", 10, true));
    }
    //Metodo que solicita los datos para crear una orden temporal
    private static OrdenServicio crearOrden() {
        System.out.println("\nTIPO DE SERVICIO");
        System.out.println("1. Diagnostico");
        System.out.println("2. Instalacion");
        System.out.println("3. Instalacion electrica certificada");
        System.out.println("4. Mantenimiento de aire acondicionado");
        System.out.print("Seleccione el tipo: ");
        int tipo = teclado.nextInt();
        teclado.nextLine();
        if (tipo < 1 || tipo > 4) {
            System.out.println("Tipo de servicio no valido.");
            return null;
        }
        System.out.print("Ingrese el codigo: ");
        String codigo = teclado.nextLine();
        System.out.print("Ingrese el cliente: ");
        String cliente = teclado.nextLine();
        System.out.print("Ingrese la direccion: ");
        String direccion = teclado.nextLine();
        System.out.print("Ingrese las horas estimadas: ");
        double horas = teclado.nextDouble();
        teclado.nextLine();
        System.out.println("1. Metropolitana");
        System.out.println("2. Periferica");
        System.out.print("Seleccione la zona: ");
        int opcionZona = teclado.nextInt();
        teclado.nextLine();
        if (opcionZona != 1 && opcionZona != 2) {
            System.out.println("Zona no valida.");
            return null;
        }
        String zona = "Metropolitana";
        if (opcionZona == 2) {
            zona = "Periferica";
        }
        //Crea la categoria seleccionada
        if (tipo == 1) {
            return new Diagnostico(codigo, cliente, direccion, horas, zona);
        }
        else if (tipo == 2) {
            System.out.print("Ingrese la cantidad de puntos: ");
            int puntos = teclado.nextInt();
            teclado.nextLine();
            return new Instalacion(codigo, cliente, direccion, horas, zona, puntos);
        }
        else if (tipo == 3) {
            System.out.print("Ingrese la cantidad de puntos: ");
            int puntos = teclado.nextInt();
            System.out.print("Ingrese el valor de materiales: Q");
            double materiales = teclado.nextDouble();
            teclado.nextLine();
            return new InstalacionElectrica(codigo, cliente, direccion, horas, zona, puntos, materiales);
        }
        else {
            System.out.print("Ingrese la capacidad en BTU: ");
            int btu = teclado.nextInt();
            teclado.nextLine();
            return new MantenimientoAire(codigo, cliente, direccion, horas, zona, btu);
        }
    }
    //Metodo que realiza una cotizacion sin registrar ingresos
    public static void cotizarOrden() {
        OrdenServicio orden = crearOrden();
        if (orden != null) {
            System.out.println("\nCOTIZACION");
            mostrarOrden(orden);
            System.out.println("La cotizacion no fue registrada.");
        }
    }
    //Metodo que registra una orden confirmada
    public static void registrarOrden() {
        OrdenServicio orden = crearOrden();
        if (orden == null) {
            return;
        }
        //Muestra el costo antes de confirmar
        mostrarOrden(orden);
        System.out.println("1. Confirmar registro");
        System.out.println("2. Cancelar");
        System.out.print("Seleccione una opcion: ");
        int confirmacion = teclado.nextInt();
        teclado.nextLine();
        if (confirmacion == 1) {
            boolean registrada = empresa.registrarOrden(orden);
            if (registrada == true) {
                System.out.println("Orden registrada correctamente.");
            }
            else {
                System.out.println("No se pudo registrar la orden. Verifique el codigo.");
            }
        }
        else {
            System.out.println("Registro cancelado. No se realizaron cambios.");
        }
    }
    //Metodo que registra una nueva cuadrilla
    public static void registrarCuadrilla() {
        System.out.print("Ingrese el codigo de la cuadrilla: ");
        String codigo = teclado.nextLine();
        System.out.print("Ingrese la capacidad maxima de horas: ");
        double capacidad = teclado.nextDouble();
        teclado.nextLine();
        System.out.println("1. Certificada en refrigeracion");
        System.out.println("2. No certificada");
        System.out.print("Seleccione una opcion: ");
        int opcion = teclado.nextInt();
        teclado.nextLine();
        if (opcion != 1 && opcion != 2) {
            System.out.println("Opcion no valida.");
            return;
        }
        boolean certificada = false;
        if (opcion == 1) {
            certificada = true;
        }
        Cuadrilla cuadrilla = new Cuadrilla(codigo, capacidad, certificada);
        if (empresa.registrarCuadrilla(cuadrilla)) {
            System.out.println("Cuadrilla registrada correctamente.");
        }
        else {
            System.out.println("Ya existe una cuadrilla con ese codigo.");
        }
    }
    //Metodo que crea una jornada para una cuadrilla
    public static void crearJornada() {
        System.out.print("Ingrese el codigo de la jornada: ");
        String codigo = teclado.nextLine();
        System.out.print("Ingrese el codigo de la cuadrilla: ");
        String codigoCuadrilla = teclado.nextLine();
        if (empresa.crearJornada(codigo, codigoCuadrilla)) {
            System.out.println("Jornada creada correctamente.");
        }
        else {
            System.out.println("No se pudo crear la jornada. Verifique el codigo y la disponibilidad de la cuadrilla.");
        }
    }
    //Metodo que asigna una orden a una jornada
    public static void asignarOrden() {
        System.out.print("Ingrese el codigo de la jornada: ");
        String codigoJornada = teclado.nextLine();
        System.out.print("Ingrese el codigo de la orden: ");
        String codigoOrden = teclado.nextLine();
        if (empresa.asignarOrden(codigoJornada, codigoOrden)) {
            System.out.println("Orden asignada correctamente.");
        }
        else {
            System.out.println("No se pudo asignar la orden. Verifique el estado, capacidad y compatibilidad.");
        }
    }
    //Metodo que retira una orden de una jornada
    public static void retirarOrden() {
        System.out.print("Ingrese el codigo de la jornada: ");
        String codigoJornada = teclado.nextLine();
        System.out.print("Ingrese el codigo de la orden: ");
        String codigoOrden = teclado.nextLine();
        if (empresa.retirarOrden(codigoJornada, codigoOrden)) {
            System.out.println("Orden retirada correctamente.");
        }
        else {
            System.out.println("No se pudo retirar la orden.");
        }
    }
    //Metodo que inicia una jornada
    public static void iniciarJornada() {
        System.out.print("Ingrese el codigo de la jornada: ");
        String codigo = teclado.nextLine();
        if (empresa.iniciarJornada(codigo)) {
            System.out.println("Jornada iniciada correctamente.");
        }
        else {
            System.out.println("No se pudo iniciar la jornada.");
        }
    }
    //Metodo que registra el resultado de una visita
    public static void registrarResultado() {
        System.out.print("Ingrese el codigo de la jornada: ");
        String codigoJornada = teclado.nextLine();
        System.out.print("Ingrese el codigo de la orden: ");
        String codigoOrden = teclado.nextLine();
        System.out.println("1. Completada");
        System.out.println("2. Visita fallida");
        System.out.print("Seleccione el resultado: ");
        int opcion = teclado.nextInt();
        teclado.nextLine();
        if (opcion != 1 && opcion != 2) {
            System.out.println("Resultado no valido.");
            return;
        }
        boolean completada = false;
        if (opcion == 1) {
            completada = true;
        }
        if (empresa.registrarResultado(codigoJornada, codigoOrden, completada)) {
            System.out.println("Resultado registrado correctamente.");
        }
        else {
            System.out.println("No se pudo registrar el resultado.");
        }
    }
    //Metodo que muestra las ordenes registradas
    public static void consultarOrdenes() {
        ArrayList<OrdenServicio> ordenes = empresa.getOrdenes();
        if (ordenes.size() == 0) {
            System.out.println("No hay ordenes registradas.");
            return;
        }
        int i = 0;
        while (i < ordenes.size()) {
            mostrarOrden(ordenes.get(i));
            i = i + 1;
        }
    }
    //Metodo que muestra las cuadrillas registradas
    public static void consultarCuadrillas() {
        ArrayList<Cuadrilla> cuadrillas = empresa.getCuadrillas();
        if (cuadrillas.size() == 0) {
            System.out.println("No hay cuadrillas registradas.");
            return;
        }
        int i = 0;
        while (i < cuadrillas.size()) {
            mostrarCuadrilla(cuadrillas.get(i));
            i = i + 1;
        }
    }
    //Metodo que muestra el reporte general
    public static void mostrarReporte() {
        System.out.println(empresa.generarReporte());
    }
    //Metodo que muestra la informacion de una orden
    public static void mostrarOrden(OrdenServicio orden) {
        System.out.println("\n" + orden.toString());
        System.out.printf("Costo: Q%.2f%n", orden.calcularCosto());
        System.out.println("Tiempo estimado: " + orden.calcularTiempoAtencion() + " dias habiles");
        System.out.println("Maximo de visitas: " + orden.maximoVisitas());
    }
    //Metodo que muestra la informacion de una cuadrilla
    public static void mostrarCuadrilla(Cuadrilla cuadrilla) {
        System.out.println("\n" + cuadrilla.toString());
    }
}
