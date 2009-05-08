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
 * se�al. Dichos algoritmos pueden tener, opcionalmente, una interfaz gr�fica de
 * configuraci�n y una interfaz gr�fica para mostrar los resultados de la
 * ejecuci�n. Opcionalmente, tambi�n pueden tener una interfaz gr�fica de
 * ejecuci�n en la cual el usuario pueda elegir que se�ales desea procesar. En
 * caso de que el algoritmo no proporcione una interfaz gr�fica para la
 * ejecuci�n se emplear� una interfaz gr�fica gen�rica que proporciona
 * JSignalWorkbench. </p>
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Rom�n Segador y
 *   Abraham Otero
 */
public interface Algorithm extends Plugin {


    /**
     * Indica si el algoritmo posee una interfaz de usuario para mostrar los
     * resultados de la ejecuci�n que deba ser invocada una vez se ha ejecutado.
     *
     * @return cierto si se debe mostrar una interfaz de usuario propio del
     *   algoritmo, falso si no se debe de realizar ninguna acci�n
     */
    public boolean hasResultsGUI();

    /**
     * Lanza la interfaz grafica que el algoritmo emplea para ejecutarse
     *
     * @param jswbManager JSWBManager del entorno de ejecuci�n.
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
     * Ejecuta el algoritmo. Aqu� es donde debe ir el c�digo que procese las
     * se�ales. Las se�ales que debe ser procesadas se obtienen a partir del
     * List<SignalIntervalProperties>. Puede interactuarse con las se�ales
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
     * Indica el numero de se�anles que necesita el algorimo para ejecutarse.
     *
     * @return N�mero de se�ales que necesita el algoritmo.
     */
    public int numberOfSignalsNeeded();
    
    /** Comunica al algoritmo que la ejecuci�n debe ser cancelada. Es respondabilidad
     *  del algoritmo gestionar esta parada de la ejecucion.
     */
    public void cancelExecution();

}
