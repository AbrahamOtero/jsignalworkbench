package research.apneas;

import java.awt.Color;
import static java.lang.Math.*;
import java.util.*;
import java.util.List;

import net.javahispano.fuzzyutilities.representation.TrapezoidalDistribution;
import net.javahispano.jsignalwb.*;
import research.beats.anotaciones.DesaturacionAnotacion;
import research.beats.anotaciones.LimitacionAnotacion;

public class DesatDetector {

    //par\u2193metros relacionados con la base de conocimiento
    private TrapezoidalDistribution pendienteAscenso;
    private TrapezoidalDistribution pendienteDescenso;
    private TrapezoidalDistribution descensoAdmisibleRespectoBasal;
    private TrapezoidalDistribution valorAdmisibleDesaturacionRespectoBasal;

    private int ventanaFiltroMediano = 3;
    private int rellenarValoresPorDebajoDe;

    private int ventanaBasalSatO2 = 90;
    private int ventanaPendientesSaO2 = 3;
    //Se permiten x segundos de gap con pos 0 y se sigue considerando
    //parte del mismo ascenso o descenso
    private float persistencia = 2;
    //se filtran los valores menores que este limite en el de posibilidades
    private float anularPosibilidaArraysPorDebajoDe = 5;
    //si un episodio de desaturacion duran m\u2663s que este limite
    //se supone que "algo va mal" y se desprecia el comienzo actual
    //que se estaba considerando para el episodio
    private int duracionMaximaEpisodiosDesaturacion;

    //fin par\u2193metros relacionados con la base de conocimiento

    //almacenan los episodios de descenso y ascenso desaturacion,
    //junto con los episodios de desaturacion

    private TreeSet<Intervalo> descensos = new TreeSet<Intervalo>();
    private TreeSet<Intervalo> ascensos = new TreeSet<Intervalo>();
    private ArrayList<EpisodioDesaturacion> episodios = new ArrayList<
            EpisodioDesaturacion>();
    private Signal satO2;
    private Loggeer logger;
    private static float[] valorBasal;
    private float[] datos;
    private float[] datosFiltrados;
    private float[] posibilidadPrincipio;
    float[] posibilidadFin;
    private SignalManager sm;
    private float frecuencia;
    private long fechaBase;
    private final boolean debugNivel1 = false;
    private final boolean debugNivel2 = false;


    public DesatDetector(SignalManager sm, Signal s) {
        this.sm = sm;
        logger = new Loggeer(s);
        logger.setDebugNivel1(debugNivel1);
        logger.setDebugNivel2(debugNivel2);
        satO2 = s;
        frecuencia = satO2.getSRate();
        this.fechaBase = satO2.getStart();
        ventanaBasalSatO2 *= satO2.getSRate();
    }


    /**
     * detectar
     */
    public ArrayList<EpisodioDesaturacion> detectar() {
        datosFiltrados = Utilidades.calculaMediaMovilClone(datos, (int) (this.ventanaFiltroMediano * frecuencia));
        logger.debugNivel1("SatO2 filtrada", datosFiltrados);

        valorBasal = Utilidades.calcularBasalMaximos(datosFiltrados, ventanaBasalSatO2, ventanaBasalSatO2 / 5);
        this.logger.debugNivel1("Valor basal", valorBasal);

        posibilidadPrincipio = calcularPrincipio(datosFiltrados);
        compatibilidadValorPrincipio(posibilidadPrincipio, datosFiltrados, descensoAdmisibleRespectoBasal);
        compatibilidadValorFin(posibilidadPrincipio, datosFiltrados, valorAdmisibleDesaturacionRespectoBasal);
        Utilidades.ponerACeroPorDebajoDe(posibilidadPrincipio, anularPosibilidaArraysPorDebajoDe);
        Utilidades.rellenarHuecos(posibilidadPrincipio, (int) (4 * frecuencia));
        logger.debugNivel1("Principiov", posibilidadPrincipio);

        posibilidadFin = calcularFin(datosFiltrados);
        compatibilidadValorFin(posibilidadFin, datosFiltrados, this.descensoAdmisibleRespectoBasal);
        compatibilidadValorPrincipio(posibilidadFin, datosFiltrados, this.valorAdmisibleDesaturacionRespectoBasal);
        Utilidades.ponerACeroPorDebajoDe(posibilidadFin, anularPosibilidaArraysPorDebajoDe);
        Utilidades.rellenarHuecos(posibilidadFin, (int) (4 * frecuencia));
        logger.debugNivel1("Finv", posibilidadFin);

        //construimos intervalos de principio y fin para los ascensos y descensos
        calculaIntervalos(posibilidadPrincipio, this.descensos);
        calculaIntervalos(posibilidadFin, this.ascensos);
        //construimos los episodios del desaturacio
        calcularEpisodios();
        eliminarEpisodiosConPosibilidadNula();
        resuelveSolapes();
        generarMarcasYEnfasis(episodios);
        return episodios;

    }

