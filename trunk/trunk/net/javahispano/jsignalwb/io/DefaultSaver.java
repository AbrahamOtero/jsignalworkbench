package net.javahispano.jsignalwb.io;

import java.io.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.plugins.*;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import javax.swing.Icon;
import net.javahispano.jsignalwb.JSWBManager;

/**
 * {@link Saver} que utiliza JSignalWorkbench por defecto. Implementa el patrón
 * template method permitiendo que el usuario defina sólo un formato diferente
 * para el archivo que contiene las señales muestreadas y conserve el formato
 * XML de JSignalWorkbench donde se almacena la configuración del entorno,
 * anotaciones, información de los plugin y demás; o viceversa.
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Román Segador y
 *   Abraham Otero
 */
public class DefaultSaver extends SaverAdapter {
    private ArrayList<String> extensions;

    /** Crea una nueva instancia del defaultSaver.*/
    public DefaultSaver() {
        extensions = new ArrayList<String>();
        extensions.add("jsw");
    }

    public String getName() {
        return "defaultSaver";
    }

    public String getShortDescription() {
        return "Proyecto JSignalWorkBench";
    }


    /**
     * <p>Almacena en los ficheros correspondientes los valores y las
     * propiedades de las senales encontradas en signals. Implementa el patron
     * Template con lo que independientemente se pueden sobreescribir el
     * almacenamiento de datos y el de propiedades sin necesidad de modificar el
     * otro.</p>
     *
     * <p> Invoca en un primer lugar al método
     * <code>saveValues(ArrayList<Signal> signals, File f) </code> y, si este
     * método devuelve true, invoca a continuación al método <code> </code>
     * </p>.
     *
     * @param f File
     * @param sm {@link SignalManager}
     * @param pm {@link PluginManager}
     * @throws IOException
     * @return boolean
     */
    public boolean save(File f, JSWBManager jswbManager) throws
            IOException {
        SignalManager sm=jswbManager.getSignalManager();
        if(!f.exists())
            f.mkdir();

        f=new File(f,f.getName()+".txt");
        /*if(f.isDirectory())
            f=new File(f,f.getName()+".txt");*/
        ArrayList<Signal> signalsArray = new ArrayList<Signal>(sm.getSignals());
        if (saveValues(signalsArray, f)) {
            return saveXml(signalsArray, f, jswbManager);
        }
        return false;
    }

    public ArrayList<String> getAvalaibleExtension() {
        return extensions;
    }

    /**
     * <p>El objetivo es almacenar en el fichero indicado en f los valores de
     * las senales(unicamente los valores) contenidas en signals. El fichero es
     * un fichero ASCII donde cada señal se almacena con una columna en las
     * distintas columnas se separan mediante tabulaciones. </p>
     *
     * <p> El usuario puede sobrescribir este método para almacenar los valores
     * de las señales en cualquier otro formato de archivo. El método de poner a
     * devolver true sólo cuando las señales se hayan almacenado de un modo
     * correcto. </p>
     *
     * @param signals ArrayList
     * @param f File
     * @throws IOException
     * @return boolean
     */
    protected boolean saveValues(ArrayList<Signal> signals,
            File f) throws IOException {
        f.createNewFile();
        f.delete();
        float[] values = null;
        int maxSize = 0;

        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        try {
            for (Signal s : signals) {
                int arrayLenght = s.getValues().length;
                if (arrayLenght > maxSize) {
                    maxSize = arrayLenght;
                }
            }
            for (int index = 0; index < maxSize; index++) {
                for (Signal s : signals) {
                    values = s.getValues();
                    if (index < values.length) {
                        pw.print((int)values[index]);
                    }
                    pw.print("\t");
                    if (s.hasEmphasisLevel() && index < s.getEmphasisLevel().length) {
                        pw.print(s.getEmphasisLevel()[index]);
                    }
                    pw.print("\t");
                }
                pw.println();
            }
            bw.flush();
        } finally {
            pw.close();
            bw.close();
            fw.close();
        }
        return true;
    }

    /**
     * <p>El objetivo es almacenar las propiedades de las senales almacenadas en
     * signals en el fichero "f.xml", siendo f la ruta del archivo f.
     * Precondiciones--> f debe indicar el fichero que tiene almacenados los
     * valores de las senales Postcondiciones--> devolvera true en caso de
     * transcurrir normalmente y false en caso contrario. </p>
     *
     * <p> Este método es responsable de almacenar: nombres, magnitudes,
     * frecuencias de muestreo, y cualquier información relativa a las señales,
     * inclusive aquella que se emplea para representarlos en pantalla; toda la
     * información que los distintos plugin cargados en el entorno deseen
     * almacenar; toda la información de configuración de JSignalWorkbench; y
     * toda información relativa a las marcas y anotaciones. </p>
     *
     * @param signals ArrayList
     * @param f File
     * @param pm PluginManager
     * @param sm SignalManager
     * @throws IOException
     * @return boolean
     */
    protected boolean saveXml(ArrayList<Signal> signals, File f,
            JSWBManager jswbManager) throws IOException {
        PluginManager pm=jswbManager.getPluginManager();
        Element root = new Element("JSignalWorkBench");
        root.setAttribute("path", f.getName());
        for (Signal s : signals) {
            root.addContent(new XMLSignal(s));
        }
        Element annotations=new Element("Annotations");
        for(AnnotationPlugin ap:jswbManager.getSignalManager().getAllAnnotations()){
            annotations.addContent(new XMLAnnotation(ap));
        }
        root.addContent(annotations);
        root.addContent(new XMLJSignalMonitor(jswbManager.getJSMFrecuency(),jswbManager.getJSMScrollValue(),
                jswbManager.getJSMLeftPanelConfigurationString()));
        ArrayList<String> loadedPlugins=pm.getKeysOfLoadedPlugins();
        for(String key:loadedPlugins){
            if(!key.startsWith("mark:")&&!key.startsWith("annotation:")&&!key.startsWith("grid:")){
                Plugin plugin=pm.getPlugin(key);
                if(plugin!=null)
                    if(plugin.hasDataToSave()||plugin.createFile()){
                        root.addContent(new XMLPlugin(key,plugin));
                    }
            }
        }
        Document doc = new Document(root);
        /*doc.setDocType(new DocType("JSignalWorkBench",
                                   "defaultSaverLoaderDTD.dtd"));*/
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        FileOutputStream file = new FileOutputStream(new File(f.getPath() +
                ".jsw"));
        out.output(doc, file);
        file.flush();
        file.close();
        return true;

    }

    public String getDescription() {
        return "Save the default work session on JSignalWorkBench";
    }

    public String getPluginVersion() {
        return "0.5";
    }


}
