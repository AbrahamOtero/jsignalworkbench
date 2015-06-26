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
import javax.swing.JPanel;
import javax.swing.JDialog;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.defaults.DefaultAlgorithmConfiguration;

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

    @Override
    public void runAlgorithm(SignalManager sm,
                             List<SignalIntervalProperties> signals,
            AlgorithmRunner ar) {
        SignalIntervalProperties i = signals.get(0);
        Signal signal = i.getSignal();
        float[] datos = signal.getValues();
        int inicio = i.getFirstArrayPosition();
        int fin =  i.getLastArrayPosition();
    for (int j = inicio; j < fin; j++) {
            datos[j] = datos[j]/3;
        }
    
    /*
        int ventana = (int) (5* signal.getSRate());
        ventana = Math.max(ventana,1);
        float valorMedioAntes = calcularValorMedio2MinAntes(datos, inicio, ventana);
        float valorMedioDespues = calcularValorMedio2MinDespues(datos, fin, ventana);
        float incremento = (valorMedioDespues- valorMedioAntes)/(fin-inicio);

        for (int j = inicio; j < fin; j++) {
            datos[j] = valorMedioAntes + (j-inicio)*incremento;
        }*/
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



    public void launchExecutionGUI(JSWBManager jswbManager) {
        JDialog conf = new JDialog(jswbManager.getParentWindow(), "Execution GUI");

        conf.setModal(true);
        DefaultAlgorithmConfiguration jPane = new DefaultAlgorithmConfiguration(this,
                jswbManager, conf);
        jPane.selectIntervals(1);
        jPane.jButton3ActionPerformed(null);
        /*conf.getContentPane().add(jPane);
        conf.setSize(jPane.getPreferredSize());
        conf.setResizable(false);
        conf.setLocationRelativeTo(conf.getParent());
        conf.setVisible(true);*/
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
