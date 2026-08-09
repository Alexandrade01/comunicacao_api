package com.alexlabs.comunicacao_api.infraestructure.client;
import com.alexlabs.comunicacao_api.api.dto.TarefaComunicacaoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "notificacao", url = "${notificacao.url}")
public interface EmailClient {

    @PostMapping
    void enviarEmail(@RequestBody TarefaComunicacaoDTO dto);

}
