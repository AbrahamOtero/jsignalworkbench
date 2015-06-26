package research.beats.anotaciones;

import java.awt.Color;
import java.awt.Window;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;
import net.javahispano.jsignalwb.jsignalmonitor.marks.MarkPaintInfo;
import java.awt.Graphics2D;
import java.awt.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.UIManager;
import javax.swing.ToolTipManager;

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
public class LimitacionAnotacion extends DefaultIntervalMark {
    public static final int APNEA = 1, HIPOAPNEA = 2, DESATURACION = 3, N = 0,
    A = -1, V = -2, P = -3, TV = -4, Vrt = -5, Prt = -6;
    private int tipo = 1;
    private boolean automatica = false;
    public LimitacionAnotacion() {
        super();
    }

    private List<LimitacionAnotacion> toraxList =
            new LinkedList<LimitacionAnotacion>();
    private List<LimitacionAnotacion> abdomenList =
            new LinkedList<LimitacionAnotacion>();

    public void addToraxLimitation(LimitacionAnotacion l) {
        this.toraxList.add(l);
    }

    public void addAbdomenLimitation(LimitacionAnotacion l) {
        this.abdomenList.add(l);
    }

    public List<LimitacionAnotacion> getAbdomenList() {
        return new LinkedList<LimitacionAnotacion>(abdomenList);
    }

    public List<LimitacionAnotacion> getToraxList() {
        return toraxList;
    }


    public String getName() {
        return "Limitacion de flujo";
    }

    public void showMarkInfo(Window owner) {
        new LimitacionesDialog((JFrame) JSWBManager.getParentWindow(), "Marca:", true, this);
    }

    public int getTipo() {
        return tipo;
    }

    public boolean isAutomatica() {
        return automatica;
    }

    public String getToolTipText() {
        UIManager.put("ToolTip.foreground", new ColorUIResource(Color.black));
            UIManager.put("ToolTip.background", new ColorUIResource(Color.CYAN ));

            ToolTipManager.sharedInstance().setDismissDelay(10000);
         // Show tool tips immediately
    ToolTipManager.sharedInstance().setInitialDelay(600);
        return "<html>Desaturation<br>Possibility: 1.0<br>"+
                "<ul><li>Drop: 18</li><li>Span: 49 sec.</li><li>Maximum value: 98</li>"+
                "<li>Minimum value: 80</li><li>Mean value: 89</li></ul></html>";
    }

  /* */ public void paint(Graphics2D g2d, MarkPaintInfo markPaintInfo) {
        super.paint(g2d, markPaintInfo);
        if (!isAutomatica()) {
            g2d.setColor(Color.GREEN);
            Point p = markPaintInfo.getPoint();
            int radio = 10;
            int x = p.x + markPaintInfo.getWidth()/2 -  radio/2;
            int y = p.y +  2*markPaintInfo.getHeight()/3 - radio;
            g2d.fillOval(x, y, radio, radio);
        }
        if (this.getSignal().getName().equals("Flujo") ) {
            g2d.setColor(Color.BLACK);
            Point p = markPaintInfo.getPoint();
            int maxY = (int) Math.max(markPaintInfo.getPoint().getY(),
                                  markPaintInfo.getMaxValueY());
            int x = p.x + markPaintInfo.getWidth()/2 - 2;
            int y = maxY  -16;
            if (this.tipo == APNEA) {
                //g2d.drawString("A", x, y);
            }
            if (this.tipo == HIPOAPNEA) {
                //g2d.drawString("H", x, y);
            }

        }
    }/**/

    public void setTipo(int tipo) {
        this.tipo = tipo;
        this.setComentary(tipo + "");
        if (tipo == LimitacionAnotacion.APNEA ||
            tipo <= 0) {
          //  this.setColor(Color.RED);
        } else if (tipo == LimitacionAnotacion.HIPOAPNEA) {
          //  this.setColor(Color.YELLOW);
        } else {
          //  this.setColor(Color.BLUE);
        }

    }

    public void setAutomatica(boolean automatica) {
        this.automatica = automatica;
    }

    public String getDataToSave() {
        return this.automatica + "*" + super.getDataToSave();
    }

    public void setSavedData(String data) {
        String a = data.substring(0, data.indexOf('*'));
        this.automatica = Boolean.parseBoolean(a);
        super.setSavedData(data.substring(data.indexOf('*') + 1, data.length()));
        if (this.getComentary().toLowerCase().equals("2")) {
            this.setTipo(LimitacionAnotacion.HIPOAPNEA);
        }
        if (this.getComentary().toLowerCase().equals("3")) {
            this.setTipo(LimitacionAnotacion.DESATURACION);
        }
        if (this.getComentary().toLowerCase().equals("1")) {
            this.setTipo(LimitacionAnotacion.APNEA);
        }

        if (this.getComentary().toLowerCase().equals("-1")) {
            this.setTipo(LimitacionAnotacion.A);
        }
        if (this.getComentary().toLowerCase().equals("-2")) {
            this.setTipo(LimitacionAnotacion.V);
        }
        if (this.getComentary().toLowerCase().equals("-3")) {
            this.setTipo(LimitacionAnotacion.P);
        }
        if (this.getComentary().toLowerCase().equals("-4")) {
            this.setTipo(LimitacionAnotacion.TV);
        }
        if (this.getComentary().toLowerCase().equals("-5")) {
            this.setTipo(LimitacionAnotacion.Vrt);
        }
        if (this.getComentary().toLowerCase().equals("-6")) {
            this.setTipo(LimitacionAnotacion.Prt);
        }
    }


    /* public int compareTo(Object o) {
     DefaultIntervalMark i = (DefaultIntervalMark) o;
     if (i.getMarkTime() < this.getMarkTime()) {
         return 1;
     } else if (i.getMarkTime() > this.getMarkTime()) {
         return -1;
     }
     return 0;//chapuza para que no haya problemas con duplicados
     }*/

}
