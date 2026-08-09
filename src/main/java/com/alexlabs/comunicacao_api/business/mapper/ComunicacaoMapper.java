package com.alexlabs.comunicacao_api.business.mapper;


import com.alexlabs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.alexlabs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.alexlabs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComunicacaoMapper {


    @Mapping(source = "dataHoraenvio", target = "dataHoraEnvio")
    ComunicacaoOutDTO toDTO(ComunicacaoEntity entity);

    @Mapping(source = "dataHoraEnvio", target = "dataHoraenvio")
    ComunicacaoEntity toEntity(ComunicacaoInDTO dto);


}
