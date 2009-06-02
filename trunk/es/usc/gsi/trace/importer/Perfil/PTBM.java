//Source file: E:\\Perfil\\Perfil\\PTBM.java

package es.usc.gsi.trace.importer.Perfil;

import java.io.Serializable;
import java.util.Vector;

/**
 * @author Abraham Otero Quintana
 * @version 0.4
 */
public class PTBM implements PTBMInterface, Serializable {
    /**
     * Esto nos hara a nosotros responsables del versionamiento de los ficheros Serializados.
     * Siempre podemos volver a leerlos, pero depende de nosotros que se haga de un modo correcto.
     */

    static final long serialVersionUID = 1233L;

//Separacion entre PTBM
    private float inicioSoporteSeparacion = 10, inicioCoreSeparacion = 15,
    finCoreSeparacion = 20, finSoporteSeparacion = 25;
    /**
     * //////////////////VARIABLES////////////////////////////////////
     */
    private String nombre;

    /**
     * //////////////////VARIABLES////////////////////////////////////
     */
    private String comentario;

    /**
     * //////////////////VARIABLES////////////////////////////////////
     */
    private transient String fichero;
    private static int numeroPTB;
    private int numeroPTBnoEstatico = 0;
    private transient boolean tieneficheroasociado = false;
    private transient boolean guardado = false;
    public static final int MODIFICAR = 0;
    public static final int BORRAR = -1;
    public static final int ANHADIR = 1;
    public static final int CREAR = 2;
    private Vector parametros;
    private Vector vectorPTB = new Vector();
    public PTBInterface thePTBInterface[];


    /**
     * @param nombre
     * @param comentario
     * @param ptb
     * @roseuid 37870819009D
     */
    public PTBM(String nombre, String comentario, PTB ptb) {
        this.nombre = nombre;
        this.comentario = comentario;
        vectorPTB.addElement(ptb);
        numeroPTB = 1;
        numeroPTBnoEstatico = 1;
    }

    /**
     * @param ptb
     * @param numeroPTB
     * @param seleccion
     * @roseuid 3787081900A1
     */
    public void anhadePTB(PTB ptb, int numeroPTB, int seleccion) {
        if (seleccion == 1) {
            vectorPTB.addElement(ptb);
            PTBM.incrementaNumeroPTB();
            numeroPTBnoEstatico++;
        } else if (seleccion == 0) {
            ((PTB) vectorPTB.elementAt(numeroPTB)).modificar(ptb.getNombre(),
                    ptb.getParametro(),
                    ptb.getUnidades(), ptb.getComentario(), "OJO");
        } else if (seleccion == -1) {
            revisaRestricciones(ptb);
            vectorPTB.remove(numeroPTB);
            PTBM.decrementaNumeroPTB();
            numeroPTBnoEstatico--;
            //Esto es para borrar restricciones que no se borran donde devieran
            for (int i = 0; i < numeroPTB; i++) {
                ((PTB) vectorPTB.elementAt(i)).setNumeroDePTB(i, numeroPTB);
            }

            for (int i = (numeroPTB); i < this.getNumeroPTB(); i++) {
                int num = ((PTB) vectorPTB.elementAt(i)).getNumeroDePTB();
                ((PTB) vectorPTB.elementAt(i)).setNumeroDePTB(num - 1,
                        numeroPTB);
            }
        }
    }

    /**
     * @return boolean
     * @roseuid 3787081900A5
     */
    public boolean tieneFicheroAsicoado() {
        return tieneficheroasociado;
    }

    /**
     * @param b
     * @roseuid 3787081900A6
     */
    public void setTieneFicheroAsociado(boolean b) {

        tieneficheroasociado = b;
    }

    /**
     * @return java.lang.String
     * @roseuid 3787081900A8
     */
    public String getFicheroAsociado() {
        return fichero;
    }

    /**
     * @param fichero
     * @roseuid 3787081900A9
     */
    public void setFicheroAsociado(String fichero) {
        this.fichero = fichero;
    }

    /**
     * @return int
     * @roseuid 3787081900AB
     */
    public static int getNumeroPTB() {
        return numeroPTB;
    }

    /**
     * @param i
     * @roseuid 3787081900AC
     */
    public static void setNumeroPTB(int i) {
        numeroPTB = i;
    }

    /**
     * @roseuid 3787081900AE
     */
    public static void incrementaNumeroPTB() {
        numeroPTB++;
    }

    /**
     * @roseuid 3787081900AF
     */
    public static void decrementaNumeroPTB() {
        numeroPTB--;
    }

    /**
     * @return PTB[]
     * @roseuid 3787081900B0
     */
    public PTB[] getPTB() {
        PTB[] vptb = new PTB[numeroPTBnoEstatico];
        for (int i = 0; i < (numeroPTBnoEstatico); i++) {
            vptb[i] = ((PTB) vectorPTB.elementAt(i));
        }
        return vptb;
    }

    /**
     * @param vptb
     * @param nptb
     * @roseuid 3787081900B1
     */
    public void setPTB(PTB[] vptb, int nptb) {
        vectorPTB.removeAllElements();
        for (int i = 0; i < nptb; i++) {
            vectorPTB.addElement(vptb[i]);
        }
        this.numeroPTB = nptb;
        this.numeroPTBnoEstatico = nptb;
    }

