package research.apneas;

import java.awt.Color;
import static java.lang.Math.*;
import java.util.Arrays;

import net.javahispano.fuzzyutilities.representation.TrapezoidalDistribution;

public class Utilidades {


    /**
     * Calcula para cada valor del array que se le pasa como argumento a partir
     * de datosAnteriores y hasta datosPosteriores el valor basal
     * correspondiente con cada muestra. Para ello, se toma en torno a cada
     * muestra una ventana temporal que comienza datosAnteriores datos antes de
     * la muestra y se extiende hasta datosPosteriores despues de la muestra. Se
     * calcula el valor medio de todas las muestras comprendidas en ese
     * intervalo tales que su valor en magnitud se encuentra en el 10% de valores mas altos
     * dentro de ese intervalo. En caso de que posibilidad sea null se consideraran para el
     * calculo del valor basal de cada punto  Todas las muestras del intervalo.
     * El array que devuelve contiene en cada oposicion el valor basal
     * correspondiente a esa muestra.
     *
     * @param datos float[]
     * @param datosAnteriores int
     * @param datosPosteriores int
     * @return float[]
     */
    public static float[] calcularBasalMaximos(float[] datos, int datosAnteriores, int datosPosteriores) {
        float[] basal = new float[datos.length];
        float p[] = new float[datosAnteriores + datosPosteriores];
        //el paso es la dcima parte de la ventana temporal
        final int paso = max(1, (datosAnteriores + datosPosteriores) / 10);
        for (int i = datosAnteriores; i < datos.length - datosPosteriores; i += paso) {
            for (int j = i; j < i + p.length && j < datos.length; j++) {
                p[j - i] = datos[j];
            }
            Arrays.sort(p);
            float suma = 0;
            //solo se considera el 10% de los datos mayores
            for (int j = p.length * 95 / 100; j < p.length; j++) {
                suma += p[j];
            }
            suma /= (p.length - p.length * 95 / 100);
            for (int j = i; j < i + paso; j++) {
                basal[j] = suma;
            }
        }

        //llenamos el principio y al final con el ultimo un valor basal, o el primero, segun corresponda
        for (int i = 0; i < datosAnteriores; i++) {
            basal[i] = basal[datosAnteriores];
        }
        for (int i = basal.length - datosAnteriores; i < basal.length; i++) {
            basal[i] = basal[datos.length - datosPosteriores - 1];
        }
        return basal;
    }


    /**
     * Calcula para cada valor del array que se le pasa como argumento a partir
     * de datosAnteriores y hasta datosPosteriores el valor basal
     * correspondiente con cada muestra. Para ello, se toma en torno a cada
     * muestra una ventana temporal que comienza datosAnteriores datos antes de
     * la muestra y se extiende hasta datosPosteriores despues de la muestra. Se
     * calcula el valor medio de todas las muestras comprendidas en ese
     * intervalo es tal que el valor correspondiente de posibilidad sea la
     * unidad. En caso de que posibilidad sea null se consideraran para el
     * calculo del valor basal de cada punto todas las muestras del intervalo.
     * El array que devuelve contiene en cada oposicion el valor basal
     * correspondiente a esa muestra.
     *
     * @param datos float[]
     * @param posibilidad float[]
     * @param datosAnteriores int
     * @param datosPosteriores int
     * @return float[]
     */
    public static float[] calcularBasal(float[] datos, float[] posibilidad, int datosAnteriores,
                                        int datosPosteriores) {
        float[] basal = new float[datos.length];
        //@todo cambio reciente: antes el paso era de uno
        int paso = max(1, min(datosAnteriores, datosPosteriores) / 10);
        for (int i = datosAnteriores; i < datos.length - datosPosteriores; i += paso) {
            float suma = 0;
            int cont = 0;
            if (posibilidad != null) {
                for (int j = i - datosAnteriores; j < i + datosPosteriores; j++) {
                    if (posibilidad[j] > 0) {
                        suma += datos[j];
                        cont++;
                    }
                }
            } else {
                for (int j = i - datosAnteriores; j < i + datosPosteriores; j++) {
                    suma += datos[j];
                    cont++;
                }
            }
            float tmp = suma / cont;
            for (int j = i; j < i + paso; j++) {
                basal[j] = tmp;
            }
        }

        //llenamos el principio y al final con el ultimo un valor basal, o el primero, segun corresponda
        for (int i = 0; i < datosAnteriores; i++) {
            basal[i] = basal[datosAnteriores];
        }
        for (int i = basal.length - datosAnteriores; i < basal.length; i++) {
            basal[i] = basal[datos.length - datosPosteriores - 1];
        }
        return basal;
    }

