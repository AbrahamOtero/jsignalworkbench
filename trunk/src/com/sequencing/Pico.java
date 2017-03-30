
package com.sequencing;

class Pico {
    
    int maximo;
    int posicion;
    char base;
    
    public static final Pico DUMMY_PICO = new Pico(Integer.MIN_VALUE, Integer.MIN_VALUE, 'o');

    Pico(int posicion, int maximo, char base) {
        this.maximo = maximo;
        this.posicion = posicion;
        this.base = base;
    }

    public int getMaximo() {
        return maximo;
    }

    public void setMaximo(int maximo) {
        this.maximo = maximo;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public char getBase() {
        return base;
    }

    public void setBase(char base) {
        this.base = base;
    }

    @Override
    public String toString() {
        return  "" + base ;
    }
    
}
