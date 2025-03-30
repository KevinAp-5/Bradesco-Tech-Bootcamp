package com.dio.banco.contas;

import java.time.LocalDateTime;

import com.dio.banco.entity.Usuario;
import com.dio.banco.transacao.TipoTransacao;
import com.dio.banco.transacao.Transacao;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class ContaPoupanca extends Conta {
    private double taxaRendimento;

    public ContaPoupanca(String numeroConta, String agencia, double saldo, Usuario titular, boolean ativa,
            double taxaRendimento) {
        super(numeroConta, agencia, saldo, titular, ativa);
        this.taxaRendimento = taxaRendimento;
    }

    @Override
    public void sacar(double valor) {
        if (valor <= 0) {
            System.out.println("Valor de saque inválido.");
            return;
        }

        if (!this.isAtiva()) {
            System.out.println("A conta não está ativa.");
            return;
        }

        if (saldo >= valor) {
            saldo -= valor;
            System.out.println("Saque de " + valor + " realizado com sucesso!");
            System.out.println("Novo saldo: " + saldo);
            transacoes.add(new Transacao(LocalDateTime.now(), valor, TipoTransacao.SAQUE));
        } else {
            System.out.println("Saldo insuficiente para o saque de " + valor);
            System.out.println("Saldo disponível: " + saldo);
        }
    }

    public void transferir(Conta contaDestino, double valor) {
        if (valor <= 0) {
            System.out.println("Valor de transferência inválido.");
            return;
        }

        if (!this.isAtiva()) {
            System.out.println("A conta de origem não está ativa.");
            return;
        }

        if (!contaDestino.isAtiva()) {
            System.out.println("A conta de destino não está ativa.");
            return;
        }

        boolean transferenciaRealizada = false;

        if (saldo >= valor) {
            saldo -= valor;
            contaDestino.depositar(valor);
            transferenciaRealizada = true;
        }

        Transacao transacaoSaida = new Transacao(LocalDateTime.now(), valor, TipoTransacao.TRANSFERENCIA);
        transacoes.add(transacaoSaida);

        Transacao transacaoEntrada = new Transacao(LocalDateTime.now(), valor, TipoTransacao.TRANSFERENCIA);
        contaDestino.getTransacoes().add(transacaoEntrada);

        if (transferenciaRealizada) {
            System.out.println("Transferência de " + valor + " realizada com sucesso!");
            System.out.println("Novo saldo: " + saldo);
        } else {
            System.out.println("Saldo insuficiente para a transferência de " + valor);
            System.out.println("Saldo disponível: " + saldo);
        }
    }

    public void calcularRendimentos(int dias) {
        if (dias <= 0) {
            System.out.println("Número de dias inválido.");
            return;
        }

        double rendimentos = saldo * Math.pow((1 + taxaRendimento / 100), dias / 30.0) - saldo;
        System.out.println("Rendimentos em " + dias + " dias: " + rendimentos);
        saldo += rendimentos;
    }
}
