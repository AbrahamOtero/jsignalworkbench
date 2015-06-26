package net.javahispano.jsignalwb.jsignalmonitor;

import javax.swing.JPanel;

/**
 * <p>Componente ligero de Swing que permite representar la evolucion temporal de
 * un conjunto de senhales mostradas, asi como una serie de marcas relacionadas
 * con dichas senhales. Las senhales y marcas que el componente representa son
 * proporcionadas por un objeto {@link JSignalMonitorDataSource}. El usuario
 * interacciona directamente con JSignalMonitor y este solicitara a su fuente de
 * datos la informacion que necesita para visualizar en pantalla los datos que
 * haya requerido el usuario a traves de sus interacciones. </p>
 *
 *<p> JSignalMonitor
 * tambien puede notificar al {@link JSignalMonitorDataSource} sobre ciertas
 * acciones realizadas por el usuario, como la seleccion de un fragmento de
 * senhal o la creacion o borrado de marcas. El objeto proporciona un {@link
 * Grid} por defecto, {@link DefaultGrid}, que puede ser reemplazado para cada
 * canal empleando metodos de esta interfaz. Las marcas que JSignalMonitor puede
 * representar tambien pueden extenderse. </p>
 *
 * <p>Para obtener el panel donde JSignalMonitor representara toda la informacion
 * relativa a las senhales debe emplearse el metodo getJSignalMonitorPanel. No se
 * recomiende interaccionar con el panel que devuelve ese metodo directamente;
 * cualquier interaccion con JSignalMonitor deberia realizarse a traves de esta
 * clase.</p>
 *
 * @author Roman Segador y Abraham Otero Copyright 2006-2007. This software is
 *   under the Apache License Version 2.0 (http://www.apache.org/licenses/).
 */
public class JSignalMonitor {
    //private JSignalMonitorDataSource jsm;
    //private static int index=10;
    private JSignalMonitorPanel jsmPanel;
    private JSMProperties jsmProperties;


    /**
     * crea una instancia de JSignalMonitor con el {@link
     * JSignalMonitorDataSource}que se le pasa como argumento.
     *
     * @param jSignalMonitorDataSource {@link JSignalMonitorDataSource}
     */
    public JSignalMonitor(JSignalMonitorDataSource jSignalMonitorDataSource) {
        this(jSignalMonitorDataSource, new LookAndFeelConfiguration());
    }

    /**
     * crea una instancia de JSignalMonitor con el {@link
     * JSignalMonitorDataSource}que se le pasa como argumento.
     *
     * @param jSignalMonitorDataSource {@link JSignalMonitorDataSource}
     */
    public JSignalMonitor(JSignalMonitorDataSource jSignalMonitorDataSource,
                          LookAndFeelConfiguration lafC) {
        this.jsmProperties = new JSMProperties(jSignalMonitorDataSource);
        jsmPanel = new JSignalMonitorPanel(jsmProperties);
        this.jsmProperties.setLookAndFeelConfiguration(lafC);
    }


    public void printSignals(int orientation) {
        this.jsmPanel.printSignals(orientation);
    }

    public void printAll(int orientation) {
        this.jsmPanel.printAll(orientation);
    }


    public void addModeListener(JSignalMonitorModeListener l) {
        jsmProperties.addModeListener(l);
    }

    public void addScrollValueListener(JSignalMonitorScrollListener l) {
        jsmProperties.addScrollValueChangeListener(l);
    }

    /**
     * Anhade un canal que visualizara la senhal cuyo nombre se le pasa como parametro.
     * Se visualizara con las propiedades de visualizacion que se le pasan como
     * segundo parametro.
     *
     * @param signalName nombre de la senhal.
     * @param {@link ChannelProperties} propiedades de visualizacion del canal.
     * @return true si la accion se realizo correctamente, false en caso
     *   contrario.
     */
    public boolean addChannel(String name, ChannelProperties properties) {
        return this.addChannel(new Channel(name, properties));
    }

    private boolean addChannel(Channel c) {
        if (jsmPanel.addChannel(c)) {
            refreshMaxMinTime();
            repaintAll();
            return true;
        }
        return false;
    }

    /**
     *  Metodo encargado de refrescar el valor minimo y maximo representados
     *  por {@link JSignalMonitor} en la escala del tiempo.
     */
    public void refreshMaxMinTime() {
        long minTime = -1;
        long maxTime = -1;
        ChannelProperties cp;
        for (String name : jsmPanel.getChannels().getChannelNames()) {
            cp = getChannelProperties(name);
            if (cp.getStartTime() < minTime || minTime < 0) {
                minTime = cp.getStartTime();
            }
            if (cp.getEndTime() > maxTime) {
                maxTime = cp.getEndTime();
            }
        }
        jsmProperties.setScrollBaseTime(minTime);
        jsmProperties.setMaxTime(maxTime);
    }

