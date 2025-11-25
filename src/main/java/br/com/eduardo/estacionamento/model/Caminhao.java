package br.com.eduardo.estacionamento.model;

import java.time.LocalDateTime;

public class Caminhao extends Veiculo {
    public Caminhao(String placa, LocalDateTime horaEntrada) {
        super(placa, horaEntrada);
    }

    @Override
    public double getTarifaPorHora() {
        return 10.0;
    }

    @Override
    public String getTipo() {
        return "Caminhão";
    }
}
