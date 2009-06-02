package research.apneas.spo2;

import java.awt.Color;
import java.util.ArrayList;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;
import research.apneas.*;

/**
 * <p>Title: </p>
 * <p/>
 * <p>Description: </p>
 * <p/>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p/>
 * <p>Company: </p>
 *
 * @author Abraham Otero
 * @version 0.5
 */
public class DetectorDesaturacionesWrapper extends AlgorithmAdapter {
    private Signal signal;


    public DetectorDesaturacionesWrapper() {

    }

    public DetectorDesaturacionesWrapper(Signal signal) {
        this.signal = signal;
    }

    public void runAlgorithm(SignalManager sm, float[] datos) {

        signal = JSWBManager.getSignalManager().getSignal("SaO2");
        ejecutar(datos);
    }

    public ArrayList<EpisodioDesaturacion> ejecutar(float[] datos) {
        inicioAbsoluto = signal.getStart();
        return test(datos, signal);
    }

    private ArrayList<EpisodioDesaturacion> test(float[] datos, Signal signal) {
        ArrayList<EpisodioDesaturacion> des = new ArrayList<EpisodioDesaturacion>();
        DetectorDesaturaciones b = new DetectorDesaturaciones();
        b.setTiempoInicial(inicioAbsoluto);
        float periodo = 1 / signal.getSRate();
        //Si el periodo de muestreo no es un entero
        if (Math.abs(Math.round(1 / periodo) - 1 / periodo) > 0.05F) {
            throw new RuntimeException(
                    "Error; la senhal de saturacion no tiene una periodo adecuada ");
        }

        int paso = Math.round(1 / periodo);

        for (int i = 0; i < datos.length; i += paso) {
            float da = 0;

            for (int j = i; j < i + paso; j++) {
                da += datos[i];
            }
            da /= paso;
            Desaturacion d = b.anadeDato(da);
            // System.out.println("*****************************Valor basal "+b.getValorBasal());

            if (d != null) {
                // System.out.println(d.toString());
                if (!d.isInicioSolo() && true) {
                    //   System.out.println(d.toString());
                    int principio = d.getComienzo() * paso; //el detector trabaja en segundos
                    int fin = d.getFin() * paso; //el detector trabaja en segundos
                    int medio = (principio + fin) / 2;
                    Intervalo ip = new Intervalo(principio, medio, (int) (d.getPos() * 100));
                    Intervalo iff = new Intervalo(medio, fin, (int) (d.getPos() * 100));
                    ip.setDatos(datos);
                    iff.setDatos(datos);
                    ip.setFrecuencia(signal.getSRate());
                    iff.setFrecuencia(signal.getSRate());
                    iff.setFechaBase(inicioAbsoluto);
                    ip.setFechaBase(inicioAbsoluto);
                    EpisodioDesaturacion e = new EpisodioDesaturacion(ip, iff, d.getValorBasal());
                    e.setDatos(datos);

                    //   DetectorDesaturacionesWrapper.generarEpisodioDesaturacion(e,Color.yellow,signal);
                    des.add(e);
                }
            }
        }
        return des;
    }

    static void generarEpisodioDesaturacion(EpisodioDesaturacion e, Color color,
                                            Signal satO2) throws SignalNotFoundException {
        //    color= Utilidades.getColor((short)e.getPosibilidad());
        DefaultIntervalMark m = new DefaultIntervalMark();
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
        JSWBManager.getJSWBManagerInstance().getSignalManager().addSignalMark(
                satO2.getName(), m);
    }

    public String getName() {
        return "Test";
    }

    private static long inicioAbsoluto = 0;

}
