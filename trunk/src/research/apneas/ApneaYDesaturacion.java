package research.apneas;

import static java.lang.Math.*;
import java.util.TreeSet;

/**
 * Las instancias de esta clase cuando son tratadas como intervalos se comportan de un modo identico al intervalo de
 * limitacion de flujo de contienen; esto es, su principio y su fin son el principio del fin del intervalo de
 * limitacion de flujo. Esto es importante ya que el algoritmo depende de ello.
 *
 * @author Abraham Otero
 * @version 0.5
 */
public class ApneaYDesaturacion extends Intervalo {
    private TreeSet<EpisodioDesaturacion> listaEpisodiosDesaturacion = new TreeSet<EpisodioDesaturacion>();
    private EpisodioDesaturacion episodioDesaturacion;
    private Intervalo limitacionFlujo;

    public ApneaYDesaturacion(Intervalo limitacionFlujo, Intervalo episodioDesaturacion, int posRelacionTemporal) {
        setPrincipio(limitacionFlujo.getPrincipio());
        setFin(limitacionFlujo.getFin());
        setPosibilidad(min(limitacionFlujo.getPosibilidad(), min(episodioDesaturacion.getPosibilidad(),
                posRelacionTemporal)));
        setFechaBase(limitacionFlujo.getFechaBase());
        setFrecuencia(limitacionFlujo.getFrecuencia());
        this.episodioDesaturacion = (EpisodioDesaturacion) episodioDesaturacion;
        listaEpisodiosDesaturacion.add((EpisodioDesaturacion) episodioDesaturacion);
        this.limitacionFlujo = limitacionFlujo;
    }

    public EpisodioDesaturacion getEpisodioDesaturacion() {
        return episodioDesaturacion;
    }

    public Intervalo getLimitacionFlujo() {
        return limitacionFlujo;
    }

    public TreeSet getListaEpisodiosDesaturacion() {
        return listaEpisodiosDesaturacion;
    }

    public void addEpisodioDesaturacion(EpisodioDesaturacion episodioDesaturacion) {
        listaEpisodiosDesaturacion.add(episodioDesaturacion);
    }

    /**
     * Este metodo esta pensado para cuando se decide cambiar la saturacion del episodio. Esto sucede cuando la
     * saturacion se detecto "partida" en dos y el algoritmo se da cuenta y junta las dos mitades de la desaturacion.
     *
     * @param episodioDesaturacion EpisodioDesaturacion
     */
    void reemplazaEpisodioDesaturacion(EpisodioDesaturacion episodioDesaturacion) {
        listaEpisodiosDesaturacion.remove(this.episodioDesaturacion);
        listaEpisodiosDesaturacion.add(episodioDesaturacion);
        this.episodioDesaturacion = episodioDesaturacion;
    }

}
