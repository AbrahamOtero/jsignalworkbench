package com.sequencing;

import com.sequencing.GUI.Configure;
import com.uspceu.SimpleAlgorithm;
import java.awt.Color;
import static java.awt.Color.blue;
import static java.awt.Color.red;
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
    public static int LENGTH = 100000;
    public static int WIDTH = 4;
    public static int[] guanine;
    public static int[] adenine;
    public static int[] thymine;
    public static int[] cytosine;

    @Override
    public void runAlgorithm(SignalManager signalManager, Signal signal, float[] data, float fs) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        int sequenceLength = 0; // LENGTH OF THE SEQUENCE

        guanine = new int[LENGTH];
        adenine = new int[LENGTH];
        thymine = new int[LENGTH];
        cytosine = new int[LENGTH];

        signalManager.renameSignal("Signal0", "G");
        signalManager.renameSignal("Signal1", "A");
        signalManager.renameSignal("Signal2", "T");
        signalManager.renameSignal("Signal3", "C");

        // MARK THE SIGNAL AND OBTAIN THE 0-1 ARRAYS FOR EACH BASE
        markSignal(signalManager, "G");
        markSignal(signalManager, "A");
        markSignal(signalManager, "T");
        markSignal(signalManager, "C");

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
        int[] orArray1 = new int[LENGTH]; // AUXILLIARY ARRAYS
        int[] orArray2 = new int[LENGTH];
        int[] orArray3 = new int[LENGTH];
        int[] orArray4 = new int[LENGTH];
        int[] orArray = new int[LENGTH]; //ARRAY THAT HOLDS THE OR OPERATION OF THE PREVIOUS 4

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
//                j += 1; // Poniendo esto, es mas corta y no pilla todos los picos
            }
        }

        // CREATE ARRAYLISTS FOR EACH BASE, AS LONG AS THE SEQUENCE IS
        ArrayList<Pico> finalGuanine = new ArrayList<Pico>(sequenceLength);
        ArrayList<Pico> finalAdenine = new ArrayList<Pico>(sequenceLength);
        ArrayList<Pico> finalThymine = new ArrayList<Pico>(sequenceLength);
        ArrayList<Pico> finalCytosine = new ArrayList<Pico>(sequenceLength);

        for (int i = 0; i < sequenceLength + 5000; i++) {
            finalGuanine.add(Pico.DUMMY_PICO);
            finalAdenine.add(Pico.DUMMY_PICO);
            finalThymine.add(Pico.DUMMY_PICO);
            finalCytosine.add(Pico.DUMMY_PICO);
        }

        // CREATE ARRAYS WITH ALL THE VALUES OF THE SIGNAL
        float[] g = signalManager.getSignal("G").getValues();
        float[] a = signalManager.getSignal("A").getValues();
        float[] t = signalManager.getSignal("T").getValues();
        float[] c = signalManager.getSignal("C").getValues();

        int aux = 0;
        // SET A LETTER IN EACH ARRAY WHERE THERE IS A PEAK OF THAT BASE
        for (int i = 0; i < sequenceLength; i++) {
            for (int j = aux; j < orArray.length; j++) {
                if (orArray[j] == 1) {
                    if (guanine[j] == 1) {
                        finalGuanine.set(i, new Pico(j, (int) g[j], 'G'));
                    }
                    if (adenine[j] == 1) {
                        finalAdenine.set(i, new Pico(j, (int) a[j], 'A'));
                    }
                    if (thymine[j] == 1) {
                        finalThymine.set(i, new Pico(j, (int) t[j], 'T'));
                    }
                    if (cytosine[j] == 1) {
                        finalCytosine.set(i, new Pico(j, (int) c[j], 'C'));
                    }
                    aux = j + 1;
                    break;
                }
            }
        }

