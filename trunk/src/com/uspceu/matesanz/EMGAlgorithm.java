package com.uspceu.matesanz;

import com.uspceu.SimpleAlgorithm;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Math.abs;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.plugins.Plugin;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;

/**
 *
 * @author Anuska
 */
public class EMGAlgorithm extends SimpleAlgorithm {

    float anchoVentanaSeg = 0.3F;
    float desplazamientoVentanaSeg = 0.1F;

    @Override
    public void runAlgorithm(SignalManager signalManager, Signal signal, float[] datos, float fs) {

        //La frecuencia de muestreo es 1000
        int anchoVentana = (int) (anchoVentanaSeg * fs);
        int desplazamientoVentana = (int) (desplazamientoVentanaSeg * fs);

       Threshold tsenal = new Threshold(datos);
        //CUADRADO SENAL
        float newData[] = new float[datos.length];
        for (int i = 1; i < datos.length; i++) {
            newData[i] = (datos[i] - tsenal.mediana) * (datos[i] - tsenal.mediana);
        }

        Signal square = new Signal("Cuadrado de" + signal.getName(),
                newData, fs, signal.getStart(), "Unidades");
        square.adjustVisibleRange();
        signalManager.addSignal(square);

        //POTENCIA SENAL
        float[] potenciaPromedioEnVentana = new float[(datos.length / desplazamientoVentana) +1];
        float[] potencia = square.getValues();
        
        float aux=0;
        int ventana =0;
        for (int i =anchoVentana; i < datos.length; i = i + desplazamientoVentana) {            
            
            aux = 0;
            for (int j = i; j != i - anchoVentana; j--) {                
                
                aux = potencia[j] + aux;
            }
            
            //potenciaPromedioEnVentana[(i-desplazamientoVentana) / desplazamientoVentana] = aux / desplazamientoVentana;//antes
            potenciaPromedioEnVentana[ventana] = aux / anchoVentana;
            ventana++;
            
        }
        final float fsPotencia = 1/desplazamientoVentanaSeg;

        Signal potenciaSenal = new Signal("Potencia de" + signal.getName(), potenciaPromedioEnVentana, fsPotencia, signal.getStart(), "");
        potenciaSenal.adjustVisibleRange();
        signalManager.addSignal(potenciaSenal);

        //ACTIVACION SENAL
        float[] actividad = new float[datos.length / desplazamientoVentana + 1];
        MaquinaDeEstados maquina = new MaquinaDeEstados(fsPotencia);
        //Threshold

        Threshold t1 = new Threshold(potenciaPromedioEnVentana);
        maquina.threshold = t1;

        //Maquina de estados
        for (int i = 0; i < potenciaPromedioEnVentana.length; i++) {
            
            maquina.funcionaMaquina(potenciaPromedioEnVentana[i],desplazamientoVentana);
            if (maquina.estadoMaquina == Estados.NO_ACTIVIDAD) {
                actividad[i] = 0;
            }
            if (maquina.estadoMaquina == Estados.TRANSICION) {
                actividad[i] = 0;
            }
            if (maquina.estadoMaquina == Estados.ACTIVIDAD) {
                actividad[i] = 1;
            }
        }

        Signal activacion = new Signal("Activacion de" + signal.getName(), actividad, fsPotencia, signal.getStart(), "");
        activacion.adjustVisibleRange();
        signalManager.addSignal(activacion);
    }

    public String getName() {
        return "EMG";
    }

}
