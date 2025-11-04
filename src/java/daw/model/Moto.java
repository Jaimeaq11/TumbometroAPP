package daw.model;

import java.io.Serializable;

public class Moto implements Serializable {
    
    private String marca;
    private String modelo;
    private double potencia;
    private double cilindrada;
    private String color;
    
    public Moto() {
    }

    public Moto(String marca, String modelo) {
        this.marca = marca;
        this.modelo = modelo;
    }

    public Moto(String marca, String modelo, double potencia, double cilindrada, String color) {
        this.marca = marca;
        this.modelo = modelo;
        this.potencia = potencia;
        this.cilindrada = cilindrada;
        this.color = color;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPotencia() {
        return potencia;
    }

    public void setPotencia(double potencia) {
        this.potencia = potencia;
    }

    public double getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(double cilindrada) {
        this.cilindrada = cilindrada;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
