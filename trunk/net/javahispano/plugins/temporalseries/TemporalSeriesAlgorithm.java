package net.javahispano.plugins.temporalseries;


import java.awt.HeadlessException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;

/**
 *
 * @author Roman
 */
public abstract class TemporalSeriesAlgorithm extends AlgorithmAdapter {
    public abstract void processTemporalSeries(SignalManager sm,
                                               List<TemporalSeries> signals);


    public void runAlgorithm(SignalManager sm,
                             List<SignalIntervalProperties> signals) {
        TemporalSeries.convertSignalsToTemporalSeries(sm);
        if (this.hasOwnConfigureGUI()) {
            this.launchConfigureGUI(JSWBManager.getJSWBManagerInstance());
        }

        List<TemporalSeries>
                listaTemporalSeries = convertir(sm, signals);
        if (listaTemporalSeries == null) {
            return;
        }
        this.processTemporalSeries(sm, listaTemporalSeries);
    }


    public boolean hasOwnConfigureGUI() {
        return true;
    }


    public void launchConfigureGUI(JSWBManager jswbManager) {

    }

    private List<TemporalSeries> convertir(SignalManager sm,
                                           List<SignalIntervalProperties>
            signals) throws HeadlessException, SignalNotFoundException {
        List<TemporalSeries>
                listaTemporalSeries = new LinkedList<TemporalSeries>();
        for (SignalIntervalProperties elem : signals) {
            Signal signal = elem.getSignal();
            if (!(signal instanceof TemporalSeries)) {
                //intentamos transformarla
                if (signal.getProperty("offset") != null) {
                    listaTemporalSeries.add(TemporalSeries.convertSignalsToTemporalSeries(sm, signal));
                    //se acaba el trabajo
                    continue;
                }
                JOptionPane.showMessageDialog(JSWBManager.
                                              getJSWBManagerInstance().
                                              getParentWindow(),
                                              "Alguna de las senhales seleccionadas no es una serie temporal",
                                              "Error",
                                              JOptionPane.ERROR_MESSAGE);
                return null;
            }
            listaTemporalSeries.add((TemporalSeries) signal);
        }
        return listaTemporalSeries;
    }

}
