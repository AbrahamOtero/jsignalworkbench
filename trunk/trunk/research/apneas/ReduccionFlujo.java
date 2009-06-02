package research.apneas;

import java.awt.Color;
import static java.lang.Math.*;
import java.util.Arrays;
import java.util.TreeSet;

import net.javahispano.fuzzyutilities.representation.TrapezoidalDistribution;
import net.javahispano.jsignalwb.*;
import research.beats.anotaciones.LimitacionAnotacion;

public class ReduccionFlujo {
    private TrapezoidalDistribution magnitudHipoapnea = new TrapezoidalDistribution(0, 0, 0.2F, 0.2F);
    private TrapezoidalDistribution magnitudApnea = new TrapezoidalDistribution(0.2F, 0.2F, 0.75F, 0.75F);
    private TrapezoidalDistribution duracionHipoapnea = new TrapezoidalDistribution(4, 10, 60, 140);
    private TrapezoidalDistribution duracionApnea = new TrapezoidalDistribution(4, 10, 100, 140);

    private float anchoVentanaValorMedioHipoApnea = 2;
    private int ventanaBasal = 200;

    private boolean considerarSoloOndasNegativas = false;
    private float ventanaCalculoDeltas = 1.5F;
    private float limiteEnergiaBajo = 0.5F;
    private float limiteEnergiaAlto = 2F;
    private int principioIntervaloFiltroEnerigia = 60;
    private int finIntervaloFiltroEnerigia = 70;
    private int principioIntervaloSegundoFiltroEnergia = 90;
    private int finIntervaloSegundoFiltroEnergia = 95;
    private final int ventanaParaCalcularLaEnergia = 3;
    private int persistencia = 2;

    //Fin De las variables de la BC
    private static float[] valorBasal;
    private float[] datos;
    private static float[] delta;
    private float frecuencia;
    private long fechaBase;

    private TreeSet<Intervalo> hipoapneasIntervalos = new TreeSet<Intervalo>();
    private TreeSet<Intervalo> apneasIntervalos = new TreeSet<Intervalo>();
    private Loggeer logger;
    private boolean apnea;
    private Signal senalDeFlujoNasal;
    private final boolean debugNivel1 = false;
    private final boolean debugNivel2 = false;


    public ReduccionFlujo() {
    }

    /**
     * Lanza la deteccion. Devuelve una lista con los episodios de apnea o de hipoapnea.
     *
     * @param nasal Signal
     * @param apnea boolean Cierto para detectar apneas, falso para las hipoapneas.
     * @return ArrayList
     */
    public TreeSet<Intervalo> detectar(Signal nasal, boolean apnea) {
        logger = new Loggeer(nasal);
        logger.setDebugNivel1(debugNivel1);
        logger.setDebugNivel2(debugNivel2);
        this.apnea = apnea;
        this.senalDeFlujoNasal = nasal;
        calcularReduccionesFlujo();
        if (!apnea) {
            return hipoapneasIntervalos;
        } else {
            return apneasIntervalos;
        }
    }

    private void calcularReduccionesFlujo() {
        if (considerarSoloOndasNegativas) {
            datos = filtrarOndasPositivos(false);
        }

        float[] sanalFiltrada = FiltroNasal.filtrarNasal(datos);
        logger.debugNivel2("Flujo filtrado", sanalFiltrada);

        delta = calcularMaximosMenosMinimos(datos);
        logger.debugNivel1("m-n", delta);

        float[] energia = calcularEnergia(sanalFiltrada);
        logger.debugNivel2("Energia", energia);

        this.valorBasal = calcularValorBasal(delta, energia);
        logger.debugNivel1("basal", valorBasal);

        float[] posibilidad = calcularPosibilidad(delta, valorBasal);
        logger.debugNivel1("Hipoapnea", posibilidad);
    }


