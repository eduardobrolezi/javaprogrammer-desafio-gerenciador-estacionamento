package br.com.eduardo.estacionamento.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Iterator;
import java.util.List;

public class LerArquivoEntrada implements Runnable {

    private final static String url = "jdbc:mysql://localhost:3306/impacta_estacionamento";
    private final static String username = "root";
    private final static String password = "order66";

    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    private PreparedStatement ps = null;

    @Override
    public void run() {
        try {
            Path ler = Paths.get("ArquivoEntrada.txt");

            if (Files.exists(ler) && !Files.isDirectory(ler)) {

                List<String> linhas = Files.readAllLines(ler);
                Iterator it = linhas.iterator();
                int i = 0;

                while (it.hasNext()) {
                    i = i + 1;

                    String linha = (String) it.next();
                    String[] dadosLinha = linha.split(";");

                    String tipoVeiculo = dadosLinha[0];
                    String placaVeiculo = dadosLinha[1];
                    String dataHoraEntrada = dadosLinha[2];

                    this.openDB();
                    this.inserir(i, tipoVeiculo, placaVeiculo, dataHoraEntrada);
                    this.closeDB();

                    System.out.println(tipoVeiculo);
                    System.out.println(placaVeiculo);
                    System.out.println(dataHoraEntrada);
                    System.out.println("----------------------");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void openDB() {
        try {
            con = DriverManager.getConnection(url, username, password);
            System.out.println("\nConectado com sucesso!");

        } catch (SQLException e) {
            System.out.println("\nNão foi possivel estabelecer a conexão " + e + "\n");
            System.exit(1);
        }
    }


    private void closeDB() {
        try {
            if (stmt != null) stmt.close();
            if (ps != null) ps.close();
            if (con != null) con.close();

        } catch (SQLException e) {
            System.out.println("\nNão foi possível fechar a conexão " + e + "\n");
        }
    }


    private void inserir(int id, String tipoVeiculo, String placa, String entrada) {

        String insertQuery = "INSERT INTO controle_estacionamento VALUES (?, ?, ?, ?, ?, ?);";

        try {
            ps = con.prepareStatement(insertQuery);

            ps.setInt(1, id);
            ps.setString(2, tipoVeiculo);
            ps.setString(3, placa);
            ps.setString(4, entrada);
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.DOUBLE);

            ps.executeUpdate();

            System.out.println("Entrada do veículo cadastrada com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
