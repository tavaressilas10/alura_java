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
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private ConsumoApi consumo = new ConsumoApi();
    private Scanner Leitura = new Scanner(System.in);

    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private SerieRepository repositorio;

    private List<Serie> series = new ArrayList<>();

    private Optional<Serie> serieBusca;

    public Principal(SerieRepository repositorio)
    {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar Series
                    4 - Buscar série por titulo
                    5 - Buscar serie por ator ou atriz
                    6 - Buscar Top 5 Series
                    7 - Buscar serie por categoria 
                    8 - Filtrar Series
                    9 - Buscar ep por trecho
                    10 - Top episódios por série
                    11 - Buscar ep por uma data
                    
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
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtorAtriz();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarSeriePorCategoria();
                    break;
                case 8:
                    filtrarSeriesPorTemporadaEAvaliacao();
                    break;
                case 9:
                    buscarEpisodioPorTrecho();
                    break; 
                case 10:
                    topepisodiosPorSerie();
                    break;
                case 11:
                    buscarEpisodiosDepoisDeUmaData();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
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
        listarSeriesBuscadas();
        System.out.println("Escolha um episodio para buscar");
        var nomeSerie = Leitura.nextLine();
        Optional<Serie> serie = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if (serie.isPresent()) {
            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());
            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        } else {
            System.out.println("Serie n encontrado");
        }
    }

    private void listarSeriesBuscadas() {
        series = repositorio.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);

        dadosSeries.forEach(System.out::println);
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Escolha uma série pelo nome: ");
        var nomeSerie = Leitura.nextLine();
        serieBusca = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBusca.isPresent()) {
            System.out.println("Dados da série: " + serieBusca.get());
        }else  {
            System.out.println("Serie não encontrado");
        }
    }
    private void buscarSeriePorAtorAtriz() {
        System.out.println("Escolha um serie pelo nome do ator ou atriz: ");
        var nomeAtorAtriz = Leitura.nextLine();
        System.out.println("Avaliação a partir de qual valor");
        var avalicao = Leitura.nextDouble();
        List<Serie> seriesEncontradas = repositorio.findByAtoresContainingIgnoreCaseAndAvalicaoGreaterThanEqual(nomeAtorAtriz, avalicao);
        System.out.println("Series em que " + nomeAtorAtriz + " Trabalhou: ");
         seriesEncontradas.forEach(s ->
                 System.out.println(s.getTitulo() + " avaliação: " + s.getAvalicao()));
    }
    private void buscarTop5Series() {
        List<Serie> serieTop = repositorio.findTop5ByOrderByAvalicaoDesc();
        serieTop.forEach(s ->
                System.out.println(s.getTitulo() + " avaliação: " + s.getAvalicao()));;
    }
    private void buscarSeriePorCategoria() {
        System.out.println("Deseja buscar serie por categoria/genero?: ");
        var nomeGenero = Leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Series por categoria " + nomeGenero);
        seriesPorCategoria.forEach(System.out::println);
    }

    private void filtrarSeriesPorTemporadaEAvaliacao() {
        System.out.println("Filtrar séries até quantas temporadas? ");
        var totalTemporadas = Leitura.nextInt();
        Leitura.nextLine();
        System.out.println("Com avaliação a partir de que valor? ");
        var avalicao = Leitura.nextDouble();
        Leitura.nextLine();
        List<Serie> filtroSeries = repositorio.seriesPorTemporadaEAvalicao(totalTemporadas, avalicao);
        System.out.println(" *** Séries filtradas *** ");
        filtroSeries.forEach(s -> System.out.println(s.getTitulo() + " avaliacão: " + s.getAvalicao()));
    }

    private void buscarEpisodioPorTrecho() {
        System.out.println("Qual o trecho do nome do ep: ");
        var trechoEpisodio = Leitura.nextLine();
        List <Episodio> episodiosEncontrados = repositorio.episodiosPorTrecho(trechoEpisodio);
        episodiosEncontrados.forEach(e -> System.out.printf("Serie: %s Temporada %s - Episódio %s - %s \n",
                e.getSerie().getTitulo(), e.getTemporada(),
                e.getNumeroEpisodio(), e.getTitulo()));
    }

    private void topepisodiosPorSerie() {
        buscarSeriePorTitulo();
        if (serieBusca.isPresent()){
            Serie serie = serieBusca.get();
            List<Episodio> topEpisodios = repositorio.topepisodiosPorSerie(serie);
            topEpisodios.forEach(e ->
                    System.out.printf("Serie: %s Temporada %s - Episódio %s \n",
                    e.getSerie().getTitulo(), e.getTemporada(),
                    e.getNumeroEpisodio(), e.getTitulo()));
        }
    }

    private void buscarEpisodiosDepoisDeUmaData() {
        buscarSeriePorTitulo();
        if(serieBusca.isPresent()){
        Serie serie = serieBusca.get();
        System.out.println("Digite o ano para buscar os episódios lançados depois dessa data: ");
        var anoLancamento = Leitura.nextInt();
        Leitura.nextLine();
        List<Episodio> episodiosAno =repositorio.episodiosPorSerieEAno(serie, anoLancamento);
        episodiosAno.forEach(System.out::println);
        }
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

