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

    float anchoVentanaSeg = 1;
    float desplazamientoVentanaSeg = 0.5F;

    @Override
    public void runAlgorithm(SignalManager signalManager, Signal signal, float[] datos, float fs) {

        //La frecuencia de muestreo es 1000
        //@Todo No se usa para nada
        int anchoVentana = (int) (anchoVentanaSeg * fs);
        int desplazamientoVentana = (int) (desplazamientoVentanaSeg * fs);

//        //Poner base de la senal a cero
//        Threshold tsenal = new Threshold(datos);
//        for (int i = 0; i < datos.length; i++) {
//            if (datos[i] < tsenal.media + tsenal.quartil1 && datos[i] > tsenal.media - tsenal.quartil1) {
//                datos[i] = tsenal.media;
//            }
//        }
        //CUADRADO SENAL
        float newData[] = new float[datos.length];
//@Todo ¿que son estos numeros magicos?
        for (int i = 1; i < datos.length; i++) {
            newData[i] = (datos[i] - 450) * (datos[i] - 450);
        }

        Signal square = new Signal("Cuadrado de" + signal.getName(),
                newData, fs, signal.getStart(), "Unidades");
        square.adjustVisibleRange();
        signalManager.addSignal(square);

        //POTENCIA SENAL
        float[] potenciaPromedioEnVentana = new float[(datos.length / desplazamientoVentana) +1];//datos entre frecuencia
        float[] potencia = square.getValues();
        
        float aux=0;
        for (int i = desplazamientoVentana; i < datos.length; i = i + desplazamientoVentana) {
            aux = 0;
            //@Todo este bucle tendría que ir hasta el ancho de la ventana, no hasta el desplazamiento
            for (int j = i; j != i - desplazamientoVentana; j--) {
                aux = potencia[j] + aux;
            }
            //@Todo habría que dividir por el ancho de la ventana
            potenciaPromedioEnVentana[(i-desplazamientoVentana) / desplazamientoVentana] = aux / desplazamientoVentana;
            //potenciaPromedioEnVentana[i/desplazamientoVentana] = aux / desplazamientoVentana; //antes
        }

        Signal potenciaSenal = new Signal("Potencia de" + signal.getName(), potenciaPromedioEnVentana,
                1 / desplazamientoVentanaSeg, signal.getStart(), "");
        potenciaSenal.adjustVisibleRange();
        signalManager.addSignal(potenciaSenal);

        //ACTIVACION SENAL
        float[] actividad = new float[datos.length / desplazamientoVentana + 1];
        MaquinaDeEstados maquina = new MaquinaDeEstados(fs);
        //Threshold

        Threshold t1 = new Threshold(potenciaPromedioEnVentana);
        maquina.threshold = t1;

        //Maquina de estados
        for (int i = 0; i < potenciaPromedioEnVentana.length; i++) {
            
                //@Todo  Ojo que lo logico sería pasar la frecuencia de muestreo de la señal
            //de potencia promedio
            maquina.funcionaMaquina(potenciaPromedioEnVentana[i],desplazamientoVentana,fs);
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

        Signal activacion = new Signal("Activacion de" + signal.getName(), actividad,
                1 / desplazamientoVentanaSeg, signal.getStart(), "");
        activacion.adjustVisibleRange();
        signalManager.addSignal(activacion);
    }

    public String getName() {
        return "EMG";
    }

}
