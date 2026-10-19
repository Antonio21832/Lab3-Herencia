
 //Estuardo Antonio Molina 26878
//Clase que representa una cuadrilla de tecnicos de TecniHogar
public class Cuadrilla {
    //Atributos que guardan la informacion de la cuadrilla
    private String codigo;
    private double capacidadHoras;
    private boolean certificadaRefrigeracion;
    //Constructor que inicializa los datos de la cuadrilla
    public Cuadrilla(String codigo, double capacidadHoras, boolean certificadaRefrigeracion) {
        //Valida que el codigo no este vacio
        if (codigo == null || codigo.trim().length() == 0) {
            throw new IllegalArgumentException("El codigo de la cuadrilla no puede estar vacio.");
        }
        //Valida que la capacidad sea mayor que cero
        if (capacidadHoras <= 0 || Double.isNaN(capacidadHoras) || Double.isInfinite(capacidadHoras)) {
            throw new IllegalArgumentException("La capacidad de horas debe ser mayor que cero.");
        }
        //Asigna los valores recibidos
        this.codigo = codigo;
        this.capacidadHoras = capacidadHoras;
        this.certificadaRefrigeracion = certificadaRefrigeracion;
    }
    //Metodo que devuelve el codigo de la cuadrilla
    public String getCodigo() {
        return codigo;
    }
    //Metodo que devuelve la capacidad maxima de horas
    public double getCapacidadHoras() {
        return capacidadHoras;
    }
    //Metodo que indica si tiene certificacion en refrigeracion
    public boolean getCertificadaRefrigeracion() {
        return certificadaRefrigeracion;
    }
    //Metodo que muestra la informacion de la cuadrilla
    @Override
    public String toString() {
        String certificacion = "No";
        //Verifica si la cuadrilla esta certificada
        if (certificadaRefrigeracion == true) {
            certificacion = "Si";
        }
        //Devuelve la informacion de la cuadrilla
        return "Codigo: " + codigo + "\nCapacidad maxima: " + capacidadHoras + " horas\nCertificada en refrigeracion: " + certificacion;
    }
}
