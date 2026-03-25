package br.com.arvore.view.render;

import br.com.arvore.model.INoArvore;

public class CalculadoraLayout {

    public int calcularProfundidade(INoArvore no) {
        if (no == null) {
            return 0;
        }
        return 1 + Math.max(
                calcularProfundidade(no.getEsquerda()),
                calcularProfundidade(no.getDireita())
        );
    }

    public int calcularLarguraNecessaria(int profundidade) {
        return (int) Math.pow(2, profundidade) * 80 + 150;
    }
}