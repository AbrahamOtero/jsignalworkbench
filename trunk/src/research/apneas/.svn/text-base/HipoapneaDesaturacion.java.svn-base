package research.apneas;

import java.awt.Color;
import static java.lang.Math.*;
import java.util.TreeSet;

import net.javahispano.jsignalwb.JSWBManager;

/**
 * Las instancias de esta clase cuando son tratadas como intervalos se comportan de un modo identico al intervalo de
 * limitacion de flujo de contienen; esto es, su principio y su fin son el principio del fin del intervalo de
 * limitacion de flujo. Esto es importante ya que el algoritmo depende de ello.
 * *
 * @author Abraham Otero
 * @version 0.5
 */
public class HipoapneaDesaturacion extends ApneaYDesaturacion {
    private TreeSet<ApneaYDesaturacion> listaApneas = new TreeSet<ApneaYDesaturacion>();

    public HipoapneaDesaturacion(Intervalo limitacionFlujo, Intervalo episodioDesaturacion, int posRelacionTemporal) {
        super(limitacionFlujo, episodioDesaturacion, posRelacionTemporal);
    }

    public TreeSet getListaApneas() {
        return listaApneas;
    }

    public void addApneaYDesaturacion(ApneaYDesaturacion apnea) {
        this.listaApneas.add(apnea);
        super.addEpisodioDesaturacion(apnea.getEpisodioDesaturacion());
        DesatDetector.generarEpisodioDesaturacion(apnea.getEpisodioDesaturacion(), Color.red,
                                                  JSWBManager.getSignalManager().getSignal("SaO2"));
    }
}
