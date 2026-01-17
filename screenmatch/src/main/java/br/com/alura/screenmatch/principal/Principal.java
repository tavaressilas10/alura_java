package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;

import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO ="https://www.omdbapi.com/?t=" ;
    private final String API_KEY ="&apikey=6585022c";
    private ConsumoApi consumo = new ConsumoApi();
    private Scanner Leitura = new Scanner(System.in);

    private List<DadosSerie> dadosSeries = new ArrayList<>();


    private SerieRepository repositorio;

    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }

    public  void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar Series
                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = Leitura.nextInt();
            Leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }
        private void buscarSerieWeb () {
            DadosSerie dados = getDadosSerie();
            //dadosSeries.add(dados);
            Serie serie = new Serie(dados);
            repositorio.save(serie);
            System.out.println(dados);
        }

        private DadosSerie getDadosSerie() {
            System.out.println("Digite o nome da série para busca");
            var nomeSerie = Leitura.nextLine();
            var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
            DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
            return dados;
        }

        private void buscarEpisodioPorSerie() {
            DadosSerie dadosSerie = getDadosSerie();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + dadosSerie.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

        }
        private void listarSeriesBuscadas() {
        List<Serie> series = repositorio.findAll();
            series.stream()
                            .sorted(Comparator.comparing(Serie::getGenero))
                    .forEach(System.out::println);

        dadosSeries.forEach(System.out::println);
        }
}
//
//        System.out.println("---Digite o nome da serie---");
//        var nomeSerie = Leitura.nextLine();
//        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+")+ API_KEY);
//        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
//        System.out.println(dados);
//
//        List<DadosTemporada> temporadas = new ArrayList<>();
//
//        for (int i =1; i<=dados.totalTemporadas(); i++){
//            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+")+ "&season=" + i +API_KEY);
//            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
//            temporadas.add(dadosTemporada);
//        }
//        temporadas.forEach(System.out::println);
//
//        for(int i = 0; i < dados. totalTemporadas(); i++) {
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j).titulo());
//
//            }
//        }
//            temporadas. forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
//
//        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
//                .flatMap(t -> t.episodios().stream())
//                .collect(Collectors.toList());
//
//        List<Episodio> episodios = temporadas.stream()
//                .flatMap(t -> t.episodios().stream()
//                        .map(d -> new Episodio(t.numero(), d))
//                ).collect(Collectors.toList());
//
//        episodios.forEach(System.out::println);

//        System.out.println("Digite um trecho do titulo do episodio");
//        var trechoTitulo = Leitura.nextLine();
//           Optional<Episodio> episodioBuscado = episodios.stream()
//                    .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                    .findFirst();
//            if(episodioBuscado.isPresent()){
//                System.out.println("Ep encontrado com sucesso");
//                System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
//            }else {
//                System.out.println("Nenhum episodio encontrado");
//            }
//


//        System.out.println("A partir de que ano você deseja ver os episodios");
//        var ano =  Leitura.nextInt();
//        Leitura.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano,1,1);
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                " Episodio: " + e.getTitulo() +
//                                " Data de Lancamento: " + e.getDataLancamento().format(formatador)
//                ));
//
//        Map <Integer, Double> avaliacaoPorTemporada = episodios.stream()
//                .filter(e -> e.getAvaliaca() > 0.0)
//                .collect(Collectors.groupingBy(Episodio::getTemporada,
//                        Collectors.averagingDouble(Episodio::getAvaliaca)));
//        System.out.println(avaliacaoPorTemporada);
//
//        /**Media(estatiscticas) de algumas avaliações dos eps da temporada*/
//        DoubleSummaryStatistics est = episodios.stream()
//                .filter(e -> e.getAvaliaca() > 0.0)
//                .collect(Collectors.summarizingDouble(Episodio::getAvaliaca));
//        System.out.println("Media " + est.getAverage());
//        System.out.println("Melhor ep " + est.getMax());
//        System.out.println("Pior ep " + est.getMin());
//        System.out.println("Quantidade de Eps  " + est.getCount());
// }}