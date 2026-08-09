package com.alexlabs.comunicacao_api.infraestructure.exception.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Resposta de erro da API")
public class ErrorResponseDTO {

    @Schema(description = "Data e hora do erro", example = "2026-06-28T14:30:00")
    private LocalDateTime timestamp;

    @Schema(description = "Código de status HTTP", example = "404")
    private int status;

    @Schema(description = "Tipo do erro", example = "Not Found")
    private String error;

    @Schema(description = "Mensagem descritiva do erro", example = "Usuário não encontrado")
    private String message;

    @Schema(description = "Caminho da requisição que gerou o erro", example = "/usuario/getByEmail")
    private String path;
}
