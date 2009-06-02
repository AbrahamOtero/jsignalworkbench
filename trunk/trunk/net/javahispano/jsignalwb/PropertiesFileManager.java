/*
 * PropertiesFileManager.java
 *
 * Created on 30 de julio de 2007, 17:44
 */

package net.javahispano.jsignalwb;

import java.io.*;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Roman Segador
 */
public class PropertiesFileManager {

    private File propertiesFile;
    public PropertiesFileManager() {

        try {
            File f = new File(System.getProperty("user.home") + "/.JSignalWorkBench");
            if (!f.exists()) {
                f.mkdir();
            }
            propertiesFile = new File(f, "jswbProperties.prop");
            if (!propertiesFile.exists()) {
                propertiesFile.createNewFile();
                Document doc = new Document(new Element("root"));
                XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
                FileOutputStream file = new FileOutputStream(propertiesFile);
                out.output(doc, file);
                file.flush();
                file.close();
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Imposible to create the properties file. The configuration data won't be remembered");
        }
    }

    public void deleteUninstallPlugins() {
        if (propertiesFile.exists()) {
            try {
                SAXBuilder builder = new SAXBuilder();
                Document doc = builder.build(propertiesFile);
                Element root = doc.getRootElement();
                Iterator it = root.getChildren("PluginToDelete").iterator();
                while (it.hasNext()) {
                    Object obj = it.next();
                    Element elem = (Element) obj;
                    File fd = new File(elem.getAttribute("path").getValue());
                    if (fd.exists()) {
                        fd.delete();
                    }
                }
            } catch (JDOMException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void savePropertiesFile(SessionInfo sessionInfo) {
        try {
            if (propertiesFile.exists()) {
                Element root = new Element("root");
                Document doc = new Document(root);
                Element lastFile = new Element("LastFile");
                lastFile.setAttribute("path", sessionInfo.getLastFileOpenedPath());
                Element lastLoader = new Element("LastLoader");
                lastLoader.setAttribute("name", sessionInfo.getLastLoaderUsed());
                Element lastSaver = new Element("LastSaver");
                lastSaver.setAttribute("name", sessionInfo.getLastSaverUsed());
                Element debug = new Element("Debug");
                debug.setAttribute("value", String.valueOf(sessionInfo.isDebugMode()));
                root.addContent(lastFile);
                root.addContent(lastLoader);
                root.addContent(lastSaver);
                root.addContent(debug);
                List<String> plugins = sessionInfo.getPluginsToDelete();
                for (String s : plugins) {
                    Element element = new Element("PluginToDelete");
                    element.setAttribute("path", s);
                    root.addContent(element);
                }

                XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
                FileOutputStream file = new FileOutputStream(propertiesFile);

                out.output(doc, file);
                file.flush();
                file.close();

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        EnvironmentConfiguration.getInstancia().almacenaADisco();
    }

    public SessionInfo loadPropertiesFile() {
        SessionInfo sessionInfo = new SessionInfo();
        if (propertiesFile.exists()) {
            try {
                SAXBuilder builder = new SAXBuilder();
                Document doc = builder.build(propertiesFile);
                Element root = doc.getRootElement();
                Element lastFile = root.getChild("LastFile");
                Element lastLoader = root.getChild("LastLoader");
                Element lastSaver = root.getChild("LastSaver");
                Element debug = root.getChild("Debug");
                if (lastFile != null) {
                    sessionInfo.setLastFileOpenedPath(lastFile.getAttribute("path").getValue());
                }
                if (lastLoader != null) {
                    sessionInfo.setLastLoaderUsed(lastLoader.getAttribute("name").getValue());
                }
                if (lastSaver != null) {
                    sessionInfo.setLastSaverUsed(lastSaver.getAttribute("name").getValue());
                }
                if (debug != null) {
                    sessionInfo.setDebugMode(Boolean.valueOf(debug.getAttribute("value").getValue()));
                } else {
                    sessionInfo.setDebugMode(false);
                }
            } catch (JDOMException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        EnvironmentConfiguration.getInstancia();
        return sessionInfo;
    }
}
