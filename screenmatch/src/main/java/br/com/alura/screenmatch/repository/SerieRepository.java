package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
   Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvalicaoGreaterThanEqual(String nomeAtorAtriz, Double avalicao);

    List<Serie> findTop5ByOrderByAvalicaoDesc();

    List<Serie> findByGenero(Categoria categoria);

    List<Serie> findByTotalTemporadasLessThanEqualAndAvalicaoGreaterThanEqual(int totalTemporadas, double avalicao);

    @Query("select s from Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.avalicao >= :avalicao")
    List <Serie> seriesPorTemporadaEAvalicao(int totalTemporadas, double avalicao);

    @Query("SELECT e FROM Serie s \n" +
            "    JOIN s.episodios e \n" +
            "    WHERE LOWER(e.titulo) LIKE LOWER(CONCAT('%', :trechoEpisodio, '%'))")
    List<Episodio> episodiosPorTrecho(String trechoEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliaca DESC LIMIT 5")
    List<Episodio> topepisodiosPorSerie(Serie serie);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie AND YEAR (e.dataLancamento) = :anoLancamento")
    List<Episodio> episodiosPorSerieEAno(Serie serie, int anoLancamento);
}
