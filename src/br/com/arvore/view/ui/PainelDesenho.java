package br.com.arvore.view.ui;

import br.com.arvore.model.INoArvore;
import br.com.arvore.view.config.ConfiguracaoVisual;
import br.com.arvore.view.render.CalculadoraLayout;
import br.com.arvore.view.render.RenderizadorArvore;

import javax.swing.*;
import java.awt.*;

public class PainelDesenho extends JPanel {

    private INoArvore raizView;
    private final CalculadoraLayout calculadoraLayout;
    private final RenderizadorArvore renderizador;

    public PainelDesenho() {
        this.calculadoraLayout = new CalculadoraLayout();
        this.renderizador = new RenderizadorArvore();
        setBackground(ConfiguracaoVisual.COR_FUNDO);
        setPreferredSize(new Dimension(ConfiguracaoVisual.LARGURA_MINIMA, ConfiguracaoVisual.ALTURA_MINIMA));
    }

    public void atualizarArvore(INoArvore raizView) {
        this.raizView = raizView;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (raizView == null) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        configurarDimensoesPainel();

        int centerX = calcularCentroX();
        int startY = 40;
        int espacoInicial = calcularEspacoInicialHorizontal();

        renderizador.desenhar(g2d, raizView, centerX, startY, espacoInicial);
    }

    private void configurarDimensoesPainel() {
        int profundidade = calculadoraLayout.calcularProfundidade(raizView);
        int altura = profundidade * ConfiguracaoVisual.ESPACO_VERTICAL + 100;
        int largura = calculadoraLayout.calcularLarguraNecessaria(profundidade);

        int preferredW = Math.max(largura, ConfiguracaoVisual.LARGURA_MINIMA);
        int preferredH = Math.max(altura, ConfiguracaoVisual.ALTURA_MINIMA);

        setPreferredSize(new Dimension(preferredW, preferredH));
        revalidate();
    }

    private int calcularCentroX() {
        Rectangle vis = getVisibleRect();
        if (getPreferredSize().width > vis.width) {
            return vis.x + vis.width / 2;
        }
        return getPreferredSize().width / 2;
    }

    private int calcularEspacoInicialHorizontal() {
        int profundidade = calculadoraLayout.calcularProfundidade(raizView);
        int div = (int) Math.pow(2, Math.max(1, profundidade));
        return Math.max(
                ConfiguracaoVisual.ESPACO_HORIZONTAL_INICIAL,
                Math.max(ConfiguracaoVisual.ESPACO_MINIMO_HORIZONTAL, getPreferredSize().width / div)
        );
    }
}