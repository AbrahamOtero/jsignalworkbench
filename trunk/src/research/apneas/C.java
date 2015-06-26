package research.apneas;

import java.awt.Color;
import static java.lang.Math.*;
import java.util.*;

import net.javahispano.fuzzyutilities.representation.TrapezoidalDistribution;
import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalAnnotation;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Abraham Otero
 * @version 0.5
 */
public class C {
    private TreeSet<Intervalo> hipoapneas, apneas, desaturaciones;
    private TreeSet<Intervalo> desaturacionesSinLigar = new TreeSet<Intervalo>(),
    apneasSinLigar = new TreeSet<Intervalo>(), hipoapneasSinLigar = new TreeSet<Intervalo>();
    private TreeSet<Intervalo> hipoapneasYDesaturaciones = new TreeSet<Intervalo>(),
    apneasYDesaturaciones = new TreeSet<Intervalo>();

    private static Signal nasal, satO2;
    //considerar disminuir oficialmente el principio del soporte de la relaci\u2665n temporal para tener en cuenta
    //que a veces metemos la pata al determinar el principio de los eventos que relaciona
    private TrapezoidalDistribution relacionTemporal = new TrapezoidalDistribution(2, 10, 20, 35);
    private float porcentageCovertura = 0.8F; //@todo hacerlo configurable

    private SignalManager sm = JSWBManager.getSignalManager();
    private float frecuencia;

    public C(TreeSet<Intervalo> hipoapneasIntervalos, TreeSet<Intervalo> apneasIntervalos,
            TreeSet<EpisodioDesaturacion> desaturaciones, Signal nasal, Signal satO2) {
        this.nasal = nasal;
        this.satO2 = satO2;
        hipoapneas = new TreeSet(hipoapneasIntervalos);
        apneas = new TreeSet(apneasIntervalos);
        this.desaturaciones = new TreeSet(desaturaciones);
        this.frecuencia = nasal.getSRate();
        detectar();
    }

    private void detectar() {
        filtrarApneas();
        asociarApneasADesaturaciones();
        if (false) {
            return;
        }
        buscaHipoapneasQueContenganUnaApnea();
        asociarHipoapneasADesaturaciones();

        eliminaDesaturacionesLigadas();
        ligarDesaturacionesResultantesSiendoOptimista();
        // fundirEpisodiosDesaturacionPartidos();
        eliminarDesaturacionesDudosas();
        buscarApneasContenidasEnHipoapneas();
        fundirHipoapneasCercanasAOtrosEventos();

        //  andirMarcas(this.desaturacionesSinLigar, false);
        //    andirMarcas(hipoapneasSinLigar, true);
        //  andirMarcas(apneasSinLigar, true);
    }

    private void fundirHipoapneasCercanasAOtrosEventos() {
        TreeSet<Intervalo> apneasSinLigarCopia = new TreeSet<Intervalo>(apneasSinLigar);
        TreeSet<Intervalo> apneasEHipoapneas = new TreeSet<Intervalo>(apneasYDesaturaciones);
        apneasEHipoapneas.addAll(hipoapneasYDesaturaciones);
        for (Intervalo apneaOHipoapnea : apneasEHipoapneas) {
            Set<Intervalo> candidatosAFundir = hipoapneasSinLigar.subSet(
                    apneaOHipoapnea.desplazaEnTiempo( -95), apneaOHipoapnea.desplazaEnTiempo(90));
            for (Intervalo limitacionCandidata : candidatosAFundir) {
                if (limitacionCandidata.distanciaCon(apneaOHipoapnea) < 4) {
                    ReduccionFlujo.generarMarca(limitacionCandidata, "Nasal", "titulo", Color.ORANGE);
                    apneasSinLigarCopia.remove(limitacionCandidata);
                }
            }
        }
        apneasSinLigar = apneasSinLigarCopia;
    }

