package research.beats.asistencia;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.plugins.Plugin;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.JSWBManager;
import java.util.Collection;
import javax.swing.Icon;
import net.javahispano.jsignalwb.SignalManager;
import java.util.List;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;
import net.javahispano.jsignalwb.IntervalSelectedListener;
import net.javahispano.jsignalwb.IntervalSelectedEvent;
import net.javahispano.jsignalwb.plugins.MarkPlugin;
import java.util.TreeSet;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;
import java.util.SortedSet;
import net.javahispano.jsignalwb.jsignalmonitor.*;

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
public class BorrarMarcasEnIntervalo extends AlgorithmAdapter implements IntervalSelectedListener {

    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {


        JSWBManager jsw = JSWBManager.getJSWBManagerInstance();
        JSMProperties jsm = jsw.getJSignalMonitor().getJSMProperties();
        jsm.setMarkCreation(false);
        jsm.setClicked(false);
        jsw.selectInterval(this);

    }

    public boolean hasOwnExecutionGUI() {
        return true;
    }

    public void launchExecutionGUI(JSWBManager jswbManager) {
        this.runAlgorithm(jswbManager.getSignalManager(), null, null);
    }

    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        if (gUIPositions == GUIPositions.MENU) {
            return true;
        } else if (gUIPositions == GUIPositions.TOOLBAR) {
            return true;
        }
        return false;
    }

    public Icon getIcon() {
         return new javax.swing.ImageIcon(getClass().getResource("erase.gif"));
    }

    public String getName() {
        return "Borrar marcas";
    }

    public String getDescription() {
        return "Borrar marcas";
    }

    public String getShortDescription() {
        return "Borrar marcas";
    }

    public void intervalSelectedActionPerformed(IntervalSelectedEvent evt) {
      SignalManager sm = JSWBManager.getSignalManager();
      Signal signal = sm.getSignal(evt.getChannelName());
      List<MarkPlugin> listMarks = signal.getAllMarks();
      TreeSet<MarkPlugin> treeMarks = new TreeSet<MarkPlugin>(listMarks);
      DefaultIntervalMark begining = new DefaultIntervalMark();
      begining.setMarkTime(evt.getStartTime());
      begining.setEndTime(evt.getStartTime());
      DefaultIntervalMark end = new DefaultIntervalMark();
     end.setMarkTime(evt.getEndTime());
     end.setEndTime(evt.getEndTime());
     SortedSet<MarkPlugin> eraseMarks = treeMarks.subSet(begining,end);

     for (MarkPlugin m: eraseMarks) {
         signal.removeMark(m);
         System.out.println("ssssssss");
     }

            JSWBManager.getJSignalMonitor().repaintChannels();

       JSWBManager.getJSignalMonitor().setMarksSelectionMode(true);
    }
}
