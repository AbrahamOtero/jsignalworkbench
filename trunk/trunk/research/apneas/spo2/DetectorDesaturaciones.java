package research.apneas.spo2;

import static java.lang.Math.*;
import java.util.Arrays;
import net.javahispano.jsignalwb.utilities.TimePositionConverter;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;

/**
 *
 * @author Abraham Otero
 * @version 0.5
 */
public class DetectorDesaturaciones {
    //variables para almacenar los datos en un buffer y para suavizar la entrada
    private final int tamanoBuffer = 120; //120 segundos
    private float[] datos = new float[tamanoBuffer];
    private float pasado, presente = 0, futuro = 0; //para calcular la mediana de tres

    //Para el calculo del valor basal
    private float valorBasal = 100; //aqui se almacena el valor basal
    private final int tiempoCalculoBasal = 10; //el valor basal se refresca cada 10 segundos

    //para llevar cuenta del tiempo
    private long origenDeTiempo = 0; //origen de tiempos
    private int tiempo = -1; //contador de toda las muestras que hemos visto
    private int indice = -1; //posicion del buffer donde se guarda la siguiente muestra

    //variables que controlan los distintos estados de la deteccoin
    private boolean cicloActivado = false; //true cuando el buffer se ha llenado
    private boolean epezadaDesat = false; //true cuando parece que ha comenzado una desaturacion
    private boolean notificadoInicio = false; //true cuando estamos seguros de que ha comenzado y se ha notificado
    private boolean buscandoFinDesat = false; //true cuando estamos buscando el fin de la desaturacion (rampla de su vida)
    private boolean caidaMinimaDeLaDesatCumplida = false; //true cuando una desaturacion  en proceso presenta la caida minima
    private boolean duracionMinimaDeLaDesat = false; //true cuando una desaturacion  en proceso presenta la duracion minima
    private int desatFinContador = 0; //contador para ayudar a buscar el fin de una desaturacion
    private int desatFinContador2 = 0; //contador para ayudar a buscar el fin de una desaturacion

    //para ir almacenando informacion sobre la desaturacion cuando todava esta en detencion
    private int tiempoPrincipioDesat; //tiempo al principio de la desaturacion
    private float valorPrincipioDesat; //valor al principio
    private float valorMinDesat = Float.MAX_VALUE; //Valor minimo que ha alcanzado

    //para el calculo del grado de posibilidad de cada desaturacion

    //comienzo del soporte de la distribucion de posibilidad trapezoidal que indica la caida minima
    //de una desaturacion para ser considerada relevante
    private final float limiteDesaturacionP0 = 2;
    //comienzo del nucleo de la distribucin anterior; el fin del nucleo es infinito
    private final float limiteDesaturacionP1 = 10;
    //comienzo del soporte de la distribucion de posibilidad trapezoidal que indica la
    // duracion minima de una desaturacion para ser considerada relevante
    private final float limiteDuracionP0 = 5;
    //comienzo del nucleo de la distribucin anterior; el fin del nucleo es infinito
    private final float limiteDuracionP1 = 12;

    //variables auxiliares; son valores precalculados Para no tener que calcularlos varias veces
    private final float mTiempoDistribucionTrapezoidal = 1 /
            (limiteDuracionP1 - limiteDesaturacionP0);
    private final float bTiempoDistribucionTrapezoidal =
            -limiteDesaturacionP0 * mTiempoDistribucionTrapezoidal;
    private final float mMagnitudDistribucionTrapezoidal =
            1 / (limiteDesaturacionP1 - limiteDesaturacionP0);
    private final float bMagnitudDistribucionTrapezoidal =
            -limiteDesaturacionP0 * mTiempoDistribucionTrapezoidal;
    private int valoVasalMinimo = 1;
    private float valoVasalMaximo = 100;


