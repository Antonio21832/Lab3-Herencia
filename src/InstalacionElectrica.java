
//Estuardo Antonio Molina 26878
//Clase que representa una instalacion electrica certificada de TecniHogar
public class InstalacionElectrica extends Instalacion {
    //Atributo que guarda el valor declarado de los materiales
    private double valorMateriales;
    //Constructor que inicializa los datos de la instalacion electrica
    public InstalacionElectrica(String codigo, String cliente, String direccion, double horasEstimadas, String zona, int cantidadPuntos, double valorMateriales) {
        //Inicializa los atributos heredados de Instalacion y OrdenServicio
        super(codigo, cliente, direccion, horasEstimadas, zona, cantidadPuntos);
        //Valida que el valor de los materiales no sea negativo
        if (valorMateriales < 0 || Double.isNaN(valorMateriales) || Double.isInfinite(valorMateriales)) {
            throw new IllegalArgumentException("El valor de los materiales no puede ser negativo ni invalido.");
        }
        //Asigna el valor declarado de los materiales
        this.valorMateriales = valorMateriales;
    }
    //Metodo que devuelve el valor de los materiales
    public double getValorMateriales() {
        return valorMateriales;
    }
    //Metodo que calcula el costo total de la instalacion electrica
    @Override
    public double calcularCosto() {
        //Obtiene el costo normal de una instalacion
        double costoInstalacion = super.calcularCosto();
        //Calcula el costo del certificado
        double certificado = 120;
        //Calcula el 5 por ciento del valor de los materiales
        double garantia = valorMateriales * 0.05;
        //Devuelve el costo total
        return costoInstalacion + certificado + garantia;
    }
    //Metodo que calcula el tiempo estimado de atencion
    @Override
    public int calcularTiempoAtencion() {
        //Agrega dos dias al tiempo de una instalacion normal
        return super.calcularTiempoAtencion() + 2;
    }
    //Metodo que devuelve el maximo de visitas permitidas
    @Override
    public int maximoVisitas() {
        return 2;
    }
    //Metodo que devuelve el nombre de la categoria
    @Override
    public String getCategoria() {
        return "Instalacion Electrica Certificada";
    }
}
