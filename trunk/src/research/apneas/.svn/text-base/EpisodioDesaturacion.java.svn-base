package research.apneas;

import static java.lang.Math.*;
import java.util.TreeSet;

import net.javahispano.fuzzyutilities.representation.TrapezoidalDistribution;

public class EpisodioDesaturacion extends Intervalo {
    private Intervalo intervaloPrincipio;
    private Intervalo intervaloFin;
    private float minimo = Float.MAX_VALUE;
    private float maximo = Float.MIN_VALUE;
    private float tiempoBajada;
    private float tiempoSubida;
    private float duracion;
    private float caidaSatO2;

    private float valorBasal;

//@todo parametro
    private TrapezoidalDistribution caidaEnSaturacion = new
            TrapezoidalDistribution(3F, 5F, 70, 110F);
    private TrapezoidalDistribution pendienteAscenso;
    private TrapezoidalDistribution pendienteDescenso;
    private TreeSet<Intervalo> artefactos = new TreeSet<Intervalo>();

    public EpisodioDesaturacion(Intervalo principio, Intervalo fin, float valorBasal) {
        this.valorBasal = valorBasal;
        intervaloPrincipio = new Intervalo(principio);
        intervaloFin = new Intervalo(fin);
        this.principio = principio.getPrincipio();
        this.fin = fin.getFin();
        frecuencia = principio.getFrecuencia();
        datos = principio.datos;
        fechaBase = principio.fechaBase;
        calcularMaximoYMinimo();

        this.posibilidad = calculaPosibilidad();
        caracterizaEpisodio();
        identificaArtefactos();

    }

    public EpisodioDesaturacion(Intervalo principio, Intervalo fin, EpisodioDesaturacion desaturacion) {
        this(principio, fin, desaturacion.valorBasal);
    }

    /**
     * Devolvera a cierto cuando este episodio de desaturacion tiene una calidad elevada; es decir, existe bastante
     * evidencia de que es un episodio de desaturacion y es relevante.
     *
     * @return boolean
     */
    public boolean esBuenaCalidad() {
        if (this.posibilidad < 60) {
            return false;
        } else if (this.getDuracion() < 6) {
            return false;
        } else if (this.getCaidaSatO2() < 5) {
            return false;
        }
        //si no cumple una calidad mnima combinando la caida y la duracion
        else if (this.getCaidaSatO2() * this.getDuracion() < 50) {
            return false;
        }
        return true;
    }

    private void identificaArtefactos() {
        for (int i = principio; i < fin; i++) {
            //Una caida de 60 o mas entre un par de muestras era considerada como artefacto
            if (datos[i] - datos[i + 1] > 60) {
                int p = i;
                //comprobamos si hay una recuperacion
                for (int j = i + 1; j < fin; j++, i++) {
                    if (datos[j] - datos[j + 1] < -60) {
                        //hemos encontrado el principio y el fin de un artefacto
                        Intervalo artefacto = new Intervalo(p, j + 1, 0);
                        artefacto.setFrecuencia(this.frecuencia);
                        artefacto.setFechaBase(this.fechaBase);
                        artefactos.add(artefacto);
                        break;
                    }
                }
            }
        }
    }

    /**
     * caracterizaEpisodio
     *
     * @todo quizas seria interesante calcular alguno de estos parametros sobre
     *   la senhal real y no sobre la filtrada. En especial el minimo.
     */
    private void caracterizaEpisodio() {
        tiempoBajada = intervaloPrincipio.getFin() - intervaloPrincipio.getPrincipio() + 1;
        tiempoBajada /= frecuencia;
        tiempoSubida = intervaloFin.getFin() - intervaloFin.getPrincipio() + 1;
        tiempoSubida /= frecuencia;
        duracion = intervaloFin.getFin() - intervaloPrincipio.getPrincipio();
        duracion /= frecuencia;
        caidaSatO2 = maximo - minimo;
        if (this.fin - this.principio < 4 * frecuencia) {
            posibilidad = 0;
        }

    }