    /**
     * Calcula la posibilidad. Requiere que se Hayan calculado antes las ventas y
     * los valores basales. Rellena huecos de tamanho pequenho y eliminar aquellos
     * episodios de posibilidad que no llegan a un cierto mnimo de duraci+n.
     *
     * @param delta float[]
     * @return float[]
     */
    private float[] calcularPosibilidad(float[] delta, float[] valorBasal) {
        //se calcula la posibilidad con el criterio de que haya un descenso
        //desde el valor basal de aproximadamente igual a
        //la correspondiente distribucin de posibilidad por el valor del valor basal en ese punto
        float[] posibilidad = new float[delta.length];
        int anchoVentana = (int) (frecuencia * anchoVentanaValorMedioHipoApnea);
        int finBucle = delta.length - anchoVentana / 2;

        for (int i = anchoVentana / 2; i < finBucle; i++) {
            if (!apnea) {
                posibilidad[i] = (this.magnitudHipoapnea.multiply(valorBasal[i])).
                                 evaluatepossibilityAt(Utilidades.
                        valorMedioEnTornoA(anchoVentana, i, delta));
            } else {
                posibilidad[i] = (this.magnitudApnea.multiply(valorBasal[i])).evaluatepossibilityAt(
                        Utilidades.
                        valorMedioEnTornoA(anchoVentana, i, delta));
            }
        }
        float[] posibilidadRellenada = Arrays.copyOf(posibilidad, posibilidad.length);
        logger.debugNivel2("Posibilidad sin rellenar", posibilidadRellenada);
//@cambio duplicado el tamanho de las ventanas
        Utilidades.rellenarHuecos(posibilidadRellenada, (int) (0.75 * frecuencia * persistencia), 99);
        Utilidades.rellenarHuecos(posibilidadRellenada, (int) (0.5 * frecuencia * persistencia));

        logger.debugNivel2("Posibilidad rellenada",
                           Arrays.copyOf(posibilidadRellenada, posibilidadRellenada.length));

        calculaEpisodios(posibilidad, posibilidadRellenada);
        return posibilidad;
    }

    /**
     * Una vez la posibilidad y la posibilidad rellenada han sido creadas buscamos los episodios.
     *
     * @param posibilidad float[]
     * @param posibilidadRellenada float[]
     */
    private void calculaEpisodios(float[] posibilidad, float[] posibilidadRellenada) {
        //ahora miramos si los episodios duran lo suficiente
        TrapezoidalDistribution duracion = this.duracionHipoapnea.multiply(frecuencia);
        short[] emphasisLevel = new short[posibilidadRellenada.length];
        for (int i = 0; i < posibilidadRellenada.length; i++) {
            int principio, fin, pos = 0;
            //busqueda de muestras consecutivas con posibilidadRellenada mayor que cero
            while (i < posibilidadRellenada.length && posibilidadRellenada[i] == 0) {
                i++;
            }
            //Si se acabo el array
            if (i == posibilidadRellenada.length) {
                break;
            }
            principio = i;
            //se encontr una muestra con posibilidadRellenada mayor que cero. Miramos cuanto dura
            while (i < posibilidadRellenada.length && posibilidadRellenada[i] > 0) {
                pos = (int) max(pos, posibilidadRellenada[i]);
                i++;
            }
            fin = i - 1;
            //miramos Si cumple el criterio de persistencia
            pos = min(pos, duracion.evaluatepossibilityAt(fin - principio));
            //actualizamos la posibilidadRellenada del array
            for (int j = principio; j <= fin; j++) {
                posibilidadRellenada[j] = min(posibilidadRellenada[j], pos);
            }
            //si la posibilidad global es mayor que cero creamos un intervalo
            if (pos > 0 && pos * ((fin - principio) / this.frecuencia) > 500) {
                generarIntervalo(principio, fin, pos, posibilidad, emphasisLevel);
            }
        }
        //  senalDeFlujoNasal.setEmphasisLevel(emphasisLevel);
    }

    /**
     * Dado el principio y el fin de un episodio de apnea o hipoapnea genera la marca correspondiente.
     * Puede decidir no generar el intervalo si su posibilidad y duracion son bajas.
     *
     * @param contador int
     * @param principio int
     * @param fin int
     * @param pos int
     * @param posibilidad float[]
     * @param en short[]
     */
    private void generarIntervalo(int principio, int fin, int pos, float[] posibilidad, short[] en) {
        //Calculo de la pos como agregacion
        int posibilidadIntervalo = 0;
        for (int i = principio; i < fin; i++) {
            posibilidadIntervalo += posibilidad[i];
        }

        posibilidadIntervalo /= (fin - principio);
        posibilidadIntervalo = Math.min(posibilidadIntervalo, pos);
        //si es demasiado mala
        if (apnea && posibilidadIntervalo * ((fin - principio) / this.frecuencia) < 500) {
            return;
        }
        for (int i = principio; i < fin; i++) {
            en[i] = (short) posibilidadIntervalo;
        }
        LimitacionFlujo episodio = new LimitacionFlujo(principio, fin, posibilidadIntervalo);
        episodio.setDatos(datos);
        episodio.setFrecuencia(frecuencia);
        episodio.setFechaBase(this.fechaBase);
        episodio.setValorBasal(this.valorBasal, this.delta);
        if (!apnea) {
            hipoapneasIntervalos.add(episodio);
        } else {
            this.apneasIntervalos.add(episodio);
        }

        if (!apnea) {
//generarMarca(episodio, senalDeFlujoNasal.getName(), "Hipoapnea", Color.BLUE);
        } else {
//generarMarca(episodio, senalDeFlujoNasal.getName(), "Apnea", Color.RED);
        }
    }

