package br.com.arvore.view.ui;

import br.com.arvore.model.ArvoreBinBusca;
import br.com.arvore.service.ExportadorArquivo;
import javax.swing.*;
import java.awt.*;

public class JanelaPrincipal extends JFrame {
    private final ArvoreBinBusca arvore = new ArvoreBinBusca();
    private final ExportadorArquivo exportador = new ExportadorArquivo();

    // Nomes das variáveis padronizados para evitar erros de "Symbol not found"
    private final PainelDesenho painelDesenho = new PainelDesenho();
    private final PainelInformacoes painelInfo = new PainelInformacoes();

    public JanelaPrincipal() {
        configurarJanela();
        montarLayout();
        atualizarInterface();
    }

    private void configurarJanela() {
        setTitle("Árvore Binária de Busca - Sistema Refatorado");
        setSize(1100, 850);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void montarLayout() {
        setLayout(new BorderLayout(5, 5));

        // Painel de botões (norte)
        PainelEntrada painelEntrada = new PainelEntrada(
                this::inserirElemento,
                this::limparArvore,
                this::salvarRelatorio
        );

        // Painel de desenho com Scroll (centro)
        JScrollPane scrollDesenho = new JScrollPane(painelDesenho);

        add(painelEntrada, BorderLayout.NORTH);
        add(scrollDesenho, BorderLayout.CENTER);
        add(painelInfo, BorderLayout.SOUTH); // Usando a variável padronizada
    }

    private void inserirElemento(int valor) {
        arvore.inserir(valor);
        atualizarInterface();
    }

    private void limparArvore() {
        arvore.limpar();
        atualizarInterface();
    }

    private void salvarRelatorio() {
        // Chama o método que criamos no PainelInformacoes
        String texto = painelInfo.getTextoRelatorio();
        exportador.salvarRelatorioTxt(this, texto);
    }

    private void atualizarInterface() {
        // Sincroniza os dois painéis com os dados da model
        painelDesenho.atualizarArvore(arvore.getRaiz());
        painelInfo.atualizarDados(arvore);
        repaint();
    }
}