    /**
     * Se le van pasando las muestras de la SpO2. Devuelve null cuando no haya deteccion. Devuelve
     * un objeto de tipo Desaturacion cuando esta seguro de que ha identificado una desaturacion; a
     * continuacion devuelve null hasta que esta seguro que la desaturacion identificada ha
     * terminado. En ese momento, devuelve un segundo objeto de tipo Desaturacion en el cual se
     * reune toda la informacion relevante de la desaturacion. La primera debe ser considerada como
     * un mero aviso sin informacion (sin valor de posibilidad asociada, sin valor minimo de
     * desaturacion...).
     *
     * @param nuevoDato float
     * @return Desaturacion
     */
    public Desaturacion anadeDato(float nuevoDato) {
        indice++;
        tiempo++;
        //Suabizambos mediante una mediana calculada con la muestra anterior y siguiente
        pasado = presente;
        presente = futuro;
        futuro = nuevoDato;
        if (tiempo < 3) {
            datos[indice] = nuevoDato; //guardamos el dato
            return null;
        }
        nuevoDato = medianaDeTres();
        if (indice == tamanoBuffer) { //si se lleno
            //comienza a funcionar el comportamiento cilico
            if (!cicloActivado) {
                cicloActivado = true;
            }
            indice = 0; //hay que volver al principio
        }
        datos[indice] = nuevoDato; //guardamos el dato
        // cada X segundos actualizamos al valor basal
        if (tiempo % tiempoCalculoBasal == 0) {
            calculaValorBasal();
        }
        if (epezadaDesat) {
            valorMinDesat = min(valorMinDesat, datos[indice]);
        }
        return compruebaDesaturacion();
    }

    /**
     * Metodo que indica que ya no hay mas datos o que se para la deteccion por cualquier motivo. En
     * caso de estar a medio de detectar una desaturacion, se devolvera dicha desaturacion. En caso
     * contrario, el metodo de volver a null. Una vez este metodo ha sido invocado el detector queda
     * inservible y debera descartarse.
     *
     * @return Desaturacion
     */
    public Desaturacion paraDeteccion() {
        if (notificadoInicio) {
            return transicionASinDeteccion();
        }

        else {
            return null;
        }

    }

    /**
     * Calcula la mediana de las variables "pasado", "presente" y " futuro". Se usa para filtrar los
     * datos nuevos.
     *
     * @return float
     */
    private float medianaDeTres() {
        float d = 0.0F;
        if (pasado <= presente && futuro >= presente || pasado >= presente && futuro <= presente) {
            d = presente;
        } else if (pasado <= presente && pasado >= futuro || pasado >= presente && pasado <= futuro) {
            d = pasado;
        } else {
            d = futuro;
        }
        return d;
    }

    /**
     * Calcula el valor basal. Para ello ordena una copia de los datos del buffer de menor a mayor,
     * y calcula la media del 10% de los datos mayores.
     */
    private void calculaValorBasal() {
        float[] datosCopia;
        //Si an no se lleno el buffer
        if (!cicloActivado) {
            if (tiempo < 10) { //si no hay ni 10 muestras devolvemos la primera y punto
                valorBasal = datos[0];
                return;
            }
            datosCopia = Arrays.copyOfRange(datos, 0, indice);
        } else {
            //En entornos con memoria limitada quizas sea mejor reutilizar un array y no crear nuevos
            datosCopia = (float[]) datos.clone();
        }
        Arrays.sort(datosCopia);
        valorBasal = calculaMedia(datosCopia);
        valorBasal = max(valorBasal, valoVasalMinimo);
        valorBasal = min(valorBasal, valoVasalMaximo);
    }

    /**
     * Calcula la media del 10% de los datos de mayor valor del buffer copia.
     *
     * @param tmp float[]
     * @return float
     */
    private float calculaMedia(float[] tmp) {
        float suma = 0;
        int fin, principio;
        if (tiempo < tamanoBuffer) {
            fin = tmp.length - tmp.length / 10;
            principio = tmp.length - 1;
        } else {
            fin = tamanoBuffer - tamanoBuffer / 10;
            principio = tamanoBuffer - 1;
        }
        for (int i = principio; i >= fin; i--) {
            suma += tmp[i];
        }
        float vBasal = suma / (principio + 1 - fin);
        return vBasal;
    }

