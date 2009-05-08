//Source file: E:\\Perfil\\Perfil\\PTBInterface.java

package es.usc.gsi.trace.importer.Perfil;

import java.io.*;

public interface PTBInterface extends Serializable {
    /**
     * Esto nos hara a nosotros responsables del versionamiento de los ficheros Serializados.
     * Siempre podemos volver a leerlos, pero depende de nosotros que se haga de un modo correcto.
     */

    static final long serialVersionUID = 1232L;
    ;

    /**
     * Asigna un objeto de tipo PTBM a este PTB. Le indica a que PTBM pertenece.
     * @param ptbm
     * @roseuid 3788BB0A0130
     */
    public void setPTBM(PTBMInterface ptbm);

    /**
     * A�ade un PtoSig a este PTBM.
     * @param ptosig - PtoSig que se le va a a�adir a este PTB.
     * @roseuid 3788BB0A0144
     */
    public void a�adePtoSig(PtoSig ptosig);

    /**
     * Devuelve el numero de PtoSig que hay actualmente en este PTBM.
     * @return int
     * @roseuid 3788BB0A0162
     */
    public int getNumeroDePtoSig();

    /**
     * Decrementa el numero de PtoSig que hay actualmente en este PTB.
     * @roseuid 3788BB0A016C
     */
    public void DecrementaNumeroDePtoSig();

    /**
     * Devuelve el numero asociado a este PTB. Empieza a contar en 0. Es la posici�n
     * que ocupa este PTB dentro del objeto Vector contenido en el PTBM al cual
     * pertence este PTB.
     * @return int
     * @roseuid 3788BB0A0176
     */
    public int getNumeroDePTB();

    /**
     * Pone el numero asociado a este PTB a un determinado valor.
     * @param tmp
     * @roseuid 3788BB0A0180
     */
    public void setNumeroDePTB(int tmp, int ptb_borrado);

    /**
     * Elimina todas las restriciones de este u otros PTB en las cuales participe este
     * PtoSig. Se emple cuando se borra un determinado PtoSig.
     * @param ptosig - PtoSig que se va ha borrar.
     * @roseuid 3788BB0A0195
     */
    public void revisaRestricciones(PtoSig ptosig);

    /**
     * Devuelve un Array conteniendo todos los PtoSig de etse PTB.
     * @return PtoSig[]
     * @roseuid 3788BB0A01A9
     */
    public PtoSig[] getPtoSig();

    /**
     * Iniciliza el Vector que contiene todos los PtoSifg de este PTB a los PtoSig que
     * se le pasan en el array.
     * @param vptosig - Vector que contiene todos los PtoSig.
     * @param nptosig - Numero de PtoSig que hay en el array.
     * @roseuid 3788BB0A01B3
     */
    public void setPtoSig(PtoSig[] vptosig, int nptosig);

    /**
     * Modifica los campos nombre, se�al, unidades, comentario y unidades temporales
     * de �ste PTB. Los inicializa a los valores que les pasamos.
     * @param nombre
     * @param parametro
     * @param unidades
     * @param comentario
     * @param unidades_tiempo
     * @roseuid 3788BB0A01BD
     */
    public void modificar(String nombre, String parametro, String unidades,
                          String comentario, String unidades_tiempo);

    public void modificar(String nombre, String parametro, String unidades,
                          String comentario, String unidades_tiempo,
                          float intInicioSoporteSeparacion,
                          float intInicioCoreSeparacion,
                          float intFinCoreSeparacion,
                          float intFinSoporteSeparacion, float longitudVentana,
                          boolean buscarEnValorAbsoluto);


    /**
     * Devuelve un vector con las restricciones del PtoSig i.
     * @param ptosig - PtoSig del cual queremos que nos devuelva las restricciones.
     * @return Restriccion[]
     * @roseuid 3788BB0A01D1
     */
    public Restriccion[] getRestricciones(int ptosig);

