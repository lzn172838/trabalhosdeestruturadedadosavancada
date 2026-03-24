package br.com.arvore.model;

public class ArvoreBinBusca {

    // O Nó interno implementa a interface pública de leitura,
    private class No implements INoArvore {
        private int valor;
        private No esquerda;
        private No direita;

        public No(int valor) {
            this.valor = valor;
        }

        @Override public int getValor() { return valor; }
        @Override public INoArvore getEsquerda() { return esquerda; }
        @Override public INoArvore getDireita() { return direita; }
    }

    private No raiz;

    public void inserir(int valor) {
        raiz = inserirRec(raiz, valor);
    }

    private No inserirRec(No atual, int valor) {
        if (atual == null) {
            return new No(valor);
        }
        if (valor < atual.valor) {
            atual.esquerda = inserirRec(atual.esquerda, valor);
        } else if (valor > atual.valor) {
            atual.direita = inserirRec(atual.direita, valor);
        }
        return atual;
    }

    // Expondo apenas a interface de visualização
    public INoArvore getRaizView() {
        return raiz;
    }
}