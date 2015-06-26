/*
 * showSignalsPropertiesPlugin.java
 *
 * Created on 12 de octubre de 2007, 11:00
 */

package net.javahispano.testplugins;

import java.util.Iterator;

import javax.swing.JOptionPane;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.GenericPluginAdapter;

/**
 *
 * @author Roman Segador
 */
public class ShowSignalsPropertiesPlugin extends GenericPluginAdapter {


    public String getName() {
        return "ShowSignalsPropertiesPlugin";
    }

    public void launch(JSWBManager jswbManager) {
        SignalManager sm = jswbManager.getSignalManager();
        Iterator<String> names = sm.getSignalsNames().iterator();
        String global = "Propiedades de las senhales:\n";
        while (names.hasNext()) {
            String name = names.next();
            Signal signal = sm.getSignal(name);
            global = global + "     Signal:" + name;
            Iterator<String> prop = signal.getAvailableProperties().iterator();
            while (prop.hasNext()) {
                String property = prop.next();
                Object obj = signal.getProperty(property);
                global = global + "\n         " + property + "-->" + obj.getClass().getName();
                DebugBean db;
                if (obj instanceof DebugBean) {
                    db = (DebugBean) obj;
                    global = global + " (" + db.getVal1() + ", " + db.getVal2() + ", " + db.getVal3() + ")";
                }
            }
            global = global + "\n";
        }
        JOptionPane.showMessageDialog(jswbManager.getParentWindow(), global);
    }

}
