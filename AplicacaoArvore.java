import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

// Painel responsável por desenhar a árvore graficamente
class PainelDesenhoArvore extends JPanel {
    private ArvoreBinBusca arvore;
    private final int RAIO = 15;
    private final int ESPACO_V = 70;

    public PainelDesenhoArvore(ArvoreBinBusca arvore) {
        this.arvore = arvore;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1000, 500));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (arvore.getRaiz() == null) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int altura = arvore.alturaArvore() + 1;
        int largura = (int) Math.pow(2, altura) * 80 + 150;
        setPreferredSize(new Dimension(Math.max(largura, 1000), Math.max(altura * ESPACO_V + 100, 500)));
        revalidate();

        Rectangle vis = getVisibleRect();
        int centroX = vis.x + vis.width / 2;
        int espacoInicial = Math.max(80, largura / (int) Math.pow(2, Math.max(1, altura)));

        desenhar(g2, arvore.getRaiz(), centroX, 40, espacoInicial);
    }

    private void desenhar(Graphics2D g, ArvoreBinBusca.No no, int x, int y, int espaco) {
        espaco = Math.max(espaco, 25);

        if (no.esquerda != null) {
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(2));
            g.drawLine(x, y, x - espaco, y + ESPACO_V);
            desenhar(g, no.esquerda, x - espaco, y + ESPACO_V, espaco / 2);
        }
        if (no.direita != null) {
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(2));
            g.drawLine(x, y, x + espaco, y + ESPACO_V);
            desenhar(g, no.direita, x + espaco, y + ESPACO_V, espaco / 2);
        }

        // Desenha o círculo do nó
        g.setColor(Color.WHITE);
        g.fillOval(x - RAIO, y - RAIO, 2 * RAIO, 2 * RAIO);
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawOval(x - RAIO, y - RAIO, 2 * RAIO, 2 * RAIO);

        // Desenha o valor dentro do círculo
        String txt = String.valueOf(no.valor);
        FontMetrics fm = g.getFontMetrics();
        g.drawString(txt, x - fm.stringWidth(txt) / 2, y + fm.getAscent() / 4);
    }
}


public class AplicacaoArvore extends JFrame {

    private ArvoreBinBusca arvore = new ArvoreBinBusca();
    private JTextField campoEntrada;
    private PainelDesenhoArvore painelDesenho;
    private JTextArea areaInfo;

