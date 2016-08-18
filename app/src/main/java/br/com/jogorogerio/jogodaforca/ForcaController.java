package br.com.jogorogerio.jogodaforca;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rogerio on 15/08/2016.
 */
public class ForcaController {

    private String palavraParaAdivinhar;
    private Set<Character> letrasUsadas;
    private int quantidadeDeErros = -1;
    private int quantidadeDeAcertos = 0;

    public ForcaController(String palavra) {
        this.palavraParaAdivinhar = palavra;
        letrasUsadas = new HashSet<Character>();
    }

    public int getQuantidadeDeErros() {
        return quantidadeDeErros;
    }

    public void joga(Character letra) {
        if(letrasUsadas.contains(letra)) {
            return;
        } else {
            letrasUsadas.add(letra);
            if(palavraParaAdivinhar.contains(letra.toString())) {
                getPalavraAteAgora();
                return;
            } else {
                quantidadeDeErros++;
            }
        }
    }

    public String getPalavraAteAgora() {
        String visualizacao = "";
        quantidadeDeAcertos = 0;
        for (char c : palavraParaAdivinhar.toCharArray()) {
            if (letrasUsadas.contains(c)) {
                visualizacao += c;
                quantidadeDeAcertos++;
            }
            else if(Character.isWhitespace(c)) {
                quantidadeDeAcertos++;
                visualizacao += " ";
            }
            else
            {
                visualizacao += "_";
            }
        }
        return visualizacao;
    }

    public boolean isMorreu() {
        return getQuantidadeDeErros() == 5;
    }

    public String getPalavraParaAdivinhar() {
        return palavraParaAdivinhar;
    }

    public void setPalavraParaAdivinhar(String palavraParaAdivinhar) {
        this.palavraParaAdivinhar = palavraParaAdivinhar;
    }

    public boolean isGanhou() {
        System.out.println(quantidadeDeAcertos);
        System.out.println("palavra length: " + palavraParaAdivinhar.length());
        return quantidadeDeAcertos == palavraParaAdivinhar.length();
    }

    public boolean isTerminou() {
        return isMorreu() || isGanhou();
    }
}