    static void generarMarca(Intervalo episodio, String nombreSenal, String titulo,
                             Color color) throws SignalNotFoundException {
        /*  if (titulo.equals("Apnea")) {
              color= Color.red;
          }
          else{
              color= Color.yellow;
          }*/
        LimitacionFlujo episodio2 = (LimitacionFlujo) episodio;
        // LimitacionFlujoMark marca = new LimitacionFlujoMark(episodio2, color);
        LimitacionAnotacion marca = new LimitacionAnotacion();
        long t = episodio.getPrincipioAbsoluto();
        marca.setMarkTime(t);
        long t2 = episodio.getFinAbsoluto();
        marca.setEndTime(t2);
        marca.setColor(Utilidades.getColor((short) episodio.getPosibilidad()));
        String texto = "Duracion: " + episodio.getDuracion() + "\n" + ", Posibilidad: " +
                       episodio.getPosibilidad();
        marca.setComentary(texto);
        marca.setTitle(titulo);
        marca.setColor(color);
        if (Color.RED == color) {
            marca.setTipo(LimitacionAnotacion.APNEA);
            marca.setColor(Color.RED);
        } else {

            marca.setTipo(LimitacionAnotacion.HIPOAPNEA);
            marca.setColor(Color.YELLOW);
        }
        marca.setAutomatica(true);
        JSWBManager.getJSWBManagerInstance().getSignalManager().addSignalMark(nombreSenal, marca);
    }

    private float[] calcularValorBasal(float[] delta, float[] indicadorIndices) {
        float[] copiaDelta = Arrays.copyOfRange(delta, 0, delta.length);
        //@cambio 1000
        int ventanaTemporal = (int) (3000 * frecuencia);
        //las ventanas temporales tendrn un cierto solape
        //de ahi que se reste el valor 200 en el paso del bucle
        for (int i = 0; i < datos.length - ventanaTemporal; i += ventanaTemporal) {
            float[] copiaDatos = Arrays.copyOfRange(copiaDelta, i, i + ventanaTemporal);
            Arrays.sort(copiaDatos);
            float valorLimite = 0;
            //cogemos el intervalo [X%, Y%] de los valores mayores de la derivada y calculamos su valor medio
            //de este modo no consideramos valores excesivamente grandes ni claramente pequenhos en la referencia
            int principio = copiaDatos.length * this.principioIntervaloSegundoFiltroEnergia / 100;
            int fin = copiaDatos.length * finIntervaloSegundoFiltroEnergia / 100;
            for (int j = principio; j < fin; j++) {
                valorLimite += copiaDatos[j];
            }
            valorLimite /= (fin - principio);
            valorLimite *= this.limiteEnergiaAlto;
            for (int j = i; j < i + ventanaTemporal; j++) {
                //si es mucho mayor que el termino medio
                if (copiaDelta[j] > valorLimite) {
                    copiaDelta[j] = valorLimite;
                }
            }
        }
        return Utilidades.calcularBasal(copiaDelta, indicadorIndices, (int) (frecuencia * this.ventanaBasal),
                                        (int) (frecuencia * this.ventanaBasal));
    }

    /**
     * Calcula la energia (realmente, la raiz cuadrada de la energia) y filtra los valores que sean claramente bajos
     * poniendolos a cero.
     *
     * @param datos float[]
     * @return float[]
     */
    private float[] calcularEnergia(float[] datos) {
        float[] energia = new float[datos.length];
        for (int i = 1; i < datos.length; i++) {
            energia[i] = Utilidades.energiaEnTornoA((int) (ventanaParaCalcularLaEnergia *
                    frecuencia), i, datos);
        }
        //iremos calculando los valores aceptables para la Energ%a en ventanas temporales de esta longitud
        int ventanaTemporal = (int) (120 * frecuencia);
        //las ventanas temporales tendrn un cierto solape
        for (int i = 0; i < datos.length - ventanaTemporal; i += ventanaTemporal / 2) {
            float[] copiaDatos = Arrays.copyOfRange(energia, i, i + ventanaTemporal);
            Arrays.sort(copiaDatos);
            float valorLimiteEnergia = 0;
            //cogemos un intervalo de los valores mayores de la Energia y calculamos su valor medio
            //de este modo no consideramos valores excesivamente grandes ni pequenhos en la referencia
            int principio = copiaDatos.length * principioIntervaloFiltroEnerigia / 100;
            int fin = copiaDatos.length * finIntervaloFiltroEnerigia / 100;
            for (int j = principio; j < fin; j++) {
                valorLimiteEnergia += copiaDatos[j];
            }
            valorLimiteEnergia /= (fin - principio);
            for (int j = i + ventanaTemporal / 2 - 1; j > i; j--) {
                if (energia[j] < limiteEnergiaBajo * valorLimiteEnergia) {
                    energia[j] = 0;
                } else {
                    energia[j] = energia[j];
                }
            }
        }
        return energia;
    }

