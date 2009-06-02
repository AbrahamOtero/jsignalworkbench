package es.usc.gsi.conversorDatosMIT;

import java.util.*;

import javax.swing.JOptionPane;

import es.usc.gsi.conversorDatosMIT.ficheros.Parametro;
import es.usc.gsi.conversorDatosMIT.interfaz.PanelPrincipal;
import es.usc.gsi.trace.importer.jsignalmonold.SamplesToDate;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.SignalManager;

/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class PideDatosAlConversor extends Thread {

    private float[][] rangos;
    private int max_numero_datos;
    private PanelPrincipal conversor;
    private JSWBManager j;
    public static final String error_quedarse_sin_memoria = "<html>" +
            "<body text=\"#000000\">" +
            "<p align=\"center\"><font color=\"#FF0000\" size=\"5\">Error al exportar los datos </font></p></p>" +
            "<p><font size=\"4\" color=\"#0000FF\">El equipo no tiene memoria virtual suficiente </font></p>" +
            "<p><font size=\"4\" color=\"#0000FF\">para importar todos los datos que usted ha</font></p>" +
            "<p><font size=\"4\" color=\"#0000FF\">selecionado. Importe un intervalo temporal</font></p>" +
            "<p><font size=\"4\" color=\"#0000FF\">menos o disminuya la frecuecia de muestreo</font></p>" +
            "<p><font size=\"4\" color=\"#0000FF\">de las se&ntilde;ales que est&aacute; adquiriendo.</font></p>" +
            "</body>" +
            "</html>";

    // private CuadroDeEspera espera;
    private float paso;
    int datos_por_aaray;

    public PideDatosAlConversor(PanelPrincipal conversor
                                , JSWBManager jswbManager) {
        this.conversor = conversor;
        this.j = jswbManager;
    }

    /**
     * metodo que coge los datos del conversor y los carga en la interface.
     */
    public boolean cargarDatos() {
        max_numero_datos = Integer.MIN_VALUE;

//LLAMADA A Codigo de CONVERSOTMIT
        Parametro[] parametros = null;
        try {
            parametros = conversor.getParametros();
        } catch (OutOfMemoryError ex) {
            JOptionPane.showMessageDialog(conversor, error_quedarse_sin_memoria,
                                          "ERROR!!!",
                                          JOptionPane.ERROR_MESSAGE);
        }

//Si no cancelo la operacion
        if (parametros != null) {
            float[][] datos = new float[parametros.length][];
            String[] nombresSenales = new String[parametros.length];
            String[] unidadesSenales = new String[parametros.length];
            float[] fs = new float[parametros.length];
            rangos = new float[parametros.length + 1][2];

            //Esto es para estimar que % se ha pasado:
            paso = 100 / parametros.length * parametros[0].getValores().length;
            paso = paso / 400;
            datos_por_aaray = parametros[0].getValores().length;
            //Recojo los datos del array de parametros
            for (int i = 0; i < parametros.length; i++) {
                datos[i] = arrayIntToFloat(parametros[i].getValores(),
                                           parametros[i].getGanancia(), i);
                nombresSenales[i] = parametros[i].getNombreParametro();
                fs[i] = parametros[i].getFrecuenciaMuestreo();
                unidadesSenales[i] = parametros[i].getUnidades();

            }
            rangos[parametros.length][0] = 0;
            rangos[parametros.length][1] = 100;
            //Configuramos la fecha base del registro:
            //Asumo que todos tiene la misma fecha de incio y cojo la del priemr parametro
            String fechaBaseConversor = parametros[0].getFechaInicio();
            long fecha = obtenerFecha(fechaBaseConversor);
            long duracionMaxima = 0;

            SignalManager s = j.getSignalManager();
            s.removeAllSignals();
            for (int i = 0; i < parametros.length; i++) {

                s.addSignal(nombresSenales[i], datos[i], fs[i], fecha,
                            unidadesSenales[i]);
                long duracion = (long) ((datos[i].length + 1) / fs[i]);
                if (duracionMaxima < duracion) {
                    duracionMaxima = duracion;
                }
                //       gestor_datos.setNombreSenal(i, nombres_senales[i]);
                //        gestor_datos.setLeyenda(i, unidades_senales[i]);
                //        gestor_datos.setFs(i, fs[i]);
                //        gestor_datos.setRango(i, rangos[i]);
                //       gestor_datos.setLeyenda(i, parametros[i].getUnidades());
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * @todo ojo, no tengo claro si en el formato MIT los dis y meses se empiezan
     * a contar en 0 o en 1. He supuesto que se empiezan a contar en 1.
     * @param fechaBaseConversor String
     * @return long
     */
    private long obtenerFecha(String fechaBaseConversor) {
        StringTokenizer tk = new StringTokenizer(fechaBaseConversor);
        String diaMesAno = tk.nextToken();
        String horaMinSeg = tk.nextToken();
        SamplesToDate.getInstancia().setFechaBase(new Date(0));
        tk = new StringTokenizer(horaMinSeg, ":", false);
        int hora = 0, minutos = 0, segundos = 0;
        try {
            hora = Integer.parseInt(tk.nextToken());
            minutos = Integer.parseInt(tk.nextToken());
            segundos = Integer.parseInt(tk.nextToken());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return 0;
        }
        tk = new StringTokenizer(diaMesAno, "/", false);
        int ano = 0, mes = 0, dia = 0;
        try {
            dia = Integer.parseInt(tk.nextToken());
            mes = Integer.parseInt(tk.nextToken());
            ano = Integer.parseInt(tk.nextToken());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return 0;
        }

        Calendar d = new GregorianCalendar(ano, mes, dia, hora, minutos,
                                           segundos);
        return d.getTime().getTime();
    }

    /**
     * Se le pasa una array de int y lo devuelve como array de float
     * @param datos_int
     * @return
     */
    private final float[] arrayIntToFloat(int[] datos_int, float ganancia,
                                          int senal) {
        float[] datos = new float[datos_int.length];
        float min = Float.MAX_VALUE;
        float max = Float.MIN_VALUE;
        for (int i = 0; i < datos_int.length; i++) {
            datos[i] = datos_int[i];
            //@todo modificado hace poco datos[i] /= ganancia;
            if (datos[i] > max) {
                max = datos[i];
            }
            if (datos[i] < min) {
                min = datos[i];
            }

            //Para el progrss bar
            if (i % 400 == 0) {
                //    espera.actulizaProgressBar((int)((senal*datos_por_aaray + i)* paso));
            }
        }
        if (max_numero_datos < datos.length) {
            max_numero_datos = datos.length;
        }

        rangos[senal][0] = Math.round(min);
        rangos[senal][1] = Math.round(max);
        return datos;
    }

    public void run() {
        setPriority(Thread.MIN_PRIORITY);
        this.cargarDatos();
        //   espera.cambiaEstado();

    }

}