    /**
     * Mira si las Hipoapneas que han sido identificadas hasta este momento son contenidas por alguna Apnea. En caso
     * afirmativo, podemos identificar la hipoapnea de modo sencillo.
     *
     * Tambien comprueba si despues de una hipoapnea ahi alguna apnea y esa apnea termina antes de que termine la
     * desaturacion correspondiente con la infamia. En ese caso tambien las funde.
     */
    private void buscarApneasContenidasEnHipoapneas() {
        TreeSet<Intervalo> apneasSinLigarCopia = new TreeSet<Intervalo>(apneasSinLigar);
        for (Intervalo apneaSinLigar : apneasSinLigarCopia) {
            //obtenemos la apnea detectada Que comience justo despues de la hipoapnea que estamos analizando
            HipoapneaDesaturacion hipoapneaDesaturacion = (HipoapneaDesaturacion) hipoapneasYDesaturaciones.floor(
                    apneaSinLigar);
            //si la apnea esta contenida
            if (hipoapneaDesaturacion != null) {
                if (hipoapneaDesaturacion.getFin() >= apneaSinLigar.getFin()) {
                    hipoapneaDesaturacion.addApneaYDesaturacion(this.asociarApneaConDesat(apneaSinLigar,
                            hipoapneaDesaturacion.getEpisodioDesaturacion(), 100, Color.red));
                    hipoapneaDesaturacion = (HipoapneaDesaturacion) hipoapneasYDesaturaciones.floor(
                            hipoapneaDesaturacion.desplazaEnTiempo(1));
                }
                //si despu\u2665s de la hipoapnea ah% una apnea
                else if (apneaSinLigar.distanciaEntreFines(hipoapneaDesaturacion.getEpisodioDesaturacion()) > 5 &&
                         apneaSinLigar.distanciaEntreFines(hipoapneaDesaturacion.getEpisodioDesaturacion()) < 40) {
                    hipoapneaDesaturacion.addApneaYDesaturacion(this.asociarApneaConDesat(apneaSinLigar,
                            hipoapneaDesaturacion.getEpisodioDesaturacion(), 100, Color.red));
                    hipoapneaDesaturacion = (HipoapneaDesaturacion) hipoapneasYDesaturaciones.floor(
                            hipoapneaDesaturacion.desplazaEnTiempo(1));
                    System.out.println("Se est usando esto");
                }
            }
        }
        apneasSinLigar = apneasSinLigarCopia;
    }

    /**
     * Funde episodios de desaturacion que inicialmente fueron identificados como dos episodios distintos pero que estan
     * ligados con una unica apnea o hipoapnea y que no tiene una clara recuperacion entre ellos.
     *
     * @todoSi se consigue extraer una interfaz comun para las clases  HipoapneaDesaturacion y ApneaYDesaturacion
     * hacer refactoring para evitar
     * el codigo duplicado.
     */
    private void fundirEpisodiosDesaturacionPartidos() {
        TreeSet<Intervalo> desaturacionesSinLigarCopia = new TreeSet<Intervalo>(desaturacionesSinLigar);
        TreeSet<Intervalo> limitacionesAsociadas = new TreeSet<Intervalo>(apneasYDesaturaciones);
        limitacionesAsociadas.addAll(hipoapneasYDesaturaciones);
        for (Intervalo e : limitacionesAsociadas) {
            ApneaYDesaturacion apneaYDesaturacion = (ApneaYDesaturacion) e;
            EpisodioDesaturacion desaturacionDeApnea = apneaYDesaturacion.getEpisodioDesaturacion();
            EpisodioDesaturacion desaturacionSinLigar =
                    (EpisodioDesaturacion) desaturacionesSinLigarCopia.ceiling(desaturacionDeApnea);
            if (desaturacionSinLigar == null) {
                break;
            }
            if (desaturacionDeApnea.distanciaCon(desaturacionSinLigar) < 15) {
                if (!esRecuperacionExcesiva(desaturacionDeApnea, desaturacionSinLigar)) {
                    desaturacionesSinLigarCopia.remove(desaturacionSinLigar);
                    EpisodioDesaturacion nuevaDesaturacion = new EpisodioDesaturacion(
                            desaturacionDeApnea.getIntervaloPrincipio(), desaturacionSinLigar.getIntervaloFin(),
                            desaturacionDeApnea);
                    DesatDetector.generarEpisodioDesaturacion(nuevaDesaturacion, Color.yellow, satO2);
                    apneaYDesaturacion.reemplazaEpisodioDesaturacion(nuevaDesaturacion);
                }
            }
        }
        //ahora hacemos lo mismo para las desaturaciones sueltas
        desaturacionesSinLigar = desaturacionesSinLigarCopia;
        desaturacionesSinLigarCopia = new TreeSet<Intervalo>(desaturacionesSinLigar);
        boolean ligado = false;
        for (Intervalo d : desaturacionesSinLigar) {
            //si agrupamos dos hay que saltarse el segundo de la lista porque ya est\u2193 procesado
            if (ligado) {
                ligado = false;
                continue;
            }
            EpisodioDesaturacion desaturacionDeApnea = (EpisodioDesaturacion) d;
            EpisodioDesaturacion desaturacionSinLigar =
                    (EpisodioDesaturacion) desaturacionesSinLigarCopia.ceiling(
                            desaturacionDeApnea.desplazaEnTiempo(1));
            if (desaturacionSinLigar == null) {
                break;
            }
            if (desaturacionDeApnea.distanciaCon(desaturacionSinLigar) < 15) {
                if (!esRecuperacionExcesiva((EpisodioDesaturacion) desaturacionDeApnea, desaturacionSinLigar)) {
                    desaturacionesSinLigarCopia.remove(desaturacionDeApnea);
                    desaturacionesSinLigarCopia.remove(desaturacionSinLigar);
                    EpisodioDesaturacion nuevaDesaturacion = new EpisodioDesaturacion(
                            desaturacionDeApnea.getIntervaloPrincipio(), desaturacionSinLigar.getIntervaloFin(),
                            desaturacionDeApnea);
                    DesatDetector.generarEpisodioDesaturacion(nuevaDesaturacion, Color.red, satO2);
                    ligado = true;
                }
            }
        }
        desaturacionesSinLigar = desaturacionesSinLigarCopia;
    }

