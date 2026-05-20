package com.startup.enterquest.model;

public class Recompensa {

    private String id;
    private String nome;
    private String descricao;
    private int custoPontos;
    private int estoque;

    public Recompensa() {
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getCustoPontos() {
        return custoPontos;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCustoPontos(int custoPontos) {
        this.custoPontos = custoPontos;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
}