//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\monitorizacion\\Data\\AlmacenDatos.java

package es.usc.gsi.trace.importer.monitorizacion.data;

import java.io.Serializable;
import java.util.*;

import es.usc.gsi.trace.importer.Perfil.PTBMInterface;
import es.usc.gsi.trace.importer.estadisticos.ResultadoCorrelacion;
import es.usc.gsi.trace.importer.estadisticos.ResultadosEstadisticos;
import es.usc.gsi.trace.importer.jsignalmonold.annotations.Annotation;
import es.usc.gsi.trace.importer.jsignalmonold.annotations.Mark;


public abstract class AlmacenDatos implements Serializable {

    /**
     * Esto nos hara a nosotros responsables del versionamiento de los ficheros Serializados.
     * Siempre podemos volver a leerlos, pero depende de nosotros que se haga de un modo correcto.
     */

    static final long serialVersionUID = 3211L;

    /**
     * Numero de senhales contenidas en este almacen.
     */
    int i;
    protected int numero_senales;
    protected boolean tienePosAsociada[];
    public static final int FLOAT = 4;
    public static final int INT = 3;
    public static final int SHORT = 2;
    public static final int BYTE = 1;
    protected float rangos_senales[][];
    protected String leyendas[];
    protected PTBMInterface ptbm;
    protected String nombre_senales[];
    protected String leyenda_temporal[];
    protected AlmacenDatosByte almacen_pos;
    protected TreeSet marcas[];
    protected TreeSet anotaciones;
    protected byte[] pos_total;
    protected String fecha_base;

    /**
     * para meter lo que sea en el futuro....
     */
    protected LinkedList olvidado;

    protected float fs[];
    protected String nombre_paciente;
    protected int edad_paciente;

    /**
     * false si es mujer
     */
    protected boolean is_hombre;
    protected String comentario;
    protected String[] otros_comentarios;

    private transient boolean esta_guardado = false;
    private transient boolean tiene_archivo_asociado = false;
    private transient String archivo;

    /**
     * Para los estadisticos
     */
    protected HashMap estadisticos;
    protected HashMap correlaciones;

    public AlmacenDatos() {

    }


    /**
     * @param datos
     * @param pos@param marcas
     * @param anotaciones
     */
    public AlmacenDatos(float[][] datos, byte[][] pos, TreeSet[] marcas,
                        TreeSet[] anotaciones) {

    }


    /**
     * @return Object
     */
    public abstract Object getDatos();


    public abstract void setDatos(Object datos);


    /**
     * @return int
     */
    public int getNumeroSenales() {

        return this.numero_senales;
    }


    /**
     * @return Object
     */
    public Object getPos() {
        return almacen_pos.getDatos();
    }


    /**
     * @param canal
     * @return boolean
     */
    public boolean isPosAsociada(int canal) {
        //En este caso es la posibilidad total
        if (this.numero_senales == canal) {
            return true;
        }

        if (almacen_pos.getDatos() != null &&
            ((byte[][]) almacen_pos.getDatos())[canal] != null) {
            byte[] pos = ((byte[]) almacen_pos.getDatos(canal));
            for (int i = 0; i < pos.length; i++) {
                if (pos[i] != 0) {
                    return true;
                }

            }

            return false;
        }

        return false;
    }


    /**
     * @param senal - Datos del array i, es decir la senhal i.
     * @return Object
     */
    public Object getPos(int senal) {
        if (almacen_pos.getDatos() == null) {
            return null; //(new int[1]);
        }
        Object tmp = almacen_pos.getDatos();
        return ((byte[][]) (tmp))[senal];
    }


    /**
     * @param senal - Datos del array i, es decir la senhal i.
     * @return Object
     * @todo cerciorarse de que funciona
     */
    public void setPos(int senal, byte[] pos) {
        almacen_pos.setPos(senal, pos);
    }


    /**
     * @param senal - Datos del array i, es decir la senhal i.
     * @return Object
     */
    public abstract Object getArray(int senal);


    /**
     * @param senal
     * @return Object
     */
    public abstract Object getDatos(int senal);


    /**
     *
     * @param numSenal
     * @param nuevosDatos
     */
    public abstract void setDatos(int numSenal, Object nuevosDatos);


    /**
     * @return LinkedList[]
     */
    public TreeSet[] getMarcas() {
        return marcas;
    }


    public void setMarcas(TreeSet[] marcas) {
        this.marcas = marcas;
    }


    /**
     * @return LinkedList[]
     */
    public TreeSet getAnotaciones() {
        return anotaciones;
    }


