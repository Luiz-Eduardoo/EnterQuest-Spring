package com.startup.enterquest.model;

public class AlterarSenhaPrimeiroAcessoRequest {

    private String idUsuario;
    private String novaSenha;

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}