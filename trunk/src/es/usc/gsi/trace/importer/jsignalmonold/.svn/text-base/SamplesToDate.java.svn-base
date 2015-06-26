package es.usc.gsi.trace.importer.jsignalmonold;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JOptionPane;


/**
 * Esta clase es util a la hora de averiguar la fecha a la cual corresponde una
 * determinada muestra. Esta clase se ha disenhado mediante el patron Singleton,
 * se configura pasandole una fecha de nase y probee metodos para saber dada una
 * frecuencia de muestreo a que fecha corresponde una muestra, o a que muestra corresponde una fecha.
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * @version 	0.6
 * @author 	Abraham Otero Quintana
 */

public class SamplesToDate {

//  static ResourceBundle res =ResourceBundle.getBundle("pintar.i18n.Res",Configuracion.localidad);
    private static boolean is_instancia = false;
    private static SamplesToDate instancia;
    private SimpleDateFormat parser_fecha_completa;
    private SimpleDateFormat parser_fecha_hora;
    private Date fecha_base;
    private SamplesToDate() throws Exception {
        if (is_instancia) {
            throw new Exception("Only one instance allowed");
        } else {
            is_instancia = true;
            parser_fecha_completa = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
            parser_fecha_hora = new SimpleDateFormat("HH:mm:ss:SSS");
        }
    }

    public void setFechaBase(Date _fecha) {
        fecha_base = _fecha;

    }

