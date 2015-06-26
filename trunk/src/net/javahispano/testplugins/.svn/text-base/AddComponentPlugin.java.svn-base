/*
 * AddComponentPlugin.java
 *
 * Created on 2 de octubre de 2007, 19:14
 */

package net.javahispano.testplugins;

import javax.swing.JButton;
import javax.swing.JToolBar;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.GenericPluginAdapter;

/**
 *
 * @author Roman Segador
 */
public class AddComponentPlugin extends GenericPluginAdapter {
    private int count = -1;
    public AddComponentPlugin() {

    }

    public String getName() {
        return "AddComponentPlugin";
    }

    public void launch(JSWBManager jswbManager) {
        JSWBManager jswb = JSWBManager.getJSWBManagerInstance();
        count++;

        if (count > 10 || count == 0) {
            jswb.removeLeftComponent();
            jswb.removeRightComponent();
            jswb.removeLowerComponent();
            jswb.removeUpperComponent();
        } else if (count % 4 == 0) {
            JToolBar bar = new JToolBar(JToolBar.HORIZONTAL);
            bar.setFloatable(false);
            bar.add(new JButton("" + count));
            jswb.setUpperComponent(bar);
        } else if (count % 4 == 1) {
            JToolBar bar = new JToolBar(JToolBar.VERTICAL);
            bar.setFloatable(false);
            bar.add(new JButton("" + count));
            jswb.setRightComponent(bar);
        } else if (count % 4 == 2) {
            JToolBar bar = new JToolBar(JToolBar.HORIZONTAL);
            bar.setFloatable(false);
            bar.add(new JButton("" + count));
            jswb.setLowerComponent(bar);
        } else if (count % 4 == 3) {
            JToolBar bar = new JToolBar(JToolBar.VERTICAL);
            bar.setFloatable(false);
            bar.add(new JButton("" + count));
            jswb.setLeftComponent(bar);
        }
        System.out.println("--->" + jswb.hasLeftComponent() + "---" +
                           jswb.hasUpperComponent() + "---" +
                           jswb.hasRightComponent() + "---" +
                           jswb.hasLowerComponent());
    }

}
