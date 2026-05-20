package com.startup.enterquest.model;

public class ResgateRecompensa {

    private String id;
    private String idUsuario;
    private String idRecompensa;
    private String nomeRecompensa;
    private String descricaoRecompensa;
    private int custoPontos;
    private String dataResgate;
    private String codigoResgate;
    private boolean utilizado;
    private String dataUtilizacao;

    public ResgateRecompensa() {
    }

    public String getId() {
        return id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getIdRecompensa() {
        return idRecompensa;
    }

    public String getNomeRecompensa() {
        return nomeRecompensa;
    }

    public String getDescricaoRecompensa() {
        return descricaoRecompensa;
    }

    public int getCustoPontos() {
        return custoPontos;
    }

    public String getDataResgate() {
        return dataResgate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIdRecompensa(String idRecompensa) {
        this.idRecompensa = idRecompensa;
    }

    public void setNomeRecompensa(String nomeRecompensa) {
        this.nomeRecompensa = nomeRecompensa;
    }

    public void setDescricaoRecompensa(String descricaoRecompensa) {
        this.descricaoRecompensa = descricaoRecompensa;
    }

    public void setCustoPontos(int custoPontos) {
        this.custoPontos = custoPontos;
    }

    public void setDataResgate(String dataResgate) {
        this.dataResgate = dataResgate;
    }
    public String getCodigoResgate() {
    return codigoResgate;
    }

    public void setCodigoResgate(String codigoResgate) {
    this.codigoResgate = codigoResgate;
    }
    public boolean isUtilizado() {
    return utilizado;
    }

    public void setUtilizado(boolean utilizado) {
    this.utilizado = utilizado;
    }

    public String getDataUtilizacao() {
    return dataUtilizacao;
    }

    public void setDataUtilizacao(String dataUtilizacao) {
    this.dataUtilizacao = dataUtilizacao;
    }
}