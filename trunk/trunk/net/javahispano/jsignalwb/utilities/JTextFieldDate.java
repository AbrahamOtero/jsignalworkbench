package net.javahispano.jsignalwb.utilities;

import javax.swing.*;
import javax.swing.text.*;

import java.text.*;
import java.awt.*;


public class JTextFieldDate extends JFormattedTextField {

    private static DateFormat sdf;
    private static Dimension size = new Dimension(60,20);
    private static MaskFormatter mf;

    // Inicializador estático
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
     * Construye un campo de texto al cual se le aplicará un formateo del tipo dd/MM/yyyy");
     */


    public JTextFieldDate() {
        this("");
    }

    /**
     * Construye un campo de texto al cual se le aplicará un formateo del tipo dd/MM/yyyy,
     * iniciándolo con la fecha que se le pasa como argumento.");
     * @param text fecha inicial
     */



    public JTextFieldDate(String text) {

        super(text);
        setFormatter(mf);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        DefaultFormatterFactory dff = new DefaultFormatterFactory(mf,mf,mf);
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
          * Devuelve el texto si está correctamente formateado, o una caena de caracteres vacia en caso contrario
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
