/*
 * JMenuPluginDetails.java
 *
 * Created on 13 de septiembre de 2007, 18:07
 */

package net.javahispano.jsignalwb.plugins.framework;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Roman Segador
 */
public class JButtonTableCellEditorAndRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer,
        ActionListener {

    private JButton button;
    public JButtonTableCellEditorAndRenderer() {
        button = null;
    }

    public JButtonTableCellEditorAndRenderer(String text) {
        this(text, "Click");
    }

    public JButtonTableCellEditorAndRenderer(String text, String tooltipText) {
        button = new JButton(text);
        button.setToolTipText(tooltipText);
        button.addActionListener(this);
    }

    public JButtonTableCellEditorAndRenderer(Icon icon) {
        this(icon, "Click");
    }

    public JButtonTableCellEditorAndRenderer(Icon icon, String tooltipText) {
        button = new JButton(icon);
        button.setToolTipText(tooltipText);
        button.addActionListener(this);
    }

    public JButtonTableCellEditorAndRenderer(String text, Icon icon) {
        this(text, icon, "Click");
    }

    public JButtonTableCellEditorAndRenderer(String text, Icon icon, String tooltipText) {
        button = new JButton(text, icon);
        button.setToolTipText(tooltipText);
        button.addActionListener(this);
    }

    public Object getCellEditorValue() {
        return button;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value != null) {
            String toolTip = button.getToolTipText();
            button = (JButton) value;
            button.setToolTipText(toolTip);
            button.addActionListener(this);
            return (JButton) value;
        }
        return null;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        if (value != null) {
            return button;
        }
        return null;
    }

    public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
    }


}
