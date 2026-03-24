
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PainelDesenhoArvore extends JPanel {
    private ArvoreBinBusca arvore;
    private final int RAIO_NO = 15;
    private final int ESPACO_VERTICAL = 70;
    private final int ESPACO_HORIZONTAL_INICIAL = 80;
    private final int LARGURA_MINIMA = 1000;
    private final int ALTURA_MINIMA = 600;

    public PainelDesenhoArvore(ArvoreBinBusca arvore) {
        this.arvore = arvore;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(LARGURA_MINIMA, ALTURA_MINIMA));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (arvore.getRaiz() != null) {
            // Calcula a altura e largura necessárias
            int profundidade = calcularProfundidade(arvore.getRaiz());
            int altura = profundidade * ESPACO_VERTICAL + 100;
            int largura = calcularLarguraNecessaria(profundidade);
            
            // Atualiza o tamanho preferido
            int preferredW = Math.max(largura, LARGURA_MINIMA);
            int preferredH = Math.max(altura, ALTURA_MINIMA);
            setPreferredSize(new Dimension(preferredW, preferredH));
            revalidate();

            // Use o centro visível do viewport quando o conteúdo for maior que a viewport
            Rectangle vis = getVisibleRect();
            int centerX;
            if (getPreferredSize().width > vis.width) {
                centerX = vis.x + vis.width / 2;
            } else {
                centerX = getPreferredSize().width / 2;
            }
            int startY = 40;

            // Calcula um espaçamento horizontal inicial baseado na largura total e profundidade
            int espacoInicial = Math.max(ESPACO_HORIZONTAL_INICIAL,
                    Math.max(25, getPreferredSize().width / (int) Math.pow(2, Math.max(1, profundidade))));

            desenharArvore(g2d, arvore.getRaiz(), centerX, startY, espacoInicial);
        }
    }

    private int calcularProfundidade(ArvoreBinBusca.No no) {
        if (no == null) {
            return 0;
        }
        return 1 + Math.max(calcularProfundidade(no.esquerda), calcularProfundidade(no.direita));
    }

    private int calcularLarguraNecessaria(int profundidade) {
        return (int) Math.pow(2, profundidade) * 80 + 150;
    }

    private void desenharArvore(Graphics2D g, ArvoreBinBusca.No no, int x, int y, int espacoHorizontal) {
        // Garante que o espaço horizontal não fique muito pequeno
        espacoHorizontal = Math.max(espacoHorizontal, 25);

        // Desenha lado esquerdo
        if (no.esquerda != null) {
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(2));
            g.drawLine(x, y, x - espacoHorizontal, y + ESPACO_VERTICAL);
            desenharArvore(g, no.esquerda, x - espacoHorizontal, y + ESPACO_VERTICAL, espacoHorizontal / 2);
        }

        // Desenha lado direito
        if (no.direita != null) {
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(2));
            g.drawLine(x, y, x + espacoHorizontal, y + ESPACO_VERTICAL);
            desenharArvore(g, no.direita, x + espacoHorizontal, y + ESPACO_VERTICAL, espacoHorizontal / 2);
        }

        // Desenha o nó
        g.setColor(Color.WHITE);
        g.fillOval(x - RAIO_NO, y - RAIO_NO, 2 * RAIO_NO, 2 * RAIO_NO);
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawOval(x - RAIO_NO, y - RAIO_NO, 2 * RAIO_NO, 2 * RAIO_NO);

        // Desenha o número no centro
        String textoValor = String.valueOf(no.valor);
        FontMetrics fm = g.getFontMetrics();
        int larguraTexto = fm.stringWidth(textoValor);
        int alturaTexto = fm.getAscent();
        g.setColor(Color.BLACK);
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
        JScrollPane scrollPane = new JScrollPane(painelDesenho);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        add(painelTopo, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

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