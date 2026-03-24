package br.com.arvore;

import br.com.arvore.view.ui.JanelaPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JanelaPrincipal tela = new JanelaPrincipal();
            tela.setVisible(true);
        });
    }
}