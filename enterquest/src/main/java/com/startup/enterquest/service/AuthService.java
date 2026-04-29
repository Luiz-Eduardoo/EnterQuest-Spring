package com.startup.enterquest.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.startup.enterquest.model.AlterarSenhaPrimeiroAcessoRequest;
import com.startup.enterquest.model.CriarUsuarioRequest;
import com.startup.enterquest.model.EnviarCodigoRequest;
import com.startup.enterquest.model.LoginRequest;
import com.startup.enterquest.model.RedefinirSenhaCodigoRequest;
import com.startup.enterquest.model.Usuario;
import com.startup.enterquest.model.ValidarCodigoRequest;
import com.startup.enterquest.util.ValidadorEntrada;
@Service
public class AuthService {

    private final JavaMailSender javaMailSender;
private final BCryptPasswordEncoder passwordEncoder;

public AuthService(JavaMailSender javaMailSender, BCryptPasswordEncoder passwordEncoder) {
    this.javaMailSender = javaMailSender;
    this.passwordEncoder = passwordEncoder;
}

    public Usuario login(LoginRequest loginRequest) throws Exception {
        if (ValidadorEntrada.contemCaracteresMaliciosos(loginRequest.getLogin()) ||
        ValidadorEntrada.contemCaracteresMaliciosos(loginRequest.getCargo())) {
    return null;
}

    Firestore banco = FirestoreClient.getFirestore();

    List<QueryDocumentSnapshot> documentos = banco.collection("usuarios")
            .whereEqualTo("login", loginRequest.getLogin())
            .whereEqualTo("cargo", loginRequest.getCargo())
            .get()
            .get()
            .getDocuments();

    if (documentos.isEmpty()) {
        return null;
    }

    QueryDocumentSnapshot documento = documentos.get(0);

    Usuario usuario = documento.toObject(Usuario.class);
    usuario.setId(documento.getId());

    boolean senhaCorreta = passwordEncoder.matches(
            loginRequest.getSenha(),
            usuario.getSenha()
    );

    if (!senhaCorreta) {
        return null;
    }

    return usuario;
}

    public boolean enviarCodigoRecuperacao(EnviarCodigoRequest request) throws Exception {
        if (ValidadorEntrada.contemCaracteresMaliciosos(request.getEmail())) {
    return false;
}

        Firestore banco = FirestoreClient.getFirestore();

        List<QueryDocumentSnapshot> usuarios = banco.collection("usuarios")
                .whereEqualTo("email", request.getEmail())
                .get()
                .get()
                .getDocuments();

        if (usuarios.isEmpty()) {
            return false;
        }

        String codigo = String.valueOf(100000 + new Random().nextInt(900000));

        Map<String, Object> dadosRecuperacao = new HashMap<>();
        dadosRecuperacao.put("email", request.getEmail());
        dadosRecuperacao.put("codigo", codigo);
        dadosRecuperacao.put("usado", false);
        dadosRecuperacao.put("expiracao", LocalDateTime.now().plusMinutes(10).toString());

        banco.collection("recuperacao_senha")
                .document()
                .set(dadosRecuperacao)
                .get();

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(request.getEmail());
        mensagem.setSubject("Código de recuperação de senha - EnterQuest");
        mensagem.setText("Seu código de recuperação é: " + codigo + "\n\nEste código expira em 10 minutos.");

        javaMailSender.send(mensagem);

        return true;
    }
    public boolean validarCodigoRecuperacao(ValidarCodigoRequest request) throws Exception {
        if (ValidadorEntrada.contemCaracteresMaliciosos(request.getEmail()) ||
        ValidadorEntrada.contemCaracteresMaliciosos(request.getCodigo())) {
    return false;
}

    Firestore banco = FirestoreClient.getFirestore();

    List<QueryDocumentSnapshot> documentos = banco.collection("recuperacao_senha")
            .whereEqualTo("email", request.getEmail())
            .whereEqualTo("codigo", request.getCodigo())
            .whereEqualTo("usado", false)
            .get()
            .get()
            .getDocuments();

    if (documentos.isEmpty()) {
        return false;
    }

    QueryDocumentSnapshot documento = documentos.get(0);

    String expiracao = documento.getString("expiracao");

    if (expiracao == null) {
        return false;
    }

    LocalDateTime dataExpiracao = LocalDateTime.parse(expiracao);

    if (LocalDateTime.now().isAfter(dataExpiracao)) {
        return false;
    }

    return true;
}
private boolean senhaForte(String senha) {
    if (senha == null) {
        return false;
    }

    String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!?.*_-]).{8,}$";

    return senha.matches(regex);
}

