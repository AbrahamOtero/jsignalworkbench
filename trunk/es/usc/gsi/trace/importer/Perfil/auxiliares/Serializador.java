package es.usc.gsi.trace.importer.Perfil.auxiliares;

import java.io.*;
import java.util.jar.*;

import es.usc.gsi.trace.importer.Perfil.PTBMInterface;


public class Serializador {

    public void guardaPTBM(String archivo, PTBMInterface ptbm) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new
                    FileOutputStream(archivo));
            out.writeObject(ptbm);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public PTBMInterface cargaPTBM(String archivo) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(
                    archivo));
            PTBMInterface ptbm = (PTBMInterface) in.readObject();
            in.close();
            return ptbm;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }

    }

    public PTBMInterface cargaPTBM(String path, String archivo, boolean es_jar) {
        if (path == null) {
            return cargaPTBM(archivo);
        } else {
            try {
                JarInputStream jar = new JarInputStream(new FileInputStream(
                        path
                        /*"Herramienta_Monitorizacion.jar"*/));
                JarEntry entry = jar.getNextJarEntry();
                while (!entry.getName().endsWith(archivo)) {
                    entry = jar.getNextJarEntry();
                }

                JarFile jf = new JarFile(path
                             /*"Herramienta_Monitorizacion.jar"*/);
                ObjectInputStream in = new ObjectInputStream(jf.getInputStream(
                        entry));
                PTBMInterface ptbm = (PTBMInterface) in.readObject();
                in.close();
                return ptbm;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public PTBMInterface cargaPTBM(String archivo, boolean es_jar) {
        try {
            JarInputStream jar = new JarInputStream(new FileInputStream(
                    "Herramienta_Monitorizacion.jar"));
            JarEntry entry = jar.getNextJarEntry();
            while (!entry.getName().endsWith(archivo)) {
                entry = jar.getNextJarEntry();
            }

            JarFile jf = new JarFile("Herramienta_Monitorizacion.jar");
            ObjectInputStream in = new ObjectInputStream(jf.getInputStream(
                    entry));
            PTBMInterface ptbm = (PTBMInterface) in.readObject();
            in.close();
            return ptbm;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}