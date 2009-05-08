/*
 * GridConfiguration.java
 *
 * Created on 23 de marzo de 2007, 20:42
 *
 */

package net.javahispano.jsignalwb.jsignalmonitor;

/**
 * Esta clase contiene informaci�n que puede resultar �til para dibujar un grid sobre un canal como el valor en magnitud
 * m�nimo y m�ximo que se est�n pintando en el canal, la frecuencia de muestreo que se emplea en visualizaci�n, etc.
 * Se trata de una clase inmutable.
 */
public class GridConfiguration {
    /*     *  scrollValue --> Indica el valor del primer punto que ha de
     *                  pintarse en el grid
     *  vZoom--> valor del zoom vertical actualmente (1 para tama�o actual)
     *  frec--> valor del zoom horizontal actualmente (1 para tama�o actual)
     **/
//    private float minValue;
    private float maxValue;
    private int abscissaPosition;
    private float vZoom;
    private float frec;
    private float abscissaValue;

    /** Creates a new instance of GridConfiguration */
    GridConfiguration(float maxValue, float abscissaValue, int abscissaPosition,
                             float vZoom, float hZoom) {
        this.frec = hZoom;
        this.vZoom = vZoom;
        this.abscissaPosition = abscissaPosition;
        this.abscissaValue = abscissaValue;
//        this.minValue=minValue;
        this.maxValue = maxValue;
    }


    /**
     * Devuelve el zoom que se est� aplicando en el eje horizontal.
     *
     * @return float
     */
    public float getVZoom() {
        return vZoom;
    }

    /**
     * Devuelve la frecuencia de muestreo que se est� empleando para visualizar en pantalla la se�al. No tiene porque
     * coincidir con la frecuencia de muestreo real de la se�al.
     *
     * @return float
     */
    public float getFrec() {
        return frec;
    }

    public int getAbscissaPosition() {
        return abscissaPosition;
    }

    public float getAbscissaValue() {
        return abscissaValue;
    }

    /**
     * Devuelve el valor de magnitud con el que se corresponde el p�xel m�s bajo que se puede dibujar sobre el canal. Es
     * el valor m�nimo que se puede representar sobre ese canal para la configuraci�n de visualizaci�n actual.
     *
     * @return float
     */
    public float getMinValue() {
        return abscissaValue;
    }

    /**
     * Devuelve el valor de magnitud con el que se corresponde el p�xel m�s alto que se puede dibujar sobre el canal. Es
     * el valor m�ximo que se puede representar sobre ese canal para la configuraci�n de visualizaci�n actual.
     *
     * @return float
     */
    public float getMaxValue() {
        return maxValue;
    }

    public boolean equals(Object obj) {
        if (obj instanceof GridConfiguration) {
            GridConfiguration temp = (GridConfiguration) obj;
            if (frec == temp.getFrec() &&
                vZoom == temp.getVZoom() &&
                abscissaPosition == temp.getAbscissaPosition() &&
                abscissaValue == temp.getAbscissaValue() &&
                maxValue == temp.getMaxValue()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public String toStrig() {
        return "maxValue_ " + maxValue + " abscissaPosition_ " + abscissaValue + " vZoom " + vZoom +
                " frec " + frec + " abscissaValue " + abscissaValue;
    }

}
