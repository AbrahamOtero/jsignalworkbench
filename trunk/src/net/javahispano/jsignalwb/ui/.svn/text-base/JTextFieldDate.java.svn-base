package net.javahispano.jsignalwb.ui;

import java.awt.Dimension;
import java.text.*;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;


public class JTextFieldDate extends JFormattedTextField {

    private static DateFormat sdf;
    private static Dimension size = new Dimension(60, 20);
    private static MaskFormatter mf;

    // Inicializador estatico
    {
        try {
            mf = new MaskFormatter("##:##:##.### || ##/##/####");
            mf.setPlaceholderCharacter('_');
            sdf = new SimpleDateFormat("HH:mm:ss.SSS || dd/MM/yyyy");
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    /**
     * Construye un campo de texto al cual se le aplicara un formateo del tipo dd/MM/yyyy");
     */


    public JTextFieldDate() {
        this("");
    }

    /**
     * Construye un campo de texto al cual se le aplicara un formateo del tipo dd/MM/yyyy,
     * iniciandolo con la fecha que se le pasa como argumento.");
     * @param text fecha inicial
     */



    public JTextFieldDate(String text) {

        super(text);
        setFormatter(mf);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        DefaultFormatterFactory dff = new DefaultFormatterFactory(mf, mf, mf);
        setFormatterFactory(dff);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent fe) {
                try {
                    sdf.parse(JTextFieldDate.super.getText());
                } catch (ParseException pe) {
                    //JTextFieldDate.this.requestFocus();
                    setText("");
                }
            }
        });
    }

    /**
     * Devuelve el texto si esta correctamente formateado, o una caena de caracteres vacia en caso contrario
     * @return Texto.
     */

    public String getFormattedText() {
        try {
            sdf.parse(getText());
            return getText();
        } catch (ParseException pe) {
            return null;
        }
    }
}