    private void refinarFin(float[] posibilidadFin) {
        int i = fin;
        //si "esta plano" pasamos de el. Ahora solo consideramos una ventana de un segundo
        //y una pendiente ocho veces superior. Este valor es completamente arbitrario.
        while (i > intervaloFin.principio + 1 &&
               this.pendienteAscenso.multiply(8 / frecuencia).evaluatepossibilityAt(
                       Utilidades.pendienteEnTornoA((int) (1 * frecuencia), i, datos)) == 0) {
            posibilidadFin[i] = -40;
            i--;
        }
        fin = i;
        intervaloFin.setFin(fin);
        caracterizaEpisodio();
    }

    private void refinarPrincipio(float[] posibilidadPrincipio) {
        int i = principio;
        //si "esta plano" pasamos de el. Ahora solo consideramos una ventana de un segundo
        //y una pendiente ocho veces superior. Este valor es completamente arbitrario.
        while (i < intervaloFin.fin - 1 && this.pendienteDescenso.multiply(8 / frecuencia).evaluatepossibilityAt(
                Utilidades.pendienteEnTornoA((int) (1 * frecuencia), i, datos)) == 0) {
            posibilidadPrincipio[i] = -10;
            i++;
        }
        principio = i;
        intervaloPrincipio.setPrincipio(principio);
        //Miramos si el episodio empieza con una caida brusca
        if (artefactos.size() > 0) {
            Intervalo n = artefactos.first();
            //si el intervalo comienza menos de dos segundos de un artefacto
            if (n.getPrincipio() - principio < 2 * frecuencia) {
                this.intervaloPrincipio.setPrincipio(n.getFin());
                this.principio = n.getFin();
            }
        }

        caracterizaEpisodio();
    }


    private void calcularMaximoYMinimo() {
        for (int i = this.principio; i < this.fin; i++) {
            minimo = min(minimo, datos[i]);
            maximo = max(maximo, datos[i]);
        }
    }

    /**
     * calculaPosibilidad
     *
     * @return int
     */
    private int calculaPosibilidad() {
        int p = min(intervaloPrincipio.getPosibilidad(), intervaloFin.getPosibilidad());
        byte satisfaccionCriterioCaida = caidaEnSaturacion.evaluatepossibilityAt(
                valorBasal - minimo);
        return min(p, satisfaccionCriterioCaida);
    }

    public void setCaidaEnSaturacion(TrapezoidalDistribution caidaEnSaturacion) {
        this.caidaEnSaturacion = caidaEnSaturacion;
    }

    public void setPendienteAscenso(TrapezoidalDistribution pendienteAscenso,
                                    float[] posibilidadFin) {
        this.pendienteAscenso = pendienteAscenso;
        this.refinarFin(posibilidadFin);
    }

    public void setPendienteDescenso(TrapezoidalDistribution pendienteDescenso,
                                     float[] posibilidadPrincipio) {
        this.pendienteDescenso = pendienteDescenso;
        this.refinarPrincipio(posibilidadPrincipio);
    }

    public float getMaximo() {
        return maximo;
    }

    public float getMinimo() {
        return minimo;
    }

    public float getTiempoSubida() {
        return tiempoSubida;
    }

    public float getTiempoBajada() {
        return tiempoBajada;
    }

    public float getDuracionEpisodio() {
        return duracion;
    }

    public float getCaidaSatO2() {
        return caidaSatO2;
    }

    public Intervalo getIntervaloPrincipio() {
        return intervaloPrincipio;
    }

    public Intervalo getIntervaloFin() {
        return intervaloFin;
    }

    public void setPrincipio(int principio) {
        this.intervaloPrincipio.setPrincipio(principio);
        super.setPrincipio(principio);
    }

    public void setFin(int fin) {
        this.intervaloFin.setFin(fin);
        super.setFin(fin);
    }

}