    /**
     * Trata de estimar la cantidad de flujo inspirado/espirado en cada instante
     * calculando la resta entre el valor minimo y maximo de la senhal de flujo
     * oronasal en un intervalo cuya duracion es aproximadamente igual a una
     * inspiracion/expiracion.
     *
     * @return float[]
     */
    private float[] calcularMaximosMenosMinimos(float[] datosNasal) {
        float[] delta = new float[datosNasal.length];
        //cogemos una ventana y calculamos
        //el minimo y el maximo. La diferencia entre ambos sera el valor
        //asociado al flujo inspirado en ese intervalo
        int ventanaDelta = (int) (ventanaCalculoDeltas * frecuencia);
        ventanaDelta /= 2;
        for (int i = ventanaDelta; i < datosNasal.length - ventanaDelta; i++) {
            float maximo = Float.NEGATIVE_INFINITY,
                           minimo = Float.POSITIVE_INFINITY;
            for (int j = i - ventanaDelta; j < i + ventanaDelta; j++) {
                maximo = max(maximo, datosNasal[j]);
                minimo = min(minimo, datosNasal[j]);
            }
            for (int j = i - ventanaDelta; j < i + ventanaDelta; j++) {
                delta[j] = maximo - minimo;
            }
        }
        return delta;
    }

    /**
     * solo considera las partes Positivas o negativas de la onda.
     *
     * @return float[]
     */
    private float[] filtrarOndasPositivos(boolean positivo) {
        float[] nuevosDatos = new float[datos.length];
        for (int i = 1; i < datos.length; i++) {
            if (positivo) {
                if (datos[i] < 0) {
                    nuevosDatos[i] = 0;
                } else {
                    nuevosDatos[i] = datos[i];
                }
            } else {
                if (datos[i] > 0) {
                    nuevosDatos[i] = 0;
                } else {
                    nuevosDatos[i] = datos[i];
                }
            }
        }
        return nuevosDatos;
    }


    public void setMagnitudApnea(TrapezoidalDistribution apnea) {
        this.magnitudApnea = apnea;
    }

    public void setDatos(float[] datos) {
        this.datos = datos;
    }

    public void setFechaBase(long fechaBase) {
        this.fechaBase = fechaBase;
    }

    public void setFrecuencia(float frecuencia) {
        this.frecuencia = frecuencia;
    }

    public void setMagnitudHipoapnea(TrapezoidalDistribution hipoapnea) {
        this.magnitudHipoapnea = hipoapnea;
    }

    public void setAnchoVentanaValorMedioHipoApnea(float anchoVentanaValorMedioHipoApnea) {
        this.anchoVentanaValorMedioHipoApnea = anchoVentanaValorMedioHipoApnea;
    }

    public void setFinVentanaBasalApnea(int finVentanaBasalFlujoApnea) {
        this.ventanaBasal = finVentanaBasalFlujoApnea;
    }

    public void setPrincipioVentanaBasalApnea(int principioVentanaBasalFlujoApnea) {
        this.ventanaBasal = principioVentanaBasalFlujoApnea;
    }

    public void setVentanaCalculoDeltas(float ventana) {
        this.ventanaCalculoDeltas = ventana;
    }

    public void setLimiteEnergiaBajo(int limiteEnergia) {
        this.limiteEnergiaBajo = ((float) limiteEnergia) / 100;
    }

    public void setConsiderarSoloOndasNegativas(boolean considerarSoloNegativos) {
        this.considerarSoloOndasNegativas = considerarSoloNegativos;
    }

    public void setDuracionApnea(TrapezoidalDistribution duracionApnea) {
        this.duracionApnea = duracionApnea;
    }

    public void setDuracionHipoapnea(TrapezoidalDistribution duracionHipoapnea) {
        this.duracionHipoapnea = duracionHipoapnea;
    }

    public void setFinIntervaloFiltroEnerigia(int finIntervaloFiltroEnerigia) {
        this.finIntervaloFiltroEnerigia = finIntervaloFiltroEnerigia;
    }

    public void setPrincipioIntervaloFiltroEnerigia(int principioIntervaloFiltroEnerigia) {
        this.principioIntervaloFiltroEnerigia = principioIntervaloFiltroEnerigia;
    }

    public void setFinIntervaloSegundoFiltroEnergia(int principioIntervaloSegundoFiltroDerivada) {
        this.finIntervaloSegundoFiltroEnergia = principioIntervaloSegundoFiltroDerivada;
    }

    public void setPersistencia(int persistencia) {
        this.persistencia = persistencia;
    }

    public static float[] getValorBasal() {
        return valorBasal;
    }

    public static float[] getDelta() {
        return delta;
    }

}
