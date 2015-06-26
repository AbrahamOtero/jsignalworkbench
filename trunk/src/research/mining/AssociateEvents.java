package research.mining;

import java.awt.Color;
import java.util.*;
import java.util.List;

import javax.swing.Icon;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.*;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import research.beats.anotaciones.DesaturacionAnotacion;
import research.beats.anotaciones.LimitacionAnotacion;

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
public class AssociateEvents extends AlgorithmAdapter {

    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {
        asociate(sm);

    }

    public void asociate(SignalManager sm) {
        Signal sato2 = sm.getSignal("Sat02");
        Signal flux = sm.getSignal("Flujo");
        Signal torax = sm.getSignal("Movimiento toracico");
        Signal abdomen = sm.getSignal("Movimiento abdominal");

        TreeSet<LimitacionAnotacion> limTree = getMarksAsTree(flux);
        TreeSet<LimitacionAnotacion> limAbdomen = getMarksAsTree(abdomen);
        TreeSet<LimitacionAnotacion> limTorax = getMarksAsTree(torax);
        java.util.List < net.javahispano.jsignalwb.plugins.MarkPlugin > listMarkPlugins = sato2.getAllMarks();
        TreeSet<DesaturacionAnotacion> desatTree = new TreeSet<DesaturacionAnotacion>();
        for (MarkPlugin elem : listMarkPlugins) {
            //Necesario porque Paco ha puesto algunas anotaciones mal
            if (!(elem instanceof DesaturacionAnotacion)) {
                desatTree.add(changeAnnotationType(sato2, elem));
                continue;
            }

            desatTree.add((DesaturacionAnotacion) elem);
        }
        assert (listMarkPlugins.size() == desatTree.size());
        asociateDesatAndLimit(limTree, desatTree);

        associateLimitationsAndMovement(limAbdomen, desatTree,true);
        associateLimitationsAndMovement(limTorax, desatTree,false);
    }

    private void associateLimitationsAndMovement(TreeSet<LimitacionAnotacion> limAbdomen,
            TreeSet<DesaturacionAnotacion> desatTree, boolean abdomen) {
        for (DesaturacionAnotacion desat : desatTree) {
            List<LimitacionAnotacion> limitationsList = desat.getLimitationsList();
            for (LimitacionAnotacion limitation : limitationsList) {
                LimitacionAnotacion begin = new LimitacionAnotacion();
                begin.setMarkTime(limitation.getMarkTime() - 10000);
                LimitacionAnotacion end = new LimitacionAnotacion();
                end.setMarkTime(limitation.getMarkTime() + (limitation.getEndTime() - limitation.getMarkTime()) / 2);
                Set<LimitacionAnotacion> abdomenEvents = limAbdomen.subSet(begin, end);
                for (LimitacionAnotacion aa : abdomenEvents) {
                    if (abdomen) {
                        limitation.addAbdomenLimitation(aa);
                    } else {
                        limitation.addToraxLimitation(aa);
                    }
                }
            }
        }
    }

    private TreeSet<LimitacionAnotacion> getMarksAsTree(Signal flux) {
        List<MarkPlugin> listMarkPlugins = flux.getAllMarks();
        TreeSet<LimitacionAnotacion> limTree = new TreeSet<LimitacionAnotacion>();
        for (Object elem : listMarkPlugins) {
            limTree.add((LimitacionAnotacion) elem);
        }
        assert (listMarkPlugins.size() == limTree.size());
        return limTree;
    }

    private void asociateDesatAndLimit(TreeSet<LimitacionAnotacion> limTree,
            TreeSet<DesaturacionAnotacion> desatTree) {
        for (DesaturacionAnotacion desat : desatTree) {
            LimitacionAnotacion lim = limTree.floor(desat);
            for (int i = 0; i < 2 && lim != null; i++) { //chapuza; a veces debe asociarse con una que no es la inmediatamente anterior
                long tLimIni = lim.getMarkTime() / 1000;
                long tdesatIni = desat.getMarkTime() / 1000;
                if (tdesatIni > tLimIni + 10 && tdesatIni < tLimIni + 50) {
                    //no hace falta probar otra vez; la inmediatamente anterior es la buena
                    i = 2;
                    limTree.remove(lim);
                    desat.addLimitation(lim);
                    boolean asociationPerformed = false;
                    do {
                        asociationPerformed = false;
                        LimitacionAnotacion limt = new LimitacionAnotacion();
                        limt.setMarkTime(lim.getMarkTime() + 1);
                        lim = limTree.ceiling(limt);
                        if (lim == null) {
                            break;
                        }
                        long tLimEnd = lim.getEndTime() / 1000;
                        long tDesatEnd = desat.getEndTime() / 1000;
                        if (tDesatEnd > tLimEnd + 20) {
                            limTree.remove(lim);
                            desat.addLimitation(lim);
                            asociationPerformed = true;
                        }
                    } while (asociationPerformed);
                } else { //probamos con la anterior a la anterior
                    DesaturacionAnotacion limDelayed = new DesaturacionAnotacion();
                    limDelayed.setMarkTime(lim.getMarkTime() - 1);
                    lim = limTree.floor(limDelayed);
                }
            }
        }
    }

    private DesaturacionAnotacion changeAnnotationType(Signal sato2, MarkPlugin elem) {
        sato2.removeMark(elem);
        DesaturacionAnotacion d = new DesaturacionAnotacion();
        d.setMarkTime(elem.getMarkTime());
        d.setEndTime(elem.getEndTime());
        d.setAutomatica(false);
        d.setTipo(DesaturacionAnotacion.HIPOAPNEA);
        d.setColor(Color.BLACK);
        return d;
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
        return super.generateImage("AE");
    }

    public String getName() {
        return "Asociar Eventos";
    }

    public String getDescription() {
        return "Asociar Eventos";
    }

    public String getShortDescription() {
        return "Asociar Eventos";
    }
}
