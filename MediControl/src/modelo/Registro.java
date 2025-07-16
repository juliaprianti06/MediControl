package modelo;

import java.time.LocalDateTime;

public class Registro {
    
    private int id;
    private Medicamento medicamento;
    private LocalDateTime horario;
    private String status; 

    public Registro() {
       
    }

    public Registro(int id, Medicamento medicamento, LocalDateTime horario, String status) {
        this.id = id;
        this.medicamento = medicamento;
        this.horario = horario;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Medicamento getMedicamento() { return medicamento; }
    public void setMedicamento(Medicamento medicamento) { this.medicamento = medicamento; }

    public LocalDateTime getHorario() { return horario; }
    public void setHorario(LocalDateTime horario) { this.horario = horario; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}