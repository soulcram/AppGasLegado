package br.com.m3Tech.appGasLegado.dto;

import lombok.Data;

@Data
public class Endereco {
    private int idEndereco;
    private String logradouro;
    private String cep;
    private String bairro;
    private String cidade;
    private String estado;
}
