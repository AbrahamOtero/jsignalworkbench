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
public class SeverityDescriptorsGenerator extends AlgorithmAdapter {

    private Signal sato2Signal, fluxSignal;
    private float[] sato2;
    //Frecuencia de muestreo
    private float sato2SamplingRate;
    //variables para guardar los datos
    private float apneaPercentage;
    private float hipoapneaPercentage;
    private float apneaAndHipoapneaPercentage;
    private float desaturationPercentage;
    private float meanValueSato2;
    private float BasalValueSato2;
    private float areaSato2;


    /**
     * Este es el metodo de JSignalWorkbench llamara desde el boton que creara
     * en la barra de tareas para este algoritmo.
     *
     * @param sm SignalManager
     * @param signals List
     * @param ar AlgorithmRunner
     */
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {
        initialize(sm);
        //Al anhadir las anotaciones a un arbol se ordenan por su instante de inicio
        TreeSet<LimitacionAnotacion> allHipoventilations = getMarksAsTree(fluxSignal);
        TreeSet<LimitacionAnotacion> allDesaturations = getMarksAsTree(sato2Signal);
        TreeSet<LimitacionAnotacion> apneas = new TreeSet<LimitacionAnotacion>();
        TreeSet<LimitacionAnotacion> hipoapneas = new TreeSet<LimitacionAnotacion>();

        TreeSet<LimitacionAnotacion> desatTree = new TreeSet<LimitacionAnotacion>();
        for (LimitacionAnotacion anotation : allHipoventilations) {
            if (anotation.getTipo() == LimitacionAnotacion.APNEA) {
                apneas.add(anotation);

            } else {
                hipoapneas.add(anotation);
            }
        }

        assert (hipoapneas.size() + apneas.size() == allHipoventilations.size());

//esto lo que mide cada una de las anotaciones, en milisegundos
        // anotation.getEndTime()- anotation.getMarkTime()

        putDataInClipBoard();

    }

    private void putDataInClipBoard() throws HeadlessException {
        String data = generateStringWithData();
        StringSelection stringSelection = new StringSelection(data);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, new ClipboardOwner() {
            public void lostOwnership(Clipboard aClipboard,
                                      Transferable aContents) {

            }
        });
    }

    private String generateStringWithData() {
        String data = apneaPercentage + "; " + hipoapneaPercentage + "; " +
                      apneaAndHipoapneaPercentage + "; " +
                      desaturationPercentage + "; " + meanValueSato2 + "; " +
                      BasalValueSato2 + "; " + areaSato2;
        return data;
    }


    /**
     * Usando la API de JSignalWorkbench obtiene las cuatro senhales, junto con
     * sus correspondientes arrays de datos, sobre los que vamos a trabajar.
     * Tambien obtiene la frecuencia de muestreo de las senhales. Todos estos
     * datos se guardan en atributos de la clase.
     *
     * @param sm SignalManager
     */
    private void initialize(SignalManager sm) {
        //Obtenemos las señales que necesitamos
        sato2Signal = sm.getSignal("Sat02");
        fluxSignal = sm.getSignal("Flujo");
        sato2 = sato2Signal.getValues();
        //Frecuencia de muestreo
        sato2SamplingRate = sato2Signal.getSRate();
    }


//**************Lo que hay aqui abajo no es relevante para ti ******************
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
