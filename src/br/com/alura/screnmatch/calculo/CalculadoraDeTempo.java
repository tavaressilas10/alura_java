package br.com.alura.screnmatch.calculo;
import br.com.alura.screnmatch.modelo.Filme;
import br.com.alura.screnmatch.modelo.Serie;
import br.com.alura.screnmatch.modelo.Titulo;

import javax.xml.transform.Source;
import java.sql.SQLOutput;

public class CalculadoraDeTempo {
    private int tempoTotal = 0;

    public int getTempoTotal(){
        return tempoTotal;
    }

//    public void inclui(Filme f){
//            this.tempoTotal += f.getDuracaoEmMinutos();
//    }
//
//    public void inclui(Serie s){
//        this.tempoTotal += s.getDuracaoEmMinutos();
//    }

    public void inclui(Titulo titulo){
        System.out.println("Adicionando duração em minutos de " +titulo);
        this.tempoTotal += titulo.getDuracaoEmMinutos();
    }

}
