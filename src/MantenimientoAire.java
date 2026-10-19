
 //Estuardo Antonio Molina 26878
//Clase que representa un mantenimiento de aire acondicionado
public class MantenimientoAire extends OrdenServicio {
    //Atributo que guarda la capacidad del equipo en BTU
    private int capacidadBTU;
    //Constructor que inicializa los datos del mantenimiento
    public MantenimientoAire(String codigo, String cliente, String direccion, double horasEstimadas, String zona, int capacidadBTU) {
        //Inicializa los atributos heredados de OrdenServicio
        super(codigo, cliente, direccion, horasEstimadas, zona);
        //Valida que el mantenimiento sea en zona metropolitana
        if (!getZona().equalsIgnoreCase("Metropolitana")) {
            throw new IllegalArgumentException("Los mantenimientos de aire acondicionado solo se permiten en zona metropolitana.");
        }
        //Valida que la capacidad este entre 9000 y 60000 BTU
        if (capacidadBTU < 9000 || capacidadBTU > 60000) {
            throw new IllegalArgumentException("La capacidad debe estar entre 9000 y 60000 BTU.");
        }
        //Asigna la capacidad del equipo
        this.capacidadBTU = capacidadBTU;
    }
    //Metodo que devuelve la capacidad del equipo
    public int getCapacidadBTU() {
        return capacidadBTU;
    }
    //Metodo que calcula el costo del mantenimiento
    @Override
    public double calcularCosto() {
        //Tarifa base metropolitana utilizada en nuestro diseño
        double tarifaBase = 100;
        //Calcula el costo por horas estimadas
        double costoHoras = getHorasEstimadas() * 110;
        //Agrega el cargo fijo por gas refrigerante
        return tarifaBase + costoHoras + 80;
    }
    //Metodo que calcula los dias estimados de atencion
    @Override
    public int calcularTiempoAtencion() {
        return 2;
    }
    //Metodo que devuelve el maximo de visitas permitidas
    @Override
    public int maximoVisitas() {
        return 1;
    }
    //Metodo que devuelve el nombre de la categoria
    @Override
    public String getCategoria() {
        return "Mantenimiento Aire";
    }
    //Metodo que verifica si una cuadrilla puede realizar el mantenimiento
    @Override
    public boolean esCompatible(Cuadrilla cuadrilla) {
        //Verifica que exista la cuadrilla
        if (cuadrilla == null) {
            return false;
        }
        //Solo permite cuadrillas certificadas en refrigeracion
        return cuadrilla.getCertificadaRefrigeracion();
    }
}
