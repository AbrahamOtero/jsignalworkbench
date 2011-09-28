package net.javahispano.jsignalwb.plugins.framework;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import java.util.jar.JarFile;

import javax.swing.*;

import net.javahispano.jsignalwb.framework.ExceptionsCollector;
import net.javahispano.jsignalwb.plugins.*;
import net.javahispano.jsignalwb.plugins.debug.DebugPluginInfo;
import net.javahispano.jsignalwb.plugins.defaults.*;
import net.javahispano.jsignalwb.ui.signalorganizer.SignalOrganizerPlugin;


/**
 * Esta clase hace fachada para toda la funcionaria relacionadas con los plugin
 * cargados en el entorno. Permite cargar plugins, obtener una instancia de
 * cualquier plugin cargado, etctera.
 *
 * @todo los metodos getXXXPlugin son bastante confusos. Algunos de ellos (casi todos) cargaran el plugins y hace falta.
 * Otros solo devuelven el plugins y yo estaba cargado.
 *
 *  * @author Roman Segador y Abraham Otero
 *    Copyright 2006-2007. This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/).
 */
public class PluginManager {

    //private static PluginManager pm = null;
    private FactoryPlugin factoryPlugin;
    private HashMap<String, Object> pluginAssociation;
    private HashMap<String, String> iconsAssociation;
    private ClassLoader classLoader;
    private String defaultDirectory = "data/plugins";

    /**
     * Crea una instancia de la clase indicando un directorio que contiene
     * plugins que deben ser registrados, aunque no cargados hasta que sea
     * necesario, por la instancia.
     *
     * @param pluginsPath directorio con plugins que deben ser registrados.
     */
    public PluginManager(String pluginsPath) {
        this();
        defaultDirectory = pluginsPath;

    }

    /**
     * crea una instancia de la clase empleando un directorio de carga de
     * plugins por defecto.
     */
    public PluginManager() {
        /*if (pm != null) {
            throw new RuntimeException(
                    "PluginManager had been initialized before");
                 }*/
        classLoader = ClassLoader.getSystemClassLoader();
        pluginAssociation = new HashMap<String, Object>();
        iconsAssociation = new HashMap<String, String>();
        pluginAssociation.put("loader:defaultLoader",
                              "net.javahispano.jsignalwb.io.DefaultLoader");
//        pluginAssociation.put("loader:basicLoader",
//                "net.javahispano.jsignalwb.io.BasicLoader");
        pluginAssociation.put("saver:defaultSaver",
                              "net.javahispano.jsignalwb.io.DefaultSaver");
//        pluginAssociation.put("saver:basicSaver",
//                "net.javahispano.jsignalwb.io.BasicSaver");
        pluginAssociation.put("mark:Default Instant Mark",
                              new DefaultInstantMark());
        pluginAssociation.put("mark:Default Interval Mark",
                              new DefaultIntervalMark());
        pluginAssociation.put("mark:Instant Icon Mark",
                              new InstantIconMark());
        pluginAssociation.put("annotation:Default Instant Annotation",
                              new DefaultInstantAnnotation());
        pluginAssociation.put("annotation:Default Interval Annotation",
                              new DefaultIntervalAnnotation());
//        pluginAssociation.put("grid:Old Default Grid",
//                "net.javahispano.jsignalwb.plugins.OldGridPlugin");
        pluginAssociation.put("grid:Default grid plugin",
                              "net.javahispano.jsignalwb.plugins.defaults.DefaultGridPlugin");
        pluginAssociation.put("grid:Temporal Axis Grid",
                              "net.javahispano.jsignalwb.plugins.defaults.AxesGridPlugin");
        pluginAssociation.put("generic:JSWTextProcessorPlugin",
                              "net.javahispano.jsignalwb.ui.texteditor.JSWTextProcessorPlugin");

        pluginAssociation.put("generic:SignalOrganizerPlugin",
                              "net.javahispano.jsignalwb.ui.signalorganizer.SignalOrganizerPlugin");



        //iconsAssociation.put("algorithm:Mark Negative Values","notAvailable.jpg");
        factoryPlugin = new FactoryPlugin(pluginAssociation);
        loadPreviousPlugins();

    }


    void registerIcon(String pluginType, String pluginName, String icon) {
        iconsAssociation.put(pluginType + ":" + pluginName, icon);
    }


    boolean isIconRegistered(String pluginType, String pluginName) {
        return isIconRegistered(pluginType + ":" + pluginName);
    }

