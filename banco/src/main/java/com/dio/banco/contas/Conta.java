package com.dio.banco.contas;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dio.banco.entity.Usuario;
import com.dio.banco.transacao.TipoTransacao;
import com.dio.banco.transacao.Transacao;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public abstract class Conta {

    protected String numeroConta;
    protected String agencia;

    @Setter(AccessLevel.NONE)
    protected double saldo;

    protected Usuario titular;
    protected boolean ativa;

    @Setter(AccessLevel.NONE)
    protected List<Transacao> transacoes = new ArrayList<>();

    protected Conta(String numeroConta, String agencia, double saldo, Usuario titular, boolean ativa) {
        this.numeroConta = numeroConta;
        this.agencia = agencia;
        this.saldo = saldo;
        this.titular = titular;
        this.ativa = ativa;
    }

    public void depositar(double valor) {
        if (valor < 0) {
            System.out.println("Valor inválido para depósito.");
            return;
        }
        if (!ativa) {
            System.out.println("Conta bloqueada. Não é possível realizar depósitos.");
            return;
        }

        Transacao transacao = new Transacao(LocalDateTime.now(), valor, TipoTransacao.DEPOSITO);
        transacoes.add(transacao);
        saldo += valor;
        System.out.println("Depósito de " + valor + " realizado com sucesso!");
        System.out.println("Novo saldo: " + saldo);
    }

    public List<Transacao> gerarExtratos() {
        return this.transacoes;
    }

    public abstract void sacar(double valor);

    public double consultarSaldo() {
        return saldo;
    }

    public boolean bloquear() {
        if (ativa) {
            ativa = false;
            System.out.println("Conta bloqueada com sucesso!");
            return true;
        } else {
            System.out.println("Conta já está bloqueada.");
            return false;
        }
    }

    public boolean desbloquear() {
        if (!ativa) {
            ativa = true;
            System.out.println("Conta desbloqueada com sucesso!");
            return true;
        } else {
            System.out.println("Conta já está desbloqueada.");
            return false;
        }
    }
}
