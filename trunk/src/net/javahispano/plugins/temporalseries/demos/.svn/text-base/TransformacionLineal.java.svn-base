/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.javahispano.plugins.temporalseries.demos;

import java.util.List;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.plugins.temporalseries.TemporalSeries;
import net.javahispano.plugins.temporalseries.TemporalSeriesAlgorithm;

/**
 *
 * @author b
 */
public class TransformacionLineal extends TemporalSeriesAlgorithm {

    private int b;
    private float a;
    @Override
    public void processTemporalSeries(SignalManager sm, List<TemporalSeries> signals) {
        if (signals.size() != 1) {
            System.out.println(" Error en el numero de senhales seleccionadas");
            return;
        }
        TemporalSeries senal = signals.get(0);
        float[] datos = senal.getValues();
        for (int i = 0; i < datos.length; i++) {
            datos[i] = a * datos[i] + b;
        }
    }

    public String getName() {
        return "Transformacion Lineal";
    }

    public void launchConfigureGUI(JSWBManager jswbManager) {
        TransformacionLinealGUI gui = new TransformacionLinealGUI(null, true);
        gui.setVisible(true);
        a = gui.getA();
        b = gui.getB();
    }
}
