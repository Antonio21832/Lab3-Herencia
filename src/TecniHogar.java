
import java.util.ArrayList;
//Estuardo Antonio Molina 26878
//Clase que administra las ordenes, cuadrillas, jornadas e ingresos de TecniHogar
public class TecniHogar {
    //Colecciones que almacenan la informacion de la empresa
    private ArrayList<OrdenServicio> ordenes;
    private ArrayList<Cuadrilla> cuadrillas;
    private ArrayList<Jornada> jornadas;
    //Atributos que guardan los ingresos acumulados
    private double ingresosRegistro;
    private double ingresosVisitas;
    //Guarda los ingresos acumulados de cada categoria
    private ArrayList<String> categoriasIngresos;
    private ArrayList<Double> montosCategorias;
    //Constructor que inicializa los datos de la empresa
    public TecniHogar() {
        this.ordenes = new ArrayList<OrdenServicio>();
        this.cuadrillas = new ArrayList<Cuadrilla>();
        this.jornadas = new ArrayList<Jornada>();
        this.ingresosRegistro = 0;
        this.ingresosVisitas = 0;
        this.categoriasIngresos = new ArrayList<String>();
        this.montosCategorias = new ArrayList<Double>();
    }
    //Metodo que registra una orden confirmada y cobra su costo
    public boolean registrarOrden(OrdenServicio orden) {
        //Verifica que la orden exista y su codigo no este repetido
        if (orden == null || buscarOrden(orden.getCodigo()) != null || !orden.getEstado().equals("Pendiente")) {
            return false;
        }
        //Calcula el costo de la orden
        double costo = orden.calcularCosto();
        //Registra la orden y los ingresos
        ordenes.add(orden);
        ingresosRegistro = ingresosRegistro + costo;
        //Registra el ingreso en la categoria correspondiente
        agregarIngresoCategoria(orden.getCategoria(), costo);
        return true;
    }

