package br.com.m3Tech.appGasLegado.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ClienteLegadoDto {

    private String telefoneCliente;
    private String nomeCliente;
    private String cep;
    private String documentoCliente;
    private String logradouro;
    private String numeroResidencia;
    private String complemento;
    private String bairro;
    private String observacao;

}
