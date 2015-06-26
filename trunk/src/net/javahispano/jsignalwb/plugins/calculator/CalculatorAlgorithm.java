package net.javahispano.jsignalwb.plugins.calculator;

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
public class CalculatorAlgorithm extends AlgorithmAdapter {


    String firstSignalName, secondSignalName, newSignalName;
    Operation operation = Operation.ADD;
    float[] firstSignalValues, secondSignalValues;


    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {
        Signal firstSignal = sm.getSignal(this.firstSignalName);
        Signal secondSignal = sm.getSignal(this.secondSignalName);

        long start = generateArrayCorrectingBegAndReturnNewBeg(firstSignal, secondSignal);
        float newFS = correctFSAndReturnNewFS(firstSignal, secondSignal);

        float[] newValues = generateNewValues();
        Signal newSignal = new Signal(newSignalName, newValues, newFS , start, "");
        newSignal.adjustVisibleRange();
        sm.addSignal(newSignal);
    }

    private long generateArrayCorrectingBegAndReturnNewBeg(Signal firstSignal, Signal secondSignal) {
        if (firstSignal.getStart() < secondSignal.getStart()) {
            secondSignalValues = secondSignal.getValues();
            firstSignalValues = adjustSignalBeginingsByTrunkingTheEarlyOne(firstSignal, secondSignal);
            return secondSignal.getStart();
        } else if (firstSignal.getStart() > secondSignal.getStart()) {
            firstSignalValues = firstSignal.getValues();
            secondSignalValues = adjustSignalBeginingsByTrunkingTheEarlyOne(secondSignal, firstSignal);
            return firstSignal.getStart();
        } else {
            firstSignalValues = firstSignal.getValues();
            secondSignalValues = secondSignal.getValues();
             return firstSignal.getStart();
        }
    }

    private float[] adjustSignalBeginingsByTrunkingTheEarlyOne(Signal earlySignal, Signal laterSignal) {
        int startOfLatestSignal = TimePositionConverter.timeToPosition(laterSignal.getStart(), earlySignal);
        float[] values = earlySignal.getValues();
        float data[] = new float[values.length - startOfLatestSignal];
        for (int j = 0; j < data.length; j++) {
            data[j] = values[startOfLatestSignal + j];
        }
        return data;
    }


    private float[] generateNewValues() {
        int fin = Math.min(firstSignalValues.length, secondSignalValues.length);

        float newValues[] = new float[fin];
        for (int i = 0; i < fin; i++) {
            switch (this.operation) {
            case ADD:
                newValues[i] = firstSignalValues[i] + secondSignalValues[i];
                break;
            case SUBSTRACT:
                newValues[i] = firstSignalValues[i] - secondSignalValues[i];
                break;
            case MULTIPLY:
                newValues[i] = firstSignalValues[i] * secondSignalValues[i];
                break;
            default:
                newValues[i] = firstSignalValues[i] / secondSignalValues[i];
                if (newValues[i] >10000) {
                    newValues[i]=10000;
                }
            }
        }
        return newValues;
    }

    private float correctFSAndReturnNewFS(Signal firstSignal, Signal secondSignal) {
        if (firstSignal.getSRate() < secondSignal.getSRate()) {
            secondSignalValues = Resample.resampleFs(secondSignalValues, secondSignal.getSRate(), firstSignal.getSRate());
            return firstSignal.getSRate();
        } else if (firstSignal.getSRate() > secondSignal.getSRate()) {
            firstSignalValues = Resample.resampleFs(firstSignalValues, firstSignal.getSRate(), secondSignal.getSRate());
            return secondSignal.getSRate();
        }
         return firstSignal.getSRate();
    }


    public boolean hasOwnExecutionGUI() {
        return true;
    }

    public void launchExecutionGUI(JSWBManager jswbManager) {
        Collection<Signal> allSignals = jswbManager.getSignalManager().getSignals();
        String[] names = new String[allSignals.size()];
        int i = 0;
        for (Signal signal : allSignals) {
            names[i] = signal.getName();
            i++;
        }
        CalculatorGUI calculatorGUI = new CalculatorGUI((JFrame) jswbManager.getParentWindow(),
                "Calculadora de señales",
                true, names);
        calculatorGUI.setVisible(true);
        if (calculatorGUI.cancelar) {
            return;
        }
        this.firstSignalName = calculatorGUI.firstSignalName;
        this.secondSignalName = calculatorGUI.secondSignalName;
        this.newSignalName = calculatorGUI.newSignalName;
        this.operation = calculatorGUI.operation;
        this.runAlgorithm(jswbManager.getSignalManager(), null, null);

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
        return new javax.swing.ImageIcon(getClass().getResource("calc.gif"));
    }

    public String getName() {
        return "Calculadora de parametros";
    }

    public String getDescription() {
        return "Calculadora de parametros";
    }

    public String getShortDescription() {
        return "Calculadora de parametros";
    }
}
