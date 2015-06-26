package research.apneas.grid;

import java.awt.*;

import net.javahispano.jsignalwb.jsignalmonitor.DefaultGrid;
import net.javahispano.jsignalwb.jsignalmonitor.GridConfiguration;
import net.javahispano.jsignalwb.plugins.GridPluginAdapter;

public class GridGris extends GridPluginAdapter {


    private DefaultGrid defaultGrid = new DefaultGrid();
    private int bigSpace;
    private int bigSpaceY;

    public int getLeyendWidth() {
        return bigSpace;
    }

    public int getLeyendHeight() {
        return bigSpaceY;
    }

    public String getName() {
        return "Default";
    }

    public void paintGrid(Graphics2D g2d, Point p, int height, int width,
                          GridConfiguration gridconfig) {
        bigSpace = Math.round((width - 5) / (float) 10);
        bigSpaceY = Math.round((height - 5) / (float) 4);
        g2d = (Graphics2D) g2d.create();

        defaultGrid.paintGrid(g2d, p, height, width, gridconfig);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
        g2d.setColor(Color.lightGray);
        g2d.fillRect(p.x, p.y, width, height);

    }


}
