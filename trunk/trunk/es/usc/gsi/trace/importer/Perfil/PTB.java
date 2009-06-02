//Source file: E:\\Perfil\\Perfil\\PTB.java

package es.usc.gsi.trace.importer.Perfil;

import java.io.Serializable;
import java.util.*;

/**
 * @author Abraham Otero Quintana
 * @version 0.4
 */
public class PTB implements PTBInterface, Serializable {
    /**
     * Esto nos hara a nosotros responsables del versionamiento de los ficheros Serializados.
     * Siempre podemos volver a leerlos, pero depende de nosotros que se haga de un modo correcto.
     */

    static final long serialVersionUID = 1231L;


    private float intInicioSoporteSeparacion = 10,
    intInicioCoreSeparacion = 15, intFinCoreSeparacion = 20,
    intFinSoporteSeparacion = 25;
    private float longitudVentana = 4;


    private String nombre;
    private String parametro;
    private String unidades, unidades_temporales;
    private String comentario;
    private int numPTB;
    private int numeroPtoSig = 0;
    private final String primerD = "0.0";
    private final String primerT2 = "20.0";
    private final String primerT = "0.0";
    private Vector parametros;
    private Vector vectorPtoSig = new Vector();
    private PTBMInterface ptbm;
    private boolean es_flotante = true;
    private HashMap almacen_offset = new HashMap();
    private boolean buscarEnValorAbsoluto = false;

    /**
     * Es el constructor de un PTB
     * @param nombre
     * @param parametro
     * @param unidades
     * @param comentario
     * @param numPTB
     * @roseuid 378708190062
     */
    public PTB(String nombre, String parametro, String unidades,
               String unidades_temporales, String comentario, int numPTB) {
        this.nombre = nombre;
        this.parametro = parametro;
        this.unidades = unidades;
        this.comentario = comentario;
        this.numPTB = numPTB;
        this.unidades_temporales = unidades_temporales;
        numeroPtoSig++;
        numeroPtoSig++;
        vectorPtoSig.addElement(new PtoSig(primerD, primerT, numPTB, true));
        vectorPtoSig.addElement(new PtoSig(primerD, primerT2, numPTB));

    }

    /**
     * @param ptbm
     * @roseuid 378708190068
     */
    public void setPTBM(PTBMInterface ptbm) {
        this.ptbm = ptbm;
    }

    /**
     * @param ptosig
     * @roseuid 37870819006A
     */
    public void anhadePtoSig(PtoSig ptosig) {
        vectorPtoSig.addElement(ptosig);
        numeroPtoSig++;
    }

    /**
     * @return int
     * @roseuid 37870819006C
     */
    public int getNumeroDePtoSig() {
        return vectorPtoSig.size();
    }

    /**
     * @roseuid 37870819006D
     */
    public void DecrementaNumeroDePtoSig() {
        numeroPtoSig--;
    }

    /**
     * @return int
     * @roseuid 37870819006E
     */
    public int getNumeroDePTB() {
        return numPTB;
    }

    /**
     * @param tmp
     * @roseuid 37870819006F
     */
    public void setNumeroDePTB(int tmp, int ptb_borrado) {
        numPTB = tmp;
        for (int i = 0; i < vectorPtoSig.size(); i++) {
            ((PtoSig) vectorPtoSig.elementAt(i)).setNumeroDePTB(tmp,
                    ptb_borrado);
        }

    }

    /**
     * @param tmp
     * @roseuid 37870819006F
     */
    public void setNumeroDePTB(int tmp) {
        numPTB = tmp;
        for (int i = 0; i < vectorPtoSig.size(); i++) {
            ((PtoSig) vectorPtoSig.elementAt(i)).setNumeroDePTB(tmp);
        }

    }

    /**
     * Metodo que solo se debe emplear cunado se carga una plantiila
     * @param tmp
     */
    public void setNumeroDePTBCargaPlantilla(int tmp) {
        numPTB = tmp;
        for (int i = 0; i < vectorPtoSig.size(); i++) {
            ((PtoSig) vectorPtoSig.elementAt(i)).setNumeroDePTBCargaPlantilla(
                    tmp);
        }

    }

    /**
     * @param ptosig
     * @roseuid 378708190071
     */
    public void revisaRestricciones(PtoSig ptosig) {

        for (int i = 0; i < vectorPtoSig.size(); i++) {
            ((PtoSig) vectorPtoSig.elementAt(i)).revisaRestricciones(ptosig);
        }
    }

