package research.beats.anotaciones;

import net.javahispano.jsignalwb.plugins.DefaultIntervalMark;
import net.javahispano.jsignalwb.JSWBManager;
import javax.swing.JFrame;
import java.awt.Window;

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
public class DesaturacionAnotacion extends LimitacionAnotacion {
    public DesaturacionAnotacion () {
        this.setTipo(LimitacionAnotacion.DESATURACION);
    }
      public String getName() {
        return "Desaturación";
    }
}

