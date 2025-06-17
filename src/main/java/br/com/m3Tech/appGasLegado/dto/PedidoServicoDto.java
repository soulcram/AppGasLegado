package br.com.m3Tech.appGasLegado.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PedidoServicoDto {

    private Integer idPedido;
    private String telefoneCliente;
    private String nomeCliente;
    private String documentoCliente;
    private String cep;
    private String logradouro;
    private String numeroResidencia;
    private String complemento;
    private String bairro;
    private BigDecimal valorTotal;
    private String formaPagamento;
    private BigDecimal troco;
    private String observacao;
    private String pedido;
    private String hora;
    private String loja;
    private String lojaOriginal;
    private Boolean reimprimir;
    private String entregador;
    private String status;

}
