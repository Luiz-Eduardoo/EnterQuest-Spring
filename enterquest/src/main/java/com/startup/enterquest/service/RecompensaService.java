package com.startup.enterquest.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.startup.enterquest.model.Recompensa;
import com.startup.enterquest.model.ResgatarRecompensaRequest;
import com.startup.enterquest.model.ResgateRecompensa;
import com.startup.enterquest.util.ValidadorEntrada;

@Service
public class RecompensaService {

    public String criarRecompensa(Recompensa recompensa) throws Exception {

        if (ValidadorEntrada.contemCaracteresMaliciosos(recompensa.getNome()) ||
                ValidadorEntrada.contemCaracteresMaliciosos(recompensa.getDescricao())) {
            throw new IllegalArgumentException("A recompensa contém conteúdo inválido.");
        }

        if (recompensa.getCustoPontos() <= 0) {
            throw new IllegalArgumentException("O custo em pontos deve ser maior que zero.");
        }

        if (recompensa.getEstoque() < 0) {
            throw new IllegalArgumentException("O estoque não pode ser negativo.");
        }

        Firestore banco = FirestoreClient.getFirestore();

        String id = banco.collection("recompensas").document().getId();
        recompensa.setId(id);

        banco.collection("recompensas")
                .document(id)
                .set(recompensa)
                .get();

        return "Recompensa criada com sucesso!";
    }

    public List<Recompensa> listarRecompensas() throws Exception {

        Firestore banco = FirestoreClient.getFirestore();

        List<QueryDocumentSnapshot> documentos = banco.collection("recompensas")
                .get()
                .get()
                .getDocuments();

        List<Recompensa> recompensas = new ArrayList<>();

        for (QueryDocumentSnapshot documento : documentos) {
            Recompensa recompensa = documento.toObject(Recompensa.class);
            recompensas.add(recompensa);
        }

        return recompensas;
    }

    public String atualizarRecompensa(String idRecompensa, Recompensa recompensa) throws Exception {

        if (ValidadorEntrada.contemCaracteresMaliciosos(recompensa.getNome()) ||
                ValidadorEntrada.contemCaracteresMaliciosos(recompensa.getDescricao())) {
            throw new IllegalArgumentException("A recompensa contém conteúdo inválido.");
        }

        if (recompensa.getCustoPontos() <= 0) {
            throw new IllegalArgumentException("O custo em pontos deve ser maior que zero.");
        }

        if (recompensa.getEstoque() < 0) {
            throw new IllegalArgumentException("O estoque não pode ser negativo.");
        }

        Firestore banco = FirestoreClient.getFirestore();

        recompensa.setId(idRecompensa);

        banco.collection("recompensas")
                .document(idRecompensa)
                .set(recompensa)
                .get();

        return "Recompensa atualizada com sucesso!";
    }

