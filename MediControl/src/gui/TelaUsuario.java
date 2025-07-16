
package gui;

import dao.UsuarioDAO;
import design.Estilo;
import factory.ConnectionFactory;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.sql.Connection;
import javax.swing.BorderFactory;
import javax.swing.UIManager;




public class TelaUsuario extends javax.swing.JFrame {

     private UsuarioDAO usuarioDAO;
     private CardLayout cardLayout;

    public TelaUsuario() {
        initComponents();
        designTelaUsuario();
        setResizable(false);
        CardLayout cl = (CardLayout) panelLogin.getLayout();
        cl.show(panelLogin, "login");

        try {
            Connection conn = ConnectionFactory.getConnection();
            usuarioDAO = new UsuarioDAO(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.");
        }
    }
    private void tentarLogin() {
        String email = textEmail.getText();
        char[] senhaChars = textSenha.getPassword();
        String senha = new String(senhaChars);

        try {
            int usuarioId = usuarioDAO.validarLogin(email, senha);
            if (usuarioId > 0) {
                JOptionPane.showMessageDialog(this, "Login bem sucedido!");
                new Telas(usuarioId).setVisible(true); 
                this.dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Email ou senha inválidos.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao validar login.");
        }
    }


    private void tentarCadastro() {
        String nome = textNomeC.getText();
        String telefone = textTelC.getText();
        String email = textEmailC.getText();
        java.util.Date dataNascUtil = textDataNascC.getDate();
        java.sql.Date dataNasc = new java.sql.Date(dataNascUtil.getTime());
        char[] senhaChars = textSenhaC.getPassword();
        char[] confirmaChars = textConfirmaC.getPassword();
        String senha = new String(senhaChars);
        String confirma = new String(confirmaChars);

        if (!senha.equals(confirma)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem!");
            return;
        }

        try {
            if (usuarioDAO.cadastrarUsuario(nome, telefone, email, dataNasc, senha)) {
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
                // volta para tela de login, por exemplo:
                cardLayout.show(panelLogin, "login");
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar usuário.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar usuário.");
        }
    }
    
    private void limparCamposCadastro() {
    textNomeC.setText("");
    textTelC.setText("");
    textEmailC.setText("");
    textDataNascC.setDate(null);
    textSenhaC.setText("");
    textConfirmaC.setText("");
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        panelCabecalho = new javax.swing.JPanel();
        labelMedicontrol = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panelEntreCadastre = new javax.swing.JPanel();
        btnSignin = new javax.swing.JButton();
        btnSignup = new javax.swing.JButton();
        panelLogin = new javax.swing.JPanel();
        panelSignup = new javax.swing.JPanel();
        nomeUser = new javax.swing.JLabel();
        textNomeC = new javax.swing.JTextField();
        telUser = new javax.swing.JLabel();
        textTelC = new javax.swing.JTextField();
        emailUser = new javax.swing.JLabel();
        textEmailC = new javax.swing.JTextField();
        dataNascUser = new javax.swing.JLabel();
        senhaUser = new javax.swing.JLabel();
        senhCoUser = new javax.swing.JLabel();
        textSenhaC = new javax.swing.JPasswordField();
        textConfirmaC = new javax.swing.JPasswordField();
        btnCadastrarUsuario = new javax.swing.JButton();
        CadastroUser = new javax.swing.JLabel();
        textDataNascC = new com.toedter.calendar.JDateChooser();
        panelSignin = new javax.swing.JPanel();
        bemVindo = new javax.swing.JLabel();
        textoUser = new javax.swing.JLabel();
        textoUser2 = new javax.swing.JLabel();
        emailEntrar = new javax.swing.JLabel();
        textEmail = new javax.swing.JTextField();
        senhaEntrar = new javax.swing.JLabel();
        btnEntrar = new javax.swing.JButton();
        entrarTexto = new javax.swing.JLabel();
        textSenha = new javax.swing.JPasswordField();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelCabecalho.setPreferredSize(new java.awt.Dimension(500, 65));

        labelMedicontrol.setText("MediControl");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/logo.png"))); // NOI18N
        jLabel2.setPreferredSize(new java.awt.Dimension(60, 60));

        javax.swing.GroupLayout panelCabecalhoLayout = new javax.swing.GroupLayout(panelCabecalho);
        panelCabecalho.setLayout(panelCabecalhoLayout);
        panelCabecalhoLayout.setHorizontalGroup(
            panelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCabecalhoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelMedicontrol)
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(290, Short.MAX_VALUE))
        );
        panelCabecalhoLayout.setVerticalGroup(
            panelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
            .addGroup(panelCabecalhoLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(panelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelMedicontrol, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(panelCabecalho, java.awt.BorderLayout.PAGE_START);

        panelEntreCadastre.setPreferredSize(new java.awt.Dimension(500, 75));

        btnSignin.setText("Tela Inicial");
        btnSignin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSigninActionPerformed(evt);
            }
        });

        btnSignup.setText("Cadastrar");
        btnSignup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEntreCadastreLayout = new javax.swing.GroupLayout(panelEntreCadastre);
        panelEntreCadastre.setLayout(panelEntreCadastreLayout);
        panelEntreCadastreLayout.setHorizontalGroup(
            panelEntreCadastreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEntreCadastreLayout.createSequentialGroup()
                .addContainerGap(281, Short.MAX_VALUE)
                .addComponent(btnSignin)
                .addGap(18, 18, 18)
                .addComponent(btnSignup)
                .addGap(34, 34, 34))
        );
        panelEntreCadastreLayout.setVerticalGroup(
            panelEntreCadastreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEntreCadastreLayout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(panelEntreCadastreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSignup)
                    .addComponent(btnSignin))
                .addGap(23, 23, 23))
        );

        getContentPane().add(panelEntreCadastre, java.awt.BorderLayout.PAGE_END);

        panelLogin.setPreferredSize(new java.awt.Dimension(500, 350));
        panelLogin.setLayout(new java.awt.CardLayout());

        panelSignup.setPreferredSize(new java.awt.Dimension(500, 350));
        panelSignup.setRequestFocusEnabled(false);

        nomeUser.setText("Nome : ");

        telUser.setText("Telefone :");

        emailUser.setText("Email :");

        dataNascUser.setText("Data de Nascimento :");

        senhaUser.setText("Senha :");

        senhCoUser.setText("Confirme sua senha :");

        textConfirmaC.setPreferredSize(new java.awt.Dimension(115, 22));

        btnCadastrarUsuario.setText("Cadastrar");
        btnCadastrarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarUsuarioActionPerformed(evt);
            }
        });

        CadastroUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        CadastroUser.setText("Cadastrar Novo Usuário");

        textDataNascC.setDateFormatString("dd/MM/yyyy");

        javax.swing.GroupLayout panelSignupLayout = new javax.swing.GroupLayout(panelSignup);
        panelSignup.setLayout(panelSignupLayout);
        panelSignupLayout.setHorizontalGroup(
            panelSignupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSignupLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(panelSignupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSignupLayout.createSequentialGroup()
                        .addGroup(panelSignupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelSignupLayout.createSequentialGroup()
                                .addComponent(senhCoUser)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textConfirmaC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelSignupLayout.createSequentialGroup()
                                .addComponent(senhaUser)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textSenhaC, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelSignupLayout.createSequentialGroup()
                        .addGroup(panelSignupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelSignupLayout.createSequentialGroup()
                                .addComponent(dataNascUser)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textDataNascC, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelSignupLayout.createSequentialGroup()
                                .addComponent(nomeUser)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textNomeC, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelSignupLayout.createSequentialGroup()
                                .addComponent(telUser)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textTelC, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelSignupLayout.createSequentialGroup()
                                .addComponent(emailUser)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textEmailC, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(panelSignupLayout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addComponent(CadastroUser)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSignupLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCadastrarUsuario)
                .addGap(71, 71, 71))
        );
        panelSignupLayout.setVerticalGroup(
            panelSignupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSignupLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(CadastroUser, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(panelSignupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomeUser)
                    .addComponent(textNomeC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSignupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(telUser)
                    .addComponent(textTelC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSignupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailUser)
                    .addComponent(textEmailC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSignupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textDataNascC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataNascUser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSignupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(senhaUser)
                    .addComponent(textSenhaC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSignupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(senhCoUser)
                    .addComponent(textConfirmaC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(btnCadastrarUsuario)
                .addContainerGap())
        );

        panelLogin.add(panelSignup, "cadastro");

        panelSignin.setPreferredSize(new java.awt.Dimension(500, 350));
        panelSignin.setRequestFocusEnabled(false);

        bemVindo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bemVindo.setText("Bem vindo ao MediControl!");

        textoUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoUser.setText("Cadastre tratamentos, registre horários e mantenha seu controle sempre em dia");

        textoUser2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoUser2.setText("Organize e acompanhe o uso dos seus medicamentos com facilidade.");

        emailEntrar.setText("Email :");

        textEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textEmailActionPerformed(evt);
            }
        });

        senhaEntrar.setText("Senha:");

        btnEntrar.setText("Entrar");
        btnEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarActionPerformed(evt);
            }
        });

        entrarTexto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        entrarTexto.setText("Entrar");

        textSenha.setMinimumSize(new java.awt.Dimension(64, 24));
        textSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSenhaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSigninLayout = new javax.swing.GroupLayout(panelSignin);
        panelSignin.setLayout(panelSigninLayout);
        panelSigninLayout.setHorizontalGroup(
            panelSigninLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSigninLayout.createSequentialGroup()
                .addGroup(panelSigninLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSigninLayout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(senhaEntrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSigninLayout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(emailEntrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSigninLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEntrar)
                .addGap(115, 115, 115))
            .addGroup(panelSigninLayout.createSequentialGroup()
                .addGroup(panelSigninLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSigninLayout.createSequentialGroup()
                        .addGap(149, 149, 149)
                        .addComponent(bemVindo))
                    .addGroup(panelSigninLayout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(entrarTexto))
                    .addGroup(panelSigninLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(textoUser2, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSigninLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(textoUser, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        panelSigninLayout.setVerticalGroup(
            panelSigninLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSigninLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(bemVindo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textoUser2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textoUser, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(entrarTexto)
                .addGap(18, 18, 18)
                .addGroup(panelSigninLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailEntrar)
                    .addComponent(textEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSigninLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(senhaEntrar)
                    .addComponent(textSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnEntrar)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        panelLogin.add(panelSignin, "login");

        getContentPane().add(panelLogin, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarActionPerformed
    tentarLogin();
    }//GEN-LAST:event_btnEntrarActionPerformed
    
    private void btnSignupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignupActionPerformed
    CardLayout cardLayout = (CardLayout) panelLogin.getLayout();
    cardLayout.show(panelLogin, "cadastro"); 
    }//GEN-LAST:event_btnSignupActionPerformed

    private void btnCadastrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarUsuarioActionPerformed
        try {
        String nome = textNomeC.getText();
        String telefone = textTelC.getText();
        String email = textEmailC.getText();
        String senha = new String(textSenhaC.getPassword());
        String confirmaSenha = new String(textConfirmaC.getPassword());

        if (!senha.equals(confirmaSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem.");
            return;
        }

     
        java.util.Date utilDate = (java.util.Date) textDataNascC.getDate();
        if (utilDate == null) {
            JOptionPane.showMessageDialog(this, "Informe uma data de nascimento válida.");
            return;
        }
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

       
        Connection conexao = factory.ConnectionFactory.getConnection();
        UsuarioDAO dao = new UsuarioDAO(conexao);

        boolean sucesso = dao.cadastrarUsuario(nome, telefone, email, sqlDate, senha);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            limparCamposCadastro();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar o usuário.");
        }

        conexao.close();
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
    }

    }//GEN-LAST:event_btnCadastrarUsuarioActionPerformed

    private void textEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textEmailActionPerformed

    private void textSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSenhaActionPerformed

    private void btnSigninActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSigninActionPerformed
        CardLayout cardLayout = (CardLayout) panelLogin.getLayout();
        cardLayout.show(panelLogin, "login");
    }//GEN-LAST:event_btnSigninActionPerformed
     private void designTelaUsuario() {
        Estilo.designUsuario(panelSignup);
        Estilo.designUsuario(panelSignin);
        Estilo.dados(nomeUser, "Nome:");
        Estilo.dados(telUser, "Telefone:");
        Estilo.dados(emailUser, "Email:");
        Estilo.dados(dataNascUser, "Data de Nascimento:");
        Estilo.dados(senhaUser, "Senha:");
        Estilo.dados(senhCoUser, "Confirme sua senha:");
        Estilo.dados(emailEntrar, "Email:");
        Estilo.dados(senhaEntrar, "Senha:");
        Estilo.titulo(CadastroUser, "Cadastrar Novo Usuário");
        Estilo.titulo(bemVindo, "Bem Vindo ao MediControl!");
        Estilo.titulo(entrarTexto, "Faça o login");
        Estilo.estiloApp(labelMedicontrol);
        Estilo.cabecalho(panelCabecalho);
        Estilo.botoes(btnCadastrarUsuario);
        Estilo.botoes(btnEntrar);
        Estilo.rodape(panelEntreCadastre);
        Estilo.botoes(btnSignup, "Criar uma conta");
        Estilo.botoes(btnSignin, "Tela Inicial");
        Estilo.texto(textoUser2, "Organize e acompanhe o uso dos seus medicamentos com facilidade.");
        Estilo.texto(textoUser, "Cadastre tratamentos, registre horários e mantenha seu controle sempre em dia");
        
     }
   
public static void main(String args[]) {
    try {
        UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    java.awt.EventQueue.invokeLater(() -> {
        new TelaUsuario().setVisible(true);
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CadastroUser;
    private javax.swing.JLabel bemVindo;
    private javax.swing.JButton btnCadastrarUsuario;
    private javax.swing.JButton btnEntrar;
    private javax.swing.JButton btnSignin;
    private javax.swing.JButton btnSignup;
    private javax.swing.JLabel dataNascUser;
    private javax.swing.JLabel emailEntrar;
    private javax.swing.JLabel emailUser;
    private javax.swing.JLabel entrarTexto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel labelMedicontrol;
    private javax.swing.JLabel nomeUser;
    private javax.swing.JPanel panelCabecalho;
    private javax.swing.JPanel panelEntreCadastre;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPanel panelSignin;
    private javax.swing.JPanel panelSignup;
    private javax.swing.JLabel senhCoUser;
    private javax.swing.JLabel senhaEntrar;
    private javax.swing.JLabel senhaUser;
    private javax.swing.JLabel telUser;
    private javax.swing.JPasswordField textConfirmaC;
    private com.toedter.calendar.JDateChooser textDataNascC;
    private javax.swing.JTextField textEmail;
    private javax.swing.JTextField textEmailC;
    private javax.swing.JTextField textNomeC;
    private javax.swing.JPasswordField textSenha;
    private javax.swing.JPasswordField textSenhaC;
    private javax.swing.JTextField textTelC;
    private javax.swing.JLabel textoUser;
    private javax.swing.JLabel textoUser2;
    // End of variables declaration//GEN-END:variables
}
