package net.javahispano.plugins.basicstats.UI;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import net.javahispano.plugins.basicstats.MyFloat;
import net.javahispano.plugins.basicstats.ResultadosEstadisticos;

/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class PanelMostrarEstadisticos extends JPanel {
    private ResultadosEstadisticos resultados_estadisticos;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private BorderLayout borderLayout2 = new BorderLayout();
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private BorderLayout borderLayout3 = new BorderLayout();
    private JPanel panel_percentiles = new JPanel();
    private JCheckBox checkbox_o_label_percentiles = new JCheckBox();
    private JScrollPane scroll_panel_percentiles = new JScrollPane();
    //Codigo mio
    String tmp[] = {"Percentil", "Valor"};
    JTable table = new JTable(new String[6][2], tmp);
    private GridLayout gridLayout1 = new GridLayout();
    private JLabel jLabel3 = new JLabel();
    private JLabel media = new JLabel();
    private JLabel mediana = new JLabel();
    private JLabel jLabel6 = new JLabel();
    private JLabel varianza = new JLabel();
    private JLabel jLabel8 = new JLabel();
    private JLabel desviacion_tipica = new JLabel();
    private JLabel jLabel10 = new JLabel();
    private JLabel jLabel11 = new JLabel();
    private JLabel error_estandar = new JLabel();
    private JLabel jLabel13 = new JLabel();
    private JLabel cociente_vaiacion = new JLabel();
    private JLabel intervalo_de_confianza_checkbox = new JLabel();
    private JPanel panel_intervalos = new JPanel();
    private JLabel intervalo_de_confianza_menor = new JLabel();
    private JLabel intervalo_de_confianza_mayor = new JLabel();
    private JPanel jPanel6 = new JPanel();
    private TitledBorder titledBorder1;
    private JPanel jPanel7 = new JPanel();
    private BorderLayout borderLayout4 = new BorderLayout();
    private JPanel jPanel8 = new JPanel();
    private JLabel jLabel4 = new JLabel();
    private JScrollPane jPanel9 = new JScrollPane();
    private JTextArea comentario = new JTextArea();
    private BorderLayout borderLayout6 = new BorderLayout();
    private JPanel jPanel10 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JPanel jPanel11 = new JPanel();
    private GridLayout gridLayout2 = new GridLayout();
    private JLabel jLabel5 = new JLabel();
    private JTextField nombre_senal = new JTextField();
    private JLabel fecha_inicio = new JLabel();
    private JLabel jLabel9 = new JLabel();
    private JLabel jLabel12 = new JLabel();
    private JLabel fecha_fin = new JLabel();
    private GridLayout gridLayout3 = new GridLayout();

    /**
     * Si se le pasa false no permite borrar los percentiles, ya que cambia
     * el checbox por un label.
     * @param resultados_estadisticos
     * @param puede_borrar_percentiles
     */
    public PanelMostrarEstadisticos(ResultadosEstadisticos
                                    resultados_estadisticos,
                                    boolean puede_borrar_percentiles) {
        this(resultados_estadisticos);
        if (!puede_borrar_percentiles) {
            panel_percentiles.remove(checkbox_o_label_percentiles);
            JLabel label_percentiles = new JLabel();
            label_percentiles.setFont(new java.awt.Font("Dialog", 1, 14));
            label_percentiles.setForeground(Color.blue);
            label_percentiles.setToolTipText("");
            label_percentiles.setText("Percentiles:");
            panel_percentiles.add(label_percentiles);
        }

    }

    public PanelMostrarEstadisticos(ResultadosEstadisticos
                                    resultados_estadisticos) {
        this.resultados_estadisticos = resultados_estadisticos;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        nombre_senal.setText(resultados_estadisticos.getNombreSenhal());
        fecha_fin.setText(resultados_estadisticos.getFechaFin());
        fecha_inicio.setText(resultados_estadisticos.getFechaInicio());
        media.setText(MyFloat.formateaNumero(resultados_estadisticos.
                                             getMediaAritmetica()));
        mediana.setText(MyFloat.formateaNumero(resultados_estadisticos.
                                               getMediana()));
        varianza.setText(MyFloat.formateaNumero(resultados_estadisticos.
                                                getVarianza()));
        desviacion_tipica.setText(MyFloat.formateaNumero(
                resultados_estadisticos.getDesviacionTipica()));
        error_estandar.setText(MyFloat.formateaNumero(resultados_estadisticos.
                getErrorEstandar()));
        cociente_vaiacion.setText(MyFloat.formateaNumero(
                resultados_estadisticos.getCocienteDeVariacion()));
        float[] tmp = new float[2];
        tmp[0] = resultados_estadisticos.getIntervaloDeConfianza()[0];
        tmp[1] = resultados_estadisticos.getIntervaloDeConfianza()[1];
        this.setIntervaloDeConfianza(tmp);
        if (resultados_estadisticos.getPercentiles() != null) {
            HashMap percentiles_has_map = resultados_estadisticos.
                                          getPercentiles();
            Set percentiles_set = percentiles_has_map.entrySet();
            Iterator it = percentiles_set.iterator();
            int num_percentiles = 0;
            //Ponemos todos los percentiles
            while (it.hasNext()) {
                Map.Entry percentil_map_entry = ((Map.Entry) (it.next()));
                Object percentil = percentil_map_entry.getKey();
                Object percentil_valor = percentil_map_entry.getValue();
                percentil_valor = MyFloat.formateaNumero((String)
                        percentil_valor);
                table.setValueAt(percentil, num_percentiles, 0);
                table.setValueAt(percentil_valor, num_percentiles, 1);
                num_percentiles++;
            }
        } else {
            jPanel3.remove(panel_percentiles);
            jPanel3.remove(scroll_panel_percentiles);
            jPanel3.setPreferredSize(new Dimension(72, 200));
            jPanel7.setPreferredSize(new Dimension(72, 170));
            validate();
        }

        comentario.setText(resultados_estadisticos.getComentario());

    }

    void jbInit() throws Exception {
        checkbox_o_label_percentiles.setSelected(true);
        titledBorder1 = new TitledBorder("");
        this.setLayout(borderLayout1);
        jPanel1.setLayout(borderLayout2);
        jPanel3.setLayout(borderLayout3);
        checkbox_o_label_percentiles.setFont(new java.awt.Font("Dialog", 1, 14));
        checkbox_o_label_percentiles.setForeground(Color.blue);
        checkbox_o_label_percentiles.setToolTipText("");
        checkbox_o_label_percentiles.setText("Percentiles:");
        jPanel3.setPreferredSize(new Dimension(72, 250));
        jPanel2.setLayout(gridLayout1);
        gridLayout1.setColumns(2);
        gridLayout1.setHgap(2);
        gridLayout1.setRows(7);
        gridLayout1.setVgap(2);
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel3.setForeground(Color.blue);
        jLabel3.setText("Media:");
        media.setFont(new java.awt.Font("Dialog", 1, 14));
        media.setText("jLabel4");
        mediana.setFont(new java.awt.Font("Dialog", 1, 14));
        mediana.setText("jLabel5");
        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel6.setForeground(Color.blue);
        jLabel6.setText("Varianza:");
        varianza.setFont(new java.awt.Font("Dialog", 1, 14));
        varianza.setText("jLabel7");
        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel8.setForeground(Color.blue);
        jLabel8.setText("Desviacion tipica:");
        desviacion_tipica.setFont(new java.awt.Font("Dialog", 1, 14));
        desviacion_tipica.setText("jLabel9");
        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel10.setForeground(Color.blue);
        jLabel10.setText("Mediana:");
        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel11.setForeground(Color.blue);
        jLabel11.setText("Error estandar:");
        error_estandar.setFont(new java.awt.Font("Dialog", 1, 14));
        error_estandar.setText("jLabel12");
        jLabel13.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel13.setForeground(Color.blue);
        jLabel13.setText("Cociente de variacion:");
        cociente_vaiacion.setFont(new java.awt.Font("Dialog", 1, 14));
        cociente_vaiacion.setText("jLabel14");
        intervalo_de_confianza_checkbox.setFont(new java.awt.Font("Dialog", 1,
                14));
        intervalo_de_confianza_checkbox.setForeground(Color.blue);
        intervalo_de_confianza_checkbox.setToolTipText("");
        intervalo_de_confianza_checkbox.setText(
                "Intervalo de confianza del 95%:");
        intervalo_de_confianza_menor.setFont(new java.awt.Font("Dialog", 1, 14));
        intervalo_de_confianza_mayor.setFont(new java.awt.Font("Dialog", 1, 14));
        panel_intervalos.setLayout(gridLayout3);
        jPanel2.setBorder(titledBorder1);
        this.setFont(new java.awt.Font("Dialog", 1, 14));
        jPanel7.setLayout(borderLayout4);
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel4.setForeground(Color.blue);
        jLabel4.setText("Comentario:");
        jPanel6.setLayout(borderLayout6);
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 16));
        jLabel1.setForeground(Color.red);
        jLabel1.setText("Resultado de los estadisticos:");
        jPanel11.setLayout(gridLayout2);
        gridLayout2.setColumns(2);
        gridLayout2.setRows(3);
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel5.setForeground(new Color(78, 110, 255));
        jLabel5.setToolTipText(
                "Nombre de la senhal sobre la cual se efectuo el calculo.");
        jLabel5.setText("Nombre de la senhal:");
        nombre_senal.setToolTipText(
                "Nombre de la senhal sobre la cual se efectuo el calculo.");
        nombre_senal.setText("jTextField1");
        fecha_inicio.setFont(new java.awt.Font("Dialog", 1, 14));
        fecha_inicio.setToolTipText(
                "Instante de esa senhal a partir del cual se realizo el calculo.");
        fecha_inicio.setText("jLabel7");
        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel9.setForeground(new Color(78, 110, 255));
        jLabel9.setToolTipText(
                "Instante de esa senhal a partir del cual se realizo el calculo.");
        jLabel9.setText("Tiempo de incio:");
        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel12.setForeground(new Color(78, 110, 255));
        jLabel12.setToolTipText(
                "Instante de esa senhal hasta donde se realizo el calculo.");
        jLabel12.setText("Tiempo de fin:");
        fecha_fin.setFont(new java.awt.Font("Dialog", 1, 14));
        fecha_fin.setToolTipText(
                "Instante de esa senhal hasta donde se realizo el calculo.");
        fecha_fin.setText("jLabel14");
        jPanel11.setBorder(titledBorder1);
        jPanel7.setFont(new java.awt.Font("Dialog", 1, 14));
        jPanel7.setPreferredSize(new Dimension(446, 130));
        table.setToolTipText(
                "Si desea anhadir mas percentiles simplemente edite las columans de " +
                "la izquierda.");
        gridLayout3.setColumns(2);
        this.add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel2, BorderLayout.CENTER);
        jPanel1.add(jPanel3, BorderLayout.SOUTH);
        jPanel3.add(panel_percentiles, BorderLayout.NORTH);
        panel_percentiles.add(checkbox_o_label_percentiles, null);
        jPanel3.add(jPanel7, BorderLayout.SOUTH);
        jPanel7.add(jPanel8, BorderLayout.NORTH);
        jPanel8.add(jLabel4, null);
        jPanel7.add(jPanel9, BorderLayout.CENTER);
        jPanel3.add(scroll_panel_percentiles, BorderLayout.CENTER);
        scroll_panel_percentiles.getViewport().add(table, null);
        jPanel9.getViewport().add(comentario, null);
        jPanel1.add(jPanel6, BorderLayout.NORTH);
        jPanel6.add(jPanel10, BorderLayout.NORTH);
        jPanel10.add(jLabel1, null);
        jPanel6.add(jPanel11, BorderLayout.CENTER);
        jPanel11.add(jLabel5, null);
        jPanel11.add(nombre_senal, null);
        jPanel11.add(jLabel9, null);
        jPanel11.add(fecha_inicio, null);
        jPanel11.add(jLabel12, null);
        jPanel11.add(fecha_fin, null);
        jPanel2.add(jLabel3, null);
        jPanel2.add(media, null);
        jPanel2.add(intervalo_de_confianza_checkbox, null);
        jPanel2.add(panel_intervalos, null);
        jPanel2.add(jLabel10, null);
        jPanel2.add(mediana, null);
        jPanel2.add(jLabel6, null);
        jPanel2.add(varianza, null);
        jPanel2.add(jLabel8, null);
        jPanel2.add(desviacion_tipica, null);
        jPanel2.add(jLabel11, null);
        jPanel2.add(error_estandar, null);
        jPanel2.add(jLabel13, null);
        jPanel2.add(cociente_vaiacion, null);
        panel_intervalos.add(intervalo_de_confianza_menor, null);
        panel_intervalos.add(intervalo_de_confianza_mayor, null);

    }


