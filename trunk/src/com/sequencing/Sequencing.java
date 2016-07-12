package com.sequencing;

import com.sequencing.GUI.Configure;
import com.uspceu.SimpleAlgorithm;
import java.awt.Color;
import static java.awt.Color.blue;
import java.util.ArrayList;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.plugins.defaults.DefaultInstantAnnotation;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalAnnotation;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;
import net.javahispano.jsignalwb.utilities.TimePositionConverter;

public class Sequencing extends SimpleAlgorithm {

    public static int HEIGHT = 140;
    public static Color COLOUR = blue;
    public static int[] guanine = new int[100000];
    public static int[] adenine = new int[100000];
    public static int[] thymine = new int[100000];
    public static int[] cytosine = new int[100000];

    @Override
    public void runAlgorithm(SignalManager signalManager, Signal signal, float[] data, float fs) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        int sequenceLength = 0; // LENGTH OF THE SEQUENCE
        

        // MARK THE SIGNAL AND OBTAIN THE 0-1 ARRAYS FOR EACH BASE
        markSignal(signalManager, "Signal0");
        markSignal(signalManager, "Signal1");
        markSignal(signalManager, "Signal2");
        markSignal(signalManager, "Signal3");

//        // PRINT THE 0-1 ARRAYS ON THE SCREEN
//        System.out.println("Guanine: ");
//        for (int j = 0; j < guanine.length; j++) {
//            System.out.print(guanine[j]);
//        }
//        System.out.println("Adenine: ");
//        for (int j = 0; j < adenine.length; j++) {
//            System.out.print(adenine[j]);
//        }
//        System.out.println("Thymine: ");
//        for (int j = 0; j < thymine.length; j++) {
//            System.out.print(thymine[j]);
//        }
//        System.out.println("Cytosine: ");
//        for (int j = 0; j < cytosine.length; j++) {
//            System.out.print(cytosine[j]);
//        }

        signalManager.renameSignal("Signal0", "Guanine");
        signalManager.renameSignal("Signal1", "Adenine");
        signalManager.renameSignal("Signal2", "Thymine");
        signalManager.renameSignal("Signal3", "Cytosine");

        int[] orArray1 = new int[guanine.length]; // AUXILLIARY ARRAYS
        int[] orArray2 = new int[adenine.length];
        int[] orArray3 = new int[thymine.length];
        int[] orArray4 = new int[cytosine.length];
        int[] orArray = new int[guanine.length]; //ARRAY THAT HOLDS THE OR OPERATION OF THE PREVIOUS 4

        // FILL THE AUXILLIARY ARRAYS
        for (int j = 0; j < guanine.length; j++) {
            orArray1[j] = guanine[j];
            orArray2[j] = adenine[j];
            orArray3[j] = thymine[j];
            orArray4[j] = cytosine[j];
        }

        // FILL AN ARRAY WITH THE OR OPERATION OF ALL THE BASES
        // THIS orArray WILL BE THE SEQUENCE ARRAY FILLED WITH 0s AND 1s WHERE THERE IS A PEAK
        for (int j = 0; j < orArray.length; j++) {
            orArray[j] |= orArray1[j] |= orArray2[j] |= orArray3[j] |= orArray4[j];
        }

//        System.out.println("OR: ");
//        for (int i = 0; i < orArray.length; i++) {
//            System.out.print(orArray[i]);
//        }
        
        // FIND HOW LONG THE SEQUENCE IS
        for (int j = 0; j < orArray.length; j++) {
            if (orArray[j] == 1) {
                sequenceLength++;
                j += 5;
            }
        }

        // CREATE ARRAYLISTS FOR EACH BASE, AS LONG AS THE SEQUENCE IS
        ArrayList<String> finalGuanine = new ArrayList<String>(sequenceLength);
        ArrayList<String> finalAdenine = new ArrayList<String>(sequenceLength);
        ArrayList<String> finalThymine = new ArrayList<String>(sequenceLength);
        ArrayList<String> finalCytosine = new ArrayList<String>(sequenceLength);

        for (int i = 0; i < sequenceLength; i++) {
            finalGuanine.add("o");
            finalAdenine.add("o");
            finalThymine.add("o");
            finalCytosine.add("o");
        }
        
        int aux = 0;

