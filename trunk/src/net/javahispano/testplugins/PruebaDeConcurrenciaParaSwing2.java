package net.javahispano.testplugins;

import java.util.List;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;

public class PruebaDeConcurrenciaParaSwing2 extends AlgorithmAdapter {
    public PruebaDeConcurrenciaParaSwing2() {
    }

    /**
     * Proporciona el hombre del plugin.
     *
     * @return Nombre del plugin
     * @todo Implement this net.javahispano.jsignalwb.plugins.Plugin method
     */
    public String getName() {
        return "Concurrencia2";
    }

    String[] nombres = {"1", "2", "3", "4*", "5", "6", "7", "8", "9", "10", "11",
                       "12", "13", "14", "15", "16", "17", "18", "19", "20",
                       "21", "22", "23", "24", "25", "26", "27", "28", "29",
                       "30"};
    int i;
    public void runAlgorithm(final SignalManager sm,
                             List<SignalIntervalProperties> signals) {
        final Signal s = signals.get(0).getSignal();
        final float[] datos = s.getValues();

        for (i = 0; i < nombres.length; i++) {

            Signal nueva = new Signal(nombres[i], datos, s.getSRate(),
                                      s.getStart(),
                                      "magnitud");

            sm.addSignal(nueva);

            esperar(100);
        }

        for (i = 0; i < nombres.length; i++) {

            sm.setSignalVisibleRange(nombres[i], -5, 6);

            esperar(100);
        }

        for (i = 0; i < nombres.length; i++) {

            sm.setSignalVisible(nombres[i], false);

            esperar(100);
        }
        for (i = 0; i < nombres.length; i++) {

            sm.setSignalVisible(nombres[i], true);

            esperar(100);
        }

        for (i = 0; i < nombres.length; i++) {

            sm.removeSignal(nombres[i]);

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
