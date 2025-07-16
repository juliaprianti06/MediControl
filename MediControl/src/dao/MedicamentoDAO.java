package dao;

import factory.ConnectionFactory;
import modelo.Medicamento;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDAO {

    // Salvar com usuarioId
    public void salvar(Medicamento m, int usuarioId) throws SQLException {
        String sql = "INSERT INTO Medicamento (nome, dosagem, unidade, forma, intervalo_uso, inicio_tratamento, duracao_dias, indeterminado, hora_primeira_dose, usuario_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getNome());
            stmt.setString(2, m.getDosagem());
            stmt.setString(3, m.getUnidade());
            stmt.setString(4, m.getForma());
            stmt.setInt(5, m.getIntervaloUso());
            stmt.setDate(6, Date.valueOf(m.getInicioTratamento()));

            if (m.isIndeterminado()) {
                stmt.setNull(7, Types.INTEGER);
            } else {
                stmt.setInt(7, m.getDuracaoDias());
            }

            stmt.setBoolean(8, m.isIndeterminado());

            if (m.getHoraPrimeiraDose() != null) {
                stmt.setTime(9, Time.valueOf(m.getHoraPrimeiraDose()));
            } else {
                stmt.setNull(9, Types.TIME);
            }

            stmt.setInt(10, usuarioId);

            stmt.executeUpdate();
        }
    }

    // Buscar por nome e usuarioId
    public Medicamento buscarPorNome(String nome, int usuarioId) throws SQLException {
        String sql = "SELECT * FROM Medicamento WHERE nome = ? AND usuario_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setInt(2, usuarioId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirMedicamento(rs);
                }
            }
        }

        return null;
    }

    // Buscar por id e usuarioId
    public Medicamento buscarPorId(int id, int usuarioId) throws SQLException {
        String sql = "SELECT * FROM Medicamento WHERE id = ? AND usuario_id = ?";
        Medicamento m = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setInt(2, usuarioId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    m = construirMedicamento(rs);
                }
            }
        }

        return m;
    }

    // Listar todos medicamentos de um usuario
    public List<Medicamento> listarTodos(int usuarioId) throws SQLException {
        List<Medicamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM Medicamento WHERE usuario_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Medicamento m = construirMedicamento(rs);
                    lista.add(m);
                }
            }
        }

        return lista;
    }

    // Deletar medicamento por id e usuarioId para garantir que só deleta do usuário correto
    public void deletar(int id, int usuarioId) throws SQLException {
        String sql = "DELETE FROM Medicamento WHERE id = ? AND usuario_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setInt(2, usuarioId);
            stmt.executeUpdate();
        }
    }

    // Atualizar medicamento: também validar usuarioId para garantir segurança
    public boolean atualizar(Medicamento m, int usuarioId) throws SQLException {
        String sql = "UPDATE Medicamento SET nome = ?, dosagem = ?, unidade = ?, forma = ?, intervalo_uso = ?, inicio_tratamento = ?, duracao_dias = ?, indeterminado = ?, hora_primeira_dose = ? WHERE id = ? AND usuario_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getNome());
            stmt.setString(2, m.getDosagem());
            stmt.setString(3, m.getUnidade());
            stmt.setString(4, m.getForma());
            stmt.setInt(5, m.getIntervaloUso());
            stmt.setDate(6, Date.valueOf(m.getInicioTratamento()));

            if (m.isIndeterminado()) {
                stmt.setNull(7, Types.INTEGER);
            } else {
                stmt.setInt(7, m.getDuracaoDias());
            }

            stmt.setBoolean(8, m.isIndeterminado());

            if (m.getHoraPrimeiraDose() != null) {
                stmt.setTime(9, Time.valueOf(m.getHoraPrimeiraDose()));
            } else {
                stmt.setNull(9, Types.TIME);
            }

            stmt.setInt(10, m.getId());
            stmt.setInt(11, usuarioId);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        }
    }

    private Medicamento construirMedicamento(ResultSet rs) throws SQLException {
        Medicamento m = new Medicamento();
        m.setId(rs.getInt("id"));
        m.setNome(rs.getString("nome"));
        m.setDosagem(rs.getString("dosagem"));
        m.setUnidade(rs.getString("unidade"));
        m.setForma(rs.getString("forma"));
        m.setIntervaloUso(rs.getInt("intervalo_uso"));
        m.setInicioTratamento(rs.getDate("inicio_tratamento").toLocalDate());
        m.setDuracaoDias(rs.getObject("duracao_dias", Integer.class));
        m.setIndeterminado(rs.getBoolean("indeterminado"));

        Time hora = rs.getTime("hora_primeira_dose");
        if (hora != null) {
            m.setHoraPrimeiraDose(hora.toLocalTime());
        }

        return m;
    }
}