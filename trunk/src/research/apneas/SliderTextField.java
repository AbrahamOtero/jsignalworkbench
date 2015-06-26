package research.apneas;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderTextField extends JPanel {

    public SliderTextField(int valor, String texto) {
        this.valor = valor;
        setTexto(texto);

    }

    public SliderTextField() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jLabel1.setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));
        jLabel1.setForeground(Color.blue);
        jLabel1.setText("jLabel1");
        jTextField.setText("jTextField1");
        jTextField.setColumns(6);
        jTextField.addFocusListener(new SliderTextField_jTextField_focusAdapter(this));
        jTextField.addActionListener(new
                                     SliderTextField_jTextField1_actionAdapter(this));
        jSlider.setMajorTickSpacing(25);
        jSlider.setMaximum(maximum);
        jSlider.setMinorTickSpacing(25);
        jSlider.setPaintLabels(true);
        jSlider.setPaintTicks(true);
        jSlider.setPreferredSize(new Dimension(sliderPreferredWidth, 46));
        jSlider.addChangeListener(new SliderTextField_jSlider1_changeAdapter(this));
        this.add(jPanel1, java.awt.BorderLayout.NORTH);
        jPanel1.add(jLabel1);
        this.add(jPanel2, java.awt.BorderLayout.CENTER);
        jPanel2.add(jSlider);
        jPanel2.add(jTextField);
    }

    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JPanel jPanel2 = new JPanel();
    JSlider jSlider = new JSlider();
    JTextField jTextField = new JTextField();
    private int maximum = 100;
    private int valor = 20;
    private int majorTickSpacing = 10;
    private int sliderPreferredWidth = 300;
    private JTextField JTextField;
    private String texto = "texto por defecto";
    public void jSlider1_stateChanged(ChangeEvent e) {
        jTextField.setText("" + jSlider.getValue());

    }

    public void jTextField1_actionPerformed(ActionEvent e) {
        int valor;
        try {
            valor = Integer.parseInt(jTextField.getText());
            jSlider.setValue(valor);
        } catch (NumberFormatException ex) {
            jTextField.requestFocus();
            jTextField.selectAll();
        }
    }

    public JTextField getJTextField() {
        return JTextField;
    }

    public JSlider getJSlider() {
        return jSlider;
    }

    public int getMajorTickSpacing() {
        return majorTickSpacing;
    }

    public int getMaximum() {
        return maximum;
    }

    public int getValor() {
        return jSlider.getValue();
    }

    public String getTexto() {
        return texto;
    }

    public int getSliderPreferredSize() {
        return sliderPreferredWidth;
    }

    public void setSliderPreferredSize(int sliderPreferredWidth) {
        this.sliderPreferredWidth = sliderPreferredWidth;
        jSlider.setPreferredSize(new Dimension(sliderPreferredWidth,
                                               jSlider.getHeight()));
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
        jSlider.setMaximum(maximum);
    }

    public void setMajorTickSpacing(int majorTickSpacing) {
        this.majorTickSpacing = majorTickSpacing;
        jSlider.setMajorTickSpacing(majorTickSpacing);
    }

    public void setValor(int valor) {
        this.valor = valor;
        jSlider.setValue(valor);
    }

    public void setTexto(String texto) {
        this.texto = texto;
        this.jLabel1.setText(texto);
    }

    public void jTextField_focusLost(FocusEvent e) {
        this.jTextField1_actionPerformed(null);
    }
}


class SliderTextField_jTextField1_actionAdapter implements ActionListener {
    private SliderTextField adaptee;
    SliderTextField_jTextField1_actionAdapter(SliderTextField adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jTextField1_actionPerformed(e);
    }
}


class SliderTextField_jTextField_focusAdapter extends FocusAdapter {
    private SliderTextField adaptee;
    SliderTextField_jTextField_focusAdapter(SliderTextField adaptee) {
        this.adaptee = adaptee;
    }

    public void focusLost(FocusEvent e) {
        adaptee.jTextField_focusLost(e);
    }
}


class SliderTextField_jSlider1_changeAdapter implements ChangeListener {
    private SliderTextField adaptee;
    SliderTextField_jSlider1_changeAdapter(SliderTextField adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jSlider1_stateChanged(e);
    }
}
