//Source file: E:\\Perfil\\Perfil\\Restriccion.java

package es.usc.gsi.trace.importer.Perfil;

import java.io.Serializable;

import es.usc.gsi.trace.importer.Perfil.auxiliares.MyFloat;

/**
 * @author Abraham Otero Quintana
 * @version 0.4
 */
public class Restriccion implements RestriccionInterface, Serializable {
    /**
     * Esto nos hara a nosotros responsables del versionamiento de los ficheros Serializados.
     * Siempre podemos volver a leerlos, pero depende de nosotros que se haga de un modo correcto.
     */

    static final long serialVersionUID = 1237L;

    public static final int SEMANTICA_PENDIENTE = 0,
    SEMANTICA_PERSISTENCIA_EN_PENDIENTE =
            1, SEMANTICA_RECTA_BORROSA = 2, SEMANTICA_TUBO_BORROSO = 4,
    SEMANTICA_SIN_SEMANTICA = 3,
    CUANTIFICADOR_TODO = 1, CUANTIFICADOR_CASI_TODO = 2, CUANTIFICADOR_MUCHO =
            4,
    CUANTIFICADOR_MITAD = 5, CUANTIFICADOR_POCO = 6, UNIDADES_MILISEGUNDOS = 1,
    UNIDADES_SEGUNDOS = 2, UNIDADES_MINUTOS = 3, UNIDADES_HORAS = 4,
    CUANTIFICADOR_MAYOR_PARTE = 3;


    private String D[];
    private String L[];
    private String M[];
    private int ptb;
    private int ptosig;
    private int semantica;
    private boolean es_referencia = false;
    private int cuantificadorSemantica;
    private int unidadesTemporales = 2;
    private float magnitudPrimerPtoSig = 0;
    private float magnitudSegundoPtoSig = 0;
    private int distanciaTemporalEntrePtoSig;
    private boolean relativaAlNivelBasal = false;
    private boolean valorAbsoluto = false;


    /**
     * @param ptb
     * @param ptosig
     * @param D
     * @param L
     * @param M
     * @param sintaxis
     * @roseuid 378708190005
     */
    public Restriccion(int ptb, int ptosig, String[] D, String[] L, String[] M,
                       int sintaxis) {
        this.D = D;
        this.L = L;
        this.M = M;
        this.ptb = ptb;
        this.ptosig = ptosig;
        this.semantica = sintaxis;
    }

    public Restriccion(int ptb, int ptosig, String[] D, String[] L, String[] M,
                       int sintaxis,
                       int cunatificadorSemantica, int unidadesTemporales) {
        this(ptb, ptosig, D, L, M, sintaxis);
        this.cuantificadorSemantica = cunatificadorSemantica;
        this.unidadesTemporales = unidadesTemporales;
    }

    /**
     * @return int
     * @roseuid 37870819000C
     */
    public int getNumeroDePTB() {
        return ptb;
    }

    /**
     * @param i
     * @roseuid 37870819000D
     */
    public void setNumeroDePTB(int i) {
        ptb = i;
    }

    /**
     * @return int
     * @roseuid 37870819000F
     */
    public int getSemantica() {
        return semantica;
    }

    /**
     * @param i
     * @roseuid 378708190010
     */
    public void setSintaxis(int i) {
        semantica = i;
    }

    /**
     * @return int
     * @roseuid 378708190012
     */
    public int getNumeroDePtoSig() {
        return ptosig;
    }

    /**
     * @return String[]
     * @roseuid 378708190013
     */
    public String[] getD() {
        return D;
    }

    /**
     * @return String[]
     * @roseuid 378708190014
     */
    public String[] getL() {
        return L;
    }

    /**
     * @return String[]
     * @roseuid 378708190015
     */
    public String[] getM() {
        return M;
    }

    /**
     * @param D
     * @roseuid 378708190016
     */
    public void setD(String[] D) {
        this.D = D;
    }

    /**
     * En milisengundos!!!!!
     */
    public void setL(String[] L) {
        this.L = L;
    }

    /**
     * @param M
     * @roseuid 37870819001A
     */
    public void setM(String[] M) {
        this.M = M;
    }


    /**
     * Este metodo se emplea para saber si esta restriccion es la que se toma como
     * referencia para que el primer PtoSig deje de ser flotante
     */
    public boolean isReferencia() {
        return es_referencia;
    }

    public void setEsReferencia(boolean b) {
        es_referencia = b;
    }

    /**
     * @return int[]
     * @roseuid 3789C925033F
     */
    public int[] getSintaxisGeneralizada() {
        return null;
    }

    /**
     * @param i
     * @roseuid 3789C9250368
     */
    public void setSintaxisGeneralizada(int[] i) {

    }

    /**
     * @return String[][]
     * @roseuid 3789C92503AE
     */
    public String[][] getDs() {
        return null;
    }

