package research.mining;

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
     * A|H}  \t {energy}  \t {Tipo : basalEnergyBefore}   \t {Tipo :
     * basalEnergyAfter}  \n
     *
     * @param level DETAILLEVEL
     * @return String
     */
    public String genrateDescriptors(DETAILLEVEL level) {
        //@Emma generar aquí todos los descriptores
        return "";
    }
}
