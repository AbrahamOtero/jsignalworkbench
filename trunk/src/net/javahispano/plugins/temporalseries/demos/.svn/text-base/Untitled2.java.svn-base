package net.javahispano.plugins.temporalseries.demos;

import java.util.Iterator;
import java.util.List;

import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.plugins.temporalseries.TemporalSeries;
import net.javahispano.plugins.temporalseries.TemporalSeriesAlgorithm;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Abraham Otero
 * @version 0.5
 */
public class Untitled2 extends TemporalSeriesAlgorithm {
    public Untitled2() {
    }

    /**
     * Proporciona el hombre del plugin.
     *
     * @return Nombre del plugin
     * @todo Implement this net.javahispano.jsignalwb.plugins.Plugin method
     */
    public String getName() {
        return "aaaaaaaa";
    }

    /**
     * processTemporalSeries
     *
     * @param sm SignalManager
     * @param signals List
     * @todo Implement this
     *   net.javahispano.jsignalwb.temporalseries.TemporalSeriesAlgorithm
     *   method
     */
    public void processTemporalSeries(SignalManager sm, List signals) {
        Iterator<TemporalSeries> it = signals.iterator();
        if (signals.size() != 1) {
            System.out.println("Error en el numero de senhales seleccionadas");
            return;
        }
        TemporalSeries seno = it.next();
        seno.setValueAt(15, 45);
        seno.setValueAt( -25, 45);
        int max = seno.getMaxIndex();
        int min = seno.getMinIndex();
        for (int i = min; i < max; i++) {
            seno.setValueAt(i, Math.abs(seno.getValueAt(i)));
        }

        seno.setMinIndex( -100);
    }
}
