/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tmp;

import java.util.Collection;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JFrame;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.Plugin;
import net.javahispano.jsignalwb.plugins.calculator.CalculatorGUI.Operation;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import net.javahispano.jsignalwb.utilities.TimePositionConverter;
import net.javahispano.jsignalwb.jsignalmonitor.Resample;

/**
 *
 * @author aq
 */
public class NetWork extends AlgorithmAdapter {


    String firstSignalName, secondSignalName, newSignalName;
    Operation operation = Operation.ADD;
    float[] firstSignalValues, secondSignalValues;


    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {
             Collection<Signal> allSignals = sm.getSignals();
        String[] names = new String[allSignals.size()];
        int i = 0;
        for (Signal signal : allSignals) {
            names[i] = signal.getName();
            System.out.println(names[i]);
        }
    }



    public boolean hasOwnExecutionGUI() {
        return false;
    }

   // public void launchExecutionGUI(JSWBManager jswbManager) {



    //}

    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        if (gUIPositions == GUIPositions.MENU) {
            return true;
        } else if (gUIPositions == GUIPositions.TOOLBAR) {
            return true;
        }
        return false;
    }

    public Icon getIcon() {
        return super.generateImage("NT");
    }

    public String getName() {
        return "Net";
    }

    public String getDescription() {
        return "Net";
    }

    public String getShortDescription() {
        return "Net";
    }
}
