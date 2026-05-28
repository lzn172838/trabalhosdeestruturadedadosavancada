package trabalhosdeestruturadedadosavancada;

import java.util.ArrayList;
import java.util.List;

public class ArvoreBinBusca extends ArvoreBase {
    private final List<Integer> historicoInsercao = new ArrayList<>();

    @Override
    public void inserir(int valor) {
        boolean novoValor = nivelNo(valor) == -1;
        raiz = inserirRec(raiz, valor);
        if (novoValor) {
            historicoInsercao.add(valor);
        }
    }

    @Override
    public void limpar() {
        super.limpar();
        historicoInsercao.clear();
    }

    @Override
    public String ordemInsercaoResumo() {
        if (historicoInsercao.isEmpty()) {
            return "(sem insercoes)";
        }

        StringBuilder sb = new StringBuilder();
        for (int valor : historicoInsercao) {
            sb.append(valor).append(" ");
        }
        return limparEspacoFinal(sb);
    }

    private No inserirRec(No noAtual, int valor) {
        if (noAtual == null) {
            return new No(valor);
        }

        if (valor < noAtual.valor) {
            noAtual.esquerda = inserirRec(noAtual.esquerda, valor);
        } else if (valor > noAtual.valor) {
            noAtual.direita = inserirRec(noAtual.direita, valor);
        }

        return noAtual;
    }
}
