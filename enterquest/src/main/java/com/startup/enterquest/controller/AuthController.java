package com.startup.enterquest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.startup.enterquest.model.AlterarSenhaPrimeiroAcessoRequest;
import com.startup.enterquest.model.CriarUsuarioRequest;
import com.startup.enterquest.model.EnviarCodigoRequest;
import com.startup.enterquest.model.LoginRequest;
import com.startup.enterquest.model.RedefinirSenhaCodigoRequest;
import com.startup.enterquest.model.Usuario;
import com.startup.enterquest.model.ValidarCodigoRequest;
import com.startup.enterquest.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {

        Usuario usuario = authService.login(loginRequest);

        if (usuario == null) {
            return ResponseEntity.status(401).body("Login, senha ou cargo inválido");
        }

        usuario.setSenha(null);

        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/enviar-codigo")
public ResponseEntity<?> enviarCodigo(@RequestBody EnviarCodigoRequest request) {
    try {
        boolean enviado = authService.enviarCodigoRecuperacao(request);

        if (!enviado) {
            return ResponseEntity.status(404).body("E-mail não encontrado");
        }

        return ResponseEntity.ok("Código enviado com sucesso!");

    } catch (Exception e) {
        return ResponseEntity.status(500).body("Erro ao enviar código: " + e.getMessage());
    }
}
@PostMapping("/validar-codigo")
public ResponseEntity<?> validarCodigo(@RequestBody ValidarCodigoRequest request) throws Exception {

    boolean valido = authService.validarCodigoRecuperacao(request);

    if (!valido) {
        return ResponseEntity.status(400).body("Código inválido ou expirado");
    }

    return ResponseEntity.ok("Código validado com sucesso!");
}
@PutMapping("/redefinir-senha-com-codigo")
public ResponseEntity<?> redefinirSenhaComCodigo(@RequestBody RedefinirSenhaCodigoRequest request) throws Exception {
    try {
        boolean redefinida = authService.redefinirSenhaComCodigo(request);

        if (!redefinida) {
            return ResponseEntity.status(400).body("Código inválido, expirado ou e-mail não encontrado");
        }

        return ResponseEntity.ok("Senha redefinida com sucesso!");

    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
@PostMapping("/criar-usuario")
public ResponseEntity<?> criarUsuario(@RequestBody CriarUsuarioRequest request) {
    try {
        authService.criarUsuario(request);
        return ResponseEntity.ok("Usuário criado com sucesso!");

    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());

    } catch (Exception e) {
        return ResponseEntity.status(500).body("Erro ao criar usuário: " + e.getMessage());
    }
}
@PutMapping("/alterar-senha-primeiro-acesso")
public ResponseEntity<?> alterarSenhaPrimeiroAcesso(@RequestBody AlterarSenhaPrimeiroAcessoRequest request) {
    try {
        boolean alterada = authService.alterarSenhaPrimeiroAcesso(request);

        if (!alterada) {
            return ResponseEntity.badRequest().body("Dados inválidos");
        }

        return ResponseEntity.ok("Senha alterada com sucesso!");

    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());

    } catch (Exception e) {
        return ResponseEntity.status(500).body("Erro ao alterar senha: " + e.getMessage());
    }
}
}