package research.mining;

import java.util.LinkedList;
import java.util.List;

public class Desaturation extends TemporalEvent {
    //M�nimo valor de la saturacion de oxigeno durante la desaturacion
    private float min;
    //Valores de la saturacion de oxigeno al principio y al final del evento
    private float beginValue, endValue;
    //Tiempo desde el inicio de la desaturaci�n hasta que se alcanza el valor m�nimo.
    private long fallDuration;
    //Tiempo desde el valor m�nimo hasta el final de la desaturaci�n.
    private long riseDuration;
    //Valor medio de la saturaci�n de oxigeno en la desaturaci�n.
    private float meanValue;
    //Pendiente de la l�nea que pasa por el punto inicial de la desaturaci�n
    //y por el punto donde se alcanza el valor m�nimo
    private float fallSlope;
    //Pendiente de la l�nea que pasa por el punto donde se alcanza el valor m�nimo
    //y por el punto final de la desaturaci�n
    private float riseSlope;
    //�rea del "hueco" de la desaturaci�n de oxigeno
    private float desaturatedArea;
    //N�mero de veces que la saturaci�n de oxigeno sube al menos dos unidades en
    //el intervalo, y despu�s vuelve a caer al menos dos unidades
    private int numDesaturations;
    //lista de limitaciones de flujo
    private List<FluxLimitation> limitations = new LinkedList<FluxLimitation>();
    //Energ�a de la se�al en los intervalos de limitacion de flujo
    //normalizada por la duraci�n del episodio
    private float energyLimitations;
    //Energ�a de la se�al fuera de los intervalos de limitacion de flujo
    //normalizada por la duraci�n del episodio
    private float energyOutOfLimitations;
    //Buscar 10 segundos de se�al de flujo respiratorio sin limitaci�n justo antes
    //de la se�al y calcular su valor m�ximo.
    private float basalEnergyBefore;
    //Buscar 10 segundos de se�al de flujo respiratorio sin limitaci�n justo Despues
    //de la se�al y calcular su energ�a normalizada por su duraci�n.
    private float basalEnergyAfter;

    public Desaturation() {
    }

    public Desaturation(long absoluteBeginingTime, long duration) {
        super(absoluteBeginingTime, duration);
    }


    /**
     * LOW: {absoluteBeginingTime: HH:MM:SS} \t {GEN_DESAT} \t {duration}  \n
     *
     * MEDIUM: {absoluteBeginingTime: HH:MM:SS} \t {GEN_DESAT} \t {duration}  \t
     * {type: A|H}  \n
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
     * +{Level EVERYTHING para cada limitaci�n de flujo}\n
     *
     * @param level DETAILLEVEL
     * @return String
     */
    public String genrateDescriptors(DETAILLEVEL level) {
        //@Emma generar aqu� todos los descriptores
        return "";
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

    public int compareTo(Object o) {
        return 0;
    }

}
