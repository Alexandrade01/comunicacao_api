package com.alexlabs.comunicacao_api.controller;


import com.alexlabs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.alexlabs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.alexlabs.comunicacao_api.business.service.ComunicacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comunicacao")
@Tag(name = "Comunicacao", description = "API para gerenciamento de comunicações")
public class ComunicacaoController {

    private final ComunicacaoService service;

    public ComunicacaoController(ComunicacaoService service) {
        this.service = service;
    }

    @Operation(
            summary = "Agendar comunicação",
            description = "Agenda uma nova comunicação para o usuário.",
            tags = {"Comunicações"}
    )
    @PostMapping("/agendar")
    public ResponseEntity<ComunicacaoOutDTO> agendar(@RequestBody ComunicacaoInDTO dto)  {
        return ResponseEntity.ok(service.agendarComunicacao(dto));
    }

    @Operation(
            summary = "Buscar status da comunicação",
            description = "Busca o status de uma comunicação específica do usuário.",
            tags = {"Comunicações"}
    )
    @GetMapping()
    public ResponseEntity<ComunicacaoOutDTO> buscarStatus(@RequestParam String emailDestinatario) {
        return ResponseEntity.ok(service.buscarStatusComunicacao(emailDestinatario));
    }

    @Operation(
            summary = "Cancelar comunicação",
            description = "Cancela uma comunicação específica do usuário.",
            tags = {"Comunicações"}
    )
    @PatchMapping("/cancelar")
    public ResponseEntity<ComunicacaoOutDTO> cancelarStatus(@RequestParam String emailDestinatario) {
        return ResponseEntity.ok(service.alterarStatusComunicacao(emailDestinatario));
    }

    @Operation(
            summary = "Enviar email",
            description = "Envia um email para o destinatário especificado.",
            tags = {"Comunicações"}
    )
    @PostMapping("/enviar-email")
    public ResponseEntity<String> enviarEmail(@RequestParam String emailDestinatario) {

        service.enviarEmail(emailDestinatario);

        return ResponseEntity.ok("Email enviado para " + emailDestinatario);
    }
}
