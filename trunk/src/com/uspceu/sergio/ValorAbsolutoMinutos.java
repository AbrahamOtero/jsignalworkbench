/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uspceu.sergio;

import com.uspceu.SimpleAlgorithm;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;

/**
 *
 * @author Sergio
 */
public class ValorAbsolutoMinutos extends SimpleAlgorithm {

    public String getName() {
        return "Calculo del valor absoluto minuto a minuto";
    }

    @Override
    public void runAlgorithm(SignalManager signalManager, Signal signal, float[] datos, float fs) {

        Signal biometrixAcumulado = signalManager.getSignal("Minuto a minuto de Acumulado de Biometrix");
        Signal biometrix = signalManager.getSignal("Biometrix");
        Signal bascula = signalManager.getSignal("Minuto a minuto de Bascula");

        float arrayBiometrixAcumulado[];
        float arrayBascula[];

        float arrayBiometrix[] = biometrix.getValues();
        analizaOy100(arrayBiometrix);

        arrayBiometrixAcumulado = biometrixAcumulado.getValues();
        arrayBascula = bascula.getValues();
        float newData[] = new float[arrayBascula.length];
        float newData2[] = new float[arrayBascula.length];

        for (int i = 0; i < arrayBascula.length; i++) {
            newData[i] = Math.abs(arrayBiometrixAcumulado[i] - arrayBascula[i]);
            newData2[i] = arrayBiometrixAcumulado[i] - arrayBascula[i];
        }

        Signal square = new Signal("Error absoluto minuto a minuto",
                newData, biometrixAcumulado.getSRate(), signal.getStart(), "Unidades");
        square.adjustVisibleRange();
        signalManager.addSignal(square);
        Signal square2 = new Signal("Error minuto a minuto",
                newData2, biometrixAcumulado.getSRate(), signal.getStart(), "Unidades");
        square2.adjustVisibleRange();
        signalManager.addSignal(square2);
    }

    private void analizaOy100(float[] arrayBiometrix) {
        int transicionDesdeCero = 0, transicionDesdeCien = 0;
        float sumaCeros = 0, sumaCienes = 0;

        int i = 0;

        while (i < arrayBiometrix.length) {
            while (i < arrayBiometrix.length && arrayBiometrix[i] == 0) {
                i++;
            }

            if (i == arrayBiometrix.length) {
                break;
            }

            transicionDesdeCero++;
            sumaCeros += arrayBiometrix[i];
            System.out.println("Sumando a Ceros " + arrayBiometrix[i]);

            while (i < arrayBiometrix.length && arrayBiometrix[i] > 0 && arrayBiometrix[i] < 100) {
                i++;
            }

            if (i == arrayBiometrix.length) {
                break;
            }

            if (arrayBiometrix[i] != 0) {//llegamos a 100
                while (i < arrayBiometrix.length && arrayBiometrix[i] == 100) {
                    i++;
                }

                if (i == arrayBiometrix.length) {
                    break;
                }

                if (arrayBiometrix[i] == 0) {
                    continue;
                }
                transicionDesdeCien++;
                sumaCienes += (arrayBiometrix[i] - 100);

                System.out.println("sumando a cienes " + (arrayBiometrix[i] - 100));

                while (i < arrayBiometrix.length && arrayBiometrix[i] > 100) {
                    i++;
                }
            }

        }

        System.out.println("Ceros " + sumaCeros / transicionDesdeCero);
        System.out.println("Cien " + sumaCienes / transicionDesdeCien);

    }

}
