package com.startup.enterquest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.startup.enterquest.model.Recompensa;
import com.startup.enterquest.model.ResgatarRecompensaRequest;
import com.startup.enterquest.model.ResgateRecompensa;
import com.startup.enterquest.service.RecompensaService;

@RestController
@RequestMapping("/recompensas")
public class RecompensaController {

    private final RecompensaService recompensaService;

    public RecompensaController(RecompensaService recompensaService) {
        this.recompensaService = recompensaService;
    }

    @PostMapping
    public ResponseEntity<?> criarRecompensa(@RequestBody Recompensa recompensa) {
        try {
            return ResponseEntity.ok(recompensaService.criarRecompensa(recompensa));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao criar recompensa.");
        }
    }

    @GetMapping
    public ResponseEntity<?> listarRecompensas() {
        try {
            List<Recompensa> recompensas = recompensaService.listarRecompensas();
            return ResponseEntity.ok(recompensas);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao listar recompensas.");
        }
    }

    @PutMapping("/{idRecompensa}")
    public ResponseEntity<?> atualizarRecompensa(
            @PathVariable String idRecompensa,
            @RequestBody Recompensa recompensa) {
        try {
            return ResponseEntity.ok(recompensaService.atualizarRecompensa(idRecompensa, recompensa));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao atualizar recompensa.");
        }
    }

    @DeleteMapping("/{idRecompensa}")
    public ResponseEntity<?> removerRecompensa(@PathVariable String idRecompensa) {
        try {
            return ResponseEntity.ok(recompensaService.removerRecompensa(idRecompensa));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao remover recompensa.");
        }
    }
    @PostMapping("/resgatar")
public ResponseEntity<?> resgatarRecompensa(@RequestBody ResgatarRecompensaRequest request) {
    try {
        return ResponseEntity.ok(recompensaService.resgatarRecompensa(request));

    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());

    } catch (Exception e) {
        return ResponseEntity.status(500).body("Erro ao resgatar recompensa.");
    }
}
@GetMapping("/resgates/usuario/{idUsuario}")
public ResponseEntity<?> listarResgatesPorUsuario(@PathVariable String idUsuario) {
    try {
        List<ResgateRecompensa> resgates = recompensaService.listarResgatesPorUsuario(idUsuario);

        System.out.println("Quantidade recebida no controller: " + resgates.size());

        return ResponseEntity.ok(resgates);

    } catch (Exception e) {
        return ResponseEntity.status(500).body("Erro ao listar recompensas resgatadas.");
    }
}
@GetMapping("/resgates")
public ResponseEntity<?> listarTodosResgates() {
    try {
        List<ResgateRecompensa> resgates = recompensaService.listarTodosResgates();
        return ResponseEntity.ok(resgates);

    } catch (Exception e) {
        return ResponseEntity.status(500).body("Erro ao listar todos os resgates.");
    }
}
@PutMapping("/resgates/{idResgate}/confirmar-entrega")
public ResponseEntity<?> confirmarEntregaResgate(@PathVariable String idResgate) {
    try {
        return ResponseEntity.ok(recompensaService.confirmarEntregaResgate(idResgate));

    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());

    } catch (Exception e) {
        return ResponseEntity.status(500).body("Erro ao confirmar entrega do resgate.");
    }
}
}