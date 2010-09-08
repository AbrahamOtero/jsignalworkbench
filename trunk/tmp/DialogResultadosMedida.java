package tmp;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.datatransfer.*;
import java.awt.*;
import net.javahispano.jsignalwb.JSWBManager;

public class DialogResultadosMedida extends JDialog {
    private JPanel panel1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private BorderLayout borderLayout2 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private JLabel jLabel1 = new JLabel();
     JTextArea jTextAreaMedida = new JTextArea();
    private JPanel jPanel2 = new JPanel();
    private BorderLayout borderLayout3 = new BorderLayout();
    private JPanel jPanel3 = new JPanel();
    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();
    private JButton jButton3 = new JButton();

    public DialogResultadosMedida(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public DialogResultadosMedida() {
        this(new Frame(), "DialogResultadosMedida", false);
    }

    private void jbInit() throws Exception {
        panel1.setLayout(borderLayout1);
        this.getContentPane().setLayout(borderLayout2);
        jButton3.setText("Copiar al portapapeles");
        jButton3.addActionListener(new DialogResultadosMedida_jButton3_actionAdapter(this));
        jTextAreaMedida.setLineWrap(true);
        jTextAreaMedida.setWrapStyleWord(true);
        jButton1.addActionListener(new DialogResultadosMedida_jButton1_actionAdapter(this));
        jButton2.addActionListener(new DialogResultadosMedida_jButton2_actionAdapter(this));
        this.getContentPane().add(panel1, java.awt.BorderLayout.CENTER);
        jLabel1.setText("Resultado de la medida:");
        jPanel2.setPreferredSize(new Dimension(10, 40));
        jPanel2.setLayout(borderLayout3);
        jButton1.setText("Pasar a la siguiente");
        jButton2.setText("Repetir");
        panel1.add(jPanel1, java.awt.BorderLayout.NORTH);
        jPanel1.add(jLabel1);
        panel1.add(jTextAreaMedida, java.awt.BorderLayout.CENTER);
        this.getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);
        jPanel2.add(jPanel3, java.awt.BorderLayout.NORTH);
        jPanel3.add(jButton1);
        jPanel3.add(jButton2);
        jPanel3.add(jButton3);
    }

    public void jButton3_actionPerformed(ActionEvent e) {
        StringSelection stringSelection =  new StringSelection(this.jTextAreaMedida.getText());
               Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
               clipboard.setContents(stringSelection, new ClipboardOwner() {
                   public void lostOwnership(Clipboard aClipboard,
                                             Transferable aContents) {

                   }
        });
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        AreaCerdo.retrocedeMedida();
        this.dispose();
    }
}


class DialogResultadosMedida_jButton2_actionAdapter implements ActionListener {
    private DialogResultadosMedida adaptee;
    DialogResultadosMedida_jButton2_actionAdapter(DialogResultadosMedida adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


class DialogResultadosMedida_jButton1_actionAdapter implements ActionListener {
    private DialogResultadosMedida adaptee;
    DialogResultadosMedida_jButton1_actionAdapter(DialogResultadosMedida adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


class DialogResultadosMedida_jButton3_actionAdapter implements ActionListener {
    private DialogResultadosMedida adaptee;
    DialogResultadosMedida_jButton3_actionAdapter(DialogResultadosMedida adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton3_actionPerformed(e);
    }
}
