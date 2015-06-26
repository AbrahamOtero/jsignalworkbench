/*
 * XMLSignal.java
 *
 * Created on 2 de noviembre de 2006, 19:13
 */

package net.javahispano.jsignalwb.io;

import java.util.Iterator;
import java.util.List;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.plugins.MarkPlugin;
import org.jdom.Element;

/**
 *
 * @author Roman Segador de la Torre
 */
class XMLSignal extends Element {

    /** Crea un elemento Signal del fichero XML con la informacion
     * del objeto signal que recibe
     */
    public XMLSignal(Signal s) {
        super("Signal");
        setAttribute("Name", s.getName());
        setAttribute("HasPosibility", Boolean.toString(s.hasEmphasisLevel()));
        setAttribute("Start", String.valueOf(s.getStart()));
        setAttribute("Frecuency", String.valueOf(s.getSRate()));
        setAttribute("Magnitude", s.getMagnitude());
        setAttribute("Size", Integer.toString(s.getValues().length));
        addContent(new XMLGrid(s.getGrid()));
        addContent(new XMLChannelProperties(s.getProperties()));

        List<MarkPlugin> marks = s.getAllMarks();
        for (MarkPlugin mark : marks) {
            addContent(new XMLMark(mark));
        }

        Iterator<String> it = s.getAvailableProperties().iterator();
        while (it.hasNext()) {
            String property = it.next();
            Object bean = s.getProperty(property);
            addContent(new XMLProperty(property, bean));
        }
    }

}
