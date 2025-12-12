import java.util.Scanner;

public class Principal {
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        ConectaAPI cotacao = new ConectaAPI("7e24344af62afa1b10dff4c9","USD", "BRL");

        OpcaoMenu opcao = null;

        while (opcao != OpcaoMenu.SAIR) {

            System.out.println("===== MENU CONVERSOR DE MOEDAS =====");

            for (OpcaoMenu op : OpcaoMenu.values()) {
                System.out.println(op.getCodigo() + " - " + op.getBase() + " para " + op.getTarget());
            }

            System.out.println("Escolha um opção: ");
            int codigoEscolhido = sc.nextInt();

            opcao = OpcaoMenu.fromCodigo(codigoEscolhido);

            if (opcao == null) {
                System.out.println("Opção inválida! Tente novamente. \n");
                continue;
            }
            if (opcao == OpcaoMenu.SAIR) {
                System.out.println("Encerrando...");
                break;
            }


            System.out.println(">> " + opcao.getBase() + " selecionado");

            cotacao.setBaseCurrency(opcao.getBase());
            cotacao.setTargetCurrency(opcao.getTarget());


            String resposta = cotacao.consultarCotacao();

            System.out.println("Digite o valor que deseja converter: ");
            double valor = sc.nextDouble();

            cotacao.resultJson(resposta, valor);

            System.out.println();

        }
        sc.close();
    }
}