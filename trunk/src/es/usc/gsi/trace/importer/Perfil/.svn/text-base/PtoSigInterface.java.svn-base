//Source file: E:\\Perfil\\Perfil\\PtoSigInterface.java

package es.usc.gsi.trace.importer.Perfil;

import java.io.Serializable;

public interface PtoSigInterface extends Serializable {
    /**
     * Esto nos hara a nosotros responsables del versionamiento de los ficheros Serializados.
     * Siempre podemos volver a leerlos, pero depende de nosotros que se haga de un modo correcto.
     */

    static final long serialVersionUID = 1236L;

    /**
     * Devuelven el valor temporal del primer PtoSig.
     * @return String
     * @roseuid 3788C760034A
     */
    public String getL();

    /**
     * Devuelve el valor de magnitud del primer PtoSig.
     * @return String
     * @roseuid 3788C760035E
     */
    public String getD();

    /**
     * Anhade, borra o modifica una restriccion.
     * @param ptb - PTB con el que se tienen la restricion.
     * @param ptosig - PtoSig conj el que se tiene la restriccion.
     * @param restriccion - Restriccion que se va a anhadir, o la nueva si se va a
     * modificar. null si se va a borrar.
     * @param restriccion_vieja - Restriccion antigua si se va a modificar o borrar.
     * Si se va a anhadir null.
     * @param seleccion - PTBM.BORAR/MODIFICAR/ANHADIR
     * @roseuid 3788C7600368
     */
    public void anhadeRestriccion(int ptb, int ptosig, Restriccion restriccion,
                                  Restriccion restriccion_vieja, int seleccion);

    /**
     * decremnat el numero de restricciones de este PtoSig.
     * @roseuid 3788C7600390
     */
    public void DecrementaNumeroRestricciones();

    /**
     * Devuelve el numero de restricciones de este PtoSig.
     * @return int
     * @roseuid 3788C76003A4
     */
    public int getNumeroDeRestricciones();

    /**
     * Devuelve el nuermo de este PtoSig dentro del PTB: Es la posicion en la cual
     * esta almacenado en el Objeto Vector que el PTB usa para almacenar los PtoSig.
     * @return int
     * @roseuid 3788C76003AE
     */
    public int getNumeroDePtoSig();

    /**
     * Inicializa el numero de este PtoSig.
     * @param num
     * @roseuid 3788C76003B8
     */
    public void setNumeroDePtoSig(int num);

    /**
     * Devuelve el numero asociado al PTB en el cual se halla este PtoSig.
     * @return int
     * @roseuid 3788C76003CC
     */
    public int getNumeroDePTB();

    /**
     * Inicializa el numero del PTB que contiene este PtoSig.
     * @param ptb
     * @roseuid 3788C76003D6
     */
    public void setNumeroDePTB(int ptb, int ptb_borrado);

    /**
     * Revisa cuales de sus restricciones hay que borrar cuando se prodece el borrado
     * del PtoSig que se le pasa en el argumento.
     * @param ptosig - Prosig que ha sido borrado.
     * @roseuid 3788C76003E0
     */
    public void revisaRestricciones(PtoSig ptosig);

    /**
     * Devuelve un vector con las restricicones que tiene este PtoSig.
     * @return Restriccion[]
     * @roseuid 3788C761000C
     */
    public Restriccion[] getRestricciones();

    /**
     * Iniciliza las restriciones que contine este PtoSig a las restriciones que se le
     * pasan en el array del argumento.
     * @param vrestricciones - Vector que contine las nuevas restricciones.
     * @param numrestricciones - numero de restriciones que hay en el array.
     * @roseuid 3788C7610020
     */
    public void setRestricciones(Restriccion[] vrestricciones,
                                 int numrestricciones);
}
