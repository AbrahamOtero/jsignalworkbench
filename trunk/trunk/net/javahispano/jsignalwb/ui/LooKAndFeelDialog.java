package net.javahispano.jsignalwb.ui;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 *<html>
 *<p>Simple candro de di&aacute;logo con unos cuantos componentes que no hacen nada,
 * simplemente cambian su apariencia para que el usuario se haga una idea de como
 * se ver&aacute; la aplicaci&oacute;n con el nuevo Look anf Feel. En el est&aacute;
 * tambi&eacute;n el LookAndFeelCombobox, d&oacute;nde el usuario elige que Look
 * and Feel quiere.</p>
 *<p>Este componete solo es capaz de cambiar el Look and Feel de los componentes
 * que est&eacute;n a&ntilde;adidos al JFrame que se le pasa en el constructor.
 * Si, por ejemplo, hay un popup men&uacute; que ha sido creado al arrancar la
 * aplicaci&oacute;n, &eacute;ste no cambiar&aacute; su Look and Feel, ya que no
 * seremos capaces de localizarlo por no estar a&ntilde;adido sobre el JFrame.
 * Si el popup men&uacute; se crea de nuevo se crear&aacute; con el nuevo Look
 * and Feel. Es reponsabilidad del programador, y no de este componente cambiar
 * el Look and Feel de los componentes que no est&eacute;n a&ntilde;adidos en el
 * JFrame. Una forma simple es volverlos a crear de nuevo.</p>
 *<p>Aunque el programador que desee emplear este componente puede hacerlo con s&oacute;lo
 * concer la interface de esta clase es LookAndFeelCombobox la clase en la que
 * se halla el c&oacute;digo que permite el cambio de apariencia. Si a alg&uacute;n
 * programador no le gusta este cuadro de di&aacute;logo puede construir uno nuevo
 * y a&ntilde;adir en la la clase LookAndFeelCombobox. </p>
 *<p>&nbsp;</p>
 *<p>Un sencillo ejemplo de como usar esta clase:</p>
 *<p>import java.awt.*;<br>
 * import javax.swing.*;<br>
 * import java.awt.event.*;<br>
 * <b>import javahispano.util.*;</b><br>
 *</p>
 *<p>public class FrameEditor extends JFrame implments ActionListener{<br>
 * ...... </p>
 *<p>private JButton boton = new JButton(&quot;Look and Feel&quot;);</p>
 *<p> public FrameEditor() {<br>
 *Construye tu aplicaci&oacute;n</p>
 *<p>boton.addActionListener(this);<br>
 * }</p>
 *<p>......</p>
 *<p> void actionPerformed(ActionEvent e) {<br>
 * <b>LooKAndFeelDialog dialog_look_and_feel = new LooKAndFeelDialog(this);<br>
 * dialog_look_and_feel.show();</b><br>
 * }<br>
 * }</p>
 *</body>
 *</html>
 * @author Abraham Otero
 * @version 1.0
 */

public class LooKAndFeelDialog extends JDialog implements ActionListener {

    //Valores por defecto de los mensajes de error
    private String look_and_feel_no_soportado = "<html>" +
                                                "<p><font color=\"#0000FF\"><b><font size=\"4\">Ese Look and Feel no est&aacute; </font></b></font></p>" +
                                                "<p><font size=\"4\"><b><font color=\"#0000FF\">soportado en esta plataforma </font></b></font></p>" +
                                                "</body>" +
                                                "</html>"
                                                , otro_error = "<html>" +
            "<p><font color=\"#0000FF\"><b><font size=\"4\">Hubo un error al intentar cambiar</font></b></font></p>" +
            "<p><b><font size=\"4\" color=\"#0000FF\">el Look anf Feel y no se pudo </font></b></p>" +
            "<p><b><font size=\"4\" color=\"#0000FF\">terminar la operaci&oacute;n.</font></b></p>" +
            "</body>" +
            "</html>";
    ;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel panel_look_and_feel = new JPanel();
    private JSlider jSlider1 = new JSlider();
    private JScrollBar jScrollBar1 = new JScrollBar();
    private JLabel jLabel1 = new JLabel();
    private JPanel jPanel1 = new JPanel();
    private GridLayout gridLayout1 = new GridLayout();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JPanel jPanel2 = new JPanel();
    private GridLayout gridLayout2 = new GridLayout();
    private JButton jButton1 = new JButton();
    private JRadioButton jRadioButton1 = new JRadioButton();
    private JCheckBox jCheckBox1 = new JCheckBox();
    private JComboBox jComboBox1 = new JComboBox();
    private JToggleButton jToggleButton1 = new JToggleButton();
    private JTextField jTextField1 = new JTextField();
    private JLabel jLabel4 = new JLabel();
    private LookAndFeelCombobox look_and_feel_selection;

    /**
     * Este es el constructor que devemos emplear para indicar que mensajes deseamos que se
     * muestren si se lanza alguna excepcion al cambiar el Look and Feel
     * @param parent JFrame al que le quereis cambiar el Look an Feel.
     * @param look_and_feel_no_soportado Mensaje se debe mostrar cuando el usuario selecione un Look and Feel no soportado.
     * @param otro_error Mensaje a mostrar cuando se produce una excepcion cualquiera salvo aquella producida por un Look and Feel no soportado.
     */
    public LooKAndFeelDialog(JFrame parent, String look_and_feel_no_soportado, String otro_error) {
        this(parent);
        this.look_and_feel_no_soportado = look_and_feel_no_soportado;
        this.otro_error = otro_error;
        //estas lineas son necesarias para actualizar el texto de error
        look_and_feel_selection.setOtroError(otro_error);
        look_and_feel_selection.setLookAndFeelNoSoportado(look_and_feel_no_soportado);
    }

