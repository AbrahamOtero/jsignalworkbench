package net.javahispano.jsignalwb;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;
import net.javahispano.jsignalwb.plugins.AnnotationPlugin;
import net.javahispano.jsignalwb.plugins.GridPlugin;
import net.javahispano.jsignalwb.plugins.MarkPlugin;

/**
 * Fachada que aglutina a todas las operaciones relacionadas con las se�ales
 * cargadas en el entorno. Alguna funcionalidad expuesta a trav�s de esta clase
 * tambi�n puede ser accedida a trav�s de {@link JSWBManager}.
 * Cualquier operaci�n que se intente realizar sobre una se�al que no existe
 * lanzar a una {@link SignalNotFoundException}. Se recomienda que antes de
 * intentar realizar cualquier operaci�n sobre una se�al se invoque al m�todo
 * exists() para comprobar si la se�al existe o no.
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Rom�n Segador y
 *   Abraham Otero
 */
public class SignalManager {

    private JSignalMonitor jSignalMonitor;
    /* signals relaciona el nombre de la senal con el objeto Signal que le corresponde
     * No podra existir mas de una senal con el mismo nombre
     */
    private Map<String, Signal> signals;
    private ArrayList<SignalSizeListener> listeners;
    private ArrayList<AnnotationPlugin> annotations;

    SignalManager() {
        signals = Collections.synchronizedMap(new HashMap<String, Signal>());
        listeners = new ArrayList<SignalSizeListener>();
        annotations=new ArrayList<AnnotationPlugin>();
    }
    SignalManager(JSignalMonitor jsm) {
        this();
        this.jSignalMonitor=jsm;
    }

   public void addListener(SignalSizeListener listener) {
        listeners.add(listener);
    }


    /**
     * Carga en el sistema una nueva se�al que se crea a partir de los datos
     * proporcionados. En caso de que exista una se�al previa con el mismo
     * nombre devuelve false. Si se carga correctamente devuelve el valor true.
     *
     * @param name nombre de la nueva se�al.
     * @param values valores de la se�al.
     * @param sRate frecuencia de muestreo de la se�al.
     * @param start instante de comienzo de la se�al medido en milisegundos
     *   desde 00:00:00 01/01/1970 del cual se requiere los valores de la
     *   se�al. Ver {@link TimePositionConverter}.
     * @param magnitude magnitud  de la se�al.
     * @return True si la operaci�n se realiz� con �xito. False si ya exist�a
     *   una se�al con el mismo nombre cargada en el entorno.
     * @todo (Abraham) En el addSignal has mantenido que devuelva un boolean en vez
     * de lanzar la excepcion.Creo que tambien deberia ser void y lanzar excepcion.
     * Confirmamelo.
     */
    public boolean addSignal(String name, float[] values, float sRate,
            long start, String magnitude) {
        if(!exists(name))
            return addSignal(new Signal(name, values, sRate, start, magnitude));
        return false;
    }

    /**
     * Carga en el sistema la senal. En caso de que exista una se�al previa con
     * el mismo nombre devuelve false. Si se carga correctamente devuelve el
     * valor true.
     *
     * @param signal {@link Signal} a a�adir.
     * @return True si la operaci�n se realiz� con �xito. False si ya exist�a
     *   una se�al con el mismo nombre cargada en el entorno.
     */
    public boolean addSignal(Signal signal){
        if (!exists(signal.getName())) {
            signals.put(signal.getName(), signal);
            fireSizeEvent(signal, true);
            return true;
        }
        return false;
    }

    /**
     * Comprueba si el sistema tiene cargada una senal con el nombre que se le
     * pasa como parametro.
     *
     * @param signalName nombre de la senal a chequear
     * @return True si existe la se�al, False en caso de que no exista
     */
    public boolean exists(String signalName){
        if(signals.get(signalName)!=null)
            return true;
        else
            return false;
    }
    /**
     * Elimina del entorno la se�al que se le indica.
     *
     * @param name nombre de la se�ala a eliminar.
     * @throws {@link SignalNotFoundException} si la se�al a eliminar no
     *   existe.//
     */
    public void removeSignal(String name) throws SignalNotFoundException  {
        if(exists(name)){
            Signal s = getSignal(name);
            signals.remove(name);
            fireSizeEvent(s, false);

        } else throw new SignalNotFoundException(name,
                "Attempt of deleteting a non existent signal("+name+")");
    }

