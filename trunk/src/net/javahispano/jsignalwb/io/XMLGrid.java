/*
 * XMLGrid.java
 *
 * Created on 4 de septiembre de 2007, 0:30
 */

package net.javahispano.jsignalwb.io;

import net.javahispano.jsignalwb.plugins.GridPlugin;
import org.jdom.Element;

/**
 *
 * @author Roman Segador
 */
class XMLGrid extends Element {

    public XMLGrid(GridPlugin grid) {
        super("Grid");
        String gridData = "";
        if (grid.hasDataToSave()) {
            gridData = grid.getDataToSave();
        }
        addContent(new XMLPlugin("grid:" + grid.getName(), grid));
    }

}
