package tmp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import java.awt.Dimension;

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
public class PanelVisualizadorCerdo extends JPanel {
    private JLabel jLabel1 = new JLabel();
    private JCheckBox jCheckBoxDiuresis = new JCheckBox();
    private JCheckBox jCheckBoxPresiones = new JCheckBox();
    private JCheckBox jCheckBoxPresionesSuavizadas = new JCheckBox();
    private JCheckBox jCheckBoxFlujos = new JCheckBox();
    private JCheckBox jCheckBoxFlujosPromedio = new JCheckBox();
    private JButton jButton1 = new JButton();
    private JCheckBox jCheckBoxFlujosRinhon = new JCheckBox();
    private JCheckBox jCheckBoxFlujosRinhons = new JCheckBox();
    private JCheckBox jCheckBox7 = new JCheckBox();
    private JCheckBox jCheckBox8 = new JCheckBox();
    private JCheckBox jCheckBox9 = new JCheckBox();
    private JCheckBox jCheckBox10 = new JCheckBox();
    private JCheckBox jCheckBox11 = new JCheckBox();
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private JDialog  frame;
    public PanelVisualizadorCerdo() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jLabel1.setText("¿Qué parámetros desea visualizar?");
        jLabel1.setBounds(new Rectangle(135, 33, 370, 30));
        jCheckBoxDiuresis.setForeground(Color.blue);
        jCheckBoxDiuresis.setText("Diuresis");
        jCheckBoxDiuresis.setBounds(new Rectangle(93, 100, 91, 25));
        jCheckBoxPresiones.setForeground(Color.blue);
        jCheckBoxPresiones.setToolTipText("");
        jCheckBoxPresiones.setText("Presión arterial");
        jCheckBoxPresiones.setBounds(new Rectangle(93, 143, 120, 25));
        jCheckBoxPresionesSuavizadas.setForeground(Color.blue);
        jCheckBoxPresionesSuavizadas.setText("Presiones Sist. y Diast.");
        jCheckBoxPresionesSuavizadas.setBounds(new Rectangle(94, 195, 162, 25));
        jCheckBoxFlujos.setForeground(Color.blue);
        jCheckBoxFlujos.setText("Flujos carot. y riñon");
        jCheckBoxFlujos.setBounds(new Rectangle(94, 288, 148, 25));
        jCheckBoxFlujosPromedio.setForeground(Color.blue);
        jCheckBoxFlujosPromedio.setText("Promedio de los flujos");
        jCheckBoxFlujosPromedio.setBounds(new Rectangle(94, 343, 178, 25));
        jButton1.setText("Aceptar");
        jButton1.addActionListener(new PanelVisualizadorCerdo_jButton1_actionAdapter(this));
        jCheckBoxFlujosRinhon.setForeground(Color.blue);
        jCheckBoxFlujosRinhon.setText("Flujos cortical y medular");
        jCheckBoxFlujosRinhon.setBounds(new Rectangle(313, 288, 179, 25));
        jCheckBoxFlujosRinhons.setForeground(Color.blue);
        jCheckBoxFlujosRinhons.setToolTipText("");
        jCheckBoxFlujosRinhons.setText("Promedio de los flujos");
        jCheckBoxFlujosRinhons.setBounds(new Rectangle(313, 343, 162, 25));
        jCheckBox7.setText("jCheckBox7");
        jCheckBox7.setBounds(new Rectangle(1038, 288, 178, 25));
        jCheckBox8.setText("jCheckBox8");
        jCheckBox8.setBounds(new Rectangle(834, 193, 91, 25));
        jCheckBox9.setText("jCheckBox9");
        jCheckBox9.setBounds(new Rectangle(834, 240, 91, 25));
        jCheckBox10.setText("jCheckBox10");
        jCheckBox10.setBounds(new Rectangle(834, 288, 179, 25));
        jCheckBox11.setText("jCheckBox11");
        jCheckBox11.setBounds(new Rectangle(1038, 240, 148, 25));
        jPanel1.setBorder(BorderFactory.createEtchedBorder());
        jPanel1.setBounds(new Rectangle(65, 137, 208, 92));
        jPanel2.setBorder(BorderFactory.createEtchedBorder());
        jPanel2.setBounds(new Rectangle(66, 282, 207, 92));
        jPanel3.setBorder(BorderFactory.createEtchedBorder());
        jPanel3.setBounds(new Rectangle(301, 282, 195, 90));
        this.setMinimumSize(new Dimension(640, 320));
        this.setPreferredSize(new Dimension(560, 460));
        jButton1.setBounds(new Rectangle(253, 405, 81, 25));
        jCheckBoxPresionPulmonar.setForeground(Color.blue);
        jCheckBoxPresionPulmonar.setText("Presión pulmonar");
        jCheckBoxPresionPulmonar.setBounds(new Rectangle(321, 143, 145, 25));
        jCheckBoxPresionPulmonarDiasTySist.setForeground(Color.blue);
        jCheckBoxPresionPulmonarDiasTySist.setText("Presiones Sist. y Diast.");
        jCheckBoxPresionPulmonarDiasTySist.setBounds(new Rectangle(321, 195, 162, 25));
        jPanel4.setBorder(BorderFactory.createEtchedBorder());
        jPanel4.setBounds(new Rectangle(292, 138, 203, 90));
        this.add(jCheckBox7);
        this.add(jCheckBox8);
        this.add(jCheckBox9);
        this.add(jCheckBox10);
        this.add(jCheckBox11);
        this.add(jLabel1);
        this.add(jCheckBoxFlujos);
        this.add(jCheckBoxFlujosPromedio);
        this.add(jPanel2);
        this.add(jCheckBoxFlujosRinhon);
        this.add(jCheckBoxFlujosRinhons);
        this.add(jPanel3);
        this.add(jCheckBoxPresionesSuavizadas);
        this.add(jCheckBoxPresionPulmonar);
        this.add(jCheckBoxPresiones);
        this.add(jPanel1);
        this.add(jCheckBoxPresionPulmonarDiasTySist);
        this.add(jPanel4);
        this.add(jCheckBoxDiuresis);
        this.add(jButton1);
        jLabel1.setForeground(Color.blue);
        jLabel1.setFont(new java.awt.Font("Tahoma", Font.BOLD, 16));
    }

    public boolean isDiuresis() {
        return this.jCheckBoxDiuresis.isSelected();
    }

    public boolean isFlujos() {
         return this.jCheckBoxFlujos.isSelected();
    }

    public boolean isFlujosDelRinhon() {
         return this.jCheckBoxFlujosRinhon.isSelected();
    }

    public boolean isFlujosDelRinhonSuavizados() {
        return this.jCheckBoxFlujosRinhons.isSelected();
    }

    public boolean isFlujosSuavizados() {
         return this.jCheckBoxFlujosPromedio.isSelected();
    }

    public boolean isPresionP() {
        return jCheckBoxPresionPulmonar.isSelected();
    }

    public boolean isPresionesPSuavizadas() {
        return jCheckBoxPresionPulmonarDiasTySist.isSelected();
    }

    public boolean isPresionesSuavizadas() {
         return this.jCheckBoxPresionesSuavizadas.isSelected();
    }

    public boolean isPresiones() {
       return this.jCheckBoxPresiones.isSelected();
    }

    public void setFlujosSuavizados(boolean flujosSuavizados) {
        this.jCheckBoxFlujosPromedio.setSelected(flujosSuavizados);
    }

    public void setFlujosDelRinhon(boolean flujosDelRinhon) {
        this.jCheckBoxFlujosRinhon.setSelected(flujosDelRinhon);
    }

    public void setFlujosDelRinhonSuavizados(boolean flujosDelRinhonSuavizados) {
        this.jCheckBoxFlujosRinhons.setSelected(flujosDelRinhonSuavizados);
    }

    public void setDiuresis(boolean diuresis) {
        this.jCheckBoxDiuresis.setSelected(diuresis);
    }

    public void setFlujos(boolean flujos) {
        this.jCheckBoxFlujos.setSelected(flujos);
    }

    public void setPresiones(boolean presiones) {
        this.jCheckBoxPresiones.setSelected(presiones);
    }

    public void setPresionesSuavizadas(boolean presionesSuavizadas) {
        this.jCheckBoxPresionesSuavizadas.setSelected(presionesSuavizadas);
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        frame.dispose();
    }

    public void setFrame(JDialog  frame) {
        this.frame = frame;
    }

    public void setPresionP(boolean presionP) {
        this.jCheckBoxPresionPulmonar.setSelected(presionP);
    }

    public void setPresionesPSuavizadas(boolean presionesPSuavizadas) {
        this.jCheckBoxPresionPulmonarDiasTySist.setSelected(presionesPSuavizadas);
    }


    private JCheckBox jCheckBoxPresionPulmonar = new JCheckBox();
    private JCheckBox jCheckBoxPresionPulmonarDiasTySist = new JCheckBox();
    private JPanel jPanel4 = new JPanel();

}


class PanelVisualizadorCerdo_jButton1_actionAdapter implements ActionListener {
    private PanelVisualizadorCerdo adaptee;
    PanelVisualizadorCerdo_jButton1_actionAdapter(PanelVisualizadorCerdo adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }


}
