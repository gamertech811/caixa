package com.kauecaco.caixa.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "saldo")
public class Saldo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private
    Integer id;
    private double valor;
    private LocalDate data;

    public Saldo(double valor,LocalDate data){
        this.valor = valor;
        this.data = data;
    }
    public Saldo(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double saldo) {
        this.valor = saldo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