    private void eliminarEpisodiosConPosibilidadNula() {
        List episodiosABorrar = new LinkedList();
        for (Intervalo i : episodios) {
            if (i.getPosibilidad() == 0) {
                episodiosABorrar.add(i);
            }
        }
        episodios.removeAll(episodiosABorrar);
    }

    /**
     * Una vez todos los episodios de desaturacion han sido identificados correctamente, a veces
     * (muy raramente) existen solapese entre el final de un episodio y el principio del siguiente.
     * Este metodo, visualmente, Resuelve ese problema.
     */
    private void resuelveSolapes() {
        Iterator<EpisodioDesaturacion> i = episodios.iterator();
        EpisodioDesaturacion anterior, siguiente = null;
        if (i.hasNext()) {
            siguiente = i.next();
        } while (i.hasNext()) {
            anterior = siguiente;
            siguiente = i.next();
            if (anterior.getFin() > siguiente.getPrincipio()) {
                //los desplazamos su segundo
                anterior.setFin(siguiente.getPrincipio() - (int) frecuencia);
            }
        }
    }

    /**
     * calcula los episodios de desaturacion aparte de la informaci+n de los
     * ascensos y los descensos sobre la senhal desaturacin.
     */
    private void calcularEpisodios() {
        //analizamos toda la senhal
        Intervalo intervaloOrigen = new Intervalo(0, 0, 0); //para obtener el primer elemento
        Intervalo descenso = null, ascenso = null, siguienteDescenso = null, siguienteAscenso = null;
        descenso = descensos.ceiling(intervaloOrigen);
        if (descenso != null) {
            siguienteDescenso = descensos.ceiling(descenso.desplazaEnTiempo(1));
        }
        ascenso = ascensos.ceiling(intervaloOrigen);
        if (ascenso != null) {
            siguienteAscenso = ascensos.ceiling(ascenso.desplazaEnTiempo(1));
        }
        //despues de pedir un nuevo ascenso o descenso siempre se vuelve al bucle
        //es aqui donde se comprueba si no hay mas elementos.
        while (descenso != null && ascenso != null && siguienteDescenso != null && siguienteAscenso != null) {
            //Si el ascenso comienza antes del descenso podemos despreciarlo por no estar emparejado
            //**********************/***\***/***************
            if (ascenso.getPrincipio() < descenso.getPrincipio()) {
                ascensos.remove(ascenso);
                ascenso = ascensos.ceiling(intervaloOrigen);
                if (ascenso != null) {
                    siguienteAscenso = ascensos.ceiling(ascenso.desplazaEnTiempo(1));
                }
                continue;
            }
            //si el descenso esta muy lejos del siguiente ascenso pasamos del descenso
            //*************************\******************\****/*******
            else if (descenso.distanciaCon(ascenso) > this.duracionMaximaEpisodiosDesaturacion) {
                //nos olvidamos del actual descenso
                descensos.remove(descenso);
                descenso = descensos.ceiling(intervaloOrigen);
                if (descenso != null) {
                    siguienteDescenso = descensos.ceiling(descenso.desplazaEnTiempo(1));
                }
                continue;
            }
            //si el siguiente descenso empieza antes de que empiece el ascenso,
            //puede ser posible fundir ambos descensos
            //******************\*\***/*******
            else if (siguienteDescenso.getFin() < ascenso.getFin()) {
                //si el actual ascenso y el siguiente descenso estn bastante proximos
                //fundimos el actual descenso con el siguiente descenso a no ser que haya una clara recuperacin en medio
                if (descenso.distanciaCon(siguienteDescenso) < 15) {
                    boolean hayUnClaroValorAlto = false;
                    for (int i = descenso.getFin() + 1; i < siguienteDescenso.getPrincipio(); i++) {
                        if (valorBasal[i] - (datosFiltrados[i - 1] + datosFiltrados[i]
                                             + datosFiltrados[i + 1]) / 3 < 2) {
                            hayUnClaroValorAlto = true;
                        }
                    }
                    if (hayUnClaroValorAlto) {
                        //entonces suponemos que el Descenso debe ser borrado
                        descensos.remove(descenso);
                        descenso = descensos.ceiling(intervaloOrigen);
                        if (descenso != null) {
                            siguienteDescenso = descensos.ceiling(descenso.desplazaEnTiempo(1));
                        }
                        continue;
                    }
                    //Si no hay un ascenso claro Fundimos ambos descensos, a pesar de que estan lejos
                    descenso.setFin(siguienteDescenso.getFin());
                    descenso.setPosibilidad(Math.max(descenso.getPosibilidad(), siguienteDescenso.getPosibilidad()));
                    descensos.remove(siguienteDescenso);
                    siguienteDescenso = descensos.ceiling(descenso.desplazaEnTiempo(1));
                    continue;
                }

                //Si no estaban suficientemente cerca para unir directamente
                else {
                    //miramos si hay alguna recuperacion en medio
                    boolean hayRecuperacionEnMedio = false;
                    for (int i = descenso.getFin(); i < siguienteDescenso.getPrincipio(); i++) {
                        if (datosFiltrados[i] + 4 > valorBasal[i]) {
                            hayRecuperacionEnMedio = true;
                            break;
                        }
                    }
                    //Si toda la senhal Presentan un valor bajo entre ambos los unimos
                    if (!hayRecuperacionEnMedio) {
                        //Si  hay un ascenso entre ambos creamos un episodio y continuamos
                        //(la condicion es un poco rara pero a veces se da  en algun registro)
                        //si se borrarse esto apenas habria una perdida de precision en el algoritmo
                        if (descenso.getFin() < ascenso.getPrincipio() &&
                            ascenso.getPrincipio() < siguienteDescenso.getPrincipio()) {
                            construyeEpisodioDesaturacion(descenso, ascenso);
                            descenso = descensos.ceiling(intervaloOrigen);
                            siguienteDescenso = descensos.ceiling(descenso.desplazaEnTiempo(1));
                            ascenso = ascensos.ceiling(intervaloOrigen);
                            siguienteAscenso = ascensos.ceiling(ascenso.desplazaEnTiempo(1));
                            continue;
                        }
                        //si no hubo recuperacin los fundimos
                        descenso.setFin(siguienteDescenso.getFin());
                        descenso.setPosibilidad(Math.max(descenso.getPosibilidad(), siguienteDescenso.getPosibilidad()));
                        descensos.remove(siguienteDescenso);
                        siguienteDescenso = descensos.ceiling(descenso.desplazaEnTiempo(1));
                        continue;
                    } else { //Sino Al recuperacion borramos Uno de ellos
                        //si el siguiente descenso comienza antes de que comience el  ascenso
                        //supondremos que la asociacin correcta es siguiente descenso con ascenso
                        if (siguienteDescenso.getPrincipio() < ascenso.getPrincipio()) {
                            descensos.remove(descenso);
                            descenso = descensos.ceiling(intervaloOrigen);
                            if (descenso != null) {
                                siguienteDescenso = descensos.ceiling(descenso.desplazaEnTiempo(1));
                            }
                            continue;
                        }
                        //en caso contrario supondremos que el siguiente descenso es un artefacto
                        else {
                            descensos.remove(siguienteDescenso);
                            siguienteDescenso = descensos.ceiling(descenso.desplazaEnTiempo(1));
                            continue;
                        }
                    }
                }

            }

            //y si el siguiente ascenso se situa antes del siguiente descenso y estan acerca
            //**************************\***/*/*******
            else if (siguienteAscenso.getFin() < siguienteDescenso.getFin()) {
                //Si el ascenso y el siguiente ascenso esta suficientemente cerca los fundimos
                if (ascenso.distanciaCon(siguienteAscenso) < 15) {
                    boolean hayUnClaroValorAlto = false;
                    for (int i = ascenso.getFin() + 1; i < siguienteAscenso.getPrincipio(); i++) {
                        if (valorBasal[i] - (datosFiltrados[i - 1] + datosFiltrados[i]
                                             + datosFiltrados[i + 1]) / 3 < 0) {
                            hayUnClaroValorAlto = true;
                        }
                    }
                    if (hayUnClaroValorAlto) {
                        //entonces suponemos que el Descenso debe ser borrado
                        ascensos.remove(siguienteAscenso);
                        ascenso = ascensos.ceiling(intervaloOrigen);
                        if (ascenso != null) {
                            siguienteAscenso = ascensos.ceiling(ascenso.desplazaEnTiempo(1));
                        }
                        continue;
                    }
                    //Si no hay un ascenso claro Fundimos ambos descensos
                    ascenso.setFin(siguienteAscenso.getFin());
                    ascenso.setPosibilidad(Math.max(ascenso.getPosibilidad(),
                            siguienteAscenso.getPosibilidad()));
                    ascensos.remove(siguienteAscenso);
                    siguienteAscenso = ascensos.ceiling(ascenso.desplazaEnTiempo(1));
                }

                //Si no estaban suficientemente cerca para unir directamente
                else {
                    //miramos si hay alguna recuperacion en medio
                    boolean hayRecuperacionEnMedio = false;
                    for (int i = ascenso.getFin(); i < siguienteAscenso.getPrincipio(); i++) {
                        if (datosFiltrados[i] + 4 > valorBasal[i]) {
                            hayRecuperacionEnMedio = true;
                            break;
                        }
                    }
                    //Si toda la senhal Presentan un valor bajo entre ambos los unimos
                    if (!hayRecuperacionEnMedio) {
                        ascenso.setFin(siguienteAscenso.getFin());
                        ascenso.setPosibilidad(Math.max(ascenso.getPosibilidad(), siguienteAscenso.getPosibilidad()));
                        ascensos.remove(siguienteAscenso);
                        siguienteAscenso = ascensos.ceiling(ascenso.desplazaEnTiempo(1));
                        continue;
                    } else { //Sino Suponemos que el ascenso era un artefacto y el ascenso del es el siguiente ascenso
                        ascensos.remove(ascenso);
                        ascenso = ascensos.ceiling(intervaloOrigen);
                        if (ascenso != null) {
                            siguienteAscenso = ascensos.ceiling(ascenso.desplazaEnTiempo(1));
                        }
                        continue;
                    }
                }
            }
            //Si aunque el principio del descenso era anterior al principio del ascenso resulta que el fin
            //del descenso es posterior al fin del ascenso "Falta" un descenso
            if (ascenso.getFin() < descenso.getFin()) {
                //Anhadimos el ascenso que falta; la posibilidad de 70 es completamente arbitraria
                Intervalo descensoNuevo = new Intervalo(ascenso.getFin() + 1, descenso.getFin(), 70);
                descensoNuevo.setFrecuencia(descenso.getFrecuencia());
                descensoNuevo.setFechaBase(descenso.getFechaBase());
                descensoNuevo.setDatos(descenso.getDatos());
                siguienteDescenso = descensoNuevo;
                descensos.add(descensoNuevo);
                //retrocedemos el actual descenso para que termine antes de que empiece el ascenso. Hemos creado otro
                //descenso que, en caso de que metamos la pata "nos cubre las espaldas" para los siguientes episodios
                descenso.setFin(ascenso.getPrincipio());
                //esto no impide que podamos crear el episodio
            }

            construyeEpisodioDesaturacion(descenso, ascenso);
            descenso = descensos.ceiling(intervaloOrigen);
            siguienteDescenso = descensos.ceiling(descenso.desplazaEnTiempo(1));
            ascenso = ascensos.ceiling(intervaloOrigen);
            siguienteAscenso = ascensos.ceiling(ascenso.desplazaEnTiempo(1));
        }
    }


