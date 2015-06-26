package net.javahispano.jsignalwb.jsignalmonitor.printsupport;

import java.awt.*;
import java.awt.print.*;

import javax.swing.RepaintManager;


/**
 * <p>Title: Herraienta de monitorizacion</p>
 *
 * <p>Description: Pequenha clase de utilidad para imprimir componentes. La clase
 * muestra el cuadro de dialogo de impresion estandar de la plataforma y
 * permitira al usuario imprimir el componente que se le paso como parametro.
 * Es posible configurar la orientacion y tamanho por defecto del papel aunque,
 * habitualmente, el cuadro de dialogo que se presentara al usuario permitira
 * cambiar estos parametros.
 * </p>
 *
 * <p>Copyright: Copyright (c) Abraham Otero y Roman Segador 2007. este codigo se distribuye
 * bajo licencia Apache 2.0</p>
 *
 * @version 1.0
 */
public class PrintUtilities implements Printable {
    private Component componentToBePrinted;
    private int defaultPaperHeight = 849; //A4
    private int defaultPaperWidht = 597; //A4
    private int orientation = PageFormat.PORTRAIT; //por defecto se imprime en vertical

    /**
     * modo de impresion: LANDSCAPE
     */
    public static final int LANDSCAPE = PageFormat.LANDSCAPE;
    /**
     * modo de impresion: PORTRAIT
     */
    public static final int PORTRAIT = PageFormat.PORTRAIT;
    /**
     * modo de impresion: REVERSE_LANDSCAPE
     */
    public static final int REVERSE_LANDSCAPE = PageFormat.REVERSE_LANDSCAPE;


    /**
     * construye un objeto de tipo PrintUtilities
     *
     * @param componentToBePrinted componente que deseamos que imprima.
     */
    public PrintUtilities(Component componentToBePrinted) {
        this.componentToBePrinted = componentToBePrinted;
    }

    /**
     * Imprime el componente que se le pasa como parametro.
     * Mostrara el cuadro de dialogo de impresion estandar de la plataforma y
     * permitira al usuario imprimir el componente que se le paso como parametro.
     *
     * @param componentToBePrinted Componente a imprimir
     * @throws PrinterException
     */
    public static void printComponent(Component componentToBePrinted) throws
            PrinterException {
        new PrintUtilities(componentToBePrinted).print();
    }


    /**
     * Imprime el componente que se le pasa como parametro. Mostrara el cuadro de
     * dialogo de impresion estandar de la plataforma y permitira al usuario
     * imprimir el componente que se le paso como parametro.
     *
     * @param componentToBePrinted Componente a imprimir
     * @param orientation orientacion con la que imprime
     * @throws PrinterException
     */
    public static void printComponent(Component componentToBePrinted, int orientation) throws PrinterException {
        PrintUtilities p = new PrintUtilities(componentToBePrinted);
        p.setOrientation(orientation);
        p.print();
    }


    /**
     * Imprime el componente que se le pasa como parametro. Mostrara el cuadro de
     * dialogo de impresion estandar de la plataforma y permitira al usuario
     * imprimir el componente que se le paso como parametro.
     *
     * @param componentToBePrinted Componente a imprimir
     * @param orientation orientacion con la que imprime por defecto
     * @param height altura por defecto del papel
     * @param width ancho por defecto del papel
     * @throws PrinterException
     */
    public static void printComponent(Component componentToBePrinted, int orientation, int height, int width) throws
            PrinterException {
        PrintUtilities p = new PrintUtilities(componentToBePrinted);
        p.setOrientation(orientation);
        p.setDefaultPaperHeight(height);
        p.setDefaultPaperWidht(width);
        p.print();
    }

    /**
     * Imprime el correspondiente componente.
     * Mostrara el cuadro de dialogo de impresion estandar de la plataforma.
     *
     * @throws PrinterException
     */
    public void print() throws PrinterException {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = new PageFormat();
        pageFormat.setOrientation(orientation);
        Paper paper = new Paper();
        paper.setSize(defaultPaperWidht, defaultPaperHeight);
        pageFormat.setPaper(paper);
        printJob.setPrintable(this, pageFormat);
        if (printJob.printDialog()) {
            printJob.print();
        }
    }