    /**
     * @param senal
     * @return float[]
     */
    public float[] getRango(int senal) {
        if (rangos_senales.length <= senal) {
            float[] f = {
                        0, 100};
            return f;
        }
        return rangos_senales[senal];
    }


    /**
     * @param senal
     * @param rango
     */
    public void setRango(int senal, float[] rango) {
        rangos_senales[senal] = rango;
    }


    /**
     *
     * @param rango
     */
    public void setRango(float[][] rango) {
        rangos_senales = rango;
    }


    /**
     * @param senal
     * @return String[]
     * @todo cutrada
     */
    public String getLeyenda(int senal) {
        /*   if (this.numero_senales == senal) {
            String[] tmp = {"W","W","W"};
         return tmp;
             }*/
        return leyendas[senal];
    }


    /**
     * @param senal
     * @param leyendas
     */
    public void setLeyenda(int senal, String leyendas) {
        this.leyendas[senal] = leyendas;
    }


    /**
     * @param senal
     * @return String
     * @todo cutrada
     */
    public String getNombreSenal(int senal) {
        /*    if (this.numero_senales == senal) {
              return "Posibilidad";
            }*/

        return this.nombre_senales[senal];
    }


    /**
     * @param senal
     * @param nombre
     */
    public void setNombreSenal(int senal, String nombre) {
        this.nombre_senales[senal] = nombre;

    }


    /**
     * @param senal
     * @return String
     * @todo cutrada
     */
    public String getLeyendaTemporal(int senal) {
        /*       if (this.numero_senales == senal) {
             return "Posibilidad";
           }*/
        return this.leyenda_temporal[senal];
    }


    /**
     *
     * @param leyenda_temporal
     */
    protected void setLeyendaTemporal(String[] leyenda_temporal) {
        this.leyenda_temporal = leyenda_temporal;
    }


    /**
     * @param senal
     * @param anotacion
     */
    public void anadeMarca(int senal, Mark anotacion) {
        this.marcas[senal].add(anotacion);
    }


    /**
     * @param senal
     * @param anotacion
     */
    public void eliminaMarca(int senal, Mark anotacion) {
        this.marcas[senal].remove(anotacion);
    }


    /**
     * @param anotacion
     */
    public void anadeAnotacion(Annotation anotacion) {
        this.anotaciones.add(anotacion);
    }


    /**
     * @param anotacion
     */
    public void eliminaAnotacion(Annotation anotacion) {
        this.anotaciones.remove(anotacion);
    }


    //////////////Conjunto de metodos para la gestion del almacenaje a archivo
    public boolean getEstaGuardado() {
        return esta_guardado;
    }


    public void setEstaGuardado(boolean _esta_guardado) {
        esta_guardado = _esta_guardado;
    }


    public boolean getTieneArchivoAsociado() {
        return tiene_archivo_asociado;
    }


    public void setTieneArchivoAsociado(boolean _tiene_archivo_asociado) {
        tiene_archivo_asociado = _tiene_archivo_asociado;
    }


    public String getArchivo() {
        return archivo;
    }


    public void setArchivo(String _archivo) {
        archivo = _archivo;
    }


    public PTBMInterface getPTBM() {
        return ptbm;
    }


    public void setPTBM(PTBMInterface _ptbm) {
        ptbm = _ptbm;
    }


    public byte[] getPosibilidadTotal() {
        return pos_total;
    }


    public void setPosibilidadTotal(byte[] _pos_total) {
        pos_total = _pos_total;
    }


    protected void setLeyendas(String[] _leyendas) {
        leyendas = _leyendas;
    }


    protected void setNombreSenales(String[] _nombre_senales) {
        nombre_senales = _nombre_senales;
    }


    protected void setFS(float[] _nombre_senales) {
        this.fs = _nombre_senales;
    }


    public String getFechaBase() {
        return fecha_base;
    }


    public void setFechaBase(String _fecha_base) {
        fecha_base = _fecha_base;
    }


    public String getNombrePaciente() {
        return nombre_paciente;
    }


    public void setNombrePaciente(String _nombre_paciente) {
        nombre_paciente = _nombre_paciente;
    }


    public int getEdadPaciente() {
        return edad_paciente;
    }


    public void setEdadPaciente(int _edad_paciente) {
        edad_paciente = _edad_paciente;
    }


    public boolean getIsHombre() {
        return is_hombre;
    }


    public void setIsHombre(boolean _is_hombre) {
        is_hombre = _is_hombre;
    }


    public String getComentario() {
        return comentario;
    }


    public void setComentario(String _comentario) {
        comentario = _comentario;
    }


    public String[] getOtrosComentarios() {
        return otros_comentarios;
    }


