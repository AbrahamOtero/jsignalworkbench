/*
 * DebugPluginsManager.java
 *
 * Created on 24 de julio de 2007, 1:39
 */

package net.javahispano.jsignalwb.plugins.debug;

import java.util.ArrayList;

import net.javahispano.jsignalwb.io.BasicLoader;
import net.javahispano.jsignalwb.plugins.Plugin.PluginTypes;
import net.javahispano.jsignalwb.plugins.framework.PluginManager;
import research.apneas.EraseInterval;
import research.beats.ExportarLatiods;
import research.beats.TestDeteccion;
import research.beats.anotaciones.*;
import research.beats.asistencia.*;
import research.beats.hrv.HRVLoader;
import research.beats.hrv.MultiTXTLoader;
import research.mining.AssociateEvents;
import research.mining.GenerateDescriptors;

/**
 *
 * @author Roman Segador
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
        /*/     plugins.add(new DebugPluginInfo(PluginTypes.GRID, "Apnea Grid",
                                            new research.apneas.grid.ApneaGrid()));
            plugins.add(new DebugPluginInfo(PluginTypes.GRID, "Respiratory effort Grid",
                                            new research.apneas.grid.ApneaGrid()));
            plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Test",
                                            new DetectorDesaturacionesWrapper()));
            plugins.add(new DebugPluginInfo(PluginTypes.GRID, "SpO2 Grid",
                                            new research.apneas.grid.SpO2Grid()));
            plugins.add(new DebugPluginInfo(PluginTypes.GRID, "Snoring Grid",
                                            new research.apneas.grid.SnoringGrid()));
            plugins.add(new DebugPluginInfo(PluginTypes.GRID, "Default",
                                            new research.apneas.grid.GridGris()));/**/
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Estadistico",
                                        new net.javahispano.plugins.basicstats.BasicStatisticsPlugin()));

        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Media movil",
                                        new net.javahispano.plugins.basicstats.MobileMeanPlugin()));

        plugins.add(new DebugPluginInfo(PluginTypes.LOADER, "TRACE importer",
                                        new es.usc.gsi.trace.importer.TraceImporter()));

        plugins.add(new DebugPluginInfo(PluginTypes.LOADER, "MultiTXTLoader",
                                        new MultiTXTLoader()));

        plugins.add(new DebugPluginInfo(PluginTypes.GENERIC, "Converor datos MIT-BIH",
                                        new es.usc.gsi.conversorDatosMIT.ConversorDatosMITPlugin()));

        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Detector de latidos",
                                        new TestDeteccion()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "ExportarLatidos",
                                        new ExportarLatiods()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Asociar Eventos",
                                        new AssociateEvents()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Generar descriptores",
                                        new GenerateDescriptors()));

        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Anotando latidos",
                                        new Paso1()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Anotando evento respiratorios",
                                        new Paso2()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Marcas Transparentes",
                                        new MarcasTransparentes()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Borrar marcas",
                                        new BorrarMarcasEnIntervalo()));

        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Apnea",
                                        new research.apneas.ApneaAlgorithm()));
        /*/  plugins.add(new DebugPluginInfo(PluginTypes.GENERIC, "Signal Generation",
                                         new SignalGenerationPlugin()));

         plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "SnoreAlgorithm",
                                         new research.apneas.SnoreAlgorithm()));
        /**/
        /*/
               plugins.add(new DebugPluginInfo("algorithm","Concurrencia",
                      new PruebaDeConcurrenciaParaSwing ()));

               plugins.add(new DebugPluginInfo("algorithm","Concurrencia 2",
                      new PruebaDeConcurrenciaParaSwing2()));
               plugins.add(new DebugPluginInfo("algorithm","AddSignalPropertiesPlugin",
                      new AddSignalPropertiesPlugin()));
               plugins.add(new DebugPluginInfo("algorithm","AddSinPlugin",
                      new AddSinPlugin()));
        /**/
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Derivada",
                                        new research.apneas.Derivada()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Borrar intervalo",
                                        new EraseInterval()));

        /* /   plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Filtro nasal",
                                            new research.apneas.FiltroNasal()));/**/

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

        /*/
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

                /* */


        // DONT REMOVE THIS CODE
        pluginManager.registerDebugPlugins(plugins);
    }

}
