/*
 * BasicSaver.java
 *
 * Created on 24 de mayo de 2007, 19:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.io;

import java.io.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import net.javahispano.jsignalwb.plugins.SaverAdapter;

/**
 * {@link Saver} basico que almacena solo los valores de las senhales cargados en
 * el entorno en un fichero ASCII donde cada senhal se almacena como una columna
 * separadas por tabulaciones.
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Roman Segador y
 *   Abraham Otero
 */
public class BasicSaver extends SaverAdapter {


    public String getName() {
        return "Basic Saver";
    }

    public String getShortDescription() {
        return "Basic Saver";
    }

    public ArrayList<String> getAvalaibleExtension() {
        ArrayList<String> ext = new ArrayList<String>();
        ext.add("txt");
        return ext;
    }

    public boolean save(File f, float[][] data, boolean addTXT) throws Exception {
        if (addTXT && !f.getName().toLowerCase().endsWith(".txt")) {
            f = new File(f.getPath() + ".txt");
        }
        return this.save(f, data);
    }

    public boolean save(File f, float[][] data) throws Exception {
        if (f.exists()) {
            if (JOptionPane.showConfirmDialog(null,
                                              "overwirte " + f.getCanonicalPath() + "?", "OverWrite",
                                              JOptionPane.YES_NO_OPTION) != 0) {
                return false;
            }
        }
        f.createNewFile();
        f.delete();
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        try {
            boolean flag = true;
            for (int index = 0; flag; index++) {
                flag = false;
                for (int index2 = 0; index2 < data.length; index2++) {
                    if (index < data[index2].length) {
                        pw.print(data[index2][index]);
                        flag = true;
                    }
                   if (!flag) continue;
                   if (index2!= data.length-1) pw.print("\t");
                }
                pw.println();
            }
            bw.flush();
        } finally {
            pw.close();
            bw.close();
            fw.close();
        }
        return true;
    }

}
