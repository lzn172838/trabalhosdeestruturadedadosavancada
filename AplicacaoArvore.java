
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PainelDesenhoArvore extends JPanel {
    private ArvoreBinBusca arvore;
    private final int RAIO_NO = 15;
    private final int ESPACO_VERTICAL = 50;

    public PainelDesenhoArvore(ArvoreBinBusca arvore) {
        this.arvore = arvore;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (arvore.getRaiz() != null) {
            desenharArvore(g2d, arvore.getRaiz(), getWidth() / 2, 30, getWidth() / 4);
        }
    }

    private void desenharArvore(Graphics2D g, ArvoreBinBusca.No no, int x, int y, int espacoHorizontal) {
        // Desenha lado esquerdo
        if (no.esquerda != null) {
            g.setColor(Color.BLACK);
            g.drawLine(x, y, x - espacoHorizontal, y + ESPACO_VERTICAL);
            desenharArvore(g, no.esquerda, x - espacoHorizontal, y + ESPACO_VERTICAL, espacoHorizontal / 2);
        }

        // Desenha lado direito
        if (no.direita != null) {
            g.setColor(Color.BLACK);
            g.drawLine(x, y, x + espacoHorizontal, y + ESPACO_VERTICAL);
            desenharArvore(g, no.direita, x + espacoHorizontal, y + ESPACO_VERTICAL, espacoHorizontal / 2);
        }

        g.setColor(Color.WHITE);
        g.fillOval(x - RAIO_NO, y - RAIO_NO, 2 * RAIO_NO, 2 * RAIO_NO);
        g.setColor(Color.BLACK);
        g.drawOval(x - RAIO_NO, y - RAIO_NO, 2 * RAIO_NO, 2 * RAIO_NO);

        // Desenha o número no centro
        String textoValor = String.valueOf(no.valor);
        FontMetrics fm = g.getFontMetrics();
        int larguraTexto = fm.stringWidth(textoValor);
        int alturaTexto = fm.getAscent();
        g.drawString(textoValor, x - (larguraTexto / 2), y + (alturaTexto / 4));
    }
}


public class AplicacaoArvore extends JFrame {
    
    private ArvoreBinBusca arvore;
    private JTextField campoEntrada;
    private PainelDesenhoArvore painelDesenho;

    public AplicacaoArvore() {
        arvore = new ArvoreBinBusca();

        setTitle("Visão Gráfica da Árvore Binária de Busca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // leitor do input
        JPanel painelTopo = new JPanel();
        painelTopo.add(new JLabel("Digite um número:"));
        
        campoEntrada = new JTextField(10);
        painelTopo.add(campoEntrada);
        
        JButton botaoInserir = new JButton("Inserir na Árvore");
        painelTopo.add(botaoInserir);

        
        painelDesenho = new PainelDesenhoArvore(arvore);
        add(painelTopo, BorderLayout.NORTH);
        add(painelDesenho, BorderLayout.CENTER);

        ActionListener acaoInserir = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarElemento();
            }
        };
        botaoInserir.addActionListener(acaoInserir);
        campoEntrada.addActionListener(acaoInserir);
    }

    private void adicionarElemento() {
        try {
            String texto = campoEntrada.getText().trim();
            if (!texto.isEmpty()) {
                int valor = Integer.parseInt(texto);
                arvore.inserir(valor); // Usa o metodo de inserçao do outro arquivo
                painelDesenho.repaint(); 
                campoEntrada.setText("");
                campoEntrada.requestFocus();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, digite apenas números inteiros válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            campoEntrada.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AplicacaoArvore tela = new AplicacaoArvore();
                tela.setLocationRelativeTo(null);
                tela.setVisible(true);
            }
        });
    }
}