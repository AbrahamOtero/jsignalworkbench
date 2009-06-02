package net.javahispano.plugins.basicstats;

/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class MediaMovil {
    public MediaMovil() {
    }

    /**
     * Se le pasa un array de dtos y la longitud de una ventana temporal, y calcula la media
     * movil. Machaca el array que se le pasa.
     * @param datos
     * @param ventana
     * @return
     * @throws MediaMovilException
     */

    public static float[] calculaMediaMovil(float[] datos, int ventana) throws
            MediaMovilException {
        if (datos == null) {
            throw new MediaMovilException("El array de datos pasado es null", ventana);
        }
        if (ventana <= 0) {
            throw (new MediaMovilException(
                    "Imposible calcular media movil con esa ventana", ventana));
        }

        for (int i = 0; i < datos.length - ventana; i++) {
            float tmp = 0;
            for (int j = 0; j < ventana; j++) {
                tmp = tmp + datos[i + j];
            }
            tmp = tmp / ventana;
            datos[i] = tmp;
        }
        for (int i = datos.length - ventana; i < datos.length; i++) {
            float tmp = 0;
            for (int j = i; j < datos.length; j++) {
                tmp = tmp + datos[j];
            }
            tmp = tmp / (datos.length - i);
            datos[i] = tmp;
        }

        return datos;
    }

    /**
     * Se le pasa un array de dtos y la longitud de una ventana temporal, y calcula la media
     * movil. No machaca el array que se le pasa.
     * @param datos
     * @param ventana
     * @return
     * @throws MediaMovilException
     */

    public static float[] calculaMediaMovilClone(float[] datos, int ventana) throws
            MediaMovilException {
        try {
            return MediaMovil.calculaMediaMovil((float[]) datos.clone(),
                                                ventana);
        } catch (MediaMovilException ex) {
            throw ex;
        }
    }

}
