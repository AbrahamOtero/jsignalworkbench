/*
 * XMLMark.java
 *
 * Created on 9 de julio de 2007, 12:31
 */

package net.javahispano.jsignalwb.io;

import net.javahispano.jsignalwb.plugins.MarkPlugin;
import org.jdom.Element;

/**
 *
 * @author Roman Segador
 */
public class XMLMark extends Element {

    public XMLMark(MarkPlugin mp) {
        super("Mark");
        setAttribute("Name", mp.getName());
        setAttribute("BaseClass", mp.getClass().getCanonicalName());
        setAttribute("Version", mp.getPluginVersion());
        setAttribute("MarkTime", String.valueOf(mp.getMarkTime()));
        if (mp.isInterval()) {
            setAttribute("Interval", String.valueOf(true));
            setAttribute("EndTime", String.valueOf(mp.getEndTime()));
        } else {
            setAttribute("Interval", String.valueOf(false));
        }
        if (mp.hasDataToSave()) {
            setText(mp.getDataToSave());
        }
    }


}