    public AplicacaoArvore() {
        setTitle("Arvore Binaria de Busca");
        setSize(950, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        // ---- Painel de botões (topo) ----
        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topo.add(new JLabel("Numero:"));
        campoEntrada = new JTextField(8);
        topo.add(campoEntrada);

        JButton btnInserir  = new JButton("Inserir");
        JButton btnLimpar   = new JButton("Limpar Tela");
        JButton btnSalvar   = new JButton("Salvar TXT");
        JButton btnCarregar = new JButton("Carregar TXT");
        topo.add(btnInserir);
        topo.add(btnLimpar);
        topo.add(btnSalvar);
        topo.add(btnCarregar);
        add(topo, BorderLayout.NORTH);

        // ---- Área de desenho da árvore (centro) ----
        painelDesenho = new PainelDesenhoArvore(arvore);
        JScrollPane scrollDesenho = new JScrollPane(painelDesenho);
        add(scrollDesenho, BorderLayout.CENTER);

        // ---- Área de informações (baixo) ----
        areaInfo = new JTextArea(10, 40);
        areaInfo.setEditable(false);
        areaInfo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollInfo = new JScrollPane(areaInfo);
        scrollInfo.setBorder(BorderFactory.createTitledBorder("Informacoes da Arvore"));
        scrollInfo.setPreferredSize(new Dimension(950, 230));
        add(scrollInfo, BorderLayout.SOUTH);

        // ---- Ações dos botões ----
        btnInserir.addActionListener(e -> inserir());
        campoEntrada.addActionListener(e -> inserir());

        btnLimpar.addActionListener(e -> {
            arvore.limpar();
            painelDesenho.repaint();
            atualizarInfo();
        });

        btnSalvar.addActionListener(e -> salvar());
        btnCarregar.addActionListener(e -> carregar());

        atualizarInfo();
    }

    private void inserir() {
        try {
            String txt = campoEntrada.getText().trim();
            if (txt.isEmpty()) return;
            arvore.inserir(Integer.parseInt(txt));
            campoEntrada.setText("");
            campoEntrada.requestFocus();
            painelDesenho.repaint();
            atualizarInfo();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um numero inteiro valido!", "Erro", JOptionPane.ERROR_MESSAGE);
            campoEntrada.setText("");
        }
    }

    // Monta o texto com todas as informações da árvore
    private void atualizarInfo() {
        if (arvore.getRaiz() == null) {
            areaInfo.setText("  (arvore vazia)");
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Parenteses Aninhados : ").append(arvore.parentesesAninhados()).append("\n\n");

        sb.append("Percursos:\n");
        sb.append("  Pre-Ordem  (Raiz-Esq-Dir): ").append(arvore.preOrdem()).append("\n");
        sb.append("  Em Ordem   (Esq-Raiz-Dir): ").append(arvore.emOrdem()).append("\n");
        sb.append("  Pos-Ordem  (Esq-Dir-Raiz): ").append(arvore.posOrdem()).append("\n\n");

        sb.append("Arvore:\n");
        sb.append("  Tipo       : ").append(arvore.tipoArvore()).append("\n");
        sb.append("  Altura     : ").append(arvore.alturaArvore()).append("\n");
        sb.append("  Nivel      : ").append(arvore.nivelArvore()).append("\n");
        sb.append("  Profundidade: ").append(arvore.profundidadeArvore()).append("\n\n");

        sb.append(String.format("  %-8s  %-8s  %-14s  %-8s%n", "No", "Nivel", "Profundidade", "Altura"));
        sb.append("  ").append("-".repeat(44)).append("\n");
        montarTabelaNos(arvore.getRaiz(), 0, sb);

        areaInfo.setText(sb.toString());
        areaInfo.setCaretPosition(0);
    }

    // Percorre a árvore em pré-ordem e exibe info de cada nó
    private void montarTabelaNos(ArvoreBinBusca.No no, int nivel, StringBuilder sb) {
        if (no == null) return;
        int altura = arvore.alturaNo(no);
        sb.append(String.format("  %-8d  %-8d  %-14d  %-8d%n", no.valor, nivel, nivel, altura));
        montarTabelaNos(no.esquerda, nivel + 1, sb);
        montarTabelaNos(no.direita,  nivel + 1, sb);
    }

    // Salva o conteúdo da área de informações em um arquivo TXT
    private void salvar() {
        if (arvore.getRaiz() == null) {
            JOptionPane.showMessageDialog(this, "A arvore esta vazia!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Salvar Arvore");
        fc.setSelectedFile(new File("arvore.txt"));
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

        File arquivo = fc.getSelectedFile();
        if (!arquivo.getName().endsWith(".txt")) {
            arquivo = new File(arquivo.getAbsolutePath() + ".txt");
        }

        try (FileWriter fw = new FileWriter(arquivo)) {
            fw.write("ARVORE BINARIA DE BUSCA\n");
            fw.write("=".repeat(50) + "\n\n");
            fw.write(areaInfo.getText());
            JOptionPane.showMessageDialog(this, "Salvo em:\n" + arquivo.getAbsolutePath(), "OK", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Carrega uma árvore a partir de um arquivo TXT salvo anteriormente
    private void carregar() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Carregar Arvore");
        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivo de texto (*.txt)", "txt"));
        if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;

        File arquivo = fc.getSelectedFile();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            String preOrdem = null;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().startsWith("Pre-Ordem")) {
                    int idx = linha.indexOf(':');
                    if (idx >= 0) {
                        preOrdem = linha.substring(idx + 1).trim();
                    }
                    break;
                }
            }

            if (preOrdem == null || preOrdem.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Arquivo invalido: linha 'Pre-Ordem' nao encontrada.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            arvore.limpar();
            for (String token : preOrdem.split("\\s+")) {
                if (!token.isEmpty()) {
                    arvore.inserir(Integer.parseInt(token));
                }
            }

            painelDesenho.repaint();
            atualizarInfo();
            JOptionPane.showMessageDialog(this,
                "Arvore carregada com sucesso!", "OK", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao ler valores do arquivo: " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao abrir arquivo: " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AplicacaoArvore app = new AplicacaoArvore();
            app.setLocationRelativeTo(null);
            app.setVisible(true);
        });
    }
}
