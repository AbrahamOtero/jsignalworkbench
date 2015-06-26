package research.mining;

import java.util.LinkedList;
import java.util.List;
import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;


public class FluxLimitation extends TemporalEvent {
    //Energia de la senhal normalizada por la duracion del episodio
    private float energy;
    //Buscar 10 segundos de senhal de flujo respiratorio sin limitacion justo antes
    //de la senhal y calcular su energia normalizada por su duracion.
    private float basalEnergyBefore;
    //Buscar 10 segundos de senhal de flujo respiratorio sin limitacion justo Despues
    //de la senhal y calcular su energia normalizada por su duracion.
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
     * //info de la limitacion toracica mas grande (mas tiempo)
     * \t {duration}  \t {type: A|H}  \t {energy}  \t
     * {Tipo : basalEnergyBefore}   \t {Tipo : basalEnergyAfter}  \t
     * //info de la limitacion abdominal mas grande
     * \t {duration}  \t {type: A|H}  \t {energy}  \t
     * {Tipo : basalEnergyBefore}   \t {Tipo : basalEnergyAfter}  \n
     *
     * @param level DETAILLEVEL
     * @return String
     */
public String genrateDescriptors(DETAILLEVEL level, long beginingRecording) {
        //@Emma que valores hay que poner si no hay ninguna limitacion toracica o abdominal??(duration == 0)
        // ahora mismo no se añade nada a la string que se devuelve
        String descriptors;
        long duration;
        AbdominalMovementLimutation abdLim = new AbdominalMovementLimutation();
        ThoracicMovementLimutation thoraxLim = new ThoracicMovementLimutation();

        abdLim.setDuration(0);
        thoraxLim.setDuration(0);

        descriptors = TimeRepresentation.timeToString(
                                this.getAbsoluteBeginingTime(),false,true,false);

        if(level == DETAILLEVEL.LOW){
            if(this.getType() == Type.APNEA)    descriptors += "\tGEN_APNEA\t";
            else                                descriptors += "\tGEN_HYPO\t";

            descriptors += this.getDuration();
        }
        else{
            descriptors +=   "\t" + (this.getAbsoluteBeginingTime()-beginingRecording)/1000F
                     + "\t" + this.getDuration()/1000F;
            if(this.getType() == Type.APNEA)    descriptors += "\tA";
            else                                descriptors += "\tH";

            if(level == DETAILLEVEL.HIGH || level == DETAILLEVEL.EVERYTHING){
                descriptors += "\t" + this.getEnergy() + "\t"
                        + this.getBasalEnergyBefore() + "\t"
                        + this.getBasalEnergyAfter();
                if(false && level == DETAILLEVEL.EVERYTHING){
                    //info de la limitacion toracica mas larga
                    for(ThoracicMovementLimutation lim: thoracicLimitations){
                        if(lim.getDuration()>thoraxLim.getDuration())
                            thoraxLim = lim;
                    }
                    duration = thoraxLim.getDuration();
                    if(duration!=0){
                        descriptors += "\t" + thoraxLim.getDuration();

                        if(thoraxLim.getType() == Type.APNEA)
                            descriptors += "\tA\t";
                        else
                            descriptors += "\tH\t";

                        descriptors += thoraxLim.getEnergy() + "\t"
                                + thoraxLim.getBasalEnergyBefore() + "\t"
                                + thoraxLim.getBasalEnergyAfter();
                    }

                    //info de la limitacion abdominal mas larga
                    for(AbdominalMovementLimutation lim : abdominalLimitations){
                        if(lim.getDuration()>abdLim.getDuration())
                            abdLim = lim;
                    }
                    duration = abdLim.getDuration();
                    if(duration!=0){
                        descriptors += "\t" + abdLim.getDuration();

                        if(abdLim.getType() == Type.APNEA)
                            descriptors += "\tA\t";
                        else
                            descriptors += "\tH\t";

                        descriptors += abdLim.getEnergy() + "\t"
                                + abdLim.getBasalEnergyBefore() + "\t"
                                + abdLim.getBasalEnergyAfter();
                    }
                }
            }
        }
        return descriptors;
    }

    public void addAbdomenLimitation(AbdominalMovementLimutation l) {
        abdominalLimitations.add(l);
    }

    public void addToraxLimitation(ThoracicMovementLimutation l) {
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

    public List<AbdominalMovementLimutation> getAbdominalLimitations(){
        List<AbdominalMovementLimutation> listCopy =
            new LinkedList<AbdominalMovementLimutation>(abdominalLimitations);

        return listCopy;
    }

    public List<ThoracicMovementLimutation> getThoracicLimitations(){
        List<ThoracicMovementLimutation> listCopy =
            new LinkedList<ThoracicMovementLimutation>(thoracicLimitations);

        return listCopy;
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
