package demo;

import java.util.List;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.temporalseries.TemporalSeries;
import net.javahispano.jsignalwb.temporalseries.TemporalSeriesAlgorithm;

/**
 *
 * @author b
 */
public class ValorAbsoluto2 extends TemporalSeriesAlgorithm{

    public void processTemporalSeries(SignalManager sm, List<TemporalSeries> signals) {
        if (signals.size()!=1) {
            System.out.println(" Error en el numero de señales seleccionadas");
            return;
        }
        TemporalSeries senal = signals.get(0);
        float[]datos= senal.getValues();
        for (int i = 0; i < datos.length; i++) {
            datos[i]= Math.abs(datos[i]);
        }


    }

    public String getName() {
       return "Valor absoluto";
    }

}
