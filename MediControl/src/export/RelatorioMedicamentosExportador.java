package export;

import dao.RegistroDAO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import modelo.Registro;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class RelatorioMedicamentosExportador {

    public static void exportar(LocalDate dataInicio, LocalDate dataFim, int usuarioId) {
    try {
        RegistroDAO dao = new RegistroDAO();
        StringBuilder relatorio = new StringBuilder();
        DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter horaFormatada = DateTimeFormatter.ofPattern("HH:mm");

        relatorio.append("RELATÓRIO DE MEDICAMENTOS\n");
        relatorio.append("Período: ").append(dataInicio.format(dataFormatada)).append(" até ").append(dataFim.format(dataFormatada)).append("\n\n");

        relatorio.append(">>> REMÉDIOS TOMADOS:\n");
        List<Registro> tomados = dao.listarPeriodo(dataInicio, dataFim, "Tomado", usuarioId);
        if (tomados.isEmpty()) {
            relatorio.append("Nenhum registro encontrado.\n");
        } else {
            tomados.sort(Comparator.comparing(Registro::getHorario));
            for (Registro r : tomados) {
                relatorio.append("- ")
                         .append(r.getMedicamento().getNome()).append(" | ")
                         .append(r.getHorario().toLocalTime().format(horaFormatada)).append(" | ")
                         .append(r.getHorario().toLocalDate().format(dataFormatada)).append("\n");
            }
            
        }

        relatorio.append("\n>>> REMÉDIOS ESQUECIDOS:\n");
        List<Registro> esquecidos = dao.listarPeriodo(dataInicio, dataFim, "Esquecido", usuarioId);
        if (esquecidos.isEmpty()) {
            relatorio.append("Nenhum registro encontrado.\n");
        } else {
            esquecidos.sort(Comparator.comparing(Registro::getHorario));
            for (Registro r : esquecidos) {
                relatorio.append("- ")
                         .append(r.getMedicamento().getNome()).append(" | ")
                         .append(r.getHorario().toLocalTime().format(horaFormatada)).append(" | ")
                         .append(r.getHorario().toLocalDate().format(dataFormatada)).append("\n");
            }
        }
        
        UIManager.put("FileChooser.openDialogTitleText", "Abrir");
        UIManager.put("FileChooser.saveDialogTitleText", "Salvar");
        UIManager.put("FileChooser.cancelButtonText", "Cancelar");
        UIManager.put("FileChooser.saveButtonText", "Salvar");
        UIManager.put("FileChooser.openButtonText", "Abrir");
        UIManager.put("FileChooser.lookInLabelText", "Procurar em");
        UIManager.put("FileChooser.fileNameLabelText", "Nome do arquivo");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Tipo de arquivos");
        UIManager.put("FileChooser.upFolderToolTipText", "Subir uma pasta");
        UIManager.put("FileChooser.newFolderToolTipText", "Criar nova pasta");
        UIManager.put("FileChooser.listViewButtonToolTipText", "Lista");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Detalhes");
        UIManager.put("FileChooser.fileNameHeaderText", "Nome");
        UIManager.put("FileChooser.fileSizeHeaderText", "Tamanho");
        UIManager.put("FileChooser.fileTypeHeaderText", "Tipo");
        UIManager.put("FileChooser.fileDateHeaderText", "Data");
        UIManager.put("FileChooser.fileAttrHeaderText", "Atributos");


     JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salvar Relatório");

            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File arquivo = fileChooser.getSelectedFile();

                if (!arquivo.getName().toLowerCase().endsWith(".txt")) {
                    arquivo = new File(arquivo.getAbsolutePath() + ".txt");
                }

                try (FileWriter writer = new FileWriter(arquivo)) {
                    writer.write(relatorio.toString());
                    JOptionPane.showMessageDialog(null, "Relatório salvo com sucesso!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
