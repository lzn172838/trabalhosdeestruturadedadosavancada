package trabalhosdeestruturadedadosavancada;

import java.util.ArrayList;
import java.util.List;

public class ArvoreRedBlack extends ArvoreBase {
    private static final boolean VERMELHO = true;
    private static final boolean PRETO = false;

    private final List<Integer> historicoInsercao = new ArrayList<>();
    private final List<String> historicoBalanceamento = new ArrayList<>();

    private static class NoRB extends No {
        boolean vermelho;

        NoRB(int valor) {
            super(valor);
            this.vermelho = VERMELHO;
        }
    }

    @Override
    public String getNomeTipo() {
        return "Arvore AVL Red-Black";
    }

    @Override
    public boolean suportaOperacoesRedBlack() {
        return true;
    }

    @Override
    public void inserir(int valor) {
        boolean novoValor = nivelNo(valor) == -1;
        raiz = inserirBalanceando((NoRB) raiz, valor);
        ((NoRB) raiz).vermelho = PRETO;
        if (novoValor) {
            historicoInsercao.add(valor);
        }
    }

    @Override
    public void limpar() {
        super.limpar();
        historicoInsercao.clear();
        historicoBalanceamento.clear();
    }

    @Override
    public String corNo(No no) {
        if (no instanceof NoRB && ((NoRB) no).vermelho) {
            return "VERMELHO";
        }
        return "PRETO";
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

    @Override
    public String historicoBalanceamentoRedBlack() {
        if (historicoBalanceamento.isEmpty()) {
            return "Nenhum ajuste realizado";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < historicoBalanceamento.size(); i++) {
            sb.append(i + 1).append(". ").append(historicoBalanceamento.get(i));
            if (i < historicoBalanceamento.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean estaValidaRedBlack() {
        if (raiz == null) {
            return true;
        }
        return !ehVermelho(raiz)
            && !temDoisVermelhosSeguidos(raiz)
            && alturaNegra(raiz) != -1;
    }

    @Override
    public int alturaNegraRedBlack() {
        return Math.max(0, alturaNegra(raiz));
    }

    private NoRB inserirBalanceando(NoRB noAtual, int valor) {
        if (noAtual == null) {
            return new NoRB(valor);
        }

        if (valor < noAtual.valor) {
            noAtual.esquerda = inserirBalanceando((NoRB) noAtual.esquerda, valor);
        } else if (valor > noAtual.valor) {
            noAtual.direita = inserirBalanceando((NoRB) noAtual.direita, valor);
        } else {
            return noAtual;
        }

        if (ehVermelho(noAtual.direita) && !ehVermelho(noAtual.esquerda)) {
            noAtual = rotacaoEsquerda(noAtual);
        }
        if (ehVermelho(noAtual.esquerda) && ehVermelho(noAtual.esquerda.esquerda)) {
            noAtual = rotacaoDireita(noAtual);
        }
        if (ehVermelho(noAtual.esquerda) && ehVermelho(noAtual.direita)) {
            inverterCores(noAtual);
        }

        atualizarAltura(noAtual);
        return noAtual;
    }

    private NoRB rotacaoEsquerda(NoRB h) {
        NoRB x = (NoRB) h.direita;
        h.direita = x.esquerda;
        x.esquerda = h;
        x.vermelho = h.vermelho;
        h.vermelho = VERMELHO;
        atualizarAltura(h);
        atualizarAltura(x);
        historicoBalanceamento.add("Rotacao a esquerda | Pivo: " + h.valor);
        return x;
    }

    private NoRB rotacaoDireita(NoRB h) {
        NoRB x = (NoRB) h.esquerda;
        h.esquerda = x.direita;
        x.direita = h;
        x.vermelho = h.vermelho;
        h.vermelho = VERMELHO;
        atualizarAltura(h);
        atualizarAltura(x);
        historicoBalanceamento.add("Rotacao a direita | Pivo: " + h.valor);
        return x;
    }

    private void inverterCores(NoRB h) {
        h.vermelho = !h.vermelho;
        ((NoRB) h.esquerda).vermelho = !((NoRB) h.esquerda).vermelho;
        ((NoRB) h.direita).vermelho = !((NoRB) h.direita).vermelho;
        historicoBalanceamento.add("Inversao de cores | Pivo: " + h.valor);
    }

    private boolean ehVermelho(No no) {
        return no instanceof NoRB && ((NoRB) no).vermelho;
    }

    private void atualizarAltura(No no) {
        if (no != null) {
            no.altura = 1 + Math.max(alturaInterna(no.esquerda), alturaInterna(no.direita));
        }
    }

    private int alturaInterna(No no) {
        return no == null ? -1 : no.altura;
    }

    private boolean temDoisVermelhosSeguidos(No no) {
        if (no == null) {
            return false;
        }
        if (ehVermelho(no) && (ehVermelho(no.esquerda) || ehVermelho(no.direita))) {
            return true;
        }
        return temDoisVermelhosSeguidos(no.esquerda)
            || temDoisVermelhosSeguidos(no.direita);
    }

    private int alturaNegra(No no) {
        if (no == null) {
            return 1;
        }

        int esquerda = alturaNegra(no.esquerda);
        int direita = alturaNegra(no.direita);
        if (esquerda == -1 || direita == -1 || esquerda != direita) {
            return -1;
        }

        return esquerda + (ehVermelho(no) ? 0 : 1);
    }
}