    /**
     * Calcula la pendiente entorno al indice que se le pasa empleando para ello
     * una ventana temporal de tamanho duracion centrada en dicho indice. La
     * pendiente se calcula sobre los datos del array que se le pasa como
     * argumento y se da en unidades que ha variado la senhal en un periodo de
     * muestreo.
     *
     * @param ventana int
     * @param indice int
     * @param d float[]
     * @return float
     */
    public static float pendienteEnTornoA(int ventana, int indice, float[] d) {
        int minimo = indice - ventana / 2; //centramos sobre el indice
        int maximo = indice + ventana / 2;
        float pendiente = (d[maximo] - d[minimo]) / (maximo - minimo);
        return pendiente;
    }

    /**
     * Calcula el valor medio entorno al indice que se le pasa empleando para ello
     * una ventana temporal de tamanho duracion centrada en dicho indice. El valor
     * medio se calcula sobre los datos del array que se le pasa como argumento.
     *
     * @param ventana int
     * @param indice int
     * @param d float[]
     * @return float
     */
    public static float valorMedioEnTornoA(int ventana, int indice, float[] d) {
        int minimo = indice - ventana / 2; //centramos sobre el indice
        int maximo = indice + ventana / 2;
        maximo = min(d.length, maximo);
        float suma = 0;
        for (int i = minimo; i < maximo; i++) {
            suma += d[i];
        }
        return suma / (maximo - minimo);
    }

    public static float valorMedioEnTornoA2(int ventana, int indice, float[] d) {
        int minimo = indice - ventana / 2; //centramos sobre el indice
        int maximo = indice + ventana / 2;
        maximo = min(d.length, maximo);
        float suma = 0;
        float tmp[] = new float[maximo - minimo];

        for (int i = minimo; i < maximo; i++) {
            tmp[i - minimo] = d[i];
            // suma += d[i];
            //suma = Math.max(suma,d[i]);
        }

        Arrays.sort(tmp);
        for (int i = tmp.length / 2; i < tmp.length; i++) {
            suma += tmp[i];
        }
        return suma / (tmp.length - tmp.length / 2); // (maximo - minimo);
    }

    /**
     * Calcula el valor medio entorno al indice que se le pasa empleando para ello
     * una ventana temporal de tamanho duracion centrada en dicho indice. El valor
     * medio se calcula sobre los datos del array que se le pasa como argumento.
     *
     * @param ventana int
     * @param indice int
     * @param d float[]
     * @return float
     */
    public static float energiaEnTornoA(int ventana, int indice, float[] d) {
        int minimo = indice - ventana / 2; //centramos sobre el indice
        int maximo = indice + ventana / 2;
        maximo = min(d.length, maximo);
        minimo = max(minimo, ventana / 2);
        float suma = 0;
        for (int i = minimo; i < maximo; i++) {
            suma += abs(d[i]);
        }
        return suma / (maximo - minimo);
    }

    /**
     * Calcula para el array de dantos que se le pasa como argumento cual es su
     * compatibilidad con la pendiente (representada mediante una distribucion
     * de posibilidad trapezoial) que se le pasa como argumento. La
     * compatibilidad para cada muestra se representa como un valor entre 0 y
     * 100 y se vuelve en el array resultado.
     *
     * @param d float[]
     * @param ventana int
     * @param pendiente TrapezoidalDistribution
     * @return float[]
     */
    public static float[] compatibilidadConPendientes(float[] datos, int ventana, TrapezoidalDistribution pendiente) {
        float[] posibilidad = new float[datos.length];
        for (int i = ventana / 2; i < posibilidad.length - ventana / 2; i++) {
            posibilidad[i] = pendiente.evaluatepossibilityAt(Utilidades.pendienteEnTornoA(ventana, i, datos));
        }
        return posibilidad;
    }

