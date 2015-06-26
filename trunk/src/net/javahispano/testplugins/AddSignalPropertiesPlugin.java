/*
 * AddSignalPropertiesPlugin.java
 *
 * Created on 12 de octubre de 2007, 10:36
 */

package net.javahispano.testplugins;

import java.util.List;

import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;

/**
 *
 * @author Roman Segador
 */
public class AddSignalPropertiesPlugin extends AlgorithmAdapter {
    int i = 0;
    public AddSignalPropertiesPlugin() {
    }

    public String getName() {
        return "AddSignalPropertiesPlugin";
    }

    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties> signals) {

        for (SignalIntervalProperties sip : signals) {
            if (sip.isFullSignal()) {
                sip.getSignal().cleanProperties();
                for (int index = 0; index < 5; index++) {
                    DebugBean db = new DebugBean();
                    db.setVal1(i);
                    db.setVal2(i + 1.5f);
                    db.setVal3("bean numero" + i);
                    sip.getSignal().setProperty("prueba " + i, db);
                    i++;
                }
            }
        }
    }

}