    /**
     * The <code>Printable</code> interface is implemented
     * by the <code>print</code> methods of the current
     * page painter, which is called by the printing
     * system to render a page.  When building a
     * {@link Pageable}, pairs of {@link PageFormat}
     * instances and instances that implement
     * this interface are used to describe each page. The
     * instance implementing <code>Printable</code> is called to
     * print the page's graphics.
     * <p>
     * A <code>Printable(..)</code> may be set on a <code>PrinterJob</code>.
     * When the client subsequently initiates printing by calling
     * <code>PrinterJob.print(..)</code> control
     * <p>
     * is handed to the printing system until all pages have been printed.
     * It does this by calling <code>Printable.print(..)</code> until
     * all pages in the document have been printed.
     * In using the <code>Printable</code> interface the printing
     * commits to image the contents of a page whenever
     * requested by the printing system.
     * <p>
     * The parameters to <code>Printable.print(..)</code> include a
     * <code>PageFormat</code> which describes the printable area of
     * the page, needed for calculating the contents that will fit the
     * page, and the page index, which specifies the zero-based print
     * stream index of the requested page.
     * <p>
     * For correct printing behaviour, the following points should be
     * observed:
     * <ul>
     * <li> The printing system may request a page index more than once.
     * On each occasion equal PageFormat parameters will be supplied.
     *
     * <li>The printing system will call <code>Printable.print(..)</code>
     * with page indexes which increase monotonically, although as noted above,
     * the <code>Printable</code> should expect multiple calls for a page index
     * and that page indexes may be skipped, when page ranges are specified
     * by the client, or by a user through a print dialog.
     *
     * <li>If multiple collated copies of a document are requested, and the
     * printer cannot natively support this, then the document may be imaged
     * multiple times. Printing will start each copy from the lowest print
     * stream page index page.
     *
     * <li>With the exception of re-imaging an entire document for multiple
     * collated copies, the increasing page index order means that when
     * page N is requested if a client needs to calculate page break position,
     * it may safely discard any state related to pages < N, and make current
     * that for page N. "State" usually is just the calculated position in the
     * document that corresponds to the start of the page.
     *
     * <li>When called by the printing system the <code>Printable</code> must
     * inspect and honour the supplied PageFormat parameter as well as the
     * page index.
     * This is key to correct printing behaviour, and it has the
     * implication that the client has the responsibility of tracking
     * what content belongs on the specified page.
     *
     * <li>When the <code>Printable</code> is obtained from a client-supplied
     * <code>Pageable</code> then the client may provide different PageFormats
     * for each page index. Calculations of page breaks must account for this.
     * </ul>
     * <p>
     * @see java.awt.print.Pageable
     * @see java.awt.print.PageFormat
     * @see java.awt.print.PrinterJob
     */
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        } else {
            disableDoubleBuffering(componentToBePrinted);
            Graphics2D g2d = (Graphics2D) g;
            //intento frustrado de rotar
            //  g2d.rotate(Math.PI/2);
            //g2d.translate(0,-componentToBePrinted.getHeight());

            //movemos el grafico al area donde se puede imprimir
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            float xRatio = ((float) pageFormat.getImageableWidth()) / componentToBePrinted.getWidth();
            float yRatio = ((float) pageFormat.getImageableHeight()) / componentToBePrinted.getHeight();
            float ratio = Math.min(xRatio, yRatio); //para no distorsionar la imagen
            g2d.scale(ratio, ratio);

            componentToBePrinted.paint(g2d);
            enableDoubleBuffering(componentToBePrinted);
            return PAGE_EXISTS;
        }
    }


    private static void disableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(false);
    }


    private static void enableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(true);
    }


    /**
     * getDefaultPaperHeight
     *
     * @return Altura del papel por defecto
     */
    public int getDefaultPaperHeight() {
        return defaultPaperHeight;
    }


    /**
     * getDefaultPaperWidht
     *
     * @return ancho del papel por defecto
     */
    public int getDefaultPaperWidht() {
        return defaultPaperWidht;
    }


    /**
     * getOrientation
     *
     * @return Orientacion del papel por defecto
     */
    public int getOrientation() {
        return orientation;
    }


    /**
     * setDefaultPaperWidht
     *
     * @param defaultPaperWidht ancho del papel por defecto
     */
    public void setDefaultPaperWidht(int defaultPaperWidht) {
        this.defaultPaperWidht = defaultPaperWidht;
    }


    /**
     * setDefaultPaperHeight
     *
     * @param defaultPaperHeight altura del papel por defecto
     */
    public void setDefaultPaperHeight(int defaultPaperHeight) {
        this.defaultPaperHeight = defaultPaperHeight;
    }


    /**
     * setOrientation
     *
     * @param orientation Orientacion del papel por defecto
     */
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
}