    /**
     * Esta es la funcion que realmente controla los distintos estados por los cuales va pasando la
     * deteccion de los episodios de desaturacion. anadeDato delega todo el trabajo en ella.
     *
     * @return Desaturacion
     */
    private Desaturacion compruebaDesaturacion() {
        if (valorBasal - datos[indice] > limiteDesaturacionP0) {
            //Empezo otra antes de que las heuristicas marcasen el fin de la anterior
            if (buscandoFinDesat) {
                if (notificadoInicio) {
                    return transicionASinDeteccion();
                }
                cancelarBusquedaFin();
                return null;
            }
            //Si empieza una deteccion
            if (!epezadaDesat) {
                epezadaDesat = true;
                refinarPrincipio();
            }
            if (epezadaDesat && !notificadoInicio) {
                //si al menos se ha producido una cada de limiteDesaturacionP0-1
                if (valorPrincipioDesat - datos[indice] > limiteDesaturacionP0 - 1) {
                    caidaMinimaDeLaDesatCumplida = true;
                } else {
                    caidaMinimaDeLaDesatCumplida = false;
                }
                if (tiempo - tiempoPrincipioDesat > limiteDuracionP0) {
                    duracionMinimaDeLaDesat = true;
                } else {
                    caidaMinimaDeLaDesatCumplida = false;
                }
                if (caidaMinimaDeLaDesatCumplida && duracionMinimaDeLaDesat) {
                    return crearDesaturacionInicial();
                }
                return null;
            }
        } else if (epezadaDesat && notificadoInicio) {
            return comprobarTransicionANoDeteccionDesdeBusquedaDeFin();
        } else if (epezadaDesat) {
            caidaMinimaDeLaDesatCumplida = false;
            duracionMinimaDeLaDesat = false;
            epezadaDesat = false;
            return null;
        }
        return null;
    }

    /**
     * Cuando comienza a haber evidencia de que hay una desaturacion (se cae en lo que corresponda
     * por debajo del nivel basal) se llama a este metodo para ver donde empezo realmente la caida;
     * el proposito de este metodo es "subir" por una pendiente negativa para encontrar el punto en
     * el que comenzo el descenso.
     *
     * @return int
     */
    private int refinarPrincipio() {
        int principio = tiempo;
        int indice0 = indice;
        int indice2 = this.corrigeIndice(indice - 2);
        int indice6 = this.corrigeIndice(indice2 - 4);
        int indice10 = this.corrigeIndice(indice6 - 4);
        int indice14 = this.corrigeIndice(indice10 - 4); //miramos hasta 14 sec atras

        while (datos[indice0] < datos[indice2] || datos[indice0] < datos[indice6] ||
               datos[indice0] < datos[indice10] || datos[indice0] < datos[indice14]) {
            principio--;
            indice0 = corrigeIndice(--indice0); //OJO tiene que ser *pre*decremento
            indice2 = corrigeIndice(--indice2);
            indice6 = corrigeIndice(--indice6);
            indice10 = corrigeIndice(--indice10);
            indice14 = corrigeIndice(--indice14);
        }
        tiempoPrincipioDesat = principio;
        valorPrincipioDesat = datos[indice0];
        return tiempoPrincipioDesat;
    }

    /**
     * Se encarga de crear la desaturacion inicial y realiza la transicion entre los estados
     * "Deteccion Iniciada" y "Deteccion Iniciada y Notificada".
     *
     * @return Desaturacion
     */
    private Desaturacion crearDesaturacionInicial() {
        notificadoInicio = true;
        caidaMinimaDeLaDesatCumplida = false;
        duracionMinimaDeLaDesat = false;
        return new Desaturacion(origenDeTiempo, tiempoPrincipioDesat);
    }

    /**
     * Comprueba si se dan las condiciones para pasar de los estados "Buscando Final" a "Sin
     * Desaturacion". En caso afirmativo, realiza la transicion.
     *
     * @return Desaturacion
     */
    private Desaturacion comprobarTransicionANoDeteccionDesdeBusquedaDeFin() {
        buscandoFinDesat = true;
        //@cambio  valian 2 y 4
        if (desatFinContador >= 6 || desatFinContador2 >= 8) {
            return transicionASinDeteccion();
        }
        desatFinContador2++;
        if (datos[indice] - 0.5F > datos[corrigeIndice(indice - 1)]) {
            desatFinContador = 0;
            desatFinContador2 = 0;
            return null;
        } else {
            desatFinContador++;
        }
        return null;
    }