    /**
     * A�ade, borra o modifica un PtoSig seg�n el selector que se le pase. Este m�todo
     * deber�a de llevar toda la funcionalidad del  metodo a�adePtoSig, y este deber�a
     * "deprecarse".
     * @param ptosig - PtoSIg que se va a a�adir, o modificar. Si se va  a borrar no
     * es necesario.
     * @param numeroPtoSig - Posici�n que ocupa el PtoSIg que se va a modificar o
     * borrar. Si se va a crear no es necesario.
     * @param seleccion - PTBM.BORRAR/MODIFICAR/A�ADIR.
     * @roseuid 3788BB0A01DB
     */
    public void a�adePtoSig(PtoSig ptosig, int numeroPtoSig, int seleccion);

    /**
     * a�ade, borra o modifica una restricion. OJO: se ha modificado para quitoer el
     * int ptosig.
     * @param ptb - PTB con el cual se tienen la restriccion.
     * @param ptosig - PtoSig que al que se le va a a�adir/borrar/modificar la
     * restriccion.
     * @param restriccion - Restricci�n nueva en caso de que se vaya a a�adir o
     * modificar. null si se va a borrar.
     * @param restriccion_vieja - restriccion antigua en caso de que se vaya a borrar
     * o modificar. null si se va a �adir.
     * @param seleccion - PTBM:BORRAR/MODIFICAR/A�ADIR
     * @roseuid 3788BB0A01EF
     */
    public void a�adeRestriccion(int ptb, int ptosig, Restriccion restriccion,
                                 Restriccion restriccion_vieja, int seleccion);

    /**
     * Devuelve le nombre de este PTB.
     * @return String
     * @roseuid 3788BB0A020D
     */
    public String getNombre();

    /**
     * Devuleve el comentario de este PTB.
     * @return String
     * @roseuid 3788BB0A0249
     */
    public String getComentario();

    /**
     * Devuelve el paramentro sobre el que se mide este PTB.
     * @return String
     * @roseuid 3788BB0A0253
     */
    public String getParametro();

    /**
     * Devuelve las unidades del parametro de este PTB.
     * @return String
     * @roseuid 3788BB0A025D
     */
    public String getUnidades();

    /**
     * Devuelve las unidades temporales de este PTB.
     * @return String
     * @roseuid 3788C6CE00F5
     */
    public String getUnidadesTemporales();

    /**
     * Este metodo no se implementar� actualmente. su misi�n es permitir
     * compatibilidad entre los formatos serializados de distitas versiones de PTB. En
     * caso de que en el futuro haya que introducir un nuevo campo en el PTB (ej:
     * nombre del autor del PTB), se almacenarta como una dato Object dentro de una
     * lista. Este metodo acceder� a la posici�n i de esa lista para recuperar ese
     * dato.
     * @param parametro
     * @return Object
     * @roseuid 3788CEC202AD
     */
    public Object getParametro(int parametro);

    /**
     * Este metodo no se implementar� actualmente. su misi�n es permitir
     * compatibilidad entre los formatos serializados de distitas versiones de PTB. En
     * caso de que en el futuro haya que introducir un nuevo campo en el PTB (ej:
     * nombre del autor del PTB), se almacenarta como una dato Object dentro de una
     * lista. Este metodo acceder� a la posici�n i de esa lista para almacenar ese dat
     * @param parametro - Es la posici�n del vector a la cual se acceder� para
     * recuperear un determinado parametro.
     * @roseuid 3788CEC202D5
     */
    public void setParametro(int parametro);

    /**
     * M�todos para acceder a la distancia entre PTB
     * @return int
     */
    public float getIntInicioSoporteSeparacion();

    public void setIntInicioSoporteSeparacion(float intInicioSoporteSeparacion);

    public void setIntInicioCoreSeparacion(float intInicioCoreSeparacion);

    public void setIntFinSoporteSeparacion(float intFinSoporteSeparacion);

    public void setIntFinCoreSeparacion(float intFinCoreSeparacion);

    public float getIntInicioCoreSeparacion();


    public float getIntFinCoreSeparacion();

    public float getIntFinSoporteSeparacion();

    public float getSeparacionCrisp();


    /**
     * M�dtodos para acceder a la longitud de la ventana de b�squeda
     * @return int
     */
    public float getLongitudVentana();

    public void setLongitudVentana(float longitudVentana);

    public boolean isBuscarEnValorAbsoluto();
}
