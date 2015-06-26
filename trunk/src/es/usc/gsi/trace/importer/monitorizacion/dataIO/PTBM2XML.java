package es.usc.gsi.trace.importer.monitorizacion.dataIO;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

import es.usc.gsi.trace.importer.Perfil.*;
import org.jdom.*;
import org.jdom.Attribute;
import org.jdom.filter.ContentFilter;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

class MyFloat {
    private static boolean hay_parser = false;
    private static DecimalFormat decimal_format;
    private static int numero_decimales_actuales = 1;
    public final static int MAS_INFINITO = Integer.MAX_VALUE / 1000,
    MENOS_INFINITO = Integer.MIN_VALUE / 1000;
    /**
     *
     */
    private static void contruyePraser() {
        if (!hay_parser) {
            Locale default_locale = Locale.getDefault();
            //Ponemos como localidad la inglesa, pa que pille . en vez de ,
            Locale.setDefault(new Locale("en", "GB"));
            decimal_format = new DecimalFormat("###.#");
            //Ahora que ya tengo un parseador "A la inglesa" volvemos pa espananha:
            Locale.setDefault(default_locale);
            hay_parser = true;
        }

    }

    /**
     *Se emplea para formatear los numeros que seguro estan bien,
     * es decir los que ya se han chequeado y se vuelven a mostrar al usuario.
     * @param numero
     * @return
     */
    public static float parseFloatSeguro(String numero) {
        if (!(numero.equals("&")) && !(numero.equals("-&"))) {
            return Float.parseFloat(numero);
        } else if (numero.equals("&")) {
            return Integer.MAX_VALUE / 1000;
        } else {
            return Integer.MIN_VALUE / 1000; //Por que si no en validar dan overflow
        }
    }

    /**
     *
     * @param numero
     * @return
     * @throws Exception
     */
    public static float parseFloat(String numero) throws Exception {
        try {
            if (!(numero.equals("&")) && !(numero.equals("-&"))) {
                return Float.parseFloat(numero);
            } else if (numero.equals("&")) {
                return MAS_INFINITO;
            } else {
                return MENOS_INFINITO; //Por que si no en validar dan overflow
            }
        } catch (NumberFormatException ex) {
            throw (new Exception("Numero Mal formado"));
        }
    }

    /**
     *
     * @param f
     * @return
     */
    public static String formateaNumero(float f) {
        //Si es infinito
        if (f == MAS_INFINITO) {
            return "&";
        } else if (f == MENOS_INFINITO) {
            return "-&";
        }

        return formateaNumero(Float.toString(f));
    }

    /**
     * Devuelve null si No se pudo completar la operacion
     * @param numero
     * @return
     */
    public static String formateaNumero(String numero) {
        if (!hay_parser) {
            contruyePraser();
        }
        numero = numero.trim();
        if (numero.equals("&") || numero.equals("-&")) {
            return numero;
        }

        try {
            String resultado = decimal_format.format(parseFloat(numero));
            return resultado;
        } catch (Exception ex) {
            return null;
        }
    }

    public static String toString(float numero) {
        return formateaNumero(numero);
    }

    /**
     *
     * @param M
     * @return
     */
    private static String convierteAString(float M) {
        if (M < MAS_INFINITO && M > MENOS_INFINITO) {
            return Float.toString(M);
        } else if (M == MENOS_INFINITO) {
            return "-&";
        } else {
            return "&";
        }
    }

    /**
     * Emplear para cambiar el numero de digitos decimales del patron
     * @param numero_decimaales
     */
    public static void setNumeroDecimales(int numero_decimales) {
        if (!hay_parser) {
            contruyePraser();
        }
        String pattern = "###";
        if (numero_decimales > 0) {
            pattern = pattern + ".";
            for (int i = 0; i < numero_decimales; i++) {
                pattern = pattern + "#";
            }

        }
        numero_decimales_actuales = numero_decimales;
        decimal_format.applyPattern(pattern);
    }

