package es.usc.gsi.trace.importer.jsignalmonold.annotations;

/**
 *
 * @version 	0.6
 * @author 	Abraham Otero Quintana
 */
public class Mark extends ClinicalEvent {

    protected boolean isMit;
    protected byte codigoMit;

    public Mark() {
        this.tipo = ClinicalEvent.MARCA;
    }

    public boolean getIsMit() {
        return isMit;
    }

    public void setIsMit(boolean _isMit) {
        isMit = _isMit;
    }

    public byte getCodigoMit() {
        return codigoMit;
    }

    public void setCodigoMit(byte _codigoMit) {
        codigoMit = _codigoMit;
    }
}