///////////SIMPLES METODOS GET SET ///////////////////////////////////////////////////7
    public String getMediaAritmetica() {
        return media.getText();
    }

    public String getMediana() {
        return mediana.getText();
    }

    public String getVarianza() {
        return varianza.getText();
    }

    public String getDesviacionTipica() {
        return desviacion_tipica.getText();
    }

    public String getErrorEstandar() {
        return error_estandar.getText();
    }

    public String getCocienteDeVariacion() {
        return cociente_vaiacion.getText();
    }

    public String getIntervaloDeConfianzaMayor() {
        String full_text = intervalo_de_confianza_mayor.getText();
        full_text = full_text.substring(1, full_text.length() - 1);
        return full_text;
    }

    public String getIntervaloDeConfianzaMenor() {
        return intervalo_de_confianza_menor.getText();
    }

    public void setMediaAritmetica(float _media_aritmetica) {
        media.setText(Float.toString(_media_aritmetica));
    }

    public void setMediana(float _mediana) {
        mediana.setText(Float.toString(_mediana));
    }

    public void setVarianza(float _varianza) {
        varianza.setText(Float.toString(_varianza));
    }

    public void setDesviacion_tipica(float _desviacion_tipica) {
        desviacion_tipica.setText(Float.toString(_desviacion_tipica));
    }

    public void setErrorEstandar(float _error_estandar) {
        error_estandar.setText(Float.toString(_error_estandar));
    }

    public void setCocienteDeVariacion(float _cociente_de_variacion) {
        cociente_vaiacion.setText(Float.toString(_cociente_de_variacion));
    }

    public void setIntervaloDeConfianza(float[] _intervalo_de_confianza) {
        intervalo_de_confianza_menor.setText("[" +
                                             MyFloat.formateaNumero(_intervalo_de_confianza[
                1]) + ",");
        intervalo_de_confianza_mayor.setText(MyFloat.formateaNumero(
                _intervalo_de_confianza[0]) + "]");
    }

    public String getComentario() {
        return comentario.getText();
    }

    public String getFechaInicio() {
        return fecha_inicio.getText();
    }

    public String getFechaFin() {
        return fecha_fin.getText();
    }

    public String getNombreSenhal() {
        return nombre_senal.getText();
    }

    public void setComentario(String _comentario) {
        comentario.setText(_comentario);
    }

    public void setFechaInicio(String _fecha_inicio) {
        fecha_inicio.setText(_fecha_inicio);
    }

    public void setFechaFin(String _fecha_fin) {
        fecha_fin.setText(_fecha_fin);
    }

    public void setNombreSenhal(String _nombre_senhal) {
        nombre_senal.setText(_nombre_senhal);
    }

    public ResultadosEstadisticos getResultadosEstadisticos() {
        return resultados_estadisticos;
    }

    ///////////SIMPLES METODOS GET SET ///////////////////////////////////////////////////7

    /**
     * Indica si hay que invalidar los percentiles.
     * @return
     */
    public boolean invalidarPercentiles() {
        return!(checkbox_o_label_percentiles.isSelected());
    }
}
