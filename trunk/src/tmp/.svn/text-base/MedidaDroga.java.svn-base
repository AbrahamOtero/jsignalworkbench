package tmp;

public class MedidaDroga {
    //todas las duraciones se dan en segundos
    private float duracionTotal, duracionCaida, duracionRecuperacion, caida,
            valorMinimo, valorBasal,area;
    private String droga, parametro;
    private float areaRatio;
    public MedidaDroga() {
    }

    public float getArea() {
        return area;
    }

    public float getCaida() {
        return caida;
    }

    public float getDuracionCaida() {
        return duracionCaida;
    }

    public float getDuracionRecuperacion() {
        return duracionRecuperacion;
    }

    public float getDuracionTotal() {
        return duracionTotal;
    }

    public String getDroga() {
        return droga;
    }

    public float getValorBasal() {
        return valorBasal;
    }

    public float getValorMinimo() {
        return valorMinimo;
    }

    public String getParametro() {
        return parametro;
    }

    public float getAreaRatio() {
        return areaRatio;
    }

    public void setValorMinimo(float valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public void setValorBasal(float valorBasal) {
        this.valorBasal = valorBasal;
    }

    public void setDroga(String droga) {
        this.droga = droga;
    }

    public void setDuracionTotal(float duracionTotal) {
        this.duracionTotal = duracionTotal;
    }

    public void setDuracionRecuperacion(float duracionRecuperacion) {
        this.duracionRecuperacion = duracionRecuperacion;
    }

    public void setDuracionCaida(float duracionCaida) {
        this.duracionCaida = duracionCaida;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public void setCaida(float caida) {
        this.caida = caida;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public void setAreaRatio(float areaRatio) {
        this.areaRatio = areaRatio;
    }

    public String toString (boolean human) {
        if (!human) {
            return droga + "," + parametro + "," + duracionTotal + "," + duracionCaida + ","
                    + duracionRecuperacion + "," +
                    caida + ","+ caida/valorBasal + "," + valorMinimo + "," + valorBasal + "," + area + "," + areaRatio;

        }
        String texto= "Subida";
if (caida <0) {
    texto = "Caída";
}
        return "Duración total: " + duracionTotal + "\nDuración "+ texto+ ": " + duracionCaida + "\nDuración Recuperación: "
                + duracionRecuperacion + "\n"+ texto+ ": " + caida + "\n"+ texto+ "/ValorBasal: "+ caida/valorBasal +
                "\nValor Mínimo: " + valorMinimo + "\nValor basal: " + valorBasal +
                "\nÁrea: " + area + "\nÁrea ratio:" + areaRatio;

    }
}
