package es.usc.gsi.conversorDatosMIT.algoritmos;


public interface InterfazConversion {

// ESTE M�TODO CONVIERTE A ENTERO EL PAR DE BYTES QUE LE PASEMOS
// SEG�N LAS ESPECIFICACIONES DE FORMATO DE MITDB .
// NECESITA EL N�MERO DE MUESTRA PARA DECIDIR C�MO OPERAR CON LOS BYTES.
// VER CLASES QUE IMPLEMENTAN ESTA INTERFAZ PARA ENTENDERLO.
// NOTA: numMuestra=0 PARA LA PRIMERA MUESTRA,1 PARA LA SEGUNDA,2 PARA LA TERCERA...



    public int convierteBytes(byte[] b, int numMuestra);

// Este m�todo proporciona el "step": cu�ntos bytes debemos avanzar para
// obtener la siguiente muestra.
    public float getStep();

// Este m�todo proporciona el n�mero de bytes que debemos leer de un fichero para
// poder obtener una muestra a partir de ellos.
    public int getArraySize();


} // Fin InterfazConversion