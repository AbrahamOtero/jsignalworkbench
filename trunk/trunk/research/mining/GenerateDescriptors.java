package research.mining;

import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.swing.Icon;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.MarkPlugin;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import net.javahispano.jsignalwb.utilities.TimePositionConverter;
import research.beats.anotaciones.DesaturacionAnotacion;
import research.beats.anotaciones.LimitacionAnotacion;
import research.mining.FluxLimitation.Type;
import research.mining.TemporalEvent.DETAILLEVEL;

public class GenerateDescriptors extends AlgorithmAdapter {

    private Signal sato2Signal, fluxSignal, toraxSignal, abdomenSignal;
    private float[] sato2, flux, thorax, abdomen;
    //Frecuencia de muestreo
    private float sr;


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
        TreeSet<DesaturacionAnotacion> desatAnnotationTree = getMarksAsTree(sato2Signal);

        TreeSet<Desaturation> desatTree = new TreeSet<Desaturation>();
        for (DesaturacionAnotacion desat : desatAnnotationTree) {
            Desaturation desaturation = generateDesaturation(desat);
          /*/  System.out.println(desaturation.genrateDescriptors(DETAILLEVEL.LOW)+"\n"+//@Emma borrar (solo para pruebas)
                desaturation.genrateDescriptors(DETAILLEVEL.MEDIUM)+ "\n"+
                desaturation.genrateDescriptors(DETAILLEVEL.HIGH)+ "\n"+
                desaturation.genrateDescriptors(DETAILLEVEL.EVERYTHING));/**/
            desatTree.add(desaturation);
        }
        SaveInfo generateInfo = new SaveInfo(desatTree);
        generateInfo.setBeginingRecording(fluxSignal.getStart());
        generateInfo.setLevel(DETAILLEVEL.EVERYTHING);
        System.out.println(""+generateInfo.genrateDescriptors());
        generateInfo.saveDescriptors();

    }

    /**
     * Se le pasa una anotacion que representa una desaturacion
     * y devuelve un objeto de tipo {@link Desaturation} conteniendo todos los
     * descriptores del evento representado por la anotacion. Entre los
     * descriptores, se encuentran las limitaciones de flujo respiratorio
     *asociadas con la limitacion.
     *
     * @param limitationAnnotation LimitacionAnotacion
     * @return FluxLimitation
     */
    private Desaturation generateDesaturation(DesaturacionAnotacion desatAnnotation) throws RuntimeException {
     /*/   System.out.println("Desaturacion: \n\t Principio: " + desatAnnotation.getMarkTime()//@Emma borrar
                           + " \n\t Indice del array correspondiente con el principio " +
                           TimePositionConverter.timeToPosition(desatAnnotation.getMarkTime(), sato2Signal)
                           + " \n\t Instante representado como hora natural " +
                           TimeRepresentation.timeToString(desatAnnotation.getMarkTime(), true)
                           + " \n\t Valor de la saturacion de oxigeno en ese punto " +
                           sato2[TimePositionConverter.timeToPosition(desatAnnotation.getMarkTime(), sato2Signal)]
                           + " \n\t Final: " + desatAnnotation.getEndTime()
                           + " \n\t Indice del array correspondiente con el principio " +
                           TimePositionConverter.timeToPosition(desatAnnotation.getEndTime(), sato2Signal)
                           + " \n\t Instante representado como hora natural " +
                           TimeRepresentation.timeToString(desatAnnotation.getEndTime(), false, true, false) //no fecha, si hora, no milisegundos
                           + " \n\t Valor de la saturacion de oxigeno en ese punto " +
                           sato2[TimePositionConverter.timeToPosition(desatAnnotation.getEndTime(), sato2Signal)]
                           + " \n\t Duracion en segundos " +
                           //Dividimos por 1000 para pasar de milisegundos a segundos
                           (desatAnnotation.getEndTime() - desatAnnotation.getMarkTime()) / 1000
                );/**/
        int posMin,begin,end;

        Desaturation desaturation = new Desaturation();
        desaturation.setAbsoluteBeginingTime(desatAnnotation.getMarkTime());
        //Los unicos metodos que te va a interesar de las anotaciones son los dos que se usan
        //en la linea que esta a continuacion: los que devuelve su instante de inicio y el fin
        desaturation.setDuration(desatAnnotation.getEndTime() - desatAnnotation.getMarkTime());

        begin = TimePositionConverter.timeToPosition(desatAnnotation.getMarkTime(),sato2Signal);
        end = TimePositionConverter.timeToPosition(desatAnnotation.getEndTime(),sato2Signal);
        posMin = calculatePosMin(sato2,begin,end);

        desaturation.setMin(calculateMin(sato2,begin,end));
        desaturation.setBeginValue(sato2[begin]);
        desaturation.setEndValue(sato2[end]);

        desaturation.setFallDuration(TimePositionConverter.positionToTime(posMin, sato2Signal)
                - TimePositionConverter.positionToTime(begin, sato2Signal));
        desaturation.setRiseDuration(TimePositionConverter.positionToTime(end, sato2Signal)
                - TimePositionConverter.positionToTime(posMin, sato2Signal));

        desaturation.setMeanValue(calculateMean(sato2,begin,end));
        desaturation.setFallSlope((sato2[posMin]-sato2[begin])/(posMin-begin));
        desaturation.setRiseSlope((sato2[end]-sato2[posMin])/(end-posMin));

        desaturation.setDesaturatedArea(calculateArea(sato2,begin,end));
        desaturation.setNumDesaturations(calculateNumDesat(sato2,begin,end));

        List<LimitacionAnotacion> limAnnotationList = desatAnnotation.getLimitationsList();
        for (LimitacionAnotacion limAnnotation : limAnnotationList) {
            FluxLimitation fluxLimitation = generateLimitation(limAnnotation);
            desaturation.addFluxLimitation(fluxLimitation);
        }

        List<FluxLimitation> list = desaturation.getLimitations();

        desaturation.setEnergyLimitations(calcEnergyLimitations(list));
        desaturation.setEnergyOutOfLimitations(calcEnergyOutLimitations(
                TimePositionConverter.timeToPosition(desatAnnotation.getMarkTime(), fluxSignal),
                TimePositionConverter.timeToPosition(desatAnnotation.getEndTime(), fluxSignal),
                desaturation));
        desaturation.setBasalEnergyBefore(calcBasalEnergyBefore(list));
        desaturation.setBasalEnergyAfter(calcBasalEnergyAfter(list));

      /*/  System.out.println("\nMinimo: " + desaturation.getMin()//@Emma borrar
            + "\nBegin value: " + desaturation.getBeginValue()
            + "\nEnd value: " + desaturation.getEndValue()
            + "\nFall Duration: " + desaturation.getFallDuration()
            + "\nRise Duration: " + desaturation.getRiseDuration()
            + "\nMean Value: " + desaturation.getMeanValue()
            + "\nFall Slope: " + desaturation.getFallSlope()
            + "\nRise Slope: " + desaturation.getRiseSlope()
            + "\nDesaturated Area: " + desaturation.getDesaturatedArea()
            + "\nNum Desaturations: " + desaturation.getNumDesaturations()
            + "\nEnergy Limitations: " + desaturation.getEnergyLimitations()
            + "\nEnergy out of Limitations: " + desaturation.getEnergyOutOfLimitations()
            + "\nBasal Energy Before: " + desaturation.getBasalEnergyBefore()
            + "\nBasal Energy After: " + desaturation.getBasalEnergyAfter());
        for(FluxLimitation lim: desaturation.getLimitations()){
            System.out.print("\nBegin time: " + lim.getAbsoluteBeginingTime()
                    + "\nType: " + lim.getType()
                    + "\nEnergy: "+ lim.getEnergy()
                    + "\nEnergy Before: "+ lim.getBasalEnergyBefore()
                    + "\nEnergy After: "+ lim.getBasalEnergyAfter());
        }/**/

        return desaturation;
    }

    /**
     * Se le pasa una anotacion que representa una limitacion de flujo
     * respiratorio y devuelve un objeto de tipo {@link FluxLimitation} conteniendo todos los
     * descriptores del evento representado por la anotacion. Entre los
     * descriptores, se encuentran las limitaciones de movimiento abdominal y
     * toracico asociadas con la limitacion.
     *
     * @param limitationAnnotation LimitacionAnotacion
     * @return FluxLimitation
     */
    private FluxLimitation generateLimitation(LimitacionAnotacion limitationAnnotation) {
        FluxLimitation fluxLimitation = new FluxLimitation();
        //@Emma generar aqui todos los descriptores DONE

        fluxLimitation.setAbsoluteBeginingTime((limitationAnnotation.getMarkTime()));
        fluxLimitation.setDuration(limitationAnnotation.getEndTime()-limitationAnnotation.getMarkTime());
        fluxLimitation.setEnergy(calculateEnergy(flux,
                TimePositionConverter.timeToPosition(limitationAnnotation.getMarkTime(),fluxSignal),
                TimePositionConverter.timeToPosition(limitationAnnotation.getEndTime(),fluxSignal)));
        fluxLimitation.setBasalEnergyBefore(calculateEnergy(flux,
                TimePositionConverter.timeToPosition(limitationAnnotation.getMarkTime()-10000,fluxSignal),
                TimePositionConverter.timeToPosition(limitationAnnotation.getMarkTime(),fluxSignal)));
        fluxLimitation.setBasalEnergyAfter(calculateEnergy(flux,
                TimePositionConverter.timeToPosition(limitationAnnotation.getEndTime(),fluxSignal),
                TimePositionConverter.timeToPosition(limitationAnnotation.getEndTime()+10000,fluxSignal)));
        if(limitationAnnotation.getTipo()==1)
            fluxLimitation.setType(Type.APNEA);
        else
            fluxLimitation.setType(Type.HIPOAPNEA);

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
     * Se le pasa una anotacion que representa una limitacion de movimiento
     * abdominal y devuelve un objeto tipo {@link AbdominalMovementLimutation} conteniendo todos los
     * descriptores del evento representado por la anotacion.
     *
     * @param abdominalLimitationAnnotation LimitacionAnotacion
     * @return AbdominalMovementLimutation
     */
    private AbdominalMovementLimutation generateAbdomenLimitation(LimitacionAnotacion abdominalLimitationAnnotation) {
        AbdominalMovementLimutation abdominalLimitation = new AbdominalMovementLimutation();
        abdominalLimitation.setAbsoluteBeginingTime(abdominalLimitationAnnotation.getMarkTime());
        abdominalLimitation.setDuration(abdominalLimitationAnnotation.getEndTime()-abdominalLimitationAnnotation.getMarkTime());
        abdominalLimitation.setEnergy(calculateEnergy(abdomen,
                TimePositionConverter.timeToPosition(abdominalLimitationAnnotation.getMarkTime(),abdomenSignal),
                TimePositionConverter.timeToPosition(abdominalLimitationAnnotation.getEndTime(),abdomenSignal)));
        abdominalLimitation.setBasalEnergyBefore(calculateEnergy(abdomen,
                TimePositionConverter.timeToPosition(abdominalLimitationAnnotation.getMarkTime()-10000,abdomenSignal),
                TimePositionConverter.timeToPosition(abdominalLimitationAnnotation.getMarkTime(),abdomenSignal)));
        abdominalLimitation.setBasalEnergyAfter(calculateEnergy(abdomen,
                TimePositionConverter.timeToPosition(abdominalLimitationAnnotation.getEndTime(),abdomenSignal),
                TimePositionConverter.timeToPosition(abdominalLimitationAnnotation.getEndTime()+10000,abdomenSignal)));
        if(abdominalLimitationAnnotation.getTipo()==1)
            abdominalLimitation.setType(Type.APNEA);
        else
            abdominalLimitation.setType(Type.HIPOAPNEA);
        return abdominalLimitation;
    }

    /**
     * Se le pasa una anotacion que representa una limitacion de movimiento
     * toracico y devuelve un objeto tipo {@link ThoracicMovementLimutation} conteniendo todos los
     * descriptores del evento representado por la anotacion.
     *
     * @param thoraxLimitacionAnotacion LimitacionAnotacion
     * @return ThoracicMovementLimutation
     */
    private ThoracicMovementLimutation generateThoraxLimitation(LimitacionAnotacion thoraxLimitacionAnotacion) {
        ThoracicMovementLimutation thoraxLimitacion = new ThoracicMovementLimutation();
        thoraxLimitacion.setAbsoluteBeginingTime(thoraxLimitacionAnotacion.getMarkTime());
        thoraxLimitacion.setDuration(thoraxLimitacionAnotacion.getEndTime()-thoraxLimitacionAnotacion.getMarkTime());
        thoraxLimitacion.setEnergy(calculateEnergy(thorax,
                TimePositionConverter.timeToPosition(thoraxLimitacionAnotacion.getMarkTime(),toraxSignal),
                TimePositionConverter.timeToPosition(thoraxLimitacionAnotacion.getEndTime(),toraxSignal)));
        thoraxLimitacion.setBasalEnergyBefore(calculateEnergy(thorax,
                TimePositionConverter.timeToPosition(thoraxLimitacionAnotacion.getMarkTime()-10000,toraxSignal),
                TimePositionConverter.timeToPosition(thoraxLimitacionAnotacion.getMarkTime(),toraxSignal)));
        thoraxLimitacion.setBasalEnergyAfter(calculateEnergy(thorax,
                TimePositionConverter.timeToPosition(thoraxLimitacionAnotacion.getEndTime(),toraxSignal),
                TimePositionConverter.timeToPosition(thoraxLimitacionAnotacion.getEndTime()+10000,toraxSignal)));
        if(thoraxLimitacionAnotacion.getTipo()==1)
            thoraxLimitacion.setType(Type.APNEA);
        else
            thoraxLimitacion.setType(Type.HIPOAPNEA);
        return thoraxLimitacion;
    }

    /**
     * Calcula la energia de un intervalo normalizada por la duracion de dicho
     * intervalo
     *
     * @param signal el array con los valores de la señal
     * @param begin  posicion de inicio del intervalo (incluido)
     * @param end    posicion final del intervalo (incluido)
     * @return float  la energia normalizada
     */
    private float calculateEnergy(float[] signal,int begin,int end){
        float energy = 0;
        if(begin<0)             begin = 0;
        if(end>signal.length)   end = signal.length;

        for(int i=begin; i<=end&&i<signal.length; i++)
                energy += signal[i]*signal[i];
        energy = energy/(end-begin+1);

        return (float)Math.sqrt(energy);
    }

    /**
     * Calcula el float minimo del intervalo de un array. Si hay algun problema
     * devuelve Float.MAX_VALUE
     *
     * @param signal    array de float
     * @param begin     comienzo del intervalo (incluido)
     * @param end       fin del intervalo (incluido)
     * @return float    el valor minimo del intervalo
     */
    private float calculateMin(float[] signal,int begin,int end){
        float min = Float.MAX_VALUE;

        for(int i = begin; i<=end; i++)
            //evitar artefactos
            if(signal[i]<min && signal[i] > 20)     min = signal[i];

        return min;
    }

    /**
     * Calcula la posicion del valor minimo del intervalo de un array de float.
     * Si hay algun problema devuelve -1
     *
     * @param signal    array de float
     * @param begin     comienzo del intervalo (incluido)
     * @param end       fin del intervalo (incluido)
     * @return int      la posicion del valor minimo del intervalo
     */
    private int calculatePosMin(float[] signal,int begin,int end){
        int pos = -1;
        float min = Float.MAX_VALUE;

        for(int i = begin; i<=end; i++)
            //el = es para que coja la ultima muestra Si hay 1 zona plana en El minimo
            if(signal[i]<=min && signal[i] > 20){
                pos = i;
                min = signal[i];
            }
        return pos;
    }

    /**
     * Calcula la media de todos los valores dentro del intervalo de un array
     *
     * @param signal    array de float
     * @param begin     comienzo del intervalo (incluido)
     * @param end       fin del intervalo (incluido)
     * @return float    la media
     */
    private float calculateMean(float[] signal,int begin, int end){
        float mean = 0;

        for(int i = begin; i<=end; i++)
            mean = mean + signal[i];
        mean = mean/(end-begin+1);
        return mean;
    }

     /**
     * Calcula el area de la desaturacion del intervalo del array
     *
     * @param signal    array de float
     * @param begin     comienzo del intervalo (incluido)
     * @param end       fin del intervalo (incluido)
     * @return float    area
     */
    private float calculateArea(float[] signal,int begin,int end){
        float area = ((signal[begin]+signal[end])/2)*(end-begin+1);
        float areaAux = 0;

        for(int i = begin;i<=end;i++)//Suma de Riemann
            areaAux += signal[i];

        float a = area-areaAux;
        if (a<0) {
            System.out.println("");
        }
        return a;
        //@bug a vecesdtan negativo
    }

     /**
     * Calcula la cantidad de desaturaciones en un intervalo
     *
     * @param signal    array de float
     * @param begin     comienzo del intervalo (incluido)
     * @param end       fin del intervalo (incluido)
     * @return int      cantidad de desaturaciones
     */
    private int calculateNumDesat(float[] signal,int begin,int end){
        int num = 0,i = begin;
        float min = 100,max1,max2=signal[begin];

        while(i<=end){
            for(;signal[i]<=min && i<=end;i++)                    min = signal[i];

            max1 = min;

            for(;signal[i]>=max1 && i<=end; i++)        max1 = signal[i];

            if(1.9<=max1-min && 1.9<=max2-min)          num++;

            max2 = max1;
            min = 100;
        }

        if(num==0)      num=1;

        return num;
    }

    /**
     * Calcula la energia de las limitaciones normalizada
     *
     * @param  limitations  la lista de limitaciones respiratorias
     * @return float        la energia en las limitaciones
     */
    float calcEnergyLimitations(List<FluxLimitation> limitations){
        float energy = 0;

        for(FluxLimitation lim: limitations){
            energy += lim.getEnergy();
        }

        return energy;
    }

    /**
     * Calcula la energia de un intervalo fuera de las limitaciones respiratorias,
     * siempre que haya mas de una
     *
     * @param begin         comienzo del intervalo (incluido)
     * @param end           fin del intervalo (incluido)
     * @param desaturation  la desaturacion sobre la que trabajamos
     * @return float        la energia fuera de la limitaciones
     */
    float calcEnergyOutLimitations(int begin, int end,Desaturation desaturation){
        float energy = 0;

        if(desaturation.getNumDesaturations()>1)
            energy = calculateEnergy(flux,begin,end) -
                        desaturation.getEnergyLimitations();

        return energy;
    }

    /**
     * Calcula la energia antes de la primera limitacion de respiratoria
     *
     * @param limitations   la lista de limitaciones
     * @param float         la energia
     */
    private float calcBasalEnergyBefore(List<FluxLimitation> limitations){
        float energy;

        if(limitations.isEmpty())  energy = 0;
        else{
            Iterator<FluxLimitation> it = limitations.iterator();
            energy = it.next().getBasalEnergyBefore();
        }
        return energy;
    }

    /**
     * Calcula la energia despues de la ultima limitacion de respiratoria
     *
     * @param limitations   la lista de limitaciones
     * @param float         la energia
     */
    private float calcBasalEnergyAfter(List<FluxLimitation> limitations){
        float energy = 0;
        Iterator<FluxLimitation> it = limitations.iterator();

        while(it.hasNext()){
            energy = it.next().getBasalEnergyBefore();
        }
        return energy;
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

//**************Lo que hay aqui abajo no es relevante para ti ******************
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
