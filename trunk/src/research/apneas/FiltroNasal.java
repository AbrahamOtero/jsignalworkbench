package research.apneas;

import java.util.List;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;

/**
    /**
  * Aplica un filtro pasa banda con las siguientes frecuencias de corte:
  * fc1=0.20;  Hz: 1 respiracion cada 5 segundos
  * fc2=0.45;  Hz: 1 respiracion cada 2 segundos
  * No modifica los datos originales; devuelve un array el mismo tamanho que
  * el original con los datos filtrados.
  *
  * @author Abraham Otero
  * @version 0.5
  */
public class FiltroNasal extends AlgorithmAdapter {
    public FiltroNasal() {
    }

    /**
     * Proporciona el hombre del plugin.
     *
     * @return Nombre del plugin
     * @todo Implement this net.javahispano.jsignalwb.plugins.Plugin method
     */
    public String getName() {
        return "Filtro Nasal";
    }

    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties> signals,
            AlgorithmRunner ar) {
        float[] datos;
        float[] datosFiltrados = null;
        Signal s = null;
        for (SignalIntervalProperties si : signals) {
            s = si.getSignal();
            datos = s.getValues();
            datosFiltrados = filtrarNasal(datos);
        }

        s = new Signal(s.getName() + "'", datosFiltrados,
                       s.getSRate(), s.getStart(), "posibilidad");
        sm.addSignal(s);
        s.adjustVisibleRange();
    }

    /**
     * Aplica un filtro pasa banda con las siguientes frecuencias de corte:
     * fc1=0.20;  Hz: 1 respiracion cada 5 segundos
     * fc2=0.45;  Hz: 1 respiracion cada 2 segundos
     * No modifica los datos originales; devuelve un array el mismo tamanho que
     * el original con los datos filtrados.
     *
     * @param datos float[]
     * @return float[]
     */
    public static float[] filtrarNasal(float[] datos) {
        float[] d = null, d2 = null;
        d = filtro(datos);
        //invertir los datos
        d2 = Utilidades.invertirArray(d);
        d = filtro(d2);
        d2 = Utilidades.invertirArray(d);
        return d2;
    }


    private static float[] filtro(float[] datos) {
        float[] datosFiltrados = new float[datos.length];
        for (int i = 0; i < 7; i++) {
            datosFiltrados[i] = datos[i];
        }
        for (int i = 7; i < datosFiltrados.length - 7; i++) {
            datosFiltrados[i] = (float) (0.0053 * datos[i] - 0.0159 * datos[i -
                                         2] +
                                         0.0159 * datos[i - 4] -
                                         0.0053 * datos[i - 6] +
                                         4.6429 * datosFiltrados[i - 1] -
                                         9.4629 * datosFiltrados[i - 2] +
                                         10.7928 * datosFiltrados[i - 3] -
                                         7.2626 * datosFiltrados[i - 4] +
                                         2.7362 * datosFiltrados[i - 5] -
                                         0.4535 * datosFiltrados[i - 6]);
        }
        for (int i = datosFiltrados.length - 7; i < datosFiltrados.length; i++) {
            datosFiltrados[i] = datos[i];
        }
        return datosFiltrados;
    }

}
