//Source file: E:\\Perfil\\Perfil\\PtoSig.java

package es.usc.gsi.trace.importer.Perfil;

import java.io.Serializable;
import java.util.Vector;

/**
 * @author Abraham Otero Quintana
 * @version 0.4
 */
public class PtoSig implements PtoSigInterface, Serializable {
    /**
     * Esto nos hara a nosotros responsables del versionamiento de los ficheros Serializados.
     * Siempre podemos volver a leerlos, pero depende de nosotros que se haga de un modo correcto.
     */

    static final long serialVersionUID = 1235L;

    private String D;
    private String L;
    private int numeroRestricciones;
    private int ptb;
    private int numeroDePtoSig = 0;
    public RestriccionInterface theRestriccionInterface[];
    private Vector vectorRestricciones = new Vector();
    private boolean es_flotante = true;


    /**
     * @param V
     * @param T
     * @param ptb
     * @roseuid 37870819002E
     */
    PtoSig(String V, String T, int ptb) {
        D = V;
        L = T;

        numeroDePtoSig = 0;
        this.ptb = ptb;

    }

    /**
     * Siempre se usa para el primer PtoSIg
     * @param V
     * @param T
     * @param ptb
     * @param es_flotante
     */

    PtoSig(String V, String T, int ptb, boolean es_flotante) {
        D = V;
        L = T;

        numeroDePtoSig = 1;
        this.ptb = ptb;
        this.es_flotante = es_flotante;
    }

    /**
     * @param restriccion
     * @param ptb
     * @param ptosig
     * @roseuid 37870819002A
     */
    public PtoSig(Restriccion restriccion, int ptb, int ptosig) {
        this.ptb = ptb;
        this.numeroDePtoSig = ptosig;
        vectorRestricciones.addElement(restriccion);
        numeroRestricciones++;
    }

    /**
     * @return java.lang.String
     * @roseuid 378708190032
     */
    public String getL() {
        return L;
    }

    /**
     * @return java.lang.String
     * @roseuid 378708190033
     */
    public String getD() {
        return D;
    }

    /**
     * @param ptb
     * @param ptosig
     * @param restriccion
     * @param restriccion_vieja
     * @param numeroPtoSig
     * @param seleccion
     * @roseuid 378708190034
     */
    public void anhadeRestriccion(int ptb, int ptosig, Restriccion restriccion,
                                  Restriccion restriccion_vieja, int seleccion) {
        if (seleccion == PTBM.ANHADIR) {
            vectorRestricciones.addElement(restriccion);
        } else if (seleccion == PTBM.MODIFICAR) {
            int index = vectorRestricciones.indexOf(restriccion_vieja);
            vectorRestricciones.removeElementAt(index);
            vectorRestricciones.insertElementAt(restriccion, index);
        } else if (seleccion == PTBM.BORRAR) {
            vectorRestricciones.removeElementAt(vectorRestricciones.indexOf(
                    restriccion_vieja));
            DecrementaNumeroRestricciones();
        }
    }

    /**
     * @roseuid 37870819003B
     */
    public void DecrementaNumeroRestricciones() {
        numeroRestricciones--;
    }

    /**
     * @return int
     * @roseuid 37870819003C
     */
    public int getNumeroDeRestricciones() {
        return vectorRestricciones.size();
    }

    /**
     * @return int
     * @roseuid 37870819003D
     */
    public int getNumeroDePtoSig() {
        return numeroDePtoSig;
    }

    /**
     * @param num
     * @roseuid 37870819003E
     */
    public void setNumeroDePtoSig(int num) {
        numeroDePtoSig = num;
    }

    /**
     * @return int
     * @roseuid 378708190040
     */
    public int getNumeroDePTB() {
        return ptb;
    }

    /**
     * @param ptb
     * @roseuid 378708190041
     */
    public void setNumeroDePTB(int ptb, int num_ptb_borrado) {

        for (int i = 0; i < vectorRestricciones.size(); i++) {
            if (((Restriccion) vectorRestricciones.elementAt(i)).getNumeroDePTB() ==
                this.ptb) {
                ((Restriccion) vectorRestricciones.elementAt(i)).setNumeroDePTB(
                        ptb);
            } else if (((Restriccion) vectorRestricciones.elementAt(i)).
                       getNumeroDePTB() > num_ptb_borrado) {
                int nuevo_numero = ((Restriccion) vectorRestricciones.elementAt(
                        i)).getNumeroDePTB() - 1;
                ((Restriccion) vectorRestricciones.elementAt(i)).setNumeroDePTB(
                        nuevo_numero);
            }
            //Nunca deberia ejecutarse esto, pero es necesio . Chapuzas S.A.
            else if (((Restriccion) vectorRestricciones.elementAt(i)).
                     getNumeroDePTB() > num_ptb_borrado) {
                vectorRestricciones.remove(i);
            }
        }
        this.ptb = ptb;
    }

    public void setNumeroDePTB(int ptb) {

        for (int i = 0; i < vectorRestricciones.size(); i++) {
            if (((Restriccion) vectorRestricciones.elementAt(i)).getNumeroDePTB() ==
                ptb) {
                ((Restriccion) vectorRestricciones.elementAt(i)).setNumeroDePTB(
                        ptb);
            }
        }
        this.ptb = ptb;
    }

    /**
     * Metodo que solo se debe emplkear cuando se carga una plantilla
     * @param ptb
     */
    public void setNumeroDePTBCargaPlantilla(int ptb) {

        for (int i = 0; i < vectorRestricciones.size(); i++) {
            ((Restriccion) vectorRestricciones.elementAt(i)).setNumeroDePTB(ptb);
        }
        this.ptb = ptb;
    }

    /**
     * @param ptosig
     * @roseuid 378708190043
     */
    public void revisaRestricciones(PtoSig ptosig) {
        for (int i = 0; i < vectorRestricciones.size(); i++) {
            if (ptosig.getNumeroDePTB() ==
                ((Restriccion) vectorRestricciones.elementAt(i)).getNumeroDePTB()
                &&
                ptosig.getNumeroDePtoSig() ==
                ((Restriccion) vectorRestricciones.elementAt(i)).
                getNumeroDePtoSig()) {
                vectorRestricciones.removeElementAt(i);
            }
        }
    }

    /**
     * @return Restriccion[]
     * @roseuid 378708190045
     */
    public Restriccion[] getRestricciones() {
        Restriccion vrestricciones[] = new Restriccion[vectorRestricciones.size()];
        for (int i = 0; i < vectorRestricciones.size(); i++) {
            vrestricciones[i] = ((Restriccion) vectorRestricciones.elementAt(i));
        }
        return vrestricciones;
    }

    /**
     * @param vrestricciones
     * @param numrestricciones
     * @roseuid 378708190046
     */
    public void setRestricciones(Restriccion[] vrestricciones,
                                 int numrestricciones) {
        vectorRestricciones.removeAllElements();
        for (int i = 0; i < numrestricciones; i++) {
            vectorRestricciones.addElement(vrestricciones[i]);
        }
    }

    /**
     * Estos metodos nos valdran para averiguar si el PTBM es "flotante" o tiene algun
     * tipo de restriccion con el primer PtoSig.
     */

    void setEsFlotante(boolean b) {
        this.es_flotante = b;
    }

    boolean getEsFlotante() {
        return es_flotante;
    }

}
