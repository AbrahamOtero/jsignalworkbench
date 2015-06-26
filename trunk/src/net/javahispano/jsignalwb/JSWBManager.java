package net.javahispano.jsignalwb;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.Action;

import net.javahispano.jsignalwb.io.IOManager;
import net.javahispano.jsignalwb.jsignalmonitor.*;
import net.javahispano.jsignalwb.jsignalmonitor.marks.JSignalMonitorAnnotation;
import net.javahispano.jsignalwb.jsignalmonitor.marks.JSignalMonitorMark;
import net.javahispano.jsignalwb.plugins.*;
import net.javahispano.jsignalwb.plugins.debug.DebugPluginsManager;
import net.javahispano.jsignalwb.plugins.framework.PluginLoadException;
import net.javahispano.jsignalwb.plugins.framework.PluginManager;
import net.javahispano.jsignalwb.ui.AlgorithmExecutionJDialog;
import net.javahispano.jsignalwb.ui.JSWBStatusBar;
import net.javahispano.jsignalwb.utilities.TimePositionConverter;
import net.javahispano.jsignalwb.ui.AlgorithmAction;
import java.awt.event.*;

/**
 * Esta clase actua a modo de fachada del framework, permitiendo acceder a la
 * mayor parte de la funcionalidad expuesta en la API de JSignalWorkbench.
 * Tambien proporciona metodos para acceder a {@link JSignalMonitor], {@link
 * SignalManager} y a {@link PluginManager}, otras tres clases fachada que se
 * encargan de exponer funcionalidad relativa a la representacion de las senhales,
 * las senhales almacenadas en el entorno y los plugins disponibles,
 * respectivamente. No obstante, esta clase proporciona metodos para las
 * acciones mas comunes que delegan su funcionalidad en otros metodos de estas
 * dos fachadas.
 *
 * @author Roman Segador y Abraham Otero
 *    Copyright 2006-2007. This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/).
 */
