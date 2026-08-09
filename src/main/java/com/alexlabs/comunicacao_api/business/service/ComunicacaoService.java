package com.alexlabs.comunicacao_api.business.service;


import com.alexlabs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.alexlabs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.alexlabs.comunicacao_api.business.EmailService;
import com.alexlabs.comunicacao_api.business.converter.ComunicacaoConverter;
import com.alexlabs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import com.alexlabs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import com.alexlabs.comunicacao_api.infraestructure.repositories.ComunicacaoRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ComunicacaoService {

    private final ComunicacaoRepository repository;
    private final ComunicacaoConverter converter;
    private final EmailService emailService;

    public ComunicacaoService(ComunicacaoRepository repository, ComunicacaoConverter converter, EmailService emailService) {
        this.repository = repository;
        this.converter = converter;
        this.emailService = emailService;
    }

    public ComunicacaoOutDTO agendarComunicacao(ComunicacaoInDTO dto) {
        if (Objects.isNull(dto)) {
            throw new RuntimeException();
        }
        dto.setStatusEnvio(StatusEnvioEnum.PENDENTE);
        ComunicacaoEntity entity = converter.paraEntity(dto);
        repository.save(entity);
        ComunicacaoOutDTO outDTO = converter.paraDTO(entity);
        return outDTO;
    }

    public ComunicacaoOutDTO buscarStatusComunicacao(String emailDestinatario) {
        ComunicacaoEntity entity = repository.findByEmailDestinatario(emailDestinatario);
        if (Objects.isNull(entity)) {
            throw new RuntimeException();
        }
        return converter.paraDTO(entity);
    }

    public ComunicacaoOutDTO alterarStatusComunicacao(String emailDestinatario) {
        ComunicacaoEntity entity = repository.findByEmailDestinatario(emailDestinatario);
        if (Objects.isNull(entity)) {
            throw new RuntimeException();
        }
        entity.setStatusEnvio(StatusEnvioEnum.CANCELADO);
        repository.save(entity);
        return (converter.paraDTO(entity));
    }

    public String enviarEmail(String emailDestinatario) {

        ComunicacaoEntity entity = repository.findByEmailDestinatario(emailDestinatario);
        if (Objects.isNull(entity) || entity.getStatusEnvio() == StatusEnvioEnum.CANCELADO) {
            throw new RuntimeException();
        }
        ComunicacaoOutDTO dto = converter.paraDTO(entity);

        try {
            emailService.enviarEmail(converter.paraEnviarEmail(dto));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "Email enviado com sucesso para o email: " + dto.getEmailDestinatario();
    }

}