    //Metodo que agrega una orden inicial sin generar ingresos
    public boolean agregarOrdenInicial(OrdenServicio orden) {
        //Verifica que la orden exista y su codigo no este repetido
        if (orden == null || buscarOrden(orden.getCodigo()) != null) {
            return false;
        }
        //Agrega la orden sin cobrar
        ordenes.add(orden);
        return true;
    }
    //Metodo que busca una orden por su codigo
    public OrdenServicio buscarOrden(String codigo) {
        int i = 0;
        while (i < ordenes.size()) {
            if (ordenes.get(i).getCodigo().equals(codigo)) {
                return ordenes.get(i);
            }
            i = i + 1;
        }
        return null;
    }
    //Metodo que devuelve las ordenes registradas
    public ArrayList<OrdenServicio> getOrdenes() {
        return new ArrayList<OrdenServicio>(ordenes);
    }
    //Metodo que registra una cuadrilla
    public boolean registrarCuadrilla(Cuadrilla cuadrilla) {
        //Verifica que exista y no tenga codigo repetido
        if (cuadrilla == null || buscarCuadrilla(cuadrilla.getCodigo()) != null) {
            return false;
        }
        cuadrillas.add(cuadrilla);
        return true;
    }
    //Metodo que busca una cuadrilla por su codigo
    public Cuadrilla buscarCuadrilla(String codigo) {
        int i = 0;
        while (i < cuadrillas.size()) {
            if (cuadrillas.get(i).getCodigo().equals(codigo)) {
                return cuadrillas.get(i);
            }
            i = i + 1;
        }
        return null;
    }
    //Metodo que devuelve las cuadrillas registradas
    public ArrayList<Cuadrilla> getCuadrillas() {
        return new ArrayList<Cuadrilla>(cuadrillas);
    }
    //Metodo que crea una nueva jornada
    public boolean crearJornada(String codigo, String codigoCuadrilla) {
        //Verifica que el codigo no este vacio ni repetido
        if (codigo == null || codigo.trim().length() == 0 || buscarJornada(codigo) != null) {
            return false;
        }
        //Busca la cuadrilla indicada
        Cuadrilla cuadrilla = buscarCuadrilla(codigoCuadrilla);
        if (cuadrilla == null) {
            return false;
        }
        //Verifica que la cuadrilla no tenga otra jornada sin cerrar
        int i = 0;
        while (i < jornadas.size()) {
            Jornada actual = jornadas.get(i);
            if (actual.getCuadrilla() == cuadrilla && !actual.getEstado().equals("Cerrada")) {
                return false;
            }
            i = i + 1;
        }
        //Crea y registra la nueva jornada
        Jornada nueva = new Jornada(codigo, cuadrilla);
        jornadas.add(nueva);
        return true;
    }
    //Metodo que busca una jornada por su codigo
    public Jornada buscarJornada(String codigo) {
        int i = 0;
        while (i < jornadas.size()) {
            if (jornadas.get(i).getCodigo().equals(codigo)) {
                return jornadas.get(i);
            }
            i = i + 1;
        }
        return null;
    }
    //Metodo que devuelve las jornadas registradas
    public ArrayList<Jornada> getJornadas() {
        return new ArrayList<Jornada>(jornadas);
    }
    //Metodo que asigna una orden a una jornada
    public boolean asignarOrden(String codigoJornada, String codigoOrden) {
        //Busca la jornada y la orden
        Jornada jornada = buscarJornada(codigoJornada);
        OrdenServicio orden = buscarOrden(codigoOrden);
        //Verifica que ambas existan
        if (jornada == null || orden == null) {
            return false;
        }
        //Verifica que la orden no este asignada a otra jornada sin cerrar
        int i = 0;
        while (i < jornadas.size()) {
            Jornada actual = jornadas.get(i);
            if (!actual.getEstado().equals("Cerrada")) {
                ArrayList<OrdenServicio> asignadas = actual.getOrdenes();
                int j = 0;
                while (j < asignadas.size()) {
                    if (asignadas.get(j) == orden) {
                        return false;
                    }
                    j = j + 1;
                }
            }
            i = i + 1;
        }
        //Solicita a la jornada realizar la asignacion
        return jornada.asignarOrden(orden);
    }
    //Metodo que retira una orden de una jornada
    public boolean retirarOrden(String codigoJornada, String codigoOrden) {
        Jornada jornada = buscarJornada(codigoJornada);
        OrdenServicio orden = buscarOrden(codigoOrden);
        if (jornada == null || orden == null) {
            return false;
        }
        return jornada.retirarOrden(orden);
    }

    //Metodo que inicia una jornada y registra los cargos adicionales
    public boolean iniciarJornada(String codigoJornada) {
        //Busca la jornada
        Jornada jornada = buscarJornada(codigoJornada);
        if (jornada == null) {
            return false;
        }
        //Intenta iniciar la jornada
        if (!jornada.iniciar()) {
            return false;
        }
        //Registra los cargos por visitas adicionales
        ArrayList<OrdenServicio> asignadas = jornada.getOrdenes();
        int i = 0;
        while (i < asignadas.size()) {
            OrdenServicio orden = asignadas.get(i);
            //Cada visita posterior a la primera genera Q40
            if (orden.getVisitasRealizadas() > 1) {
                ingresosVisitas = ingresosVisitas + 40;
                agregarIngresoCategoria(orden.getCategoria(), 40);
            }
            i = i + 1;
        }
        return true;
    }

    //Metodo que registra el resultado de una visita
    public boolean registrarResultado(String codigoJornada, String codigoOrden, boolean completada) {
        Jornada jornada = buscarJornada(codigoJornada);
        if (jornada == null) {
            return false;
        }
        return jornada.registrarResultado(codigoOrden, completada);
    }
    //Metodo que devuelve los ingresos por registro
    public double getIngresosRegistro() {
        return ingresosRegistro;
    }
    //Metodo que devuelve los ingresos por visitas adicionales
    public double getIngresosVisitas() {
        return ingresosVisitas;
    }
    //Metodo que calcula los ingresos totales
    public double calcularIngresosTotales() {
        return ingresosRegistro + ingresosVisitas;
    }

