//Source file: E:\\Perfil\\Perfil\\PTBMInterface.java

package es.usc.gsi.trace.importer.Perfil;

import java.io.*;

/**
 * @author Abraham Otero Quintana
 * @version 0.4
 */
public interface PTBMInterface extends Serializable {

    /**
     * Esto nos hara a nosotros responsables del versionamiento de los ficheros Serializados.
     * Siempre podemos volver a leerlos, pero depende de nosotros que se haga de un modo correcto.
     */

    static final long serialVersionUID = 1234L;

    /**
     * Este método se emplea para añadir, eliminar o modificar un PTB de éste PTBM.
     * @param ptb - Es el PTB que se va a añadir, borrar o modificar. Si se va a
     * borrar o modificar no es necesario.
     * @param numeroPTB - Es el número del PTB que se va a añadir/borrar/modificar.
     * @param seleccion - Puede tomar los valors PTBM.AÑADIR, PTBM.BORRAR,
     * PTBM.Modificar
     * @roseuid 37875DDC03AB
     */
    public void añadePTB(PTB ptb, int numeroPTB, int seleccion);

    /**
     * Indica si el objeto PTBM tiene un fichero asignado o no.
     * @return boolean
     * @roseuid 37875DED0234
     */
    public boolean tieneFicheroAsicoado();

    /**
     * Modifica el estado de tener o no fichero asignado.
     * @param b - True si tiene fichero asignado, fales si no
     * @roseuid 37875DED0252
     */
    public void setTieneFicheroAsociado(boolean b);

    /**
     * Debuelve un String con el fichero que tiene asignado éste PTBM, en caso de que
     * tenga fichero asignado.
     * @return String
     * @roseuid 37875DED0266
     */
    public String getFicheroAsociado();

    /**
     * Asocia un Fichero a éste PTBM
     * @param fichero - String del PATH completo del fichero
     * @roseuid 37875DED0270
     */
    public void setFicheroAsociado(String fichero);

    /**
     * Devuelve un array con todos lo PTB que componen éste PTBM.
     * @return PTB[]
     * @roseuid 37875DED02FC
     */
    public PTB[] getPTB();

    /**
     * Hace que los PTB que contenga este PTBM sean los que se le pasan en el array.
     * @param vptb - Array con los PTB que queremos se hallen en este PTBM
     * @param nptb - Número de PTB que contiene el array.
     * @roseuid 37875DED0306
     */
    public void setPTB(PTB[] vptb, int nptb);

    /**
     * Devuelve un Array con el numero de PtoSig que tiene cada PTB. En la posicion 0
     * del array va el numero de puntos que contiene el PTB 0 ...
     * @return int[]
     * @roseuid 37875DED0338
     */
    public int[] getNumeroPtoSig();

    /**
     * Añade, modifica o borra un PtoSig de un PTB.
     * @param ptb - PTB al cual se le va a añadir el PtoSig. OJO: debería modificarse
     * esto para en vez de pasarle el PTB, pasáele el numero del PTB.
     * @param ptosig - PtoSig a añadir/modificar/borrar. Si se va a borrar no es
     * necesario.
     * @param numeroPtoSig - Numero del PtoSig . Solo es imprescindible cuando se va a
     * borrar.
     * @param selecion - PTBM.AÑADIR/BORRAR/MODIFICAR
     * @roseuid 37875DED034C
     */
    public void añadePtoSig(PTB ptb, PtoSig ptosig, int numeroPtoSig,
                            int selecion);

    /**
     * @param ptb
     * @param ptosig
     * @param restriccion
     * @param restriccion_vieja
     * @param numeroPtoSig
     * @param selecion
     * @roseuid 37875DED0360
     */
    public void añadeRestriccion(PTB ptb, PtoSig ptosig,
                                 Restriccion restriccion,
                                 Restriccion restriccion_vieja,
                                 int numeroPtoSig, int selecion);

