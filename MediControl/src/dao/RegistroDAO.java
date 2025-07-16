package dao;

import factory.ConnectionFactory;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import modelo.Medicamento;
import modelo.Registro;

public class RegistroDAO {

    
    public List<Registro> listarRegistrosDoDia(LocalDate dia, String status, int usuarioId) throws SQLException {
        String sql = "SELECT r.id, r.horario, r.status, " +
                     "m.id as med_id, m.nome, m.dosagem, m.unidade, m.forma, " +
                     "m.intervalo_uso, m.inicio_tratamento, m.duracao_dias, m.indeterminado, m.hora_primeira_dose " +
                     "FROM Registro r " +
                     "JOIN Medicamento m ON r.medicamento_id = m.id " +
                     "WHERE DATE(r.horario) = ? AND r.status = ? AND r.usuario_id = ?";

        List<Registro> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(dia));
            stmt.setString(2, status);
            stmt.setInt(3, usuarioId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Medicamento m = new Medicamento();
                    m.setId(rs.getInt("med_id"));
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

                    Timestamp horario = rs.getTimestamp("horario");
                    String statusRegistro = rs.getString("status");
                    int idRegistro = rs.getInt("id");

                    lista.add(new Registro(idRegistro, m, horario.toLocalDateTime(), statusRegistro));
                }
            }
        }

        return lista;
    }

    public List<Registro> listarPeriodo(LocalDate inicio, LocalDate fim, String status, int usuarioId) throws SQLException {
    String sql = """
           SELECT r.id, r.horario, r.status, 
            m.id as med_id, m.nome, m.dosagem, m.unidade, m.forma,
            m.intervalo_uso, m.inicio_tratamento, m.duracao_dias, m.indeterminado, m.hora_primeira_dose
            FROM Registro r JOIN Medicamento m ON r.medicamento_id = m.id
            WHERE r.horario >= ? AND r.horario < ?
            AND r.status = ?
            AND r.usuario_id = ?""";

    List<Registro> lista = new ArrayList<>();

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setTimestamp(1, Timestamp.valueOf(inicio.atStartOfDay()));
        stmt.setTimestamp(2, Timestamp.valueOf(fim.plusDays(1).atStartOfDay()));
        stmt.setString(3, status);
        stmt.setInt(4, usuarioId);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Medicamento m = new Medicamento();
                m.setId(rs.getInt("med_id"));
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

                Registro registro = new Registro(
                    rs.getInt("id"),
                    m,
                    rs.getTimestamp("horario").toLocalDateTime(),
                    rs.getString("status")
                );

                lista.add(registro);
            }
        }
    }

    return lista;
}

    public boolean foiTomadoHoje(int idMedicamento, LocalDateTime horario, int usuarioId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Registro " +
                     "WHERE medicamento_id = ? AND horario = ? AND status = 'TOMADO' AND usuario_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMedicamento);
            stmt.setTimestamp(2, Timestamp.valueOf(horario));
            stmt.setInt(3, usuarioId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public void registrarTomado(int idMedicamento, LocalDateTime horario, int usuarioId) throws SQLException {
        String sql = "INSERT INTO Registro (medicamento_id, horario, status, usuario_id) VALUES (?, ?, 'TOMADO', ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMedicamento);
            stmt.setTimestamp(2, Timestamp.valueOf(horario));
            stmt.setInt(3, usuarioId);

            stmt.executeUpdate();
        }
    }

    public void atualizarStatusRegistro(int idRegistro, String novoStatus, int usuarioId) throws SQLException {

        if (!"TOMADO".equals(novoStatus) && !"ESQUECIDO".equals(novoStatus) && !"PENDENTE".equals(novoStatus)) {
            throw new IllegalArgumentException("Status inválido: " + novoStatus);
        }

        String sql = "UPDATE Registro SET status = ? WHERE id = ? AND usuario_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoStatus);
            stmt.setInt(2, idRegistro);
            stmt.setInt(3, usuarioId);

            stmt.executeUpdate();
        }
    }

    public Registro buscarPorMedicamentoHorario(int idMedicamento, LocalDateTime horario, int usuarioId) throws SQLException {
        String sql = "SELECT id, medicamento_id, horario, status FROM Registro WHERE medicamento_id = ? AND horario = ? AND usuario_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMedicamento);
            stmt.setTimestamp(2, Timestamp.valueOf(horario));
            stmt.setInt(3, usuarioId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Registro r = new Registro();
                    r.setId(rs.getInt("id"));
                    r.setStatus(rs.getString("status"));
                    r.setHorario(rs.getTimestamp("horario").toLocalDateTime());
                    return r;
                }
            }
        }
        return null;
    }
    public void registrarEsquecido(int idMedicamento, LocalDateTime horario, int usuarioId) throws SQLException {
    String sql = "INSERT INTO Registro (medicamento_id, horario, status, usuario_id) VALUES (?, ?, 'ESQUECIDO', ?)";
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idMedicamento);
        stmt.setTimestamp(2, Timestamp.valueOf(horario));
        stmt.setInt(3, usuarioId);
        stmt.executeUpdate();
    }
    }
}