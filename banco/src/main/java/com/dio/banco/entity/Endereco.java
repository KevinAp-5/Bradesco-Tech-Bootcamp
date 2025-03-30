package com.dio.banco.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String complemento;

    public boolean validarCep() {
        // Simulação de validação de CEP
        if (cep.matches("\\d{5}-\\d{3}")) {
            System.out.println("CEP válido!");
            return true;
        } else {
            System.out.println("CEP inválido!");
            return false;
        }
    }
}