    /**
     * @return int[]
     * @roseuid 3787081900B4
     */
    public int[] getNumeroPtoSig() {
        int v[] = new int[numeroPTB];
        for (int i = 0; i < vectorPTB.size(); i++) {
            v[i] = ((PTB) vectorPTB.elementAt(i)).getNumeroDePtoSig();
        }
        return v;
    }

    /**
     * @param ptb
     * @param ptosig
     * @param numeroPtoSig
     * @param selecion
     * @roseuid 3787081900B5
     */
    public void anhadePtoSig(PTB ptb, PtoSig ptosig, int numeroPtoSig,
                             int selecion) {
        ((PTB) vectorPTB.elementAt(vectorPTB.indexOf(ptb))).anhadePtoSig(ptosig,
                numeroPtoSig, selecion);
    }

    /**
     * @param ptb
     * @param ptosig
     * @param restriccion
     * @param restriccion_vieja
     * @param numeroPtoSig
     * @param selecion
     * @roseuid 3787081900BA
     */
    public void anhadeRestriccion(PTB ptb, PtoSig ptosig,
                                  Restriccion restriccion,
                                  Restriccion restriccion_vieja,
                                  int numeroPtoSig, int selecion) {
        ((PTB) (vectorPTB.elementAt(vectorPTB.indexOf(ptb)))).anhadeRestriccion(
                ptb.getNumeroDePTB(),
                ptosig.getNumeroDePtoSig(), restriccion,
                restriccion_vieja, /*numeroPtoSig,*/ selecion);

    }

    /**
     * @return boolean
     * @roseuid 3787081900C1
     */
    public boolean isGuardado() {
        return guardado;
    }

    /**
     * @param b
     * @roseuid 3787081900C2
     */
    public void setGuardado(boolean b) {
        guardado = b;
    }

    /**
     * @param ptb
     * @param ptosig
     * @return Restriccion[]
     * @roseuid 3787081900C4
     */
    public Restriccion[] getRestricciones(int ptb, int ptosig) {
        return ((PTB) vectorPTB.elementAt(ptb)).getRestricciones(ptosig);
    }

    /**
     * @param ptosig
     * @roseuid 3787081900C7
     */
    public void revisaRestricciones(PtoSig ptosig) {
        for (int i = 0; i < numeroPTB; i++) {
            ((PTB) vectorPTB.elementAt(i)).revisaRestricciones(ptosig);
        }
    }

    /**
     * @param ptb
     * @roseuid 3787081900C9
     */
    public void revisaRestricciones(PTB ptb) {
        PTB ptb2 = ((PTB) vectorPTB.elementAt(vectorPTB.indexOf(ptb)));
        int numptosig = ptb2.getNumeroDePtoSig();
        PtoSig vptosig[] = new PtoSig[numptosig];
        vptosig = ptb2.getPtoSig();

        for (int i = 0; i < numeroPTB; i++) {
            for (int j = 0; j < numptosig; j++) {
                ((PTB) vectorPTB.elementAt(i)).revisaRestricciones(vptosig[j]);
            }
        }
    }

    /**
     * @roseuid 3787081900CB
     */
    public void almacenaNumeroPTB() {
        numeroPTBnoEstatico = PTBM.getNumeroPTB();
    }

    /**
     * @return int
     * @roseuid 3787081900CC
     */
    public int getnumeroPTBnoEstatico() {
        return numeroPTBnoEstatico;
    }

    public String getTitulo() {
        return nombre;
    }

    /**
     * Este metodo devuelve el comentario del PTBM
     * @roseuid 3788BA0903A7
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Este metodo modifica el titulo y el comentario del PTBM
     * @roseuid 3788BA0903A7
     * @param titulo
     * @param comentario
     */
    public void modificar(String titulo, String comentario) {
        this.nombre = titulo;
        this.comentario = comentario;
    }


    /**
     * @param parametro
     * @return Object
     * @roseuid 3788D4B4020C
     */
    public Object getParametro(int parametro) {
        return null;
    }

    /**
     * @param parametro
     * @roseuid 3788D4B4027A
     */
    public void setParametro(int parametro) {

    }

    public float getFinCoreSeparacion() {
        return finCoreSeparacion;
    }

    public float getFinSoporteSeparacion() {
        return finSoporteSeparacion;
    }

    public void setFinCoreSeparacion(float finCoreSeparacion) {
        this.finCoreSeparacion = finCoreSeparacion;
    }

    public void setFinSoporteSeparacion(float finSoporteSeparacion) {
        this.finSoporteSeparacion = finSoporteSeparacion;
    }

    public float getInicioSoporteSeparacion() {
        return inicioSoporteSeparacion;
    }

    public float getInicioCoreSeparacion() {
        return inicioCoreSeparacion;
    }

    public void setInicioCoreSeparacion(float inicioCoreSeparacion) {
        this.inicioCoreSeparacion = inicioCoreSeparacion;
    }

    public void setInicioSoporteSeparacion(float inicioSoporteSeparacion) {
        this.inicioSoporteSeparacion = inicioSoporteSeparacion;
    }

    /**
     * getSeparacionCrips
     *
     * @return float
     */
    public float getSeparacionCrips() {
        return inicioSoporteSeparacion;
    }

}
