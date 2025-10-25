import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /* Criação do menu de inicialização */
        String nome = "Silas";
        String tipodeConta = "Corrente";
        double saldo = 1599.00;

        Scanner leitura = new Scanner(System.in);
        System.out.print("**************************");
        System.out.print("\nNome do Cliente: " + nome);
        System.out.print("\nTipo de conta: " + tipodeConta);
        System.out.print("\nSaldo da conta: " + saldo);
        System.out.print("\n**************************");

        /* Criação do loop com while */
        /* Criando Condicionais if ou else */
        String menu = """
                Digite sua opção
                1 - Consultar saldo
                2 - Transferir valor
                3 - Receber valor
                4 - Sair
                """;
        int opcao = 0;
        while(opcao != 4) {
            System.out.println(menu);
            opcao = leitura.nextInt();

            if (opcao == 1) {
                System.out.print("Seu saldo é: " + saldo);
            } else if (opcao == 2) {
                System.out.println("Qual valor deseja tranferir");
                double valor = leitura.nextDouble();
                if (valor > saldo) {
                    System.out.println("Saldo insuficiente");
                } else {
                    saldo -= valor;
                    System.out.println("Transferencia realizado com sucesso, saldo atual " + saldo);
                }
            } else if (opcao == 3) {
                System.out.println("Valor recebido: ");
                double valor = leitura.nextDouble();
                saldo += valor;
                System.out.println("Saldo Atualizado: " + saldo);
            } else if (opcao != 4) {
                System.out.println("Opçãp invalida");
            }
        }
    }
}