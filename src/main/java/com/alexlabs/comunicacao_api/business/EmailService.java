package com.alexlabs.comunicacao_api.business;

import com.alexlabs.comunicacao_api.api.dto.TarefaComunicacaoDTO;
import com.alexlabs.comunicacao_api.infraestructure.client.EmailClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private final EmailClient emailClient;

    public void enviarEmail(TarefaComunicacaoDTO email) {

        emailClient.enviarEmail(email);

    }
}