    private boolean esRecuperacionExcesiva(EpisodioDesaturacion d, Intervalo d2) {
        float limite = 0;
        if (d.getCaidaSatO2() < 10) {
            limite = d.getDatos()[d.getIntervaloPrincipio().getFin()] + d.getCaidaSatO2() / 2;
        } else if (d.getCaidaSatO2() < 20) {
            limite = d.getDatos()[d.getIntervaloPrincipio().getFin()] + d.getCaidaSatO2() / 3;
        } else {
            limite = d.getDatos()[d.getIntervaloPrincipio().getFin()] + d.getCaidaSatO2() / 4;
        }
        limite = Math.max(85, limite);
        for (int i = d.getFin(); i < d2.getPrincipio(); i++) {
            if (d.getDatos()[i] > limite) {
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina aquellas desaturaciones que son dudosas. Ya no vamos a asociar desaturaciones con nada. El proposito de
     * este metodo es filtrar aquellas desaturaciones que no merece la pena.
     */
    private void eliminarDesaturacionesDudosas() {
        TreeSet<Intervalo> desaturacionesSinLigarCopia = new TreeSet<Intervalo>(desaturacionesSinLigar);
        for (Intervalo e : desaturacionesSinLigar) {
            EpisodioDesaturacion d = (EpisodioDesaturacion) e;
            if (!d.esBuenaCalidad()) {
                desaturacionesSinLigarCopia.remove(d);
            }
        }
        desaturacionesSinLigar = desaturacionesSinLigarCopia;
    }

    /**
     * Este metodo recorre la lista de desaturaciones que quedan sin llegar y busca alguna apnea o hipoapnea que "quede
     * cerca" de la desaturacion y las asocia. Aunque se comprueban ciertas relaciones temporales, se es muy laxo en
     * ellas. La idea es que una vez que se han identificado todas las apneas e hipoapneas claras (junto con sus
     * desaturaciones) sera menos probable equivocarse aunque seamos bastante laxos con las relaciones temporales.
     *
     * El motivo de tolerar en este metodo relaciones temporales que serian fisiologicamente imposibles (o al menos
     * improbables) entre los eventos es que el algoritmo puede equivocarse al determinar con precision el principio y el
     * fin de los eventos.
     */
    private void ligarDesaturacionesResultantesSiendoOptimista() {
        TreeSet<Intervalo> desaturacionesSinLigarCopia = new TreeSet<Intervalo>(desaturacionesSinLigar);
        Iterator<Intervalo> itDesaturaciones = desaturacionesSinLigar.iterator();
        //Cogemos lar desaturaciones que queden sueltas e intentamos asociar las
        while (itDesaturaciones.hasNext()) {
            Intervalo desaturacion = itDesaturaciones.next();
            //hacia atras desplazamos bastante ya qu la limitacion de flujo empieza antes. Hacia adelante
            //se desplaza poco ya que s+lo se trata de corregir errores en la atencion de principios
            //(es fisiologicamente imposible Que esa sea la relacion real)
            Set<Intervalo> listaLimitaciones = hipoapneasSinLigar.subSet(
                    desaturacion.desplazaEnTiempo( -60), desaturacion.desplazaEnTiempo(5));
            listaLimitaciones.addAll(apneasSinLigar.subSet(
                    desaturacion.desplazaEnTiempo( -60), desaturacion.desplazaEnTiempo(5)));
            Iterator<Intervalo> itLimitaciones = listaLimitaciones.iterator();
            boolean ligada = false;
            while (!ligada && itLimitaciones.hasNext()) {
                Intervalo hipoapnea = itLimitaciones.next();
                int distanciaEntreFines = hipoapnea.distanciaEntreFines(desaturacion);
                int distanciaEntrePrincipios = hipoapnea.distanciaEntrePrincipios(desaturacion);
                if (distanciaEntreFines < 45 && distanciaEntreFines > 5 && distanciaEntrePrincipios < 50) {
                    //dado que esto Es un tanto heuristico solo asignamos un grado  de cumplimiento a
                    //la relacion temporal de 50
                    this.asociarHipoapneaConDesat(hipoapnea, desaturacion, 50, Color.red);
                    desaturacionesSinLigarCopia.remove(desaturacion);
                    hipoapneasSinLigar.remove(hipoapnea);
                    apneasSinLigar.remove(hipoapnea);
                    ligada = true;
                }
            }
        }
        desaturacionesSinLigar = desaturacionesSinLigarCopia;
    }

    /**
     * Al identificar hipoapneas hemos ido eliminando desaturaciones de la lista de desaturaciones. Al identificar
     * apneas no lo hicimos por si acaso alguna hipoapnea se asocia con la misma desaturacion que alguna apnea. Ahora,
     * una vez identificadas las hipoapneas, eliminemos aquellas desaturaciones que estan asociadas con alguna apnea y
     * que no fueron eliminadas durante la deteccion de hipoapneas.
     */
    private void eliminaDesaturacionesLigadas() {
        for (Intervalo elem : this.apneasYDesaturaciones) {
            ApneaYDesaturacion tmp = (ApneaYDesaturacion) elem;
            this.desaturacionesSinLigar.remove(tmp.getEpisodioDesaturacion());
        }

    }

    /**
     * Mira si las apneas que han sido identificadas hasta este momento son contenidas por alguna hipoapnea. En caso
     * afirmativo, podemos identificar la hipoapnea de modo sencillo.
     */
    private void buscaHipoapneasQueContenganUnaApnea() {
        TreeSet<Intervalo> copiaHipoapneas = new TreeSet<Intervalo>(hipoapneas);
        for (Intervalo hipoapnea : hipoapneas) {
            Intervalo apnea;
            //obtenemos la apnea detectada Que comience justo despues de la hipoapnea que estamos analizando
            apnea = apneasYDesaturaciones.ceiling(hipoapnea);
            //si la apnea esta contenida
            SortedSet<Intervalo> apneasContenidas =
                    apneasYDesaturaciones.subSet(hipoapnea, hipoapnea.desplazaEnTiempo(hipoapnea.getDuracion() + 1));
            if (apneasContenidas.size() != 0) {
                ApneaYDesaturacion apneaYDesaturacion = (ApneaYDesaturacion) apneasContenidas.first();
                EpisodioDesaturacion desaturacion = apneaYDesaturacion.getEpisodioDesaturacion();
                //la desaturacion, que no se borro en su dia por si se asociaba a una hipoapnea, Ya no hara falta mas
                this.desaturaciones.remove(desaturacion);
                copiaHipoapneas.remove(hipoapnea);
                //suponemos que la relacion temporal se cumple totalmente (de ahi el 100)
                HipoapneaDesaturacion hipoapneaDesaturacion = asociarHipoapneaConDesat(hipoapnea, desaturacion,
                        100, Color.RED);
                hipoapneasYDesaturaciones.add(hipoapneaDesaturacion);
                //apneasContenidas.remove(apneaYDesaturacion);
                for (Intervalo elem : apneasContenidas) {
                    hipoapneaDesaturacion.addApneaYDesaturacion((ApneaYDesaturacion) elem);
                }
            }
        }
        hipoapneas = copiaHipoapneas;
    }

    /**
     * asociarApneasADesaturaciones
     */
    private void asociarApneasADesaturaciones() {
        //trabajamos sobre una copia de las desaturaciones porque no debemos de alterarlas
        TreeSet<Intervalo> copiaDesaturaciones = new TreeSet(desaturaciones);
        if (apneas.size() == 0 || desaturaciones.size() == 0) {
            return;
        }
        Intervalo apnea = apneas.first();
        Intervalo desaturacion = copiaDesaturaciones.first();
        Intervalo siguienteApnea = null, siguienteDesaturacion = null;
        siguienteApnea = apneas.ceiling(apnea.desplazaEnTiempo(1));
        siguienteDesaturacion = copiaDesaturaciones.ceiling(desaturacion.desplazaEnTiempo(1));
        while (desaturacion != null && apnea != null && siguienteApnea != null && siguienteDesaturacion != null) {
            //********U****----*******(desaturacion antes) o bien:
             //************----*******
              //*************U******** (demasiado cerca)
               if (apnea.distanciaEntrePrincipios(desaturacion) <= relacionTemporal.getBeginSupport()) {
                   copiaDesaturaciones.remove(desaturacion);
                   desaturacion = copiaDesaturaciones.ceiling(desaturacion);
                   if (desaturacion != null) {
                       siguienteDesaturacion = copiaDesaturaciones.ceiling(desaturacion.desplazaEnTiempo(1));
                   }
                   continue;
               }
               //************----******************---***U*******
                else if (apnea.distanciaEntrePrincipios(desaturacion) >= relacionTemporal.getEndSupport()) {
                    apneas.remove(apnea);
                    apneasSinLigar.add(apnea);
                    apnea = apneas.ceiling(apnea.desplazaEnTiempo(1));
                    if (apnea != null) {
                        siguienteApnea = apneas.ceiling(apnea.desplazaEnTiempo(1));
                    }
                    continue;
                }
                //************----***---***U******* (ambas compatibles)
                 else if (caculaCompatibilidad(siguienteApnea, desaturacion) > 0) {
                     //si Actual tiene mas compatibilidad que la siguiente
                     if (caculaCompatibilidad(apnea, desaturacion) > caculaCompatibilidad(siguienteApnea, desaturacion)
                         || //O si la siguiente apnea tiene compatibilidad con la siguiente desaturacion
                         caculaCompatibilidad(siguienteApnea, siguienteDesaturacion) > 0) {
                         //Asociamos actual apnea con actual desaturacionDe
                         asociarApneaConDesat(apnea, desaturacion, relacionTemporal.evaluatepossibilityAt(
                                 apnea.distanciaEntrePrincipios(desaturacion)), Color.RED);
                         copiaDesaturaciones.remove(desaturacion);
                         desaturacion = desaturaciones.ceiling(desaturacion.desplazaEnTiempo(1));
                         apneas.remove(apnea);
                         apnea = apneas.ceiling(apnea.desplazaEnTiempo(1));
                         if (apnea != null && desaturacion != null) {
                             siguienteApnea = apneas.ceiling(apnea.desplazaEnTiempo(1));
                             siguienteDesaturacion = copiaDesaturaciones.ceiling(desaturacion.desplazaEnTiempo(1));
                         }
                         continue;
                     }
                     //nos cargamos la apnea
                     else {
                         apneas.remove(apnea);
                         apneasSinLigar.add(apnea);
                         apnea = apneas.ceiling(apnea.desplazaEnTiempo(1));
                         if (apnea != null) {
                             siguienteApnea = apneas.ceiling(apnea.desplazaEnTiempo(1));
                         }
                         continue;
                     }
                 }
                 //************----*U**U******* (ambas compatibles)
                  else if (caculaCompatibilidad(apnea, desaturacion) > 0 &&
                           caculaCompatibilidad(apnea, siguienteDesaturacion) > 0) {
                      //si la compatibilidad de la presente apnea con la siguiente desaturacion es mayor que la compatibilidad
                      //de la actual apnea con la actual desaturacion y la compatibilidad De la actual apnea y la Siguiente
                      //desaturacion Es mayor que la compatibilidad de la siguiente apnea con la siguiente desaturacion
                      if (caculaCompatibilidad(apnea, desaturacion) < caculaCompatibilidad(apnea, siguienteDesaturacion)
                          && caculaCompatibilidad(apnea, siguienteDesaturacion) >
                          caculaCompatibilidad(siguienteApnea, siguienteDesaturacion)) {
                          //unimos la actual apnea con la siguiente desaturacion y pasamos de la actual desaturacion
                          asociarApneaConDesat(apnea, siguienteDesaturacion, relacionTemporal.evaluatepossibilityAt(
                                  apnea.distanciaEntrePrincipios(siguienteDesaturacion)), Color.red);
                          copiaDesaturaciones.remove(desaturacion); // esta la ignoramos
                          copiaDesaturaciones.remove(siguienteDesaturacion);
                          desaturacion = copiaDesaturaciones.ceiling(desaturacion.desplazaEnTiempo(1));
                          apneas.remove(apnea);
                          apnea = apneas.ceiling(apnea.desplazaEnTiempo(1));
                          if (apnea != null && desaturacion != null) {
                              siguienteApnea = apneas.ceiling(apnea.desplazaEnTiempo(1));
                              siguienteDesaturacion = copiaDesaturaciones.ceiling(desaturacion.desplazaEnTiempo(1));
                          }
                          continue;
                      } //no es necesario gestionar el "else" ya que es la opcion por defecto: liga actual apnea y desaturacion
                  }
            asociarApneaConDesat(apnea, desaturacion, relacionTemporal.evaluatepossibilityAt(
                    apnea.distanciaEntrePrincipios(desaturacion)), Color.RED);
            copiaDesaturaciones.remove(desaturacion);
            desaturacion = copiaDesaturaciones.ceiling(desaturacion.desplazaEnTiempo(1));
            apneas.remove(apnea);
            apnea = apneas.ceiling(apnea.desplazaEnTiempo(1));
            if (apnea != null && desaturacion != null) {
                siguienteApnea = apneas.ceiling(apnea.desplazaEnTiempo(1));
                siguienteDesaturacion = copiaDesaturaciones.ceiling(desaturacion.desplazaEnTiempo(1));
            }
            continue;
        }
    }

    /**
     * Su funcion es muy similar a la del metodo de las asambleas. Trabaja sobre colecciones de datos diferentes y
     * realiza acciones ligeramente diferentes (una desaturacion que aqui no se ha ligado a nada pasa a ser un problema,
     * mientras que en el metodo anterior todavia teniamos la esperanza de que aqui se ligase).
     * Nota: evalue fundir este metodo y el anterior, pero incrementaria la ya complicada logica de cada metodo por
     * tener que considerar las acciones diferentes a realizar cuando se trabaja sobre apneas e hipoapneas.
     */
    private void asociarHipoapneasADesaturaciones() {
        TreeSet<Intervalo> copiaDesaturaciones = new TreeSet(desaturaciones);
        if (hipoapneas.size() == 0 || desaturaciones.size() == 0) {
            return;
        }
        Intervalo hipoapnea = hipoapneas.first();
        Intervalo desaturacion = desaturaciones.first();
        Intervalo sigHipoapnea = null, siguienteDesaturacion = null;
        sigHipoapnea = hipoapneas.ceiling(hipoapnea.desplazaEnTiempo(1));
        siguienteDesaturacion = desaturaciones.ceiling(desaturacion.desplazaEnTiempo(1));
        while (desaturacion != null && hipoapnea != null && sigHipoapnea != null && siguienteDesaturacion != null) {
            //********U****----*******(desaturacion antes) o bien:
             //************----*******
              //*************U******** (demasiado cerca)
               if (hipoapnea.distanciaEntrePrincipios(desaturacion) <= relacionTemporal.getBeginSupport()) {
                   desaturaciones.remove(desaturacion);
                   desaturacionesSinLigar.add(desaturacion);
                   desaturacion = desaturaciones.ceiling(desaturacion);
                   if (desaturacion != null) {
                       siguienteDesaturacion = desaturaciones.ceiling(desaturacion.desplazaEnTiempo(1));
                   }
                   continue;
               }
               //************----******************---***U*******
                else if (hipoapnea.distanciaEntrePrincipios(desaturacion) >= relacionTemporal.getEndSupport()) {
                    hipoapneas.remove(hipoapnea);
                    hipoapneasSinLigar.add(hipoapnea);
                    hipoapnea = hipoapneas.ceiling(hipoapnea.desplazaEnTiempo(1));
                    if (hipoapnea != null) {
                        sigHipoapnea = hipoapneas.ceiling(hipoapnea.desplazaEnTiempo(1));
                    }
                    continue;
                }
                //************----***---***U******* (ambas compatibles)
                 else if (caculaCompatibilidad(sigHipoapnea, desaturacion) > 0) {
                     //si Actual tiene mas compatibilidad que la siguiente
                     if (caculaCompatibilidad(hipoapnea, desaturacion) >
                         caculaCompatibilidad(sigHipoapnea, desaturacion)
                         || //O si la siguiente apnea tiene compatibilidad con la siguiente desaturacion
                         caculaCompatibilidad(sigHipoapnea, siguienteDesaturacion) > 0) {
                         //Asociamos actual apnea con actual desaturacion
                         asociarHipoapneaConDesat(hipoapnea, desaturacion, relacionTemporal.evaluatepossibilityAt(
                                 hipoapnea.distanciaEntrePrincipios(desaturacion)), Color.RED);
                         desaturaciones.remove(desaturacion);
                         desaturacion = this.desaturaciones.ceiling(desaturacion.desplazaEnTiempo(1));
                         hipoapneas.remove(hipoapnea);
                         hipoapnea = hipoapneas.ceiling(hipoapnea.desplazaEnTiempo(1));
                         if (hipoapnea != null && desaturacion != null) {
                             sigHipoapnea = hipoapneas.ceiling(hipoapnea.desplazaEnTiempo(1));
                             siguienteDesaturacion = desaturaciones.ceiling(desaturacion.desplazaEnTiempo(1));
                         }
                         continue;
                     }
                     //nos cargamos la apnea
                     else {
                         hipoapneas.remove(hipoapnea);
                         hipoapneasSinLigar.add(hipoapnea);
                         hipoapnea = hipoapneas.ceiling(hipoapnea.desplazaEnTiempo(1));
                         if (hipoapnea != null) {
                             sigHipoapnea = hipoapneas.ceiling(hipoapnea.desplazaEnTiempo(1));
                         }
                         continue;
                     }
                 }
                 //************----*U**U******* (ambas compatibles)
                  else if (caculaCompatibilidad(hipoapnea, desaturacion) > 0 &&
                           caculaCompatibilidad(hipoapnea, siguienteDesaturacion) > 0) {
                      //si la compatibilidad de la presente apnea con la siguiente desaturacion es mayor que la compatibilidad
                      //de la actual apnea con la actual desaturacion y la compatibilidad De la actual apnea y la Siguiente
                      //desaturacion Es mayor que la compatibilidad de la siguiente apnea con la siguiente desaturacion
                      if (caculaCompatibilidad(hipoapnea, desaturacion) < caculaCompatibilidad(hipoapnea,
                              siguienteDesaturacion) && caculaCompatibilidad(hipoapnea, siguienteDesaturacion) >
                          caculaCompatibilidad(sigHipoapnea, siguienteDesaturacion)) {
                          //unimos la actual apnea con la siguiente desaturacion y pasamos de la actual desaturacion
                          asociarHipoapneaConDesat(hipoapnea, siguienteDesaturacion,
                                  relacionTemporal.evaluatepossibilityAt(
                                          hipoapnea.distanciaEntrePrincipios(siguienteDesaturacion)), Color.RED);
                          desaturaciones.remove(desaturacion); // esta la ignoramos
                          desaturacionesSinLigar.add(desaturacion);
                          desaturaciones.remove(siguienteDesaturacion);
                          desaturacion = desaturaciones.ceiling(desaturacion.desplazaEnTiempo(1));
                          hipoapneas.remove(hipoapnea);
                          hipoapnea = hipoapneas.ceiling(hipoapnea.desplazaEnTiempo(1));
                          if (hipoapnea != null && desaturacion != null) {
                              sigHipoapnea = hipoapneas.ceiling(hipoapnea.desplazaEnTiempo(1));
                              siguienteDesaturacion = desaturaciones.ceiling(desaturacion.desplazaEnTiempo(1));
                          }
                          continue;
                      } //no es necesario gestionar el "else" ya que es la opcion por defecto: liga actual apnea y desaturacion
                  }

            asociarHipoapneaConDesat(hipoapnea, desaturacion, relacionTemporal.evaluatepossibilityAt(
                    hipoapnea.distanciaEntrePrincipios(desaturacion)), Color.RED);
            desaturaciones.remove(desaturacion);
            desaturacion = desaturaciones.ceiling(desaturacion.desplazaEnTiempo(1));
            hipoapneas.remove(hipoapnea);
            hipoapnea = hipoapneas.ceiling(hipoapnea.desplazaEnTiempo(1));
            if (hipoapnea != null && desaturacion != null) {
                sigHipoapnea = hipoapneas.ceiling(hipoapnea.desplazaEnTiempo(1));
                siguienteDesaturacion = desaturaciones.ceiling(desaturacion.desplazaEnTiempo(1));
            }
            continue;
        }
    }


    /**
     * Calcula la compatibilidad global resultante de ligar una limitacion de flujo, una desaturacion y tener en cuenta
     * la relacion temporal entre ambas.
     *
     * @param limitacionFlujo Intervalo
     * @param desaturacion Intervalo
     * @return byte
     */
    private byte caculaCompatibilidad(Intervalo limitacionFlujo, Intervalo desaturacion) {
        return (byte) min(min(relacionTemporal.evaluatepossibilityAt(limitacionFlujo.distanciaEntrePrincipios(
                desaturacion)), limitacionFlujo.getPosibilidad()), desaturacion.getPosibilidad());
        /*@todo considerar la anterior implementaci+n:
                return (byte) min(min(relacionTemporal.evaluatepossibilityAt(limitacionFlujo.distanciaEntrePrincipios(
                                   desaturacion)), limitacionFlujo.getPosibilidad()), desaturacion.getPosibilidad());
         */
    }


    private ApneaYDesaturacion asociarApneaConDesat(Intervalo limitacionFlujo, Intervalo episodioDesaturacion,
            int posibilidadRelacionTemporal, Color color) {
        ApneaYDesaturacion episodio = new ApneaYDesaturacion(limitacionFlujo, episodioDesaturacion,
                posibilidadRelacionTemporal);
        ReduccionFlujo.generarMarca(limitacionFlujo, nasal.getName(), "Apnea", color);
        DesatDetector.generarEpisodioDesaturacion((EpisodioDesaturacion) episodioDesaturacion, color, satO2);
        apneasYDesaturaciones.add(episodio);
        generarAnnotation(episodio, "Apnea");
        return episodio;
    }

    private HipoapneaDesaturacion asociarHipoapneaConDesat(Intervalo limitacionFlujo, Intervalo episodioDesaturacion,
            int posibilidadRelacionTemporal, Color color) {
        HipoapneaDesaturacion episodio = new HipoapneaDesaturacion(limitacionFlujo, episodioDesaturacion,
                posibilidadRelacionTemporal);
        ReduccionFlujo.generarMarca(limitacionFlujo, nasal.getName(), "Hypoapnea", color);
        DesatDetector.generarEpisodioDesaturacion((EpisodioDesaturacion) episodioDesaturacion, color, satO2);
        hipoapneasYDesaturaciones.add(episodio);
        generarAnnotation(episodio, "Hypoapnea");
        return episodio;
    }


    private void generarAnnotation(Intervalo hipoapnea, String titulo) {
        DefaultIntervalAnnotation annotation = new DefaultIntervalAnnotation();
        long principio = hipoapnea.getPrincipioAbsoluto();
        annotation.setAnnotationTime(principio);
        long fin = hipoapnea.getFinAbsoluto();
        annotation.setEndTime(fin);
        annotation.setColor(Utilidades.getColor((short) hipoapnea.getPosibilidad()));
        String texto = "Duracion: " + hipoapnea.getDuracion() + "\n" +
                       ", Posibilidad: " + hipoapnea.getPosibilidad() + "\n";
        annotation.setComentary(texto);
        annotation.setTitle(titulo);
        annotation.setCategory(titulo);
        sm.addAnnotation(annotation);
    }

    /**
     * Elimina aquellas hipoapneas que contengan en su interior una unica apnea y que esta apnea abarca casi toda la
     * hipoapnea. Se considera que en este caso el senhalar la hipoapnea no aporta nada.
     */
    private void filtrarApneas() {
        Intervalo hipoapnea = null;
        hipoapnea = hipoapneas.first();
        Set<Intervalo> listaApneas;
        //mientras haya hipoapneasIntervalos
        while (hipoapnea != null) {
            listaApneas = apneas.subSet(
                    hipoapnea, hipoapnea.desplazaEnTiempo(hipoapnea.getDuracion() + 1));
            //Si slo hay una apnea contenida en esta hipoapnea y tienen una duraci+n superior al
            //porcentageCovertura% de la duracion de la hipoapnea ignoramos la hipoapnea.
            Iterator<Intervalo> it = listaApneas.iterator();
            if (listaApneas.size() == 1) {
                Intervalo intervalo = it.next();
                if (intervalo.getFin() - intervalo.getPrincipio() >
                    porcentageCovertura * (hipoapnea.getFin() - hipoapnea.getPrincipio())) {
                    hipoapneas.remove(hipoapnea);
                }
            } //pasamos a la siguiente hipoapnea
            hipoapnea = hipoapneas.ceiling(hipoapnea.desplazaEnTiempo(1));
        }
    }

    /**
     * andirMarcas
     *
     * @param hipoapneas TreeSet
     */
    private void andirMarcas(TreeSet<Intervalo> lista, boolean apnea) {
        for (Intervalo elem : lista) {
            if (apnea) {
                ReduccionFlujo.generarMarca(elem, nasal.getName(), "Hipoapnea", Color.BLACK);
            } else {
                DesatDetector.generarEpisodioDesaturacion((EpisodioDesaturacion) elem, Color.BLACK, this.satO2);
            }
        }
    }

    public void setRelacionTemporal(TrapezoidalDistribution relacionTemporal) {
        this.relacionTemporal = relacionTemporal;
    }

    public static Signal getSatO2() {
        return satO2;
    }
}
