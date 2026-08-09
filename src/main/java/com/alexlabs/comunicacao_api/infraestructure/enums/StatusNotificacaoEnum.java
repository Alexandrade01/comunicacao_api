package com.alexlabs.comunicacao_api.infraestructure.enums;

public enum StatusNotificacaoEnum {

    PENDENTE,
    NOTIFICADO,
    CANCELADO;

    public static StatusNotificacaoEnum fromString(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("StatusNotificacaoEnum não pode ser nulo ou vazio");
        }

        return StatusNotificacaoEnum.valueOf(value.trim().toUpperCase());
    }
}

