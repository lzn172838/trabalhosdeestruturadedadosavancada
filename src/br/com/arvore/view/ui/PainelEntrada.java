package br.com.arvore.view.ui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class PainelEntrada extends JPanel {
    private JTextField campoEntrada;

    // O construtor agora exige 3 parâmetros (um Consumer e dois Runnables)
    public PainelEntrada(Consumer<Integer> onInserir, Runnable onLimpar, Runnable onSalvar) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createEtchedBorder());

        add(new JLabel("Inserir Valor:"));
        campoEntrada = new JTextField(10);
        add(campoEntrada);

        JButton btnInserir = new JButton("Inserir");
        JButton btnLimpar = new JButton("Limpar Árvore");
        JButton btnSalvar = new JButton("Exportar TXT");

        // Configuração dos eventos
        btnInserir.addActionListener(e -> executarInsercao(onInserir));
        campoEntrada.addActionListener(e -> executarInsercao(onInserir));

        btnLimpar.addActionListener(e -> onLimpar.run());
        btnSalvar.addActionListener(e -> onSalvar.run());

        add(btnInserir);
        add(btnLimpar);
        add(btnSalvar);
    }

    private void executarInsercao(Consumer<Integer> callback) {
        try {
            String texto = campoEntrada.getText().trim();
            if (!texto.isEmpty()) {
                callback.accept(Integer.parseInt(texto));
                campoEntrada.setText("");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um número inteiro válido.");
        }
        campoEntrada.requestFocus();
    }
}