    /**
     * @return String[][]
     * @roseuid 3789C92503CC
     */
    public String[][] getLs() {
        return null;
    }

    /**
     * @return String[][]
     * @roseuid 3789C926000C
     */
    public String[][] getMs() {
        return null;
    }

    /**
     * @param D
     * @roseuid 3789C926002A
     */
    public void setDs(String[][] D) {

    }

    /**
     * @param L
     * @roseuid 3789C926007A
     */
    public void setLs(String[][] L) {

    }

    /**
     * @param M
     * @roseuid 3789C92600CA
     */
    public void setMs(String[][] M) {

    }

    /**
     * @return int[]
     * @roseuid 3789C9260124
     */
    public int[] getNumerosDePTB() {
        return null;
    }

    /**
     * @param v_PTB
     * @return Void
     * @roseuid 3789C9260142
     */
    public Void setNumerosDePTB(int[] v_PTB) {
        return null;
    }

    /**
     * @return int[]
     * @roseuid 3789C9260192
     */
    public int[] getNumerosDePtoSig() {
        return null;
    }

    /**
     * @param v_PtoSig
     * @return Void
     * @roseuid 3789C92601BA
     */
    public Void setNumerosDePtoSig(int[] v_PtoSig) {
        return null;
    }

    /**
     * @return float
     * @roseuid 3789C926021F
     */
    public float resolver() {
        return 0;
    }

    /**
     * @return Void
     * @roseuid 3789C926023D
     */
    public Void resetCauce() {
        return null;
    }

    public int getCuantificadorSemantica() {
        return cuantificadorSemantica;
    }

    public void setCuantificadorSemantica(int cuantificadorSemantica) {
        this.cuantificadorSemantica = cuantificadorSemantica;
    }

    public int getUnidadesTemporales() {
        return unidadesTemporales;
    }

    public void setUnidadesTemporales(int unidadesTemporales) {
        this.unidadesTemporales = unidadesTemporales;
    }

    public float getMagnitudPrimerPtoSig() {
        return magnitudPrimerPtoSig;
    }

    public void setMagnitudPrimerPtoSig(float magnitudPrimerPtoSig) {
        this.magnitudPrimerPtoSig = magnitudPrimerPtoSig;
    }

    public float getMagnitudSegundoPtoSig() {
        return magnitudSegundoPtoSig;
    }

    public void setMagnitudSegundoPtoSig(float magnitudSegundoPtoSig) {
        this.magnitudSegundoPtoSig = magnitudSegundoPtoSig;
    }

    public int getDistanciaTemporalEntrePtoSig() {
        return distanciaTemporalEntrePtoSig;
    }

    /**
     * Se mide en milisegundos
     * @param distanciaTemporalEntrePtoSig
     */
    public void setDistanciaTemporalEntrePtoSig(int
                                                distanciaTemporalEntrePtoSig) {
        this.distanciaTemporalEntrePtoSig = distanciaTemporalEntrePtoSig;
    }

    /**
     * Devuelve una distribuccion de posibilidad respresentado la restriccion temporal en
     * numero de muestras para una fs dada
     * @param fs
     * @return
     */
    public String[] getLParaFs(float fs) {
        String[] Lfs = new String[4];
        for (int i = 0; i < L.length; i++) {
            float tmp = MyFloat.parseFloatSeguro(L[i]);
            tmp = tmp * fs / 1000;
            Lfs[i] = Float.toString(tmp);
        }
        return Lfs;
    }

    /**
     * Devuelve una distribuccion de posibilidad respresentado la restriccion temporal en
     * numero de muestras para una fs dada
     * @param fs
     * @return
     */
    public String[] getMParaFs(float fs) {
        //Si es semantica de tubo la multiplicamos por 1000 y putno, no tiene unidades temporales
        //pero para procesrla de modo estandar se divide por 1000 al cargarla
        if (semantica == Restriccion.SEMANTICA_TUBO_BORROSO) {
            fs = 1;
        }

        String[] Mfs = new String[4];
        for (int i = 0; i < L.length; i++) {
            float tmp = MyFloat.parseFloatSeguro(M[i]);
            tmp = tmp * 1000 / fs;
            Mfs[i] = Float.toString(tmp);
        }
        return Mfs;
    }

    public boolean isRelativaAlNivelBasal() {
        return relativaAlNivelBasal;
    }

    public void setRelativaAlNivelBasal(boolean relativaAlNivelBasal) {
        this.relativaAlNivelBasal = relativaAlNivelBasal;
    }

    public boolean isValorAbsoluto() {
        return valorAbsoluto;
    }

    public void setValorAbsoluto(boolean valorAbsoluto) {
        this.valorAbsoluto = valorAbsoluto;
    }

}
