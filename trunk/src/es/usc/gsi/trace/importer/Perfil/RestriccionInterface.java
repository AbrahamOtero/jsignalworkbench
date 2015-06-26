//Source file: E:\\Perfil\\Perfil\\RestriccionInterface.java

package es.usc.gsi.trace.importer.Perfil;

import java.io.Serializable;

public interface RestriccionInterface extends Serializable {
    /**
     * Esto nos hara a nosotros responsables del versionamiento de los ficheros Serializados.
     * Siempre podemos volver a leerlos, pero depende de nosotros que se haga de un modo correcto.
     */

    static final long serialVersionUID = 1238L;

    /**
     * Devuelve la sintaxis de esta restriccion. En las posiciones del array se halla
     * la "sintaxis" (o lo que fuera si no tiene sentido la existencia de una
     * sintaxis) con cada PtoSig implicado en una restriccion. Si no hay semantica o
     * no tiene sentido se le asigna el valor 4.
     * pendiente -> 1
     * persistencia en pendiente -> 2
     * recta borrosa -> 3
     * sin semantica -> 4
     * @return int[]
     * @roseuid 3788D0690302
     */
    public int[] getSintaxisGeneralizada();

    /**
     * Iniciliza la sintaxis de esta restriccion.
     * @param i - Sintaxis respecto de cada Ptosig envuelto en la restriccion.
     * @roseuid 3788D0690316
     */
    public void setSintaxisGeneralizada(int[] i);

    /**
     * Devuelve la restriccion en magnitud. Lo hace en forma de un vector de 4
     * Strings.En cada posicion estan las restriiciones con el Ptosig 0, 1, 2...
     * @return String[][]
     * @roseuid 3788D069033E
     */
    public String[][] getDs();

    /**
     * Devuelve la restriccion temporal. Lo hace en forma de un vector de 4 Strings.En
     * cada posicion estan las restriiciones con el Ptosig 0, 1, 2...
     * @return String[][]
     * @roseuid 3788D0690352
     */
    public String[][] getLs();

    /**
     * Devuelve la restriccion en pendiente. Lo hace en forma de un vector de 4
     * Strings.En cada posicion estan las restriiciones con el Ptosig 0, 1, 2...
     * @return String[][]
     * @roseuid 3788D0690366
     */
    public String[][] getMs();

    /**
     * Inicializa la restricicon en magnitud.En cada posicion estan las restriiciones
     * con el Ptosig 0, 1, 2...
     * @param D - Arrayde arrays, cada unao de ellos  con 4 Strings representando la
     * distribucion de posibilidad en forma de trapecio.
     * @roseuid 3788D069037A
     */
    public void setDs(String[][] D);

    /**
     * Inicializa la restricicon temporal.
     * @param L - Array de arrays, cada unao de ellos  con 4 Strings representando la
     * distribucion de posibilidad en forma de trapecio.
     * @roseuid 3788D069038E
     */
    public void setLs(String[][] L);

    /**
     * Inicializa la restricicon en pendiente.
     * @param M - Array de arrays, cada unao de ellos con 4 Strings representando la
     * distribucion de posibilidad en forma de trapecio.
     * @roseuid 3788D06903A2
     */
    public void setMs(String[][] M);

    /**
     * Devuelve todos los PTB envueltos en esta restriccion. El orden en el que son
     * devulestops es el mismo en el que se nos devolveran los Ptosig de cada PTB que
     * envuelven a esta restriccion.
     * @return int[]
     * @roseuid 3789AF8D021A
     */
    public int[] getNumerosDePTB();

    /**
     * Vale para inicializar los PTB envueltosen esta restriccion.
     * @param v_PTB - Array de enteros correspondientes a los PTB.
     * @return Void
     * @roseuid 3789AFFD037E
     */
    public Void setNumerosDePTB(int[] v_PTB);

    /**
     * Devuelve un array con los PtoSig de cada PTB dado por getNumerosDePTB.
     * @return int[]
     * @roseuid 3789B18503E2
     */
    public int[] getNumerosDePtoSig();

    /**
     * Inicializa los PtosSig de cada PTB envueltos en esta restriccion.
     * @param v_PtoSig
     * @return Void
     * @roseuid 3789B2E80184
     */
    public Void setNumerosDePtoSig(int[] v_PtoSig);

    /**
     * Este el el metodo que permite resolver est restriccion. Para su reralizacion
     * sera necesario que la restriccion tenga un acceso a la senhal real de algum
     * modo. Probablemente se cree un Objeto que se encapsule dentro de la restriccion
     * y que tenga acceso a los cauces de entrada.
     * @return float
     * @roseuid 3789B7F80283
     */
    public float resolver();

    /**
     * Este metodo "resetea" el cauce de entrada poniendolo a null. De este modo se
     * evita que este sea serializado.
     * @return Void
     * @roseuid 3789BB62005E
     */
    public Void resetCauce();


    /**
     * indica si la restriccione s relativa al nevel basal
     * @return boolean
     */
    public boolean isRelativaAlNivelBasal();

    public void setRelativaAlNivelBasal(boolean relativaAlNivelBasal);

}
