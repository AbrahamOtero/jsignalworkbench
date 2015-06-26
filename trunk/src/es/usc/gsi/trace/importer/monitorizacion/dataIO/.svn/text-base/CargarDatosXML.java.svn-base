package es.usc.gsi.trace.importer.monitorizacion.dataIO;


import java.io.*;
import java.util.*;

import es.usc.gsi.trace.importer.Perfil.PTBMInterface;
import es.usc.gsi.trace.importer.estadisticos.*;
import es.usc.gsi.trace.importer.jsignalmonold.SamplesToDate;
import es.usc.gsi.trace.importer.jsignalmonold.annotations.*;
import es.usc.gsi.trace.importer.monitorizacion.data.AlmacenDatosFloat;
import es.usc.gsi.trace.importer.monitorizacion.data.GestorDatos;
import org.jdom.*;
import org.jdom.filter.ContentFilter;
import org.jdom.input.SAXBuilder;

/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class CargarDatosXML extends CargarDatos {

    private byte[] pos_gloabal;

    public CargarDatosXML(String archivo) throws JDOMException {
        super(archivo);
        cargaDatos();

    }

    private void cargaDatos() throws JDOMException {
        //Generamos el path de este archivo, lo necesitaremos para mas tarde:
        String arcivo_monitorizacion_path = (new File(archivo)).getParent() +
                                            File.separator;
        //Cargamos el documento validando
        SAXBuilder bulder = new SAXBuilder(false /*true*/);
        Document documento = null;
        try {
            documento = bulder.build(archivo);
        } catch (IOException ex1) {
            ex1.printStackTrace();
        } catch (JDOMException ex) {
            throw (ex);
        }
        //Obtenemos el elemento raiz, el PTBM, y cojemos sus atributos.
        Element root = documento.getRootElement();
        String fecha_base = root.getAttributeValue("FechaBase");
        String tiene_pos_glogal_string = root.getAttributeValue(
                "TienePosAsociada");
        boolean tiene_pos_gloga = false;
        if (tiene_pos_glogal_string.equals("true")) {
            tiene_pos_gloga = true;
        }

        String fichero_PTBM = root.getChildText("PTBM");
        //Empleamos un filtro para deshacernos de los nodos de texto
        ContentFilter filtro = new ContentFilter(false);
        filtro.setElementVisible(true);
        List lista_senales = root.getChildren("Senal");
        Iterator it = lista_senales.iterator();
        //Defino las extructuras que voy a emplear para almacenar la informacion
        int num_senales = lista_senales.size();
        float[][] datos = new float[num_senales][];
        byte[][] pos = new byte[num_senales][];
        float[][] rango;
        if (tiene_pos_gloga) {
            rango = new float[num_senales + 1][2];
            rango[num_senales][0] = 0;
            rango[num_senales][1] = 100;
        } else {
            rango = new float[num_senales][2];
        }

        float[] fs = new float[num_senales];
        String[] nombre_senales = new String[num_senales];
        String[] leyenda_temporal = new String[num_senales];
        String[] leyendas = new String[num_senales];
        TreeSet[] marcas = new TreeSet[num_senales];
        for (int i = 0; i < marcas.length; i++) {
            marcas[i] = new TreeSet();
        }

        boolean[] tien_pos_asociada = new boolean[num_senales];
        String nombre_archivo_senal;

        //Primero cargaremos la configuracion de las senhales
        int cont_senal = -1;
        while (it.hasNext()) {
            cont_senal++;
            Element senal_xml = (Element) it.next();
            nombre_senales[cont_senal] = senal_xml.getAttribute("NombreSenal").
                                         getValue();
            leyenda_temporal[cont_senal] = senal_xml.getAttribute(
                    "LeyendaTemporal").getValue();
            leyendas[cont_senal] = senal_xml.getAttribute("Leyenda").getValue();

            try {
                rango[cont_senal][0] = senal_xml.getAttribute("Minimo").
                                       getFloatValue();
                rango[cont_senal][1] = senal_xml.getAttribute("Maximo").
                                       getFloatValue();
                fs[cont_senal] = senal_xml.getAttribute("Fs").getFloatValue();
            } catch (DataConversionException ex) {
                System.out.println(
                        "Exception de converision en JDOM sin procesar");
            }
            String tien_pos_string = senal_xml.getAttribute(
                    "TienePosibilidadAsociada").getValue().trim();
            if (tien_pos_string.equals("true")) {
                tien_pos_asociada[cont_senal] = true;
            } else {
                tien_pos_asociada[cont_senal] = false;
            }
            //Ahora leemos las marcas de esta senhal
            List lista_marcas = senal_xml.getChildren("Marca");
            Iterator it2 = lista_marcas.iterator();
            while (it2.hasNext()) {
                Element marca_xml = (Element) it2.next();
                String texto_marca = marca_xml.getAttributeValue("Texto");
                String comentaio_marca = marca_xml.getAttributeValue(
                        "Comentario");
                int timepo_marca = 0;
                try {
                    timepo_marca = marca_xml.getAttribute("InstanteInicio").
                                   getIntValue();
                } catch (DataConversionException ex) {
                    System.out.println(
                            "Exception de converision en JDOM sin procesar");
                }
                Mark marca = new Mark();
                marca.setTexto(texto_marca);
                marca.setComentario(comentaio_marca);
                marca.setTiempo(timepo_marca);
                marcas[cont_senal].add(marca);
            }
        }

        //Ahora cargamos los datos
        nombre_archivo_senal = root.getChildText("FicheroDatos");
        nombre_archivo_senal = arcivo_monitorizacion_path +
                               (new File(nombre_archivo_senal)).getName();
        cargaDatos(nombre_archivo_senal, num_senales, tien_pos_asociada, datos,
                   pos, tiene_pos_gloga);

        //Crgamos el PTBM. si no habia ptbm lo ponemos a  null
        PTBMInterface ptbm;
        if (fichero_PTBM != null) {
            //Para cargar empleo path relativo, aunque para almacenar no
            fichero_PTBM = (new File(fichero_PTBM)).getName();
            fichero_PTBM = arcivo_monitorizacion_path + fichero_PTBM;
            ptbm = PTBM2XML.getIntsancia().cargaPTBM(fichero_PTBM);
        } else {
            ptbm = null;
        }
        //Ahora nos quedan las anotaciones
        TreeSet tree_set = new TreeSet();
        List lista_terapia_xml = root.getChildren("Terapia");
        LinkedList lista_terapia = genraAnotacionesTerapia(lista_terapia_xml);
        List lista_diagnostico_xml = root.getChildren("Diagnostico");
        LinkedList lista_diagnostico = genraAnotacionesDiagnostico(
                lista_diagnostico_xml);
        List lista_manifestacion_xml = root.getChildren("Manifestacion");
        LinkedList lista_manifestacion = genraAnotacionesManifestacion(
                lista_manifestacion_xml);
        tree_set.addAll(lista_diagnostico);
        tree_set.addAll(lista_manifestacion);
        tree_set.addAll(lista_terapia);

        GestorDatos.getInstancia().setAlmacen(almacen);
        almacen = new AlmacenDatosFloat(datos, pos, tree_set, marcas, ptbm);
        almacen.setAnotaciones(tree_set);
        almacen.setMarcas(marcas);
        almacen.setRango(rango);
        for (int i = 0; i < num_senales; i++) {
            almacen.setNombreSenal(i, nombre_senales[i]);
            almacen.setLeyenda(i, leyendas[i]);
            almacen.setFs(fs[i], i);
        }
        GestorDatos gestor_datos = GestorDatos.getInstancia();
        gestor_datos.setAlmacen(almacen);

        //Y  los estadisticos
        LinkedList estadisticos_list = this.cargaEstadisticos(root);
        //Anhadimos al alamcen todos los estadisticos
        Iterator it2 = estadisticos_list.iterator();
        while (it2.hasNext()) {
            ResultadosEstadisticos estadsitico = (ResultadosEstadisticos) it2.
                                                 next();
            gestor_datos.anadeEstadistico(estadsitico);
        }

        //Las correlaciones
        LinkedList correlaciones_list = this.cargaCorrelaciones(root);
        //Anhadimos al alamcen todos los estadisticos
        Iterator it3 = correlaciones_list.iterator();
        while (it3.hasNext()) {
            ResultadoCorrelacion correlacion = (ResultadoCorrelacion) it3.next();
            gestor_datos.anadeCorrelacion(correlacion);
        }
        if (pos_gloabal != null) {
            //Esto es una chapuza, lo hago para preparar el almacen ara contener una
            //Posibilidad mas
            GestorDatos.getInstancia().setPosibilidadTotal(0,
                    pos_gloabal.length - 1, (byte) 0);
            almacen.setPosibilidadTotal(pos_gloabal);
        }
        almacen.setFechaBase(fecha_base);
        SamplesToDate.getInstancia().setFechaBase(fecha_base);

        //Leemos las senales monitorizadas y las "monitorizamos"
        this.cargaConfiguracion(root);
        //cargamos el cometario:
        Element cometario_xml = root.getChild("Comentario");
        if (cometario_xml != null) {
            gestor_datos.setComentario(cometario_xml.getText());
        }

    }

    /**
     * Las leyendas por cmodidad las habiamos guardado todas en un solo String
     * sepradas por espacios en blanco. Aqui las recuperamos.
     * @param todas_las_leyendas
     * @return
     */
    private Object[] procesaLeyenda(String todas_las_leyendas) {
        StringTokenizer tk = new StringTokenizer(todas_las_leyendas.trim());
        int num_leyendas = tk.countTokens();
        Object[] leyendas = new Object[num_leyendas];
        int count = 0;
        while (tk.hasMoreTokens()) {
            leyendas[count] = tk.nextToken();
            count++;
        }
        return leyendas;
    }

    /**
     * Dada una lista con los nodos XML de terapia devuelve una LinkedList con los
     * objetos de tipo terapia crados a partir de esta.
     * @param lista_terapia
     * @return
     */
    private LinkedList genraAnotacionesTerapia(List lista_terapia) {
        LinkedList resultado = new LinkedList();
        Iterator it = lista_terapia.iterator();
        //Para todas las anotaciones
        while (it.hasNext()) {
            //Obtenemos del nodo XML los componentes de la terapia
            Element terapia_xml = (Element) it.next();
            String texto = terapia_xml.getAttribute("Texto").getValue();
            int tiempo_inicio, tipo_evento, offset, tiempo_fin;
            try {
                tiempo_inicio = terapia_xml.getAttribute("TiempoInicio").
                                getIntValue();
                tipo_evento = terapia_xml.getAttribute("TipoEvento").
                              getIntValue();
                offset = terapia_xml.getAttribute("Offset").getIntValue();
                tiempo_fin = terapia_xml.getAttribute("TiempoFin").getIntValue();
            } catch (DataConversionException ex) {
                System.out.println(
                        "Excepion de converios numerica de JDOM no procesada");
                ex.printStackTrace();
                //Devolvemos lo que tengamos
                return resultado;
            }
            String comentario = terapia_xml.getAttribute("Comentario").getValue();
            String nombre_farmaco = terapia_xml.getAttribute("NombreFarmaco").
                                    getValue();
            String fase_terapeutica = terapia_xml.getAttribute(
                    "FaseTerapeutica").getValue();
            String dosificacion = terapia_xml.getAttribute("Dosificacion").
                                  getValue();
            String tipo_terapia = terapia_xml.getAttribute("TipoTerapia").
                                  getValue();
            //Creamos el objeto de tipo terapia
            Therapy terapia = new Therapy(nombre_farmaco, fase_terapeutica,
                                          dosificacion, tipo_terapia);
            terapia.setOffset(offset);
            terapia.setTipo(tipo_evento);
            terapia.setTiempo(tiempo_inicio);
            terapia.setTipoTerapia(tipo_terapia);
            terapia.setTiempoFin(tiempo_fin);
            terapia.setTexto(texto);
            terapia.setComentario(comentario);
            resultado.add(terapia);
        }
        return resultado;
    }


    /**
     * Dada una lista con los nodos XML de Diagnostico devuelve una LinkedList con los
     * objetos de tipo Diagnostico crados a partir de esta.
     * @param lista_terapia
     * @return
     */
    private LinkedList genraAnotacionesDiagnostico(List lista_diagnostico) {
        LinkedList resultado = new LinkedList();
        Iterator it = lista_diagnostico.iterator();
        //Para todas las anotaciones
        while (it.hasNext()) {
            //Obtenemos del nodo XML los componentes de la terapia
            Element diagnostico_xml = (Element) it.next();
            String texto = diagnostico_xml.getAttribute("Texto").getValue();
            int tiempo_inicio, tipo_evento, offset, tiempo_fin;
            try {
                tiempo_inicio = diagnostico_xml.getAttribute("TiempoInicio").
                                getIntValue();
                tipo_evento = diagnostico_xml.getAttribute("TipoEvento").
                              getIntValue();
                offset = diagnostico_xml.getAttribute("Offset").getIntValue();
                tiempo_fin = diagnostico_xml.getAttribute("TiempoFin").
                             getIntValue();
            } catch (DataConversionException ex) {
                System.out.println(
                        "Excepion de converios numerica de JDOM no procesada");
                ex.printStackTrace();
                //Devolvemos lo que tengamos
                return resultado;
            }
            String comentario = diagnostico_xml.getAttribute("Comentario").
                                getValue();
            //Pedimos el elemento de tipo atributo
            Element atributo_xml = diagnostico_xml.getChild("Atributo");
            String nobre_atributo = atributo_xml.getAttributeValue("Atributo");
            String valor_atributo = atributo_xml.getAttributeValue("Valor");
            es.usc.gsi.trace.importer.jsignalmonold.annotations.Attribute
                    atributo = new
                               es.usc.gsi.trace.importer.jsignalmonold.
                               annotations.Attribute(
                                       nobre_atributo, valor_atributo);
            //Creamos el objeto de tipo terapia
            Diagnostic diagnostico = new Diagnostic(texto, atributo);
            diagnostico.setOffset(offset);
            diagnostico.setTipo(tipo_evento);
            diagnostico.setTiempo(tiempo_inicio);
            diagnostico.setTiempoFin(tiempo_fin);
            diagnostico.setAtributo(atributo);
            diagnostico.setComentario(comentario);
            resultado.add(diagnostico);
        }
        return resultado;
    }

    /**
     * Dada una lista con los nodos XML de Manifestacion devuelve una LinkedList con los
     * objetos de tipo Manifestacion crados a partir de esta.
     * @param lista_terapia
     * @return
     */
    private LinkedList genraAnotacionesManifestacion(List lista_manifestacion) {
        LinkedList resultado = new LinkedList();
        Iterator it = lista_manifestacion.iterator();
        //Para todas las anotaciones
        while (it.hasNext()) {
            //Obtenemos del nodo XML los componentes de la terapia
            Element manifestacion_xml = (Element) it.next();
            String texto = manifestacion_xml.getAttribute("Texto").getValue();
            int tiempo_inicio, tipo_evento, offset, tiempo_fin,
                    tipo_manifestacion;
            try {
                tiempo_inicio = manifestacion_xml.getAttribute("TiempoInicio").
                                getIntValue();
                tipo_evento = manifestacion_xml.getAttribute("TipoEvento").
                              getIntValue();
                offset = manifestacion_xml.getAttribute("Offset").getIntValue();
                tiempo_fin = manifestacion_xml.getAttribute("TiempoFin").
                             getIntValue();
                tipo_manifestacion = manifestacion_xml.getAttribute(
                        "TipoManifestacion").getIntValue();
            } catch (DataConversionException ex) {
                System.out.println(
                        "Excepion de converios numerica de JDOM no procesada");
                ex.printStackTrace();
                //Devolvemos lo que tengamos
                return resultado;
            }
            String comentario = manifestacion_xml.getAttribute("Comentario").
                                getValue();
            //Pedimos la lsita de elementos de tipo atributo
            List lista_atributo_xml = manifestacion_xml.getChildren("Atributo");
            Iterator it2 = lista_atributo_xml.iterator();
            LinkedList lista_atributo = new LinkedList();
            while (it2.hasNext()) {
                Element atributo_xml = (Element) it2.next();
                String nobre_atributo = atributo_xml.getAttributeValue(
                        "Atributo");
                String valor_atributo = atributo_xml.getAttributeValue("Valor");
                es.usc.gsi.trace.importer.jsignalmonold.annotations.Attribute
                        atributo = new
                                   es.usc.gsi.trace.importer.jsignalmonold.
                                   annotations.Attribute(
                                           nobre_atributo, valor_atributo);
                lista_atributo.add(atributo);
            }

            //Creamos el objeto de tipo terapia
            Manifestacion manifestacion = new Manifestacion(texto, comentario,
                    tipo_evento, lista_atributo);
            manifestacion.setOffset(offset);
            manifestacion.setTipo(tipo_evento);
            manifestacion.setTiempo(tiempo_inicio);
            manifestacion.setTiempoFin(tiempo_fin);
            manifestacion.setComentario(comentario);
            manifestacion.setTipoManifestacion(tipo_manifestacion);
            resultado.add(manifestacion);
        }
        return resultado;
    }

    /**
     * Carga los datos del fichcer de texto
     * @param file
     * @param datos
     * @param pos
     */
    private void cargaDatos(String file, int num_senales,
                            boolean[] tien_pos_asociada,
                            float[][] datos, byte[][] pos,
                            boolean tiene_pos_gloabal) {
        BufferedReader bf = null;
        try {
            FileReader f = new FileReader(file);
            bf = new BufferedReader(f);
            File fich = new File(file);
            //Marcasmos el Buffer para poder resetearlo una vez averiguado el numero
            //de filas y columnas que hay en el
            bf.mark((int) fich.length() + 1);
            String line = bf.readLine();
            StringTokenizer tk = new StringTokenizer(line);
            int columnas = 0;
            //Contamos el numero de columnas que hay en el archivo
            while (tk.hasMoreTokens()) {
                columnas++;
                tk.nextToken();
            }
            //Contamos el numero de filas. Aunque ya llevamos 1 leida el contador
            //empieza en 0 ya que el bucle incrementara su valor la primera vez que lea null
            int filas = 0;
            while (line != null) {
                line = bf.readLine();
                filas++;
            }
            //Inicilizamos todas la variables
            for (int i = 0; i < num_senales; i++) {
                datos[i] = new float[filas];
                pos[i] = new byte[filas];
            }
            //Y la pos gloablal, si es necesaria
            if (tiene_pos_gloabal) {
                pos_gloabal = new byte[filas];
            }
            marcas = new TreeSet[num_senales];
            anotaciones = new TreeSet();
            for (int i = 0; i < num_senales; i++) {
                marcas[i] = new TreeSet();
            }
            //reseteamos el buffer
            bf.reset();
            //Contadores de linea y columna
            int columna = 0, linea = 0;
            do {
                line = bf.readLine();
                if (line != null) {
                    StringTokenizer tk2 = new StringTokenizer(line, "\t", true);
                    columna = 0;
                    while (tk2.hasMoreTokens() && !(columna == num_senales)) {
                        String dato_fichero = tk2.nextToken();
                        //Si columna es mayor que numero de senhales => estamos leyendo la posibilidad global
                        if (columna == num_senales) {
                            pos_gloabal[linea] = Byte.parseByte(dato_fichero);
                        }

                        else if (dato_fichero.equals("\t")) {
                            datos[columna][linea] = 0;
                        } else {
                            datos[columna][linea] = Float.parseFloat(
                                    dato_fichero);
                            //Si no estamos en la ultima columna
                            if (tk2.hasMoreElements()) {
                                //consuminos el \t adicional
                                if (!(tk2.nextElement().equals("\t"))) {
                                    System.out.println(
                                            "Se esta leyneod mal el fichero de entrada");
                                }
                            }
                            columna++;
                        }
                        //Si tiene posibilidad asociada leemos ya este dato:
                        //El primer condicional es porque hemos incrementado columna y puede que ya hallmos acabado.
                        if (tien_pos_asociada[columna - 1]) {
                            dato_fichero = tk2.nextToken();
                            if (dato_fichero.equals("\t")) {
                                //pos[lin][col] = 0;
                            } else {
                                pos[columna -
                                        1][linea] = Byte.parseByte(dato_fichero);
                            }
                            if (tk2.hasMoreElements()) {
                                //consuminos el \t adicional
                                if (!(tk2.nextElement().equals("\t"))) {
                                    System.out.println(
                                            "Se esta leyneod mal el fichero de entrada");
                                }
                            }
                        }
                    }

                    //Si columna es mayor que numero de senhales => estamos leyendo la posibilidad global
                    if (columna == num_senales && tk2.hasMoreTokens()) {
                        String dato_fichero = tk2.nextToken();
                        pos_gloabal[linea] = Byte.parseByte(dato_fichero);
                    }
                    linea++;
                }

            } while (line != null);
            bf.close();

            GestorIO gestor_io = GestorIO.getGestorIO();
            gestor_io.setNumDatos(filas);
            gestor_io.setNumSenales(columnas);

        } catch (IOException ex) {
            ex.printStackTrace();
            datos = null;
            pos = null;
            marcas = null;
            anotaciones = null;
        }
    }

    private LinkedList cargaEstadisticos(Element root) {
        LinkedList resultado = new LinkedList();
        List list_estadisticos = root.getChildren("Estadistico");
        Iterator it = list_estadisticos.iterator();
        while (it.hasNext()) {
            try {
                Element estadistico_xml = (Element) it.next();
                float media = estadistico_xml.getAttribute("Media").
                              getFloatValue();
                float mediana = estadistico_xml.getAttribute("Mediana").
                                getFloatValue();
                float varianza = estadistico_xml.getAttribute("Varianza").
                                 getFloatValue();
                float desviacion_tipica = estadistico_xml.getAttribute(
                        "DesviacionTipica").getFloatValue();
                float error_estandar = estadistico_xml.getAttribute(
                        "ErrorEstandar").getFloatValue();
                float cociente_de_variacion = estadistico_xml.getAttribute(
                        "CocienteDeVariacion").getFloatValue();
                String fecha_inicio = estadistico_xml.getAttribute(
                        "FechaInicio").getValue();
                String fecha_fin = estadistico_xml.getAttribute("FechaFin").
                                   getValue();
                String nombre = estadistico_xml.getAttribute("Nombre").getValue();
                float[] intervalo_de_confianza = new float[2];
                intervalo_de_confianza[0] = estadistico_xml.getAttribute(
                        "IntervaloDeConfianzaInicio").getFloatValue();
                intervalo_de_confianza[1] = estadistico_xml.getAttribute(
                        "IntervaloDeConfianzaFin").getFloatValue();
                String comentario = estadistico_xml.getChild("Comentario").
                                    getText();

                //Cojemos la lista de percentiles
                List list_percentiles = estadistico_xml.getChildren(
                        "Percentiles");
                Iterator it2 = list_percentiles.iterator();
                int num_percentiles = list_percentiles.size();
                int[] percentiles_float = new int[num_percentiles];
                float[] valores_percentiles = new float[num_percentiles];
                int cuantos_van = 0;
                while (it2.hasNext()) {
                    Element percentil_xml = (Element) it2.next();
                    percentiles_float[cuantos_van] = percentil_xml.getAttribute(
                            "Percentil").getIntValue();
                    valores_percentiles[cuantos_van] = percentil_xml.
                            getAttribute("ValorPercentil").getFloatValue();
                    cuantos_van++;
                }
                ResultadosEstadisticos estadistico = new ResultadosEstadisticos(
                        media, mediana, varianza,
                        desviacion_tipica, error_estandar,
                        cociente_de_variacion, intervalo_de_confianza,
                        percentiles_float,
                        valores_percentiles, fecha_inicio, fecha_fin, nombre);
                estadistico.setComentario(comentario);
                resultado.add(estadistico);
            } catch (NotPercentilException ex) {
                ex.printStackTrace();
            } catch (DataConversionException ex) {
                ex.printStackTrace();
            }
        }
        return resultado;
    }

    /**
     * Almacena los canales que se estaban visualizando en un determinado momento.
     * @param root
     */
    private void cargaConfiguracion(Element root) {
        GestorDatos gestor_datos = GestorDatos.getInstancia();
        Element configuracion = root.getChild("Configuracion");
        if (configuracion != null) {
            List lista_senales = configuracion.getChildren("CanalMonitorizado");
            int[] senesles_monitorizadas = new int[lista_senales.size()];
            Iterator it = lista_senales.iterator();
            int count = 0;
            while (it.hasNext()) {
                try {
                    senesles_monitorizadas[count] = ((Element) it.next()).
                            getAttribute("NumeroSenal").getIntValue();
                    count++;
                } catch (DataConversionException ex) {
                }
            }
            gestor_datos.setSenalesQueSeMonitorizaronLaUltimaVez(
                    senesles_monitorizadas);
        }

    }

    /**
     *
     * @param root
     * @return
     */
    private LinkedList cargaCorrelaciones(Element root) {
        LinkedList resultado = new LinkedList();
        List list_correlaciones = root.getChildren("Correlacion");
        Iterator it = list_correlaciones.iterator();
        while (it.hasNext()) {
            try {
                Element correlacion_xml = (Element) it.next();
                float coef_correlacion = correlacion_xml.getAttribute(
                        "Correlacion").getFloatValue();
                int significacion = correlacion_xml.getAttribute(
                        "Significacion").getIntValue();
                String nombre_correlacion = correlacion_xml.getAttribute(
                        "NombreCorrelacion").getValue();
                String nombre_senhal1 = correlacion_xml.getAttribute(
                        "NombreSenhal1").getValue();
                String nombre_senhal2 = correlacion_xml.getAttribute(
                        "NombreSenhal2").getValue();
                String fecha_inicio1 = correlacion_xml.getAttribute(
                        "FechaInicio1").getValue();
                String fecha_inicio2 = correlacion_xml.getAttribute(
                        "FechaInicio2").getValue();
                String fecha_fin1 = correlacion_xml.getAttribute("FechaFin1").
                                    getValue();
                String fecha_fin2 = correlacion_xml.getAttribute("FechaFin2").
                                    getValue();
                String comentario = correlacion_xml.getChild("Comentario").
                                    getText();

                ResultadoCorrelacion correlacion = new ResultadoCorrelacion();
                correlacion.setNombre(nombre_correlacion);
                correlacion.setFechaFin1(fecha_fin1);
                correlacion.setFechaFin2(fecha_fin2);
                correlacion.setFechaInicio1(fecha_inicio1);
                correlacion.setFechaInicio2(fecha_inicio2);
                correlacion.setSenal1(nombre_senhal1);
                correlacion.setSenal2(nombre_senhal2);
                correlacion.setComentario(comentario);
                correlacion.setNivelDeSignificacion(coef_correlacion);
                correlacion.setNivelDeSignificacionDiscreto(significacion);
                resultado.add(correlacion);
            } catch (DataConversionException ex) {
                ex.printStackTrace();
            }
        }
        return resultado;
    }

}
