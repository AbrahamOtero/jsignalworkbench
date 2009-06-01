package research.mining;

import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.SignalManager;
import java.util.List;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import net.javahispano.jsignalwb.Signal;
import java.util.TreeSet;
import research.beats.anotaciones.LimitacionAnotacion;
import research.beats.anotaciones.DesaturacionAnotacion;
import javax.swing.Icon;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.MarkPlugin;
import net.javahispano.jsignalwb.utilities.TimePositionConverter;
import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;

public class GenerateDescriptors extends AlgorithmAdapter {

    private Signal sato2Signal, fluxSignal, toraxSignal, abdomenSignal;
    private float[] sato2, flux, thorax, abdomen;
    //Frecuencia de muestreo
    private float sr;


    /**
     * Este es el método de JSignalWorkbench llamará desde el botón que creará
     * en la barra de tareas para este algoritmo.
     *
     * @param sm SignalManager
     * @param signals List
     * @param ar AlgorithmRunner
     */
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {
        initialize(sm);
        //Al añadir las anotaciones a un arbol se ordenan por su instante de inicio
        TreeSet<DesaturacionAnotacion> desatAnnotationTree = getMarksAsTree(sato2Signal);

        TreeSet<Desaturation> desatTree = new TreeSet<Desaturation>();
        for (DesaturacionAnotacion desat : desatAnnotationTree) {
            Desaturation desaturation = generateDesaturation(desat);
            desatTree.add(desaturation);
        }
        SaveInfo generateInfo = new SaveInfo(desatTree);

    }
    /**
     * Se le pasa una anotación que representa una limitación de flujo
     * respiratorio y devuelve un objeto de tipo {@link Desaturation} conteniendo todos los
     * descriptores del evento representado por la anotación. Entre los
     * descriptores, se encuentran las limitaciones de flujo respiratorio
     *asociadas con la limitación.
     *
     * @param limitationAnnotation LimitacionAnotacion
     * @return FluxLimitation
     */
    private Desaturation generateDesaturation(DesaturacionAnotacion desatAnnotation) throws RuntimeException {
        System.out.println("Desaturacion: \n\t Principio: " + desatAnnotation.getMarkTime()
                           + " \n\t Índice del array correspondiente con el principio " +
                           TimePositionConverter.timeToPosition(desatAnnotation.getMarkTime(), sato2Signal)
                           + " \n\t Instante representado como hora natural " +
                           TimeRepresentation.timeToString(desatAnnotation.getMarkTime(), true)
                           + " \n\t Valor de la saturación de oxigeno en ese punto " +
                           sato2[TimePositionConverter.timeToPosition(desatAnnotation.getMarkTime(), sato2Signal)]
                           + " \n\t Final: " + desatAnnotation.getEndTime()
                           + " \n\t Índice del array correspondiente con el principio " +
                           TimePositionConverter.timeToPosition(desatAnnotation.getEndTime(), sato2Signal)
                           + " \n\t Instante representado como hora natural " +
                           TimeRepresentation.timeToString(desatAnnotation.getEndTime(), false, true, false) //no fecha, si hora, no milisegundos
                           + " \n\t Valor de la saturación de oxigeno en ese punto " +
                           sato2[TimePositionConverter.timeToPosition(desatAnnotation.getEndTime(), sato2Signal)]
                           + " \n\t Duración en segundos " +
                           //Dividimos por 1000 para pasar de milisegundos a segundos
                           (desatAnnotation.getEndTime() - desatAnnotation.getMarkTime()) / 1000
                );

        Desaturation desaturation = new Desaturation();
        desaturation.setAbsoluteBeginingTime(desatAnnotation.getMarkTime());
        //Los unicos metodos que te va a interesar de las anotaciones son los dos que se usan
        //en la lÝnea que esta a continuacion: los que devuelve su instante de inicio y el fin
        desaturation.setDuration(desatAnnotation.getEndTime() - desatAnnotation.getMarkTime());
        //...
        //@Emma generar aquí todos los descriptores
        List<LimitacionAnotacion> limAnnotationList = desatAnnotation.getLimitationsList();
        for (LimitacionAnotacion limAnnotation : limAnnotationList) {
            FluxLimitation fluxLimitation = generateLimitation(limAnnotation);
            desaturation.addFluxLimitation(fluxLimitation);
        }
        return desaturation;
    }

