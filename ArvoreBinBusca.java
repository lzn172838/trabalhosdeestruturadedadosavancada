public class ArvoreBinBusca {

    // Classe que representa cada nó da árvore
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

    // Retorna a raiz da árvore
    public No getRaiz() {
        return raiz;
    }

    // Limpa a árvore (remove todos os nós)
    public void limpar() {
        raiz = null;
    }

    // ============================================================
    // INSERÇÃO
    // ============================================================

    public void inserir(int valor) {
        raiz = inserirRec(raiz, valor);
    }

    private No inserirRec(No atual, int valor) {
        if (atual == null) return new No(valor);
        if (valor < atual.valor) atual.esquerda = inserirRec(atual.esquerda, valor);
        else if (valor > atual.valor) atual.direita = inserirRec(atual.direita, valor);
        return atual;
    }

    // ============================================================
    // PERCURSOS
    // ============================================================

    // Pré-Ordem: visita Raiz → Esquerda → Direita
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

    // Em Ordem: visita Esquerda → Raiz → Direita (resultado em ordem crescente)
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

    // Pós-Ordem: visita Esquerda → Direita → Raiz
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

    // ============================================================
    // ALTURA  (folha = 0, árvore vazia = -1)
    // ============================================================

    public int alturaNo(No no) {
        if (no == null) return -1;
        return 1 + Math.max(alturaNo(no.esquerda), alturaNo(no.direita));
    }

    public int alturaArvore() {
        return alturaNo(raiz);
    }

    // ============================================================
    // NÍVEL  (raiz = 0, filhos = 1, netos = 2 ...)
    // ============================================================

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

    // ============================================================
    // PROFUNDIDADE  (igual ao nível do nó)
    // ============================================================

    public int profundidadeNo(int valor) {
        return nivelNo(valor);
    }

    public int profundidadeArvore() {
        return alturaArvore();
    }

    // ============================================================
    // PARÊNTESES ANINHADOS  ex: (50(30()())(70()()))
    // ============================================================

    public String parentesesAninhados() {
        if (raiz == null) return "(vazia)";
        return parentesesRec(raiz);
    }
    private String parentesesRec(No no) {
        if (no == null) return "()";
        return "(" + no.valor + parentesesRec(no.esquerda) + parentesesRec(no.direita) + ")";
    }
}
