package br.com.m3Tech.appGasLegado.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClienteDto {
    private Integer idCliente;
    private String nome;
    private String documento;
    private String telefone;
    private String observacao;
    private List<ClienteEndereco> clienteEnderecos;
    private List<PedidoLegadoSimplesDto> ultimosPedidos;
    private Boolean viaApi;
}
