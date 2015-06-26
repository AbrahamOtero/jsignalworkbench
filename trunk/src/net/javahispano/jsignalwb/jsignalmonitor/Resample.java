package net.javahispano.jsignalwb.jsignalmonitor;

/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: Clase que permite remuestrar una senhal</p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class Resample {

    /**
     * Remuestre el array de float que se le pase. No emeplea suavizado.
     * @param datos datos iniciales a remuestrear
     * @param fsInicial Frecuencia de muestreo inicial
     * @param fsFinal Frecuencia de muestreo final
     * los datos antiguos.
     * @return array con los datos remuesrteados
     */
    public static float[] resampleFs(float data[], float startFs,
                                     float endFs) {
        return resampleFs(data, startFs, endFs, false);
    }

    /**
     * Remuestre el array de float que se le pase
     * @param datos datos iniciales a remuestrear
     * @param fsInicial Frecuencia de muestreo inicial
     * @param fsFinal Frecuencia de muestreo final
     * @param suaviza Si es true indica que al comprimir (pasa de una Fs de 10 a 1 por ejemplo)
     * el valor del nuevo dato i es la media de la ventan temporalq ue le corresponde sobre
     * los datos antiguos.
     * @return array con los datos remuesrteados
     */
    public static float[] resampleFs(float data[], float startFs,
                                     float endFs,
                                     boolean smooth) {
        return resampleT(data, 1 / startFs, 1 / endFs, smooth);
    }

    /**
     * Remuestre el array de float que se le pase. No suaviza los datos.
     * @param datos datos iniciales a remuestrear
     * @param periodoInicial Periodo de muestreo inicial
     * @param periodoFinal Periodo de muestreo final
     * @param suaviza Si es true indica que al comprimir (pasa de una Fs de 10 a 1 por ejemplo)
     * el valor del nuevo dato i es la media de la ventan temporalq ue le corresponde sobre
     * los datos antiguos.
     * @return array con los datos remuesrteados
     */

    public static float[] resampleT(float data[], float startPeriod,
                                    float endPeriod) {
        return resampleT(data, startPeriod, endPeriod, false);
    }

    /**
     * Remuestre el array de float que se le pase
     * @param datos datos iniciales a remuestrear
     * @param periodoInicial Periodo de muestreo inicial
     * @param periodoFinal Periodo de muestreo final
     * los datos antiguos.
     * @return array con los datos remuesrteados
     */

    public static float[] resampleT(float data[], float startPeriod,
                                    float endPeriod,
                                    boolean smooth) {
        int numDatos = data.length;
        float relation = (startPeriod / endPeriod);
        //El numero de datos finales sera el entyero superior a numDatos*periodoInicial/periodoFinal
        int nuevoNumDatos = (int) (numDatos * relation);
        float[] nuevoDatos = new float[nuevoNumDatos];
        for (int i = 0; i < numDatos; i++) {
            //Empezamos a rellenar en el dato correspondiente a i
            int primerDatoARellenar = (int) (i * (relation));
            //Seguimos hasta el dato correspondiente a i+1
            int ultimoDatoARellenar = (int) (((i + 1) * (relation)));
            for (int j = primerDatoARellenar; j < ultimoDatoARellenar; j++) {

                //Si el dato nuevo se corresponde a varios antiguos y se quiere suavizado
                if (endPeriod > startPeriod && smooth) {
                    //El ultimo dato que no ue empleado para rellenar
                    int anteriorDato = i - (int) (1 / relation);
                    anteriorDato = Math.max(0, anteriorDato);
                    //Este es el numero de dtaos qeu saltamos
                    int nunDatosSaltados = i - anteriorDato;
                    float sum = 0;
                    //Hacemos la media aritmetica desde el ultimo dato que no empleamos hasta
                    //el presente, y ese es el valor que empleamos para el nuevo dato
                    for (int k = anteriorDato; k < i; k++) {
                        sum += data[k];
                    }
                    sum = sum / nunDatosSaltados;
                    nuevoDatos[j] = sum;
                } else {
                    nuevoDatos[j] = data[i];
                }

            }

        }
        return nuevoDatos;
    }

    /**
     * Remuestre el array de float que se le pase. No emeplea suavizado.
     * @param datos datos iniciales a remuestrear
     * @param fsInicial Frecuencia de muestreo inicial
     * @param fsFinal Frecuencia de muestreo final
     * los datos antiguos.
     * @return array con los datos remuesrteados
     */
    public static short[] resampleFs(short data[], float startFs,
                                     float endFs) {
        return resampleFs(data, startFs, endFs, false);
    }

    /**
     * Remuestre el array de float que se le pase
     * @param datos datos iniciales a remuestrear
     * @param fsInicial Frecuencia de muestreo inicial
     * @param fsFinal Frecuencia de muestreo final
     * @param suaviza Si es true indica que al comprimir (pasa de una Fs de 10 a 1 por ejemplo)
     * el valor del nuevo dato i es la media de la ventan temporalq ue le corresponde sobre
     * los datos antiguos.
     * @return array con los datos remuesrteados
     */
    public static short[] resampleFs(short data[], float startFs,
                                     float endFs,
                                     boolean smooth) {
        return resampleT(data, 1 / startFs, 1 / endFs, smooth);
    }

    /**
     * Remuestre el array de float que se le pase. No suaviza los datos.
     * @param datos datos iniciales a remuestrear
     * @param periodoInicial Periodo de muestreo inicial
     * @param periodoFinal Periodo de muestreo final
     * @param suaviza Si es true indica que al comprimir (pasa de una Fs de 10 a 1 por ejemplo)
     * el valor del nuevo dato i es la media de la ventan temporalq ue le corresponde sobre
     * los datos antiguos.
     * @return array con los datos remuesrteados
     */

    public static short[] resampleT(short data[], float startPeriod,
                                    float endPeriod) {
        return resampleT(data, startPeriod, endPeriod, false);
    }

    /**
     * Remuestre el array de float que se le pase
     * @param datos datos iniciales a remuestrear
     * @param periodoInicial Periodo de muestreo inicial
     * @param periodoFinal Periodo de muestreo final
     * los datos antiguos.
     * @return array con los datos remuesrteados
     */

    public static short[] resampleT(short data[], float startPeriod,
                                    float endPeriod,
                                    boolean smooth) {
        int numDatos = data.length;
        float relation = (startPeriod / endPeriod);
        //El numero de datos finales sera el entyero superior a numDatos*periodoInicial/periodoFinal
        int nuevoNumDatos = (int) (numDatos * relation);
        short[] nuevoDatos = new short[nuevoNumDatos];
        for (int i = 0; i < numDatos; i++) {
            //Empezamos a rellenar en el dato correspondiente a i
            int primerDatoARellenar = (int) (i * (relation));
            //Seguimos hasta el dato correspondiente a i+1
            int ultimoDatoARellenar = (int) (((i + 1) * (relation)));
            for (int j = primerDatoARellenar; j < ultimoDatoARellenar; j++) {

                //Si el dato nuevo se corresponde a varios antiguos y se quiere suavizado
                if (endPeriod > startPeriod && smooth) {
                    //El ultimo dato que no ue empleado para rellenar
                    int anteriorDato = i - (int) (1 / relation);
                    anteriorDato = Math.max(0, anteriorDato);
                    //Este es el numero de dtaos qeu saltamos
                    int nunDatosSaltados = i - anteriorDato;
                    short sum = 0;
                    //Hacemos la media aritmetica desde el ultimo dato que no empleamos hasta
                    //el presente, y ese es el valor que empleamos para el nuevo dato
                    for (int k = anteriorDato; k < i; k++) {
                        sum += data[k];
                    }
                    sum = (short) (sum / nunDatosSaltados);
                    nuevoDatos[j] = sum;
                } else {
                    nuevoDatos[j] = data[i];
                }

            }

        }
        return nuevoDatos;
    }

    /**
     * Elimina los 0 finales de las senhales
     * @param datos
     * @return
     */
    public static float[] deleteLastZeros(float[] data) {
        int datoDeCorte = data.length;
        for (int i = data.length - 1; i >= 0; i--) {
            if (data[i] != 0) {
                break;
            }
            datoDeCorte--;
        }
        if (datoDeCorte != data.length) {
            float resultado[] = new float[datoDeCorte];
            for (int i = 0; i < datoDeCorte; i++) {
                resultado[i] = data[i];
            }
            return resultado;
        }
        return data;
    }


}