    /**
     * Debuelve true si el fichero de Sereializacion asociado a este PTBM esta
     * actualizado, false en caso contrario.
     * @return boolean
     * @roseuid 37875DED0374
     */
    public boolean isGuardado();

    /**
     * Actualiza el estado de gurdado (true) o no guardado (false) de este PTBM.
     * @param b
     * @roseuid 37875DED03B0
     */
    public void setGuardado(boolean b);

    /**
     * Debuelva un Array con todas las restricciones del PtoSig deseado.
     * @param ptb - Numero del PTB en el que se halla el PtoSig.
     * @param ptosig - Numero del PtoSig.
     * @return Restriccion[]
     * @roseuid 37875DED03D9
     */
    public Restriccion[] getRestricciones(int ptb, int ptosig);

    /**
     * Realiza los ajustes necesarios en las restricciones y PtoSig de todos los PTB
     * cuando un PtoSig es borrado.
     * @param ptosig - PtoSig que es borrado.
     * @roseuid 37875DEE000F
     */
    public void revisaRestricciones(PtoSig ptosig);

    /**
     * Revisa las restricciones de todas los PTB cuando uno es borrado.
     * @param ptb - PTB que ha sido borrado.
     * @roseuid 37875DEE002D
     */
    public void revisaRestricciones(PTB ptb);

    /**
     * Almacena en una variable no estatica el numero de PTB que hay en este PTBM. Es
     * para que esta sea serailizada.
     * @roseuid 37875DEE0041
     */
    public void almacenaNumeroPTB();

    /**
     * Debuelve el contenido de la variable no estatica que almacena el numero de PTB
     * que hay en este PTBM.
     * @return int
     * @roseuid 37875DEE0069
     */
    public int getnumeroPTBnoEstatico();

    /**
     * Este metodo no se implementará actualmente. su misión es permitir
     * compatibilidad entre los formatos serializados de distitas versiones de PTBM.
     * En caso de que en el futuro haya que introducir un nuevo campo en el PTBM (ej:
     * nombre del autor del PTBM), se almacenarta como una dato Object dentro de una
     * lista. Este metodo accederá a la posición i de esa lista para recuperar ese
     * dato.
     * @param parametro
     * @return Object
     * @roseuid 3788B6DD0285
     */
    public Object getParametro(int parametro);

    /**
     * Este metodo no se implementará actualmente. su misión es permitir
     * compatibilidad entre los formatos serializados de distitas versiones de PTBM.
     * En caso de que en el futuro haya que introducir un nuevo campo en el PTBM (ej:
     * nombre del autor del PTBM), se almacenarta como una dato Object dentro de una
     * lista. Este metodo accederá a la posición i de esa lista para almacenar ese dat
     * @param parametro - Es la posición del vector a la cual se accederá para
     * recuperear un determinado parametro.
     * @roseuid 3788BA0903A7
     */
    public void setParametro(int parametro);

    /**
     * Este método devuelve el titulo del PTBM
     * @roseuid 3788BA0903A7
     */
    public String getTitulo();

    /**
     * Este método devuelve el comentario del PTBM
     * @roseuid 3788BA0903A7
     */
    public String getComentario();

    /**
     * Este método modifica el titulo y el comentario del PTBM
     * @roseuid 3788BA0903A7
     * @param titulo
     * @param comentario
     */
    public void modificar(String titulo, String comentario);

    public float getFinCoreSeparacion();

    public float getFinSoporteSeparacion();

    public void setFinCoreSeparacion(float finCoreSeparacion);

    public void setFinSoporteSeparacion(float finSoporteSeparacion);

    public float getInicioSoporteSeparacion();

    public float getInicioCoreSeparacion();

    public void setInicioCoreSeparacion(float inicioCoreSeparacion);

    public void setInicioSoporteSeparacion(float inicioSoporteSeparacion);

    public float getSeparacionCrips();

}
