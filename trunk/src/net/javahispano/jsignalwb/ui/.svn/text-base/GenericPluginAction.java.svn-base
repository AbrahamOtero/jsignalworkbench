package net.javahispano.jsignalwb.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import net.javahispano.jsignalwb.JSWBManager;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class GenericPluginAction extends AbstractAction {
    public final static int CONFIGURE_ACTION = 1;
    public final static int LAUNCH_ACTION = 2;
    private int action;
    private String genericPluginName;
    private JSWBManager jswbm;

    public GenericPluginAction(JSWBManager jswbManager,
                               String genericPluginName, int action) {
        this(jswbManager, genericPluginName, action, 20, 20);
    }

    public GenericPluginAction(JSWBManager jswbManager,
                               String genericPluginName, int action, int iconWidth, int iconHeight) {
        this.action = action;
        this.genericPluginName = genericPluginName;
        this.jswbm = jswbManager;
        this.putValue(SHORT_DESCRIPTION, genericPluginName);
        if (action == CONFIGURE_ACTION) {
            this.putValue(NAME, "Configure");
        } else if (action == LAUNCH_ACTION) {
            this.putValue(NAME, "Launch");
            Icon smallIcon = jswbManager.getPluginManager().getIconDefaultSize("generic", genericPluginName,
                    iconWidth, iconHeight);
            this.putValue(SMALL_ICON, smallIcon);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (action == CONFIGURE_ACTION) {
            jswbm.showPluginConfiguration("generic", genericPluginName);
        } else if (action == LAUNCH_ACTION) {
            jswbm.showPluginExecution("generic", genericPluginName);
        }
    }
}
