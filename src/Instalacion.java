
//Estuardo Antonio Molina 26878
//Clase que representa un servicio de instalacion de TecniHogar
public class Instalacion extends OrdenServicio {
    //Atributo que guarda la cantidad de puntos a instalar
    private int cantidadPuntos;
    //Constructor que inicializa los datos de la instalacion
    public Instalacion(String codigo, String cliente, String direccion, double horasEstimadas, String zona, int cantidadPuntos) {
        //Inicializa los atributos heredados de OrdenServicio
        super(codigo, cliente, direccion, horasEstimadas, zona);
        //Valida que la cantidad de puntos sea positiva
        if (cantidadPuntos <= 0) {
            throw new IllegalArgumentException("La cantidad de puntos debe ser mayor que cero.");
        }
        //Asigna la cantidad de puntos
        this.cantidadPuntos = cantidadPuntos;
    }
    //Metodo que devuelve la cantidad de puntos a instalar
    public int getCantidadPuntos() {
        return cantidadPuntos;
    }
    //Metodo que calcula las horas facturables de la instalacion
    public int calcularHorasFacturables() {
        //Calcula las horas necesarias segun la cantidad de puntos
        double horasPuntos = cantidadPuntos * 0.75;
        //Compara las horas por puntos con las horas estimadas
        double horasFacturables = getHorasEstimadas();
        if (horasPuntos > horasFacturables) {
            horasFacturables = horasPuntos;
        }
        //Redondea las horas hacia arriba al entero siguiente
        return (int) Math.ceil(horasFacturables);
    }
    //Metodo que calcula el costo de la instalacion
    @Override
    public double calcularCosto() {
        //Establece la tarifa base segun la zona
        double tarifaBase = 100;
        if (getZona().equalsIgnoreCase("Periferica")) {
            tarifaBase = 175;
        }
        //Calcula el costo total de la instalacion
        return tarifaBase + (calcularHorasFacturables() * 90);
    }
    //Metodo que calcula los dias estimados de atencion
    @Override
    public int calcularTiempoAtencion() {
        //Verifica si la zona es metropolitana
        if (getZona().equalsIgnoreCase("Metropolitana")) {
            return 3;
        }
        //Devuelve los dias de la zona periferica
        return 5;
    }
    //Metodo que devuelve el maximo de visitas permitidas
    @Override
    public int maximoVisitas() {
        return 3;
    }
    //Metodo que devuelve el nombre de la categoria
    @Override
    public String getCategoria() {
        return "Instalacion";
    }
}
