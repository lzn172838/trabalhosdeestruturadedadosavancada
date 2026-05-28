package trabalhosdeestruturadedadosavancada;

import java.util.ArrayList;
import java.util.List;

public class ArvoreAVL extends ArvoreBase {
    private String ultimaRotacao = "Nenhuma";
    private String rotacaoUsadaNoBalanceamento = "Nenhuma rotacao";
    private String pivoBalanceamento = "Nenhum";
    private final List<Integer> historicoInsercao = new ArrayList<>();
    private final List<String> historicoRotacoes = new ArrayList<>();

    @Override
    public String getNomeTipo() {
        return "Arvore AVL";
    }

    @Override
    public boolean suportaOperacoesAVL() {
        return true;
    }

    @Override
    public void inserir(int valor) {
        ultimaRotacao = "Nenhuma";
        rotacaoUsadaNoBalanceamento = "Nenhuma rotacao";
        pivoBalanceamento = "Nenhum";
        boolean novoValor = nivelNo(valor) == -1;
        raiz = inserirBalanceando(raiz, valor);
        if (novoValor) {
            historicoInsercao.add(valor);
        }
    }

    @Override
    public void limpar() {
        super.limpar();
        historicoInsercao.clear();
        historicoRotacoes.clear();
    }

    @Override
    public void rotacaoSimplesEsquerdaRaiz() {
        ultimaRotacao = "Nenhuma";
        rotacaoUsadaNoBalanceamento = "Nenhuma rotacao";
        pivoBalanceamento = "Nenhum";
        if (raiz != null && raiz.direita != null) {
            pivoBalanceamento = String.valueOf(raiz.valor);
            raiz = rotacaoEsquerda(raiz);
            ultimaRotacao = "Rotacao simples a esquerda";
            rotacaoUsadaNoBalanceamento = "Rotacao a esquerda";
            registrarRotacao(ultimaRotacao, pivoBalanceamento);
        }
    }

    @Override
    public void rotacaoSimplesDireitaRaiz() {
        ultimaRotacao = "Nenhuma";
        rotacaoUsadaNoBalanceamento = "Nenhuma rotacao";
        pivoBalanceamento = "Nenhum";
        if (raiz != null && raiz.esquerda != null) {
            pivoBalanceamento = String.valueOf(raiz.valor);
            raiz = rotacaoDireita(raiz);
            ultimaRotacao = "Rotacao simples a direita";
            rotacaoUsadaNoBalanceamento = "Rotacao a direita";
            registrarRotacao(ultimaRotacao, pivoBalanceamento);
        }
    }

    @Override
    public void rotacaoDuplaEsquerdaDireitaRaiz() {
        ultimaRotacao = "Nenhuma";
        rotacaoUsadaNoBalanceamento = "Nenhuma rotacao";
        pivoBalanceamento = "Nenhum";
        if (podeRotacionarDuplaEsquerdaDireitaRaiz()) {
            pivoBalanceamento = String.valueOf(raiz.valor);
            raiz.esquerda = rotacaoEsquerda(raiz.esquerda);
            raiz = rotacaoDireita(raiz);
            ultimaRotacao = "Rotacao dupla esquerda-direita";
            rotacaoUsadaNoBalanceamento = "Rotacao a esquerda e depois a direita";
            registrarRotacao(ultimaRotacao, pivoBalanceamento);
        }
    }

    @Override
    public void rotacaoDuplaDireitaEsquerdaRaiz() {
        ultimaRotacao = "Nenhuma";
        rotacaoUsadaNoBalanceamento = "Nenhuma rotacao";
        pivoBalanceamento = "Nenhum";
        if (podeRotacionarDuplaDireitaEsquerdaRaiz()) {
            pivoBalanceamento = String.valueOf(raiz.valor);
            raiz.direita = rotacaoDireita(raiz.direita);
            raiz = rotacaoEsquerda(raiz);
            ultimaRotacao = "Rotacao dupla direita-esquerda";
            rotacaoUsadaNoBalanceamento = "Rotacao a direita e depois a esquerda";
            registrarRotacao(ultimaRotacao, pivoBalanceamento);
        }
    }

    @Override
    public int fatorBalanceamentoRaiz() {
        return fatorBalanceamento(raiz);
    }

    @Override
    public String ultimaRotacaoAVL() {
        return ultimaRotacao;
    }

    @Override
    public String rotacaoUsadaNoBalanceamentoAVL() {
        return rotacaoUsadaNoBalanceamento;
    }

    @Override
    public String pivoBalanceamentoAVL() {
        return pivoBalanceamento;
    }

