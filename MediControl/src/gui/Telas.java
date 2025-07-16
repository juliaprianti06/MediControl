package gui;

import dao.MedicamentoDAO;
import dao.RegistroDAO;
import design.Estilo;
import java.awt.CardLayout;
import java.awt.Image;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Medicamento;
import modelo.Registro;
import java.sql.SQLException;
import utils.HorariosUtils;

public class Telas extends javax.swing.JFrame {

    private int usuarioId;  

    public Telas(int usuarioId) {
        this.usuarioId = usuarioId;
        initComponents();
        designTelaInicial();
        setResizable(false);

        CardLayout cl = (CardLayout) panelTela.getLayout();
        cl.show(panelTela, "telaPrincipal");

        configurarTabelaComCheckbox(); 
        atualizarTabelaRemediosHoje(); 
        atualizarTabelaRemediosEsquecidos();

        DataChooserInicio.setDateFormatString("dd/MM/yyyy");
        DataChooserFim.setDateFormatString("dd/MM/yyyy");
    }

private void configurarTabelaComCheckbox() {
    DefaultTableModel modelo = new DefaultTableModel(
        new Object[]{"ID", "Status", "Nome", "Horário", "idMed", "horarioFull"}, 0
    ) {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 1) return Boolean.class;
            return String.class;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 1; // Apenas o checkbox
        }
    };

    tabelaRemedios.setModel(modelo);
    for (int c : new int[]{0, 4, 5}) {
        tabelaRemedios.getColumnModel().getColumn(c).setMinWidth(0);
        tabelaRemedios.getColumnModel().getColumn(c).setMaxWidth(0);
        tabelaRemedios.getColumnModel().getColumn(c).setWidth(0);
    }

    modelo.addTableModelListener(e -> {
        if (e.getColumn() != 1) return;

        int row = e.getFirstRow();
        Boolean marcado = (Boolean) modelo.getValueAt(row, 1);
        if (marcado == null) return;

        Integer idRegistro = (Integer) modelo.getValueAt(row, 0);
        Integer idMedicamento = (Integer) modelo.getValueAt(row, 4);
        LocalDateTime horario = (LocalDateTime) modelo.getValueAt(row, 5);

        try {
            RegistroDAO dao = new RegistroDAO();

            if (idRegistro != null && idRegistro > 0) {
                dao.atualizarStatusRegistro(
                    idRegistro,
                    marcado ? "TOMADO" : "ESQUECIDO",
                    usuarioId
                );
            } else {
                if (marcado) {
                    dao.registrarTomado(idMedicamento, horario, usuarioId);
                } else {
                    dao.registrarEsquecido(idMedicamento, horario, usuarioId);
                }
            }

            atualizarTabelaRemediosHoje();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Erro ao salvar status: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    });
}

    private void carregarTabela() {
        try {
            MedicamentoDAO dao = new MedicamentoDAO();
            List<Medicamento> lista = dao.listarTodos(usuarioId);

            DefaultTableModel modelo = (DefaultTableModel) tabelaMedicamentos.getModel();
            modelo.setRowCount(0);

            for (Medicamento m : lista) {
                modelo.addRow(new Object[]{
                    m.getId(),
                    m.getNome(),
                    m.getDosagem() + " " + m.getUnidade(),
                    m.getForma(),
                    m.getIntervaloUso() + "h",
                    m.getInicioTratamento(),
                    m.isIndeterminado() ? "Indeterminado" : m.getDuracaoDias() + " dias"
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tabela: " + ex.getMessage());
        }
    }

    public void atualizarTabelaRemediosHoje() {
    DefaultTableModel modelo = (DefaultTableModel) tabelaRemedios.getModel();
    modelo.setRowCount(0); // Limpa a tabela

    MedicamentoDAO medicamentoDAO = new MedicamentoDAO();
    RegistroDAO registroDAO = new RegistroDAO();

    class Entrada {
        int idRegistro;
        boolean tomado;
        String nome;
        LocalDateTime horarioCompleto;
        int idMedicamento;

        Entrada(int idRegistro, boolean tomado, String nome, LocalDateTime horarioCompleto, int idMedicamento) {
            this.idRegistro = idRegistro;
            this.tomado = tomado;
            this.nome = nome;
            this.horarioCompleto = horarioCompleto;
            this.idMedicamento = idMedicamento;
        }
    }

    try {
        List<Medicamento> medicamentos = medicamentoDAO.listarTodos(usuarioId);
        List<Entrada> entradas = new ArrayList<>();

        for (Medicamento m : medicamentos) {
            LocalTime horaPrimeiraDose = m.getHoraPrimeiraDose();

            if (horaPrimeiraDose == null) {
                JOptionPane.showMessageDialog(null,
                    "O medicamento \"" + m.getNome() + "\" não possui horário da primeira dose definido.\nPor favor, edite o cadastro.",
                    "Erro de horário da primeira dose",
                    JOptionPane.ERROR_MESSAGE);
                continue;
            }

            List<LocalDateTime> horarios = HorariosUtils.gerarHorariosDeHoje(
                m.getInicioTratamento(),
                m.getIntervaloUso(),
                m.getDuracaoDias(),
                m.isIndeterminado(),
                horaPrimeiraDose
            );

            for (LocalDateTime horario : horarios) {
                Registro registro = registroDAO.buscarPorMedicamentoHorario(m.getId(), horario, usuarioId);
                if (registro == null && horario.isBefore(LocalDateTime.now())) {
                    registroDAO.registrarEsquecido(m.getId(), horario, usuarioId);
                    registro = registroDAO.buscarPorMedicamentoHorario(m.getId(), horario, usuarioId);
                }

                boolean tomado = registro != null && "TOMADO".equalsIgnoreCase(registro.getStatus());
                int idRegistro = registro != null ? registro.getId() : 0;

                entradas.add(new Entrada(idRegistro, tomado, m.getNome(), horario, m.getId()));
            }
        }

        entradas.sort((e1, e2) -> e1.horarioCompleto.compareTo(e2.horarioCompleto));

        for (Entrada e : entradas) {
            modelo.addRow(new Object[]{
                e.idRegistro,
                e.tomado,
                e.nome,
                e.horarioCompleto.toLocalTime().toString(),
                e.idMedicamento,
                e.horarioCompleto
            });
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null,
            "Erro ao atualizar a tabela de medicamentos: " + e.getMessage(),
            "Erro",
            JOptionPane.ERROR_MESSAGE);
    }
}

    public void atualizarTabelaRemediosEsquecidos() {
        DefaultTableModel modelo = (DefaultTableModel) tabelaEsquecidos.getModel();
        modelo.setRowCount(0); 

        MedicamentoDAO medicamentoDAO = new MedicamentoDAO();
        RegistroDAO registroDAO = new RegistroDAO();

        class Entrada {
            String nome;
            LocalTime horario;

            Entrada(String nome, LocalTime horario) {
                this.nome = nome;
                this.horario = horario;
            }
        }

        try {
            List<Medicamento> medicamentos = medicamentoDAO.listarTodos(usuarioId);
            List<Entrada> esquecidos = new ArrayList<>();

            for (Medicamento m : medicamentos) {
                LocalTime horaPrimeiraDose = m.getHoraPrimeiraDose();

                if (horaPrimeiraDose == null) {
                    JOptionPane.showMessageDialog(null,
                        "O medicamento \"" + m.getNome() + "\" não possui horário da primeira dose definido.\nPor favor, edite o cadastro.",
                        "Erro de horário da primeira dose",
                        JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                List<LocalDateTime> horarios = HorariosUtils.gerarHorariosDeHoje(
                    m.getInicioTratamento(),
                    m.getIntervaloUso(),
                    m.getDuracaoDias(),
                    m.isIndeterminado(),
                    horaPrimeiraDose
                );

                for (LocalDateTime horario : horarios) {
                    if (horario.isBefore(LocalDateTime.now())) {
                        boolean tomado = registroDAO.foiTomadoHoje(m.getId(), horario, usuarioId);
                        if (!tomado) {
                            esquecidos.add(new Entrada(m.getNome(), horario.toLocalTime()));
                        }
                    }
                }
            }

            esquecidos.sort((e1, e2) -> e1.horario.compareTo(e2.horario));

            for (Entrada e : esquecidos) {
                modelo.addRow(new Object[]{
                    e.nome,
                    e.horario.toString()
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Erro ao atualizar a tabela de esquecidos: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // rest of your class (event handlers, initComponents etc)

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelHeader = new javax.swing.JPanel();
        btnTelaMedicamento = new javax.swing.JButton();
        meuapp = new javax.swing.JLabel();
        btnTelaPrincipal = new javax.swing.JButton();
        panelTela = new javax.swing.JPanel();
        panelMedicamento = new javax.swing.JPanel();
        medCad = new javax.swing.JLabel();
        btnAtualizar = new javax.swing.JButton();
        btnDeletar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaMedicamentos = new javax.swing.JTable();
        panelPrincipal = new javax.swing.JPanel();
        remediosHj = new javax.swing.JLabel();
        medEsquecidos = new javax.swing.JLabel();
        gerarRelatorio = new javax.swing.JLabel();
        de = new javax.swing.JLabel();
        ate = new javax.swing.JLabel();
        btnGerar = new javax.swing.JButton();
        btnCadastrar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaRemedios = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaEsquecidos = new javax.swing.JTable();
        DataChooserFim = new com.toedter.calendar.JDateChooser();
        DataChooserInicio = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panelHeader.setPreferredSize(new java.awt.Dimension(780, 50));

        btnTelaMedicamento.setText("Medicamentos");
        btnTelaMedicamento.setContentAreaFilled(false);
        btnTelaMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTelaMedicamentoActionPerformed(evt);
            }
        });

        meuapp.setText("MediControl");

        btnTelaPrincipal.setText("Tela Principal");
        btnTelaPrincipal.setContentAreaFilled(false);
        btnTelaPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTelaPrincipalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(meuapp)
                .addGap(133, 133, 133)
                .addComponent(btnTelaPrincipal)
                .addGap(130, 130, 130)
                .addComponent(btnTelaMedicamento)
                .addContainerGap(246, Short.MAX_VALUE))
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(meuapp, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTelaPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTelaMedicamento))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(panelHeader, java.awt.BorderLayout.PAGE_START);

        panelTela.setLayout(new java.awt.CardLayout());

        medCad.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        medCad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        medCad.setText("Medicamentos cadastrados");

        btnAtualizar.setText("Atualizar");
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });

        btnDeletar.setText("Deletar");
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });

        tabelaMedicamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Medicamento", "Dosagem", "Tipo", "Intervalo de uso", "Inicio Tratamento", "Duração (dias)"
            }
        ));
        jScrollPane1.setViewportView(tabelaMedicamentos);

        javax.swing.GroupLayout panelMedicamentoLayout = new javax.swing.GroupLayout(panelMedicamento);
        panelMedicamento.setLayout(panelMedicamentoLayout);
        panelMedicamentoLayout.setHorizontalGroup(
            panelMedicamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMedicamentoLayout.createSequentialGroup()
                .addGap(238, 238, 238)
                .addComponent(btnAtualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 207, Short.MAX_VALUE)
                .addComponent(btnDeletar)
                .addGap(238, 238, 238))
            .addGroup(panelMedicamentoLayout.createSequentialGroup()
                .addGap(293, 293, 293)
                .addComponent(medCad, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(297, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        panelMedicamentoLayout.setVerticalGroup(
            panelMedicamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMedicamentoLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(medCad)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(panelMedicamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAtualizar)
                    .addComponent(btnDeletar))
                .addContainerGap(96, Short.MAX_VALUE))
        );

        panelTela.add(panelMedicamento, "telaMedicamento");

        remediosHj.setText("Remédios | Hoje");

        medEsquecidos.setText("Medicamentos esquecidos");

        gerarRelatorio.setText("Gerar relatório");

        de.setText("De:");

        ate.setText("Até:");

        btnGerar.setText("Gerar");
        btnGerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarActionPerformed(evt);
            }
        });

        btnCadastrar.setText("Cadastrar novo medicamento");
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        tabelaRemedios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Status", "Nome", "Horário"
            }
        ));
        jScrollPane2.setViewportView(tabelaRemedios);

        tabelaEsquecidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nome", "Horário"
            }
        ));
        jScrollPane3.setViewportView(tabelaEsquecidos);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/remediopositivo.png"))); // NOI18N
        jLabel3.setText("jLabel3");
        jLabel3.setPreferredSize(new java.awt.Dimension(60, 60));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/remedionegativo.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/calendario.png"))); // NOI18N

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/casa.png"))); // NOI18N

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(de)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 543, Short.MAX_VALUE)
                        .addComponent(btnCadastrar)
                        .addGap(27, 27, 27))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DataChooserInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(ate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DataChooserFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnGerar)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(184, 184, 184)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(remediosHj)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(medEsquecidos)
                        .addGap(123, 123, 123))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79))))
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gerarRelatorio))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(378, 378, 378)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addGap(132, 132, 132)
                                .addComponent(jLabel1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                                .addGap(138, 138, 138)
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(remediosHj))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7))
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addComponent(medEsquecidos)
                                .addGap(18, 18, 18)))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                                .addComponent(gerarRelatorio)
                                .addGap(8, 8, 8)))
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panelPrincipalLayout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addComponent(ate))
                                .addComponent(btnGerar, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(DataChooserFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(DataChooserInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(de))))
                        .addContainerGap(76, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCadastrar)
                        .addGap(59, 59, 59))))
        );

        panelTela.add(panelPrincipal, "telaPrincipal");

        getContentPane().add(panelTela, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTelaPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTelaPrincipalActionPerformed
    CardLayout cl = (CardLayout) panelTela.getLayout();
    cl.show(panelTela, "telaPrincipal");
    atualizarTabelaRemediosHoje();
    atualizarTabelaRemediosEsquecidos();
    }//GEN-LAST:event_btnTelaPrincipalActionPerformed

    private void btnTelaMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTelaMedicamentoActionPerformed
    CardLayout cl = (CardLayout) panelTela.getLayout();
    cl.show(panelTela, "telaMedicamento");
    carregarTabela();
    }//GEN-LAST:event_btnTelaMedicamentoActionPerformed

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
    TelaCadastro cadastro = new TelaCadastro(this.usuarioId);
    cadastro.setVisible(true);
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed
        int row = tabelaMedicamentos.getSelectedRow();
    if (row >= 0) {
        int id = (int) tabelaMedicamentos.getValueAt(row, 0);
        try {
            MedicamentoDAO dao = new MedicamentoDAO();
            dao.deletar(id, usuarioId);
            carregarTabela();
            JOptionPane.showMessageDialog(this, "Medicamento deletado com sucesso!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar: " + ex.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(this, "Selecione um medicamento para deletar.");
    }
    }//GEN-LAST:event_btnDeletarActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        try {
            int linha = tabelaMedicamentos.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um medicamento para atualizar");
                return;
            }
            
            int id = (int) tabelaMedicamentos.getValueAt(linha, 0);
            String nome = (String) tabelaMedicamentos.getValueAt(linha, 1);
            String dosagem = (String) tabelaMedicamentos.getValueAt(linha, 2);
            String forma = (String) tabelaMedicamentos.getValueAt(linha, 3);
            String intervalo_str = (String) tabelaMedicamentos.getValueAt(linha, 4);
            Object dataObj = tabelaMedicamentos.getValueAt(linha, 5);
            String duracaoStr = (String) tabelaMedicamentos.getValueAt(linha, 6);
            int intervalo = Integer.parseInt(intervalo_str.replaceAll("[^0-9]", ""));
            int duracao = duracaoStr.contains("Indeterminado") ? 0 : Integer.parseInt(duracaoStr.replaceAll("[^0-9]", ""));
            boolean indeterminado = duracaoStr.contains("Indeterminado");
            LocalDate inicioTratamento;
            if (dataObj instanceof LocalDate) {
                inicioTratamento = (LocalDate) dataObj;
            } else {
                inicioTratamento = LocalDate.parse(dataObj.toString());
            }
            String novaDosagem = JOptionPane.showInputDialog(this, "Atualize a dosagem:", dosagem);
            if (novaDosagem == null || novaDosagem.trim().isEmpty()) return;
            String novaForma = JOptionPane.showInputDialog(this, "Atualize a forma:", forma);
            if (novaForma == null || novaForma.trim().isEmpty()) return;
            String novoIntervaloStr = JOptionPane.showInputDialog(this, "Atualize o intervalo (horas):", intervalo);
            if (novoIntervaloStr == null || novoIntervaloStr.trim().isEmpty()) return;
            int novoIntervalo = Integer.parseInt(novoIntervaloStr);
            Medicamento m = new Medicamento();
            String dataStr = JOptionPane.showInputDialog(this, "Atualize a data de início (dd/MM/yyyy):", 
            inicioTratamento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            if (dataStr == null || dataStr.trim().isEmpty()) return;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate novaData = LocalDate.parse(dataStr, formatter);
             String novaDuracaoStr = JOptionPane.showInputDialog(this,
            "Atualize a duração do remédio ou digite 'Indeterminado':",
            duracaoStr);
            if (novaDuracaoStr == null || novaDuracaoStr.trim().isEmpty()) return;
            Integer novaDuracao = null;
            if (novaDuracaoStr.equalsIgnoreCase("Indeterminado")) {
                indeterminado = true;
            } else {
            try {
            novaDuracao = Integer.parseInt(novaDuracaoStr.replaceAll("[^0-9]", ""));
            } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Duração inválida! Digite o número de dias ou 'Indeterminado'");
            return;
            }
    }

            m.setId(id);
            m.setNome(nome);
            m.setDosagem(novaDosagem);
            m.setForma(novaForma);
            m.setIntervaloUso(novoIntervalo);
            m.setInicioTratamento(inicioTratamento);
             m.setDuracaoDias(indeterminado ? null : novaDuracao);
            m.setIndeterminado(indeterminado);
            m.setInicioTratamento(novaData);

            MedicamentoDAO dao = new MedicamentoDAO();
            boolean atualizado = dao.atualizar(m, usuarioId);
            if (atualizado) {
                carregarTabela();
                JOptionPane.showMessageDialog(this, "Medicamento atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Nenhuma alteração realizada");
            }   } catch (SQLException ex) {
            Logger.getLogger(Telas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void btnGerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarActionPerformed
    if (DataChooserInicio.getDate() == null || DataChooserFim.getDate() == null) {
        JOptionPane.showMessageDialog(this, "Selecione a data inicial e final.");
        return;
    }

    LocalDate dataInicio = DataChooserInicio.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate dataFim = DataChooserFim.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            URL url = getClass().getResource("/resources/computador.png"); // ajuste o caminho se necessário
       if (url == null) {
           System.err.println("❌ Imagem não encontrada!");
           return;
       }
       ImageIcon icone = new ImageIcon(url);
       Image imagemRedimensionada = icone.getImage().getScaledInstance(85, 85, Image.SCALE_SMOOTH);
       ImageIcon iconeAlterado = new ImageIcon(imagemRedimensionada);
              JOptionPane.showMessageDialog(this, 
           "O relatório gerado terá duas seções:\n" +
           "- Remédios Tomados: lista de todos os medicamentos marcados como tomados no período selecionado\n" +
           "- Remédios Esquecidos: lista dos medicamentos que foram esquecidos no período selecionado\n\n" +
           "Você poderá salvar o relatório em um arquivo .txt.",
           "Informação",
           JOptionPane.INFORMATION_MESSAGE,
           iconeAlterado
       );

    export.RelatorioMedicamentosExportador.exportar(dataInicio, dataFim, usuarioId);
    }//GEN-LAST:event_btnGerarActionPerformed
    private void designTelaInicial() {
        Estilo.estiloAppTelaInicial(meuapp);
        Estilo.botaoMenu(btnTelaPrincipal, "Tela Principal");
        Estilo.botaoMenu(btnTelaMedicamento, "Medicamentos");
        Estilo.designTelaInicial(panelPrincipal);
        Estilo.designMenu(panelHeader);
        Estilo.designTelaInicial(panelMedicamento);
        Estilo.tituloTI(de, "De:");
        Estilo.tituloTI(ate, "Até:");
        Estilo.tituloTI(remediosHj, "Remédios | Hoje");
        Estilo.tituloTI(medEsquecidos, "Medicamentos Esquecidos");
        Estilo.tituloTI(gerarRelatorio, "Gerar Relatório");
        Estilo.botoes(btnGerar);
        Estilo.botoes(btnCadastrar);
        Estilo.fonteTabela(tabelaRemedios, "Status");
        Estilo.fonteTabela(tabelaEsquecidos, "Nome");
        Estilo.titulo(medCad, "Medicamentos Cadastrados");
        Estilo.botoes(btnAtualizar);
        Estilo.botoes(btnDeletar);
        Estilo.fonteTabela(tabelaMedicamentos, "ID");
        
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> {
            new TelaUsuario().setVisible(true); // só abre a tela de login aqui
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DataChooserFim;
    private com.toedter.calendar.JDateChooser DataChooserInicio;
    private javax.swing.JLabel ate;
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnDeletar;
    private javax.swing.JButton btnGerar;
    private javax.swing.JButton btnTelaMedicamento;
    private javax.swing.JButton btnTelaPrincipal;
    private javax.swing.JLabel de;
    private javax.swing.JLabel gerarRelatorio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel medCad;
    private javax.swing.JLabel medEsquecidos;
    private javax.swing.JLabel meuapp;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelMedicamento;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPanel panelTela;
    private javax.swing.JLabel remediosHj;
    private javax.swing.JTable tabelaEsquecidos;
    private javax.swing.JTable tabelaMedicamentos;
    private javax.swing.JTable tabelaRemedios;
    // End of variables declaration//GEN-END:variables
}
