package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HorariosUtils {

    public static List<LocalDateTime> gerarHorariosDeHoje(LocalDate dataInicio, int intervaloHoras, Integer duracaoDias, boolean indeterminado, LocalTime horaPrimeiraDose) {
        if (horaPrimeiraDose == null) {
            throw new IllegalArgumentException("Hora da primeira dose não pode ser nula");
        }
        if (intervaloHoras <= 0) {
            throw new IllegalArgumentException("Intervalo de horas deve ser maior que zero.");
        }

        List<LocalDateTime> horarios = new ArrayList<>();
        LocalDate hoje = LocalDate.now();

        if (!indeterminado) {
            if (duracaoDias == null) {
                throw new IllegalArgumentException("Duração em dias não pode ser nula para tratamento determinado.");
            }
            if (hoje.isBefore(dataInicio) || hoje.isAfter(dataInicio.plusDays(duracaoDias - 1))) {
                return horarios;
            }
        }

        LocalDateTime horario = LocalDateTime.of(hoje, horaPrimeiraDose);

        while (horario.toLocalDate().isEqual(hoje)) {
            horarios.add(horario);
            horario = horario.plusHours(intervaloHoras);
        }

        return horarios;
    }
}
