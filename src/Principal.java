/*Na classe nasce a essência, o molde e o propósito.
No main, ela recebe ordens e descobre seus dons.*/
import br.com.alura.screnmatch.calculo.CalculadoraDeTempo;
import br.com.alura.screnmatch.calculo.FiltroRecomendacao;
import br.com.alura.screnmatch.modelo.Filme;
import br.com.alura.screnmatch.modelo.Serie;

public class Principal {
    public static void main(String[] args) {
        /*Tipo referencia - busca na memoria um espaço reservado
        para classe desejada nesse caso a filme*/

        Filme favorito = new Filme(); /*essa função busca dentro da classe
         Filme seus atributos dando vida aos seus atributos*/
        favorito.setNome("The Matrix");
        favorito.setAnoDeLancamento(1999);
        favorito.setDuracaoEmMinutos(135);

        /*Aqui lemos o que criamos dentro da classe filme e lemos os atributos da classe main*/
        System.out.println(favorito.getNome());
        System.out.println(favorito.getAnoDeLancamento());

        /** O dialeto sendo colocado em pratica para receber as ordens (Dialeto do metodo void) */
        favorito.exibeFichaTecnica();
        favorito.avalia(10);
        favorito.avalia(8);
        favorito.avalia(7);
        favorito.avalia(9);
        favorito.pegaMedia();

        /**Maneira correta de chamar uma função(Metodo) privada da classe filme */
        System.out.println("Total de avaliações " +favorito.getTotalDeAvaliacoes());
        System.out.println("Média de avaliações do filme: " +favorito.pegaMedia());

        /**Maneira errada de chamar uma função privada da classe filme*/
        //favorito.somaDasAvaliacoes = 10;
        //favorito.TotalDeAvaliacoes = 5;



        /** Serie criado com os atributos da classe serie com alguns metodos(Funções)*/
        Serie lost = new Serie();
        lost.setNome("Lost");
        lost.setAnoDeLancamento(2006);
        lost.setTemporadas(10);
        lost.setEpisodiosPorTemporada(24);
        lost.setMinutosPorEpisodio(7);
        System.out.println("Duração para Maratonar Lost " + lost.getDuracaoEmMinutos());
        lost.exibeFichaTecnica();

        /** Novo filme criado com os atributos da classe filme */
        Filme outroFavorito = new Filme(); /*essa função busca dentro da classe Filme seus atributos dando vida aos seus atributos*/
        outroFavorito.setNome("Avatar");
        outroFavorito.setAnoDeLancamento(2023);
        outroFavorito.setDuracaoEmMinutos(200);


        /** Metodo(função) para calcular o tempo incluido a classe Titulo
         * e as subclasses serie e filme que estão incluida em titulo */
        CalculadoraDeTempo calculadora = new CalculadoraDeTempo();
        calculadora.inclui(favorito);
        calculadora.inclui(outroFavorito);
        calculadora.inclui(lost);
        System.out.println(calculadora.getTempoTotal());


        FiltroRecomendacao filtro = new FiltroRecomendacao();
        filtro.filtra(favorito);
    }
}