    /**
     * @return PtoSig[]
     * @roseuid 378708190073
     */
    public PtoSig[] getPtoSig() {
        PtoSig vptosig[] = new PtoSig[vectorPtoSig.size()];
        for (int i = 0; i < vectorPtoSig.size(); i++) {
            vptosig[i] = ((PtoSig) vectorPtoSig.elementAt(i));
        }
        return vptosig;
    }

    /**
     * @param vptosig
     * @param nptosig
     * @roseuid 378708190074
     */
    public void setPtoSig(PtoSig[] vptosig, int nptosig) {
        vectorPtoSig.removeAllElements();
        for (int i = 0; i < nptosig; i++) {
            vectorPtoSig.addElement(vptosig[i]);
        }
    }

    /**
     * @param nombre
     * @param parametro
     * @param unidades
     * @param comentario
     * @param unidades_tiempo
     * @roseuid 378708190077
     */
    public void modificar(String nombre, String parametro, String unidades,
                          String unidades_tiempo, String comentario,
                          float intInicioSoporteSeparacion,
                          float intInicioCoreSeparacion,
                          float intFinCoreSeparacion,
                          float intFinSoporteSeparacion, float longitudVentana,
                          boolean buscarEnValorAbsoluto) {
        this.nombre = nombre;
        this.parametro = parametro;
        this.unidades = unidades;
        this.comentario = comentario;
        this.unidades_temporales = unidades_tiempo;
        this.intFinCoreSeparacion = intFinCoreSeparacion;
        this.intFinSoporteSeparacion = intFinSoporteSeparacion;
        this.intInicioCoreSeparacion = intInicioCoreSeparacion;
        this.intInicioSoporteSeparacion = intInicioSoporteSeparacion;
        this.longitudVentana = longitudVentana;
        this.buscarEnValorAbsoluto = buscarEnValorAbsoluto;
    }

    public void modificar(String nombre, String parametro, String unidades,
                          String unidades_tiempo, String comentario) {
        this.nombre = nombre;
        this.parametro = parametro;
        this.unidades = unidades;
        this.comentario = comentario;
        this.unidades_temporales = unidades_tiempo;

    }


    /**
     * @param ptosig
     * @return Restriccion[]
     * @roseuid 37870819007C
     */
    public Restriccion[] getRestricciones(int ptosig) {
        return (((PtoSig) vectorPtoSig.elementAt(ptosig)).getRestricciones());

    }

    /**
     * @param ptosig
     * @param numeroPtoSig
     * @param seleccion
     * @roseuid 37870819007E
     */
    public void anhadePtoSig(PtoSig ptosig, int numeroPtoSig, int seleccion) {
        if (seleccion == PTBM.ANHADIR) {
            vectorPtoSig.addElement(ptosig);
            numeroPtoSig++;
        } else if (seleccion == PTBM.BORRAR) {
            ptbm.revisaRestricciones(((PtoSig) vectorPtoSig.elementAt(
                    numeroPtoSig)));
            int numero_ptos_inicilaes = vectorPtoSig.size();
            for (int i = numero_ptos_inicilaes - 1; i >= numeroPtoSig; i--) {
                PtoSig pto = (PtoSig) vectorPtoSig.elementAt(i);
                //   pto.setNumeroDePtoSig(pto.getNumeroDePtoSig() -1);
                for (int j = 0; j < numeroPtoSig; j++) {
                    PtoSig pto2 = (PtoSig) vectorPtoSig.elementAt(j);
                    pto2.revisaRestricciones(pto);
                    ptbm.revisaRestricciones(pto);
                }
                vectorPtoSig.remove(i);
                this.numeroPtoSig--;

            }
        } else if (seleccion == PTBM.MODIFICAR) {
            vectorPtoSig.setElementAt(ptosig, numeroPtoSig);

        }
    }

    /**
     * @param ptb
     * @param ptosig
     * @param restriccion
     * @param restriccion_vieja
     * @param numeroPtoSig
     * @param seleccion
     * @roseuid 378708190082
     */
    public void anhadeRestriccion(int ptb, int ptosig, Restriccion restriccion,
                                  Restriccion restriccion_vieja, int seleccion) {

        ((PtoSig) vectorPtoSig.elementAt(ptosig))
                .anhadeRestriccion(ptb, ptosig, restriccion, restriccion_vieja,
                                   /*numeroPtoSig,*/seleccion);
    }

