package com.alexlabs.comunicacao_api.business.service;

import com.alexlabs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.alexlabs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.alexlabs.comunicacao_api.api.dto.TarefaComunicacaoDTO;
import com.alexlabs.comunicacao_api.business.EmailService;
import com.alexlabs.comunicacao_api.business.converter.ComunicacaoConverter;
import com.alexlabs.comunicacao_api.business.mapper.ComunicacaoMapper;
import com.alexlabs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import com.alexlabs.comunicacao_api.infraestructure.enums.ModoEnvioEnum;
import com.alexlabs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import com.alexlabs.comunicacao_api.infraestructure.repositories.ComunicacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ComunicacaoServiceTest {

    @Mock
    private ComunicacaoRepository repository;

    @Mock
    private ComunicacaoConverter converter;

    @Mock
    private EmailService emailService;

    @Mock
    private ComunicacaoMapper mapper;

    @InjectMocks
    private ComunicacaoService service;

    @Test
    void deveAgendarComunicacaoComStatusPendente() {
        ComunicacaoInDTO dto = ComunicacaoInDTO.builder()
                .dataHoraEnvio(new Date())
                .nomeDestinatario("Maria")
                .emailDestinatario("maria@email.com")
                .telefoneDestinatario("11999999999")
                .mensagem("Olá")
                .modoDeEnvio(ModoEnvioEnum.EMAIL)
                .build();

        ComunicacaoEntity entity = ComunicacaoEntity.builder()
                .dataHoraenvio(dto.getDataHoraEnvio())
                .nomeDestinatario(dto.getNomeDestinatario())
                .emailDestinatario(dto.getEmailDestinatario())
                .telefoneDestinatario(dto.getTelefoneDestinatario())
                .mensagem(dto.getMensagem())
                .modoDeEnvio(dto.getModoDeEnvio())
                .statusEnvio(StatusEnvioEnum.PENDENTE)
                .build();

        ComunicacaoOutDTO response = ComunicacaoOutDTO.builder()
                .dataHoraEnvio(entity.getDataHoraenvio())
                .nomeDestinatario(entity.getNomeDestinatario())
                .emailDestinatario(entity.getEmailDestinatario())
                .telefoneDestinatario(entity.getTelefoneDestinatario())
                .mensagem(entity.getMensagem())
                .modoDeEnvio(entity.getModoDeEnvio())
                .statusEnvio(entity.getStatusEnvio())
                .build();

        when(mapper.toEntity(any(ComunicacaoInDTO.class))).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(response);

        ComunicacaoOutDTO result = service.agendarComunicacao(dto);

        ArgumentCaptor<ComunicacaoInDTO> dtoCaptor = ArgumentCaptor.forClass(ComunicacaoInDTO.class);
        verify(mapper).toEntity(dtoCaptor.capture());
        verify(repository).save(entity);
        verify(mapper).toDTO(entity);

        assertEquals(StatusEnvioEnum.PENDENTE, dtoCaptor.getValue().getStatusEnvio());
        assertEquals(response, result);
    }

    @Test
    void deveLancarExcecaoAoAgendarComunicacaoNula() {
        assertThrows(RuntimeException.class, () -> service.agendarComunicacao(null));

        verify(repository, never()).save(any());
        verify(mapper, never()).toEntity(any());
    }

    @Test
    void deveBuscarStatusDaComunicacao() {
        ComunicacaoEntity entity = ComunicacaoEntity.builder()
                .dataHoraenvio(new Date())
                .nomeDestinatario("Maria")
                .emailDestinatario("maria@email.com")
                .telefoneDestinatario("11999999999")
                .mensagem("Olá")
                .modoDeEnvio(ModoEnvioEnum.EMAIL)
                .statusEnvio(StatusEnvioEnum.PENDENTE)
                .build();

        ComunicacaoOutDTO response = ComunicacaoOutDTO.builder()
                .dataHoraEnvio(entity.getDataHoraenvio())
                .nomeDestinatario(entity.getNomeDestinatario())
                .emailDestinatario(entity.getEmailDestinatario())
                .telefoneDestinatario(entity.getTelefoneDestinatario())
                .mensagem(entity.getMensagem())
                .modoDeEnvio(entity.getModoDeEnvio())
                .statusEnvio(entity.getStatusEnvio())
                .build();

        when(repository.findByEmailDestinatario("maria@email.com")).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(response);

        ComunicacaoOutDTO result = service.buscarStatusComunicacao("maria@email.com");

        verify(repository).findByEmailDestinatario("maria@email.com");
        verify(mapper).toDTO(entity);
        assertEquals(response, result);
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarComunicacao() {
        when(repository.findByEmailDestinatario("inexistente@email.com")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> service.buscarStatusComunicacao("inexistente@email.com"));

        verify(mapper, never()).toDTO(any());
    }

    @Test
    void deveCancelarComunicacao() {
        ComunicacaoEntity entity = ComunicacaoEntity.builder()
                .dataHoraenvio(new Date())
                .nomeDestinatario("Maria")
                .emailDestinatario("maria@email.com")
                .telefoneDestinatario("11999999999")
                .mensagem("Olá")
                .modoDeEnvio(ModoEnvioEnum.EMAIL)
                .statusEnvio(StatusEnvioEnum.PENDENTE)
                .build();

        ComunicacaoOutDTO response = ComunicacaoOutDTO.builder()
                .dataHoraEnvio(entity.getDataHoraenvio())
                .nomeDestinatario(entity.getNomeDestinatario())
                .emailDestinatario(entity.getEmailDestinatario())
                .telefoneDestinatario(entity.getTelefoneDestinatario())
                .mensagem(entity.getMensagem())
                .modoDeEnvio(entity.getModoDeEnvio())
                .statusEnvio(StatusEnvioEnum.CANCELADO)
                .build();

        when(repository.findByEmailDestinatario("maria@email.com")).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(response);

        ComunicacaoOutDTO result = service.alterarStatusComunicacao("maria@email.com");

        verify(repository).findByEmailDestinatario("maria@email.com");
        verify(repository).save(entity);
        verify(mapper).toDTO(entity);
        assertEquals(StatusEnvioEnum.CANCELADO, entity.getStatusEnvio());
        assertEquals(response, result);
    }

    @Test
    void deveLancarExcecaoAoCancelarComunicacaoInexistente() {
        when(repository.findByEmailDestinatario("inexistente@email.com")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> service.alterarStatusComunicacao("inexistente@email.com"));

        verify(repository, never()).save(any());
        verify(mapper, never()).toDTO(any());
    }

    @Test
    void deveEnviarEmailQuandoComunicacaoEstiverAtiva() {
        ComunicacaoEntity entity = ComunicacaoEntity.builder()
                .dataHoraenvio(new Date())
                .nomeDestinatario("Maria")
                .emailDestinatario("maria@email.com")
                .telefoneDestinatario("11999999999")
                .mensagem("Olá")
                .modoDeEnvio(ModoEnvioEnum.EMAIL)
                .statusEnvio(StatusEnvioEnum.PENDENTE)
                .build();

        ComunicacaoOutDTO dto = ComunicacaoOutDTO.builder()
                .dataHoraEnvio(entity.getDataHoraenvio())
                .nomeDestinatario(entity.getNomeDestinatario())
                .emailDestinatario(entity.getEmailDestinatario())
                .telefoneDestinatario(entity.getTelefoneDestinatario())
                .mensagem(entity.getMensagem())
                .modoDeEnvio(entity.getModoDeEnvio())
                .statusEnvio(entity.getStatusEnvio())
                .build();

        TarefaComunicacaoDTO tarefa = TarefaComunicacaoDTO.builder()
                .id(null)
                .nomeTarefa("Comunicação - Aviso!")
                .descricao(dto.getMensagem())
                .emailUsuario(dto.getEmailDestinatario())
                .status(null)
                .build();

        when(repository.findByEmailDestinatario("maria@email.com")).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(dto);
        when(converter.paraEnviarEmail(dto)).thenReturn(tarefa);

        String resultado = service.enviarEmail("maria@email.com");

        verify(repository).findByEmailDestinatario("maria@email.com");
        verify(mapper).toDTO(entity);
        verify(converter).paraEnviarEmail(dto);
        verify(emailService).enviarEmail(tarefa);
        assertEquals("Email enviado com sucesso para o email: maria@email.com", resultado);
    }

    @Test
    void deveLancarExcecaoAoEnviarEmailComComunicacaoCancelada() {
        ComunicacaoEntity entity = ComunicacaoEntity.builder()
                .dataHoraenvio(new Date())
                .emailDestinatario("maria@email.com")
                .statusEnvio(StatusEnvioEnum.CANCELADO)
                .build();

        when(repository.findByEmailDestinatario("maria@email.com")).thenReturn(entity);

        assertThrows(RuntimeException.class, () -> service.enviarEmail("maria@email.com"));

        verify(emailService, never()).enviarEmail(any());
        verify(converter, never()).paraEnviarEmail(any());
    }
}

