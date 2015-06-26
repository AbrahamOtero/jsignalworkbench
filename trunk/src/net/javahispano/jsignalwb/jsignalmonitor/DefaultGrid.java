/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.jsignalmonitor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.swing.JOptionPane;

import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author roman.segador.torre
 */
public class DefaultGrid implements JSignalMonitorGrid {
    private int bigSpace;
    private int numberOfDivisions;
    private int numberOfBigDivisionsX;
    private boolean showBigGrid;
    private boolean showSmallGrid;
    private int bigSpaceY;
    private int numberOfDivisionsY;
    private int numberOfBigDivisionsY;
    private boolean showBigGridY;
    private boolean showSmallGridY;
    private Color color;
    private Color colorGrid;
    private Color colorGridSmall;
    private BufferedImage bufferedImage;
    private GridConfiguration gridConfig;
    private Font font;
    private int abscisaPositionBufferedImage;
    public DefaultGrid() {
//        color=Color.BLACK;
//        colorGrid=Color.LIGHT_GRAY;
//        colorGridSmall=Color.LIGHT_GRAY;
        setColor(Color.BLACK);
        bigSpace = 1;
        numberOfBigDivisionsX = 10;
        numberOfDivisions = 5;
        showBigGrid = true;
        showSmallGrid = false;

        bigSpaceY = 1;
        numberOfBigDivisionsY = 4;
        numberOfDivisionsY = 2;
        showBigGridY = true;
        showSmallGridY = false;

        bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        gridConfig = null;
        font = new Font(Font.SANS_SERIF, Font.BOLD, 10);
    }

    public void launchConfigureGridGUI(Window owner) {
        this.gridConfig = new GridConfiguration( -1, -1, 1, -1, -1);
        new DefaultGridConfigPanel(this).showJWindow(owner);
    }

    public void paintGrid(Graphics2D g2d, Point p, int height, int width,
                          GridConfiguration gridconfig) {
        if (height <= 0 || width <= 0) {
            return;
        }
        if (bufferedImage.getHeight() != height + 5 ||
            bufferedImage.getWidth() != width + 5 || !this.gridConfig.equals(gridconfig)) {
            this.gridConfig = gridconfig;
            this.abscisaPositionBufferedImage = gridconfig.getAbscissaPosition() - p.y;
            refreshBufferedImage(height + 5, width + 5, abscisaPositionBufferedImage);
        }
        g2d.drawImage(bufferedImage, p.x - 5, p.y, null);
        Color original = g2d.getColor();
        Font originalFont = g2d.getFont();
        g2d.setColor(Color.BLUE);
        g2d.setFont(font);
        g2d.drawString(String.format("%4.2f", gridConfig.getMaxValue()), p.x + 3, p.y - 3);
        g2d.drawString(String.format("%4.2f", gridConfig.getMinValue()), p.x + 3, p.y + height + 12);
        g2d.setColor(original);
        g2d.setFont(originalFont);
    }

    public int getLeyendWidth() {
        return bigSpace;
    }

    public int getLeyendHeight() {
        return bigSpaceY;
    }

    private void refreshBufferedImage(int height, int width, int absPos) {
        //this.gridConfig=gridconfig;
        bigSpace = Math.round((width - 5) / (float) getNumberOfBigDivisionsX());
        bigSpaceY = Math.round((height - 5) / (float) getNumberOfBigDivisionsY());
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        Point p = new Point(5, 0);
        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, width, height);
        g2d.setColor(color);
        //int absPos=gridconfig.getAbscissaPosition();
        int smallSpace = Math.round(bigSpace / (float) numberOfDivisions);
        int smallSpaceY = (int) (bigSpaceY / (float) numberOfDivisionsY);

        for (int i = p.x; i < p.x + width - 8; i += bigSpace) {
            if (i != p.x) {
//                g2d.setStroke(bigStroke);
                if (showBigGrid) {
                    g2d.setColor(colorGrid);
                    g2d.drawLine(i, p.y, i, p.y + height);
                }
                g2d.setColor(color);
                g2d.drawLine(i, absPos - 4, i, absPos + 4);
            }
            for (int j = i + smallSpace, index = 1; index < numberOfDivisions; index++, j += smallSpace) {
//                g2d.setStroke(smallStroke);
                if (showSmallGrid) {
                    g2d.setColor(colorGridSmall);
                    g2d.drawLine(j, p.y, j, p.y + height - 5);
                }
                g2d.setColor(color);
                g2d.drawLine(j, absPos - 2, j, absPos + 2);
            }
        }

        for (int i = p.y; i < p.y + height - 8; i += bigSpaceY) {
            if (i != p.y) {
                if (showBigGridY) {
                    g2d.setColor(colorGrid);
                    g2d.drawLine(p.x, i, p.x + width - 5, i);
                }

                g2d.setColor(color);
                g2d.drawLine(p.x - 4, i, p.x + 4, i);
            }
            for (int j = i + smallSpaceY, index = 1; index < numberOfDivisionsY; index++, j += smallSpaceY) {
//                g2d.setStroke(smallStroke);
                if (showSmallGridY) {
                    g2d.setColor(colorGridSmall);
                    g2d.drawLine(p.x, j, p.x + width - 5, j);
                }
                g2d.setColor(color);
                g2d.drawLine(p.x - 2, j, p.x + 2, j);
            }
        }

        //g2d.setStroke(bigStroke);
        g2d.setColor(color);
        g2d.drawLine(p.x, p.y, p.x, p.y + height - 5);
        g2d.drawLine(p.x, absPos, p.x + width - 5, absPos);