public class JSWBManager implements JSignalMonitorDataSource,
        SignalSizeListener {
    private static JSWBManager jswbManagerInstance;
    //private static JSWBManager jswbmanager = null;
    private static JSignalMonitor jSignalMonitor = null;
    //private static String pluginsPath;
    //private JSWBFrame jswbFrame;
    //private boolean jswbFrameMode;
    private static Window parentWindow;
    private static SignalManager signalManager;
    private static IOManager iOManager;
    private static PluginManager pluginManager;
    private ArrayList<IntervalSelectedListener> intervalSelectedListeners;
    private ArrayList<Component> jToolBarItems;
    private ArrayList<JMenu> jMenuBarItems;
    private JToolBar jToolBar;
    private JMenuBar jMenuBar;
    private SessionInfo sessionInfo;
    private PropertiesFileManager pfm;
    private JPanel jswbPanel;
    //private Font font;
    private JSWBStatusBar statusBar;


    private static Font normalFont = new java.awt.Font("Tahoma", Font.BOLD, 12);
    private static Font smallFont = new java.awt.Font("Tahoma", Font.BOLD, 11);
    private static Font bigFont = new java.awt.Font("Tahoma", Font.BOLD, 13);
    private static Color fontColor = Color.BLUE;
    private List<SessionListener> sessionListenetList = new LinkedList<SessionListener>();
    //Si true borra las senhales actualmente cargadas al cargar las siguientes.
    private static boolean deleteSignalsInNextLoad = true;
    static {

    }

    private JSWBManager() {
        this(true);
    }

    /** Creates a new instance of JSWBManager */
    private JSWBManager(boolean develop) {
        pfm = new PropertiesFileManager();
        pfm.deleteUninstallPlugins();
        sessionInfo = pfm.loadPropertiesFile();

        intervalSelectedListeners = new ArrayList<IntervalSelectedListener>();
        LookAndFeelConfiguration l = new LookAndFeelConfiguration();
        l.setFontColor(JSWBManager.getFontColor());
        l.setSmallFont(JSWBManager.getSmallFont());
        l.setMediumFont(JSWBManager.getNormalFont());
        l.setLargeFont(JSWBManager.getBigFont());
        jSignalMonitor = new JSignalMonitor(this);
        jswbPanel = new JPanel();
        jswbPanel.setLayout(new BorderLayout());
        jswbPanel.add(jSignalMonitor.getJSignalMonitorPanel(), BorderLayout.CENTER);
        statusBar = new JSWBStatusBar(sessionInfo);
        //setLowerComponent(statusBar);
        signalManager = new SignalManager(jSignalMonitor);
        signalManager.addListener(this);
        pluginManager = new PluginManager();
        iOManager = new IOManager(this);
        //jswbFrameMode=false;
        parentWindow = null;
        jToolBarItems = null;
        jMenuBarItems = null;
        jMenuBar = null;
        jToolBar = null;
        //font=new Font(Font.DIALOG,Font.BOLD,12);
        if (develop || sessionInfo.isDebugMode() || true) {
            DebugPluginsManager.registerDebugPlugins(pluginManager);
        }

    }

    public static JSWBManager getJSWBManagerInstance() {
        if (jswbManagerInstance == null) {
            jswbManagerInstance = new JSWBManager();
        }
        return jswbManagerInstance;
    }

    public static JSWBManager getJSWBManagerInstance(boolean develop) {
        if (jswbManagerInstance != null) {
            throw new RuntimeException("A JSWBManager is already created." +
                                       " Try to request a non develop instance");
        }
        jswbManagerInstance = new JSWBManager(develop);
        return jswbManagerInstance;
    }

    public void printSignals(boolean everything, int orientation) {
        if (everything) {
            jSignalMonitor.printAll(orientation);
        } else {
            jSignalMonitor.printSignals(orientation);
        }

    }

    public void savePropertiesFile() {
        pfm.savePropertiesFile(sessionInfo);
    }

    public void loadPreviouslyFile() {
        String path = sessionInfo.getLastFileOpenedPath();
        String loader = sessionInfo.getLastLoaderUsed();
        if (path != null && loader != null && !path.equals("") && !loader.equals("")) {
            File file = new File(path);
            if (file.exists()) {
                loadChannels(loader, file);
            }
        }
    }

    /*public void setJSWBFrame(JSWBFrame jswbFrame){
        this.jswbFrame=jswbFrame;
        this.jswbFrameMode=true;
        jToolBarItems=new ArrayList();
        initJFrame();
        initJToolBar();
        refreshJToolBar();

         }*/

    public void setParentWindow(Window parentWindow) {
        this.parentWindow = parentWindow;
    }

    public static void setBigFont(Font bigFont) {
        JSWBManager.bigFont = bigFont;
    }

    public static void setSmallFont(Font smallFont) {
        JSWBManager.smallFont = smallFont;
    }

    public static void setNormalFont(Font normalFont) {
        JSWBManager.normalFont = normalFont;
    }

    public void setDeleteSignalsInNextLoad(boolean deleteSignalsInNextLoad) {
        this.deleteSignalsInNextLoad = deleteSignalsInNextLoad;
    }

    public JPanel getStatusBarPanel() {
        return statusBar;
    }

    public void addJMenuBarItem(JMenu jMenu) {
        if (jMenuBarItems == null) {
            jMenuBarItems = new ArrayList<JMenu>();
        }
        jMenuBarItems.add(jMenu);
        refreshJMenuBar();
    }

    /**
     * Anhade un {@link Action} a la barra de herramientas de la ventana
     * principal.
     *
     * @param action Action a anhadir.
     * @todo comprobar que funciona correctamente.
     * @todo (Roman)Proporcionar metodos para eliminar acciones y para listar
     *   todas las acciones que tiene anhadidas en un determinado momento la
     *   barra.
     */
    public void addJToolBarButton(Action action) {
        if (jToolBarItems == null) {
            jToolBarItems = new ArrayList<Component>();
        }
        JButton button = new JButton(action);
        if (action instanceof AlgorithmAction) {


        AlgorithmAction a = (AlgorithmAction)action;
        if (a.getAlgorithmName().endsWith("Interpolar en hueco")) {
            button.setMnemonic(KeyEvent.VK_A);

        }
        }
        button.setText("");
        jToolBarItems.add(button);
        refreshJToolBar();

    }

    public void addJToolBarComponent(Component comp) {
        if (jToolBarItems == null) {
            jToolBarItems = new ArrayList<Component>();
        }
        jToolBarItems.add(comp);
        refreshJToolBar();
    }

    public void addJToolBarSeparator() {
        if (jToolBarItems == null) {
            jToolBarItems = new ArrayList<Component>();
        }
        JSeparator sep = new JSeparator(JSeparator.VERTICAL);
        sep.setMaximumSize(new Dimension(10, 1000));
        jToolBarItems.add(sep);

    }

    public void removeJToolBarComponent(JComponent comp) {
        if (jToolBarItems != null) {
            jToolBarItems.remove(comp);
        }
        refreshJToolBar();
    }

    public void clearJToolBar() {
        if (jToolBarItems != null) {
            jToolBarItems.clear();
        }
        refreshJToolBar();
    }

    public ArrayList<Component> getJToolBarItems() {
        return jToolBarItems;
    }

    public JToolBar getJToolBar() {
        if (jToolBar == null) {
            jToolBar = new JToolBar("JSWBManager ToolBar");
        }
        refreshJToolBar();
        return jToolBar;
    }

    public JMenuBar getJMenuBar() {
        if (jMenuBar == null) {
            jMenuBar = new JMenuBar();
        }
        refreshJMenuBar();
        return jMenuBar;
    }

    public boolean hasLeftComponent() {
        Component comp = getComponent(BorderLayout.WEST);
        return (comp != null);
    }

    public boolean hasRightComponent() {
        Component comp = getComponent(BorderLayout.EAST);
        return (comp != null);
    }

    public boolean hasUpperComponent() {
        Component comp = getComponent(BorderLayout.NORTH);
        return (comp != null);
    }

    public boolean hasLowerComponent() {
        Component comp = getComponent(BorderLayout.SOUTH);
        return (comp != null);
    }

    public Component removeLeftComponent() {
        Component comp = getComponent(BorderLayout.WEST);
        if (comp != null) {
            jswbPanel.remove(comp);
            launchJSWBPanelValidate();
        }
        return comp;
    }

    public Component removeRightComponent() {
        Component comp = getComponent(BorderLayout.EAST);
        if (comp != null) {
            jswbPanel.remove(comp);
            launchJSWBPanelValidate();
        }
        return comp;
    }

    public Component removeUpperComponent() {
        Component comp = getComponent(BorderLayout.NORTH);
        if (comp != null) {
            jswbPanel.remove(comp);
            launchJSWBPanelValidate();
        }
        return comp;
    }

    public Component removeLowerComponent() {
        Component comp = getComponent(BorderLayout.SOUTH);
        if (comp != null) {
            jswbPanel.remove(comp);
            launchJSWBPanelValidate();
        }
        return comp;
    }

    /**
     * Anhade el componente que se le pasa como parametro a la izquierda de
     * {@link JSignalMonitor}. Provoca una validacion (validate) de la ventana
     * principal. Si ya habia algun componente en esa posicion sera eliminado y
     * se anhadira el componente nuevo.
     *
     * @param panel componente a anhadir.
     * @todo comprobar que funciona correctamente.
     */
    public void setLeftComponent(JComponent component) {
        Component comp = getComponent(BorderLayout.WEST);
        if (comp != null) {
            jswbPanel.remove(comp);
        }
        jswbPanel.add(component, BorderLayout.WEST);
        launchJSWBPanelValidate();
    }


    /**
     * Anhade el componente que se le pasa como parametro a la derecha de
     * {@link JSignalMonitor}. Provoca una validacion (validate) de la ventana
     * principal. Si ya habia algun componente en esa posicion sera eliminado y
     * se anhadira el componente nuevo.
     *
     * @param panel componente a anhadir.
     * @todo comprobar que funciona correctamente.
     */
    public void setRightComponent(JComponent component) {
        Component comp = getComponent(BorderLayout.EAST);
        if (comp != null) {
            jswbPanel.remove(comp);
        }
        jswbPanel.add(component, BorderLayout.EAST);
        launchJSWBPanelValidate();
    }

    /**
     * Anhade el componente que se le pasa como parametro encima de
     * {@link JSignalMonitor}. Provoca una validacion (validate) de la ventana
     * principal. Si ya habia algun componente en esa posicion sera eliminado y
     * se anhadira el componente nuevo.
     *
     * @param panel componente a anhadir.
     * @todo comprobar que funciona correctamente.
     */
    public void setUpperComponent(JComponent component) {
        Component comp = getComponent(BorderLayout.NORTH);
        if (comp != null) {
            jswbPanel.remove(comp);
        }
        jswbPanel.add(component, BorderLayout.NORTH);
        launchJSWBPanelValidate();
    }

    /**
     * Anhade el componente que se le pasa como parametro debajo de
     * {@link JSignalMonitor}. Provoca una validacion (validate) de la ventana
     * principal. Si ya habia algun componente en esa posicion sera eliminado y
     * se anhadira el componente nuevo.
     *
     * @param panel componente a anhadir.
     * @todo comprobar que funciona correctamente.
     */
    public void setLowerComponent(JComponent component) {
        Component comp = getComponent(BorderLayout.SOUTH);
        if (comp != null) {
            jswbPanel.remove(comp);
        }
        jswbPanel.add(component, BorderLayout.SOUTH);
        launchJSWBPanelValidate();
    }

    /**
     * Devuelve el {@link JSignalMonitor}que est\u2193 empleando la aplicacion.
     *
     * @return JSignalMonitor
     */
    public static JSignalMonitor getJSignalMonitor() {
        return jSignalMonitor;
    }

    /**
     * Devuelve un HashMap cuyas claves son los diferentes tipos de plugins que
     * existen en el entorno y el objeto asociado con cada clave una lista de
     * todos los plugins de ese tipo asociados con dicha clave. Si no existiesen
     * plugins de algun tipo la clave correspondiente no estaria en el HashMap.
     *
     * @return HashMap
     */
    public HashMap<String, ArrayList<String>> getPluginNames() {
        return pluginManager.getRegisteredPlugins();
    }

    /**
     * Indica si debe o no mostrarse una senhal.
     *
     * @param signalName nombre de la senhal.
     * @param visible true si debe mostrarse, false en caso contrario.
     * @return true si la accion se realizo correctamente, false en caso
     *   contrario.
     */
    public boolean setChannelVisible(String signalName, boolean visible) {
        Signal s = getSignalManager().getSignal(signalName);
        if (s != null) {
            s.getProperties().setVisible(visible);

            if (!visible) {
                return jSignalMonitor.removeChannel(signalName);
            } else {
                boolean flag = jSignalMonitor.addChannel(s.getName(), s.getProperties());
                //if(s.hasOwnGrid())
                jSignalMonitor.setChannelGrid(s.getName(), s.getGrid());
                return flag;
            }
        }
        return false;

    }

    public void addSignalMark(String signalName, MarkPlugin mark) throws SignalNotFoundException {
        signalManager.addSignalMark(signalName, mark);
        setSaved(false);
    }

    public void addAnnotation(AnnotationPlugin annotation) {
        signalManager.addAnnotation(annotation);
        setSaved(false);
    }

    /**
     * Elimina por completo la senhal pasada como argumento.
     *
     * @param signalName nombre de la senhal.
     * @throws {@link SignalNotFoundException} si la senhal a eliminar no
     *   existe.
     */
    public void removeSignal(String signalName) throws SignalNotFoundException {
        getSignalManager().removeSignal(signalName);
        setSaved(false);
    }

    public void removeSignalMark(String signalName, MarkPlugin mark) throws SignalNotFoundException {
        signalManager.removeSignalMark(signalName, mark);
        setSaved(false);
    }

    public void removeAnnotation(AnnotationPlugin annotation) {
        signalManager.removeAnnotation(annotation);
        setSaved(false);
    }

    public void removeAllSignalMarks(String signalName) throws SignalNotFoundException {
        signalManager.removeAllSignalMarks(signalName);
        setSaved(false);
    }

    public void removeAllMarks() {
        signalManager.removeAllMarks();
        setSaved(false);
    }

    public void removeAllAnnotations() {
        signalManager.removeAllAnnotations();
        setSaved(false);
    }

    public List<MarkPlugin> getAllSignalMarks(String signalName) throws SignalNotFoundException {
        return signalManager.getAllSignalMarks(signalName);
    }

    public List<AnnotationPlugin> getAllAnnotations() {
        return signalManager.getAllAnnotations();
    }

    public float getChannelValueAtTime(String signalName, long time) {
        Signal signal = signalManager.getSignal(signalName);
        if (signal != null) {
            int pos = TimePositionConverter.timeToPosition(signal.getProperties().
                    getStartTime(), time, signal.getProperties().getDataRate());
            if (pos >= 0 && pos < signal.getValues().length) {
                return signal.getValues()[pos];
            }
            //si la senhal no esta definida en ese instante
            return Float.NaN;
        }
        throw new SignalNotFoundException(signalName, "Attempt of obtaining the value of a non existent signal");
    }

    public List<JSignalMonitorMark> getChannelMark(String signalName, long firstValue,
            long lastValue) {
        ArrayList<JSignalMonitorMark> temp = new ArrayList<JSignalMonitorMark>();
        for (MarkPlugin mp : signalManager.getSignalMarks(signalName, firstValue, lastValue)) {
            temp.add(mp);
        }
        return temp;
    }

    public List<JSignalMonitorAnnotation> getAnnotations(long firstValue, long lastValue) {
        ArrayList<JSignalMonitorAnnotation> temp = new ArrayList<JSignalMonitorAnnotation>();
        for (AnnotationPlugin mp : signalManager.getAnnotations(firstValue, lastValue)) {
            temp.add(mp);
        }
        return temp;
    }

    public void notifyMarkAdded(String kindOfMark, String signalName, long time) {
        /*JPopupMenu popup =new MarksPopupMenu(this,signalName,time);
                 Point p=MouseInfo.getPointerInfo().getLocation();
                 popup.show(null,(int)p.getX(),(int)p.getY());*/
        MarkPlugin mp = pluginManager.createMarkPlugin(kindOfMark);
        mp.setMarkTime(time);
        signalManager.addSignalMark(signalName, mp);
        refreshJSM(true);
        mp.showMarkInfo(getParentWindow());
        setSaved(false);
    }

    public void notifyMarkAdded(String kindOfMark, String signalName, long startTime, long endTime) {
        /*JPopupMenu popup =new MarksPopupMenu(this,signalName,startTime,endTime);
                 Point p=MouseInfo.getPointerInfo().getLocation();
                 popup.show(null,(int)p.getX(),(int)p.getY());*/
        MarkPlugin mp = pluginManager.createMarkPlugin(kindOfMark);
        mp.setMarkTime(startTime);
        mp.setEndTime(endTime);
        signalManager.addSignalMark(signalName, mp);
        refreshJSM(true);
        mp.showMarkInfo(getParentWindow());
        setSaved(false);
    }

    public void notifyAnnotationAdded(String kindOfAnnotation, long time) {
        /*JPopupMenu popup =new MarksPopupMenu(this,signalName,time);
                 Point p=MouseInfo.getPointerInfo().getLocation();
                 popup.show(null,(int)p.getX(),(int)p.getY());*/
        AnnotationPlugin ap = pluginManager.createAnnotationPlugin(kindOfAnnotation);
        ap.setAnnotationTime(time);
        ap.setJSWBManager(this);
        signalManager.addAnnotation(ap);
        refreshJSM(false);
        ap.showMarkInfo(getParentWindow());
        setSaved(false);
    }

    public void notifyAnnotationAdded(String kindOfAnnotation, long startTime, long endTime) {
        /*JPopupMenu popup =new MarksPopupMenu(this,signalName,startTime,endTime);
                 Point p=MouseInfo.getPointerInfo().getLocation();
                 popup.show(null,(int)p.getX(),(int)p.getY());*/
        AnnotationPlugin ap = pluginManager.createAnnotationPlugin(kindOfAnnotation);

        ap.setAnnotationTime(startTime);
        ap.setEndTime(endTime);
        ap.setJSWBManager(this);
        signalManager.addAnnotation(ap);
        refreshJSM(false);
        ap.showMarkInfo(getParentWindow());
        setSaved(false);
    }

    public List<String> getAvailableKindsOfInstantMarks() {
        ArrayList<String> temp = new ArrayList<String>();
        for (String mark : pluginManager.getRegisteredMarks()) {
            if (!pluginManager.createMarkPlugin(mark).isInterval()) {
                temp.add(mark);
            }
        }
        return temp;
    }

    public List<String> getAvailableKindsOfIntervalMarks() {
        ArrayList<String> temp = new ArrayList<String>();
        for (String mark : pluginManager.getRegisteredMarks()) {
            if (pluginManager.createMarkPlugin(mark).isInterval()) {
                temp.add(mark);
            }
        }
        return temp;
    }

    public List<String> getAvailableKindsOfInstantAnnotations() {
        ArrayList<String> temp = new ArrayList<String>();
        for (String annotation : pluginManager.getRegisteredAnnotations()) {
            if (!pluginManager.createAnnotationPlugin(annotation).isInterval()) {
                temp.add(annotation);
            }
        }
        return temp;
    }

    public List<String> getAvailableKindsOfIntervalAnnotations() {
        ArrayList<String> temp = new ArrayList<String>();
        for (String annotation : pluginManager.getRegisteredAnnotations()) {
            if (pluginManager.createAnnotationPlugin(annotation).isInterval()) {
                temp.add(annotation);
            }
        }
        return temp;
    }


    public ArrayList<String> getAvailableCategoriesOfAnnotations() {
        if (signalManager != null) {
            return signalManager.getAnnotationsCategories();
        } else {
            return new ArrayList<String>();
        }
    }

    public float[] getChannelData(String signalName, long firstValue,
                                  long lastValue) {
        Signal signal = signalManager.getSignal(signalName);
        if (signal != null) {
            int pos1 = TimePositionConverter.timeToPosition(signal.
                    getProperties().getStartTime(), firstValue,
                    signal.getProperties().getDataRate());
            int pos2 = TimePositionConverter.timeToPosition(signal.
                    getProperties().getStartTime(), lastValue,
                    signal.getProperties().getDataRate());

            return getChannelData(signal.getValues(), pos1, pos2, signal.getProperties().getAbscissaValue());
        }
        throw new SignalNotFoundException(signalName, "Attempt of obtaining values of a non existent signal");

    }

    public short[] getSignalEmphasisLevels(String signalName, long firstValue,
                                           long lastValue) {

        Signal signal = signalManager.getSignal(signalName);
        if (signal == null) {
            throw new SignalNotFoundException(signalName,
                                              "Attempt of obtaining emphasis levels of a non existent signal");
        }
        int pos1 = TimePositionConverter.timeToPosition(signal.getProperties().
                getStartTime(), firstValue, signal.getProperties().getDataRate());
        int pos2 = TimePositionConverter.timeToPosition(signal.getProperties().
                getStartTime(), lastValue, signal.getProperties().getDataRate());
        return getChannelColors(signal.getEmphasisLevel(), pos1, pos2);
    }

    public void notifyIntervalSelection(String channelName, long startTime,
                                        long endTime) {
        fireIntervalSelectedEvent(new IntervalSelectedEvent(channelName,
                startTime,
                endTime));

    }


    /**
     * Anhade un {@link IntervalSelectedListener} a la lista de listeners que
     * desean ser notificados de eventos de seleccion. Ademas indica a {@link
     * JSignalMonitor} que debe seleccionar un intervalo.
     *
     * @param listener IntervalSelectedListener
     */
    public void selectInterval(IntervalSelectedListener listener) {
        intervalSelectedListeners.add(listener);
        jSignalMonitor.setSelectIntervalMode(true);
    }


    /**
     * No disenhado como parte del API.
     * @todo (Abraham) Si lo considero una parte del API. Este metodo es el que
     * se encarga de mostrar la interfaz de seleccion de senhales previa a la ejecucion
     * de un algorithm. Si alguien desea crearse un acceso directo en, por ejemplo,
     * un panel nuevo anhadido a la aplicacion, para ejecutar determinado plugin,
     * debera llamar a este metodo. Sin embargo tambien es posible que le forcemos
     * a que utilice el AlgorithmAction para lanzarlo, con lo que ya no necesitaria
     * este metodo. Mi opinion es q deberia formar parte del API.
     */
    public boolean showPluginExecution(String pluginType, String name) {
        if (pluginType.equals("algorithm")) {
            pluginManager.getAlgorithm(name).launchExecutionGUI(this);
            return true;
        } else if (pluginType.equals("generic")) {
            pluginManager.getGeneric(name).launch(this);
            return true;
        }
        return false;
    }

    /**
     * Muestra el cuadro de dialogo de configuracion de un plugin.
     *
     * @param pluginType tipo del plugin.
     * @param name nombre del plugin.
     * @return True si la accion concluyo con exito, false en caso contrario.
     */
    public boolean showPluginConfiguration(String pluginType, String name) {
        Plugin plug = pluginManager.getPlugin(pluginType + ":" + name);
        if (plug != null && plug.hasOwnConfigureGUI()) {
            plug.launchConfigureGUI(this);
            setSaved(false);
            return true;
        } else {
            JOptionPane.showMessageDialog(getParentWindow(),
                                          "This Plugin(" + name +
                                          ") hasn't configuration window");
            return false;
        }

    }

    /**
     * No disenhado como parte del API.
     * @todo (Abraham) Mismo caso que ShowAlgorithmExecution;
     */
    public boolean showAlgorithmResults(String algorithmName) {
        Algorithm alg = pluginManager.getAlgorithm(algorithmName);
        if (alg != null && alg.hasResultsGUI()) {
            alg.launchResultsGUI(this);
            return true;
        } else {
            JOptionPane.showMessageDialog(getParentWindow(), "This algorithm" +
                                          " hasn't results window");
            return true;
        }

    }

    /**
     * No disenhado como parte del API.
     * //Documentacion en caso de ser API
     *  Metodo que lanzara la ejecucion del algoritmo que recibe como parametro
     *  sobre las senales que recibe como segundo parametro(Nombre). La ejecucion
     *  del algoritmo se hara en un hilo de ejecucion distinto al de la interfaz
     *  grafica y principal de la aplicacion. Ver {@link AlgorithmRunner},{@link
     *  AlgorithmExecutionJDialog}
     *  @param {@link Algorithm} alg Algoritmo sobre el que se lanzara la ejecucion
     *  @param {@link Enumeration} signals Enumeracion del nombre de las senales sobre
     *  las cuales se quiere ejecutar el algoritmo.
     *  @return true si el la ejecucion termina satisfactoriamente
     *          false si la ejecucion no termina satisfactoriamente.
     *  // fin de la documentacion en caso de ser API
     * @todo (Abraham) Casi similar al de showPluginExecution. Creo que deberia formar
     *       parte de la API, ya que alguien puede querer ejecutar un algorithm
     *       sin necesidad de tener que seleccionar senheles a modo de acceso. Por
     *       ejemplo para ejecutar un algorithm para todas las senhales completas le
     *       vale con llamar a este metodo con el Algorithm y una llamada a sm.getSignalsNames().
     *       No obstante si que es cierto que si incluimos estos metodos en la API puede llevar
     *       a confusion y que la gente los utilice directamente sin pasar por el
     *       showPluginExecution. Aunque creo que con documentarlo bien serviria.
     */
    public boolean runAlgorithm(Algorithm alg, Enumeration signals) {
        ArrayList<SignalIntervalProperties> intervals =
                new ArrayList<SignalIntervalProperties>();
        while (signals.hasMoreElements()) {
            intervals.add(new SignalIntervalProperties(
                    signalManager.getSignal(signals.nextElement().toString())));
        }
        runAlgorithmP(alg, intervals);
        return true;
    }


    /**
     * //Documentacion en caso de ser API
     *  Metodo que lanzara la ejecucion del algoritmo que recibe como parametro
     *  sobre los intervalos que recibe como segundo parametro.ver {@link IntervalSelectedEvent}
     *  La ejecucion del algoritmo se hara en un hilo de ejecucion distinto al de la interfaz
     *  grafica y principal de la aplicacion. Ver {@link AlgorithmRunner},{@link
     *  AlgorithmExecutionJDialog}
     *  @param {@link Algorithm} alg Algoritmo sobre el que se lanzara la ejecucion
     *  @param {@link ArrayList}<IntervalSelectedEvent> interval Enumeracion de los intervalos sobre
     *  los cuales se quiere ejecutar el algoritmo.
     *  @return true si el la ejecucion termina satisfactoriamente
     *          false si la ejecucion no termina satisfactoriamente.
     *  // fin de la documentacion en caso de ser API
     */
    public boolean runAlgorithm(Algorithm alg,
                                ArrayList<IntervalSelectedEvent> intervals) {
        ArrayList<SignalIntervalProperties>
                signals = new ArrayList<SignalIntervalProperties>();
        for (IntervalSelectedEvent interval : intervals) {
            Signal signal = signalManager.getSignal(interval.getChannelName());
            if (interval.isFullSignal()) {
                signals.add(new SignalIntervalProperties(signal));
            } else {
                signals.add(new SignalIntervalProperties(
                        signal,
                        interval.getStartTime(), interval.getEndTime(),
                        TimePositionConverter.timeToPosition(signal.getStart(),
                        interval.getStartTime(), signal.getSRate()),
                        TimePositionConverter.timeToPosition(signal.getStart(),
                        interval.getEndTime(), signal.getSRate())));
            }
        }
        runAlgorithmP(alg, signals);
        return true;
    }


    /**
     * Metodo que se encarga de lanzar definitivamente el algoritmo a traves de
     * {@link AlgorithmExecutionJDialog}
     */
    private void runAlgorithmP(Algorithm alg, ArrayList<SignalIntervalProperties> intervals) {
        new AlgorithmExecutionJDialog(alg, intervals, this);
        setSaved(false);
        refreshJSM(false);
        if (alg.hasResultsGUI()) {
            alg.launchResultsGUI(this);
        }
    }


    /**
     * Devuelve el {@link PluginManager}.
     *
     * @param pluginsPath Directorio donde el {@link PluginManager} debe buscar
     *   los plugins.
     * @return PluginManager
     */
    public static PluginManager getPluginManager(String pluginsPath) {
        pluginManager.setDefaultDirectory(pluginsPath);
        return pluginManager;
    }

    /**
     * Devuelve el {@link PluginManager}.
     *
     * @return PluginManager
     */
    public static PluginManager getPluginManager() {
        return pluginManager;
    }

    /**
     * Devuelve el {@link IOManager}.
     *
     * @return IOManager
     */
    public static IOManager getIOManager() {
        return iOManager;
    }

    /**
     * No disenhado como parte del API.
     */
    public boolean loadChannels(String loaderName, File f) {
        try {
            if (!iOManager.loadSignals(f, loaderName, deleteSignalsInNextLoad)) {
                JOptionPane.showMessageDialog(getParentWindow(), "Alguna senhal no se ha cargado");
            }
            sessionInfo.setLastFileOpenedPath(f.getAbsolutePath());
            sessionInfo.setLastLoaderUsed(loaderName);
            setSaved(true);
            this.fireSessionEvent(new SessionEvent(true, true));
        } catch (PluginLoadException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(getParentWindow(), ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(getParentWindow(), "Error al cargar el fichero:\n" + ex.getMessage());
        }
        return true;
    }

    public boolean saveChannels() {
        try {
            String saverName = sessionInfo.getLastSaverUsed();
            String path = sessionInfo.getLastFileOpenedPath();
            if (saverName != null && path != null && !saverName.equals("") && !path.equals("")) {
                File file = new File(path);
                if (!file.exists()) {
                    file.createNewFile();
                }
                return saveChannelsAs(saverName, file, false);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(getParentWindow(), ex.getMessage());
        }
        return false;
    }

    public boolean saveChannelsAs(String saverName, File f, boolean newSession) {
        try {
            iOManager.saveSignals(f, saverName);
            SessionEvent sesionEvt = new SessionEvent(newSession, true);
            if (newSession) { //la llamada viene de "Save as"
                sesionEvt.setSaveAs(true);
            }
            this.fireSessionEvent(sesionEvt);
            sessionInfo.setLastFileOpenedPath(f.getAbsolutePath());
            sessionInfo.setLastSaverUsed(saverName);
            setSaved(true);
        } catch (PluginLoadException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(getParentWindow(), ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(getParentWindow(), "Error al salvar el fichero:\n" +
                                          ex.getMessage());
        }
        return true;
    }

    /**
     * Devuelve el {@link SignalManager}.
     *
     * @return SignalManager
     */
    public static SignalManager getSignalManager() {
        return signalManager;
    }

    /**
     * No disenhado como parte del API.
     */
    public void signalSizeActionPerformed(SignalSizeEvent evt) {
        Signal s = evt.getSignal();

        if (evt.isSignalAdded()) {
            if (s.getProperties().isVisible()) {
                setChannelVisible(s.getName(), true);
                //jSignalMonitor.addChannel(s.getName(), s.getProperties());
            }
        } else {
            if (evt.isSignalRemoved()) {
                jSignalMonitor.removeChannel(s.getName());
            } else {
                if (evt.isSignalsReset()) {
                    jSignalMonitor.removeAllChannels();
                }
            }
        }
        jSignalMonitor.repaintAll();
    }

    /**
     * Establece el valor maximo y minimo a mostrar en pantalla para el eje de
     * las ordenadas para la senhal indicada.
     *
     * @param signalName Senhal a la cual va a afectar el nuevo rango.
     * @param minValue Valor minimo del eje de ordenadas.
     * @param maxValue Valor maximo del eje de ordenadas.
     * @param abscissaValue valor de la senhal en el q se representara la abscissa.
     * @throws {@link SignalNotFoundException} si no hay una senhal con el
     *   nombre indicado cargada y el entorno.
     */
    public void setSignalVisibleRange(String signalName,
                                      float abscissaValue, float maxValue) {
        signalManager.setSignalVisibleRange(signalName, abscissaValue, maxValue);
    }

    /**
     * Establece el valor maximo y minimo a mostrar en pantalla para el eje de
     * las ordenadas para todas las senhales.
     *
     * @param minValue Valor minimo del eje de ordenadas.
     * @param maxValue Valor maximo del eje de ordenadas.
     * @param abscissaValue valor de la senhal en el q se representara la abscissa.
     * @throws {@link SignalNotFoundException} si no hay una senhal con el
     *   nombre indicado cargada y el entorno.
     */
    public boolean setSignalVisibleRange(float abscissaValue, float maxValue) {
        return signalManager.setSignalVisibleRange(abscissaValue, maxValue);
    }

    /**
     * Establece como valores maximo y minimo del eje de ordenadas de la senhal que
     * se le pasa como argumento el valor maximo y minimo de la senhal.
     *
     * @param signalName nombre de la senhal.
     * @throws {@link SignalNotFoundException} si no hay una senhal con el
     *   nombre indicado cargada y el entorno.
     */
    public void adjustVisibleRange(String signalName) {
        adjustVisibleRange(signalName, 1);
    }

    public void adjustVisibleRange(String signalName, float range) {
        signalManager.adjustVisibleRange(signalName, range);
    }

    /**
     * Establece como valores maximo y minimo del eje de abcisas de todas las
     * senhales el valor maximo y minimo de cada senhal.
     */
    public void adjustVisibleRange() {
        adjustVisibleRange(1);
    }

    public void adjustVisibleRange(float range) {
        signalManager.adjustVisibleRange(range);
    }

    /**
     * Devuelve el contenedor del sistema operativo sobre el cual se esta
     * ejecutando JSignalWorkbench. Es util para crear cuadros de dialogo
     * modales.
     *
     * @return Window
     */
    public static Window getParentWindow() {
        return parentWindow;
    }

    /** Este metodo se encarga de actualizar y repintar el {@Link JSignalMonitor}. Si el
     *  parametro es true solo se actualizaran los canales y si es false se
     *  actualizaran todos los componentes.
     *  @param boolean true para actualizar todos los componentes, false para
     *  actualizar unicamente los canales
     */
    public void refreshJSM(boolean onlyChannels) {
        if (!onlyChannels) {
            jSignalMonitor.repaintAll();
        } else {
            jSignalMonitor.repaintChannels();
        }
    }

    /**
     * modifica el tiempo base del scroll y, por tanto, el tiempo base que
     * emplea {@Link JSignalMonitor}. No se representaran instantes de tiempo anteriores
     * a este instante.
     *
     * @param baseTime tiempo base del scroll medido en milisegundos.  Ver
     *   {@link TimePositionConverter}.
     */
    public void setJSMScrollBaseTime(long baseTime) {
        jSignalMonitor.setScrollBaseTime(baseTime);
    }

    /**
     * Modifica el tiempo maximo que sera visualizado por {@Link JSignalMonitor}. Para
     * {@Link JSignalMonitor} este instante del tiempo es el final del registro de
     * senhal y no se mostraran instantes de tiempo posteriores a el.
     *
     * @param maxTime Instante de tiempo medido en milisegundos.
     * Ver {@link TimePositionConverter}.
     */
    public void setJSMMaxTime(long maxTime) {
        jSignalMonitor.setEndTime(maxTime);
    }

    /**
     * Modifica el instante de tiempo representado por el scroll de {@Link JSignalMonitor}.
     * Debe estar contenido entre los limites del scroll.ver {@link getJSMScrollBaseTime()} y
     * {@link getJSMMaxTime()}.
     *
     * @param scrollValue instante de tiempo al cual va a pasar a apuntar el
     *   scroll metido en milisegundos.  Ver {@link TimePositionConverter}.
     */
    public void setJSMScrollValue(long scrollValue) {
        jSignalMonitor.setScrollValue(scrollValue);
    }

    /**
     * Modifica la frecuencia de representacion de {@Link JSignalMonitor}. Aunque las
     * distintas senhales que este visualizando {@Link JSignalMonitor} tengan distinta
     * frecuencia un corte vertical sobre todos los ejes temporales que
     * representa {@Link JSignalMonitor} se corresponde siempre a un mismo instante del
     * tiempo. Para ello, {@Link JSignalMonitor} emplea una frecuencia ficticia de
     * representacion, que podria coincidir con la de alguna o todas las
     * senhales. Dicha frecuencia ficticia de representacion se modifica a traves
     * de este metodo.
     *
     * @param frecuency Frecuencia de representacion de {@Link JSignalMonitor}.
     */
    public void setJSMFrecuency(float frecuency) {
        jSignalMonitor.setFrecuency(frecuency);
    }

    public void setJSMIgnoreRepaintMode(boolean value) {
        jSignalMonitor.setIgnoreRepaint(value);
    }

    /**
     * Devuelve el tiempo base del scroll representado en milisegundos de {@Link JSignalMonitor}
     * . Ver {@link TimePositionConverter}.
     *
     * @return long
     */
    public long getJSMScrollBaseTime() {
        return jSignalMonitor.getScrollBaseTime();
    }

    /**
     * Devuelve el instante de fin del registro representado en milisegundos de {@Link JSignalMonitor}.
     * Ver {@link TimePositionConverter}.
     *
     * @return long
     */
    public long getJSMMaxTime() {
        return jSignalMonitor.getEndTime();
    }

    /**
     * Devuelve el instante de tiempo al cual esta apuntando el scroll
     * representado en milisegundos en {@Link JSignalMonitor}. Ver {@link TimePositionConverter}.
     *
     * @return long
     */
    public long getJSMScrollValue() {
        return jSignalMonitor.getScrollValue();
    }

    /**
     * Devuelve la frecuencia ficticia de representacion de {@Link JSignalMonitor}. Ver
     *  setFrecuency()
     *
     * @return float
     */
    public float getJSMFrecuency() {
        return jSignalMonitor.getFrecuency();
    }

    /**
     * Devuelve el alto, medido en pixeles, de cada uno de los canales representados
     * en {@Link JSignalMonitor}.
     *
     * @return int
     */
    public int getJSMChannelHeight() {
        return jSignalMonitor.getChannelHeight();
    }

    public String getJSMLeftPanelConfigurationString() {
        boolean prop[] = jSignalMonitor.getJSMProperties().getLeftPanelConfiguration().getProperties();
        String temp = "" + prop.length;
        for (int index = 0; index < prop.length; index++) {
            temp = temp + "," + String.valueOf(prop[index]);
        }
        return temp;
    }

    public void setJSMLeftPanelConfigurationString(String leftPanelConfig) {
        int numberArg = Integer.parseInt(leftPanelConfig.substring(0, leftPanelConfig.indexOf(",")));
        boolean args[] = new boolean[numberArg];

        for (int index = 0; index < numberArg - 1; index++) {
            leftPanelConfig = leftPanelConfig.substring(leftPanelConfig.indexOf(",") + 1);
            args[index] = Boolean.parseBoolean(leftPanelConfig.substring(0, leftPanelConfig.indexOf(",")));
        }
        leftPanelConfig = leftPanelConfig.substring(leftPanelConfig.indexOf(",") + 1);
        args[numberArg - 1] = Boolean.parseBoolean(leftPanelConfig);
        jSignalMonitor.getJSMProperties().getLeftPanelConfiguration().setProperties(args);
    }

    /**
     * Devuelve el panel en el cual se representa {@link JSignalMonitor}
     * @return JPanel
     */
    public JPanel getJSMPanel() {
        return jSignalMonitor.getJSignalMonitorPanel();
    }

    public JPanel getJSWBPanel() {
        return jswbPanel;
    }

    public void deletePluginFile(File path) {

        sessionInfo.addPluginToDelete(path.getAbsolutePath());
    }

    private void fireIntervalSelectedEvent(IntervalSelectedEvent evt) {
        IntervalSelectedListener listeners[] =
                intervalSelectedListeners.toArray(
                        new IntervalSelectedListener[intervalSelectedListeners.
                        size()]);
        intervalSelectedListeners.clear();
        for (int index = 0; index < listeners.length; index++) {
            listeners[index].intervalSelectedActionPerformed(evt);
        }

    }

    /**
     *
     * que se ejecute adecuadamente aunque no haya parametros de la consola
     * @todo obtener los plugin de desarrollo de un modo adecuado
     *
     * @param args the command line arguments
     *
     * public static void main(final String args[]) {
     * java.awt.EventQueue.invokeLater(new Runnable() {
     * public void run() {
     *
     * String path = null;
     * //pluginsPath = ".";
     * boolean develop = false; //cierto solo si se esta usando el framework para el desarrollo de plugings
     * try {
     * develop = Boolean.parseBoolean(args[0]);
     * path = args[1];
     * //pluginsPath = args[1];
     * } catch (Exception ex) {
     * //no se proporciona parmetro y se usa el valor por defecto
     * }
     *
     * JSWBManager jswbManager = new JSWBManager();
     * if (develop) {
     *
     * DebugPluginsManager.registerDebugPlugins(
     * jswbManager.getPluginManager());
     * if(path!=null)
     * jswbManager.loadChannels(
     * "defaultLoader", new File(path));
     * }
     * jswbManager.refreshJToolBar();
     * jswbManager.getJSignalMonitor().repaintChannels();
     *
     *
     * }
     * });
     * }*/

    /*private void initJMenuBar() {
        if(jMenuBar==null)
            jMenuBar=new JMenuBar();
        if(jMenuBarItems==null)
            jMenuBarItems=new ArrayList<JMenu>();

        //jswbFrame = new JSWBFrame(jSignalMonitor, getPluginManager());
        //addJMenuBarItem(new JMenuFile());
        //addJMenuBarItem(new JMenuSignals(this));
        //addJMenuBarItem(new JMenuPlugins(this));
        //addJMenuBarItem(new JMenuActions(this));



         }

         private void initJToolBar() {
        if(jToolBar==null)
            jToolBar=new JToolBar("JSWB ToolBar");
        if(jToolBarItems==null)
            jToolBarItems=new ArrayList<JComponent>();

        /*addJToolBarButton(new OpenFileAction(this));
           addJToolBarButton(new SaveAction(this));
           HashMap<String, ArrayList<String>> plugins = pluginManager.getRegisteredPlugins();
           ArrayList<String> algorithms = plugins.get("algorithm");
           if (algorithms != null) {
               addJToolBarSeparator();
               addJToolBarButton(new JLabel(" Algorithms: "));
               for (String algorithm : algorithms) {
                   addJToolBarButton(new AlgorithmAction(algorithm,
                           AlgorithmAction.RUN_ACTION, this));
               }

           }

           ArrayList<String> genericPlugins = plugins.get("generic");
           if (genericPlugins != null) {
               addJToolBarSeparator();
               addJToolBarButton(new JLabel(" Generics: "));
               for (String genericPlugin : genericPlugins) {
                   addJToolBarButton(new GenericPluginAction(this,genericPlugin,
                           GenericPluginAction.LAUNCH_ACTION));
               }
           }

           addJToolBarSeparator();
           addJToolBarButton(new AdjustSignalVisibleRangeAction(this));
           addJToolBarSeparator();
           addJToolBarButton(new JRadioButtonXY(jSignalMonitor));
           addJToolBarButton(new JRadioButtonAddMarks(jSignalMonitor));
           addJToolBarSeparator();
           addJToolBarButton(new MoveScrollPanel(jSignalMonitor));
           /*addJToolBarButton(new RemoveAllMarksAction(this));
              addJToolBarButton(new RemoveAllAnnotationsAction(this));

              if(parentWindow !=null && (parentWindow instanceof JFrame)){
                  addJToolBarSeparator();
                  addJToolBarButton(new LookAndFeelAction((JFrame)parentWindow));
              }

               }*/

      private void refreshJToolBar() {
          if (jToolBar != null && jToolBarItems != null) {
              jToolBar.removeAll();
              for (Component component : jToolBarItems) {
                  jToolBar.add(component);
              }
              Runnable uiUpdateRunnable = new Runnable() {
                  public void run() {
                      jToolBar.validate();
                      jToolBar.repaint();
                  }
              };
              javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);
          }
      }

    private void refreshJMenuBar() {
        if (jMenuBar != null && jMenuBarItems != null) {
            jMenuBar.removeAll();
            for (JComponent component : jMenuBarItems) {
                jMenuBar.add(component);
            }
            Runnable uiUpdateRunnable = new Runnable() {
                public void run() {
                    jMenuBar.validate();
                }
            };
            javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);

        }
    }

    /**
     * getChannelData
     *
     * @param signalValues float[]
     * @param pos1 int
     * @param pos2 int
     * @return float[]
     */
    private static float[] getChannelData(float[] signalValues, int pos1, int pos2, float defaultValue) {
        if (pos2 < pos1) {
            int a;
            a = pos2;
            pos2 = pos1;
            pos1 = a;
        }
        /*@todo bug
                 a veces se llama a este metodo como un valor de pos1 de -2147483648
         */
        if (pos2 <= pos1 || pos1 < 0 || pos2 < 0|| pos2 - pos1 > 10000000) {
            return new float[1];
        }
        float[] partialValues = new float[pos2 - pos1];
        for (int index = pos1, index2 = 0; index < pos2; index++, index2++) {
            if (index < 0) {
                partialValues[index2] = signalValues[0];
            } else if (index >= signalValues.length) {
                partialValues[index2] = signalValues[signalValues.length - 1];
            } else {
                partialValues[index2] = signalValues[index];
            }
        }
        return partialValues;
    }


    private static short[] getChannelColors(short[] signalColors, int pos1, int pos2) {
        if (pos2 < pos1) {
            int a;
            a = pos2;
            pos2 = pos1;
            pos1 = a;
        }
        if (pos2 == pos1) {
            return new short[1];
        }
        short[] partialValues = new short[pos2 - pos1];
        for (int index = pos1, index2 = 0; index < pos2; index++, index2++) {
            if (index < 0 || index >= signalColors.length) {
                partialValues[index2] = 0;
            } else {
                partialValues[index2] = signalColors[index];
            }
        }
        return partialValues;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public static Font getBigFont() {
        return bigFont;
    }

    public static Font getSmallFont() {
        return smallFont;
    }

    public static Font getCustomSizeFont(int fontSize) {
        return new java.awt.Font("Tahoma", Font.BOLD, fontSize);
    }


    public static Font getNormalFont() {
        return normalFont;
    }

    public static Color getFontColor() {
        return fontColor;
    }

    public boolean isDeleteSignalsInNextLoad() {
        return deleteSignalsInNextLoad;
    }

    public PropertiesFileManager getPropertiesFileManager() {
        return pfm;
    }

    private void setSaved(boolean saved) {
        sessionInfo.setSessionSaved(saved);
        statusBar.refresh();
    }

    public boolean isProjectSaved() {
        return sessionInfo.isSessionSaved();
    }

    public void setSignalDataColor(String signalName, Color color) {
        try {
            signalManager.setSignalColor(signalName, color);
            setSaved(false);
        } catch (SignalNotFoundException ex) {
            JOptionPane.showMessageDialog(getParentWindow(), ex.getMessage());
        }
    }

    public Color getSignalDataColor(String signalName) {
        try {
            return signalManager.getSignalColor(signalName);
        } catch (SignalNotFoundException ex) {
            JOptionPane.showMessageDialog(getParentWindow(), ex.getMessage());
        }
        return null;
    }

    public boolean setSignalHasEmphasis(String signalName, boolean value) {
        try {

            if (!signalManager.setSignalHasEmphasisLevel(signalName, value)) {
                return false;
            }
            setSaved(false);
            return true;
        } catch (SignalNotFoundException ex) {
            JOptionPane.showMessageDialog(getParentWindow(), ex.getMessage());
        }
        return false;
    }

    public boolean getSignalHasEmphasis(String signalName) {
        try {
            return signalManager.getSignalHasEmphasisLevel(signalName);
        } catch (SignalNotFoundException ex) {
            JOptionPane.showMessageDialog(getParentWindow(), ex.getMessage());
        }
        return false; // @todo (Roman) mirar esto
    }

    public GridPlugin getSignalGrid(String signalName) {
        try {
            return signalManager.getSignalGrid(signalName);
        } catch (SignalNotFoundException ex) {
            JOptionPane.showMessageDialog(getParentWindow(), ex.getMessage());
        }
        return null;
    }

    public void setSignalGrid(String signalName, String gridName) {

        try {
            if (pluginManager.isPluginRegistered("grid", gridName)) {
                GridPlugin grid = pluginManager.createGridPlugin(gridName);
                signalManager.setSignalGrid(signalName, grid);
            }
        } catch (SignalNotFoundException ex) {
            JOptionPane.showMessageDialog(getParentWindow(), ex.getMessage());
        } catch (PluginLoadException ex) {
            JOptionPane.showMessageDialog(getParentWindow(), ex.getMessage());
        }
    }

    public List<String> getAvailableKindsOfGrids() {
        return pluginManager.getRegisteredGrids();
    }

    /**
     * prepareClose
     *
     * @param isEmptySession Cierto cuando se vaya a crear una nueva sesion vacia. Falso si se va a cerrar la herramienta.
     * @return boolean
     */
    public boolean prepareClose(boolean isEmptySession) {
        if (!isProjectSaved()) {
            int i = JOptionPane.showConfirmDialog(getParentWindow(), "Do you want to save?", "save",
                                                  JOptionPane.YES_NO_CANCEL_OPTION);
            if (!(i == JOptionPane.CANCEL_OPTION)) {
                if (i == JOptionPane.YES_OPTION) {
                    saveChannels();
                    savePropertiesFile();
                    SessionEvent e = new SessionEvent(true);
                    e.setNoSesion(isEmptySession);
                    fireSessionEvent(e);
                    return true;
                }
                SessionEvent e = new SessionEvent(false);
                e.setNoSesion(isEmptySession);
                fireSessionEvent(e);
                savePropertiesFile();
                return true;
            } else {
                return false;
            }
        }
        SessionEvent e = new SessionEvent(true);
        e.setNoSesion(isEmptySession);
        fireSessionEvent(e);
        savePropertiesFile();
        return true;
    }

    /**
     * fireNewSessionCreatedEvent
     *
     * @param newSessionCreatedEvent NewSessionCreatedEvent
     */
    private synchronized void fireSessionEvent(SessionEvent newSessionCreatedEvent) {
        for (SessionListener elem : sessionListenetList) {
            //si la sesion no es nueva Pero esta guardada es que acabamos de cargarla
            if (newSessionCreatedEvent.isNewSession()) {
                elem.sessionCreated(newSessionCreatedEvent);
            }
            if (newSessionCreatedEvent.isSaved()
                    /*&&!newSessionCreatedEvent.isNewSession()*/) {
                elem.sessionSaved(newSessionCreatedEvent);
            }
            if (newSessionCreatedEvent.isNoSession()) {
                elem.sessionDestroyed(newSessionCreatedEvent);
            }
        }
    }

    private Component getComponent(String constraint) {
        Component comp =
                ((BorderLayout) jswbPanel.getLayout()).getLayoutComponent(constraint);
        return comp;
    }

    private void launchJSWBPanelValidate() {
        Runnable uiUpdateRunnable = new Runnable() {
            public void run() {
                jswbPanel.validate();
            }
        };
        javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);
    }

    /**
     * Permite registrar un plugin que implemente la interfaz {@link  SessionListener} para escuchar eventos de sesion.
     *
     * @param n SessionListener
     * @return boolean cierto si se registro correctamente.
     */
    public boolean addSessionListener(SessionListener n) {
        return sessionListenetList.add(n);
    }

    public boolean removeSessionListener(SessionListener n) {
        return sessionListenetList.remove(n);
    }

    public void setDebugModeOnRestart(boolean debug) {
        if (debug) {
            DebugPluginsManager.registerDebugPlugins(pluginManager);
        }
        sessionInfo.setDebugMode(debug);
    }

    public boolean isDebugModeOnRestart() {
        return sessionInfo.isDebugMode();
    }
}
