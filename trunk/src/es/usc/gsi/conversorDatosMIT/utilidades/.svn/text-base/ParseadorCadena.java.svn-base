package es.usc.gsi.conversorDatosMIT.utilidades;


import java.util.StringTokenizer;

public class ParseadorCadena {


    public static String[] split(String cadena, String separador) {

        // Rompemos la cadena en tokens
        StringTokenizer strToken = new StringTokenizer(cadena, separador);
        String[] resultado = new String[strToken.countTokens()];

        for (int i = 0; strToken.hasMoreTokens(); i++) {
            resultado[i] = strToken.nextToken();
        }

        return resultado;
    }
} // Fin ParseadorCadena