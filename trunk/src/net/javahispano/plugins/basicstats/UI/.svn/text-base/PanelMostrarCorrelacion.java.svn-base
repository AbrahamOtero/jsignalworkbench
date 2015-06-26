package net.javahispano.plugins.basicstats.UI;

import java.awt.*;

import javax.swing.*;

import net.javahispano.plugins.basicstats.ResultadoCorrelacion;
import net.javahispano.plugins.basicstats.RutinasEstadisticas;

public class PanelMostrarCorrelacion extends JPanel {
    private ResultadoCorrelacion correlacion;
    private JPanel jPanel1 = new JPanel();
    private BorderLayout borderLayout2 = new BorderLayout();
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private BorderLayout borderLayout3 = new BorderLayout();
    private JEditorPane texto_resumen_correlacion = new JEditorPane();
    private JPanel jPanel4 = new JPanel();
    private JLabel jLabel2 = new JLabel();
    private JPanel panel_auxiliar = new JPanel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTextArea comentario = new JTextArea();
    private JPanel jPanel5 = new JPanel();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanel jPanel6 = new JPanel();
    private JPanel jPanel7 = new JPanel();
    private JPanel jPanel8 = new JPanel();
    private JPanel jPanel9 = new JPanel();
    private JLabel jLabel3 = new JLabel();
    private JLabel coef_correlacion = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JLabel nivel_de_significacion = new JLabel();
    private JLabel jLabel1 = new JLabel();
    private JTextField nombre_correlacion = new JTextField();
    private FlowLayout flowLayout1 = new FlowLayout();
    private BorderLayout borderLayout1 = new BorderLayout();
    private BorderLayout borderLayout4 = new BorderLayout();

    public PanelMostrarCorrelacion(ResultadoCorrelacion res) {
        this();
        this.setCorrelacion(res);
    }

    public PanelMostrarCorrelacion() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        texto_resumen_correlacion.setFont(new Font("Dialog", Font.BOLD, 14));
    }

    void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jPanel1.setLayout(borderLayout2);
        borderLayout2.setHgap(2);
        borderLayout2.setVgap(2);
        jPanel3.setLayout(borderLayout3);
        texto_resumen_correlacion.setBackground(UIManager.getColor(
                "Panel.background"));
        texto_resumen_correlacion.setDisabledTextColor(UIManager.getColor(
                "Panel.background"));
        texto_resumen_correlacion.setEditable(false);
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel2.setForeground(Color.blue);
        jLabel2.setText("Comentario:");
        jPanel5.setLayout(gridLayout1);
        gridLayout1.setColumns(2);
        gridLayout1.setRows(2);
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel3.setForeground(Color.blue);
        jLabel3.setText("Coeficiente de correlacion:");
        coef_correlacion.setFont(new java.awt.Font("Dialog", 1, 14));
        coef_correlacion.setForeground(Color.blue);
        coef_correlacion.setText("jLabel4");
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel5.setForeground(Color.blue);
        jLabel5.setText("Es significativo:");
        nivel_de_significacion.setFont(new java.awt.Font("Dialog", 1, 14));
        nivel_de_significacion.setForeground(Color.blue);
        nivel_de_significacion.setText("jLabel6");
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel1.setForeground(Color.blue);
        jLabel1.setText("Nombre de identificacion:");
        nombre_correlacion.setColumns(15);
        jPanel2.setLayout(flowLayout1);
        flowLayout1.setHgap(15);
        panel_auxiliar.setPreferredSize(new Dimension(4, 130));
        panel_auxiliar.setLayout(borderLayout4);
        this.add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel2, BorderLayout.NORTH);
        jPanel2.add(jLabel1, null);
        jPanel2.add(nombre_correlacion, null);
        jPanel1.add(jPanel3, BorderLayout.CENTER);
        jPanel3.add(texto_resumen_correlacion, BorderLayout.CENTER);
        jPanel3.add(jPanel4, BorderLayout.SOUTH);
        jPanel4.add(jLabel2, null);
        jPanel3.add(jPanel5, BorderLayout.NORTH);
        jPanel5.add(jPanel6, null);
        jPanel6.add(jLabel3, null);
        jPanel5.add(jPanel9, null);
        jPanel9.add(coef_correlacion, null);
        panel_auxiliar.add(jScrollPane1, BorderLayout.CENTER);
        jPanel1.add(panel_auxiliar, BorderLayout.SOUTH);
        jScrollPane1.getViewport().add(comentario, null);
        jPanel5.add(jPanel8, null);
        jPanel8.add(jLabel5, null);
        jPanel5.add(jPanel7, null);
        jPanel7.add(nivel_de_significacion, null);
    }

    /**
     * Devuelve el cometario.
     * @return
     */
    public String getComentario() {
        return comentario.getText();
    }

    /**
     * Modifica el comentario de este panel.
     * @param s
     */
    void setComentario(String s) {
        comentario.setText(s);
    }

    /**
     * Pone el texto que describe lo que se calculo en esta correlacion.
     * @param s
     */
    void setTextoDescriptivo(String s) {
        texto_resumen_correlacion.setText(s);
    }

    /**
     * Pone el texto que describe lo que se calculo en esta correlacion.
     * @param s
     */
    public String getTextoDescriptivo() {
        return texto_resumen_correlacion.getText();
    }

    /**
     * Inica cual es la correlacion a mostrar.
     * @param s
     */
    void setCorrelacion(String s) {
        coef_correlacion.setText(s);
    }

    /**
     * Inica cual es la correlacion a mostrar.
     * @param s
     */
    public String getCorrelacionCoeficiente() {
        return coef_correlacion.getText();
    }

    /**
     * Indica cual es el nivel de significacion a mostrar.
     * @param s
     */
    void setNivelDeCorrelacion(String s) {
        nivel_de_significacion.setText(s);
    }

    /**
     * Indica cual es el nivel de significacion a mostrar.
     * @param s
     */
    public String getNivelDeCorrelacion() {
        return nivel_de_significacion.getText();
    }

    /**
     * Pone el nombre de la correlacion que muestra este panel.
     * @param s
     */
    void setNombreCorrelacion(String s) {
        nombre_correlacion.setText(s);
    }

    /**
     * Indica que  nombre de correlacion muestra este panel.
     * @param s
     */
    public String getNombreCorrelacion() {
        return nombre_correlacion.getText();
    }

    /**
     * Pone la correlacion indicada en este panel
     */
    public void setCorrelacion(ResultadoCorrelacion correlacion) {
        this.correlacion = correlacion;
        this.setComentario(correlacion.getComentario());
        this.setNombreCorrelacion(correlacion.getNombre());
        this.setCorrelacion(Float.toString(correlacion.getNivelDeSignificacion()));
        this.setNivelDeCorrelacion(RutinasEstadisticas.getTextoDeSignificacion(
                correlacion.getNivelDeSignificacionDiscreto()));
        this.setTextoDescriptivo(correlacion.getTextoDescriptivo());
    }

    /**
     * Devuelve la correlacion que muestr este panel.
     * @return
     */
    public ResultadoCorrelacion getCorrelacion() {
        return correlacion;
    }

    /**
     * Devuelve la correlacion que muestra este panel actualizada con su nuevo cometario
     * y nombre.
     * @return
     */
    public ResultadoCorrelacion getCorrelacionActualizada() {
        correlacion.setNombre(nombre_correlacion.getText());
        correlacion.setComentario(comentario.getText());
        return correlacion;
    }
}
