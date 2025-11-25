package br.com.eduardo.estacionamento.main;

import br.com.eduardo.estacionamento.model.*;
import br.com.eduardo.estacionamento.service.EstacionamentoService;
import br.com.eduardo.estacionamento.service.LerArquivoEntrada;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

public class Simulador {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Informe a capacidade do estacionamento: ");
        int capacidade = sc.nextInt();

        EstacionamentoService estacionamento = new EstacionamentoService(capacidade);
        Random random = new Random();

        for (int i = 0; i < capacidade; i++) {
            int tipo = random.nextInt(3);
            String placa = gerarPlacaAleatoria(random);
            LocalDateTime horaEntrada = LocalDateTime.now().minusMinutes(random.nextInt(180));

            Veiculo v;

            if (tipo == 0) {
                v = new Carro(placa, horaEntrada);
            } else if (tipo == 1) {
                v = new Moto(placa, horaEntrada);
            } else {
                v = new Caminhao(placa, horaEntrada);
            }

            estacionamento.entrar(v);


            if (random.nextDouble() < 0.5 && estacionamento.getVagasOcupadas() > 0) {
                estacionamento.sair(placa, LocalDateTime.now());
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {

            }
        }
        try {
            Thread.sleep(1000); // pequena pausa antes de iniciar a thread
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        LerArquivoEntrada lerArquivoEntrada = new LerArquivoEntrada();
        Thread test = new Thread(lerArquivoEntrada);
        test.start();

        System.out.println("Simulador iniciado com sucesso!");

        try {
            test.join(); // aguarda o término da thread
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        estacionamento.exibirRelatorio();

        sc.close();

    }

    private static String gerarPlacaAleatoria(Random rand) {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append(letras.charAt(rand.nextInt(letras.length())));
        }
        sb.append('-');
        sb.append(1000 + rand.nextInt(9000));
        return sb.toString();
    }


}
