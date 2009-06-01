package research.mining;

import java.util.LinkedList;
import java.util.List;


public class FluxLimitation extends TemporalEvent {
    //Energ�a de la se�al normalizada por la duraci�n del episodio
    private float energy;
    //Buscar 10 segundos de se�al de flujo respiratorio sin limitaci�n justo antes
    //de la se�al y calcular su energ�a normalizada por su duraci�n.
    private float basalEnergyBefore;
    //Buscar 10 segundos de se�al de flujo respiratorio sin limitaci�n justo Despues
    //de la se�al y calcular su energ�a normalizada por su duraci�n.
    private float basalEnergyAfter;
    //si es hipoapnea o apnea
    private Type type;
    //lista de limitaciones de movimiento abdominal
    private List<AbdominalMovementLimutation> abdominalLimitations =
            new LinkedList<AbdominalMovementLimutation>();
    //lista de limitaciones de movimiento toracico
    private List<ThoracicMovementLimutation> thoracicLimitations =
            new LinkedList<ThoracicMovementLimutation>();

    public enum Type {
        APNEA, HIPOAPNEA
    }


    public FluxLimitation() {}

    public FluxLimitation(long absoluteBeginingTime, long duration) {
        super(absoluteBeginingTime, duration);
    }


    /**
     * LOW: {absoluteBeginingTime: HH:MM:SS} \t {GEN_APNEA|GEN_HYPO} \t
     * {duration} \n
     *
     * MEDIUM: {absoluteBeginingTime: HH:MM:SS} \t {GEN_LIM} \t {duration}  \t
     * {type: A|H}  \n
     *
     * HIGH: {absoluteBeginingTime: HH:MM:SS} \t {GEN_LIM} \t {duration}  \t
     * {type: A|H}  \t {energy}  \t {Tipo : basalEnergyBefore}   \t {Tipo :
     * basalEnergyAfter}  \n
     *
     * EVERYTHING: {absoluteBeginingTime: HH:MM:SS} \t {GEN_LIM} \t {duration}
     * \t {type: A|H}  \t {energy}  \t {Tipo : basalEnergyBefore}   \t {Tipo :
     * basalEnergyAfter}  \t
     * //info de la t m�s grande \t {duration}  \t {type: A|H}  \t {energy}  \t
     * {Tipo : basalEnergyBefore}   \t {Tipo : basalEnergyAfter}  \t
     * //info de la a m�s grande \t {duration}  \t {type: A|H}  \t {energy}  \t
     * {Tipo : basalEnergyBefore}   \t {Tipo : basalEnergyAfter}  \n
     *
     * @param level DETAILLEVEL
     * @return String
     */
    public String genrateDescriptors(DETAILLEVEL level) {
        //@Emma generar aqu� todos los descriptores
        return "";
    }

    public void addAbdomenLimitation(AbdominalMovementLimutation l) {
        abdominalLimitations.add(l);
    }

    public void addToraxLimitation (ThoracicMovementLimutation l) {
        thoracicLimitations.add(l);
    }

    public float getBasalEnergyAfter() {
        return basalEnergyAfter;
    }

    public float getBasalEnergyBefore() {
        return basalEnergyBefore;
    }

    public float getEnergy() {
        return energy;
    }

    public research.mining.FluxLimitation.Type getType() {
        return type;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public void setBasalEnergyBefore(float basalEnergyBefore) {
        this.basalEnergyBefore = basalEnergyBefore;
    }

    public void setBasalEnergyAfter(float basalEnergyAfter) {
        this.basalEnergyAfter = basalEnergyAfter;
    }

    public void setType(research.mining.FluxLimitation.Type type) {
        this.type = type;
    }


}
