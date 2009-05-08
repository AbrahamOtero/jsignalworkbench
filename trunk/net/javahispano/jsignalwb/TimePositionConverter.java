package net.javahispano.jsignalwb;

/**
 * <p>Peque�a clase de utilidad que permite convertir un instante de tiempo medido
 * en milisegundos desde 00:00:00 01/01/1970 y representado mediante un long en
 * un �ndice de un array asociado con una se�al que posee una frecuencia de
 * muestreo determinada. Dentro del framework los instantes de tiempo se
 * representan como milisegundos segundos a partir de la fecha base indicada.
 * Sin embargo, al programador a menudo le interesar� saber que muestra de la
 * se�al o que intervalo dentro de la se�al se corresponde con un par de
 * instantes temporales. Esta clase permite realizar una traducci�n de un modo
 * sencillo entre ambas representaciones.</p>
 *
 * <p> Esta clase est� orientada a realizar operaciones internas sobre las
 * se�ales. Cuando sea necesario solicitar del usuario informaci�n relativa a
 * instantes de tiempo, o presentarle informaci�n relativa a instantes de
 * tiempo, se recomienda emplear la clase {@link TimeRepresentation}, m�s
 * adecuada que est� para convertir cadenas de caracteres que representen
 * fechas a instantes de tiempo y viceversa. </p>
 *
 * @author Rom�n Segador y Abraham Otero
 *    Copyright 2006-2007. This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/

 */
public class TimePositionConverter {

    /**
     * Convierte un instante de tiempo en un �ndice de un determinado array
     * dadas la fecha base del array y su frecuencia de muestreo.
     *
     * @param start fecha base del array.
     * @param time Instante temporal que deseamos convertir �ndice.
     * @param sRate frecuencia demuestre a la cual se encuentran los datos
     *   contenidos en el array.
     * @return �ndice del array. Retornar un valor negativo de la posicion del
     *   array en caso de que el tiempo que se le pas� como argumento sea
     *   anterior al de inicio de la senal.
     * @throws RuntimeException si <code>(start<0 || time<0|| sRate<=0
     *   )</code>
     */
    public static int timeToPosition(long start,long time,float sRate) throws RuntimeException{
//        Estas posiciones negativas son necesarias ya que posteriormente en
 //el array que se le pasa a JSignalMonitor se substituyen por el valor que represente el eje de abscissa.

 /*@todo bug
      java.lang.RuntimeException: The start value, time or frecuency is less than zero
        at net.javahispano.jsignalwb.TimePositionConverter.timeToPosition(TimePositionConverter.java:46)
        at net.javahispano.jsignalwb.JSWBManager.getChannelData(JSWBManager.java:537)
        at net.javahispano.jsignalwb.jsignalmonitor.Channels.refreshData(Channels.java:475)
        at net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitorPanel.refreshData(JSignalMonitorPanel.java:115)
        at net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitorPanel$3.run(JSignalMonitorPanel.java:134)
        at java.awt.event.InvocationEvent.dispatch(InvocationEvent.java:209)
        at java.awt.EventQueue.dispatchEvent(EventQueue.java:597)
        at java.awt.EventDispatchThread.pumpOneEventForFilters(EventDispatchThread.java:273)
        at java.awt.EventDispatchThread.pumpEventsForFilter(EventDispatchThread.java:183)
        at java.awt.EventDispatchThread.pumpEventsForFilter(EventDispatchThread.java:177)
        at java.awt.Dialog$1.run(Dialog.java:1039)
        at java.awt.Dialog$3.run(Dialog.java:1091)

      */
        if(start<0 || time<0|| sRate<=0)
            throw new RuntimeException("The start value, time or frecuency is less than zero");
        int i=(int)((((time-start))*sRate)/1000f);
        return i;
    }


    /**
     * Devuelve el �ndice del array del objeto {@link Signal} que se corresponde
     * con un instante de tiempo que se le pasa como argumento.
     *
     * @param time instante de tiempo.
     * @param signal {@link Signal}
     * @return �ndice del array.
     * @throws RuntimeException si <code>(start<0 || time<0|| sRate<=0 || start> time)</code>
     */
    public static int timeToPosition(long time,Signal signal)  throws RuntimeException{
        return timeToPosition(signal.getStart(), time, signal.getSRate());
    }
    /**
     * Convierte una posicion de array en un instante tiempo para un determinado array
     * dadas la fecha base del array y su frecuencia de muestreo.
     *
     * @param start fecha base del array.
     * @param position posicion del array
     * @param sRate frecuencia demuestre a la cual se encuentran los datos
     *   contenidos en el array.
     * @return long tiempo que se corresponde con la posicion del array.
     * @throws RuntimeException si <code>(start<0 || position<0|| sRate<=0 )</code>
     */
    public static long positionToTime(long start,int position,float sRate){
        if(start<0 || position<0|| sRate<=0  )
            throw new RuntimeException("The start value, position or frecuency is less than zero");
        return start + (long)(((float)position/sRate)*1000f);
    }
    /**
     * Convierte una posicion de array en un instante tiempo para un determinado array
     * dada una senal.
     *
     * @param start fecha base del array.
     * @param {@link Signal} senal en funcion de la cual se calcula el tiempo.
     * @return long tiempo que se corresponde con la posicion del array.
     * @throws RuntimeException si <code>(start<0 || position<0|| sRate<=0 )</code>
     */
    public static long positionToTime(int position,Signal signal){
        return positionToTime(signal.getStart(),position,signal.getSRate());
    }


}