//        // PRINT THE LETTER ARRAYS
//        System.out.println("Guanine: ");
//        for (Pico finalGuanine1 : finalGuanine) {
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
        ArrayList<Pico> finalSequence = new ArrayList<Pico>(sequenceLength);
        for (int i = 0; i < sequenceLength; i++) {
            finalSequence.add(Pico.DUMMY_PICO);
        }

        // FILL THE FINAL SEQUENCE ARRAY
        for (int i = 0; i < sequenceLength; i++) {

            Pico aux2 = Pico.DUMMY_PICO;

            Pico picoG = finalGuanine.get(i);
            if (picoG != Pico.DUMMY_PICO) {
                if (buscarPicoEnEntornoDe(finalAdenine, i, picoG)) {
                    if (buscarPicoEnEntornoDe(finalCytosine, i, picoG)) {
                        if (buscarPicoEnEntornoDe(finalThymine, i, picoG)) {
                            aux2 = new Pico(picoG.getPosicion(), picoG.getMaximo(), 'N');
                        }
                        if (aux2 == Pico.DUMMY_PICO) {
                            aux2 = new Pico(picoG.getPosicion(), picoG.getMaximo(), 'V');
                        }
                    }
                    if (buscarPicoEnEntornoDe(finalThymine, i, picoG)) {
                        aux2 = new Pico(picoG.getPosicion(), picoG.getMaximo(), 'D');
                    }
                    if (aux2 == Pico.DUMMY_PICO) {
                        aux2 = new Pico(picoG.getPosicion(), picoG.getMaximo(), 'R');
                    }
                }
                if (buscarPicoEnEntornoDe(finalCytosine, i, picoG)) {
                    if (buscarPicoEnEntornoDe(finalThymine, i, picoG)) {
                        aux2 = new Pico(picoG.getPosicion(), picoG.getMaximo(), 'B');
                    }
                    if (aux2 == Pico.DUMMY_PICO) {
                        aux2 = new Pico(picoG.getPosicion(), picoG.getMaximo(), 'S');
                    }
                }
                if (buscarPicoEnEntornoDe(finalThymine, i, picoG)) {
                    aux2 = new Pico(picoG.getPosicion(), picoG.getMaximo(), 'K');
                }
                if (aux2 == Pico.DUMMY_PICO) {
                    aux2 = picoG;
                }
            }

            Pico picoA = finalAdenine.get(i);
            if (picoA != Pico.DUMMY_PICO) {
                if (buscarPicoEnEntornoDe(finalGuanine, i, picoA)) {
                    if (buscarPicoEnEntornoDe(finalCytosine, i, picoA)) {
                        if (buscarPicoEnEntornoDe(finalThymine, i, picoA)) {
                            aux2 = new Pico(picoA.getPosicion(), picoA.getMaximo(), 'N');
                        }
                        if (aux2 == Pico.DUMMY_PICO) {
                            aux2 = new Pico(picoA.getPosicion(), picoA.getMaximo(), 'V');
                        }
                    }
                    if (buscarPicoEnEntornoDe(finalThymine, i, picoA)) {
                        aux2 = new Pico(picoA.getPosicion(), picoA.getMaximo(), 'D');
                    }
                    if (aux2 == Pico.DUMMY_PICO) {
                        aux2 = new Pico(picoA.getPosicion(), picoA.getMaximo(), 'R');
                    }
                }
                if (buscarPicoEnEntornoDe(finalCytosine, i, picoA)) {
                    if (buscarPicoEnEntornoDe(finalThymine, i, picoA)) {
                        aux2 = new Pico(picoA.getPosicion(), picoA.getMaximo(), 'H');
                    }
                    if (aux2 == Pico.DUMMY_PICO) {
                        aux2 = new Pico(picoA.getPosicion(), picoA.getMaximo(), 'M');
                    }
                }
                if (buscarPicoEnEntornoDe(finalThymine, i, picoA)) {
                    aux2 = new Pico(picoA.getPosicion(), picoA.getMaximo(), 'W');
                }
                if (aux2 == Pico.DUMMY_PICO) {
                    aux2 = picoA;
                }
            }

            Pico picoT = finalThymine.get(i);
            if (picoT != Pico.DUMMY_PICO) {
                if (buscarPicoEnEntornoDe(finalGuanine, i, picoT)) {
                    if (buscarPicoEnEntornoDe(finalAdenine, i, picoT)) {
                        if (buscarPicoEnEntornoDe(finalCytosine, i, picoT)) {
                            aux2 = new Pico(picoT.getPosicion(), picoT.getMaximo(), 'N');
                        }
                        if (aux2 == Pico.DUMMY_PICO) {
                            aux2 = new Pico(picoT.getPosicion(), picoT.getMaximo(), 'D');
                        }
                    }
                    if (buscarPicoEnEntornoDe(finalCytosine, i, picoT)) {
                        aux2 = new Pico(picoT.getPosicion(), picoT.getMaximo(), 'B');
                    }
                    if (aux2 == Pico.DUMMY_PICO) {
                        aux2 = new Pico(picoT.getPosicion(), picoT.getMaximo(), 'K');
                    }
                }
                if (buscarPicoEnEntornoDe(finalAdenine, i, picoT)) {
                    if (buscarPicoEnEntornoDe(finalCytosine, i, picoT)) {
                        aux2 = new Pico(picoT.getPosicion(), picoT.getMaximo(), 'H');
                    }
                    if (aux2 == Pico.DUMMY_PICO) {
                        aux2 = new Pico(picoT.getPosicion(), picoT.getMaximo(), 'W');
                    }
                }
                if (buscarPicoEnEntornoDe(finalCytosine, i, picoT)) {
                    aux2 = new Pico(picoT.getPosicion(), picoT.getMaximo(), 'Y');
                }
                if (aux2 == Pico.DUMMY_PICO) {
                    aux2 = picoT;
                }
            }

            Pico picoC = finalCytosine.get(i);
            if (picoC != Pico.DUMMY_PICO) {
                if (buscarPicoEnEntornoDe(finalGuanine, i, picoC)) {
                    if (buscarPicoEnEntornoDe(finalAdenine, i, picoC)) {
                        if (buscarPicoEnEntornoDe(finalThymine, i, picoC)) {
                            aux2 = new Pico(picoC.getPosicion(), picoC.getMaximo(), 'N');
                        }
                        if (aux2 == Pico.DUMMY_PICO) {
                            aux2 = new Pico(picoC.getPosicion(), picoC.getMaximo(), 'V');
                        }
                    }
                    if (buscarPicoEnEntornoDe(finalThymine, i, picoC)) {
                        aux2 = new Pico(picoC.getPosicion(), picoC.getMaximo(), 'B');
                    }
                    if (aux2 == Pico.DUMMY_PICO) {
                        aux2 = new Pico(picoC.getPosicion(), picoC.getMaximo(), 'S');
                    }
                }
                if (buscarPicoEnEntornoDe(finalAdenine, i, picoC)) {
                    if (buscarPicoEnEntornoDe(finalThymine, i, picoC)) {
                        aux2 = new Pico(picoC.getPosicion(), picoC.getMaximo(), 'H');
                    }
                    if (aux2 == Pico.DUMMY_PICO) {
                        aux2 = new Pico(picoC.getPosicion(), picoC.getMaximo(), 'M');
                    }
                }
                if (buscarPicoEnEntornoDe(finalThymine, i, picoC)) {
                    aux2 = new Pico(picoC.getPosicion(), picoC.getMaximo(), 'Y');
                }
                if (aux2 == Pico.DUMMY_PICO) {
                    aux2 = picoC;
                }
            }

            finalSequence.set(i, aux2);
        }

        //         PRINT THE FINAL SEQUENCE
        System.out.println("\nSequence: ");
        for (int j = 0; j < finalSequence.size(); j++) {
            System.out.print(finalSequence.get(j));
        }
        
        int dummies = 0;
        // DELETE DUMMY PEAKS
        for (int i = 0; i < finalSequence.size(); i++){
            if (finalSequence.get(i) == Pico.DUMMY_PICO)
                dummies ++;
        }
        ArrayList<Pico> finalCleanSequence = new ArrayList<Pico>(finalSequence.size()-dummies);
        for (int i = 0; i < finalSequence.size(); i++) {
            if (finalSequence.get(i) != Pico.DUMMY_PICO){
                finalCleanSequence.add(finalSequence.get(i));
            }
        }
        System.out.println("\nClean sequence: ");
        for (int j = 0; j < finalCleanSequence.size(); j++) {
            System.out.print(finalCleanSequence.get(j));
        }
        

        int k = 0;
        // ADD ANNOTATIONS
        for (int i = 0; i < orArray.length; i++) {
            if (orArray[i] == 1) {
                DefaultIntervalAnnotation defaultIntervalAnnotation = new DefaultIntervalAnnotation();
                defaultIntervalAnnotation.setAnnotationTime(TimePositionConverter.positionToTime(i - 3, signal));
                defaultIntervalAnnotation.setEndTime(TimePositionConverter.positionToTime(i + 3, signal));
                defaultIntervalAnnotation.setTitle(finalSequence.get(k).toString());
                if (finalSequence.get(k) != Pico.DUMMY_PICO) {
                    JSWBManager.getSignalManager().addAnnotation(defaultIntervalAnnotation);
                }
                k++;
            }
        }

    }

    private static boolean buscarPicoEnEntornoDe(ArrayList<Pico> listaPicos, int i, Pico pico) {

        if (i - 3 > 0 && i + 4 < LENGTH) {
            for (int j = i; j < i + 3; j++) {
                if (j > 0 && j < listaPicos.size()) {
                    if (listaPicos.get(j) != Pico.DUMMY_PICO) {
                        if (Math.abs(listaPicos.get(j).posicion - pico.posicion) <= 4) {
                            listaPicos.set(j, Pico.DUMMY_PICO);
                            return true;
                        }
                    }
                }
            }
        }

        return false;
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

                if (signal.getName().equalsIgnoreCase("G")) {
                    guanine[maxPosition] = 1;
                } else if (signal.getName().equalsIgnoreCase("A")) {
                    adenine[maxPosition] = 1;
                } else if (signal.getName().equalsIgnoreCase("T")) {
                    thymine[maxPosition] = 1;
                } else if (signal.getName().equalsIgnoreCase("C")) {
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
