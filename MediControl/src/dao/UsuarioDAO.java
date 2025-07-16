package dao;

import factory.ConnectionFactory;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UsuarioDAO {
    private Connection conexao;

    public UsuarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public boolean cadastrarUsuario(String nome, String telefone, String email, Date dataNasc, String senha) throws SQLException {
        String sql = "INSERT INTO usuario (nome, telefone, email, data_nasc, senha) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setString(2, telefone);
            ps.setString(3, email);
            ps.setDate(4, dataNasc);
            ps.setString(5, hashSenha(senha));
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    public int validarLogin(String email, String senha) throws SQLException {
        String sql = "SELECT id FROM usuario WHERE email = ? AND senha = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, hashSenha(senha)); // comparar senha hasheada
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }

    private String hashSenha(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(senha.getBytes());
            StringBuilder sb = new StringBuilder();
            for(byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
        public Integer autenticar(String email, String senha) throws SQLException {
        String sql = "SELECT id FROM usuario WHERE email = ? AND senha = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, email);
            stmt.setString(2, senha); 
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");  
                } else {
                    return null; 
                }
            }
        }
    }
}