    /**
     * construye un episodio de desaturacin con ambos intervalos y elimina los intervalos.
     *
     * @param descenso Intervalo
     * @param ascenso Intervalo
     */
    private void construyeEpisodioDesaturacion(Intervalo descenso, Intervalo ascenso) {
        EpisodioDesaturacion e = new EpisodioDesaturacion(descenso, ascenso,
                valorBasal[descenso.getFin()]);
        if (e.getPosibilidad() > 0) {
            episodios.add(e);
        }
        e.setPendienteDescenso(this.pendienteDescenso, this.posibilidadPrincipio);
        e.setPendienteAscenso(this.pendienteAscenso, this.posibilidadFin);
        descensos.remove(descenso);
        ascensos.remove(ascenso);
    }


    /**
     * genera las marcas que representan los episodios de desaturacion y el
     * colorido de la senhal.
     *
     * @todo este no es el mejor sitio para este mUtodo.
     */
    private void generarMarcasYEnfasis(ArrayList<EpisodioDesaturacion> episodios) {
        Iterator<EpisodioDesaturacion> i = episodios.iterator();
        short[] p = new short[satO2.getValues().length];

        while (i.hasNext()) {
            EpisodioDesaturacion e = i.next();
// generarEpisodioDesaturacion(e, null,satO2);

            //Enfasis
            for (int j = e.getPrincipio(); j < e.getFin(); j++) {
                p[j] = (short) e.getPosibilidad();
            }
            Signal s = sm.getSignal(satO2.getName());
            //      s.setEmphasisLevel(p);

        }
    }

