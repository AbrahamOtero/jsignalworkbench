package net.javahispano.plugins.temporalseries;

import java.util.Collection;
import java.util.Date;

import javax.swing.JOptionPane;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.defaults.AxesGridPlugin;

/**
 * Clase que extiende a {@link Signal} proporcionando ciertos metodos de
 * utilidad para el procesado de series temporales.
 */
public class TemporalSeries extends Signal {
    private static final long startAllSeries = (new Date(2008 - 1900, 1, 1)).getTime();

    private int offset = 0;

    /**
     * Crea una nueva instancia de Signal. sName indica el nombre de la senal mientras que sValues almacena los valores
     * de la senal ordenados para cada instante de tiempo. Todos los demas parametros de la serie (frecuencia de
     * muestreo, origen de tiempo, unidades,enfasis
     *
     * @param sName nombre de la senhal.
     * @param sValues valores de la senhal.
     * @param t serie temporal de la cual se copiara la frecuencia de muestreo, instante de origen y unidades.
     */
    public TemporalSeries(String sName, float[] sValues, TemporalSeries t) {
        this(sName, sValues, t.getSRate(), 0, t.getMagnitude(),
             t.getEmphasisLevel());
    }

    /**
     * Crea una nueva instancia de Signal. sName indica el nombre de la senal mientras que sValues almacena los valores de la senal ordenados para cada
     * instante de tiempo.
     * Todos los demas parametros de la serie (frecuencia de muestreo, origen de tiempo,
     * unidades,enfasis
     *
     * @param sName nombre de la senhal.
     * @param t serie temporal de la cual se copiara la frecuencia de muestreo, instante de origen y unidades.
     */

    public TemporalSeries(String sName, TemporalSeries t) {
        this(sName, new float[10], t.getSRate(), 0, t.getMagnitude(),
             t.getEmphasisLevel());
    }


    /**
     * Crea una nueva instancia de Signal.
     *
     * @param sName nombre de la senhal.
     * @param sValues valores de la senhal.
     * @param emphasis Nivel de enfasis con que se debe representar la senhal.
     *   Debe contener valores entre [0, 100].
     * @param sStart long instante de inicio de la senhal medido en milisegundos desde
     *   00:00:00 01/01/1970. Ver {@link TimePositionConverter}.
     * @param sMagnitude magnitud de la senhal.
     */
    public TemporalSeries(String sName, float[] sValues, float frec,
                          int sStart, String sMagnitude) {
        this(sName, sValues, frec, sStart, sMagnitude, null);
    }

    /**
     * Crea una nueva instancia de Signal.
     *
     * @param sName nombre de la senhal.
     * @param emphasis Nivel de enfasis con que se debe representar la senhal.
     *   Debe contener valores entre [0, 100].
     * @param sStart long instante de inicio de la senhal medido en milisegundos desde
     *   00:00:00 01/01/1970. Ver {@link TimePositionConverter}.
     * @param sMagnitude magnitud de la senhal.
     */
    public TemporalSeries(String sName, float frec,
                          int sStart, String sMagnitude) {
        this(sName, new float[10], frec, sStart, sMagnitude, null);
    }

    /**
     * Crea una nueva instancia de Signal.
     *
     * @param sName nombre de la senhal.
     * @param sValues valores de la senhal.
     * @param emphasis Nivel de enfasis con que se debe representar la senhal.
     *   Debe contener valores entre [0, 100].
     * @param sStart long instante de inicio de la senhal medido en milisegundos desde
     *   00:00:00 01/01/1970. Ver {@link TimePositionConverter}.
     * @param sMagnitude magnitud de la senhal.
     * @param emphasis Nivel de enfasis con que se debe representar la senhal.
     *   Debe contener valores entre [0, 100].
     */
    public TemporalSeries(String sName, float[] sValues, float frec,
                          int sStart, String sMagnitude, short[] emphasis) {
        super(sName, sValues, frec, TemporalSeries.startAllSeries, sMagnitude, emphasis);
        AxesGridPlugin grid = new AxesGridPlugin(this);
        grid.setYAxePosition(TemporalSeries.startAllSeries);
        this.setGrid(grid);
        this.setMinIndex(sStart);
    }

