package trabalhosdeestruturadedadosavancada;

public abstract class ArvoreBase {

    public static class No {
        public int valor;
        public No esquerda;
        public No direita;
        public int altura;

        public No(int valor) {
            this.valor = valor;
            this.esquerda = null;
            this.direita = null;
            this.altura = 0;
        }
    }

    protected No raiz;

    public ArvoreBase() {
        this.raiz = null;
    }

    public No getRaiz() {
        return raiz;
    }

    public void limpar() {
        raiz = null;
    }

    public abstract void inserir(int valor);

    public String getNomeTipo() {
        return "Arvore Binaria de Busca";
    }

    public boolean suportaOperacoesAVL() {
        return false;
    }

    public void rotacaoSimplesEsquerdaRaiz() {
        throw new UnsupportedOperationException("Operacao disponivel apenas para AVL.");
    }

    public void rotacaoSimplesDireitaRaiz() {
        throw new UnsupportedOperationException("Operacao disponivel apenas para AVL.");
    }

    public int fatorBalanceamentoRaiz() {
        return 0;
    }

    public void balancearAVL() {
        throw new UnsupportedOperationException("Operacao disponivel apenas para AVL.");
    }

    public boolean estaBalanceadaAVL() {
        return true;
    }

    public boolean podeRotacionarEsquerdaRaiz() {
        return false;
    }

    public boolean podeRotacionarDireitaRaiz() {
        return false;
    }

    public String preOrdem() {
        StringBuilder sb = new StringBuilder();
        preOrdemRec(raiz, sb);
        return limparEspacoFinal(sb);
    }

    private void preOrdemRec(No no, StringBuilder sb) {
        if (no == null) {
            return;
        }

        sb.append(no.valor).append(" ");
        preOrdemRec(no.esquerda, sb);
        preOrdemRec(no.direita, sb);
    }

    public String emOrdem() {
        StringBuilder sb = new StringBuilder();
        emOrdemRec(raiz, sb);
        return limparEspacoFinal(sb);
    }

    private void emOrdemRec(No no, StringBuilder sb) {
        if (no == null) {
            return;
        }

        emOrdemRec(no.esquerda, sb);
        sb.append(no.valor).append(" ");
        emOrdemRec(no.direita, sb);
    }

    public String posOrdem() {
        StringBuilder sb = new StringBuilder();
        posOrdemRec(raiz, sb);
        return limparEspacoFinal(sb);
    }

    private void posOrdemRec(No no, StringBuilder sb) {
        if (no == null) {
            return;
        }

        posOrdemRec(no.esquerda, sb);
        posOrdemRec(no.direita, sb);
        sb.append(no.valor).append(" ");
    }

    public int alturaNo(No no) {
        if (no == null) {
            return -1;
        }
        return 1 + Math.max(alturaNo(no.esquerda), alturaNo(no.direita));
    }

    public int alturaArvore() {
        return alturaNo(raiz);
    }

    public int nivelNo(int valor) {
        return nivelRec(raiz, valor, 0);
    }

    private int nivelRec(No no, int valor, int nivel) {
        if (no == null) {
            return -1;
        }
        if (no.valor == valor) {
            return nivel;
        }
        int esq = nivelRec(no.esquerda, valor, nivel + 1);
        if (esq != -1) {
            return esq;
        }
        return nivelRec(no.direita, valor, nivel + 1);
    }

    public int nivelArvore() {
        return alturaArvore();
    }

    public int profundidadeNo(int valor) {
        return nivelNo(valor);
    }

    public int profundidadeArvore() {
        return alturaArvore();
    }

    public String parentesesAninhados() {
        if (raiz == null) {
            return "(vazia)";
        }
        return parentesesRec(raiz);
    }

    private String parentesesRec(No no) {
        if (no == null) {
            return "()";
        }

        String esquerda = parentesesRec(no.esquerda);
        String direita = parentesesRec(no.direita);
        return "(" + no.valor + esquerda + direita + ")";
    }

    public boolean isCheia() {
        if (raiz == null) {
            return true;
        }
        int altura = alturaArvore();
        return isCheiaRec(raiz, 0, altura);
    }

    private boolean isCheiaRec(No no, int nivel, int altura) {
        if (no == null) {
            return true;
        }
        if (no.esquerda == null && no.direita == null) {
            return nivel == altura;
        }
        return isCheiaRec(no.esquerda, nivel + 1, altura)
            && isCheiaRec(no.direita, nivel + 1, altura);
    }

    public boolean isCompleta() {
        if (raiz == null) {
            return true;
        }
        int altura = alturaArvore();
        return isCompletaRec(raiz, 0, altura);
    }

    private boolean isCompletaRec(No no, int nivel, int altura) {
        if (no == null) {
            return true;
        }
        if (no.esquerda == null || no.direita == null) {
            return nivel >= altura - 1;
        }
        return isCompletaRec(no.esquerda, nivel + 1, altura)
            && isCompletaRec(no.direita, nivel + 1, altura);
    }

    public String tipoArvore() {
        if (raiz == null) {
            return "Vazia";
        }
        if (isCheia()) {
            return "Cheia";
        }
        if (isCompleta()) {
            return "Completa";
        }
        return "Nao Completa";
    }

    public void inverter() {
        inverterRec(raiz);
    }

    private void inverterRec(No no) {
        if (no == null) {
            return;
        }
        No temp = no.esquerda;
        no.esquerda = no.direita;
        no.direita = temp;
        inverterRec(no.esquerda);
        inverterRec(no.direita);
    }

    protected String limparEspacoFinal(StringBuilder sb) {
        int tamanho = sb.length();
        if (tamanho == 0) {
            return "";
        }
        if (sb.charAt(tamanho - 1) == ' ') {
            sb.deleteCharAt(tamanho - 1);
        }
        return sb.toString();
    }
}
