package beats.anotaciones;

import java.awt.Window;

import javax.swing.JFrame;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.DefaultIntervalMark;
import java.awt.Color;

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
    public static final int APNEA =1, HIPOAPNEA=2, DESATURACION =3, LATIDOS = 0;
    private int tipo=1;
    private boolean automatica=false;

    public LimitacionAnotacion() {
       super ();
   }

    public String getName() {
        return "Limitacion de flujo";
    }

    public void showMarkInfo(Window owner) {
        new LimitacionesDialog((JFrame)JSWBManager.getParentWindow(), "Marca:", true,this);
    }

    public int getTipo() {
        return tipo;
    }

    public boolean isAutomatica() {
        return automatica;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
               this.setComentary(tipo+"");
               if (tipo == LimitacionAnotacion.APNEA ||
                   tipo == LimitacionAnotacion.LATIDOS) {
                   this.setColor(Color.RED);
               } else if (tipo == LimitacionAnotacion.HIPOAPNEA) {
               this.setColor(Color.YELLOW);
               } else {
               this.setColor(Color.BLUE);
               }

    }

    public void setAutomatica(boolean automatica) {
        this.automatica = automatica;
    }
    public String getDataToSave() {
        return this.automatica + "*"+super.getDataToSave();
    }
    public void setSavedData(String data) {
        String a = data.substring(0,data.indexOf('*'));
        this.automatica = Boolean.parseBoolean(a);
        super.setSavedData(data.substring(data.indexOf('*')+1,data.length()));
        if (this.getComentary().toLowerCase().equals("2")) {
            this.setTipo(LimitacionAnotacion.HIPOAPNEA);
        }
        if (this.getComentary().toLowerCase().equals("3")) {
            this.setTipo(LimitacionAnotacion.DESATURACION);
        }
        if (this.getComentary().toLowerCase().equals("1")) {
            this.setTipo(LimitacionAnotacion.APNEA);
        }

        if (this.getComentary().toLowerCase().equals("0")) {
            this.setTipo(LimitacionAnotacion.LATIDOS);
        }
    }
}
