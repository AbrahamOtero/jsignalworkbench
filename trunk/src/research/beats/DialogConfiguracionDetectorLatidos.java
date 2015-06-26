package research.beats;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.Rectangle;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class DialogConfiguracionDetectorLatidos extends JDialog {
    private JPanel panel1 = new JPanel();
    private JRadioButton jRadioButton1 = new JRadioButton();
    private JRadioButton jRadioRatas = new JRadioButton();
    private ButtonGroup buttonGroup1 = new ButtonGroup();
    private JButton jButtonHumanos = new JButton();
    private JButton jButtonRatas = new JButton();
    public DialogConfiguracionDetectorLatidos(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        this.setSize(330,220);
        this.setLocationRelativeTo(owner);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        jRadioRatas.setSelected(TestDeteccion.esRata);
    }

    public DialogConfiguracionDetectorLatidos() {
        this(new Frame(), "DialogConfiguracion", false);
    }

    private void jbInit() throws Exception {
        panel1.setLayout(null);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Humanos");
        jRadioButton1.setBounds(new Rectangle(87, 47, 93, 23));
        jRadioRatas.setText("Ratas");
        jRadioRatas.setBounds(new Rectangle(87, 92, 86, 23));
        jButtonHumanos.setBounds(new Rectangle(45, 143, 81, 23));
        jButtonHumanos.setText("Aceptar");
        jButtonHumanos.addActionListener(new DialogConfiguracion_jButton1_actionAdapter(this));
        jButtonRatas.setBounds(new Rectangle(176, 143, 95, 23));
        jButtonRatas.setText("Cancelar");
        jButtonRatas.addActionListener(new DialogConfiguracion_jButton2_actionAdapter(this));
        getContentPane().add(panel1);
        panel1.add(jRadioButton1);
        panel1.add(jButtonHumanos);
        panel1.add(jButtonRatas);
        panel1.add(jRadioRatas);
        buttonGroup1.add(jRadioRatas);
        buttonGroup1.add(jRadioButton1);

    }

    public void jButton2_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        TestDeteccion.esRata = jRadioRatas.isSelected();
        this.dispose();
    }
}


class DialogConfiguracion_jButton1_actionAdapter implements ActionListener {
    private DialogConfiguracionDetectorLatidos adaptee;
    DialogConfiguracion_jButton1_actionAdapter(DialogConfiguracionDetectorLatidos adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


class DialogConfiguracion_jButton2_actionAdapter implements ActionListener {
    private DialogConfiguracionDetectorLatidos adaptee;
    DialogConfiguracion_jButton2_actionAdapter(DialogConfiguracionDetectorLatidos adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}