    /**
     *
     * @return
     */
    public static int getNumeroDecimalesActuales() {
        return numero_decimales_actuales;
    }
}


/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class PTBM2XML {

    private static PTBM2XML ptbm_2_XML = null;

    private PTBM2XML() {

        if (ptbm_2_XML != null) {
            System.out.println("ERROR, intanciado dos veces un singleton");
        }

    }

    /**
     * Dada una cadena de caracteres correspondiente a un archivo y un ptbm almacena el
     * objeto ptbm en el archivo correpondiente en formato XML.
     * @param ptbm
     * @param archivo
     * @return
     */
    public boolean GuardaPTBM(PTBMInterface ptbm, String archivo) {
        if (ptbm == null) {
            return true;
        }

        //Creo el documento XML con el nodo raiz
        Element root = new Element("PTBM");
        //  DocType doc_type = new DocType("PTBM","file:///C:/ptbm.dtd");
        Document documento = new Document(root /*,doc_type*/);
        root.setAttribute("NumeroPTB", ptbm.getPTB().length + "");
        root.setAttribute("ComentarioPTBM", ptbm.getComentario());
        root.setAttribute("NombrePTBM", ptbm.getTitulo());
        //Bucle que ira anhadiendo los nodos PTB
        PTBInterface[] ptb_array = ptbm.getPTB();
        for (int i = 0; i < ptb_array.length; i++) {
            //Creo el nodo PTB con sus atributos
            Element ptb = new Element("PTB");
            ptb.setAttribute("NombrePTB", ptb_array[i].getNombre());
            ptb.setAttribute("NumeroPtoSig",
                             ptb_array[i].getNumeroDePtoSig() + "");
            ptb.setAttribute("ComentarioPTB", ptb_array[i].getComentario());
            ptb.setAttribute("Unidades", ptb_array[i].getUnidades());
            ptb.setAttribute("Parametro", ptb_array[i].getParametro());
            ptb.setAttribute("UnidadesTemporales",
                             ptb_array[i].getUnidadesTemporales());
            ptb.setAttribute("BuscarEnValorAbsoluto",
                             ptb_array[i].isBuscarEnValorAbsoluto() + "");
            //Anhado el nodo creado a la raiz del documebnto
            root.addContent(ptb);
            //Continuo Anhadiendo los hijos al nodo PTB
            PtoSigInterface[] pto_sig_array = ptb_array[i].getPtoSig();
            for (int j = 0; j < pto_sig_array.length; j++) {
                //Creo el nodo PtoSig con sus atributos
                Element pto_sig = new Element("PtoSig");
                Restriccion[] restricciones_array = pto_sig_array[j].
                        getRestricciones();
                pto_sig.setAttribute("NumeroDePtoSig", j + "");
                pto_sig.setAttribute("NumeroDeRestricciones",
                                     restricciones_array.length + "");
                //Anhado el nodo creado al nodo del cual culega
                ptb.addContent(pto_sig);
                //Continuo anhadiendo los nodos hijos
                for (int k = 0; k < restricciones_array.length; k++) {
                    //Creo el nodo restriicon con sus atributos
                    Element restriccion = new Element("Restriccion");
                    restriccion.setAttribute("PTBOrigen",
                                             restricciones_array[k].
                                             getNumeroDePTB() +
                                             "");
                    restriccion.setAttribute("PtoSigOrigen",
                                             restricciones_array[k].
                                             getNumeroDePtoSig() + "");
                    restriccion.setAttribute("PTBDestino", i + "");
                    restriccion.setAttribute("PtoSigDestino", j + "");
                    restriccion.setAttribute("RelativaAlBasal",
                                             restricciones_array[k].
                                             isRelativaAlNivelBasal() + "");
                    //Anhado el nod ala padre
                    pto_sig.addContent(restriccion);
                    //Continuo anhadiendole los hijos al nodo
                    String[] D = restricciones_array[k].getD();
                    String[] L = restricciones_array[k].getL();
                    String[] M = restricciones_array[k].getM();
                    int sintaxis_int = restricciones_array[k].getSemantica();
                    int cunatificadorInt = restricciones_array[k].
                                           getCuantificadorSemantica();
                    int unidadesTemporalesInt = restricciones_array[k].
                                                getUnidadesTemporales();
                    float magnitudPrimerPtoSigFloat = restricciones_array[k].
                            getMagnitudPrimerPtoSig();
                    float magnitudSegundoPtoSigFloat = restricciones_array[k].
                            getMagnitudSegundoPtoSig();
                    int distanciaTemporalEntrePtoSig = restricciones_array[k].
                            getDistanciaTemporalEntrePtoSig();

                    //Elemento restricion magnitud:
                    Element restriccon_magnitud = new Element("Magnitud");
                    restriccon_magnitud.setAttribute("InicioSoporte", D[0]);
                    restriccon_magnitud.setAttribute("InicioCore", D[1]);
                    restriccon_magnitud.setAttribute("FinCore", D[2]);
                    restriccon_magnitud.setAttribute("FinSoporte", D[3]);
                    //Anhadimos este elemnto al padre:
                    restriccion.addContent(restriccon_magnitud);

                    //Elemento restricion Temporal:
                    Element restriccon_temporal = new Element("Temporal");
                    restriccon_temporal.setAttribute("InicioSoporte", L[0]);
                    restriccon_temporal.setAttribute("InicioCore", L[1]);
                    restriccon_temporal.setAttribute("FinCore", L[2]);
                    restriccon_temporal.setAttribute("FinSoporte", L[3]);
                    //Anhadimos este elemnto al padre:
                    restriccion.addContent(restriccon_temporal);

                    //Elemento unidades temporales:
                    Element unidadesTemporales = new Element(
                            "UnidadesTemporales");
                    unidadesTemporales.setAttribute("Tipo",
                            "" + unidadesTemporalesInt);
                    restriccion.addContent(unidadesTemporales);
                    //Elemento restricion Pendiente:
                    Element restriccon_pendiente = new Element("Pendiente");
                    restriccon_pendiente.setAttribute("InicioSoporte", M[0]);
                    restriccon_pendiente.setAttribute("InicioCore", M[1]);
                    restriccon_pendiente.setAttribute("FinCore", M[2]);
                    restriccon_pendiente.setAttribute("FinSoporte", M[3]);
                    //Anhadimos este elemnto al padre:
                    restriccion.addContent(restriccon_pendiente);

                    //Semantica
                    Element semantica = new Element("Semantica");
                    semantica.setAttribute("Tipo", sintaxis_int + "");
                    semantica.setAttribute("Cuantificador",
                                           cunatificadorInt + "");
                    restriccion.addContent(semantica);

                    //Informacion para ayudar al usuario
                    Element infoUsuario = new Element("InformacionUsuario");
                    infoUsuario.setAttribute("MagnitudPrimerPtoSig",
                                             "" + magnitudPrimerPtoSigFloat);
                    infoUsuario.setAttribute("MagnitudSegundoPtoSig",
                                             "" + magnitudSegundoPtoSigFloat);
                    infoUsuario.setAttribute("DistanciaTemporal",
                                             distanciaTemporalEntrePtoSig + "");
                    restriccion.addContent(infoUsuario);
                }

            }
            //Distancia de separacion entre PTB y longitud de la ventana temporal
            Element distanciaEntrePTB = new Element("DistanciaEntrePTB");
            distanciaEntrePTB.setAttribute("InicioSoporte",
                                           "" +
                                           ptb_array[i].
                                           getIntInicioSoporteSeparacion());
            distanciaEntrePTB.setAttribute("InicioCore",
                                           "" +
                                           ptb_array[i].
                                           getIntInicioCoreSeparacion());
            distanciaEntrePTB.setAttribute("FinCore",
                                           "" +
                                           ptb_array[i].getIntFinCoreSeparacion());
            distanciaEntrePTB.setAttribute("FinSoporte",
                                           "" +
                                           ptb_array[i].
                                           getIntFinSoporteSeparacion());
            ptb.addContent(distanciaEntrePTB);
            Element longitudVentanaTemporal = new Element(
                    "LongitudVentanaTemporal");
            longitudVentanaTemporal.setAttribute("LongitudVentanaTemporal",
                                                 "" +
                                                 ptb_array[i].
                                                 getLongitudVentana());
            ptb.addContent(longitudVentanaTemporal);
        }
        Element distanciaEntrePTBM = new Element("DistanciaEntrePTBM");
        distanciaEntrePTBM.setAttribute("InicioSoporte",
                                        "" + ptbm.getInicioSoporteSeparacion());
        distanciaEntrePTBM.setAttribute("InicioCore",
                                        "" + ptbm.getInicioCoreSeparacion());
        distanciaEntrePTBM.setAttribute("FinCore",
                                        "" + ptbm.getFinCoreSeparacion());
        distanciaEntrePTBM.setAttribute("FinSoporte",
                                        "" + ptbm.getFinSoporteSeparacion());
        root.addContent(distanciaEntrePTBM);
        //Todo el documento deberia estar creado, asi que vamos a Almacenarlo:
        XMLOutputter xml_outputter = new XMLOutputter();
        File file = new File(archivo);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            return false;
        }
        try {
            xml_outputter.output(documento, out);
            return true;
        } catch (IOException ex) {
            return false;
        }

    }


    /**
     * Dada una cadena de caracteres que representa un archivo devuelve el PTBM contenido
     * en el archivo. En este debe haber una PTBM en formato XML.
     * @param archivo
     * @return
     */
    public PTBMInterface cargaPTBM(String archivo) {
        //Cargamos el documento validando
        SAXBuilder bulder = new SAXBuilder( /*true*/false);
        Document documento = null;
        try {
            documento = bulder.build(archivo);
        } catch (IOException ex3) {
            ex3.printStackTrace();
        } catch (JDOMException ex3) {
            ex3.printStackTrace();
        }

        //Obtenemos el elemento raiz, el PTBM, y cojemos sus atributos.
        Element root = documento.getRootElement();
        String comentarioPTBM = root.getAttribute("ComentarioPTBM").getValue();
        String nombrePTBM = root.getAttribute("NombrePTBM").getValue();
        //Empleamos un filtro para deshacernos de los nodos de texto
        ContentFilter filtro = new ContentFilter(false);
        filtro.setElementVisible(true);

        List lista_ptb = root.getContent(filtro);
        Iterator it = lista_ptb.iterator();
        //Empleamos un floag para marcar el primer PTB, que se le pasara en el constructor
        //Al PTBM, de los demas que se le anhadiran
        PTBM ptbm = null;
        boolean primer_ptb = true;
        int num_PTB = -1;
        Element ultimoNodoDelPTBM = null;
        while (it.hasNext()) {
            //Contador de PTB
            num_PTB++;
            //Cojo el primer elemento ptb y extraigo sus atributos
            Element ptb_xml = (Element) it.next();
            Attribute comentario = ptb_xml.getAttribute("ComentarioPTB");
            //Si no hay comentario => es ya la separacion entre PTB
            if (comentario == null) {
                //Este nodo realemente es la distancia
                ultimoNodoDelPTBM = ptb_xml;
                break;
            }
            String comentario_PTB = comentario.getValue();
            String parametro = ptb_xml.getAttribute("Parametro").getValue();
            String unidades = ptb_xml.getAttribute("Unidades").getValue();
            String unidades_temporales = ptb_xml.getAttribute(
                    "UnidadesTemporales").getValue();
            String nombre_PTB = ptb_xml.getAttribute("NombrePTB").getValue();
            //Miro si hay atributo de usar en valor absoluto
            Attribute atrUsarEnValorAbsoluto = ptb_xml.getAttribute(
                    "BuscarEnValorAbsoluto");
            boolean usarEnValorAbsoluto = false;
            if (atrUsarEnValorAbsoluto != null) {
                try {
                    usarEnValorAbsoluto = atrUsarEnValorAbsoluto.
                                          getBooleanValue();
                } catch (DataConversionException ex2) {
                    ex2.printStackTrace();
                }
            }
            //Creo el PTB
            PTB ptb = new PTB(nombre_PTB, parametro, unidades,
                              unidades_temporales, comentario_PTB, num_PTB);
            ptb.setBuscarEnValorAbsoluto(usarEnValorAbsoluto);
            if (primer_ptb) {
                ptbm = new PTBM(nombrePTBM, comentarioPTBM, ptb);
                primer_ptb = false;
            } else {
                ptbm.anhadePTB(ptb, num_PTB, PTBM.ANHADIR);
            }

            //Comenzamos a RECOPILAR PUNTOS SIGNIFICATIVOS:
            List lista_PtoSig = ptb_xml.getContent(filtro);
            int num_PtoSig = -1;
            Iterator it2 = lista_PtoSig.iterator();
            while (it2.hasNext()) {
                num_PtoSig++;
                Element PtoSig_xml = (Element) it2.next();
                //Los puntos significativos = y ! se anhaden automaticamente al PTB, solo tendremos
                //que anhadir las restricciones que tengan
                if (num_PtoSig < 2) {
                    if (PtoSig_xml.getChildren().size() == 0) {
                        continue;
                    }

                }
                Restriccion[] restriciones_de_un_PToSig = generaRestricciones(
                        PtoSig_xml);
                for (int i = 0; i < restriciones_de_un_PToSig.length; i++) {
                    //Si es el primer o segundo Pto Sig ya esta anhadido al PTB
                    if (num_PtoSig < 2) {
                        ptb.anhadeRestriccion(0, num_PtoSig,
                                              restriciones_de_un_PToSig[i], null,
                                              PTBM.ANHADIR);
                    }
                    //Si no lo era y si es la primera restriccion => preimero anhadir el PtoSig al PTB:
                    else if (i == 0) {
                        PtoSig pto_sig = new PtoSig(restriciones_de_un_PToSig[0],
                                num_PTB, num_PtoSig);
                        ptb.anhadePtoSig(pto_sig);
                    }
                    //Si no es que ya esta anhadido el PtoSig => anhadimos solo la restricion
                    else {
                        ptb.anhadeRestriccion(num_PTB, num_PtoSig,
                                              restriciones_de_un_PToSig[i], null,
                                              PTBM.ANHADIR);
                    }
                }
            }
            //Leemos la distancia entre PTB y la ventana temporal
            Element distanciaEntrePTB_XML = ptb_xml.getChild(
                    "DistanciaEntrePTB");
            Element ventanaTemporal_XML = ptb_xml.getChild(
                    "LongitudVentanaTemporal");

            //Por motivos de compatibilidad hacia atas chequeamos si este PYB
            //No tiene asociada esta informacion
            if (distanciaEntrePTB_XML == null || ventanaTemporal_XML == null) {
                continue;
            }
            try {
                ptb.setIntInicioSoporteSeparacion(distanciaEntrePTB_XML.
                                                  getAttribute("InicioSoporte").
                                                  getFloatValue());
                ptb.setIntInicioCoreSeparacion(distanciaEntrePTB_XML.
                                               getAttribute("InicioCore").
                                               getFloatValue());
                ptb.setIntFinCoreSeparacion(distanciaEntrePTB_XML.getAttribute(
                        "FinCore").getFloatValue());
                ptb.setIntFinSoporteSeparacion(distanciaEntrePTB_XML.
                                               getAttribute("FinSoporte").
                                               getFloatValue());
                ptb.setLongitudVentana(ventanaTemporal_XML.getAttribute(
                        "LongitudVentanaTemporal").getFloatValue());
            } catch (DataConversionException ex1) {
                ex1.printStackTrace();
            }
        }
        //Leemos la distancia entre PTB y la ventana temporal
        Element distanciaEntrePTBM_XML = ultimoNodoDelPTBM; //root.getChild("DistanciaEntrePTBM");
        //Por motivos de compatibilidad hacia atas chequeamos si este PTBM
        //No tiene asociada esta informacion
        if (distanciaEntrePTBM_XML == null) {
            return ptbm;
        }

        try {
            ptbm.setInicioSoporteSeparacion(distanciaEntrePTBM_XML.getAttribute(
                    "InicioSoporte").getFloatValue());
            ptbm.setInicioCoreSeparacion(distanciaEntrePTBM_XML.getAttribute(
                    "InicioCore").getFloatValue());
            ptbm.setFinCoreSeparacion(distanciaEntrePTBM_XML.getAttribute(
                    "FinCore").getFloatValue());
            ptbm.setFinSoporteSeparacion(distanciaEntrePTBM_XML.getAttribute(
                    "FinSoporte").getFloatValue());
        } catch (DataConversionException ex1) {
            ex1.printStackTrace();
        }

        return ptbm;
    }

    /**
     * Devuelve un array con las restricciones de un Nodo Ptuo significativo.
     * @param PtoSig_xml
     * @return
     */
    private Restriccion[] generaRestricciones(Element PtoSig_xml) {
        //Obtengo la lista de elementos restricciones
        //Empleamos un filtro para deshacernos de los nodos de texto
        ContentFilter filtro = new ContentFilter(false);
        filtro.setElementVisible(true);
        List lista_restriciones = PtoSig_xml.getContent(filtro);
        Iterator it = lista_restriciones.iterator();
        Restriccion[] restriccion_array = new Restriccion[lista_restriciones.
                                          size()];
        int num_restriciones = -1;
        while (it.hasNext()) {
            num_restriciones++;
            Element restriccion_xml = (Element) it.next();
            //Obtenemos los atributos de la restriccion
            int ptb_origen, PtoSig_origen, ptb_destino, Ptosig_destino;
            boolean relativaAlBasal = false;
            try {
                ptb_origen = restriccion_xml.getAttribute("PTBOrigen").
                             getIntValue();
                PtoSig_origen = restriccion_xml.getAttribute("PtoSigOrigen").
                                getIntValue();
                ptb_destino = restriccion_xml.getAttribute("PTBDestino").
                              getIntValue();
                Ptosig_destino = restriccion_xml.getAttribute("PtoSigDestino").
                                 getIntValue();
                Attribute atributoBoolean = restriccion_xml.getAttribute(
                        "RelativaAlBasal");
                if (atributoBoolean != null) {
                    relativaAlBasal = atributoBoolean.getBooleanValue();
                }
            } catch (DataConversionException ex) {
                return null;
            }

            //Empzamos a procesar las restricciones
            String[] D = new String[4], L = new String[4], M = new String[4];
            int sintaxis_int, unidadesTemporales, cunatificadoSemantica,
                    distanciaEntrePtoSig;
            float magnitudPrimerPtoSig, magnitudSegundoPtoSig;
            Element magnitud = restriccion_xml.getChild("Magnitud");
            Element temporal = restriccion_xml.getChild("Temporal");
            Element unidadesTemporalesElemento = restriccion_xml.getChild(
                    "UnidadesTemporales");
            Element pendiente = restriccion_xml.getChild("Pendiente");
            Element sintaxis = restriccion_xml.getChild("Semantica");
            Element infoUsuario = restriccion_xml.getChild("InformacionUsuario");
            try {
                D[0] = magnitud.getAttribute("InicioSoporte").getValue();
                D[1] = magnitud.getAttribute("InicioCore").getValue();
                D[2] = magnitud.getAttribute("FinCore").getValue();
                D[3] = magnitud.getAttribute("FinSoporte").getValue();

                L[0] = temporal.getAttribute("InicioSoporte").getValue();
                L[1] = temporal.getAttribute("InicioCore").getValue();
                L[2] = temporal.getAttribute("FinCore").getValue();
                L[3] = temporal.getAttribute("FinSoporte").getValue();

                M[0] = pendiente.getAttribute("InicioSoporte").getValue();
                M[1] = pendiente.getAttribute("InicioCore").getValue();
                M[2] = pendiente.getAttribute("FinCore").getValue();
                M[3] = pendiente.getAttribute("FinSoporte").getValue();

                sintaxis_int = sintaxis.getAttribute("Tipo").getIntValue();
                Attribute cunatificadoSemanticaAtr = sintaxis.getAttribute(
                        "Cuantificador");
                //Chequeo introduciodo para mantener la compatibilidad hacia atras
                if (cunatificadoSemanticaAtr != null) {
                    cunatificadoSemantica = cunatificadoSemanticaAtr.
                                            getIntValue();
                } else {
                    cunatificadoSemantica = Restriccion.CUANTIFICADOR_TODO;
                }
                if (unidadesTemporalesElemento != null) {
                    unidadesTemporales = unidadesTemporalesElemento.
                                         getAttribute("Tipo").getIntValue();
                } else {
                    //Asumimos que es un fichero antiguo y que estab en segundos
                    unidadesTemporales = Restriccion.UNIDADES_SEGUNDOS;
                    //Pasamos a milisegundos la restriccion
                    for (int i = 0; i < L.length; i++) {
                        L[i] = MyFloat.parseFloatSeguro(L[i]) * 1000 + "";
                        M[i] = MyFloat.parseFloatSeguro(M[i]) / 1000 + "";
                    }

                }
                //Infomacion del usuario
                if (infoUsuario != null) {
                    magnitudPrimerPtoSig = infoUsuario.getAttribute(
                            "MagnitudPrimerPtoSig").getFloatValue();
                    magnitudSegundoPtoSig = infoUsuario.getAttribute(
                            "MagnitudSegundoPtoSig").getFloatValue();
                    distanciaEntrePtoSig = infoUsuario.getAttribute(
                            "DistanciaTemporal").
                                           getIntValue();
                } else {
                    magnitudPrimerPtoSig = 0;
                    magnitudSegundoPtoSig = 0;
                    distanciaEntrePtoSig = 0;

                }

            } catch (DataConversionException ex) {
                return null;
            }
            //Creamos la restriccion con la informacion
            restriccion_array[num_restriciones] = new Restriccion(ptb_origen,
                    PtoSig_origen,
                    D, L, M, sintaxis_int, cunatificadoSemantica,
                    unidadesTemporales);
            restriccion_array[num_restriciones].setMagnitudPrimerPtoSig(
                    magnitudPrimerPtoSig);
            restriccion_array[num_restriciones].setMagnitudSegundoPtoSig(
                    magnitudSegundoPtoSig);
            restriccion_array[num_restriciones].setDistanciaTemporalEntrePtoSig(
                    distanciaEntrePtoSig);
            restriccion_array[num_restriciones].setRelativaAlNivelBasal(
                    relativaAlBasal);
        }

        return restriccion_array;
    }

    public static void main(String[] args) {
        PTBM2XML PTBM2XML1 = null;

        PTBM2XML1 = new PTBM2XML();

        javax.swing.JFileChooser fch = new javax.swing.JFileChooser();
        fch.showOpenDialog(null);
        es.usc.gsi.trace.importer.Perfil.auxiliares.Serializador serial = new
                es.usc.gsi.trace.importer.Perfil.auxiliares.Serializador();

        /*
         PTBMInterface ptbm = serial.cargaPTBM(fch.getSelectedFile().toString());

             PTBM2XML1.GuardaPTBM(ptbm, "C:/ptbm");
         */
        /**/
        PTBM2XML1.GuardaPTBM(PTBM2XML1.cargaPTBM("C:/ptbm"), "C:/ptbm2");
        /**/
    }

    public static PTBM2XML getIntsancia() {
        if (ptbm_2_XML == null) {
            ptbm_2_XML = new PTBM2XML();
            return ptbm_2_XML;
        }
        return ptbm_2_XML;
    }
}
