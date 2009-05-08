package net.javahispano.jsignalwb.plugins.basicstats.UI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import net.javahispano.jsignalwb.plugins.basicstats.ResultadosEstadisticos;
import net.javahispano.jsignalwb.plugins.basicstats.BasicStatisticsPlugin;

/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class NewStatisticsDialog extends JDialog {

  private static boolean quiere_ver_todos = true;
  private ResultadosEstadisticos estadistico;
  private PanelMostrarEstadisticos panelEstadicticos;
  private BasicStatisticsPlugin statisticsPlugin;//statisticsPlugin


  private JPanel panel1 = new JPanel();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel panelAñadirPanelEstadisticos = new JPanel();
  private BorderLayout borderLayout2 = new BorderLayout();
  private JPanel jPanel4 = new JPanel();
  private JLabel jLabel1 = new JLabel();
  private JPanel jPanel2 = new JPanel();
  private JButton descartar = new JButton();
  private JButton añadir = new JButton();
  private JCheckBox mostrarTodosEstadisticos = new JCheckBox();
  private Window parent;

  public NewStatisticsDialog(BasicStatisticsPlugin statisticsPlugin,ResultadosEstadisticos estadistico, Window parent, boolean modal) {
    super(parent,"Nuevo estadístico", Dialog.ModalityType.APPLICATION_MODAL);
    this.parent = parent;
    this.estadistico = estadistico;
    this.statisticsPlugin = statisticsPlugin;
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    panelEstadicticos = new PanelMostrarEstadisticos(estadistico);
    panelAñadirPanelEstadisticos.add(panelEstadicticos);
    this.setSize(500,600);
    Point d = parent.getLocation();
    Dimension t = parent.getSize();
    setLocation ((int)(d.x+t.getWidth()/2-350),(int)((d.y)+ 5));
  }


  private void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    panelAñadirPanelEstadisticos.setLayout(borderLayout2);
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 18));
    jLabel1.setForeground(Color.blue);
    jLabel1.setText("Estadístico nuevo:");
    descartar.setText("Descartar");

    descartar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        descartar_actionPerformed(e);
      }
    });
    añadir.setText("Añadir");

    añadir.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        añadir_actionPerformed(e);
      }
    });
    mostrarTodosEstadisticos.setSelected(NewStatisticsDialog.quiere_ver_todos);
    mostrarTodosEstadisticos.setText("Mostrar todos los estadísticos");
    jPanel2.setPreferredSize(new Dimension(422, 37));
    getContentPane().add(panel1);
    panel1.add(panelAñadirPanelEstadisticos, BorderLayout.CENTER);
    panel1.add(jPanel4, BorderLayout.NORTH);
    jPanel4.add(jLabel1, null);
    panel1.add(jPanel2,  BorderLayout.SOUTH);
    jPanel2.add(mostrarTodosEstadisticos, null);
    jPanel2.add(añadir, null);
    jPanel2.add(descartar, null);
  }

  void descartar_actionPerformed(ActionEvent e) {
    dispose();
  }

  void añadir_actionPerformed(ActionEvent e) {
    dispose();
    estadistico.setNombreSeñal(panelEstadicticos.getNombreSeñal());
    estadistico.setComentario(panelEstadicticos.getComentario());
    //Si el usuario quiere pasar de los percentiles => lo eliminamos.
    if (panelEstadicticos.invalidarPercentiles()) {
      estadistico.invalidaPercentiles();
    }
   this.statisticsPlugin.addStatistics(estadistico);
    quiere_ver_todos = mostrarTodosEstadisticos.isSelected();
    if (NewStatisticsDialog.quiere_ver_todos) {
      AllStatisticsDialog verTodos = new AllStatisticsDialog(statisticsPlugin, parent, false);
      verTodos.setVisible(true);
    }
  }

}
