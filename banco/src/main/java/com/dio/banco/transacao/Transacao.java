package com.dio.banco.transacao;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Transacao {
    private UUID uuid;
    private LocalDateTime dataHora;
    private double valor;
    private TipoTransacao tipo;

    public Transacao(LocalDateTime dataHora, double valor, TipoTransacao tipo) {
        this.uuid = UUID.randomUUID();
        this.dataHora = dataHora;
        this.valor = valor;
        this.tipo = tipo;
    }
}
