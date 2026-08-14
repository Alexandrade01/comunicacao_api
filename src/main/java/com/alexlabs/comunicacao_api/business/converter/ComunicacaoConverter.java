package com.alexlabs.comunicacao_api.business.converter;


import com.alexlabs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.alexlabs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.alexlabs.comunicacao_api.api.dto.TarefaComunicacaoDTO;
import com.alexlabs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import com.alexlabs.comunicacao_api.infraestructure.enums.StatusNotificacaoEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

@AllArgsConstructor
@Component
public class ComunicacaoConverter {

    private static final DateTimeFormatter DATA_HORA_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm:ss")
            .optionalStart()
            .appendFraction(ChronoField.NANO_OF_SECOND, 1, 9, true)
            .optionalEnd()
            .toFormatter();

    public TarefaComunicacaoDTO  paraEnviarEmail(ComunicacaoOutDTO entity) {

        return TarefaComunicacaoDTO.builder()
                .id(null)
                .nomeTarefa("Comunicação - Aviso!")
                .descricao(entity.getMensagem())
                .dataCriacao(LocalDateTime.now())
                .dataEvento(LocalDateTime.parse(entity.getDataHoraEnvio().toString(), DATA_HORA_FORMATTER))
                .emailUsuario(entity.getEmailDestinatario())
                .dataAlteração(null)
                .status(StatusNotificacaoEnum.fromString(entity.getStatusEnvio().toString()))
                .build();
    }
}
