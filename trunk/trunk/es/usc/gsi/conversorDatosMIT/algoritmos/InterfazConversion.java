package es.usc.gsi.conversorDatosMIT.algoritmos;


public interface InterfazConversion {

// ESTE MÉTODO CONVIERTE A ENTERO EL PAR DE BYTES QUE LE PASEMOS
// SEGÚN LAS ESPECIFICACIONES DE FORMATO DE MITDB .
// NECESITA EL NÚMERO DE MUESTRA PARA DECIDIR CÓMO OPERAR CON LOS BYTES.
// VER CLASES QUE IMPLEMENTAN ESTA INTERFAZ PARA ENTENDERLO.
// NOTA: numMuestra=0 PARA LA PRIMERA MUESTRA,1 PARA LA SEGUNDA,2 PARA LA TERCERA...



    public int convierteBytes(byte[] b, int numMuestra);

// Este método proporciona el "step": cuántos bytes debemos avanzar para
// obtener la siguiente muestra.
    public float getStep();

// Este método proporciona el número de bytes que debemos leer de un fichero para
// poder obtener una muestra a partir de ellos.
    public int getArraySize();


} // Fin InterfazConversion