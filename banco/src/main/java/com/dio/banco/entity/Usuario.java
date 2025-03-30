package com.dio.banco.entity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
    private String name;

    @Setter(AccessLevel.NONE)
    private String cpf;
    private String email;
    private String telefone;
    private Endereco endereco;
    private LocalDateTime dataNascimento;

    public boolean cadastrar() {
        // Simulação de cadastro
        System.out.println("Usuário cadastrado com sucesso!");
        return true;
    }

    public boolean atualizar() {
        // Simulação de atualização
        System.out.println("Usuário atualizado com sucesso!");
        return true;
    }

    public boolean validarDocumento() {
        // Simulação de validação de CPF
        if (cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            System.out.println("CPF válido!");
            return true;
        } else {
            System.out.println("CPF inválido!");
            return false;
        }
    }
}
