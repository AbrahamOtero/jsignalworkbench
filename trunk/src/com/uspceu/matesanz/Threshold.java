package com.uspceu.matesanz;

import java.util.Arrays;

public class Threshold {

    float mediana;
    float quartil1;
    float quartil3;
    float media;

    Threshold(float[] datos) {
        float suma = 0;
        for (int i = 0; i < datos.length; i++) {
            suma = datos[i] + suma;
        }
        media = suma / datos.length;
        float[] datosOrdenados = new float[datos.length];
        for (int i = 0; i < datos.length; i++) {
            datosOrdenados[i] = datos[i];
        }

        Arrays.sort(datosOrdenados);
        int aux = (int) datosOrdenados.length / 4;
        quartil1 = datosOrdenados[aux];
        mediana = datosOrdenados[aux * 2];
        quartil3 = datosOrdenados[aux * 3];

    }

    public float getMediana() {
        return mediana;
    }

    public void setMediana(float mediana) {
        this.mediana = mediana;
    }

    public float getQuartil1() {
        return quartil1;
    }

    public void setQuartil1(float quartil1) {
        this.quartil1 = quartil1;
    }

    public float getQuartil3() {
        return quartil3;
    }

    public void setQuartil3(float quartil3) {
        this.quartil3 = quartil3;
    }

    public float getMedia() {
        return media;
    }

    public void setMedia(float media) {
        this.media = media;
    }

}
