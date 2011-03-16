package tmp;

import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;
import java.util.List;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import javax.swing.Icon;
import java.awt.Color;
import net.javahispano.jsignalwb.plugins.Plugin;

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
public class FillInterval extends AlgorithmAdapter {
    public String getName() {
        return "Interpolar en hueco";
    }

    public void runAlgorithm(SignalManager sm,
                             List<SignalIntervalProperties> signals,
            AlgorithmRunner ar) {
        SignalIntervalProperties i = signals.get(0);
        Signal signal = i.getSignal();
        float[] datos = signal.getValues();
        int inicio = i.getFirstArrayPosition();
        int fin =  i.getLastArrayPosition();

        int ventana = (int) (120* signal.getSRate());
        float valorMedioAntes = calcularValorMedio2MinAntes(datos, inicio, ventana);
        float valorMedioDespues = calcularValorMedio2MinDespues(datos, fin, ventana);
        float incremento = (valorMedioDespues- valorMedioAntes)/(fin-inicio);

        for (int j = inicio; j < fin; j++) {
            datos[j] = valorMedioAntes + (j-inicio)*incremento;
        }
    }

    private float calcularValorMedio2MinDespues(float[] datos, int fin, int ventana) {
        float media=0;
        for (int j = fin; j <  fin+ventana; j++) {
                   media +=datos [j] ;
               }
               media /=ventana;
        return media;
    }

    private float calcularValorMedio2MinAntes(float[] datos, int inicio, int ventana) {
        float media=0;
        for (int j = inicio-ventana; j < inicio; j++) {
            media +=datos [j] ;
        }
        media /=ventana;
        return media;
    }


    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        if (gUIPositions == GUIPositions.MENU) {
            return true;
        } else if (gUIPositions == GUIPositions.TOOLBAR) {
            return true;
        }
        return false;
    }

    public Icon getIcon() {
        return super.generateImageSimple("F", Color.blue);
    }

}
