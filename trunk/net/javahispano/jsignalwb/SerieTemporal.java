package net.javahispano.jsignalwb;

import org.joda.time.DateTime;

/**
 * Clase que extiende a {@link Signal} proporcionando ciertos métodos de
 * utilidad para el procesado de series temporales.
 */
public class SerieTemporal extends Signal {
    /**
 * Crea una nueva instancia de Signal. sName indica el nombre de la senal
 * mientras que sValues almacena los valores de la senal ordenados para cada
 * instante de tiempo. Se asigna por defecto 1 al rango de tiempo entre
 * valores y el instance actual al tiempo de inicio de la senal. Se indica
 * que la magnitud es desconocida
 *
 * @param sName nombre de la señal.
 * @param sValues valores de la señal.
 */
public SerieTemporal(String sName, float[] sValues) {
    this(sName, sValues, null);
}

/**
 * Crea una nueva instancia de Signal.
 *
 * @param sName nombre de la señal.
 * @param sValues valores de la señal.
 * @param emphasis Nivel de énfasis con que se debe representar la señal.
 *   Debe contener valores entre [0, 100].
 */
public SerieTemporal(String sName, float[] sValues, short[] emphasis) {
    this(sName, sValues, 1, new DateTime().getMillis(), "Unknown", emphasis);
}

/**
 * Crea una nueva instancia de Signal.
 *
 * @param sName nombre de la señal.
 * @param sValues valores de la señal.
 * @param emphasis Nivel de énfasis con que se debe representar la señal.
 *   Debe contener valores entre [0, 100].
 * @param sStart long instante de inicio de la señal medido en milisegundos desde
 *   00:00:00 01/01/1970. Ver {@link TimePositionConverter}.
 * @param sMagnitude magnitud de la señal.
 */
public SerieTemporal(String sName, float[] sValues, float frec, long sStart, String sMagnitude) {
    this(sName, sValues, frec, sStart, sMagnitude, null);
}

/**
 * Crea una nueva instancia de Signal.
 *
 * @param sName nombre de la señal.
 * @param sValues valores de la señal.
 * @param emphasis Nivel de énfasis con que se debe representar la señal.
 *   Debe contener valores entre [0, 100].
 * @param sStart long instante de inicio de la señal medido en milisegundos desde
 *   00:00:00 01/01/1970. Ver {@link TimePositionConverter}.
 * @param sMagnitude magnitud de la señal.
 * @param emphasis Nivel de énfasis con que se debe representar la señal.
 *   Debe contener valores entre [0, 100].
 */
public SerieTemporal(String sName, float[] sValues, float frec, long sStart, String sMagnitude, short[] emphasis) {
    super (sName, sValues, frec, sStart,sMagnitude,emphasis);
}

    /**
     * Devuelve el origen temporal de la serie temporal. En caso de que dicho origen
     * no haya sido definido devuelve el instante temporal correspondiente con la
     * primera muestra de la señal.
     *
     * @return long
     */
    public long getTimeOrigin (){
    Object or = super.getProperty( "TimeOrigin");
    if (or!=null) {
        long  origin = (Long)or;
        return origin;
    } else {
     return super.getStart();
    }
}

    /**
     * Permite indicar cuál es el origen temporal de esta señal.
     *
     * @param timeOrigin long
     */
    public void setTimeOrigin (long timeOrigin){
    super.setProperty("TimeOrigin",timeOrigin);
}

}