    /**
     * Este es el constructor que devemos emplear para emplear los mensajes por defecto
     * si se lanza alguna excepcion al cambiar el Look and Feel
     * @param parent JFrame al que le quereis cambiar el Look an Feel.
     */
    public LooKAndFeelDialog(JFrame parent) {
        super(parent);
        jbInit(parent);
        setModal(true);
    }

    private void jbInit(JFrame parent) {
        Point d = parent.getLocation();
        Dimension t = parent.getSize();
        setLocation((int) (d.x + t.getWidth() / 2 - 200), (int) ((d.y) + t.getHeight() / 2 - 250));
        this.setSize(500, 300);

        this.getContentPane().setLayout(borderLayout1);
        jSlider1.setOrientation(JSlider.VERTICAL);
        jLabel1.setText("jLabel1");
        jPanel1.setLayout(gridLayout1);
        gridLayout1.setRows(2);
        gridLayout1.setColumns(1);
        gridLayout1.setHgap(5);
        gridLayout1.setVgap(5);
        jLabel3.setFont(new java.awt.Font("Dialog", 0, 14));
        jLabel3.setForeground(Color.blue);
        jLabel3.setText("Aqui tiene algunas muestras de la apariencia que tendra la aplicacion:");
        jLabel2.setFont(new java.awt.Font("Dialog", 0, 15));
        jLabel2.setForeground(Color.blue);
        jLabel2.setText("Elija el Look And Feel que prefiere para la plicacion:");
        jPanel2.setLayout(gridLayout2);
        gridLayout2.setRows(3);
        gridLayout2.setColumns(2);
        gridLayout2.setHgap(5);
        gridLayout2.setVgap(5);
        jButton1.setText("Boton");
        jRadioButton1.setText("RadioButton");
        jCheckBox1.setText("CheckBox");
        jToggleButton1.setText("ToggleButton");
        jTextField1.setText("Campo de texto");
        borderLayout1.setHgap(20);
        jLabel4.setForeground(Color.red);
        jLabel4.setText("Seleccione Look And Feel:");
        this.getContentPane().add(panel_look_and_feel, BorderLayout.SOUTH);
        panel_look_and_feel.add(jLabel4, null);
        this.getContentPane().add(jSlider1, BorderLayout.EAST);
        this.getContentPane().add(jScrollBar1, BorderLayout.WEST);
        this.getContentPane().add(jPanel1, BorderLayout.NORTH);
        jPanel1.add(jLabel2, null);
        jPanel1.add(jLabel3, null);
        this.getContentPane().add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(jButton1, null);
        jPanel2.add(jTextField1, null);
        jPanel2.add(jToggleButton1, null);
        jPanel2.add(jComboBox1, null);
        jPanel2.add(jCheckBox1, null);
        jPanel2.add(jRadioButton1, null);
        look_and_feel_selection = new LookAndFeelCombobox(parent, look_and_feel_no_soportado, otro_error);
        panel_look_and_feel.add(look_and_feel_selection);

        JLabel jLabe = new JLabel();
        jLabe.setForeground(Color.red);
        jLabe.setText("Aplicar a la interfase:");
        panel_look_and_feel.add(jLabe);
        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(this);
        aceptar.setBorder(BorderFactory.createRaisedBevelBorder());
        panel_look_and_feel.add(aceptar);
    }

    public void actionPerformed(ActionEvent e) {
        this.dispose();
    }

    /**
     * Devuelve el Look and Feel que ha seleccionado el usuario.
     * @return Look and Feel selecionado.
     */
    public String getLookAnfFeel() {
        return look_and_feel_selection.getLookAnfFeel();
    }

    /**
     * Devuelve el texto a mostrar cuando el usuario elige un Look and Feel no soportado.
     * @return Texto a mostrar cuando el usuario elige un Look and Feel no soportado.
     */
    public String getLookAndFeelNoSoportado() {
        return look_and_feel_no_soportado;
    }

    /**
     * Indica que texto se debe mostrar cuando el usuario selecione un Look and Feel no soportado.
     * @param _look_and_feel_no_soportado Texto que se debe mostrar cuando el usuario elige un Look and Feel no soportado.
     */
    public void setLookAndFeelNoSoportado(String _look_and_feel_no_soportado) {
        look_and_feel_no_soportado = _look_and_feel_no_soportado;
        look_and_feel_selection.setLookAndFeelNoSoportado(_look_and_feel_no_soportado);
    }

    /**
     * Devuelve el texto a mostrar cuando se produce una excepcion cualquiera
     * salvo aquella producida por un Look and Feel no soportado.
     * @return Texto a mostrar cuando se produce una excepcion cualquiera.
     */
    public String getOtroError() {
        return otro_error;
    }

    /**
     * Indica que texto se debe mostar  mostrar cuando se produce una excepcion cualquiera
     * salvo aquella producida por un Look and Feel no soportado.
     * @param _otro_error Texto a mostrar cuando se produce una excepcion cualquiera.
     */
    public void setOtroError(String _otro_error) {
        otro_error = _otro_error;
        look_and_feel_selection.setOtroError(_otro_error);
    }
}
