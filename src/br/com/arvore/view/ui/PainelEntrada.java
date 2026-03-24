package br.com.arvore.view.ui;

import javax.swing.*;
import java.util.function.Consumer;

public class PainelEntrada extends JPanel {

    private JTextField campoEntrada;
    private Consumer<Integer> onInserirCallback;

    public PainelEntrada(Consumer<Integer> onInserirCallback) {
        this.onInserirCallback = onInserirCallback;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        add(new JLabel("Digite um número:"));

        campoEntrada = new JTextField(10);
        add(campoEntrada);

        JButton botaoInserir = new JButton("Inserir na Árvore");
        add(botaoInserir);

        botaoInserir.addActionListener(e -> processarEntrada());
        campoEntrada.addActionListener(e -> processarEntrada());
    }

    private void processarEntrada() {
        try {
            String texto = campoEntrada.getText().trim();
            if (!texto.isEmpty()) {
                int valor = Integer.parseInt(texto);
                onInserirCallback.accept(valor);
                limparCampo();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, digite apenas números inteiros válidos.",
                    "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            limparCampo();
        }
    }

    private void limparCampo() {
        campoEntrada.setText("");
        campoEntrada.requestFocus();
    }
}