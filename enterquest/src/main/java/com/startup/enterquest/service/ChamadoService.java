package com.startup.enterquest.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.startup.enterquest.model.Chamado;
import com.startup.enterquest.model.DashboardResumo;
import com.startup.enterquest.util.ValidadorEntrada;

@Service
public class ChamadoService {
    

    public String criarChamado(Chamado chamado) throws Exception {

        if (ValidadorEntrada.contemCaracteresMaliciosos(chamado.getTitulo()) ||
        ValidadorEntrada.contemCaracteresMaliciosos(chamado.getDescricao()) ||
        ValidadorEntrada.contemCaracteresMaliciosos(chamado.getSetor()) ||
        ValidadorEntrada.contemCaracteresMaliciosos(chamado.getCategoria())) {

    throw new IllegalArgumentException("O chamado contém caracteres ou comandos inválidos.");
}

        chamado.setStatus("Pendente");
        chamado.setDataAbertura(LocalDateTime.now().toString());
        chamado.setDataEncerramento("");


        Firestore banco = FirestoreClient.getFirestore();

        String id = banco.collection("chamados").document().getId();

        chamado.setId(id);

        banco.collection("chamados").document(id).set(chamado).get();

        return "Chamado aberto com sucesso!";
    }
 public List<Chamado> listarChamados() throws Exception {

    Firestore banco = FirestoreClient.getFirestore();

    List<QueryDocumentSnapshot> documentos = banco.collection("chamados")
            .get()
            .get()
            .getDocuments();

    System.out.println("Quantidade de chamados encontrados: " + documentos.size());

    List<Chamado> chamados = new ArrayList<>();

    for (QueryDocumentSnapshot documento : documentos) {
        Chamado chamado = documento.toObject(Chamado.class);

        System.out.println("Chamado encontrado:");
        System.out.println("ID: " + chamado.getId());
        System.out.println("Título: " + chamado.getTitulo());

        chamados.add(chamado);
    }

    return chamados;
}
public List<Chamado> listarChamadosPorUsuario(String idUsuario) throws Exception {

    Firestore banco = FirestoreClient.getFirestore();

    List<QueryDocumentSnapshot> documentos = banco.collection("chamados")
            .whereEqualTo("idUsuario", idUsuario)
            .get()
            .get()
            .getDocuments();

    List<Chamado> chamados = new ArrayList<>();

    for (QueryDocumentSnapshot documento : documentos) {
        Chamado chamado = documento.toObject(Chamado.class);
        chamados.add(chamado);
    }

    return chamados;
}

public boolean atualizarStatusChamado(String idChamado, String novoStatus) throws Exception {

    Firestore banco = FirestoreClient.getFirestore();

    if (novoStatus == null || novoStatus.trim().isEmpty()) {
        return false;
    }

    Map<String, Object> atualizacoes = new HashMap<>();
    atualizacoes.put("status", novoStatus);

    if (novoStatus.equals("Concluído")) {
        atualizacoes.put("dataEncerramento", LocalDateTime.now().toString());
    }

    banco.collection("chamados")
            .document(idChamado)
            .update(atualizacoes)
            .get();

    return true;
}
public DashboardResumo gerarResumoDashboard() throws Exception {

    Firestore banco = FirestoreClient.getFirestore();

    List<QueryDocumentSnapshot> documentos = banco.collection("chamados")
            .get()
            .get()
            .getDocuments();

    DashboardResumo resumo = new DashboardResumo();

    int total = 0;
    int pendentes = 0;
    int emAndamento = 0;
    int concluidos = 0;

    int abertosHoje = 0;
    int abertosSemana = 0;
    int abertosMes = 0;

    int concluidosHoje = 0;
    int concluidosSemana = 0;
    int concluidosMes = 0;

    Map<String, Integer> chamadosPorSetor = new HashMap<>();
    Map<String, Integer> chamadosPorCategoria = new HashMap<>();

    LocalDate hoje = LocalDate.now();
    LocalDate inicioSemana = hoje.minusDays(6);
    LocalDate inicioMes = hoje.withDayOfMonth(1);

    for (QueryDocumentSnapshot documento : documentos) {
        Chamado chamado = documento.toObject(Chamado.class);

        total++;

        if ("Pendente".equals(chamado.getStatus())) {
            pendentes++;
        } else if ("Em andamento".equals(chamado.getStatus())) {
            emAndamento++;
        } else if ("Concluído".equals(chamado.getStatus())) {
            concluidos++;
        }

        String setor = chamado.getSetor();

        if (setor == null || setor.trim().isEmpty()) {
            setor = "Não informado";
        }

        chamadosPorSetor.put(setor, chamadosPorSetor.getOrDefault(setor, 0) + 1);

        String categoria = chamado.getCategoria();

        if (categoria == null || categoria.trim().isEmpty()) {
            categoria = "Não informado";
        }

        chamadosPorCategoria.put(categoria, chamadosPorCategoria.getOrDefault(categoria, 0) + 1);

        String dataAberturaTexto = chamado.getDataAbertura();

        if (dataAberturaTexto != null && !dataAberturaTexto.trim().isEmpty()) {
            try {
                LocalDate dataAbertura = LocalDateTime.parse(dataAberturaTexto).toLocalDate();

                if (dataAbertura.isEqual(hoje)) {
                    abertosHoje++;
                }

                if (!dataAbertura.isBefore(inicioSemana) && !dataAbertura.isAfter(hoje)) {
                    abertosSemana++;
                }

                if (!dataAbertura.isBefore(inicioMes) && !dataAbertura.isAfter(hoje)) {
                    abertosMes++;
                }

            } catch (Exception e) {
                System.out.println("Data de abertura inválida: " + dataAberturaTexto);
            }
        }

        String dataEncerramentoTexto = chamado.getDataEncerramento();

        if (dataEncerramentoTexto != null && !dataEncerramentoTexto.trim().isEmpty()) {
            try {
                LocalDate dataEncerramento = LocalDateTime.parse(dataEncerramentoTexto).toLocalDate();

                if (dataEncerramento.isEqual(hoje)) {
                    concluidosHoje++;
                }

                if (!dataEncerramento.isBefore(inicioSemana) && !dataEncerramento.isAfter(hoje)) {
                    concluidosSemana++;
                }

                if (!dataEncerramento.isBefore(inicioMes) && !dataEncerramento.isAfter(hoje)) {
                    concluidosMes++;
                }

            } catch (Exception e) {
                System.out.println("Data de encerramento inválida: " + dataEncerramentoTexto);
            }
        }
    }

    String setorMaisChamados = "Não informado";
    int quantidadeSetorMaisChamados = 0;

    for (Map.Entry<String, Integer> entrada : chamadosPorSetor.entrySet()) {
        if (entrada.getValue() > quantidadeSetorMaisChamados) {
            setorMaisChamados = entrada.getKey();
            quantidadeSetorMaisChamados = entrada.getValue();
        }
    }

    String categoriaMaisRecorrente = "Não informado";
    int quantidadeCategoriaMaisRecorrente = 0;

    for (Map.Entry<String, Integer> entrada : chamadosPorCategoria.entrySet()) {
        if (entrada.getValue() > quantidadeCategoriaMaisRecorrente) {
            categoriaMaisRecorrente = entrada.getKey();
            quantidadeCategoriaMaisRecorrente = entrada.getValue();
        }
    }

    resumo.setTotalChamados(total);
    resumo.setPendentes(pendentes);
    resumo.setEmAndamento(emAndamento);
    resumo.setConcluidos(concluidos);

    resumo.setChamadosPorSetor(chamadosPorSetor);
    resumo.setChamadosPorCategoria(chamadosPorCategoria);

    resumo.setAbertosHoje(abertosHoje);
    resumo.setAbertosSemana(abertosSemana);
    resumo.setAbertosMes(abertosMes);

    resumo.setConcluidosHoje(concluidosHoje);
    resumo.setConcluidosSemana(concluidosSemana);
    resumo.setConcluidosMes(concluidosMes);

    resumo.setSetorMaisChamados(setorMaisChamados);
    resumo.setQuantidadeSetorMaisChamados(quantidadeSetorMaisChamados);

    resumo.setCategoriaMaisRecorrente(categoriaMaisRecorrente);
    resumo.setQuantidadeCategoriaMaisRecorrente(quantidadeCategoriaMaisRecorrente);

    return resumo;
}
}