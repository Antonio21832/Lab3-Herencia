
//Clase padre que representa una orden de servicio de TecniHogar
public abstract class OrdenServicio {
    //Atributos comunes de todas las ordenes
    private String codigo;
    private String cliente;
    private String direccion;
    private double horasEstimadas;
    private String zona;
    private String estado;
    private int visitasRealizadas;
    //Constructor que inicializa los datos comunes de la orden
    protected OrdenServicio(String codigo, String cliente, String direccion, double horasEstimadas, String zona) {
        //Valida que el codigo no este vacio
        if (codigo == null || codigo.trim().length() == 0) {
            throw new IllegalArgumentException("El codigo no puede estar vacio.");
        }
        //Valida que el nombre del cliente no este vacio
        if (cliente == null || cliente.trim().length() == 0) {
            throw new IllegalArgumentException("El cliente no puede estar vacio.");
        }
        //Valida que la direccion no este vacia
        if (direccion == null || direccion.trim().length() == 0) {
            throw new IllegalArgumentException("La direccion no puede estar vacia.");
        }
        //Valida que las horas estimadas sean positivas
        if (horasEstimadas <= 0 || Double.isNaN(horasEstimadas) || Double.isInfinite(horasEstimadas)) {
            throw new IllegalArgumentException("Las horas estimadas deben ser mayores que cero.");
        }
        //Valida que la zona sea metropolitana o periferica
        if (zona == null || (!zona.equalsIgnoreCase("Metropolitana") && !zona.equalsIgnoreCase("Periferica"))) {
            throw new IllegalArgumentException("La zona debe ser Metropolitana o Periferica.");
        }
        //Asigna los valores iniciales
        this.codigo = codigo;
        this.cliente = cliente;
        this.direccion = direccion;
        this.horasEstimadas = horasEstimadas;
        this.zona = zona;
        this.estado = "Pendiente";
        this.visitasRealizadas = 0;
    }
    //Devuelve el codigo de la orden
    public String getCodigo() {
        return codigo;
    }
    //Devuelve el nombre del cliente
    public String getCliente() {
        return cliente;
    }
    //Devuelve la direccion del servicio
    public String getDireccion() {
        return direccion;
    }
    //Devuelve las horas estimadas
    public double getHorasEstimadas() {
        return horasEstimadas;
    }
    //Devuelve la zona del servicio
    public String getZona() {
        return zona;
    }
    //Devuelve el estado actual de la orden
    public String getEstado() {
        return estado;
    }
    //Devuelve la cantidad de visitas realizadas
    public int getVisitasRealizadas() {
        return visitasRealizadas;
    }
    //Metodo abstracto que calcula el costo segun la categoria
    public abstract double calcularCosto();
    //Metodo abstracto que calcula los dias de atencion
    public abstract int calcularTiempoAtencion();
    //Metodo abstracto que devuelve el maximo de visitas
    public abstract int maximoVisitas();
    //Metodo abstracto que identifica la categoria
    public abstract String getCategoria();
    //Metodo que verifica si una cuadrilla puede atender la orden
    public boolean esCompatible(Cuadrilla cuadrilla) {
        return cuadrilla != null;
    }
    //Metodo que asigna una orden pendiente a una jornada
    public boolean asignar() {
        //Verifica que la orden este pendiente
        if (!estado.equals("Pendiente")) {
            return false;
        }
        //Cambia el estado de la orden
        estado = "Asignada";
        return true;
    }
    //Metodo que devuelve una orden asignada al estado pendiente
    public boolean retirar() {
        //Verifica que la orden este asignada
        if (!estado.equals("Asignada")) {
            return false;
        }
        //Devuelve la orden al estado pendiente
        estado = "Pendiente";
        return true;
    }
    //Metodo que inicia la atencion de una orden
    public boolean iniciarAtencion() {
        //Verifica que la orden este asignada y tenga visitas disponibles
        if (!estado.equals("Asignada") || visitasRealizadas >= maximoVisitas()) {
            return false;
        }
        //Cambia el estado y registra una visita
        estado = "En atencion";
        visitasRealizadas = visitasRealizadas + 1;
        return true;
    }
    //Metodo que registra el resultado de una visita
    public boolean registrarResultado(boolean completada) {
        //Verifica que la orden este en atencion
        if (!estado.equals("En atencion")) {
            return false;
        }
        //Verifica si la visita fue completada
        if (completada == true) {
            estado = "Completada";
        }
        //Verifica si todavia tiene visitas disponibles
        else if (visitasRealizadas < maximoVisitas()) {
            estado = "Pendiente";
        }
        //Cancela la orden si agoto sus visitas
        else {
            estado = "Cancelada";
        }
        return true;
    }
    //Metodo que muestra la informacion general de la orden
    @Override
    public String toString() {
        return "Codigo: " + codigo + "\nCliente: " + cliente + "\nDireccion: " + direccion + "\nHoras estimadas: " + horasEstimadas + "\nZona: " + zona + "\nCategoria: " + getCategoria() + "\nEstado: " + estado + "\nVisitas realizadas: " + visitasRealizadas;
    }
}
