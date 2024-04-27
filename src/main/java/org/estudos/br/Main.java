package org.estudos.br;

import java.io.IOException;
import java.util.Scanner;

/**
 * Classe principal para interação com o usuário e consulta de informações de estados brasileiros e distritos.
 */
public class Main {

    /**
     * Método principal para execução do programa.
     *
     * @param args Argumentos da linha de comando (não utilizados neste programa).
     */
    public static void main(String[] args) {
        // Cria um objeto Scanner para ler entrada do usuário a partir do console
        Scanner scanner = new Scanner(System.in);

        // Variável para controlar o loop do menu
        boolean continuar = true;

        // Loop do menu
        while (continuar) {
            // Imprime um menu para o usuário escolher qual API consultar
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Consultar informações de todos distritos");
            System.out.println("2. Consultar informações de um estado por id");
            System.out.println("3. Sair");

            System.out.print("Opção: ");

            // Lê a opção do usuário
            int opcao = scanner.nextInt();

            try {
                // Verifica a opção do usuário e executa a ação correspondente
                switch (opcao) {
                    case 1:
                        consultarTodosDistritos();
                        break;
                    case 2:
                        consultarEstadoId(scanner);
                        break;
                    case 3:
                        continuar = false;
                        System.out.println("\n Obrigado por usar nossos serviços!");// Sair do loop do menu
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (IOException e) {
                // Trata exceção caso ocorra um erro durante a consulta
                System.err.println("Ocorreu um erro ao realizar a consulta: " + e.getMessage());
            }
        }

        // Fecha o objeto Scanner para liberar recursos
        scanner.close();
    }



    /**
     * Método para consultar informações de todos distritos.
     *
     *
     * @throws IOException Se houver algum erro de conexão ou leitura.
     */
    private static void consultarTodosDistritos() throws IOException {
        // Imprime uma mensagem de instrução para o usuário
        System.out.println("Digite o identificador do distrito a ser consultado: ");

        // Chama o método consultarTodosDistritos da classe ConsultaIBGE para obter informações dos distritos
        String resultado = ConsultaIBGE.consultarTodosDistritos();

        // Imprime o resultado da consulta
        System.out.println("Resultado da consulta: ");
        System.out.println(resultado);
    }

    /**
     * Método para consultar informações de todos os estados.
     *
     *
     * @throws IOException Se houver algum erro de conexão ou leitura.
     */
    private static void consultarEstadoId(Scanner scanner) throws IOException {

        System.out.println("Digite o id do estado a ser consultado: ");


        int id = scanner.nextInt();

        // Chama o método consultarEstadosId da classe ConsultaIBGE para obter informações dos Estados
        String resultado = ConsultaIBGE.consultarEstadoId(id);

        // Imprime o resultado da consulta
        System.out.println("Resultado da consulta: ");
        System.out.println(resultado);
    }

    private static void consultaTodasRegioes() throws IOException {

        System.out.println(" regiões a ser consultadas: ");


        // Chama o método consultaTodasRegioes classe ConsultaIBGE para obter informações dos Estados
        String resultado = ConsultaIBGE.consultaTodasRegioes();

        // Imprime o resultado da consulta
        System.out.println("Resultado da consulta: ");
        System.out.println(resultado);
    }



}

