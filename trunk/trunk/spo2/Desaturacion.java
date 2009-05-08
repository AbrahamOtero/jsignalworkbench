package spo2;

/**
 * Representa una desaturación. Si en el objeto Desaturacion que se emplea para notificar el inicio
 * de desaturación, sólo el instante de inicio tiene sentido. En el caso del objeto Desaturacion que
 * marca el final de una desaturación todos los atributos están correctamente inicializados.
 *
 * @author Abraham Otero
 * @version 0.5
 */
public class Desaturacion {

    private long tiempoInicial = 0;//origen de tiempos
    private int comienzo = 0;//Posición relativa al origen
    private int fin = 0;//Posición relativa al origen
    private boolean inicioSolo=true;//si es cierto es que solo es el principio y an no ha acabado
    private float pos=0;//grado de cumplimiento de los criterios de duración y caída
    private float valorMinimo =0;//el valor mnimo que ha alcanzado la desaturacion

    public Desaturacion(long tiempoInicial,int comienzo) {
         this.tiempoInicial = tiempoInicial;
         this.comienzo = comienzo;
    }
    public Desaturacion(long tiempoInicial,int comienzo, int fin) {
        this (tiempoInicial, comienzo);
        this.fin = fin;
        this.inicioSolo = false;
    }

    public int getComienzo() {
        return comienzo;
    }

    public boolean isInicioSolo() {
        return inicioSolo;
    }

    public long getTiempoInicial() {
        return tiempoInicial;
    }

    public int getFin() {
        return fin;
    }

    public float getPos() {
        return pos;
    }

    public float getValorMinimo() {
        return valorMinimo;
    }

    public String toString (){
        return "Comienzo: "+comienzo+
                " Fin: "+fin+
                " Posibilidad: "+pos+
                " Valor minimo: "+valorMinimo;
    }

    public void setPos(float pos) {
        this.pos = pos;
    }

    public void setValorMinimo(float valorMinimo) {
        this.valorMinimo = valorMinimo;
    }
}
