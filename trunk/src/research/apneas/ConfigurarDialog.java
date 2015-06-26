package research.apneas;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.javahispano.fuzzyutilities.adquisition.FuzzyAcquisitionPanel;
import net.javahispano.fuzzyutilities.representation.TrapezoidalDistribution;

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
public class ConfigurarDialog extends JDialog {

    public ConfigurarDialog() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private JPanel panelPendiente = new JPanel();
    private BorderLayout borderLayout2 = new BorderLayout();
    private FuzzyAcquisitionPanel panelAdquisicionBorrososPendiente;
    private JPanel jPanel3 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JSlider jSliderVentanaEstadoNormal = new JSlider();
    private JTextField textVentanaEstadoNormal = new JTextField();
    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();
    JTabbedPane tabbedPaneSatO2 = new JTabbedPane();
    GridLayout gridLayout1 = new GridLayout();
    JPanel PanelPersistencia = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    FuzzyAcquisitionPanel panelAdquisicionBorrososPersistencia;
    JPanel panelPrincipio = new JPanel();
    JPanel panelFinal = new JPanel();
    BorderLayout borderLayout4 = new BorderLayout();
    FuzzyAcquisitionPanel panelAdquisicionBorrososPrincipio;
    JPanel jPanel5 = new JPanel();
    BorderLayout borderLayout5 = new BorderLayout();
    BorderLayout borderLayout6 = new BorderLayout();
    FuzzyAcquisitionPanel panelAdquisicionBorrososFinal;
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    FuzzyAcquisitionPanel panelDescensoAdmisibleRespectoBasal;
    FuzzyAcquisitionPanel panelValorAdmisibleDesaturacionRespectoBasal;
    JPanel jPanel2 = new JPanel();
    JLabel jLabel3 = new JLabel();
    JTextField jTextDuracionVentanaAscenso = new JTextField();
    JSlider jSliderVentanaAscenso = new JSlider();
    JPanel jPanel4 = new JPanel();
    JLabel jLabel4 = new JLabel();
    JTextField jTextDuracionVentanaDescenso = new JTextField();
    JSlider jSliderVentanaDescenso = new JSlider();
    JPanel jPanel6 = new JPanel();
    JLabel jLabel5 = new JLabel();
    JTextField jTextFieldprincipioVentanaBasalSatO2 = new JTextField();
    JSlider jSliderprincipioVentanaBasalSatO2 = new JSlider();
    JSlider jSliderfinVentanaBasalSatO2 = new JSlider();
    JTextField jTextFieldfinVentanaBasalSatO2 = new JTextField();
    JLabel jLabel6 = new JLabel();
    JPanel panelMagnitudApnea = new JPanel();
    BorderLayout borderLayout7 = new BorderLayout();
    FuzzyAcquisitionPanel panelAdquisicionBorrososApnea;
    JPanel panelMagnitudHipoapnea = new JPanel();
    JPanel jPanel9 = new JPanel();
    JLabel jLabel7 = new JLabel();
    JSlider jSliderprincipioVentanaBasalFlujoApnea = new JSlider();
    JTextField jTextFieldprincipioVentanaBasalFlujoApnea = new JTextField();
    JLabel jLabel8 = new JLabel();
    JSlider jSliderfinVentanaBasalFlujoApnea = new JSlider();
    JTextField jTextFieldfinVentanaBasalFlujoApnea = new JTextField();
    JLabel jLabel9 = new JLabel();
    JSlider jSlideranchoVentanaValorMedioApnea = new JSlider();
    JTextField jTextFieldanchoVentanaValorMedioApnea = new JTextField();
    BorderLayout borderLayout8 = new BorderLayout();
    FuzzyAcquisitionPanel panelAdquisicionBorrososHipoApnea;
    JPanel jPanel10 = new JPanel();
    JLabel jLabel12 = new JLabel();
    JSlider jSlideranchoVentanaValorMedioHipoapnea = new JSlider();
    JTextField jTextFieldanchoVentanaValorMedioHipoapnea = new JTextField();
    private int duracion;
    private TrapezoidalDistribution restriccionFin;
    private TrapezoidalDistribution restriccionPendiente;
    private TrapezoidalDistribution restriccionPrincipio;
    private TrapezoidalDistribution restriccionPersistencia;
    FlowLayout flowLayout1 = new FlowLayout();
    FlowLayout flowLayout2 = new FlowLayout();
    private boolean aceptar;
    JPanel panelOtrosSatO2 = new JPanel();
    SliderTextField filtroArrayPosibilidades = new SliderTextField();
    SliderTextField distanciaRellenoArrayPosibilidades = new SliderTextField();
    SliderTextField duracionMaximaDesaturacion = new SliderTextField();
    JTabbedPane tabbedPaneApnea = new JTabbedPane();
    JPanel panelTemporalApnea = new JPanel();
    JPanel panelTemporalHipoapnea = new JPanel();
    JPanel panelValorBasalApnea = new JPanel();
    JPanel panelDerivadas = new JPanel();
    JPanel panelDeltas = new JPanel();
    FuzzyAcquisitionPanel restriccionTemporalApnea;
    BorderLayout borderLayout9 = new BorderLayout();
    BorderLayout borderLayout10 = new BorderLayout();
    FuzzyAcquisitionPanel restriccionTemporalHipoapnea;
    SliderTextField sliderprincipioIntervaloFiltroEnergia = new
            SliderTextField();

