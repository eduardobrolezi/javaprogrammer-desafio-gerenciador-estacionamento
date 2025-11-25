📄 README – Sistema de Estacionamento Inteligente (Projeto em Java)

Autor: Eduardo
Pacote-base: br.com.eduardo.estacionamento

📌 1. Sobre o Projeto

Este projeto simula o funcionamento de um estacionamento inteligente, utilizando Java 8, Programação Orientada a Objetos, leitura de arquivos, persistência em banco MySQL e simulação de fluxo de veículos.

O objetivo é demonstrar o uso de:

Classes, objetos e encapsulamento

Herança e polimorfismo (Veículo → Carro/Moto/Caminhão)

Controle de estados (vagas ocupadas, vagas livres)

Manipulação de datas com LocalDateTime

Cálculo de permanência com Duration

Thread para leitura de arquivo

Acesso a banco de dados com JDBC

Simulação de entradas e saídas utilizando aleatoriedade

📌 2. Estrutura do Projeto (Pacotes)
br.com.eduardo.estacionamento
 ├── dto
 ├── model
 │    ├── Veiculo
 │    ├── Carro
 │    ├── Moto
 │    ├── Caminhao
 ├── service
 │    ├── EstacionamentoService
 │    ├── LerArquivoEntrada
 ├── main
      └── SimuladorMain

✔ dto

Armazena objetos simples de transferência de dados.

✔ model

Contém as classes que representam os veículos.

✔ service

Regras de negócio (controle de vagas, leitura de arquivo, banco de dados).

✔ main

Classe principal que roda o sistema.

📌 3. Como o Sistema Funciona
✔ 3.1 Leitura do Arquivo de Entrada

O arquivo ArquivoEntrada.txt possui linhas no formato:

TIPO;PLACA;HORA


Exemplo:

CARRO;ABC1234;2024-10-15 08:30
MOTO;XYZ9988;2024-10-15 09:10


A classe LerArquivoEntrada, que implementa Runnable, é executada dentro de uma thread e faz:

Lê cada linha

Separa os dados usando split(";")

Insere no banco de dados via JDBC

Exibe no console

✔ 3.2 Entrada dos Veículos

A hora de entrada do veículo é definida a partir do arquivo TXT, não do relógio do sistema.

Portanto:
➡ A ENTRADA é lida do arquivo, não gerada automaticamente.

✔ 3.3 Saída dos Veículos

A hora de saída é calculada com LocalDateTime.now(), ou seja:

➡ A SAÍDA usa o horário atual do sistema.

O tempo de permanência é calculado usando:

Duration.between(entrada, saida)


Esse intervalo é arredondado para a próxima hora cheia.

✔ 3.4 Cálculo do Valor

Tabela usada:

Tipo	Valor por Hora
Carro	R$ 5,00
Moto	R$ 3,00
Caminhão	R$ 10,00

Fórmula:

horasCobradas = (tempoPermanencia + (1h - 1 segundo)) arredondado pra cima
valor = horasCobradas * preçoPorHora

✔ 3.5 Simulador Diário

A classe SimuladorMain roda o sistema e:

inicia o estacionamento com número X de vagas

dispara a thread que lê o arquivo

simula entradas e saídas aleatórias

exibe relatórios finais

📌 4. Como Executar o Programa
✔ Pré-requisitos

Java 8

MySQL instalado

Tabela criada:

CREATE TABLE controle_estacionamento(
    id INT PRIMARY KEY,
    tipo VARCHAR(50),
    placa VARCHAR(50),
    entrada VARCHAR(50),
    saida VARCHAR(50),
    valor DOUBLE
);

✔ Passo a passo

Abra o projeto na IDE (IntelliJ, Eclipse ou NetBeans).

Ajuste usuário e senha do MySQL em LerArquivoEntrada.

Coloque o arquivo ArquivoEntrada.txt na pasta raiz do projeto.

Execute a classe SimuladorMain.

A thread irá carregar os veículos do arquivo e gravar no banco.

O simulador executará a lógica e exibirá os relatórios.

📌 5. Conceitos Aprendidos no Projeto
✔ Programação Orientada a Objetos

Classes e objetos

Herança

Polimorfismo

Encapsulamento

✔ Manipulação de Datas (Java 8)

LocalDateTime para horários

Duration para calcular diferença

Arredondamento de horas

✔ Threads

Execução paralela usando Runnable

Leitura de arquivo rodando ao mesmo tempo do simulador

✔ Arquivo TXT

Leitura com Files.readAllLines

Processamento linha a linha

Conversão de texto em dados do sistema

✔ JDBC (MySQL)

Conexão ao banco

PreparedStatement

Insert de dados

Fechamento seguro de conexões

✔ Simulação e Aleatoriedade

Geração randômica de entradas e saídas

Lógica de controle de vagas

📌 6. O que Poderia Ser Melhorado (Desafios)

Implementar persistência da saída no banco

Criar interface de menu interativo no console

Implementar fila de espera de veículos

Adicionar clientes VIP ou recorrentes com desconto

Exportar relatório final em .txt

Criar testes unitários

📌 7. Conclusão

Este projeto demonstra um sistema completo que envolve:

Entrada/saída de dados

Processamento de horário

Simulação realística

Banco de dados

Arquivo externo

Conceitos sólidos de Java

É um projeto excelente para demonstrar domínio de Java 8, POO, JDBC e lógica de programação.
