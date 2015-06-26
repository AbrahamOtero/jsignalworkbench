package net.javahispano.jsignalwb.ui.signalorganizer;

import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;
import net.javahispano.jsignalwb.plugins.GenericPluginAdapter;
import net.javahispano.jsignalwb.plugins.Plugin;
import net.javahispano.jsignalwb.ui.texteditor.JSWTextProcessor;

public class SignalOrganizerPlugin extends GenericPluginAdapter implements SignalSizeListener {
    private SignalManager signalManager = JSWBManager.getSignalManager();

    public SignalOrganizerPlugin() {
        signalManager.addListener(this);
    }

    //se ejecuta en el EDT
    public void launch(JSWBManager jswbManager) {
        JSignalMonitor jSignalMonitor = JSWBManager.getJSignalMonitor();
        for (String signalName : signalManager.getSignalsNames()) {
            Signal signal = signalManager.getSignal(signalName);
            System.out.println(signal.getName() + " visible: " + signal.isVisible()
                               + "Posicion en pantalla: " + jSignalMonitor.getChannelPosition(signalName));
        }

        signalManager.hideAllSignals();

        for (String signalName : signalManager.getSignalsNames()) {
            signalManager.setSignalVisible(signalName, true);
        }
    }

    public String getDataToSave() {
        return "Datos que quiero guardar en la sesion de trabajo";
    }

    public void setSavedData(String dataSavedOnTheWorkingSession) {
        System.out.println(dataSavedOnTheWorkingSession);
    }

    public boolean hasDataToSave() {
        return true;
    }

    public void signalSizeActionPerformed(SignalSizeEvent event) {
      /*  if (event.isSignalsReset()) {
            return;
        }
       if (event.isSignalAdded()) {
            System.out.println("Anhadiendo senhal con nombre: " + event.getSignal().getName());
        } else {
            System.out.println("Eliminando senhal con nombre: " + event.getSignal().getName());
        }*/

    }

//no debera ser necesario tocarlo de aqui abajo
    public String getName() {
        return "Signal Organizer";
    }

    public String getShortDescription() {
        return "Configures the display order of sigals";
    }

    public String getDescription() {
        return this.getShortDescription();
    }

    public String getPluginVersion() {
        return "1.0";
    }

    public Icon getIcon() {
        return new ImageIcon(Toolkit.getDefaultToolkit().createImage(
                SignalOrganizerPlugin.class.getResource("organize.gif")));
    }

    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return false;
    }
}