    SliderTextField sliderrelacionPrimerFiltroDerivada = new SliderTextField();
    SliderTextField sliderprincipioIntervaloSegundoFiltroDerivada = new
            SliderTextField();
    SliderTextField sliderlimiteEnergia = new SliderTextField();
    SliderTextField slideFinIntervaloFiltroEnergia = new SliderTextField();
    JCheckBox checkBoxConsiderarSoloOndasNegativas = new JCheckBox();
    JLabel jLabel13 = new JLabel();
    JTextField textventanaCalculoDeltas = new JTextField();
    JPanel panelTiempo = new JPanel();
    BorderLayout borderLayout11 = new BorderLayout();
    FuzzyAcquisitionPanel panelRelacionTemporalFlujoDesaturacion = new
            FuzzyAcquisitionPanel();
    SliderTextField sliderPersistenciaFlujo = new SliderTextField();
    SliderTextField sliderTextField2 = new SliderTextField();
    public ConfigurarDialog(Window padre, String texto,
                            TrapezoidalDistribution pendiente,
                            int ventanaNormal,
                            TrapezoidalDistribution persistencia,
                            TrapezoidalDistribution principio,
                            TrapezoidalDistribution fin
            ) {
        super(padre, texto);

        try {
            jbInit();
        } catch (Exception ex) {
        }
        jSliderVentanaEstadoNormal.setValue(ventanaNormal);
        this.setSize(800, 550);
        this.setLocationRelativeTo(padre);

        this.validate();
    }


    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        jLabel2.setForeground(Color.blue);
        jLabel2.setRequestFocusEnabled(false);
        jLabel2.setText(
                "Ventana temporal para los valores normales (segundos):");
        jSliderVentanaEstadoNormal.setMajorTickSpacing(10);
        jSliderVentanaEstadoNormal.setMaximum(30);
        jSliderVentanaEstadoNormal.setPaintLabels(true);
        jSliderVentanaEstadoNormal.setPaintTicks(true);
        jSliderVentanaEstadoNormal.setPreferredSize(new Dimension(300, 46));
        jSliderVentanaEstadoNormal.addChangeListener(new
                Configura_jSlider1_changeAdapter(this));
        jButton1.setText("Aceptar");
        jButton1.addActionListener(new ConfigurarDialog_jButton1_actionAdapter(this));
        jButton1.addHierarchyBoundsListener(new
                                            ConfigurarDialog_jButton1_hierarchyBoundsAdapter(this));
        jButton2.setText("Cancelar");
        jButton2.addActionListener(new Configura_jButton2_actionAdapter(this));
        jPanel1.setPreferredSize(new Dimension(40, 40));
        textVentanaEstadoNormal.setColumns(6);
        textVentanaEstadoNormal.addActionListener(new
                                                  Configura_tv_actionAdapter(this));
        textVentanaEstadoNormal.addFocusListener(new Configura_tv_focusAdapter(this));
        jPanel3.setPreferredSize(new Dimension(395, 90));
        PanelPersistencia.setLayout(borderLayout3);
        panelPrincipio.setLayout(borderLayout4);
        jPanel5.setLayout(borderLayout5);
        panelFinal.setLayout(borderLayout6);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.
                                      DISPOSE_ON_CLOSE);
        tabbedPaneSatO2.setTabPlacement(JTabbedPane.LEFT);
        jLabel3.setForeground(Color.blue);
        jLabel3.setRequestFocusEnabled(false);
        jLabel3.setText("Ventana temporal para el ascenso (segundos):");
        jTextDuracionVentanaAscenso.setText("jTextField1");
        jTextDuracionVentanaAscenso.setColumns(6);
        jTextDuracionVentanaAscenso.addFocusListener(new
                ConfigurarDialog_jTextDuracionVentanaAscenso_focusAdapter(this));
        jTextDuracionVentanaAscenso.addActionListener(new
                ConfigurarDialog_jTextDuracionVentanaAscenso_actionAdapter(this));
        jSliderVentanaAscenso.setMajorTickSpacing(10);
        jSliderVentanaAscenso.setMaximum(30);
        jSliderVentanaAscenso.setPaintLabels(true);
        jSliderVentanaAscenso.setPaintTicks(true);
        jSliderVentanaAscenso.setPreferredSize(new Dimension(300, 46));
        jSliderVentanaAscenso.addChangeListener(new
                                                ConfigurarDialog_jSliderVentanaAscenso_changeAdapter(this));
        jLabel4.setForeground(Color.blue);
        jLabel4.setRequestFocusEnabled(false);
        jLabel4.setText("Ventana temporal para el descenso (segundos):");
        jTextDuracionVentanaDescenso.setText("jTextField1");
        jTextDuracionVentanaDescenso.setColumns(6);
        jTextDuracionVentanaDescenso.addFocusListener(new
                ConfigurarDialog_jTextDuracionVentanaDescenso_focusAdapter(this));
        jTextDuracionVentanaDescenso.addActionListener(new
                ConfigurarDialog_jTextDuracionVentanaDescenso_actionAdapter(this));
        jSliderVentanaDescenso.setMajorTickSpacing(10);
        jSliderVentanaDescenso.setMaximum(30);
        jSliderVentanaDescenso.setPaintLabels(true);
        jSliderVentanaDescenso.setPaintTicks(true);
        jSliderVentanaDescenso.setPreferredSize(new Dimension(300, 46));
        jSliderVentanaDescenso.addChangeListener(new
                                                 ConfigurarDialog_jSliderVentanaDescenso_changeAdapter(this));
        jLabel5.setForeground(Color.blue);
        jLabel5.setRequestFocusEnabled(false);
        jLabel5.setText(
                "Valores antes del actual a considerar en el calculo del basal (segundos):");
        jTextFieldprincipioVentanaBasalSatO2.setText("jTextField1");
        jTextFieldprincipioVentanaBasalSatO2.setColumns(6);
        jTextFieldprincipioVentanaBasalSatO2.addFocusListener(new
                ConfigurarDialog_jTextFieldprincipioVentanaBasalSatO2_focusAdapter(this));
        jTextFieldprincipioVentanaBasalSatO2.addActionListener(new
                ConfigurarDialog_jTextFieldprincipioVentanaBasalSatO2_actionAdapter(this));
        jSliderprincipioVentanaBasalSatO2.setMajorTickSpacing(500);
        jSliderprincipioVentanaBasalSatO2.setMaximum(3000);
        jSliderprincipioVentanaBasalSatO2.setPaintLabels(true);
        jSliderprincipioVentanaBasalSatO2.setPaintTicks(true);
        jSliderprincipioVentanaBasalSatO2.setPreferredSize(new Dimension(350,
                46));
        jSliderprincipioVentanaBasalSatO2.addChangeListener(new
                ConfigurarDialog_jSliderprincipioVentanaBasalSatO2_changeAdapter(this));
        jSliderfinVentanaBasalSatO2.setMajorTickSpacing(500);
        jSliderfinVentanaBasalSatO2.setMaximum(3000);
        jSliderfinVentanaBasalSatO2.setPaintLabels(true);
        jSliderfinVentanaBasalSatO2.setPaintTicks(true);
        jSliderfinVentanaBasalSatO2.setPreferredSize(new Dimension(350, 46));
        jSliderfinVentanaBasalSatO2.addChangeListener(new
                ConfigurarDialog_jSliderfinVentanaBasalSatO2_changeAdapter(this));
        jTextFieldfinVentanaBasalSatO2.setText("jTextField1");
        jTextFieldfinVentanaBasalSatO2.setColumns(6);
        jTextFieldfinVentanaBasalSatO2.addFocusListener(new
                ConfigurarDialog_jTextFieldfinVentanaBasalSatO2_focusAdapter(this));
        jTextFieldfinVentanaBasalSatO2.addActionListener(new
                ConfigurarDialog_jTextFieldfinVentanaBasalSatO2_actionAdapter(this));
        jLabel6.setForeground(Color.blue);
        jLabel6.setRequestFocusEnabled(false);
        jLabel6.setText(
                "Valores despues del actual a considerar en el calculo del basal (segundos):");
        jPanel6.setPreferredSize(new Dimension(899, 200));
        jPanel2.setPreferredSize(new Dimension(452, 90));
        jPanel4.setPreferredSize(new Dimension(452, 90));
        panelMagnitudApnea.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
        panelMagnitudApnea.setLayout(borderLayout7);
        jPanel9.setPreferredSize(new Dimension(899, 150));
        jPanel9.setLayout(flowLayout1);
        jLabel7.setForeground(Color.blue);
        jLabel7.setRequestFocusEnabled(false);
        jLabel7.setText(
                "Valores antes del actual a considerar en el calculo del basal (segundos):");
        jSliderprincipioVentanaBasalFlujoApnea.setMajorTickSpacing(100);
        jSliderprincipioVentanaBasalFlujoApnea.setMaximum(600);
        jSliderprincipioVentanaBasalFlujoApnea.setPaintLabels(true);
        jSliderprincipioVentanaBasalFlujoApnea.setPaintTicks(true);
        jSliderprincipioVentanaBasalFlujoApnea.setPreferredSize(new Dimension(
                350, 46));
        jSliderprincipioVentanaBasalFlujoApnea.addChangeListener(new
                ConfigurarDialog_jSliderprincipioVentanaBasalFlujoApnea_changeAdapter(this));
        jTextFieldprincipioVentanaBasalFlujoApnea.setText("jTextField1");
        jTextFieldprincipioVentanaBasalFlujoApnea.setColumns(6);
        jTextFieldprincipioVentanaBasalFlujoApnea.addFocusListener(new
                ConfigurarDialog_jTextFieldprincipioVentanaBasalFlujoApnea_focusAdapter(this));
        jTextFieldprincipioVentanaBasalFlujoApnea.addActionListener(new
                ConfigurarDialog_jTextFieldprincipioVentanaBasalFlujoApnea_actionAdapter(this));
        jLabel8.setForeground(Color.blue);
        jLabel8.setRequestFocusEnabled(false);
        jLabel8.setText(
                "Valores despues del actual a considerar en el calculo del basal (segundos):");
        jSliderfinVentanaBasalFlujoApnea.setMajorTickSpacing(50);
        jSliderfinVentanaBasalFlujoApnea.setMaximum(200);
        jSliderfinVentanaBasalFlujoApnea.setPaintLabels(true);
        jSliderfinVentanaBasalFlujoApnea.setPaintTicks(true);
        jSliderfinVentanaBasalFlujoApnea.setPreferredSize(new Dimension(350, 46));
        jSliderfinVentanaBasalFlujoApnea.addChangeListener(new
                ConfigurarDialog_jSliderfinVentanaBasalFlujoApnea_changeAdapter(this));
        jTextFieldfinVentanaBasalFlujoApnea.setText("jTextField1");
        jTextFieldfinVentanaBasalFlujoApnea.setColumns(6);
        jTextFieldfinVentanaBasalFlujoApnea.addFocusListener(new
                ConfigurarDialog_jTextFieldfinVentanaBasalFlujoApnea_focusAdapter(this));
        jTextFieldfinVentanaBasalFlujoApnea.addActionListener(new
                ConfigurarDialog_jTextFieldfinVentanaBasalFlujoApnea_actionAdapter(this));
        jLabel9.setForeground(Color.blue);
        jLabel9.setRequestFocusEnabled(false);
        jLabel9.setText(
                "Ancho de la ventana para el calculo de apneas (segundos): ");
        jSlideranchoVentanaValorMedioApnea.setMajorTickSpacing(10);
        jSlideranchoVentanaValorMedioApnea.setMaximum(30);
        jSlideranchoVentanaValorMedioApnea.setPaintLabels(true);
        jSlideranchoVentanaValorMedioApnea.setPaintTicks(true);
        jSlideranchoVentanaValorMedioApnea.setPreferredSize(new Dimension(350,
                46));
        jSlideranchoVentanaValorMedioApnea.addChangeListener(new
                ConfigurarDialog_jSlideranchoVentanaValorMedioApnea_changeAdapter(this));
        jTextFieldanchoVentanaValorMedioApnea.setText("jTextField1");
        jTextFieldanchoVentanaValorMedioApnea.setColumns(6);
        jTextFieldanchoVentanaValorMedioApnea.addFocusListener(new
                ConfigurarDialog_jTextFieldanchoVentanaValorMedioApnea_focusAdapter(this));
        jTextFieldanchoVentanaValorMedioApnea.addActionListener(new
                ConfigurarDialog_jTextFieldanchoVentanaValorMedioApnea_actionAdapter(this));
        panelMagnitudHipoapnea.setLayout(borderLayout8);
        jPanel10.setPreferredSize(new Dimension(899, 150));
        jPanel10.setLayout(flowLayout2);
        jLabel12.setForeground(Color.blue);
        jLabel12.setRequestFocusEnabled(false);
        jLabel12.setText(
                "Ancho de la ventana para el calculo de apneas (segundos): ");
        jSlideranchoVentanaValorMedioHipoapnea.setMajorTickSpacing(10);
        jSlideranchoVentanaValorMedioHipoapnea.setMaximum(60);
        jSlideranchoVentanaValorMedioHipoapnea.setPaintLabels(true);
        jSlideranchoVentanaValorMedioHipoapnea.setPaintTicks(true);
        jSlideranchoVentanaValorMedioHipoapnea.setPreferredSize(new Dimension(
                350, 46));
        jSlideranchoVentanaValorMedioHipoapnea.addChangeListener(new
                ConfigurarDialog_jSlideranchoVentanaValorMedioHipoapnea_changeAdapter(this));
        jTextFieldanchoVentanaValorMedioHipoapnea.setText("jTextField1");
        jTextFieldanchoVentanaValorMedioHipoapnea.setColumns(6);
        jTextFieldanchoVentanaValorMedioHipoapnea.addFocusListener(new
                ConfigurarDialog_jTextFieldanchoVentanaValorMedioHipoapnea_focusAdapter(this));
        jTextFieldanchoVentanaValorMedioHipoapnea.addActionListener(new
                ConfigurarDialog_jTextFieldanchoVentanaValorMedioHipoapnea_actionAdapter(this));
        flowLayout1.setAlignment(FlowLayout.LEFT);
        flowLayout1.setHgap(10);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        flowLayout2.setHgap(10);
        filtroArrayPosibilidades.setMaximum(50);
        filtroArrayPosibilidades.setValor(5);
        filtroArrayPosibilidades.setTexto(
                "Posibilidad por debajo de la cual desprecian ascensos o descensos " +
                "en el array de posibilidades");
        distanciaRellenoArrayPosibilidades.setMaximum(50);
        distanciaRellenoArrayPosibilidades.setValor(10);
        distanciaRellenoArrayPosibilidades.setTexto(
                "Los ascensos y descensos se funden si estan a menos de (segundos):");
        duracionMaximaDesaturacion.setMajorTickSpacing(25);
        duracionMaximaDesaturacion.setMaximum(200);
        duracionMaximaDesaturacion.setValor(100);
        duracionMaximaDesaturacion.setTexto(
                "Maxima duracion de un episodio de desaturacian (segundos):");
        tabbedPaneApnea.setTabPlacement(JTabbedPane.LEFT);
        panelTemporalApnea.setLayout(borderLayout9);
        panelTemporalHipoapnea.setLayout(borderLayout10);
        sliderprincipioIntervaloFiltroEnergia.setTexto(
                "Principio del intervalo para el primer filtro");
        sliderrelacionPrimerFiltroDerivada.setTexto(
                "Relacion (porcentaje) de corte para el primer filtro de la derivada");
        sliderprincipioIntervaloSegundoFiltroDerivada.setTexto(
                "Fin del intervalo para el segundo filtro");
        sliderlimiteEnergia.setValor(0);
        sliderlimiteEnergia.setTexto(
                "Relacion (porcentaje) de corte para el segundo filtro de la derivada");
        slideFinIntervaloFiltroEnergia.setTexto(
                "Fin del intervalo para el primer filtro");
        checkBoxConsiderarSoloOndasNegativas.setFont(new java.awt.Font("Tahoma",
                Font.BOLD, 12));
        checkBoxConsiderarSoloOndasNegativas.setForeground(Color.blue);
        checkBoxConsiderarSoloOndasNegativas.setSelected(true);
        checkBoxConsiderarSoloOndasNegativas.setText(
                "Considerar solo las ondas negativas ");
        jLabel13.setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));
        jLabel13.setForeground(Color.blue);
        jLabel13.setText(
                "Ventana temporal a emplear en el calculo de la delta (flujo respiratorio, " +
                "se da en segundos):");
        textventanaCalculoDeltas.setColumns(8);
        textventanaCalculoDeltas.addFocusListener(new
                                                  ConfigurarDialog_textventanaCalculoDeltas_focusAdapter(this));
        panelTiempo.setLayout(borderLayout11);
        panelRelacionTemporalFlujoDesaturacion.setPreferredSize(new Dimension(310, 300));
        sliderPersistenciaFlujo.setMajorTickSpacing(1);
        sliderPersistenciaFlujo.setMaximum(10);
        sliderPersistenciaFlujo.setTexto(
                "Persistencia, las limitaciones de flujo se unen si estan a menos " +
                "de (segundos):");
        this.getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);
        jPanel1.add(jButton1);
        jPanel1.add(jButton2);
        panelPendiente.setLayout(borderLayout2);
        this.getContentPane().add(this.jTabbedPane1,
                                  java.awt.BorderLayout.CENTER);
        this.jTabbedPane1.addTab("Desaturacion", tabbedPaneSatO2);
        this.jTabbedPane1.addTab("Apnea e hipoapnea", tabbedPaneApnea);
        this.jTabbedPane1.addTab("Relacion temporal", panelTiempo);
        jPanel4.add(jLabel4);
        jPanel4.add(jSliderVentanaDescenso);
        jPanel4.add(jTextDuracionVentanaDescenso);

        jLabel1.setText("jLabel1");
        panelPendiente.add(jPanel3, java.awt.BorderLayout.SOUTH);
        jPanel3.add(jLabel2);
        jPanel3.add(jSliderVentanaEstadoNormal);
        jPanel3.add(textVentanaEstadoNormal);
        panelFinal.add(jPanel5, java.awt.BorderLayout.CENTER);
        jPanel6.add(jLabel5);
        jPanel6.add(jSliderprincipioVentanaBasalSatO2);
        jPanel6.add(jTextFieldprincipioVentanaBasalSatO2);
        jPanel6.add(jLabel6);
        jPanel6.add(jSliderfinVentanaBasalSatO2);
        jPanel6.add(jTextFieldfinVentanaBasalSatO2);
        tabbedPaneSatO2.add(panelPendiente, "Pendiente para el estado normal");
        tabbedPaneSatO2.add(PanelPersistencia, "Pendiente de ascenso");
        tabbedPaneSatO2.add(panelPrincipio, "Pendiente del descenso");
        tabbedPaneSatO2.add(panelFinal, "Valores admisibles como normales");
        PanelPersistencia.add(jPanel2, java.awt.BorderLayout.SOUTH);
        jPanel2.add(jLabel3);
        jPanel2.add(jSliderVentanaAscenso);
        jPanel2.add(jTextDuracionVentanaAscenso);
        panelPrincipio.add(jPanel4, java.awt.BorderLayout.SOUTH);
        panelFinal.add(jPanel6, java.awt.BorderLayout.SOUTH);
        jPanel9.add(jLabel9);
        jPanel9.add(jSlideranchoVentanaValorMedioApnea);
        jPanel9.add(jTextFieldanchoVentanaValorMedioApnea);
        jPanel10.add(jLabel12);
        jPanel10.add(jSlideranchoVentanaValorMedioHipoapnea);
        jPanel10.add(jTextFieldanchoVentanaValorMedioHipoapnea);
        panelMagnitudHipoapnea.add(jPanel10, java.awt.BorderLayout.SOUTH);
        panelOtrosSatO2.add(filtroArrayPosibilidades);
        panelOtrosSatO2.add(distanciaRellenoArrayPosibilidades);
        panelOtrosSatO2.add(duracionMaximaDesaturacion);
        panelMagnitudApnea.add(jPanel9, java.awt.BorderLayout.SOUTH);
        tabbedPaneApnea.add(panelMagnitudApnea, "Restriccion magnitud apnea");
        tabbedPaneApnea.add(panelMagnitudHipoapnea,
                            "Restriccion magnitud hipoapnea");
        tabbedPaneApnea.add(panelTemporalApnea, "Restriccion temporal apnea");
        tabbedPaneApnea.add(panelTemporalHipoapnea,
                            "Restriccion temporal hipoapnea");
        tabbedPaneApnea.add(panelValorBasalApnea, "Valor basal");
        panelValorBasalApnea.add(jLabel7);
        panelValorBasalApnea.add(jSliderprincipioVentanaBasalFlujoApnea);
        panelValorBasalApnea.add(jTextFieldprincipioVentanaBasalFlujoApnea);
        panelValorBasalApnea.add(jLabel8);
        panelValorBasalApnea.add(jSliderfinVentanaBasalFlujoApnea);
        panelValorBasalApnea.add(jTextFieldfinVentanaBasalFlujoApnea);
        tabbedPaneApnea.add(panelDerivadas, "Parametros de las derivadas");
        panelDerivadas.add(sliderprincipioIntervaloFiltroEnergia);
        panelDerivadas.add(slideFinIntervaloFiltroEnergia);
        panelDerivadas.add(sliderrelacionPrimerFiltroDerivada);
        panelDerivadas.add(sliderprincipioIntervaloSegundoFiltroDerivada);
        panelDerivadas.add(sliderlimiteEnergia);
        panelDeltas.add(checkBoxConsiderarSoloOndasNegativas);
        panelDeltas.add(jLabel13);
        panelDeltas.add(textventanaCalculoDeltas);
        panelDeltas.add(sliderPersistenciaFlujo);
        panelTiempo.add(panelRelacionTemporalFlujoDesaturacion, java.awt.BorderLayout.NORTH);
        tabbedPaneApnea.add(panelDeltas, "Otros");
        tabbedPaneSatO2.setSelectedIndex(0);
        jTabbedPane1.setSelectedIndex(1);
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        aceptar = false;
        this.dispose();
    }

    public void jSlider1_stateChanged(ChangeEvent changeEvent) {
        int valor = jSliderVentanaEstadoNormal.getValue();
        textVentanaEstadoNormal.setText(Integer.toString(valor));

    }

    public void tv_focusLost(FocusEvent focusEvent) {
        tv_actionPerformed(null);
    }

    public void tv_actionPerformed(ActionEvent e) {
        try {
            int valor = Integer.parseInt(textVentanaEstadoNormal.getText());
            jSliderVentanaEstadoNormal.setValue(valor);
        } catch (NumberFormatException ex) {
            textVentanaEstadoNormal.requestFocus();
            textVentanaEstadoNormal.selectAll();
        }

    }

    public int getDuracion() {
        return jSliderVentanaEstadoNormal.getValue();
    }

    public TrapezoidalDistribution getRestriccionPendiente() {
        return panelAdquisicionBorrososPendiente.getTrapezoidalDistribution();
    }

    public TrapezoidalDistribution getRestriccionPersistencia() {

        return panelAdquisicionBorrososPersistencia.
                getTrapezoidalDistribution();

    }

    public TrapezoidalDistribution getRestriccionPrincipio() {
        return this.panelAdquisicionBorrososPrincipio.
                getTrapezoidalDistribution();

    }

    public TrapezoidalDistribution getRestriccionFin() {
        return this.panelAdquisicionBorrososFinal.
                getTrapezoidalDistribution();
    }


    public void jButton1_ancestorMoved(HierarchyEvent hierarchyEvent) {

    }

    public void jButton1_actionPerformed(ActionEvent e) {
        if (this.panelAdquisicionBorrososPendiente.validaActualizar() &&
            panelAdquisicionBorrososPersistencia.validaActualizar() &&
            this.panelAdquisicionBorrososPrincipio.validaActualizar() &&
            this.panelAdquisicionBorrososFinal.validaActualizar() &&
            this.panelAdquisicionBorrososApnea.validaActualizar() &&
            this.panelAdquisicionBorrososHipoApnea.validaActualizar() &&
            this.panelDescensoAdmisibleRespectoBasal.validaActualizar() &&
            this.panelValorAdmisibleDesaturacionRespectoBasal.validaActualizar()) {
            this.dispose();
        }
        aceptar = true;
    }

    public static void main(String[] args) {
        ConfigurarDialog configura = new ConfigurarDialog(null, "sola",
                new TrapezoidalDistribution(0.1F, .4F, 2F, 3F), 10,
                new TrapezoidalDistribution(0.1F, .4F, 2F, 3F),
                new TrapezoidalDistribution(0.1F, .4F, 2F, 3F),
                new TrapezoidalDistribution(0.1F, .4F, 2F, 3F));
        configura.show();
        configura.repaint();
    }

    public void jSliderVentanaAscenso_stateChanged(ChangeEvent e) {

        int valor = this.jSliderVentanaAscenso.getValue();
        this.jTextDuracionVentanaAscenso.setText(Integer.toString(valor));
    }

    public void jTextDuracionVentanaAscenso_actionPerformed(ActionEvent e) {
        try {
            int valor = Integer.parseInt(jTextDuracionVentanaAscenso.getText());
            jSliderVentanaAscenso.setValue(valor);
        } catch (NumberFormatException ex) {
            jTextDuracionVentanaAscenso.requestFocus();
            jTextDuracionVentanaAscenso.selectAll();
        }
    }

    public void jTextDuracionVentanaAscenso_focusLost(FocusEvent e) {
        this.jTextDuracionVentanaAscenso_actionPerformed(null);
    }

    public void jSliderVentanaDescenso_stateChanged(ChangeEvent e) {
        int valor = this.jSliderVentanaDescenso.getValue();
        this.jTextDuracionVentanaDescenso.setText(Integer.toString(valor));
    }

    public void jTextDuracionVentanaDescenso_actionPerformed(ActionEvent e) {
        try {
            int valor = Integer.parseInt(jTextDuracionVentanaDescenso.getText());
            jSliderVentanaDescenso.setValue(valor);
        } catch (NumberFormatException ex) {
            jTextDuracionVentanaDescenso.requestFocus();
            jTextDuracionVentanaDescenso.selectAll();
        }
    }

    public void jSliderprincipioVentanaBasalSatO2_stateChanged(ChangeEvent e) {

        int valor = this.jSliderprincipioVentanaBasalSatO2.getValue();
        this.jTextFieldprincipioVentanaBasalSatO2.setText(Integer.toString(
                valor));
    }

    public void jTextFieldprincipioVentanaBasalSatO2_actionPerformed(
            ActionEvent e) {
        try {
            int valor = Integer.parseInt(jTextFieldprincipioVentanaBasalSatO2.
                                         getText());
            jSliderprincipioVentanaBasalSatO2.setValue(valor);
        } catch (NumberFormatException ex) {
            jTextFieldprincipioVentanaBasalSatO2.requestFocus();
            jTextFieldprincipioVentanaBasalSatO2.selectAll();
        }
    }

    public void jTextFieldprincipioVentanaBasalSatO2_focusLost(FocusEvent e) {
        this.jTextFieldprincipioVentanaBasalSatO2_actionPerformed(null);
    }

    public void jSliderfinVentanaBasalSatO2_stateChanged(ChangeEvent e) {

        int valor = this.jSliderfinVentanaBasalSatO2.getValue();
        this.jTextFieldfinVentanaBasalSatO2.setText(Integer.toString(valor));
    }

    public void jTextFieldfinVentanaBasalSatO2_actionPerformed(ActionEvent e) {
        try {
            int valor = Integer.parseInt(jTextFieldfinVentanaBasalSatO2.getText());
            jSliderfinVentanaBasalSatO2.setValue(valor);
        } catch (NumberFormatException ex) {
            jTextFieldfinVentanaBasalSatO2.requestFocus();
            jTextFieldfinVentanaBasalSatO2.selectAll();
        }
    }

    public void jTextFieldfinVentanaBasalSatO2_focusLost(FocusEvent e) {
        this.jTextFieldfinVentanaBasalSatO2_actionPerformed(null);
    }

    public void jTextDuracionVentanaDescenso_focusLost(FocusEvent e) {
        jTextDuracionVentanaDescenso_actionPerformed(null);
    }

    public void jSliderprincipioVentanaBasalFlujoApnea_stateChanged(ChangeEvent
            e) {

        int valor = this.jSliderprincipioVentanaBasalFlujoApnea.getValue();
        this.jTextFieldprincipioVentanaBasalFlujoApnea.setText(Integer.toString(
                valor));
    }

    public void jTextFieldprincipioVentanaBasalFlujoApnea_actionPerformed(
            ActionEvent e) {
        try {
            int valor = Integer.parseInt(
                    jTextFieldprincipioVentanaBasalFlujoApnea.getText());
            jSliderprincipioVentanaBasalFlujoApnea.setValue(valor);
        } catch (NumberFormatException ex) {
            jTextFieldprincipioVentanaBasalFlujoApnea.requestFocus();
            jTextFieldprincipioVentanaBasalFlujoApnea.selectAll();
        }
    }

    public void jTextFieldprincipioVentanaBasalFlujoApnea_focusLost(FocusEvent
            e) {
        jTextFieldprincipioVentanaBasalFlujoApnea_actionPerformed(null);
    }

    public void jSliderfinVentanaBasalFlujoApnea_stateChanged(ChangeEvent e) {

        int valor = this.jSliderfinVentanaBasalFlujoApnea.getValue();
        this.jTextFieldfinVentanaBasalFlujoApnea.setText(Integer.toString(valor));
    }

    public void jTextFieldfinVentanaBasalFlujoApnea_actionPerformed(ActionEvent
            e) {
        try {
            int valor = Integer.parseInt(jTextFieldfinVentanaBasalFlujoApnea.
                                         getText());
            jSliderfinVentanaBasalFlujoApnea.setValue(valor);
        } catch (NumberFormatException ex) {
            jTextFieldfinVentanaBasalFlujoApnea.requestFocus();
            jTextFieldfinVentanaBasalFlujoApnea.selectAll();
        }
    }

    public void jTextFieldfinVentanaBasalFlujoApnea_focusLost(FocusEvent e) {
        jTextFieldfinVentanaBasalFlujoApnea_actionPerformed(null);
    }

    public void jSlideranchoVentanaValorMedioApnea_stateChanged(ChangeEvent e) {

        int valor = this.jSlideranchoVentanaValorMedioApnea.getValue();
        this.jTextFieldanchoVentanaValorMedioApnea.setText(Integer.toString(
                valor));
    }

    public void jTextFieldanchoVentanaValorMedioApnea_actionPerformed(
            ActionEvent e) {
        try {
            int valor = Integer.parseInt(jTextFieldanchoVentanaValorMedioApnea.
                                         getText());
            jSlideranchoVentanaValorMedioApnea.setValue(valor);
        } catch (NumberFormatException ex) {
            jTextFieldanchoVentanaValorMedioApnea.requestFocus();
            jTextFieldanchoVentanaValorMedioApnea.selectAll();
        }
    }

    public void jTextFieldanchoVentanaValorMedioApnea_focusLost(FocusEvent e) {
        jTextFieldanchoVentanaValorMedioApnea_actionPerformed(null);
    }


    public void jTextFieldprincipioVentanaBasalFlujoHipoapnea_actionPerformed(
            ActionEvent e) {
        try {
            int valor = Integer.parseInt(
                    jTextFieldanchoVentanaValorMedioHipoapnea.getText());
        } catch (NumberFormatException ex) {
            jTextFieldanchoVentanaValorMedioHipoapnea.requestFocus();
            jTextFieldanchoVentanaValorMedioHipoapnea.selectAll();
        }
    }

    public void jTextFieldprincipioVentanaBasalFlujoHipoapnea_focusLost(
            FocusEvent e) {
        jTextFieldprincipioVentanaBasalFlujoHipoapnea_actionPerformed(null);
    }

    public void jSlideranchoVentanaValorMedioHipoapnea_stateChanged(ChangeEvent
            e) {

        int valor = this.jSlideranchoVentanaValorMedioHipoapnea.getValue();
        this.jTextFieldanchoVentanaValorMedioHipoapnea.setText(Integer.toString(
                valor));
    }

    public void jTextFieldanchoVentanaValorMedioHipoapnea_actionPerformed(
            ActionEvent e) {
        try {
            int valor = Integer.parseInt(
                    jTextFieldanchoVentanaValorMedioHipoapnea.getText());
            jSlideranchoVentanaValorMedioHipoapnea.setValue(valor);
        } catch (NumberFormatException ex) {
            jTextFieldanchoVentanaValorMedioHipoapnea.requestFocus();
            jTextFieldanchoVentanaValorMedioHipoapnea.selectAll();
        }
    }

    public void jTextFieldanchoVentanaValorMedioHipoapnea_focusLost(FocusEvent
            e) {
        jTextFieldanchoVentanaValorMedioHipoapnea_actionPerformed(null);
    }

    public int getAnchoVentanaValorMedioApnea() {
        return jSlideranchoVentanaValorMedioApnea.getValue();
    }

    public int getAnchoVentanaValorMedioHipoApnea() {
        return jSlideranchoVentanaValorMedioHipoapnea.getValue();
    }

    public TrapezoidalDistribution getApnea() {
        return panelAdquisicionBorrososApnea.getTrapezoidalDistribution();
    }

    public TrapezoidalDistribution getDescensoAdmisibleRespectoBasal() {
        return panelDescensoAdmisibleRespectoBasal.getTrapezoidalDistribution();
    }

    public int getFinVentanaBasalSatO2() {
        return jSliderfinVentanaBasalSatO2.getValue();
    }

    public TrapezoidalDistribution getHipoapnea() {
        return panelAdquisicionBorrososHipoApnea.getTrapezoidalDistribution();
    }

    public FuzzyAcquisitionPanel getPanelAdquisicionBorrosos1() {
        return panelAdquisicionBorrososHipoApnea;
    }

    public FuzzyAcquisitionPanel getPanelAdquisicionBorrososApnea() {
        return panelAdquisicionBorrososApnea;
    }

    public FuzzyAcquisitionPanel getPanelAdquisicionBorrososFinal() {
        return panelAdquisicionBorrososFinal;
    }

    public FuzzyAcquisitionPanel getPanelAdquisicionBorrososPendiente() {
        return panelAdquisicionBorrososPendiente;
    }

    public FuzzyAcquisitionPanel getPanelAdquisicionBorrososPersistencia() {
        return panelAdquisicionBorrososPersistencia;
    }

    public FuzzyAcquisitionPanel getPanelAdquisicionBorrososPrincipio() {
        return panelAdquisicionBorrososPrincipio;
    }

    public FuzzyAcquisitionPanel getPanelDescensoAdmisibleRespectoBasal() {
        return panelDescensoAdmisibleRespectoBasal;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public int getPersistenciaFlujo() {
        return sliderPersistenciaFlujo.getValor();
    }

    public TrapezoidalDistribution getRelacionTemporal() {
        return this.panelRelacionTemporalFlujoDesaturacion.getTrapezoidalDistribution();
    }

    public float getVentanaCalculoDeltas() {
        float v = 1.5F;
        try {
            v = Float.parseFloat(this.textventanaCalculoDeltas.getText());
        } catch (NumberFormatException ex) {
        }
        return v;
    }

    public boolean isConsiderarSoloOndasNegativas() {
        return this.checkBoxConsiderarSoloOndasNegativas.isSelected();
    }

    public int getFinIntervaloFiltroEnergia() {
        return this.slideFinIntervaloFiltroEnergia.getValor();
    }

    public int getFinIntervaloSegundoFiltroEnergia() {
        return this.sliderprincipioIntervaloSegundoFiltroDerivada.getValor();
    }

    public int getPrincipioIntervaloFiltroEnergia() {
        return this.sliderprincipioIntervaloFiltroEnergia.getValor();
    }

    public int getRelacionPrimerFiltroDerivada() {
        return this.sliderrelacionPrimerFiltroDerivada.getValor();
    }

    public int getLimiteEnergia() {
        return this.sliderlimiteEnergia.getValor();
    }

    public TrapezoidalDistribution getDuracionApnea() {
        return this.restriccionTemporalApnea.getTrapezoidalDistribution();
    }

    public TrapezoidalDistribution getDuracionHipoapnea() {
        return this.restriccionTemporalHipoapnea.getTrapezoidalDistribution();
    }

    public int getPersistencia() {
        return this.distanciaRellenoArrayPosibilidades.getValor();
    }

    public int getLimiteArrayPosiblidades() {
        return this.filtroArrayPosibilidades.getValor();
    }

    public int getDuracionMaximaEpisodiosDesaturacion() {
        return this.duracionMaximaDesaturacion.getValor();
    }


    public int getFinVentanaBasalFlujoApnea() {
        return jSliderfinVentanaBasalFlujoApnea.getValue();
    }

    public int getVentanaPendientesSaO2() {
        return jSliderVentanaEstadoNormal.getValue();
    }

    public int getVentanaDescenso() {
        return jSliderVentanaDescenso.getValue();
    }

    public int getVentanaAscenso() {
        return jSliderVentanaAscenso.getValue();
    }

    public int getPrincipioVentanaBasalFlujoApnea() {
        return jSliderprincipioVentanaBasalFlujoApnea.getValue();
    }

    public int getPrincipioVentanaBasalSatO2() {
        return jSliderprincipioVentanaBasalSatO2.getValue();
    }

    public TrapezoidalDistribution getPendienteNormal() {
        return panelAdquisicionBorrososPendiente.getTrapezoidalDistribution();
    }

    public TrapezoidalDistribution getPendienteDescenso() {
        return panelAdquisicionBorrososPrincipio.getTrapezoidalDistribution();
    }

    public TrapezoidalDistribution getPendienteAscenso() {
        return this.panelAdquisicionBorrososPersistencia.
                getTrapezoidalDistribution();
    }


    public TrapezoidalDistribution getValorNormal() {
        return panelAdquisicionBorrososFinal.getTrapezoidalDistribution();
    }

    public TrapezoidalDistribution getValorAdmisibleDesaturacionRespectoBasal() {
        return panelValorAdmisibleDesaturacionRespectoBasal.
                getTrapezoidalDistribution();
    }

    public void setApnea(TrapezoidalDistribution apnea) {
        panelAdquisicionBorrososApnea = new FuzzyAcquisitionPanel(apnea);
        panelAdquisicionBorrososApnea.setNumeroDecimales(3);
        panelMagnitudApnea.add(panelAdquisicionBorrososApnea,
                               java.awt.BorderLayout.CENTER);

    }

    public void setAnchoVentanaValorMedioHipoApnea(int
            anchoVentanaValorMedioHipoApnea) {
        jSlideranchoVentanaValorMedioHipoapnea.setValue(
                anchoVentanaValorMedioHipoApnea);
    }

    public void setAnchoVentanaValorMedioApnea(int
                                               anchoVentanaValorMedioApnea) {
        jSlideranchoVentanaValorMedioApnea.setValue(anchoVentanaValorMedioApnea);
    }

    public void setDescensoAdmisibleRespectoBasal(TrapezoidalDistribution
                                                  descensoAdmisibleRespectoBasal) {
        panelDescensoAdmisibleRespectoBasal = new FuzzyAcquisitionPanel(
                descensoAdmisibleRespectoBasal);
        panelDescensoAdmisibleRespectoBasal.setNumeroDecimales(3);
        tabbedPaneSatO2.add(panelDescensoAdmisibleRespectoBasal,
                            "Desviacion admisible respecto al basal");

    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void setFinVentanaBasalFlujoApnea(int finVentanaBasalFlujo) {
        jSliderfinVentanaBasalFlujoApnea.setValue(finVentanaBasalFlujo);
    }

    public void setFinVentanaBasalSatO2(int finVentanaBasalSatO2) {
        jSliderfinVentanaBasalSatO2.setValue(finVentanaBasalSatO2);
    }

    public void setHipoapnea(TrapezoidalDistribution hipoapnea) {

        panelAdquisicionBorrososHipoApnea = new FuzzyAcquisitionPanel(
                hipoapnea);
        panelAdquisicionBorrososHipoApnea.setNumeroDecimales(3);
        panelMagnitudHipoapnea.add(panelAdquisicionBorrososHipoApnea,
                                   java.awt.BorderLayout.CENTER);
    }

    public void setPanelAdquisicionBorrososPrincipio(FuzzyAcquisitionPanel
            panelAdquisicionBorrososPrincipio) {
        this.panelAdquisicionBorrososPrincipio =
                panelAdquisicionBorrososPrincipio;
    }

    public void setPanelAdquisicionBorrososPersistencia(
            FuzzyAcquisitionPanel panelAdquisicionBorrososPersistencia) {
        this.panelAdquisicionBorrososPersistencia =
                panelAdquisicionBorrososPersistencia;
    }

    public void setPanelAdquisicionBorrososPendiente(FuzzyAcquisitionPanel
            panelAdquisicionBorrososPendiente) {
        this.panelAdquisicionBorrososPendiente =
                panelAdquisicionBorrososPendiente;
    }


    public void setRestriccionFin(TrapezoidalDistribution restriccionFin) {
        this.restriccionFin = restriccionFin;
    }

    public void setRestriccionPendiente(TrapezoidalDistribution
                                        restriccionPendiente) {
        this.restriccionPendiente = restriccionPendiente;
    }

    public void setRestriccionPrincipio(TrapezoidalDistribution
                                        restriccionPrincipio) {
        this.restriccionPrincipio = restriccionPrincipio;
    }

    public void setRestriccionPersistencia(TrapezoidalDistribution
                                           restriccionPersistencia) {
        this.restriccionPersistencia = restriccionPersistencia;
    }

    public void setPersistenciaFlujo(int persistenciaFlujo) {
        sliderPersistenciaFlujo.setValor(persistenciaFlujo);
    }

    public void setRelacionTemporal(TrapezoidalDistribution relacionTemporal) {
        panelRelacionTemporalFlujoDesaturacion.setTrapezoidalDistribution(relacionTemporal);
        panelRelacionTemporalFlujoDesaturacion.validaActualizar();
    }

    public void setVentanaCalculoDeltas(float ventanaCalculoDeltas) {
        this.textventanaCalculoDeltas.setText("" + ventanaCalculoDeltas);
    }

    public void setConsiderarSoloOndasNegativas(boolean
                                                considerarSoloOndasNegativas) {
        checkBoxConsiderarSoloOndasNegativas.setSelected(
                considerarSoloOndasNegativas);
    }

    public void setFinIntervaloFiltroEnergia(int
                                             finIntervaloFiltroEnergia) {
        this.slideFinIntervaloFiltroEnergia.setValor(
                finIntervaloFiltroEnergia);
    }

    public void setFinIntervaloSegundoFiltroEnergia(int
            finIntervaloSegundoFiltroEnergia) {
        this.sliderprincipioIntervaloSegundoFiltroDerivada.setValor(
                finIntervaloSegundoFiltroEnergia);
    }

    public void setPrincipioIntervaloFiltroEnergia(int
            principioIntervaloFiltroEnergia) {
        this.sliderprincipioIntervaloFiltroEnergia.setValor(
                principioIntervaloFiltroEnergia);
    }

    public void setRelacionPrimerFiltroDerivada(int
                                                relacionPrimerFiltroDerivada) {
        this.sliderrelacionPrimerFiltroDerivada.setValor(
                relacionPrimerFiltroDerivada);
    }

    public void setLimiteEnergia(int limiteEnergia) {
        this.sliderlimiteEnergia.setValor(limiteEnergia);
        System.out.println("mierda por decir algo");

    }

    public void setDuracionApnea(TrapezoidalDistribution duracionApnea) {
        this.restriccionTemporalApnea = new FuzzyAcquisitionPanel(
                duracionApnea);
        restriccionTemporalApnea.setNumeroDecimales(3);

        panelTemporalApnea.add(restriccionTemporalApnea,
                               java.awt.BorderLayout.CENTER);
    }

    public void setDuracionHipoapnea(TrapezoidalDistribution duracionHipoapnea) {
        this.restriccionTemporalHipoapnea = new FuzzyAcquisitionPanel(
                duracionHipoapnea);
        restriccionTemporalHipoapnea.setNumeroDecimales(3);

        panelTemporalHipoapnea.add(restriccionTemporalHipoapnea,
                                   java.awt.BorderLayout.CENTER);

    }

    public void setPersistencia(int persistencia) {
        this.distanciaRellenoArrayPosibilidades.setValor(persistencia);
    }

    public void setLimiteArrayPosiblidades(int limiteArrayPosiblidades) {
        this.filtroArrayPosibilidades.setValor(limiteArrayPosiblidades);
    }

    public void setDuracionMaximaEpisodiosDesaturacion(int
            duracionMaximaEpisodiosDesaturacion) {
        this.duracionMaximaDesaturacion.setValor(
                duracionMaximaEpisodiosDesaturacion);
        tabbedPaneSatO2.add(this.panelOtrosSatO2,
                            "Otros parametros");
    }

    public void setFinVentanaBasalFlujoHipoApnea(int
                                                 finVentanaBasalFlujoHipoApnea) {
    }

    public void setPrincipioVentanaBasalFlujoHipoApnea(int
            principioVentanaBasalFlujoHipoApnea) {
    }

    public void setVentanaAscenso(int ventanaAscenso) {
        jSliderVentanaAscenso.setValue(ventanaAscenso);
    }

    public void setVentanaDescenso(int ventanaDescenso) {
        jSliderVentanaDescenso.setValue(ventanaDescenso);
    }

    public void setVentanaPendientesSaO2(int ventanaNormal) {
        jSliderVentanaEstadoNormal.setValue(ventanaNormal);
    }

    public void setPrincipioVentanaBasalSatO2(int principioVentanaBasalSatO2) {
        jSliderprincipioVentanaBasalSatO2.setValue(principioVentanaBasalSatO2);
    }

    public void setPrincipioVentanaBasalFlujoApnea(int
            principioVentanaBasalFlujo) {
        jSliderprincipioVentanaBasalFlujoApnea.setValue(
                principioVentanaBasalFlujo);
    }

    public void setValorNormal(TrapezoidalDistribution valorNormal) {
        this.panelAdquisicionBorrososFinal = new FuzzyAcquisitionPanel(
                valorNormal);
        this.panelAdquisicionBorrososFinal.setNumeroDecimales(3);
        jPanel5.add(panelAdquisicionBorrososFinal, java.awt.BorderLayout.CENTER);
    }

    public void setValorAdmisibleDesaturacionRespectoBasal(
            TrapezoidalDistribution valorAdmisibleDesaturacionRespectoBasal) {
        panelValorAdmisibleDesaturacionRespectoBasal = new
                FuzzyAcquisitionPanel(
                        valorAdmisibleDesaturacionRespectoBasal);
        panelValorAdmisibleDesaturacionRespectoBasal.setNumeroDecimales(3);
        tabbedPaneSatO2.add(
                panelValorAdmisibleDesaturacionRespectoBasal,
                "Valor admisible de desaturacion rrespecto al basal");

    }

    public void setPendienteAscenso(TrapezoidalDistribution pendienteAscenso) {
        this.panelAdquisicionBorrososPersistencia = new
                FuzzyAcquisitionPanel(pendienteAscenso);
        this.panelAdquisicionBorrososPersistencia.setNumeroDecimales(3);
        PanelPersistencia.add(panelAdquisicionBorrososPersistencia,
                              java.awt.BorderLayout.CENTER);
    }

    public void setPendienteDescenso(TrapezoidalDistribution pendienteDescenso) {
        this.panelAdquisicionBorrososPrincipio = new FuzzyAcquisitionPanel(
                pendienteDescenso);
        this.panelAdquisicionBorrososPrincipio.setNumeroDecimales(3);
        panelPrincipio.add(panelAdquisicionBorrososPrincipio,
                           java.awt.BorderLayout.CENTER);

    }

    public void setPendienteNormal(TrapezoidalDistribution pendienteNormal) {
        panelAdquisicionBorrososPendiente = new FuzzyAcquisitionPanel(
                pendienteNormal);
        this.panelAdquisicionBorrososPendiente.setNumeroDecimales(3);

        panelPendiente.add(panelAdquisicionBorrososPendiente,
                           java.awt.BorderLayout.CENTER);
    }

    public void textventanaCalculoDeltas_focusLost(FocusEvent e) {
        try {
            Float.parseFloat(textventanaCalculoDeltas.getText());
        } catch (NumberFormatException ex) {
            textventanaCalculoDeltas.requestFocus();
            textventanaCalculoDeltas.selectAll();
        }
    }


    class ConfigurarDialog_textventanaCalculoDeltas_focusAdapter extends
            FocusAdapter {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_textventanaCalculoDeltas_focusAdapter(ConfigurarDialog
                adaptee) {
            this.adaptee = adaptee;
        }

        public void focusLost(FocusEvent e) {
            adaptee.textventanaCalculoDeltas_focusLost(e);
        }
    }


    class ConfigurarDialog_jTextFieldanchoVentanaValorMedioHipoapnea_actionAdapter implements
            ActionListener {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jTextFieldanchoVentanaValorMedioHipoapnea_actionAdapter(
                ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.jTextFieldanchoVentanaValorMedioHipoapnea_actionPerformed(e);
        }
    }


    class ConfigurarDialog_jSlideranchoVentanaValorMedioHipoapnea_changeAdapter implements
            ChangeListener {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jSlideranchoVentanaValorMedioHipoapnea_changeAdapter(
                ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void stateChanged(ChangeEvent e) {
            adaptee.jSlideranchoVentanaValorMedioHipoapnea_stateChanged(e);
        }
    }


    class ConfigurarDialog_jTextFieldanchoVentanaValorMedioHipoapnea_focusAdapter extends
            FocusAdapter {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jTextFieldanchoVentanaValorMedioHipoapnea_focusAdapter(
                ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void focusLost(FocusEvent e) {
            adaptee.jTextFieldanchoVentanaValorMedioHipoapnea_focusLost(e);
        }
    }


    class ConfigurarDialog_jTextFieldanchoVentanaValorMedioApnea_focusAdapter extends
            FocusAdapter {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jTextFieldanchoVentanaValorMedioApnea_focusAdapter(
                ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void focusLost(FocusEvent e) {
            adaptee.jTextFieldanchoVentanaValorMedioApnea_focusLost(e);
        }
    }


    class ConfigurarDialog_jTextFieldfinVentanaBasalFlujoApnea_focusAdapter extends
            FocusAdapter {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jTextFieldfinVentanaBasalFlujoApnea_focusAdapter(
                ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void focusLost(FocusEvent e) {
            adaptee.jTextFieldfinVentanaBasalFlujoApnea_focusLost(e);
        }
    }


    class ConfigurarDialog_jTextFieldfinVentanaBasalFlujoApnea_actionAdapter implements
            ActionListener {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jTextFieldfinVentanaBasalFlujoApnea_actionAdapter(
                ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.jTextFieldfinVentanaBasalFlujoApnea_actionPerformed(e);
        }
    }


    class ConfigurarDialog_jSlideranchoVentanaValorMedioApnea_changeAdapter implements
            ChangeListener {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jSlideranchoVentanaValorMedioApnea_changeAdapter(
                ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void stateChanged(ChangeEvent e) {
            adaptee.jSlideranchoVentanaValorMedioApnea_stateChanged(e);
        }
    }


    class ConfigurarDialog_jTextFieldanchoVentanaValorMedioApnea_actionAdapter implements
            ActionListener {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jTextFieldanchoVentanaValorMedioApnea_actionAdapter(
                ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.jTextFieldanchoVentanaValorMedioApnea_actionPerformed(e);
        }
    }


    class ConfigurarDialog_jTextFieldprincipioVentanaBasalFlujoApnea_focusAdapter extends
            FocusAdapter {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jTextFieldprincipioVentanaBasalFlujoApnea_focusAdapter(
                ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void focusLost(FocusEvent e) {
            adaptee.jTextFieldprincipioVentanaBasalFlujoApnea_focusLost(e);
        }
    }


    class ConfigurarDialog_jTextFieldprincipioVentanaBasalFlujoApnea_actionAdapter implements
            ActionListener {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jTextFieldprincipioVentanaBasalFlujoApnea_actionAdapter(
                ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.jTextFieldprincipioVentanaBasalFlujoApnea_actionPerformed(e);
        }
    }


    class ConfigurarDialog_jSliderfinVentanaBasalFlujoApnea_changeAdapter implements
            ChangeListener {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jSliderfinVentanaBasalFlujoApnea_changeAdapter(
                ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void stateChanged(ChangeEvent e) {
            adaptee.jSliderfinVentanaBasalFlujoApnea_stateChanged(e);
        }
    }


    class ConfigurarDialog_jSliderprincipioVentanaBasalFlujoApnea_changeAdapter implements
            ChangeListener {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jSliderprincipioVentanaBasalFlujoApnea_changeAdapter(
                ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void stateChanged(ChangeEvent e) {
            adaptee.jSliderprincipioVentanaBasalFlujoApnea_stateChanged(e);
        }
    }


    class ConfigurarDialog_jTextFieldfinVentanaBasalSatO2_focusAdapter extends
            FocusAdapter {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jTextFieldfinVentanaBasalSatO2_focusAdapter(
                ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void focusLost(FocusEvent e) {
            adaptee.jTextFieldfinVentanaBasalSatO2_focusLost(e);
        }
    }


    class ConfigurarDialog_jSliderfinVentanaBasalSatO2_changeAdapter implements
            ChangeListener {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jSliderfinVentanaBasalSatO2_changeAdapter(ConfigurarDialog
                adaptee) {
            this.adaptee = adaptee;
        }

        public void stateChanged(ChangeEvent e) {
            adaptee.jSliderfinVentanaBasalSatO2_stateChanged(e);
        }
    }


    class ConfigurarDialog_jTextFieldfinVentanaBasalSatO2_actionAdapter implements
            ActionListener {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jTextFieldfinVentanaBasalSatO2_actionAdapter(
                ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.jTextFieldfinVentanaBasalSatO2_actionPerformed(e);
        }
    }


    class ConfigurarDialog_jTextDuracionVentanaDescenso_focusAdapter extends
            FocusAdapter {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jTextDuracionVentanaDescenso_focusAdapter(ConfigurarDialog
                adaptee) {
            this.adaptee = adaptee;
        }

        public void focusLost(FocusEvent e) {
            adaptee.jTextDuracionVentanaDescenso_focusLost(e);
        }
    }


    class ConfigurarDialog_jTextFieldprincipioVentanaBasalSatO2_focusAdapter extends
            FocusAdapter {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jTextFieldprincipioVentanaBasalSatO2_focusAdapter(
                ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void focusLost(FocusEvent e) {
            adaptee.jTextFieldprincipioVentanaBasalSatO2_focusLost(e);
        }
    }


    class ConfigurarDialog_jTextFieldprincipioVentanaBasalSatO2_actionAdapter implements
            ActionListener {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jTextFieldprincipioVentanaBasalSatO2_actionAdapter(
                ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.jTextFieldprincipioVentanaBasalSatO2_actionPerformed(e);
        }
    }


    class ConfigurarDialog_jSliderprincipioVentanaBasalSatO2_changeAdapter implements
            ChangeListener {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jSliderprincipioVentanaBasalSatO2_changeAdapter(
                ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void stateChanged(ChangeEvent e) {
            adaptee.jSliderprincipioVentanaBasalSatO2_stateChanged(e);
        }
    }


    class ConfigurarDialog_jTextDuracionVentanaDescenso_actionAdapter implements
            ActionListener {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jTextDuracionVentanaDescenso_actionAdapter(
                ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.jTextDuracionVentanaDescenso_actionPerformed(e);
        }
    }


    class ConfigurarDialog_jSliderVentanaDescenso_changeAdapter implements
            ChangeListener {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jSliderVentanaDescenso_changeAdapter(ConfigurarDialog
                adaptee) {
            this.adaptee = adaptee;
        }

        public void stateChanged(ChangeEvent e) {
            adaptee.jSliderVentanaDescenso_stateChanged(e);
        }
    }


    class ConfigurarDialog_jTextDuracionVentanaAscenso_focusAdapter extends
            FocusAdapter {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jTextDuracionVentanaAscenso_focusAdapter(ConfigurarDialog
                adaptee) {
            this.adaptee = adaptee;
        }

        public void focusLost(FocusEvent e) {
            adaptee.jTextDuracionVentanaAscenso_focusLost(e);
        }
    }


    class ConfigurarDialog_jButton1_actionAdapter implements ActionListener {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jButton1_actionAdapter(ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent actionEvent) {
            adaptee.jButton1_actionPerformed(actionEvent);
        }
    }


    class ConfigurarDialog_jButton1_hierarchyBoundsAdapter extends
            HierarchyBoundsAdapter {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jButton1_hierarchyBoundsAdapter(ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void ancestorMoved(HierarchyEvent hierarchyEvent) {
            adaptee.jButton1_ancestorMoved(hierarchyEvent);
        }
    }


    class Configura_tv_actionAdapter implements ActionListener {
        private ConfigurarDialog adaptee;
        Configura_tv_actionAdapter(ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.tv_actionPerformed(e);
        }
    }


    class Configura_jSlider1_changeAdapter implements ChangeListener {
        private ConfigurarDialog adaptee;
        Configura_jSlider1_changeAdapter(ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void stateChanged(ChangeEvent changeEvent) {
            adaptee.jSlider1_stateChanged(changeEvent);
        }
    }


    class ConfigurarDialog_jSliderVentanaAscenso_changeAdapter implements
            ChangeListener {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jSliderVentanaAscenso_changeAdapter(ConfigurarDialog
                adaptee) {
            this.adaptee = adaptee;
        }

        public void stateChanged(ChangeEvent e) {
            adaptee.jSliderVentanaAscenso_stateChanged(e);
        }
    }


    class Configura_tv_focusAdapter extends FocusAdapter {
        private ConfigurarDialog adaptee;
        Configura_tv_focusAdapter(ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void focusLost(FocusEvent focusEvent) {
            adaptee.tv_focusLost(focusEvent);
        }
    }


    class ConfigurarDialog_jTextDuracionVentanaAscenso_actionAdapter implements
            ActionListener {
        private ConfigurarDialog adaptee;
        ConfigurarDialog_jTextDuracionVentanaAscenso_actionAdapter(ConfigurarDialog
                adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.jTextDuracionVentanaAscenso_actionPerformed(e);
        }
    }


    class Configura_jButton2_actionAdapter implements ActionListener {
        private ConfigurarDialog adaptee;
        Configura_jButton2_actionAdapter(ConfigurarDialog adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.jButton2_actionPerformed(e);
        }


    }
}
