package br.com.arvore.model;

public class ArvoreBinBusca {

    public INoArvore getRaiz() {
        return this.raiz;
    }

    private class No implements INoArvore {
        private int valor;
        private No esquerda;
        private No direita;

        public No(int valor) { this.valor = valor; }

        @Override public int getValor() { return valor; }
        @Override public INoArvore getEsquerda() { return esquerda; }
        @Override public INoArvore getDireita() { return direita; }
    }

    private No raiz;

    public void inserir(int valor) {
        raiz = inserirRec(raiz, valor);
    }

    private No inserirRec(No atual, int valor) {
        if (atual == null) return new No(valor);
        if (valor < atual.valor) atual.esquerda = inserirRec(atual.esquerda, valor);
        else if (valor > atual.valor) atual.direita = inserirRec(atual.direita, valor);
        return atual;
    }

    public INoArvore getRaizView() {
        return raiz;
    }

    public void limpar() {
        raiz = null;
    }

    // --- PERCURSOS ---
    public String preOrdem() {
        StringBuilder sb = new StringBuilder();
        preOrdemRec(raiz, sb);
        return sb.toString().trim();
    }
    private void preOrdemRec(No no, StringBuilder sb) {
        if (no == null) return;
        sb.append(no.valor).append(" ");
        preOrdemRec(no.esquerda, sb);
        preOrdemRec(no.direita, sb);
    }

    public String emOrdem() {
        StringBuilder sb = new StringBuilder();
        emOrdemRec(raiz, sb);
        return sb.toString().trim();
    }
    private void emOrdemRec(No no, StringBuilder sb) {
        if (no == null) return;
        emOrdemRec(no.esquerda, sb);
        sb.append(no.valor).append(" ");
        emOrdemRec(no.direita, sb);
    }

    public String posOrdem() {
        StringBuilder sb = new StringBuilder();
        posOrdemRec(raiz, sb);
        return sb.toString().trim();
    }
    private void posOrdemRec(No no, StringBuilder sb) {
        if (no == null) return;
        posOrdemRec(no.esquerda, sb);
        posOrdemRec(no.direita, sb);
        sb.append(no.valor).append(" ");
    }

    // --- MÉTRICAS ---
    public int alturaArvore() { return alturaNo(raiz); }

    private int alturaNo(No no) {
        if (no == null) return -1;
        return 1 + Math.max(alturaNo(no.esquerda), alturaNo(no.direita));
    }

    public int nivelArvore() { return alturaArvore(); }
    public int profundidadeArvore() { return alturaArvore(); }

    public String parentesesAninhados() {
        if (raiz == null) return "(vazia)";
        return parentesesRec(raiz);
    }
    private String parentesesRec(No no) {
        if (no == null) return "()";
        return "(" + no.valor + parentesesRec(no.esquerda) + parentesesRec(no.direita) + ")";
    }

    // Auxiliar para a View montar a tabela sem acessar o Nó privado diretamente
    public int buscarAlturaNo(int valor) {
        return alturaNo(buscarNoRec(raiz, valor));
    }

    private No buscarNoRec(No atual, int valor) {
        if (atual == null || atual.valor == valor) return atual;
        if (valor < atual.valor) return buscarNoRec(atual.esquerda, valor);
        return buscarNoRec(atual.direita, valor);
    }
}