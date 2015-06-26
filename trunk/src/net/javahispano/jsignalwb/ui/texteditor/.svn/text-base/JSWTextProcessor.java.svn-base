package net.javahispano.jsignalwb.ui.texteditor;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;

import com.borland.dbswing.DBTextDataBinder;
import com.borland.dbswing.FontChooser;

public class JSWTextProcessor extends JFrame {

    private ByteArrayOutputStream inMemoryDucyment;
    private Border normal = BorderFactory.createEtchedBorder();
    private Border selected = BorderFactory.createRaisedBevelBorder();


    private JPanel contentPane;
    private JMenuBar menuBar1 = new JMenuBar();
    private JMenu menuFile = new JMenu();
    private JMenuItem menuFileExit = new JMenuItem();
    private JToolBar toolBar = new JToolBar();
    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();
    private ImageIcon gifAbrir;
    private ImageIcon gifGuardar;
    private ImageIcon gifNuevo;
    private JLabel statusBar = new JLabel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JEditorPane editorpane = new JEditorPane();
    private JMenuItem jMenuItem1 = new JMenuItem();
    private JMenuItem jMenuItem2 = new JMenuItem();
    private JMenuItem jMenuItem3 = new JMenuItem();
    private JMenuItem jMenuItem4 = new JMenuItem();
    private FontChooser fontChooser1 = new FontChooser();
    private JMenu jMenu1 = new JMenu();
    private JMenuItem jMenuItem7 = new JMenuItem();
    private JFileChooser jFileChooser1 = new JFileChooser();
    private String currFileName = null;
    private boolean dirty = false;
    private Document document1;
    private DBTextDataBinder dBTextDataBinder1 = new DBTextDataBinder();
    private JButton jButton3 = new JButton();
    private JMenu jMenu2 = new JMenu();
    private JMenuItem jMenuItem5 = new JMenuItem();

    public JSWTextProcessor(File file, WindowListener windowFocusListener, ActionListener actionListener) {
        this.currFileName = file.toString();
        this.addWindowListener(windowFocusListener);
        menuFileExit.addActionListener(actionListener);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
            updateCaption();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setIconImage(Toolkit.getDefaultToolkit().createImage(JSWTextProcessor.class.getResource("comentario.gif")));
        jButton1.setBorder(normal);
        jButton2.setBorder(normal);
        jButton3.setBorder(normal);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.openFile(currFileName);
    }

