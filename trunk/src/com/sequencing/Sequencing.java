package com.sequencing;

import com.sequencing.GUI.Configure;
import com.uspceu.SimpleAlgorithm;
import java.awt.Color;
import static java.awt.Color.blue;
import java.util.ArrayList;
import java.util.Arrays;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;

public class Sequencing extends SimpleAlgorithm {

    public static int HEIGHT = 140;
    public static Color COLOUR = blue;
    public static Integer[] guanine;
    public static Integer[] adenine;
    public static Integer[] thymine;
    public static Integer[] cytosine;

    @Override
    public void runAlgorithm(SignalManager signalManager, Signal signal, float[] data, float fs) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int maxPos = 0; //POSITION OF THE PEAK
        float maxValue = 0; //HIGHEST VALUE OF THE PEAK
        boolean aux = false; // AUXILLIARY VARIABLE
        float[] dataClean = new float[data.length]; //CLEAN SIGNAL
        float[] derivative = new float[dataClean.length]; //DERIVATIVE OF THE SIGNAL
        guanine = new Integer[data.length]; //ARRAY FOR GUANINE
        adenine = new Integer[data.length]; //ARRAY FOR ADENINE
        thymine = new Integer[data.length]; //ARRAY FOR THYMINE
        cytosine = new Integer[data.length]; //ARRAY FOR CYTOSINE

        Arrays.fill(guanine, 0);
        Arrays.fill(adenine, 0);
        Arrays.fill(thymine, 0);
        Arrays.fill(cytosine, 0);

        // CLEAN THE SIGNAL
        for (int i = 1; i < data.length; i++) {
            dataClean[i] = Math.max(data[i] - HEIGHT, 0);
        }

        // SHOW THE CLEAN SIGNAL
//        Signal cleanSignal = new Signal(signal.getName() + "'",
//                dataClean, signal.getSRate(), signal.getStart(), "");
//        signalManager.addSignal(cleanSignal);
//        cleanSignal.setVisibleRange(0, 10, 400);
        
        // DERIVE THE CLEAN SIGNAL
        for (int i = 1; i < dataClean.length; i++) {
            derivative[i] = dataClean[i] - dataClean[i - 1];
        }

        // SHOW THE DERIVATIVE
//        Signal derivedCleanSignal = new Signal(signal.getName() + "'",
//                derivative, signal.getSRate(), signal.getStart(), "");
//        signalManager.addSignal(derivedCleanSignal);
//        derivedCleanSignal.setVisibleRange(0, 10, 400);
        
        // FIND THE PEAKS .2
        for (int i = 0; i < derivative.length; i++) {
            aux = false;
            while (derivative[i] > 0) {
                maxValue = data[i];
                maxPos = i;
                aux = true;

                i++;
            }

            if (aux == true) {
//                Peak peak = new Peak(maxPos, maxValue);
                DefaultIntervalMark mark = createIntervalMark(maxPos - 2, maxPos + 2, signal);
                mark.setColor(COLOUR);
                signal.addMark(mark);

                if (signal.getName().equalsIgnoreCase("signal0")) {
                    guanine[maxPos] = 1;
                } else if (signal.getName().equalsIgnoreCase("signal1")) {
                    adenine[maxPos] = 1;
                } else if (signal.getName().equalsIgnoreCase("signal2")) {
                    thymine[maxPos] = 1;
                } else if (signal.getName().equalsIgnoreCase("signal3")) {
                    cytosine[maxPos] = 1;
                }

            }
        }

        if (signal.getName().equalsIgnoreCase("signal0")) {
            for (int i = 0; i < derivative.length; i++) {
                System.out.print(guanine[i]);
            }
        }
        if (signal.getName().equalsIgnoreCase("signal1")) {
            for (int i = 0; i < derivative.length; i++) {
                System.out.print(adenine[i]);
            }
        }
        if (signal.getName().equalsIgnoreCase("signal2")) {
            for (int i = 0; i < derivative.length; i++) {
                System.out.print(thymine[i]);
            }
        }
        if (signal.getName().equalsIgnoreCase("signal3")) {
            for (int i = 0; i < derivative.length; i++) {
                System.out.print(cytosine[i]);
            }
        }

    }

    // GETTERS + SETTERS
    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static void setHEIGHT(int HEIGHT) {
        Sequencing.HEIGHT = HEIGHT;
    }

    public static Color getCOLOUR() {
        return COLOUR;
    }

    public static void setCOLOUR(Color COLOUR) {
        Sequencing.COLOUR = COLOUR;
    }

    // CONFIGURATION
    public boolean hasOwnConfigureGUI() {
        return true;
    }

    public void launchConfigureGUI(JSWBManager jswbManager) {
        Configure c = new Configure();
        c.show();
    }

    public String getName() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return "PeakDetector";
    }

}
