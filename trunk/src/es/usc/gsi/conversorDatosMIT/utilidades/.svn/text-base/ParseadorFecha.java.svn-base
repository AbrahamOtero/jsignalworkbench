package es.usc.gsi.conversorDatosMIT.utilidades;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ParseadorFecha {

// Argumento con formato de fecha de tipo hispano: dd/mm/yyyy hh:mm:ss
// Devuelve el numero de segundos desde 1-1-1970
    private static Locale formatoFechaLocal = new Locale("es", "ES"); // Formato de fecha local a Espanha
    private static SimpleDateFormat conversorADate = new SimpleDateFormat(
            "dd/MM/yyyy HH:mm:ss", formatoFechaLocal);

// Devuelve el numero de segundos desde 1-1-1970 00:00:00
    public static long convierteASegundos(String fecha) {

        long res = 0;

        try {
            Date f = conversorADate.parse(fecha); // Convertimos en un objeto Date
            res = f.getTime() / 1000; // Convertimos el tiempo en milisegundos a segundos
        } catch (ParseException e) {
            System.out.println("Imposible convertir a fecha");
        }

        return res;
    }

//////////////////

    public static String calculaFechaFinal(String fechaInicial,
                                           long segundosASumar) {

        String res = "";
        long fechaInicialSegundos = convierteASegundos(fechaInicial);
        long totalSegundos = fechaInicialSegundos + segundosASumar;
        Date fechaFinal = new Date(totalSegundos * 1000); // Argumento en milisegundos
        res = conversorADate.format(fechaFinal);
        return res;

    }


    public static long calculaDiferencia(String fechaInicial, String fechaFinal) {

        long res = 0;
        try {
            Date fInicial = conversorADate.parse(fechaInicial);
            Date fFinal = conversorADate.parse(fechaFinal);
            res = (fFinal.getTime() - fInicial.getTime()) / 1000;
        } catch (ParseException e) {
            System.out.println("Imposible calcular la diferencia");
        }

        return res;

    }

// Elimina los milisegundos de la fecha y la devuelve en formato dd/mm/yyyy hh:mm:ss
// MEJORAR: DEBE DETECTAR SI SE LE PASA UNA FECHA QUE REALMENTE ES EN FORMATO MITDB
    public static String convierteAFormatoHispano(String fechaMITDB) {

        String res = "";

        String[] hora_dia = ParseadorCadena.split(fechaMITDB, " ");
        String dia = hora_dia[1];
        String hora = hora_dia[0];

        String[] horaParseada = ParseadorCadena.split(hora, "."); // Separamos los milisegundos
        String horaSinMilis = horaParseada[0]; // Hora sin milisegundos

        try {
            Date fFormateada = conversorADate.parse(dia + " " + horaSinMilis);
            res = conversorADate.format(fFormateada);
        } catch (ParseException e) {
            System.out.println("Imposible convertir a formato hispano");
        }

        return res;

    }

    ///////////77

    public static void verificaFecha(String fecha) throws ParseException {

        try {
            conversorADate.parse(fecha);
        } catch (ParseException e) {
            throw e;
        }

    }


} // Fin clase ParseadorFecha