    //Component initialization
    private void jbInit() throws Exception {
        gifAbrir = new ImageIcon(JSWTextProcessor.class.getResource("openFile.gif"));
        gifGuardar = new ImageIcon(JSWTextProcessor.class.getResource("closeFile.gif"));
        gifNuevo = new ImageIcon(JSWTextProcessor.class.getResource("new.gif"));

        contentPane = (JPanel)this.getContentPane();
        document1 = editorpane.getDocument();
        editorpane.setContentType("text/rtf");
        contentPane.setLayout(borderLayout1);
        this.updateCaption();
        statusBar.setText(" ");
        menuFile.setText("File");
        menuFileExit.setToolTipText("Exits the text editor");
        menuFileExit.setText("Exit");
        menuFileExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(69, java.awt.event.KeyEvent.CTRL_MASK, false));
        menuFileExit.addActionListener(new TextEditFrameMenuFileExitActionAdapter(this));
        jButton1.setIcon(gifAbrir);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                jButton1MouseEntered(e);
            }

            public void mouseExited(MouseEvent e) {
                jButton1MouseExited(e);
            }
        });
        jButton1.addActionListener(new TextEditFrameJButton1ActionAdapter(this));
        jButton1.setBorder(normal);
        jButton1.setToolTipText("Opens an rtf document");
        jButton2.setIcon(gifGuardar);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton2ActionPerformed(e);
            }
        });
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                jButton2MouseEntered(e);
            }

            public void mouseExited(MouseEvent e) {
                jButton2MouseExited(e);
            }
        });
        jButton2.addActionListener(new TextEditFrameJButton2ActionAdapter(this));
        jButton2.setBorder(normal);
        jButton2.setToolTipText("Saves a document as an rtf file");
        editorpane.setBackground(Color.white);
        jMenuItem1.setToolTipText("Creates a new document");
        jMenuItem1.setIcon(gifNuevo);
        jMenuItem1.setText("New");
        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(79, java.awt.event.KeyEvent.CTRL_MASK, false));
        jMenuItem1.addActionListener(new TextEditFrameJMenuItem1ActionAdapter(this));
        jMenuItem2.setToolTipText("Opens an rtf document");
        jMenuItem2.setIcon(gifAbrir);
        jMenuItem2.setText("Open");
        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(79, java.awt.event.KeyEvent.CTRL_MASK, false));
        jMenuItem2.addActionListener(new TextEditFrameJMenuItem2ActionAdapter(this));
        jMenuItem3.setToolTipText("Saves a document as an rtf file");
        jMenuItem3.setIcon(gifGuardar);
        jMenuItem3.setText("Save");
        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(83, java.awt.event.KeyEvent.CTRL_MASK, false));
        jMenuItem3.addActionListener(new TextEditFrameJMenuItem3ActionAdapter(this));
        jMenuItem4.setToolTipText("Exports the document as an rtf independent from the working session");
        jMenuItem4.setText("Export as rtf");
        jMenuItem4.addActionListener(new TextEditFrameJMenuItem4ActionAdapter(this));
        fontChooser1.setFrame(this);
        fontChooser1.setTitle("Font");
        jMenu1.setText("Edit");
        jMenuItem7.setToolTipText("Changes document's background color.");
        jMenuItem7.setText("Background color");
        jMenuItem7.addActionListener(new TextEditFrameMenuItem7ActionAdapter(this));
        document1.addDocumentListener(new TextEditFrameDocument1DocumentAdapter(this));
        dBTextDataBinder1.setJTextComponent(editorpane);
        dBTextDataBinder1.setEnableFileLoading(false);
        dBTextDataBinder1.setEnableURLLoading(false);
        dBTextDataBinder1.setEnableFileSaving(false);
        jButton3.setBorder(normal);
        jButton3.setToolTipText("Creates a new document");
        jButton3.setIcon(gifNuevo);
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                jButton3MouseEntered(e);
            }

            public void mouseExited(MouseEvent e) {
                jButton3MouseExited(e);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton3ActionPerformed(e);
            }
        });
        jMenu2.setText("About");
        jMenuItem5.setText("About...");
        jMenuItem5.addActionListener(new JSWTextProcessor_jMenuItem5_actionAdapter(this));
        toolBar.add(jButton3, null);
        toolBar.addSeparator(new Dimension(8, toolBar.getSize().height));
        toolBar.add(jButton1);
        toolBar.addSeparator(new Dimension(3, toolBar.getSize().height));
        toolBar.add(jButton2);
        menuFile.add(jMenuItem1);
        menuFile.addSeparator();
        menuFile.add(jMenuItem2);
        menuFile.add(jMenuItem3);
        menuFile.add(jMenuItem4);
        menuFile.addSeparator();
        menuFile.add(menuFileExit);
        menuBar1.add(menuFile);
        menuBar1.add(jMenu1);
        menuBar1.add(jMenu2);
        this.setJMenuBar(menuBar1);
        contentPane.add(toolBar, BorderLayout.NORTH);
        contentPane.add(statusBar, BorderLayout.SOUTH);
        contentPane.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(editorpane, null);
        jMenu1.add(jMenuItem7);
        jMenu2.add(jMenuItem5);
        //Codigo mio
        jFileChooser1.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return (f.getName().toLowerCase().endsWith(".rtf") ||
                        f.getName().toLowerCase().endsWith(".rtf") || f.isDirectory());
            }

            public String getDescription() {
                return "rtf documents";
            }
        });

    }

    void fileOpen() {
        if (!isSaved()) {
            return;
        }
        if (JFileChooser.APPROVE_OPTION == jFileChooser1.showOpenDialog(this)) {
            openFile(jFileChooser1.getSelectedFile().getPath());
        }
        this.repaint();
    }

    boolean openFile(String fileName) {
        File file = new File(fileName);
        try {
            FileInputStream out = new FileInputStream(file);
            editorpane.getEditorKit().read(out, editorpane.getDocument(), 0);
            out.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error opening the file", "Error", JOptionPane.ERROR_MESSAGE);
            statusBar.setText("Error opening the file comments file of " + getJSWFileName());
            return false;
        }
        this.dirty = false;
        //  this.currFileName = fileName;
        //  statusBar.setText("Editing " + currFileName);
        updateCaption();
        return true;
    }

    boolean saveFile(String file) {
        try {
            FileOutputStream out = new FileOutputStream(file);
            editorpane.getEditorKit().write(out, editorpane.getDocument(), 0, editorpane.getDocument().getLength());
            out.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error scaving the file", "Error", JOptionPane.ERROR_MESSAGE);
            statusBar.setText("Error saving the file in" + getJSWFileName());
            return false;
        }
        this.dirty = false;
        statusBar.setText("Saved in" + getJSWFileName());
        updateCaption();
        return true;
    }


    /**
     * saveFile
     */
    void saveFile() {
        saveFile(this.currFileName);
    }


    private String getJSWFileName() {
        return this.currFileName.substring(0, currFileName.lastIndexOf(System.getProperty("file.separator")));
    }

    boolean exportFile() {
        this.repaint();
        if (JFileChooser.APPROVE_OPTION == jFileChooser1.showSaveDialog(this)) {
            String file = jFileChooser1.getSelectedFile().getPath();
            if (!file.endsWith(".rtf")) {
                file += ".rtf";
            }
            this.repaint();
            return saveFile(file);
        } else {
            this.repaint();
            return false;
        }
    }


    /**
     * Comprueba si el archivo esta o no guardado.
     *
     * @return true si esta guardado, false en caso contrario.
     */
    boolean isSaved() {
        if (!dirty) {
            return true;
        }
        int value = JOptionPane.showConfirmDialog(this, "Save changes?",
                                                  "Text Edit", JOptionPane.YES_NO_CANCEL_OPTION);
        switch (value) {
        case JOptionPane.YES_OPTION:

            return saveFile(this.currFileName);
        case JOptionPane.NO_OPTION:

            return true;
        case JOptionPane.CANCEL_OPTION:
        default:

            return false;
        }
    }

    void updateCaption() {
        String caption;
        caption = "Editing the comment of " + getJSWFileName();
        if (dirty) {
            caption = "* " + caption;
            caption = "JSignalWorkbench Editor - " + caption;
        }

        this.setTitle(caption);
    }

    public void fileExit_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }


    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            fileExit_actionPerformed(null);
        }
    }


    void jMenuItem7ActionPerformed(ActionEvent e) {
        Color color = JColorChooser.showDialog(this, "Background Color", editorpane.getBackground());
        if (color != null) {
            editorpane.setBackground(color);
        }
        this.repaint();
    }

    void jMenuItem1ActionPerformed(ActionEvent e) {
        if (isSaved()) {
            editorpane.setText("");
            statusBar.setText("New");
            this.setTitle("New");
            //currFileName = null;
            dirty = false;
            updateCaption();
        }
    }

    void jMenuItem2ActionPerformed(ActionEvent e) {
        fileOpen();
    }

    void jMenuItem3ActionPerformed(ActionEvent e) {
        saveFile(this.currFileName);
    }

    void jMenuItem4ActionPerformed(ActionEvent e) {
        exportFile();
    }

    void jButton1ActionPerformed(ActionEvent e) {
        fileOpen();
    }

    void jButton2ActionPerformed(ActionEvent e) {
        saveFile(this.currFileName);
    }

    void document1ChangedUpdate(DocumentEvent e) {
        dirty = true;
        updateCaption();
    }

    void document1InsertUpdate(DocumentEvent e) {
        if (!dirty) {
            dirty = true;
            updateCaption();
        }
    }

    void document1RemoveUpdate(DocumentEvent e) {
        if (!dirty) {
            dirty = true;
            updateCaption();
        }
    }

    public String getTextoComentario() {
        return editorpane.getText();
    }

    void jButton3ActionPerformed(ActionEvent e) {
        jMenuItem1ActionPerformed(e);
    }

    void jButton3MouseEntered(MouseEvent e) {
        jButton3.setBorder(selected);
    }

    void jButton3MouseExited(MouseEvent e) {
        jButton3.setBorder(normal);
    }

    void jButton1MouseEntered(MouseEvent e) {
        jButton1.setBorder(selected);
    }

    void jButton1MouseExited(MouseEvent e) {
        jButton1.setBorder(normal);
    }

    void jButton2MouseEntered(MouseEvent e) {
        jButton2.setBorder(selected);
    }

    void jButton2MouseExited(MouseEvent e) {
        jButton2.setBorder(normal);
    }

    public ByteArrayOutputStream getInMemoryDucyment() {
        return inMemoryDucyment;
    }

    public void setInMemoryDucyment(ByteArrayOutputStream inMemoryDucyment) {
        this.inMemoryDucyment = inMemoryDucyment;
    }

    public void setCurrFileName(String currFileName) {
        this.currFileName = currFileName;
    }

    public void jMenuItem5_actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this,
                                      "<html></head><body><p><font color=\"#FF0000\" size=\"5\">About</font>" +
                                      "</p><p><font " +
                                      "color=\"#0000FF\" size=\"4\">" +
                                      "</font></p><p><font color=\"#0000FF\" size=\"4\"> text editor.<br>" +
                                      "Developed by Abraham Otero and Roman Segador</font></p></body></html>",
                                      "About", JOptionPane.INFORMATION_MESSAGE);
    }


    class TextEditFrameMenuFileExitActionAdapter implements ActionListener {
        JSWTextProcessor adaptee;

        TextEditFrameMenuFileExitActionAdapter(JSWTextProcessor adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.fileExit_actionPerformed(e);
        }
    }


    class TextEditFrameMenuItem7ActionAdapter implements java.awt.event.ActionListener {
        JSWTextProcessor adaptee;

        TextEditFrameMenuItem7ActionAdapter(JSWTextProcessor adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.jMenuItem7ActionPerformed(e);
        }
    }


    class TextEditFrameJMenuItem1ActionAdapter implements java.awt.event.ActionListener {
        JSWTextProcessor adaptee;

        TextEditFrameJMenuItem1ActionAdapter(JSWTextProcessor adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.jMenuItem1ActionPerformed(e);
        }
    }


    class TextEditFrameJMenuItem2ActionAdapter implements java.awt.event.ActionListener {
        JSWTextProcessor adaptee;

        TextEditFrameJMenuItem2ActionAdapter(JSWTextProcessor adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.jMenuItem2ActionPerformed(e);
        }
    }


    class TextEditFrameJMenuItem3ActionAdapter implements java.awt.event.ActionListener {
        JSWTextProcessor adaptee;

        TextEditFrameJMenuItem3ActionAdapter(JSWTextProcessor adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.jMenuItem3ActionPerformed(e);
        }
    }


    class TextEditFrameJMenuItem4ActionAdapter implements java.awt.event.ActionListener {
        JSWTextProcessor adaptee;

        TextEditFrameJMenuItem4ActionAdapter(JSWTextProcessor adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.jMenuItem4ActionPerformed(e);
        }
    }


    class TextEditFrameJButton1ActionAdapter implements java.awt.event.ActionListener {
        JSWTextProcessor adaptee;

        TextEditFrameJButton1ActionAdapter(JSWTextProcessor adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.jButton1ActionPerformed(e);
        }
    }


    class TextEditFrameJButton2ActionAdapter implements java.awt.event.ActionListener {
        JSWTextProcessor adaptee;

        TextEditFrameJButton2ActionAdapter(JSWTextProcessor adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.jButton2ActionPerformed(e);
        }

    }


    class TextEditFrameDocument1DocumentAdapter implements javax.swing.event.DocumentListener {
        JSWTextProcessor adaptee;

        TextEditFrameDocument1DocumentAdapter(JSWTextProcessor adaptee) {
            this.adaptee = adaptee;
        }

        public void changedUpdate(DocumentEvent e) {
            adaptee.document1ChangedUpdate(e);
        }

        public void insertUpdate(DocumentEvent e) {
            adaptee.document1InsertUpdate(e);
        }

        public void removeUpdate(DocumentEvent e) {
            adaptee.document1RemoveUpdate(e);
        }
    }


}


class JSWTextProcessor_jMenuItem5_actionAdapter implements ActionListener {
    private JSWTextProcessor adaptee;
    JSWTextProcessor_jMenuItem5_actionAdapter(JSWTextProcessor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jMenuItem5_actionPerformed(e);
    }
}
