package tmp;

import static java.lang.Math.*;
import java.util.List;

import javax.swing.Icon;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;

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
public class DiuresisEstimador extends AlgorithmAdapter {
    public String getName() {
        return "Diuresis error";
    }

        int numDesplazamientos = 2;
        float fs = 1.0F / 1800;
    public void runAlgorithm(SignalManager sm,
                             List<SignalIntervalProperties> signals,
            AlgorithmRunner ar) {
        SignalIntervalProperties in = signals.get(0);
        Signal diuresisSignal = in.getSignal();
        final int numMuestras = diuresisSignal.getValues().length;
        float[] diuresis = diuresisSignal.getValues();
        float pesoCerdo = 25;

        float[][] posiblesValoresDiuresis =
            calculaTodosLosPosiblesValoresPAraCadaMuestra(numMuestras, diuresis, pesoCerdo);
        float[][] diuresisMinMedYMax = calculaDiuresisMinMedYMax(numMuestras, posiblesValoresDiuresis);

        generaSenhalesDiuresis(sm, diuresisSignal, diuresisMinMedYMax);

        calculaYAnhadeErroresMaxYMed(sm, diuresisSignal, numMuestras, diuresis, pesoCerdo, diuresisMinMedYMax);

    }

    private float[][] calculaTodosLosPosiblesValoresPAraCadaMuestra(int numMuestras, float[] diuresis, float pesoCerdo) {
        float[][] posiblesValoresDiuresis = new float[numMuestras][numDesplazamientos];
        for (int muestra = (numDesplazamientos-1); muestra < numMuestras - (numDesplazamientos-1); muestra++) {
            for (int desplazamiento = -(numDesplazamientos-1); desplazamiento <= 0; desplazamiento++) {
                int empezarEn = muestra + desplazamiento;
                float valor = 0;
                for (int i = empezarEn; i < empezarEn + (numDesplazamientos-1); i++) {
                    valor += diuresis[i];//acumular los valores durante una hora para la diuresis
                }
                posiblesValoresDiuresis[muestra][desplazamiento + (numDesplazamientos-1)] = valor / 2;
            }
        }
        return posiblesValoresDiuresis;
    }

    private float[][] calculaDiuresisMinMedYMax(int numMuestras, float[][] posiblesValoresDiuresis) {
        float[][] diuresisMinMedYMax = new float[3][numMuestras];
        for (int muestra = (numDesplazamientos-1); muestra < numMuestras - (numDesplazamientos-1); muestra++) {
            diuresisMinMedYMax[0][muestra] = 1000000;
            diuresisMinMedYMax[2][muestra] = 0;
        }

        for (int muestra = (numDesplazamientos-1); muestra < numMuestras - (numDesplazamientos-1); muestra++) {
            for (int desplazamiento = 0; desplazamiento <= (numDesplazamientos-1); desplazamiento++) {
                diuresisMinMedYMax[0][muestra] =
                        min(posiblesValoresDiuresis[muestra][desplazamiento],
                            diuresisMinMedYMax[0][muestra]);
                diuresisMinMedYMax[1][muestra] = mediaDiuresis(posiblesValoresDiuresis[muestra]);
                diuresisMinMedYMax[2][muestra] =
                        max(posiblesValoresDiuresis[muestra][desplazamiento],
                            diuresisMinMedYMax[2][muestra]);
            }
        }
        return diuresisMinMedYMax;
    }

    private void generaSenhalesDiuresis(SignalManager sm, Signal diuresisSignal, float[][] diuresisMinMedYMax) {
        Signal s2 = new Signal("Mínimo2", diuresisMinMedYMax[0], fs, diuresisSignal.getStart(), "");
        Signal s3 = new Signal("Media2", diuresisMinMedYMax[1], fs, diuresisSignal.getStart(), "");
        Signal s4 = new Signal("Máximo2", diuresisMinMedYMax[2], fs, diuresisSignal.getStart(), "");
        sm.addSignal(s2);
        sm.addSignal(s3);
        sm.addSignal(s4);
    }

    private void calculaYAnhadeErroresMaxYMed(SignalManager sm, Signal diuresisSignal, int numMuestras,
                                              float[] diuresis, float pesoCerdo, float[][] diuresisMinMedYMax) {
        //corregir diuresis
        for (int i = 0; i < diuresis.length; i++) {
    //        diuresis[i] = diuresis[i] * 60 / pesoCerdo;
        }
        //calcular los errores Relativos a la diuresis

        float[][] errores = new float[2][numMuestras];
        for (int muestra = (numDesplazamientos-1); muestra < numMuestras - (numDesplazamientos-1); muestra++) {
            errores[0][muestra] = abs(diuresisMinMedYMax[1][muestra] - diuresis[muestra]);
            float errorMin = abs(diuresisMinMedYMax[0][muestra] - diuresis[muestra]);
            float errorMax = abs(diuresisMinMedYMax[2][muestra] - diuresis[muestra]);
            errores[1][muestra] = max(errorMin, errorMax);

        }

        Signal s5 = new Signal("Error medio2", errores[0], fs, diuresisSignal.getStart(), "");
        Signal s6 = new Signal("Error maximo2", errores[1], fs, diuresisSignal.getStart(), "");
        sm.addSignal(s5);
        sm.addSignal(s6);

        System.out.println("Medio y maximo:\nn");
        System.out.println(""+mediaErrores(errores[0])+" \t "+ standardDeviation( errores[0])+" \t "
                           +mediaErrores(errores[1])+" \t "+ standardDeviation( errores[1]));

    }

    /**
     * @param population an array, the population
     * @return the variance
     */
    public float variance(float[] population) {
            long n = 0;
            float mean = 0;
            float s = 0.0F;

            for (float x : population) {
                    n++;
                    float delta = x - mean;
                    mean += delta / n;
                    s += delta * (x - mean);
            }
            return (s / n);
    }

    /**
     * @param population an array, the population
     * @return the standard deviation
     */
    public float standardDeviation(float[] population) {
            return (float)Math.sqrt(variance(population));
}


        private float mediaDiuresis(float[] datos) {
            float media = 0;
            for (int desplazamiento = 0; desplazamiento <= (numDesplazamientos-1); desplazamiento++) {
                media += datos[desplazamiento];
            }
            return media / numDesplazamientos;
        }


        private float mediaErrores(float[] datos) {
            float media = 0;
            for (int desplazamiento = (numDesplazamientos-1); desplazamiento <= datos.length-(numDesplazamientos-1); desplazamiento++) {
                media += datos[desplazamiento];
            }
            return media / (datos.length-(numDesplazamientos-1)-(numDesplazamientos-1)) ;
        }




    public Icon getIcon() {
        return generateImage("DE");
    }

    public boolean showInGUIOnthe(GUIPositions g) {
        return true;
    }

}
