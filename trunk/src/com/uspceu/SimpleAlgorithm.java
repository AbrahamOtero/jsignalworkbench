package com.uspceu;

import com.uspceu.sergio.DiuresisAcumuladaBiometrix;
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
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Cuadrado nuva señal",
                new SquareNewSignal()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Cuadrado nueva señal y detectar",
                new SquareNewSignalBeatDetect()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Calculo de la diuresis acumulada",
                new DiuresisAcumuladaBiometrix()));



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
        mark.setMarkTime(TimePositionConverter.positionToTime(start - 5, signal));
        mark.setEndTime(TimePositionConverter.positionToTime(end + 5, signal));
        return mark;
    }

    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return true;
    }
}