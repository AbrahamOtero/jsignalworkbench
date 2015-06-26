package research.mining;

import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;
import research.mining.FluxLimitation.Type;

public class AbdominalMovementLimutation extends FluxLimitation {

    public AbdominalMovementLimutation() {}

    public AbdominalMovementLimutation(long absoluteBeginingTime, long duration) {
        super(absoluteBeginingTime, duration);
    }

    /**
     * LOW: {absoluteBeginingTime: HH:MM:SS} \t {GEN_AAPNEA|GEN_AHYPO} \t
     * {duration}  \n
     *
     * MEDIUM:{absoluteBeginingTime: HH:MM:SS} \t {GEN_LIM} \t {duration}  \t
     * {type: A|H}  \n
     *
     * HIGH: {absoluteBeginingTime: HH:MM:SS} \t {GEN_LIM} \t {duration}  \t
     * {type: A|H}  \t {energy}  \t {Tipo : basalEnergyBefore}   \t {Tipo :
     * EVERYTHING: basalEnergyAfter}  \n
     *
     * {absoluteBeginingTime: HH:MM:SS} \t {GEN_LIM} \t {duration}  \t {type:
     * A|H}  \t {energy}  \t {basalEnergyBefore}   \t {Tipo :
     * basalEnergyAfter}  \n
     *
     * @param level DETAILLEVEL
     * @return String
     */
    public String genrateDescriptors(DETAILLEVEL level) {
        String descriptors;

        descriptors = TimeRepresentation.timeToString(
                            this.getAbsoluteBeginingTime(),false,true,false);

        if(level == DETAILLEVEL.LOW){
            if(this.getType() == Type.APNEA)    descriptors += "\tGEN_AAPNEA\t";
            else                                descriptors += "\tGEN_AHYPO\t";

            descriptors += this.getDuration();
        }
        else{
            descriptors += "\tGEN_LIM\t" + this.getDuration();
            if(this.getType() == Type.APNEA)    descriptors += "\tA";
            else                                descriptors += "\tH";

            if(level == DETAILLEVEL.HIGH || level == DETAILLEVEL.EVERYTHING)
                descriptors += "\t" + this.getEnergy() + "\t"
                        + this.getBasalEnergyBefore() + "\t"
                        + this.getBasalEnergyAfter();
        }

        descriptors += "\n";
        return descriptors;
    }
}
