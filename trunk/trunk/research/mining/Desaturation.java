package research.mining;

import java.util.LinkedList;
import java.util.List;

import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;
import research.mining.FluxLimitation.Type;

public class Desaturation extends TemporalEvent {
    //Minimo valor de la saturacion de oxigeno durante la desaturacion
    private float min;
    //Valores de la saturacion de oxigeno al principio y al final del evento
    private float beginValue, endValue;
    //Tiempo desde el inicio de la desaturacion hasta que se alcanza el valor minimo.
    private long fallDuration;
    //Tiempo desde el valor minimo hasta el final de la desaturacion.
    private long riseDuration;
    //Valor medio de la saturacion de oxigeno en la desaturacion.
    private float meanValue;
    //Pendiente de la linea que pasa por el punto inicial de la desaturacion
    //y por el punto donde se alcanza el valor minimo
    private float fallSlope;
    //Pendiente de la linea que pasa por el punto donde se alcanza el valor minimo
    //y por el punto final de la desaturacion
    private float riseSlope;
    //Area del "hueco" de la desaturacion de oxigeno
    private float desaturatedArea;
    //Numero de veces que la saturacion de oxigeno sube al menos dos unidades en
    //el intervalo, y despues vuelve a caer al menos dos unidades
    private int numDesaturations;
    //lista de limitaciones de flujo
    private List<FluxLimitation> limitations = new LinkedList<FluxLimitation>();
    //Energia de la senhal en los intervalos de limitacion de flujo
    //normalizada por la duracion del episodio
    private float energyLimitations;
    //Energia de la senhal fuera de los intervalos de limitacion de flujo
    //normalizada por la duracion del episodio
    private float energyOutOfLimitations;
    //Buscar 10 segundos de senhal de flujo respiratorio sin limitacion justo antes
    //de la senhal y calcular su valor maximo.
    private float basalEnergyBefore;
    //Buscar 10 segundos de senhal de flujo respiratorio sin limitacion justo Despues
    //de la senhal y calcular su energia normalizada por su duracion.
    private float basalEnergyAfter;

    public Desaturation() {
    }

    public Desaturation(long absoluteBeginingTime, long duration) {
        super(absoluteBeginingTime, duration);
    }


    /**
     * LOW: {absoluteBeginingTime: HH:MM:SS} \t {GEN_DESAT} \t {duration}  \n
     *
     * MEDIUM: {absoluteBeginingTime: HH:MM:SS} \t {GEN_DESAT} \t {duration} \n
     *
     * HIGH: {absoluteBeginingTime: HH:MM:SS} \t {GEN_DESAT} \t {duration}  \t
     * {min}  \t {meanValue}  \t {beginValue}   \t {endValue} \t
     * {desaturatedArea}   \t {numDesaturations}  \t {energyLimitations} \t
     * {energyOutOfLimitations}  \t {basalEnergyBefore}  \t {basalEnergyAfter}
     * \n
     *
     * EVERYTHING: {absoluteBeginingTime: HH:MM:SS} \t {GEN_DESAT} \t {duration}
     * \t {min}  \t {meanValue}  \t {beginValue}   \t {endValue} \t
     * {fallDuration}  \t {riseDuration}  \t {fallSlope}  \t {riseSlope}  \t
     * {desaturatedArea}   \t {numDesaturations}  \t {energyLimitations} \t
     * {energyOutOfLimitations}  \t {basalEnergyBefore}  \t {basalEnergyAfter}
     * +{Level EVERYTHING para cada limitacion de flujo}\n
     *
     * @param level DETAILLEVEL
     * @return String
     */
    public String genrateDescriptors(DETAILLEVEL level, long beginingRecording) {
        String descriptors;

        descriptors = TimeRepresentation.timeToString(
                                this.getAbsoluteBeginingTime(),false,true,false)
                 + "\t" + (this.getAbsoluteBeginingTime()-beginingRecording) + "\t"
                 + this.getDuration()/1000F;
        if(level==DETAILLEVEL.HIGH || level==DETAILLEVEL.EVERYTHING){
            descriptors += "\t" + min + "\t" + meanValue + "\t" + beginValue
                    + "\t" + endValue + "\t" + (beginValue - min)+ "\t" +
                   (endValue - min) + "\t" + fallDuration/1000F + "\t" + riseDuration/1000F + "\t" +
                   fallSlope + "\t" + riseSlope + "\t" + desaturatedArea;
                  //  + numDesaturations + "\t" + energyLimitations + "\t" +
                  //  + energyOutOfLimitations+ "\t" + basalEnergyBefore +  "\t"
                  //  + basalEnergyAfter;
            if(level==DETAILLEVEL.MEDIUM || level==DETAILLEVEL.HIGH || level==DETAILLEVEL.EVERYTHING){
                descriptors += "\t";
                FluxLimitation fluxLim = generateSurrotageFluxLim ();
                descriptors += fluxLim.genrateDescriptors(DETAILLEVEL.EVERYTHING, beginingRecording);
                if (limitations.size()>1) {
                    descriptors += "\ttrue";
                }
                else{
                    descriptors += "\tfalse";
                }
                descriptors += "\t"+limitations.size();
              /*  for (FluxLimitation fluxLim : limitations) {
                    descriptors += fluxLim.genrateDescriptors(DETAILLEVEL.EVERYTHING, beginingRecording);
                }*/
                descriptors = generateTemporalRelations(descriptors);
                //"Age	Weight	BMI	AHI
                //Afternoon	MeanAp	MeanHy"+
                //"	MeanDesat	MeandValSpO2	BasalSpO2	Diagnostic	ID\n"
                descriptors += "\t" + "   111	 111	 111	 19.4"+
                       "	false	  111	 111"+
                       "	111	  111	 111	 91	 94.5	    Mild	     P287";
           }
        }
        descriptors += "\n";
        return descriptors;
    }

