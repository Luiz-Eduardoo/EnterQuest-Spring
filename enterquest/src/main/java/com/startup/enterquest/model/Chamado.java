package com.startup.enterquest.model;

public class Chamado {

    private String id;
    private String titulo;
    private String setor;
    private String categoria;
    private String descricao;
    private String foto;
    private String status;
    private String idUsuario;
    private String dataAbertura;
    private String dataEncerramento;

    public Chamado() {
    }

    public Chamado(String id, String titulo, String setor, String categoria, String descricao, String foto, String status) {
        this.id = id;
        this.titulo = titulo;
        this.setor = setor;
        this.categoria = categoria;
        this.descricao = descricao;
        this.foto = foto;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSetor() {
        return setor;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getFoto() {
        return foto;
    }

    public String getStatus() {
        return status;
    }
    public String getIdUsuario() {
    return idUsuario;
}

public void setIdUsuario(String idUsuario) {
    this.idUsuario = idUsuario;
}

    public void setId(String id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getDataAbertura() {
    return dataAbertura;
}

public void setDataAbertura(String dataAbertura) {
    this.dataAbertura = dataAbertura;
}

public String getDataEncerramento() {
    return dataEncerramento;
}

public void setDataEncerramento(String dataEncerramento) {
    this.dataEncerramento = dataEncerramento;
}
}