
public class ArvoreBinBusca {

    
    public class No {
        public int valor;
        public No esquerda;
        public No direita;

        public No(int valor) {
            this.valor = valor;
            this.esquerda = null;
            this.direita = null;
        }
    }

    private No raiz;

    public ArvoreBinBusca() {
        this.raiz = null;
    }

    public No getRaiz() {
        return raiz;
    }

    public void limpar() {
        raiz = null;
    }

    public void inserir(int valor) {
        raiz = inserirRec(raiz, valor);
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

    public String preOrdem() {
        StringBuilder sb = new StringBuilder();
        preOrdemRec(raiz, sb);
        return limparEspacoFinal(sb);
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
        return limparEspacoFinal(sb);
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
        return limparEspacoFinal(sb);
    }
    private void posOrdemRec(No no, StringBuilder sb) {
        if (no == null) return;

        posOrdemRec(no.esquerda, sb);
        posOrdemRec(no.direita, sb);
        sb.append(no.valor).append(" ");
    }

    public int alturaNo(No no) {
        if (no == null) return -1;
        return 1 + Math.max(alturaNo(no.esquerda), alturaNo(no.direita));
    }

    public int alturaArvore() {
        return alturaNo(raiz);
    }

    public int nivelNo(int valor) {
        return nivelRec(raiz, valor, 0);
    }
    private int nivelRec(No no, int valor, int nivel) {
        if (no == null) return -1;
        if (no.valor == valor) return nivel;
        int esq = nivelRec(no.esquerda, valor, nivel + 1);
        if (esq != -1) return esq;
        return nivelRec(no.direita, valor, nivel + 1);
    }

    public int nivelArvore() {
        return alturaArvore(); // nível máximo da árvore = sua altura
    }

    public int profundidadeNo(int valor) {
        return nivelNo(valor);
    }

    public int profundidadeArvore() {
        return alturaArvore();
    }

    public String parentesesAninhados() {
        if (raiz == null) return "(vazia)";
        return parentesesRec(raiz);
    }
    private String parentesesRec(No no) {
        if (no == null) return "()";

        String esquerda = parentesesRec(no.esquerda);
        String direita = parentesesRec(no.direita);
        return "(" + no.valor + esquerda + direita + ")";
    }

    // --- Tipo da Árvore ---

    // Cheia: todas as folhas estão no último nível
    public boolean isCheia() {
        if (raiz == null) return true;
        int altura = alturaArvore();
        return isCheiaRec(raiz, 0, altura);
    }
    private boolean isCheiaRec(No no, int nivel, int altura) {
        if (no == null) return true;
        if (no.esquerda == null && no.direita == null)
            return nivel == altura;
        return isCheiaRec(no.esquerda, nivel + 1, altura) &&
               isCheiaRec(no.direita,  nivel + 1, altura);
    }

    // Completa: todo nó sem pelo menos uma subárvore deve estar no último ou penúltimo nível
    public boolean isCompleta() {
        if (raiz == null) return true;
        int altura = alturaArvore();
        return isCompletaRec(raiz, 0, altura);
    }
    private boolean isCompletaRec(No no, int nivel, int altura) {
        if (no == null) return true;
        if (no.esquerda == null || no.direita == null)
            return nivel >= altura - 1;
        return isCompletaRec(no.esquerda, nivel + 1, altura) &&
               isCompletaRec(no.direita,  nivel + 1, altura);
    }

    public String tipoArvore() {
        if (raiz == null) return "Vazia";
        if (isCheia()) return "Cheia";
        if (isCompleta()) return "Completa";
        return "Nao Completa";
    }

    private String limparEspacoFinal(StringBuilder sb) {
        int tamanho = sb.length();
        if (tamanho == 0) return "";
        if (sb.charAt(tamanho - 1) == ' ') {
            sb.deleteCharAt(tamanho - 1);
        }
        return sb.toString();
    }
}