    /**
     * generateSurrotageFluxLim
     *
     * @param limitations List
     * @return FluxLimitation
     */
    private FluxLimitation generateSurrotageFluxLim() {
        FluxLimitation fluxLimitation = new FluxLimitation();
        fluxLimitation.setAbsoluteBeginingTime(limitations.get(0).getAbsoluteBeginingTime());
        int duration=0,apneaCounter=0,hypopneaCounter=0,episodesCounter=0;
        float energy=0,energyBefore=0,energyAfter=0;

        for (FluxLimitation fluxLim : limitations) {
            duration+=fluxLim.getDuration();
            energy+=fluxLim.getEnergy();
            energyBefore+=fluxLim.getBasalEnergyBefore();
            energyAfter+=fluxLim.getBasalEnergyAfter();
            if (fluxLim.getType()==Type.APNEA) {
                apneaCounter++;
            } else {
                hypopneaCounter++;
            }
            episodesCounter++;
        }
        fluxLimitation.setDuration(duration);
        fluxLimitation.setEnergy(energy);
        fluxLimitation.setBasalEnergyBefore(energyBefore);
        fluxLimitation.setBasalEnergyAfter(energyAfter);
        if (apneaCounter>=hypopneaCounter) {
            fluxLimitation.setType(Type.APNEA);
        } else {
            fluxLimitation.setType(Type.HIPOAPNEA);
        }
        return fluxLimitation;
    }

    private String generateTemporalRelations(String descriptors) {
        FluxLimitation fluxLim = limitations.get(0);
        descriptors += "\t" +  (this.getAbsoluteBeginingTime() - fluxLim.getAbsoluteBeginingTime())/1000F
                 + "\t" +  (this.getAbsoluteBeginingTime() + this.getFallDuration()
                 -fluxLim.getAbsoluteBeginingTime() - fluxLim.getDuration())/1000F
                 + "\t" +  (this.getAbsoluteBeginingTime() + this.getDuration()
                            -fluxLim.getAbsoluteBeginingTime() - fluxLim.getDuration())/1000F;
        return descriptors;
    }


    public void addFluxLimitation(FluxLimitation l) {
        limitations.add(l);
    }

    public float getBeginValue() {
        return beginValue;
    }

    public float getDesaturatedArea() {
        return desaturatedArea;
    }

    public float getEndValue() {
        return endValue;
    }

    public long getFallDuration() {
        return fallDuration;
    }

    public float getFallSlope() {
        return fallSlope;
    }

    public float getMeanValue() {
        return meanValue;
    }

    public float getMin() {
        return min;
    }

    public int getNumDesaturations() {
        return numDesaturations;
    }

    public long getRiseDuration() {
        return riseDuration;
    }

    public float getRiseSlope() {
        return riseSlope;
    }

    public List<FluxLimitation> getLimitations(){
        List<FluxLimitation> listCopy = new LinkedList(limitations);

        return listCopy;
    }

    public float getEnergyLimitations() {
        return energyLimitations;
    }

    public float getEnergyOutOfLimitations() {
        return energyOutOfLimitations;
    }

    public float getBasalEnergyBefore() {
        return basalEnergyBefore;
    }

    public float getBasalEnergyAfter() {
        return basalEnergyAfter;
    }

    public void setRiseSlope(float riseSlope) {
        this.riseSlope = riseSlope;
    }

    public void setRiseDuration(long riseDuration) {
        this.riseDuration = riseDuration;
    }

    public void setNumDesaturations(int numDesaturations) {
        this.numDesaturations = numDesaturations;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public void setMeanValue(float meanValue) {
        this.meanValue = meanValue;
    }

    public void setFallSlope(float fallSlope) {
        this.fallSlope = fallSlope;
    }

    public void setFallDuration(long fallDuration) {
        this.fallDuration = fallDuration;
    }

    public void setEndValue(float endValue) {
        this.endValue = endValue;
    }

    public void setDesaturatedArea(float desaturatedArea) {
        this.desaturatedArea = desaturatedArea;
    }

    public void setBeginValue(float beginValue) {
        this.beginValue = beginValue;
    }

    public void setEnergyOutOfLimitations(float energyOutOfLimitations) {
        this.energyOutOfLimitations = energyOutOfLimitations;
    }

    public void setEnergyLimitations(float energyLimitations) {
        this.energyLimitations = energyLimitations;
    }

    public void setBasalEnergyAfter(float basalEnergyAfter) {
        this.basalEnergyAfter = basalEnergyAfter;
    }

    public void setBasalEnergyBefore(float basalEnergyBefore) {
        this.basalEnergyBefore = basalEnergyBefore;
    }

}
