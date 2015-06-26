package research.mining;

import java.io.*;
import java.util.TreeSet;

import javax.swing.JOptionPane;

import research.mining.TemporalEvent.DETAILLEVEL;

public class SaveInfo {
    //Nivel de detalle a emplear al generar la informacion
    private DETAILLEVEL level;
    private TreeSet<Desaturation> desatTree;
    private String fileName = "C:\\tmp.csv";
    private long beginingRecording;
    //si vale true indica que debe de incluirse el tipo de episodio correspondiente
    //en la informacion generada; en caso contrario nos incluiran los episodios
    //correspondientes
    private boolean includeDesat = true, includeFlux = true, includeThorax = true,
    includeAbdomen = true;

    public SaveInfo(TreeSet<Desaturation> desatTree) {
        this.desatTree = desatTree;
    }

    /**
     * Este metodo debe devolver una cadena de caracteres conteniendo  toda la
     * informacion a guardar en el fichero.
     *
     * @return String
     */
    public String genrateDescriptors() {
        String descriptors = "";
        for(Desaturation desaturation: desatTree){
            //Sólo desaturaciones bien identificadas y asociadas con una
            //única limitacion de flujo
            if (desaturation.getDesaturatedArea()<= 0 ||
                    desaturation.getLimitations().size() == 0) {
                continue;
            }
            descriptors += desaturation.genrateDescriptors(level, beginingRecording);
        }
        return descriptors;
    }

    public void saveDescriptors() {
        File f = new File(fileName);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(f);
            printHead(pw);
            pw.print(this.genrateDescriptors());
            pw.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            if (pw != null) {
                pw.close();
            }
            JOptionPane.showMessageDialog(null,
                                          "Ha sucedido un error",
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);

        }
    }

    private void printHead(PrintWriter pw) {
        pw.print("timeDesat	tBeginingDesat	DurationDesat	minValueDesat"+
                 "	meanValueDesat	beginValueDesat	endValueDesat	fallValueDesat"+
                 "	riseValueDesat	fallDurationDesat	riseDurationDesat"+
                 "	fallSlopeDesat	riseSlopeDesat	desaturatedArea	timeFlux"+
                 "	tBeginingFlux	durationFlux	A|H	energyFlux"+
                 "	basalEnergyBefore	BasalEnergyAfter	CompositeEvent	NumLimitations	tBeginingBegining"+
                 "	tEndMin	tEndEnd" +
                 "	Age	Weight	BMI	AHI	Afternoon	MeanDurationAp	MeanDurationHy"+
                 "	MeanDurationDesat	MeandValSpO2Com	BasalSpO2Com	MeandValSpO2	BasalSpO2	Diagnostic	ID\n");
    }

    public boolean isIncludeAbdomen() {
        return includeAbdomen;
    }

    public boolean isIncludeThorax() {
        return includeThorax;
    }

    public boolean isIncludeFlux() {
        return includeFlux;
    }

    public boolean isIncludeDesat() {
        return includeDesat;
    }

    public DETAILLEVEL getLevel() {
        return level;
    }

    public long getBeginingRecording() {
        return beginingRecording;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setIncludeAbdomen(boolean includeAbdomen) {
        this.includeAbdomen = includeAbdomen;
    }

    public void setIncludeDesat(boolean includeDesat) {
        this.includeDesat = includeDesat;
    }

    public void setIncludeFlux(boolean includeFlux) {
        this.includeFlux = includeFlux;
    }

    public void setIncludeThorax(boolean includeThorax) {
        this.includeThorax = includeThorax;
    }

    public void setLevel(DETAILLEVEL level) {
        this.level = level;
    }

    public void setBeginingRecording(long beginingRecording) {
        this.beginingRecording = beginingRecording;
    }
}
