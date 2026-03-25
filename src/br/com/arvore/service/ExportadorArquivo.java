package br.com.arvore.service;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExportadorArquivo {

    public void salvarRelatorioTxt(Component componentePai, String conteudo) {
        if (conteudo == null || conteudo.trim().isEmpty() || conteudo.contains("(arvore vazia)")) {
            JOptionPane.showMessageDialog(componentePai, "Não há dados para salvar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Salvar Relatório da Árvore");
        fc.setSelectedFile(new File("arvore_relatorio.txt"));

        if (fc.showSaveDialog(componentePai) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File arquivo = fc.getSelectedFile();
        if (!arquivo.getName().endsWith(".txt")) {
            arquivo = new File(arquivo.getAbsolutePath() + ".txt");
        }

        try (FileWriter fw = new FileWriter(arquivo)) {
            fw.write("RELATÓRIO DA ÁRVORE BINÁRIA DE BUSCA\n");
            fw.write("==================================================\n\n");
            fw.write(conteudo);
            JOptionPane.showMessageDialog(componentePai, "Salvo com sucesso em:\n" + arquivo.getAbsolutePath(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(componentePai, "Erro ao salvar arquivo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}