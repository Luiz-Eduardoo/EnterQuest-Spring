package com.startup.enterquest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.startup.enterquest.model.Chamado;
import com.startup.enterquest.model.DashboardResumo;
import com.startup.enterquest.service.ChamadoService;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    private final ChamadoService chamadoService;

    public ChamadoController(ChamadoService chamadoService) {
        this.chamadoService = chamadoService;
    }

    @PostMapping
public ResponseEntity<?> criarChamado(@RequestBody Chamado chamado) {
    try {
        String resposta = chamadoService.criarChamado(chamado);
        return ResponseEntity.ok(resposta);

    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());

    } catch (Exception e) {
        return ResponseEntity.status(500).body("Erro ao abrir chamado");
    }
}
    @GetMapping
    public List<Chamado> listarChamados() throws Exception {
    return chamadoService.listarChamados();
    }
    @GetMapping("/usuario/{idUsuario}")
public List<Chamado> listarChamadosPorUsuario(@PathVariable String idUsuario) throws Exception {
    return chamadoService.listarChamadosPorUsuario(idUsuario);
}
@PutMapping("/{idChamado}/status")
public ResponseEntity<?> atualizarStatusChamado(
        @PathVariable String idChamado,
        @RequestBody Map<String, String> body) {

    try {
        String novoStatus = body.get("status");

        boolean atualizado = chamadoService.atualizarStatusChamado(idChamado, novoStatus);

        if (!atualizado) {
            return ResponseEntity.badRequest().body("Status inválido");
        }

        return ResponseEntity.ok("Status atualizado com sucesso!");

    } catch (Exception e) {
        return ResponseEntity.status(500).body("Erro ao atualizar status");
    }
}
@GetMapping("/dashboard")
public DashboardResumo gerarResumoDashboard() throws Exception {
    return chamadoService.gerarResumoDashboard();
}
}