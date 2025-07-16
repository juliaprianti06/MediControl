package gui;

import dao.MedicamentoDAO;
import design.Estilo;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JOptionPane;
import modelo.Medicamento;

public class TelaCadastro extends javax.swing.JFrame {

    private int usuarioId;

    public TelaCadastro(int usuarioId) {
        this.usuarioId = usuarioId;
        
        initComponents();
        setResizable(false);
        designCadastroMedicamento();
        comboUnidade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "mg", "ml", "g", "unid" }));
        comboForma.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Comprimido", "Gotas", "Injeção", "Xarope", "Pomada" }));
        
    }

private void limparCampos() {
    txtNome.setText("");
    txtDosagem.setText("");
    comboUnidade.setSelectedIndex(0);
    comboForma.setSelectedIndex(0);
    txtIntervalo.setText("");
    txtDuracao.setText("");
    checkIndeterminado.setSelected(false);
    txtPrDose.setText("");
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelMedicamento = new javax.swing.JPanel();
        cadastroMed = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        nomeMed = new javax.swing.JLabel();
        dosagemMed = new javax.swing.JLabel();
        txtDosagem = new javax.swing.JTextField();
        comboUnidade = new javax.swing.JComboBox<>();
        formaMed = new javax.swing.JLabel();
        comboForma = new javax.swing.JComboBox<>();
        intervaloMed = new javax.swing.JLabel();
        txtIntervalo = new javax.swing.JTextField();
        tratamentoMed = new javax.swing.JLabel();
        duracaoMed = new javax.swing.JLabel();
        txtDuracao = new javax.swing.JTextField();
        checkIndeterminado = new javax.swing.JCheckBox();
        btnSalvar = new javax.swing.JButton();
        horarioMed = new javax.swing.JLabel();
        txtPrDose = new javax.swing.JFormattedTextField();
        dataInicio = new com.toedter.calendar.JDateChooser();
        panelCabecalho = new javax.swing.JPanel();
        labelMedicontrol1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        cadastroMed.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cadastroMed.setText("Cadastrar Novo Medicamento");

        txtNome.setMaximumSize(new java.awt.Dimension(34344, 3434));

        nomeMed.setText("Nome do Medicamento :");

        dosagemMed.setText("Dosagem :");

        comboUnidade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        formaMed.setText("Forma :");

        comboForma.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        intervaloMed.setText("Intervalo de uso :");

        txtIntervalo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIntervaloActionPerformed(evt);
            }
        });

        tratamentoMed.setText("Início do tratamento :");

        duracaoMed.setText("Duração ( em dias ) :");

        checkIndeterminado.setText("Indeterminado");

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        horarioMed.setText("Horário primeira dose :");

        txtPrDose.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));

        dataInicio.setDateFormatString("dd/MM/yyyy");

        panelCabecalho.setPreferredSize(new java.awt.Dimension(500, 65));

        labelMedicontrol1.setText("MediControl");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/logo.png"))); // NOI18N
        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout panelCabecalhoLayout = new javax.swing.GroupLayout(panelCabecalho);
        panelCabecalho.setLayout(panelCabecalhoLayout);
        panelCabecalhoLayout.setHorizontalGroup(
            panelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCabecalhoLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelMedicontrol1)
                .addGap(27, 27, 27)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(290, Short.MAX_VALUE))
        );
        panelCabecalhoLayout.setVerticalGroup(
            panelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCabecalhoLayout.createSequentialGroup()
                .addGroup(panelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelCabecalhoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelMedicontrol1, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)))
                    .addGroup(panelCabecalhoLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout painelMedicamentoLayout = new javax.swing.GroupLayout(painelMedicamento);
        painelMedicamento.setLayout(painelMedicamentoLayout);
        painelMedicamentoLayout.setHorizontalGroup(
            painelMedicamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCabecalho, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelMedicamentoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalvar)
                .addGap(85, 85, 85))
            .addGroup(painelMedicamentoLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(painelMedicamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cadastroMed)
                    .addGroup(painelMedicamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(painelMedicamentoLayout.createSequentialGroup()
                            .addComponent(duracaoMed)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtDuracao, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(34, 34, 34)
                            .addComponent(checkIndeterminado))
                        .addGroup(painelMedicamentoLayout.createSequentialGroup()
                            .addComponent(tratamentoMed)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(dataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(painelMedicamentoLayout.createSequentialGroup()
                            .addComponent(horarioMed)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtPrDose, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(intervaloMed)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtIntervalo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(painelMedicamentoLayout.createSequentialGroup()
                            .addComponent(formaMed)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(comboForma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(painelMedicamentoLayout.createSequentialGroup()
                            .addComponent(dosagemMed)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtDosagem, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(comboUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(painelMedicamentoLayout.createSequentialGroup()
                            .addComponent(nomeMed)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(106, Short.MAX_VALUE))
        );
        painelMedicamentoLayout.setVerticalGroup(
            painelMedicamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelMedicamentoLayout.createSequentialGroup()
                .addComponent(panelCabecalho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(cadastroMed)
                .addGap(43, 43, 43)
                .addGroup(painelMedicamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomeMed)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelMedicamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dosagemMed)
                    .addComponent(txtDosagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelMedicamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboForma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(formaMed))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelMedicamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(horarioMed)
                    .addComponent(txtPrDose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(intervaloMed, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIntervalo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelMedicamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelMedicamentoLayout.createSequentialGroup()
                        .addComponent(tratamentoMed)
                        .addGap(12, 12, 12)
                        .addGroup(painelMedicamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(duracaoMed)
                            .addComponent(txtDuracao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(checkIndeterminado))
                        .addGap(30, 30, 30)
                        .addComponent(btnSalvar))
                    .addComponent(dataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelMedicamento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelMedicamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
    try {
        
        String nome = txtNome.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome do medicamento.");
            txtNome.requestFocus();
            return;
        }

        String dosagem = txtDosagem.getText().trim();
        if (dosagem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a dosagem do medicamento.");
            txtDosagem.requestFocus();
            return;
        }

        String unidade = (String) comboUnidade.getSelectedItem();
        String forma = (String) comboForma.getSelectedItem();
        if (unidade == null || unidade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione a unidade do medicamento.");
            comboUnidade.requestFocus();
            return;
        }
        if (forma == null || forma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione a forma do medicamento.");
            comboForma.requestFocus();
            return;
        }

        int intervaloUso;
        try {
            intervaloUso = Integer.parseInt(txtIntervalo.getText().trim());
            if (intervaloUso <= 0) {
                JOptionPane.showMessageDialog(this, "O intervalo de uso deve ser um número positivo.");
                txtIntervalo.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Informe um número válido para o intervalo de uso.");
            txtIntervalo.requestFocus();
            return;
        }
         Date dataSelecionada = dataInicio.getDate(); 

            if (dataSelecionada == null) {
                JOptionPane.showMessageDialog(this, "Informe uma data de início válida.");
                dataInicio.requestFocus();
                return;
            }
            

            LocalDate data = dataSelecionada.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalTime horaPrimeiraDose;
        try {
            String horarioTexto = txtPrDose.getText().trim();
            if (horarioTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o horário da primeira dose.");
                txtPrDose.requestFocus();
                return;
            }
            horaPrimeiraDose = LocalTime.parse(horarioTexto, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Formato do horário da primeira dose inválido. Use HH:mm.");
            txtPrDose.requestFocus();
            return;
        }

        Integer duracaoDias = null;
        boolean indeterminado = checkIndeterminado.isSelected();
        if (!indeterminado) {
            try {
                duracaoDias = Integer.parseInt(txtDuracao.getText().trim());
                if (duracaoDias <= 0) {
                    JOptionPane.showMessageDialog(this, "A duração deve ser um número positivo.");
                    txtDuracao.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Informe uma duração válida ou marque 'Indeterminado'.");
                txtDuracao.requestFocus();
                return;
            }
        }

        Medicamento m = new Medicamento();
        m.setNome(nome);
        m.setDosagem(dosagem);
        m.setUnidade(unidade);
        m.setForma(forma);
        m.setIntervaloUso(intervaloUso);
        m.setInicioTratamento(data);
        m.setHoraPrimeiraDose(horaPrimeiraDose);
        m.setIndeterminado(indeterminado);
        m.setDuracaoDias(duracaoDias);

        MedicamentoDAO dao = new MedicamentoDAO();
        dao.salvar(m, usuarioId);

        JOptionPane.showMessageDialog(this, "Medicamento salvo com sucesso!");
        limparCampos();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
    }

    

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void txtIntervaloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIntervaloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIntervaloActionPerformed
    private void designCadastroMedicamento(){
        Estilo.titulo(cadastroMed, "Cadastrar Novo Medicamento");
        Estilo.dados(nomeMed, "Nome:");
        Estilo.dados(dosagemMed, "Dosagem:");
        Estilo.dados(formaMed, "Forma:");
        Estilo.dados(horarioMed, "Horário:");
        Estilo.dados(tratamentoMed, "Inicio do tratamento:");
        Estilo.dados(duracaoMed, "Duração (em dias):");
        Estilo.dados(intervaloMed, "Intervalo de uso:");
        Estilo.botoes(btnSalvar);
        Estilo.estiloApp(labelMedicontrol1);
        Estilo.cabecalho(panelCabecalho);
        
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel cadastroMed;
    private javax.swing.JCheckBox checkIndeterminado;
    private javax.swing.JComboBox<String> comboForma;
    private javax.swing.JComboBox<String> comboUnidade;
    private com.toedter.calendar.JDateChooser dataInicio;
    private javax.swing.JLabel dosagemMed;
    private javax.swing.JLabel duracaoMed;
    private javax.swing.JLabel formaMed;
    private javax.swing.JLabel horarioMed;
    private javax.swing.JLabel intervaloMed;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel labelMedicontrol1;
    private javax.swing.JLabel nomeMed;
    private javax.swing.JPanel painelMedicamento;
    private javax.swing.JPanel panelCabecalho;
    private javax.swing.JLabel tratamentoMed;
    private javax.swing.JTextField txtDosagem;
    private javax.swing.JTextField txtDuracao;
    private javax.swing.JTextField txtIntervalo;
    private javax.swing.JTextField txtNome;
    private javax.swing.JFormattedTextField txtPrDose;
    // End of variables declaration//GEN-END:variables
}
