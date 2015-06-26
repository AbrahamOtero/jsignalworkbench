/*
 * DebugPlugsManager.java
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
import tmp.*;
import research.mining.tmp.CalcularValorBasalSpO2;
import net.javahispano.jsignalwb.plugins.calculator.CalculatorAlgorithm;
import research.descriptors.SeverityDescriptorsGenerator;
import tmp.AdjustSignalsSartsAndEnds;
import research.beats.hrv.ApneaEpisodeGenerator;
import research.mining.*;
import net.javahispano.plugins.signalgeneration.*;
import net.javahispano.testplugins.*;
import net.javahispano.plugins.basicstats.*;
import cerdos.DiuresisAnalisisError;
import com.uspceu.SimpleAlgorithm;
import research.beats.RatasLoader;

/**
 *
 * @author Roman Segador
 */
public class DebugPluginsManager {

    private static boolean analisisECG = true;
    private static boolean shas = true;
    private static boolean gridsDeSAHS = false;
    private static boolean diuresis = false;
    private static boolean variosDeGetafe = false;
    private static boolean diversosPluginsDeTest = false;

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

        
        SimpleAlgorithm.registerSimpleAlgorithms();
        
        
        //NO  TOCAR ESTE CODIGO
        drivers(plugins);
        analisisGenericoDeSeriesTemporales(plugins);
        genericosMarcas(plugins);
       // generarSenhalesArtificiales(plugins);

        analisisECG(plugins);
        shas(plugins);
        gridsDeSAHS(plugins);

        diuresis(plugins);
        variosDeGetafe(plugins);

        diversosPluginsDeTest(plugins);


        // DONT REMOVE THIS CODE
        pluginManager.registerDebugPlugins(plugins);
    }


    private static void analisisGenericoDeSeriesTemporales(ArrayList<DebugPluginInfo> plugins) {
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Media movil",
                                        new net.javahispano.plugins.basicstats.MobileMeanPlugin()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Borrar intervalo",
                                        new EraseInterval()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Interpolar en hueco",
                                        new FillInterval()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Ajustar principio y fin de las senhales",
                                        new AdjustSignalsSartsAndEnds()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Calculadora de parametros",
                                        new CalculatorAlgorithm()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Estadistico",
                                        new BasicStatisticsPlugin()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Derivada",
                                        new research.apneas.Derivada()));
    }

    private static void genericosMarcas(ArrayList<DebugPluginInfo> plugins) {
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Marcas Transparentes",
                                        new MarcasTransparentes()));
        plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Borrar marcas",
                                        new BorrarMarcasEnIntervalo()));
    }

    private static void analisisECG(ArrayList<DebugPluginInfo> plugins) {
        if (analisisECG) {
            plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Corregir la posicion cuando cambia fecha",
                                            new CorregirPosicionLatidosCuandoCambiaFecha()));
            plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Detector de latidos",
                                            new TestDeteccion()));
            plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "ExportarLatidos",
                                            new ExportarLatiods()));
            plugins.add(new DebugPluginInfo(PluginTypes.MARK, "Latido normal",
                                            new LatidoAnotacion()));
        }
    }

    private static void shas(ArrayList<DebugPluginInfo> plugins) {
        if (shas) {
            plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Generador de episodios de apnea",
                                            new ApneaEpisodeGenerator()));
//            plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Asociar Eventos",
//                                            new AssociateEvents()));
//            plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Generar descriptores",
//                                            new GenerateDescriptors()));
//            plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Anotando latidos",
//                                            new Paso1()));
//            plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Calcular valor basal SpO2",
//                                            new CalcularValorBasalSpO2()));
//            plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Anotando evento respiratorios",
//                                            new Paso2()));
//            plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "As",
//                                            new Paso3()));
            plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Apnea",
                                            new research.apneas.ApneaAlgorithm()));
//            plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "SnoreAlgorithm",
//                                            new research.apneas.SnoreAlgorithm()));
//            plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Filtro nasal",
//                                            new research.apneas.FiltroNasal()));
            plugins.add(new DebugPluginInfo(PluginTypes.MARK, "Limitacion de flujo",
                                            new LimitacionAnotacion()));
            plugins.add(new DebugPluginInfo(PluginTypes.MARK, "Desaturación",
                                            new DesaturacionAnotacion()));
