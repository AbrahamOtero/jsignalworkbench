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


/**
 * <p> Esta interfaz representa a los plugin que son algoritmos de procesado de
 * señal. Dichos algoritmos pueden tener, opcionalmente, una interfaz gráfica de
 * configuración y una interfaz gráfica para mostrar los resultados de la
 * ejecución. Opcionalmente, también pueden tener una interfaz gráfica de
 * ejecución en la cual el usuario pueda elegir que señales desea procesar. En
 * caso de que el algoritmo no proporcione una interfaz gráfica para la
 * ejecución se empleará una interfaz gráfica genérica que proporciona
 * JSignalWorkbench. </p>
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Román Segador y
 *   Abraham Otero
 */
public interface Algorithm extends Plugin {


    /**
     * Indica si el algoritmo posee una interfaz de usuario para mostrar los
     * resultados de la ejecución que deba ser invocada una vez se ha ejecutado.
     *
     * @return cierto si se debe mostrar una interfaz de usuario propio del
     *   algoritmo, falso si no se debe de realizar ninguna acción
     */
    public boolean hasResultsGUI();

    /**
     * Lanza la interfaz grafica que el algoritmo emplea para ejecutarse
     *
     * @param jswbManager JSWBManager del entorno de ejecución.
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
     * Ejecuta el algoritmo. Aquí es donde debe ir el código que procese las
     * señales. Las señales que debe ser procesadas se obtienen a partir del
     * List<SignalIntervalProperties>. Puede interactuarse con las señales
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
     * Indica el numero de señanles que necesita el algorimo para ejecutarse.
     *
     * @return Número de señales que necesita el algoritmo.
     */
    public int numberOfSignalsNeeded();
    
    /** Comunica al algoritmo que la ejecución debe ser cancelada. Es respondabilidad
     *  del algoritmo gestionar esta parada de la ejecucion.
     */
    public void cancelExecution();

}
