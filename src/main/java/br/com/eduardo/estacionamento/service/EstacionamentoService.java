package br.com.eduardo.estacionamento.service;

import br.com.eduardo.estacionamento.dto.RegistroDTO;
import br.com.eduardo.estacionamento.model.Veiculo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EstacionamentoService {

    private int capacidade;
    private List<Veiculo> vagas;
    private List<RegistroDTO> historico;
    private double totalArrecadado;

    public EstacionamentoService(int capacidade) {
        this.capacidade = capacidade;
        this.vagas = new ArrayList<>();
        this.historico = new ArrayList<>();
        this.totalArrecadado = 0.0;
    }

    public boolean entrar(Veiculo v) {
        if (vagas.size() < capacidade) {
            vagas.add(v);
            //System.out.println("Veículo: " +  v.getPlaca() + "entrou às: " +v.getHoraEntrada().format(mascara1));
            this.salvarEntradaArquivo(v);
            return true;

        } else {
            System.out.println("Estacionamento cheio! " + v.getPlaca() + " não pôde entrar.");
            return false;
        }
    }

    public double sair(String placa, LocalDateTime horaSaida) {
        for (Veiculo v : new ArrayList<>(vagas)) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                Duration duracao = Duration.between(v.getHoraEntrada(), horaSaida);
                long minutos = duracao.toMinutes();
                if (minutos < 0) minutos = 0;
                long horas = (minutos + 59) / 60;
                if (horas == 0) horas = 1;

                double valor = horas * v.getTarifaPorHora();

                vagas.remove(v);
                historico.add(new RegistroDTO(v.getPlaca(), v.getTipo(), valor));
                totalArrecadado += valor;

                System.out.printf("%s %s saiu. Tempo: %d horas. Pagou R$ %.2f%n",
                        v.getTipo(), v.getPlaca(), horas, valor);
                return valor;
            }
        }
        System.out.println("Veículo não encontrado: " + placa);
        return 0.0;
    }

    public int getVagasLivres() { return capacidade - vagas.size(); }
    public int getVagasOcupadas() { return vagas.size(); }
    public double getTotalArrecadado() { return totalArrecadado; }
    public int getTotalVeiculosPassaram() { return historico.size(); }

    public void exibirRelatorio() {
        System.out.println("----- RELATÓRIO FINAL -----");
        System.out.println("Total de veículos que passaram: " + getTotalVeiculosPassaram());
        System.out.println("Vagas ocupadas: " + getVagasOcupadas());
        System.out.println("Vagas livres: " + getVagasLivres());
        System.out.printf("Valor total arrecadado: R$ %.2f%n", totalArrecadado);
        System.out.println("---------------------------");
    }

    private void salvarEntradaArquivo (Veiculo v) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ArquivoEntrada.txt", true))){
            DateTimeFormatter mascara1 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String linha = v.getTipo() + ";" + v.getPlaca() + ";" + v.getHoraEntrada().format(mascara1);
            writer.write(linha);
            writer.newLine();
        } catch (IOException e){
            System.out.println("Erro ao salvar arquivo ArquivoEntrada.txt" + e.getMessage());
        }
    }



}
