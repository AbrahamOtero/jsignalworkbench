package es.usc.gsi.trace.importer.estadisticos;

/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: Clase que permite remuestrar una senhal</p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class Remuestrea {

    /**
     * Remuestre el array de float que se le pase. No emeplea suavizado.
     * @param datos datos iniciales a remuestrear
     * @param fsInicial Frecuencia de muestreo inicial
     * @param fsFinal Frecuencia de muestreo final
     * los datos antiguos.
     * @return array con los datos remuesrteados
     */
    public static float[] remuestreaFs(float datos[], float fsInicial,
                                       float fsFinal) {
        return remuestreaFs(datos, fsInicial, fsFinal, false);
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
    public static float[] remuestreaFs(float datos[], float fsInicial,
                                       float fsFinal,
                                       boolean suaviza) {
        return remuestreaT(datos, 1 / fsInicial, 1 / fsFinal, suaviza);
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

    public static float[] remuestreaT(float datos[], float periodoInicial,
                                      float periodoFinal) {
        return remuestreaT(datos, periodoInicial, periodoFinal, false);
    }

    /**
     * Remuestre el array de float que se le pase
     * @param datos datos iniciales a remuestrear
     * @param periodoInicial Periodo de muestreo inicial
     * @param periodoFinal Periodo de muestreo final
     * los datos antiguos.
     * @return array con los datos remuesrteados
     */

    public static float[] remuestreaT(float datos[], float periodoInicial,
                                      float periodoFinal,
                                      boolean suaviza) {
        int numDatos = datos.length;
        //El numero de datos finales sera el entyero superior a numDatos*periodoInicial/periodoFinal
        int nuevoNumDatos = (int) (numDatos * periodoInicial / periodoFinal);
        float[] nuevoDatos = new float[nuevoNumDatos];
        for (int i = 0; i < numDatos; i++) {
            //Empezamos a rellenar en el dato correspondiente a i
            int primerDatoARellenar = (int) i * (nuevoNumDatos / numDatos);
            //Seguimos hasta el dato correspondiente a i+1
            int ultimoDatoARellenar = (int) (((i + 1) *
                                              (nuevoNumDatos / numDatos)));
            if (ultimoDatoARellenar < 0 || primerDatoARellenar < 0) {
                System.out.println("");
            }
            for (int j = primerDatoARellenar; j < ultimoDatoARellenar; j++) {

                //Si el dato nuevo se corresponde a varios antiguos y se quiere suavizado
                if (periodoFinal > periodoInicial && suaviza) {
                    //El ultimo dato que no ue empleado para rellenar
                    int anteriorDato = i - (int) (periodoFinal / periodoInicial);
                    anteriorDato = Math.max(0, anteriorDato);
                    //Este es el numero de dtaos qeu saltamos
                    int nunDatosSaltados = i - anteriorDato;
                    float sum = 0;
                    //Hacemos la media aritmetica desde el ultimo dato que no empleamos hasta
                    //el presente, y ese es el valor que empleamos para el nuevo dato
                    for (int k = anteriorDato; k < i; k++) {
                        sum += datos[k];
                    }
                    sum = sum / nunDatosSaltados;
                    nuevoDatos[j] = sum;
                } else {
                    nuevoDatos[j] = datos[i];
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
    public static float[] elimina0Finales(float[] datos) {
        int dato_de_corte = datos.length;
        for (int i = datos.length - 1; i >= 0; i--) {
            if (datos[i] != 0) {
                break;
            }
            dato_de_corte--;
        }
        if (dato_de_corte != datos.length) {
            float resultado[] = new float[dato_de_corte];
            for (int i = 0; i < dato_de_corte; i++) {
                resultado[i] = datos[i];
            }
            return resultado;
        }
        return datos;
    }


}
