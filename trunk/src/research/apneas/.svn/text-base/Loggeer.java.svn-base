package research.apneas;

import net.javahispano.jsignalwb.*;

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
public class Loggeer {
    private boolean debugNivel1 = false;
    private boolean debugNivel2 = false;
    private float frecuencia = 1;
    private long fechaBase = 0;
    private SignalManager sm;
    private Signal senalReferencia;

    public Loggeer(Signal senalReferencia) {
        sm = JSWBManager.getJSWBManagerInstance().getSignalManager();
        this.senalReferencia = senalReferencia;
        fechaBase = senalReferencia.getStart();
        frecuencia = senalReferencia.getSRate();
    }

    public void debugNivel1(String nombre, float[] posibilidad) {
        if (debugNivel1) {
            anadeSenalParaDebug(posibilidad, nombre);
        }
    }

    public void debugNivel2(String nombre, float[] posibilidad) {
        if (debugNivel2) {
            anadeSenalParaDebug(posibilidad, nombre);
        }
    }


    private void anadeSenalParaDebug(float[] posibilidad, String nombre) {
        Signal senalTemporal = new Signal(nombre, posibilidad,
                                          frecuencia, fechaBase, nombre);
        sm.addSignal(senalTemporal);
        senalTemporal.adjustVisibleRange();
    }

    public void setDebugNivel1(boolean debugNivel1) {
        this.debugNivel1 = debugNivel1;
    }

    public void setDebugNivel2(boolean debugNivel2) {
        this.debugNivel2 = debugNivel2;
    }

    public void setFrecuencia(float frecuencia) {
        this.frecuencia = frecuencia;
    }

    public void setFechaBase(long fechaBase) {
        this.fechaBase = fechaBase;
    }

    public boolean isDebugNivel1() {
        return debugNivel1;
    }

    public boolean isDebugNivel2() {
        return debugNivel2;
    }

    public Object getFrecuencia() {
        return frecuencia;
    }

    public long getFechaBase() {
        return fechaBase;
    }

}
