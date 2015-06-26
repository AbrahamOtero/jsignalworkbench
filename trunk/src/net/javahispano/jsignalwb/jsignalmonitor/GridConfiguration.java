/*
 * GridConfiguration.java
 *
 * Created on 23 de marzo de 2007, 20:42
 *
 */

package net.javahispano.jsignalwb.jsignalmonitor;

/**
 * Esta clase contiene informacion que puede resultar util para dibujar un grid sobre un canal como el valor en magnitud
 * minimo y maximo que se estan pintando en el canal, la frecuencia de muestreo que se emplea en visualizacion, etc.
 * Se trata de una clase inmutable.
 */
public class GridConfiguration {
    /*     *  scrollValue --> Indica el valor del primer punto que ha de
     *                  pintarse en el grid
     *  vZoom--> valor del zoom vertical actualmente (1 para tamanho actual)
     *  frec--> valor del zoom horizontal actualmente (1 para tamanho actual)
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
     * Devuelve el zoom que se esta aplicando en el eje horizontal.
     *
     * @return float
     */
    public float getVZoom() {
        return vZoom;
    }

    /**
     * Devuelve la frecuencia de muestreo que se esta empleando para visualizar en pantalla la senhal. No tiene porque
     * coincidir con la frecuencia de muestreo real de la senhal.
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
     * Devuelve el valor de magnitud con el que se corresponde el pixel mas bajo que se puede dibujar sobre el canal. Es
     * el valor minimo que se puede representar sobre ese canal para la configuracion de visualizacion actual.
     *
     * @return float
     */
    public float getMinValue() {
        return abscissaValue;
    }

    /**
     * Devuelve el valor de magnitud con el que se corresponde el pixel mas alto que se puede dibujar sobre el canal. Es
     * el valor maximo que se puede representar sobre ese canal para la configuracion de visualizacion actual.
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
