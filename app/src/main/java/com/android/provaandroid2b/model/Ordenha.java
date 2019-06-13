package com.android.provaandroid2b.model;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.io.Serializable;
import java.util.Date;

public class Ordenha extends SugarRecord implements Serializable {
    @Unique
    private int identificador;
    private MatrizLeiteira matrizLeiteira;
    private double qtdLitros;
    private String data;
    private String hora;

    public Ordenha() {
    }

    public Ordenha(int identificador, MatrizLeiteira matrizLeiteira, double qtdLitros, String data, String hora) {
        this.identificador = identificador;
        this.matrizLeiteira = matrizLeiteira;
        this.qtdLitros = qtdLitros;
        this.data = data;
        this.hora = hora;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public MatrizLeiteira getMatrizLeiteira() {
        return matrizLeiteira;
    }

    public void setMatrizLeiteira(MatrizLeiteira matrizLeiteira) {
        this.matrizLeiteira = matrizLeiteira;
    }

    public double getQtdLitros() {
        return qtdLitros;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setQtdLitros(double qtdLitros) {
        this.qtdLitros = qtdLitros;
    }


    @Override
    public String toString() {
        return identificador + " - "  + qtdLitros + " - " + data ;
    }
}
