package com.uspceu.matesanz;

enum Estados {

    NO_ACTIVIDAD, TRANSICION, ACTIVIDAD
};

class MaquinaDeEstados {

    float tiempoEsperaEnTransicion = 0.5F;
    float tiempoEsperaEnActividad =0.5F;
    Estados estadoMaquina;
    float dato;
    Threshold threshold;
    final float frecuenciaDePotencia;
    final float muestrasEsperaEnTransicion;
    final float muestrasEsperaEnActividad;
    float muestrasEsperandoEnTransicion;
    float muestrasEsperandoEnActividad;

    MaquinaDeEstados(float fs) {
        muestrasEsperaEnTransicion = tiempoEsperaEnTransicion * fs;
        muestrasEsperaEnActividad = tiempoEsperaEnActividad * fs;
        estadoMaquina = Estados.NO_ACTIVIDAD;
        this.frecuenciaDePotencia=fs;
    }

    public void funcionaMaquina(float dato, float muestrasPorVentanaDePotencia) {
        final float THRESHOLD = 1.5f * threshold.mediana;
        if (estadoMaquina == Estados.NO_ACTIVIDAD) {
            if (dato >= THRESHOLD) {
                estadoMaquina = Estados.TRANSICION;
            }
        }
        else if (estadoMaquina == Estados.TRANSICION) {
            if (dato < THRESHOLD) {
                estadoMaquina = Estados.NO_ACTIVIDAD;
                muestrasEsperandoEnTransicion = 0;
            }
            else if (dato >= THRESHOLD) {
                if (muestrasEsperandoEnTransicion >= muestrasEsperaEnTransicion) {
                    estadoMaquina = Estados.ACTIVIDAD;
                    muestrasEsperandoEnActividad=0;
                } else {
                    muestrasEsperandoEnTransicion++;
                }
            }
        }
        else if (estadoMaquina == Estados.ACTIVIDAD) {
            if (dato <= THRESHOLD && muestrasEsperandoEnActividad >= muestrasEsperaEnActividad) {
                estadoMaquina = Estados.NO_ACTIVIDAD;//transición?
                muestrasEsperandoEnActividad=0;
            }
            else if (dato >= THRESHOLD) {
                muestrasEsperandoEnActividad=0;

            } else {
                muestrasEsperandoEnActividad++;
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