    /**
     * Data una fecha la toma como fecha base para todos os calculos que se le pidan
     * a continuacion: es la fecha donde se empieza a monitorizar.
     * Su argumento debe ser una fecha completa.
     * @return true si no hay fallos en la fecha, false en caso contrario
     */
    public boolean setFechaBase(String _fecha) {
        try {
            fecha_base = parser_fecha_completa.parse(_fecha);
            return true;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String getFechaBase() {
        return parser_fecha_completa.format(fecha_base);
    }

    /**
     * Devuelve el numero de muestras que han transcurrido desde la fecha base
     * hasta la fecha ctual
     * @param fecha_hasta fecha hasta lac ual deseamos sober cuantras muestras han
     * sucedido desde la fecha base
     * @param fs frecuencia a la cual esta muestreada la senhal
     * @return nuero de muestras transcurridas. Si hubo algun fallo en la oporacion
     * devuelve Long.MIN_VALUE
     */
    public long getSamplesTill(String fecha_hasta, float fs) {
        Date date_fecha_hasta = null;
        boolean fallo = false;
        //En este caso la fecha esta en formato corto
        if (fecha_hasta.length() < 9) {
            StringTokenizer tk = new StringTokenizer(fecha_hasta.trim(), ":", false);
            int hora = 0, minutos = 0, segundos = 0;
            try {
                hora = Integer.parseInt(tk.nextToken());
                minutos = Integer.parseInt(tk.nextToken());
                segundos = Integer.parseInt(tk.nextToken());
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                fallo = true;
            }
            Calendar calendario_fech_base = parser_fecha_completa.getCalendar();
            date_fecha_hasta = new Date(calendario_fech_base.get(Calendar.YEAR) -
                                        1900,
                                        calendario_fech_base.get(Calendar.MONTH),
                                        calendario_fech_base.get(Calendar.
                    DAY_OF_MONTH), hora, minutos,
                                        segundos);
        }
        //Si se cumple esto => llevava milisegundos
        else if (fecha_hasta.length() < 14) {
            StringTokenizer tk = new StringTokenizer(fecha_hasta.trim(), ":", false);
            int hora = 0, minutos = 0, segundos = 0, milisegundos = 0;
            try {
                hora = Integer.parseInt(tk.nextToken());
                minutos = Integer.parseInt(tk.nextToken());
                segundos = Integer.parseInt(tk.nextToken());
                milisegundos = Integer.parseInt(tk.nextToken());
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                fallo = true;
            }
            Calendar calendario_fech_base = parser_fecha_completa.getCalendar();
            date_fecha_hasta = new Date(calendario_fech_base.get(Calendar.YEAR) -
                                        1900,
                                        calendario_fech_base.get(Calendar.MONTH),
                                        calendario_fech_base.get(Calendar.
                    DAY_OF_MONTH), hora, minutos,
                                        segundos);
            date_fecha_hasta.setTime(date_fecha_hasta.getTime() + milisegundos);
        }
        //Si no es que la fecha era completa
        else {
            Calendar tmp = null;
            try {
                tmp = (Calendar) parser_fecha_completa.getCalendar().clone();
                date_fecha_hasta = parser_fecha_completa.parse(fecha_hasta);
            } catch (ParseException ex) {
                ex.printStackTrace();
                fallo = true;
            }
            //Para estar seguros de no modificar la fecha del parser
            //esta linea ha de ejecutarse tanto como si hay excepcion como si no
            parser_fecha_completa.setCalendar(tmp);
        }
        if (!fallo) {
            Date date_fecha_base = parser_fecha_completa.getCalendar().getTime();
            long diferencia_en_ms = date_fecha_hasta.getTime() -
                                    date_fecha_base.getTime();
            return (long) (diferencia_en_ms * fs / 1000);
        } else {
            return Long.MIN_VALUE;
        }
    }

    /**
     * Este metodo devuelve un String con la fecha que correponde a una muestra
     * muestras de una senhal con una frecuencia de muestreo fs. La calcula sobre
     * la fecha base con la que se configuro este SamplesToDate.
     * Segun el valor del ultimo boolean se devolvera la fecha con o sin milisegundos.
     * @param muestras
     * @param fs
     * @param corta
     * @param con_milisegundos
     * @return
     */
    public String getFecha(long muestras, float fs, boolean corta,
                           boolean con_milisegundos) {
        if (!corta || con_milisegundos) {
            return this.getFecha(muestras, fs, corta);
        } else {
            String fecha = this.getFecha(muestras, fs, corta);
            fecha = fecha.substring(0, fecha.lastIndexOf(":"));
            return fecha;
        }
    }

    /**
     * Este metodo devuelve un String con la fecha que correponde a una muestra
     * muestras de una senhal con una frecuencia de muestreo fs. La calcula sobre
     * la fecha base con la que se configuro este SamplesToDate.
     * @param muestras
     * @param fs
     * @param corta
     * @return
     */
    public String getFecha(long muestras, float fs, boolean corta) {
        Date fecha_respuesta = (Date) parser_fecha_completa.getCalendar().
                               getTime().
                               clone();
        long tiempo_total = fecha_respuesta.getTime() +
                            (long) (muestras * 1000 / fs);
        fecha_respuesta.setTime(tiempo_total);
        if (corta) {
            Calendar tmp = (Calendar) parser_fecha_completa.getCalendar().clone();
            String tmp2 = parser_fecha_hora.format(fecha_respuesta);
            parser_fecha_completa.setCalendar(tmp);
            return tmp2;
        } else {
            /*            Calendar tmp = (Calendar) parser_fecha_hora_completa_milisegundos.getCalendar().clone();
             String tmp2  = parser_fecha_hora_completa_milisegundos.format(fecha_respuesta);
             parser_fecha_hora_completa_milisegundos.setCalendar(tmp);
                    return tmp2;*/
            Calendar tmp = (Calendar) parser_fecha_completa.getCalendar().clone();
            String tmp2 = parser_fecha_completa.format(fecha_respuesta);
            parser_fecha_completa.setCalendar(tmp);
            return tmp2;

        }
    }

    /**
     * @return GestorDatos
     */
    public static SamplesToDate getInstancia() {
        if (is_instancia) {
            return instancia;
        } else {
            try {
                SamplesToDate.instancia = new SamplesToDate();
                return instancia;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
                return null;
            }

        }
    }

    private static final String texto_adevertencia_fecha =
            "<html>" +
            "<body>" +
            "<p><font color=\"#FF0000\" size=\"5\">Error en el formato de al menos una fecha!!</font></p>" +
            "<p><font color=\"#0000FF\" size=\"4\">Usted ha cometido un error al introducir alguna fecha. " +
            "Recuerde que hay </font></p>" +
            "<p><font color=\"#0000FF\" size=\"4\">dos posibles formatos para introducir una fecha:</font></p>" +
            "<p><font size=\"4\">&nbsp;</font></p>" +
            "<p><font size=\"4\">&nbsp;&nbsp; <font color=\"#0000FF\"> 1) Formato Corto:&nbsp; </font><font color=\"#009933\">hora:minutos:segundos" +
            "</font><font color=\"#0000FF\"> Ej: 22:30:00 serian las ocho</font></font></p>" +
            "<p><font size=\"4\">&nbsp;&nbsp; <font color=\"#0000FF\">&nbsp;&nbsp;&nbsp;&nbsp; y media de la " +
            "tarde. Al no indicar ni anho, ni dia ni mes se supone que</font></font></p>" +
            "<p><font size=\"4\">&nbsp;&nbsp; <font color=\"#0000FF\">&nbsp;&nbsp;&nbsp;&nbsp; son los del " +
            "inicio de la monitorizacion.</font></font></p>" +

            "<p><font size=\"4\">&nbsp;&nbsp; <font color=\"#0000FF\"> 2) Formato Corto con milisegundos:&nbsp;" +
            "</font><font color=\"#009933\">hora:minutos:segundos:milisegundos </font></font></p>" +
            "<p><font size=\"4\"><font color=\"#0000FF\"> &nbsp;&nbsp;&nbsp;&nbsp; Ej: 22:30:00:23 serian las ocho </font></font><font size=\"4\"><font color=\"#0000FF\"> " +
            "y media de la tarde con 23 ms.</font></font></p>" +
            "<p><font size=\"4\" color=\"#0000FF\"> &nbsp;&nbsp;&nbsp;&nbsp; que en " +
            "A&ntilde;o, mes y dis</font><font size=\"4\"><font color=\"#0000FF\">&nbsp; " +
            "son los del inicio de la monitorizacion.</font></font></p>" +

            "<p><font size=\"4\">&nbsp;&nbsp; <font color=\"#0000FF\"> 3)Formato Largo </font><font color=\"#009933\">" +
            "hora:minutos:segundos anho/mes/dia</font></font></p>" +
            "<p><font size=\"4\">&nbsp;&nbsp; <font color=\"#0000FF\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
            "Ej: 12:15:45 2002/06/25</font></font></p>" +
            "<p><font size=\"4\">&nbsp;</font></p>" +
            "<p><font color=\"#0000FF\" size=\"4\">Usted puede en cualquier momento emplear cualquier" +
            "formato, excepto </font></p>" +
            "<p><font color=\"#0000FF\" size=\"4\">para definir la fecha base de un registro, para lo cual se ha de</font></p>" +
            "<p><font color=\"#0000FF\" size=\"4\">emplear el formato Largo.</font></p>" +
            "</body>" +
            "</html>";
    private static final JOptionPane optionpane = new JOptionPane();

    public static String getTextoAdevertenciaFecha() {
        return texto_adevertencia_fecha;
    }

    public static JOptionPane getOptionpane() {
        return optionpane;
    }
}
