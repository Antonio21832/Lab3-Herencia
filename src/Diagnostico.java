
//Estuardo Antonio Molina 26878
//Clase que representa un servicio de diagnostico de TecniHogar
public class Diagnostico extends OrdenServicio {
    //Constructor que inicializa los datos del diagnostico
    public Diagnostico(String codigo, String cliente, String direccion, double horasEstimadas, String zona) {
        //Inicializa los atributos heredados de OrdenServicio
        super(codigo, cliente, direccion, horasEstimadas, zona);
        //Valida que el diagnostico no supere 1.5 horas
        if (horasEstimadas > 1.5) {
            throw new IllegalArgumentException("Un diagnostico no puede superar 1.5 horas estimadas.");
        }
    }
    //Metodo que calcula el costo fijo segun la zona
    @Override
    public double calcularCosto() {
        //Verifica si el servicio es en zona metropolitana
        if (getZona().equalsIgnoreCase("Metropolitana")) {
            return 150;
        }
        //Devuelve el costo de la zona periferica
        return 225;
    }
    //Metodo que calcula los dias estimados de atencion
    @Override
    public int calcularTiempoAtencion() {
        //Verifica si la zona es metropolitana
        if (getZona().equalsIgnoreCase("Metropolitana")) {
            return 1;
        }
        //Devuelve los dias de la zona periferica
        return 2;
    }
    //Metodo que devuelve el maximo de visitas permitidas
    @Override
    public int maximoVisitas() {
        return 3;
    }
    //Metodo que devuelve el nombre de la categoria
    @Override
    public String getCategoria() {
        return "Diagnostico";
    }
}
