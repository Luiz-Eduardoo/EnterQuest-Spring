package com.startup.enterquest.service;

import org.springframework.stereotype.Service;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.startup.enterquest.model.ConfiguracaoGamificacao;

@Service
public class GamificacaoService {

    private static final String COLECAO = "configuracao_gamificacao";
    private static final String DOCUMENTO = "configuracao";

    public ConfiguracaoGamificacao buscarConfiguracao() throws Exception {
        Firestore banco = FirestoreClient.getFirestore();

        DocumentSnapshot documento = banco.collection(COLECAO)
                .document(DOCUMENTO)
                .get()
                .get();

        if (!documento.exists()) {
            ConfiguracaoGamificacao configuracao = new ConfiguracaoGamificacao();
            configuracao.setPontosAbrirChamado(0);
            configuracao.setPontosConcluirChamado(0);
            return configuracao;
        }

        return documento.toObject(ConfiguracaoGamificacao.class);
    }

    public void atualizarConfiguracao(ConfiguracaoGamificacao configuracao) throws Exception {
        Firestore banco = FirestoreClient.getFirestore();

        banco.collection(COLECAO)
                .document(DOCUMENTO)
                .set(configuracao)
                .get();
    }
    public void adicionarPontosUsuario(String idUsuario, int pontos) throws Exception {

    if (idUsuario == null || idUsuario.trim().isEmpty() || pontos <= 0) {
        return;
    }

    Firestore banco = FirestoreClient.getFirestore();

    DocumentSnapshot documentoUsuario = banco.collection("usuarios")
            .document(idUsuario)
            .get()
            .get();

    if (!documentoUsuario.exists()) {
        return;
    }

    Long pontosAtuaisLong = documentoUsuario.getLong("pontos");
    int pontosAtuais = pontosAtuaisLong != null ? pontosAtuaisLong.intValue() : 0;

    int novosPontos = pontosAtuais + pontos;

    banco.collection("usuarios")
            .document(idUsuario)
            .update("pontos", novosPontos)
            .get();
}
public int buscarPontosUsuario(String idUsuario) throws Exception {

    Firestore banco = FirestoreClient.getFirestore();

    DocumentSnapshot documentoUsuario = banco.collection("usuarios")
            .document(idUsuario)
            .get()
            .get();

    if (!documentoUsuario.exists()) {
        throw new IllegalArgumentException("Usuário não encontrado.");
    }

    Long pontosLong = documentoUsuario.getLong("pontos");

    return pontosLong != null ? pontosLong.intValue() : 0;
}
}