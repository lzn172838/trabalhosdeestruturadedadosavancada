package trabalhosdeestruturadedadosavancada;

public class ArvoreBinBusca extends ArvoreBase {

    @Override
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
}