    /**
     * Crea la desaturacion de notificacion de final del desaturacion y hace que el estado del
     * detector pase a ser "Sin Deteccion".
     *
     * @return Desaturacion
     */
    private Desaturacion transicionASinDeteccion() {
        Desaturacion d = crearDesaturacionFinal(tiempo);
        cancelarBusquedaFin();
        return d;
    }

    /**
     * Crea la desaturacion de final de desaturacion.
     *
     * @param fin int
     * @return Desaturacion
     */
    private Desaturacion crearDesaturacionFinal(int fin) {
        int f = refinarFin(fin);
        Desaturacion d = new Desaturacion(origenDeTiempo, tiempoPrincipioDesat, f);

        if (f < tiempoPrincipioDesat) {
            return null;
        }

        d.setValorMinimo(valorMinDesat);
        d.setValorBasal(valorBasal);
        calculaCompatibilidad(d);
        return d;
    }

    /**
     * El instante en el que "estamos seguros" de que la desaturacion ha terminado puede ser
     * posterior al instante en el que realmente ha terminado. Este metodo trata de buscar el
     * momento en el que realmente termino.
     *
     * @param fin int
     * @return int
     */
    private int refinarFin(int fin) {
        int indice1 = indice;
        int indice2 = this.corrigeIndice(indice - 1);
        while (datos[indice1] <= datos[indice2]) {
            fin--;
            indice1--;
            indice2--;
            indice1 = corrigeIndice(indice1);
            indice2 = corrigeIndice(indice2);
        }
        return fin;
    }

    /**
     * Evalua los criterios borrosos de duracion y de caida de la desaturacion.
     *
     * @param d Desaturacion
     */
    private void calculaCompatibilidad(Desaturacion d) {
        int duracion = d.getFin() - d.getComienzo();
        float posibilidadDuracion = evaluarDuracion(duracion);
        float posibilidadCaida = evaluarCaida();
        d.setPos(min(posibilidadDuracion, posibilidadCaida));
    }

    /**
     * Comprueba el grado de cumplimiento borroso de la duracion de una desaturacion.
     *
     * @param duracion int
     * @return float
     */
    private float evaluarDuracion(int duracion) {
        if (duracion <= this.limiteDuracionP0) {
            return 0;
        } else if (duracion >= this.limiteDuracionP1) {
            return 1;
        } else {
            return mTiempoDistribucionTrapezoidal * duracion + bTiempoDistribucionTrapezoidal;
        }
    }

    /**
     * Comprueba el grado de satisfaccion borroso de la magnitud de la caida de una desaturacion.
     *
     * @return float
     */
    private float evaluarCaida() {
        float caida = valorPrincipioDesat - valorMinDesat;
        if (caida <= this.limiteDesaturacionP0) {
            return 0;
        } else if (caida >= this.limiteDesaturacionP1) {
            return 1;
        } else {
            return mMagnitudDistribucionTrapezoidal * caida + bMagnitudDistribucionTrapezoidal;
        }
    }

    /**
     * Modifica el estado interno del detector para que pase a ser "Sin Deteccion".
     */
    private void cancelarBusquedaFin() {
        buscandoFinDesat = false;
        desatFinContador = 0;
        desatFinContador2 = 0;
        epezadaDesat = false;
        notificadoInicio = false;
        valorMinDesat = Float.MAX_VALUE;
    }

    /**
     * Metodo auxiliar que corrige el valor de un indice que apunta al buffer interno
     * de datos y que se le pasa con argumento. Este indice siempre se decrementa
     * (no tiene sentido ir hacia el futuro en los datos). Cuando el indice alcanza cero,
     * debo volver a tamanoBuffer -1;
     *
     * @param i int
     * @return int[]
     */
    private int corrigeIndice(int i) {
        if (i < 0) {
            return tamanoBuffer + i;
        }
        return i;
    }

    public long getTiempoInicial() {
        return origenDeTiempo;
    }

    /**
     * Permite establecer un origen de tiempos medido en milisegundos del 1 de enero de 1970. Por
     * defecto es 0. Solo se usa para situar de modo absoluto los objetos de tipo desaturacion.
     *
     * @param tiempoInicial long
     */
    public void setTiempoInicial(long tiempoInicial) {
        this.origenDeTiempo = tiempoInicial;
    }

    public float getValorBasal() {
        return valorBasal;
    }

}
