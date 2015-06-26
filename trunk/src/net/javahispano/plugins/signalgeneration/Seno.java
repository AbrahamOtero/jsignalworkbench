package net.javahispano.plugins.signalgeneration;

public class Seno {
    private double amplitud, frecuencia, desfase;
    private SenoGUI panel;
    public Seno(double amplitud, double frecuencia, double desfase, SenoGUI panel) {
        this.amplitud = amplitud;
        this.frecuencia = frecuencia;
        this.desfase = desfase;
        this.panel = panel;
    }

    public double getAmplitud() {
        return amplitud;
    }

    public double getDesfase() {
        return desfase;
    }

    public double getFrecuencia() {
        return frecuencia;
    }

    public SenoGUI getPanel() {
        return panel;
    }

    public boolean isSeleccionado() {
        return panel.isSeleccionado();
    }
}
