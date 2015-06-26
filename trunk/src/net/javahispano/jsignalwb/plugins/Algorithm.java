/*
 * Algorithm.java
 *
 * Created on 11 de abril de 2007, 12:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.plugins;

import java.util.List;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;


/**
 * <p> Esta interfaz representa a los plugin que son algoritmos de procesado de
 * senhal. Dichos algoritmos pueden tener, opcionalmente, una interfaz grafica de
 * configuracion y una interfaz grafica para mostrar los resultados de la
 * ejecucion. Opcionalmente, tambien pueden tener una interfaz grafica de
 * ejecucion en la cual el usuario pueda elegir que senhales desea procesar. En
 * caso de que el algoritmo no proporcione una interfaz grafica para la
 * ejecucion se empleara una interfaz grafica generica que proporciona
 * JSignalWorkbench. </p>
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Roman Segador y
 *   Abraham Otero
 */
public interface Algorithm extends Plugin {


    /**
     * Indica si el algoritmo posee una interfaz de usuario para mostrar los
     * resultados de la ejecucion que deba ser invocada una vez se ha ejecutado.
     *
     * @return cierto si se debe mostrar una interfaz de usuario propio del
     *   algoritmo, falso si no se debe de realizar ninguna accion
     */
    public boolean hasResultsGUI();

    /**
     * Lanza la interfaz grafica que el algoritmo emplea para ejecutarse
     *
     * @param jswbManager JSWBManager del entorno de ejecucion.
     */
    public void launchResultsGUI(JSWBManager jswbManager);

    /**
     * Indica si el algoritmo posee una interfaz grafica propia para ejecutarse.
     *
     * @return ciertos y el algoritmo va a proporcionar su propia interfaz de
     *   usuario para ejecutarse, falso si quiere emplear la interfaz por
     *   defecto.
     */
    public boolean hasOwnExecutionGUI();

    /**
     * Lanza la interfaz grafica que el algoritmo emplea para ejecutarse
     *
     * @param jswbManager JSWBManager
     */
    public void launchExecutionGUI(JSWBManager jswbManager);

    /**
     * Ejecuta el algoritmo. Aqui es donde debe ir el codigo que procese las
     * senhales. Las senhales que debe ser procesadas se obtienen a partir del
     * List<SignalIntervalProperties>. Puede interactuarse con las senhales
     * almacenadas en el entorno a partir del {@link SignalManager}.</p>
     *
     * <p> Es recomendable cuando un algoritmo realice un procesado
     * complejo que peridicamente invoque al mtodo isCancelled de {@link AlgorithmRunner}
     *
     * @param sm {@link SignalManager}
     * @param signals Lista (List<SignalIntervalProperties>) de objetos {@link
     *   SignalIntervalProperties} que el usuario ha seleccionado para que sean
     *   procesados por el algoritmo.
     * @param ar {@link AlgorithmRunner}; permite notificar al usuario del
     *   progreso del algoritmo.
     */
    public void runAlgorithm(SignalManager sm,
                             List<SignalIntervalProperties> signals,
            AlgorithmRunner ar);

    /**
     * Indica el numero de senhanles que necesita el algorimo para ejecutarse.
     *
     * @return Numero de senhales que necesita el algoritmo.
     */
    public int numberOfSignalsNeeded();

    /** Comunica al algoritmo que la ejecucion debe ser cancelada. Es respondabilidad
     *  del algoritmo gestionar esta parada de la ejecucion.
     */
    public void cancelExecution();

}