    /**
     * Establece el zoom vertical en tanto por ciento. Un zoom de 100 mostrara
     * la senhal original sin ninguna amplificacion o reduccion. Un zoom de 50,
     * por ejemplo, mostrara el valor de la senhal original dividido entre dos, y
     * uno de 200 el doble del valor de la senhal original.
     *
     * @param signalName nombre de la senhal a la cual se va a cambiar el zoom.
     * @param value nuevo zoom.
     */
    public void setVerticalZoom(String signalName, int value) {
        jsmPanel.setVerticalZoom(signalName, value);
    }

    /**
     * Obtiene el zoom vertical en tanto por ciento. Un zoom de 100 mostrara
     * la senhal original sin ninguna amplificacion o reduccion. Un zoom de 50,
     * por ejemplo, mostrara el valor de la senhal original dividido entre dos, y
     * uno de 200 el doble del valor de la senhal original.
     *
     * @param signalName nombre de la senhal de la cual se va a obtener el zoom.
     * @return  zoom.
     */
    public int getVerticalZoom(String signalName) {
        return jsmPanel.getVerticalZoom(signalName);
    }

    /**
     * Devuelve el numero de canales que se estan representando.
     *
     * @return int
     * @todo  http://en.wikipedia.org/wiki/Law_of_Demeter
     */
    public int channelsSize() {
        return jsmPanel.getChannelsSize();
    }

    /**
     * Permite averiguar en que canal se esta visualizando una determinada senhal.
     * el primer canal es el 0.
     *
     * @param channelName nombre de la senhal.
     * @return numero del canal.
     */
    public int getChannelPosition(String channelName) {
        return jsmPanel.getChannelPosition(channelName);
    }

    /**
     * Permite averiguar en que canal se esta visualizando una determinada senhal.
     * El primer canal es el 0.
     *
     * @param index numero de canal.
     * @return Nombre de la senhal que se esta visualizando.
     */
    public String getChannelName(int index) {
        return jsmPanel.getChannelAtIndex(index);
    }

    /**
     * Intercambia las posiciones de los canales correspondientes con las
     * senhales que se le pasan como argumentos.
     *
     * @param channel1 nombre de la primera senhal.
     * @param channel2 nombre de la segunda senhal.
     */
    public void swapChannelsPositions(String channel1, String channel2) {
        jsmPanel.swapChannelsPositions(channel1, channel2);
    }

    /**
     * obtiene el objeto {@link ChannelProperties} correspondiente con el canal
     * en el que se representa la senhal que se le pasa como argumento.
     *
     * @param channelName nombre de la senhal.
     * @return {@link ChannelProperties} correspondiente a la senhal que se le
     *   paso como argumento.
     */
    public ChannelProperties getChannelProperties(String channelName) {
        return jsmPanel.getChannelProperties(channelName);
    }

    /**
     * Deja de mostrar la senhal que se le pasa como parametro, eliminando el
     * canal por completo de {@link JSignalMonitor}.
     *
     * @param signalName nombre de la senhal.
     * @return true si la accion se realizo correctamente, false en caso
     *   contrario.
     */
    public boolean removeChannel(String signalName) {

        boolean flag = jsmPanel.removeChannel(signalName);
        refreshMaxMinTime();
        repaintAll();
        return flag;
    }

    /**
     * comprobar si existe un canal asociado con el nombre que se le pasa como
     * argumento.
     *
     * @param channelName String
     * @return true si existe el canal, fase en caso contrario.
     */
    public boolean hasChannel(String channelName) {
        return jsmPanel.hasChannel(channelName);
    }

    /**
     * Elimina todos los canales que se estaban mostrando.
     */
    public void removeAllChannels() {
        jsmPanel.resetChannels();
        this.repaintAll();
    }

    /**
     * Fuerza un repintado de los canales. Se volveran a solicitar los datos a
     * representar al {@link JSignalMonitorDataSource}.
     */
    public void repaintChannels() {
        if (!jsmProperties.isIgnoreRepaint()) {
            jsmPanel.refreshData();
        }
    }

    /**
     * Fuerza un repintado de los canales y refrescar los paneles que se situan
     * a la izquierda de JSignalMonitor muestran informacion sobre las
     * senhales, asi como los valores representado en el scrollBar.
     * Se volveran a solicitar los datos a representar al {@link
     * JSignalMonitorDataSource}.
     */
    public void repaintAll() {
        if (!jsmProperties.isIgnoreRepaint()) {
            refreshMaxMinTime();
            jsmPanel.refresh();
        }
    }

