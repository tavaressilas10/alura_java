package br.com.alura.screnmatch.modelo; /**Criamos um projeto novo(pasta com o nome do projeto) com a Classe br.com.alura.screnmatch.modelo.Filme*/
/**Dentro dessa classe criamos um objeto com seus atributos*/
import br.com.alura.screnmatch.calculo.Classificavel;

/**Na classe nasce a essência, o molde e o propósito.
No main, ela recebe ordens e descobre seus dons.*/

public class Filme extends Titulo implements Classificavel {
   private String Diretor;

    public String getDiretor() {
        return Diretor;
    }
    public void setDiretor(String diretor) {}

    @Override
    public int getClassificacao() {
        return (int) pegaMedia()/2;
    }
}

