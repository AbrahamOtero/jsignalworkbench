package net.javahispano.jsignalwb.jsignalmonitor;

import javax.swing.JPanel;

/**
 * <p>Componente ligero de Swing que permite representar la evoluci�n temporal de
 * un conjunto de se�ales mostradas, as� como una serie de marcas relacionadas
 * con dichas se�ales. Las se�ales y marcas que el componente representa son
 * proporcionadas por un objeto {@link JSignalMonitorDataSource}. El usuario
 * interacciona directamente con JSignalMonitor y �ste solicitar� a su fuente de
 * datos la informaci�n que necesita para visualizar en pantalla los datos que
 * haya requerido el usuario a trav�s de sus interacciones. </p>
 *
 *<p> JSignalMonitor
 * tambi�n puede notificar al {@link JSignalMonitorDataSource} sobre ciertas
 * acciones realizadas por el usuario, como la selecci�n de un fragmento de
 * se�al o la creaci�n o borrado de marcas. El objeto proporciona un {@link
 * Grid} por defecto, {@link DefaultGrid}, que puede ser reemplazado para cada
 * canal empleando m�todos de esta interfaz. Las marcas que JSignalMonitor puede
 * representar tambi�n pueden extenderse. </p>
 *
 * <p>Para obtener el panel donde JSignalMonitor representar� toda la informaci�n
 * relativa a las se�ales debe emplearse el m�todo getJSignalMonitorPanel. No se
 * recomiende interaccionar con el panel que devuelve ese m�todo directamente;
 * cualquier interacci�n con JSignalMonitor deber�a realizarse a trav�s de esta
 * clase.</p>
 *
 * @author Rom�n Segador y Abraham Otero Copyright 2006-2007. This software is
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
    this.jsmProperties=new JSMProperties(jSignalMonitorDataSource);
    jsmPanel=new JSignalMonitorPanel(jsmProperties);
    this.jsmProperties.setLookAndFeelConfiguration(lafC);
}


    public void printSignals(int orientation){
          this.jsmPanel.printSignals(orientation);
    }

    public void printAll(int orientation){
      this.jsmPanel.printAll(orientation);
}


    public void addModeListener(JSignalMonitorModeListener l){
        jsmProperties.addModeListener(l);
    }
    public void addScrollValueListener(JSignalMonitorScrollListener l){
        jsmProperties.addScrollValueChangeListener(l);
    }
    /**
     * A�ade un canal que visualizar� la se�al cuyo nombre se le pasa como par�metro.
     * Se visualizara con las propiedades de visualizacion que se le pasan como
     * segundo parametro.
     *
     * @param signalName nombre de la se�al.
     * @param {@link ChannelProperties} propiedades de visualizacion del canal.
     * @return true si la acci�n se realiz� correctamente, false en caso
     *   contrario.
     */
    public boolean addChannel(String name,ChannelProperties properties){
        return this.addChannel(new Channel(name,properties));
    }

    private boolean addChannel(Channel c){
        if(jsmPanel.addChannel(c)){
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
    public void refreshMaxMinTime(){
        long minTime=-1;
        long maxTime=-1;
        ChannelProperties cp;
        for(String name:jsmPanel.getChannels().getChannelNames()){
            cp=getChannelProperties(name);
            if(cp.getStartTime()<minTime ||minTime<0)
                minTime=cp.getStartTime();
            if(cp.getEndTime()>maxTime)
                maxTime=cp.getEndTime();
        }
        jsmProperties.setScrollBaseTime(minTime);
        jsmProperties.setMaxTime(maxTime);
    }

    /**
     * Establece el zoom vertical en tanto por ciento. Un zoom de 100 mostrar�
     * la se�al original sin ninguna amplificaci�n o reducci�n. Un zoom de 50,
     * por ejemplo, mostrar� el valor de la se�al original dividido entre dos, y
     * uno de 200 el doble del valor de la se�al original.
     *
     * @param signalName nombre de la se�al a la cual se va a cambiar el zoom.
     * @param value nuevo zoom.
     */
    public void setVerticalZoom(String signalName,int value){
        jsmPanel.setVerticalZoom(signalName,value);
    }
    /**
     * Obtiene el zoom vertical en tanto por ciento. Un zoom de 100 mostrar�
     * la se�al original sin ninguna amplificaci�n o reducci�n. Un zoom de 50,
     * por ejemplo, mostrar� el valor de la se�al original dividido entre dos, y
     * uno de 200 el doble del valor de la se�al original.
     *
     * @param signalName nombre de la se�al de la cual se va a obtener el zoom.
     * @return  zoom.
     */
    public int getVerticalZoom(String signalName){
        return jsmPanel.getVerticalZoom(signalName);
    }

    /**
     * Devuelve el n�mero de canales que se est�n representando.
     *
     * @return int
     * @todo  http://en.wikipedia.org/wiki/Law_of_Demeter
     */
    public int channelsSize(){
        return jsmPanel.getChannelsSize();
    }

    /**
     * Permite averiguar en qu� canal se est� visualizando una determinada se�al.
     * el primer canal es el 0.
     *
     * @param channelName nombre de la se�al.
     * @return n�mero del canal.
     */
    public int getChannelPosition(String channelName){
        return jsmPanel.getChannelPosition(channelName);
    }

    /**
     * Permite averiguar en qu� canal se est� visualizando una determinada se�al.
     * El primer canal es el 0.
     *
     * @param index n�mero de canal.
     * @return Nombre de la se�al que se est� visualizando.
     */
    public String getChannelName(int index){
        return jsmPanel.getChannelAtIndex(index);
    }

    /**
     * Intercambia las posiciones de los canales correspondientes con las
     * se�ales que se le pasan como argumentos.
     *
     * @param channel1 nombre de la primera se�al.
     * @param channel2 nombre de la segunda se�al.
     */
    public void swapChannelsPositions(String channel1,String channel2){
        jsmPanel.swapChannelsPositions(channel1,channel2);
    }

    /**
     * obtiene el objeto {@link ChannelProperties} correspondiente con el canal
     * en el que se representa la se�al que se le pasa como argumento.
     *
     * @param channelName nombre de la se�al.
     * @return {@link ChannelProperties} correspondiente a la se�al que se le
     *   pas� como argumento.
     */
    public ChannelProperties getChannelProperties(String channelName){
        return jsmPanel.getChannelProperties(channelName);
    }

    /**
     * Deja de mostrar la se�al que se le pasa como par�metro, eliminando el
     * canal por completo de {@link JSignalMonitor}.
     *
     * @param signalName nombre de la se�al.
     * @return true si la acci�n se realiz� correctamente, false en caso
     *   contrario.
     */
    public boolean removeChannel(String signalName) {

        boolean flag=jsmPanel.removeChannel(signalName);
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
    public boolean hasChannel(String channelName){
        return jsmPanel.hasChannel(channelName);
    }

    /**
     * Elimina todos los canales que se estaban mostrando.
     */
    public void removeAllChannels(){
        jsmPanel.resetChannels();
        this.repaintAll();
    }

    /**
     * Fuerza un repintado de los canales. Se volver�n a solicitar los datos a
     * representar al {@link JSignalMonitorDataSource}.
     */
    public void repaintChannels() {
        if(!jsmProperties.isIgnoreRepaint())
            jsmPanel.refreshData();
    }

    /**
     * Fuerza un repintado de los canales y refrescar los paneles que se sit�an
     * a la izquierda de JSignalMonitor muestran informacion sobre las
     * se�ales, asi como los valores representado en el scrollBar.
     * Se volver�n a solicitar los datos a representar al {@link
     * JSignalMonitorDataSource}.
     */
    public void repaintAll() {
        if(!jsmProperties.isIgnoreRepaint()){
            refreshMaxMinTime();
            jsmPanel.refresh();
        }
    }

    /**
     * Devuelve el panel en el cual JSignalMonitor representar� toda la
     * informaci�n. Este ser� el panel que debe a�adirse a la ventana o Applet
     * donde se quiera emplear JSignalMonitor.
     *
     * @return JPanel
     */
    public JPanel getJSignalMonitorPanel(){
        return jsmPanel;
    }

    /**
     * Modifica el {@link JSignalMonitorDataSource}de esta instancia de
     * JSignalMonitor.
     *
     * @param jsmds JSignalMonitorDataSource
     */
    public void setJSMDataSource(JSignalMonitorDataSource jsmds){
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
    public void setScrollBaseTime(long baseTime){
        jsmProperties.setScrollBaseTime(baseTime);
    }

    /**
     * Obtiene el {@link JSignalMonitorDataSource}asociado con esta instancia de
     * JSignalMonitor.
     *
     * @return JSignalMonitorDataSource
     */
    public JSignalMonitorDataSource getJSMDataSource(){
        return jsmProperties.getDataSource();
    }

    /**
     * Modifica el tiempo m�ximo que ser� visualizado por JSignalMonitor. Para
     * JSignalMonitor este instante del tiempo es el final del registro de
     * se�al y no se mostraran instantes de tiempo posteriores a el.
     *
     * @param maxTime Instante de tiempo medido en milisegundos.  Ver {@link TimePositionConverter}.
     */
    public void setEndTime(long maxTime){
        jsmProperties.setMaxTime(maxTime);
    }

    /**
     * Modifica el instante de tiempo a cual est� apuntando el scroll. Debe estar
     * contenido entre los limites del scroll.ver {@link getScrollBaseTime} y
     * {@link getEndTime}.
     *
     * @param scrollValue instante de tiempo al cual va a pasar a apuntar el
     *   scroll metido en milisegundos.  Ver {@link TimePositionConverter}.
     */
    public void setScrollValue(long scrollValue){
        if(scrollValue<getScrollBaseTime())
            scrollValue=getScrollBaseTime();
        else if(scrollValue>(jsmProperties.getMaxTime()-getVisibleTime()))
            scrollValue=(jsmProperties.getMaxTime()-getVisibleTime());
        jsmProperties.setScrollValue(scrollValue);
    }

    /**
     * Modifica la frecuencia de representaci�n de JSignalMonitor. Aunque las
     * distintas se�ales que est� visualizando JSignalMonitor tengan distinta
     * frecuencia un corte vertical sobre todos los ejes temporales que
     * representa JSignalMonitor se corresponde siempre a un mismo instante del
     * tiempo. Para ello, JSignalMonitor emplea una frecuencia ficticia de
     * representaci�n, que podr�a coincidir con la de alguna o todas las
     * se�ales. Dicha frecuencia ficticia de representaci�n se modifica a trav�s
     * de este m�todo.
     *
     * @param frecuency Frecuencia de representaci�n de JSignalMonitor.
     */
    public void setFrecuency(float frecuency){
        jsmProperties.setFrec(frecuency);
    }

    /**
     * Devuelve el tiempo base del scroll representado en milisegundos. Ver
     * {@link TimePositionConverter}.
     *
     * @return long
     */
    public long getScrollBaseTime(){
        return jsmProperties.getScrollBaseTime();
    }

    /**
     * Devuelve el instante de fin del registro representado en milisegundos.
     * Ver {@link TimePositionConverter}.
     *
     * @return long
     */
    public long getEndTime(){
        return jsmProperties.getMaxTime();
    }

    /**
     * Devuelve el instante de tiempo al cual est� apuntando el scroll
     * representado en milisegundos. Ver {@link TimePositionConverter}.
     *
     * @return long
     */
    public long getScrollValue(){
        return jsmProperties.getScrollValue();
    }

    /**
     * Devuelve la frecuencia ficticia de representaci�n de JSignalMonitor. Ver
     *  setFrecuency()
     *
     * @return float
     */
    public float getFrecuency(){
        return jsmProperties.getFrec();
    }

    /**
     * Indica si se deben de representar los valores (x, y) de las se�ales al
     * mover el rat�n sobre ellas.
     *
     * @param rep true si se deben pintar los valores, false en caso contrario.
     */
    public void setRepresentingXYValues(boolean rep){
        jsmProperties.setRepresentingValues(rep);
        jsmPanel.refreshLeftPanel();

    }

    /**
     * Indica si se est�n representando los valores (x, y) de las se�ales.
     *
     * @return true si se est�n representando, false en caso contrario.
     */
    public boolean isRepresentingXYValues(){
        return jsmProperties.isRepresentingValues();
    }

    /**
     * Si se le pasa el valor true ordena a JSignalMonitor que comience la
     * selecci�n de un intervalo de se�al; si se le pasa el valor false se
     * detiene la selecci�n del intervalo.
     *
     * @param value boolean
     */
    public void setSelectIntervalMode(boolean value){
        if(value)
            jsmProperties.setMarkCreation(false);
        jsmProperties.setIntervalSelection(value);
        if(value)
            repaintChannels();
    }

    /**
     * Indica si se est� procediendo actualmente a la selecci�n de un intervalo
     * de se�al.
     *
     * @return true en caso afirmativo, false en caso negativo.
     */
    public boolean isSelectIntervalMode(){
        return jsmProperties.isIntervalSelection();
    }

    /**
     * Si se le pasa el valor true ordena a JSignalMonitor que comience la
     * creaccion de marcas en las se�ales; si se le pasa el valor false se
     * detiene la creacion. Si el parametro es true y {@link isSelectIntervalMode()}
     * devolvia el valor true, este pasara a devolver el valor false.
     *
     * @param value boolean
     */
    public void setMarksSelectionMode(boolean value){
         jsmProperties.setMarkCreation(value);
         repaintChannels();
    }

    /**
     * Indica si se est� procediendo actualmente a la creacion de marcas en las
     * se�ales.
     *
     * @return true en caso afirmativo, false en caso negativo.
     */

    public boolean isMarkSelectionMode(){
        return jsmProperties.isMarkCreation();
    }

    public void setIgnoreRepaint(boolean value){
        jsmProperties.setIgnoreRepaint(value);
        if(!value)
            this.repaintAll();
    }

    public boolean isIgnoreRepaint(){
        return jsmProperties.isIgnoreRepaint();
    }
    /**
     * Devuelve el alto, medido en p�xeles, de cada uno de los canales.
     *
     * @return int
     */
    public int getChannelHeight(){
        return jsmPanel.getChannelHeight();
    }

    public JSignalMonitorGrid getChannelGrid(String channelName){
        return jsmPanel.getChannelGrid(channelName);
    }

    public void setChannelGrid(String channelName,JSignalMonitorGrid jsmGrid){
        jsmPanel.setChannelGrid(channelName,jsmGrid);
    }
    public JSMProperties getJSMProperties(){
        return jsmProperties;
    }
    public long getVisibleTime(){
        return jsmPanel.getVisibleTime();
    }

    public void setShowTimeLeyend(boolean show){
        jsmProperties.setShowTimeLeyend(show);
//        repaintChannels();
    }
    public boolean isShowTimeLeyend(){
        return jsmProperties.isShowTimeLeyend();
    }
     public void setShowActionLeyend(boolean show){
        jsmProperties.setShowActionLeyend(show);
//        repaintChannels();
    }
    public boolean isShowActionLeyend(){
        return jsmProperties.isShowActionLeyend();
    }
    public void showAnnotationsPanel(boolean show){
        jsmPanel.showAnnotationsPanel(show);
    }
    public void showLeftPanel(boolean show){
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
    public void setZoomForFullView(){
        setFrecuency(jsmPanel.getFrecForFullView());
    }
    public float getZoomForFullView(){
        return jsmPanel.getFrecForFullView();
    }
    public void setZoomForTimeInterval(long startTime,long endTime){
        setFrecuency(jsmPanel.getFrecForTimeInterval(startTime, endTime));
        setScrollValue(startTime);
        repaintAll();
    }
}