    @Override
    public String historicoInsercaoAVL() {
        return ordemInsercaoResumo();
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
    public String historicoRotacoesResumo() {
        if (historicoRotacoes.isEmpty()) {
            return "Nenhuma rotacao realizada";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < historicoRotacoes.size(); i++) {
            sb.append(i + 1).append(". ").append(historicoRotacoes.get(i));
            if (i < historicoRotacoes.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public void balancearAVL() {
        ultimaRotacao = "Nenhuma";
        rotacaoUsadaNoBalanceamento = "Nenhuma rotacao";
        pivoBalanceamento = "Nenhum";
        while (!estaBalanceadaAVL()) {
            raiz = balancearSubarvore(raiz);
        }
    }

    @Override
    public boolean estaBalanceadaAVL() {
        return verificarBalanceamento(raiz);
    }

    @Override
    public boolean podeRotacionarEsquerdaRaiz() {
        return raiz != null
            && fatorBalanceamento(raiz) < -1
            && fatorBalanceamento(raiz.direita) <= 0;
    }

    @Override
    public boolean podeRotacionarDireitaRaiz() {
        return raiz != null
            && fatorBalanceamento(raiz) > 1
            && fatorBalanceamento(raiz.esquerda) >= 0;
    }

    @Override
    public boolean podeRotacionarDuplaEsquerdaDireitaRaiz() {
        return raiz != null
            && fatorBalanceamento(raiz) > 1
            && fatorBalanceamento(raiz.esquerda) < 0;
    }

    @Override
    public boolean podeRotacionarDuplaDireitaEsquerdaRaiz() {
        return raiz != null
            && fatorBalanceamento(raiz) < -1
            && fatorBalanceamento(raiz.direita) > 0;
    }

    private No inserirBalanceando(No noAtual, int valor) {
        if (noAtual == null) {
            return new No(valor);
        }

        if (valor < noAtual.valor) {
            noAtual.esquerda = inserirBalanceando(noAtual.esquerda, valor);
        } else if (valor > noAtual.valor) {
            noAtual.direita = inserirBalanceando(noAtual.direita, valor);
        } else {
            return noAtual;
        }

        atualizarAltura(noAtual);
        return balancearNo(noAtual);
    }

    private No balancearSubarvore(No noAtual) {
        if (noAtual == null) {
            return null;
        }

        noAtual.esquerda = balancearSubarvore(noAtual.esquerda);
        noAtual.direita = balancearSubarvore(noAtual.direita);
        atualizarAltura(noAtual);

        return balancearNo(noAtual);
    }

    private No balancearNo(No noAtual) {
        int fator = fatorBalanceamento(noAtual);

        if (fator > 1) {
            pivoBalanceamento = String.valueOf(noAtual.valor);
            if (fatorBalanceamento(noAtual.esquerda) < 0) {
                ultimaRotacao = "Rotacao dupla esquerda-direita";
                rotacaoUsadaNoBalanceamento = "Rotacao a esquerda e depois a direita";
                registrarRotacao(ultimaRotacao, pivoBalanceamento);
                noAtual.esquerda = rotacaoEsquerda(noAtual.esquerda);
            } else {
                ultimaRotacao = "Rotacao simples a direita";
                rotacaoUsadaNoBalanceamento = "Rotacao a direita";
                registrarRotacao(ultimaRotacao, pivoBalanceamento);
            }
            return rotacaoDireita(noAtual);
        }

        if (fator < -1) {
            pivoBalanceamento = String.valueOf(noAtual.valor);
            if (fatorBalanceamento(noAtual.direita) > 0) {
                ultimaRotacao = "Rotacao dupla direita-esquerda";
                rotacaoUsadaNoBalanceamento = "Rotacao a direita e depois a esquerda";
                registrarRotacao(ultimaRotacao, pivoBalanceamento);
                noAtual.direita = rotacaoDireita(noAtual.direita);
            } else {
                ultimaRotacao = "Rotacao simples a esquerda";
                rotacaoUsadaNoBalanceamento = "Rotacao a esquerda";
                registrarRotacao(ultimaRotacao, pivoBalanceamento);
            }
            return rotacaoEsquerda(noAtual);
        }

        return noAtual;
    }

    private boolean verificarBalanceamento(No noAtual) {
        if (noAtual == null) {
            return true;
        }

        atualizarAltura(noAtual);
        int fator = fatorBalanceamento(noAtual);
        return Math.abs(fator) <= 1
            && verificarBalanceamento(noAtual.esquerda)
            && verificarBalanceamento(noAtual.direita);
    }

    private No rotacaoDireita(No y) {
        No x = y.esquerda;
        No t2 = x.direita;

        x.direita = y;
        y.esquerda = t2;

        atualizarAltura(y);
        atualizarAltura(x);
        return x;
    }

    private No rotacaoEsquerda(No x) {
        No y = x.direita;
        No t2 = y.esquerda;

        y.esquerda = x;
        x.direita = t2;

        atualizarAltura(x);
        atualizarAltura(y);
        return y;
    }

    private void atualizarAltura(No no) {
        no.altura = 1 + Math.max(alturaInterna(no.esquerda), alturaInterna(no.direita));
    }

    private int alturaInterna(No no) {
        return no == null ? -1 : no.altura;
    }

    private int fatorBalanceamento(No no) {
        if (no == null) {
            return 0;
        }
        return alturaInterna(no.esquerda) - alturaInterna(no.direita);
    }

    private void registrarRotacao(String rotacao, String pivo) {
        historicoRotacoes.add(rotacao + " | Pivo: " + pivo);
    }
}
