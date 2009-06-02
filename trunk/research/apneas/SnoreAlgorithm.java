package research.apneas;

import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;

public class SnoreAlgorithm extends AlgorithmAdapter {
    /**
     * Proporciona el hombre del plugin.
     *
     * @return Nombre del plugin
     * @todo Implement this net.javahispano.jsignalwb.plugins.Plugin method
     */
    public String getName() {
        return "Snore";
    }

    public void runAlgorithm(SignalManager sm,
                             float[] signal) {

        float out[] = new float[signal.length];
        System.arraycopy(signal, 0, out, 0, signal.length);
        int Order = 19;
        for (int i = 0; i < Order; i++) {
            out[i] = (short) (int) (b[0] * (double) signal[i]);
            for (int j = 1; j < i; j++) {
                out[i] += (float) b[j] * (float) signal[i - j];
            }

        }

        for (int i = Order; i < signal.length; i++) {
            double buffer = b[0] * (double) signal[i];
            for (int j = 1; j < Order; j++) {
                buffer += b[j] * (double) signal[i - j];
            }

            out[i] = (float) buffer;
        }

        /*  for(int i = 0; i < signal.length; i++)
              if(out[i] > -3 && out[i] < 3)
                  out[i] = 0;   */
        for (int i = 0; i < signal.length; i++) {
            signal[i] = out[i];
        }
    }

    private static double b[] = {
                                -0.00162558392012D, 0.0048384067012599997D, -0.0038737211624500002D,
                                -0.0094577728804400001D, 0.030677290245529999D, -0.031790462858829999D,
                                -0.022249930239230001D, 0.13418406692905999D, -0.25296382436101D, 0.30435388295804D,
                                -0.25296382436101D, 0.13418406692905999D, -0.022249930239230001D,
                                -0.031790462858829999D, 0.030677290245529999D, -0.0094577728804400001D,
                                -0.0038737211624500002D, 0.0048384067012599997D, -0.00162558392012D
    };

}