    public void setOtrosComentarios(String[] _otros_comentarios) {
        otros_comentarios = _otros_comentarios;
    }


    public float getFs(int i) {
        return fs[i];
    }


    public void setFs(float _fs, int i) {
        fs[i] = _fs;
    }


    /**
     * Devuelve la longitud maxima de la senhal mas grande.
     * Han de implementarlo las clases que la extiendan.
     * @return
     */
    public abstract int getMaximoNumeroDeDatos();


    void anhadeSenhal(String nombre, String leyenda, String Leyenda_temporal,
                      float fs,
                      float[] rango, int numero_datos) {
        int nueva_num_senales = numero_senales + 1; //this.marcas.length+1;
        if (pos_total != null) {
            nueva_num_senales++;
        }

        String[] nombre_senales_tmp = new String[nueva_num_senales];
        float[] fs_tmp = new float[nueva_num_senales];
        float[][] rango_tmp = new float[nueva_num_senales /*+ 1*/][2];
        String[] leyendas_tmp = new String[nueva_num_senales];
        TreeSet[] marcas_tmp = new TreeSet[nueva_num_senales];
        boolean[] tienePosAsociada_tmp = null;
        if (pos_total == null) {
            tienePosAsociada_tmp = new boolean[nueva_num_senales];
        } else {
            tienePosAsociada_tmp = new boolean[nueva_num_senales];
        }

        String[] leyenda_temporal_tmp = new String[nueva_num_senales];
        //Si hay posibilida total

        if (pos_total != null) {
            nueva_num_senales--;
        }

        for (int i = 0; i < nueva_num_senales - 1; i++) {
            nombre_senales_tmp[i] = this.nombre_senales[i];
            fs_tmp[i] = this.fs[i];
            leyendas_tmp[i] = this.leyendas[i];
            rango_tmp[i][0] = this.rangos_senales[i][0];
            rango_tmp[i][1] = this.rangos_senales[i][1];
            marcas_tmp[i] = this.marcas[i];
            //TienePosibilidadAsociadano no incluye a la posibilidad
            if ( /*pos_total ==  null || */i != nueva_num_senales - 1) {
                tienePosAsociada_tmp[i] = this.tienePosAsociada[i];
            }
            leyenda_temporal_tmp[i] = this.leyenda_temporal[i];
        }
        //if (pos_total == null) {
        nueva_num_senales--;
        //  }
        nombre_senales_tmp[nueva_num_senales] = nombre;
        fs_tmp[nueva_num_senales] = fs;
//      leyendas_tmp[nueva_num_senales] = tmp;
        rango_tmp[nueva_num_senales][0] = rango[0];
        rango_tmp[nueva_num_senales][1] = rango[1];
        marcas_tmp[nueva_num_senales] = new TreeSet();
        tienePosAsociada_tmp[nueva_num_senales - 1] = true;
        leyendas_tmp[nueva_num_senales] = leyenda;
        leyenda_temporal_tmp[nueva_num_senales] = Leyenda_temporal;
        //La posibilidad siempre debe ser la ultima en almacenar su rango
        if (pos_total != null) {
            nueva_num_senales++;
            nombre_senales_tmp[nueva_num_senales] = this.nombre_senales[
                    numero_senales];
            fs_tmp[nueva_num_senales] = this.fs[numero_senales];
            leyendas_tmp[nueva_num_senales] = this.leyendas[numero_senales];
            rango_tmp[nueva_num_senales][0] = this.rangos_senales[
                                              numero_senales][0];
            rango_tmp[nueva_num_senales][1] = this.rangos_senales[
                                              numero_senales][1];
            marcas_tmp[nueva_num_senales] = this.marcas[numero_senales];
            tienePosAsociada_tmp[nueva_num_senales - 1] = true;
            //leyendas_tmp[nueva_num_senales] = this.leyenda_temporal[numero_senales];
            leyenda_temporal_tmp[nueva_num_senales] = this.leyenda_temporal[
                    numero_senales];
        }

        nombre_senales = nombre_senales_tmp;
        this.fs = fs_tmp;
        leyendas = leyendas_tmp;
        this.rangos_senales = rango_tmp;
        marcas = marcas_tmp;
        tienePosAsociada = tienePosAsociada_tmp;
        this.leyenda_temporal = leyenda_temporal_tmp;
        this.leyendas = leyendas_tmp;
        numero_senales++;
        this.almacen_pos.anhadeSenhal(new byte[numero_datos]);

    }