    /**
     * Devuelve el panel en el cual JSignalMonitor representara toda la
     * informacion. Este sera el panel que debe anhadirse a la ventana o Applet
     * donde se quiera emplear JSignalMonitor.
     *
     * @return JPanel
     */
    public JPanel getJSignalMonitorPanel() {
        return jsmPanel;
    }

    /**
     * Modifica el {@link JSignalMonitorDataSource}de esta instancia de
     * JSignalMonitor.
     *
     * @param jsmds JSignalMonitorDataSource
     */
    public void setJSMDataSource(JSignalMonitorDataSource jsmds) {
        jsmProperties.setDataSource(jsmds);
    }

    /**
     * modifica el tiempo base del scroll y, por tanto, el tiempo base que
     * emplea JSignalMonitor. No se representaran instantes de tiempo anteriores
     * a este instante.
     *
     * @param baseTime tiempo base del scroll medido en milisegundos.  Ver
     *   {@link TimePositionConverter}.
     */
    public void setScrollBaseTime(long baseTime) {
        jsmProperties.setScrollBaseTime(baseTime);
    }

    /**
     * Obtiene el {@link JSignalMonitorDataSource}asociado con esta instancia de
     * JSignalMonitor.
     *
     * @return JSignalMonitorDataSource
     */
    public JSignalMonitorDataSource getJSMDataSource() {
        return jsmProperties.getDataSource();
    }

    /**
     * Modifica el tiempo maximo que sera visualizado por JSignalMonitor. Para
     * JSignalMonitor este instante del tiempo es el final del registro de
     * senhal y no se mostraran instantes de tiempo posteriores a el.
     *
     * @param maxTime Instante de tiempo medido en milisegundos.  Ver {@link TimePositionConverter}.
     */
    public void setEndTime(long maxTime) {
        jsmProperties.setMaxTime(maxTime);
    }

    /**
     * Modifica el instante de tiempo a cual esta apuntando el scroll. Debe estar
     * contenido entre los limites del scroll.ver {@link getScrollBaseTime} y
     * {@link getEndTime}.
     *
     * @param scrollValue instante de tiempo al cual va a pasar a apuntar el
     *   scroll metido en milisegundos.  Ver {@link TimePositionConverter}.
     */
    public void setScrollValue(long scrollValue) {
        if (scrollValue < getScrollBaseTime()) {
            scrollValue = getScrollBaseTime();
        } else if (scrollValue > (jsmProperties.getMaxTime() - getVisibleTime())) {
            scrollValue = (jsmProperties.getMaxTime() - getVisibleTime());
        }
        jsmProperties.setScrollValue(scrollValue);
    }

    /**
     * Modifica la frecuencia de representacion de JSignalMonitor. Aunque las
     * distintas senhales que este visualizando JSignalMonitor tengan distinta
     * frecuencia un corte vertical sobre todos los ejes temporales que
     * representa JSignalMonitor se corresponde siempre a un mismo instante del
     * tiempo. Para ello, JSignalMonitor emplea una frecuencia ficticia de
     * representacion, que podria coincidir con la de alguna o todas las
     * senhales. Dicha frecuencia ficticia de representacion se modifica a traves
     * de este metodo.
     *
     * @param frecuency Frecuencia de representacion de JSignalMonitor.
     */
    public void setFrecuency(float frecuency) {
        jsmProperties.setFrec(frecuency);
    }

    /**
     * Devuelve el tiempo base del scroll representado en milisegundos. Ver
     * {@link TimePositionConverter}.
     *
     * @return long
     */
    public long getScrollBaseTime() {
        return jsmProperties.getScrollBaseTime();
    }

    /**
     * Devuelve el instante de fin del registro representado en milisegundos.
     * Ver {@link TimePositionConverter}.
     *
     * @return long
     */
    public long getEndTime() {
        return jsmProperties.getMaxTime();
    }

    /**
     * Devuelve el instante de tiempo al cual esta apuntando el scroll
     * representado en milisegundos. Ver {@link TimePositionConverter}.
     *
     * @return long
     */
    public long getScrollValue() {
        return jsmProperties.getScrollValue();
    }

    /**
     * Devuelve la frecuencia ficticia de representacion de JSignalMonitor. Ver
     *  setFrecuency()
     *
     * @return float
     */
    public float getFrecuency() {
        return jsmProperties.getFrec();
    }