    /**
     * @return java.lang.String
     * @roseuid 378708190089
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return java.lang.String
     * @roseuid 37870819008A
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @return java.lang.String
     * @roseuid 37870819008B
     */
    public String getParametro() {
        return parametro;
    }

    /**
     * @return java.lang.String
     * @roseuid 37870819008C
     */
    public String getUnidades() {
        return unidades;
    }

    /**
     * @return String
     * @roseuid 3788C7170157
     */
    public String getUnidadesTemporales() {
        return unidades_temporales;
    }


    /**
     * Estos metodos nos valdran para averiguar si el PTBM es "flotante" o tiene algun
     * tipo de restriccion con el primer PtoSig.
     */

    public void setEsFlotante(boolean b) {
        this.es_flotante = b;
    }

    public boolean getEsFlotante() {
        return es_flotante;
    }

    /**
     * @param parametro
     * @return Object
     * @roseuid 3788D4B30355
     */
    public Object getParametro(int parametro) {
        return null;
    }

    /**
     * @param parametro
     * @roseuid 3788D4B303C3
     */
    public void setParametro(int parametro) {

    }

    /**Cada vez que el usuario defina una restriccion con el origen se invocara a este
     * metodo y se anhadira en una lista sus offsets correspondientes. Si se elimina, se
     * eliminara de esta lista.
     */
    public void addOffset(String has, float[] off) {
        almacen_offset.put(has, new AuxiliarOffset(has, off));
        this.es_flotante = false;
    }


    public void delOffset(String has) {
        float[] tmp = {0, 0};
        almacen_offset.remove(has);
        if (almacen_offset.isEmpty()) {
            this.es_flotante = true;
        } else {
            this.es_flotante = false;
        }
    }

    public float[] getOffset() {
        if (!almacen_offset.isEmpty()) {
            Collection c = almacen_offset.values();
            Iterator it = c.iterator();
            float[] f = ((AuxiliarOffset) it.next()).getOffset();
            return f;
        } else {
            float[] tmp = {0, 0};
            return tmp;
        }
    }

    public float getIntInicioSoporteSeparacion() {
        return intInicioSoporteSeparacion;
    }

    public void setIntInicioSoporteSeparacion(float intInicioSoporteSeparacion) {
        this.intInicioSoporteSeparacion = intInicioSoporteSeparacion;
    }

    public void setIntInicioCoreSeparacion(float intInicioCoreSeparacion) {
        this.intInicioCoreSeparacion = intInicioCoreSeparacion;
    }

    public void setIntFinSoporteSeparacion(float intFinSoporteSeparacion) {
        this.intFinSoporteSeparacion = intFinSoporteSeparacion;
    }

    public void setIntFinCoreSeparacion(float intFinCoreSeparacion) {
        this.intFinCoreSeparacion = intFinCoreSeparacion;
    }

    public float getIntFinCoreSeparacion() {
        return intFinCoreSeparacion;
    }

    public float getIntFinSoporteSeparacion() {
        return intFinSoporteSeparacion;
    }

    public float getIntInicioCoreSeparacion() {
        return intInicioCoreSeparacion;
    }

    public float getLongitudVentana() {
        return longitudVentana;
    }

    public void setLongitudVentana(float longitudVentana) {
        this.longitudVentana = longitudVentana;
    }

    public float getSeparacionCrisp() {
        return intInicioSoporteSeparacion;
    }

    /**
     * isBuscarEnValorAbsoluto
     *
     * @return boolean
     */
    public boolean isBuscarEnValorAbsoluto() {
        return buscarEnValorAbsoluto;
    }

    public void setBuscarEnValorAbsoluto(boolean buscarEnValorAbsoluto) {
        this.buscarEnValorAbsoluto = buscarEnValorAbsoluto;
    }

    public class AuxiliarOffset implements Serializable {
        public AuxiliarOffset(String has, float[] offset) {
            this.has = has;
            this.offset = offset;
        }

        public int hashCode() {
            return Integer.parseInt(has);
        }

        public float[] getOffset() {
            return offset;
        }

        private String has;
        private float[] offset;
        static final long serialVersionUID = 12311L;
    }


}