    boolean isIconRegistered(String key) {
        return iconsAssociation.containsKey(key);
    }

    /**
     * Devuelve el icono asociado con un determinado plugin con un tamanho de 20 x 20 pixeles.
     *
     * @param pluginType tipo del plugin.
     * @param pluginName nombre del plugin.
     * @return icono asociado con el plugin requerido y con el tamanho
     *   especificado.
     */
    public Icon getIconDefaultSize(String pluginType, String pluginName) {
        ImageIcon ii = (ImageIcon) (getIcon(pluginType, pluginName));
        if (ii != null) {
            return new ImageIcon(ii.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        } else {
            return new ImageIcon(generateImage(pluginName));
        }
    }

    /**
     * Devuelve el icono asociado con un determinado plugin.
     *
     * @param pluginType tipo del plugin.
     * @param pluginName nombre del plugin.
     * @param height altura del icono.
     * @param width ancho del icono.
     * @return icono asociado con el plugin requerido y con el tamanho
     *   especificado.
     */
    public Icon getIconDefaultSize(String pluginType, String pluginName, int width, int height) {
        ImageIcon ii = (ImageIcon) (getIcon(pluginType, pluginName));
        if (ii != null) {
            return new ImageIcon(ii.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        } else {
            return new ImageIcon(generateImage(pluginName).getScaledInstance(width, height, Image.SCALE_SMOOTH));
        }
    }


    /*
     * Devuelve el icono asociado con un determinado plugin.
     *
     * @param pluginType tipo del plugin.
     * @param pluginName nombre del plugin.
     * @return icono asociado con el plugin requerido.
     */
    /**
     * getIcon
     *
     * @param pluginType String
     * @param pluginName String
     * @return Icon
     * @throws {@link PluginLoadException}
     */
    public Icon getIcon(String pluginType, String pluginName) {
        try {
            if (isPluginRegistered(pluginType, pluginName)) {
                if (isIconRegistered(pluginType, pluginName)) {
                    if (isPluginLoaded(pluginType, pluginName)) {
                        return new ImageIcon(Toolkit.getDefaultToolkit().
                                             createImage(pluginAssociation.get(
                                pluginType + ":" + pluginName).getClass().
                                getResource(iconsAssociation.get(pluginType + ":" +
                                pluginName))));
                    } else {
                        URL url = classLoader.loadClass((
                                String) (pluginAssociation.get(pluginType + ":" +
                                pluginName))).getResource(iconsAssociation.get(
                                        pluginType + ":" + pluginName));

                        if (url != null) {
                            Image image = Toolkit.getDefaultToolkit().
                                          createImage(url);
                            return new ImageIcon(image);
                        } else {
//
                            return new ImageIcon(generateImage(pluginName));
//                            return new ImageIcon(
//                                    this.getClass().getResource(
//                                    "images/notAvailable.jpg"));
                        }
                    }
                } else {
                    return this.getPlugin(pluginType + ":" + pluginName).getIcon();
                }
            } else {
                return new ImageIcon(generateImage(pluginName));
//                return new ImageIcon(
//                        this.getClass().getResource(
//                        "images/notAvailable.jpg"));
            }
        } catch (ClassNotFoundException ex) {
            throw new PluginLoadException("Error loading icon from " +
                                          pluginName, ex);
        }
    }

    private Image generateImage(String name) {
        name = name.toUpperCase();
        BufferedImage bufferedImage = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
        char first = name.charAt(0);
        char last = name.charAt(name.length() - 1);
        Graphics2D g2d = bufferedImage.createGraphics();
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 13);
        g2d.setBackground(Color.LIGHT_GRAY);
        g2d.clearRect(0, 0, 20, 20);
        g2d.setColor(Color.RED);
        g2d.setFont(font);
        g2d.drawString(String.valueOf(first), 1, 10);
        g2d.drawString(String.valueOf(last), 10, 20);
        return bufferedImage;
    }

    /**
     * Registra el plugin indicado.
     *
     * @param pluginType tipo de plugin a registrar.
     * @param pluginName nombre del plugin.
     * @param pluginBaseClass URL apuntando a la clase del plugin, esto es, la
     *   clase que se debe cargar para crear el plugin.
     * @return boolean true si el plugin se ha registrado correctamente,
     *   false en caso contrario.
     */
    public boolean registerPlugin(String pluginType, String pluginName,
                                  String pluginBaseClass) {
        String key = pluginType + ":" + pluginName;
        return registerPlugin(key, pluginBaseClass);
    }

    /**
     * Registra el plugin indicado.
     *
     * @param key Clave asociada con este plugin. Las claves se forman
     *   concatenando el tipo del plugin con ":" y con el nombre del plugin.
     * @param pluginBaseClass URL apuntando a la clase del plugin, esto es, a la
     *   clase que se ha de cargar para crear instancias del plugin.
     * @return boolean true si el plugin se ha registrado correctamente,
     *   false en caso contrario.
     */
    public boolean registerPlugin(String key, String pluginBaseClass) {
        if (!isPluginRegistered(key)) {
            pluginAssociation.put(key, pluginBaseClass);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Registra el plugin indicado.
     *
     * @param key Clave asociada con este plugin. Las claves se forman
     *   concatenando el tipo del plugin con ":" y con el nombre del plugin.
     * @param plugin {@link  Plugin} a registrar.
     * @return boolean true si el plugin se ha registrado correctamente,
     *   false en caso contrario.
     */
    public boolean registerPlugin(String key, Plugin plugin) {
        if (!isPluginRegistered(key)) {
            pluginAssociation.put(key, plugin);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Elimina un plugin.
     *
     * @param pluginType tipo de plugin a registrar.
     * @param pluginName nombre del plugin.
     * @return boolean
     */
    public boolean unregisterPlugin(String pluginType, String pluginName) {
        String key = pluginType + ":" + pluginName;
        return unregisterPlugin(key);
    }

    /**
     * Elimina un plugin.
     *
     * @param key Clave asociada con este plugin. Las claves se forman
     *   concatenando el tipo del plugin con ":" y con el nombre del plugin.
     * @return boolean
     */
    public boolean unregisterPlugin(String key) {
        if (isPluginRegistered(key)) {
            pluginAssociation.remove(key);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Permite averiguar si un plugin esta o no registrado.
     *
     * @param pluginType tipo de plugin a registrar.
     * @param pluginName nombre del plugin.
     * @return boolean true si el plugin est\u2193 registrado,
     * false en caso contrario
     */
    public boolean isPluginRegistered(String pluginType, String pluginName) {
        return isPluginRegistered(pluginType + ":" + pluginName);
    }

    /**
     * Permite averiguar si un plugin esta o aun no registrado.
     *
     * @param key Clave asociada con este plugin. Las claves se forman
     *   concatenando el tipo del plugin con ":" y con el nombre del plugin.
     * @return boolean true si el plugin est\u2193 registrado,
     * false en caso contrario
     */

    public boolean isPluginRegistered(String key) {
        return pluginAssociation.containsKey(key);
    }

    /**
     * Permite averiguar si un plugin esta o no cargado en el entorno.
     *
     * @param pluginType tipo de plugin a registrar.
     * @param pluginName nombre del plugin.
     * @return boolean true si el plugin est\u2193 registrado,
     * false en caso contrario
     */

    public boolean isPluginLoaded(String pluginType, String pluginName) {
        return isPluginLoaded(pluginType + ":" + pluginName);
    }

    /**
     * Permite averiguar si un plugin esta o no cargado en el entorno.
     *
     * @param key Clave asociada con este plugin. Las claves se forman
     *   concatenando el tipo del plugin con ":" y con el nombre del plugin.
     * @return boolean true si el plugin est\u2193 registrado,
     * false en caso contrario
     */
    public boolean isPluginLoaded(String key) {
        return!(pluginAssociation.get(key) instanceof String);
    }

    /**
     * Establece el directorio donde se deben buscar los plugin.
     *
     * @param defaultDirectory String
     */
    public void setDefaultDirectory(String defaultDirectory) {
        this.defaultDirectory = defaultDirectory;
        searchPlugins();
    }


    /**
     * Registra los plugins que se encuentren en el directorio por defecto.
     * Implica que los plugins se copien en la carpeta por defecto del programa
     * para el ususario {user}/.JSignalWorkBench
     */
    public void searchPlugins() {
        searchPlugins(defaultDirectory, new ExceptionsCollector(new JFrame()));
    }

    /**
     * Registra los plugins que se encuentren en el directorio indicado en los
     * paramentros.
     * Implica que los plugins se copien en la carpeta por defecto del programa
     * para el ususario {user}/.JSignalWorkBench
     * @param directory directorio del cual se desean instalar los plugins
     */
    public void searchPlugins(String directory) {
        searchPlugins(directory, new ExceptionsCollector(new JFrame()));
    }

    /**
     * Registra los plugin del directorio por defecto y los que se le pasen como segundo parametro.
     * Solo se usa para desarrollar plugins.
     *
     * @param names String[] nombres de los plugin a registrar
     * @param types String[] tipos de los plugin a registrar
     * @param plugins Object[] plugings a registrar
     *
     * public void searchPlugins(String[] names, String[] types, Object[] plugins) {
     * //searchPlugins();
     * for (int i = 0; i < names.length; i++) {
     * pluginAssociation.put(types[i] + ":" + names[i], plugins[i]);
     * }
     *
     * }*/

    /** don't designed as API part*/
    public void registerDebugPlugins(List<DebugPluginInfo> plugins) {
        for (DebugPluginInfo d : plugins) {
            pluginAssociation.put(d.getPluginType() + ":" + d.getPluginName(),
                                  d.getPlugin());
        }
    }


    /**
     * Devuelve el plugin asociado con la clave que se pasa como argumento. No debe
     * llamarse a este metodo para adquirir la instancia de un plugin concreto,
     * especialmente en los casos de grid, marcas y anotaciones, ya que se debera
     * crear una instancia nueva y no devolver la instancia creada como es este caso.
     * Existen metodos get o create, get devuelve la instancia, create crea una nueva,
     * para cada tipo de plugin.
     *
     * @param key Clave del plugin. Las claves se forman concatenando el tipo
     *   del plugin con ":" y con el nombre del plugin.
     * @return {@link Plugin} solicitado. un
     * @throws PluginLoadException Si ha habido algun error al cargar el plugin.
     */
    public Plugin getPlugin(String key) throws PluginLoadException {
        Object p = factoryPlugin.getPlugin(key, classLoader);
        if (p != null && p instanceof Plugin) {
            return (Plugin) p;
        } else {
            return null;
        }
    }


    /**
     * Proporciona el {@link Loader} registrado con el nombre name.
     *
     * @param name nombre del plugin.
     * @throws PluginLoadException Si ha habido algun error al cargar el plugin.
     * @return {@link Loader} asociado con el nombre que paso como parametro.
     */
    public Loader getLoader(String name) throws PluginLoadException {
        Object l = factoryPlugin.getPlugin("loader:" + name, classLoader);
        if (l != null && l instanceof Loader) {
            return (Loader) l;
        } else {
            return null;
        }
        //return FactoryLoader.getFactoryLoaderInstance().getLoader(name);
    }

    /**
     * Devuelve una lista con todos los objetos {@link Loader} registrados. Para
     * que todos aquellos {@link Loader} registrados en el entorno pero no cargados se
     * carguen.
     *
     * @return Lista con todos los objetos {@link Loader}.
     */
    public ArrayList<Loader> getAllLoaders() {
        ArrayList<Loader> loaders = new ArrayList<Loader>();
        ArrayList<String> names = new ArrayList<String>();
        Iterator<String> it = pluginAssociation.keySet().iterator();
        while (it.hasNext()) {
            String plugin = it.next();
            if (plugin.startsWith("loader:")) {
                names.add(plugin);
            }
        }
        for (String plugin : names) {
            try {
                loaders.add((Loader) getPlugin(plugin));
                //haremos un esfuerzo para cargar la mayor parte de los plugin posible
                //si alguno falla no notificamos ya que el usuario puede no ser consciente
                //de esta operacion. Tampoco avisamos al codigo cliente
                //porque no hay nada que este pueda hacer para solucionar el fallo
            } catch (PluginLoadException ex) {
                System.out.println("Error intentar cargar un plugin");
                ex.printStackTrace();
            }
        }
        return loaders;
    }

    /**
     * Proporciona el {@link Saver} registrado con el nombre name.
     *
     * @param name nombre del plugin.
     * @throws PluginLoadException Si ha habido algun error al cargar el plugin.
     * @return {@link Saver} asociado con el nombre que paso como parametro.
     */

    public Saver getSaver(String name) throws PluginLoadException {
        Object s = factoryPlugin.getPlugin("saver:" + name, classLoader);
        if (s != null && s instanceof Saver) {
            return (Saver) s;
        } else {
            return null;
        }
    }

    /**
     * Devuelve una lista con todos los objetos {@link Loader} registrados. Para
     * que todos aquellos {@link Saver} registrados en el entorno pero no cargados se
     * carguen.
     *
     * @return Lista con todos los objetos {@link Saver}.
     */
    public ArrayList<Saver> getAllSavers() {
        ArrayList<Saver> savers = new ArrayList<Saver>();
        ArrayList<String> names = new ArrayList<String>();
        Iterator<String> it = pluginAssociation.keySet().iterator();
        while (it.hasNext()) {
            String plugin = it.next();
            if (plugin.startsWith("saver:")) {
                names.add(plugin);
            }
        }
        for (String plugin : names) {
            try {
                savers.add((Saver) getPlugin(plugin));
                //haremos un esfuerzo para cargar la mayor parte de los plugin posible
                //si alguno falla no notificamos ya que el usuario puede no ser consciente
                //de esta operacion. Tampoco avisamos al codigo cliente
                //porque no hay nada que este pueda hacer para solucionar el fallo
            } catch (PluginLoadException ex) {
                System.out.println("Error intentar cargar un plugin");
                ex.printStackTrace();
            }
        }
        return savers;
    }

    /**
     * Proporciona una nueva instancia del {@link GridPlugin} registrado con el
     * nombre name.
     *
     *
     * @param name nombre del plugin.
     * @return {@link Grid} asociado con el nombre que paso como parametro.
     * @throws PluginLoadExGridPluginion Si ha habido algun error al cargar el plugin.
     */
    public GridPlugin createGridPlugin(String name) throws PluginLoadException {
        Object g = factoryPlugin.getPlugin("grid:" + name, classLoader);
        if (g != null && g instanceof GridPlugin) {
            try {
                return (GridPlugin) g.getClass().newInstance();
            } catch (InstantiationException ex) {
                throw new PluginLoadException("Error instantiation grid", ex);
            } catch (IllegalAccessException ex) {
                throw new PluginLoadException("Error instantiation grid", ex);
            }
        } else {
            return null;
        }
    }

    /**
     * Proporciona el {@link Algorithm} registrado con el nombre name.
     *
     * @param name nombre del plugin.
     * @throws PluginLoadException Si ha habido algun error al cargar el plugin.
     * @return {@link Algorithm} asociado con el nombre que paso como parametro.
     */
    public Algorithm getAlgorithm(String name) throws PluginLoadException {
        Object a = factoryPlugin.getPlugin("algorithm:" + name, classLoader);
        if (a != null && a instanceof Algorithm) {
            return (Algorithm) a;
        } else {
            return null;
        }
    }

    /**
     * Proporciona el {@link Generic} registrado con el nombre name.
     *
     * @param name nombre del plugin.
     * @throws PluginLoadException Si ha habido algun error al cargar el plugin.
     * @return {@link Generic} asociado con el nombre que paso como parametro.
     */
    public GenericPlugin getGeneric(String name) throws PluginLoadException {
        Object g = factoryPlugin.getPlugin("generic:" + name, classLoader);
        if (g != null && g instanceof GenericPlugin) {
            return (GenericPlugin) g;
        } else {
            return null;
        }
    }

    /**
     * Proporciona una nueva instancia del {@link MarkPlugin} registrado con el
     * nombre name.
     *
     * @param name nombre del plugin.
     * @throws PluginLoadException Si ha habido algun error al cargar el plugin.
     * @return {@link MarkPlugin} asociado con el nombre que paso como parametro.
     */
    public MarkPlugin createMarkPlugin(String name) throws PluginLoadException {
        Object m = factoryPlugin.getPlugin("mark:" + name, classLoader);
        if (m != null && m instanceof MarkPlugin) {
            try {
                return (MarkPlugin) m.getClass().newInstance();
            } catch (IllegalAccessException ex) {
                throw new PluginLoadException("Error instantiation mark", ex);
            } catch (InstantiationException ex) {
                throw new PluginLoadException("Error instantiation mark", ex);
            }
        } else {
            return null;
        }
    }

    /** Devuelve un {@link List}<String> que contiene el nombre de los {@link
     *  MarkPlugin} registrados en el sistema.
     *  @return {@link List}<String> listado de {@link MarkPlugin} registrados.
     */
    public List<String> getRegisteredMarks() {
        return getRegisteredPlugins().get("mark");
    }

    /**
     * Proporciona el {@link AnnotationPlugin} registrado con el nombre name.
     *
     * @param name nombre del plugin.
     * @throws PluginLoadException Si ha habido algun error al cargar el plugin.
     * @return {@link AnnotationPlugin} asociado con el nombre que paso como parametro.
     */
    public AnnotationPlugin createAnnotationPlugin(String name) throws PluginLoadException {
        Object a = factoryPlugin.getPlugin("annotation:" + name, classLoader);
        if (a != null && a instanceof AnnotationPlugin) {
            try {
                return (AnnotationPlugin) a.getClass().newInstance();
            } catch (IllegalAccessException ex) {
                throw new PluginLoadException("Error instantiation annotation", ex);
            } catch (InstantiationException ex) {
                throw new PluginLoadException("Error instantiation annotation", ex);
            }
        } else {
            return null;
        }
    }

    /** Devuelve un {@link List}<String> que contiene el nombre de los {@link
     *  AnnotationPlugin} registrados en el sistema.
     *  @return {@link List}<String> listado de {@link AnnotationPlugin} registrados.
     */
    public List<String> getRegisteredAnnotations() {
        return getRegisteredPlugins().get("annotation");
    }

    /** Devuelve un {@link List}<String> que contiene el nombre de los {@link
     *  GridPlugin} registrados en el sistema.
     *  @return {@link List}<String> listado de {@link GridPlugin} registrados.
     */
    public List<String> getRegisteredGrids() {
        return getRegisteredPlugins().get("grid");
    }

    /**
     * Devuelve un listado con los nombres de todos los plugin registrados en el entorno.
     * Agrupados por categorias.No provocara que se carguen todos los plugin.
     *
     * @return HashMap donde las claves son los distintos tipos de plugin y el
     *   valor asociado con cada clave es un ArrayList<String> con un listado de
     *   los nombres de todos los plugins de esa categoria registrados.
     */

    public HashMap<String, ArrayList<String>> getRegisteredPlugins() {
        HashMap<String, ArrayList<String>>
                plugins = new HashMap<String, ArrayList<String>>();
        Iterator<String> it = pluginAssociation.keySet().iterator();
        while (it.hasNext()) {
            String s = it.next();
            String pluginType = s.substring(0, s.indexOf(":"));
            if (!plugins.containsKey(pluginType)) {
                plugins.put(pluginType, new ArrayList<String>());
            }
            // try {
            plugins.get(pluginType).add(s.substring(s.lastIndexOf(":") + 1));
            //} catch (Exception ex) {
            //    System.out.println("Error intentar cargar un plugin");
            //ex.printStackTrace();
            //}
        }
        return plugins;
    }

    /**
     * Devuelve una lista con todas las claves de los plugins cargados
     * actualmente en el entorno(Los que solo estan registrados no seran devueltos).
     * No provoca que se cargue ningun plugin.
     *
     * @return Lista con todas las claves de los plugin cargados.
     */
    public ArrayList<String> getKeysOfLoadedPlugins() {
        ArrayList<String> plugins = new ArrayList<String>();
        Iterator<String> it = pluginAssociation.keySet().iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (isPluginLoaded(s)) {
                plugins.add(s);
            }
        }
        return plugins;
    }

    /**
     * Devuelve un array con todos los archivos .jar correspondientes a los
     * plugins instalados actualmente en el entorno.
     *
     * @return Array con todos los ficheros .jar de los plugin instalados.
     */
    public File[] getInstalledPlugins() {
        File file = new File(System.getProperty("user.home") + "/.JSignalWorkBench");
        if (file.exists()) {
            File[] files = file.listFiles(
                    new FileFilter() {
                public boolean accept(File fileToBeFiltered) {
                    return fileToBeFiltered.getName().toLowerCase().endsWith(
                            ".jar");
                }
            }
            );
            return files;
        } else {
            return null;
        }
    }

    private void searchPlugins(String directory, ExceptionsCollector ec) {
        classLoader = PluginBrowser.install(this, classLoader,
                                            PluginBrowser.search(new File(directory)), ec);
        ec.showExceptions(
                "The following errors appeared while looking for plugins:");
    }

    private void loadPreviousPlugins() {
        File f = new File(System.getProperty("user.home") + "/.JSignalWorkBench");
        if (!f.exists()) {
            f.mkdir();
        }
        File[] files = f.listFiles(
                new FileFilter() {
            public boolean accept(File fileToBeFiltered) {
                return fileToBeFiltered.getName().toLowerCase().endsWith(
                        ".jar");
            }
        }
        );
        URL[] urls = new URL[files.length];
        for (int index = 0; index < files.length; index++) {
            try {
                JarFile jar = new JarFile(files[index]);
                PluginBrowser.loadPlugin(jar, this);
                urls[index] = files[index].toURI().toURL();
                jar.close();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        classLoader = new URLClassLoader(urls, classLoader);
    }
}
