package net.javahispano.plugins.signalgeneration;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.GenericPluginAdapter;
import net.javahispano.jsignalwb.plugins.Plugin;
import net.javahispano.plugins.temporalseries.TemporalSeries;

public class SignalGenerationPlugin extends GenericPluginAdapter {
    public SignalGenerationPlugin() {
    }


    public String getName() {
        return "Signal Generation";
    }


    public void launch(JSWBManager jswbManager) {
        //antes de lanzar la interfaz gr\u2663fica debemos asegurarnos que
        //todas las senhales son series temporales
        TemporalSeries.convertSignalsToTemporalSeries(jswbManager.getSignalManager());
        SignalGeneration d = new SignalGeneration();
        d.setSize(680, 420);
        d.setLocationRelativeTo(jswbManager.getParentWindow());
        d.setVisible(true);
    }

    public String getShortDescription() {
        return "Permite generar senhales artificiales para testeo";
    }


    public String getDescription() {
        return "Plugin que permite generar una senhal artificial a partir de la suma de un conjunto de senos de amplitud, frecuencia y desfase configurable. Tambien permite anhadir ruido blanco de amplitud configurable a la senhal final.";
    }

    public Icon getIcon() {
        Image logo = Toolkit.getDefaultToolkit().createImage(
                SignalGeneration.class.getResource("logo.gif"));
        Icon pulso = new ImageIcon(logo);
        return pulso;
    }

    public String getPluginVersion() {
        return "0.5";
    }

    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        if (gUIPositions == GUIPositions.MENU) {
            return true;
        } else if (gUIPositions == GUIPositions.TOOLBAR) {
            return true;
        }
        return false;
    }
}
