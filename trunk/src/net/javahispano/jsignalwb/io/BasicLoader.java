/*
 * basicLoader.java
 *
 * Created on 24 de mayo de 2007, 18:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.io;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;

import net.javahispano.jsignalwb.plugins.LoaderAdapter;

/**
 * {@link Loader} que permite cargar en el entorno ficheros ASCII donde cada
 * senhal se organiza como una columna y a distintas columnas (senhales) se
 * separan mediante espacios en blanco o tabulaciones.
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Roman Segador y
 *   Abraham Otero
 */
public class BasicLoader extends LoaderAdapter {

    /** Creates a new instance of basicLoader */
    public BasicLoader() {
    }

    public String getName() {
        return "basicLoader";
    }

    public String getShortDescription() {
        return "Basic Loader";
    }

    /**
     * La extension que soporta es "txt".
     *
     * @return ArrayList
     */
    public ArrayList<String> getAvalaibleExtensions() {
        ArrayList<String> ext = new ArrayList<String>();
        ext.add("txt");
        return ext;
    }

    protected float[][] loadSignals(File f) throws Exception {
        float[][] values = null;
        FileReader fr = new FileReader(f);
        BufferedReader input = new BufferedReader(fr);

        try {
            String line;
            StringTokenizer st;
            int index1 = 0;
            int pos = 0;
            String temp = "";
            while ((line = input.readLine()) != null) {

                st = new StringTokenizer(line, "; \t", false);
                if (index1 == 0) {
                    while (st.hasMoreTokens()) {
                        temp = st.nextToken();

                        if (!temp.equals(";")) {
                            pos++;
                        }
                    }
                }
                index1++;
            }

            values = new float[pos][index1];
            input.close();
            fr.close();
            fr = new FileReader(f);
            input = new BufferedReader(fr);

            index1 = 0;
            pos = 0;
            temp = "";
            while ((line = input.readLine()) != null) {
                st = new StringTokenizer(line, "; \t");
                while (st.hasMoreTokens()) {
                    temp = st.nextToken();
                    values[pos][index1] = Float.parseFloat(temp);//@CNIC *10000000;
                    pos++;

                }
                pos = 0;
                index1++;
            }

        } finally {
            input.close();
            fr.close();
        }
        return values;

    }
    //@CNIC
        protected boolean load(File f, SignalManager sm) throws Exception {
        boolean flag = true;
             super.load(f, sm);/*
        ((Signal)sm.getSignals().toArray()[0]).setFrecuency(500);
        ((Signal)sm.getSignals().toArray()[1]).setFrecuency(500);
        ((Signal)sm.getSignals().toArray()[2]).setFrecuency(500);//@CNIC
       for (Signal s: sm.getSignals()) {
                s.setFrecuency(100);
            }*/
        return flag;
    }
}
