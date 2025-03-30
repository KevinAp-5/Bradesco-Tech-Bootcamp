package com.dio.banco.transacao;

public enum TipoTransacao {
    DEPOSITO("Depósito"),
    SAQUE("Saque"),
    TRANSFERENCIA("Transferência"),
    PAGAMENTO("Pagamento"),
    ESTORNO("Estorno");

    private String descricao;

    TipoTransacao(String descricao) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return descricao;
    }

}
