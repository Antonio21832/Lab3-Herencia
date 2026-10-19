
import java.util.ArrayList;
//Estuardo Antonio Molina 26878
//Clase que representa una jornada de trabajo de TecniHogar
public class Jornada {
    //Atributos que guardan la informacion de la jornada
    private String codigo;
    private Cuadrilla cuadrilla;
    private ArrayList<OrdenServicio> ordenes;
    private String estado;
    private ArrayList<Boolean> resultadosRegistrados;
    //Constructor que inicializa los datos de la jornada
    public Jornada(String codigo, Cuadrilla cuadrilla) {
        //Valida que el codigo no este vacio
        if (codigo == null || codigo.trim().length() == 0) {
            throw new IllegalArgumentException("El codigo de la jornada no puede estar vacio.");
        }
        //Valida que exista una cuadrilla
        if (cuadrilla == null) {
            throw new IllegalArgumentException("La jornada debe tener una cuadrilla.");
        }
        //Asigna los datos iniciales
        this.codigo = codigo;
        this.cuadrilla = cuadrilla;
        this.ordenes = new ArrayList<OrdenServicio>();
        this.resultadosRegistrados = new ArrayList<Boolean>();
        this.estado = "Preparacion";
    }
    //Metodo que devuelve el codigo de la jornada
    public String getCodigo() {
        return codigo;
    }
    //Metodo que devuelve la cuadrilla responsable
    public Cuadrilla getCuadrilla() {
        return cuadrilla;
    }
    //Metodo que devuelve el estado actual de la jornada
    public String getEstado() {
        return estado;
    }
    //Metodo que devuelve las ordenes de la jornada
    public ArrayList<OrdenServicio> getOrdenes() {
        return new ArrayList<OrdenServicio>(ordenes);
    }
    //Metodo que calcula las horas estimadas asignadas
    public double calcularHorasAsignadas() {
        double total = 0;
        int i = 0;
        //Recorre todas las ordenes de la jornada
        while (i < ordenes.size()) {
            total = total + ordenes.get(i).getHorasEstimadas();
            i = i + 1;
        }
        return total;
    }
    //Metodo que devuelve las horas disponibles de la cuadrilla
    public double capacidadDisponible() {
        return cuadrilla.getCapacidadHoras() - calcularHorasAsignadas();
    }
    //Metodo que asigna una orden a la jornada
    public boolean asignarOrden(OrdenServicio orden) {
        //Verifica que la orden exista y la jornada este en preparacion
        if (orden == null || !estado.equals("Preparacion")) {
            return false;
        }
        //Verifica que la orden este pendiente
        if (!orden.getEstado().equals("Pendiente")) {
            return false;
        }
        //Verifica que la cuadrilla sea compatible con la orden
        if (!orden.esCompatible(cuadrilla)) {
            return false;
        }
        //Verifica que exista capacidad suficiente
        if (orden.getHorasEstimadas() > capacidadDisponible()) {
            return false;
        }
        //Cambia el estado de la orden a asignada
        if (!orden.asignar()) {
            return false;
        }
        //Agrega la orden y su resultado pendiente
        ordenes.add(orden);
        resultadosRegistrados.add(false);
        return true;
    }
    //Metodo que retira una orden de la jornada
    public boolean retirarOrden(OrdenServicio orden) {
        //Verifica que la jornada este en preparacion
        if (!estado.equals("Preparacion") || orden == null) {
            return false;
        }
        //Busca la orden dentro de la jornada
        int i = 0;
        while (i < ordenes.size()) {
            if (ordenes.get(i) == orden) {
                //Devuelve la orden al estado pendiente
                if (!orden.retirar()) {
                    return false;
                }
                //Elimina la orden y su resultado correspondiente
                ordenes.remove(i);
                resultadosRegistrados.remove(i);
                return true;
            }
            i = i + 1;
        }
        return false;
    }
    //Metodo que inicia la jornada
    public boolean iniciar() {
        //Verifica que este en preparacion y tenga ordenes
        if (!estado.equals("Preparacion") || ordenes.size() == 0) {
            return false;
        }
        //Verifica que todas las ordenes puedan iniciar
        int i = 0;
        while (i < ordenes.size()) {
            OrdenServicio orden = ordenes.get(i);
            if (!orden.getEstado().equals("Asignada") || orden.getVisitasRealizadas() >= orden.maximoVisitas()) {
                return false;
            }
            i = i + 1;
        }
        //Inicia la atencion de todas las ordenes
        i = 0;
        while (i < ordenes.size()) {
            ordenes.get(i).iniciarAtencion();
            i = i + 1;
        }
        //Cambia el estado de la jornada
        estado = "Iniciada";
        return true;
    }
    //Metodo que registra el resultado de una visita
    public boolean registrarResultado(String codigoOrden, boolean completada) {
        //Verifica que la jornada este iniciada
        if (!estado.equals("Iniciada")) {
            return false;
        }
        //Busca la orden por su codigo
        int i = 0;
        while (i < ordenes.size()) {
            OrdenServicio orden = ordenes.get(i);
            if (orden.getCodigo().equals(codigoOrden)) {
                //Verifica que no tenga resultado registrado
                if (resultadosRegistrados.get(i) == true) {
                    return false;
                }
                //Registra el resultado de la visita
                if (!orden.registrarResultado(completada)) {
                    return false;
                }
                //Marca que el resultado ya fue registrado
                resultadosRegistrados.set(i, true);
                //Verifica si todas las ordenes tienen resultado
                boolean todosRegistrados = true;
                int j = 0;
                while (j < resultadosRegistrados.size()) {
                    if (resultadosRegistrados.get(j) == false) {
                        todosRegistrados = false;
                    }
                    j = j + 1;
                }
                //Cierra la jornada cuando todos tienen resultado
                if (todosRegistrados == true) {
                    estado = "Cerrada";
                }
                return true;
            }
            i = i + 1;
        }
        return false;
    }
    //Metodo que muestra la informacion de la jornada
    @Override
    public String toString() {
        return "Codigo: " + codigo + "\nCuadrilla: " + cuadrilla.getCodigo() + "\nEstado: " + estado + "\nOrdenes asignadas: " + ordenes.size() + "\nHoras asignadas: " + calcularHorasAsignadas() + "\nCapacidad disponible: " + capacidadDisponible();
    }
}
