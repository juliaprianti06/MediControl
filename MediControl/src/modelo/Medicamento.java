package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Medicamento {
    private int id;
    private String nome;
    private String dosagem;
    private String unidade;
    private String forma;
    private int intervaloUso; 
    private LocalDate inicioTratamento;
    private LocalTime horaPrimeiraDose;
    private Integer duracaoDias;
    private boolean indeterminado;

    
    public Medicamento() {}

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public int getIntervaloUso() {
        return intervaloUso;
    }

    public void setIntervaloUso(int intervaloUso) {
        this.intervaloUso = intervaloUso;
    }

    public LocalDate getInicioTratamento() {
        return inicioTratamento;
    }

    public void setInicioTratamento(LocalDate inicioTratamento) {
        this.inicioTratamento = inicioTratamento;
    }

    public Integer getDuracaoDias() {
        return duracaoDias;
    }

    public void setDuracaoDias(Integer duracaoDias) {
        this.duracaoDias = duracaoDias;
    }

    public boolean isIndeterminado() {
        return indeterminado;
    }

    public void setIndeterminado(boolean indeterminado) {
        this.indeterminado = indeterminado;
    }
    public LocalTime getHoraPrimeiraDose() {
    return horaPrimeiraDose;
}

    public void setHoraPrimeiraDose(LocalTime horaPrimeiraDose) {
    this.horaPrimeiraDose = horaPrimeiraDose;
    }
}