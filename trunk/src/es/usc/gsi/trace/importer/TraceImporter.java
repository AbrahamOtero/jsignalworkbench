package es.usc.gsi.trace.importer;

import java.io.File;
import java.util.*;

import javax.swing.Icon;

import es.usc.gsi.trace.importer.jsignalmonold.SamplesToDate;
import es.usc.gsi.trace.importer.monitorizacion.data.GestorDatos;
import es.usc.gsi.trace.importer.monitorizacion.dataIO.GestorIO;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.LoaderAdapter;

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
public class TraceImporter extends LoaderAdapter {


    public ArrayList getAvalaibleExtensions() {
        ArrayList l = new ArrayList();
        l.add("mon");
        return l;
    }

    /**
     * Devuelve una decision textual mas amplia de la funcionalidad del
     * plugin.
     *
     * @return descripcion textual larga
     */
    public String getDescription() {
        return "permite importar las senhales, junto con sus nombres, frecuencias de muestreo, etc. y las anotaciones contenidas en archivos de TRACE";
    }

    public Icon getIcon() {

        return new javax.swing.ImageIcon(getClass().getResource("trace.gif"));
    }

    /**
     * Proporciona el hombre del plugin.
     *
     * @return Nombre del plugin
     */
    public String getName() {
        return "TRACE importer";
    }

    /**
     * Devuelve la version del plugin.
     *
     * @return Version del plugin
     */
    public String getPluginVersion() {
        return "0.5";
    }

    /**
     * Devuelve una de extincion textual corta sobre la funcionalidad del
     * plugin.
     *
     * @return descripcion textual corta
     */
    public String getShortDescription() {
        return "TRACE importer";
    }

    /**
     * load
     *
     * @param file File
     * @param sm SignalManager
     * @param pm PluginManager
     * @return boolean
     * @throws Exception
     */
    public boolean load(File file) throws
            Exception {
        GestorIO gestor_io = GestorIO.getGestorIO();
        String name = file.getPath();
        boolean hubo_exito = gestor_io.cargarDatos(name, true);
        int num_datos = gestor_io.getNumDatos();
        if (name.endsWith(".mon") && hubo_exito) {
            GestorDatos gestorDatos = GestorDatos.getInstancia();
            gestorDatos.setArchivo(name);
            gestorDatos.setEstaGuardado(true);
            gestorDatos.setTieneArchivoAsociado(true);
            int numeroSenales = gestorDatos.getNumeroSenales();
            SamplesToDate.getInstancia().setFechaBase(new Date(0));
            long fecha = obtenerFecha(gestorDatos.getFechaBase());

            //la fecha que le ponemos al registro
            System.out.println((new Date(fecha)).toString());

            for (int i = 0; i < numeroSenales; i++) {
                String unidades = gestorDatos.getAlmacen().getLeyenda(i);
                JSWBManager.getSignalManager().addSignal(gestorDatos.getNombreSenal(i),
                        (float[]) gestorDatos.getDatos(i),
                        gestorDatos.getFsSenal(i), fecha, unidades);
            }
            return true;
        }
        return false;
    }


    private long obtenerFecha(String fechaBaseConversor) {
        StringTokenizer tk = new StringTokenizer(fechaBaseConversor);
        String horaMinSeg = tk.nextToken();
        String diaMesAno = tk.nextToken();
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

}