    void eliminaSenhal(int numero_senhal) {

        float[][] rangos_tmp = new float[rangos_senales.length - 1][];
        float[] fs_tmp = new float[fs.length - 1];
        String[] nombres_tmp = new String[nombre_senales.length - 1];
        String[] leyendas_temporales_tmp = new String[leyenda_temporal.length -
                                           1];
        String[] leyendsd_tmp = new String[leyendas.length - 1];
        TreeSet[] marcas_tmp = new TreeSet[marcas.length - 1];
        boolean[] pos_tmp = new boolean[tienePosAsociada.length - 1];
        int cout = 0;
        int num_senales = rangos_senales.length;
        /*   if ( pos_total != null) {
             num_senales++;
           }*/

        //Si hay posibilidad => esa no cuenta
        for (int i = 0; i < num_senales; i++) {
            if (i != numero_senhal) {
                rangos_tmp[cout] = rangos_senales[i];
                fs_tmp[cout] = fs[i];
                nombres_tmp[cout] = nombre_senales[i];
                leyendas_temporales_tmp[cout] = leyenda_temporal[i];
                leyendsd_tmp[cout] = leyendas[i];
                marcas_tmp[cout] = marcas[i];
                if (pos_total == null || i != num_senales - 1) {
                    pos_tmp[cout] = tienePosAsociada[i];
                }
                cout++;
            }
        }
        //Si hay posibilidad global hay que almacenar sus datos
        //El segundo chequeo siempre deberiera ser true, pero es para curarse en salud
        if (getPosibilidadTotal() != null && marcas_tmp.length == num_senales) {
            fs_tmp[num_senales - 1] = fs[num_senales];
            nombres_tmp[num_senales - 1] = nombre_senales[num_senales];
            leyendas_temporales_tmp[num_senales -
                    1] = leyenda_temporal[num_senales];
            leyendsd_tmp[num_senales - 1] = leyendas[num_senales];
            marcas_tmp[num_senales - 1] = marcas[num_senales];
        }

        numero_senales--;

        rangos_senales = rangos_tmp;
        fs = fs_tmp;
        nombre_senales = nombres_tmp;
        leyenda_temporal = leyendas_temporales_tmp;
        leyendas = leyendsd_tmp;
        marcas = marcas_tmp;
        tienePosAsociada = pos_tmp;

    }


    /**
     *
     * @param tmp
     */
    public void setAnotaciones(TreeSet tmp) {
        anotaciones = tmp;
    }


    /**
     *
     * @param resultados
     */
    void anadeEstadistico(ResultadosEstadisticos resultados) {
        estadisticos.put(resultados.getNombreSenhal() +
                         resultados.getFechaInicio() +
                         resultados.getFechaFin(), resultados);
    }


    /**
     *
     * @param resultados
     */
    void anadeCorrelacion(ResultadoCorrelacion resultados) {
        correlaciones.put(resultados.getKey(), resultados);
    }


    /**
     *
     * @param estadistico
     * @return
     */
    boolean eliminaEstadistico(String estadistico) {
        if (estadisticos.remove(estadistico) != null) {
            return true;
        }
        return false;
    }


    /**
     *
     * @param estadistico
     * @return
     */
    boolean eliminaCorrelacion(ResultadoCorrelacion correlacion) {
        if (correlaciones.remove(correlacion.getKey()) != null) {
            return true;
        }
        return false;
    }


    /**
     *
     * @param estadistico
     * @return
     */
    boolean eliminaCorrelacion(String correlacion) {
        if (correlaciones.remove(correlacion) != null) {
            return true;
        }
        return false;
    }


    /**
     *
     */
    void eliminaTodosLosEstadisticos() {
        estadisticos = new HashMap();
    }


    /**
     *
     */
    void eliminaTodasLasCorrelaciones() {
        correlaciones = new HashMap();
    }


    /**
     *
     * @return
     */
    Collection getEstadisticos() {
        return estadisticos.values();
    }


    /**
     *
     * @return
     */
    Collection getCorrelaciones() {
        return correlaciones.values();
    }


    /**
     *
     * @param estadistico
     * @return
     */
    ResultadosEstadisticos getEstadistico(String estadistico) {
        return ((ResultadosEstadisticos) estadisticos.get(estadistico));
    }


    ResultadoCorrelacion getCorrelacion(String correlacion) {
        return ((ResultadoCorrelacion) estadisticos.get(correlacion));
    }


    /**Cuando se carga un archivo serializado este no posee ciertos campos que se anhadienron
     * al almacen a posteriori. Los inicializamos aqui
     */

    public void inicializaPartesNoSerializadas() {
        estadisticos = new HashMap();
        correlaciones = new HashMap();
    }
}
