/*
 * LoadedTableCellEditorAndRenderer.java
 *
 * Created on 14 de septiembre de 2007, 1:52
 */

package net.javahispano.jsignalwb.plugins.framework;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Roman Segador
 */
public class LoadedTableCellEditorAndRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer,
        ActionListener {
    private JButton button;
    private JLabel label;
    private boolean loaded;
    public LoadedTableCellEditorAndRenderer() {
        Image image = Toolkit.getDefaultToolkit().createImage(
                LoadedTableCellEditorAndRenderer.class.getResource(
                        "images/load.jpg"));
        Icon smallIcon = new ImageIcon(
                image.getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        button = new JButton(smallIcon);
        button.setBackground(Color.WHITE);
        button.setBorderPainted(false);
        button.setToolTipText("Click to load the plugin");
        button.addActionListener(this);
        Image imageTic = Toolkit.getDefaultToolkit().createImage(
                LoadedTableCellEditorAndRenderer.class.getResource(
                        "images/tic.jpg"));
        label = new JLabel(
                new ImageIcon(
                        imageTic.getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setToolTipText("Plugin loaded");
        loaded = false;
    }

    public Object getCellEditorValue() {
        if (loaded) {
            return label;
        }
        return button;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof JButton) {
            button = (JButton) value;
            button.addActionListener(this);
            loaded = false;
            return (JButton) value;
        } else {
            loaded = true;
            return label;
        }
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        if (value instanceof JButton) {
            return button;
        } else {
            return label;
        }
    }

    public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
    }
}
