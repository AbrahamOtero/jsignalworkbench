package research.apneas.grid;

import java.awt.*;

import net.javahispano.fuzzyutilities.representation.TrapezoidalDistribution;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.jsignalmonitor.DefaultGrid;
import net.javahispano.jsignalwb.jsignalmonitor.GridConfiguration;
import net.javahispano.jsignalwb.plugins.GridPluginAdapter;
import net.javahispano.jsignalwb.utilities.TimePositionConverter;
import research.apneas.ReduccionFlujo;

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
public class ApneaGrid extends GridPluginAdapter {
    private float[] valorBasal;
    private float[] delta;
    private final int pasoX = 20;
    private final int pasoX_1 = pasoX - 1;
    private DefaultGrid defaultGrid = new DefaultGrid();
    private TrapezoidalDistribution apnea =
            new TrapezoidalDistribution(0, 0, 0.1F, 0.25F);
    private TrapezoidalDistribution ha =
            new TrapezoidalDistribution(0, 0, 0.5F, 0.8F);
    private boolean pintarRalla = true;
    private int bigSpace;
    private int bigSpaceY;

    public int getLeyendWidth() {
        return bigSpace;
    }

    public int getLeyendHeight() {
        return bigSpaceY;
    }

    public ApneaGrid() {
        valorBasal = ReduccionFlujo.getValorBasal();
        delta = ReduccionFlujo.getDelta();
    }

    public void setSignal(Signal s) {
        signal = s;
        apnea =
                new TrapezoidalDistribution(0, 0, 0.1F, 0.25F);
        if (s.getName().equals("R. Airflow")) {
            ha =
                    new TrapezoidalDistribution(0, 0, 0.25F, 0.8F);
        } else {
            ha =
                    new TrapezoidalDistribution(0, 0, 0.25F, 0.6F);
        }

    }

    public String getName() {
        return "Grid Apnea";
    }

    public void launchConfigureGridGUI(Window owner) {

    }

    public void paintGrid(Graphics2D g2d, Point p, int height, int width,
                          GridConfiguration gridconfig) {
        bigSpace = Math.round((width - 5) / (float) 10);
        bigSpaceY = Math.round((height - 5) / (float) 4);
        g2d = (Graphics2D) g2d.create();
        defaultGrid.paintGrid(g2d, p, height, width, gridconfig);
        long start = JSWBManager.getJSignalMonitor().getScrollValue();
        long end = start + JSWBManager.getJSignalMonitor().getVisibleTime();
        int startIndex = TimePositionConverter.timeToPosition(start, signal);
        int endIndex = TimePositionConverter.timeToPosition(end, signal);
        float temporalStep = (endIndex - startIndex) / (float) width;
        //asumimos cero en el centro
        float magnitudeStep = (2 * gridconfig.getMaxValue()) / height;
        int van = -pasoX;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .6f));
        for (float i = startIndex; i < endIndex; i += pasoX * temporalStep) {
            van += pasoX;
            for (int j = 0; j < height / 2; j++) {
                int altura = p.y + height / 2 - j;
                int altura2 = p.y + height / 2 + j;
                short pos = apnea.evaluatepossibilityAt(j * 2 * magnitudeStep / (valorBasal[(int) i]));
                if (pos == 0) {
                    j++;
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                    paintFragm(g2d, p, van, altura, altura2, Color.BLACK);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .6f));
                    for (; j < height / 2; j++) {
                        altura = p.y + height / 2 - j;
                        altura2 = p.y + height / 2 + j;
                        pos = ha.evaluatepossibilityAt(j * 2 * magnitudeStep / (valorBasal[(int) i]));
                        paintFragm(g2d, p, van, altura, altura2, getColorYellow(pos));
                        if (pos == 0) {
                            break;
                        }
                    }
                    break;
                }
                paintFragm(g2d, p, van, altura, altura2, getColorRed(pos));
            }
        }

    }

    private void paintFragm(Graphics2D g2d, Point p, int van, int altura, int altura2, Color color) {
        if (!pintarRalla && color == Color.black) {
            return;
        }
        g2d.setColor(color);
        g2d.drawLine(p.x + van, altura, p.x + van + pasoX_1, altura);
        g2d.drawLine(p.x + van, altura2, p.x + van + pasoX_1, altura2);

    }

    public boolean hasDataToSave() {
        if (signal != null) {
            return true;
        }
        return false;
    }

    public void setSavedData(String data) {

    }

    public String getDataToSave() {
        return "";
    }

    public static Color getColorRed(short code) {
        return new Color(255, (int) (2.5F * (100 - code)), 0);
    }

    public static Color getColorYellow(short code) {
        return new Color(255 - (int) (2.5F * (100 - code)), 255, 0);
    }
}