    /**
     * Cambia el nombre de una se�al cargada en el entorno.
     *
     * @param oldName nombre de la se�al.
     * @param newName nuevo nombre de la senal.
     * @throws {@link SignalNotFoundException} si la se�al a renombrar no
     *   existe o existe alguna con el nombre nuevo.
     */
    public void renameSignal(String oldName,String newName) throws SignalNotFoundException{
        if(!exists(oldName))
            throw new SignalNotFoundException(oldName,
                    "Attempt of renaming a non existent signal("+oldName+")");
        if(exists(newName))
            throw new SignalNotFoundException(newName,
                    "Attempt of set a existent name renaming a signal("+oldName+" to "+newName+")"); //@todo (Roman) comprobar si se deberia devolver true o false en vez de excepcion si existe la se�al nueva
        if(!oldName.equals(newName)){
            Signal signal = getSignal(oldName);
            removeSignal(oldName);
            signal.setName(newName);
            addSignal(signal);
        }
    }


    /**
     * Muestra o oculta la se�al indicada, dependiendo del valor del segundo
     * par�metro.
     *
     * @param signalName nombre de la se�al.
     * @param visible true si la se�al debe mostrarse, false en caso contrario.
     *   Si se intenta mostrar una se�al que ya se est� mostrando, o ocultar
     *   una que ya est� oculta, no se realice ninguna acci�n.
     * @throws {@link SignalNotFoundException} si la se�al no
     *   existe.
     */
    public void setSignalVisible(String signalName,boolean visible) throws SignalNotFoundException{

        if(!exists(signalName))
            throw new SignalNotFoundException(signalName,
                    "The signal is not loaded("+signalName+")");
        if(!(isSignalVisible(signalName)==visible)){
            Signal s=signals.get(signalName);
            s.setVisible(visible);
            fireSizeEvent(s,visible);
        }
    }

    /**
     * Modifica la magnitud de la se�al indicada.
     *
     * @param signalName nombre de la se�al.
     * @param magnitude nueva magnitud.
     * @throws {@link SignalNotFoundException} si la se�al no
     *   existe.
     */
    public void setSignalMagnitude(String signalName,String magnitude) throws SignalNotFoundException{
        if(!exists(signalName))
            throw new SignalNotFoundException(signalName,
                    "The signal is not loaded("+signalName+")");
        signals.get(signalName).setMagnitude(magnitude);
    }

    /**
     * modifica la frecuencia de la se�al indicada.
     *
     * @param signalName nombre de la se�al.
     * @param frecuency nueva frecuencia.
     * @throws {@link SignalNotFoundException} si la se�al no
     *   existe.
     */
    public void setSignalFrecuency(String signalName,float frecuency) throws SignalNotFoundException{
        if(!exists(signalName))
            throw new SignalNotFoundException(signalName,
                    "The signal is not loaded("+signalName+")");
        signals.get(signalName).setFrecuency(frecuency);
    }

    /**
     * Modifica el zoom de la se�al indicada.
     *
     * @param signalName nombre de la se�al.
     * @param zoom nuevo zoom.
     * @throws {@link SignalNotFoundException} si la se�al no
     *   existe.
     */
    public void setSignalZoom(String signalName,float zoom) throws SignalNotFoundException{
        if(!exists(signalName))
            throw new SignalNotFoundException(signalName,
                    "The signal is not loaded("+signalName+")");
        signals.get(signalName).setZoom(zoom);
    }

    /**
     * Modifica el instante de comienzo de la se�al indicada.
     *
     * @param signalName nombre de la se�al.
     * @param startTime instante de comienzo de la se�al medido en milisegundos
     *   desde 00:00:00 01/01/1970 del cual se requiere los valores de la
     *   se�al. Ver {@link TimePositionConverter}.
     * @throws {@link SignalNotFoundException} si la se�al no
     *   existe.
     */
    public void setSignalStartTime(String signalName,long startTime) throws SignalNotFoundException{
        Signal s=signals.get(signalName);
        if(!(s==null)){
            s.setStart(startTime);
            if(isSignalVisible(signalName) && jSignalMonitor.getScrollBaseTime()>startTime){ //@todo (roman) esto no deberia ir aqui.
                jSignalMonitor.setScrollBaseTime(startTime);
            }
        }else
            throw new SignalNotFoundException(signalName,
                    "The signal is not loaded("+signalName+")");
    }

    /**
     * NO FORMA PARTE DE LA API PUBLICA
     * Modifica el offset de la se�al indicada.
     *
     * @param signalName nombre de la se�al.
     * @param abscissaOffset nuevo offset.
     * @throws {@link SignalNotFoundException} si la se�al no
     *   existe.
     */
//    public void setSignalAbscissaOffset(String signalName,float abscissaOffset) throws SignalNotFoundException{
//        Signal s=signals.get(signalName);
//        if(!(s==null))
//            s.setAbscissaOffset(abscissaOffset);
//        else
//            throw new SignalNotFoundException(signalName,
//                    "The signal is not loaded("+signalName+")");
//    }

