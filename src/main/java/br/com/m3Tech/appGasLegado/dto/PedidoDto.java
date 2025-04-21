package br.com.m3Tech.appGasLegado.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class PedidoDto {

    private String telefoneCliente;
    private String nomeCliente;
    private String documentoCliente;
    private String logradouro;
    private String numeroResidencia;
    private String complemento;
    private String bairro;
    private BigDecimal valorTotal;
    private String formaPagamento;
    private BigDecimal troco;
    private String observacao;
    private String loja;

    List<PedidoProdutoDto> produtos;
}