    /**
     * Calcula para el array de dantos que se le pasa como argumento cual es su
     * compatibilidad con la pendiente (representada mediante una distribucion
     * de posibilidad trapezoial) que se le pasa como argumento. La
     * compatibilidad para cada muestra se representa como un valor entre 0 y
     * 100 y se vuelve en el array resultado.
     *
     * @param d float[]
     * @param ventanaNormalMuestras int
     * @param pendiente TrapezoidalDistribution
     * @return float[]
     */
    public static float[] compatibilidadConPendientes2(float[] datos,
            int ventanaNormalMuestras, TrapezoidalDistribution pendiente) {
        float[] posibilidad = new float[datos.length];
        for (int i = ventanaNormalMuestras / 2; i < posibilidad.length - ventanaNormalMuestras / 2;
                     i++) {
            posibilidad[i] = pendiente.evaluatepossibilityAt(
                    datos[i - ventanaNormalMuestras / 2] - datos[i + ventanaNormalMuestras / 2]);
        }
        return posibilidad;
    }


    /**
     * Rellena todos los valores del array que sean menores que limite con el
     * ultimo valor a la izquierda del valor menor que el limite que era mayor
     * que el limite; siempre y cuando no haya mas de persistencia valores
     * seguidos que son menores que el limite.
     *
     * @param datos float[]
     * @param persistencia numero de muestras mximas consecutivas a rellenar
     * @param limite float
     */
    public static void rellenarHuecos(float[] datos, int persistencia, float limite) {

        for (int i = persistencia; i < datos.length; i++) {
            //buscamos el primer dato mayor que el limite
            if (datos[i] > limite) {
                // miramos cuando deja de haber datos mayores que el limite
                while (datos[i] > limite && i < datos.length) {
                    i++;
                }
                //ahora miramos  si vuelve a haber datos mayores que el limite
                //antes de #Persistencia muestras
                for (int j = i; j < datos.length && j < i + persistencia; j++) {
                    if (datos[j] > limite) {
                        //se encontro un dato mayor que limite; rellenamos con el ultimo valor
                        for (int k = i - 1; k < j; k++) {
                            datos[k] = datos[k - 1];
                        }
                        //Seguimos A partir de j; los datos entre el antiguo
                        //valor de i y j han sido rellenados y no hay que mirarlos
                        i = j - 1;
                        break;
                    }
                }
            }
        }
    }


    public static void ponerACeroPorDebajoDe(float[] datos, float limite) {
        for (int i = 0; i < datos.length; i++) {
            if (datos[i] < limite) {
                datos[i] = 0;
            }
        }
    }

    /**
     * Rellena todos los valores del array que sean menores que 0 con el
     * ultimo valor a la izquierda del valor menor que el limite que era mayor
     * que 0; siempre y cuando no haya mas de persistencia valores
     * seguidos que son menores que 0.
     *
     * @param datos float[]
     * @param persistencia numero de muestras mximas consecutivas a rellenar
     */
    public static void rellenarHuecos(float[] datos, int persistencia) {
        rellenarHuecos(datos, persistencia, 0);
    }

    /**
     * Este metodo devuelve un array cuyos elementos se encuentran en orden
     * inverso al array argumento; es decir, el primer elemento del array
     * argumento es el ultimo elemento del array resultado, el segundo elemento
     * del primer array es el penultimo elemento de array resultado... y el
     * ultimo elemento del primer array es el primer elemento del array
     * resultado.
     *
     * @param original float[]
     * @return float[]
     */
    public static float[] invertirArray(float[] original) {
        float[] invertido = new float[original.length];
        for (int i = 0; i < original.length; i++) {
            invertido[i] = original[original.length - 1 - i];
        }

        return invertido;
    }

    public static float[] calculaMediaMovilClone(float[] datos, int ventana) {
        float[] datosFiltrados = new float[datos.length];
        int medio;
        float[] datosVentana;
        if (ventana % 2 != 0) {
            medio = ventana / 2;
            datosVentana = new float[ventana];
        } else {
            medio = (ventana + 1) / 2;
            datosVentana = new float[ventana + 1];
        }

        for (int i = 0; i < medio; i++) {
            datosFiltrados[i] = datos[i];
        }
        for (int i = medio; i < datos.length - medio; i++) {
            int c = 0;
            for (int j = i - medio; j < i - medio + datosVentana.length; j++) {
                datosVentana[c++] = datos[j];
            }
            Arrays.sort(datosVentana);
            datosFiltrados[i] = datosVentana[medio];
        }
        return datosFiltrados;
    }


    public static Color getColor(short code) {
        if (code == 0) {
            return Color.BLACK;
        }
        if (code < 20) {
            return Color.GREEN;
        }
        if (code < 40) {
            return Color.YELLOW;
        }
        if (code < 60) {
            return Color.PINK;
        }
        if (code < 80) {
            return Color.ORANGE;
        } else {
            return Color.RED;
        }
    }
}
