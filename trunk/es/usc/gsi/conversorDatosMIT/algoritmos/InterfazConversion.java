package es.usc.gsi.conversorDatosMIT.algoritmos;


public interface InterfazConversion {

// ESTE METODO CONVIERTE A ENTERO EL PAR DE BYTES QUE LE PASEMOS
// SEGUN LAS ESPECIFICACIONES DE FORMATO DE MITDB .
// NECESITA EL NUMERO DE MUESTRA PARA DECIDIR COMO OPERAR CON LOS BYTES.
// VER CLASES QUE IMPLEMENTAN ESTA INTERFAZ PARA ENTENDERLO.
// NOTA: numMuestra=0 PARA LA PRIMERA MUESTRA,1 PARA LA SEGUNDA,2 PARA LA TERCERA...



    public int convierteBytes(byte[] b, int numMuestra);

// Este metodo proporciona el "step": cuantos bytes debemos avanzar para
// obtener la siguiente muestra.
    public float getStep();

// Este metodo proporciona el numero de bytes que debemos leer de un fichero para
// poder obtener una muestra a partir de ellos.
    public int getArraySize();


} // Fin InterfazConversion
