package com.uspceu.matesanz;

enum Estados {

    NO_ACTIVIDAD, TRANSICION, ACTIVIDAD
};

class MaquinaDeEstados {

    float tiempoEsperaEnTransicion = 1.0F;
    float tiempoEsperaEnActividad = 1.0F;
    Estados estadoMaquina;
    float dato;
    Threshold threshold;
    float muestrasEsperaEnTransicion;
    float muestrasEsperaEnActividad;

    MaquinaDeEstados(float fs) {
        muestrasEsperaEnTransicion = tiempoEsperaEnTransicion * fs;
        muestrasEsperaEnActividad = tiempoEsperaEnActividad * fs;
        estadoMaquina = Estados.NO_ACTIVIDAD;
    }
//@Todo no veo en absoluto como se cuentan las muestras de espera para pasar de
// TRANSICION a ACTIVIDAD Y de ACTIVIDAD a NO ACTIVIDAD
    
    public void funcionaMaquina(float dato, float muestrasPorVentanaDePotencia, float fs) {
        final float THRESHOLD = 1.5f * threshold.mediana;
        if (estadoMaquina == Estados.NO_ACTIVIDAD) {
            if (dato >= THRESHOLD) {
                estadoMaquina = Estados.TRANSICION;
            }
        }
        if (estadoMaquina == Estados.TRANSICION) {
            if (dato < THRESHOLD) {
                estadoMaquina = Estados.NO_ACTIVIDAD;
                //@Todo  No entiendo esta linea
                muestrasEsperaEnTransicion = tiempoEsperaEnTransicion * fs;
            }
            if (dato >= THRESHOLD) {
                if (muestrasEsperaEnTransicion <= 0) {
                //@Todo  No entiendo esta linea
                    muestrasEsperaEnTransicion = tiempoEsperaEnTransicion * fs;
                    estadoMaquina = Estados.ACTIVIDAD;
                } else {
                //@Todo  No entiendo esta linea
                    muestrasEsperaEnTransicion = muestrasEsperaEnTransicion - muestrasPorVentanaDePotencia;
                }
            }
        }
        if (estadoMaquina == Estados.ACTIVIDAD) {
            if (dato <= THRESHOLD && muestrasEsperaEnActividad <= 0) {
                estadoMaquina = Estados.NO_ACTIVIDAD;//transición?
            }
            if (dato >= THRESHOLD) {
                //@Todo  No entiendo esta linea
                muestrasEsperaEnActividad = tiempoEsperaEnActividad * fs;

            } else {
                //@Todo  No entiendo esta linea
                muestrasEsperaEnActividad = muestrasEsperaEnActividad - muestrasPorVentanaDePotencia;
            }
        }

    }

    public Estados getEstadoMaquina() {
        return estadoMaquina;
    }

    public void setEstadoMaquina(Estados estadoMaquina) {
        this.estadoMaquina = estadoMaquina;
    }

    public Threshold getThreshold() {
        return threshold;
    }

    public void setThreshold(Threshold threshold) {
        this.threshold = threshold;
    }

    public float geTiempoEsperaEnTransicion() {
        return tiempoEsperaEnTransicion;
    }

    public void setTiempoEsperaEnTransicion(float tiempo2) {
        this.tiempoEsperaEnTransicion = tiempo2;
    }

    public float getTiempoEsperaEnActividad() {
        return tiempoEsperaEnActividad;
    }

    public void setTiempoEsperaEnActividad(float tiempo3) {
        this.tiempoEsperaEnActividad = tiempo3;
    }

}
