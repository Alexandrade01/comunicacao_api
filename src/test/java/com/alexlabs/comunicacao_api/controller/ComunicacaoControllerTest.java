package com.alexlabs.comunicacao_api.controller;

import com.alexlabs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.alexlabs.comunicacao_api.business.service.ComunicacaoService;
import com.alexlabs.comunicacao_api.infraestructure.enums.ModoEnvioEnum;
import com.alexlabs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ComunicacaoControllerTest {

    @Mock
    private ComunicacaoService service;

    @InjectMocks
    private ComunicacaoController controller;

    @Test
    void deveAgendarComunicacao() {
        ComunicacaoOutDTO response = ComunicacaoOutDTO.builder()
                .dataHoraEnvio(new Date())
                .nomeDestinatario("Maria")
                .emailDestinatario("maria@email.com")
                .telefoneDestinatario("11999999999")
                .mensagem("Olá")
                .modoDeEnvio(ModoEnvioEnum.EMAIL)
                .statusEnvio(StatusEnvioEnum.PENDENTE)
                .build();

        when(service.agendarComunicacao(org.mockito.ArgumentMatchers.any())).thenReturn(response);

        var responseEntity = controller.agendar(null);

        verify(service).agendarComunicacao(org.mockito.ArgumentMatchers.any());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
    }

    @Test
    void deveBuscarStatusDaComunicacao() {
        ComunicacaoOutDTO response = ComunicacaoOutDTO.builder()
                .dataHoraEnvio(new Date())
                .nomeDestinatario("Maria")
                .emailDestinatario("maria@email.com")
                .telefoneDestinatario("11999999999")
                .mensagem("Olá")
                .modoDeEnvio(ModoEnvioEnum.EMAIL)
                .statusEnvio(StatusEnvioEnum.PENDENTE)
                .build();

        when(service.buscarStatusComunicacao("maria@email.com")).thenReturn(response);

        var responseEntity = controller.buscarStatus("maria@email.com");

        verify(service).buscarStatusComunicacao("maria@email.com");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
    }

    @Test
    void deveCancelarComunicacao() {
        ComunicacaoOutDTO response = ComunicacaoOutDTO.builder()
                .dataHoraEnvio(new Date())
                .nomeDestinatario("Maria")
                .emailDestinatario("maria@email.com")
                .telefoneDestinatario("11999999999")
                .mensagem("Olá")
                .modoDeEnvio(ModoEnvioEnum.EMAIL)
                .statusEnvio(StatusEnvioEnum.CANCELADO)
                .build();

        when(service.alterarStatusComunicacao("maria@email.com")).thenReturn(response);

        var responseEntity = controller.cancelarStatus("maria@email.com");

        verify(service).alterarStatusComunicacao("maria@email.com");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
    }

    @Test
    void deveEnviarEmail() {
        when(service.enviarEmail(anyString())).thenReturn("Email enviado com sucesso para o email: maria@email.com");

        var responseEntity = controller.enviarEmail("maria@email.com");

        verify(service).enviarEmail("maria@email.com");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Email enviado para maria@email.com", responseEntity.getBody());
    }
}
