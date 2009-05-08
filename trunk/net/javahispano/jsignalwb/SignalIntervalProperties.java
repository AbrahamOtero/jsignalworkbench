package net.javahispano.jsignalwb;

/*
 * Representa un intervalo de se�al.
 *
 * @author Rom�n Segador y Abraham Otero
 *    Copyright 2006-2007. This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/).
 */
public class SignalIntervalProperties {
    private Signal signal;
    private long startTime;
    private long endTime;
    private int firstArrayPosition;
    private int lastArrayPosition;
    private boolean fullSignal;
    /**
     * Construye un intervalo que empieza al principio de la se�al que se le
     * pasa como argumento y termina en su fin.
     *
     * @param signal se�al para la cose quiere construir el intervalo.
     */
    public SignalIntervalProperties(Signal signal){
        this.signal=signal;
        this.fullSignal=true;
        this.startTime=signal.getStart();
        this.endTime=signal.getProperties().getEndTime();
        this.firstArrayPosition=0;
        this.lastArrayPosition=signal.getValues().length - 1;
    }

    /**
     * construye un intervalo de se�al con las propiedades especificadas.
     *
     * @param signal se�ala a la cual afecta el intervalo.
     * @param startTime principio del intervalo medido en milisegundos desde
     *   00:00:00 01/01/1970. Ver {@link TimePositionConverter}.
     * @param endTime fin del intervalo medido en milisegundos desde 00:00:00
     *   01/01/1970. Ver {@link TimePositionConverter}.
     * @param firstArrayPosition �ndice de la ra�z de datos asociado con esta
     *   se�al que se corresponde con el instante de inicio del intervalo.
     * @param lastArrayPosition �ndice del array de datos asociado con esta
     *   se�al que se corresponde con el instante de fin del intervalo.
     */
    public SignalIntervalProperties(Signal signal,
            long startTime,long endTime,
            int firstArrayPosition,int lastArrayPosition) {
        this.signal=signal;
        this.startTime=startTime;
        this.endTime=endTime;
        this.firstArrayPosition=firstArrayPosition;
        this.lastArrayPosition=lastArrayPosition;
        if(firstArrayPosition == 0 && lastArrayPosition == signal.getValues().length - 1)
            this.fullSignal=true;
        else
            this.fullSignal=false;
    }

    /**
     * getSignal
     *
     * @return {@link Signal} asociada con este intervalo. El objeto es
     * la se�al del entorno, no una copia; esto es, si el objeto
     * se modifica la se�al del entorno ser� modificada.
     */
    public Signal getSignal() {
        return signal;
    }

    /**
     * getStartTime
     *
     * @return long instante de tiempo medido en milisegundos desde
     *   00:00:00 01/01/1970 en el cual comienza el intervalo.
     *   Ver {@link TimePositionConverter}.
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * getEndTime
     *
     * @return long instante de tiempo medido en milisegundos desde
     *   00:00:00 01/01/1970 en el cual comienza el intervalo.
     *   Ver {@link TimePositionConverter}.
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * getFirstArrayPosition
     *
     * @return �ndice del array de datos asociado con esta se�al que se
     *   corresponde con el instante de inicio del intervalo.
     */
    public int getFirstArrayPosition() {
        return firstArrayPosition;
    }

    /**
     * getLastArrayPosition
     *
     * @return �ndice del array de datos asociado con esta se�al que se
     *   corresponde con el instante de fin del intervalo.
     */
    public int getLastArrayPosition() {
        return lastArrayPosition;
    }

    /**
     * isFullSignal
     *
     * @return true si el intervalo se corresponde con una se�al completa,
     *   false en caso contrario.
     */
    public boolean isFullSignal() {
        return fullSignal;
    }

}