    /**
     * Se le pasa una anotación que representa una limitación de flujo
     * respiratorio y devuelve un objeto de tipo {@link FluxLimitation} conteniendo todos los
     * descriptores del evento representado por la anotación. Entre los
     * descriptores, se encuentran las limitaciones de movimiento abdominal y
     * torácico asociadas con la limitación.
     *
     * @param limitationAnnotation LimitacionAnotacion
     * @return FluxLimitation
     */
    private FluxLimitation generateLimitation(LimitacionAnotacion limitationAnnotation) {
        System.out.println("\t\tLimitación de flujo: " + limitationAnnotation.getMarkTime());

        FluxLimitation fluxLimitation = new FluxLimitation();
        //@Emma generar aquí todos los descriptores
        List<LimitacionAnotacion> abdomenAnnotationList = limitationAnnotation.getAbdomenList();
        for (LimitacionAnotacion abdomenAnnotationLimitation : abdomenAnnotationList) {
            AbdominalMovementLimutation abdomenLimitation = generateAbdomenLimitation(abdomenAnnotationLimitation);
            fluxLimitation.addAbdomenLimitation(abdomenLimitation);
        }
        List<LimitacionAnotacion> thoraxAnnotationList = limitationAnnotation.getToraxList();
        for (LimitacionAnotacion thoraxAnnotationLimitation : thoraxAnnotationList) {
            ThoracicMovementLimutation thoraxLimitation = generateThoraxLimitation(thoraxAnnotationLimitation);
            fluxLimitation.addToraxLimitation(thoraxLimitation);
        }
        return fluxLimitation;
    }

    /**
     * Se le pasa una anotación que representa una limitación de movimiento
     * abdominal y devuelve un objeto tipo {@link AbdominalMovementLimutation} conteniendo todos los
     * descriptores del evento representado por la anotación.
     *
     * @param thoraxLimitacionAnotacion LimitacionAnotacion
     * @return ThoracicMovementLimutation
     */
    private AbdominalMovementLimutation generateAbdomenLimitation(LimitacionAnotacion abdominalLimitationAnnotation) {
        System.out.println("\t\t\tLimitación de movimiento abdominal: " + abdominalLimitationAnnotation.getMarkTime());
        AbdominalMovementLimutation abdominalLimitation = new AbdominalMovementLimutation();
        //@Emma generar aquí todos los descriptores
        return abdominalLimitation;
    }

    /**
     * Se le pasa una anotación que representa una limitación de movimiento
     * torácico y devuelve un objeto tipo {@link ThoracicMovementLimutation} conteniendo todos los
     * descriptores del evento representado por la anotación.
     *
     * @param thoraxLimitacionAnotacion LimitacionAnotacion
     * @return ThoracicMovementLimutation
     */
    private ThoracicMovementLimutation generateThoraxLimitation(LimitacionAnotacion thoraxLimitacionAnotacion) {
        System.out.println("\t\t\tLimitación de movimiento torácico: " + thoraxLimitacionAnotacion.getMarkTime());
        ThoracicMovementLimutation thoraxLimitacion = new ThoracicMovementLimutation();
         //@Emma generar aquí todos los descriptores
        return thoraxLimitacion;
    }


    /**
     * Usando la API de JSignalWorkbench obtiene las cuatro señales, junto con
     * sus correspondientes arrays de datos, sobre los que vamos a trabajar.
     * También obtiene la frecuencia de muestreo de las señales. Todos estos
     * datos se guardan en atributos de la clase.
     *
     * @param sm SignalManager
     */
    private void initialize(SignalManager sm) {
        //Preparamos las anotaciones para ser procesadas
        (new AssociateEvents()).asociate(sm);
        //Obtenemos los array que necesitamos
        sato2Signal = sm.getSignal("Sat02");
        fluxSignal = sm.getSignal("Flujo");
        toraxSignal = sm.getSignal("Movimiento toracico");
        abdomenSignal = sm.getSignal("Movimiento abdominal");
        sato2 = sato2Signal.getValues();
        flux = fluxSignal.getValues();
        thorax = toraxSignal.getValues();
        abdomen = abdomenSignal.getValues();
        //Frecuencia de muestreo
        sr = sato2Signal.getSRate();
    }


    public void launchExecutionGUI(JSWBManager jswbManager) {
        this.runAlgorithm(jswbManager.getSignalManager(), null, null);
    }

//**************Lo que hay aquí abajo no es relevante para ti ******************
     private TreeSet<DesaturacionAnotacion> getMarksAsTree(Signal sato2) {
         List<MarkPlugin> listMarkPlugins = sato2.getAllMarks();
         TreeSet<DesaturacionAnotacion> limTree = new TreeSet<DesaturacionAnotacion>();
         for (Object elem : listMarkPlugins) {
             limTree.add((DesaturacionAnotacion) elem);
         }
         assert (listMarkPlugins.size() == limTree.size());
         return limTree;
     }

    public boolean hasOwnExecutionGUI() {
        return true;
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
        return super.generateImage("GD");
    }

    public String getName() {
        return "Generar descriptores";
    }

    public String getDescription() {
        return getName();
    }

    public String getShortDescription() {
        return getName();
    }
}
