package net.javahispano.plugins.basicstats;

import java.text.DecimalFormat;
import java.util.Locale;

public class MyFloat {
    private static boolean hay_parser = false;
    private static DecimalFormat decimal_format;
    private static int numero_decimales_actuales = 3;

    /**
     *
     */
    private static void contruyePraser() {
        if (!hay_parser) {
            Locale default_locale = Locale.getDefault();
            //Ponemos como localidad la inglesa, pa que pille . en vez de ,
            Locale.setDefault(new Locale("en", "GB"));
            decimal_format = new DecimalFormat("###.###");
            //Ahora que ya tengo un parseador "A la inglesa" volvemos pa espananha:
            Locale.setDefault(default_locale);
            hay_parser = true;
        }

    }

    /**
     *Se emplea para formatear los numeros que seguro estan bien,
     * es decir los que ya se han chequeado y se vuelven a mostrar al usuario.
     * @param numero
     * @return
     */
    public static float parseFloatSeguro(String numero) {
        if (!(numero.equals("&")) && !(numero.equals("-&"))) {
            return Float.parseFloat(numero);
        } else if (numero.equals("&")) {
            return Integer.MAX_VALUE / 1000;
        } else {
            return Integer.MIN_VALUE / 1000; //Por que si no en validar dan overflow
        }
    }

    /**
     *
     * @param numero
     * @return
     * @throws Exception
     */
    public static float parseFloat(String numero) throws Exception {
        try {
            if (!(numero.equals("&")) && !(numero.equals("-&"))) {
                return Float.parseFloat(numero);
            } else if (numero.equals("&")) {
                return Integer.MAX_VALUE / 1000;
            } else {
                return Integer.MIN_VALUE / 1000; //Por que si no en validar dan overflow
            }
        } catch (NumberFormatException ex) {
            throw (new Exception("Numero Mal formado"));
        }
    }

    /**
     *
     * @param f
     * @return
     */
    public static String formateaNumero(float f) {
        return formateaNumero(Float.toString(f));
    }

    /**
     * Devuelve null si No se pudo completar la operacion
     * @param numero
     * @return
     */
    public static String formateaNumero(String numero) {
        if (!hay_parser) {
            contruyePraser();
        }
        numero = numero.trim();
        if (numero.equals("&") || numero.equals("-&")) {
            return numero;
        }

        try {
            String resultado = decimal_format.format(parseFloat(numero));
            return resultado;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param M
     * @return
     */
    private static String convierteAString(float M) {
        if (M != Float.NEGATIVE_INFINITY && M != Float.POSITIVE_INFINITY) {
            return Float.toString(M);
        } else if (M == Float.NEGATIVE_INFINITY) {
            return "-&";
        } else {
            return "&";
        }
    }

    /**
     * Emplear para cambiar el numero de digitos decimales del patron
     * @param numero_decimaales
     */
    public static void setNumeroDecimales(int numero_decimales) {
        if (!hay_parser) {
            contruyePraser();
        }
        String pattern = "###";
        if (numero_decimales > 0) {
            pattern = pattern + ".";
            for (int i = 0; i < numero_decimales; i++) {
                pattern = pattern + "#";
            }

        }
        numero_decimales_actuales = numero_decimales;
        decimal_format.applyPattern(pattern);
    }

    /**
     *
     * @return
     */
    public static int getNumeroDecimalesActuales() {
        return numero_decimales_actuales;
    }
}
