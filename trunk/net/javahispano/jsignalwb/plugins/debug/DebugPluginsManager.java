/*
 * DebugPluginsManager.java
 *
 * Created on 24 de julio de 2007, 1:39
 */

package net.javahispano.jsignalwb.plugins.debug;

import java.util.ArrayList;

import a.EraseInterval;
import beats.ExportarLatiods;
import beats.TestDeteccion;
import beats.anotaciones.*;
import net.javahispano.hrv.HRVLoader;
import net.javahispano.hrv.MultiTXTLoader;
import net.javahispano.jsignalwb.io.BasicLoader;
import net.javahispano.jsignalwb.plugins.Plugin.PluginTypes;
import net.javahispano.jsignalwb.plugins.PluginManager;
import beats.asistencia.*;

/**
 *
 * @author Román Segador
 */
public class DebugPluginsManager {

    private DebugPluginsManager() {
    }

    public static void registerDebugPlugins(PluginManager pluginManager) {
        // DONT REMOVE THIS CODE
        ArrayList<DebugPluginInfo> plugins = new ArrayList<DebugPluginInfo>();
        //plugins=new ArrayList<DebugPluginInfo>();

        /* INSERT HERE THE CODE TO ADD A NEW PLUGIN IN DEBUG MODE
         * Use this sintaxis
         * {@code plugins.add(new DebugPluginInfo(pluginType,pluginName,Plugin));}
         */
   /*     plugins.add(new DebugPluginInfo(PluginTypes.GRID, "Apnea Grid",
                                        new a.grid.ApneaGrid()));
        plugins.add(new DebugPluginInfo(PluginTypes.GRID, "Respiratory effort Grid",
                                        new a.grid.ApneaGrid()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Test",
                                        new DetectorDesaturacionesWrapper()));
        plugins.add(new DebugPluginInfo(PluginTypes.GRID, "SpO2 Grid",
                                        new a.grid.SpO2Grid()));
        plugins.add(new DebugPluginInfo(PluginTypes.GRID, "Snoring Grid",
                                        new a.grid.SnoringGrid()));
        plugins.add(new DebugPluginInfo(PluginTypes.GRID, "Default",
                                        new a.grid.GridGris()));*/
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Estadistico",
                                        new net.javahispano.jsignalwb.plugins.basicstats.BasicStatisticsPlugin()));

        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Media movil",
                                        new net.javahispano.jsignalwb.plugins.basicstats.MobileMeanPlugin()));

        plugins.add(new DebugPluginInfo(PluginTypes.LOADER, "TRACE importer",
                                        new es.usc.gsi.trace.importer.TraceImporter()));

        plugins.add(new DebugPluginInfo(PluginTypes.LOADER, "MultiTXTLoader",
                                        new MultiTXTLoader()));


        plugins.add(new DebugPluginInfo(PluginTypes.GENERIC, "Converor datos MIT-BIH",
                                        new es.usc.gsi.conversorDatosMIT.ConversorDatosMITPlugin()));

        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM,"Detector de latidos",
                new TestDeteccion()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM,"ExportarLatidos",
                new ExportarLatiods()));

        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM,"Anotando latidos",
                                        new Paso1()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM,"Anotando evento respiratorios",
                                        new Paso2()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM,"Marcas Transparentes",
                                        new MarcasTransparentes()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM,"Borrar marcas",
                                        new BorrarMarcasEnIntervalo()));

        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Apnea",
                                        new a.ApneaAlgorithm()));
      /*  plugins.add(new DebugPluginInfo(PluginTypes.GENERIC, "Signal Generation",
                                        new SignalGenerationPlugin()));

        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "SnoreAlgorithm",
                                        new a.SnoreAlgorithm()));
*/
        /*
                plugins.add(new DebugPluginInfo("algorithm","Concurrencia",
                       new PruebaDeConcurrenciaParaSwing ()));

                plugins.add(new DebugPluginInfo("algorithm","Concurrencia 2",
                       new PruebaDeConcurrenciaParaSwing2()));
                plugins.add(new DebugPluginInfo("algorithm","AddSignalPropertiesPlugin",
                       new AddSignalPropertiesPlugin()));
                plugins.add(new DebugPluginInfo("algorithm","AddSinPlugin",
                       new AddSinPlugin()));
         */
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Derivada",
                                        new a.Derivada()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Borrar intervalo",
                                        new EraseInterval()));

    /*    plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Filtro nasal",
                                        new a.FiltroNasal()));*/

        plugins.add(new DebugPluginInfo(PluginTypes.LOADER, "basicLoader",
                                        new BasicLoader()));

        plugins.add(new DebugPluginInfo(PluginTypes.LOADER, "HRVLoader",
                                        new HRVLoader()));
        plugins.add(new DebugPluginInfo(PluginTypes.MARK, "Limitacion de flujo",
                                        new LimitacionAnotacion()));

        plugins.add(new DebugPluginInfo(PluginTypes.MARK, "Desaturación",
                                        new DesaturacionAnotacion()));

        plugins.add(new DebugPluginInfo(PluginTypes.MARK, "Latido normal",
                                        new LatidoAnotacion()));


        /*
                 plugins.add(new DebugPluginInfo("algorithm","Concurrencia",
                new PruebaDeConcurrenciaParaSwing()));
                 plugins.add(new DebugPluginInfo("generic","AddToolBarElementsPlugin",
                new AddToolBarElementsPlugin()));
                 plugins.add(new DebugPluginInfo("generic","AddComponentPlugin",
                new AddComponentPlugin()));
                 plugins.add(new DebugPluginInfo("generic","AddJMenuPlugin",
                new AddJMenuPlugin()));
         plugins.add(new DebugPluginInfo("generic","ShowSignalsPropertiesPlugin",
                new ShowSignalsPropertiesPlugin()));

         */


        // DONT REMOVE THIS CODE
        pluginManager.registerDebugPlugins(plugins);
    }

}
