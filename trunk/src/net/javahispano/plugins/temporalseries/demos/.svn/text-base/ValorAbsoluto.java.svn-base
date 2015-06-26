/*
 * ValorAbsoluto.java
 *
 * Created on 21-oct-2007, 15:50:34
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.javahispano.plugins.temporalseries.demos;

import java.util.List;

import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.plugins.temporalseries.TemporalSeries;
import net.javahispano.plugins.temporalseries.TemporalSeriesAlgorithm;

/**
 *
 * @author b
 */
public class ValorAbsoluto extends TemporalSeriesAlgorithm {

    public void processTemporalSeries(SignalManager sm, List<TemporalSeries> signals) {
        if (signals.size() != 1) {
            System.out.println(" Error en el numero de senhales seleccionadas");
            return;
        }
        TemporalSeries senal = signals.get(0);
        for (int i = senal.getMinIndex(); i < senal.getMaxIndex(); i++) {
            senal.setValueAt(i, Math.abs(senal.getValueAt(i)));
        }

    }

    public String getName() {
        return "Valor absoluto";
    }

}