    /**
     * Devuelve el valor de la serie temporal en x[n].
     *
     * @param n int
     * @return float
     */
    public float getValueAt(int n) {
        int posicionCorregida = n - offset;
        if (posicionCorregida < 0 || posicionCorregida >= values.length) {
            return 0;
        } else {
            return values[posicionCorregida];
        }
    }

    /**
     * Permite modificar el valor de la serie temporal en x[n]. Puede ocasionar que el tamanho del array crezca. Si se
     * invoca a este metodo, el menor y el mayor valor de n para el cual la serie esta definida pueden cambiar.
     *
     * @param pos int
     * @param value float
     * @return boolean
     */
    public void setValueAt(int pos, float value) {
        int posicionCorregida = pos - offset;
        if (posicionCorregida < 0 || posicionCorregida >= values.length) {
            if (pos >= offset && posicionCorregida >= values.length) {
                int anadirAntes = Math.abs(pos - offset - values.length + 1);
                float[] d = new float[values.length + anadirAntes];
                for (int i = 0; i < values.length; i++) {
                    d[i] = values[i];
                }
                this.setValues(d);
                posicionCorregida = values.length - 1;
            } else if (pos < offset) {
                int anadirAntes = Math.abs(pos - offset);
                float[] d = new float[anadirAntes + values.length];

                for (int i = anadirAntes; i < d.length; i++) {
                    d[i] = values[i - anadirAntes];
                }
                values = d;
                this.setMinIndex(pos);
                posicionCorregida = 0;
            }
        }
        values[posicionCorregida] = value;
    }

    /**
     * Devuelve el minimo valor de n para el cual la serie temporal esta definida. Para valores menores que este el
     * valor de la serie sera 0.
     *
     * @return int
     */
    public int getMinIndex() {
        return offset;
    }

    /**
     * Devuelve el maximo valor de n para el cual la serie temporal esta definida. Para valores mayores que este el
     * valor de la serie sera 0.
     *
     * @return int
     */
    public int getMaxIndex() {
        return values.length + offset;
    }

    public static void convertSignalsToTemporalSeries(SignalManager sm) throws
            SignalNotFoundException {

        Collection<Signal>
                listaTemporalSeries = sm.getSignals();
        for (Signal signal : listaTemporalSeries) {
            if (!(signal instanceof TemporalSeries)) {
                //intentamos transformarla
                if (signal.getProperty("offset") != null) {
                    convertSignalsToTemporalSeries(sm, signal);
                    continue;
                }
                JOptionPane.showMessageDialog(JSWBManager.
                                              getJSWBManagerInstance().
                                              getParentWindow(),
                                              "Alguna de las senhales del entorno no es una serie temporal",
                                              "Error",
                                              JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

    public static TemporalSeries convertSignalsToTemporalSeries(SignalManager
            sm, Signal signal) throws
            SignalNotFoundException {
        sm.removeSignal(signal.getName());
        TemporalSeries temporalSeries = new TemporalSeries(signal.
                getName(), signal.getValues(), signal.getSRate(),
                0, signal.getMagnitude());
        temporalSeries.setGrid(new AxesGridPlugin(temporalSeries));
        temporalSeries.adjustVisibleRange();
        Object o = signal.getProperty("offset");
        if (o != null) {
            temporalSeries.setMinIndex((Integer) o);
        }
        sm.addSignal(temporalSeries);
        return temporalSeries;
    }

    /**
     * Permite modificar el instante de comienzo de la serie temporal. Desde un punto de vista matematico, desplaza toda
     * la serie temporal de tal modo que el anterior valor minimo para el cual la serie estaba definida pase a ser el
     * "n" que se le pasa como argumento a este metodo.
     *
     * @param offset int
     */
    public void setMinIndex(int n) {
        long start = TemporalSeries.startAllSeries + (long) (n * 1000 / this.getSRate());
        this.setStart(start);
        this.offset = n;
        this.setProperty("offset", n);
    }

    /**
     * setFrecuency
     *
     * @param frecuency frecuencia de la senhal medida en hercios.
     */
    public void setFrecuency(float frecuency) {
        super.setFrecuency(frecuency);
        this.setMinIndex(this.getMinIndex());
    }

    public float getFrecuency() {
        return super.getSRate();
    }
}