        g2d.setColor(colorGridSmall);
        g2d.drawLine(5, 0, width, 0);
        g2d.drawLine(width - 1, 0, width - 1, height - 5);
        g2d.setColor(color);
        g2d.fillRoundRect(2, absPos - 3, 6, 6, 2, 2);
    }

    public int getBigSpace() {
        return bigSpace;
    }

    public void setBigSpace(int bigSpace) {
        this.bigSpace = bigSpace;
    }

    public int getNumberOfDivisions() {
        return numberOfDivisions;
    }

    public void setNumberOfDivisions(int numberOfDivisions) {
        this.numberOfDivisions = numberOfDivisions;
        refresh();
    }

    public boolean isShowBigGrid() {
        return showBigGrid;
    }

    public void setShowBigGrid(boolean showBigGrid) {
        this.showBigGrid = showBigGrid;
        refresh();
    }

    public boolean isShowSmallGrid() {
        return showSmallGrid;
    }

    public void setShowSmallGrid(boolean showSmallGrid) {
        this.showSmallGrid = showSmallGrid;
        refresh();
    }


    public int getNumberOfDivisionsY() {
        return numberOfDivisionsY;
    }

    public void setNumberOfDivisionsY(int numberOfDivisionsY) {
        this.numberOfDivisionsY = numberOfDivisionsY;
        refresh();
    }

    public boolean isShowBigGridY() {
        return showBigGridY;
    }

    public void setShowBigGridY(boolean showBigGridY) {
        this.showBigGridY = showBigGridY;
        refresh();
    }

    public boolean isShowSmallGridY() {
        return showSmallGridY;
    }

    public void setShowSmallGridY(boolean showSmallGridY) {
        this.showSmallGridY = showSmallGridY;
        refresh();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        colorGrid = new Color(color.getRed(), color.getGreen(), color.getBlue(), 125);
        colorGridSmall = new Color(color.getRed(), color.getGreen(), color.getBlue(), 75);
        refresh();
    }

    public GridConfiguration getGridConfig() {
        return gridConfig;
    }

    public void refresh() {
        if (bufferedImage != null) {
            refreshBufferedImage(bufferedImage.getHeight(), bufferedImage.getWidth(), abscisaPositionBufferedImage);
        }
    }

    public String getDataToSave() {
        Element root = new Element("defaultGrid");
        root.setAttribute("numberOfBigDivisionsX", String.valueOf(getNumberOfBigDivisionsX()));
        root.setAttribute("numberOfDivisions", String.valueOf(numberOfDivisions));
        root.setAttribute("numberOfBigDivisionsY", String.valueOf(getNumberOfBigDivisionsY()));
        root.setAttribute("numberOfDivisionsY", String.valueOf(numberOfDivisionsY));
        root.setAttribute("showBigGrid", String.valueOf(showBigGrid));
        root.setAttribute("showSmallGrid", String.valueOf(showSmallGrid));
        root.setAttribute("showBigGridY", String.valueOf(showBigGridY));
        root.setAttribute("showSmallGridY", String.valueOf(showSmallGridY));
        root.setAttribute("color", String.valueOf(color.getRGB()));
        XMLOutputter outputter = new XMLOutputter();
        return outputter.outputString(root);
    }

    public void setSavedData(String data) {
        SAXBuilder db = new SAXBuilder();
        StringReader sr = new StringReader(data);
        Document doc;
        try {
            doc = db.build((Reader) sr);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Ha sucedido un error al recuperar la informacion de sesiones pasadas en defaultGrid",
                                          "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return;
        } catch (JDOMException ex) {
            JOptionPane.showMessageDialog(null,
                    "Ha sucedido un error al recuperar la informacion de sesiones pasadas en defaultGrid",
                                          "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return;
        }
        Element root = doc.getRootElement();
        try {
            Attribute att;
            att = root.getAttribute("color");
            if (att != null) {
                setColor(new Color(att.getIntValue()));
            }
            att = root.getAttribute("numberOfDivisions");
            if (att != null) {
                setNumberOfDivisions(att.getIntValue());
            }
            att = root.getAttribute("numberOfDivisionsY");
            if (att != null) {
                setNumberOfDivisionsY(att.getIntValue());
            }
            att = root.getAttribute("showBigGrid");
            if (att != null) {
                setShowBigGrid(att.getBooleanValue());
            }
            att = root.getAttribute("showBigGridY");
            if (att != null) {
                setShowBigGridY(att.getBooleanValue());
            }
            att = root.getAttribute("showSmallGrid");
            if (att != null) {
                setShowSmallGrid(att.getBooleanValue());
            }
            att = root.getAttribute("showSmallGridY");
            if (att != null) {
                setShowSmallGridY(att.getBooleanValue());
            }
            att = root.getAttribute("numberOfBigDivisionsX");
            if (att != null) {
                setNumberOfBigDivisionsX(att.getIntValue());
            }
            att = root.getAttribute("numberOfBigDivisionsY");
            if (att != null) {
                setNumberOfBigDivisionsY(att.getIntValue());
            }
        } catch (DataConversionException ex) {
            JOptionPane.showMessageDialog(null,
                    "Ha sucedido un error al recuperar la informacion de sesiones pasadas en defaultGrid",
                                          "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public int getNumberOfBigDivisionsX() {
        return numberOfBigDivisionsX;
    }

    public void setNumberOfBigDivisionsX(int numberOfBigDivisionsX) {
        this.numberOfBigDivisionsX = numberOfBigDivisionsX;
        refresh();
    }

    public int getNumberOfBigDivisionsY() {
        return numberOfBigDivisionsY;
    }

    public void setNumberOfBigDivisionsY(int numberOfBigDivisionsY) {
        this.numberOfBigDivisionsY = numberOfBigDivisionsY;
        refresh();
    }

}
