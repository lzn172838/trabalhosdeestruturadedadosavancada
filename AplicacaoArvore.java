import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class PainelDesenhoArvore extends JPanel {
    private ArvoreBinBusca arvore;
    private final int RAIO = 15;
    private final int ESPACO_V = 70;
    private final int ESPACO_H = 50;

    private java.util.Map<ArvoreBinBusca.No, int[]> posicoes = new java.util.HashMap<>();
    private int[] contador = {0};

    public PainelDesenhoArvore(ArvoreBinBusca arvore) {
        this.arvore = arvore;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1000, 500));
    }

    private void calcularPosicoes(ArvoreBinBusca.No no, int profundidade) {
        if (no == null) return;
        calcularPosicoes(no.esquerda, profundidade + 1);
        posicoes.put(no, new int[]{contador[0]++, profundidade});
        calcularPosicoes(no.direita, profundidade + 1);
    }

    private int px(ArvoreBinBusca.No no, int offsetX) {
        return posicoes.get(no)[0] * ESPACO_H + offsetX;
    }

    private int py(ArvoreBinBusca.No no, int offsetY) {
        return posicoes.get(no)[1] * ESPACO_V + offsetY;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (arvore.getRaiz() == null) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        posicoes = new java.util.HashMap<>();
        contador = new int[]{0};
        calcularPosicoes(arvore.getRaiz(), 0);

        int qtdNos = posicoes.size();
        int altura = arvore.alturaArvore() + 1;
        int larguraTotal = Math.max(qtdNos * ESPACO_H + 80, 1000);
        int alturaTotal  = Math.max(altura * ESPACO_V + 100, 500);
        setPreferredSize(new Dimension(larguraTotal, alturaTotal));
        revalidate();

        Rectangle vis = getVisibleRect();
        int larguraArvore = (qtdNos - 1) * ESPACO_H;
        int offsetX = vis.x + (vis.width - larguraArvore) / 2;
        int offsetY = 40;

        desenhar(g2, arvore.getRaiz(), offsetX, offsetY);
    }

    private void desenhar(Graphics2D g, ArvoreBinBusca.No no, int offsetX, int offsetY) {
        if (no == null) return;

        int x = px(no, offsetX);
        int y = py(no, offsetY);

        if (no.esquerda != null) {
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(2));
            g.drawLine(x, y, px(no.esquerda, offsetX), py(no.esquerda, offsetY));
            desenhar(g, no.esquerda, offsetX, offsetY);
        }
        if (no.direita != null) {
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(2));
            g.drawLine(x, y, px(no.direita, offsetX), py(no.direita, offsetY));
            desenhar(g, no.direita, offsetX, offsetY);
        }

        g.setColor(Color.WHITE);
        g.fillOval(x - RAIO, y - RAIO, 2 * RAIO, 2 * RAIO);
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawOval(x - RAIO, y - RAIO, 2 * RAIO, 2 * RAIO);

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

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topo.add(new JLabel("Numero:"));
        campoEntrada = new JTextField(8);
        topo.add(campoEntrada);

        JButton btnInserir  = new JButton("Inserir");
        JButton btnLimpar   = new JButton("Limpar Tela");
        JButton btnInverter = new JButton("Inverter");
        JButton btnSalvar   = new JButton("Salvar TXT");
        JButton btnCarregar = new JButton("Carregar TXT");
        topo.add(btnInserir);
        topo.add(btnLimpar);
        topo.add(btnInverter);
        topo.add(btnSalvar);
        topo.add(btnCarregar);
        add(topo, BorderLayout.NORTH);

        painelDesenho = new PainelDesenhoArvore(arvore);
        JScrollPane scrollDesenho = new JScrollPane(painelDesenho);
        add(scrollDesenho, BorderLayout.CENTER);

        areaInfo = new JTextArea(10, 40);
        areaInfo.setEditable(false);
        areaInfo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollInfo = new JScrollPane(areaInfo);
        scrollInfo.setBorder(BorderFactory.createTitledBorder("Informacoes da Arvore"));
        scrollInfo.setPreferredSize(new Dimension(950, 230));
        add(scrollInfo, BorderLayout.SOUTH);

        btnInserir.addActionListener(e -> inserir());
        campoEntrada.addActionListener(e -> inserir());

        btnInverter.addActionListener(e -> {
            arvore.inverter();
            painelDesenho.repaint();
            atualizarInfo();
        });

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

        int[] info21 = buscarNo(arvore.getRaiz(), 21, 0);
        sb.append("No 21:\n");
        if (info21 != null) {
            sb.append("  Nivel        : ").append(info21[0]).append("\n");
            sb.append("  Profundidade : ").append(info21[0]).append("\n");
            sb.append("  Altura       : ").append(info21[1]).append("\n\n");
        } else {
            sb.append("  (nao encontrado na arvore)\n\n");
        }

        sb.append(String.format("  %-8s  %-8s  %-14s  %-8s%n", "No", "Nivel", "Profundidade", "Altura"));
        sb.append("  ").append("-".repeat(44)).append("\n");
        montarTabelaNos(arvore.getRaiz(), 0, sb);

        areaInfo.setText(sb.toString());
        areaInfo.setCaretPosition(0);
    }

    private int[] buscarNo(ArvoreBinBusca.No no, int valor, int nivel) {
        if (no == null) return null;
        if (no.valor == valor) return new int[]{nivel, arvore.alturaNo(no)};
        int[] res = buscarNo(no.esquerda, valor, nivel + 1);
        if (res != null) return res;
        return buscarNo(no.direita, valor, nivel + 1);
    }

    private void montarTabelaNos(ArvoreBinBusca.No no, int nivel, StringBuilder sb) {
        if (no == null) return;
        int altura = arvore.alturaNo(no);
        sb.append(String.format("  %-8d  %-8d  %-14d  %-8d%n", no.valor, nivel, nivel, altura));
        montarTabelaNos(no.esquerda, nivel + 1, sb);
        montarTabelaNos(no.direita,  nivel + 1, sb);
    }

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
