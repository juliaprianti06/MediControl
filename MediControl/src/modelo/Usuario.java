package modelo;

import java.sql.Date;

public class Usuario {
    private int id; // se usar auto_increment no banco
    private String nome;
    private String telefone;
    private String email;
    private Date dataNascimento;
    private String senha; // pode guardar hash da senha (mais seguro)

    // Construtor sem id (para cadastro)
    public Usuario(String nome, String telefone, String email, Date dataNascimento, String senha) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.senha = senha;
    }

    // Construtor com id (para consultas)
    public Usuario(int id, String nome, String telefone, String email, Date dataNascimento, String senha) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.senha = senha;
    }

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Date getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}