public boolean redefinirSenhaComCodigo(RedefinirSenhaCodigoRequest request) throws Exception {

    if (ValidadorEntrada.contemCaracteresMaliciosos(request.getEmail()) ||
        ValidadorEntrada.contemCaracteresMaliciosos(request.getCodigo()) ||
        ValidadorEntrada.contemCaracteresMaliciosos(request.getNovaSenha())) {
    return false;
}

    if (!senhaForte(request.getNovaSenha())) {
        throw new IllegalArgumentException("A senha deve ter no mínimo 8 caracteres, com letra maiúscula, minúscula, número e caractere especial.");
    }

    Firestore banco = FirestoreClient.getFirestore();

    List<QueryDocumentSnapshot> codigos = banco.collection("recuperacao_senha")
            .whereEqualTo("email", request.getEmail())
            .whereEqualTo("codigo", request.getCodigo())
            .whereEqualTo("usado", false)
            .get()
            .get()
            .getDocuments();

    if (codigos.isEmpty()) {
        return false;
    }

    QueryDocumentSnapshot documentoCodigo = codigos.get(0);

    String expiracao = documentoCodigo.getString("expiracao");

    if (expiracao == null) {
        return false;
    }

    LocalDateTime dataExpiracao = LocalDateTime.parse(expiracao);

    if (LocalDateTime.now().isAfter(dataExpiracao)) {
        return false;
    }

    List<QueryDocumentSnapshot> usuarios = banco.collection("usuarios")
            .whereEqualTo("email", request.getEmail())
            .get()
            .get()
            .getDocuments();

    if (usuarios.isEmpty()) {
        return false;
    }

    QueryDocumentSnapshot usuario = usuarios.get(0);

    String senhaCriptografada = passwordEncoder.encode(request.getNovaSenha());

    banco.collection("usuarios")
        .document(usuario.getId())
        .update("senha", senhaCriptografada)
        .get();

    banco.collection("recuperacao_senha")
            .document(documentoCodigo.getId())
            .update("usado", true)
            .get();

    return true;
}
public boolean criarUsuario(CriarUsuarioRequest request) throws Exception {

    if (ValidadorEntrada.contemCaracteresMaliciosos(request.getNome()) ||
        ValidadorEntrada.contemCaracteresMaliciosos(request.getLogin()) ||
        ValidadorEntrada.contemCaracteresMaliciosos(request.getEmail()) ||
        ValidadorEntrada.contemCaracteresMaliciosos(request.getCargo())) {
        throw new IllegalArgumentException("Os dados do usuário contêm conteúdo inválido.");
    }

    if (!senhaForte(request.getSenha())) {
        throw new IllegalArgumentException("A senha provisória deve ter no mínimo 8 caracteres, com letra maiúscula, minúscula, número e caractere especial.");
    }

    Firestore banco = FirestoreClient.getFirestore();

    List<QueryDocumentSnapshot> usuariosExistentes = banco.collection("usuarios")
            .whereEqualTo("login", request.getLogin())
            .get()
            .get()
            .getDocuments();

    if (!usuariosExistentes.isEmpty()) {
        throw new IllegalArgumentException("Já existe um usuário com esse login.");
    }

    Usuario usuario = new Usuario();
    usuario.setNome(request.getNome());
    usuario.setLogin(request.getLogin());
    usuario.setEmail(request.getEmail());
    usuario.setCargo(request.getCargo());
    usuario.setSenha(passwordEncoder.encode(request.getSenha()));
    usuario.setPrimeiroAcesso(true);

    String id = banco.collection("usuarios").document().getId();
    usuario.setId(id);

    banco.collection("usuarios")
            .document(id)
            .set(usuario)
            .get();

    return true;
}
public boolean alterarSenhaPrimeiroAcesso(AlterarSenhaPrimeiroAcessoRequest request) throws Exception {

    if (ValidadorEntrada.contemCaracteresMaliciosos(request.getNovaSenha())) {
        return false;
    }

    if (!senhaForte(request.getNovaSenha())) {
        throw new IllegalArgumentException("A senha deve ter no mínimo 8 caracteres, com letra maiúscula, minúscula, número e caractere especial.");
    }

    Firestore banco = FirestoreClient.getFirestore();

    String senhaCriptografada = passwordEncoder.encode(request.getNovaSenha());

    Map<String, Object> atualizacoes = new HashMap<>();
    atualizacoes.put("senha", senhaCriptografada);
    atualizacoes.put("primeiroAcesso", false);

    banco.collection("usuarios")
            .document(request.getIdUsuario())
            .update(atualizacoes)
            .get();

    return true;
}
}