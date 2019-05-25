package com.project.tungui.fingcalories;

public class Perfil {

    private double altura;
    private double peso;
    private double edad;
    private int actividad_semanal;
    private String genero;

    // Valor de Calorias

    private int meta_basal;
    private int mantener_cal;
    private int perder_peso_cal;
    private int ganar_peso_cal;

    public Perfil() {
    }

    public Perfil(double altura, double peso, double edad, int actividad_semanal) {
        this.altura = altura;
        this.peso = peso;
        this.edad = edad;
        this.actividad_semanal = actividad_semanal;
    }

    public double getAltura() {
        return altura;
    }

    public double getPeso() {
        return peso;
    }

    public double getEdad() {
        return edad;
    }

    public int getActividad_semanal() {
        return actividad_semanal;
    }

    public String getGenero() {
        return genero;
    }

    public int getMeta_basal() {
        return meta_basal;
    }

    public int getMantener_cal() {
        return mantener_cal;
    }

    public int getPerder_peso_cal() {
        return perder_peso_cal;
    }

    public int getGanar_peso_cal() {
        return ganar_peso_cal;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setEdad(double edad) {
        this.edad = edad;
    }

    public void setActividad_semanal(int actividad_semanal) {
        this.actividad_semanal = actividad_semanal;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setMeta_basal(int meta_basal) {
        this.meta_basal = meta_basal;
    }

    public void setMantener_cal(int mantener_cal) {
        this.mantener_cal = mantener_cal;
    }

    public void setPerder_peso_cal(int perder_peso_cal) {
        this.perder_peso_cal = perder_peso_cal;
    }

    public void setGanar_peso_cal(int ganar_peso_cal) {
        this.ganar_peso_cal = ganar_peso_cal;
    }
}