    static void generarEpisodioDesaturacion(EpisodioDesaturacion e, Color color,
                                            Signal satO2) throws SignalNotFoundException {
        //    color= Utilidades.getColor((short)e.getPosibilidad());
        DesaturacionAnotacion m = new DesaturacionAnotacion();
        long t = e.getPrincipioAbsoluto();
        m.setMarkTime(t);
        long t2 = e.getFinAbsoluto();
        m.setEndTime(t2);
        if (color == null) {
            m.setColor(Utilidades.getColor((short) e.getPosibilidad()));
        } else {
            m.setColor(color);
        }

        String texto = "Duracion: " + e.getDuracionEpisodio() + ",\n" +
                       "Valor minimo: " + e.getMinimo() + ",\n" +
                       "Caida en la saturacion: " + e.getCaidaSatO2() + ",\n" +
                       "Posibilidad: " + e.getPosibilidad() + ",\n" +
                       "Tiempo de caida: " + e.getTiempoBajada() + ",\n" +
                       "Tiempo de subida: " + e.getTiempoSubida();
        m.setComentary(texto);
        m.setTitle("Episodio de desaturacion");
        m.setTipo(LimitacionAnotacion.DESATURACION);
        m.setAutomatica(true);
        m.setColor(Color.CYAN);
        JSWBManager.getJSWBManagerInstance().getSignalManager().addSignalMark(
                satO2.getName(), m);
    }

