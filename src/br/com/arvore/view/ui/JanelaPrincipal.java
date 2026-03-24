package br.com.arvore.view.ui;

import br.com.arvore.model.ArvoreBinBusca;

import javax.swing.*;
import java.awt.*;

public class JanelaPrincipal extends JFrame {

    private final ArvoreBinBusca arvore;
    private final PainelDesenho painelDesenho;

    public JanelaPrincipal() {
        this.arvore = new ArvoreBinBusca();
        this.painelDesenho = new PainelDesenho();

        configurarJanela();
        montarLayout();
    }

    private void configurarJanela() {
        setTitle("Visão Gráfica da Árvore Binária de Busca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void montarLayout() {
        // Passamos um callback: Quando um número for digitado, ele insere na árvore e atualiza o desenho
        PainelEntrada painelEntrada = new PainelEntrada(this::inserirElemento);

        JScrollPane scrollPane = new JScrollPane(painelDesenho);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(painelEntrada, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void inserirElemento(int valor) {
        arvore.inserir(valor);
        painelDesenho.atualizarArvore(arvore.getRaizView());
    }
}