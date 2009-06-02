package research.apneas;

import static java.lang.Math.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Abraham Otero
 * @version 0.5
 */
public class LimitacionFlujo extends Intervalo {
    private float porcentajeReduccion;
    private boolean usarEnEstadisticas = true;
    private float[] valorBasal = null, delta = null;
    public LimitacionFlujo(int principio, int fin, int posibilidad) {
        super(principio, fin, posibilidad);
    }

    public LimitacionFlujo(Intervalo i) {
        super(i);
    }

    public float getPorcentajeReduccion() {
        return porcentajeReduccion;
    }

    public boolean isUsarEnEstadisticas() {
        return usarEnEstadisticas;
    }

    public void setPorcentajeReduccion(float porcentajeReduccion) {
        this.porcentajeReduccion = porcentajeReduccion;
    }

    public void setUsarEnEstadisticas(boolean usarEnEstadisticas) {
        this.usarEnEstadisticas = usarEnEstadisticas;
    }

    public void setValorBasal(float[] valorBasal, float[] delta) {
        this.valorBasal = valorBasal;
        this.delta = delta;
        float energia = 0, energiaBasal = 0;
        for (int i = principio; i < fin; i++) {
            energia += energia + pow(delta[i], 2);
            energiaBasal += energiaBasal + pow(valorBasal[i], 2);
        }
        porcentajeReduccion = energia / (energiaBasal * (fin - principio));
    }
}
