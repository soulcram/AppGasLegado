package br.com.m3Tech.appGasLegado.dto;

import lombok.Data;

@Data
public class ClienteEndereco {
    private Integer idClienteEndereco;
    private String numero;
    private String apelido;
    private String complemento;
    private Endereco endereco;
}
