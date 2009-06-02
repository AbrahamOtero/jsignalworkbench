package net.javahispano.jsignalwb.io;

import net.javahispano.jsignalwb.jsignalmonitor.ChannelProperties;
import org.jdom.Element;

/**
 *
 * @author Roman
 */
class XMLChannelProperties extends Element {

    /** Creates a new instance of XMLChannelPorperties */
    public XMLChannelProperties(ChannelProperties cp) {
        super("ChannelProperties");
        //setAttribute("DataStroke",cp.getDataStroke().);
        setAttribute("MinValue", String.valueOf(cp.getAbscissaValue()));
        setAttribute("MaxValue", String.valueOf(cp.getMaxValue()));
        setAttribute("DataColor", String.valueOf(cp.getDataColor().getRGB()));
        setAttribute("Visible", Boolean.toString(cp.isVisible()));
//        setAttribute("AbscissaValue",String.valueOf(cp.getAbscissaValue()));
    }

}
