package com.startup.enterquest.model;

public class Usuario {

    private String id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private String cargo;
    private Boolean primeiroAcesso;
    

    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public String getCargo() {
        return cargo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Boolean getPrimeiroAcesso() {
    return primeiroAcesso;
}

public void setPrimeiroAcesso(Boolean primeiroAcesso) {
    this.primeiroAcesso = primeiroAcesso;
}
public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}
}