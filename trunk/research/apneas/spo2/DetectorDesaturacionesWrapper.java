package research.apneas.spo2;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalNotFoundException;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;
import research.apneas.spo2.Desaturacion;
import research.apneas.spo2.DetectorDesaturaciones;
import research.apneas.EpisodioDesaturacion;
import research.apneas.Utilidades;
import research.apneas.Intervalo;

import java.util.ArrayList;
import java.awt.*;

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

    private   ArrayList<EpisodioDesaturacion>  test(float[] datos, Signal signal) {
         ArrayList<EpisodioDesaturacion> des = new  ArrayList<EpisodioDesaturacion>();
        DetectorDesaturaciones b = new DetectorDesaturaciones();
        b.setTiempoInicial(inicioAbsoluto);
        float periodo = 1 / signal.getSRate();
        //Si el periodo de muestreo no es un entero
        if (Math.abs(Math.round(1 / periodo) - 1 / periodo) > 0.05F) {
            throw new RuntimeException(
                    "Error; la se�al de saturaci�n no tiene una periodo adecuada ");
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
                    int principio = d.getComienzo()*paso;  //el detector trabaja en segundos
                    int fin = d.getFin() *paso;          //el detector trabaja en segundos
                    int medio = (principio+ fin)/2;
                    Intervalo ip=new Intervalo(principio, medio,(int)(d.getPos()*100));
                    Intervalo iff=new Intervalo(medio,fin,(int)(d.getPos()*100));
                    ip.setDatos(datos);
                    iff.setDatos(datos);             
                    ip.setFrecuencia(signal.getSRate());
                    iff.setFrecuencia(signal.getSRate());
                    iff.setFechaBase(inicioAbsoluto);
                    ip.setFechaBase(inicioAbsoluto);
                    EpisodioDesaturacion e = new EpisodioDesaturacion(ip,iff,d.getValorBasal());
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

        String texto = "Duraci�n: " + e.getDuracionEpisodio() + ",\n" +
                       "Valor m�nimo: " + e.getMinimo() + ",\n" +
                       "Ca�da en la saturaci�n: " + e.getCaidaSatO2() + ",\n" +
                       "Posibilidad: " + e.getPosibilidad() + ",\n" +
                       "Tiempo de ca�da: " + e.getTiempoBajada() + ",\n" +
                       "Tiempo de subida: " + e.getTiempoSubida();
        m.setComentary(texto);
        m.setTitle("Episodio de desaturaci�n");
        JSWBManager.getJSWBManagerInstance().getSignalManager().addSignalMark(
           satO2.getName(), m);
    }
    public String getName() {
        return "Test";
    }

    private static long inicioAbsoluto = 0;

}