    /**
     * Devuelve el offset de la se�al que se le pasa como par�metro.
     *
     * @param signalName se�al cuyo offset deseamos conocer.
     * @return offset.
     * @throws {@link SignalNotFoundException} si la se�al no
     *   existe.
     */
//    public float getSignalAbscissaOffset(String signalName) throws SignalNotFoundException{
//        Signal s=signals.get(signalName);
//        if(!(s==null))
//            return s.getProperties().getAbscissaOffset();
//        else
//            throw new SignalNotFoundException(signalName,
//                    "The signal is not loaded("+signalName+")");
//    }

    /**
     * Modifica el valor correspondiente con el eje de abcisas que se dibuja
     * para la se�al que se indica como par�metro.
     *
     * @param signalName nombre de la se�al.
     * @param abscissaValue nuevo valor para el eje de abcisas.
     * @throws {@link SignalNotFoundException} si la se�al no
     *   existe.
     */
    public void setSignalAbscissaValue(String signalName,float abscissaValue) throws SignalNotFoundException{
        Signal s=signals.get(signalName);
        if(!(s==null))
            s.setAbscissaValue(abscissaValue);
        else
            throw new SignalNotFoundException(signalName,
                    "The signal is not loaded("+signalName+")");
    }

    /**
     * Elimina todas las se�ales cargadas en el entorno.
     */
    public void removeAllSignals() {
        signals.clear();
        annotations.clear();
        fireSizeEvent(null, false);
    }

    /**
     * Proporciona el nombre de todas las senales cargadas en el sistema
     *
     * @return Set
     */
    public List<String> getSignalsNames() {
        ArrayList<String> signalNames=new ArrayList<String>();
        Set<String> signalsSet=signals.keySet();
        synchronized(signals) {
            Iterator<String> i = signalsSet.iterator();
            while (i.hasNext())
                signalNames.add(i.next());
        }
        return signalNames;
    }

    /**
     * Proporciona el numero de senales cargadas en el entorno.
     *
     * @return n�mero de se�ales cargadas en el entorno.
     */
    public int getSignalsSize() {
        return signals.size();
    }

    /** Proporciona todos los objetos Signal cargados en el sistema */
    public Collection<Signal> getSignals() {
        ArrayList<Signal> signalsSync=new ArrayList<Signal>();
        Collection<Signal> c=signals.values();
        synchronized(signals){
            for(Signal s:c)
                signalsSync.add(s);
        }
        return signalsSync;
    }

    /** Sustituye las senales cargadas en el sistema por las contenidas en "newSignals"
     * Si todo sucede de manera normal devolvera el valor true, en caso contrario devolvera
     * el valor false
     */

