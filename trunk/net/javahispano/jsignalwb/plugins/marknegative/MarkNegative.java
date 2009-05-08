/*
 * MarkNegative.java
 *
 * Created on 15 de junio de 2007, 14:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.plugins.marknegative;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.AlgorithmRunner;
import java.util.*;
import javax.swing.JOptionPane;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;
import org.joda.time.DateTime;

/**
 *
 * @author Roman
 */
public class MarkNegative extends AlgorithmAdapter{
    private float value;
    private File file;
    private String data="";
    /** Creates a new instance of MarkNegative */
    public MarkNegative() {
        value=0;
    }

    public String getName(){
        return "Mark Negative Values";
    }

    public boolean hasOwnConfigureGUI() {
        return true;
    }

    public void launchConfigureGUI(JSWBManager jswbManager) {
        String s=JOptionPane.showInputDialog(jswbManager.getParentWindow(),"Introduzca el valor limite de la marca");
        try {
            value=Float.parseFloat(s);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(jswbManager.getParentWindow(),"Error. El valor no tenia un formato valido. No se pudo actualizar");
        }
    }

    public void runAlgorithm(SignalManager sm,
            List<SignalIntervalProperties> signals,
            AlgorithmRunner ar){
        for(SignalIntervalProperties sip:signals){
            ar.setARProgress(0);
            if(!isExecutionCanceled()){
                ar.setTaskRunning("Mark negative values");

                //String signalName=(String)signals.nextElement();
                ar.setSignalRunning(sip.getSignal().getName());
                Signal signal=sip.getSignal();
                float[] data=signal.getValues();
                short[] colors;
                if(signal.hasEmphasisLevel())
                    colors=signal.getEmphasisLevel();
                else
                    colors=new short[data.length];
                for(int index=0;index<data.length&&(!isExecutionCanceled());index++){
                    if((sip.isFullSignal()||(index>=sip.getFirstArrayPosition() &&
                            index<=sip.getLastArrayPosition())) &&
                            data[index]<value){
                        colors[index]=100;

                    }else {

                        if(!signal.hasEmphasisLevel())
                            colors[index]=0;
                    }
                    ar.setARProgress((int)((100f/(float)(data.length))*index));

                }
                signal.setEmphasisLevel(colors);
                ar.setTaskRunning("Swap signal");

            }
        }
    }

    public boolean createFile() {
        return false;
    }

    public void save() {
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            data+="Session saved on:"+TimeRepresentation.timeToString(new DateTime().getMillis())+"\n";
            pw.write(data);
            //System.out.println(data);
            bw.flush();
            pw.close();
            bw.close();
            fw.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setFile(File file) {
        this.file=file;

        try {
            FileReader fr = new FileReader(file);
            BufferedReader input = new BufferedReader(fr);
            String temp="";
            String line;
            while((line=input.readLine()) != null)
                temp+=line;
            data=temp;
            input.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }




}
