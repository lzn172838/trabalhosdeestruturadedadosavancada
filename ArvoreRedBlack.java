package trabalhosdeestruturadedadosavancada;

import java.util.ArrayList;
import java.util.List;

public class ArvoreRedBlack extends ArvoreBase {
    private static final boolean VERMELHO = true;
    private static final boolean PRETO = false;

    private final List<Integer> historicoInsercao = new ArrayList<>();
    private final List<String> historicoBalanceamento = new ArrayList<>();

    private static class NoRB extends No {
        NoRB pai;
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
        if (raiz == null) {
            raiz = new NoRB(valor);
            ((NoRB) raiz).vermelho = PRETO;
            historicoInsercao.add(valor);
            return;
        }

        NoRB atual = (NoRB) raiz;
        NoRB pai = null;

        while (atual != null) {
            pai = atual;
            if (valor < atual.valor) {
                atual = (NoRB) atual.esquerda;
            } else if (valor > atual.valor) {
                atual = (NoRB) atual.direita;
            } else {
                return;
            }
        }

        NoRB novo = new NoRB(valor);
        novo.pai = pai;
        if (valor < pai.valor) {
            pai.esquerda = novo;
        } else {
            pai.direita = novo;
        }

        historicoInsercao.add(valor);
        corrigirInsercao(novo);
    }

    @Override
    public void limpar() {
        super.limpar();
        historicoInsercao.clear();
        historicoBalanceamento.clear();
    }

    @Override
    public String corNo(No no) {
        if (ehVermelho(no)) {
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

    private void corrigirInsercao(NoRB no) {
        while (no != raiz && ehVermelho(no.pai)) {
            NoRB pai = no.pai;
            NoRB avo = pai.pai;

            if (pai == avo.esquerda) {
                NoRB tio = (NoRB) avo.direita;
                if (ehVermelho(tio)) {
                    pai.vermelho = PRETO;
                    tio.vermelho = PRETO;
                    avo.vermelho = VERMELHO;
                    historicoBalanceamento.add("Recoloracao | Avo: " + avo.valor);
                    no = avo;
                } else {
                    if (no == pai.direita) {
                        no = pai;
                        rotacaoEsquerda(no);
                    }
                    no.pai.vermelho = PRETO;
                    no.pai.pai.vermelho = VERMELHO;
                    rotacaoDireita(no.pai.pai);
                }
            } else {
                NoRB tio = (NoRB) avo.esquerda;
                if (ehVermelho(tio)) {
                    pai.vermelho = PRETO;
                    tio.vermelho = PRETO;
                    avo.vermelho = VERMELHO;
                    historicoBalanceamento.add("Recoloracao | Avo: " + avo.valor);
                    no = avo;
                } else {
                    if (no == pai.esquerda) {
                        no = pai;
                        rotacaoDireita(no);
                    }
                    no.pai.vermelho = PRETO;
                    no.pai.pai.vermelho = VERMELHO;
                    rotacaoEsquerda(no.pai.pai);
                }
            }
        }

        ((NoRB) raiz).vermelho = PRETO;
        recalcularAlturas(raiz);
    }

    private void rotacaoEsquerda(NoRB x) {
        NoRB y = (NoRB) x.direita;
        x.direita = y.esquerda;
        if (y.esquerda != null) {
            ((NoRB) y.esquerda).pai = x;
        }

        y.pai = x.pai;
        if (x.pai == null) {
            raiz = y;
        } else if (x == x.pai.esquerda) {
            x.pai.esquerda = y;
        } else {
            x.pai.direita = y;
        }

        y.esquerda = x;
        x.pai = y;
        historicoBalanceamento.add("Rotacao a esquerda | Pivo: " + x.valor);
    }

    private void rotacaoDireita(NoRB y) {
        NoRB x = (NoRB) y.esquerda;
        y.esquerda = x.direita;
        if (x.direita != null) {
            ((NoRB) x.direita).pai = y;
        }

        x.pai = y.pai;
        if (y.pai == null) {
            raiz = x;
        } else if (y == y.pai.direita) {
            y.pai.direita = x;
        } else {
            y.pai.esquerda = x;
        }

        x.direita = y;
        y.pai = x;
        historicoBalanceamento.add("Rotacao a direita | Pivo: " + y.valor);
    }

    private boolean ehVermelho(No no) {
        return no instanceof NoRB && ((NoRB) no).vermelho;
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

    private int recalcularAlturas(No no) {
        if (no == null) {
            return -1;
        }
        no.altura = 1 + Math.max(recalcularAlturas(no.esquerda), recalcularAlturas(no.direita));
        return no.altura;
    }
}
