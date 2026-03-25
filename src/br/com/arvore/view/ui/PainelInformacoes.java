package br.com.arvore.view.ui;

import br.com.arvore.model.ArvoreBinBusca;
import javax.swing.*;
import java.awt.*;

public class PainelInformacoes extends JPanel {
    private final JTextArea areaTexto;

    public PainelInformacoes() {
        setLayout(new BorderLayout());
        // Ajusta o tamanho do painel inferior para não ficar achatado
        setPreferredSize(new Dimension(800, 150));

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        // Usar fonte monoespaçada ajuda a alinhar os dados
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 14));

        // Adiciona margem interna para o texto não ficar colado na borda
        areaTexto.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(areaTexto);
        add(scroll, BorderLayout.CENTER);
    }

    // Este método é chamado pela JanelaPrincipal a cada inserção/limpeza
    public void atualizarDados(ArvoreBinBusca arvore) {
        // Usa o getRaiz() que corrigimos no passo anterior!
        if (arvore == null || arvore.getRaiz() == null) {
            areaTexto.setText("(arvore vazia)");
            return;
        }

        // Puxa as métricas lá da sua ArvoreBinBusca.java
        StringBuilder sb = new StringBuilder();
        sb.append("Pré-ordem            : ").append(arvore.preOrdem()).append("\n");
        sb.append("Em-ordem             : ").append(arvore.emOrdem()).append("\n");
        sb.append("Pós-ordem            : ").append(arvore.posOrdem()).append("\n");
        sb.append("Estrutura Parenteses : ").append(arvore.parentesesAninhados()).append("\n");
        sb.append("Altura da Árvore     : ").append(arvore.alturaArvore()).append("\n");

        areaTexto.setText(sb.toString());
    }

    // Este método fornece o texto para o ExportadorArquivo.java
    public String getTextoRelatorio() {
        return areaTexto.getText();
    }
}