//            plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Caracterizar la gravedad del paciente",
//                                            new SeverityDescriptorsGenerator()));
        }
    }

    private static void drivers(ArrayList<DebugPluginInfo> plugins) {
        plugins.add(new DebugPluginInfo(PluginTypes.LOADER, "TRACE importer",
                                        new es.usc.gsi.trace.importer.TraceImporter()));
        plugins.add(new DebugPluginInfo(PluginTypes.LOADER, "MultiTXTLoader",
                                        new MultiTXTLoader()));
        plugins.add(new DebugPluginInfo(PluginTypes.LOADER, "basicLoader",
                                        new BasicLoader()));
        plugins.add(new DebugPluginInfo(PluginTypes.LOADER, "HRVLoader",
                                        new HRVLoader()));
        plugins.add(new DebugPluginInfo(PluginTypes.GENERIC, "Converor datos MIT-BIH",
                                        new es.usc.gsi.conversorDatosMIT.ConversorDatosMITPlugin()));

        plugins.add(new DebugPluginInfo(PluginTypes.LOADER, "RataLoader",
                                        new RatasLoader()));

    }

    private static void gridsDeSAHS(ArrayList<DebugPluginInfo> plugins) {
        if (gridsDeSAHS) {
            plugins.add(new DebugPluginInfo(PluginTypes.GRID, "Apnea Grid",
                                            new research.apneas.grid.ApneaGrid()));
            plugins.add(new DebugPluginInfo(PluginTypes.GRID, "Respiratory effort Grid",
                                            new research.apneas.grid.ApneaGrid()));
            plugins.add(new DebugPluginInfo(PluginTypes.GRID, "SpO2 Grid",
                                            new research.apneas.grid.SpO2Grid()));
            plugins.add(new DebugPluginInfo(PluginTypes.GRID, "Snoring Grid",
                                            new research.apneas.grid.SnoringGrid()));
            plugins.add(new DebugPluginInfo(PluginTypes.GRID, "Default",
                                            new research.apneas.grid.GridGris()));
        }
    }

    private static void diuresis(ArrayList<DebugPluginInfo> plugins) {
        if (diuresis) {
            plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Diuresis",
                                            new PigAlg()));
            plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Diuresis error",
                                            new DiuresisEstimador()));
        }
    }

    private static void variosDeGetafe(ArrayList<DebugPluginInfo> plugins) {
        if (variosDeGetafe) {
            plugins.add(new DebugPluginInfo(PluginTypes.MARK, "Pico",
                                            new PicoCerdo()));
            plugins.add(new DebugPluginInfo(PluginTypes.MARK, "Valle",
                                            new ValleCerdo()));
            plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Filtrado cerdo",
                                            new AreaCerdo()));
            plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Visualizador de datos del cerdo",
                                            new VisualizadorDatosCerdo()));
            plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Exportar parmetros cerdo",
                                            new ExportarParametros()));
            plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Frecuencia cardiaca de las ratas",
                                            new FCRatas()));
                    plugins.add(new DebugPluginInfo(PluginTypes.ALGORITHM, "Diuresis error",
                                        new DiuresisAnalisisError()));
        }
    }

    private static void generarSenhalesArtificiales(ArrayList<DebugPluginInfo> plugins) {
        plugins.add(new DebugPluginInfo(PluginTypes.GENERIC, "Signal Generation",
                                        new SignalGenerationPlugin()));
    }

    private static void diversosPluginsDeTest(ArrayList<DebugPluginInfo> plugins) {
        if (diversosPluginsDeTest) {
            plugins.add(new DebugPluginInfo("algorithm", "Concurrencia",
                                            new PruebaDeConcurrenciaParaSwing()));
            plugins.add(new DebugPluginInfo("algorithm", "Concurrencia 2",
                                            new PruebaDeConcurrenciaParaSwing2()));
            plugins.add(new DebugPluginInfo("algorithm", "AddSignalPropertiesPlugin",
                                            new AddSignalPropertiesPlugin()));
            plugins.add(new DebugPluginInfo("algorithm", "AddSinPlugin",
                                            new AddSinPlugin()));
            plugins.add(new DebugPluginInfo("algorithm", "Concurrencia",
                                            new PruebaDeConcurrenciaParaSwing()));
            plugins.add(new DebugPluginInfo("generic", "AddToolBarElementsPlugin",
                                            new AddToolBarElementsPlugin()));
            plugins.add(new DebugPluginInfo("generic", "AddComponentPlugin",
                                            new AddComponentPlugin()));
            plugins.add(new DebugPluginInfo("generic", "AddJMenuPlugin",
                                            new AddJMenuPlugin()));
            plugins.add(new DebugPluginInfo("generic", "ShowSignalsPropertiesPlugin",
                                            new ShowSignalsPropertiesPlugin()));

        }
    }
}
