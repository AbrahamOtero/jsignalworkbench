/*
 * XMLProperty.java
 *
 * Created on 12 de octubre de 2007, 10:24
 */

package net.javahispano.jsignalwb.io;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;

import org.jdom.Element;


/**
 *
 * @author Roman Segador
 */
public class XMLProperty extends Element {

    public XMLProperty(String name, Object bean) {
        super("Property");
        setAttribute("Name", name);
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(byteArrayOS);
        encoder.writeObject(bean);
        encoder.close();
        String beanValue = byteArrayOS.toString();
        setText(beanValue);
    }

}
