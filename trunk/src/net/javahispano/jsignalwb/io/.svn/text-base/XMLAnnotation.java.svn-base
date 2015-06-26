/*
 * XMLAnnotation.java
 *
 * Created on 18 de julio de 2007, 14:50
 */

package net.javahispano.jsignalwb.io;

import net.javahispano.jsignalwb.plugins.AnnotationPlugin;
import org.jdom.Element;

/**
 *
 * @author Roman Segador
 */
public class XMLAnnotation extends Element {

    public XMLAnnotation(AnnotationPlugin ap) {
        super("Annotation");
        setAttribute("Name", ap.getName());
        setAttribute("BaseClass", ap.getClass().getCanonicalName());
        setAttribute("Version", ap.getPluginVersion());
        setAttribute("MarkTime", String.valueOf(ap.getAnnotationTime()));
        if (ap.isInterval()) {
            setAttribute("Interval", String.valueOf(true));
            setAttribute("EndTime", String.valueOf(ap.getEndTime()));
        } else {
            setAttribute("Interval", String.valueOf(false));
        }
        if (ap.hasDataToSave()) {
            setText(ap.getDataToSave());
        }
    }

}
