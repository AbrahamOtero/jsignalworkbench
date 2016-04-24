package com.uspceu;

import com.sequencing.Sequencing;
import com.uspceu.sergio.DiuresisAcumuladaBiometrix;
import com.uspceu.sergio.DiuresisHoraAHora;
import com.uspceu.sergio.DiuresisMinutoAMinuto;
import com.uspceu.sergio.ValorAbsolutoHoras;
import com.uspceu.sergio.ValorAbsolutoMinutos;
import com.uspceu.victoria.DeteccionOndaP;
import com.uspceu.victoria.ExportarOndaP;
import com.uspceu.victoria.MarcasP;
import java.util.ArrayList;
import java.util.List;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.debug.DebugPluginInfo;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import net.javahispano.jsignalwb.utilities.TimePositionConverter;

public abstract class SimpleAlgorithm extends AlgorithmAdapter {

    public static void registerSimpleAlgorithms() {
        // DONT REMOVE THIS CODE
        ArrayList<DebugPluginInfo> plugins = new ArrayList<DebugPluginInfo>();

        /* INSERT HERE THE CODE TO ADD A NEW PLUGIN IN DEBUG MODE
         * Use this sintaxis
         * {@code plugins.add(new DebugPluginInfo(pluginType,pluginName,Plugin));}
         */


        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Cuadrado",
                new Square()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Cuadrado nuva se�al",
                new SquareNewSignal()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Cuadrado nueva se�al y detectar",
                new SquareNewSignalBeatDetect()));

        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Calculo de la diuresis acumulada",
                new DiuresisAcumuladaBiometrix()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Calculo de la diuresis minuto a minuto",
                new DiuresisMinutoAMinuto()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Calculo de la diuresis hora a hora",
                new DiuresisHoraAHora()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Calculo del valor absoluto minuto a minuto",
                new ValorAbsolutoMinutos()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Calculo del valor absoluto hora a hora",
                new ValorAbsolutoHoras()));
        /*plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Marcar onda P",
                new MarcasP()));*/
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Deteccion onda P",
                new DeteccionOndaP()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Exportar P ", new ExportarOndaP()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Deteccion Picos",
                new Sequencing() {

        }));
        

        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "EMG",
                new com.uspceu.matesanz.EMGAlgorithm()));







        // DONT REMOVE THIS CODE
        JSWBManager.getPluginManager().registerDebugPlugins(plugins);
    }

    public abstract void runAlgorithm(SignalManager signalManager,
            Signal signal, float datos[], float fs);

    @Override
    public void runAlgorithm(SignalManager signalManager, List<SignalIntervalProperties> signals,
            AlgorithmRunner ar) {
        SignalIntervalProperties signalIntervalProperties = signals.get(0);
        Signal signal = signalIntervalProperties.getSignal();
        float[] datos = signal.getValues();
        this.runAlgorithm(signalManager, signal, datos, signal.getSRate());

    }

    public DefaultIntervalMark createIntervalMark(int start, int end, Signal signal) {
        DefaultIntervalMark mark = new DefaultIntervalMark();
        int delayedStart = start - 5;
        int advancedEnd = end + 5;
        if(delayedStart < 0){
            delayedStart=1;
        }
        if (advancedEnd > signal.getValues().length) {
            advancedEnd =  signal.getValues().length-1;
        }
        mark.setMarkTime(TimePositionConverter.positionToTime(delayedStart, signal));
        mark.setEndTime(TimePositionConverter.positionToTime(advancedEnd, signal));
        return mark;
    }

    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return true;
    }
}