    public boolean setSignals(ArrayList<Signal> newSignals) {
        boolean flag = true;
        //flag=this.removeAllSignals();
        removeAllSignals();
        for (Signal s : newSignals) {
            if (!this.addSignal(s)) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * Devuelva el objeto Signal identificado con el parametro name. En caso de
     * no existir devuelve null
     *
     * @param name nombre de la se�al.
     * @return objeto {@link Signal} correspondiente.
     */
    public Signal getSignal(String name) {
        return signals.get(name);
    }

    /**
     * Indica si la se�al que se le pasa como par�metro se est� visualizando.
     *
     * @param name nombre de la se�al.
     * @return true si se est� visualizando, false en caso contrario.
     * @throws {@link SignalNotFoundException} si la se�al no
     *   existe.
     */
    public boolean isSignalVisible(String name) throws SignalNotFoundException {
        Signal s=getSignal(name);
        if(!(s==null))
            return s.getProperties().isVisible();
        else
            throw new SignalNotFoundException(name,
                    "The signal is not loaded("+name+")");
    }

    /**
     * Indica si la se�al que se le pasa como par�metro tiene �nfasis asociado.
     *
     * @param name nombre de la se�al.
     * @return true si tiene �nfasis, false en caso contrario.
     * @throws {@link SignalNotFoundException} si la se�al no
     *   existe.
     */
    public boolean hasSignalEmphasisLevel(String signalName) throws SignalNotFoundException {
        Signal s=getSignal(signalName);
        if(!(s==null))
            return s.hasEmphasisLevel();
        throw new SignalNotFoundException(signalName,
                "The signal is not loaded("+signalName+")");
    }




    /**
     * Establece los valores m�ximos y m�nimos del eje de abcisas de la se�al
     * que se pasa como argumento.
     *
     * @param signalName Nombre de la se�al.
     * @param minValue valor m�nimo del eje de abcisas.
     * @param maxValue valor m�ximo del eje de abcisas.
     * @param abscissaValue valor de la se�al en el q se representara la abscissa.
     * @throws {@link SignalNotFoundException} si la se�al no
     *   existe.
     */
    public void setSignalVisibleRange(String signalName,
            float abscissaValue,float maxValue)  throws SignalNotFoundException{
        Signal s=signals.get(signalName);
        if(s!=null)
            s.setVisibleRange(abscissaValue,maxValue);
        else
            throw new SignalNotFoundException(signalName,"Attempt of change settings of a non existent signal("+signalName+")");
    }

    /**
     * Establece los valores m�ximos y m�nimos del eje de abcisas todas las se�ales.
     *
     * @param minValue valor m�nimo del eje de abcisas.
     * @param maxValue valor m�ximo del eje de abcisas.
     * @param abscissaValue valor de la se�al en el q se representara la abscissa.
     * @throws {@link SignalNotFoundException} si la se�al no
     *   existe.
     */

    public boolean setSignalVisibleRange(float abscissaValue,float maxValue){
        boolean flag=true;
        synchronized(signals){
            Iterator<Signal> it=signals.values().iterator();
            Signal s;
            while(it.hasNext()){
                s=it.next();
                if(!s.setVisibleRange(abscissaValue,maxValue))
                    flag=false;
            }
        }return flag;
    }

    /**
     * Establece como valores m�ximo y m�nimo del eje de abcisas de la se�al que
     * se le pasa como argumento el valor m�ximo y m�nimo de la se�al.
     *
     * @param signalName nombre de la se�al.
     * @param size tama�o en pixeles que ocupara la representacion de la senal.
     * @return boolean
     */
     public void adjustVisibleRange(String signalName){
         adjustVisibleRange(signalName,1);
     }
    public void adjustVisibleRange(String signalName,float range)  throws SignalNotFoundException{
        Signal s=signals.get(signalName);
        if(s!=null)
            s.adjustVisibleRange(range);
        else
            throw new SignalNotFoundException(signalName,"Attempt of change settings of a non existent signal("+signalName+")");
    }

    /**
     * Establece como valores m�ximo y m�nimo del eje de abcisas de todas las
     * se�ales el valor m�ximo y m�nimo de cada se�al.
     *
     * @param size tama�o en pixeles que ocupara la representacion de cada senal.
     */
    public void adjustVisibleRange(){
        adjustVisibleRange(1);
    }
    public void adjustVisibleRange(float range){
        synchronized(signals){
            Iterator<Signal> it=signals.values().iterator();
            Signal s;
            while(it.hasNext()){
                s=it.next();
                s.adjustVisibleRange(range);
            }
        }
    }

    public List<MarkPlugin> getSignalMarks(String signalName,long startTime,long endTime) throws SignalNotFoundException{
        if(exists(signalName))
            return signals.get(signalName).getMarks(startTime,endTime);
        throw new SignalNotFoundException(signalName,"Attempt of get Marks of a non existent signal("+signalName+")");
    }

    public List<MarkPlugin> getAllSignalMarks(String signalName) throws SignalNotFoundException{
        if(exists(signalName))
            return signals.get(signalName).getAllMarks();
        throw new SignalNotFoundException(signalName,"Attempt of get Marks of a non existent signal("+signalName+")");
    }

    public void addSignalMark(String signalName,MarkPlugin mark) throws SignalNotFoundException{
        if(exists(signalName))
            signals.get(signalName).addMark(mark);
        else
            throw new SignalNotFoundException(signalName,"Attempt of set Marks on a non existent signa("+signalName+")");
    }

    public void removeSignalMark(String signalName,MarkPlugin mark) throws SignalNotFoundException{
        if(exists(signalName)){
            signals.get(signalName).removeMark(mark);
            jSignalMonitor.repaintChannels();
        }else
            throw new SignalNotFoundException(signalName,"Attempt of remove Marks on a non existent signal("+signalName+")");
    }
    public void removeAllSignalMarks(String signalName) throws SignalNotFoundException{
        if(exists(signalName))
            signals.get(signalName).removeAllMarks();
        else
            throw new SignalNotFoundException(signalName,"Attempt of remove Marks on a non existent signal("+signalName+")");
    }

    public void removeAllMarks(){
        synchronized(signals){
            for(Signal s:signals.values())
                s.removeAllMarks();
        }
    }

    public void eraseAllEmphasisLevels(){
    synchronized(signals){
        for(Signal s:signals.values())
            s.eraseEmphasis();
    }
}

    public ArrayList<String> getAnnotationsCategories(){
        ArrayList<String> cat=new ArrayList<String>();
        for(AnnotationPlugin ap:annotations){
            if(!cat.contains(ap.getCategory())){
                cat.add(ap.getCategory());
            }
        }
        return cat;
    }
    public List<AnnotationPlugin> getAnnotations(long startTime,long endTime){
        ArrayList<AnnotationPlugin> tempAnnotations=new ArrayList<AnnotationPlugin>();
        for(AnnotationPlugin ap:annotations){
            if(ap.isInterval()){
                if(ap.getEndTime()>= startTime && ap.getAnnotationTime()<endTime)
                    tempAnnotations.add(ap);
            }else{
                if(ap.getAnnotationTime()>= startTime && ap.getAnnotationTime()<endTime)
                    tempAnnotations.add(ap);
            }
        }
        return tempAnnotations;
    }

    public void addAnnotation(AnnotationPlugin annotationPlugin){
        if(!annotations.contains(annotationPlugin))
            annotations.add(annotationPlugin);
    }
    public void removeAnnotation(AnnotationPlugin annotationPlugin){
        annotations.remove(annotationPlugin);
    }
    public void removeAllAnnotations(){
        annotations.clear();
    }
    public List<AnnotationPlugin> getAllAnnotations(){
        return annotations;
    }
    private void fireSizeEvent(Signal s, boolean add) {
        SignalSizeEvent sse=null;
        if(listeners.size()>0)
            sse=new SignalSizeEvent(s, add);
        for (SignalSizeListener listener : listeners) {
            listener.signalSizeActionPerformed(sse);
        }
    }
    public void setSignalColor(String signalName,Color color) throws SignalNotFoundException{
        if(exists(signalName)){
            signals.get(signalName).setDataColor(color);
            jSignalMonitor.repaintChannels();
        }else
            throw new SignalNotFoundException(signalName,"Attempt of set data color on a non existent signal("+signalName+")");
    }
    public Color getSignalColor(String signalName) throws SignalNotFoundException{
        if(exists(signalName)){
            return signals.get(signalName).getDataColor();
        }else
            throw new SignalNotFoundException(signalName,"Attempt of get data color on a non existent signal("+signalName+")");
    }
    public GridPlugin getSignalGrid(String signalName) throws SignalNotFoundException{
        if(exists(signalName)){
            return signals.get(signalName).getGrid();
        }else
            throw new SignalNotFoundException(signalName,"Attempt of set data color on a non existent signal("+signalName+")");
    }

    public void setSignalGrid(String signalName,GridPlugin grid) throws SignalNotFoundException{
        if(exists(signalName)){
            Signal signal = signals.get(signalName);
            signal.setGrid(grid);
            grid.setSignal(signal);
            if(signals.get(signalName).getProperties().isVisible()){
                jSignalMonitor.setChannelGrid(signalName,grid);
                jSignalMonitor.repaintAll();
            }
        }else
            throw new SignalNotFoundException(signalName,"Attempt of set data color on a non existent signal("+signalName+")");
    }

    public boolean setSignalHasEmphasisLevel(String signalName,boolean value){ // @todo (Document) No a�adido a la documentacion
        if(exists(signalName)){
            return signals.get(signalName).setHasEmphasis(value);
        }else
            throw new SignalNotFoundException(signalName,"Attempt of changue the attribute" +
                    "hasEmphasisLevel on a non existent signal("+signalName+")");
    }

    public boolean getSignalHasEmphasisLevel(String signalName){ // @todo (Document) No a�adido a la documentacion
        if(exists(signalName)){
            return signals.get(signalName).hasEmphasisLevel();
        }else
            throw new SignalNotFoundException(signalName,"Attempt of obtain the attribute" +
                    "hasEmphasisLevel on a non existent signal("+signalName+")");
    }

    public void setSignalImaginary(String signalName, boolean imaginary){
        if(!exists(signalName)){
            throw new SignalNotFoundException(signalName,"Attempt of set the attribute" +
                    "imaginary on a non existent signal("+signalName+")");
        }
        if(signals.get(signalName).isImaginary()!=imaginary){
            if(imaginary){
                if(!signalName.endsWith("*")){
                    renameSignal(signalName,signalName+"*");
                    signals.get(signalName+"*").setImaginary(imaginary);
                }
            }else{
                String newSignalName=signalName.substring(0);
                while(newSignalName.endsWith("*")){
                    newSignalName=newSignalName.substring(0,newSignalName.length()-1);
                }
                renameSignal(signalName,newSignalName);
                signals.get(newSignalName).setImaginary(imaginary);
            }
        }
    }
}