    public String removerRecompensa(String idRecompensa) throws Exception {

        Firestore banco = FirestoreClient.getFirestore();

        banco.collection("recompensas")
                .document(idRecompensa)
                .delete()
                .get();

        return "Recompensa removida com sucesso!";
    }
    public String resgatarRecompensa(ResgatarRecompensaRequest request) throws Exception {

    Firestore banco = FirestoreClient.getFirestore();

    DocumentSnapshot documentoUsuario = banco.collection("usuarios")
            .document(request.getIdUsuario())
            .get()
            .get();

            

    if (!documentoUsuario.exists()) {
        throw new IllegalArgumentException("Usuário não encontrado.");
    }

    DocumentSnapshot documentoRecompensa = banco.collection("recompensas")
            .document(request.getIdRecompensa())
            .get()
            .get();

    if (!documentoRecompensa.exists()) {
        throw new IllegalArgumentException("Recompensa não encontrada.");
    }

    Long pontosUsuarioLong = documentoUsuario.getLong("pontos");
    int pontosUsuario = pontosUsuarioLong != null ? pontosUsuarioLong.intValue() : 0;

    Recompensa recompensa = documentoRecompensa.toObject(Recompensa.class);

    if (recompensa == null) {
        throw new IllegalArgumentException("Erro ao carregar recompensa.");
    }

    if (recompensa.getEstoque() <= 0) {
        throw new IllegalArgumentException("Recompensa sem estoque disponível.");
    }

    if (pontosUsuario < recompensa.getCustoPontos()) {
        throw new IllegalArgumentException("Pontos insuficientes para resgatar esta recompensa.");
    }

    int novosPontos = pontosUsuario - recompensa.getCustoPontos();
    int novoEstoque = recompensa.getEstoque() - 1;

    banco.collection("usuarios")
            .document(request.getIdUsuario())
            .update("pontos", novosPontos)
            .get();

    banco.collection("recompensas")
            .document(request.getIdRecompensa())
            .update("estoque", novoEstoque)
            .get();

            ResgateRecompensa resgate = new ResgateRecompensa();

String idResgate = banco.collection("resgates_recompensas").document().getId();

String codigoResgate = "RES-" + UUID.randomUUID()
        .toString()
        .substring(0, 6)
        .toUpperCase();

resgate.setId(idResgate);
resgate.setCodigoResgate(codigoResgate);
resgate.setIdUsuario(request.getIdUsuario());
resgate.setIdRecompensa(request.getIdRecompensa());
resgate.setNomeRecompensa(recompensa.getNome());
resgate.setDescricaoRecompensa(recompensa.getDescricao());
resgate.setCustoPontos(recompensa.getCustoPontos());
resgate.setDataResgate(LocalDateTime.now().toString());
resgate.setUtilizado(false);
resgate.setDataUtilizacao("");

banco.collection("resgates_recompensas")
        .document(idResgate)
        .set(resgate)
        .get();

    return "Recompensa resgatada com sucesso!";
}

public List<ResgateRecompensa> listarResgatesPorUsuario(String idUsuario) throws Exception {

    Firestore banco = FirestoreClient.getFirestore();

    List<QueryDocumentSnapshot> documentos = banco.collection("resgates_recompensas")
            .whereEqualTo("idUsuario", idUsuario)
            .get()
            .get()
            .getDocuments();
            System.out.println("Quantidade de resgates encontrados: " + documentos.size());

    List<ResgateRecompensa> resgates = new ArrayList<>();

    for (QueryDocumentSnapshot documento : documentos) {
        ResgateRecompensa resgate = documento.toObject(ResgateRecompensa.class);
        resgates.add(resgate);
    }
System.out.println("ID recebido na busca: " + idUsuario);
System.out.println("Quantidade de resgates na lista de retorno: " + resgates.size());
    return resgates;
}
public List<ResgateRecompensa> listarTodosResgates() throws Exception {

    Firestore banco = FirestoreClient.getFirestore();

    List<QueryDocumentSnapshot> documentos = banco.collection("resgates_recompensas")
            .get()
            .get()
            .getDocuments();

    List<ResgateRecompensa> resgates = new ArrayList<>();

    for (QueryDocumentSnapshot documento : documentos) {
        ResgateRecompensa resgate = documento.toObject(ResgateRecompensa.class);
        resgates.add(resgate);
    }

    return resgates;
}
public String confirmarEntregaResgate(String idResgate) throws Exception {

    Firestore banco = FirestoreClient.getFirestore();

    DocumentSnapshot documentoResgate = banco.collection("resgates_recompensas")
            .document(idResgate)
            .get()
            .get();

    if (!documentoResgate.exists()) {
        throw new IllegalArgumentException("Resgate não encontrado.");
    }

    Boolean utilizado = documentoResgate.getBoolean("utilizado");

    if (Boolean.TRUE.equals(utilizado)) {
        throw new IllegalArgumentException("Este resgate já foi entregue.");
    }

    banco.collection("resgates_recompensas")
            .document(idResgate)
            .update(
                    "utilizado", true,
                    "dataUtilizacao", LocalDateTime.now().toString()
            )
            .get();

    return "Entrega confirmada com sucesso!";
}
}