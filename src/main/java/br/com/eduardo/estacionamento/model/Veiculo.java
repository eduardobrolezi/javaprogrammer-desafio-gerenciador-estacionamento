package br.com.eduardo.estacionamento.model;

import java.time.LocalDateTime;

public abstract class Veiculo {
    private String placa;
    private LocalDateTime horaEntrada;

    public Veiculo(String placa, LocalDateTime horaEntrada) {
        this.placa = placa;
        this.horaEntrada = horaEntrada;
    }

    public String getPlaca() {
        return placa;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public abstract double getTarifaPorHora();
    public abstract String getTipo();
}
