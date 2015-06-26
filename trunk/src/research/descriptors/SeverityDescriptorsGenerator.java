package research.descriptors;

import java.util.List;
import java.util.TreeSet;

import javax.swing.Icon;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.MarkPlugin;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import research.beats.anotaciones.LimitacionAnotacion;
import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.*;
import java.util.Arrays;

public class SeverityDescriptorsGenerator extends AlgorithmAdapter {

    private Signal sato2Signal, fluxSignal;
    private float[] sato2;
    private final int minValueSpO2Aceptable = 20;
    //variables para guardar los datos
    private float apneaPercentage;
    private float hipoapneaPercentage;
    private float apneaAndHipoapneaPercentage;
    private float desaturationPercentage;
    private float meanValueSato2;
    private float basalValueSato2;
    private float areaSato2;
    private float deltaSato2;

    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {
        initialize(sm);
        //Al anhadir las anotaciones a un arbol se ordenan por su instante de inicio
        TreeSet<LimitacionAnotacion> allHipoventilations = getMarksAsTree(fluxSignal);
        TreeSet<LimitacionAnotacion> allDesaturations = getMarksAsTree(sato2Signal);
        TreeSet<LimitacionAnotacion> apneas = new TreeSet<LimitacionAnotacion>();
        TreeSet<LimitacionAnotacion> hipoapneas = new TreeSet<LimitacionAnotacion>();

        separateApneasAndHypopneas(allHipoventilations, apneas, hipoapneas);
        float[] sato2WithoutCeros=calculateStO2WithoutCeros();
        calculateTimes(allDesaturations, apneas, hipoapneas, sato2WithoutCeros);
        this.meanValueSato2 = calculateMeanValueSato2(sato2WithoutCeros);
        this.basalValueSato2 = calculateBasalValueSato2(sato2WithoutCeros);
        this.areaSato2 = calculaArea(sato2WithoutCeros);
        deltaSato2 = this.basalValueSato2 - this.meanValueSato2;

        putDataInClipBoard();
    }

    private void separateApneasAndHypopneas(TreeSet<LimitacionAnotacion> allHipoventilations,
            TreeSet<LimitacionAnotacion> apneas, TreeSet<LimitacionAnotacion> hipoapneas) {
            for (LimitacionAnotacion anotation : allHipoventilations) {
            if (anotation.getTipo() == LimitacionAnotacion.APNEA) {
                apneas.add(anotation);

            } else {
                hipoapneas.add(anotation);
            }
        }
        assert (hipoapneas.size() + apneas.size() == allHipoventilations.size());
    }


    private float[] calculateStO2WithoutCeros() {
        float[] sato2WithoutCeros = null;
        int start =0;
        for (start = 0; start < sato2.length; start++) {
            if (sato2[start]!=0) {
                break;
            }
        }
        int end =0;
        for (end = sato2.length-1; end >0 ; end--) {
            if (sato2[end]!=0) {
                break;
            }
        }
        sato2WithoutCeros = new float[end-start];
        for (int i = start; i < end; i++) {
            sato2WithoutCeros[i-start] = sato2[i];
        }
        return sato2WithoutCeros;
    }

    private void calculateTimes(TreeSet<LimitacionAnotacion> allDesaturations, TreeSet<LimitacionAnotacion> apneas,
            TreeSet<LimitacionAnotacion> hipoapneas, float[] sato2WithoutCeros) {
        int timeApneas=calculaTiempoApneasInSeconds(apneas);
        int timeHipoapneas=calculaTiempoApneasInSeconds(hipoapneas);
        int timeDesaturations=calculaTiempoApneasInSeconds(allDesaturations);
        int timeApneasHipoapneas=timeApneas+timeHipoapneas;
        float totalTime= sato2WithoutCeros.length/sato2Signal.getSRate();
        apneaPercentage =100*timeApneas/totalTime;
        hipoapneaPercentage =100*timeHipoapneas/totalTime;
        desaturationPercentage =100*timeDesaturations/totalTime;
        apneaAndHipoapneaPercentage =100*timeApneasHipoapneas/totalTime;
    }

    private int calculaTiempoApneasInSeconds(TreeSet<LimitacionAnotacion> apneas) {
        long tiempoTotal =0;
        for (LimitacionAnotacion anotation : apneas){
            tiempoTotal+=anotation.getEndTime()- anotation.getMarkTime();
        }
        return (int)(tiempoTotal/1000);
    }

    private float calculateMeanValueSato2(float[] sato2WithoutCeros) {
        float mean=0;
        int counter=0;
        for (int i = 0; i < sato2WithoutCeros.length; i++) {
            if (sato2WithoutCeros [i] > minValueSpO2Aceptable && sato2WithoutCeros [i] < 101) {
                mean += sato2WithoutCeros [i];
                counter++;
            }
        }
        return mean/counter;
    }

    private float calculateBasalValueSato2(float[] sato2WithoutCeros) {
        float[] copia =Arrays.copyOf(sato2WithoutCeros,sato2WithoutCeros.length);
        Arrays.sort(copia);
        int start = copia.length*90/100;
        int ends = copia.length*95/100;
        float media=0;
        int contador=0;
        for (int i = start; i < ends; i++) {
            if (copia [i]>minValueSpO2Aceptable && copia [i]<101) {
                media += copia [i];
                contador++;
            }
        }
        return media/contador;
    }

  private float calculaArea(float[] sato2WithoutCeros) {
        float area=0;
        int contador=0;
        for (int i = 0; i < sato2WithoutCeros.length; i++) {
            float delta = basalValueSato2-sato2WithoutCeros [i];
            if (sato2WithoutCeros [i]>20&&delta>=0) {
                area += delta;
                contador++;
            }
        }
        return sato2Signal.getSRate()*3600*area/contador;
    }


    private void putDataInClipBoard() throws HeadlessException {
        String data = generateStringWithData();
        System.out.println(data);
        StringSelection stringSelection = new StringSelection(data);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, new ClipboardOwner() {
            public void lostOwnership(Clipboard aClipboard,
                                      Transferable aContents) {

            }
        });
    }

    private String generateStringWithData() {
        String data = apneaPercentage + "," + hipoapneaPercentage + "," +
                      apneaAndHipoapneaPercentage + "," +
                      desaturationPercentage + "," + meanValueSato2 + "," +
                      basalValueSato2 + "," + deltaSato2 + "," + areaSato2   +",";
        return data;
    }


    private void initialize(SignalManager sm) {
        sato2Signal = sm.getSignal("Sat02");
        fluxSignal = sm.getSignal("Flujo");
        sato2 = sato2Signal.getValues();
    }


     private TreeSet<LimitacionAnotacion> getMarksAsTree(Signal signal) {
         List<MarkPlugin> listMarkPlugins = signal.getAllMarks();
         TreeSet<LimitacionAnotacion> limTree = new TreeSet<LimitacionAnotacion>();
         for (Object elem : listMarkPlugins) {
             limTree.add((LimitacionAnotacion) elem);
         }
         assert (listMarkPlugins.size() == limTree.size());
         return limTree;
     }

    public boolean hasOwnExecutionGUI() {
        return true;
    }

    public void launchExecutionGUI(JSWBManager jswbManager) {
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
        return super.generateImage("PS");
    }

    public String getName() {
        return "Caracterizar la gravedad del paciente";
    }

    public String getDescription() {
        return getName();
    }

    public String getShortDescription() {
        return getName();
    }
}
