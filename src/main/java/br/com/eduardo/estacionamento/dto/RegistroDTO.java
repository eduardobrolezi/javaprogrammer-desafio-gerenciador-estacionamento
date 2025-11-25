package br.com.eduardo.estacionamento.dto;

public class RegistroDTO {
    private String placa;
    private String tipo;
    private double valorPago;

    public RegistroDTO(String placa, String tipo, double valorPago) {
        this.placa = placa;
        this.tipo = tipo;
        this.valorPago = valorPago;
    }

    public String getPlaca() { return placa; }
    public String getTipo() { return tipo; }
    public double getValorPago() { return valorPago; }

    @Override
    public String toString() {
        return String.format("%s [%s] - Pagou: R$ %.2f", placa, tipo, valorPago);
    }
}
