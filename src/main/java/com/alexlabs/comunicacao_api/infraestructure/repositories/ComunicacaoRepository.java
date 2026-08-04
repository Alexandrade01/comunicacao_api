package com.alexlabs.comunicacao_api.infraestructure.repositories;


import com.alexlabs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import org.springframework.data.repository.CrudRepository;

public interface ComunicacaoRepository extends CrudRepository<ComunicacaoEntity, Long> {

    ComunicacaoEntity findByEmailDestinatario(String nomeDestinatario);
}
