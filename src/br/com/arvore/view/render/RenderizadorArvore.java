package br.com.arvore.view.render;

import br.com.arvore.model.INoArvore;
import br.com.arvore.view.config.ConfiguracaoVisual;

import java.awt.*;

public class RenderizadorArvore {

    public void desenhar(Graphics2D g, INoArvore no, int x, int y, int espacoHorizontal) {
        if (no == null) return;

        espacoHorizontal = Math.max(espacoHorizontal, ConfiguracaoVisual.ESPACO_MINIMO_HORIZONTAL);
        g.setStroke(new BasicStroke(2));

        desenharConexaoEsquerda(g, no, x, y, espacoHorizontal);
        desenharConexaoDireita(g, no, x, y, espacoHorizontal);
        desenharNo(g, no, x, y);
    }

    private void desenharConexaoEsquerda(Graphics2D g, INoArvore no, int x, int y, int espacoHorizontal) {
        if (no.getEsquerda() != null) {
            g.setColor(ConfiguracaoVisual.COR_LINHA);
            int novoX = x - espacoHorizontal;
            int novoY = y + ConfiguracaoVisual.ESPACO_VERTICAL;
            g.drawLine(x, y, novoX, novoY);
            desenhar(g, no.getEsquerda(), novoX, novoY, espacoHorizontal / 2);
        }
    }

    private void desenharConexaoDireita(Graphics2D g, INoArvore no, int x, int y, int espacoHorizontal) {
        if (no.getDireita() != null) {
            g.setColor(ConfiguracaoVisual.COR_LINHA);
            int novoX = x + espacoHorizontal;
            int novoY = y + ConfiguracaoVisual.ESPACO_VERTICAL;
            g.drawLine(x, y, novoX, novoY);
            desenhar(g, no.getDireita(), novoX, novoY, espacoHorizontal / 2);
        }
    }

    private void desenharNo(Graphics2D g, INoArvore no, int x, int y) {
        int raio = ConfiguracaoVisual.RAIO_NO;

        // Fundo do nó
        g.setColor(ConfiguracaoVisual.COR_FUNDO_NO);
        g.fillOval(x - raio, y - raio, 2 * raio, 2 * raio);

        // Borda do nó
        g.setColor(ConfiguracaoVisual.COR_LINHA);
        g.drawOval(x - raio, y - raio, 2 * raio, 2 * raio);

        // Texto do nó
        desenharTexto(g, String.valueOf(no.getValor()), x, y);
    }

    private void desenharTexto(Graphics2D g, String texto, int x, int y) {
        FontMetrics fm = g.getFontMetrics();
        int larguraTexto = fm.stringWidth(texto);
        int alturaTexto = fm.getAscent();

        g.setColor(ConfiguracaoVisual.COR_TEXTO);
        g.drawString(texto, x - (larguraTexto / 2), y + (alturaTexto / 4));
    }
}