    /**
     * Genera objetos {@link Intervalo} delimitando todos los descensos
     * encontrados en la saturacion de oxigeno.
     *@cambio antes del onceno de noviembre no se bien porque este metodo comprobaba que
     * ciertas condiciones con los valores de principio y fin de los intervalos se cumpliesen.
     * @param posibilidadPrincipio float[]
     */
    private void calculaIntervalos(float[] posibilidadPrincipio, TreeSet<Intervalo> listaIntervalos) {
        for (int i = 0; i < posibilidadPrincipio.length; i++) {
            int principio, fin;
            while (i < posibilidadPrincipio.length && posibilidadPrincipio[i] == 0) {
                i++;
            }
            //Si se acabo el array
            if (i == posibilidadPrincipio.length) {
                return;
            }
            principio = i;
            float posibilidad = 0;
            int longitudEpisodio = 0;
            while (i < posibilidadPrincipio.length && posibilidadPrincipio[i] > 0) {
                posibilidad = Math.max(posibilidad, posibilidadPrincipio[i]);
                i++;
                longitudEpisodio++;
            }
            fin = i - 1;
            Intervalo intervalo = new Intervalo(principio, fin, (int) posibilidad);
            intervalo.setDatos(datosFiltrados);
            intervalo.setFrecuencia(frecuencia);
            intervalo.setFechaBase(this.fechaBase);
            listaIntervalos.add(intervalo);
        }
    }

