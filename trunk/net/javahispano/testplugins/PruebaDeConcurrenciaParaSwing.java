package net.javahispano.testplugins;

import java.util.List;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;

public class PruebaDeConcurrenciaParaSwing extends AlgorithmAdapter {
    public PruebaDeConcurrenciaParaSwing() {
    }

    /**
     * Proporciona el hombre del plugin.
     *
     * @return Nombre del plugin
     * @todo Implement this net.javahispano.jsignalwb.plugins.Plugin method
     */
    public String getName() {
        return "Concurrencia";
    }

    String[] nombres = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
                       "12", "13", "14", "15", "16", "17", "18", "19", "20",
                       "21", "22", "23", "24", "25", "26", "27", "28", "29",
                       "30"};
    int i, j, k, l, m;
    public void runAlgorithm(final SignalManager sm,
                             List<SignalIntervalProperties> signals) {
        final Signal s = signals.get(0).getSignal();
        final float[] datos = s.getValues();

        for (i = 0; i < nombres.length && (!isExecutionCanceled()); i++) {

            Runnable uiUpdateRunnable = new Runnable() {
                public void run() {
                    Signal nueva = new Signal(nombres[i], datos, s.getSRate(),
                                              s.getStart(),
                                              "magnitud");
                    try {
                        sm.addSignal(nueva);
                    } catch (SignalNotFoundException evt) {
                        System.out.println(evt.getSignalName());
                    }
                }
            };

            javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);

            esperar(100);
        }

        for (j = 0; j < nombres.length && (!isExecutionCanceled()); j++) {

            Runnable uiUpdateRunnable = new Runnable() {
                public void run() {

                    try {
                        sm.setSignalVisibleRange(nombres[j], -5, 6);
                    } catch (SignalNotFoundException evt) {
                        System.out.println(evt.getSignalName());
                    }
                }
            };

            javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);

            esperar(100);
        }

        for (k = 0; k < nombres.length && (!isExecutionCanceled()); k++) {

            Runnable uiUpdateRunnable = new Runnable() {
                public void run() {
                    try {
                        sm.setSignalVisible(nombres[k], false);
                    } catch (SignalNotFoundException evt) {
                        System.out.println(evt.getSignalName());
                    }
                }
            };

            javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);

            esperar(100);
        }
        for (l = 0; l < nombres.length && (!isExecutionCanceled()); l++) {

            Runnable uiUpdateRunnable = new Runnable() {
                public void run() {
                    try {
                        sm.setSignalVisible(nombres[l], true);
                    } catch (SignalNotFoundException evt) {
                        System.out.println(evt.getSignalName());
                    }
                }
            };

            javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);

            esperar(100);
        }

        for (m = 0; m < nombres.length && (!isExecutionCanceled()); m++) {

            Runnable uiUpdateRunnable = new Runnable() {
                public void run() {
                    try {
                        sm.removeSignal(nombres[m]);
                    } catch (SignalNotFoundException evt) {
                        System.out.println(evt.getSignalName());
                    }
                }
            };

            javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);

            esperar(100);
        }

    }

    private void esperar(int tiempo) {
        try {
            Thread.currentThread().sleep(tiempo);
        } catch (InterruptedException ex) {
        }
    }


}
