/*
 * Channels.java
 *
 * Created on 18 de abril de 2007, 16:34
 */

package net.javahispano.jsignalwb.jsignalmonitor;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPopupMenu;

import net.javahispano.jsignalwb.jsignalmonitor.marks.*;

/**
 *
 * @author  Roman
 */
public class Channels extends javax.swing.JPanel {
//    private List<String> positions; //Almacena el nombre de los canales en el orden que se mostraran
//    private Map<String, Channel> channels; //Almacena los canales identificandolos mediante su nombre
//    private Map<JSignalMonitorMark,Rectangle> marks;
    private Map<JSignalMonitorMark, MarkPaintInfo> marksPaintInfo;
    private SynchronizedElements syncElements;
    private int vScaleOffset; // Margen superior
    private int hLeftOffsetScale; // Margen izquierdo
    private int channelHeight; // Altura de cada canal
    private Color backgroundColor;
    private Color separatorsColor;
    private Stroke separatorsStroke;
    private Line2D l2d;
    private Rectangle2D r2d;
    private JSMProperties jsmProperties;
    private Point mousePosition;
    private Color g2dColor;

    /** Creates new form Channels */
    public Channels(JSMProperties jsmProperties) {
//        channels=Collections.synchronizedMap(new HashMap<String, Channel>());
//        positions=Collections.synchronizedList(new ArrayList<String>());
//        marks=Collections.synchronizedMap(new HashMap<JSignalMonitorMark,Rectangle>());
        marksPaintInfo = Collections.synchronizedMap(new HashMap<JSignalMonitorMark, MarkPaintInfo>());
        syncElements = new SynchronizedElements();
        this.jsmProperties = jsmProperties;
        setVScaleOffset(15);
        setHLeftOffsetScale(40);
        setBackgroundColor(Color.WHITE);
        setSeparatorsColor(Color.BLACK);
        g2dColor = new Color(0, 255, 0, 75);
        setSeparatorsStroke(new BasicStroke(1));
        this.setBackground(getBackgroundColor());
        mousePosition = new Point();
        setL2d(new Line2D.Float());
        setR2d(new Rectangle2D.Float());
        this.setLayout(null);

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                channels1MouseMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                channels1Clicked(evt);
            }
        });
    }

    public void sizeChange() {
        int pos;
        synchronized (syncElements) {
            Map<String, Channel> channels = syncElements.getChannels();
            List<String> positions = syncElements.getPositions();
            //float oldChannelHeight=channelHeight;
            if (channels.size() > 0) {
                channelHeight = (int) (getSize().getHeight() - 2 * vScaleOffset) / channels.size();
            } else {
                channelHeight = 0;
            }

            for (Channel c : channels.values()) {
                c.getChannelProperties().refreshChannelHeight(channelHeight - 2 * getVScaleOffset());
                pos = positions.indexOf(c.getChannelProperties().getName());
                if (pos >= 0) {
                    int y = pos * (int) channelHeight + 2 * getVScaleOffset();
                    c.setAbscissaPosition((int) (y + (channelHeight - 2 * getVScaleOffset()))); //@abscissa mirar si hay problemas
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (true) {
            int pos;
            Point p = new Point();
            Graphics2D g2d = (Graphics2D) g;
            //g2d.setBackground(new Color(235,235,245));
            //g2d.clearRect(0,0,getWidth(),getHeight());
            g2d.setClip(0, 0, getWidth(), getHeight());

            synchronized (syncElements) {
                Map<String, Channel> channels = syncElements.getChannels();
                List<String> positions = syncElements.getPositions();
                for (Channel c : channels.values()) {
                    pos = positions.indexOf(c.getChannelProperties().getName());
                    if (pos >= 0) {
                        p.setLocation(getHLeftOffsetScale(),
                                      pos * (int) channelHeight + 2 * getVScaleOffset());
                        c.getGrid().paintGrid(g2d, p, channelHeight - 2 * getVScaleOffset(),
                                              (int) getSize().getWidth() - hLeftOffsetScale - 10,
                                              c.getGridConfiguration());
                        if (c.getGrid().getLeyendHeight() > 0 ||
                            c.getGrid().getLeyendWidth() > 0) {
                            paintLegend(g2d, pos + 1, c.getGrid().getLeyendHeight(),
                                        c.getGrid().getLeyendWidth(),
                                        c.getChannelProperties().getZoom());
                        }
                    }
                }

                paintMarks(g2d);

                for (Channel c : channels.values()) {
                    if (!c.getChannelProperties().isInvadeNearChannels()) {
                        pos = positions.indexOf(c.getChannelProperties().getName());
                        p.setLocation(getHLeftOffsetScale(), pos * (int) channelHeight + 1.5 * getVScaleOffset());
                        Shape s = new Rectangle(p.x, p.y, getSize().width, channelHeight - (int) (1.5 * getVScaleOffset()));
                        g2d.setClip(s);
                    }
                    p.setLocation(getHLeftOffsetScale(), 0);
                    int startOffset = 0;
                    int endOffset = 0;
                    if (c.getChannelProperties().getStartTime() > jsmProperties.getScrollValue()) {
                        startOffset = jsmProperties.getLocationAtTime(c.getChannelProperties().getStartTime());
                    }
                    if (c.getChannelProperties().getEndTime() <
                        jsmProperties.getTimeAtLocation(getSize().width - hLeftOffsetScale - 10)) {
                        endOffset = (getSize().width - hLeftOffsetScale - 10) -
                                    (jsmProperties.getLocationAtTime(c.getChannelProperties().getEndTime()));
                    }
                    c.paintData(g2d, p, startOffset, endOffset);
                    Shape s = new Rectangle(0, 0, getWidth(), getHeight());
                    g2d.setClip(s);
                }

                if (jsmProperties.isRepresentingValues()) {
                    g2d.setColor(Color.RED);
                    g2d.draw(l2d);
                }

                if (jsmProperties.isIntervalSelection() && jsmProperties.isClicked()) {
                    g2d.setColor(g2dColor);
                    if (mousePosition.getX() != 0 && mousePosition.getY() != 0) {
                        int firstClickPosition = getPanelPosition(jsmProperties.getFirstTimeClicked());
                        g2d.fillRect((int) Math.min(firstClickPosition, mousePosition.getX()),
                                     (int) mousePosition.getY(),
                                     (int) Math.abs(firstClickPosition - mousePosition.getX()),
                                     channelHeight);
                    }
                }

                paintSeparators(g2d);
            }
        }
    }

    protected void paintMarks(Graphics2D g2d) {

        if (syncElements.getMarks().size() > 0) {
            Iterator<JSignalMonitorMark> it = syncElements.getMarks().keySet().iterator();
            JSignalMonitorMark mark;
            Rectangle rect;
            while (it.hasNext()) {
                mark = it.next();
                rect = syncElements.getMarks().get(mark);
                if (mark.isOwnPainted()) {
                    mark.paint(g2d, marksPaintInfo.get(mark));
                } else {

                    g2d.drawImage(mark.getImage(),
                                  (int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight(), null);
                }
            }
        }
    }

    protected void paintLegend(Graphics2D g2d, int pos, int eight, int width, float zoom) {
        int middle = getMiddle();
        int vMiddle = channelHeight / 2;
        Color oldColor = g2d.getColor();
        Stroke oldStroke = g2d.getStroke();
        g2d.setColor(Color.GRAY);
        g2d.setStroke(getSeparatorsStroke());
        if (width > 0) {
            g2d.drawLine(middle - width, pos * channelHeight + 20,
                         middle - width, pos * channelHeight + 10);
            g2d.drawLine(middle, pos * channelHeight + 20,
                         middle, pos * channelHeight + 10);
            g2d.drawLine(middle - width, pos * channelHeight + 15,
                         middle, pos * channelHeight + 15);
            g2d.drawString(getTimeAdapted((((float) width) / jsmProperties.getFrec()) * 1000), middle + 8,
                           pos * channelHeight + 20);
        }
        if (eight > 0) {
            pos--;
            g2d.drawLine(hLeftOffsetScale / 2 - 2, pos * channelHeight + vMiddle + eight,
                         hLeftOffsetScale / 2 + 2, pos * channelHeight + vMiddle + eight);
            g2d.drawLine(hLeftOffsetScale / 2 - 2, pos * channelHeight + vMiddle,
                         hLeftOffsetScale / 2 + 2, pos * channelHeight + vMiddle);
            g2d.drawLine(hLeftOffsetScale / 2, pos * channelHeight + vMiddle + eight,
                         hLeftOffsetScale / 2, pos * channelHeight + vMiddle);
            g2d.drawString(String.format("%4.2f", eight / zoom), 5,
                           pos * channelHeight + vMiddle + eight + 12);
        }
        g2d.setColor(oldColor);
        g2d.setStroke(oldStroke);
    }

    protected void paintSeparators(Graphics2D g2d) {

        if (syncElements.getPositions().size() > 0) {
            int middle = getMiddle();
            g2d.setColor(getSeparatorsColor());
            g2d.setStroke(getSeparatorsStroke());
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
            if (jsmProperties.isShowActionLeyend()) {
                if (jsmProperties.isIntervalSelection() && jsmProperties.isMarkCreation()) {
                    g2d.setColor(Color.RED);
                    g2d.drawString("INTERVAL SELECTION(MARK CREATION)", middle - 15, getVScaleOffset() - 2);
                } else if (jsmProperties.isIntervalSelection()) {
                    g2d.setColor(Color.RED);
                    g2d.drawString("INTERVAL SELECTION", middle - 15, getVScaleOffset() - 2);
                } else if (jsmProperties.isMarkCreation()) {
                    g2d.setColor(Color.RED);
                    g2d.drawString("MARK CREATION", middle - 15, getVScaleOffset() - 2);
                }
            }
            if (jsmProperties.isShowTimeLeyend()) {
                g2d.setColor(Color.BLACK);
                g2d.drawString(TimeRepresentation.timeToString(jsmProperties.getScrollValue(), true, true, true),
                               hLeftOffsetScale,
                               (float) syncElements.getPositions().size() * channelHeight + vScaleOffset + 15);
                g2d.drawString(TimeRepresentation.timeToString(jsmProperties.getTimeAtLocation((getSize().width -
                        hLeftOffsetScale - 10)), true, true, true),
                               (int) getSize().getWidth() - 160,
                               (float) syncElements.getPositions().size() * channelHeight + vScaleOffset + 15);
            }

        }
    }

    private int getMiddle() {
        return (int) (getSize().getWidth() - hLeftOffsetScale) / 2 + hLeftOffsetScale;
    }

    private String getTimeAdapted(float f) {
        if (f >= 1000) {
            f = f / 1000f;
            if (f >= 60) {
                f = f / 60;
                if (f >= 60) {
                    f = f / 60;
                    return String.format("%.2f h", f);
                } else {
                    return String.format("%.2f m", f);
                }
            } else {
                return String.format("%.2f s", f);
            }
        } else {
            return String.format("%.2f ms", f);
        }
    }

    private void channels1Clicked(MouseEvent evt) {
        if (evt.getButton() == evt.BUTTON1) {
            boolean existentMark = false;
            synchronized (syncElements) {
                Map<JSignalMonitorMark, Rectangle> marks = syncElements.getMarks();
                Iterator<Rectangle> it = marks.values().iterator();
                while (it.hasNext()) {
                    Rectangle rect = it.next();
                    if (rect.contains(evt.getPoint())) {
                        Iterator<JSignalMonitorMark> it2 = marks.keySet().iterator();
                        while (it2.hasNext()) {
                            JSignalMonitorMark mark = it2.next();
                            if (marks.get(mark).equals(rect)) {
                                existentMark = true;
                                mark.showMarkInfo(getParentFrame());
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            if (!existentMark) {
                if (jsmProperties.isIntervalSelection()) {
                    if (!jsmProperties.isClicked()) {
                        jsmProperties.setFirstTimeClicked(jsmProperties.getTimeAtLocation(evt.getX() -
                                getHLeftOffsetScale()));
                        jsmProperties.setClicked(true);
                    } else {
                        synchronized (syncElements) {
                            List<String> positions = syncElements.getPositions();
                            jsmProperties.setIntervalSelection(false);
                            long secondTimeClicked = jsmProperties.getTimeAtLocation(evt.getX() - getHLeftOffsetScale());
                            if (jsmProperties.isMarkCreation()) {
                                JPopupMenu popup = new MarksPopupMenu(jsmProperties.getDataSource(),
                                        positions.get((evt.getY() - getVScaleOffset()) / this.channelHeight),
                                        Math.min(jsmProperties.getFirstTimeClicked(), secondTimeClicked),
                                        Math.max(jsmProperties.getFirstTimeClicked(), secondTimeClicked));
                                popup.show(this, evt.getX(), evt.getY());
                            } else {
                                jsmProperties.getDataSource().notifyIntervalSelection(
                                        positions.get((evt.getY() - getVScaleOffset()) / this.channelHeight),
                                        Math.min(jsmProperties.getFirstTimeClicked(), secondTimeClicked),
                                        Math.max(jsmProperties.getFirstTimeClicked(), secondTimeClicked));
                                jsmProperties.setFirstTimeClicked( -1);
                                jsmProperties.setClicked(false);
                            }
                        }
                        Runnable uiUpdateRunnable = new Runnable() {
                            public void run() {
                                repaint();
                            }
                        };
                        javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);

                    }
                } else if (jsmProperties.isMarkCreation()) {
                    JPopupMenu popup = new JPopupMenu();
                    popup.add(new MarkAction(this, jsmProperties, evt.getPoint(), false));
                    popup.add(new MarkAction(this, jsmProperties, evt.getPoint(), true));
                    popup.show(this, evt.getX(), evt.getY());
                }
            }
        }
    }

    private void channels1MouseMoved(MouseEvent evt) {
        boolean flag = false;
        synchronized (syncElements) {
            Map<JSignalMonitorMark, Rectangle> marks = syncElements.getMarks();
            Map<String, Channel> channels = syncElements.getChannels();
            List<String> positions = syncElements.getPositions();
            Iterator<Rectangle> it = marks.values().iterator();
            while (it.hasNext()) {
                Rectangle rect = it.next();
                if (rect.contains(evt.getPoint())) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    Iterator<JSignalMonitorMark> it2 = marks.keySet().iterator();
                    while (it2.hasNext()) {
                        JSignalMonitorMark mark = it2.next();
                        if (marks.get(mark).equals(rect)) {
                            setToolTipText(mark.getToolTipText());
                            break;
                        }
                    }
                    break;
                }
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                setToolTipText(null);
            }

            if (jsmProperties.isRepresentingValues()) {
                flag = true;
                if (evt.getY() < getVScaleOffset()) {
                    l2d.setLine(0, 0, 0, 0);
                } else {
                    int index = (evt.getY() - getVScaleOffset()) / this.channelHeight;
                    if (index < positions.size() && index >= 0 &&
                        evt.getX() >= getHLeftOffsetScale()) {
                        l2d.setLine(evt.getX(),
                                    getVScaleOffset(),
                                    evt.getX(),
                                    (positions.size()) * channelHeight + getVScaleOffset());
                        jsmProperties.setMouseTime(jsmProperties.getTimeAtLocation(
                                evt.getX() - getHLeftOffsetScale()));
                    } else {
                        l2d.setLine(0, 0, 0, 0);
                    }
                }
                //this.repaint();
            }
            if (jsmProperties.isIntervalSelection() && jsmProperties.isClicked()) {
                flag = true;
                if (evt.getY() >= getVScaleOffset()) {
                    int index = (evt.getY() - getVScaleOffset()) / this.channelHeight;
                    if (index < positions.size() && index >= 0 &&
                        evt.getX() >= getHLeftOffsetScale()) {
                        mousePosition.setLocation(evt.getX(),
                                                  index * channelHeight + getVScaleOffset());
                    } else {
                        mousePosition.setLocation(0, 0);
                    }
                } else {
                    mousePosition.setLocation(0, 0);
                }
            }
        }
        if (flag) {
            Runnable uiUpdateRunnable = new Runnable() {
                public void run() {
                    repaint();
                }
            };
            javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);
        }

    }

    // Objetivo-> Cargar un nuevo canal a la aplicacion
    // En caso de existir otro con el mismo nombre no lo carga y devuelve false
    public Boolean addChannel(Channel ch) {
        synchronized (syncElements) {
            Map<String, Channel> channels = syncElements.getChannels();
            List<String> positions = syncElements.getPositions();
            if (!channels.containsKey(ch.getChannelProperties().getName())) {
                channels.put(ch.getChannelProperties().getName(), ch);
                positions.add(ch.getChannelProperties().getName());
                return true;
            } else {
                return false;
            }
        }
    }

    // Elimina el canal identificado por name en caso de existir
    public Boolean removeChannel(String name) {
        synchronized (syncElements) {
            Map<String, Channel> channels = syncElements.getChannels();
            List<String> positions = syncElements.getPositions();
            if (channels.containsKey(name) && positions.contains(name)) {
                if (channels.remove(name) != null) {
                    positions.remove(name);
                    return true;
                }
            }
            return false;
        }
    }


    /** Intercambia la posicion de los canales channela y channelb*/
    public void swapPositions(String channela, String channelb) {
        synchronized (syncElements) {
            List<String> positions = syncElements.getPositions();
            int temp = positions.indexOf(channela);
            positions.set(positions.indexOf(channelb), channela);
            positions.set(temp, channelb);
        }
    }

    /** El objetivo es el de solicitar los valores de los canales al JSignalData Source
     *  para el intervalo de tiempo mostrado por pantalla, debera ser invocado cada vez que se modifique
     *  el intervalo mostrado o q los datos de la senhal original sean modificados */

    public void refreshData(JSignalMonitorDataSource dataSource, long firstValue, long lastValue, float dataRate) {
        synchronized (syncElements) {
            Map<JSignalMonitorMark, Rectangle> marks = syncElements.getMarks();
            Map<String, Channel> channels = syncElements.getChannels();
            List<String> positions = syncElements.getPositions();
            marks.clear();
            marksPaintInfo.clear();

            for (Channel c : channels.values()) {
                float data[] = dataSource.getChannelData(c.getChannelProperties().getName(), firstValue, lastValue);
                c.setPoints(Resample.resampleFs(data, c.getChannelProperties().getDataRate(), dataRate, true));
                if (c.getChannelProperties().hasEmphasis()) {
                    short color[] = dataSource.getSignalEmphasisLevels(c.getChannelProperties().getName(), firstValue,
                            lastValue);
                    c.setColors(Resample.resampleFs(color, c.getChannelProperties().getDataRate(), dataRate, true));
                }

                List<JSignalMonitorMark>
                        temp = dataSource.getChannelMark(c.getChannelProperties().getName(), firstValue, lastValue);
                for (JSignalMonitorMark mark : temp) {

                    if (!mark.isInterval()) {
                        Image markImage = mark.getImage();
                        marks.put(mark,
                                  new Rectangle(getHLeftOffsetScale() +
                                                jsmProperties.getLocationAtTime(mark.getMarkTime()),
                                                (int) (c.getAbscissaPosition() -
                                (dataSource.getChannelValueAtTime(c.getChannelProperties().getName(),
                                mark.getMarkTime()) - c.getChannelProperties().getAbscissaValue()) *
                                c.getChannelProperties().getZoom()),
                                                markImage.getWidth(null),
                                                markImage.getHeight(null)));
                    } else {
                        int startPosition = getHLeftOffsetScale() + jsmProperties.getLocationAtTime(mark.getMarkTime());
                        int endPosition = getHLeftOffsetScale() + jsmProperties.getLocationAtTime(mark.getEndTime());
                        if (startPosition < getHLeftOffsetScale()) {
                            startPosition = getHLeftOffsetScale();
                        }

                        if (mark.isOwnPainted()) {
                            Rectangle rect = new Rectangle(startPosition,
                                    (int) (c.getAbscissaPosition() -
                                           (dataSource.getChannelValueAtTime(c.getChannelProperties().getName(),
                                    mark.getMarkTime()) - c.getChannelProperties().getAbscissaValue()) *
                                           c.getChannelProperties().getZoom()),
                                    endPosition - startPosition,
                                    1);
                            marks.put(mark, rect);
                            Point point = new Point((int) rect.getX(),
                                    positions.indexOf(c.getChannelProperties().getName()) * (int) channelHeight +
                                    2 * getVScaleOffset());
                            Point endValue = new Point(((int) (rect.getX() + rect.getWidth())),
                                    (int) (c.getAbscissaPosition() -
                                           (dataSource.getChannelValueAtTime(c.getChannelProperties().getName(),
                                    mark.getEndTime()) - c.getChannelProperties().getAbscissaValue()) *
                                           c.getChannelProperties().getZoom()));
                            float markData[] = dataSource.getChannelData(c.getChannelProperties().getName(),
                                    mark.getMarkTime(), mark.getEndTime());
                            int maxPos = 0;
                            int minPos = 0;
                            if (markData.length > 0) {
                                float min = markData[0];
                                float max = markData[0];
                                for (int index = 1; index < markData.length; index++) {
                                    if (markData[index] < min) {
                                        min = markData[index];
                                    } else if (markData[index] > max) {
                                        max = markData[index];
                                    }
                                }
                                maxPos = (int) (c.getAbscissaPosition() -
                                                ((max - c.getChannelProperties().getAbscissaValue()) *
                                                 c.getChannelProperties().getZoom()));
                                minPos = (int) (c.getAbscissaPosition() -
                                                ((min - c.getChannelProperties().getAbscissaValue()) *
                                                 c.getChannelProperties().getZoom()));

                            }
                            marksPaintInfo.put(mark,
                                               new MarkPaintInfo(point, rect.getLocation(), endValue, maxPos, minPos,
                                    (int) rect.getWidth(), channelHeight - 2 * vScaleOffset));
                            rect.setLocation(point);
                            rect.setSize(rect.width, channelHeight - 2 * vScaleOffset);
                        } else {
                            Rectangle rect = new Rectangle(startPosition,
                                    (int) (c.getAbscissaPosition() -
                                           (dataSource.getChannelValueAtTime(c.getChannelProperties().getName(),
                                    mark.getMarkTime()) - c.getChannelProperties().getAbscissaValue()) *
                                           c.getChannelProperties().getZoom()),
                                    endPosition - startPosition,
                                    mark.getImage().getHeight(null));
                            marks.put(mark, rect);
                        }
                    }
                }
            }
        }
    }

    /** Debe recibir un numero entre 0 y 1 para aplicar el zoom correspondiente
     *  asi como el nombre del canal sobre el cual se debe modificar el zoom*/

    private void verticalZoom(String name, float value) {
        synchronized (syncElements) {
            Channel c = syncElements.getChannels().get(name);
            c.getChannelProperties().setZoom(value);
        }
    }

    /** Hace o no visible el canal cuyo nombre se indica en funcion de la condicion
     *  visible*/

//    public void setChannelVisible(String name, boolean visible) {
//
//        syncElements.getChannels().get(name).setVisible(visible);
//        if (!visible) {
//            int index = syncElements.getPositions().indexOf(name);
//            ArrayList<String> temp = new ArrayList<String>();
//            synchronized (syncElements){
//                for (String s : syncElements.getPositions()) {
//                    if (syncElements.getPositions().indexOf(s) != index) {
//                        temp.add(s);
//                    }
//                }
//            }syncElements.getPositions().clear();
//            syncElements.getPositions().addAll(temp);
////            positions = temp;
//        } else {
//            syncElements.getPositions().add(name);
//        }
//    }

//    public void abscissaOffset(String name, float value) {
//        Channel c = syncElements.getChannels().get(name);
//        c.getChannelProperties().setAbscissaOffset(value);
//    }

    /** Borra todos los canales de JSignalMonitor */
    public void resetChannels() {
        synchronized (syncElements) {
            syncElements.getChannels().clear();
            syncElements.getPositions().clear();
        }
    }


    /** Proporciona un listado de los nombres de los canales cargados, esten o no visibles */
    public ArrayList<String> getChannelNames() {
        ArrayList<String> temp = new ArrayList<String>();
        synchronized (syncElements) {
            Set<String> channelsSync = syncElements.getChannels().keySet();
            for (String c : channelsSync) {
                temp.add(c);
            }
        }
        return temp;
    }

//    /** Proporciona un listado de los nombres de los canales visibles */
//    public List<String> getVisibleChannelNames() {
//        ArrayList<String> names=new ArrayList<String>();
//        synchronized (syncElements){
//            Iterator<String> it=syncElements.getPositions().iterator();
//            while (it.hasNext())
//                names.add(it.next());
//        }
//        return names;
//    }

    /** Proporciona las propiedades del canal indicado */
    public ChannelProperties getChannelProperties(String name) {
        ChannelProperties prop;
        synchronized (syncElements) {
            prop = syncElements.getChannels().get(name).getChannelProperties();
        }
        return prop;
    }

    public int getVScaleOffset() {
        return vScaleOffset;
    }

    public void setVScaleOffset(int vScaleOffset) {
        this.vScaleOffset = vScaleOffset;
    }

    public int getHLeftOffsetScale() {
        return hLeftOffsetScale;
    }

    public void setHLeftOffsetScale(int hLeftOffsetScale) {
        this.hLeftOffsetScale = hLeftOffsetScale;
    }

    public int getChannelHeight() {
        return channelHeight; //-2*getVScaleOffset();
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color background) {
        this.backgroundColor = background;
    }

    public Color getSeparatorsColor() {
        return separatorsColor;
    }

    public void setSeparatorsColor(Color separatorsColor) {
        this.separatorsColor = separatorsColor;
    }

    public Stroke getSeparatorsStroke() {
        return separatorsStroke;
    }

    public void setSeparatorsStroke(Stroke separatorsStroke) {
        this.separatorsStroke = separatorsStroke;
    }

    public boolean hasChannel(String channelName) {
        boolean hasChannel;
        synchronized (syncElements) {
            hasChannel = syncElements.getPositions().contains(channelName);
        }
        return hasChannel;
    }


    private Line2D getL2d() {
        return l2d;
    }

    private void setL2d(Line2D l2d) {
        this.l2d = l2d;
    }

    private Rectangle2D getR2d() {
        return r2d;
    }

    private void setR2d(Rectangle2D r2d) {
        this.r2d = r2d;
    }


    /** Establece el zoom de la senhal en tanto por ciento */
    public void vZoomToSignal(String name, float zoom) {
        if (zoom > 0) {
            verticalZoom(name, (zoom / 100f));
        }
    }

    public int getVerticalZoom(String signalName) {
        int zoom;
        synchronized (syncElements) {
            Channel c = syncElements.getChannels().get(signalName);
            zoom = Math.round(c.getChannelProperties().getZoom()) * 100;
        }
        return zoom;
    }


    public void setJsmProperties(JSMProperties jsmProperties) {
        this.jsmProperties = jsmProperties;
    }

    public int getChannelsSize() {
        int size;
        synchronized (syncElements) {
            size = syncElements.getChannels().size();
        }
        return size;
    }

    public String getChannelAtIndex(int index) {
        String name;
        synchronized (syncElements) {
            name = syncElements.getPositions().get(index);
        }
        return name;
    }

    public int getChannelPosition(String channelName) {
        int position;
        synchronized (syncElements) {
            position = syncElements.getPositions().indexOf(channelName);
        }
        return position;
    }

    public int getPanelPosition(long time) {
        if (time < jsmProperties.getScrollValue()) {
            return getHLeftOffsetScale();
        } else if (time > jsmProperties.getTimeAtLocation((int) (getSize().getWidth() - getHLeftOffsetScale()))) {
            return (int) getSize().getWidth();
        }
        return (int) (((time - jsmProperties.getScrollValue()) / 1000f) * jsmProperties.getFrec()) +
                getHLeftOffsetScale();
    }

    private Window getParentFrame() {
        Container c = this.getParent();
        while (c != null && c.isLightweight()) {
            c = c.getParent();
        }
        if (c instanceof Window) {
            return (Window) c;
        }
        return null;
    }

    public void refreshGridsConfig() {
        synchronized (syncElements) {
            List<String> positions = syncElements.getPositions();
            Map<String, Channel> channels = syncElements.getChannels();
            for (String s : positions) {
                channels.get(s).refreshGridConfig(jsmProperties.getFrec());
            }
        }
    }

    public JSignalMonitorGrid getChannelGrid(String channelName) {
        JSignalMonitorGrid grid;
        synchronized (syncElements) {
            grid = syncElements.getChannels().get(channelName).getGrid();
        }
        return grid;
    }

    public void setChannelGrid(String channelName, JSignalMonitorGrid jsmGrid) {
        synchronized (syncElements) {
            syncElements.getChannels().get(channelName).setGrid(jsmGrid);
        }
    }
}
