package research.apneas;

import static java.lang.Math.*;

public class Intervalo implements Comparable {
    protected int principio;
    protected int fin;
    protected int posibilidad;
    protected long fechaBase = 0;
    protected float[] datos;
    protected float frecuencia;

    /**
     * La clase supone que todos los episodios con los que se van a trabajar
     * tienen la misma frecuencia.
     */
    public Intervalo() {}

    public Intervalo(int principio, int fin, int posibilidad) {
        this.principio = principio;
        this.posibilidad = posibilidad;
        this.fin = fin;
    }

    public Intervalo(Intervalo i) {
        this.principio = i.principio;
        this.posibilidad = i.posibilidad;
        this.fin = i.fin;
        this.datos = i.datos;
        this.fechaBase = i.fechaBase;
        this.frecuencia = i.frecuencia;
    }


    /**
     * En seg.
     *
     * @return int
     */
    public int getDuracion() {
        return (int) ((fin - principio) / frecuencia);
    }

    /**
     * Compares this object with the specified object for order.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this
     *   object is less than, equal to, or greater than the specified object.
     * @todo Implement this java.lang.Comparable method
     */
    public int compareTo(Object o) {
        Intervalo i = (Intervalo) o;
        if (i.getPrincipio() < principio) {
            return 1;
        } else if (i.getPrincipio() > principio) {
            return -1;
        }
        return 0;
    }

    /*  public boolean equals(Object o) {
          if (o instanceof Intervalo) {
              Intervalo i = (Intervalo) o;
              return i.principio == this.principio;
          }
          return false;
      }*/

    public boolean seSolapaCon(Intervalo i) {
        if (i.principio <= this.principio && i.fin <= fin && i.fin > principio ||
            i.principio >= principio && i.fin <= fin ||
            i.principio >= principio && i.fin >= fin && i.principio < fin) {
            return true;
        }
        return false;
    }

    public Intervalo desplazaEnTiempo(int segundos) {
        segundos = (int) (segundos * frecuencia);
        Intervalo i = new Intervalo(principio + segundos, fin + segundos, posibilidad);
        i.setFrecuencia(frecuencia);
        i.setPosibilidad(posibilidad);
        return i;
    }

    /**
     * Devuelve la distancia desde el fin del intervalo al principio del
     * intervalo que se le pasa como argumento.
     *
     * @param i Intervalo
     * @return int distancia en segundos
     */
    public int distanciaCon(Intervalo i) {
        //anterior implementacin espacioreturn (int) ((i.principio - fin) / frecuencia);
        return (int) (min(abs((i.principio - fin)), abs(principio - i.fin)) / frecuencia);
    }

    /**
     * Devuelve la distancia desde el fin del intervalo al fin del
     * intervalo que se le pasa como argumento.
     *
     * @param i Intervalo
     * @return int distancia en segundos
     */
    public int distanciaEntreFines(Intervalo i) {
        return (int) ((i.fin - fin) / frecuencia);
    }

    /**
     * Devuelve la distancia desde el Principio del intervalo al Principio del
     * intervalo que se le pasa como argumento.
     *
     * @param i Intervalo
     * @return int distancia en segundos
     */
    public int distanciaEntrePrincipios(Intervalo i) {
        return (int) ((i.principio - principio) / frecuencia);
    }

    public int getPrincipio() {
        return principio;
    }

    public int getFin() {
        return fin;
    }

    /**
     * Principio de la marca medido en milisegundos desde 1970.
     *
     * @return long
     */
    public long getPrincipioAbsoluto() {
        return (long) (principio / frecuencia) * 1000 + fechaBase;
    }

    /**
     * Fin de la marca medido en milisegundos desde 1970.
     *
     * @return long
     */
    public long getFinAbsoluto() {
        return (long) (fin / frecuencia) * 1000 + fechaBase;
    }


    public int getPosibilidad() {
        return posibilidad;
    }

    public long getFechaBase() {
        return fechaBase;
    }

    public float getFrecuencia() {
        return frecuencia;
    }

    public float[] getDatos() {
        return datos;
    }

    public void setFechaBase(long fechaBase) {
        this.fechaBase = fechaBase;
    }

    public void setDatos(float[] datos) {
        this.datos = datos;
    }

    public void setFrecuencia(float frecuencia) {
        this.frecuencia = frecuencia;
    }

    public void setPrincipio(int principio) {
        this.principio = principio;
        if (principio > fin) {
            fin = principio;
        }
    }

    public void setFin(int fin) {
        this.fin = fin;
        if (fin < principio) {
            principio = fin;
        }
    }

    public void setPosibilidad(int posibilidad) {
        this.posibilidad = posibilidad;
    }
}