    //Metodo que registra un ingreso en su categoria correspondiente
        private void agregarIngresoCategoria(String categoria, double monto) {
            //Busca si la categoria ya tiene ingresos registrados
            int posicion = -1;
            int i = 0;
            while (i < categoriasIngresos.size()) {
                if (categoriasIngresos.get(i).equals(categoria)) {
                    posicion = i;
                }
                i = i + 1;
            }
            //Si la categoria no existe la agrega
            if (posicion == -1) {
                categoriasIngresos.add(categoria);
                montosCategorias.add(monto);
            }
            //Si ya existe suma el nuevo ingreso
            else {
                montosCategorias.set(posicion, montosCategorias.get(posicion) + monto);
            }
        }


    //Metodo que genera el reporte general de la empresa
    public String generarReporte() {
        String reporte = "\nREPORTE GENERAL DE TECNIHOGAR";
        //Muestra los ingresos acumulados
        reporte = reporte + "\nIngresos por registro: Q" + String.format("%.2f", ingresosRegistro);
        reporte = reporte + "\nIngresos por visitas adicionales: Q" + String.format("%.2f", ingresosVisitas);
        reporte = reporte + "\nIngresos totales: Q" + String.format("%.2f", calcularIngresosTotales());
        //Muestra las ordenes por categoria y estado
        reporte = reporte + "\n\nORDENES POR CATEGORIA Y ESTADO";
        ArrayList<String> categorias = new ArrayList<String>();
        ArrayList<String> estados = new ArrayList<String>();
        ArrayList<Integer> cantidades = new ArrayList<Integer>();
        int i = 0;
        while (i < ordenes.size()) {
            OrdenServicio orden = ordenes.get(i);
            String categoria = orden.getCategoria();
            String estado = orden.getEstado();
            int posicion = -1;
            int j = 0;
            while (j < categorias.size()) {
                if (categorias.get(j).equals(categoria) && estados.get(j).equals(estado)) {
                    posicion = j;
                }
                j = j + 1;
            }
            if (posicion == -1) {
                categorias.add(categoria);
                estados.add(estado);
                cantidades.add(1);
            }
            else {
                cantidades.set(posicion, cantidades.get(posicion) + 1);
            }
            i = i + 1;
        }
        //Muestra los resultados encontrados
        i = 0;
        while (i < categorias.size()) {
            reporte = reporte + "\n" + categorias.get(i) + " | " + estados.get(i) + ": " + cantidades.get(i);
            i = i + 1;
        }
        //Muestra los ingresos por categoria
        reporte = reporte + "\n\nINGRESOS POR CATEGORIA";
        i = 0;
        while (i < categoriasIngresos.size()) {
            reporte = reporte + "\n" + categoriasIngresos.get(i) + ": Q" + String.format("%.2f", montosCategorias.get(i));
            i = i + 1;
        }
        if (categoriasIngresos.size() == 0) {
            reporte = reporte + "\nNo hay ingresos registrados.";
        }
        //Muestra las jornadas sin cerrar
        reporte = reporte + "\n\nJORNADAS SIN CERRAR";
        boolean hayJornadas = false;
        i = 0;
        while (i < jornadas.size()) {
            Jornada jornada = jornadas.get(i);
            if (!jornada.getEstado().equals("Cerrada")) {
                reporte = reporte + "\n" + jornada.toString();
                hayJornadas = true;
            }
            i = i + 1;
        }
        if (hayJornadas == false) {
            reporte = reporte + "\nNo hay jornadas sin cerrar.";
        }
        return reporte;
    }
}
