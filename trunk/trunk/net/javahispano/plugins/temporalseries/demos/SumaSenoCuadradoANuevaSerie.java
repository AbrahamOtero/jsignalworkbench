/*
 * NewClass.java
 *
 * Created on 21-oct-2007, 18:22:30
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.javahispano.plugins.temporalseries.demos;

import java.util.List;

import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.plugins.temporalseries.TemporalSeriesAlgorithm;
import net.javahispano.plugins.temporalseries.TemporalSeries;
import java.util.*;

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
public class SumaSenoCuadradoANuevaSerie extends TemporalSeriesAlgorithm {
    public SumaSenoCuadradoANuevaSerie() {
    }

    /**
     * Proporciona el hombre del plugin.
     *
     * @return Nombre del plugin
     */
    public String getName() {
        return "Suma de seno y cuadrado2";
    }

    /**
     * processTemporalSeries
     *
     * @param sm SignalManager
     * @param signals List
     */
    public void processTemporalSeries(SignalManager sm, List<TemporalSeries> signals) {
        Iterator<TemporalSeries> it = signals.iterator();
        if (signals.size()!=2) {
            System.out.println("Error en el número de señales seleccionadas");
            return;
        }
        TemporalSeries seno=it.next();
        TemporalSeries cuadrado=it.next();

        TemporalSeries nuevaSerie = new TemporalSeries("Suma", seno);
        int inicio = Math.min(seno.getMinIndex(), cuadrado.getMinIndex());
        int fin = Math.max(seno.getMaxIndex(), cuadrado.getMaxIndex());
        for (int i = inicio; i < fin; i++) {
            nuevaSerie.setValueAt(i,cuadrado.getValueAt(i)+seno.getValueAt(i));
        }
        sm.addSignal(nuevaSerie);
        nuevaSerie.adjustVisibleRange();
    }
}
