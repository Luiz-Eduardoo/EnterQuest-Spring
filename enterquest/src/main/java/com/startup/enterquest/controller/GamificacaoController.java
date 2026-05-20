package com.startup.enterquest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.startup.enterquest.model.ConfiguracaoGamificacao;
import com.startup.enterquest.service.GamificacaoService;

@RestController
@RequestMapping("/gamificacao")
public class GamificacaoController {

    private final GamificacaoService gamificacaoService;

    public GamificacaoController(GamificacaoService gamificacaoService) {
        this.gamificacaoService = gamificacaoService;
    }

    @GetMapping("/configuracao")
    public ResponseEntity<?> buscarConfiguracao() {
        try {
            return ResponseEntity.ok(gamificacaoService.buscarConfiguracao());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao buscar configuração da gamificação.");
        }
    }

    @PutMapping("/configuracao")
    public ResponseEntity<?> atualizarConfiguracao(@RequestBody ConfiguracaoGamificacao configuracao) {
        try {
            gamificacaoService.atualizarConfiguracao(configuracao);
            return ResponseEntity.ok("Configuração atualizada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao atualizar configuração da gamificação.");
        }
    }
    @GetMapping("/pontos/{idUsuario}")
public ResponseEntity<?> buscarPontosUsuario(@PathVariable String idUsuario) {
    try {
        int pontos = gamificacaoService.buscarPontosUsuario(idUsuario);
        return ResponseEntity.ok(pontos);

    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());

    } catch (Exception e) {
        return ResponseEntity.status(500).body("Erro ao buscar pontos do usuário.");
    }
}
}