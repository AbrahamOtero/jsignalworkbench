package net.javahispano.plugins.basicstats.UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.*;

import net.javahispano.plugins.basicstats.BasicStatisticsPlugin;
import net.javahispano.plugins.basicstats.ResultadosEstadisticos;


/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class AllStatisticsDialog extends JDialog {

    private JPanel jPanel1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel3 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JPanel jPanel4 = new JPanel();
    private JButton aceptar = new JButton();
    private JButton borrar = new JButton();
    private JButton borrar_todo = new JButton();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private static final String borrar_uno = "<html><p><font size=\"5\" color=\"#FF0000\"><b>Va a borrar los estad&iacute;sticos</b></font></p><p><font size=\"5\" color=\"#FF0000\"><b> que est&aacute; visualizando! </b></font></p></body></html>";
    private static final String borrar_todos = "<html><p><font size=\"5\" color=\"#FF0000\"><b>Va a borrar todos</font><font size=\"5\" color=\"#FF0000\"><b> los</font></p><p></b><font size=\"5\" color=\"#FF0000\"><b> estad&iacute;sticos calculados!</b></font></p></body></html>";
    private BasicStatisticsPlugin statisticsPlugin;

    private FlowLayout flowLayout1 = new FlowLayout();

    public AllStatisticsDialog(BasicStatisticsPlugin statisticsPlugin,
                               Window parent, boolean modal) {
        super(parent, "Todos los estadsticos almacenados en el entorno", Dialog.ModalityType.APPLICATION_MODAL);
        this.statisticsPlugin = statisticsPlugin;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collection coleccion = statisticsPlugin.getStatisticsCollection();
        Iterator it = coleccion.iterator();
        while (it.hasNext()) {
            ResultadosEstadisticos resultado_estdaitico = (
                    ResultadosEstadisticos) it.next();
            PanelMostrarEstadisticos panel_estaditico = new
                    PanelMostrarEstadisticos(
                            resultado_estdaitico, false);
            jTabbedPane1.add(resultado_estdaitico.getNombreSenhal(),
                             panel_estaditico);
        }
        Point d = parent.getLocation();
        Dimension t = parent.getSize();
        setLocation((int) (d.x + t.getWidth() / 2 - 350), (int) ((d.y) + 5));
        this.setSize(500, 600);

    }

    private void jbInit() throws Exception {
        jPanel1.setLayout(borderLayout1);
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel1.setForeground(Color.red);
        jLabel1.setText("Estadisticos calculados:");
        aceptar.setText("Aceptar");

        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aceptar_actionPerformed(e);
            }
        });
        borrar.setText("Borrar");

        borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                borrar_actionPerformed(e);
            }
        });
        borrar_todo.setText("Borrar Todos");
        borrar_todo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                borrar_todo_actionPerformed(e);
            }
        });
        jPanel4.setLayout(flowLayout1);
        flowLayout1.setHgap(15);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel3, BorderLayout.NORTH);
        jPanel3.add(jLabel1, null);
        jPanel1.add(jPanel4, BorderLayout.SOUTH);
        jPanel4.add(aceptar, null);
        jPanel4.add(borrar, null);
        jPanel4.add(borrar_todo, null);
        jPanel1.add(jTabbedPane1, BorderLayout.CENTER);
    }

    void aceptar_actionPerformed(ActionEvent e) {
        for (int i = 0; i < jTabbedPane1.getTabCount(); i++) {
            PanelMostrarEstadisticos panel = (PanelMostrarEstadisticos)
                                             jTabbedPane1.getComponentAt(i);
            ResultadosEstadisticos resultados_estadisticos = panel.
                    getResultadosEstadisticos();
            String nuevo_nombre_senal = panel.getNombreSenhal();
            String key = resultados_estadisticos.getNombreSenhal() +
                         resultados_estadisticos.getFechaInicio() +
                         resultados_estadisticos.getFechaFin();
            statisticsPlugin.eraseStatistics(key);
            resultados_estadisticos.setNombreSenhal(nuevo_nombre_senal);
            resultados_estadisticos.setComentario(panel.getComentario());
            statisticsPlugin.addStatistics(resultados_estadisticos);
        }

        dispose();
    }

    void borrar_actionPerformed(ActionEvent e) {
        if (JOptionPane.showConfirmDialog(this, borrar_uno, "Adevertencia!!!",
                                          JOptionPane.WARNING_MESSAGE)
            == JOptionPane.YES_OPTION) {
            PanelMostrarEstadisticos panel = (PanelMostrarEstadisticos)
                                             jTabbedPane1.getSelectedComponent();
            jTabbedPane1.remove(panel);
            ResultadosEstadisticos resultados_estadisticos = panel.
                    getResultadosEstadisticos();
            statisticsPlugin.eraseStatistics(resultados_estadisticos.
                                             getNombreSenhal() +
                                             resultados_estadisticos.
                                             getFechaInicio() +
                                             resultados_estadisticos.
                                             getFechaFin());
        }

    }

    void borrar_todo_actionPerformed(ActionEvent e) {
        if (JOptionPane.showConfirmDialog(this, borrar_todos, "Adevertencia!!!",
                                          JOptionPane.WARNING_MESSAGE)
            == JOptionPane.YES_OPTION) {
            statisticsPlugin.eraseAllStatistics();
            dispose();
        }
    }


}