    /**
     * Indica si se deben de representar los valores (x, y) de las senhales al
     * mover el raton sobre ellas.
     *
     * @param rep true si se deben pintar los valores, false en caso contrario.
     */
    public void setRepresentingXYValues(boolean rep) {
        jsmProperties.setRepresentingValues(rep);
        jsmPanel.refreshLeftPanel();

    }

    /**
     * Indica si se estan representando los valores (x, y) de las senhales.
     *
     * @return true si se estan representando, false en caso contrario.
     */
    public boolean isRepresentingXYValues() {
        return jsmProperties.isRepresentingValues();
    }

    /**
     * Si se le pasa el valor true ordena a JSignalMonitor que comience la
     * seleccion de un intervalo de senhal; si se le pasa el valor false se
     * detiene la seleccion del intervalo.
     *
     * @param value boolean
     */
    public void setSelectIntervalMode(boolean value) {
        if (value) {
            jsmProperties.setMarkCreation(false);
        }
        jsmProperties.setIntervalSelection(value);
        if (value) {
            repaintChannels();
        }
    }

    /**
     * Indica si se esta procediendo actualmente a la seleccion de un intervalo
     * de senhal.
     *
     * @return true en caso afirmativo, false en caso negativo.
     */
    public boolean isSelectIntervalMode() {
        return jsmProperties.isIntervalSelection();
    }

    /**
     * Si se le pasa el valor true ordena a JSignalMonitor que comience la
     * creaccion de marcas en las senhales; si se le pasa el valor false se
     * detiene la creacion. Si el parametro es true y {@link isSelectIntervalMode()}
     * devolvia el valor true, este pasara a devolver el valor false.
     *
     * @param value boolean
     */
    public void setMarksSelectionMode(boolean value) {
        jsmProperties.setMarkCreation(value);
        repaintChannels();
    }

    /**
     * Indica si se esta procediendo actualmente a la creacion de marcas en las
     * senhales.
     *
     * @return true en caso afirmativo, false en caso negativo.
     */

    public boolean isMarkSelectionMode() {
        return jsmProperties.isMarkCreation();
    }

    public void setIgnoreRepaint(boolean value) {
        jsmProperties.setIgnoreRepaint(value);
        if (!value) {
            this.repaintAll();
        }
    }

    public boolean isIgnoreRepaint() {
        return jsmProperties.isIgnoreRepaint();
    }

    /**
     * Devuelve el alto, medido en pixeles, de cada uno de los canales.
     *
     * @return int
     */
    public int getChannelHeight() {
        return jsmPanel.getChannelHeight();
    }

    public JSignalMonitorGrid getChannelGrid(String channelName) {
        return jsmPanel.getChannelGrid(channelName);
    }

    public void setChannelGrid(String channelName, JSignalMonitorGrid jsmGrid) {
        jsmPanel.setChannelGrid(channelName, jsmGrid);
    }

    public JSMProperties getJSMProperties() {
        return jsmProperties;
    }

    public long getVisibleTime() {
        return jsmPanel.getVisibleTime();
    }

    public void setShowTimeLeyend(boolean show) {
        jsmProperties.setShowTimeLeyend(show);
//        repaintChannels();
    }

    public boolean isShowTimeLeyend() {
        return jsmProperties.isShowTimeLeyend();
    }

    public void setShowActionLeyend(boolean show) {
        jsmProperties.setShowActionLeyend(show);
//        repaintChannels();
    }

    public boolean isShowActionLeyend() {
        return jsmProperties.isShowActionLeyend();
    }

    public void showAnnotationsPanel(boolean show) {
        jsmPanel.showAnnotationsPanel(show);
    }

    public void showLeftPanel(boolean show) {
        jsmPanel.showLeftPanel(show);
    }

    public int getVScaleOffset() {
        return jsmPanel.getVScaleOffset();
    }

    public void setVScaleOffset(int vScaleOffset) {
        jsmPanel.setVScaleOffset(vScaleOffset);
    }

    public int getHLeftOffsetScale() {
        return jsmPanel.getHLeftOffsetScale();
    }

    public void setHLeftOffsetScale(int hLeftOffsetScale) {
        jsmPanel.setHLeftOffsetScale(hLeftOffsetScale);
    }

    public void setZoomForFullView() {
        setFrecuency(jsmPanel.getFrecForFullView());
    }

    public float getZoomForFullView() {
        return jsmPanel.getFrecForFullView();
    }

    public void setZoomForTimeInterval(long startTime, long endTime) {
        setFrecuency(jsmPanel.getFrecForTimeInterval(startTime, endTime));
        setScrollValue(startTime);
        repaintAll();
    }
}
