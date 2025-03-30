package com.dio.banco.contas;

import java.time.LocalDateTime;

import com.dio.banco.entity.Usuario;
import com.dio.banco.transacao.TipoTransacao;
import com.dio.banco.transacao.Transacao;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class ContaCorrente extends Conta {
    private static final String NOVO_SALDO_MSG = "Novo saldo: ";
    private double limiteChequeEspecial;
    private double taxaManutencao;

    public ContaCorrente(String numeroConta, String agencia, double saldo, Usuario titular, boolean ativa) {
        super(numeroConta, agencia, saldo, titular, ativa);
        this.limiteChequeEspecial = 100;
        this.taxaManutencao = 5;
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

        boolean saqueRealizado = false;

        if (saldo >= valor) {
            saldo -= valor;
            saqueRealizado = true;
        } else if (saldo + limiteChequeEspecial >= valor) {
            double valorChequeEspecial = valor - saldo;
            saldo = 0;
            limiteChequeEspecial -= valorChequeEspecial;
            saqueRealizado = true;
        }

        Transacao transacao = new Transacao(LocalDateTime.now(), valor, TipoTransacao.SAQUE);
        transacoes.add(transacao);

        if (saqueRealizado) {
            System.out.println(NOVO_SALDO_MSG + saldo);
            System.out.println("Novo limite de cheque especial: " + limiteChequeEspecial);
        } else {
            System.out.println("Saldo e cheque especial insuficientes para o saque de " + valor);
            System.out.println("Saldo disponível: " + saldo);
            System.out.println("Limite de cheque especial disponível: " + limiteChequeEspecial);
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
        } else if (saldo + limiteChequeEspecial >= valor) {
            double valorChequeEspecial = valor - saldo;
            saldo = 0;
            limiteChequeEspecial -= valorChequeEspecial;
            contaDestino.depositar(valor);
            transferenciaRealizada = true;
        }

        Transacao transacaoSaida = new Transacao(LocalDateTime.now(), valor, TipoTransacao.TRANSFERENCIA);
        transacoes.add(transacaoSaida);

        Transacao transacaoEntrada = new Transacao(LocalDateTime.now(), valor, TipoTransacao.TRANSFERENCIA);
        contaDestino.getTransacoes().add(transacaoEntrada);

        if (transferenciaRealizada) {
            System.out.println("Transferência de " + valor + " realizada com sucesso!");
            System.out.println(NOVO_SALDO_MSG + saldo);
            System.out.println("Novo limite de cheque especial: " + limiteChequeEspecial);
        } else {
            System.out.println("Saldo e cheque especial insuficientes para a transferência de " + valor);
            System.out.println("Saldo disponível: " + saldo);
            System.out.println("Limite de cheque especial disponível: " + limiteChequeEspecial);
        }
    }

    public void ajustarLimiteChequeEspecial(double novoLimite) {
        if (novoLimite < 0) {
            System.out.println("Valor inválido para o limite do cheque especial.");
            return;
        }
        this.limiteChequeEspecial = novoLimite;
        System.out.println("Novo limite de cheque especial ajustado: " + limiteChequeEspecial);
    }

    public double consultarLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    public void descontarDataManutencao() {
        if (this.isAtiva()) {
            if (saldo >= taxaManutencao) {
                saldo -= taxaManutencao;
                System.out.println("Taxa de manutenção descontada com sucesso!");
                System.out.println(NOVO_SALDO_MSG + saldo);
            } else {
                System.out.println("Saldo insuficiente para descontar a taxa de manutenção.");
            }
        } else {
            System.out.println("A conta não está ativa.");
        }
    }
}
