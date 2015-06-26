package es.usc.gsi.conversorDatosMIT.ficheros;


public class Parametro {

    public static final int F8 = 8;
    public static final int F16 = 16;
    public static final int F61 = 61;
    public static final int F80 = 80;
    public static final int F160 = 160;
    public static final int F212 = 212;
    public static final int F310 = 310;

    private String nombreFichero;
    private FicheroDat ficheroDat;
    private int formatoAlmacenamiento;
    private int factorFrecuencia = 1;
    private float ganancia;
    private String unidades;
    private int resolucionConversor;

    // No se usan en sutil+: todos valen 0
    private int offset;
    private long primerValorLeido;
    private long checksum;
    private long tamanhoBloque;

    // Nombre de la columna de la tabla de la MIT DB
    private String nombreParametro;

    // Frecuencia REAL a la cual
    // se muestrea nuestro parametro=frecuencia muestreo marco * factorFrecuencia

    private float frecuenciaMuestreo;
    private float backupFrecuenciaMuestreo;

    // Fechas de inicio y fin para el remuestreo con sus correspondientes backups.
    private String fechaInicio;
    private String backupFechaInicio;

    private String fechaFin;
    private String backupFechaFin;


    // Flag que indica si su checkbox esta activado
    private boolean activado = false;
    public static int numSeleccionados = 0;
    // Atributo que almacena los valores seleccionados

    private int[] valores;


    // ADMITE FECHAS DE INICIO DE REMUESTREO Y DE FIN DE REMUESTREO.
    // ESTAS FECHAS COINCIDIRAN CON LAS REALES EN CASO DE QUE NO SE REQUIERA REMUESTREO.
    public Parametro(String nombreFichero, int formatoAlmacenamiento,
                     int factorFrecuencia, float ganancia,
                     String unidades, int resolucionConversor,
                     int offset, long primerValorLeido, long checksum,
                     long tamanhoBloque,
                     String nombreParametro, float frecuenciaMuestreo,
                     String fechaInicio, String fechaFin
            ) {

        // Inicializacion de atributos

        this.nombreFichero = nombreFichero;
        this.ficheroDat = new FicheroDat(nombreFichero); //Fichero donde se almacena la info.
        this.formatoAlmacenamiento = formatoAlmacenamiento;
        this.factorFrecuencia = factorFrecuencia;
        this.ganancia = ganancia;
        this.unidades = unidades;
        this.resolucionConversor = resolucionConversor;
        this.offset = offset;
        this.primerValorLeido = primerValorLeido;
        this.checksum = checksum;
        this.tamanhoBloque = tamanhoBloque;
        this.nombreParametro = nombreParametro;
        this.frecuenciaMuestreo = frecuenciaMuestreo;
        this.backupFrecuenciaMuestreo = frecuenciaMuestreo;
        this.fechaInicio = fechaInicio;
        this.backupFechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.backupFechaFin = fechaFin;
        this.corrigeNombre();
    }

    // Constructor copia
    public Parametro(Parametro par) {
        this(
                par.getNombreFichero(), par.getFormatoAlmacenamiento(),
                par.getFactorFrecuencia(), par.getGanancia(),
                par.getUnidades(), par.getResolucionConversor(),
                par.getOffset(), par.getPrimerValorLeido(), par.getChecksum(),
                par.getTamanhoBloque(), par.getNombreParametro(),
                par.getFrecuenciaMuestreo(),
                par.getFechaInicio(), par.getFechaFin()
                );
    }

    public void setNombreFichero(String nombreFichero) {
        this.nombreFichero = nombreFichero;
    }

    public String getNombreFichero() {
        return nombreFichero;
    }

    public FicheroDat getFicheroDat() {
        return ficheroDat;
    }

    public void setFormatoAlmacenamiento(int formatoAlmacenamiento) {
        this.formatoAlmacenamiento = formatoAlmacenamiento;
    }

    public int getFormatoAlmacenamiento() {
        return formatoAlmacenamiento;
    }

    public void setFactorFrecuencia(int factorFrecuencia) {
        this.factorFrecuencia = factorFrecuencia;
    }

    public int getFactorFrecuencia() {
        return factorFrecuencia;
    }

    public void setGanancia(float ganancia) {
        this.ganancia = ganancia;
    }

    public float getGanancia() {
        return ganancia;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }

    public String getUnidades() {
        return unidades;
    }

    public void setResolucionConversor(int resolucionConversor) {
        this.resolucionConversor = resolucionConversor;
    }

    public int getResolucionConversor() {
        return resolucionConversor;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    public void setPrimerValorLeido(long primerValorLeido) {
        this.primerValorLeido = primerValorLeido;
    }

    public long getPrimerValorLeido() {
        return primerValorLeido;
    }

    public void setChecksum(long checksum) {
        this.checksum = checksum;
    }

    public long getChecksum() {
        return checksum;
    }

    public void setTamanhoBloque(long tamanhoBloque) {
        this.tamanhoBloque = tamanhoBloque;
    }

    public long getTamanhoBloque() {
        return tamanhoBloque;
    }

    public void setNombreParametro(String nombreParametro) {
        this.nombreParametro = nombreParametro;
    }

    public String getNombreParametro() {
        return nombreParametro;
    }

    public void setFrecuenciaMuestreo(float frecuenciaMuestreo) {
        this.frecuenciaMuestreo = frecuenciaMuestreo;
    }

    public float getFrecuenciaMuestreo() {
        return frecuenciaMuestreo;
    }

    public float getBackupFrecuenciaMuestreo() {
        return backupFrecuenciaMuestreo;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaInicio() {
        return this.fechaInicio;
    }

    public String getBackupFechaInicio() {
        return this.backupFechaInicio;
    }

    public String getBackupFechaFin() {
        return this.backupFechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaFin() {
        return this.fechaFin;
    }

    public void setValores(int[] valores) {
        this.valores = valores;
    }

    public int[] getValores() {
        return valores;
    }


    // Restaura valores originales
    public void resetFrecuencia() {

        this.frecuenciaMuestreo = backupFrecuenciaMuestreo;

    }

    public void resetFechas() {

        this.fechaInicio = this.backupFechaInicio;
        this.fechaFin = this.backupFechaFin;

    }

    public void setActivado(boolean b) {
        this.activado = b;
        numSeleccionados = b ? (numSeleccionados + 1) : (numSeleccionados - 1);
    }

    public boolean getActivado() {
        return activado;
    }

    // Corrector de nombre del parametro
    // SOLO CORRIGE LA ULTIMA CIFRA. SI HUBIESE 2 CIFRAS AL FINAL
    // DEL NOMBRE DEL PARAMETRO, ERROR!!!
    private void corrigeNombre() {

        String[] terminacion = {"I", "II", "III", "V", "aVL", "aVR", "aVF",
                               "SaO2", "Resp"};
        int indice;
        String nuevoNombre;

        try {
            //  System.out.println( nombreParametro.substring( nombreParametro.length() - 1 ) );

            if (nombreParametro.equalsIgnoreCase("SaO2")) {
                return; // Excepcion a la regla general: en este caso, no se debe corregir el numero de final del nombre.
            }

            indice = Integer.parseInt(nombreParametro.substring(nombreParametro.
                    length() - 1));
            nuevoNombre = nombreParametro.substring(0,
                    nombreParametro.length() - 1) +
                          terminacion[indice];
            this.nombreParametro = nuevoNombre;

        } catch (NumberFormatException e) {
            /*No es necesario corregir nombres*/
        }

    }


} // Fin clase Parametro