    /**
     * Comprueba que al final de un evento la senhal este aproximadamente un determinado valor
     * borroso.
     *
     * @param posibilidadFin float[]
     * @param datos float[]
     * @param valor TrapezoidalDistribution
     */
    private void compatibilidadValorFin(float[] posibilidadFin, float[] datos, TrapezoidalDistribution valor) {
        //primero miramos que el valor al final sea adecuado
        for (int i = datos.length - 1; i > 0; i--) {
            //hasta que no encontremos un principio de de saturacin
            while (i > 0 && posibilidadFin[i] == 0) {
                i--;
            }
            if (i == 0) {
                break;
            }
            //una vez que lo encontramos miramos la compatibilidad con el valor admisible para el principio
            float valorPrincipioAscenso = datos[i] + datos[i + 1] + datos[i + 2] + datos[i + 3];
            valorPrincipioAscenso /= 4;
            //actualizamos la posibilidad del principio
            while (i > 0 && posibilidadFin[i] > 0) {
                byte criterioMagnitud = valor.evaluatepossibilityAt(valorPrincipioAscenso - valorBasal[i]);
                posibilidadFin[i] = min(criterioMagnitud, posibilidadFin[i]);
                i--;
            }
        }
    }

    /**
     * Comprueba que al principio de un evento la senhal este en torno a un determinado valor borroso.
     *
     * @param posibilidadPrincipio float[]
     * @param datos float[]
     * @param valor TrapezoidalDistribution
     */
    private void compatibilidadValorPrincipio(float[] posibilidadPrincipio, float[] datos,
                                              TrapezoidalDistribution valor) {
        for (int i = 0; i < datos.length; i++) {
            //hasta que no encontremos un principio de de saturacin
            while (i < datos.length && posibilidadPrincipio[i] == 0) {
                i++;
            }
            if (i == datos.length) {
                break;
            }
            //una vez que lo encontramos miramos la compatibilidad con el valor admisible
            //para el principio
            float c = datos[i] + datos[i - 1] + datos[i - 2] + datos[i - 3];
            c /= 4;
            //actualizamos la posibilidad del principio
            byte criterioMagnitud = valor.evaluatepossibilityAt(c - valorBasal[i]);
            while (i < datos.length && posibilidadPrincipio[i] != 0) {
                posibilidadPrincipio[i] = min(criterioMagnitud, posibilidadPrincipio[i]);
                i++;
            }
        }
    }

    /**
     * Busca descensos en la senhal de saturacion de oxigeno empleando criterio
     * de pendiente. Previamente debe haberse calculado el valor basal de la
     * senhal.
     *
     * @return float[]
     */
    private float[] calcularPrincipio(float[] datos) {
        float[] posibilidadPrincipio = Utilidades.compatibilidadConPendientes(datos,
                ((int) frecuencia * ventanaPendientesSaO2), pendienteDescenso.multiply(1 / frecuencia));
        //Calculamos los principios de las desaturaciones
        for (int i = (int) (persistencia * frecuencia); i < datos.length; i++) {
            if (posibilidadPrincipio[i] > 0) {
                //Mientras sea mayor que cero continuamos
                while (i < datos.length && posibilidadPrincipio[i] > 0) {
                    i++;
                }
                //contamos cuantas muestras se pasa a cero
                int distancia = i;
                while (distancia < datos.length && posibilidadPrincipio[distancia] == 0) {
                    distancia++;
                }
                //si hay menos muestras que la persistencia rellenamos
                //a no ser que entre los dos puntos a rellenar all un claro incremento
                if (distancia - i <= persistencia * frecuencia && distancia < datos.length
                    && datos[distancia] - datos[i] < 4) {
                    for (int j = i; j < distancia; j++) {
                        posibilidadPrincipio[j] = posibilidadPrincipio[j - 1];
                    }
                }
            }
        }
        return posibilidadPrincipio;
    }