        // SET A LETTER IN EACH ARRAY WHERE THERE IS A PEAK OF THAT BASE
        for (int i = sequenceLength; i > 0; i--) {
            for (int j = aux; j < orArray.length; j++) {
                if (orArray[j] == 1) {
                    if (guanine[j] == 1) {
                        finalGuanine.set(sequenceLength - i, "G");
                    }
                    if (adenine[j] == 1) {
                        finalAdenine.set(sequenceLength - i, "A");
                    }
                    if (thymine[j] == 1) {
                        finalThymine.set(sequenceLength - i, "T");
                    }
                    if (cytosine[j] == 1) {
                        finalCytosine.set(sequenceLength - i, "C");
                    }
                    aux = j+1;
                    break;
                }
            }
        }

//        // PRINT THE LETTER ARRAYS
//        System.out.println("Guanine: ");
//        for (String finalGuanine1 : finalGuanine) {
//            System.out.print(finalGuanine1);
//        }
//        System.out.println("Adenine: ");
//        for (String finalAdenine1 : finalAdenine) {
//            System.out.print(finalAdenine1);
//        }
//        System.out.println("Thymine: ");
//        for (String finalThymine1 : finalThymine) {
//            System.out.print(finalThymine1);
//        }
//        System.out.println("Cytosine: ");
//        for (String finalCytosine1 : finalCytosine) {
//            System.out.print(finalCytosine1);
//        }
        
        // CREATE AN ARRAY FOR THE FINAL SEQUENCE AND "INITIALIZE" IT
        ArrayList<String> finalSequence = new ArrayList<String>(sequenceLength);
        for (int i = 0; i < sequenceLength; i++) {
            finalSequence.add("o");
        }

        // FILL THE FINAL SEQUENCE ARRAY
        for (int i = 0; i < sequenceLength; i++) {

            String aux2 = null;

            if (finalGuanine.get(i).equalsIgnoreCase("G")) {
                aux2 = "G";
            }
            if (finalAdenine.get(i).equalsIgnoreCase("A")) {
                if (aux2 != null) {
                    aux2 = "N";
                } else {
                    aux2 = "A";
                }
            }
            if (finalThymine.get(i).equalsIgnoreCase("T")) {
                if (aux2 != null) {
                    aux2 = "N";
                } else {
                    aux2 = "T";
                }
            }
            if (finalCytosine.get(i).equalsIgnoreCase("C")) {
                if (aux2 != null) {
                    aux2 = "N";
                } else {
                    aux2 = "C";
                }
            }
            finalSequence.set(i, aux2);
        }

        int k = 0;
        
        // ADD ANNOTATIONS
        for (int i = 0 ; i < orArray.length ; i++) {
            if(orArray[i]==1){
                DefaultIntervalAnnotation defaultIntervalAnnotation = new DefaultIntervalAnnotation();
                defaultIntervalAnnotation.setAnnotationTime(TimePositionConverter.positionToTime(i-5, signal));
                defaultIntervalAnnotation.setEndTime(TimePositionConverter.positionToTime(i+5, signal));
                defaultIntervalAnnotation.setTitle(finalSequence.get(k));
                JSWBManager.getSignalManager().addAnnotation(defaultIntervalAnnotation);
                k++;
            }
        }
        
//        for (int i = 0 ; i < orArray.length ; i++){
//            DefaultInstantAnnotation defaultInstantAnnotation = new DefaultInstantAnnotation();
//            defaultInstantAnnotation.setAnnotationTime(TimePositionConverter.positionToTime(maxPos[i], signal));
//            defaultInstantAnnotation.setTitle(finalSequence.get(i));
//            JSWBManager.getSignalManager().addAnnotation(defaultInstantAnnotation);
//        }
        
//         PRINT THE FINAL SEQUENCE
        System.out.println("Sequence: ");
        for (int j = 0; j < finalSequence.size(); j++) {
            System.out.print(finalSequence.get(j));
        }

    }

    void markSignal(SignalManager signalManager, String name) {

        Signal signal = signalManager.getSignal(name);
        float[] data = signalManager.getSignal(name).getValues();

        int maxPosition = 0; // POSITION OF THE PEAK
        float maxValue = 0; // HIGHEST VALUE OF THE PEAK
        boolean aux = false; // AUXILLIARY VARIABLE
        float[] dataClean = new float[data.length]; // CLEAN SIGNAL
        float[] derivative = new float[dataClean.length]; // DERIVATIVE OF THE SIGNAL

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
        
        // FIND THE PEAKS(.2) + CREATE ARRAYS
        for (int i = 0; i < derivative.length; i++) {
            aux = false;
            while (derivative[i] > 0) {
                maxValue = data[i];
                maxPosition = i;
                aux = true;

                i++;
            }

            if (aux == true) {
//                Peak peak = new Peak(maxPosition, maxValue);
                DefaultIntervalMark mark = createIntervalMark(maxPosition - 2, maxPosition + 2, signal);
                mark.setColor(COLOUR);
                signal.addMark(mark);

                if (signal.getName().equalsIgnoreCase("signal0")) {
                    guanine[maxPosition] = 1;
                } else if (signal.getName().equalsIgnoreCase("signal1")) {
                    adenine[maxPosition] = 1;
                } else if (signal.getName().equalsIgnoreCase("signal2")) {
                    thymine[maxPosition] = 1;
                } else if (signal.getName().equalsIgnoreCase("signal3")) {
                    cytosine[maxPosition] = 1;
                }

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
