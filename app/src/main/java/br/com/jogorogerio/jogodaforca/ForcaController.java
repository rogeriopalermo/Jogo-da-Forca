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
                return;
            } else {
                quantidadeDeErros++;
            }
        }
    }

    public String getPalavraAteAgora() {
        String visualizacao = "";
        for (char c : palavraParaAdivinhar.toCharArray()) {
            if (letrasUsadas.contains(c)) {
                visualizacao += c;
            } else
            {
                visualizacao += " ";
            }
        }
        return visualizacao;
    }

    public boolean isMorreu() {
        return getQuantidadeDeErros() == 5;
    }

    public boolean isGanhou() {
        return !getPalavraAteAgora().contains(" ");
    }

    public boolean isTerminou() {
        return isMorreu() || isGanhou();
    }
}
