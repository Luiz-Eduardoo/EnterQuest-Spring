package com.startup.enterquest.model;

import java.util.Map;

public class DashboardResumo {

    private int totalChamados;
    private int pendentes;
    private int emAndamento;
    private int concluidos;

    private Map<String, Integer> chamadosPorSetor;
    private Map<String, Integer> chamadosPorCategoria;

    private int abertosHoje;
    private int abertosSemana;
    private int abertosMes;

    private int concluidosHoje;
    private int concluidosSemana;
    private int concluidosMes;

    private String setorMaisChamados;
    private int quantidadeSetorMaisChamados;

    private String categoriaMaisRecorrente;
    private int quantidadeCategoriaMaisRecorrente;

    public int getTotalChamados() {
        return totalChamados;
    }

    public void setTotalChamados(int totalChamados) {
        this.totalChamados = totalChamados;
    }

    public int getPendentes() {
        return pendentes;
    }

    public void setPendentes(int pendentes) {
        this.pendentes = pendentes;
    }

    public int getEmAndamento() {
        return emAndamento;
    }

    public void setEmAndamento(int emAndamento) {
        this.emAndamento = emAndamento;
    }

    public int getConcluidos() {
        return concluidos;
    }

    public void setConcluidos(int concluidos) {
        this.concluidos = concluidos;
    }

    public Map<String, Integer> getChamadosPorSetor() {
        return chamadosPorSetor;
    }

    public void setChamadosPorSetor(Map<String, Integer> chamadosPorSetor) {
        this.chamadosPorSetor = chamadosPorSetor;
    }

    public Map<String, Integer> getChamadosPorCategoria() {
        return chamadosPorCategoria;
    }

    public void setChamadosPorCategoria(Map<String, Integer> chamadosPorCategoria) {
        this.chamadosPorCategoria = chamadosPorCategoria;
    }

    public int getAbertosHoje() {
        return abertosHoje;
    }

    public void setAbertosHoje(int abertosHoje) {
        this.abertosHoje = abertosHoje;
    }

    public int getAbertosSemana() {
        return abertosSemana;
    }

    public void setAbertosSemana(int abertosSemana) {
        this.abertosSemana = abertosSemana;
    }

    public int getAbertosMes() {
        return abertosMes;
    }

    public void setAbertosMes(int abertosMes) {
        this.abertosMes = abertosMes;
    }

    public int getConcluidosHoje() {
        return concluidosHoje;
    }

    public void setConcluidosHoje(int concluidosHoje) {
        this.concluidosHoje = concluidosHoje;
    }

    public int getConcluidosSemana() {
        return concluidosSemana;
    }

    public void setConcluidosSemana(int concluidosSemana) {
        this.concluidosSemana = concluidosSemana;
    }

    public int getConcluidosMes() {
        return concluidosMes;
    }

    public void setConcluidosMes(int concluidosMes) {
        this.concluidosMes = concluidosMes;
    }

    public String getSetorMaisChamados() {
        return setorMaisChamados;
    }

    public void setSetorMaisChamados(String setorMaisChamados) {
        this.setorMaisChamados = setorMaisChamados;
    }

    public int getQuantidadeSetorMaisChamados() {
        return quantidadeSetorMaisChamados;
    }

    public void setQuantidadeSetorMaisChamados(int quantidadeSetorMaisChamados) {
        this.quantidadeSetorMaisChamados = quantidadeSetorMaisChamados;
    }

    public String getCategoriaMaisRecorrente() {
        return categoriaMaisRecorrente;
    }

    public void setCategoriaMaisRecorrente(String categoriaMaisRecorrente) {
        this.categoriaMaisRecorrente = categoriaMaisRecorrente;
    }

    public int getQuantidadeCategoriaMaisRecorrente() {
        return quantidadeCategoriaMaisRecorrente;
    }

    public void setQuantidadeCategoriaMaisRecorrente(int quantidadeCategoriaMaisRecorrente) {
        this.quantidadeCategoriaMaisRecorrente = quantidadeCategoriaMaisRecorrente;
    }
}