    /**
     * Busca ascensos en la senhal de saturacion de oxigeno empleando criterio
     * dependiente. Previamente debe haberse calculado el valor basal de la
     * senhal.
     *
     * @return float[]
     */
    private float[] calcularFin(float[] datos) {
        float[] posibilidadFin = Utilidades.compatibilidadConPendientes(datos,
                ((int) frecuencia * ventanaPendientesSaO2), pendienteAscenso.multiply(1 / frecuencia));
        //Calculamos los principios de las desaturaciones
        for (int i = datos.length - (int) (persistencia * frecuencia); i > 0; i--) {
            if (posibilidadFin[i] > 0) {
                //determinamos el fin; persistencia segundos sin posibilidad
                while (i < datos.length && posibilidadFin[i] > 0) {
                    i--;
                }
                //contamos cuant asmuestras se pasa a cero
                int distancia = i;
                while (distancia > 0 && posibilidadFin[distancia] == 0) {
                    distancia--;
                }
                //rellenamos si es menos que la distancia permitida Y no hay una recuperacin clara
                if (i - distancia <= persistencia * frecuencia && distancia > 0 && datos[i] - datos[distancia] < 4) {
                    for (int j = i; j < distancia; j--) {
                        posibilidadFin[j] = posibilidadFin[j + 1];
                    }
                }
            }
        }
        return posibilidadFin;
    }

    public void setPendienteAscenso(TrapezoidalDistribution pendienteAscenso) {
        this.pendienteAscenso = pendienteAscenso;
    }

    public void setPendienteDescenso(TrapezoidalDistribution pendienteDescenso) {
        this.pendienteDescenso = pendienteDescenso;
    }

    /**
     * Distribucion de posibilidad que indica los valores posibles para datos
     * [i]- valorBasal[i] para que datos[i] pueda considerarse que es un valor
     * "normal".
     *
     * @param descensoAdmisibleRespectoBasal TrapezoidalDistribution
     */
    public void setDescensoAdmisibleRespectoBasal(TrapezoidalDistribution descensoAdmisibleRespectoBasal) {
        this.descensoAdmisibleRespectoBasal = descensoAdmisibleRespectoBasal;
    }

    /**
     * Distribucion de posibilidad que indica los valores posibles para datos
     * [i]- valorBasal[i] para que datos[i] pueda considerarse que es un valor
     * correspondiente con una desaturacion; se usa para comprobar que el final
     * de una caida en la saturacion de oxigeno o el principio de una subida
     * pueden efectivamente delimitar los episodios de desaturacion.
     *
     * @param valorAdmisibleFinCaida TrapezoidalDistribution
     */
    public void setValorAdmisibleDesaturacionRespectoBasal(TrapezoidalDistribution valorAdmisibleFinCaida) {
        this.valorAdmisibleDesaturacionRespectoBasal = valorAdmisibleFinCaida;
    }

    public void setDatos(float[] datos) {
        this.datos = datos;
    }

    public void setFrecuencia(float frecuencia) {
        this.frecuencia = frecuencia;
    }

    public void setFechaBase(long fechaBase) {
        this.fechaBase = fechaBase;
    }

    public void setPersistencia(float persistencia) {
        this.persistencia = persistencia;
    }

    public void setPrincipioVentanaBasalSatO2(int principioVentanaBasalSatO2) {
        this.ventanaBasalSatO2 = (int) (principioVentanaBasalSatO2 * satO2.getSRate());
    }

    public void setVentanaPendientesSaO2(int ventanaPendientesSaO2) {
        this.ventanaPendientesSaO2 = ventanaPendientesSaO2;
    }

    public void setAnularPosibilidadeArraysPorDebajoDe(float anularPosibilidadeArraysPorDebajoDe) {
        this.anularPosibilidaArraysPorDebajoDe = anularPosibilidadeArraysPorDebajoDe;
    }

    public void setDuracionMaximaEpisodiosDesaturacion(int duracionMaximaEpisodiosDesaturacion) {
        this.duracionMaximaEpisodiosDesaturacion = duracionMaximaEpisodiosDesaturacion;
    }

    public static float[] getValorBasal() {
        return valorBasal;
    }
}
